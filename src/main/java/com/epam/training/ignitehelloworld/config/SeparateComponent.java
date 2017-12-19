package com.epam.training.ignitehelloworld.config;

import org.springframework.stereotype.Component;

@Component(value = "separate-component")
public class SeparateComponent {

    public String sayHi() {
        return "Hi";
    }
}
