package com.serverwin.core;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;

/**
 * 
 * @ClassName: CloseRequest 
 * @Description: TODO(程序退出请求) 
 * @author 威 
 * @date 2017年5月28日 下午1:45:50 
 *
 */
public class CloseResponse implements Response{
	private static CloseResponse closeRequest  = new CloseResponse() ;
	public static CloseResponse newInstants(){
		return closeRequest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		Message.setFrom("##server##");
		Message.setType("0007");
		Message.setDate("");
		MessageFactory.newInstants().messageSend(sockect, Message);
	}
	
}
