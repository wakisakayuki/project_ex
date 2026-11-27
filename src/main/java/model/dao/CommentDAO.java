package model.dao;

import java.sql.*;
import java.util.*;
import model.entity.CommentBean;

/**
 * コメントの登録・取得・削除を担当するDAO。
 */
public class CommentDAO {
    private final Connection connection;

    public CommentDAO() {
        this.connection = null;
    }

    public CommentDAO(Connection c) {
        this.connection = c;
    }

    private Connection open() throws SQLException {
        return connection == null ? ConnectionManager.getConnection() : connection;
    }

    private boolean owns() {
        return connection == null;
    }

    public List<CommentBean> findByTaskIdOrderByUpdateDatetime(int taskId) throws SQLException {
        String sql = "SELECT c.comment_id,c.task_id,c.user_id,u.user_name,c.comment,c.update_datetime FROM t_comment c JOIN m_user u ON c.user_id=u.user_id WHERE c.task_id=? ORDER BY c.update_datetime ASC,c.comment_id ASC";
        List<CommentBean> list = new ArrayList<>();
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, taskId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) list.add(map(r));
            }
        }
        return list;
    }

    public int insert(CommentBean b) throws SQLException {
        String sql = "INSERT INTO t_comment(task_id,user_id,comment,update_datetime) VALUES(?,?,?,CURRENT_TIMESTAMP)";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, b.getTaskId());
            p.setString(2, b.getUserId());
            p.setString(3, b.getComment());
            return p.executeUpdate();
        }
    }

    public CommentBean findByIdAndUserId(int id, String userId) throws SQLException {
        String sql = "SELECT comment_id,task_id,user_id,comment,update_datetime FROM t_comment WHERE comment_id=? AND user_id=?";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.setString(2, userId);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }

    public int updateByIdAndUserId(int id, String userId, String body) throws SQLException {
        String sql = "UPDATE t_comment SET comment=?,update_datetime=CURRENT_TIMESTAMP WHERE comment_id=? AND user_id=?";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, body);
            p.setInt(2, id);
            p.setString(3, userId);
            return p.executeUpdate();
        }
    }

    public int deleteByIdAndUserId(int id, String userId) throws SQLException {
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement("DELETE FROM t_comment WHERE comment_id=? AND user_id=?")) {
            p.setInt(1, id);
            p.setString(2, userId);
            return p.executeUpdate();
        }
    }

    public int deleteByTaskIds(List<Integer> ids) throws SQLException {
        int count = 0;
        for (Integer id : ids) {
            try (PreparedStatement p = connection.prepareStatement("DELETE FROM t_comment WHERE task_id=?")) {
                p.setInt(1, id);
                count+= p.executeUpdate();
            }
        }
        return count;
    }

    private CommentBean map(ResultSet r) throws SQLException {
        CommentBean b = new CommentBean();
        b.setCommentId(r.getInt("comment_id"));
        b.setTaskId(r.getInt("task_id"));
        b.setUserId(r.getString("user_id"));
        try {
            b.setUserName(r.getString("user_name"));
        }
        catch (SQLException ignored) {
        }
        b.setComment(r.getString("comment"));
        b.setUpdateDateTime(r.getTimestamp("update_datetime"));
        return b;
    }
}
