package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles all XML socket event by the MINA framework.
 */
public class XmlSocketMinaIoHandler extends IoHandlerAdapter {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketMinaIoHandler.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		log.debug("messageReceived");
		if(message instanceof IoBuffer) {
			XmlSocketConnection xmlConn = (XmlSocketConnection)session.getAttribute("conn");
			IoBuffer buf = (IoBuffer)message;
			String inData = new String(buf.array());
			if(inData.contains("<policy-file-request/>")) {
				xmlConn.crossDomain();
				xmlConn.close();
			}
			else if(inData.contains("<pong />")){
				xmlConn.resetPingCount();
				return;
			}
			XmlSocketConnManager.getInstance().getAdapter().getData(xmlConn, buf);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("Session created");
		XmlSocketConnManager.getInstance().createConnection(session);
		super.sessionCreated(session);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("Session Closed");
		XmlSocketConnManager.getInstance().destroyConnection(session);
		super.sessionClosed(session);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.debug("Session Idle");
		XmlSocketConnection xmlConn = (XmlSocketConnection)session.getAttribute("conn");
		xmlConn.ping();
		super.sessionIdle(session, status);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		log.warn("Exception caught: {}", cause.getMessage());
		if(log.isDebugEnabled()) {
			log.error("Exception Detail", cause);
		}
		session.close(true);
	}
}
