package com.iebm.pfds.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GatewayStarter {
	public static void main(String[] args) {
		SpringApplication.run(GatewayStarter.class, args);
	}
}
