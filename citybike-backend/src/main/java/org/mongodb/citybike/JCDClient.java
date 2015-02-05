package org.mongodb.citybike;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;

public class JCDClient {
	private String API_KEY = null;
	private String API_URI = null;
	private Client restClient = null;
	
	public JCDClient() {
		try {
			Properties apiProps = new Properties();
			FileInputStream in = new FileInputStream("api.properties");
			apiProps.load(in);

			API_KEY = apiProps.getProperty("key");
			API_URI = apiProps.getProperty("uri");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		restClient = ClientBuilder.newClient(new ClientConfig().register(JsonProcessingFeature.class));
	}
	
	
	public JsonArray getAllContracts() {
		try {
			WebTarget target = restClient.target(new URI(API_URI + "/contracts")).queryParam("apiKey", API_KEY);
			
			return target.request().get(JsonArray.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonObject getStationInformation(String contractName, int stationNumber) {
		try {
			WebTarget target = restClient.target(new URI(API_URI + "/stations/" + stationNumber)).queryParam("contract", contractName).queryParam("apiKey", API_KEY);
			
			return target.request().get(JsonObject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonArray getAllStations() {
		try {
			WebTarget target = restClient.target(new URI(API_URI + "/stations")).queryParam("apiKey", API_KEY);
			
			return target.request().get(JsonArray.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonArray getContractStations(String contractName) {
		try {
			WebTarget target = restClient.target(new URI(API_URI + "/stations")).queryParam("contract", contractName).queryParam("apiKey", API_KEY);
			
			return target.request().get(JsonArray.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}