package com.casestudy.target;

public class TargetConstants {
	public final static String DEAULT_CURRENCY = "USD";
	public final static double DEAULT_PRICE = 0.0;
	
	// service messages
	public final static String SERVICE_MSG_RECORD_NOT_FOUND = "Record not found";
	public final static String SERVICE_MSG_CACHE_REFRESHED = "Cache was refreshed for ID: ";
	public final static String SERVICE_MSG_AGGREGATED_PRODUCT = "Aggregated Product: ";
	public final static String SERVICE_MSG_REDSKY_RETRIEVE = "Pulled product ID from the Redsky API: ";
	public final static String SERVICE_MSG_REDSKY_NOT_FOUND = "Product ID NOT found the Redsky API: ";
	public final static String SERVICE_MSG_NOSQL_RETRIEVE = "Access Price DB (NoSQL) for record: ";
	public final static String SERVICE_MSG_NOSQL_NOT_FOUND = "No record in DB (NoSQL) for id: ";
	public final static String SERVICE_MSG_NOSQL_PRICE_UPDATED = "Updated Price for record: ";
	
	// unit test
	public final static String UNIT_TEST_NAME = "Test";
	public final static int UNIT_TEST_ID = 1010101;
	public final static float UNIT_TEST_PRICE = (float) 100.0;
	public final static int UNIT_TEST_VALID_ID = 13860428;
}
