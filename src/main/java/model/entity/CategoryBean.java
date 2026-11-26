package model.entity;

import java.time.LocalDate;

public class CategoryBean {
	private int categoryId;
	private String categoryName;
	private LocalDate updateDateTime;
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
	public LocalDate getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(LocalDate updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
