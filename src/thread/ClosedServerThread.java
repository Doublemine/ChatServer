package thread;

import server.ConDataCenter;
import untl.TcpSocketUtil;

/**
 * 
 * @author wangh 此进程用于向所有已经连接到聊天端口的客户端发送服务器关闭的消息
 */
public class ClosedServerThread extends Thread {

	private static final String CLOSE_SERVER = "#SHUTDOWNSERVER#MSG";

	@Override
	public void run() {
		for (ReceiveClientMsgThread rcmt : ConDataCenter.clientCon) {
			TcpSocketUtil.sendClientData(rcmt.getSocket(), CLOSE_SERVER);
		}
	}
}
