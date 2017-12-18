package com.epam.training.ignitehelloworld;

import com.epam.training.ignitehelloworld.config.IgniteConfig;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Trying out Spring based Ignite configuration
 */
public class ServerNodeApp {

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(IgniteConfig.class);
        Ignite ignite = appContext.getBean(IgniteSpringBean.class);
    }
}
