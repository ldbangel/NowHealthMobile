package com.nowhealth.mobile.VerifiUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Security;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;


public class CryptUnderRestrict implements CryptInf{

	private boolean isConvertEncode = true;
	protected String lastResult;
	private String sessionMsg;
	protected String lastSignMsg;
	private boolean isTestServer = true;
	private String encoding;

	public String getLastSignMsg()
	{
		return this.lastSignMsg;
	}

	//数字签名需要用到
	public CryptUnderRestrict(String encoding){
		this.encoding = encoding;//（Security，BouncyCastleProvider为第三方jar包）
		Security.addProvider(new BouncyCastleProvider());
	}

	//数据签名方法返回一个boolen值
	public boolean SignMsg(String TobeSigned, String KeyFile, String PassWord) throws Exception{
	
		reset();
		//获取报文的字节数组
		byte[] plain = TobeSigned.getBytes(encoding);
		byte[] signed = (byte[]) null;

		RSADigestSigner signer = new RSADigestSigner(new SHA1Digest());
		//jar
		RSAPrivateCrtKeyParameters rsaParams = null;
		//方法2
		rsaParams = getRSAPrivateCrtKeyParameters(KeyFile, PassWord);

		signer.init(true, rsaParams);

		signer.update(plain, 0, plain.length);
		signed = signer.generateSignature();

		this.lastResult = new String(Hex.encode(signed));
		this.lastSignMsg = this.lastResult;

		return true;
	}
	
	//1.参数重置
	private void reset()
	{
		this.lastResult = null;
		this.lastSignMsg = null;
	}
	//2.数据签名所要用到
	private RSAPrivateCrtKeyParameters getRSAPrivateCrtKeyParameters(String pfxPath, String pfxPassword) throws Exception
	{
		PkiStore store = new PkiStore();
		//关于生成一个文件
		RSAPrivateCrtKeyStructure pkStructure = store.load(pfxPath, pfxPassword);
		
		BigInteger mod = pkStructure.getModulus();
		BigInteger pubExp = pkStructure.getPubExp();
		BigInteger privExp = pkStructure.getPriExp();
		BigInteger dP = pkStructure.getDP();
		BigInteger dQ = pkStructure.getDQ();
		BigInteger p = pkStructure.getP();
		BigInteger q = pkStructure.getQ();
		BigInteger qInv = pkStructure.getQInv();
		//RSAPrivateCrtKeyParameters  jar
		RSAPrivateCrtKeyParameters params = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, dP, dQ, qInv);

