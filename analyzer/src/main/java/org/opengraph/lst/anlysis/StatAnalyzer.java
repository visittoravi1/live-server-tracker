package org.opengraph.lst.anlysis;

import org.opengraph.lst.core.beans.Summary;
import org.opengraph.lst.core.events.SummaryEvent;
import org.opengraph.lst.core.repos.StatRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StatAnalyzer extends Analyzer{

    private StatRepository repository;
    private ApplicationEventPublisher publisher;

    public StatAnalyzer(StatRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Async
    public void analyze(@NonNull String id) {
        Summary summary = createSummary(id, repository.get(id));
        if (summary != null && !summary.isIncomplete()) {
            publisher.publishEvent(new SummaryEvent(summary));
        }
    }
}
