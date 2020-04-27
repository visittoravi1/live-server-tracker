package org.opengraph.lst.web.controllers;

import org.opengraph.lst.core.beans.AppAnalysis;
import org.opengraph.lst.core.repos.StatRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @GetMapping
    public Map<String, AppAnalysis> getAnalysis() {
        return null;
    }
}
