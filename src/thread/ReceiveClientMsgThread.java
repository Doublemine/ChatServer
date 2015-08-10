package thread;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import server.ConDataCenter;
import server.User;
import untl.TcpSocketUtil;

public class ReceiveClientMsgThread extends Thread {
	private Socket socket;
	private final static String ERR_HEAD = "#ERROR#";// 异常信息头
	private final static String NOR_HEAD = "#NORMAL#";// 正常信息头
	private final static String USERINFO_HEAD = "#USERINFO#";// 客户端发过来的用户信息头
	private static final String CLOSE_SERVER = "#SHUTDOWNSERVER#";// 关闭服务器的信息头
	private final static String CLOSE_CLIENT = "#CLIENTCLOSE#";// 客户端关闭的信息头
	private User user;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ReceiveClientMsgThread(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {
		while (true) {
			String msg = TcpSocketUtil.getClientData(this.socket);
			if (msg.startsWith(NOR_HEAD)) {// 正常的聊天信息-->转发给所有客户端
				System.out.println("收到来自客户端" + this.socket.getInetAddress()
						+ "的消息-->" + msg);

				sendAllSocketMsg(this.user.getName() + " 说：" + msg.substring(8)
						+ "\n");
			} else if (msg.startsWith(ERR_HEAD)) {// 异常信息--->关闭此socket连接和进程。
				try {
					ConDataCenter.clientCon.remove(this);
					socket.close();
					sendAllSocketMsg("#USERINFOCLOSED#" + user.getName());
					// System.err.println("关闭连接完成");
					break;
				} catch (Exception e) {
					// System.err.println("关闭连接失败，开始刷屏。。。");
					break;
				}
			} else if (msg.startsWith(USERINFO_HEAD)) {// 接收到来自客户端的用户信息

				System.out.println("收到来自客户端的用户信息:" + msg);
				String str[] = TcpSocketUtil.getRegInfoData(msg, USERINFO_HEAD,
						"&&");
				user = new User(str[0], str[1]);
				sendAllSocketMsg("#USERINFOADD#" + user.getName());// 发送给所有用户当前用户上线的信息
				ConDataCenter.clientCon.add(this);
				for (ReceiveClientMsgThread rcmt : ConDataCenter.clientCon) {
					TcpSocketUtil.sendClientData(socket, "#USERINFOADD#"
							+ rcmt.user.getName());
				}

				// System.out.println("收到来自客户端的用户信息-用户名为:" + user.getName()
				// + "\tip：" + user.getIp());
			} else if (msg.startsWith(CLOSE_SERVER)) {
				try {
					socket.close();
					JOptionPane.showMessageDialog(null,
							"系统检测到服务器已经关闭，程序将自动退出！", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(1);
					break;
				} catch (IOException e) {

				}
			} else if (msg.startsWith(CLOSE_CLIENT)) {
				ConDataCenter.clientCon.remove(this);
				try {
					socket.close();
					sendAllSocketMsg("#USERINFOCLOSED#" + user.getName());
				} catch (IOException e) {

				}

			}

		}

	}

	private synchronized void sendAllSocketMsg(String msg) {
		for (ReceiveClientMsgThread socket : ConDataCenter.clientCon) {
			System.out.println("socket"
					+ socket.getSocket().getRemoteSocketAddress());
			TcpSocketUtil.sendClientData(socket.getSocket(), msg);
		}
	}
}
