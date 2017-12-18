package com.epam.training.ignitehelloworld;

import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Trying out Spring based Ignite configuration
 */
@SpringBootApplication
public class ServerNodeApp {

    @Autowired
    private Ignite ignite;

    public static void main(String[] args) {
        SpringApplication.run(ServerNodeApp.class, args);
    }
}
