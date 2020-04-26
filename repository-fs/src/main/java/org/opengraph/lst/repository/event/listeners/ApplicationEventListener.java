package org.opengraph.lst.repository.event.listeners;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.events.BackupEvent;
import org.opengraph.lst.core.events.StatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.util.MultiValueMap;

public class ApplicationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventListener.class);

    @EventListener
    public void backup(BackupEvent event) {
        MultiValueMap<String, Stat> backup = (MultiValueMap<String, Stat>)event.getSource();
        LOGGER.info("Backup event received with size {}", backup.size());
    }

    @EventListener
    public void recordState(StatEvent event) {
        Stat state = (Stat) event.getSource();
        LOGGER.info("State event received {}", state);
    }
}
