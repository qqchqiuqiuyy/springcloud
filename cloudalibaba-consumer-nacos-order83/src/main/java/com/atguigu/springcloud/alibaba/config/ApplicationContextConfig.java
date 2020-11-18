package com.atguigu.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @auther zzyy
 * @create 2020-02-23 14:45
 */
@Configuration
public class ApplicationContextConfig
{
    @Bean
    @LoadBalanced  //进行负载均衡
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
