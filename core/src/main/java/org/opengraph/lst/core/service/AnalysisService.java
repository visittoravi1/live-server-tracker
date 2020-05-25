package org.opengraph.lst.core.service;

import java.util.List;
import java.util.Map;

import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.beans.Stat;

public interface AnalysisService {
	
	/**
	 * Save stat
	 * @param stat
	 * @return
	 */
	void save(Stat stat);
	/**
	 * Get analysis
	 * @param flowName
	 * @return
	 */
	Map<String, Map<String, AppAnalysis>> getAnalysis(String flowName);
	
	/**
	 * List stats for given app
	 * @param flow
	 * @param app
	 * @return
	 */
	List<Stat> getStats(String flow, String app);
}
