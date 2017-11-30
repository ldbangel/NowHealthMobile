package com.nowhealth.mobile.entity;

public class UserCommssionInfor {
	
	private Integer id;

	private Integer userid;

    private String phoneno;

    private String bankcard;

    private String darwaltime;

    private Double amount;

    private String drawalstate;

    private String transacNumber;//交易批次号
    
    public String getTransacNumber() {
		return transacNumber;
	}

	public void setTransacNumber(String transacNumber) {
		this.transacNumber = transacNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard == null ? null : bankcard.trim();
    }

    public String getDarwaltime() {
        return darwaltime;
    }

    public void setDarwaltime(String darwaltime) {
        this.darwaltime = darwaltime == null ? null : darwaltime.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDrawalstate() {
        return drawalstate;
    }

    public void setDrawalstate(String drawalstate) {
        this.drawalstate = drawalstate == null ? null : drawalstate.trim();
    }
}
