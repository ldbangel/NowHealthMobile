package com.nowhealth.mobile.entity;

import java.util.Date;

public class UserInfor {
    private Integer userid;

    private String username;//用户名称

    private String password;//用户密码

    private String nickname;//微信昵称
    
    private String headimg;

    private String headportrait;//微信头像

    private String userphoneno;//电话号码

    private String emailsaddress;//邮箱

    private Integer agentflag;//代理人标志

    private Integer usertype;//账户类型

    private String couponcode;//代理人优惠码

    private String payee;//收款人

    private String payeeid;//收款人身份证

    private String banknumber;//绑定银行卡号

    private String bankphoneno;//银行预留手机号码
    
    private String drawalamount;  //提现金额
    
    private String withdrawal;    //已提现金额
    
    private String totaldraAmount; //佣金总额

    private Date createtimes;//创建时间

    private Date updatetimes;//更新时间
    
    private String cashDealpass;//提现交易密码
    
    private String bankprop;//银行代码属性
    
    private Integer verificNumber;//四要素验证當天次數
    
    private String verificTime;//四要素验证時間
    
    private Integer varificTotalNumber;//四要素验证总记录数
    
    private String bankPayUserId;//银行卡支付通联返回的userid;
    
    
 /*   private */
    
    public String getBankPayUserId() {
		return bankPayUserId;
	}

	public void setBankPayUserId(String bankPayUserId) {
		this.bankPayUserId = bankPayUserId;
	}

	public Integer getVarificTotalNumber() {
		return varificTotalNumber;
	}

	public void setVarificTotalNumber(Integer varificTotalNumber) {
		this.varificTotalNumber = varificTotalNumber;
	}

	public Integer getVerificNumber() {
		return verificNumber;
	}

	public void setVerificNumber(Integer verificNumber) {
		this.verificNumber = verificNumber;
	}

	public String getVerificTime() {
		return verificTime;
	}

	public void setVerificTime(String verificTime) {
		this.verificTime = verificTime;
	}

	public String getBankprop() {
		return bankprop;
	}

	public void setBankprop(String bankprop) {
		this.bankprop = bankprop;
	}

	public String getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}

	public String getTotaldraAmount() {
		return totaldraAmount;
	}

	public void setTotaldraAmount(String totaldraAmount) {
		this.totaldraAmount = totaldraAmount;
	}

	public String getDrawalamount() {
		return drawalamount;
	}

	public void setDrawalamount(String drawalamount) {
		this.drawalamount = drawalamount;
	}

	public String getCashDealpass() {
		return cashDealpass;
	}

	public void setCashDealpass(String cashDealpass) {
		this.cashDealpass = cashDealpass;
	}

	public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }
    
    

    public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait == null ? null : headportrait.trim();
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno == null ? null : userphoneno.trim();
    }

    public String getEmailsaddress() {
        return emailsaddress;
    }

    public void setEmailsaddress(String emailsaddress) {
        this.emailsaddress = emailsaddress == null ? null : emailsaddress.trim();
    }

    public Integer getAgentflag() {
        return agentflag;
    }

    public void setAgentflag(Integer agentflag) {
        this.agentflag = agentflag;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode == null ? null : couponcode.trim();
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.trim();
    }

    public String getPayeeid() {
        return payeeid;
    }

    public void setPayeeid(String payeeid) {
        this.payeeid = payeeid == null ? null : payeeid.trim();
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

    public Date getCreatetimes() {
        return createtimes;
    }

    public void setCreatetimes(Date createtimes) {
        this.createtimes = createtimes;
    }

    public Date getUpdatetimes() {
        return updatetimes;
    }

    public void setUpdatetimes(Date updatetimes) {
        this.updatetimes = updatetimes;
    }
}