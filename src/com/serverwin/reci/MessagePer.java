package com.serverwin.reci;

import java.util.List;
import java.util.Map;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.PersonResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.ArrayJson;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessagePer 
 * @Description: TODO(接收个人信息保存状态) 
 * @author 威 
 * @date 2017年5月27日 下午11:11:17 
 *
 */
public class MessagePer implements MessageType{
	private static MessagePer messagePer = new MessagePer() ;
	public static MessagePer newInstants(){
		return messagePer ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		SQLOperation jdbc = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		//保存成功
		com.serverwin.core.SSData ss = new com.serverwin.core.SSData() ;
		CrateSendMessage msgMudle = new CrateSendMessage() ;
		ArrayJson json = new ArrayJson() ;
		json.dealMessage(messageAnaly.getContent()) ;
		if(jdbc.doDataOperation("UPDATE register SET Aname = '"+json.get("Aname")+"',email = '"+json.get("email")+"' WHERE usercode = '"+messageAnaly.getFrom()+"' ;", "register")){
			msgMudle.setContent("{state:true,msg:修改成功}") ;
		}else{
			msgMudle.setContent("{state:true,msg:修改失败}") ;
		}
		msgMudle.setTo(messageAnaly.getFrom()) ;
		UserConnPoll pool = UserConnPoll.newInstants() ;
		PersonResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), msgMudle);
		//完成
	}

}
