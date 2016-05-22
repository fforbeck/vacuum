package com.felipeforbeck.vacuum.application.util;

import com.felipeforbeck.vacuum.domain.model.Endpoint;
import com.felipeforbeck.vacuum.domain.model.Microservice;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by fforbeck on 21/05/16.
 */
@Component
@Scope("prototype")
public class SwaggerToMicroserviceConverter {

    public Microservice convert(Swagger swagger) {
        Microservice microservice = new Microservice(swagger.getHost());

        Map<String, Path> paths = swagger.getPaths();
        paths.forEach((pathName, path) -> microservice.has(new Endpoint(swagger.getBasePath() + pathName)));

        return microservice;
    }
}
