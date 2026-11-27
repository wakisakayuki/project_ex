package model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import model.entity.TaskBean;

/**
 * タスクの登録・検索・更新・削除を担当するDAO。
 */
public class TaskDAO {
    private final Connection connection;

    public TaskDAO() {
        this.connection = null;
    }

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection open() throws SQLException {
        return connection == null ? ConnectionManager.getConnection() : connection;
    }

    private boolean ownsConnection() {
        return connection == null;
    }

    public int insert(TaskBean task) throws SQLException {
        String sql = "INSERT INTO t_task (task_name,category_id,limit_date,user_id,status_code,memo,create_datetime,update_datetime) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
        try (Connection c = ownsConnection() ? open() : null; PreparedStatement p = (ownsConnection() ? c : connection).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, task.getTaskName());
            p.setInt(2, task.getCategoryId());
            if (task.getLimitDate() == null) p.setNull(3, Types.DATE);
            else p.setDate(3, java.sql.Date.valueOf(task.getLimitDate()));
            p.setString(4, task.getUserId());
            p.setString(5, task.getStatusCode());
            p.setString(6, task.getMemo());
            int n = p.executeUpdate();
            if (n == 1) try (ResultSet k = p.getGeneratedKeys()) {
                if (k.next()) task.setTaskId(k.getInt(1));
            }
            return n;
        }
    }

    public List<TaskBean> findByUserIdOrderByLimitDate(String userId) throws SQLException {
        String sql = "SELECT t.task_id,t.task_name,t.category_id,c.category_name,t.limit_date,t.user_id,u.user_name,t.status_code,s.status_name,t.memo,t.create_datetime,t.update_datetime FROM t_task t JOIN m_category c ON t.category_id=c.category_id JOIN m_user u ON t.user_id=u.user_id JOIN m_status s ON t.status_code=s.status_code WHERE t.user_id=? ORDER BY t.limit_date IS NULL,t.limit_date ASC,t.task_id ASC";
        List<TaskBean> list = new ArrayList<>();
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, userId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) list.add(map(r));
            }
        }
        return list;
    }

    public TaskBean findById(int id) throws SQLException {
        String sql = "SELECT t.task_id,t.task_name,t.category_id,c.category_name,t.limit_date,t.user_id,u.user_name,t.status_code,s.status_name,t.memo,t.create_datetime,t.update_datetime FROM t_task t JOIN m_category c ON t.category_id=c.category_id JOIN m_user u ON t.user_id=u.user_id JOIN m_status s ON t.status_code=s.status_code WHERE t.task_id=?";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }

    public TaskBean findByIdAndUserId(int id, String userId) throws SQLException {
        String sql = "SELECT t.task_id,t.task_name,t.category_id,c.category_name,t.limit_date,t.user_id,u.user_name,t.status_code,s.status_name,t.memo,t.create_datetime,t.update_datetime FROM t_task t JOIN m_category c ON t.category_id=c.category_id JOIN m_user u ON t.user_id=u.user_id JOIN m_status s ON t.status_code=s.status_code WHERE t.task_id=? AND t.user_id=?";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.setString(2, userId);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }

    public TaskBean findDetailById(int id) throws SQLException {
        return findById(id);
    }

    public int updateByIdAndUserId(TaskBean task, String userId) throws SQLException {
        String sql = "UPDATE t_task SET task_name=?,category_id=?,limit_date=?,status_code=?,memo=?,update_datetime=CURRENT_TIMESTAMP WHERE task_id=? AND user_id=?";
        try (Connection c = ConnectionManager.getConnection();PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, task.getTaskName());
            p.setInt(2, task.getCategoryId());
            if (task.getLimitDate() == null) p.setNull(3, Types.DATE);
            else p.setDate(3, java.sql.Date.valueOf(task.getLimitDate()));
            p.setString(4, task.getStatusCode());
            p.setString(5, task.getMemo());
            p.setInt(6, task.getTaskId());
            p.setString(7, userId);
            return p.executeUpdate();
        }
    }

    public boolean existsById(int id) throws SQLException {
        Connection c = ownsConnection() ? open() : connection;
        try (Connection owned = ownsConnection() ? c : null;PreparedStatement p = c.prepareStatement("SELECT 1 FROM t_task WHERE task_id=?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        }
    }

    public List<TaskBean> findOwnedByIdsForUpdate(List<Integer> ids, String userId) throws SQLException {
        List<TaskBean> result = new ArrayList<>();
        String sql = "SELECT t.task_id,t.task_name,t.category_id,c.category_name,t.limit_date,t.user_id,u.user_name,t.status_code,s.status_name,t.memo,t.create_datetime,t.update_datetime FROM t_task t JOIN m_category c ON t.category_id=c.category_id JOIN m_user u ON t.user_id=u.user_id JOIN m_status s ON t.status_code=s.status_code WHERE t.task_id=? AND t.user_id=? FOR UPDATE";
        for (Integer id : ids) {
            try (PreparedStatement p = connection.prepareStatement(sql)) {
                p.setInt(1, id);
                p.setString(2, userId);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) result.add(map(r));
                }
            }
        }
        return result;
    }

    public int deleteOwnedByIds(List<Integer> ids, String userId) throws SQLException {
        int count = 0;
        String sql = "DELETE FROM t_task WHERE user_id=? AND task_id=?";
        for (Integer id : ids) {
            try (PreparedStatement p = connection.prepareStatement(sql)) {
                p.setString(1, userId);
                p.setInt(2, id);
                count+= p.executeUpdate();
            }
        }
        return count;
    }

    private TaskBean map(ResultSet r) throws SQLException {
        TaskBean b = new TaskBean();
        b.setTaskId(r.getInt("task_id"));
        b.setTaskName(r.getString("task_name"));
        b.setCategoryId(r.getInt("category_id"));
        b.setCategoryName(r.getString("category_name"));
        java.sql.Date d = r.getDate("limit_date");
        b.setLimitDate(d == null ? null : d.toLocalDate());
        b.setUserId(r.getString("user_id"));
        b.setUserName(r.getString("user_name"));
        b.setStatusCode(r.getString("status_code"));
        b.setStatusName(r.getString("status_name"));
        b.setMemo(r.getString("memo"));
        b.setCreateDateTime(r.getTimestamp("create_datetime"));
        b.setUpdateDateTime(r.getTimestamp("update_datetime"));
        return b;
    }
}
