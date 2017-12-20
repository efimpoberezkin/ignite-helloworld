package com.epam.training.ignitehelloworld.config;

import org.springframework.stereotype.Component;

@Component
public class AdditionComponent {

    public int add(int a, int b) {
        return a + b;
    }
}
