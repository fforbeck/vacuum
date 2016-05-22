package com.felipeforbeck.vacuum.interfaces.web;

import com.felipeforbeck.vacuum.application.RequestEventServiceInterface;
import com.felipeforbeck.vacuum.domain.shared.RequestEventMetaData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fforbeck on 21/05/16.
 */
@RestController
@RequestMapping("/requests")
public class RequestEventController {

    private static final Logger LOG = Logger.getLogger(RequestEventController.class);

    @Autowired
    private RequestEventServiceInterface service;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveRequestEvent(@RequestBody RequestEventMetaData requestEvent) {
        try {
            String id = service.saveRequest(requestEvent);
            return new ResponseEntity<>("{'id': '"+id+"'}", HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

