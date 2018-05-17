package com.serverwin.service;

import java.net.Socket;
import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: Pool 
 * @Description: TODO(池接口) 
 * @author 威 
 * @date 2017年5月27日 下午3:03:20 
 *
 */
public interface Pool {
	/**
	 * 
	 * @Title: put 
	 * @Description: TODO(添加进连接池) 
	 * @param userName
	 * @param socket
	 * void
	 *
	 */
	public void put(String userName, Object object) ;
	/**
	 * 
	 * @Title: get 
	 * @Description: TODO(从池中选中) 
	 * @param userName
	 * @return
	 * Socket
	 *
	 */
	public Object get(String userName) ;
	/**
	 * 
	 * @Title: remove 
	 * @Description: TODO(从连接池中删除) 
	 * @param userName
	 * void
	 *
	 */
	public void  remove(String userName) ;
	/**
	 * 
	 * @Title: toString 
	 * @Description: TODO(以字符串的形式输出) 
	 * @return
	 * String
	 *
	 */
	public String toString() ;
}
