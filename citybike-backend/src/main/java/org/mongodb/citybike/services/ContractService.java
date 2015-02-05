package org.mongodb.citybike.services;

import java.io.StringReader;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mongodb.citybike.JCDClient;
import org.mongodb.citybike.dao.ContractDAO;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Path("/contract")
public class ContractService {
	JCDClient jcdc = new JCDClient();
	
	@Inject
	ContractDAO contractDAO;
	
	
	@GET
	@Path("/load")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject loadContracts() {
		JsonArray contracts = jcdc.getAllContracts();
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		if(contracts != null) {
			contractDAO.saveAllContracts(contracts);
			
			job.add("status", "ok");
			job.add("count", contracts.size());	
		} else {
			job.add("status", "null");
		}
		
		return job.build(); 
	}
	
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray listContracts() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
			
		DBCursor cursor = contractDAO.list();
		while(cursor.hasNext()) {
			DBObject contract = cursor.next();
			jab.add(Json.createReader(new StringReader(JSON.serialize(contract))).readObject());
		}
		
		return jab.build(); 
	}

}
