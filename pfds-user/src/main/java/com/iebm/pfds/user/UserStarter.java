package com.iebm.pfds.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import com.iebm.pfds.commons.core.annotation.SpringCloudApplication;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.iebm.pfds.**"})
@EntityScan(basePackages = {"com.iebm.pfds.commons.pojo"})
@ServletComponentScan(basePackages={"com.iebm.pfds.commons.config"})
public class UserStarter {
    public static void main(String[] args) {
        SpringApplication.run(UserStarter.class, args);
    }
}
