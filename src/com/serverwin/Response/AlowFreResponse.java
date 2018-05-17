package com.serverwin.Response;


import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;


/**
 * 
 * @ClassName: LoginRequest 
 * @Description: TODO(被允许添加请求服务器传送好友信息) 
 * @author 威 
 * @date 2017年5月28日 下午1:44:37 
 *
 */
public class AlowFreResponse implements Response{
	private static AlowFreResponse alowFreRequest  = new AlowFreResponse() ;
	public static AlowFreResponse newInstants(){
		return alowFreRequest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		Message.setFrom("##server##");
		Message.setType("0004");
		Message.setDate(TimeUtil.getDatetime());
		//调用类发送
		MessageFactory.newInstants().messageSend(sockect, Message) ;
	}
}
