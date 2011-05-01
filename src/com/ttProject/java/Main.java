package com.ttProject.java;

import com.ttProject.server.net.xmlSocket.XmlSocketMinaTransport;

/**
 * sample entry for Java
 */
public class Main {
	public static void main(String[] args) {
		XmlSocketMinaTransport transport = new XmlSocketMinaTransport();
		try {
			// start server
			transport.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
