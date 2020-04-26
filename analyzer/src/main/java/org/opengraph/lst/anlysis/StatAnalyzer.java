package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.StringUtil;
import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.beans.Summary;
import org.opengraph.lst.core.events.SummaryEvent;
import org.opengraph.lst.core.repos.StatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class StatAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatAnalyzer.class);

    private StatRepository repository;
    private ApplicationEventPublisher publisher;

    public StatAnalyzer(StatRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public void analyze(@NonNull String id) {
        Summary summary = createSummary(id, repository.get(id));
        if (summary != null && !summary.isIncomplete()) {
            publisher.publishEvent(new SummaryEvent(summary));
        }
    }

    private Summary createSummary(String id, List<Stat> stats) {
        Summary summary = new Summary();
        Set<String> ids = stats.stream().map(Stat::getId).distinct().collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(ids) || ids.size() > 1) {
            LOGGER.warn("Unique ids are more than one {}", ids);
            return null;
        }
        if (stats.stream().map(Stat::getFlow).distinct().count() != 1) {
            LOGGER.error("Found more than one flow name for request with id {}", id);
            return null;
        }
        summary.setFlow(stats.stream().map(Stat::getFlow).distinct().findFirst().orElse(StringUtil.EMPTY));
        Map<String, List<Stat>> group = stats.stream().collect(groupingBy(Stat::getApp));
        for (Map.Entry<String, List<Stat>> entry: group.entrySet()) {
            String app = entry.getKey();
            // Populate timeline and errors
            Map<Stat.Type, List<Stat>> statTypeMap = entry.getValue().stream()
                    .filter(stat -> stat.getType().equals(Stat.Type.START) || stat.getType().equals(Stat.Type.END))
                    .collect(groupingBy(Stat::getType));
            if (CollectionUtils.isEmpty(statTypeMap) || statTypeMap.size() < 2) {
                LOGGER.warn("Stat is not complete for app {}", app);
                summary.getProgress().add(app + " didn't get START and END event both");
            } else {
                summary.getTimelines().put(app, new Summary.Timeline(statTypeMap.get(Stat.Type.START).get(0).getReceivedOn(), statTypeMap.get(Stat.Type.END).get(0).getReceivedOn()));
            }
            // Populate data
            entry.getValue().stream()
                    .filter(stat -> stat.getType().equals(Stat.Type.DATA))
                    .filter(stat -> !CollectionUtils.isEmpty(stat.getData()))
                    .collect(Collectors.toSet()).forEach(stat -> summary.getData().put(app, stat.getData()));
        }
        return summary;
    }
}
