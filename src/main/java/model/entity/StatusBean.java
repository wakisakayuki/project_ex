package model.entity;

import java.sql.Timestamp;

/**
 * タスク状態の情報を保持するエンティティ。
 */
public class StatusBean {
    private String statusCode;
    private String statusName;
    private Timestamp updateDateTime;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
