package thread;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ListenClientMsgThread extends Thread {

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
				new ReceiveClientMsgThread(socket).start();
				// ConDataCenter.clientCon.add(socket);
				// System.out.println("应答来自" + socket.getRemoteSocketAddress()
				// + "的连接请求");

			}

		} catch (Exception exception) {
			// System.err.println("ConClientThread--->" + exception.toString()
			// + "\n" + "DEBUG: RegPort=" + MsgPort);
			JOptionPane.showMessageDialog(null,
					"服务器启动失败，端口被占用。请修改配置文件端口号再重启本程序！", "严重错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
