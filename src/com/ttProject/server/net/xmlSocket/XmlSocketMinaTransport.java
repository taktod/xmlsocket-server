package com.ttProject.server.net.xmlSocket;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSocketMinaTransport {
	protected static Logger log = LoggerFactory.getLogger(XmlSocketMinaTransport.class);
	
	private int ioThreads;
	private int connectionThreads;
	private int receiveBufferSize;
	private int sendBufferSize;
	private int pingDelay;
	private int port;
	private boolean tcpNoDelay;
	private boolean useHeapBuffers;
	private IoHandlerAdapter ioHandler;
	private SocketAcceptor acceptor;
	
	/**
	 * @param ioThreads the ioThreads to set
	 */
	public void setIoThreads(int ioThreads) {
		this.ioThreads = ioThreads;
	}
	/**
	 * @param connectionThreads the connectionThreads to set
	 */
	public void setConnectionThreads(int connectionThreads) {
		this.connectionThreads = connectionThreads;
	}
	/**
	 * @param receiveBufferSize the receiveBufferSize to set
	 */
	public void setReceiveBufferSize(int receiveBufferSize) {
		this.receiveBufferSize = receiveBufferSize;
	}
	/**
	 * @param sendBufferSize the sendBufferSize to set
	 */
	public void setSendBufferSize(int sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}
	/**
	 * @param pingDelay the pingDelay to set
	 */
	public void setPingDelay(int pingDelay) {
		this.pingDelay = pingDelay;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @param tcpNoDelay the tcpNoDelay to set
	 */
	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}
	/**
	 * @param useHeapBuffers the useHeapBuffers to set
	 */
	public void setUseHeapBuffers(boolean useHeapBuffers) {
		this.useHeapBuffers = useHeapBuffers;
	}
	/**
	 * @param ioHandler the ioHandler to set
	 */
	public void setIoHandler(IoHandlerAdapter ioHandler) {
		this.ioHandler = ioHandler;
	}
	/**
	 * @param acceptor the acceptor to set
	 */
	public void setAcceptor(SocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}
	/**
	 * @param ioThreads
	 * @param connectionThreads
	 * @param receiveBufferSize
	 * @param sendBufferSize
	 * @param pingDelay
	 * @param port
	 * @param tcpNoDelay
	 * @param useHeapBuffers
	 * @param ioHandler
	 */
	public XmlSocketMinaTransport(int ioThreads, int connectionThreads,
			int receiveBufferSize, int sendBufferSize, int pingDelay, int port,
			boolean tcpNoDelay, boolean useHeapBuffers,
			IoHandlerAdapter ioHandler) {
		this.ioThreads = ioThreads;
		this.connectionThreads = connectionThreads;
		this.receiveBufferSize = receiveBufferSize;
		this.sendBufferSize = sendBufferSize;
		this.pingDelay = pingDelay;
		this.port = port;
		this.tcpNoDelay = tcpNoDelay;
		this.useHeapBuffers = useHeapBuffers;
		this.ioHandler = ioHandler;
	}
	/**
	 * construct with default values.
	 */
	public XmlSocketMinaTransport() {
		connectionThreads = Runtime.getRuntime().availableProcessors();
		ioThreads = connectionThreads * 2;
		receiveBufferSize = 256 * 1024;
		sendBufferSize = 256 * 1024;
		pingDelay = 30;
		port = 7080;
		tcpNoDelay = true;
		useHeapBuffers = true;
	}
	private void initIoHandler() {
		if(ioHandler == null) {
			// if no handler use default.
			ioHandler = new XmlSocketMinaIoHandler();
		}
	}
	public void start()
			throws Exception {
		initIoHandler();
		
		IoBuffer.setUseDirectBuffer(!useHeapBuffers);
		if(useHeapBuffers) {
			IoBuffer.setAllocator(new SimpleBufferAllocator());
		}
		Executor connectionExecutor = Executors.newFixedThreadPool(connectionThreads);
		Executor ioExecutor = Executors.newFixedThreadPool(ioThreads);
		acceptor = new NioSocketAcceptor(connectionExecutor, new NioProcessor(ioExecutor));
		
		SocketSessionConfig sessionConf = acceptor.getSessionConfig();
		sessionConf.setReuseAddress(true);
		sessionConf.setTcpNoDelay(tcpNoDelay);
		sessionConf.setReceiveBufferSize(receiveBufferSize);
		sessionConf.setSendBufferSize(sendBufferSize);
		sessionConf.setIdleTime(IdleStatus.BOTH_IDLE, pingDelay);
		
		acceptor.setReuseAddress(true);
		acceptor.setHandler(ioHandler);
		acceptor.bind(new InetSocketAddress(port));
		log.info("server start");
		XmlSocketConnManager.getInstance().getAdapter().start();
	}
	public void stop() {
		XmlSocketConnManager.getInstance().getAdapter().stop();
		if(acceptor != null) {
			acceptor.unbind();
		}
	}
}
