package com.nowhealth.mobile.entity;

import java.util.Date;

public class BaseInfor {
    private Integer orderid;//订单ID

    private String orderno;//订单号

    private Integer isagentshare;//是否为分享后出单标准

    private String purchasername;//购买人名字

    private String cardStyle;       //证件类型
    
    private String purchaserID;     //身份证号
    
    private String passportID;      //护照号
    
    private String cardID;          //购买人护照号===
    
    private String purchaserSex;    //购买人性别===
    
    private String purchaserAge;    //购买人年龄===
    
    private double purchaserHigh;   //购买人身高===
    
    private double purchaserweight; //购买人体重===
    
    private String isAllergy;       //是否有过敏史===
    
    private String isfamilyillness; //是否有家族病史

    private String purchaserphoneno;//购买人手机号

    private String purchaseraddress;//购买人常住地址

    private String orderstart;//订单状态

    private String agentcode;//代理人编码

    private Integer userinforid;//userInfor 表主键id

    private String querycode;//查询码

    private Integer queryinforid;//查询码主键id

    private double totalamount;//总金额

    private double commission;//佣金

    private String commissionstart;//佣金状态(10未提现20提现中30已提现)

    private String paymentstatus;//付款状态

    private Date createtime;//创建时间

    private Date updatetime;//更新时间

    private String effectivedate;//生效日期
    
    private String purchaseremail;//购买人邮箱
    
    public String getPurchaseremail() {
		return purchaseremail;
	}

	public String getPurchaserID() {
		return purchaserID;
	}

	public void setPurchaserID(String purchaserID) {
		this.purchaserID = purchaserID;
	}

	public String getPassportID() {
		return passportID;
	}

	public void setPassportID(String passportID) {
		this.passportID = passportID;
	}

	public void setPurchaseremail(String purchaseremail) {
		this.purchaseremail = purchaseremail;
	}

	public String getEffectivedate() {
		return effectivedate;
	}

	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}

	public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getIsagentshare() {
        return isagentshare;
    }
    
    public void setIsagentshare(Integer isagentshare) {
        this.isagentshare = isagentshare;
    }

    public String getPurchasername() {
        return purchasername;
    }

    public void setPurchasername(String purchasername) {
        this.purchasername = purchasername == null ? null : purchasername.trim();
    }

    public String getCardStyle() {
        return cardStyle;
    }

    public void setCardStyle(String cardStyle) {
        this.cardStyle = cardStyle;
    }

    public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID == null ? null : cardID.trim();
	}

	public String getPurchaserSex() {
		return purchaserSex;
	}

	public void setPurchaserSex(String purchaserSex) {
		this.purchaserSex = purchaserSex;
	}

	public String getPurchaserAge() {
		return purchaserAge;
	}

	public void setPurchaserAge(String purchaserAge) {
		this.purchaserAge = purchaserAge;
	}

	public double getPurchaserHigh() {
		return purchaserHigh;
	}

	public void setPurchaserHigh(double purchaserHigh) {
		this.purchaserHigh = purchaserHigh;
	}

	public double getPurchaserweight() {
		return purchaserweight;
	}

	public void setPurchaserweight(double purchaserweight) {
		this.purchaserweight = purchaserweight;
	}

	public String getIsAllergy() {
		return isAllergy;
	}

	public void setIsAllergy(String isAllergy) {
		this.isAllergy = isAllergy;
	}

	public String getIsfamilyillness() {
		return isfamilyillness;
	}

	public void setIsfamilyillness(String isfamilyillness) {
		this.isfamilyillness = isfamilyillness;
	}

	public String getPurchaserphoneno() {
        return purchaserphoneno;
    }

    public void setPurchaserphoneno(String purchaserphoneno) {
        this.purchaserphoneno = purchaserphoneno == null ? null : purchaserphoneno.trim();
    }

    public String getPurchaseraddress() {
        return purchaseraddress;
    }

    public void setPurchaseraddress(String purchaseraddress) {
        this.purchaseraddress = purchaseraddress == null ? null : purchaseraddress.trim();
    }

    public String getOrderstart() {
        return orderstart;
    }

    public void setOrderstart(String orderstart) {
        this.orderstart = orderstart == null ? null : orderstart.trim();
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode == null ? null : agentcode.trim();
    }

    public Integer getUserinforid() {
        return userinforid;
    }

    public void setUserinforid(Integer userinforid) {
        this.userinforid = userinforid;
    }

    public String getQuerycode() {
        return querycode;
    }

    public void setQuerycode(String querycode) {
        this.querycode = querycode == null ? null : querycode.trim();
    }

    public Integer getQueryinforid() {
        return queryinforid;
    }

    public void setQueryinforid(Integer queryinforid) {
        this.queryinforid = queryinforid;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getCommissionstart() {
        return commissionstart;
    }

    public void setCommissionstart(String commissionstart) {
        this.commissionstart = commissionstart == null ? null : commissionstart.trim();
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus == null ? null : paymentstatus.trim();
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