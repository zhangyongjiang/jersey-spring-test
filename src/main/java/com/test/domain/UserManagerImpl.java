package com.test.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.domain.interfaces.UserManager;
import com.test.storage.interfaces.UserDao;

@Service
@Transactional
public class UserManagerImpl implements UserManager {

	@Autowired
	private UserDao userDAO;

	@Override
	public void insertUser(User user) {
		this.userDAO.insertUser(user);
	}

	@Override
	public void insertUserAndFailTransation(User user) {
		this.userDAO.insertUser(user);
		this.userDAO.insertUser(user);
	}

	@Override
	public User getUser(String username) {
		return this.userDAO.getUser(username);
	}

	@Override
	public List<User> getUsers() {
		return this.userDAO.getUsers();
	}
}
