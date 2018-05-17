package com.serverwin.reci;


import java.util.List;
import java.util.Map;

import com.chen.jdbc.SQLOperation;
import com.chen.jdbcutil.DataBaseFormat;
import com.serverwin.Response.LoginResponse;
import com.serverwin.core.AnalyReceMessage;
import com.serverwin.core.ArrayJson;
import com.serverwin.core.CrateSendMessage;
import com.serverwin.core.SSData;
import com.serverwin.core.SSObject;
import com.serverwin.main.MessageFactory;
import com.serverwin.pool.UserConnPoll;
import com.serverwin.service.MessageType;

/**
 * 
 * @ClassName: MessageLogin 
 * @Description: TODO(接收处理登录信息类) 
 * @author 威 
 * @date 2017年5月27日 下午10:30:34 
 *
 */
public class MessageLogin implements MessageType{
	private static MessageLogin messageLogin = new MessageLogin() ;
	public static MessageLogin newInstants() {
		return messageLogin ;
	}
	@Override
	public void reciMessage(AnalyReceMessage messageAnaly) {
		dealMessage(messageAnaly) ;
	}
	@Override
	public void dealMessage(AnalyReceMessage messageAnaly) {
		//创建SQLOperation数据库操作对象opar
		SQLOperation opar = new SQLOperation("root", "123456", DataBaseFormat.MySql) ;
		
		//创建连接池对象
		UserConnPoll pool = UserConnPoll.newInstants() ;
		
		//json - 解析接收的主体信息
		ArrayJson json = ArrayJson.getInstance() ;
		json.dealMessage(messageAnaly.getContent());
		
		//创建ArrayJson格式的发送信息对象sendjson
		ArrayJson sendjson = ArrayJson.newInstance() ;
		
		//创建CrateSendMessage格式的发送信息对象
		CrateSendMessage cresmsg = CrateSendMessage.doAlymsgChageCremesg(messageAnaly) ;
		cresmsg.setTo(messageAnaly.getFrom()) ; //设置固定返回用户名

		if(checkLoginState(opar, json).equals("1")){//数据库验证用户名密码是否正确
			System.out.println("存在用户") ;
			String str = checkState(opar, json) ;
			System.out.println("在线状态"+str + "yya" + str.equals("online")) ;
			if(str.equals("online")) {//数据库查看该登录账号是否在其他地方登陆
				//在他地登录
				System.out.println("他地登陆") ;
				cresmsg.setContent("{state:false,msg:该账号在别地登录}") ;
				LoginResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), cresmsg) ;
			}else{//并未在他地登录 -- 登录成功返回数据
				System.out.println("登陆ok") ;
				//查询好友表
				cresmsg.setContent(repFreData (opar, json)) ;//设置CrateSendMessage格式发送信息的主体
				
				//修改个人状态 -- 为在线
				if(updateState(opar, json.get("user"))){
					System.out.println("修改在线状态成功\n" +"发送\n"+ "显示连接池状态信息"+ pool.toString()) ;
					pool.put(json.get("user"), pool.get(messageAnaly.getFrom())) ;//覆盖新的登录连接信息
					System.out.println("显示连接池状态信息"+pool.toString()) ;
					//清除原来的
					//pool.remove(messageAnaly.getFrom(), pool.get(messageAnaly.getFrom())) ;
					cresmsg.setTo(json.get("user")) ;
					System.out.println("显示连接池状态信息"+pool.toString()) ;
					////响应
					LoginResponse.newInstants().doResponse(pool.get(json.get("user")), cresmsg) ;
					System.out.println("响应的对象"+json.get("user")) ;
					System.out.println("显示连接池状态信息"+pool.toString()) ;
				}
				else{
					System.out.println("修改在线状态失败") ;
				}
				//向当前登录的客户用户端返回所有离线信息
				repDownMessage(opar, pool, json) ;
				
			}
			//json.put("frestate", "code-*-state,code-*-state");
		}else{  //密码错误 
			System.out.println("失败") ;
			//登录失败
			sendjson.put("state", "false") ;
			sendjson.put("msg", "账号或者用户名错误！") ;
			cresmsg.setContent(sendjson.getMessage()) ;
			LoginResponse.newInstants().doResponse(pool.get(messageAnaly.getFrom()), cresmsg) ;
		}	
		
	}	
	/**
	 * 
	 * dealMessage 数据库操作方法
	 * 
	 * 查询用户和密码是否正确
	 * checkLoginState(SQLOperation opar, ArrayJson jsonData)
	 * 查询是否在线 
	 * checkState (SQLOperation opar, ArrayJson jsonData)
	 * 登录成功之后返回好友数据
	 * repFreData (SQLOperation opar, ArrayJson jsonData)
	 * 修改个人状态为登录在线状态
	 * updateState(SQLOperation opar, String userName)
	 * 查找离线信息表中的离线信息并返回	
	 * repDownMessage(SQLOperation opar,UserConnPoll pool, ArrayJson jsonData) 
	 */
	
	/**
	 * 
	 * 查询用户和密码是否正确
	 * @see
	 * @param opar 简化数据库操作类
	 * @param jsonData 特制的数据类型
	 * @return
	 * String
	 *
	 */
	public String checkLoginState(SQLOperation opar, ArrayJson jsonData){
		String usercode = jsonData.get("user") ;
		String pass = jsonData.get("pass") ;
		String sql = "SELECT COUNT(usercode) AS 'isExit' FROM login WHERE usercode = '"+usercode+"' AND password = '"+pass+"'" ;
		//数据库查看该登录账号是否在其他地方登陆
		
		List<Map<String, Object>> lists = opar.doQuery("server", sql, "isExit") ;
		return (String) lists.get(0).get("isExit") ;
	}
	/**
	 * 
	 * 查询是否在线 
	 * @see
	 * @param opar 简化数据库操作类
	 * @return
	 * String
	 *
	 */
	public String checkState (SQLOperation opar, ArrayJson jsonData){
		String usercode = jsonData.get("user") ;
		String sql1 = "SELECT state FROM LOGIN WHERE usercode = '"+usercode+"'" ;
		List<Map<String, Object>> lists1 = opar.doQuery("server", sql1, "state") ;
		System.out.println("查询状态") ;
		return (String) lists1.get(0).get("state") ;
	}
	/**
	 * 
	 * 查询好友表返回好友数据 
	 * @see
	 * @param opar 简化数据库操作类
	 * @param jsonData 特制的数据类型
	 * @return
	 * String
	 *
	 */
	public String repFreData (SQLOperation opar, ArrayJson jsonData){
		String tableName = "fre" + jsonData.get("user") ;
		String sql2 = "SELECT b.usercode as 'usercode',a.state as 'state' from login a,"+tableName+" b where a.usercode = b.usercode" ;
		
		//获取好友用户名
		List<Map<String, Object>> lists2 = opar.doQuery("server", sql2, "usercode", "state") ;
		//SSData -- 编辑好友状态数据
		SSData ss = new SSData() ;
		SSObject sobj = new SSObject() ;
		//查询该用户的好友表 -- 反回该登录账号的好友状态
		if(lists2.size() != 0){
			for(Map<String, Object> maps : lists2){
				System.out.println("maps.get(usercode):"+maps.get("usercode")) ;
				sobj.put("Aname", (String) opar.doQuery("server", "SELECT Aname FROM register where usercode = '"+maps.get("usercode")+"'", "Aname").get(0).get("Aname")) ;
				for(Map.Entry<String, Object> item : maps.entrySet()){
					sobj.put(item.getKey(), (String) item.getValue()) ;
				}
				ss.add(sobj) ;
			}
		}
		ArrayJson j = ArrayJson.newInstance() ;
		j.put("state", "true") ; //返回这次请求的状态
		j.put("fre_info", ss.toString()) ; //返回好友信息的特定格式
		j.put("user", jsonData.get("user")) ; //返回当前的登录的用户名
		j.put("Aname", (String) opar.doQuery( 
				"server", "select Aname from register "
						+ "where usercode = '"+jsonData.get("user")+"'", "Aname").get(0).get("Aname")) ; //返回当前登录账号的昵称
		return j.getMessage() ;
	}
	/**
	 * 
	 * 修改个人状态为登录在线状态
	 * @see
	 * @param opar
	 * @param userName
	 * @return
	 * boolean
	 *
	 */
	public boolean updateState(SQLOperation opar, String userName){
		String update = "UPDATE login SET state = 'online' WHERE usercode = '"+userName+"' ;" ;  
		return opar.doDataOperation(update, "server") ;
	}
	/**
	 * 
	 * 查找离线信息表中的离线信息并返回
	 * @see
	 * @param opar
	 * @param pool
	 * @param jsonData
	 * void
	 *
	 */
	public void repDownMessage(SQLOperation opar,UserConnPoll pool, ArrayJson jsonData){
		//返回离线信息 -- 查询离线信息
		List<Map<String, Object>> lists3 = opar.doQuery("server", "SELECT downMessage FROM dm"+jsonData.get("user"), "downMessage") ;
		if(lists3.size() != 0){
			//有离线消息
			//直接放进工厂发出去
			for(Map<String, Object> map : lists3){
				System.out.println("发送离线消息") ;
				MessageFactory.newInstants().messageSend(pool.get(jsonData.get("user")), (String) map.get("downMessage")) ; 
				//删除发送到离线信息
				if(opar.doDataOperation("DELETE FROM dm"+jsonData.get("user"), "server")){
					System.out.println("删除") ;
				}
			}
		}
		else{
			System.out.println("没有离线消息") ;
		}
	}
}
