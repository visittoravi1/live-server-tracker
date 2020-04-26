package org.opengraph.lst.core.events;

import org.springframework.context.ApplicationEvent;

/**
 * Event to fire when a summary is available
 */
public class SummaryEvent extends ApplicationEvent {

    public SummaryEvent(Object source) {
        super(source);
    }
}
