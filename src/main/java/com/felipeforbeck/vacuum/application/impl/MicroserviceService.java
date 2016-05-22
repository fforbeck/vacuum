package com.felipeforbeck.vacuum.application.impl;

import com.felipeforbeck.vacuum.application.MicroserviceServiceInterface;
import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.domain.model.MicroserviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fforbeck on 21/05/16.
 */
@Service
public class MicroserviceService implements MicroserviceServiceInterface {

    @Autowired
    private MicroserviceRepository repository;

    public String newMicroservice(String swaggerUrl) {
        return repository.newMicroservice(swaggerUrl);
    }

    public List<Microservice> findMicroservicesByPath(String path) {
        return repository.findMicroservicesByPathTerm(path);
    }

}
