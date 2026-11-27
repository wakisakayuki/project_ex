package model.entity;

import java.sql.Timestamp;

public class CommentBean {
    private int commentId;
    private int taskId;
    private String userId;
    private String userName;
    private String comment;
    private Timestamp updateDateTime;
    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }
    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Timestamp getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(Timestamp updateDateTime) { this.updateDateTime = updateDateTime; }
}
