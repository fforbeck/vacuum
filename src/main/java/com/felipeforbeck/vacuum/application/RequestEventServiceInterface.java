package com.felipeforbeck.vacuum.application;

import com.felipeforbeck.vacuum.domain.shared.RequestEventMetaData;

/**
 * Created by fforbeck on 22/05/16.
 */
public interface RequestEventServiceInterface {

    /**
     * Registers a new request event in the system based on the provided data.
     *
     * @param requestEventMetaData        swagger documentation url
     * @return Request event id
     */
    String saveRequest(RequestEventMetaData requestEventMetaData);

}
