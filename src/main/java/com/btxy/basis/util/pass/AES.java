package com.btxy.basis.util.pass;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

public class AES {
	public static void main(String[] args) throws Exception {
		//[mac:00227E001207|letvUserName:vip0309586|ip:|timeStamp:19700101011148|checkLevel:1|version:1.0|reserved:NetworkUserName=cqcs;NetworkPassword=888888|NetworkUserName:cqcs|NetworkPassword888888|realIp:124.205.228.170|userAgent|identityType:0|ifIdentitySuccess:true]
		String s = "001A9AFFFFF1$$001A9AFFFFF1$$$$20131226093723$$1$$001$$";//"AA00997E033BE5$$vip0309586$$$$19700101000148$$1$$1.0$$r";
		
		String s1=new AES("87dcb78a32").encrypt(s);
		System.out.println(s1);

		//s1="4f832be2d6a0a67f0836936410d4973c6b609faf66003c09789c44d5d5cd5e023c3270f3a76b4b9965102231e59a3bf79921620451272836106c905f71dcbf160ee7f7865e5f750d14507d2c160cbfbb883a81f868bd54f9e2baa639aca59630";
		String ss = new AES().decrypt(s1);
		String[] a=ss.split("\\$\\$");
		String astr="";
		for(String one:a){
			astr=astr+""+one+"$$";
		}
		System.out.println(astr);
		
		//AuthInfo=10fe1c7ef4bd817799192aa93becddcb9b0d802a3ba7d318adca0483b92127c8e53702a3ae4d9c520e3755a1a82797841d3189d764a680e983740c54db16b3a09ebdc304094a102f9631d24860fc5889
	}
	
	//private static String strDefaultKey = "cddcb9b0d802a3ba7d";
	private static String strDefaultKey = "btxy2015";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public AES() throws Exception {
		this(strDefaultKey);
	}

	public AES(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("AES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("AES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 *  加密
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 *  解密
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		try{
			return new String(decrypt(hexStr2ByteArr(strIn)));
		}catch(Exception e){
			System.out.println("解密错误："+strIn);
			throw e;
		}
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[16]; // 128

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "AES");

		return key;
	}



}
