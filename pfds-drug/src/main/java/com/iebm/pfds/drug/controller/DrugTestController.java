package com.iebm.pfds.drug.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drug")
public class DrugTestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostMapping("/get")
	public String get(String id) {
		logger.info("get drug from pfds-drug by id " + id);
		return "get drug from pfds-drug";
	}
}
