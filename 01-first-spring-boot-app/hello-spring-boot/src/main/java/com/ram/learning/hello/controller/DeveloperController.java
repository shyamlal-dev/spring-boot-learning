package com.ram.learning.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ram.learning.hello.service.DeveloperService;

@RestController
public class DeveloperController{

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService){
        this.developerService = developerService;
    }

    @GetMapping("/build")
    public String build(){
        return developerService.buildProject();
    }
    
}