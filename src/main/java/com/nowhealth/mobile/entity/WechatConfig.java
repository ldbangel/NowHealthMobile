package com.nowhealth.mobile.entity;

public class WechatConfig {
	private String appid;
	private String timestamp;
	private String nonceStr;
	private String signature;
	private String sharedLink;
	public String getSharedLink() {
		return sharedLink;
	}
	public void setSharedLink(String sharedLink) {
		this.sharedLink = sharedLink;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
		
}
