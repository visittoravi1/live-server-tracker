package org.opengraph.lst.repository.fs;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.events.BackupEvent;
import org.opengraph.lst.core.events.StatEvent;
import org.opengraph.lst.core.repos.StatRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ravi
 *
 * An in memory implementation of {@link StatRepository}
 */
public class InMemoryStatRepository implements StatRepository {

    private static final int MAX_REPOSITORY_SIZE = 5000;

    private MultiValueMap<String, Stat> store = new LinkedMultiValueMap<>(MAX_REPOSITORY_SIZE);
    private ApplicationEventPublisher publisher;

    public InMemoryStatRepository(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void save(Stat stat) {
        store.add(stat.getId(), stat);
        synchronized (store) {
            if (this.store.size() >= MAX_REPOSITORY_SIZE) {
                publisher.publishEvent(new BackupEvent(store));
            }
        }
        publisher.publishEvent(new StatEvent(stat));
    }

    @Override
    public List<Stat> get(String id) {
        return store.get(id);
    }

	@Override
	public List<Stat> get() {
		return store.values().parallelStream().flatMap(Collection::stream).collect(Collectors.toList());
	}
}
