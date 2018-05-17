package com.serverwin.core;

/**
 * 
 * @ClassName: GenerateMessage 
 * @Description: TODO(生成可发送信息) 
 * @author 威 
 * @date 2017年5月27日 下午5:41:09 
 * @see 1.setFrom&nbsp设置发送人 <br>
 * 		2.setTo&nbsp设置接收人 <br>
 * 		3.setType&nbsp设置信息类型 <br>
 * 		4.setContent&nbsp设置信息主体 <br>
 * 		5.setDate&nbsp设置信息发送时间
 *
 */
public class CrateSendMessage{
	private String fromUserName = "" ;
	private String toUserName = "" ;
	private String messageType = "" ;
	private String content = "" ;
	private String date = "" ;
	private static CrateSendMessage msgFactory= new CrateSendMessage() ;
	public static CrateSendMessage newInstans(){
		return msgFactory ;
	}
	/**
	 * 
	 * 发送人 
	 * @see
	 * @param UserName
	 * void
	 *
	 */
	public void setFrom(String UserName){
		fromUserName = UserName ;
	}
	/**
	 * 
	 * 接收人 
	 * @see
	 * @param UserName
	 * void
	 *
	 */
	public void setTo(String UserName){
		toUserName = UserName ;
	}
	/**
	 * 
	 * 信息类型
	 * @see
	 * @param type
	 * void
	 *
	 */
	public void setType(String type){
		messageType = type ;
	}
	/**
	 * 
	 * 信息主体
	 * @see
	 * @param content
	 * void
	 *
	 */
	public void setContent(String content){
		this.content = content ;
	}
	/**
	 * 
	 * 信息发送时间 
	 * @see
	 * @param dateString
	 * void
	 *
	 */
	public void setDate(String dateString){
		date = dateString ;
	}
	public String getCompleteMessage(){
		//为了防止意外发生还需要过滤掉相关的信息--保证信息的可靠性
		return "{##from####:####"+fromUserName+"####,##" 
		+ "##to####:####"+toUserName+"####,##"
		+ "##type####:####"+messageType+"####,##"
		+ "##content####:####"+content+"####,##"
		+ "##date####:####"+date+"####,##"
		+ "}" ;
	}
	/**
	 * 
	 * @Title: getCompleteMessage 
	 * @Description: TODO(将AnalyReceMessage对象转换成符合发送的字符串) 
	 * @param anly
	 * @return
	 * String
	 *
	 */
	
	public String getCompleteMessage(AnalyReceMessage anly){
		//为了防止意外发生还需要过滤掉相关的信息--保证信息的可靠性
		return "{##from####:####"+anly.getFrom()+"####,##" 
		+ "##to####:####"+anly.getTo()+"####,##"
		+ "##type####:####"+anly.getType()+"####,##"
		+ "##content####:####"+anly.getContent()+"####,##"
		+ "##date####:####"+anly.getDate()+"####,##"
		+ "}" ;
	}
	/**
	 * 
	 * 直接把AnalyReceMessage格式的信息转换成CrateSendMessage格式信息
	 * @see
	 * @param anly
	 * @return
	 * CrateSendMessage
	 *
	 */
	public static CrateSendMessage doAlymsgChageCremesg(AnalyReceMessage anly){
		CrateSendMessage creaMsg = new CrateSendMessage() ;
		creaMsg.setFrom(anly.getFrom());
		creaMsg.setTo(anly.getTo());
		creaMsg.setType(anly.getType());
		creaMsg.setContent(anly.getContent());
		creaMsg.setDate(anly.getDate());
		return creaMsg;
	} 
	public static void main(String[] args){
		
	}
}
