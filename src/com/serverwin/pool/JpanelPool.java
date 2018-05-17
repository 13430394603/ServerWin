package com.serverwin.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.serverwin.service.Pool;
/**
 * 
 * @ClassName: Jpanelpool 
 * @Description: TODO(好友列表组件池) 
 * @author 威 
 * @date 2017年5月27日 下午3:17:39 
 *
 */
public class JpanelPool implements Pool{
	private List<Map<String, JPanel>> lists = new ArrayList<Map<String, JPanel>>() ;
	private Map<String, JPanel> map = null ;
	private static JpanelPool pool = new JpanelPool () ;
	public static JpanelPool newInstans() {
		return pool ;
	}
	/**
	 * 存入
	 */
	@Override
	public void put(String userName, Object jpanel) {
		map = new HashMap<String, JPanel>() ;
		map.put(userName, (JPanel)jpanel) ;
		lists.add(map) ;
	}
	/**
	 * 删除
	 */
	@Override
	public void remove(String userName){
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
	 * 获取
	 */
	@Override
	public JPanel get(String userName){
		for(Map<String, JPanel> map : lists){
			for(Map.Entry<String, JPanel> item : map.entrySet()){
				if(item.getKey().equals(userName)){
					return item.getValue() ;
				}
			}
		}
		return null ;
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
	/**
	 * 以字符串的形式输出
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer() ;
		boolean flag = true ;
		if(lists.size()!=0){
			
			for(Map<String, JPanel> maps : lists){
				sb.append(flag ? "": ",") ;
				flag = false ;
				for(Map.Entry<String , JPanel> item : maps.entrySet()){
					sb.append("{") ;
					sb.append(item.getKey()+"="+item.getValue());
					sb.append("}") ;
				}
			}
			return sb.toString() ;
		}
		return null ;
	}
	public static void main(String[] args){
		JPanel jp = new JPanel() ;
		JPanel jp2 = new JPanel() ;
		JPanel jp3 = new JPanel() ;
		JPanel jp4 = new JPanel() ;
		JpanelPool.newInstans().put("1", jp);
		JpanelPool.newInstans().put("2", jp2);
		JpanelPool.newInstans().put("3", jp3);
		JpanelPool.newInstans().put("4", jp4);
		System.out.println(JpanelPool.newInstans().toString()) ; 
		System.out.println(":"+JpanelPool.newInstans().getUserName(jp2)) ;
		System.out.println(":"+JpanelPool.newInstans().get("2")) ;
	}
}
