package server;

import java.util.ArrayList;
import java.util.List;

import thread.ReceiveClientMsgThread;

public class ConDataCenter {

	public static List<ReceiveClientMsgThread> clientCon = new ArrayList<ReceiveClientMsgThread>();// 连接聊天的线程

}
