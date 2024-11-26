package com.common.config;

import cn.hutool.http.*;

public class HttpInterceptorConfig {

    static Double id;

    public void init() {
        // 获取全局的HTTP配置
        GlobalInterceptor.INSTANCE.addRequestInterceptor(httpObj -> this.wirteRequestLog(httpObj));
        // 设置hutool HttpUtil的response参数打印
        GlobalInterceptor.INSTANCE.addResponseInterceptor(httpObj -> this.writeResponseLog(httpObj));
        // 设置hutool HttpUtil的连接超时时间、读取响应超时时间
        HttpGlobalConfig.setTimeout(3000);
    }

    private static Double wirteRequestLog(HttpRequest request) {
        System.out.println(request);
        id = Math.random();
        System.out.println("request随机数" + id);
        return id;
    }

    private static void writeResponseLog(HttpResponse response) {
        System.out.println(response);
        System.out.println("response随机数" + id);
    }

    public static void main(String[] args) {
        // 获取全局的HTTP配置
        GlobalInterceptor.INSTANCE.addRequestInterceptor(httpObj -> wirteRequestLog(httpObj));
        // 设置hutool HttpUtil的response参数打印
        GlobalInterceptor.INSTANCE.addResponseInterceptor(httpObj -> writeResponseLog(httpObj));
        String s = HttpUtil.get("https://www.baidu.com");
    }
}