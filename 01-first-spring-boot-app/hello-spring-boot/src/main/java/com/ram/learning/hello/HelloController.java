package com.ram.learning.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController{

    @GetMapping("/")
    public String home() {
        return "My first Spring Boot application is running!";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot!";
    }
}
