package cn.harryai.toolkit.http;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	
	public final static String ARGS_TYPE_NAME_VALUE="nameValue";
	public final static String ARGS_TYPE_JSON="json";

	private static CloseableHttpClient httpClient=null;
	public static void setProxy(String hostname, int port) {
		HttpHost proxy = new HttpHost(hostname, port);
		httpClient= HttpClients.custom().setProxy(proxy).build();
		
	}
	/**
	 * 向服务器发送请求并获取返回结果
	 * 
	 * @param url
	 *            请求路径
	 * @param args
	 *            请求参数
	 * @param encoding
	 *            编码
	 * @return 服务器返回的结果
	 */
	public static String httpRequestGet(String url, Map<String, String> args, String encoding) {
		
		// 创建HttpClinet客户端
		if(httpClient==null) {
			httpClient = HttpClients.createDefault();
		}
		

		String argStr=null;
		try {
			argStr = EntityUtils.toString(convertMapToUrlEncodedFormEntity(args, encoding));
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		HttpGet get = initHttpGet(url, argStr, encoding);

		// 获取返回结果
		String resData = null;
		try {
			// 执行请求
			HttpResponse response = httpClient.execute(get);
			resData = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resData;

	}

	/**
	 * 向服务器发送请求并获取返回结果
	 * 
	 * @param url
	 *            请求路径
	 * @param args
	 *            请求参数
	 * @param method
	 *            请求的方法 （get/post）
	 * @param encoding
	 *            编码
	 * @param contentType
	 *            请求头
	 * @param argType
	 *            参数类型
	 * @return 服务器返回的结果
	 */
	public static String httpRequestPost(String url, Map<String, String> args, String argType, String encoding) {

		// 创建HttpClinet客户端
		// 创建HttpClinet客户端
		if(httpClient==null) {
			httpClient = HttpClients.createDefault();
		}

		//初始化HttpPost
		HttpPost post = initHttpPost(url, args,  encoding,argType);

		// 获取返回结果
		String resData = null;
		try {
			// 执行请求
			HttpResponse response = httpClient.execute(post);
			//获得结果
			resData = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resData;

	}

		
			
	

	/**
	 * 将参数map转换为UrlEncodedFormEntity对象
	 * 
	 * @param map
	 *            参数map
	 * @param encoding
	 *            编码
	 * @return
	 */
	private static UrlEncodedFormEntity convertMapToUrlEncodedFormEntity(Map<String, String> map, String encoding) {
		// 创建名字|值对的集合
		List<BasicNameValuePair> NameValuePairs = new ArrayList<BasicNameValuePair>();

		// 将map转换为NameValuePairs对象
		for (Map.Entry<String, String> e : map.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();

			NameValuePairs.add(new BasicNameValuePair(key, value));
		}

		try {
			//将参数转化为urlEncodingFormEntity对象
			UrlEncodedFormEntity entity= new UrlEncodedFormEntity(NameValuePairs, encoding);
//			entity.setContentType("application/x-www-form-urlencoded");
			entity.setContentEncoding(encoding);
			return entity;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return null;

	}

	/**
	 * 将map参数转换为StringEntity
	 * 主要用于json
	 * @param map
	 * @param encoding
	 * @return
	 */
	private static StringEntity convertMapToStringEntity(Map<String, String> map, String encoding) {

		String json = JSONArray.toJSONString(map);
		// 将json数据封装进参数实体
		StringEntity entity = new StringEntity(json, encoding);// 向服务端传参，避免乱码
		entity.setContentEncoding(encoding); // 向获取内容，避免乱码
		entity.setContentType("application/json");
		return entity;

	}

	/**
	 * 初始化HttpPost
	 * 
	 * @param url
	 *            请求路径
	 * @param argStr
	 *            参数 拼接字符串/json
	 * @param contentType
	 *            请求头
	 * @param encoding
	 *            编码
	 * @return HttpPost对象
	 */
	private static HttpPost initHttpPost(String url, Map<String,String> args, String encoding, String argType) {
		if(argType==null) {
			argType=ARGS_TYPE_NAME_VALUE;
		}
		if(encoding==null) {
			encoding="UTF-8";
		}
		
		HttpEntity entity=null;
		if(ARGS_TYPE_NAME_VALUE.equals(argType)) {
			entity=convertMapToUrlEncodedFormEntity(args, encoding);
			
			
		}else if(ARGS_TYPE_JSON.equals(argType)) {
			entity=convertMapToStringEntity(args, encoding);
		}
		
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);

		return post;

	}

	/**
	 * 初始化HttpGet
	 * 
	 * @param url
	 *            请求路径
	 * @param argStr
	 *            参数 拼接字符串
	 * @param encoding
	 *            编码
	 * @return HttpGet对象
	 */
	private static HttpGet initHttpGet(String url, String argStr, String encoding) {
		HttpGet get = new HttpGet(url + "?" + argStr);
		get.setHeader("Content-Type", "application/x-www-form-urlencoded;" + encoding);
		return get;
	}

}
