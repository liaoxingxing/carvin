package com.hlpfwx.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping(value = "/weixinMapping")
public class WeixinMappingController {
	/*
	@Autowired
	private DlUserService dlUserService;
	
	@Autowired
	private DlAccoutGoodsService dlAccoutGoodsService;
	
	@Autowired
	private DlOrderInfoService dlOrderInfoService;
	
	//微信保存个人信息
	@RequestMapping("/infoperfectsave")
	@ResponseBody
	public String infoperfectsave(DlUser dlUser,ModelMap modelMap) {
		dlUserService.updateByPrimaryKeySelective(dlUser);
		return "sucess";
	}
	
	//购物车
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBuyCar")
	@ResponseBody
	public Map<String, Object> getBuyCar(HttpServletRequest request) {
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		Map<String, Object> tempcart = ConstSM.getBuyCar(dlUser.getMobile());
		Map<String, Object> data = new HashMap<String, Object>();
		if(tempcart!=null&&!tempcart.isEmpty()){
			Map<Integer, DlOrderGoods> getBuyCar = (Map<Integer, DlOrderGoods>)tempcart.get("dlOrderInfo");
			Double [] buycarinfo = getcartcountandamt(getBuyCar);
			data.put("cartcount", buycarinfo[2]);
			data.put("cartamount", buycarinfo[1]);
			data.put("cartlist", getBuyCar);
		}else{
			data.put("cartcount", 0);
			data.put("cart_amount", 0);
			data.put("goods_number",0);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", "0");
        resultMap.put("message", "注册成功");
        resultMap.put("data", data);
		return resultMap;
	}
	

	@RequestMapping(value = "/goodcatlist")
	@ResponseBody
	public Map<String, Object> goodcatlist(String key,HttpServletRequest request,ModelMap modelMap){
		//获取分类列表
		DlZhsmaccout dlZhsmaccout= ConstSM.getsmaccounts().get(key);
		Map<String, DlGoodsCat> catlist = ConstSM.getGoodscats(dlZhsmaccout.getId());
		request.getSession().setAttribute("cartlist", catlist);
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", "0");
        resultMap.put("message", "注册成功");
        resultMap.put("data", catlist);
		return resultMap;
	}
	
	//进入我的订单
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getgoodlist")
	@ResponseBody
	public Map<String, Object> getgoodlist(HttpServletRequest request,ModelMap modelMap){
		
		DlAccoutGoods dag = new DlAccoutGoods();
		if(2==NumberUtils.toInt(request.getParameter("catgrade"))){
			dag.setSmcatpid(request.getParameter("cat_id"));
		}else{
			dag.setSmcatid(request.getParameter("cat_id"));
		}
		
		Page page = new Page();
		page.setCurrentPage(NumberUtils.toInt(request.getParameter("page")));
		page.setShowCount(12);
		dag.setPage(page);
		List<DlAccoutGoods> goodslist = dlAccoutGoodsService.listPageDlAccoutGoods(dag);
		Map<String,DlAccoutGoods> tempmap  = new HashMap<String, DlAccoutGoods>();
		for (DlAccoutGoods dlAccoutGoods : goodslist) {
			tempmap.put(dlAccoutGoods.getZhsmgoodsid(), dlAccoutGoods);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("goodslistmap", tempmap);
		
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		Map<String, Object> tempcart = ConstSM.getBuyCar(dlUser.getOpenid());
		Map<Integer, DlOrderGoods> getBuyCar = new HashMap<Integer, DlOrderGoods>();
		if(tempcart!=null&&!tempcart.isEmpty()){
			getBuyCar = (Map<Integer, DlOrderGoods>)tempcart.get("dlOrderInfo");
		}
		
		data.put("cartlist", getBuyCar);
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", "0");
        resultMap.put("message", "注册成功");
        resultMap.put("data", data);
		return resultMap;
	}
	
	public Double[] getcartcountandamt(Map<Integer, DlOrderGoods> getBuyCar){
		double cart_amount =0d;
		int cartcount =0;
		int goods_number =0;
		for(Map.Entry<Integer, DlOrderGoods> entry:getBuyCar.entrySet()){   
		     System.out.println(entry.getKey()+"--->"+entry.getValue());
		     DlOrderGoods og = getBuyCar.get(entry.getKey());
		     cart_amount += Tools.dec2Doub(og.getGoodsPrice().multiply(new BigDecimal(og.getGoodsNum()))) ;
		     goods_number += og.getGoodsNum();
		     cartcount += 1;
		} 
		Double [] result= {Double.valueOf(cartcount),cart_amount,Double.valueOf(goods_number)};
		return result;
	}
	
	//合并choiceunit方法
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addcart")
	@ResponseBody
	public Map<String, Object> addcart(HttpServletRequest request,ModelMap modelMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		Integer cat_pid= NumberUtils.toInt(request.getParameter("cat_pid"));
		Integer unitgrade= NumberUtils.toInt(request.getParameter("unitgrade"));
		Integer vnumber= NumberUtils.toInt(request.getParameter("vnumber"));
		Integer goodsId= NumberUtils.toInt(request.getParameter("goodsId"));
		String dlWarehouseGoodsstr= request.getParameter("goodsinfo");
		
		Map<String, Object> getBuyCarInfo = (Map<String, Object>)ConstSM.getBuyCar(dlUser.getMobile());
		Map<Integer, DlOrderGoods> getBuyCar = new HashMap<Integer, DlOrderGoods>();
		
		if(!StringUtils.isEmpty(dlWarehouseGoodsstr)){
			DlWarehouseGoods dlWarehouseGoods = JSONObject.parseObject(dlWarehouseGoodsstr, DlWarehouseGoods.class);
			int unitgoods_number =0;
			if(unitgrade==2){
				unitgoods_number = dlWarehouseGoods.getUnitgoodnum1();
				if(dlWarehouseGoods.getUnitgoodprice1().compareTo(BigDecimal.ZERO)<1){
					resultMap.put("status", "-1");
			        resultMap.put("message", "商品价格无效!");
			        return resultMap;
				}
			}else{
				unitgoods_number = 1;
				if(dlWarehouseGoods.getDealerPrice().compareTo(BigDecimal.ZERO)<1){
					resultMap.put("status", "-1");
			        resultMap.put("message", "商品价格无效!");
			        return resultMap;
				}
				
				*//**删除最低5件起购限制
				 if(Arrays.asList(Const.WHITE_CATLIST).contains(cat_pid)&&unitgrade==1){
					 if(vnumber<5)vnumber=5;
					}
				 *//*
			}
			
			if(dlWarehouseGoods.getGoodsNumber()<(vnumber*unitgoods_number)){
				resultMap.put("status", "-1");
		        resultMap.put("message", "库存不够了!");
		        return resultMap;
			}
			
			DlOrderGoods og = new DlOrderGoods();
			og.setGoodsNum(vnumber);
			og.setUnit(unitgrade==2?dlWarehouseGoods.getUnit1():dlWarehouseGoods.getUnit());
			og.setGoodsPrice(unitgrade==2?dlWarehouseGoods.getUnitgoodprice1():dlWarehouseGoods.getDealerPrice());
			
			if(getBuyCarInfo!=null&&!getBuyCarInfo.isEmpty()){
				getBuyCar = (Map<Integer, DlOrderGoods>)getBuyCarInfo.get("dlOrderInfo");
				getBuyCar.put(dlWarehouseGoods.getGoodsId(), og);
			}else{
				Map<String, Object> orderinfo = new HashMap<String, Object>();
				getBuyCar.put(dlWarehouseGoods.getGoodsId(), og);
				orderinfo.put("dlOrderInfo", getBuyCar);
				ConstSM.SetBuyCar(dlUser.getMobile(), orderinfo);
				getBuyCarInfo = (Map<String, Object>)ConstSM.getBuyCar(dlUser.getMobile());
			}
		}else{
			getBuyCar = (Map<Integer, DlOrderGoods>)getBuyCarInfo.get("dlOrderInfo");
			DlOrderGoods og = getBuyCar.get(goodsId);
			og.setGoodsNum(vnumber);
			if(vnumber>0){
				getBuyCar.put(goodsId, og);
			}else{
				getBuyCar.remove(goodsId);
			}
		}
		
		Double []buycarinfo =getcartcountandamt(getBuyCar);
		getBuyCarInfo.put("cartcount", buycarinfo[0]);
		getBuyCarInfo.put("cartamount", buycarinfo[1]);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("cartcount",buycarinfo[2]);
		data.put("cartamount", buycarinfo[1]);
		data.put("goods_count", vnumber);
        resultMap.put("status", "0");
        resultMap.put("message", "请求成功");
        resultMap.put("data", data);
		//Map<Integer, DlOrderGoods> getBuyCar = (Map<Integer, DlOrderGoods>)Const.getBuyCar(dlUser.getMobile()).get("dlOrderInfo");
		return resultMap;
	}
	
	@RequestMapping("/clearcart")
	@ResponseBody
	public Map<String, Object> clearcart(HttpServletRequest request) {
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		Map<String, Object> tempcart = ConstSM.getBuyCar(dlUser.getMobile());
		Map<String, Object> data = new HashMap<String, Object>();
		if(tempcart!=null&&!tempcart.isEmpty()){
			tempcart.remove(dlUser.getMobile());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status", "0");
        resultMap.put("message", "请求成功");
        resultMap.put("data", data);
		return resultMap;
	}
	
	@RequestMapping("/orderinfo")
	public String orderinfo(HttpServletRequest request,ModelMap modelMap) throws Exception{
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		modelMap.addAttribute("dlUser", dlUser);
		return "wxjsp/order/order";
	}
	
	@RequestMapping("/tosearchGoods")
	public String tosearchGoods(HttpServletRequest request,ModelMap modelMap) throws Exception{
		DlUser dlUser =(DlUser)request.getSession().getAttribute(Const.SESSION_DLUSER);
		modelMap.addAttribute("openid", request.getParameter("openid"));
		modelMap.addAttribute("dlUser", dlUser);
		return "wxjsp/dealer/search-goods";
	}
	
	@RequestMapping("/submitOrder")
	@ResponseBody
	public Map<String, Object> submitOrder(HttpServletRequest request,ModelMap modelMap) throws Exception{
		return dlOrderInfoService.insertwxDlOrder(request);
	}*/
	
	@RequestMapping("/listOrder")
	public String listOrder(HttpServletRequest request,ModelMap modelMap) throws Exception{
		return "wxjsp/order/lists";
	}
}
