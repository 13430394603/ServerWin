package com.serverwin.core;

import java.util.HashMap;
import java.util.Map;

public class SSObject {
	Map<String, String> maps = new HashMap<String, String>() ;
	public void put(String key, String value){
		maps.put(key, value) ;
	}
	//返回value
	public Object get(String key){
		return maps.get(key) ;
	}
	public Map<String, String> getAll(){
		return maps ;
	}
}
