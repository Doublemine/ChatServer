package thread;

import java.net.Socket;

import untl.TcpSocketUtil;

/**
 * 
 * @author wangh
 *
 *         接收客户端发送的注册或者登陆信息
 */
public class ReceiveClientRegInfoThread extends Thread {
	private Socket socket;
	private final static String REG_HEAD = "#REG#";// 注册信息头
	private final static String LOGIN_HEAD = "#LOGIN#";// 登陆信息头
	private final static String INFO_FLAG = "&&";// 连接用户名和密码的分割符
	private final static String USER_INFO_PATH = "info/user.xml";// 用户信息保存的文件路径
	private final static String CLOSE_CON = "#CLOSE#";

	/**
	 * 
	 * @param socket
	 *            接收客户端发送的注册或者登陆信息
	 */
	public ReceiveClientRegInfoThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {

			String msg = TcpSocketUtil.getClientData(socket);// 接收到来自客户端的注册或者登陆信息
			if (msg.startsWith("#NORMAL#")) {
				msg = msg.substring(8);
				if (msg.startsWith(LOGIN_HEAD)) {// 如果为登录
					String[] s = TcpSocketUtil.getRegInfoData(msg, LOGIN_HEAD,
							INFO_FLAG);
					if (TcpSocketUtil.isLogin(USER_INFO_PATH, s[0], s[1])) {// 登陆口令正确
						TcpSocketUtil.sendClientFlag(socket, 2);
					} else {// 登陆口令不正确
						TcpSocketUtil.sendClientFlag(socket, 1);
					}

				} else if (msg.startsWith(REG_HEAD)) {// 如果为注册
					String[] s = TcpSocketUtil.getRegInfoData(msg, REG_HEAD,
							INFO_FLAG);
					if (!TcpSocketUtil.isReged(USER_INFO_PATH, s[0])) {// 如果用户名可用
						TcpSocketUtil.writenRegInfo(s, USER_INFO_PATH);
						TcpSocketUtil.sendClientFlag(socket, 2);
					} else {// 如果用户名不可用（已被注册）
						TcpSocketUtil.sendClientFlag(socket, 1);
					}
				} else {// 为其他未知信息
					// System.err.println("接受到了来自客户端未知的注册或者登陆请求,自动关闭此链接");
					try {
						socket.close();
						// System.err.println("服务器关闭socket成功");
					} catch (Exception e) {
						// System.err.println("服务器关闭socket失败" + e.toString());
					}
					break;

				}
			} else if (msg.startsWith("#ERROR#")) {
				try {
					socket.close();
					// System.err.println("服务器接收到关闭socket请求:"
					// + socket.isConnected());
					break;
				} catch (Exception e) {
					// System.err.println("服务器关闭socket失败" + e.toString());
				}
			}

		}
	}
}
