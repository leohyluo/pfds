package com.iebm.pfds.user.service;

import com.iebm.pfds.commons.core.service.BaseService;
import com.iebm.pfds.commons.pojo.User;

public interface TableUserService extends BaseService<User, Long> {

	void add(User user);
}
