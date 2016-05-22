package com.felipeforbeck.vacuum.domain.model;

import com.felipeforbeck.vacuum.domain.shared.RequestEventMetaData;

/**
 * Created by fforbeck on 21/05/16.
 */
public interface RequestEventRepository {

    String saveRequest(RequestEventMetaData requestEventMetaData);

}
