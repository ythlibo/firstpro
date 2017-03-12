package com.milepost.encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.junit.Test;
//见http://blog.csdn.net/furongkang/article/details/6882039
/**
 * Java中加密分为两种方式，一个是对称加密，另一个是非对称加密。对称加密是因为加密和解密的钥匙相同，而非对称加密是加密和解密的钥匙不同。
 * 对称加密与非对称加密的区别： 对称加密称为密钥加密，速度快，但加密和解密的钥匙必须相同，只有通信双方才能知道密钥。
 * 非对称加密称为公钥加密，算法更加复杂，速度慢，加密和解密钥匙不相同，任何人都可以知道公钥，只有一个人持有私钥可以解密。
 * 
 * @author HRF
 */
public class EncryptionTest {

	public static final String FILE_BASE_PATH = "C:\\Users\\HRF\\Desktop\\TestPicture\\";

	/**
	 * 对称加密
	 */
	@Test
	public void secretEncrypt() throws Exception {
		// 使用Cipher的实例
		Cipher cipher = Cipher.getInstance("AES");

		// 得到加密的钥匙
		//SecretKey key = KeyGenerator.getInstance("AES").generateKey();
		Key key = KeyGenerator.getInstance("AES").generateKey();

		// 初始化加密操作,传递加密的钥匙
		cipher.init(Cipher.ENCRYPT_MODE, key); // ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE or UNWRAP_MODE

		// 将加密的钥匙写入secretKey.key文件中
		FileOutputStream fosKey = new FileOutputStream(FILE_BASE_PATH + "secretKey.key");
		ObjectOutputStream oosSecretKey = new ObjectOutputStream(fosKey);
		oosSecretKey.writeObject(key);
		oosSecretKey.close();
		fosKey.close();

		// 将加密的内容传递进去，返回加密后的二进制数据
		byte[] results = cipher.doFinal("哈哈哈哈哈".getBytes());

		// 将加密后的二进制数据写入到secretContent.dat文件中
		FileOutputStream fosData = new FileOutputStream(FILE_BASE_PATH + "secretContent.dat");
		fosData.write(results);
		fosData.close();
	}

	/**
	 * 对称解密
	 */
	@Test
	public void secretDecrypt() throws Exception {
		// 使用Cipher的实例
		Cipher cipher = Cipher.getInstance("AES");

		// 获取文件中的key进行解密
		FileInputStream fisKey = new FileInputStream(FILE_BASE_PATH + "secretKey.key");
		ObjectInputStream oisKey = new ObjectInputStream(fisKey);
		Key key = (Key) oisKey.readObject();
		oisKey.close();
		fisKey.close();

		// 初始化解密操作,传递加密的钥匙
		cipher.init(Cipher.DECRYPT_MODE, key); // ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE or UNWRAP_MODE

		// 获取文件中的二进制数据
		FileInputStream fisDat = new FileInputStream(FILE_BASE_PATH + "secretContent.dat");
		// 获取数据第一种方式 ---------------------将FileInputStream读进byte []
		byte [] src = new byte [fisDat.available()];
		fisDat.read(src);
		//执行解密
		byte [] result = cipher.doFinal(src);
		fisDat.close();
		System.out.println(new String(result));

		// 读文件中的数据第二种方式
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		copyStream(fisDat, baos);
//		byte[] result = cipher.doFinal(baos.toByteArray());
//		fisDat.close();
//		baos.close();
//		System.out.println(new String(result));
	}

	/**
	 * 复制流
	 * @param ips
	 * @param ops
	 * @throws Exception
	 */
	public void copyStream(InputStream ips, OutputStream ops) throws Exception {
		byte[] buf = new byte[1024];
		int len = ips.read(buf);
		while (len != -1) {
			ops.write(buf, 0, len);
			len = ips.read(buf);
		}
	}

	/*
	 * 	基于口令的对称加密与解密
		系统自动生成的Key不容易记忆，我们可以使用我们容易记忆的口令通过java自带的一个工具将它转换成Key，在解密的时候我们就可以通过口令进行解密。
	 */
	/** 
	 * 基于口令的对称加密 
	 */  
	@Test
	public void secretEncrypt1() throws Exception {  
	    //实例化工具  
	    Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");  
	      
	    //使用该工具将基于密码的形式生成Key  
	    //SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("123".toCharArray()));  //pwd
	    Key key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("123".toCharArray()));  //pwd
	    PBEParameterSpec parameterspec = new PBEParameterSpec(new byte[]{1,2,3,4,5,6,7,8},1000);  //salt
	      
