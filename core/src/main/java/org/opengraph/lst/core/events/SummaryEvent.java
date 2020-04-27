package org.opengraph.lst.core.events;

import org.springframework.context.ApplicationEvent;

/**
 * Event to fire when a summary is available
 */
public class SummaryEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SummaryEvent(Object source) {
        super(source);
    }
}
