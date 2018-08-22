package com.hlpfwx.service.impl;

import javax.servlet.http.HttpServletRequest;

import lib.MESSAGEXsend;

import org.springframework.stereotype.Service;

import utils.ConfigLoader;

import com.alibaba.druid.util.StringUtils;
import com.hlpfwx.service.WinxinService;
import com.hlpfwx.util.StringUtil;

import config.AppConfig;

/**
 * watch_user
 */
@Service
public class WeixinServiceImpl implements WinxinService {
	
	/**
	 * 处理微信发来的请求
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = "success";
		/*try {
			Element rootElement = WeixinUtil.parseXml(request);//xml请求解析
			String msgType = rootElement.elementText("MsgType");//消息类型
			String fromUserName = rootElement.elementText("FromUserName");
			//不处理未注册用户的操作
			//if(WeixinUtil.getCacheuserMap().get(fromUserName)==null) return "success";
				
			if (msgType.equals(WeixinUtil.REQ_MESSAGE_TYPE_TEXT)){//文本消息
				//respMessage = dealTextMessage(rootElement);
			}else if(msgType.equals(WeixinUtil.REQ_MESSAGE_TYPE_VOICE)){//音频信息
				//respMessage = dealVoiceMessage(rootElement);
			}else if(msgType.equals(WeixinUtil.REQ_MESSAGE_TYPE_EVENT)){//消息类型为Event
				String event = rootElement.elementText("Event");
				if(event.equals(WeixinUtil.EVENT_TYPE_SHAKEAROUNDUSERSHAKE)){//事件类型为摇一摇周边
					//respMessage = dealShakearoundusershake(rootElement);
				}else if(event.equals(WeixinUtil.EVENT_TYPE_CLICK)){//事件类型为自定义事件
					String eventkey = rootElement.elementText("EventKey");
					//if(eventkey.equals("alertxg")) respMessage = dealAlertxg(rootElement);
				}else if(event.equals(WeixinUtil.EVENT_TYPE_LOCATION)){//事件类型为地理信息
					//dealUserLocation(rootElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return respMessage;
	}
	
	public String getValidateCode(String phonenum,String sendContent){
		if (!StringUtils.isEmpty(phonenum)) {
			try {
				if(StringUtils.isEmpty(sendContent)){
					sendContent = StringUtil.getRandomNumString(4);
				}
				MessageXSend(phonenum, sendContent);
				return sendContent;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	void MessageXSend(String phonenum, String smscode) {
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		MESSAGEXsend submail = new MESSAGEXsend(config);
		submail.addTo(phonenum);
		submail.setProject("DfpvI2");
		submail.addVar("smscode", smscode);
		submail.addVar("etime", "5");
		submail.xsend();
	}
}