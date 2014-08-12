package com.test.storage;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ShardedDataSource implements DataSource {
	private ThreadLocal<Map<String, Connection>> transConns;
	private DataSource wrapped;
	
	public ShardedDataSource(DataSource ds) {
		wrapped = ds;
		transConns = new ThreadLocal<Map<String,Connection>>();
		transConns.set(new HashMap<String, Connection>());
	}
	
	public Connection getConnection() throws SQLException {
		return null;
	}

	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this);
		return template;
	}
	
	
	public <T> T execute(String sql, SqlParameterSource paramSource, PreparedStatementCallback<T> action)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().execute(sql, paramSource, action);
	}

	public <T> T execute(String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> action)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().execute(sql, paramMap, action);
	}

	public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
		return getNamedParameterJdbcTemplate().execute(sql, action);
	}

	public <T> T query(String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql, paramSource, rse);
	}

	public <T> T query(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql, paramMap, rse);
	}

	public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql,  rse);
	}

	public void query(String sql, SqlParameterSource paramSource, RowCallbackHandler rch)
			throws DataAccessException {
		getNamedParameterJdbcTemplate().query(sql, paramSource, rch);
	}

	public void query(String sql, Map<String, ?> paramMap, RowCallbackHandler rch)
			throws DataAccessException {
		getNamedParameterJdbcTemplate().query(sql, paramMap, rch);
	}

	public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
		getNamedParameterJdbcTemplate().query(sql, rch);
	}

	public <T> List<T> query(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql, paramSource, rowMapper);
	}

	public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return getNamedParameterJdbcTemplate().query(sql, rowMapper);
	}

	public <T> T queryForObject(String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource, rowMapper);
	}

	public <T> T queryForObject(String sql, Map<String, ?> paramMap, RowMapper<T>rowMapper)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, rowMapper);
	}

	public <T> T queryForObject(String sql, SqlParameterSource paramSource, Class<T> requiredType)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramSource, requiredType);
	}

	public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, requiredType);
	}

	public Map<String, Object> queryForMap(String sql, SqlParameterSource paramSource) throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForMap(sql, paramSource);
	}

	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
	}

	public <T> List<T> queryForList(String sql, SqlParameterSource paramSource, Class<T> elementType)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForList(sql, paramSource, elementType);
	}

	public <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> elementType)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForList(sql, paramMap, elementType);
	}

	public List<Map<String, Object>> queryForList(String sql, SqlParameterSource paramSource)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForList(sql, paramSource);
	}

	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	public SqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource) throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForRowSet(sql, paramSource);
	}

	public SqlRowSet queryForRowSet(String sql, Map<String, ?> paramMap) throws DataAccessException {
		return getNamedParameterJdbcTemplate().queryForRowSet(sql, paramMap);
	}

	public int update(String sql, SqlParameterSource paramSource) throws DataAccessException {
		return getNamedParameterJdbcTemplate().update(sql, paramSource);
	}

	public int update(String sql, Map<String, ?> paramMap) throws DataAccessException {
		return getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	public int update(String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().update(sql, paramSource, generatedKeyHolder);
	}

	public int update(
			String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder, String[] keyColumnNames)
			throws DataAccessException {
		return getNamedParameterJdbcTemplate().update(sql, paramSource, generatedKeyHolder, keyColumnNames);
	}

	public int[] batchUpdate(String sql, Map<String, ?>[] batchValues) {
		return getNamedParameterJdbcTemplate().batchUpdate(sql, batchValues);
	}

	public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs) {
		return getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return wrapped.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		wrapped.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		wrapped.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return wrapped.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return wrapped.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return wrapped.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return wrapped.isWrapperFor(iface);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		throw new RuntimeException("unimplemented");
	}
	
}
