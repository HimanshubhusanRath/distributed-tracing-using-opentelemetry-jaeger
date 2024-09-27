package com.hr.opentelemetry.controller;

import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Enumeration;

@RestController
@RequestMapping("/service")
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class.getName());
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Tracer tracer;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/path1")
    public ResponseEntity path1() {
        LOG.info("Incoming request at {} for request /path1 ", applicationName);
        tracer.currentSpan().tag("user-id","himanshubhusan.rath");
        String traceId = MDC.get("traceId"); // Get the trace ID
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-TRACE-ID", Collections.singletonList(traceId));
        String response = restTemplate.getForObject("http://localhost:8082/service/path2", String.class);
        return new ResponseEntity<>("response from /path1 + " + response, headers, HttpStatus.OK);
    }

    @GetMapping("/path2")
    public ResponseEntity path2(final HttpServletRequest request) {
        printHeaders(request);
        tracer.currentSpan().tag("user-id","himanshubhusan.rath");
        LOG.info("Incoming request at {} at /path2", applicationName);
        return ResponseEntity.ok("response from /path2");
    }

    private void printHeaders(final HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Header Type " + headerNames.getClass());
        String headerName = null;
        while(headerNames.hasMoreElements()){
            headerName = headerNames.nextElement();
            System.out.println(headerName+" : "+request.getHeader(headerName));
        }
    }
}
