/*
package com.iebm.pfds.commons.security;

import com.spsoft.common.redis.FwRedisConfig;
import com.spsoft.common.security.token.FwRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * 资源服务配置类
 * 
 * @author LiNing
 *
 *//*

@EnableResourceServer
@FwRedisConfig
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FwResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Autowired
	JedisConnectionFactory jedisConnectionFactory;

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	@Bean
	public FwRedisTokenStore tokenStore() {
		return new FwRedisTokenStore(jedisConnectionFactory);
	}

	@Bean
	public OAuth2AccessDeniedHandler oauth2AccessDeniedHandler() {
		OAuth2AccessDeniedHandler oauth2AccessDeniedHandler = new OAuth2AccessDeniedHandler();
		oauth2AccessDeniedHandler.setExceptionRenderer(new HeaderOnlyOAuth2ExceptionRenderer());
		return oauth2AccessDeniedHandler;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId).tokenStore(tokenStore()).accessDeniedHandler(oauth2AccessDeniedHandler());
	}

	*/
/**
	 * URL匹配验证 isAuthenticated：是否授权通过 hasIpAddress：IP地址，根据网关计算
	 *//*

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().addFilterBefore(contextClearer(), AbstractPreAuthenticatedProcessingFilter.class)
				.authorizeRequests().antMatchers("/rest/**").access("isAuthenticated()");
		// http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy()).sessionCreationPolicy(SessionCreationPolicy.NEVER);
	}

	private OncePerRequestFilter contextClearer() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				if (tokenExtractor.extract(request) == null) {
					SecurityContextHolder.clearContext();
				}
				filterChain.doFilter(request, response);
			}
		};
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {return new RestTemplate();}

}*/
