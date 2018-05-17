package com.serverwin.main;

import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 
 * <b>Desc:广播通信,需使用MulticastSokect 类中的joinGroup(); 方法
 *     加入到广播地址中，并且端口号要一致.(发送广播信息)<b>
 * @author 威 
 * <br>2017年9月2日 上午5:02:45 
 *
 */
public class Bord{
	int port = 318 ;
  
	InetAddress group=null;
	MulticastSocket socket = null;
	InetAddress address = null ;
	String ipadd = "" ;
	static String ipstring = "" ;
	Bord(){
		try{
			group = InetAddress.getByName("239.255.8.0");
			socket = new MulticastSocket(port);
			socket.setTimeToLive(1);
			socket.joinGroup(group);
			
			address = InetAddress.getLocalHost() ;
			ipadd = address.getHostAddress() ;
			ipstring = ipadd.substring(ipadd.indexOf("/")+1, ipadd.length()) ; //获取本地ip地址
		}catch(Exception e){}
	}
	/**
	 * 
	 * 进行广播传播服务器的ip地址，让客户端接收
	 * @see
	 * void
	 *
	 */
	public void play(){
		 while(true){
			  try{
				   byte data[]=ipstring.getBytes();
				   DatagramPacket packet=new DatagramPacket(data,data.length,group,port);
				   socket.send(packet);
				   String mess=new String(packet.getData(),0,packet.getLength());
				   Thread.sleep(2000);
			  }catch(Exception e){}
		 }
   }
}
