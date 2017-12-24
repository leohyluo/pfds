package com.iebm.pfds.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iebm.pfds.commons.core.web.ResponseMessage;
import com.iebm.pfds.commons.core.web.WebUtils;
import com.iebm.pfds.commons.pojo.UserInfo;
import com.iebm.pfds.service.rpc.user.DrugFeign;
import com.iebm.pfds.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private DrugFeign drugFeign;

	@GetMapping("/{userId}")
	public ResponseMessage getByUserId(@PathVariable Long userId) {
		List<UserInfo> userList = userService.getByUserId(userId);
		return WebUtils.buildSuccessResponseMessage(userList);
	}
	
	@GetMapping("/drug/get")
	public ResponseMessage getDrug() {
		String result = drugFeign.get("100");
		return WebUtils.buildSuccessResponseMessage(result);
	}
}
