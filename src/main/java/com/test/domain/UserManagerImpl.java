package com.test.domain;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.domain.interfaces.UserManager;
import com.test.storage.UserDaoImpl;
import com.test.storage.interfaces.UserDao;

@Service
@Transactional
public class UserManagerImpl extends ServiceBase implements UserManager {

	private UserDao getUserDAO() {
		DataSource dataSource = appContext.getBean(DataSource.class);
		UserDaoImpl dao = new UserDaoImpl();
		dao.setDataSource(dataSource );
		return dao;
	}

	@Override
	public void insertUser(User user) {
		this.getUserDAO().insertUser(user);
	}

	@Override
	public void insertUserAndFailTransation(User user) {
		this.getUserDAO().insertUser(user);
		this.getUserDAO().insertUser(user);
	}

	@Override
	public User getUser(String username) {
		return this.getUserDAO().getUser(username);
	}

	@Override
	public List<User> getUsers() {
		return this.getUserDAO().getUsers();
	}

}
