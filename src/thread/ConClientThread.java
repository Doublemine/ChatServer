package thread;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import server.ConDataCenter;

public class ConClientThread extends Thread {

	private static int MsgPort;
	private final static String SET_STRING = "config/config.properties";
	static {
		Properties getProperties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SET_STRING);
			getProperties.load(fis);
			fis.close();
			MsgPort = Integer.parseInt(getProperties.getProperty("MsgPort"));

		} catch (Exception e) {
			System.err.println(e.toString());

		}
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(MsgPort);
			while (true) {
				Socket socket = serverSocket.accept();
				new ClientMsgThread(socket).start();
				ConDataCenter.clientCon.add(socket);
			}

		} catch (Exception exception) {
			System.err.println("ConClientThread--->" + exception.toString()
					+ "\n" + "DEBUG: RegPort=" + MsgPort);
		}
	}

}
