package com.serverwin.service;
/**
 * 
 * @ClassName: Json 
 * @Description: TODO(Json数据接口类) 
 * @author 威 
 * @date 2017年5月28日 下午2:50:42 
 *
 */
public interface Json {
	public void put(String key, String value) ;
	public String getMessage() ;
	public String get(String key) ;
	public void dealMessage(String message) ;
}
