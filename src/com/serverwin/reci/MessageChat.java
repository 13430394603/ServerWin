package com.serverwin.reci;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.main.MessageFactory;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;
/**
 * 
 * @ClassName: MessageChat 
 * @Description: TODO(接收基本的聊天信息) 
 * @author 威 
 * @date 2017年5月27日 下午11:23:40 
 *
 */
public class MessageChat implements MessageType{
	private static MessageChat messageChat = new MessageChat() ;
	public static MessageChat newInstants(){
		return messageChat ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		System.out.println("do") ;
		SQLOperation jdbc = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		//基本的聊天信息
		//获取用户名
		//查询用户池
		if(UserConnPoll.newInstants().isExist(messageAnaly.getTo())){
			//如果存在用户池中
			//使用信息工厂发送出去
			MessageFactory.newInstants().messageSend(UserConnPoll.newInstants().get(messageAnaly.getTo()),
					CrateSendMessage.doAlymsgChageCremesg(messageAnaly));
			System.out.println("发送") ;
		}else{
			System.out.println("离线缓存") ;
			CrateSendMessage msgMdule = CrateSendMessage.doAlymsgChageCremesg(messageAnaly) ;
			jdbc.doDataOperation("INSERT INTO dm"+messageAnaly.getTo()+"(downMessage) VALUE('"+msgMdule.getCompleteMessage()+"');", "server") ;
			//不存在	
			//保存到好友表中的缓存项中
			//关联数据库代码
		}
		
		
		
		
		
		
		
	}

}
