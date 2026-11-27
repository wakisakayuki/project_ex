package model.entity;

import java.time.LocalDate;

public class TaskBean {
	private int taskId;
	private String taskName;
	private int categoryId;
	private String categoryName;
	private LocalDate limitDate;
	private String userId;
	private String userName;
	private char statusCode;
	private String statusName;
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	private String memo;
	private LocalDate createDateTime;
	private LocalDate updateDateTime;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public LocalDate getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public char getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(char statusCode) {
		this.statusCode = statusCode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public LocalDate getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(LocalDate createDateTime) {
		this.createDateTime = createDateTime;
	}
	public LocalDate getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(LocalDate updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	}
