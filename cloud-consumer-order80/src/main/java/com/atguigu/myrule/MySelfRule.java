package com.atguigu.myrule;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 负载均衡类, 不能在@ComponentScan下面
 */
@Configuration
public class MySelfRule {

	@Bean
	public IRule myRule(){
		return new RandomRule();//定义为随机
	}
}
