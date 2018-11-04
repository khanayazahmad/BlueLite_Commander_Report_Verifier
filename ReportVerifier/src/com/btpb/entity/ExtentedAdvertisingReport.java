package com.btpb.entity;

public class ExtentedAdvertisingReport extends CommandEvent {
	
	
	private String Subevent_Code;
	private String Num_Reports;
	private String Event_Type;
	private String Address_Type;
	private String Address;
	private String Primary_PHY;
	private String Secondary_PHY;
	private String Advertising_SID;
	private String Tx_Power;
	private String RSSI;
	private String Periodic_Advertising_Interval;
	private String Direct_Address_Type;
	private String Direct_Address;
	private String Data_Length;
	private String Data;
	
	public ExtentedAdvertisingReport(String subevent_Code, String num_Reports, String event_Type, String address_Type,
			String address, String primary_PHY, String secondary_PHY, String advertising_SID, String tx_Power,
			String rSSI, String periodic_Advertising_Interval, String direct_Address_Type, String direct_Address,
			String data_Length, String data) {
		super();
		Subevent_Code = subevent_Code;
		Num_Reports = num_Reports;
		Event_Type = event_Type;
		Address_Type = address_Type;
		Address = address;
		Primary_PHY = primary_PHY;
		Secondary_PHY = secondary_PHY;
		Advertising_SID = advertising_SID;
		Tx_Power = tx_Power;
		RSSI = rSSI;
		Periodic_Advertising_Interval = periodic_Advertising_Interval;
		Direct_Address_Type = direct_Address_Type;
		Direct_Address = direct_Address;
		Data_Length = data_Length;
		Data = data;
	}
	
	public ExtentedAdvertisingReport() {
		super();
	}

	public String getSubevent_Code() {
		return Subevent_Code;
	}
	public void setSubevent_Code(String subevent_Code) {
		Subevent_Code = subevent_Code;
	}
	public String getNum_Reports() {
		return Num_Reports;
	}
	public void setNum_Reports(String num_Reports) {
		Num_Reports = num_Reports;
	}
	public String getEvent_Type() {
		return Event_Type;
	}
	public void setEvent_Type(String event_Type) {
		Event_Type = event_Type;
	}
	public String getAddress_Type() {
		return Address_Type;
	}
	public void setAddress_Type(String address_Type) {
		Address_Type = address_Type;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPrimary_PHY() {
		return Primary_PHY;
	}
	public void setPrimary_PHY(String primary_PHY) {
		Primary_PHY = primary_PHY;
	}
	public String getSecondary_PHY() {
		return Secondary_PHY;
	}
	public void setSecondary_PHY(String secondary_PHY) {
		Secondary_PHY = secondary_PHY;
	}
	public String getAdvertising_SID() {
		return Advertising_SID;
	}
	public void setAdvertising_SID(String advertising_SID) {
		Advertising_SID = advertising_SID;
	}
	public String getTx_Power() {
		return Tx_Power;
	}
	public void setTx_Power(String tx_Power) {
		Tx_Power = tx_Power;
	}
	public String getRSSI() {
		return RSSI;
	}
	public void setRSSI(String rSSI) {
		RSSI = rSSI;
	}
	public String getPeriodic_Advertising_Interval() {
		return Periodic_Advertising_Interval;
	}
	public void setPeriodic_Advertising_Interval(String periodic_Advertising_Interval) {
		Periodic_Advertising_Interval = periodic_Advertising_Interval;
	}
	public String getDirect_Address_Type() {
		return Direct_Address_Type;
	}
	public void setDirect_Address_Type(String direct_Address_Type) {
		Direct_Address_Type = direct_Address_Type;
	}
	public String getDirect_Address() {
		return Direct_Address;
	}
	public void setDirect_Address(String direct_Address) {
		Direct_Address = direct_Address;
	}
	public String getData_Length() {
		return Data_Length;
	}
	public void setData_Length(String data_Length) {
		Data_Length = data_Length;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	
	public String getTime_Stamp() {
		return Time_Stamp;
	}

	public void setTime_Stamp(String time_Stamp) {
		Time_Stamp = time_Stamp;
	}
	
	public String getByte_Dump() {
		return Byte_Dump;
	}

	public void populateParams(String byteDump){
		Byte_Dump = byteDump;
		String params[] = byteDump.split(" ");
		Subevent_Code = params[0];
		Num_Reports = params[1];
		Event_Type = params[2] + " " + params[3];
		Address_Type = params[4];
		Address = params[5] + " " + params[6] + " " + params[7] 
				+ " " + params[8] + " " + params[9] 
						+ " " + params[10];
		Primary_PHY = params[11];
		Secondary_PHY = params[12];
		Advertising_SID = params[13];
		Tx_Power = params[14];
		RSSI = params[15];
		Periodic_Advertising_Interval = params[16] + " " + params[17];
		Direct_Address_Type = params[18];
		Direct_Address = params[19] + " " + params[20] + " " + params[21] 
				+ " " + params[22] + " " + params[23] 
						+ " " + params[24];
		Data_Length = params[25];
		if(params.length > 26){
			Data = params[26];
				
			Integer dataLength = Integer.parseInt(Data_Length.replace("0x", ""),16);
			for(int i=0;i<dataLength-1;i++)
				Data += " " + params[i+27];
		}else{
			Data = "";
		}
				
	}

	
}
