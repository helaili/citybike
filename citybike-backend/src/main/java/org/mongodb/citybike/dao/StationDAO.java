package org.mongodb.citybike.dao;

import javax.json.JsonArray;

import com.mongodb.BulkWriteResult;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface StationDAO {
	public BulkWriteResult saveAllStations(JsonArray stations);
	public BulkWriteResult saveMonitoringData(JsonArray stations);
	public DBCursor getNearByStations(double longitude, double latitude, int radius);
	public DBCursor getStationsByContract(String contractId);
	public DBObject getStationsById(String stationId);
}
