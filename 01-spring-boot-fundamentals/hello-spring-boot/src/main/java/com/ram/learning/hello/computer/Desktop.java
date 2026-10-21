package com.ram.learning.hello.computer;

import org.springframework.stereotype.Component;

@Component
public class Desktop implements Computer{

    @Override 
    public String compile(){
        return "Compiling project using Desktop.";
    } 
}