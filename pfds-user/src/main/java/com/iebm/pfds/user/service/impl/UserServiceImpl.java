package com.iebm.pfds.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iebm.pfds.commons.core.repository.BaseRepository;
import com.iebm.pfds.commons.core.service.AbstractService;
import com.iebm.pfds.commons.pojo.User;
import com.iebm.pfds.commons.pojo.UserInfo;
import com.iebm.pfds.user.repository.UserRepository;
import com.iebm.pfds.user.service.TableUserService;
import com.iebm.pfds.user.service.UserService;

@Service
public class UserServiceImpl extends AbstractService<UserInfo, Long> implements UserService {

	@Resource
	private UserRepository repository;
	@Resource
	private TableUserService tableUserService;
	
	@Override
	public List<UserInfo> getByUserId(Long userId) {
		List<UserInfo> userList = repository.findByUserId(userId);
		return userList;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testSave() {
		UserInfo userInfo = get(1L);
		userInfo.setUserName("测试1226");
		save(userInfo);
		
		User user = tableUserService.get(3L);
		user.setUserName("测试1226-2");
		tableUserService.add(user);
		
		int a = 1/0;
		System.out.println("==============================");
	}

	@Override
	protected BaseRepository<UserInfo, Long> getRepository() {
		return repository;
	}
}
