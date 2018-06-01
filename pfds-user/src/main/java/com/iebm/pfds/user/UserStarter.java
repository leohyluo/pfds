package com.iebm.pfds.user;

import com.iebm.pfds.commons.core.annotation.SpringCloudApplication;
import com.iebm.pfds.commons.utils.CloudConfigSupportConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.iebm.pfds.**"})
@EntityScan(basePackages = {"com.iebm.pfds.commons.pojo"})
@ServletComponentScan(basePackages={"com.iebm.pfds.commons.config"})
@EnableFeignClients(basePackages = {"com.iebm.pfds.service.rpc.user"})
public class UserStarter {
    public static void main(String[] args) {
        //SpringApplication.run(UserStarter.class, args);
        new SpringApplicationBuilder(UserStarter.class).initializers(new CloudConfigSupportConfiguration()).web(true).run(args);
    }
}
