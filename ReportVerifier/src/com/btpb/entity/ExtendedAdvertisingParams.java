package com.btpb.entity;

public class ExtendedAdvertisingParams extends CommandEvent {
	
	private String Advertising_Handle;
	private String Advertising_Event_Properties;
	private String Primary_Advertising_Interval_Min;
	private String Primary_Advertising_Interval_Max;
	private String Primary_Advertising_Channel_Map;
	private String Own_Address_Type;
	private String Peer_Address_Type;
	private String Peer_Address;
	private String Advertising_Filter_Policy;
	private String Advertising_Tx_Power;
	private String Primary_Advertising_PHY;
	private String Secondary_Advertising_Max_Skip;
	private String Secondary_Advertising_PHY;
	private String Advertising_SID;
	private String Scan_Request_Notification_Enable;
	
	public ExtendedAdvertisingParams(String advertising_Handle, String advertising_Event_Properties,
			String primary_Advertising_Interval_Min, String primary_Advertising_Interval_Max,
			String primary_Advertising_Channel_Map, String own_Address_Type, String peer_Address_Type,
			String peer_Address, String advertising_Filter_Policy, String advertising_Tx_Power,
			String primary_Advertising_PHY, String secondary_Advertising_Max_Skip, String secondary_Advertising_PHY,
			String advertising_SID, String scan_Request_Notification_Enable) {
		super();
		Advertising_Handle = advertising_Handle;
		Advertising_Event_Properties = advertising_Event_Properties;
		Primary_Advertising_Interval_Min = primary_Advertising_Interval_Min;
		Primary_Advertising_Interval_Max = primary_Advertising_Interval_Max;
		Primary_Advertising_Channel_Map = primary_Advertising_Channel_Map;
		Own_Address_Type = own_Address_Type;
		Peer_Address_Type = peer_Address_Type;
		Peer_Address = peer_Address;
		Advertising_Filter_Policy = advertising_Filter_Policy;
		Advertising_Tx_Power = advertising_Tx_Power;
		Primary_Advertising_PHY = primary_Advertising_PHY;
		Secondary_Advertising_Max_Skip = secondary_Advertising_Max_Skip;
		Secondary_Advertising_PHY = secondary_Advertising_PHY;
		Advertising_SID = advertising_SID;
		Scan_Request_Notification_Enable = scan_Request_Notification_Enable;
		
		
	}

	public ExtendedAdvertisingParams() {
		super();
	}

	public String getAdvertising_Handle() {
		return Advertising_Handle;
	}

	public void setAdvertising_Handle(String advertising_Handle) {
		Advertising_Handle = advertising_Handle;
	}

	public String getAdvertising_Event_Properties() {
		return Advertising_Event_Properties;
	}

	public void setAdvertising_Event_Properties(String advertising_Event_Properties) {
		Advertising_Event_Properties = advertising_Event_Properties;
	}

	public String getPrimary_Advertising_Interval_Min() {
		return Primary_Advertising_Interval_Min;
	}

	public void setPrimary_Advertising_Interval_Min(String primary_Advertising_Interval_Min) {
		Primary_Advertising_Interval_Min = primary_Advertising_Interval_Min;
	}

	public String getPrimary_Advertising_Interval_Max() {
		return Primary_Advertising_Interval_Max;
	}

	public void setPrimary_Advertising_Interval_Max(String primary_Advertising_Interval_Max) {
		Primary_Advertising_Interval_Max = primary_Advertising_Interval_Max;
	}

	public String getPrimary_Advertising_Channel_Map() {
		return Primary_Advertising_Channel_Map;
	}

	public void setPrimary_Advertising_Channel_Map(String primary_Advertising_Channel_Map) {
		Primary_Advertising_Channel_Map = primary_Advertising_Channel_Map;
	}

	public String getOwn_Address_Type() {
		return Own_Address_Type;
	}

	public void setOwn_Address_Type(String own_Address_Type) {
		Own_Address_Type = own_Address_Type;
	}

	public String getPeer_Address_Type() {
		return Peer_Address_Type;
	}

	public void setPeer_Address_Type(String peer_Address_Type) {
		Peer_Address_Type = peer_Address_Type;
	}

	public String getPeer_Address() {
		return Peer_Address;
	}

	public void setPeer_Address(String peer_Address) {
		Peer_Address = peer_Address;
	}

	public String getAdvertising_Filter_Policy() {
		return Advertising_Filter_Policy;
	}

	public void setAdvertising_Filter_Policy(String advertising_Filter_Policy) {
		Advertising_Filter_Policy = advertising_Filter_Policy;
	}

	public String getAdvertising_Tx_Power() {
		return Advertising_Tx_Power;
	}

	public void setAdvertising_Tx_Power(String advertising_Tx_Power) {
		Advertising_Tx_Power = advertising_Tx_Power;
	}

	public String getPrimary_Advertising_PHY() {
		return Primary_Advertising_PHY;
	}

	public void setPrimary_Advertising_PHY(String primary_Advertising_PHY) {
		Primary_Advertising_PHY = primary_Advertising_PHY;
	}

	public String getSecondary_Advertising_Max_Skip() {
		return Secondary_Advertising_Max_Skip;
	}

	public void setSecondary_Advertising_Max_Skip(String secondary_Advertising_Max_Skip) {
		Secondary_Advertising_Max_Skip = secondary_Advertising_Max_Skip;
	}

	public String getSecondary_Advertising_PHY() {
		return Secondary_Advertising_PHY;
	}

	public void setSecondary_Advertising_PHY(String secondary_Advertising_PHY) {
		Secondary_Advertising_PHY = secondary_Advertising_PHY;
	}

	public String getAdvertising_SID() {
		return Advertising_SID;
	}

	public void setAdvertising_SID(String advertising_SID) {
		Advertising_SID = advertising_SID;
	}

	public String getScan_Request_Notification_Enable() {
		return Scan_Request_Notification_Enable;
	}

	public void setScan_Request_Notification_Enable(String scan_Request_Notification_Enable) {
		Scan_Request_Notification_Enable = scan_Request_Notification_Enable;
	}
	
	public void populateParams(String byteDump){
		
		String params[] = byteDump.split(" ");
				
		Advertising_Handle = params[0];
		Advertising_Event_Properties = params[1] + " " + params[2];
		Primary_Advertising_Interval_Min = params[3] + " " + params[4] + " " + params[5];
		Primary_Advertising_Interval_Max = params[6] + " " + params[7] + " " + params[8];
		Primary_Advertising_Channel_Map = params[9];
		Own_Address_Type = params[10];
		Peer_Address_Type = params[11];
		Peer_Address = params[12] + " " + params[13] + " " + params[14] 
				+ " " + params[15] + " " + params[16] 
						+ " " + params[17];
		Advertising_Filter_Policy = params[18];
		Advertising_Tx_Power = params[19];
		Primary_Advertising_PHY = params[20];
		Secondary_Advertising_Max_Skip = params[21];
		Secondary_Advertising_PHY = params[22];
		Advertising_SID = params[23];
		Scan_Request_Notification_Enable = params[24];

				
	}

}
