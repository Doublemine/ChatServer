package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConDataCenter {

	public static List<Socket> clientCon = new ArrayList<Socket>();// 连接聊天信息socket
	public static List<Socket> regCon = new ArrayList<Socket>();// 注册信息、查询socket

}
