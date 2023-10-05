# Distributed Tracing with OpenTelemetry and Jaeger
## 1. Concepts:
* OpenTelemetry:
  * OpenTelemetry (OTel) is an open-source framework for collecting and analyzing observability data, like traces and metrics, from distributed systems. It provides APIs and libraries for developers to instrument their applications, gain insights into system performance, and integrate with various observability tools.
* Jaeger:
  * Jaeger is an open-source, end-to-end distributed tracing system that is used to monitor and troubleshoot the performance of distributed systems and microservices-based applications.

<TODO: Jaeger Architecture>

## 2. Setup:
*  Dependency:
```
  <!-- We will be using the micrometer collector in the actuator dependency to collect the traces and then use the OpenTelemetry dependency to export the traces. -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
  </dependency>
  <dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
  </dependency>
```
* Configure `OtlpHttpSpanExporter` to export the Spans to Jaeger.
* Start the Jaeger instance : 
    ``` 
    cd /jaeger
    docker-compose up
    ```
* Services:
  * There are two endpoints in this microservice. One is `/service/path1` and another is `/service/path2`.
  * Build the project: `mvn clean install`
  * Create two instances of this microservice with name as `service-1` and `service-2` running on port `8081` and `8082` respectively.
    * `/service/path1` in the first instance calls the `/service/path2` endpoint of the second instance. This is how we are simulating the inter-service communication here.
  ```
    java -jar \
    target/distributed-tracing-using-opentelemetry-0.0.1-SNAPSHOT.jar \
    --spring.application.name=Service-1 \
    --server.port=8081
  
    java -jar \
    target/distributed-tracing-using-opentelemetry-0.0.1-SNAPSHOT.jar \
    --spring.application.name=Service-2 \
    --server.port=8082
  ```
  * Now try to access the below endpoint in service-1:
    * `http://localhost:8081/service/path1`
* Check the tracing:
  * Open Jaeger UI at `http://localhost:16686/`
  * Select the `Service-1` from the `Services` list and then the individual call traces can be viewed.
  * When we click on a trace, it expends to highlight the spans which gives us the detail picture of the service interactions.
  <TODO: Jaeger Result>
  <TODO: Jaeger restul details>
  

