package com.felipeforbeck.vacuum.domain.model;

import java.io.Serializable;

/**
 * Created by fforbeck on 21/05/16.
 */
public class Endpoint implements Serializable {

    private String path;

    public Endpoint() {
    }

    public Endpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}