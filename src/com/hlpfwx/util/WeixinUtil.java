package com.hlpfwx.util;

import java.io.InputStream;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.hlpfwx.entity.Article;
import com.hlpfwx.entity.MusicMessage;
import com.hlpfwx.entity.NewsMessage;
import com.hlpfwx.entity.TextMessage;
import com.hlpfwx.entity.WeixinAccount;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WeixinUtil {
    //TODO:获取失败异常通知机制
	public static String WINXIN_TOKEN = "";
	public static String getWinxinToken(WeixinAccount wa){
		if(StringUtils.isEmpty(WINXIN_TOKEN)){
			WINXIN_TOKEN = resetWeixinToken(wa);
		}
		return WINXIN_TOKEN;
	}
	
	/*public static String WINXIN_JSTOKEN = "";
	public static String getWinxinJsToken(){
		if(StringUtils.isEmpty(WINXIN_JSTOKEN)){
			WINXIN_JSTOKEN = resetjsToken();
		}
		return WINXIN_JSTOKEN;
	}*/
	
	//public static final String WINXIN_APPID="wx7b3c0d7db5f3eb35";
	//public static final String WINXIN_SECRET="6943e7b02d5cefeecb6765597e943ac2";
	
	/*public static Map<String,Watch_user> CACHEUSERMAP = new HashMap<String, Watch_user>();
	public static Map<String,Watch_user> getCacheuserMap(){
		if(CACHEUSERMAP.isEmpty()){
			Watch_userMapper wu =ApplicationContextHelper.getBean("watch_userMapper");
			List<Watch_user> wulist = wu.getAllWatch_user(new Watch_user());
			for (Watch_user watch_user : wulist) {
				CACHEUSERMAP.put(watch_user.getOpenid(), watch_user);
			}
		}
		return CACHEUSERMAP;
	}*/
	
	//请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	//请求消息类型：view
	public static final String REQ_MESSAGE_TYPE_VIEW = "VIEW";
	//请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	//请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	//请求消息类型：音频
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	//请求消息类型：推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	//事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	//事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	//事件类型：CLICK(自定义菜单点击事件)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	//事件类型：ShakearoundUserShake(摇一摇)
	public static final String EVENT_TYPE_SHAKEAROUNDUSERSHAKE = "ShakearoundUserShake";
	//事件类型：(发送经纬度) 
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	
    public static String resetWeixinToken(WeixinAccount wa){
    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
    			+ "&appid="+wa.getAccountappid()+"&secret="+wa.getAccountappsecret();
    	String token="";
		try {
			JSONObject json = JSONObject.parseObject(HttpRequestUrl.getHttpsurl(url, ""));
			if(StringUtils.isNotEmpty(json.getString("access_token"))){
				token= json.getString("access_token");
		    }else {
				System.err.println("获取微信TOKEN失败："+ json.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return token;
    }
    
    /*public static String resetjsToken(WeixinAccount wa){
    	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+wa.getAccesstoken()
    			+ "&type=jsapi";
        String token="";
		try {
			JSONObject json = JSONObject.parseObject(HttpRequestUrl.getHttpsurl(url,""));
			if(StringUtils.isNotEmpty(json.getString("ticket"))){
				token= json.getString("ticket");
		    }else {
				System.err.println("获取微信TOKEN失败："+ json.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return token;
    }*/
    
    public static String sendmessagebycs(String msg,String accesstoken){
    	msg = "{\"touser\":\"oZmTXvmtRt63ge8Kq-cqBQtV-e-s\",\"msgtype\":\"text\","
				+ "\"text\":{\"content\":\" 批发新订单通知\n订单号：\n订单金额：\n收货人：\n联系电话：\n收货地址：\n\n 请核对信息，欢迎再次购买\"}}";
    	accesstoken = "YSmjHYOxrmG_iTZZQoROXGciy3i81jzjwyIduVlsQkmpF20lg9YPEGHuaVRTEe2CU_9HqxGhcloBTL19mz8O9ukS-bs5Ee6srMmXrafOwpVQAuu_F9AIt4Lxc_2NF0oJQLFiAEAKFZ";
    	String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accesstoken;
    	String result="";
		try {
			result = HttpRequestUrl.postHttpsurl(url, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
	
    //创建微信菜单 
    /*public static void createmenu() {
    	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
		String pama = " {\"button\":[{\"type\":\"view\", \"name\":\"我要订货\",\"url\":\"http://www.ailinktech.com/\"},"
    			+ "{\"type\":\"view\", \"name\":\"关于我联\",\"url\":\"http://www.ailinktech.com/\"},"
    			+"{\"name\":\"我联巡更\",\"sub_button\":["
    			+"{\"type\":\"view\",\"name\":\"用户注册\",\"url\":\""
    				+ "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7b3c0d7db5f3eb35"
    			+ "&redirect_uri=http://wlxg.ailinktech.com/hlpfwx/weixin/login"
    			+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"},"
    			+ "{\"type\":\"click\",\"name\":\"巡更报警\",\"key\":\"alertxg\"}"
    			+ "]}]}";
    	url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+getWinxinToken();
		String aa="";
		try {
			aa = HttpRequestUrl.postHttpsurl(url,pama);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("aa== " + aa);
	}*/
    
    public static void createmenu(WeixinAccount wa) {
		String pama = " {\"button\":[{\"type\":\"view\", \"name\":\"我要订货\","
				+ "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wa.getAccountappid()
    			+ "&redirect_uri=http://flyfish.tunnel.2bdata.com/hlpf/weixin/goodlist.do"
    			+ "&response_type=code&scope=snsapi_base&state="+wa.getAccountnumber()+"#wechat_redirect\"},"
    			+ "{\"type\":\"view\", \"name\":\"最新优惠\",\"url\":\"http://mp.weixin.qq.com/s?__biz=MzA5MDkwMjI0Nw==&mid=402186738&idx=1&sn=b55b586b2e9ea5e0575ff48aab1b572e&scene=0#wechat_redirect\"},"
    			+"{\"type\":\"view\", \"name\":\"个人中心\","
				+ "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wa.getAccountappid()
    			+ "&redirect_uri=http://flyfish.tunnel.2bdata.com/hlpf/weixin/ucenter.do"
    			+ "&response_type=code&scope=snsapi_base&state="+wa.getAccountnumber()+"#wechat_redirect\"}]}";
    			/*+"{\"name\":\"我的\",\"sub_button\":["
    			+"{\"type\":\"view\",\"name\":\"个人中心\",\"url\":\""
    				+ "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wa.getAccountappid()
    			+ "&redirect_uri=http://flyfish.tunnel.2bdata.com/hlpf/weixin/ucenter.do"
    			+ "&response_type=code&scope=snsapi_userinfo&state="+wa.getAccountnumber()+"#wechat_redirect\"},"
    			+ "{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\""
    			+ "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wa.getAccountappid()
    			+ "&redirect_uri=http://m.haolin.com/hlb/order/lists"
    			+ "&response_type=code&scope=snsapi_userinfo&state="+wa.getAccountnumber()+"#wechat_redirect\"},"
    			+ "]}]}";*/
		
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+wa.getAccountopenid();
		String aa="";
		try {
			System.out.println(pama);
			aa = HttpRequestUrl.postHttpsurl(url,pama);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("aa== " + aa);
	}
    
	/**
	 * 解析微信发来的请求（XML）
	 * @param request
	 * @throws Exception
	 */
	public static Element parseXml(HttpServletRequest request) throws Exception {
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		//InputStream inputStream= new FileInputStream("E:\\test.txt");
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		System.out.println(document.asXML());
		// 得到xml根元素
		Element root = document.getRootElement();
		// 释放资源
		inputStream.close();
		inputStream = null;
		
		return root;
	}
	
	public static void main(String[] args) {
		try {
			//System.out.println(getWinxinJsToken());
			//createmenu();
			//System.out.println(sendmessagebycs("", ""));
			resetWeixinToken(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				//对所有xml节点的转换都增加CDATA标记
				//boolean cdata = true;

				protected void writeText(QuickWriter writer, String text) {
					if (text.startsWith("nocdata-")) {
						writer.write(text.substring(8));
					} else {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}
				}
			};
		}
	});
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
