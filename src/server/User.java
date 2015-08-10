package server;


public class User {
	private String ip;
	private String name;

	/**
	 * 传入用户信息
	 * 
	 * @param name
	 *            base64位加密的用户名字符串
	 * @param ip
	 *            ip地址（getIpAdress）
	 */
	public User(String name, String ip) {
		this.ip = ip.substring(1);
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
