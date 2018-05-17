package com.serverwin.main;

import java.net.Socket;

import com.serverwin.core.*;
import com.serverwin.service.MsgFactory;

/**
 * 
 * @ClassName: MessageFactory 
 * @Description: TODO(每一条信息都要经过信息工厂 -- 客户端) 
 * 					 (每一条信息都要从工厂发出)
 * @author 威 
 * @date 2017年5月27日 下午5:57:20 
 *
 */
public class MessageFactory implements MsgFactory{
	private static MessageFactory messageFactory = new MessageFactory() ;
	/**
	 * 获取实例化的对象
	 */
	public static MessageFactory newInstants(){
		return messageFactory ;
	}
	/**
	 * 两种方式进入信息工厂
	 * 信息进入工厂
	 */
	@Override
	public void messageCome(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}
	/**
	 * 
	 * @Title: messageCome 
	 * @Description: TODO(信息进入工厂) 
	 * @param message
	 * void
	 *
	 */
	public void messageCome(String message){
		//实例化AnalyReceMessage对象
		AnalyReceMessage messageAnaly = AnalyReceMessage.newInstans() ;
		//分析接收信息并封装成AnalyReceMessage对象
		messageAnaly.delMsg(message) ;
		//传递AnalyReceMessage对象进行处理
		dealMessage(messageAnaly) ;
	}
	/**
	 * 辨认是谁发的信息
	 */
	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		if(messageAnaly.getTo().equals("##server##")){
			//传递到服务器信息处理工厂
			ServerMessageFactory.newInstants().dealMessage(messageAnaly);
		}
		else{
			System.out.println("处理一般信息") ;
			ChatMessageFactory.newInstants().dealMessage(messageAnaly);
			//处理一般信息
		}
	}
	
	/**
	 * 
	 * @Title: messageSend 
	 * @Description: TODO(信息出工厂) 
	 * @param sokect 发送对象
	 * @param message
	 * void
	 *
	 */
	@SuppressWarnings("static-access")
	public void messageSend(Socket sokect, CrateSendMessage message){
		System.out.println("工厂发送信息"+message.getCompleteMessage()) ;
		Server server = new Server() ;
		server.write_(sokect, message.getCompleteMessage()) ;
	}
	/**
	 * 
	 * @Title: messageSend 
	 * @Description: TODO(一般用于发送离线信息) 
	 * @param sokect
	 * @param message
	 * void
	 *
	 */
	public void messageSend(Socket sokect, String message){
		System.out.println("工厂发送信息"+message) ;
		Server server = new Server() ;
		server.write_(sokect, message) ;
	}
	/*
	public static void main(String[] args){
		CrateSendMessage messageMdule = CrateSendMessage.newInstans() ;
		messageMdule.setFrom("13430394603");
		messageMdule.setTo("##server##");
		messageMdule.setType("0000");
		messageMdule.setContent("hello");
		messageMdule.setDate("2013");
		MessageFactory.newInstants().messageSend(messageMdule);
		//测试内容
		String mm = "{##from####:####123456####,##" 
				+ "##to####:######server######,##"
				+ "##type####:####0008####,##"
				+ "##content####:####hello####,##"
				+ "##date####:####2017####,##"
				+ "}" ;		
		MessageFactory.newInstants().messageCome(mm);
	}*/
	
}
