package com.iebm.pfds.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iebm.pfds.commons.core.repository.BaseRepository;
import com.iebm.pfds.commons.core.service.AbstractService;
import com.iebm.pfds.commons.pojo.UserInfo;
import com.iebm.pfds.user.repository.UserRepository;
import com.iebm.pfds.user.service.UserService;

@Service
public class UserServiceImpl extends AbstractService<UserInfo, Long> implements UserService {

	@Resource
	private UserRepository repository;
	
	@Override
	public List<UserInfo> getByUserId(Long userId) {
		List<UserInfo> userList = repository.findByUserId(userId);
		return userList;
	}

	@Override
	protected BaseRepository<UserInfo, Long> getRepository() {
		return repository;
	}

}
