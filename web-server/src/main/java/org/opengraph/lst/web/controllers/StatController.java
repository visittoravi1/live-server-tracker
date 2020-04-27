package org.opengraph.lst.web.controllers;

import org.opengraph.lst.core.beans.Stat;
import org.opengraph.lst.core.repos.StatRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
public class StatController {

    private StatRepository repository;

    public StatController(StatRepository repository) {
        this.repository = repository;
    }

    @PutMapping
    public void save(@RequestBody Stat stat) {
        repository.save(stat);
    }

}
