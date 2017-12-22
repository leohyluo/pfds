package com.iebm.pfds.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iebm.pfds.commons.core.repository.BaseRepository;
import com.iebm.pfds.commons.pojo.UserInfo;

@Repository
public interface UserRepository extends BaseRepository<UserInfo, Long> {

	List<UserInfo> findByUserId(Long userId);
}
