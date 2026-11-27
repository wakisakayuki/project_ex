package model.dao;

import java.sql.Connection; import java.sql.PreparedStatement; import java.sql.ResultSet; import java.sql.SQLException;
import java.util.ArrayList; import java.util.List; import model.entity.CategoryBean;
public class CategoryDAO {
    public List<CategoryBean> selectAllCategory() throws SQLException {
        List<CategoryBean> result = new ArrayList<>();
        try (Connection c=ConnectionManager.getConnection(); PreparedStatement p=c.prepareStatement("SELECT category_id, category_name, update_datetime FROM m_category ORDER BY category_id"); ResultSet r=p.executeQuery()) {
            while(r.next()){ CategoryBean b=new CategoryBean(); b.setCategoryId(r.getInt("category_id")); b.setCategoryName(r.getString("category_name")); b.setUpdateDateTime(r.getTimestamp("update_datetime")); result.add(b); }
        } return result;
    }
}
