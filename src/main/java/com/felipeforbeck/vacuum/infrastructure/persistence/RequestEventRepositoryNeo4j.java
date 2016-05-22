package com.felipeforbeck.vacuum.infrastructure.persistence;

import com.felipeforbeck.vacuum.domain.model.RequestEventRepository;
import com.felipeforbeck.vacuum.domain.shared.RequestEventMetaData;
import com.felipeforbeck.vacuum.infrastructure.Neo4JConnector;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Created by fforbeck on 21/05/16.
 */
@Repository
public class RequestEventRepositoryNeo4j implements RequestEventRepository {

    @Autowired
    private Neo4JConnector connector;

    @Override
    public String saveRequest(RequestEventMetaData event) {
        final String uuid = nextId();

        HashMap<String, Object> params = new HashMap<>();
        params.put("origin", event.getOrigin_host());
        params.put("target", event.getTarget_host());
        params.put("path", event.getTarget_path());

        Driver driver = connector.getDriver();
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            StringBuilder statement = new StringBuilder();
            String method = event.getMethod().toUpperCase();
            statement
                    .append("MATCH (s1:Service {host: {origin}}), (s2:Service {host: {target}})")
                    .append(" CREATE UNIQUE (s1)-[r1:").append(method).append(" {path: {path}}]->(s2)")
                    .append(" CREATE UNIQUE (s1)-[r2:CALL]->(s2)")
                    .append(" SET r1.count = coalesce(r1.count, 0) + 1")
                    .append(" SET r2.count = coalesce(r2.count, 0) + 1");
            tx.run(statement.toString(), params);
            tx.success();
        }
        return uuid;
    }

    private String nextId() {
        return java.util.UUID.randomUUID().toString();
    }

}
