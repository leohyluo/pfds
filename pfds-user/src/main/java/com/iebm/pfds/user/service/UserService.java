package com.iebm.pfds.user.service;

import java.util.List;

import com.iebm.pfds.commons.core.service.BaseService;
import com.iebm.pfds.commons.pojo.UserInfo;

public interface UserService extends BaseService<UserInfo, Long> {

	List<UserInfo> getByUserId(Long userId);
	
	void testSave();
}
