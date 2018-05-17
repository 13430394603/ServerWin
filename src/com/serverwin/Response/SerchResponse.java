package com.serverwin.Response;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;

/**
 * 
 * @ClassName: LoginRequest 
 * @Description: TODO(查找好友请求) 
 * @author 威 
 * @date 2017年5月28日 下午1:41:40 
 *
 */
public class SerchResponse implements Response{
	private static SerchResponse serchReuest  = new SerchResponse() ;
	public static SerchResponse newInstants(){
		return serchReuest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		Message.setFrom("##server##");
		Message.setType("0003");
		Message.setDate(TimeUtil.getDatetime());
		MessageFactory.newInstants().messageSend(sockect, Message);
	}
	
}
