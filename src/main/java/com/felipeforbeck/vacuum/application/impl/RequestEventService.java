package com.felipeforbeck.vacuum.application.impl;

import com.felipeforbeck.vacuum.application.RequestEventServiceInterface;
import com.felipeforbeck.vacuum.domain.shared.RequestEventMetaData;
import com.felipeforbeck.vacuum.domain.model.RequestEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fforbeck on 21/05/16.
 */
@Service
public class RequestEventService implements RequestEventServiceInterface {

    @Autowired
    private RequestEventRepository repository;

    @Override
    public String saveRequest(RequestEventMetaData requestEventMetaData) {
        return repository.saveRequest(requestEventMetaData);
    }
}
