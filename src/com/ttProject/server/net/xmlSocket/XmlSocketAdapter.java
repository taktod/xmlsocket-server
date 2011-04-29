package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSocketAdapter {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketAdapter.class);

	public void start() {
	}
	public void stop() {
	}
	public boolean xmlConnect(XmlSocketConnection conn) {
		return true;
	}
	public void xmlDisconnect(XmlSocketConnection conn) {
		
	}
	public void getData(XmlSocketConnection conn, IoBuffer data) {
		
	}
	public String crossDomainPolicy() {
		return "<cross-domain-policy>"
		+ "<allow-access-from domain='*' to-port='*' />"
		+ "</cross-domain-policy>";
	}
}