package com.iebm.pfds.service.rpc.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class DrugFeignFallbackFactory implements FallbackFactory<DrugFeign> {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public DrugFeign create(Throwable e) {
		logger.warn("error on invoke pfds-drug cause by " + e.getMessage());
		return new DrugFeign() {
			@Override
			public String get(String id) {
				return "-1";
			}
		};
	}

}
