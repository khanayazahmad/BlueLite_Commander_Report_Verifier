package com.btpb.entity;

public class ExtendedAdvertisingData extends CommandEvent {
	
	private String Advertising_Handle;
	private String Operation;
	private String Fragment_Preference;
	private String Advertising_Data_Length;
	private String Advertising_Data;
	
	public ExtendedAdvertisingData(String advertising_Handle, String operation, String fragment_Preference,
			String advertising_Data_Length, String advertising_Data) {
		super();
		Advertising_Handle = advertising_Handle;
		Operation = operation;
		Fragment_Preference = fragment_Preference;
		Advertising_Data_Length = advertising_Data_Length;
		Advertising_Data = advertising_Data;
	}
	
	public ExtendedAdvertisingData() {
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
		return Advertising_Data_Length;
	}
	public void setAdvertising_Data_Length(String advertising_Data_Length) {
		Advertising_Data_Length = advertising_Data_Length;
	}
	public String getAdvertising_Data() {
		return Advertising_Data;
	}
	public void setAdvertising_Data(String advertising_Data) {
		Advertising_Data = advertising_Data;
	}

	public void populateParams(String byteDump) {
		
		String params[] = byteDump.split(" ");
		
		Advertising_Handle = params[0];
		Operation = params[1];
		Fragment_Preference = params[2];
		Advertising_Data_Length = params[3];
		Advertising_Data = params[4];
		Integer dataLength = Integer.parseInt(Advertising_Data_Length.replace("0x", ""),16);
		for(int i=0;i<dataLength-1;i++)
			Advertising_Data += " " + params[i+5];
				
		
	}
	

}
