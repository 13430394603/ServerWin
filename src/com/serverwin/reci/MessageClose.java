package com.serverwin.reci;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.DownLineResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

public class MessageClose implements MessageType {
	private static MessageClose message = new MessageClose() ;
	public static MessageClose newInstants(){
		return message ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		// TODO Auto-generated method stub
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		System.out.println("关闭程序") ;
		SQLOperation jdbc = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		System.out.println(messageAnaly.getFrom()) ;
		if(jdbc.doDataOperation("UPDATE login SET state = 'downline' WHERE usercode = '"+messageAnaly.getFrom()+"'", "server")){
			System.out.println("关闭成功") ;
		}
		else{
			System.out.println("失败") ;
		}
		UserConnPoll pool = UserConnPoll.newInstants() ;
		pool.put(messageAnaly.getFrom(), null);
		System.out.println(pool.toString()) ;
	}
}
