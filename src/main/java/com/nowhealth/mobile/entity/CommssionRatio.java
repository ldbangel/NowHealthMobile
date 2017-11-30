package com.nowhealth.mobile.entity;

import java.util.Date;

/**
 * 
 * @Description: (佣金比例实体类) 
 * @author zhuhaidong
 * @date 2017-8-21 上午10:41:55
 */
public class CommssionRatio {

	    private Integer comratioid;

	    private Double comratio;

	    private Date createtime;

	    private Date updatetime;

	    private String userphone;

	    private String usernickname;

	    public Integer getComratioid() {
	        return comratioid;
	    }

	    public void setComratioid(Integer comratioid) {
	        this.comratioid = comratioid;
	    }

	    public Double getComratio() {
	        return comratio;
	    }

	    public void setComratio(Double comratio) {
	        this.comratio = comratio;
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

	    public String getUserphone() {
	        return userphone;
	    }

	    public void setUserphone(String userphone) {
	        this.userphone = userphone == null ? null : userphone.trim();
	    }

	    public String getUsernickname() {
	        return usernickname;
	    }

	    public void setUsernickname(String usernickname) {
	        this.usernickname = usernickname == null ? null : usernickname.trim();
	    }
}
