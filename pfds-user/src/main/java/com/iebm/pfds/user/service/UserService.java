package com.iebm.pfds.user.service;

import java.util.List;

import com.iebm.pfds.commons.pojo.UserInfo;

public interface UserService {

	List<UserInfo> getByUserId(Long userId);
}
