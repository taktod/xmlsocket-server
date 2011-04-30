package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSocketAdapter {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketAdapter.class);

	/**
	 * event for starting xmlsocketServer
	 */
	public void start() {
		log.debug("start xmlSocketServer ...");
	}
	/**
	 * event for stopping xmlsocketServer
	 */
	public void stop() {
		log.debug("stop xmlSocketServer ...");
	}
	/**
	 * event for connecting somebody for the server.
	 * @param conn XmlSocketConnection instance for this connection
	 * @return true
	 */
	public boolean xmlConnect(XmlSocketConnection conn) {
		log.debug("new connection for xmlSocketServer ...");
		return true;
	}
	/**
	 * event for disconnecting somebody from the server.
	 * @param conn XmlSocketConnection instance for this connection
	 */
	public void xmlDisconnect(XmlSocketConnection conn) {
		log.debug("disconnect from xmlSocketServer");
	}
	/**
	 * event for getting data from clients
	 * @param conn XmlSocketConnection instance for this connection
	 * @param data IoBuffer data from client.
	 */
	public void getData(XmlSocketConnection conn, IoBuffer data) {
		
	}
	/**
	 * reply for crossDomain
	 * @return string data for crossDomain.
	 */
	public String crossDomainPolicy() {
		return "<cross-domain-policy>"
			+ "<allow-access-from domain='*' to-port='*' />"
			+ "</cross-domain-policy>";
	}
}