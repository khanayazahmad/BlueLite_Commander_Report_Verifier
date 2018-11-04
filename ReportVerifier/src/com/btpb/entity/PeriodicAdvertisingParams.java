package com.btpb.entity;

public class PeriodicAdvertisingParams extends CommandEvent {

	private String Advertising_Handle;
	private String Periodic_Advertising_Interval_Min;
	private String Periodic_Advertising_Interval_Max;
	private String Periodic_Advertising_Properties;
	
	public PeriodicAdvertisingParams(String advertising_Handle, String periodic_Advertising_Interval_Min,
			String periodic_Advertising_Interval_Max, String periodic_Advertising_Properties) {
		super();
		Advertising_Handle = advertising_Handle;
		Periodic_Advertising_Interval_Min = periodic_Advertising_Interval_Min;
		Periodic_Advertising_Interval_Max = periodic_Advertising_Interval_Max;
		Periodic_Advertising_Properties = periodic_Advertising_Properties;
	}

	public PeriodicAdvertisingParams() {
		super();
	}

	public String getAdvertising_Handle() {
		return Advertising_Handle;
	}

	public void setAdvertising_Handle(String advertising_Handle) {
		Advertising_Handle = advertising_Handle;
	}

	public String getPeriodic_Advertising_Interval_Min() {
		return Periodic_Advertising_Interval_Min;
	}

	public void setPeriodic_Advertising_Interval_Min(String periodic_Advertising_Interval_Min) {
		Periodic_Advertising_Interval_Min = periodic_Advertising_Interval_Min;
	}

	public String getPeriodic_Advertising_Interval_Max() {
		return Periodic_Advertising_Interval_Max;
	}

	public void setPeriodic_Advertising_Interval_Max(String periodic_Advertising_Interval_Max) {
		Periodic_Advertising_Interval_Max = periodic_Advertising_Interval_Max;
	}

	public String getPeriodic_Advertising_Properties() {
		return Periodic_Advertising_Properties;
	}

	public void setPeriodic_Advertising_Properties(String periodic_Advertising_Properties) {
		Periodic_Advertising_Properties = periodic_Advertising_Properties;
	}
	
	public void populateParams(String byteDump){
		
		String params[] = byteDump.split(" ");
				
		Advertising_Handle = params[0];
		Periodic_Advertising_Interval_Min = params[1] + " " + params[2];
		Periodic_Advertising_Interval_Max = params[3] + " " + params[4];
		Periodic_Advertising_Properties = params[5] + " " + params[6];
				
	}
	
}
