package com.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.http.converter.HttpMessageConverter;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

public class FeignLoggingDecoder implements Decoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignLoggingDecoder.class);

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        ObjectFactory<HttpMessageConverters> messageConverters =
            () -> new HttpMessageConverters(new HttpMessageConverter[0]);
        Decoder decoder = new ResponseEntityDecoder(new SpringDecoder(messageConverters));
        // 在解码前记录响应内容
        int status = response.status();
        StringBuilder stringBuilder = new StringBuilder();
        logger.info("Feign接口响应 - 响应状态码: {}", status);
        logger.info("Feign接口响应 - 响应头信息: {}", response.headers());
        // 将response.body()读取为字节数组
        InputStream inputStream = response.body().asInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        // 使用ObjectMapper将字节数组转换为JSON字符串
        String jsonString = new String(stringBuilder);
        logger.info("Feign接口响应 - 响应体内容: {}", jsonString);
        return decoder.decode(response, type);
    }
}
