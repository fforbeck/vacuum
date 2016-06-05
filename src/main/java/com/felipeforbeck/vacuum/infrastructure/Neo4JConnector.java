package com.felipeforbeck.vacuum.infrastructure;

import org.apache.log4j.Logger;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
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
        ping();
        LOG.info("Graph DB started");
    }

    private void ping() {
        Session session = this.driver.session();
        session.isOpen();
        session.close();
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
