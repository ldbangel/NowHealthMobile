package com.nowhealth.mobile.entity;

import java.util.Date;

public class QueryInfor {
	
	    private Integer querycodeid;  //咨询码id

	    private String baseinfororderno; //订单号

	    private String querycode;  //咨询码

	    private String querycodestart; //咨询码状态

	    private Date createtime; //创建时间 

	    private Date updatetime;//更新时间

	    public Integer getQuerycodeid() {
	        return querycodeid;
	    }

	    public void setQuerycodeid(Integer querycodeid) {
	        this.querycodeid = querycodeid;
	    }

	    public String getBaseinfororderno() {
	        return baseinfororderno;
	    }

	    public void setBaseinfororderno(String baseinfororderno) {
	        this.baseinfororderno = baseinfororderno == null ? null : baseinfororderno.trim();
	    }

	    public String getQuerycode() {
	        return querycode;
	    }

	    public void setQuerycode(String querycode) {
	        this.querycode = querycode == null ? null : querycode.trim();
	    }

	    public String getQuerycodestart() {
	        return querycodestart;
	    }

	    public void setQuerycodestart(String querycodestart) {
	        this.querycodestart = querycodestart == null ? null : querycodestart.trim();
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
