package com.serverwin.main;

import java.net.*;
import java.io.*;
import java.util.*;

import com.serverwin.pool.UserConnPoll;
/**
 * 
 * @ClassName: Server 
 * @Description: TODO(服务器主要类) 
 * @author 威 
 * @date 2017年5月26日 上午11:51:00 
 *
 */
public class Server{
  static ServerSocket server=null ;
  static Socket socket=null ;
  static String str="" ;
  public Server(){
  }
  public static void main(String[] args){
	  new BordThread() ;
      new AcceptThread() ;
  }
  /**
   * 
   * @Title: onAccept 
   * @Description: TODO(回应请求) 
   * void
   *
   */
  public static void onAccept(){
	  try{
	      server=new ServerSocket(2010);
	  }catch(IOException e){}
	  try{
		  System.out.println("服务器已启动") ;
		  socket=server.accept() ;
		  System.out.println("请求成功") ;
	  }catch(Exception e){}
	  if(socket!=null){
		  new ReadThread(socket) ;
		  System.out.println(socket.getInetAddress().getHostAddress()) ;
		  UserConnPoll.newInstants().put(socket.getInetAddress().getHostAddress(), socket) ; 
	      System.out.println("载入用户池") ;
	  }
  }
  /**
   * 
   * @Title: write_ 
   * @Description: TODO(发送) 
   * @param socket
   * @param strline
   * void
   *
   */
  public static void write_(Socket socket,String strline){
  	  System.out.println("S-write_");
  	  try{
		  DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		  out.writeUTF(strline);
		  
	  }catch(Exception e){}
  	  System.out.println("E-write");
  	  
  }
  /**
   * 
   * @Title: write_1 
   * @Description: TODO(重写发送 -- ) 
   * @param socket
   * @param strline
   * void
   *
   */
  public static void write_1(Socket socket,String strline){
  	  try{
		  DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		  out.writeUTF(strline);
	  }catch(Exception e){}
  }
}


/**
 *ClassName:AcceptThread
 *desc:回应请求 线程
 *
 */
class AcceptThread extends Thread{
    AcceptThread(){
        start();	
    }	 
    public void run(){
        while(true){
            Server.onAccept();		
        }
    }
}
/**
 *ClassName:AcceptThread
 *desc:广播线程
 *
 */
class BordThread extends Thread{
	BordThread(){
        start();	
    }	 
    public void run(){
        while(true){
        	System.out.println("开启线程并开启Ip广播");
        	new Bord().play() ;		
        }
    }
}
/**
 *ClassName:ReadThread
 *desc:读取信息 线程
 *
 */
class ReadThread extends Thread{
	DataInputStream in=null;
	Socket socket=null;
    ReadThread(Socket s){
    	socket=s;
		try{
    	in=new DataInputStream(socket.getInputStream());
		}catch(IOException e){}
        start();
    }
    public void run(){
		String str="";
		while(true){
			try{
				str=in.readUTF() ;
			    System.out.println("收到："+str) ;
			    MessageFactory.newInstants().messageCome(str) ;
			}catch(Exception e){}
		}
    }
}
