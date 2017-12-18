package com.epam.training.ignitehelloworld;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 * Trying out xml based Ignite configuration
 */
public class AnotherServerNodeApp {

    public static void main(String[] args) {
        Ignite ignite = Ignition.start("spring-context.xml");
    }
}
