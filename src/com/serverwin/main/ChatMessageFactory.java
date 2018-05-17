package com.serverwin.main;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.reci.MessageChat;
import com.serverwin.service.MsgFactory;
/**
 * 
 * @ClassName: FriendMessageFactory 
 * @Description: TODO(好友信息接收处理工厂) 
 * @author 威 
 * @date 2017年5月27日 下午9:52:59 
 *
 */
public class ChatMessageFactory implements MsgFactory {
	private static ChatMessageFactory friendmessage = new ChatMessageFactory() ;
	/**
	 * 
	 * @Title: newInstants 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @return
	 * FriendMessageFactory
	 *
	 */
	public static ChatMessageFactory newInstants(){
		return friendmessage ;
	}
	/**
	 * 信息进入
	 */
	public void messageCome(AnalyReceMessage messageAnaly){
		dealMessage(messageAnaly) ;
	}
	/**
	 * 处理进入信息
	 */
	public void dealMessage(AnalyReceMessage messageAnaly){
		if(messageAnaly.getType().equals("0008")){
			MessageChat.newInstants().reciMessage(messageAnaly);
			System.out.print("聊天信息") ;
		}else if(messageAnaly.getType().equals("0005")){
			MessageChat.newInstants().reciMessage(messageAnaly);
			System.out.print("添加好友信息") ;
		}
		else{
			//将错误信息秘密返回给服务器，服务器记录下当前错误信息等待修补漏洞
			System.out.print("错误信息");
		}
	}
	//测试代码
	public static void main(String[] args){
		//测试内容
		ChatMessageFactory ser = ChatMessageFactory.newInstants() ;
		String mm = "{##from####:####12356####,##" 
				+ "##to####:######server######,##"
				+ "##type####:####0005####,##"
				+ "##content####:####hello####,##"
				+ "##date####:####2017####,##"
				+ "}" ;
		AnalyReceMessage msg = AnalyReceMessage.newInstans() ;
		msg.delMsg(mm);
		ser.dealMessage(msg);
	}
}