	    //初始化加密操作，同时传递加密的算法  
	    cipher.init(Cipher.ENCRYPT_MODE,key,parameterspec);  
	      
	    //将要加密的数据传递进去，返回加密后的数据  
	    byte [] results = cipher.doFinal("哈哈哈哈哈aaa".getBytes());  
	      
	    //将加密后的数据写入到文件中  
	    FileOutputStream fosData=new FileOutputStream(FILE_BASE_PATH + "secretContent1.dat");  
	    fosData.write(results);  
	    fosData.close();  
	}  
	  
	/** 
	 * 基于口令的对称解密 
	 */  
	@Test
	public void secretDecrypt1() throws Exception{  
		//实例化工具  
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");  
	    
		//使用该工具将基于密码的形式生成Key  
		//SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("123".toCharArray()));  //pwd
		Key key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("123".toCharArray()));  //pwd
	    PBEParameterSpec parameterspec=new PBEParameterSpec(new byte[]{1,2,3,4,5,6,7,8},1000);  //salt
	    
	    cipher.init(Cipher.DECRYPT_MODE,key,parameterspec);  
	    FileInputStream fisDat=new FileInputStream(FILE_BASE_PATH + "secretContent1.dat");  
	    byte [] src=new byte [fisDat.available()];  
	    fisDat.read(src);  
	    byte [] result=cipher.doFinal(src);  
	    fisDat.close();  
	    System.out.println(new String(result));  
	}  
	
	
	/*
	 * 	非对称加密解密：
		非对称加密是公钥加密，私钥来解密，这个个人使用的少一点，主要针对于大型的网站大型的企业
	 */
	/** 
	 * 公钥加密 
	 */  
	@Test
	public void publicEnrypt()throws Exception {  
		//实例化工具  
		Cipher cipher = Cipher.getInstance("RSA");  
	    //实例化Key  
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
	    //获取一对钥匙  
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();  
	    //获得公钥  
	    PublicKey publicKey = keyPair.getPublic();  
	    //获得私钥   
	    PrivateKey privateKey = keyPair.getPrivate();  
	    //用公钥加密  
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
	    byte [] result=cipher.doFinal("传智播客".getBytes("UTF-8"));  
	    //将Key(私钥)写入到文件  
	    saveKey(privateKey,FILE_BASE_PATH + "private.key");  
	    //加密后的数据写入到文件  
	    saveData(result,FILE_BASE_PATH + "public_encryt.dat");  
	}  
	  
	/** 
	 * 私钥解密 
	 */  
	@Test
	public void privateDecrypt() throws Exception {  
		//实例化工具  
		Cipher cipher = Cipher.getInstance("RSA");  
	    //得到Key  
		PrivateKey privateKey = (PrivateKey) readKey(FILE_BASE_PATH + "private.key");  
	    //用私钥去解密  
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	    //读数据源  
	    byte [] src = readData(FILE_BASE_PATH + "public_encryt.dat");  
	    //得到解密后的结果  
	    byte[] result=cipher.doFinal(src);  
	    //二进制数据要变成字符串需解码  
	    System.out.println(new String(result,"UTF-8"));  
	}  
	  
	private void saveData(byte[] result, String fileName) throws Exception {  
	    FileOutputStream fosData=new FileOutputStream(fileName);  
	    fosData.write(result);  
	    fosData.close();  
	}  
	private void saveKey(Key key,String fileName)throws Exception{  
	    FileOutputStream fosKey=new FileOutputStream(fileName);  
	    ObjectOutputStream oosSecretKey =new ObjectOutputStream(fosKey);  
	    oosSecretKey.writeObject(key);  
	    oosSecretKey.close();  
	    fosKey.close();  
	}  
	private Key readKey(String fileName) throws Exception {  
	    FileInputStream fisKey=new FileInputStream(fileName);  
	    ObjectInputStream oisKey =new ObjectInputStream(fisKey);  
	    Key key=(Key)oisKey.readObject();  
	    oisKey.close();  
	    fisKey.close();  
	    return key;  
	}  
	//这个方法这样设计不合理，见testInputStream2ByteArray
	private byte[] readData(String filename) throws Exception {  
	    FileInputStream fisDat=new FileInputStream(filename);  
	    byte [] src=new byte [fisDat.available()];  
	    int len =fisDat.read(src);  
	    int total =0;  
	    while(total<src.length){  
	        total +=len;  
	        len=fisDat.read(src,total,src.length-total);  
	    }  
	    fisDat.close();  
	    return src;  
	}  

	
	/*
	 * 	数字签名：(要证明这段数据是你发过来的，并且没有被别人改过，而不是就将自己的数据加密而不被别人看到)
		数字签名的基础是公钥和私钥的非对称加密，发送者使用私钥加密的消息摘要(签名)，接收者使用公钥解密消息摘要以验证签名是否是某个人。
		要证明这段数据是你发过来的，并且没有被别人改过，这就需要用到数字签名，首先我们对整个文档进行md5加密得到16个字节，
		然后把消息摘要和文档(明文也可以是能解密的密文)发过去，解密者首先对发过来的文档进行解密，解密后得到一个摘要(md5)，对接收的文档进行md5加密，
		得到的md5结果匹配解密后的摘要，如果匹配成功的话证明没有修改过，我们使用Signature进行签名
	 */
	/*  
	 * 发送者使用私钥签名  
	 */   
	@Test
	public void sign()throws Exception {    
	    //实例化Key     
	    KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");    
	    //获取一对钥匙     
	    KeyPair keyPair = keyPairGenerator.generateKeyPair();    
	    //获得公钥     
	    PublicKey publicKey = keyPair.getPublic();    
	    //获得私钥      
	    PrivateKey privateKey = keyPair.getPrivate();    
	      
	    //数字签名  
	    Signature signature = Signature.getInstance("SHA1withRSA");  
	    signature.initSign(privateKey);//用私钥签名  
	    signature.update("这里签名".getBytes());//对怎样的数据进行签名  
	    byte [] sign = signature.sign();  //获取签名的结果  
	      
	    //保存公钥并写入文件中   
	    saveKey(publicKey,FILE_BASE_PATH + "public.key");    
	    //将签名后的数据写入到文件     
	    saveData(sign,FILE_BASE_PATH + "public_encryt.dat");    
	}  
	    
	/*  
	 * 接受者公钥解密  
	 */
	@Test
	public void verify() throws Exception {    
	    Signature signture = Signature.getInstance("SHA1withRSA");  
	    //获取到公钥  
	    PublicKey publicKey = (PublicKey)readKey(FILE_BASE_PATH + "public.key");  
	    //初始化校验  
	    signture.initVerify(publicKey);  
	    //初始化签名对象  
	    signture.update("这里签名".getBytes());  
	    //读数据源     
	    byte [] sign = readData(FILE_BASE_PATH + "public_encryt.dat");    
	    //返回匹配结果  
	    boolean isYouSigned = signture.verify(sign);  
	    //如果返回数据为true则数据没有发生修改，否则发生修改  
	    System.out.println(isYouSigned);  
	}   
	
	
	/**
	 * 测试将输入流读进数组
	 */
	@Test
	public void testInputStream2ByteArray() {
		try {
			String fileName = "1.jpg";
			FileInputStream fisDat = new FileInputStream(FILE_BASE_PATH + fileName);
			// 这样是不合理的，不能避免因一次读取太多的字节进入字节数组中而造成内存溢出
			// byte [] src = new byte
			// [fisDat.available()];//因为src本身的长度已经能容纳流中的所有字节了，
			// int len =
			// fisDat.read(src);//读取字节的个数就是src的长度，即一次能读取完，//read方法返回的是本次读入的字节个数，不会累加
			// int total = 0;
			// while(total < src.length){
			// //这里的while肯定执行一次
			// total +=len;
			// len = fisDat.read(src,total,src.length-total);
			// }
			// fisDat.close();

			// 可以这样实现
			// 将输入流读进字节数组
			byte[] src = new byte[fisDat.available()];
			int perLength = 2048;// 每次读取的最大长度
			int len;// 每次实际读取的长度
			int total = 0;// 一共读取的长度
			if (src.length >= perLength) {
				len = fisDat.read(src, total, perLength);
			} else {
				len = fisDat.read(src, total, src.length);
			}
			while (total < src.length) {
				total += len;
				if ((total + perLength) > src.length) {
					len = fisDat.read(src, total, src.length - total);
				} else {
					len = fisDat.read(src, total, perLength);
				}
			}
			fisDat.close();

			// 将字节数组写入输出流
			FileOutputStream fosDat = new FileOutputStream(FILE_BASE_PATH + "-" + fileName);
			fosDat.write(src);// Writes b.length bytes from the specified byte array to this file output stream.,输入流也可以这样一次读出来
			fosDat.flush();// 刷新输出流缓冲区，即将输出流中缓冲的数据全部写到目的地。
			fosDat.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
