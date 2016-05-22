package com.felipeforbeck.vacuum.infrastructure.persistence;

import com.felipeforbeck.vacuum.application.util.LabelTypes;
import com.felipeforbeck.vacuum.application.util.Queries;
import com.felipeforbeck.vacuum.application.util.RelationshipTypes;
import com.felipeforbeck.vacuum.application.util.SwaggerToMicroserviceConverter;
import com.felipeforbeck.vacuum.domain.model.Endpoint;
import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.domain.model.MicroserviceRepository;
import com.felipeforbeck.vacuum.infrastructure.Neo4JConnector;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
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
    public String newMicroservice(String url) {
        Swagger swagger = new SwaggerParser().read(url);
        Microservice microservice = converter.convert(swagger);

        String uuid = java.util.UUID.randomUUID().toString();

        GraphDatabaseService graphDB = connector.getGraphDB();
        try (Transaction tx = graphDB.beginTx()) {
            Node serviceNode = graphDB.createNode();
            serviceNode.addLabel(LabelTypes.SERVICE);
            serviceNode.setProperty("id", uuid);
            serviceNode.setProperty("host", microservice.getHost());

            for (Endpoint endpoint : microservice.getEndpoints()) {
                Node endpointNode = graphDB.createNode();
                endpointNode.addLabel(LabelTypes.ENDPOINT);
                endpointNode.setProperty("path", endpoint.getPath());
                serviceNode.createRelationshipTo(serviceNode, RelationshipTypes.HAS);
            }
            tx.success();
        }
        return uuid;
    }

    @Override
    public List<Microservice> findMicroservicesByPathTerm(String term) {
        GraphDatabaseService graphDB = connector.getGraphDB();

        HashMap<String, Object> params = new HashMap<>();
        params.put("term", term);

        List<Microservice> results = new ArrayList<>();
        try (Transaction ignored = graphDB.beginTx();
             Result result = graphDB.execute(Queries.ALL_MICROSERVICES_WHICH_CONTAINS_TERM, params)) {
            result.forEachRemaining(line -> {
                Microservice microservice = new Microservice((String) line.get("s.host"));
                microservice.has(new Endpoint((String) line.get("e.path")));
                results.add(microservice);
            });
        }
        return results;
    }

}
