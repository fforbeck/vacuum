package com.felipeforbeck.vacuum.application.util;

/**
 * Created by fforbeck on 22/05/16.
 */
public class Queries {

    public static String ALL_MICROSERVICES_WHICH_CONTAINS_TERM =
            "MATCH (s:SERVICE)-[r:HAS]->(e:ENDPOINT) WHERE e.path CONTAINS {term} RETURN s.host, e.path";
}
