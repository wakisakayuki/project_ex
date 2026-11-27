package model.entity;

import java.sql.Timestamp;

public class CategoryBean {
    private int categoryId;
    private String categoryName;
    private Timestamp updateDateTime;
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Timestamp getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(Timestamp updateDateTime) { this.updateDateTime = updateDateTime; }
}
