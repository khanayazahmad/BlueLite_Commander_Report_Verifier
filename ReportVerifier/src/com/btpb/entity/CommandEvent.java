package com.btpb.entity;

public abstract class CommandEvent {
	protected String Time_Stamp;
	protected String Byte_Dump;
	public abstract void populateParams(String byteDump);

}
