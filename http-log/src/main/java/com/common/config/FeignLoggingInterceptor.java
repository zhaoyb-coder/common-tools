package com.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignLoggingInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignLoggingInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        // 记录请求的URL
        logger.info("Feign接口调用 - URL: {}", template.url());
        // 记录请求头信息
        Map<String, String> headers = new HashMap<>();
        Set<String> strings = template.headers().keySet();
        logger.info("Feign接口调用 - 头信息: {}", strings);
        // 记录请求体信息（如果有的话，这里简单示例，实际可能需要根据请求类型更准确处理）
        if (template.requestBody() != null) {
            byte[] bytes = template.requestBody().asBytes();
            // 使用ObjectMapper将字节数组转换为JSON字符串
            String jsonString = new String(bytes);
            logger.info("Feign接口调用 - 请求体: {}", jsonString);
        }
    }
}
