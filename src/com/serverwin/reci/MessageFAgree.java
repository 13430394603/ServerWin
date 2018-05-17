package com.serverwin.reci;

import java.util.List;
import java.util.Map;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.AlowFreResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.ArrayJson;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.TimeUtil;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessageFAgree 
 * @Description: TODO(对方是否同意被添加好友请求 -- 返回) 
 * @author 威 
 * @date 2017年5月27日 下午11:15:45 
 *
 */
public class MessageFAgree implements MessageType {
	private static MessageFAgree messageFAgree = new MessageFAgree () ;
	public static MessageFAgree newInstants(){
		return messageFAgree;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}

	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		ArrayJson json = new ArrayJson() ;//创建ArrayJson格式类型的信息对象
		json.dealMessage(messageAnaly.getContent()) ; //对AnalyReceMessage类型信息主体进行解析
		
		//ArrayJson jsonSend = new ArrayJson() ;//创建ArrayJson格式类型的信息对象 -- 用于发送
		ArrayJson jsonSend = null ;
		ArrayJson jsonSend1 = new ArrayJson() ;//被添加方的ArrayJson信息格式
		
		SQLOperation opar = new SQLOperation("root", "123456", DataBaseFormat.MySql) ; //创建数据库操作类
		
		if(json.get("state").equals("true")){//被添加方同意对方的好友请求
			jsonSend = setJsonData(opar, messageAnaly) ;//获取主动添加方ArrayJson格式信息
			
			jsonSend1 = setJson1Data(opar, json) ;//被添加方
			
			CrateSendMessage msgMdule1 = new CrateSendMessage() ;//创建被动方CrateSendMessage格式的发送信息
			msgMdule1.setTo(json.get("user")) ;
			msgMdule1.setContent(jsonSend1.getMessage()) ;
			msgMdule1.setDate(TimeUtil.getDatetime()) ;
			AlowFreResponse.newInstants().doResponse(UserConnPoll.newInstants().get(messageAnaly.getFrom()), msgMdule1) ;//发送至被添加方
			
			insertFreTable(opar, messageAnaly, json) ;//插入数据库 -- 保存
		}else{
			//拒绝添加
			jsonSend = setJsonData_F(opar, messageAnaly) ;
			System.out.println("拒绝添加好友") ;
		}
		//返回主动方
		CrateSendMessage msgMdule = new CrateSendMessage() ;//创建主动方CrateSendMessage格式的发送信息
		msgMdule.setTo(json.get("active_side")) ;
		msgMdule.setContent(jsonSend.getMessage()) ;
		msgMdule.setDate(TimeUtil.getDatetime()) ;
		//判断主动交友方是否在线 -- 否则存为离线信息
		String userName = json.get("active_side") ; //主动方用户名
		if(UserConnPoll.newInstants().isExist(userName)) {
			//在线
			System.out.println("在线") ;
			AlowFreResponse.newInstants().doResponse(UserConnPoll.newInstants().get(userName), msgMdule) ;
		}else{
			//离线 -- 放置离线消息
			System.out.println("离线") ;
			opar.doDataOperation("INSERT INTO dm"+userName+"(downMessage) VALUE('"+msgMdule.getCompleteMessage()+"');", "server") ;
		}
	}
	/**
	 * dealMessage方法分步完成
	 * 
	 * 添加成功时主动方信息设置
	 * setJsonData(SQLOperation opar, AnalyReceMessage messageAnaly)
	 * 添加失败时主动方的信息设置
	 * setJsonData_F(SQLOperation opar, AnalyReceMessage messageAnaly)
	 * 添加成功时被动方信息设置
	 * setJson1Data(SQLOperation opar, ArrayJson json)
	 * 将主动方和被动方的信息互相插入对方的好友信息表中
	 * insertFreTable(SQLOperation opar, AnalyReceMessage messageAnaly, ArrayJson json)
	 */
	
	/**
	 * 
	 * 添加成功时主动方信息设置
	 * @see
	 * @param opar
	 * @param messageAnaly
	 * @return
	 * ArrayJson
	 *
	 */
	public ArrayJson setJsonData(SQLOperation opar, AnalyReceMessage messageAnaly){
		ArrayJson jsonSend = new ArrayJson() ;
		//查询注册信息
		String sql = "SELECT usercode,Aname,email FROM register WHERE usercode='"+messageAnaly.getFrom()+"'" ;
		//查询特定用户状态
		String sql1 = "SELECT state FROM login WHERE usercode='"+messageAnaly.getFrom()+"'";
		List<Map<String, Object>> lists = opar.doQuery("server", sql, "usercode", "Aname","email") ;
		
		jsonSend.put("result", "true") ;
		jsonSend.put("user", (String) lists.get(0).get("usercode")) ;
		jsonSend.put("Aname", (String) lists.get(0).get("Aname")) ;
		jsonSend.put("email", (String) lists.get(0).get("email")) ;
		jsonSend.put("state", (String) opar.doQuery("server", sql1, "state").get(0).get("state")) ;
		return jsonSend ;
	}
	/**
	 * 
	 * 添加失败时主动方的信息设置
	 * @see
	 * @param opar
	 * @param messageAnaly
	 * @return
	 * ArrayJson
	 *
	 */
	public ArrayJson setJsonData_F(SQLOperation opar, AnalyReceMessage messageAnaly){
		ArrayJson jsonSend = new ArrayJson() ;
		String sql = "SELECT usercode,Aname FROM register WHERE usercode='"+messageAnaly.getFrom()+"'" ;
		List<Map<String, Object>> lists = opar.doQuery("server", sql, "usercode", "Aname") ;
		jsonSend.put("result", "false") ;
		jsonSend.put("user", (String)lists.get(0).get("usercode")) ;
		jsonSend.put("Aname", (String)lists.get(0).get("Aname")) ;
		jsonSend.put("msg", "对方拒绝你的好友请求") ;
		return jsonSend ;
	}
	/**
	 * 
	 * 添加成功时被动方信息设置
	 * @see
	 * @param opar
	 * @param json
	 * @return
	 * ArrayJson
	 *
	 */
	public ArrayJson setJson1Data(SQLOperation opar, ArrayJson json){
		ArrayJson jsonSend = new ArrayJson() ;
		String sql1 = "SELECT usercode,Aname,email FROM register WHERE usercode='"+json.get("active_side")+"'" ;
		String sql2 = "SELECT state FROM login WHERE usercode='"+json.get("active_side")+"'" ;
		List<Map<String, Object>> lists1 = opar.doQuery("server", sql1, "usercode", "Aname","email") ;
		//自己是同意方 - -
		jsonSend.put("result", "self") ;
		jsonSend.put("user", (String) lists1.get(0).get("usercode")) ;
		jsonSend.put("Aname", (String) lists1.get(0).get("Aname")) ;
		jsonSend.put("email", (String) lists1.get(0).get("email")) ;
		List<Map<String, Object>> lists2 = opar.doQuery("server", sql2, "state") ;
		jsonSend.put("state", (String) lists2.get(0).get("state")) ;
		return jsonSend ;
	}
	/**
	 * 
	 * 将主动方和被动方的信息互相插入对方的好友信息表中
	 * @see
	 * @param opar 
	 * @param messageAnaly
	 * @param json
	 * @return
	 * boolean
	 *
	 */
	public void insertFreTable(SQLOperation opar, AnalyReceMessage messageAnaly, ArrayJson json){
		String sql1 = "INSERT INTO fre"+messageAnaly.getFrom()+"(usercode) VALUE('"+json.get("active_side")+"')" ; 
		String sql2 = "INSERT INTO fre"+json.get("active_side")+"(usercode) VALUE('"+messageAnaly.getFrom()+"')" ;
		if(opar.doDataOperation(sql1, "server")){
			System.out.println("被动方保存信息成功") ;
			if(opar.doDataOperation(sql2, "server"))
			System.out.println("主动方保存信息成功") ;
			else System.out.println("主动方保存信息失败") ;
		}
		System.out.println("被动方保存信息失败") ;
	}
	public static void main(String[] args){
		String str = "INSERT INTO fre88(usercode) VALUE('99')" ;
		SQLOperation opar = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		if(opar.doDataOperation(str, "server")){
			System.out.println("ok") ;
		}
		else{
			System.out.println("no") ;
		}
	}
}
