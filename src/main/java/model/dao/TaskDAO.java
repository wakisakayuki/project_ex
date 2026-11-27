package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entity.TaskBean;

public class TaskDAO {
	public List<TaskBean> selectAll() throws ClassNotFoundException, SQLException{
		String sql = "SELECT t1.task_id,t1.task_name,t1.category_id,t2.category_name,t1.limit_date,t1.user_id,t3.user_name,t1.status_code,t4.status_name,t1.memo,t1.create_datetime,t1.update_datetime "
				+ "FROM t_task t1 LEFT JOIN m_category t2 "
				+ "ON t1.category_id = t2.category_id "
				+ "LEFT JOIN m_user t3 "
				+ "ON t1.user_id = t3.user_id "
				+ "LEFT JOIN m_status t4 "
				+ "ON t1.status_code = t4.status_code";
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		try(PreparedStatement pstmt = ConnectionManager.getConnection().prepareStatement(sql)){
			ResultSet res = pstmt.executeQuery();
			while(res.next()) {
				TaskBean task = new TaskBean();
				task.setTaskId(res.getInt("t1.task_id"));
				task.setTaskName(res.getString("t1.task_name"));
				task.setCategoryId(res.getInt("t1.category_id"));
				task.setCategoryName(res.getString("t2.category_name"));
				task.setLimitDate(res.getObject("t1.limit_date",LocalDate.class));
				task.setUserId(res.getString("t1.user_id"));
				task.setUserName(res.getString("t3.user_name"));
				task.setStatusCode(res.getString("t1.status_code").charAt(0));
				task.setStatusName(res.getString("t4.status_name"));
				task.setMemo(res.getString("t1.memo"));
				task.setUpdateDateTime(res.getObject("t1.update_datetime",LocalDate.class));
				task.setCreateDateTime(res.getObject("t1.create_datetime",LocalDate.class));
				taskList.add(task);
			}
		}
		return taskList;
	}
	
}
