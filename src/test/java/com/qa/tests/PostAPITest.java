package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	TestBase testbase;
	String url;
	String serviceUrl;
	String apiUrl;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setup() throws ClientProtocolException, IOException
	{
		testbase = new TestBase();

		String serviceUrl=prop.getProperty("URL");
		String apiUrl= prop.getProperty("serviceURL");
		url =serviceUrl+apiUrl;
	}
	
	@Test
	public void postAPITest() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//Jackson API
		ObjectMapper mapper = new ObjectMapper(); //mapper class used to map the class as json
		Users users = Users.builder().name("Rohan Das").job("Teacher").build();
		
		String jsonString = mapper.writeValueAsString(users); //mapping users object as json body
		
		System.out.println("this is the values from user ::::: "+jsonString);
		
		//1. Sending POST request with URL, Json post body and Header
		closeableHttpResponse = restClient.post(url, jsonString, headerMap);
		
		//2. Getting status code
		System.out.println("Response status code : "+closeableHttpResponse.getStatusLine().getStatusCode());
		
		//3. Getting json response
		JSONObject jstring = new JSONObject(EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8"));
		System.out.println("Response Json String : "+jstring);
		
		
	}
}
