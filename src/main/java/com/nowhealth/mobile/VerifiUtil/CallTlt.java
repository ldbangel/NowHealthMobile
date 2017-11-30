package com.nowhealth.mobile.VerifiUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



/**
 * 请求通联通进行实名验证
 */
public class CallTlt {
	
	private static Provider prvd=null;
	private static final SSLHandler simpleVerifier=new SSLHandler();
	private static SSLSocketFactory sslFactory;
	//发送url,请求tlt
	public static String send(String url,String xml) throws Exception
	{
		OutputStream reqStream=null;
		InputStream resStream =null;
		URLConnection request = null;
		String respText=null;
		byte[] postData;
		try
		{
			postData = xml.getBytes("GBK");
			request = createRequest(url, "POST");
	
			request.setRequestProperty("Content-type", "application/tlt-notify");
			request.setRequestProperty("Content-length", String.valueOf(postData.length));
			request.setRequestProperty("Keep-alive", "false");
	
			reqStream = request.getOutputStream();
			reqStream.write(postData);
			reqStream.close();
	
			ByteArrayOutputStream ms = null;	
			resStream = request.getInputStream();
			ms = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int count;
			while ((count = resStream.read(buf, 0, buf.length)) > 0)
			{
				ms.write(buf, 0, count);
			}
			resStream.close();
			respText = new String(ms.toByteArray(), "GBK");
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			close(reqStream);
			close(resStream);
		}
		return respText;
	}
	
	//创建url连接
	private static URLConnection createRequest(String strUrl, String strMethod) throws Exception{
		URL url = new URL(strUrl);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conn instanceof HttpsURLConnection){
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			httpsConn.setRequestMethod(strMethod);
			httpsConn.setSSLSocketFactory(CallTlt.getSSLSF());
			httpsConn.setHostnameVerifier(CallTlt.getVerifier());
		}
		else if (conn instanceof HttpURLConnection){
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod(strMethod);
		}
		return conn;
	}
	//关闭输出流
	private static void close(OutputStream c){
		try{
			if(c!=null) c.close();
		}
		catch(Exception ex){
		}
	}
	//关闭输入流
	private static void close(InputStream c){
		try{
			if(c!=null) c.close();
		}
		catch(Exception ex){
		}
	}
	
	public static synchronized SSLSocketFactory getSSLSF() throws Exception	{
		if(sslFactory!=null) return sslFactory; 
		SSLContext sc = prvd==null?SSLContext.getInstance("TLS"):SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[]{simpleVerifier}, null);
		sslFactory = sc.getSocketFactory();
		return sslFactory;
	}
	
	public static HostnameVerifier getVerifier(){
		return simpleVerifier;
	}
	
	private static class SSLHandler implements X509TrustManager,HostnameVerifier{
		
		private SSLHandler(){
		
		}
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public boolean verify(String arg0, SSLSession arg1)
		{
			return true;
		}
	}
}
