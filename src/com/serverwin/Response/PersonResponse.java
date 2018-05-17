package com.serverwin.Response ;

import java.net.Socket ;

import com.serverwin.core.CrateSendMessage ;
import com.serverwin.core.TimeUtil ;
import com.serverwin.main.MessageFactory ;
import com.serverwin.service.Response ;
/**
 * 
 * @ClassName: PersonRequest 
 * @Description: TODO(个人信息) 
 * @author 威 
 * @date 2017年5月28日 下午2:44:26 
 *
 */
public class PersonResponse implements Response{
	private static PersonResponse personRequest  = new PersonResponse() ;
	public static PersonResponse newInstants(){
		return personRequest ;
	}
	@Override
	public void doResponse(Socket sockect, CrateSendMessage Message) {
		// TODO Auto-generated method stub
		Message.setFrom("##server##") ;
		Message.setType("0002") ;
		Message.setDate(TimeUtil.getDatetime()) ;
		MessageFactory.newInstants().messageSend(sockect, Message) ;
	}

}
