package com.felipeforbeck.vacuum.interfaces.web;

import com.felipeforbeck.vacuum.domain.model.Microservice;
import com.felipeforbeck.vacuum.domain.shared.MicroserviceMetaData;
import com.felipeforbeck.vacuum.application.impl.MicroserviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private MicroserviceService service;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> post(@RequestBody MicroserviceMetaData microserviceMetaData) {
        try {
            service.newMicroservice(microserviceMetaData.getSwagger_url());
            return new ResponseEntity<>("Created", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Microservice>> get(@RequestParam(name = "path_term") String pathTerm) {
        try {
            List<Microservice> microservice = service.findMicroservicesByPath(pathTerm);
            return new ResponseEntity<>(microservice, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

