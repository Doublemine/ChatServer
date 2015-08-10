package server;

import thread.ClosedServerThread;
import thread.ListenClientMsgThread;
import thread.ListenClientRegThread;

public class ServerMain {
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new ClosedServerThread());
		ListenClientMsgThread msgThread = new ListenClientMsgThread();
		ListenClientRegThread regThread = new ListenClientRegThread();
		msgThread.start();
		regThread.start();
		System.err.println("Server has been Strat!");

	}
}
