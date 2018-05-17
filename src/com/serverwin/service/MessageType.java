package com.serverwin.service;
import com.serverwin.core.AnalyReceMessage;
/**
 * 
 * @ClassName: MessageType 
 * @Description: TODO(所有接收信息的接口) 
 * @author 威 
 * @date 2017年5月27日 下午10:23:44 
 *
 */
public interface MessageType {
	/**
	 * 
	 * @Title: reciMessage 
	 * @Description: TODO(承接信息) 
	 * @param messageAnaly
	 * void
	 *
	 */
	public void reciMessage(AnalyReceMessage messageAnaly) ;
	/**
	 * 
	 * @Title: dealMessage 
	 * @Description: TODO(信息的处理) 
	 * @param messageAnaly
	 * void
	 *
	 */
	public void dealMessage(AnalyReceMessage messageAnaly) ;
}
