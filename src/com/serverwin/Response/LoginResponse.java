package com.serverwin.Response;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.service.Response;

/**
 * 
 * @ClassName: LoginRequest 
 * @Description: TODO(登录) 
 * @author 威 
 * @date 2017年5月28日 下午1:36:24 
 *
 */
public class LoginResponse implements Response{
	private static LoginResponse loginRequest  = new LoginResponse() ;
	public static LoginResponse newInstants(){
		return loginRequest ;
	}
	@Override
	public void doResponse(Socket socket, CrateSendMessage Message) {
		// TODO Auto-generated method stub
		//传入一个ArrayJson对象
		//这个对象类可以生成ArrayJson也可以反向解析成集合
		
		Message.setFrom("##server##");
		Message.setType("0000");
		Message.setDate(TimeUtil.getDatetime());
		System.out.println(Message.getCompleteMessage());
		MessageFactory.newInstants().messageSend(socket, Message);
	}
	
}
