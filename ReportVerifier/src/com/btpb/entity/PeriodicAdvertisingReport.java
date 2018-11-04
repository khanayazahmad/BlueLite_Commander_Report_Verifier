package com.btpb.entity;

public class PeriodicAdvertisingReport extends CommandEvent {
	
	private String Subevent_Code;
	private String Sync_Handle;
	private String Tx_Power;
	private String RSSI;
	private String Unused;
	private String Data_Status;
	private String Data_Length;
	private String Data;
	
	public PeriodicAdvertisingReport(String subevent_Code, String sync_Handle, String tx_Power, String rSSI,
			String unused, String data_Status, String data_Length, String data) {
		super();
		Subevent_Code = subevent_Code;
		Sync_Handle = sync_Handle;
		Tx_Power = tx_Power;
		RSSI = rSSI;
		Unused = unused;
		Data_Status = data_Status;
		Data_Length = data_Length;
		Data = data;
	}
	
	public PeriodicAdvertisingReport() {
		super();
	}

	public String getSubevent_Code() {
		return Subevent_Code;
	}
	public void setSubevent_Code(String subevent_Code) {
		Subevent_Code = subevent_Code;
	}
	public String getSync_Handle() {
		return Sync_Handle;
	}
	public void setSync_Handle(String sync_Handle) {
		Sync_Handle = sync_Handle;
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
	public String getUnused() {
		return Unused;
	}
	public void setUnused(String unused) {
		Unused = unused;
	}
	public String getData_Status() {
		return Data_Status;
	}
	public void setData_Status(String data_Status) {
		Data_Status = data_Status;
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

	public void populateParams(String byteDump){
		
		String params[] = byteDump.split(" ");
		Byte_Dump = byteDump;
		Subevent_Code = params[0];
		Sync_Handle = params[1] + " " + params[2];
		Tx_Power = params[3];
		RSSI = params[4];
		Data_Status = params[6];
		Data_Length = params[7];
		
		if(params.length > 8){				
			Data = params[8];
			
			Integer dataLength = Integer.parseInt(Data_Length.replace("0x", ""),16);
			for(int i=0;i<dataLength-1;i++)
				Data += " " + params[i+9];
			
		}else{
			Data = "";
		}
	}

	public CharSequence getByte_Dump() {
		// TODO Auto-generated method stub
		return Byte_Dump;
	}

}
