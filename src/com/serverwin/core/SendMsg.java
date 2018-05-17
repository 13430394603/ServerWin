package com.serverwin.core;

import java.io.DataOutputStream;
import java.net.Socket;
/**
 * 
 * @ClassName: Test 
 * @Description: TODO(发送信息--示范) 
 * @author 威 
 * @date 2017年5月26日 下午9:09:23 
 *
 */
//选中用户调用SendMassage.newInstans(socket, 发送内容)进行发送信息给对应的用户
public class SendMsg {
	//输入
	public static void Sc(Socket socket,String strline){
		 System.out.println("S-Scanner");
		 write_(socket,strline);
		 System.out.println("发送"+strline);
		 System.out.println("E-Scanner");
	}
	//发送
	public static void write_(Socket socket,String strline){
		System.out.println("S-write_");
		try{
			 DataOutputStream out=new DataOutputStream(socket.getOutputStream());
			 out.writeUTF(strline);
		 }catch(Exception e){}  
	  	 System.out.println("E-write");
	}
	public static void main(String[] args){
		SendMassage.newInstans(null, "").start(); 
	}
}
/**
 * 
 * @ClassName: NewThread 
 * @Description: TODO(启动发送线程) 
 * @author 威 
 * @date 2017年5月26日 下午9:02:59 
 *
 */
class SendMassage extends Thread{
	private static SendMassage thread = new SendMassage() ;
	private static Socket CONN_SOCKET = null ;
	private static String SEND_DATA = "" ;
	public static SendMassage newInstans(Socket sokcet, String sendData){
		CONN_SOCKET = sokcet ;
		SEND_DATA = sendData ;
		return thread ;
	}
	public void run(){
		SendMsg.Sc(CONN_SOCKET,SEND_DATA) ;
	}
}
