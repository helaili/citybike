package org.mongodb.citybike.dao;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;



public class BasicContractDAO extends BasicDAO implements ContractDAO {
	
	public BasicContractDAO() {
		super();
	}
	
	
	public BulkWriteResult saveAllContracts(JsonArray contracts) {
		BulkWriteOperation contractBulkUpserts = getContractCollection().initializeUnorderedBulkOperation();

		for(JsonValue contactValue : contracts) {
			JsonObject contact = (JsonObject)contactValue;
			DBObject contactBson = (DBObject) JSON.parse(contact.toString());
			contractBulkUpserts.find(new BasicDBObject("name", contact.getString("name"))).upsert().replaceOne(contactBson);
		}
		
		return contractBulkUpserts.execute();
	}
	
	public DBCursor list() {
		return getContractCollection().find();
	}
}
