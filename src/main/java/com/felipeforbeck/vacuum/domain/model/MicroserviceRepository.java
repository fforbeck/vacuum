package com.felipeforbeck.vacuum.domain.model;

import java.util.List;

/**
 * Created by fforbeck on 21/05/16.
 */
public interface MicroserviceRepository {

    String newMicroservice(String url);

    List<Microservice> findMicroservicesByPathTerm(String term);

}
