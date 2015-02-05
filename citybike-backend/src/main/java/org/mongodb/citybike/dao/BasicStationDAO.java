package org.mongodb.citybike.dao;

import java.util.Calendar;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;



public class BasicStationDAO extends BasicDAO implements StationDAO {
	
	public BasicStationDAO() {
		super();
	}
	
	
	@Override
	public BulkWriteResult saveAllStations(JsonArray stations) {
		BulkWriteOperation stationBulkUpserts = getStationCollection().initializeUnorderedBulkOperation();

		for(JsonValue stationValue : stations) {
			JsonObject station = (JsonObject)stationValue;
			
			
			BasicDBList coordinates = new BasicDBList();
			coordinates.add(station.getJsonObject("position").getJsonNumber("lng").doubleValue());
			coordinates.add(station.getJsonObject("position").getJsonNumber("lat").doubleValue());
			BasicDBObject position = new BasicDBObject("type", "Point").append("coordinates", coordinates);
					
			DBObject stationBson = (DBObject) JSON.parse(station.toString());
			stationBson.put("position", position);
			
			stationBulkUpserts.find(new BasicDBObject("number", station.getInt("number")).
											append("contract_name", station.getString("contract_name"))).upsert().replaceOne(stationBson);
		}
		
		return stationBulkUpserts.execute();
	}
	
	
	@Override
	public BulkWriteResult saveMonitoringData(JsonArray stations) {
		BulkWriteOperation stationBulkUpserts = getMonitoringCollection().initializeUnorderedBulkOperation();

		for(JsonValue stationValue : stations) {
			JsonObject station = (JsonObject)stationValue;
			
			Calendar calendar = Calendar.getInstance();
			String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			
			calendar.clear(Calendar.MILLISECOND);
			calendar.clear(Calendar.SECOND);
			calendar.clear(Calendar.MINUTE);
			calendar.clear(Calendar.HOUR);
			
			BasicDBObject monitoringData = new BasicDBObject("status", "OPEN").
													append("stands_total", station.getInt("bike_stands")).
													append("stands_available", station.getInt("available_bike_stands")).
													append("bikes", station.getInt("available_bikes"));
			
			
			stationBulkUpserts.find(new BasicDBObject("station", station.getInt("number")).
												append("contract", station.getString("contract_name")).
												append("date", calendar.getTime())).
							upsert().
							updateOne(new BasicDBObject("$set", new BasicDBObject("data", new BasicDBObject(hour, monitoringData ))));
		}
		
		return stationBulkUpserts.execute();
	}
	
	@Override
	public DBCursor getNearByStations(double longitude, double latitude, int radius) {
		BasicDBList coordinates = new BasicDBList();
		coordinates.add(longitude);
		coordinates.add(latitude);
		
		BasicDBObject query = new BasicDBObject("position", 
													new BasicDBObject("$near", 
															new BasicDBObject("$geometry", 
																			new BasicDBObject("type", "Point").
																			append("coordinates", coordinates)).
																		append("$maxDistance", radius)));
		
		return getStationCollection().find(query);
	}


	@Override
	public DBCursor getStationsByContract(String contractId) {
		DBObject contractDBObject = getContractCollection().findOne(new BasicDBObject("_id", new ObjectId(contractId)), 
																	new BasicDBObject("name", 1));
		return getStationCollection().find(new BasicDBObject("contract_name", contractDBObject.get("name")));	
	}


	@Override
	public DBObject getStationsById(String stationId) {
		return getStationCollection().findOne(new BasicDBObject("_id", new ObjectId(stationId)));
	}
	
	
}
