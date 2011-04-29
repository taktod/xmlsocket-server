package com.ttProject.server.net.xmlSocket;

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
