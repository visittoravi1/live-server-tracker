package org.opengraph.lst.core.events;

import org.springframework.context.ApplicationEvent;

/**
 * @author ravi
 * Event to be fired if a stat recieved
 */
public class StatEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatEvent(Object source) {
        super(source);
    }
}
