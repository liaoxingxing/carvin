package com.hlpfwx.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hlpfwx.service.WinxinService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/weixin")
public class WeixinController {
	@Autowired
	private WinxinService winxinService;
	/*
	@Autowired
	private DlUserService dlUserService;
	
	@Autowired
	private DlGoodsCatService dlGoodsCatService;*/
	
	//微信登陆页面
	@RequestMapping("/login")
	public String login(HttpServletRequest request,ModelMap modelMap) throws Exception{
		return "wxjsp/user/login";
	}
	
	//微信登陆页面
	/*@RequestMapping("/logintopage")
	public String logintopage(DlUser dlUser,HttpServletRequest request,ModelMap modelMap) throws Exception{
		if(StringUtils.isNotEmpty(dlUser.getMobile())){
			dlUser.setOpenid((String)request.getSession().getAttribute(Const.DLUSER_OPENID));
			dlUserService.insertSelective(dlUser);
			request.getSession().setAttribute(Const.SESSION_DLUSER,dlUser);
		}else{
			dlUser=(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		}
		modelMap.addAttribute("dlUser", dlUser);
		return "wxjsp/user/infoperfect";
	}*/
	
	//微信授权接口
	@RequestMapping(value = "/codeweixinaccessinfo", method = RequestMethod.GET)
	@ResponseBody
	public String validSignature(HttpServletRequest request){
		return request.getParameter("echostr");
	}

	//微信接收参数接口
	@RequestMapping(value = "/codeweixinaccessinfo", method = RequestMethod.POST,produces = "application/xml;charset=UTF-8")
	@ResponseBody
	public String accessPostData(HttpServletRequest request){
		//TODO 校验信息的安全性
		return winxinService.processRequest(request);
	}
	
	//获取短信验证码
	@RequestMapping(value = "/codegetValidateCode")
	@ResponseBody
	public String getValidateCode(String mobile){
		return winxinService.getValidateCode(mobile, null);
	}
	
	//进入个人中心
	@RequestMapping(value = "/ucenter")
	public String ucenter(String mobile,HttpServletRequest request,ModelMap modelMap){
		/*DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		modelMap.addAttribute("dlUser", dlUser);*/
		return "wxjsp/user/ucenter";
	}
	
	@RequestMapping(value = "/goodlist")
	public String goodlist(String key,HttpServletRequest request,ModelMap modelMap){
		/*DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		//获取分类列表
		DlZhsmaccout dlZhsmaccout= ConstSM.getsmaccounts().get(key);
		Map<String, DlGoodsCat> catlist = ConstSM.getGoodscats(dlZhsmaccout.getId());
		modelMap.addAttribute("catlist", catlist);
		modelMap.addAttribute("dlUser", dlUser);*/
		return "wxjsp/dealer/goodslist";
	}
	
}
