package com.serverwin.core;

import java.util.HashMap;
import java.util.Map;

import com.serverwin.service.Json;

/**
 * 
 * <b>把信息主体封装成特定的信息格式以及返编译被封装成特定格式的信息<b>
 * @author 威 
 * <br>2017年9月1日 下午11:20:07 
 *
 */
public class ArrayJson implements Json {
	
	private Map<String, String> maps = new HashMap<String, String>() ;
	private static ArrayJson json = new ArrayJson() ;
	/**
	 * 
	 * 获取实例化对象 -- 单例模式
	 * @see
	 * @return
	 * ArrayJson
	 *
	 */
	public static ArrayJson getInstance(){
		return json ;
	}
	/**
	 * 
	 * 创建一个实例对象并获取
	 * @see 猜测：防止两个单例模式同时操作导致数据交错，因此必要时可以调用此方法
	 * @return
	 * ArrayJson
	 *
	 */
	public static ArrayJson newInstance(){
		return new ArrayJson() ;
	}
	@Override
	public void put(String key, String value) {
		maps.put(key, value) ;
	}
	/**
	 * 跟put方法配合使用获取字符串形式的数据
	 */
	public String getMessage(){
		StringBuffer sb = new StringBuffer() ;
		boolean flag = true ;
		for(Map.Entry<String, String> item : maps.entrySet()){
			sb.append(flag?"":",") ;
			flag = false ;
			sb.append(item.getKey()+":"+item.getValue()) ;
		}
		return "{"+sb.toString()+"}";
	}
	
	@Override
	public String get(String key) {
		return maps.get(key) ;
	}
	/**
	 * 处理一个字符串数据通过get方法获取单个数据
	 */
	public void dealMessage(String message){
		message = message.substring(message.indexOf("{")+1, message.indexOf("}")) ;
		String[] msgArs = message.split(",") ;
		for(int i = 0; i < msgArs.length; i++){
			String[] msgAr = msgArs[i].split(":") ;
			maps.put(msgAr[0], msgAr[1]) ;
		}
	}
	
	public static void main(String[] args) throws CloneNotSupportedException{
		ArrayJson json = new ArrayJson();
		json.put("pass", "1235");
		ArrayJson js = new ArrayJson();
		String msg = "{user:1235}" ;
		js.dealMessage(msg) ;
		System.out.println(js.get("user"));
		System.out.println(json.getMessage());
	}
	 
}
