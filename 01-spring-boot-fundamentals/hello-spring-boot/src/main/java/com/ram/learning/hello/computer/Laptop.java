package com.ram.learning.hello.computer;

import org.springframework.stereotype.Component;

@Component
//@Primary
public class Laptop implements Computer{

    @Override 
    public String compile(){
        return "Compiling project using Laptop.";
    } 
}