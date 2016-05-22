package com.felipeforbeck.vacuum.infrastructure;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * Created by fforbeck on 21/05/16.
 */
@Component
public class Neo4JConnector {

    private static final Logger LOG = Logger.getLogger(Neo4JConnector.class);

    private GraphDatabaseService graphDatabaseService;

    @Value("${db_path}")
    private String dbPath;

    @PostConstruct
    public void init() {
        this.graphDatabaseService = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(dbPath))
                .newGraphDatabase();
        LOG.info("Graph DB started: " + dbPath);
    }

    @PreDestroy
    public void terminate() {
        graphDatabaseService.shutdown();
        LOG.info("Graph DB terminated: " + dbPath);
    }

    public GraphDatabaseService getGraphDB() {
        return graphDatabaseService;
    }

}
