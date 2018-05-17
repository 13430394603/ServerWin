package com.serverwin.service;

import com.serverwin.core.AnalyReceMessage;
/**
 * 
 * @ClassName: MsgFactory 
 * @Description: TODO(信息工厂接口) 
 * @author 威 
 * @date 2017年5月27日 下午9:52:35 
 *
 */
public interface MsgFactory {
	/**
	 * 
	 * @Title: messageCome 
	 * @Description: TODO(信息入口) 
	 * @param messageAnaly
	 * void
	 *
	 */
	public void messageCome(AnalyReceMessage messageAnaly) ;
	/**
	 * 
	 * @Title: dealMessage 
	 * @Description: TODO(处理进来的信息) 
	 * @param messageAnaly
	 * void
	 *
	 */
	public void dealMessage(AnalyReceMessage messageAnaly) ;
}
