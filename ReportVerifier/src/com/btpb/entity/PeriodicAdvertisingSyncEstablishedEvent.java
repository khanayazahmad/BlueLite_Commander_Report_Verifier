package com.btpb.entity;

public class PeriodicAdvertisingSyncEstablishedEvent extends CommandEvent {

	private String Subevent_Code;
	private String Status;
	private String Sync_Handle;
	private String Advertising_SID;
	private String Advertiser_Address_Type;
	private String Advertiser_Address;
	private String Advertiser_PHY;
	private String Periodic_Advertising_Interval;
	private String Advertiser_Clock_Accuracy;
	
	public PeriodicAdvertisingSyncEstablishedEvent(String subevent_Code, String status, String sync_Handle,
			String advertising_SID, String advertiser_Address_Type, String advertiser_Address, String advertiser_PHY,
			String periodic_Advertising_Interval, String advertiser_Clock_Accuracy) {
		super();
		Subevent_Code = subevent_Code;
		Status = status;
		Sync_Handle = sync_Handle;
		Advertising_SID = advertising_SID;
		Advertiser_Address_Type = advertiser_Address_Type;
		Advertiser_Address = advertiser_Address;
		Advertiser_PHY = advertiser_PHY;
		Periodic_Advertising_Interval = periodic_Advertising_Interval;
		Advertiser_Clock_Accuracy = advertiser_Clock_Accuracy;
	}
	
	public PeriodicAdvertisingSyncEstablishedEvent() {
		super();
	}

	public String getSubevent_Code() {
		return Subevent_Code;
	}
	public void setSubevent_Code(String subevent_Code) {
		Subevent_Code = subevent_Code;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSync_Handle() {
		return Sync_Handle;
	}
	public void setSync_Handle(String sync_Handle) {
		Sync_Handle = sync_Handle;
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
	public String getAdvertiser_PHY() {
		return Advertiser_PHY;
	}
	public void setAdvertiser_PHY(String advertiser_PHY) {
		Advertiser_PHY = advertiser_PHY;
	}
	public String getPeriodic_Advertising_Interval() {
		return Periodic_Advertising_Interval;
	}
	public void setPeriodic_Advertising_Interval(String periodic_Advertising_Interval) {
		Periodic_Advertising_Interval = periodic_Advertising_Interval;
	}
	public String getAdvertiser_Clock_Accuracy() {
		return Advertiser_Clock_Accuracy;
	}
	public void setAdvertiser_Clock_Accuracy(String advertiser_Clock_Accuracy) {
		Advertiser_Clock_Accuracy = advertiser_Clock_Accuracy;
	}
	
	public void populateParams(String byteDump){
		
		String params[] = byteDump.split(" ");
		Byte_Dump = byteDump;
		Subevent_Code = params[0];
		Status = params[1];
		Sync_Handle = params[2] + " " + params[3];
		Advertising_SID = params[4];
		Advertiser_Address_Type = params[5];
		Advertiser_Address = params[6] + " " + params[7] + " " + params[8] 
				+ " " + params[9] + " " + params[10] 
						+ " " + params[11];
		Advertiser_PHY = params[12];
		Periodic_Advertising_Interval = params[13] + " " + params[14];
		Advertiser_Clock_Accuracy = params[15];

	}

}
