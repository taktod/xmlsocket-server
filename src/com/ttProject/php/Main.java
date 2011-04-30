package com.ttProject.php;

import com.ttProject.server.net.xmlSocket.XmlSocketConnManager;
import com.ttProject.server.net.xmlSocket.XmlSocketMinaTransport;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlSocketMinaTransport transport = new XmlSocketMinaTransport();
		XmlSocketConnManager.getInstance().setAdapter(new PhpXmlSocketAdapter());
		try {
			transport.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
