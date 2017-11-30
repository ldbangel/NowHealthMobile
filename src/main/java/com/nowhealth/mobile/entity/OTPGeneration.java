package com.nowhealth.mobile.entity;

import java.util.Date;

public class OTPGeneration {
    private Integer otpid;//主键id
    private String phoneno;//手机号
    private String validationno;//生成随机短信验证码
    private Date createtimes;//创建时间
    private String exprietimes;//更新时间
    private String status;//使用状态

    public Integer getOtpid() {
        return otpid;
    }

    public void setOtpid(Integer otpid) {
        this.otpid = otpid;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getValidationno() {
        return validationno;
    }

    public void setValidationno(String validationno) {
        this.validationno = validationno == null ? null : validationno.trim();
    }

    public Date getCreatetimes() {
        return createtimes;
    }

    public void setCreatetimes(Date createtimes) {
        this.createtimes = createtimes;
    }

    public String getExprietimes() {
        return exprietimes;
    }

    public void setExprietimes(String exprietimes) {
        this.exprietimes = exprietimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}