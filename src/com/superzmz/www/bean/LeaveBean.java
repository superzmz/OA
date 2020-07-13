package com.superzmz.www.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "zmz_leave")
public class LeaveBean { //请假表
    public static final int STATUS_DRAFT = 0;//草稿
    public static final int STATUS_NOMAL = 1; //正常发布

    public static final int STATUS_PROCESS = 0; //处理中
    public static final int STATUS_AGREE = 1; //同意
    public static final int STATUS_REFUSE = 2;//拒绝


    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int type; //1事假,2病假,3年假,4调休,5婚假,6产假,7陪产假,8路途假,9其他

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private float days; //请假天数

    private String reason;// 原因

    private String picture;// 图片

    private int result;// 0处理中，1同意 2拒绝

    private int status;// 1草稿  2正常发布

    private Date createTime;//创建时间

    private Date updateTime;//更新时间

    private String approverid;//审批人s...

    private Long userId; //请假人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public String getApproverid() {
        return approverid;
    }

    public void setApproverid(String approverid) {
        this.approverid = approverid;
    }

    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", type=" + type +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", days=" + days +
                ", reason='" + reason + '\'' +
                ", picture='" + picture + '\'' +
                ", result=" + result +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", approverIds='" + approverid + '\'' +
                ", userId=" + userId +
                '}';
    }
}
