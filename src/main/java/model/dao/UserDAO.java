package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.entity.UserBean;

public class UserDAO {
    public UserBean findByIdAndPassword(String userId, String password) throws SQLException {
        String sql = "SELECT user_id, user_name FROM m_user WHERE user_id = ? AND password = ?";
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId); ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    public List<UserBean> selectAllUser() throws SQLException {
        List<UserBean> users = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT user_id, user_name FROM m_user ORDER BY user_id ASC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) users.add(map(rs));
        }
        return users;
    }
    private UserBean map(ResultSet rs) throws SQLException {
        UserBean user = new UserBean(); user.setUserId(rs.getString("user_id")); user.setUserName(rs.getString("user_name"));
        return user;
    }
}
