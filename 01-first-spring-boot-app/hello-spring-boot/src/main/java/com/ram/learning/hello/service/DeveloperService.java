package com.ram.learning.hello.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ram.learning.hello.computer.Computer;

@Service
public class DeveloperService{

    //@Autowired
    private final Computer computer;
    
    public DeveloperService(@Qualifier("desktop") Computer computer){
        this.computer = computer;
    }

    public String buildProject(){
        return computer.compile();
    }
}