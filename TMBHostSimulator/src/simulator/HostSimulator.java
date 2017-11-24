/**
 * @(#)HostSimulator.java	1.0a 25/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */

package simulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import pool.APIImpl;
import api.Processor;
import db.ConnectionManager;

/**
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @since
 * @version
 * @created date: Apr 25, 2007 5:12:28 PM
 */
public class HostSimulator extends Thread {
	
	public static String ipAddress;
	
	public static int millis = 300000;

	/**
	 * Child thread used to handle all the read and write operations of
	 * the message from client
	 */
	class ChildThread implements Runnable {

		private BufferedReader bufferedReader;

		private BufferedWriter bufferedWriter;

		Socket socket = null;

		public ChildThread(final Socket socket) {

			this.socket = socket;

			try {
				this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream(), "ISO8859_1"));
				this.bufferedReader = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "ISO8859_1"));
			} catch (final UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {

			String request;
			String response;

			while (true) {

				try {
					//Thread.sleep(80000);
					request = Simulator.read(this.bufferedReader, 3, 4);
					System.out.println("\n\r"+ "TMB Request :: "+request );

					final Processor processor = (Processor) HostSimulator.apiPool
							.borrowObject(HostSimulator.conf.getString("APINAME"));

					HostSimulator.apiPool.returnObject(
								HostSimulator.conf.getString("APINAME"), processor);

					response = processor.process(request);

					//IOB,TMB
					//Thread.sleep(millis);
					System.out.println("TMB Response :: "+response +"\n\r");

				//	Simulator.write(this.bufferedWriter, response, 3, 4);
				}
				catch(final SocketException e) {

					try {
						this.socket.close();
					} catch (final IOException ie) {
						// TODO Auto-generated catch block
						ie.printStackTrace();
					}

					break;
				}
				catch (final Exception e) {
					//e.printStackTrace();
				}
			}
		}
	}

	public static final GenericKeyedObjectPool apiPool = new GenericKeyedObjectPool();

	public static PropertiesConfiguration conf;
	public static XMLConfiguration bitProperty;

	public static final String CONF_PROPERTY = "simulator\\conf.properties";
	public static final String BITS_PROPERTY = "simulator\\bitsdetails.xml";

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	      Date dateobj = new Date();
	      
	    //  long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		
	      //int port;
			InetAddress address = null;
	        byte[] ip = new byte[0];
	        try {
	            address = InetAddress.getLocalHost();
	            ip = address.getAddress();
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }

	        ipAddress  = getIpAddress(ip);
	        
	      int port = 9010;
		//new HostSimulator(Integer.parseInt(args[0]));
		new HostSimulator(port);
		
		System.out.println("**************Bank host started on " +df.format(dateobj) +"**************");
		System.out.println("IOB Bank host has started on IP : " +ipAddress+ " Port no :  "+port + "\n");
        //System.out.println("Explicit waiting for " +seconds+" seconds");
		}

	public static String getIpAddress(byte[] rawBytes) {
        int i = 4;
        String ipAddress = "";
        for (byte raw : rawBytes)
        {
            ipAddress += (raw & 0xFF);
            if (--i > 0)
            {
                ipAddress += ".";
            }
        }

        return ipAddress;
    }
	private Socket foreignSocket;

	private int localPort;

	private ServerSocket localSocket;

	public HostSimulator(final int port) {

		this.localPort = port;

		final URL input   = this.getClass().getClassLoader().
								getResource(HostSimulator.CONF_PROPERTY);

		final URL bitsURL = this.getClass().getClassLoader().
								getResource(HostSimulator.BITS_PROPERTY);

		try {
			HostSimulator.conf 			= new PropertiesConfiguration(input);
			HostSimulator.bitProperty 	= new XMLConfiguration(bitsURL);

			HostSimulator.bitProperty.setReloadingStrategy(
								new FileChangedReloadingStrategy() {
				@Override
				public boolean reloadingRequired() {
					return true;
				}
			});
		}
		catch (final ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			new ConnectionManager().createConnection();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			new APIImpl();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.start();
	}

	@Override
	public void run() {

		try {
			this.localSocket = new ServerSocket(this.localPort);

			while (true) {
				this.foreignSocket = this.localSocket.accept();

				this.foreignSocket.setKeepAlive(Simulator.SO_KEEPALIVE);
				this.foreignSocket.setReceiveBufferSize(Simulator.SO_RCVBUF);
				this.foreignSocket.setSoLinger(true, Simulator.SO_LINGER);
				this.foreignSocket.setSoTimeout(Simulator.SO_TIMEOUT);

				new Thread(new ChildThread(this.foreignSocket)).start();
			}
		} catch (final SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}