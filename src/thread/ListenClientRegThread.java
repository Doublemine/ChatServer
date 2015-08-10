package thread;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 
 * @author wangh 监听客户端发过来的注册或者登陆请求。
 */
public class ListenClientRegThread extends Thread {

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
				new ReceiveClientRegInfoThread(socket).start();
				// System.out.println("应答来自" + socket.getRemoteSocketAddress()
				// + "的连接请求");
				// ConDataCenter.regCon.add(socket);
			}

		} catch (Exception exception) {
			// System.err.println("RegThread--->" + exception.toString() + "\n"
			// + "DEBUG: RegPort=" + RegPort);
			JOptionPane.showMessageDialog(null,
					"服务器启动失败，端口被占用。请修改配置文件端口号再重启本程序！", "严重错误",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
