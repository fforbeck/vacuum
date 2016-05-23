package com.felipeforbeck.vacuum.interfaces.web;

import com.felipeforbeck.vacuum.application.MicroserviceServiceInterface;
import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.domain.shared.MicroserviceMetaData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by fforbeck on 21/05/16.
 */
@RestController
@RequestMapping("/microservices")
public class MicroserviceController {

    private static final Logger LOG = Logger.getLogger(MicroserviceController.class);

    @Autowired
    private MicroserviceServiceInterface service;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveMicroservice(@RequestBody MicroserviceMetaData microserviceMetaData) {
        try {
            String id = service.saveMicroservice(microserviceMetaData.getSwagger_url());
            return new ResponseEntity<>("{'id': '"+id+"'}", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Microservice>> findMicroservicesByPath(@RequestParam(name = "path_term") String pathTerm) {
        try {
            List<Microservice> microservice = service.findMicroservicesByPath(pathTerm);
            return new ResponseEntity<>(microservice, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{microserviceId}/dependants", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Microservice>> findDependants(@PathVariable String microserviceId) {
        try {
            List<Microservice> microservice = service.findDependants(microserviceId);
            return new ResponseEntity<>(microservice, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO count total calls from host A to host B based on http_method(optional: default = any type of call)

    //TODO find all endpoints called from A to B sort by DESC|ASC param

    //TODO find all service names in use by service X (id)

    //TODO find all calls using http method X from host A to host B

    //TODO get last call timestamp based on http_method from service A to Service B


}

