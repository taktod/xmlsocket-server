package com.ttProject.server.net.xmlSocket;

import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Set<XmlSocketConnection> xmlConns = new HashSet<XmlSocketConnection>();
	private XmlSocketAdapter adapter = null;
	public void setAdapter(XmlSocketAdapter adapter) {
		this.adapter = adapter;
	}
	public XmlSocketAdapter getAdapter() {
		return adapter;
	}
	protected boolean createConnection(IoSession session) {
		XmlSocketConnection xmlConn = new XmlSocketConnection(session);
		session.setAttribute("conn", xmlConn);
		synchronized (xmlConns) {
			xmlConns.add(xmlConn);
		}
		adapter.xmlConnect(xmlConn);
		return true;
	}
	protected void destroyConnection(IoSession session) {
		XmlSocketConnection xmlConn = (XmlSocketConnection)session.getAttribute("conn");
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
	public XmlSocketConnection getConnectionById(long id) {
		for(XmlSocketConnection conn : xmlConns) {
			if(conn.getId() == id) {
				return conn;
			}
		}
		return null;
	}
	public Set<XmlSocketConnection> getConnectionSet() {
		return xmlConns;
	}
}
