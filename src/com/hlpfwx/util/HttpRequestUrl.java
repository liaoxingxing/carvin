package com.hlpfwx.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
public class HttpRequestUrl {
	
	private static final String DEFAULT_CHARSET = "utf-8"; 
	
	public static void main(String[] args) {
		try {
			String path1 = "E:\\";  
	        String path2 = "E:\\1458652555773.amr";  
	        ToMp3(path1, path2);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ToMp3(String webroot, String sourcePath){  
        //File file = new File(sourcePath);  
        String targetPath = sourcePath+".mp3";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名  
        Runtime run = null;    
        try {    
            run = Runtime.getRuntime();    
            long start=System.currentTimeMillis();    
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            Process p=run.exec(webroot+"ffmpeg -i "+sourcePath+" -acodec libmp3lame "+targetPath);  
            p.waitFor();
            //释放进程    
            p.getOutputStream().close();    
            p.getInputStream().close();    
            p.getErrorStream().close();    
            long end=System.currentTimeMillis();    
            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");    
            //删除原来的文件    
            //if(file.exists()){    
                //file.delete();    
            //}    
        } catch (Exception e) {    
            e.printStackTrace();    
        }finally{    
            //run调用lame解码器最后释放内存    
            run.freeMemory();    
        }  
    }
	
	public static void downloadFile(String url,String filePath,String filename){  
		try {
			URL theURL = new URL(url);
			File dirFile = new File(filePath);
			if(!dirFile.exists())dirFile.mkdir();
			//从服务器上获取文件并保存
			URLConnection connection = theURL.openConnection();
			InputStream in = connection.getInputStream();  
			FileOutputStream os = new FileOutputStream(filePath+filename); 
			byte[] buffer = new byte[4 * 1024];  
			int read;  
			while ((read = in.read(buffer)) > 0) {  
				os.write(buffer, 0, read);  
			}  
			os.close();  
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 通过get 访问链接
	 */
	public static InputStream httpRequeseGet(String urlStr) {
		InputStream inputStream = null;
		try{
			URL url = new URL(urlStr);
			HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();
			httpURLConn.setDoOutput(true);
			httpURLConn.setRequestMethod("GET");
			httpURLConn.connect();
			inputStream = httpURLConn.getInputStream();
		}catch(Exception e){
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/*
	 * 将输入流转换成字符串
	 */
	public static String getStringFromFlow(InputStream inputStream) throws IOException{
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				jsonString = new String(outputStream.toByteArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonString;
	
	}
	
	public static String getHttpsurl(String url, String params) throws Exception {  
        String ctype = "application/json;charset=utf-8";  
        byte[] content = {};  
        if(params != null){  
            content = params.getBytes("utf-8");  
        }  
          
        return httpRequeset(url, ctype, content, 6000, 6000,"GET");  
    }
	
	public static String postHttpsurl(String url, String params) throws Exception {  
        String ctype = "application/json;charset=utf-8";  
        byte[] content = {};  
        if(params != null){  
            content = params.getBytes("utf-8");  
        }  
        return httpRequeset(url, ctype, content, 6000, 6000,"POST");  
    }
	
	public static String httpRequeset(String url, String params, String charset, int connectTimeout, int readTimeout,String method) throws Exception {  
        String ctype = "application/json;charset=" + charset;  
        byte[] content = {};  
        if(params != null){  
            content = params.getBytes(charset);  
        }  
          
        return httpRequeset(url, ctype, content, connectTimeout, readTimeout,method);  
    }
	
	public static String httpRequeset(String url, String ctype, byte[] content,int connectTimeout,int readTimeout,String method) throws Exception {  
	    HttpsURLConnection conn = null;  
	    OutputStream out = null;  
	    String rsp = null;  
	    try {  
	        try{  
	            SSLContext ctx = SSLContext.getInstance("TLSv1");  
	            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());  
	            SSLContext.setDefault(ctx);  
	
	            conn = getConnection(new URL(url), method, ctype);   
	            conn.setHostnameVerifier(new HostnameVerifier() {  
	                @Override  
	                public boolean verify(String hostname, SSLSession session) {  
	                    return true;  
	                }  
	            });  
	            conn.setConnectTimeout(connectTimeout);  
	            conn.setReadTimeout(readTimeout);  
	        }catch(Exception e){  
	            e.printStackTrace();
	            throw e;  
	        }  
	        try{  
	            out = conn.getOutputStream();  
	            out.write(content);  
	            rsp = getResponseAsString(conn);  
	        }catch(IOException e){  
	            e.printStackTrace();  
	            throw e;  
	        }  
	          
	    }finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	        if (conn != null) {  
	            conn.disconnect();  
	        }  
	    }  
	      
	    return rsp;  
	}  

	private static class DefaultTrustManager implements X509TrustManager {  
	
	    @Override  
	    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}  
	
	    @Override  
	    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}  
	
	    @Override  
	    public X509Certificate[] getAcceptedIssuers() {  
	        return null;  
	    }  
	
	}  
	  
	private static HttpsURLConnection getConnection(URL url, String method, String ctype)  
	        throws IOException {  
	    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
	    conn.setRequestMethod(method);  
	    conn.setDoInput(true);  
	    conn.setDoOutput(true);  
	    conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");  
	    conn.setRequestProperty("User-Agent", "stargate");  
	    conn.setRequestProperty("Content-Type", ctype);  
	    return conn;  
	}  
	
	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {  
	    String charset = getResponseCharset(conn.getContentType());  
	    InputStream es = conn.getErrorStream();  
	    if (es == null) {  
	        return getStreamAsString(conn.getInputStream(), charset);  
	    } else {  
	        String msg = getStreamAsString(es, charset);  
	        if (StringUtils.isEmpty(msg)) {  
	            throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());  
	        } else {  
	            throw new IOException(msg);  
	        }  
	    }  
	}  
	
	private static String getStreamAsString(InputStream stream, String charset) throws IOException {  
	    try {  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));  
	        StringWriter writer = new StringWriter();  
	
	        char[] chars = new char[256];  
	        int count = 0;  
	        while ((count = reader.read(chars)) > 0) {  
	            writer.write(chars, 0, count);  
	        }  
	
	        return writer.toString();  
	    } finally {  
	        if (stream != null) {  
	            stream.close();  
	        }  
	    }  
	}  
	
	private static String getResponseCharset(String ctype) {  
	    String charset = DEFAULT_CHARSET;  
	
	    if (!StringUtils.isEmpty(ctype)) {  
	        String[] params = ctype.split(";");  
	        for (String param : params) {  
	            param = param.trim();  
	            if (param.startsWith("charset")) {  
	                String[] pair = param.split("=", 2);  
	                if (pair.length == 2) {  
	                    if (!StringUtils.isEmpty(pair[1])) {  
	                        charset = pair[1].trim();  
	                    }  
	                }  
	                break;  
	            }  
	        }  
	    }  
	
	    return charset;  
	}  
}
