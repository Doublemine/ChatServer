package thread;

import java.net.Socket;

import untl.TcpSocketUtil;

public class RegAccroutThread extends Thread {
	private Socket socket;
	private String regInfo;
	private final static String REG_INFO_PATH = "info/user.xml";
	private final static String REG_STAT_CODE = "#REGINFO#";
	private final static String LOG_STAT_CODE = "#LOGIN#";
	private final static String REG_FLAG_CODE = "&&";
	private final static String REG_SUCC_STR = "&REG_COMPLETE&";
	private String[] regStrArr;

	public RegAccroutThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public synchronized void run() {

		regInfo = TcpSocketUtil.getClientData(this.socket);
		regStrArr = TcpSocketUtil.getRegInfoData(regInfo, REG_STAT_CODE,
				REG_FLAG_CODE);
		if (TcpSocketUtil.judeStringBegin(regInfo, REG_STAT_CODE)) {
			if (TcpSocketUtil.isReged(REG_INFO_PATH, regStrArr[0])) {
				TcpSocketUtil.writenRegInfo(regStrArr, REG_INFO_PATH);
				TcpSocketUtil.sendClientData(this.socket, REG_SUCC_STR);
			} else {
				TcpSocketUtil.sendClientFlag(this.socket, false);
			}
		} else if (TcpSocketUtil.judeStringBegin(regInfo, LOG_STAT_CODE)) {
			if (TcpSocketUtil
					.isLogin(REG_INFO_PATH, regStrArr[0], regStrArr[1])) {
				TcpSocketUtil.sendClientFlag(this.socket, true);
			} else {
				TcpSocketUtil.sendClientFlag(this.socket, false);
			}

		}

	}
}
