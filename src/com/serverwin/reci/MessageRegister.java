package com.serverwin.reci;

import java.util.List;
import java.util.Map;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.RegisterResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.ArrayJson;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessageRegister 
 * @Description: TODO(接收处理注册信息) 
 * @author 威 
 * @date 2017年5月27日 下午10:58:36 
 *
 */
public class MessageRegister implements MessageType {
	private static MessageRegister messageRegister = new MessageRegister() ;
	public static MessageRegister newInstants(){
		return messageRegister ;
	}
	/**
	 * 承接信息
	 */
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}
	/**
	 * 信息的处理
	 */
	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		CrateSendMessage msgMudle = new CrateSendMessage() ;
		msgMudle.setTo(messageAnaly.getFrom()) ;
		//获取主体获取注册信息
		ArrayJson json = new ArrayJson() ;
		json.dealMessage(messageAnaly.getContent()) ;
		SQLOperation opar = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		//查询是否注册
		String sql = "SELECT COUNT(usercode) AS 'isExit' FROM register WHERE usercode = '"+json.get("user")+"'" ;
		List<Map<String, Object>> lists = opar.doQuery("server", sql, "isExit") ;
		if(lists.get(0).get("isExit").equals("1")){ 
			//已被注册 -- 提示用户名已被注册
			System.out.println("已注册") ;
			msgMudle.setContent("{state:false,msg:用户名已注册}") ;
		}else{
			//未被注册 -- 进行注册
			System.out.println("ok") ;
			//将注册信息插入register注册表
			if(insertRegister(opar, json)){
				String tablename = "fre" + json.get("user") ;
				// -- 创建好友信息表
				if(createFreshtable(opar, tablename)){
					msgMudle.setContent("{state:true,user:"+json.get("user")+"}") ;
					System.out.println("创建好友表ok") ;
				}
				else{
					System.out.println("创建好友表时出错") ;
					msgMudle.setContent("{state:false,msg:注册失败}") ;
				}
				//创建个人离线信息表
				if(createDownMessage(opar, json.get("user"))){
					msgMudle.setContent("{state:true,user:"+json.get("user")+"}") ;
					insertLogin(opar, json) ;
					System.out.println("创建个人离线信息表ok") ;
				}
				else msgMudle.setContent("{state:false,msg:注册失败}") ;
			} 
			else{
				msgMudle.setContent("{state:false,msg:注册失败}") ;
			}
		}
		goResponse(messageAnaly, msgMudle) ;
	}
	/**
	 * 
	 * dealMessage 数据库操作方法
	 * 
	 * 注册时将注册信息插入注册信息表中 
	 * insertRegister(SQLOperation sqlOperate, ArrayJson jsonData) 
	 * 创建好友表  -- 存放好友信息
	 * createFreshtable(SQLOperation sqlOperate, String taleName) 
	 * 创建当前注册的账号离线信息表
	 * createDownMessage(SQLOperation sqlOperate, String user) 
	 * 进行响应前端 -- 对注册的响应
	 * goResponse(AnalyReceMessage messageAnaly, CrateSendMessage msgMudle)
	 * 注册成功将信息插入登录表中
	 * insertLogin(SQLOperation sqlOperate, ArrayJson jsonData)
	 */
	
	/**
	 * 
	 * 注册时将注册信息插入注册信息表中 
	 * @see
	 * @param sqlOperate 简化数据库操作类
	 * @param jsonData 特制的数据类型
	 * @return
	 * boolean
	 *
	 */
	public boolean insertRegister(SQLOperation sqlOperate, ArrayJson jsonData){
		String userRegister = jsonData.get("user") ;
		String passRegister = jsonData.get("pass") ;
		String emailRegister =jsonData.get("email") ;
		String sqlinsert = 
				"INSERT INTO register(usercode,pass,email,Aname) VALUE("
				+ "'"+userRegister+"', '"+passRegister+"', '"+emailRegister+"', '未设置用户名');" ;
		return sqlOperate.doDataOperation(sqlinsert, "server") ;
	}
	/**
	 * 
	 * 注册成功将信息插入登录表中
	 * @see
	 * @param sqlOperate 简化数据库操作类
	 * @param jsonData 特制的数据类型
	 * @return
	 * boolean
	 *
	 */
	public boolean insertLogin(SQLOperation sqlOperate, ArrayJson jsonData){
		String userLogin = jsonData.get("user") ;
		String passLogin = jsonData.get("pass") ;
		String sql = "INSERT INTO login(usercode,password,state) VALUE('"+userLogin+"', '"+passLogin+"', 'downline')" ;
		return sqlOperate.doDataOperation(sql, "server") ;
	}
	
	/**
	 * 
	 * 创建好友表  -- 存放好友信息
	 * @see
	 * @param sqlOperate 简化数据库操作类
	 * @param taleName 数据库表名称
	 * @return
	 * boolean
	 *
	 */
	public boolean createFreshtable(SQLOperation sqlOperate, String taleName){
		String sql = "CREATE TABLE "+taleName+" (usercode VARCHAR(16) NOT NULL PRIMARY KEY)" ;
		return sqlOperate.doDataOperation(sql, "server") ;
	}
	
	/**
	 * 
	 * 创建当前注册的账号离线信息表
	 * @see
	 * @param sqlOperate 简化数据库操作类
	 * @param user 当前注册的用户名
	 * @return
	 * boolean
	 *
	 */
	public boolean createDownMessage(SQLOperation sqlOperate, String user){
		String sql = "CREATE TABLE dm"+user+" (downMessage VARCHAR(600) NULL)" ;
		return sqlOperate.doDataOperation(sql, "server") ;
	}
	
	/**
	 * 
	 * 进行响应前端 -- 对注册的响应
	 * @see
	 * @param messageAnaly
	 * @return
	 * boolean
	 *
	 */
	public boolean goResponse(AnalyReceMessage messageAnaly, CrateSendMessage msgMudle){
		boolean flag = false ;
		UserConnPoll pool = UserConnPoll.newInstants() ;
		RegisterResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), msgMudle);	
		flag = true ;
		return flag ;
	}
	
}
