package org.opengraph.lst.core.repos;

import org.opengraph.lst.core.beans.Stat;

import java.util.List;

/**
 * Stat repository
 */
public interface StatRepository {
    void save(Stat stat);
    List<Stat> get(String id);
}