		return params;
	}
	
	
	//验证签名
	public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile) throws Exception
	{
		return VerifyMsg(TobeVerified, PlainText, CertFile, "SHA1withRSA");
	}
	//验证签名
	public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile, String algorithm) throws Exception
	{
		reset();

		byte[] plain = PlainText.getBytes(encoding);
		byte[] signed = Hex.decode(TobeVerified.getBytes(encoding));

		RSADigestSigner signer = null;
		if (algorithm.endsWith("SHA1withRSA"))
		{
			signer = new RSADigestSigner(new SHA1Digest());
		}
		else
		{
			throw new Exception("不支持SHA1withRSA");
		}

		RSAKeyParameters rsaParams = null;
		rsaParams = getRSAKeyParameters(CertFile);

		signer.init(false, rsaParams);
		signer.update(plain, 0, plain.length);

		return signer.verifySignature(signed);
	}
	//验证签名
	private RSAKeyParameters getRSAKeyParameters(String certPath) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = new FileInputStream(certPath);
		int bufSize = 4048;
		byte[] buffer = new byte[bufSize];
		while (in.available() > 0)
		{
			int readSize = in.read(buffer);

			out.write(buffer, 0, readSize);

			if (readSize < bufSize)
			{
				break;
			}
		}

		byte[] cert = out.toByteArray();

		PkiStore store = new PkiStore();
		RSAPublicKeyStructure pkStructure = store.load(certPath);
		BigInteger mod = pkStructure.getModulus();
		BigInteger pubExp = pkStructure.getPubExp();

		RSAKeyParameters params = new RSAKeyParameters(false, mod, pubExp);

		return params;
	}
	
	
	
	
	
	
	
	/*public boolean EncryptMsg(String TobeEncrypted, String CertFile) throws Exception
	{
		reset();

		byte[] plain = TobeEncrypted.getBytes(encoding);
		byte[] iv = "12345678".getBytes(encoding);

		RSAKeyParameters rsaParams = null;
		rsaParams = getRSAKeyParameters(CertFile);

		BigInteger mod = rsaParams.getModulus();

		int keylen = mod.bitLength() / 8;
		if (plain.length > keylen - 11)
		{
			SecureRandom securerandom = new SecureRandom();
			DESedeKeyGenerator desedeKeyGenerator = new DESedeKeyGenerator();
			desedeKeyGenerator.init(new KeyGenerationParameters(securerandom, 192));
			byte[] key = desedeKeyGenerator.generateKey();
			if (key.length != 24)
			{
				throw new Exception("密钥长度不为24,加密失败");
			}

			byte[] encryptedPlain = new byte[plain.length];
			DESedeEngine desede = new DESedeEngine();
			BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new OFBBlockCipher(desede, 8 * desede.getBlockSize()));
			CipherParameters desedeParams = new ParametersWithIV(new DESedeParameters(key), iv);
			bufferedBlockCipher.init(true, desedeParams);
			int outOff = bufferedBlockCipher.processBytes(plain, 0, plain.length, encryptedPlain, 0);
			bufferedBlockCipher.doFinal(encryptedPlain, outOff);
			byte[] encryptedKey = (byte[]) null;
			AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
			rsa.init(true, rsaParams);
			encryptedKey = rsa.processBlock(key, 0, key.length);
			this.lastResult = new String(Hex.encode(iv)) + new String(Hex.encode(encryptedKey)) + new String(Hex.encode(encryptedPlain));
		}
		else
		{
			byte[] encrypted = (byte[]) null;
			AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
			rsa.init(true, rsaParams);
			encrypted = rsa.processBlock(plain, 0, plain.length);
			this.lastResult = new String(Hex.encode(encrypted));
		}

		return true;
	}

	public boolean DecryptMsg(String TobeDecrypted, String KeyFile, String PassWord) throws Exception
	{
		reset();

		byte[] encrypted = TobeDecrypted.getBytes(encoding);

		RSAPrivateCrtKeyParameters rsaParams = null;
		rsaParams = getRSAPrivateCrtKeyParameters(KeyFile, PassWord);

		BigInteger mod = rsaParams.getModulus();

		int keylen = mod.bitLength() / 8;
		if (encrypted.length > keylen * 2)
		{
			byte[] iv_asc = TobeDecrypted.substring(0, 16).getBytes(encoding);
			byte[] key_encrypted_asc = TobeDecrypted.substring(iv_asc.length, iv_asc.length + keylen * 2).getBytes(encoding);
			byte[] enc_ascii = TobeDecrypted.substring(iv_asc.length + keylen * 2).getBytes(encoding);

			byte[] iv = Hex.decode(iv_asc);
			byte[] encryptedKey = Hex.decode(key_encrypted_asc);
			byte[] encryptedPlain = Hex.decode(enc_ascii);

			AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());

			rsa.init(false, rsaParams);

			byte[] key = (byte[]) null;
			key = rsa.processBlock(encryptedKey, 0, encryptedKey.length);

			DESedeEngine desede = new DESedeEngine();
			BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new OFBBlockCipher(desede, 8 * desede.getBlockSize()));

			byte[] decrypted = new byte[encryptedPlain.length];
			CipherParameters desedeParams = new ParametersWithIV(new KeyParameter(key), iv);
			bufferedBlockCipher.init(false, desedeParams);
			int i = bufferedBlockCipher.processBytes(encryptedPlain, 0, encryptedPlain.length, decrypted, 0);
			bufferedBlockCipher.doFinal(decrypted, i);
			this.lastResult = new String(decrypted);
		}
		else
		{
			byte[] encryptedPlain = Hex.decode(TobeDecrypted.getBytes(encoding));
			byte[] decrypted = (byte[]) null;

			AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
			rsa.init(false, rsaParams);
			decrypted = rsa.processBlock(encryptedPlain, 0, encryptedPlain.length);

			this.lastResult = new String(decrypted, encoding);
		}

		return true;
	}
	

	


	public String getLastResult()
	{
		return this.lastResult;
	}

	

	protected String replaceAll(String strURL, String strAugs)
	{
		int start = 0;
		int end = 0;
		String temp = new String();
		while (start < strURL.length())
		{
			end = strURL.indexOf(" ", start);
			if (end != -1)
			{
				temp = temp.concat(strURL.substring(start, end).concat("%20"));
				if (++end < strURL.length())
					continue;
				strURL = temp;
				break;
			}

			if (end == -1)
			{
				if (start == 0)
					break;
				if (start < strURL.length())
				{
					temp = temp.concat(strURL.substring(start, strURL.length()));
					strURL = temp;
					break;
				}
			}

		}

		temp = "";
		start = end = 0;

		while (start < strAugs.length())
		{
			end = strAugs.indexOf(" ", start);
			if (end != -1)
			{
				temp = temp.concat(strAugs.substring(start, end).concat("%20"));
				if (++end < strAugs.length())
					continue;
				strAugs = temp;
				break;
			}

			if (end == -1)
			{
				if (start == 0)
					break;
				if (start < strAugs.length())
				{
					temp = temp.concat(strAugs.substring(start, strAugs.length()));
					strAugs = temp;
					break;
				}

			}

		}

		return strAugs;
	}

	public String getSessionMsg()
	{
		return this.sessionMsg;
	}

	public void setSessionMsg(String sessionMsg)
	{
		this.sessionMsg = sessionMsg;
	}

	protected String getProperties(String key, String propFile)
	{
		ResourceBundle rb = null;
		if (this.isTestServer)
			rb = ResourceBundle.getBundle(propFile + "Test");
		else
		{
			rb = ResourceBundle.getBundle(propFile);
		}
		return rb.getString(key);
	}

	public boolean isTestServer()
	{
		return this.isTestServer;
	}

	public void setTestServer(boolean isTestServer)
	{
		this.isTestServer = isTestServer;
	}

	public boolean isConvertEncode()
	{
		return this.isConvertEncode;
	}

	public void setConvertEncode(boolean isConvertEncode)
	{
		this.isConvertEncode = isConvertEncode;
	}

	

*/
	
}