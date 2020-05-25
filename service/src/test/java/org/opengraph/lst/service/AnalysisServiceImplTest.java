package org.opengraph.lst.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.opengraph.lst.anlysis.SummaryAnalyzer;
import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.repos.StatRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link AnalysisServiceImpl}
 */
public class AnalysisServiceImplTest {

    private static final String FLOW_NAME = "Flow";

    @Spy
    private SummaryAnalyzer analyzer;
    @Spy
    private StatRepository repository;

    private AnalysisServiceImpl analysisService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        // Mock repository
        List<Stat> stats = new ArrayList<>();
        Stat stat1 = new Stat();
        stat1.setFlow(FLOW_NAME);
        stat1.setApp("App1");
        stats.add(stat1);
        Stat stat2 = new Stat();
        stat2.setFlow(FLOW_NAME);
        stat2.setApp("App2");
        stats.add(stat2);
        doReturn(stats).when(repository).get();
        analysisService = new AnalysisServiceImpl(analyzer, repository);
    }

    @Test
    public void testGetAnalysis() {
        analysisService.getAnalysis("");
        verify(analyzer).getAppAnalysis(anyString());
    }

    @Test
    public void testGetStats() {
        List<Stat> stats = analysisService.getStats(FLOW_NAME, null);
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
        assertEquals(2, stats.size());
        stats = analysisService.getStats(FLOW_NAME, "App1");
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
        assertEquals(1, stats.size());
    }

    @Test
    public void testSave() {
        analysisService.save(new Stat());
        verify(repository).save(any(Stat.class));
    }
}
