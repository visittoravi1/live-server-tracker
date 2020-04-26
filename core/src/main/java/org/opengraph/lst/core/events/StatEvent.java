package org.opengraph.lst.core.events;

import org.springframework.context.ApplicationEvent;

/**
 * @author ravi
 * Event to be fired if a stat recieved
 */
public class StatEvent extends ApplicationEvent {

    public StatEvent(Object source) {
        super(source);
    }
}
