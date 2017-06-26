package com.penghai.linker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class HTTPUtil {

	private static Logger log = Logger.getLogger(HTTPUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return string
	 * @throws Exception 
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		String jsonString = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
			// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
			// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
			// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			httpUrlConn.setDoOutput(true);
			// Read from the connection. Default is true.
			httpUrlConn.setDoInput(true);
			// Post 请求不能使用缓存
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				// connection.getOutputStream会隐含的进行 connect
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonString = buffer.toString();
		} catch (ConnectException ce) {
			log.error("connect timeout| " + ce.getCause().getMessage());
			throw new ConnectException();
		} catch (Exception e) {
			log.error("服务器异常| " + e.getCause().getMessage());
			throw new Exception();
		}
		return jsonString;
	}
	
	/**
	 * 调用医疗云系统接口
	 * 
	 * @param url
	 *            请求地址
	 * @return string
	 * @throws Exception 
	 * @author chenmin
	 */
	 public static String loadURL (String url) throws UnsupportedEncodingException{
	        StringBuilder json = new StringBuilder();
	        try {
	            URL urlObject = new URL(url);
	            URLConnection uc = urlObject.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
	            String inputLine = null;
	           if ((inputLine = in.readLine()) != null ) {
	           //   json.append("{");
	              json.append(inputLine);
	              
	              if ((inputLine = in.readLine())!= null) {
		        	   json.append(",ResultList:");
		        	   json.append(inputLine);
		           }
	           //   json.append("}");
	              System.out.println(json.toString());
	            }
	            in.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
//	        return URLEncoder.encode(json.toString(), "UTF-8");
	        return json.toString();
	    } 

	public static void main(String[] args) throws Exception {
//		String jsonData = httpRequest("http://192.168.1.102:8080/service/getOpenId", "POST", "userId=sui14444772904137483,sui14443689191947475");
//		Gson gson = new Gson();
//		
//		System.out.println(gson.fromJson(jsonData, OpenIdModel.class));
	}
}
