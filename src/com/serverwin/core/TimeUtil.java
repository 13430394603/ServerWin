package com.serverwin.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static String getDatetime(){
		Date datetime=new Date();
		SimpleDateFormat st=new SimpleDateFormat("MM/dd-HH:mm");
		return st.format(datetime);
	}
	public static void main(String[] args){
		System.out.println(getDatetime()) ;
	}
}
