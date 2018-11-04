package com.btpb.entity;

public class AdvertisingSetRandomAddress extends CommandEvent {
	
	private String Advertising_Handle;
	private String Random_Address;
	
	public AdvertisingSetRandomAddress(String advertising_Handle, String random_Address) {
		super();
		Advertising_Handle = advertising_Handle;
		Random_Address = random_Address;
	}

	public AdvertisingSetRandomAddress() {
		super();
	}

	public String getAdvertising_Handle() {
		return Advertising_Handle;
	}

	public void setAdvertising_Handle(String advertising_Handle) {
		Advertising_Handle = advertising_Handle;
	}

	public String getRandom_Address() {
		return Random_Address;
	}

	public void setRandom_Address(String random_Address) {
		Random_Address = random_Address;
	}
	
	public void populateParams(String byteDump) {
		
		String params[] = byteDump.split(" ");
		
		Advertising_Handle = params[0];
		Random_Address =params[1] + " " + params[2] + " " + params[3] 
				+ " " + params[4] + " " + params[5] 
						+ " " + params[6];
		
	}

}
