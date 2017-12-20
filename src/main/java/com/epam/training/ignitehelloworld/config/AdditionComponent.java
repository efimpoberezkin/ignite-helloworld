package com.epam.training.ignitehelloworld.config;

import org.springframework.stereotype.Component;

@Component(value = "addition-component")
public class AdditionComponent {

    public int add(int a, int b) {
        return a + b;
    }
}
