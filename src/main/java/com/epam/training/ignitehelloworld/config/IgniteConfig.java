package com.epam.training.ignitehelloworld.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class IgniteConfig {

    @Bean
    public String localHost() {
        return "127.0.0.1";
    }

    @Bean
    public DiscoverySpi discoverySpi() {
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setIpFinder(ipFinder());
        return discoverySpi;
    }

    @Bean
    public TcpDiscoveryIpFinder ipFinder() {
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(addresses());
        return ipFinder;
    }

    @Bean
    public List<String> addresses() {
        return Collections.singletonList(localHost() + ":47500..47509");
    }

    @Bean
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setLocalHost(localHost());
        cfg.setDiscoverySpi(discoverySpi());
        return cfg;
    }

    @Bean
    public Ignite ignite(IgniteConfiguration igniteConfig, ApplicationContext context) throws IgniteCheckedException {
        return IgniteSpring.start(igniteConfig, context);
    }
}
