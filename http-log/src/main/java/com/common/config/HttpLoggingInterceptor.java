package com.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.http.HttpBase;
import cn.hutool.http.HttpInterceptor;

public class HttpLoggingInterceptor implements HttpInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);

    @Override
    public void process(HttpBase httpBase) {
        // 记录请求的URL
        httpBase.bodyBytes();

    }
}
