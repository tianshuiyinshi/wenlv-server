package com.zte.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {
	private final static String encoding = "UTF-8";
	private static Logger log = LoggerFactory.getLogger(AESUtil.class);
	private final static String IV_STRING = "16-Bytes--String";

	/**
	 * AES加密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encryptAES(String content, String password) {
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		// BASE64位加密
		encryptResultStr = ebotongEncrypto(encryptResultStr);
		return encryptResultStr;
	}

	/**
	 * AES解密
	 * 
	 * @param encryptResultStr
	 * @param password
	 * @return
	 */
	public static String decrypt(String encryptResultStr, String password) {
		String result = "";
		
		try {
			log.info("开始进行AES解密"); 
			// BASE64位解密
			String decrpt = ebotongDecrypto(encryptResultStr);
			log.info("解密后的字符串 = " + decrpt);
			byte[] decryptFrom = parseHexStr2Byte(decrpt);
			log.info("转换成16进制 = " + Arrays.toString(decryptFrom));
			byte[] decryptResult = decrypt(decryptFrom, password);
			result = new String(decryptResult);
			log.info("最后获取到明文 = " + result);
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return result;
	}

	/**
	 * 加密字符串
	 */
	public static String ebotongEncrypto(String str) {
		BASE64Encoder base64encoder = new BASE64Encoder();
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = str.getBytes(encoding);
				result = base64encoder.encode(encodeByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// base64加密超过一定长度会自动换行 需要去除换行符
		return result.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
	}

	/**
	 * 解密字符串
	 */
	public static String ebotongDecrypto(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			byte[] encodeByte = base64decoder.decodeBuffer(str);
			return new String(encodeByte);
		} catch (IOException e) {
			log.info("ebotongDecrypto" + e.getMessage());
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 加密
	 * 
	 * @param content  需要加密的内容
	 * @param password 加密密码
	 * @return
	 */
	private static byte[] encrypt(String content, String password) {

		try {
			byte[] byteContent = content.getBytes("UTF-8");
			// 注意，为了能与 iOS 统一
			// 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
			byte[] enCodeFormat = password.getBytes();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

			// 指定加密的算法、工作模式和填充方式
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			byte[] initParam = IV_STRING.getBytes();
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encryptedBytes = cipher.doFinal(byteContent);

			return encryptedBytes;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | InvalidAlgorithmParameterException | BadPaddingException e) {
			log.error("AES failed 9999: {}",e.getMessage());
		}
        return null;
	}

	/**
	 * 解密
	 * 
	 * @param content  待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	private static byte[] decrypt(byte[] content, String password) {

		byte[] enCodeFormat = password.getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");

		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			byte[] initParam = IV_STRING.getBytes();
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            log.error("AES failed 9999: {}",e.getMessage());
		}
        return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String encryptAES1(String content, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		byte[] byteContent = content.getBytes("UTF-8");
		// 注意，为了能与 iOS 统一
		// 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
		byte[] enCodeFormat = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
		// 指定加密的算法、工作模式和填充方式
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] initParam = IV_STRING.getBytes();
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(byteContent);
		// 同样对加密后数据进行 base64 编码
		BASE64Encoder base64encoder = new BASE64Encoder();
		return base64encoder.encode(encryptedBytes);
	}

	public static String decryptAES(String content, String key)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException, InvalidAlgorithmParameterException {
		// base64 解码
		BASE64Decoder base64decoder = new BASE64Decoder();

		byte[] encryptedBytes = base64decoder.decodeBuffer(content);
		byte[] enCodeFormat = key.getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
		byte[] initParam = IV_STRING.getBytes();
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] result = cipher.doFinal(encryptedBytes);
		return new String(result, "UTF-8");
	}

	public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidKeyException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("number", 1);
//		String strResult = AESUtil.encryptAES(jsonObject.toString(), "NzkyODM0Q0U4QzY1");
//		System.out.println("加密：" + strResult);
//
//		String deResult = AESUtil.decrypt(strResult, "NzkyODM0Q0U4QzY1");
//		System.out.println("解密：" + deResult);
//
//
//
//		String s = "RDY0MDlCQURFRUEzNTZFRDRBNDVCQTE3NjkyRjU4QTk=";
//		String s1 = "NUNGMTg1MzhDQUEyQUIzMTBFQ0RGQ0E3";
//		String deResult1 = AESUtil.decrypt(s, "QUIzMTBFQ0RGQ0E3");
//		System.out.println("解密：" + deResult1);


        String strResult = AESUtil.encryptAES("16", "a9itu123glbd-2xo");
        System.out.println("加密：" + strResult);

		String deResult1 = AESUtil.decrypt("Q0M1MDhCMDdDQjkxOTA3NTNFNURENDQyNkUzRDM3MkM=", "QUIzMTBFQ0RGQ0E3");
		System.out.println("解密：" + deResult1);

	}
}