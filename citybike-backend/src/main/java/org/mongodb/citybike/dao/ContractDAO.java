package org.mongodb.citybike.dao;

import javax.json.JsonArray;

import com.mongodb.BulkWriteResult;
import com.mongodb.DBCursor;

public interface ContractDAO {
	public BulkWriteResult saveAllContracts(JsonArray contracts);
	public DBCursor list();
}
