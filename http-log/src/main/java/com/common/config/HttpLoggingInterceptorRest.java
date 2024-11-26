package com.common.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class HttpLoggingInterceptorRest implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptorRest.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {
        logger.info("HTTP请求URL: {}", request.getURI());
        logger.info("HTTP请求方法: {}", request.getMethod());
        logger.info("HTTP请求头: {}", request.getHeaders());
        if (body != null) {
            logger.info("HTTP请求入参: {}", new String(body, StandardCharsets.UTF_8));
        }
        ClientHttpResponse response = execution.execute(request, body);
        byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());
        logger.info("HTTP请求返回值: {}", new String(responseBody, StandardCharsets.UTF_8));
        return response;
    }
}