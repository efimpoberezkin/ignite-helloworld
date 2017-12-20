package com.epam.training.ignitehelloworld.service;

import com.epam.training.ignitehelloworld.config.AdditionComponent;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.LoggerResource;
import org.apache.ignite.resources.SpringResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;

import javax.cache.Cache;

public class SumServiceImpl implements Service, SumService {

    @IgniteInstanceResource
    private transient Ignite ignite;

    @LoggerResource
    private transient IgniteLogger log;

    @SpringResource(resourceClass = AdditionComponent.class)
    private transient AdditionComponent additionComponent;

    private transient IgniteCache<Integer, Integer> cache;

    private String serviceName;

    @Override
    public void init(ServiceContext ctx) {
        cache = ignite.cache(SumService.CACHE_NAME);

        serviceName = ctx.name();

        log.info("Service was initialized: " + serviceName);
    }

    @Override
    public void execute(ServiceContext ctx) {
        log.info("Executing service: " + serviceName);
    }

    @Override
    public void cancel(ServiceContext ctx) {
        log.info("Service was cancelled: " + serviceName);
    }

    @Override
    public int getSum() {
        int sum = 0;
        for (Cache.Entry<Integer, Integer> entry : cache) {
            sum = additionComponent.add(sum, entry.getValue());
        }
        return sum;
    }
}
