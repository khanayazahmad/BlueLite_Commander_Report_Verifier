package com.btpb.entity;

public class ExtendedScanResponseData extends CommandEvent {
	private String Advertising_Handle;
	private String Operation;
	private String Fragment_Preference;
	private String Scan_Response_Data_Length;
	private String Scan_Response_Data;
	
	public ExtendedScanResponseData(String advertising_Handle, String operation, String fragment_Preference,
			String advertising_Data_Length, String advertising_Data) {
		super();
		Advertising_Handle = advertising_Handle;
		Operation = operation;
		Fragment_Preference = fragment_Preference;
		Scan_Response_Data_Length = advertising_Data_Length;
		Scan_Response_Data = advertising_Data;
	}
	
	public ExtendedScanResponseData() {
		super();
	}

	public String getAdvertising_Handle() {
		return Advertising_Handle;
	}
	public void setAdvertising_Handle(String advertising_Handle) {
		Advertising_Handle = advertising_Handle;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public String getFragment_Preference() {
		return Fragment_Preference;
	}
	public void setFragment_Preference(String fragment_Preference) {
		Fragment_Preference = fragment_Preference;
	}
	public String getAdvertising_Data_Length() {
		return Scan_Response_Data_Length;
	}
	public void setAdvertising_Data_Length(String advertising_Data_Length) {
		Scan_Response_Data_Length = advertising_Data_Length;
	}
	public String getAdvertising_Data() {
		return Scan_Response_Data;
	}
	public void setAdvertising_Data(String advertising_Data) {
		Scan_Response_Data = advertising_Data;
	}

	public void populateParams(String byteDump) {
		
		String params[] = byteDump.split(" ");
		
		Advertising_Handle = params[0];
		Operation = params[1];
		Fragment_Preference = params[2];
		Scan_Response_Data_Length = params[3];
		Scan_Response_Data = params[4];
		Integer dataLength = Integer.parseInt(Scan_Response_Data_Length.replace("0x", ""),16);
		for(int i=0;i<dataLength-1;i++)
			Scan_Response_Data += " " + params[i+5];
				
		
	}
	

}
