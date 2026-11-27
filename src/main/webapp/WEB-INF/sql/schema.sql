CREATE DATABASE IF NOT EXISTS task_db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE task_db;

CREATE TABLE IF NOT EXISTS m_user (
  user_id VARCHAR(24) NOT NULL,
  password VARCHAR(32) NOT NULL,
  user_name VARCHAR(20) NOT NULL,
  update_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id),
  UNIQUE KEY uq_m_user_user_name (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS m_category (
  category_id INT NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(20) NOT NULL,
  update_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (category_id),
  UNIQUE KEY uq_m_category_category_name (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS m_status (
  status_code CHAR(2) NOT NULL,
  status_name VARCHAR(20) NOT NULL,
  update_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (status_code),
  UNIQUE KEY uq_m_status_status_name (status_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS t_task (
  task_id INT NOT NULL AUTO_INCREMENT,
  task_name VARCHAR(50) NOT NULL,
  category_id INT NOT NULL,
  limit_date DATE NULL,
  user_id VARCHAR(24) NOT NULL,
  status_code CHAR(2) NOT NULL,
  memo VARCHAR(100) NULL,
  create_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (task_id),
  KEY ix_t_task_user_limit (user_id, limit_date, task_id),
  CONSTRAINT fk_t_task_category FOREIGN KEY (category_id) REFERENCES m_category(category_id),
  CONSTRAINT fk_t_task_user FOREIGN KEY (user_id) REFERENCES m_user(user_id),
  CONSTRAINT fk_t_task_status FOREIGN KEY (status_code) REFERENCES m_status(status_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS t_comment (
  comment_id INT NOT NULL AUTO_INCREMENT,
  task_id INT NOT NULL,
  user_id VARCHAR(24) NOT NULL,
  comment VARCHAR(100) NOT NULL,
  update_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (comment_id),
  KEY ix_t_comment_task_datetime (task_id, update_datetime, comment_id),
  KEY ix_t_comment_user (user_id),
  CONSTRAINT fk_t_comment_task FOREIGN KEY (task_id) REFERENCES t_task(task_id),
  CONSTRAINT fk_t_comment_user FOREIGN KEY (user_id) REFERENCES m_user(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO m_category (category_id, category_name) VALUES
  (1, '新商品A：開発プロジェクト'),
  (2, '新商品B：改良プロジェクト')
ON DUPLICATE KEY UPDATE category_name=VALUES(category_name);

INSERT INTO m_status (status_code, status_name) VALUES
  ('00', '未着手'), ('50', '着手'), ('99', '完了')
ON DUPLICATE KEY UPDATE status_name=VALUES(status_name);

-- ユーザー情報は設計書に初期アカウントがないため登録していません。
-- 既存の認証情報または管理者から配布された情報を m_user に登録してください。
