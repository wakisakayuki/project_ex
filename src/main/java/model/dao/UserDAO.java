package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.UserBean;

public class UserDAO {
	public UserBean login(String password,String userId) throws ClassNotFoundException, SQLException {
		String sql = "SELECT user_name user_id update_datetime FROM m_user WHERE user_id = ? AND password = ?";
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		
		pstmt.setString(1, password);
		pstmt.setString(2, userId);
		
		ResultSet rs = pstmt.executeQuery();
		
		UserBean userBean = new UserBean();
		
		while (rs.next()) {
			userBean.setUserName(rs.getString("user_name"));
			userBean.setUserId(rs.getString("user_id"));
			userBean.setUserId(rs.getString("update_datetime"));
		}
		
		return userBean;
		
	}
	
}
