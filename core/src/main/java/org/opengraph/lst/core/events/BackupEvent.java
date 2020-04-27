package org.opengraph.lst.core.events;

import org.springframework.context.ApplicationEvent;

/**
 * @author ravi
 * This event is fired when backup needs to be done
 */
public class BackupEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BackupEvent(Object source) {
        super(source);
    }
}
