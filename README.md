

### About
---
Vacuum is a REST API which allows you to understand the dependency graph of your microservices architecture.

It is based on [Swagger.io](http://swagger.io/), you can just submit the URL of your swagger documentation
and Vacuum will parse it and create the Service-Endpoint graph as you can see in the image bellow.

![Service-Endpoint-Graph](graph-microservice-dependencies.png)


The microservice/service host property, captured from Swagger document, represents the main node labeled as `Service` in which
all paths declared in the swagger spec, are extracted and created as nodes labeled as `Endpoint`.
So, basically, the graph is composed by hosts and paths, where hosts represent the service name
and paths the endpoints exposed on that service.

There is only one relationship between `Service` and `Endpoint` which is identified by the label `EXPOSES`.

Once you have created the Service-Endpoint graph you can adjust your microservices to send a request
to Vacuum API with the details about the call that your service A is sending to any other service.

After that, you can use Vacum API to obtain details about the dependancy of your services.

The relatioship between services are created everytime the API receives a request event with these details:
- origin_host: The service A host name which is firing the call to service B
- method: The HTTP method
- target_host: The service B host name
- target_path: The endpoint that service A is calling from service B

### Requirements:
---

- [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Neo4j Community 3.0.1](http://neo4j.com/download/)


### Basic Usage 
---

#### Creating a Service-Endpoint graph based on a Swagger URL
```
POST <host>:8090/v1/microservices -H 'Content-Type: application/json' -d '{"swagger_url": "<the_swagger_url>"}'
```

#### Registering a call from service A to service B
```
POST <host>:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "api.uber.com", "method": "POST", "target_host": "petstore.swagger.io", "target_path": "/v2/user/login"}'
```

#### Registering another call from service A to service B
```
POST <host>:8090/v1/requests -H 'Content-Type: application/json' -d '{"origin_host": "api.uber.com", "method": "DELETE", "target_host": "petstore.swagger.io", "target_path": "/v2/pet"}'
```

### Sample Data
---
Under `scripts` folder you will find the script `fetch_db.sh`, it will provide some sample data to populate Neo4J.
It creates two graphs Service-Endpoint and add relatioships (`GET`,`POST`,`DELETE`,`CALL`) for `Service` nodes which represents call events between them.


### Basic Queries
---



### TODO
---
- New types of queries
- Authentication (API, Neo4J)
- Unit & IT Tests
