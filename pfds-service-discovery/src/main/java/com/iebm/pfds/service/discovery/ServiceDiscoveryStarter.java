package com.iebm.pfds.service.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceDiscoveryStarter {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryStarter.class, args);
        System.out.println("启动discovery ServerDiscoveryStarter 成功！");
        System.out.println("--------------------------------");
    }
}
