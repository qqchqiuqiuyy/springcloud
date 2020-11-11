package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
	@Value("${server.port}")
	private String serverPort;

	@Resource
	private PaymentService paymentService;

	@Resource
	private DiscoveryClient discoveryClient;

	@PostMapping(value = "/payment/create")
	public CommonResult create(@RequestBody Payment payment) {
		int result = paymentService.create(payment);
		log.info("******插入结果: " + result);
		if (result > 0) {
			return new CommonResult(200, "插入数据库成功 port:" + serverPort, result);
		}
		return new CommonResult(444, "插入数据库失败 port:" + serverPort, result);
	}

	@GetMapping(value = "/payment/get/{id}")
	public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
	{
		Payment payment = paymentService.getPaymentById(id);

		if(payment != null)
		{
			return new CommonResult(200,"查询成功, port:" + serverPort,payment);
		}else{
			return new CommonResult(444,"没有对应记录,查询ID: "+id + "port:" + serverPort,null);
		}
	}

	@GetMapping(value = "/payment/discovery")
	public Object discovery()
	{
		List<String> services = discoveryClient.getServices();
		for (String element : services) {
			log.info("*****element: "+element);
		}

		// 轮询算法是根据这里面的集合实例进行操作
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
		for (ServiceInstance instance : instances) {
			log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
		}

		return this.discoveryClient;
	}

	@GetMapping(value = "/payment/lb")
	public String getPaymentLB()
	{
		return serverPort;
	}

	@GetMapping(value = "/payment/feign/timeout")
	public String paymentFeignTimeout()
	{
		// 业务逻辑处理正确，但是需要耗费3秒钟
		try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
		return serverPort;
	}
}
