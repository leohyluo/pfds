package com.iebm.pfds.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

import com.iebm.pfds.commons.core.annotation.SpringCloudApplication;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.iebm.pfds.**"})
@EntityScan(basePackages = {"com.iebm.pfds.commons.pojo"})
@ServletComponentScan(basePackages={"com.iebm.pfds.commons.config"})
@EnableFeignClients(basePackages = {"com.iebm.pfds.service.rpc.user"})
@EnableHystrixDashboard
public class UserStarter {
    public static void main(String[] args) {
        SpringApplication.run(UserStarter.class, args);
    }
}
