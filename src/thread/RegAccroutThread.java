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
	/* private final static String REG_SUCC_STR = "&REG_COMPLETE&"; */
	private String[] regStrArr;

	public RegAccroutThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		regInfo = TcpSocketUtil.getClientData(this.socket);
		regStrArr = TcpSocketUtil.getRegInfoData(regInfo, REG_STAT_CODE,
				REG_FLAG_CODE);
		if (TcpSocketUtil.judeStringBegin(regInfo, REG_STAT_CODE)) {// 如果为注册信息
			if (!TcpSocketUtil.isReged(REG_INFO_PATH, regStrArr[0])) {// 用户名没有重复
				TcpSocketUtil.writenRegInfo(regStrArr, REG_INFO_PATH);
				TcpSocketUtil.sendClientFlag(this.socket, 2);// 注册成功

			} else {// 用户名重复
				TcpSocketUtil.sendClientFlag(this.socket, 1);// 1为错误 2位正确

			}
		} else if (TcpSocketUtil.judeStringBegin(regInfo, LOG_STAT_CODE)) {// 如果为登录信息
			if (TcpSocketUtil
					.isLogin(REG_INFO_PATH, regStrArr[0], regStrArr[1])) {// 口令正确
				TcpSocketUtil.sendClientFlag(this.socket, 2);

			} else {// 口令错误
				TcpSocketUtil.sendClientFlag(this.socket, 1);

			}

		}

	}
}
