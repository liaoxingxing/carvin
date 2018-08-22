package com.hlpfwx.service;
import javax.servlet.http.HttpServletRequest;

/**
 * watch_user
 */
public interface WinxinService{
	
	//接收消息
	public String processRequest(HttpServletRequest request);
	
	//接收消息
	public String getValidateCode(String phonenum,String sendContent);

}