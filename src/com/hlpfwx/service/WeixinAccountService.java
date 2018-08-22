package com.hlpfwx.service;
import java.util.*;

import com.hlpfwx.entity.WeixinAccount;

/**
 * weixin_account
 */
public interface WeixinAccountService{
   
	
	/**
	 * 根据条件 查询全部listPageAccount
	 */
	public List<WeixinAccount> listPageWeixinAccount(WeixinAccount weixinAccount);
	
	/**
	 *  获取WeixinAccount的数量
	 */
	public Integer getWeixinAccountCount();
	
	/**
	 *插入
	 */
	public void insert(WeixinAccount weixinAccount) throws Exception;
	
	/**
	 * 根据主键查询(唯一)
	 */
	public WeixinAccount getWeixinAccountById(Integer id);
	
	/**
	 * 根据根据条件查询WeixinAccount 
	 */
	public List<WeixinAccount> listWeixinAccount(WeixinAccount weixinAccount);  
	 
	 /**
	 * 更新WeixinAccount
	 */
	public void updateWeixinAccount(WeixinAccount weixinAccount) throws Exception;
	
	 /**
	 * 很据实体类删除
	 */
	
	public void  deleteWeixinAccount(WeixinAccount weixinAccount)throws Exception;
	
	 /**
	 * 根据主键删除
	 */
	public void  deleteWeixinAccountByIds (String[] ids);
	/**
	 *有条件的更新
	 */
	
	public void insertSelective(WeixinAccount weixinAccount);
	/**
	 *根据主键有条件的更新
	 */
	
	public void updateByPrimaryKeySelective(WeixinAccount weixinAccount)  throws Exception;
	
}