package com.nowhealth.mobile.entity;

import java.util.Date;

public class BankInfor {
    private Integer bankid;//主键id

    private String baseinfororderno;//订单号

    private Float amountmoney;//佣金金额

    private String starts;//佣金状态

    private String payee;//收款人

    private String banknumber;//银行账户

    private String bankphoneno;//银行预留手机号

    private Date createtime;//创建时间

    private Date updatetime;//更新时间

    public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    public String getBaseinfororderno() {
        return baseinfororderno;
    }

    public void setBaseinfororderno(String baseinfororderno) {
        this.baseinfororderno = baseinfororderno == null ? null : baseinfororderno.trim();
    }

    public Float getAmountmoney() {
        return amountmoney;
    }

    public void setAmountmoney(Float amountmoney) {
        this.amountmoney = amountmoney;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts == null ? null : starts.trim();
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.trim();
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber == null ? null : banknumber.trim();
    }

    public String getBankphoneno() {
        return bankphoneno;
    }

    public void setBankphoneno(String bankphoneno) {
        this.bankphoneno = bankphoneno == null ? null : bankphoneno.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}