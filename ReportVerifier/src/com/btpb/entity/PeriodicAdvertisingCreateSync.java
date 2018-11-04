package com.btpb.entity;

public class PeriodicAdvertisingCreateSync extends CommandEvent {

	private String Filter_Policy;
	private String Advertising_SID;
	private String Advertiser_Address_Type;
	private String Advertiser_Address;
	private String Skip;
	private String Sync_Timeout;
	private String Unused;
	
	public PeriodicAdvertisingCreateSync(String filter_Policy, String advertising_SID, String advertiser_Address_Type,
			String advertiser_Address, String skip, String sync_Timeout, String unused) {
		super();
		Filter_Policy = filter_Policy;
		Advertising_SID = advertising_SID;
		Advertiser_Address_Type = advertiser_Address_Type;
		Advertiser_Address = advertiser_Address;
		Skip = skip;
		Sync_Timeout = sync_Timeout;
		Unused = unused;
	}
	
	public PeriodicAdvertisingCreateSync() {
		super();
	}

	public String getFilter_Policy() {
		return Filter_Policy;
	}
	public void setFilter_Policy(String filter_Policy) {
		Filter_Policy = filter_Policy;
	}
	public String getAdvertising_SID() {
		return Advertising_SID;
	}
	public void setAdvertising_SID(String advertising_SID) {
		Advertising_SID = advertising_SID;
	}
	public String getAdvertiser_Address_Type() {
		return Advertiser_Address_Type;
	}
	public void setAdvertiser_Address_Type(String advertiser_Address_Type) {
		Advertiser_Address_Type = advertiser_Address_Type;
	}
	public String getAdvertiser_Address() {
		return Advertiser_Address;
	}
	public void setAdvertiser_Address(String advertiser_Address) {
		Advertiser_Address = advertiser_Address;
	}
	public String getSkip() {
		return Skip;
	}
	public void setSkip(String skip) {
		Skip = skip;
	}
	public String getSync_Timeout() {
		return Sync_Timeout;
	}
	public void setSync_Timeout(String sync_Timeout) {
		Sync_Timeout = sync_Timeout;
	}
	public String getUnused() {
		return Unused;
	}
	public void setUnused(String unused) {
		Unused = unused;
	}
	
	public void populateParams(String byteDump){
		
		String params[] = byteDump.split(" ");
		
		Filter_Policy = params[0];
		Advertising_SID = params[1];
		Advertiser_Address_Type = params[2];
		Advertiser_Address = params[3] + " " + params[4] + " " + params[5] 
				+ " " + params[6] + " " + params[7] 
						+ " " + params[8];
		Skip = params[9] + " " + params[10];
		Sync_Timeout = params[11] + " " + params[12];
				
	}
	
}
