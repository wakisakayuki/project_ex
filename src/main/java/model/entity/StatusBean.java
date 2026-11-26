package model.entity;

import java.time.LocalDate;

public class StatusBean {
	private char statasCode;
	private String statusName;
	private LocalDate updateDateTime;
	public char getStatasCode() {
		return statasCode;
	}
	public void setStatasCode(char statasCode) {
		this.statasCode = statasCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public LocalDate getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(LocalDate updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
