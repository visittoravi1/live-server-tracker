package org.opengraph.lst.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.opengraph.lst.anlysis.SummaryAnalyzer;
import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.repos.StatRepository;
import org.opengraph.lst.core.service.AnalysisService;

public class AnalysisServiceImpl implements AnalysisService {
	
	private SummaryAnalyzer analyzer;
	private StatRepository repository;
	
	public AnalysisServiceImpl(SummaryAnalyzer analyzer, StatRepository repository) {
		this.analyzer = analyzer;
		this.repository = repository;
	}

	@Override
	public Map<String, Map<String, AppAnalysis>> getAnalysis(String flowName) {
		return analyzer.getAppAnalysis(flowName);
	}

	@Override
	public List<Stat> getStats(String flow, String app) {
		return repository.get().stream()
				.filter(stat -> stat.getFlow().equals(flow))
				.filter(stat -> StringUtils.isEmpty(app) || app.equals(stat.getApp()))
				.collect(Collectors.toList());
	}

	@Override
	public void save(Stat stat) {
		repository.save(stat);
	}

}
