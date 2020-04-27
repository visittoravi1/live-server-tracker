package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.beans.Summary;
import org.opengraph.lst.core.events.BackupEvent;
import org.opengraph.lst.core.events.StatEvent;
import org.opengraph.lst.core.events.SummaryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.MultiValueMap;

public class ApplicationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);

    private StatAnalyzer statAnalyzer;
    private SummaryAnalyzer summaryAnalyzer;

    public ApplicationEventListener(StatAnalyzer statAnalyzer, SummaryAnalyzer summaryAnalyzer) {
        this.statAnalyzer = statAnalyzer;
        this.summaryAnalyzer = summaryAnalyzer;
    }

    @EventListener
    public void backup(BackupEvent event) {
        @SuppressWarnings("unchecked")
		MultiValueMap<String, Stat> backup = (MultiValueMap<String, Stat>)event.getSource();
        LOGGER.info("Backup event received with size {}", backup.size());
    }

    @EventListener
    @Async
    public void recordState(StatEvent event) {
        Stat stat = (Stat) event.getSource();
        LOGGER.info("State event received {}", stat);
        statAnalyzer.analyze(stat.getId());
    }

    @EventListener
    public void analyzeSummary(SummaryEvent event) {
        Summary summary = (Summary) event.getSource();
        LOGGER.info("Analyzing summary with flow {}", summary.getFlow());
        summaryAnalyzer.analyzeSummary(summary);
    }
}
