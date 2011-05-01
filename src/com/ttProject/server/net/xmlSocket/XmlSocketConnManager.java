package com.ttProject.server.net.xmlSocket;

import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XmlSocketConnManager
 * <pre>
 * Handling connections for the server
 * </pre>
 */
public class XmlSocketConnManager {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketConnManager.class);
	private static final XmlSocketConnManager instance = new XmlSocketConnManager();
	private XmlSocketConnManager() {
		adapter = new XmlSocketAdapter();
	};
	public static synchronized XmlSocketConnManager getInstance() {
		if(instance == null) {
			throw new RuntimeException("Manager instance of broken.");
		}
		return instance;
	}
	/** set of connections */
	private Set<XmlSocketConnection> xmlConns = new HashSet<XmlSocketConnection>();
	/** event adapter */
	private XmlSocketAdapter adapter = null;
	/**
	 * set original adapter for event
	 * @param adapter XmlSocketAdapter object
	 */
	public void setAdapter(XmlSocketAdapter adapter) {
		log.debug("setAdapter: {}", adapter);
		this.adapter = adapter;
	}
	/**
	 * get adapter for event
	 * @return XmlSocketAdapter object
	 */
	public XmlSocketAdapter getAdapter() {
		return adapter;
	}
	/**
	 * setup new Connection object.
	 * @param session IoSession for making new Connetion
	 * @return ture
	 */
	protected boolean createConnection(IoSession session) {
		log.debug("createConnection with session: {}", session);
		XmlSocketConnection xmlConn = new XmlSocketConnection(session);
		session.setAttribute("conn", xmlConn);
		synchronized (xmlConns) {
			xmlConns.add(xmlConn);
		}
		adapter.xmlConnect(xmlConn);
		return true;
	}
	/**
	 * remove Connection object
	 * @param session IoSession for removing Connection
	 */
	protected void destroyConnection(IoSession session) {
		log.debug("destroyConnection with session: {}", session);
		XmlSocketConnection xmlConn = (XmlSocketConnection)session.getAttribute("conn");
		log.debug("xmlConnection: {}", xmlConn);
		adapter.xmlDisconnect(xmlConn);
		try {
			synchronized (xmlConns) {
				xmlConns.remove(xmlConn);
			}
		}
		catch(NullPointerException e) {
			;
		}
	}
	/**
	 * Search connection by connection id
	 * @param id long id number.
	 * @return found XmlSocketConnection Object or null
	 */
	public XmlSocketConnection getConnectionById(long id) {
		for(XmlSocketConnection conn : xmlConns) {
			if(conn.getId() == id) {
				return conn;
			}
		}
		return null;
	}
	/**
	 * @return Set<XmlSocketConnection> object.
	 */
	public Set<XmlSocketConnection> getConnectionSet() {
		return xmlConns;
	}
}
