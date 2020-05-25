package org.opengraph.lst.web.controllers;

import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService service;

    @GetMapping
    public Map<String, Map<String, AppAnalysis>> getAnalysis(@RequestParam(value = "flow", required = false) String app) {
        return service.getAnalysis(app);
    }
}
