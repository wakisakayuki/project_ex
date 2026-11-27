package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** MySQL connection settings can be supplied with TASK_DB_URL, TASK_DB_USER and TASK_DB_PASSWORD. */
/**
 * データベース接続の生成と接続設定をまとめるクラス。
 */
public final class ConnectionManager {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/task_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        String url = setting("TASK_DB_URL", "task.db.url", DEFAULT_URL);
        String user = setting("TASK_DB_USER", "task.db.user", "root");
        String password = setting("TASK_DB_PASSWORD", "task.db.password", "");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver is unavailable", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String env, String property, String fallback) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) value = System.getenv(env);
        return value == null ? fallback : value;
    }
}
