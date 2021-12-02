package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class GetAPITest extends TestBase{
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
	public void getTest() throws ClientProtocolException, IOException 
	{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);	

		//a. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code-----> "+statusCode);
		Assert.assertEquals(statusCode, RESPONSE_CODE_200,"Status code not 200");
				
		//b. json string:
		String responseString = null;
		responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

		JSONObject responJson = new JSONObject(responseString); //parsing string as json
		System.out.println("Response Json from API------> "+responJson);
		
		
		System.out.println("value of per-page from json::::::"+responJson.query("/data/0/last_name"));
		
		//b.1. Asserting actual response with expected response
		String expResponse =prop.getProperty("getResponse"); // getting expected response from properties file
		JSONObject expJson = new JSONObject(expResponse); //parsing string as json 
		Assert.assertEquals(responJson.query("/data/0/last_name"), expJson.query("/data/0/last_name"), "Assertion failed -- response not matched with expected response");
	
		
		//c. all Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header: headersArray)
		{
			allHeaders.put(header.getName(),header.getValue());
		}
		System.out.println("Headers Array-----> "+allHeaders);
	}
}
