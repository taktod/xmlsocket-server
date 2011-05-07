package com.ttProject.php;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

import com.caucho.quercus.Quercus;
import com.caucho.quercus.QuercusDieException;
import com.caucho.quercus.QuercusErrorException;
import com.caucho.quercus.QuercusExitException;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.vfs.Path;
import com.caucho.vfs.StdoutStream;
import com.caucho.vfs.WriteStream;
import com.ttProject.server.net.xmlSocket.XmlSocketAdapter;
import com.ttProject.server.net.xmlSocket.XmlSocketConnection;

/**
 * sample socket adapter for PHP
 */
public class PhpXmlSocketAdapter extends XmlSocketAdapter implements ActionListener {
	/** quercus object */
	private QuercusEx quercus;
	public PhpXmlSocketAdapter() {
		quercus = new QuercusEx();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		try {
			quercus.execute("php/start.php", ArgumentManager.getInstance().setArgument(this));
		}
		catch (IOException e) {
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		try {
			quercus.execute("php/stop.php", ArgumentManager.getInstance().setArgument(this));
		}
		catch (IOException e) {
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean xmlConnect(XmlSocketConnection conn) {
		try {
			quercus.execute("php/connect.php", ArgumentManager.getInstance().setArgument(this, conn));
		}
		catch (IOException e) {
		}
		return true;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void xmlDisconnect(XmlSocketConnection conn) {
		try {
			quercus.execute("php/disconnect.php", ArgumentManager.getInstance().setArgument(this, conn));
		}
		catch (IOException e) {
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getData(XmlSocketConnection conn, IoBuffer data) {
		try {
			conn.resetPingCount();
			quercus.execute("php/getData.php", ArgumentManager.getInstance().setArgument(this, conn, data));
		}
		catch (IOException e) {
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			quercus.execute("php/actionEvent.php", ArgumentManager.getInstance().setArgument(this, event));
		}
		catch (IOException e) {
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String crossDomainPolicy() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader("php/crossdomain.xml"));
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			log.trace("", e);
			return "";
		} catch (IOException e) {
			log.trace("", e);
			return "";
		}
		return sb.toString();
	}
	/**
	 * private quercus class for php engine.
	 */
	private class QuercusEx extends Quercus {
		private String arg = "";
		public QuercusEx() {
			super();
			this.init();
			this.start();
		}
		public void execute(String path, String arg)
				throws IOException{
			this.setFileName(path);
			this.arg = arg;
			this.execute();
		}
		public void execute(Path path) 
				throws IOException {
			QuercusPage page = parse(path);
		    WriteStream os = new WriteStream(StdoutStream.create());

		    os.setNewlineString("\r\n");
//		    os.setEncoding("UTF-8");

		    Env env = createEnv(page, os, null, null);
		    env.setGlobalValue("_JAVAARG", objectToValue(arg));
		    env.start();

		    try {
		      env.execute();
		    } catch (QuercusDieException e) {
		    } catch (QuercusExitException e) {
		    } catch (QuercusErrorException e) {
		    } finally {
		      env.close();

		      os.flush();
		    }
		}
	}
}
