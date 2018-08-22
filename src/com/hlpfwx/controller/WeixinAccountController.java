package com.hlpfwx.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hlpfwx.entity.WeixinAccount;
import com.hlpfwx.service.WeixinAccountService;

@Controller
@RequestMapping(value = "/weixinAccount")
public class WeixinAccountController {

	@Autowired
	private WeixinAccountService weixinAccountService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

	/**
	 * 显示列表
	 * @param weixin_account
	 * @return
	 */
	@RequestMapping("list")
	public String list(WeixinAccount weixinAccount,ModelMap modelMap) {
		
		List<WeixinAccount> weixinAccountList = 
		weixinAccountService.listPageWeixinAccount(weixinAccount);
		modelMap.addAttribute("weixinAccountList", weixinAccountList);
		modelMap.addAttribute("weixinAccount", weixinAccount);
		return "weixinAccount/WeixinAccountList";
	}
	
	/**
	 * 请求编辑页面
	 * @param weixinAccountId
	 * @return
	 */
	@RequestMapping(value = "/load")
	public String load( WeixinAccount weixinAccount,ModelMap modelMap) {
	 weixinAccount = weixinAccountService.getWeixinAccountById(weixinAccount.getId());
		modelMap.addAttribute("weixinAccount", weixinAccount);
		
		return "weixinAccount/WeixinAccountInfo";
	}

	/**
	 * 保存店铺信息
	 * 
	 * @param Equipment
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(WeixinAccount weixinAccount,ModelMap modelMap) {
	    try {
			if (weixinAccount.getId() == null || weixinAccount.getId().intValue() == 0) {
				weixinAccountService.insertSelective(weixinAccount);
			} else {
				weixinAccountService.updateByPrimaryKeySelective(weixinAccount);
			}
		    modelMap.addAttribute("success","success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "system/save_result";
	}
	
	/**
	 * 查看详情
	 * @param  weixinAccountId
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(WeixinAccount weixinAccount ,ModelMap modelMap) throws Exception {
		weixinAccountService.deleteWeixinAccount(weixinAccount);
		return "success";
	}
}
