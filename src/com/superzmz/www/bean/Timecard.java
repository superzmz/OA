package com.superzmz.www.bean;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "zmz_timecard")
public class Timecard {
    public static final int NO_CLOCK_IN = 1;//未打卡
    public static final int CLOCK_IN = 2;//打卡

    @Id
    @Column(name = "timecard_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;//用户Id

    private String loginName;//登录名

    private String username;//用户姓名

    private int type;//请假，迟到，矿工，出差

    private Date createTime;//创建时间

    private String departmentName;//部门名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Timecard{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
