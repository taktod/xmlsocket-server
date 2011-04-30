package com.ttProject.java;

import com.ttProject.server.net.xmlSocket.XmlSocketMinaTransport;

public class Main {
	public static void main(String[] args) {
		XmlSocketMinaTransport transport = new XmlSocketMinaTransport();
		try {
			transport.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
