package com.felipeforbeck.vacuum.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fforbeck on 21/05/16.
 */
public class Microservice implements Serializable {

    private String host;

    private Set<Endpoint> endpoints = new HashSet<>();

    public Microservice() {
    }

    public Microservice(String host) {
        this.host = host;
    }

    public boolean has(Endpoint endpoint) {
        return endpoints.add(endpoint);
    }

    public String getHost() {
        return host;
    }

    public Set<Endpoint> getEndpoints() {
        return endpoints;
    }
}
