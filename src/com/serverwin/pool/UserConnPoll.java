package com.serverwin.pool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.serverwin.service.Pool;

/**
 * 
 * <b>用户连接池<b>
 * @author 威 
 * <br>2017年5月26日 下午8:00:13 
 * @see
 * 	<br>put(String userName, Object socket) 添加进连接池
	<br>get(String userName)				从线程池中选中
	<br>getUserName(Socket socket) 			通过Socket去获取用户名
	<br>getAll()							 获得所有正在连接的用户
	<br>isExist(Socket socket)
	<br>isExist(String usercode)
	<br>toString()
	<br>getSize()
 */
public class UserConnPoll implements Pool
{
	private int lenght = 0 ;
	private static UserConnPoll connpool = new UserConnPoll() ;
	private static Map<String, Socket> map = new HashMap<String, Socket>() ;
	public UserConnPoll(){
	}
	public static UserConnPoll newInstants(){
		return connpool ;
	}
	/**
	 * 
	 * @Title: put 
	 * @Description: TODO(添加进连接池) 
	 * @param userName
	 * @param socket
	 * void
	 *
	 */
	@Override
	public void put(String userName, Object socket) {
		// TODO Auto-generated method stub
		map.put(userName, (Socket)socket) ;
		lenght++ ;
	}
	
	/**
	 * 
	 * @Title: get 
	 * @Description: TODO(从线程池中选中) 
	 * @param userName
	 * @return
	 * Socket
	 *
	 */
	public Socket get(String userName){
		for(Map.Entry<String, Socket> item : map.entrySet()){
			if(item.getKey().equals(userName)){
				return item.getValue() ;
			}
		}
		return null ;
	} 
	/**
	 * 
	 * @Title: getUserName 
	 * @Description: TODO(通过Socket去获取用户名) 
	 * @param Socket
	 * @return
	 * String
	 *
	 */
	public String getUserName(Socket socket){
		for(Map.Entry<String, Socket> item : map.entrySet()){
			System.out.println("="+item.getValue()) ;
			if(item.getValue().equals(socket)){
				return item.getKey() ;
			}
		}
		return null;
	}
	/**
	 * 
	 * @Title: getAll 
	 * @Description: TODO(获得所有正在连接的用户) 
	 * @return
	 * List<Map<String,Socket>>
	 *
	 */
	public Map<String, Socket> getAll(){
		return map ;
	}
	public boolean isExist(Socket socket){
		for(Map.Entry<String, Socket> item : map.entrySet()){
			if(item.getValue().equals(socket)){
				System.out.println("存在的") ;
				return true ;
			}
		}
		return false ;
	}
	public boolean isExist(String usercode){
		for(Map.Entry<String, Socket> item : map.entrySet()){
			if(item.getKey().equals(usercode)){
				System.out.println("存在的") ;
				return true ;
			}
		}
		return false ;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("") ;
		boolean flag = true ;
		for(Map.Entry<String , Socket> item : map.entrySet()){
			sb.append(flag ? "": ",") ;
			flag = false ;
			sb.append("{") ;
			sb.append(item.getKey()+"="+item.getValue());
			sb.append("}") ;
		}
		return sb.toString() ;
	}
	public int getSize(){
		return lenght ;
	}
	public static void main(String[] args){
		Socket ss = new Socket() ;
		Socket s1 = new Socket() ;
		UserConnPoll.newInstants().put("55", ss);
		UserConnPoll.newInstants().put("55", ss);
		UserConnPoll.newInstants().put("45", s1);
		System.out.println(UserConnPoll.newInstants().toString()) ;
 	}
	@Override
	public void remove(String userName) {
		
	}
	
} 
