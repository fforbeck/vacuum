package com.felipeforbeck.vacuum.infrastructure;

import org.apache.log4j.Logger;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by fforbeck on 21/05/16.
 */
@Component
public class Neo4JConnector {

    private static final Logger LOG = Logger.getLogger(Neo4JConnector.class);

    private Driver driver;

    @PostConstruct
    public void init() {
        this.driver = GraphDatabase.driver("bolt://localhost");
        LOG.info("Graph DB started");
    }

    @PreDestroy
    public void terminate() {
        driver.close();
        LOG.info("Graph DB terminated");
    }

    public Driver getDriver() {
        return driver;
    }
}
