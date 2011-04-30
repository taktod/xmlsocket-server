package com.ttProject.server.net.xmlSocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// コネクションがidleになったらクライアントに<ping/>をなげる。
// クライアント側が応答をかえしてきたら、Idleは初期化される。
// Idleが５回以上つらなった場合は存在せずとし、切断する。
public class XmlSocketConnection {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketConnection.class);
	private IoSession session = null;
	
	private int pingCount = 0;
	public XmlSocketConnection(IoSession session) {
		this.session = session;
	}
	protected void crossDomain() {
		sendData(XmlSocketConnManager.getInstance().getAdapter().crossDomainPolicy());
	}
	// 受け取り側はなんらかのイベントリスナーでやっておく。
	public void resetPingCount() {
		pingCount = 0;
	}
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
	public void sendData(String data) {
		IoBuffer buf = IoBuffer.allocate(data.getBytes().length + 10);
		buf.put(data.getBytes());
		buf.put((byte)0x00);
		buf.flip();
		sendData(buf);
	}
	public void sendData(byte[] data) {
		IoBuffer buf = IoBuffer.allocate(data.length + 10);
		buf.put(data);
		buf.flip();
		sendData(buf);
	}
	public void sendData(IoBuffer outData) {
		try {
			session.write(outData);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	// ピングを飛ばす。
	protected void ping() {
		pingCount ++;
		if(pingCount > 5) {
			session.close(true);
			return;
		}
		sendData("<ping />");
	}
	public long getId() {
		return session.getId();
	}
	public void close() {
		session.close(true);
	}
}
