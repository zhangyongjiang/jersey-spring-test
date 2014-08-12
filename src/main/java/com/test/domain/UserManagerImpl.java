package com.test.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.domain.interfaces.UserManager;
import com.test.storage.TransactionAwareDataSource;
import com.test.storage.UserDaoImpl;
import com.test.storage.interfaces.UserDao;

@Service
public class UserManagerImpl extends ServiceBase implements UserManager {
	@Autowired TransactionAwareDataSource dataSource;

	
	private UserDao getUserDAO() {
		return getDao(UserDaoImpl.class);
	}

	@Override
	public void insertUser(User user) {
		dataSource.beginTransaction();
		this.getUserDAO().insertUser(user);
		try {
			dataSource.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insertUserAndFailTransation(final User user) {
		dataSource.beginTransaction();
		final List<Integer> list = new ArrayList<Integer>();
		this.getUserDAO().insertUser(user);
		TransactionAwareDataSource.DbThread thread = new TransactionAwareDataSource.DbThread(new Runnable() {
			@Override
			public void run() {
				try {
					UserManagerImpl.this.getUserDAO().insertUser(user);
				} catch (Exception e) {
					list.add(1);
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			if(list.size()==0)
				dataSource.commit();
			else
				dataSource.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
