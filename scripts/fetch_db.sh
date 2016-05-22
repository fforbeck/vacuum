#!/usr/bin/env bash

# Creates the graph for uber api
curl -XPOST localhost:8090/v1/microservices -H 'Content-Type: application/json' -d '{"swagger_url": "https://raw.githubusercontent.com/swagger-api/swagger-parser/master/modules/swagger-parser/src/test/resources/uber.json"}'

# Creates the graph for petstore api
curl -XPOST localhost:8090/v1/microservices -H 'Content-Type: application/json' -d '{"swagger_url": "http://petstore.swagger.io/v2/swagger.json"}'

# Uber GET Petstore login
curl -XPOST localhost:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "api.uber.com", "method": "POST", "target_host": "petstore.swagger.io", "target_path": "/v2/user/login"}'


4. uber delete pet from petstore
curl -XPOST localhost:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "api.uber.com", "method": "DELETE", "target_host": "petstore.swagger.io", "target_path": "/v2/pet"}'

5.u petstore get uber history
curl -XPOST localhost:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "petstore.swagger.io", "method": "GET", "target_host": "api.uber.com", "target_path": "/v1/history"}'

6.u petstore get uber products
curl -XPOST localhost:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "petstore.swagger.io", "method": "GET", "target_host": "api.uber.com", "target_path": "/v1/products"}'