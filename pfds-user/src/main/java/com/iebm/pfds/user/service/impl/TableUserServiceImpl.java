package com.iebm.pfds.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iebm.pfds.commons.core.repository.BaseRepository;
import com.iebm.pfds.commons.core.service.AbstractService;
import com.iebm.pfds.commons.pojo.User;
import com.iebm.pfds.user.repository.TableUserRepository;
import com.iebm.pfds.user.service.TableUserService;

@Service
public class TableUserServiceImpl extends AbstractService<User, Long> implements TableUserService {
	
	@Resource
	private TableUserRepository repository;

	@Override
	protected BaseRepository<User, Long> getRepository() {
		return repository;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void add(User user) {
		repository.save(user);
	}

}
