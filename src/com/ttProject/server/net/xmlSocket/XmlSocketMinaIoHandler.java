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
			if(new String(buf.array()).contains("<policy-file-request/>")) {
				// セキュリティーポリシーの応答の処理
				xmlConn.crossDomain();
				xmlConn.close();
			}
			XmlSocketConnManager.getInstance().getAdapter().getData(xmlConn, buf);
		}
//		super.messageReceived(session, message);
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
		// ここで応答してはいけない。オリジナルのExceptionが動作してしまう。
		session.close(true);
//		super.exceptionCaught(session, cause);
	}
}
