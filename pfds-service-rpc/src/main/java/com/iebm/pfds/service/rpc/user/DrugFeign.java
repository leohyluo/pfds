package com.iebm.pfds.service.rpc.user;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.iebm.pfds.service.rpc.configuration.FeignConfiguration;

@FeignClient(name = "pfds-drug", configuration = FeignConfiguration.class, fallbackFactory = DrugFeignFallbackFactory.class)
public interface DrugFeign {

	@RequestMapping(value = "/drug/get", method = RequestMethod.POST)
	public String get(@RequestParam("id") String id);
}
