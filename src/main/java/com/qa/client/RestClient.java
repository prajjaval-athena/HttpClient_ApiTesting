package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	//1. GET method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException 
	{
		CloseableHttpClient httpClient=	HttpClients.createDefault(); // this create client connection
		HttpGet httpget = new HttpGet(url); // Get method object creation
		CloseableHttpResponse closeableHttpResponse;
		closeableHttpResponse = httpClient.execute(httpget); //executing/hitting get method by invoking get method object

		return closeableHttpResponse;
	}
	
	//2. POST method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpClient=	HttpClients.createDefault(); // this create client connection
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new StringEntity(entityString)); //passing payload or json post body 
		
		//for storing and passing headers
		for(Map.Entry<String, String> entry: headerMap.entrySet())
		{
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;
	}
}
