package com.schcilin.goods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 重保零报告事件|sjxf|zbgl
 * </p>
 *
 * @author garve
 * @since 2018-12-26
 */
@TableName("zbgl_incident")
public class ZbglIncident implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表单id
     */
    private String id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 标题
     */
    private String subject;

    /**
     * 机构代码
     */
    private byte[] orgCode;

    /**
     * 需求公司id
     */
    private String companyId;

    /**
     * 需求公司代码
     */
    private String companyCode;

    /**
     * 需求公司名称
     */
    private String companyName;

    /**
     * 需求部门id
     */
    private String deptId;

    /**
     * 需求部门代码
     */
    private String deptCode;

    /**
     * 需求部门名称
     */
    private String deptName;

    /**
     * 联系人
     */
    private String creatorName;

    /**
     * 联系人电话
     */
    private String creatorPhone;

    /**
     * 起草日期
     */
    private LocalDateTime createDate;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建人工号
     */
    private String createAccount;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 工单号
     */
    private String appNo;

    /**
     * 自定义表单名称
     */
    private String customName;

    /**
     * 自定义表单id
     */
    private String customId;

    /**
     * 每日回单时间
     */
    private String returnTime;

    /**
     * 提交开始时间
     */
    private LocalDateTime submitStart;

    /**
     * 提交结束时间
     */
    private LocalDateTime submitEnd;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 发布状态（0：未发布，1：已发布）
     */
    private String publishStatus;

    /**
     * 事件活动描述
     */
    private String description;

    /**
     * 填报人名称
     */
    private String informantName;

    /**
     * 填报人账号
     */
    private String informantAccount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(byte[] orgCode) {
        this.orgCode = orgCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public LocalDateTime getSubmitStart() {
        return submitStart;
    }

    public void setSubmitStart(LocalDateTime submitStart) {
        this.submitStart = submitStart;
    }

    public LocalDateTime getSubmitEnd() {
        return submitEnd;
    }

    public void setSubmitEnd(LocalDateTime submitEnd) {
        this.submitEnd = submitEnd;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInformantName() {
        return informantName;
    }

    public void setInformantName(String informantName) {
        this.informantName = informantName;
    }

    public String getInformantAccount() {
        return informantAccount;
    }

    public void setInformantAccount(String informantAccount) {
        this.informantAccount = informantAccount;
    }

    @Override
    public String toString() {
        return "ZbglIncident{" +
        "id=" + id +
        ", status=" + status +
        ", subject=" + subject +
        ", orgCode=" + orgCode +
        ", companyId=" + companyId +
        ", companyCode=" + companyCode +
        ", companyName=" + companyName +
        ", deptId=" + deptId +
        ", deptCode=" + deptCode +
        ", deptName=" + deptName +
        ", creatorName=" + creatorName +
        ", creatorPhone=" + creatorPhone +
        ", createDate=" + createDate +
        ", createId=" + createId +
        ", createAccount=" + createAccount +
        ", createName=" + createName +
        ", appNo=" + appNo +
        ", customName=" + customName +
        ", customId=" + customId +
        ", returnTime=" + returnTime +
        ", submitStart=" + submitStart +
        ", submitEnd=" + submitEnd +
        ", activityName=" + activityName +
        ", publishStatus=" + publishStatus +
        ", description=" + description +
        ", informantName=" + informantName +
        ", informantAccount=" + informantAccount +
        "}";
    }
}
