package thread;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import server.ConDataCenter;

public class RegThread extends Thread {

	private static int RegPort;
	private final static String SET_STRING = "config/config.properties";
	static {
		Properties getProperties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SET_STRING);
			getProperties.load(fis);
			fis.close();
			RegPort = Integer.parseInt(getProperties.getProperty("RegPort"));

		} catch (Exception e) {
			System.err.println(e.toString());

		}
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(RegPort);
			while (true) {
				Socket socket = serverSocket.accept();
				new RegAccroutThread(socket).start();
				ConDataCenter.regCon.add(socket);
			}

		} catch (Exception exception) {
			System.err.println("RegThread--->" + exception.toString() + "\n"
					+ "DEBUG: RegPort=" + RegPort);
		}
	}

}
