package org.mongodb.citybike.dao;

import java.io.FileInputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public abstract class BasicDAO {
	private String dbName = null;
	private MongoClientURI clientUri = null;
	private MongoClient mongoClient = null;
	private DB db = null;
	private DBCollection contractCollection = null;
	private DBCollection stationCollection = null;
	private DBCollection monitoringCollection = null;
	
	private final static String CONTRACT_COLLECTION_NAME = "contracts";
	private final static String STATION_COLLECTION_NAME = "stations";
	private final static String MONITORING_COLLECTION_NAME = "monitoring";
	
	public BasicDAO() {
		super();

		try {
			Properties dbProps = new Properties();
			FileInputStream in = new FileInputStream("db.properties");
			dbProps.load(in);

			clientUri = new MongoClientURI(dbProps.getProperty("uri"));
			dbName = dbProps.getProperty("db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
	
	public MongoClient getMongoClient() {
		if (mongoClient == null) {
			try {
				mongoClient = new MongoClient(clientUri);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.exit(-2);
			}

		}
		return mongoClient;
	}
	
	public DB getDB() {
		if(db == null) {
			db = getMongoClient().getDB(dbName);
		}
		return db;
	}
	
	public DBCollection getContractCollection() {
		if(contractCollection == null) {
			return getDB().getCollection(CONTRACT_COLLECTION_NAME);
		}
		
		return contractCollection;
	}
	
	public DBCollection getStationCollection() {
		if(stationCollection == null) {
			return getDB().getCollection(STATION_COLLECTION_NAME);
		}
		
		return stationCollection;
	}
	
	public DBCollection getMonitoringCollection() {
		if(monitoringCollection == null) {
			return getDB().getCollection(MONITORING_COLLECTION_NAME);
		}
		
		return monitoringCollection;
	}
}
