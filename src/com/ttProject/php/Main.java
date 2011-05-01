package com.ttProject.php;

import com.ttProject.server.net.xmlSocket.XmlSocketConnManager;
import com.ttProject.server.net.xmlSocket.XmlSocketMinaTransport;

/**
 * sample entry for PHP
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlSocketMinaTransport transport = new XmlSocketMinaTransport();
		// set socket adapter for php
		XmlSocketConnManager.getInstance().setAdapter(new PhpXmlSocketAdapter());
		try {
			transport.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
