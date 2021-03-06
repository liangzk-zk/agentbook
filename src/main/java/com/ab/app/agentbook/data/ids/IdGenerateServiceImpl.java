package com.ab.app.agentbook.data.ids;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.jdbc.support.JdbcUtils;
/**
 * 使用数据库表实现的id生成器，注意该生成器应该用于非xa的dataSource
 * <br>
 *
 */
public class IdGenerateServiceImpl extends JdbcAccessor implements
		IdGenerateService {
	private String createIdQuery;
	private String loadIdQuery;
	private String updateIdQuery;
	private int maxRetry = 10;
	private IdDecorator idDecorator;

	/**
	 * 返回用于建立新的id记录的sql
	 * 
	 * @return
	 */
	public String getCreateIdQuery() {
		return createIdQuery;
	}

	/**
	 * 设置用于建立新的id记录的sql
	 * 
	 * @param createIdQuery
	 */
	public void setCreateIdQuery(String createIdQuery) {
		this.createIdQuery = createIdQuery;
	}

	/**
	 * 返回用于读取旧id值的sql
	 * 
	 * @return
	 */
	public String getLoadIdQuery() {
		return loadIdQuery;
	}

	/**
	 * 设置用于读取旧id值的sql
	 * 
	 * @param loadIdQuery
	 */
	public void setLoadIdQuery(String loadIdQuery) {
		this.loadIdQuery = loadIdQuery;
	}

	/**
	 * 返回用于更新id值的sql
	 * 
	 * @return
	 */
	public String getUpdateIdQuery() {
		return updateIdQuery;
	}

	/**
	 * 设置用于更新id值的sql
	 * 
	 * @param updateIdQuery
	 */
	public void setUpdateIdQuery(String updateIdQuery) {
		this.updateIdQuery = updateIdQuery;
	}

	/**
	 * 返回生成id失败时的最大重试次数，缺省为10，如果设置值小于1则无重试次数限制
	 * 
	 * @return
	 */
	public int getMaxRetry() {
		return maxRetry;
	}

	/**
	 * 设置生成id失败时的最大重试次数，缺省为10，如果设置值小于1则无重试次数限制
	 * 
	 * @param maxRetry
	 */
	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}
	
	public IdDecorator getIdDecorator() {
		return idDecorator;
	}
	
	public void setIdDecorator(IdDecorator idDecorator) {
		this.idDecorator = idDecorator;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (maxRetry == 0) {
			maxRetry = 10;
		}
		if (org.springframework.util.StringUtils.isEmpty(createIdQuery)) {
			createIdQuery = "INSERT INTO HD_ID_GEN (ID_NAME, ID_VAL) VALUES (?, ?)";
		}
		if (org.springframework.util.StringUtils.isEmpty(loadIdQuery)) {
			loadIdQuery = "SELECT ID_VAL FROM HD_ID_GEN WHERE ID_NAME = ?";
		}
		if (org.springframework.util.StringUtils.isEmpty(updateIdQuery)) {
			updateIdQuery = "UPDATE HD_ID_GEN SET ID_VAL = ? WHERE ID_NAME = ? AND ID_VAL = ?";
		}
	}

	@Override
	public long getNextLongId(String idName) {
		int count = 0;
		long oldId = 0;
		long retries = maxRetry;
		while (count == 0 && (maxRetry > 0 && retries > 0)) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			boolean success = false;
			try {
				conn = getDataSource().getConnection();
				conn.setAutoCommit(true);
				pstmt = conn.prepareStatement(loadIdQuery);
				pstmt.setString(1, idName);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					oldId = rs.getLong(1);
				}
				rs.close();
				pstmt.close();
				pstmt = null;
				if (oldId == 0) {
					oldId = 1;
					pstmt = conn.prepareStatement(createIdQuery);
					pstmt.setString(1, idName);
					pstmt.setLong(2, oldId);
					pstmt.executeUpdate();
					pstmt.close();
					pstmt = null;
				}
				pstmt = conn.prepareStatement(updateIdQuery);
				pstmt.setLong(1, oldId + 1);
				pstmt.setString(2, idName);
				pstmt.setLong(3, oldId);
				count = pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				conn.close();
				conn = null;
				success = true;
			} catch (Exception e) {
				if (logger.isWarnEnabled()) {
					logger.warn("error on getNextLongId(\"" + idName + "\")", e);
				}
			} finally {
				JdbcUtils.closeStatement(pstmt);
				JdbcUtils.closeConnection(conn);
				if (maxRetry > 0) {
					retries--;
				}
			}

			if (count > 0 && success) {
				if (logger.isDebugEnabled()) {
					logger.debug("getNextLongId(\"" + idName + "\") success!");
				}

				// 更新成功
				return idDecorator == null ? oldId : (Long)idDecorator
						.getDecoratedId(oldId);				
			}
			// 未获取到id，准备重试
			if (maxRetry > 0 && retries <= 0) {
				logger.info("getNextLongId(\"" + idName
						+ "\") failure after retried " + maxRetry + " times!");
				// 已达到最大重试次数，出错
				throw new TransientDataAccessResourceException(
						"getNextLongId(\"" + idName
								+ "\") failure after retried " + maxRetry
								+ " times!");
			}
			// Call this method again, but sleep briefly to try to avoid
			// thread contention.
			if (logger.isDebugEnabled()) {
				logger.debug("wait a while before retry getNextLongId(\""
						+ idName + "\")."
						+ (maxRetry > 0 ? " (" + retries + " retry left)" : ""));
			}
			try {
				Thread.sleep((long) (100 * Math.random()));
			} catch (InterruptedException ie) {
			}
		}
		// 未能成功生成，出错
		throw new TransientDataAccessResourceException("getNextLongId failure!");
	}

}
