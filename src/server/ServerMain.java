package server;

import thread.ConClientThread;
import thread.RegThread;

public class ServerMain {
	public static void main(String[] args) {
		ConClientThread msgThread = new ConClientThread();
		RegThread regThread = new RegThread();
		msgThread.start();
		regThread.start();
		System.err.println("Server has been Strat!");
	}
}
