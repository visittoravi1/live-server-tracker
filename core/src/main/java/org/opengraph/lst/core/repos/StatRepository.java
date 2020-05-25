package org.opengraph.lst.core.repos;

import org.opengraph.lst.core.beans.Stat;

import java.util.List;

/**
 * @author ravi
 *
 */
public interface StatRepository {
    /**
     * Save {@link Stat}
     * @param stat
     */
    void save(Stat stat);
    
    /**
     * list stat by id
     * @param id
     * @return
     */
    List<Stat> get(String id);
    
    /**
     * List all stats
     * @return
     */
    List<Stat> get();
}
