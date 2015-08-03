package thread;

import java.net.Socket;

import untl.TcpSocketUtil;

public class ClientMsgThread extends Thread {
	private Socket socket;

	public ClientMsgThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (true) {
			String msg = TcpSocketUtil.getClientData(this.socket);
			new ForwardMsgThread(this.socket.getRemoteSocketAddress() + "说："
					+ msg);
		}

	}
}
