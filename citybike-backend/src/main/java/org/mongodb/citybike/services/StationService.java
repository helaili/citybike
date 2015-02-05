package org.mongodb.citybike.services;

import java.io.StringReader;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.mongodb.citybike.JCDClient;
import org.mongodb.citybike.dao.StationDAO;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Path("/station")
public class StationService {
	JCDClient jcdc = new JCDClient();
	
	@Inject
	StationDAO stationDAO;
	
	@GET
	@Path("/load")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject loadStations() {
		JsonArray stations = jcdc.getAllStations();
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		if(stations != null) {
			stationDAO.saveAllStations(stations);
			
			job.add("status", "ok");
			job.add("count", stations.size());	
		} else {
			job.add("status", "null");
		}
		
		return job.build(); 
	}
	
	@GET
	@Path("/monitor")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject monitorStations() {
		JsonArray stations = jcdc.getAllStations();
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		if(stations != null) {
			stationDAO.saveMonitoringData(stations);
			
			job.add("status", "ok");
			job.add("count", stations.size());	
		} else {
			job.add("status", "null");
		}
		
		return job.build(); 
	}
	
	@GET
	@Path("/list/{contractId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray listStations(@PathParam("contractId")String contractId) {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		DBCursor cursor = stationDAO.getStationsByContract(contractId);
		while(cursor.hasNext()) {
			DBObject station = cursor.next();
			jab.add(Json.createReader(new StringReader(JSON.serialize(station))).readObject());
		}
		
		return jab.build(); 
	}
	
	@GET
	@Path("/{stationId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getStation(@PathParam("stationId")String stationId) {
		DBObject station = stationDAO.getStationsById(stationId);
		if(station != null) {
			return Json.createReader(new StringReader(JSON.serialize(station))).readObject();
		} else {
			return null;
		} 
	}
	
	@GET
	@Path("/geoSearch")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray geoSearchStations(@QueryParam("longitude")double longitude, @QueryParam("latitude")double latitude, @QueryParam("radius")int radius) {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		DBCursor cursor = stationDAO.getStationsByGeo(longitude, latitude, radius);
		while(cursor.hasNext()) {
			DBObject station = cursor.next();
			jab.add(Json.createReader(new StringReader(JSON.serialize(station))).readObject());
		}
		
		return jab.build(); 
	}
	
	@POST
	@Path("/textSearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	public JsonArray textSearchStations(String searchString) {
		JsonObject searchObject =  Json.createReader(new StringReader(searchString)).readObject();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		DBCursor cursor = stationDAO.getStationsByText(searchObject.getString("text"));
		while(cursor.hasNext()) {
			DBObject station = cursor.next();
			jab.add(Json.createReader(new StringReader(JSON.serialize(station))).readObject());
		}
		
		return jab.build(); 
	}
	
	
	
}
