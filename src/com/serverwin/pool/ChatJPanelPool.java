package com.serverwin.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.serverwin.service.*;

public class ChatJPanelPool implements Pool{
	private static ChatJPanelPool pool = new ChatJPanelPool() ;
	private List<Map<String, JPanel>> lists = new ArrayList<Map<String, JPanel>>() ;
	private Map<String, JPanel> map = null ;
	public static ChatJPanelPool newInstants() {
		return pool ;
	}
	@Override
	public void put(String userName, Object jpanel) {
		map = new HashMap<String, JPanel>() ;
		map.put(userName, (JPanel)jpanel) ;
		lists.add(map) ;
	}

	@Override
	public Object get(String userName) {
		for(Map<String, JPanel> map : lists){
			for(Map.Entry<String, JPanel> item : map.entrySet()){
				if(item.getKey().equals(userName)){
					return item.getValue() ;
				}
			}
		}
		return null ;
	}

	@Override
	public void remove(String userName) {
		map = new HashMap<String, JPanel>() ;
		for(Map<String, JPanel> map : lists){
			for(Map.Entry<String, JPanel> item : map.entrySet()){
				if(item.getKey().equals(userName)){
					lists.remove(map) ;
				}
			}
		}
	}
	/**
	 * 通过JPanel去获取用户名
	 */
	public String getUserName(JPanel jpanel){
		System.out.println("-"+jpanel) ;
		for(Map<String, JPanel> row : lists){
			for(Map.Entry<String, JPanel> item : row.entrySet()){
				System.out.println("do") ;
				System.out.println("="+item.getValue()) ;
				if(item.getValue().equals((JPanel)jpanel)){
					System.out.println("you") ;
					return item.getKey() ;
				}
			}
		}
		return "" ;
	}
	/**
	 * 获得所有
	 */
	public List<Map<String, JPanel>> getAll() {
		return lists ;
	}
	

}
