package com.serverwin.core;

/**
 * 
 * @ClassName: DelMessage 
 * @Description: TODO(解析收到的信息) 
 * @author 威 
 * @date 2017年5月27日 下午5:14:05 
 *
 */
public class AnalyReceMessage{
	private String fromUserName = "" ;
	private String toUserName = "" ;
	private String messageType = "" ;
	private String content = "" ;
	private String date = "" ;
    private static AnalyReceMessage delMessage = new AnalyReceMessage() ;
	public static AnalyReceMessage newInstans(){
		return delMessage ;
	}
	//处理接收信息
	public void delMsg(String message){
		fromUserName = delMsgg(message, "from") ;
		toUserName = delMsgg(message, "to") ;
		messageType = delMsgg(message, "type") ;
		content = delMsgg(message, "content") ;
		date = delMsgg(message, "date") ;
	}
	private String delMsgg(String message, String type){
		int startIndex = message.indexOf("##"+type+"##") + ("##"+type+"####:####").length() ;
		int endIndex = message.indexOf("##,##", startIndex) - "##".length() ;
		if(startIndex!=endIndex){
			return message.substring(startIndex, endIndex) ;
		}
		return null ;
	}
	/**
	 * 
	 * 获取来信息人
	 * @see
	 * @return
	 * String
	 *
	 */
	public String getFrom(){
		return fromUserName ;
	}
	/**
	 * 
	 * 获取接收人
	 * @see
	 * @return
	 * String
	 *
	 */
	public String getTo(){
		return toUserName ;
	}
	/**
	 * 
	 * 获取信息的类型 
	 * @see
	 * @return
	 * String
	 *
	 */
	public String getType(){
		return messageType ;
	}
	/**
	 * 
	 * 获取主体 
	 * @see
	 * @return
	 * String
	 *
	 */
	public String getContent(){
		return content ;
	}
	/**
	 * 
	 * 获取信息的发送时间 
	 * @see
	 * @return
	 * String
	 *
	 */
	public String getDate(){
		return date ;	
	}
	public static void main(String[] args){
		String msg = "{##from####:####12356####,##" 
			+ "##to####:####111111####,##"
			+ "##type####:####0008####,##"
			+ "##content####:####hello####,##"
			+ "##date####:####2017####,##"
			+ "}" ;
		CrateSendMessage.newInstans().setFrom("5555555");
		CrateSendMessage.newInstans().setTo("666666");
		CrateSendMessage.newInstans().setType("8888");
		CrateSendMessage.newInstans().setContent("");
		CrateSendMessage.newInstans().setDate("");
		System.out.println(CrateSendMessage.newInstans().getCompleteMessage()) ;
		AnalyReceMessage.newInstans().delMsg(CrateSendMessage.newInstans().getCompleteMessage()) ;
		System.out.println(AnalyReceMessage.newInstans().getContent()) ;
		System.out.println(AnalyReceMessage.newInstans().getDate()) ;
		System.out.println(AnalyReceMessage.newInstans().getFrom()) ;
	}	
}
