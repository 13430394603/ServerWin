package com.serverwin.reci;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.DownLineResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.main.MessageFactory;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessageDownLine 
 * @Description: TODO(处理注销请求注销) 
 * @author 威 
 * @date 2017年5月27日 下午11:18:45 
 *
 */
public class MessageDownLine implements MessageType {
	private static MessageDownLine message = new MessageDownLine() ;
	public static MessageDownLine newInstants(){
		return message ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		SQLOperation jdbc = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		String now_code = "" ;
		//返回信息主体 state 
		CrateSendMessage cremsg = new CrateSendMessage() ;
		cremsg.setFrom(messageAnaly.getTo());
		cremsg.setTo(messageAnaly.getFrom());
		cremsg.setType(messageAnaly.getType());
		cremsg.setDate(TimeUtil.getDatetime()) ;
		UserConnPoll pool = UserConnPoll.newInstants();
		if(jdbc.doDataOperation("UPDATE login SET state = 'downline' WHERE usercode = '"+messageAnaly.getFrom()+"'", "server")){
			//清除用户池
			System.out.println("用户池"+pool.toString()) ;
			now_code = pool.get(messageAnaly.getFrom()).getInetAddress().getHostAddress() ;
			pool.put(pool.get(messageAnaly.getFrom()).getInetAddress().getHostAddress(), pool.get(messageAnaly.getFrom())) ;
			//pool.remove(messageAnaly.getFrom(), pool.get(messageAnaly.getFrom())) ;
			System.out.println("用户池"+pool.toString()) ;
			cremsg.setContent("{state:true}") ;
			DownLineResponse.newInstants().doResponse(pool.get(now_code), cremsg) ;
		}else{
			cremsg.setContent("{state:false,msg:注销失败}") ;
			DownLineResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), cremsg) ;
		}
		
		
	}

}
