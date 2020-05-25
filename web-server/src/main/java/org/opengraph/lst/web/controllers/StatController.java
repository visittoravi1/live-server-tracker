package org.opengraph.lst.web.controllers;

import java.util.List;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.service.AnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
public class StatController {

    private AnalysisService service;

    public StatController(AnalysisService service) {
        this.service = service;
    }

    @PutMapping
    public void save(@RequestBody Stat stat) {
        service.save(stat);
    }
    
    @GetMapping
    public List<Stat> stats(@RequestParam(name = "flow") String flow, @RequestParam(name = "app", required = false) String app) {
    	return service.getStats(flow, app);
    }

}
