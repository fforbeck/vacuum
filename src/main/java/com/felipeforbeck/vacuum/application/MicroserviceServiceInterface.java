package com.felipeforbeck.vacuum.application;

import com.felipeforbeck.vacuum.domain.model.Microservice;

import java.util.List;

/**
 * Created by fforbeck on 22/05/16.
 */
public interface MicroserviceServiceInterface {

    /**
     * Registers a new microservice in the system based on the data captured from swagger documentation.
     *
     * @param swaggerUrl        swagger documentation url
     * @return Microservice id
     */
    String newMicroservice(String swaggerUrl);

    /**
     * Requests a list of microservices with endpoints that contains the specified term in the path.
     *
     * @param termPath          term path
     * @return A list of possible microservices with endpoints matching the term path
     */
    List<Microservice> findMicroservicesByPath(String termPath);

}
