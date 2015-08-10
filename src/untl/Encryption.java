package untl;

import java.security.MessageDigest;

public class Encryption {

	/* private final static String ENCODING = "UTF-8"; */
	private static final String HEX_NUMS_STR = "0123456789ABCDEF";

	/**
	 * 
	 * @param str
	 *            传入带加密的str字符串
	 * @return 返回加密过后的字符串
	 * 
	 *         此函数为静态函数，不需要实例化即可使用。使用MD5粗略完成加密
	 */
	public static String EncryptionStr(String str) {
		String md5srt = "";
		try {
			MessageDigest mdDigest = MessageDigest.getInstance("MD5");
			mdDigest.update(str.getBytes());
			byte[] temp = mdDigest.digest();
			md5srt = byteArrayToHex(temp);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return md5srt;
	}

	/**
	 * 
	 * @param bytes
	 *            传入一个字符数组
	 * @return 返回16进制数组的String类型
	 */
	private static String byteArrayToHex(byte[] bytes) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * 将16进制字符串转换成字节数组
	 * 
	 * @param hex
	 *            传入hex String
	 * @return 返回字符数组byte []
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
					.indexOf(hexChars[pos + 1]));
		}
		return result;
	}

	/**
	 * 解码Base64算法加密的字符串
	 * 
	 * @param str
	 *            Base64加密之后的字符串
	 * @return 返回解码之后的字符串
	 */
	/*
	 * public static String base64Decode(String str) { try { byte[] b =
	 * Base64.decodeBase64(str.getBytes(ENCODING)); return new String(b,
	 * ENCODING); } catch (Exception e) { System.err.println("解码Base64失败:" +
	 * e.toString()); return "#Decode#FAIL"; }
	 * 
	 * }
	 */
	/**
	 * 使用Base64算法加密指定的字符串
	 * 
	 * @param data
	 *            待加密的字符串
	 * @return 返回Base64加密之后的字符串
	 */
	/*
	 * public static String encodedSafe(String data) { try { byte[] b =
	 * Base64.encodeBase64(data.getBytes(ENCODING), true); return new String(b,
	 * ENCODING); } catch (Exception e) { System.err.println("加密Base64失败" +
	 * e.toString()); return "#ENCODE#FAIL"; }
	 * 
	 * }
	 */
}
