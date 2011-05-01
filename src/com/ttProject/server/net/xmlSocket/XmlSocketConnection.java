package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XmlSocketConnection
 * <pre>
 * Object to handle connection between server and client.
 * server will send <ping />, and client need to reply <pong /> for their alive.
 * 5 missing <pong /> will close the connection.
 * </pre>
 */
public class XmlSocketConnection {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketConnection.class);
	/** IoSession */
	private IoSession session = null;
	/** counter for ping */
	private int pingCount = 0;
	/**
	 * Constructor
	 * @param session IoSession Object
	 */
	public XmlSocketConnection(IoSession session) {
		this.session = session;
	}
	/**
	 * corssDomain reply;
	 */
	protected void crossDomain() {
		sendData(XmlSocketConnManager.getInstance().getAdapter().crossDomainPolicy());
	}
	/**
	 * to reset ping count;
	 */
	public void resetPingCount() {
		pingCount = 0;
	}
	/**
	 * Send Hex String to client.
	 * <pre>Hex String: "e39f9a8e" is 0xE3 0x9F 0x9A 0x8E byte[] object</pre>
	 * @param hexString String
	 */
	public void sendHexString(String hexString) {
		IoBuffer buf = IoBuffer.allocate(hexString.getBytes().length + 10);
		byte[] bytes = new byte[hexString.length() / 2];
		for(int i = 0;i < bytes.length;i ++) {
			bytes[i] = (byte)Integer.parseInt(hexString.substring(i * 2, (i+1) * 2), 16);
		}
		buf.put(bytes);
		buf.put((byte)0x00);
		buf.flip();
		sendData(buf);
	}
	/**
	 * Send String to client.
	 * @param data String
	 */
	public void sendData(String data) {
		IoBuffer buf = IoBuffer.allocate(data.getBytes().length + 10);
		buf.put(data.getBytes());
		buf.put((byte)0x00);
		buf.flip();
		sendData(buf);
	}
	/**
	 * Send byte data to client
	 * @param data byte[] object
	 */
	public void sendData(byte[] data) {
		IoBuffer buf = IoBuffer.allocate(data.length + 10);
		buf.put(data);
		buf.flip();
		sendData(buf);
	}
	/**
	 * Send IoBuffer to client
	 * @param outData IoBuffer Object
	 */
	public void sendData(IoBuffer outData) {
		try {
			session.write(outData);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Send ping to Client
	 */
	protected void ping() {
		pingCount ++;
		if(pingCount > 5) {
			log.debug("pingCount is exceed, so close this connection.");
			close();
			return;
		}
		sendData("<ping />");
	}
	/**
	 * close connection.
	 */
	public void close() {
		session.close(true);
	}
	/**
	 * @return id of session.
	 */
	public long getId() {
		return session.getId();
	}
	/**
	 * @return remoteAddress of session
	 */
	public String getRemoteAddress() {
		return session.getRemoteAddress().toString();
	}
	/**
	 * @return localAddress of session
	 */
	public String getLocalAddress() {
		return session.getLocalAddress().toString();
	}
	/**
	 * @return writtenBytes of session
	 */
	public long getWrittenBytes(){
		return session.getWrittenBytes();
	}
	/**
	 * @return readBytes of session
	 */
	public long getReadBytes() {
		return session.getReadBytes();
	}
}
