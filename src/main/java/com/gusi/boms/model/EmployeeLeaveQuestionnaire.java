package com.gusi.boms.model;

import java.util.Date;


public class EmployeeLeaveQuestionnaire {
    private int id;

    private Integer leaveRecordId;

    private Integer questionId;

    private Integer answerId;

    private String answerValue;

    private Date createTime;

    private String description;

    private Integer order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLeaveRecordId() {
        return leaveRecordId;
    }

    public void setLeaveRecordId(Integer leaveRecordId) {
        this.leaveRecordId = leaveRecordId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}