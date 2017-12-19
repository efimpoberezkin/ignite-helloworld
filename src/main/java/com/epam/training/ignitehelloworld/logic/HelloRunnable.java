package com.epam.training.ignitehelloworld.logic;

import com.epam.training.ignitehelloworld.config.SeparateComponent;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;

public class HelloRunnable implements IgniteRunnable {

    @IgniteInstanceResource
    private Ignite ignite;

    @LoggerResource
    private transient IgniteLogger log;

    @SpringResource(resourceName = "separate-component")
    private transient SeparateComponent separateComponent;

    private String cacheName;

    public HelloRunnable(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public void run() {
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cacheName);

        String hello = cache.get(1);
        String world = cache.get(2);

        log.info(hello + " " + world);

        // trying out a Spring component outside of Ignite context

        log.info(separateComponent.sayHi());
    }
}
