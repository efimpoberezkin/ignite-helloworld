package com.epam.training.ignitehelloworld.logic;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;

import java.util.Random;

import static java.lang.Thread.sleep;

public class CacheFiller implements IgniteRunnable {

    @IgniteInstanceResource
    private transient Ignite ignite;

    @LoggerResource
    private transient IgniteLogger log;

    private String cacheName;

    public CacheFiller(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public void run() {
        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(cacheName);
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int randomInt = random.nextInt(10) + 1;
            cache.put(i, randomInt);
            log.info("--- Value put into cache : " + randomInt + " ---");
            pause();
        }
    }

    private void pause() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
