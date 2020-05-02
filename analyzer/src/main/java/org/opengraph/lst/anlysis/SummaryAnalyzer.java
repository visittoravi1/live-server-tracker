package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.beans.Summary;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Analyze a summary
 */
public class SummaryAnalyzer {

    private Map<String, Map<String, AppAnalysis>> appAnalysis;
    private Map<String, AppAnalysis> overallAnalysis;

    public SummaryAnalyzer() {
        this.appAnalysis = new ConcurrentHashMap<>();
        this.overallAnalysis = new ConcurrentHashMap<>();
    }

    public Map<String, Map<String, AppAnalysis>> getAppAnalysis(Optional<String> flow) {
        return flow.isEmpty() ? appAnalysis : Map.of(flow.get(), appAnalysis.get(flow.get()));
    }

    public void analyzeSummary(Summary summary) {
        if (summary.isIncomplete()) {
            return;
        }
        // Get flow of the summary
        appAnalysis.compute(summary.getFlow(), (key, val) -> {
            if (val == null) {
                val = new ConcurrentHashMap<>();
            }
            return val;
        });
        summary.getTimelines().entrySet().forEach(e -> {
            String app = e.getKey();
            appAnalysis.get(summary.getFlow()).compute(app, (key, val) -> {
                if (val == null) {
                    val = new AppAnalysis(app);
                    val.setMin(Long.MAX_VALUE);
                    val.setMax(Long.MIN_VALUE);
                }
                val.setSum(val.getSum() + ((double)e.getValue().getTimeToComplete() / 1000));
                val.setCount(val.getCount() + 1);
                val.setAverage((val.getSum() * 1000) / val.getCount());
                if (val.getRange().getEnd() == null || val.getRange().getEnd().isBefore(e.getValue().getEnd())) {
                    val.getRange().setEnd(e.getValue().getEnd());
                }
                if (val.getRange().getStart() == null || val.getRange().getStart().isAfter(e.getValue().getStart())) {
                    val.getRange().setStart(e.getValue().getStart());
                }
                if (e.getValue().getTimeToComplete() > val.getMax()) {
                    val.setMax(e.getValue().getTimeToComplete());
                }
                if (e.getValue().getTimeToComplete() < val.getMin()) {
                    val.setMin(e.getValue().getTimeToComplete());
                }
                return val;
            });
        });
        computeOverallAnalysis(summary);
    }

    private void computeOverallAnalysis(Summary summary) {
        String flow = summary.getFlow();
        overallAnalysis.compute(flow, (key, val) -> {
            if (val == null) {
                val = new AppAnalysis();
            }
            val.setSum(val.getSum() + summary.getTimelines().values().stream().map(Summary.Timeline::getTimeToComplete).collect(Collectors.summingLong(Long::longValue)));
            val.setCount(val.getCount() + 1);
            val.setAverage((double)val.getSum() / val.getCount());
            LocalDateTime maxEndDateTime = summary.getTimelines().values().stream().map(Summary.Timeline::getEnd).max(LocalDateTime::compareTo).orElse(null);
            LocalDateTime minStartDateTime = summary.getTimelines().values().stream().map(Summary.Timeline::getStart).min(LocalDateTime::compareTo).orElse(null);
            if (minStartDateTime != null && (val.getRange().getStart() == null || val.getRange().getStart().isAfter(minStartDateTime))) {
                val.getRange().setStart(minStartDateTime);
            }
            if (maxEndDateTime != null && (val.getRange().getEnd() == null || val.getRange().getEnd().isBefore(maxEndDateTime))) {
                val.getRange().setEnd(maxEndDateTime);
            }
            return val;
        });
    }
}
