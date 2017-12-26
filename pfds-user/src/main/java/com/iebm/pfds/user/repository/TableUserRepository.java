package com.iebm.pfds.user.repository;

import org.springframework.stereotype.Repository;

import com.iebm.pfds.commons.core.repository.BaseRepository;
import com.iebm.pfds.commons.pojo.User;

@Repository
public interface TableUserRepository extends BaseRepository<User, Long> {

}
