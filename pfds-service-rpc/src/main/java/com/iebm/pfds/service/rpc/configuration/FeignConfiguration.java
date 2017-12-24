package com.iebm.pfds.service.rpc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

@Configuration
public class FeignConfiguration {

	/**
	 * 重写FeignClientsConfiguration配置
	 * @return
	 */
	@Bean
	public Retryer feignRetryer() {
		System.out.println("override feign configuration");
		//更改了该FeignClient的重试次数，重试间隔为100ms，最大重试时间为1s,重试次数为2次
		return new Retryer.Default(100, 1000, 2);
	}
}
