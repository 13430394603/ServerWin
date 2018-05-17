package com.serverwin.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: SSData 
 * @Description: TODO(一种数据形式，跟json平行<br> -- “[333-123456;y-123456]”) 
 * @author 威 
 * @date 2017年6月1日 下午11:25:04 
 *
 */
public class SSData{
	private List<Map<String, String>> sslists = new ArrayList<Map<String, String>>() ;
	private Map<String, String> ssmsps = new HashMap<String, String>() ; 

	
	
	
	public void dealMessage(String message){
	}
	private int length = 0 ;
	private StringBuffer PARAM_SB = new StringBuffer() ;
	private boolean Flag = true ;
	
	public void add(SSObject object) {
		deladdVal(object) ;
	}
	//处理add方法中的值
	public void deladdVal(SSObject json){
		boolean Flag_1 = true ;
		Map<String, String> maps = json.getAll() ;
		PARAM_SB.append(Flag ? "" : "--;--") ; //
		Flag = false ;
		PARAM_SB.append("-[-") ;
		for(Map.Entry<String, String> item : maps.entrySet()){
			PARAM_SB.append(Flag_1 ? "" : "-##-") ;
		    Flag_1 = false ;
			String key = item.getKey() ;
			PARAM_SB.append("" + key) ;
			PARAM_SB.append("-#-" + item.getValue()) ;
		}
		PARAM_SB.append("-]-") ;
		length++ ;
	}	
	public String toString() {
		return PARAM_SB.toString() ;
	}
	public List<Map<String, String>> toList(String SSData_str){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>() ;
		if(SSData_str.length() > 7){
			int startIndex = 0 ;
			int endIndex = 0 ;
			while((startIndex = SSData_str.indexOf("-[-", startIndex)) != -1){
				startIndex += 3 ; 
				endIndex = SSData_str.indexOf("-]-", endIndex + 3) ;
				System.out.println(SSData_str.substring(startIndex, endIndex)) ;
				list.add(getMaps(SSData_str.substring(startIndex, endIndex))) ;
			}
 		}
		return list ;
	}
	public Map<String, String> getMaps(String map_str){
		Map<String, String> maps = new HashMap<String, String>() ;
		String[] Arraymap = map_str.split("-##-") ;
		for(int i = 0; i < Arraymap.length; i++){
			maps.put(Arraymap[i].substring(0, Arraymap[i].indexOf("-#-")), Arraymap[i].substring(Arraymap[i].indexOf("-#-") + 3, Arraymap[i].length())) ;
		}
		return maps ;
	}
	public static void main(String[] args){
		SSData ss = new SSData() ;
		SSObject object = new SSObject() ;
		object.put("1", "55") ;
		object.put("2", "55");
		ss.add(object) ;
		object.put("1", "55") ;
		object.put("2", "55");
		ss.add(object) ;
		List<Map<String, String>> lists = ss.toList(ss.toString()) ;
		System.out.println(lists.size()) ;
		for(Map<String, String> row : lists){
			for(Map.Entry<String, String> item : row.entrySet()){
				System.out.println(item.getKey()+"-"+item.getValue()) ;
			}
		}
	}
}
