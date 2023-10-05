package com.hr.opentelemetry.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OLTPConfiguration {

    /**
     * This OtlpHttpSpanExporter exports the traces to the tracing backend server (Jaeger). The mentioned url is Jaeger's url
     *
     * @param url
     * @return
     */
    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") final String url)
    {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build();
    }

}
