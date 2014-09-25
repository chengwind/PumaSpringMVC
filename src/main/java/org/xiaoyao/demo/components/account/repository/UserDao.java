package org.xiaoyao.demo.components.account.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.xiaoyao.demo.components.account.model.User;

public interface UserDao extends MongoRepository<User, String> {
	User findByLoginName(String loginName);
}
