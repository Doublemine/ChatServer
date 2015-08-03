package thread;

import java.net.Socket;

import server.ConDataCenter;
import untl.TcpSocketUtil;

public class ForwardMsgThread extends Thread {
	private String msg;

	public ForwardMsgThread(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		for (Socket socket : ConDataCenter.clientCon) {
			TcpSocketUtil.sendClientData(socket, this.msg);
		}
	}
}
