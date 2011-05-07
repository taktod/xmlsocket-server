package com.ttProject.server.net.xmlSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XmlSocketAdapter to handle main event.
 * <pre>
 * to make original server application please extends this class.
 * </pre>
 */
public class XmlSocketAdapter {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketAdapter.class);
	private Map<Object, Object> attribute = new ConcurrentHashMap<Object, Object>(1);

	private Object obj;
	public Object getSr() {
		return obj;
	}
	public void setSr(Object obj) {
		this.obj = obj;
	}

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
		log.debug("getData from client");
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
	public Object getAttribute(Object key, Object defaultObject) {
		Object obj = attribute.get(key);
		if(obj == null) {
			return defaultObject;
		}
		return obj;
	}
	public Object getAttribute(Object key) {
		return getAttribute(key, null);
	}
	public Object setAttribute(Object key, Object value) {
		return attribute.put(key, value);
	}
	public Object removeAttribute(Object key) {
		return attribute.remove(key);
	}
	public boolean containsKey(Object key) {
		return attribute.containsKey(key);
	}
}