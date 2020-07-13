package com.superzmz.www.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scheduleapprover")
public class ScheduleApprover {
    public static final int STATUS_PROCESS = 0; //处理中
    public static final int STATUS_DONE = 1; //
    public static final int STATUS_NOT_DONE = 2;//

    @Id
    @Column(name = "staff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;// 说明

    private int status; // 1.同意 2.拒绝

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private Long scheduleId;

    private Long userId; //

    private String username; //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
