package com.serverwin.Response;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;

/**
 * 
 * @ClassName: DownLineRequest 
 * @Description: TODO(注销请求类) 
 * @author 威 
 * @date 2017年5月28日 下午1:40:34 
 *
 */
public class DownLineResponse implements Response{
	private static DownLineResponse downlienRequest  = new DownLineResponse() ;
	public static DownLineResponse newInstants(){
		return downlienRequest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		Message.setFrom("##server##");
		Message.setType("0006");
		Message.setDate(TimeUtil.getDatetime());
		MessageFactory.newInstants().messageSend(sockect, Message);
	}
	
}