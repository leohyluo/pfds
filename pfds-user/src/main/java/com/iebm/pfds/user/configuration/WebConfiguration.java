package com.iebm.pfds.user.configuration;

import com.iebm.pfds.commons.core.framework.CustomJSONReturnValueHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        CustomJSONReturnValueHandler handler = new CustomJSONReturnValueHandler();
        returnValueHandlers.add(handler);
    }
}
