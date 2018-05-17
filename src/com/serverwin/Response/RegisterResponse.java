package com.serverwin.Response;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;

/**
 * 
 * @ClassName: RegisterRequest 
 * @Description: TODO(注册请求) 
 * @author 威 
 * @date 2017年5月28日 下午1:40:57 
 *
 */
public class RegisterResponse implements Response{
	private static RegisterResponse registerRequest  = new RegisterResponse() ;
	public static RegisterResponse newInstants(){
		return registerRequest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		Message.setFrom("##server##") ;
		Message.setType("0001") ;
		Message.setDate(TimeUtil.getDatetime()) ;
		MessageFactory.newInstants().messageSend(sockect, Message) ;
	}
	
}