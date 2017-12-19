package com.epam.training.ignitehelloworld;

import com.epam.training.ignitehelloworld.logic.HelloRunnable;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Collections;

/**
 * Trying out plain Java Ignite configuration
 */
public class ClientNodeApp {

    private static final String CACHE_NAME = "helloWorldCache";

    public static void main(String[] args) {
        Ignite ignite = Ignition.start(configuration());

        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME);

        cache.put(1, "Hello");
        cache.put(2, "World!");

        ignite.compute().broadcast(new HelloRunnable(CACHE_NAME));
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
