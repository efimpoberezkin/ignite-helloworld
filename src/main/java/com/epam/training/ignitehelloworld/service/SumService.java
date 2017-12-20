package com.epam.training.ignitehelloworld.service;

public interface SumService {

    String SERVICE_NAME = "summationService";
    String CACHE_NAME = "summationServiceCache";

    int getSum();
}
