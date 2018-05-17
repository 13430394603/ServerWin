package com.serverwin.service;

import java.net.Socket;

import com.serverwin.core.CrateSendMessage;

public interface Response {
	public void doResponse(Socket sockect, CrateSendMessage Message);
	
}
