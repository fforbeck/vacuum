package com.felipeforbeck.vacuum.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
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

    public Iterator<Endpoint> getEndpoints() {
        return endpoints.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Microservice that = (Microservice) o;

        return new EqualsBuilder()
                .append(host, that.host)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(host)
                .toHashCode();
    }
}
