package com.felipeforbeck.vacuum.infrastructure.persistence;

import com.felipeforbeck.vacuum.application.util.Labels;
import com.felipeforbeck.vacuum.application.util.RelationshipTypes;
import com.felipeforbeck.vacuum.application.util.SwaggerToMicroserviceConverter;
import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.domain.model.MicroserviceRepository;
import com.felipeforbeck.vacuum.infrastructure.Neo4JConnector;
import io.swagger.parser.SwaggerParser;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fforbeck on 21/05/16.
 */
@Repository
public class MicroserviceRepositoryNeo4j implements MicroserviceRepository {

    @Autowired
    private Neo4JConnector connector;

    @Autowired
    private SwaggerToMicroserviceConverter converter;

    @Override
    public String saveMicroservice(String url) {
        Microservice microservice = converter.convert(new SwaggerParser().read(url));

        final String uuid = nextId();

        Driver driver = connector.getDriver();
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            microservice.getEndpoints().forEachRemaining(endpoint -> {
                int countEndpoints = 0;
                StringBuilder statement = new StringBuilder();
                statement
                        .append("MERGE (s").append(countEndpoints).append(":Service { host: '")
                        .append(microservice.getHost()).append("',")
                        .append(" uuid: '").append(uuid).append("'})")
                        .append(" CREATE UNIQUE (s").append(countEndpoints).append(")")
                        .append("-[:").append(RelationshipTypes.EXPOSES).append("]->")
                        .append("(e").append(countEndpoints).append(":").append(Labels.ENDPOINT)
                        .append(" {path:'").append(endpoint.getPath()).append("'})");
                tx.run(statement.toString());
                countEndpoints++;
                tx.success();
            });
        }
        return uuid;
    }

    @Override
    public List<Microservice> findMicroservicesByPathTerm(String term) {
        List<Microservice> microservices = new ArrayList<>();
        Driver driver = connector.getDriver();
        HashMap<String, Object> params = new HashMap<>();
        params.put("term", term);
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            StatementResult r = tx.run("MATCH (s:Service)-[:EXPOSES]->(e:Endpoint) WHERE e.path CONTAINS {term} RETURN DISTINCT s.host",
                    params);
            r.forEachRemaining(record -> microservices.add(new Microservice(record.get("s.host").asString())));
            tx.success();
        }
        return microservices;
    }

    @Override
    public List<Microservice> findDependants(String microserviceId) {
        List<Microservice> microservices = new ArrayList<>();

        HashMap<String, Object> params = new HashMap<>();
        params.put("uuid", microserviceId);

        Driver driver = connector.getDriver();
        try (Session session = driver.session();
             Transaction tx = session.beginTransaction()) {
            StringBuilder sb = new StringBuilder();
            sb.append("MATCH (s1:Service {uuid: {uuid}})<-[:CALL]-(s2:Service)");
            sb.append(" RETURN s2.host");
            StatementResult r = tx.run(sb.toString(), params);
            r.forEachRemaining(record -> microservices.add(new Microservice(record.get("s2.host").asString())));
            tx.success();
        }
        return microservices;
    }

    private String nextId() {
        return java.util.UUID.randomUUID().toString();
    }

}
