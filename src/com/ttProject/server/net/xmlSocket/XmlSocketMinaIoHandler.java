package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSocketMinaIoHandler extends IoHandlerAdapter {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketMinaIoHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
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
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		XmlSocketConnManager.getInstance().createConnection(session);
		super.sessionCreated(session);
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		XmlSocketConnManager.getInstance().destroyConnection(session);
		super.sessionClosed(session);
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		XmlSocketConnection xmlConn = (XmlSocketConnection)session.getAttribute("conn");
		xmlConn.ping();
		super.sessionIdle(session, status);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		session.close(true);
	}
}
