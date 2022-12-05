package com.luciamoreyra.microservices.limitsservice.controllers;

import com.luciamoreyra.microservices.limitsservice.bean.Limits;
import com.luciamoreyra.microservices.limitsservice.configuraton.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    private final Configuration configuration;

    public LimitsController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public Limits retrieveLimits(){
//        return new Limits(1,1000);
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }

}
