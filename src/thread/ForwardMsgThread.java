package thread;

import server.ConDataCenter;
import untl.TcpSocketUtil;

/**
 * 转发信息到所有已经连接的客户端
 */
public class ForwardMsgThread extends Thread {
	private String msg;

	public ForwardMsgThread(String msg) {
		this.msg = msg;
	}

	@Override
	public synchronized void run() {
		for (ReceiveClientMsgThread socket : ConDataCenter.clientCon) {
			TcpSocketUtil.sendClientData(socket.getSocket(), msg);
		}
	}
}
