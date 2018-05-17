package com.serverwin.reci;

import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.SerchResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.ArrayJson;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.SSData;
import com.serverwin.core.SSObject;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessageSerch 
 * @Description: TODO(接收查找好友信息处理) 
 * @author 威 
 * @date 2017年5月27日 下午11:06:43 
 *
 */
public class MessageSerch implements MessageType {
	private static MessageSerch messageSerch = new MessageSerch() ;
	public static MessageSerch newInstants(){
		return messageSerch ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		CrateSendMessage msgMudle = new CrateSendMessage() ;
		SQLOperation jdbc = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		//查找好友请求是否存在
		ArrayJson json = new ArrayJson() ;
		json.dealMessage(messageAnaly.getContent()) ;
		SSData ss = new SSData() ; 
		SSObject sobj = new SSObject() ;
		if(json.get("serch_key").equals("在线")){
			//查找在线人数
			UserConnPoll pool = UserConnPoll.newInstants() ;
			Map<String, Socket> map = pool.getAll() ;
			System.out.println(pool.toString()) ;
			//从用户连接池中找出在线用户
			if(map.size() != 0){
				for(Map.Entry<String, Socket> item : map.entrySet()){
					System.out.println(item.getKey()+"-"+item.getValue()) ;
					//防止查找到自己
					if(item.getKey().indexOf(".") == -1){
						System.out.println(item.getKey()) ; 
						List<Map<String, Object>> lists = jdbc.doQuery("server", "SELECT usercode,Aname FROM register WHERE usercode = '"+item.getKey()+"'", "usercode", "Aname") ;
						sobj.put("user", (String) lists.get(0).get("usercode")) ;
						if(((String)lists.get(0).get("Aname")).length()!=0){
							sobj.put("Aname", (String) lists.get(0).get("Aname")) ;
						}else{
							sobj.put("Aname", "小白白") ;
						}
						ss.add(sobj) ;
					}else{
						System.out.println("不符合信息") ; 
					}
				}	
			}
			System.out.println("发") ;
			msgMudle.setContent("{state:true,result:"+ss.toString()+"}") ;
		}else if(json.get("serch_key").equals("所有")){
			//查找所有与人 -- 上限十个
			List<Map<String, Object>> lists = jdbc.doQuery("server", "SELECT TOP 10 usercode,Aname FROM register", "usercode", "Aname") ;
			for(Map<String, Object> maps : lists){
				sobj.put("user", (String) maps.get("usercode")) ;
				sobj.put("Aname", (String) maps.get("Aname")) ; 
				ss.add(sobj) ; 
 			}
			System.out.println("查找所有与人 -- 上限十个") ;
			msgMudle.setContent("{state:true,result:"+ss.toString()+"}") ;
		}else{
			//精准查找
			List<Map<String, Object>> lists = jdbc.doQuery("server", "SELECT usercode,Aname FROM register WHERE usercode = '"+json.get("serch_key")+"'", "usercode", "Aname") ;
			if(lists.size() != 0){
				sobj.put("user", (String) lists.get(0).get("usercode")) ;
				sobj.put("Aname", (String) lists.get(0).get("Aname")) ;
				ss.add(sobj) ;
				msgMudle.setContent("{state:true,result:"+ss.toString()+"}") ;
				System.out.println("精准查找") ;
			}else{
				msgMudle.setContent("{state:false,msg:找不到你要查找的人，换个查找对象吧}") ;
			}
		}
		msgMudle.setTo(messageAnaly.getFrom()) ;
		UserConnPoll pool = UserConnPoll.newInstants() ;
		SerchResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), msgMudle);
	}
	public static void main(String[] args){
		String str = "{##from####:####55####,####to####:######server######,####type####:####0003####,####content####:####{serch_key:在线}####,####date####:####06/04-16:35####,##}" 
				;
		AnalyReceMessage msg = new AnalyReceMessage() ;
		msg.delMsg(str) ;
		newInstants().reciMessage(msg) ;
	}
	
}
