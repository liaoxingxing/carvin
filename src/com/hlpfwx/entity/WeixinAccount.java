package com.hlpfwx.entity;

import org.springframework.util.StringUtils;

import com.framework.util.Page;

/**
 * weixin_account
 */
public class WeixinAccount {

	/**
	 */
	private Integer id;
	
	/**
	   公众帐号名称
	 */
	private String accountname;
	
	/**
	   公众帐号TOKEN
	 */
	private String accounttoken;
	
	/**
	   公众帐号授权码
	 */
	private String accesstoken;
	
	/**
	   公众微信号
	 */
	private String accountnumber;
	
	/**
	   公众号类型
	 */
	private String accounttype;
	
	/**
	   电子邮箱
	 */
	private String accountemail;
	
	/**
	   公众帐号描述
	 */
	private String accountdesc;
	
	/**
	   ACCESS_TOKEN
	 */
	private String accountopenid;
	
	/**
	   公众帐号APPID
	 */
	private String accountappid;
	
	/**
	   公众帐号APPSECRET
	 */
	private String accountappsecret;
	
		
	//分页
	private Page page;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 
	
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	} 
	
	public String getAccounttoken() {
		return accounttoken;
	}

	public void setAccounttoken(String accounttoken) {
		this.accounttoken = accounttoken;
	} 
	
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	} 
	
	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	} 
	
	public String getAccountemail() {
		return accountemail;
	}

	public void setAccountemail(String accountemail) {
		this.accountemail = accountemail;
	} 
	
	public String getAccountdesc() {
		return accountdesc;
	}

	public void setAccountdesc(String accountdesc) {
		this.accountdesc = accountdesc;
	} 
	
	public String getAccountopenid() {
		return accountopenid;
	}

	public void setAccountopenid(String accountopenid) {
		this.accountopenid = accountopenid;
	} 
	
	public String getAccountappid() {
		return accountappid;
	}

	public void setAccountappid(String accountappid) {
		this.accountappid = accountappid;
	} 
	
	public String getAccountappsecret() {
		return accountappsecret;
	}

	public void setAccountappsecret(String accountappsecret) {
		this.accountappsecret = accountappsecret;
	} 
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public String getAccesstoken() {
		if(StringUtils.isEmpty(accesstoken)){
			
		}
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
}
