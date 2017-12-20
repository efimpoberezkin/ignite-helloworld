package com.epam.training.ignitehelloworld;

import com.epam.training.ignitehelloworld.logic.CacheFiller;
import com.epam.training.ignitehelloworld.service.SumService;
import com.epam.training.ignitehelloworld.service.SumServiceImpl;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.log4j.Logger;

import java.util.Collections;

import static java.lang.Thread.sleep;

/**
 * Trying out plain Java Ignite configuration
 */
public class ClientNodeApp {

    private static final Logger log = Logger.getRootLogger();

    public static void main(String[] args) {
        Ignite ignite = Ignition.start(configuration());

        // Asynchronously filling cache with random numbers
        ignite.compute(ignite.cluster().forLocal()).runAsync(new CacheFiller(SumService.CACHE_NAME));

        // Deploying summation service
        ignite.services().deployNodeSingleton(SumService.SERVICE_NAME, new SumServiceImpl());

        // Calling summation service
        SumService sumService = ignite.services().serviceProxy(SumService.SERVICE_NAME, SumService.class, true);
        for (int i = 0; i < 100; i++) {
            log.info("--- Sum calculated by service : " + sumService.getSum() + " ---");
            pause();
        }
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

    private static void pause() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
