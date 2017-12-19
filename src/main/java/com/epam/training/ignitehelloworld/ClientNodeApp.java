package com.epam.training.ignitehelloworld;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.log4j.Logger;

import java.util.Collections;

/**
 * Trying out plain Java Ignite configuration
 */
public class ClientNodeApp {

    @LoggerResource
    private static final Logger log = Logger.getRootLogger();

    public static void main(String[] args) {
        Ignite ignite = Ignition.start(configuration());

        IgniteCache<Integer, String> cache = ignite.getOrCreateCache("helloWorldCache");

        cache.put(1, "Hello");
        cache.put(2, "World!");

        log.info("Filled cache with values");

        ignite.compute().broadcast(
                new IgniteRunnable() {
                    @Override
                    public void run() {
                        String hello = cache.get(1);
                        String world = cache.get(2);

                        log.info(hello + " " + world);
                    }
                }
        );
    }

    private static IgniteConfiguration configuration() throws IgniteException {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setLocalHost("127.0.0.1");

        cfg.setClientMode(true);

        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));

        discoverySpi.setIpFinder(ipFinder);

        cfg.setDiscoverySpi(discoverySpi);

        return cfg;
    }
}
