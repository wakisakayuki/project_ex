package model.entity;

import java.sql.Timestamp;

/**
 * ログインユーザーの情報を保持するエンティティ。
 */
public class UserBean {
    private String userId;
    private String userName;
    private Timestamp updateDateTime;

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

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
