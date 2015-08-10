package untl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * 
 * @author 末
 *
 *         注册信息使用 #REGINFO#作为开始标示，账号和密码使用&&分割 登录信息使用#LOGIN#作为开始标示，账号密码使用&&分割
 */
public class TcpSocketUtil {

	/**
	 * 
	 * @param socket
	 *            指定客户端连接的socket
	 * @param flag
	 *            整形标志位
	 * @return 向客户端发送服务器判断的数据状态，发送成功返回true否则返回false
	 */
	public static boolean sendClientFlag(Socket socket, int flag) {
		OutputStream os = null;
		DataOutputStream dos = null;
		try {
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeInt(flag);// 写入整形值
			return true;
		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}
	}

	/**
	 * 
	 * @param socket
	 *            指定的客户端连接socket
	 * @param msg
	 *            字符串消息
	 * @return 给指定的socket客户端发送数据，成功true，否则为false。
	 */
	public static boolean sendClientData(Socket socket, String msg) {

		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(msg);// 写入字符串
			return true;
		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}

	}

	/**
	 * 
	 * @param socket
	 *            传入连接的socket
	 * @return 返回与此socket连接的客户端传输的字符串信息
	 */
	public static String getClientData(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			return dis.readUTF();
		} catch (Exception e) {
			// System.err.println("获取客户端信息错误：" + e.toString());
			return "#ERROR#MSG";
		}
	}

	/**
	 * 
	 * @param str
	 *            传入处理之后的字符串数组
	 * @param filepath
	 *            文件路径（包含文件后缀） 保存注册信息为xml文件 配置为pro文件
	 * @return 写入注册信息成功返回true，否则，返回false
	 */
	public static synchronized boolean writenRegInfo(String[] str,
			String filepath) {

		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
			}
			Properties writeIni = new Properties();
			if (file.length() != 0) {
				writeIni.loadFromXML(new FileInputStream(file));
			}
			FileOutputStream fos = new FileOutputStream(file);
			writeIni.setProperty(str[0], str[1]);
			writeIni.storeToXML(fos, "LOGIN INFO");
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}
		return true;

	}

	/**
	 * 
	 * @param filepath
	 *            文件路径，文件为xml类型！
	 * @param username
	 *            用户名
	 * @return 返回知否存在该用户的布尔值，存在为true
	 */
	public static boolean isReged(String filepath, String username) {
		Properties getProperties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			getProperties.loadFromXML(fis);
			fis.close();
			if (getProperties.containsKey(username)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// System.err.println("isReged发生错误：" + e.toString());
			return false;
		}

	}

	/**
	 * 
	 * @param str
	 *            判断的字符串
	 * @param judestring
	 *            判断的依据
	 * @return 判断指定的字符串是否为指定的开头，返回true，否则false
	 */
	public static boolean judeStringBegin(String str, String judestring) {
		if (str.startsWith(judestring, 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param filepath
	 *            文件路径，必须为xml文件否则会造成程序混乱
	 * @param username
	 *            用户名
	 * @param passwd
	 *            密码
	 * @return 对比账户信息是否正确，返回true否则false。
	 */
	public static boolean isLogin(String filepath, String username,
			String passwd) {
		Properties readlogin = new Properties();
		File file = new File(filepath);
		if (!file.exists()) {
			return false;
		}
		FileInputStream fis = null;
		String localusername = "";
		String localpasswd = "";
		try {
			fis = new FileInputStream(file);
			readlogin.loadFromXML(fis);
			fis.close();
			if (!readlogin.containsKey(username)) {
				return false;
			} else {
				localusername = username;
				localpasswd = readlogin.getProperty(localusername);
				if (username.equals(localusername)
						&& passwd.equals(localpasswd)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			// System.err.println("isLogin发生错误：" + e.toString());
			return false;
		}

	}

	/**
	 * 
	 * @param str
	 *            传入的待处理字符
	 * @param stat
	 *            传入的分割开始状态码
	 * @param flag
	 *            分割符
	 * @return 返回去除开始状态码并分割账户和密码之后的字符串数组
	 * 
	 * @exception 使用
	 *                **可能会带来异常
	 */
	public static String[] getRegInfoData(String str, String stat, String flag) {
		String[] comStr = new String[2];
		if (judeStringBegin(str, stat)) {
			String temp = str.substring(stat.length());
			comStr = temp.split(flag);
			return comStr;
		} else {
			comStr[0] = "ERROR";
			comStr[1] = "ERROR";
			return comStr;
		}

	}
}
