package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.entity.StatusBean;

/**
 * タスク状態のマスタ情報を取得するDAO。
 */
public class StatusDAO {

    public List<StatusBean> selectAllStatus() throws SQLException {
        List<StatusBean> result = new ArrayList<>();
        try (Connection c = ConnectionManager.getConnection(); PreparedStatement p = c.prepareStatement("SELECT status_code, status_name, update_datetime FROM m_status ORDER BY status_code"); ResultSet r = p.executeQuery()) {
            while (r.next()) {
                StatusBean b = new StatusBean();
                b.setStatusCode(r.getString("status_code"));
                b.setStatusName(r.getString("status_name"));
                b.setUpdateDateTime(r.getTimestamp("update_datetime"));
                result.add(b);
            }
        }
        return result;
    }

    public boolean exists(String statusCode) throws SQLException {
        try (Connection c = ConnectionManager.getConnection(); PreparedStatement p = c.prepareStatement("SELECT 1 FROM m_status WHERE status_code=?")) {
            p.setString(1, statusCode);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        }
    }
}
