package com.felipeforbeck.vacuum.domain.shared;

import java.io.Serializable;

/**
 * Created by fforbeck on 21/05/16.
 */
public class MicroserviceMetaData implements Serializable {

    private String swagger_url;

    private String path_term;

    public String getPath_term() {
        return path_term;
    }

    public String getSwagger_url() {
        return swagger_url;
    }
}
