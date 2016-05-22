package com.felipeforbeck.vacuum.domain.shared;

import java.io.Serializable;

/**
 * Created by fforbeck on 22/05/16.
 */
public class RequestEventMetaData implements Serializable {

    private String method;

    private String origin_host;

    private String target_host;

    private String target_path;

    private Long timestamp;

    public RequestEventMetaData() {

    }

    public RequestEventMetaData(String target_host, String origin_host, String method, String target_path, Long timestamp) {
        this.target_host = target_host;
        this.origin_host = origin_host;
        this.method = method;
        this.target_path = target_path;
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getOrigin_host() {
        return origin_host;
    }

    public String getTarget_host() {
        return target_host;
    }

    public String getTarget_path() {
        return target_path;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
