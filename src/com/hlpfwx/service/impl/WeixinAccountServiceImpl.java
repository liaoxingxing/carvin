package com.hlpfwx.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.framework.service.CacheService;
import com.hlpfwx.entity.WeixinAccount;
import com.hlpfwx.mapper.WeixinAccountMapper;
import com.hlpfwx.service.WeixinAccountService;

/**
 * weixin_account
 */
@Service
public  class WeixinAccountServiceImpl implements WeixinAccountService{
    @Autowired
	private WeixinAccountMapper weixinAccountMapper;
	
    @Autowired
	private CacheService cacheService;
    
	@Override
	public List<WeixinAccount> listPageWeixinAccount(WeixinAccount weixinAccount){
		return weixinAccountMapper.listPageWeixinAccount(weixinAccount);
	}
	
	@Override
	public Integer getWeixinAccountCount(){
	    return weixinAccountMapper.getWeixinAccountCount();
	}
	
	@Override
	public void insert(WeixinAccount weixinAccount) throws Exception{
		 //cacheService.setWxaccount(weixinAccount.getAccountnumber(), JSONObject.toJSONString(weixinAccount));
	     weixinAccountMapper.insert(weixinAccount);
	}
	
	@Override
	public WeixinAccount getWeixinAccountById(Integer id){
	    return weixinAccountMapper.getWeixinAccountById(id);
	}
	
	@Override
	public List<WeixinAccount> listWeixinAccount(WeixinAccount weixinAccount){
	    return weixinAccountMapper.listWeixinAccount(weixinAccount);
	}  
	
	@Override
	public void updateWeixinAccount(WeixinAccount weixinAccount) throws Exception{
		 //cacheService.setWxaccount(weixinAccount.getAccountnumber(), JSONObject.toJSONString(weixinAccount));
	     weixinAccountMapper.updateWeixinAccount(weixinAccount);
	}
	
	@Override
	public void  deleteWeixinAccount(WeixinAccount weixinAccount) throws Exception{
		 //cacheService.delWxaccount(weixinAccount.getAccountnumber());
	     weixinAccountMapper.deleteWeixinAccount(weixinAccount);
	}
	
	@Override
	public void  deleteWeixinAccountByIds (String[] ids){
	    weixinAccountMapper.deleteWeixinAccountByIds(ids); 
	}
	
	@Override
	public void insertSelective(WeixinAccount weixinAccount){
		try {
			//cacheService.setWxaccount(weixinAccount.getAccountnumber(), JSONObject.toJSONString(weixinAccount));
		} catch (Exception e) {
			e.printStackTrace();
		}
		weixinAccountMapper.insertSelective(weixinAccount);
	}
	
	@Override
	public void updateByPrimaryKeySelective(WeixinAccount weixinAccount) throws Exception{
		 //cacheService.setWxaccount(weixinAccount.getAccountnumber(), JSONObject.toJSONString(weixinAccount));
		 weixinAccountMapper.updateByPrimaryKeySelective(weixinAccount);
	}
	
}