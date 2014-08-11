package com.test.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.test.domain.User;
import com.test.storage.interfaces.UserDao;

public class UserDaoImpl extends ShardedJdbcDaoSupport implements UserDao {

	@Override
	public void insertUser(User user) {
		this.getJdbcTemplate().update(
				"INSERT INTO USER (ID, USERNAME, NAME) VALUES (?, ?, ?)",
				new Object[] {
						user.getId(),
						user.getUsername(),
						user.getName()
				}
				);
	}

	@Override
	public User getUser(String username) {
		User user = this.getJdbcTemplate().
				queryForObject(
						"SELECT * FROM USER WHERE USERNAME = ?",
						new Object[] { username },
						new UserMapper()
						);
		return user;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = this.getJdbcTemplate().
				query("SELECT * FROM USER",
						new UserMapper()
						);
		return users;
	}

	private class UserMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			User user = new User();
			user.setId(rs.getInt("ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setName(rs.getString("NAME"));
			return user;
		}

	}

}
