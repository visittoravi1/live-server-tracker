package org.opengraph.lst.web.controllers;

import org.opengraph.lst.anlysis.SummaryAnalyzer;
import org.opengraph.lst.core.beans.AppAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private SummaryAnalyzer analyzer;

    @GetMapping
    public Map<String, Map<String, AppAnalysis>> getAnalysis(@RequestParam(value = "app", required = false) Optional<String> app) {
        return analyzer.getAppAnalysis(app);
    }
}
