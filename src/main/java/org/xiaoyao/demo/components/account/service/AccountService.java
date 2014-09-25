package org.xiaoyao.demo.components.account.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.xiaoyao.demo.components.account.model.User;
import org.xiaoyao.demo.components.account.repository.UserDao;
import org.xiaoyao.demo.components.account.service.ShiroDbRealm.ShiroUser;
import org.xiaoyao.demo.components.common.model.Sequence;
import org.xiaoyao.demo.components.common.repository.SequenceDao;
import org.xiaoyao.framework.utils.Clock;
import org.xiaoyao.framework.utils.Digests;
import org.xiaoyao.framework.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author jason
 */
// Spring Service Bean的标识.
@Component
public class AccountService {

	private static ApplicationContext applicationContext;

	static {
		try {
			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao = (UserDao) applicationContext.getBean("userDao");

	protected SequenceDao sequenceDao = (SequenceDao) applicationContext.getBean("sequenceDao");

	// private TaskDao taskDao;
	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(String id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public void registerUser(User user) {
		entryptPassword(user);
		user.setRoles("user");

		Date date = new Date();
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String str = formatter.format(date);
		user.setRegisterDate(str);

//		if (user.getId() == null) {
//			Long id = getNextSequenceId(user.getSquenceKey(), user.getSquenceStart());
//			user.setId(id.toString());
//		}

		userDao.save(user);
	}

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(String id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new RuntimeException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		// taskDao.deleteByUserId(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(String id) {
		return "1".equals(id);
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	private Long getNextSequenceId(String id, Long start) {

		Sequence sequence = sequenceDao.findOne(id);
		if (sequence == null) {
			sequence = new Sequence();
			sequence.setId(id);
			sequence.setValue(start);
		} else {
			sequence.setValue(sequence.getValue() + 1);
		}
		sequenceDao.save(sequence);
		return sequence.getValue();

	}
}
