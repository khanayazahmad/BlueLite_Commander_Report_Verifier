package com.btpb.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;


import com.btpb.entity.AdvertisingSetRandomAddress;
import com.btpb.entity.ExtendedAdvertisingData;
import com.btpb.entity.ExtendedAdvertisingParams;
import com.btpb.entity.ExtendedScanResponseData;
import com.btpb.entity.ExtentedAdvertisingReport;
import com.btpb.entity.PeriodicAdvertisingData;
import com.btpb.entity.PeriodicAdvertisingParams;
import com.btpb.entity.PeriodicAdvertisingReport;
import com.btpb.entity.PeriodicAdvertisingSyncEstablishedEvent;

public class ReportValidator {
	
	private HashMap<String,ExtendedAdvertisingParams> extAdvParamSets;
	private HashMap<String,Boolean> checkSet;
	private HashMap<String,AdvertisingSetRandomAddress> advSetRandAddrSets;
	private HashMap<String,ExtendedAdvertisingData> extAdvDataSets;        
	private HashMap<String,PeriodicAdvertisingParams> perAdvParamSets;     
	private HashMap<String,PeriodicAdvertisingData> perAdvDataSets;        
	private HashMap<String,ExtendedScanResponseData> extScanRespDataSets;  
	private String advAddress;
	
	public ReportValidator(){
		
	}

	public void initReportValidator(){
		extAdvParamSets = new HashMap<String,ExtendedAdvertisingParams>();
		checkSet = new HashMap<String,Boolean>();
		advSetRandAddrSets = new HashMap<String,AdvertisingSetRandomAddress>();
		extAdvDataSets = new HashMap<String,ExtendedAdvertisingData>();
		perAdvParamSets = new HashMap<String,PeriodicAdvertisingParams>();
		perAdvDataSets = new HashMap<String,PeriodicAdvertisingData>();
		extScanRespDataSets = new HashMap<String,ExtendedScanResponseData>();
		advAddress = "";

	}
	
	public void extractParams(String templateFile){
		
		initReportValidator();
		ArrayList<String> templateFileLines = LogFileHandler.readFile(templateFile);

		
		for(int i = 0;i<templateFileLines.size();i++){
			
			if(templateFileLines.get(i).contains("<ADVADDR>")){
				advAddress = templateFileLines.get(i).substring(
						templateFileLines.get(i).indexOf(">")+1,
						templateFileLines.get(i).lastIndexOf("<"));
			}else if(templateFileLines.get(i).contains("<SET>")){
				while(!templateFileLines.get(++i).contains("</SET>")){
					
					
					String byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));

					String sid = byteDump.split(" ")[23];
					extAdvParamSets.put(sid, new ExtendedAdvertisingParams());
					extAdvParamSets.get(sid).populateParams(byteDump);
					i++;
					checkSet.put(sid, false);
					byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));
					if(!byteDump.trim().equals("")){
						advSetRandAddrSets.put(sid, new AdvertisingSetRandomAddress());
						advSetRandAddrSets.get(sid).populateParams(byteDump);
					}
					i++;
					
					byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));
					if(!byteDump.equals("")){
						extScanRespDataSets.put(sid, new ExtendedScanResponseData());
						extScanRespDataSets.get(sid).populateParams(byteDump);
					}
					i++;
					
					byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));
					if(!byteDump.equals("")){
						extAdvDataSets.put(sid, new ExtendedAdvertisingData());
						extAdvDataSets.get(sid).populateParams(byteDump);
					}
					i++;
					
					byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));
					if(!byteDump.equals("")){
						perAdvParamSets.put(sid, new PeriodicAdvertisingParams());
						perAdvParamSets.get(sid).populateParams(byteDump);
					}
					i++;
					
					byteDump = templateFileLines.get(i).substring(
							templateFileLines.get(i).indexOf(">")+1,
							templateFileLines.get(i).lastIndexOf("<"));
					if(!byteDump.equals("")){
						perAdvDataSets.put(sid, new PeriodicAdvertisingData());
						perAdvDataSets.get(sid).populateParams(byteDump);
					}
					
				}
				
			}
			
		}
		
	}
	
	public Boolean validateExtAdvReports(String templateFile, String scanLogFile){
		
		ArrayList<String> logLines = LogFileHandler.readFile(scanLogFile);
		ArrayList<ArrayList<ExtentedAdvertisingReport>> extAdvReports = LogFileHandler.extractExtAdvReportEvents(logLines);

		extractParams(templateFile);
		Integer corruptCount = 0,
				incompleteCount = 0,
				passCount = 0,  
				interruptCount = 0;
		
		for(int i=0;i< extAdvReports.size();i++){
			ArrayList<ExtentedAdvertisingReport> extendedAdvertisingReportSet = extAdvReports.get(i);
			String data = "";
			Integer dataLength = 0;
			Boolean match = true;
			String message = "";
			String color = "#71FF66";

			
			for(ExtentedAdvertisingReport extendedAdvertisingReport : extendedAdvertisingReportSet){
				data += " " + extendedAdvertisingReport.getData();
				dataLength += Integer.parseInt(extendedAdvertisingReport.getData_Length().replace("0x", ""),16);
				if(advAddress.equals(extendedAdvertisingReport.getAddress())
						&& checkSet.containsKey(extendedAdvertisingReport.getAdvertising_SID())){
					checkSet.replace(extendedAdvertisingReport.getAdvertising_SID(), true);

				}else{
					match = false;
					color = "#C2C2C2";
					message = "Set Not Present in Advertising Input";
					break;
				}
			}
			
			Integer extAdvDataLength;
			String extAdvData;

			if(extAdvDataSets.containsKey(extendedAdvertisingReportSet.get(0)
					.getAdvertising_SID())){
				extAdvDataLength = Integer.parseInt(
						extAdvDataSets.get(
								extendedAdvertisingReportSet.get(0)
									.getAdvertising_SID()
								).getAdvertising_Data_Length().replace("0x", ""),16
						);
				extAdvData = extAdvDataSets.get(
						extendedAdvertisingReportSet.get(0)
						.getAdvertising_SID()
						).getAdvertising_Data();
			
			}else if(extScanRespDataSets.containsKey(extendedAdvertisingReportSet.get(0)
					.getAdvertising_SID())){
				extAdvDataLength = Integer.parseInt(
						extScanRespDataSets.get(
								extendedAdvertisingReportSet.get(0)
									.getAdvertising_SID()
								).getAdvertising_Data_Length().replace("0x", ""),16
						);
				extAdvData = extScanRespDataSets.get(
						extendedAdvertisingReportSet.get(0)
						.getAdvertising_SID()
						).getAdvertising_Data();
				
			}else{
				extAdvDataLength = 0;
				extAdvData = "";
			}			
			if((dataLength.equals(extAdvDataLength))&&
					data.trim().equals(extAdvData)){
				

			}else if((dataLength==extAdvDataLength)&&
					!(data.trim().equals(extAdvData))){
				match = false;
				color = "#FF8080";
				message = "Corrupt Extended Adv Data";
			}else{

				if(match!=false && (dataLength>extAdvDataLength 
						&& extendedAdvertisingReportSet.get(extendedAdvertisingReportSet.size()-1).getEvent_Type().charAt(2)!='4')){
					match = false;
					color = "#FF8080";
					message = "Corrupt Extended Adv Data";
				}else if(match!=false && (dataLength<extAdvDataLength)
						&& extendedAdvertisingReportSet.get(extendedAdvertisingReportSet.size()-1).getEvent_Type().charAt(2)!='4'){
					match = false;
					color = "#FF8080";
					message = "Incomplete Extended Adv Data";
				}else if(match != false){
					if(!(dataLength.equals(extAdvDataLength))){
						match = false;
						color = "#89C0FF";
						message = "Interrupt received! Extended Adv Data Truncated";

					}else{
						if(extendedAdvertisingReportSet.size()!=1){
							ExtentedAdvertisingReport ear = extendedAdvertisingReportSet.remove(extendedAdvertisingReportSet.size()-1);
							extAdvReports.add(new ArrayList<ExtentedAdvertisingReport>());
							extAdvReports.get(extAdvReports.size()-1).add(ear);
						}
					}
				}
				
			}
			
			if(!match){
				
				LogFileHandler.markExtAdvReports(extendedAdvertisingReportSet,scanLogFile,message,color);
			}else{
				LogFileHandler.markExtAdvReports(extendedAdvertisingReportSet,scanLogFile,"Extended Adv Report Set Matched","#71FF66");

			}
  
			
		}
		
		int setNotReceivedCount = 0;
		String setNotReceived = "";

		for(String sid:checkSet.keySet()){
			
			if(checkSet.get(sid).equals(false)){
				setNotReceivedCount++;
				setNotReceived += sid+" ";
			}
			
		}
		
		logLines = LogFileHandler.readFile(scanLogFile);
		
		passCount = Collections.frequency(logLines, "<td>Extended Adv Report Set Matched</td>");
		interruptCount = Collections.frequency(logLines, "<td>Interrupt received! Extended Adv Data Truncated</td>");
		corruptCount = Collections.frequency(logLines, "<td>Corrupt Extended Adv Data</td>");
		incompleteCount = Collections.frequency(logLines, "<td>Incomplete Extended Adv Data</td>");
		
		String analysisReport = "<table width=\"100%\" style=\"font-size:20px; border: 1px solid black\">"+
								"<tr><th>Extended Advertising Analysis Report</th></tr>\r\n"+
								"<tr bgcolor=\"#EEEEE0\">\r\n"+
								"<td>Total </td>\r\n"+
								"<td>"+(passCount+corruptCount+incompleteCount+interruptCount)+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#71FF66\">\r\n"+
								"<td>Correct </td>\r\n"+
								"<td>"+passCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#FF8080\">\r\n"+
								"<td>Incomplete </td>\r\n"+
								"<td>"+incompleteCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#89C0FF\">\r\n"+
								"<td>Interrupted </td>\r\n"+
								"<td>"+interruptCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#FF8080\">\r\n"+
								"<td>Corrupt </td>\r\n"+
								"<td>"+corruptCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#C2C2C2\">\r\n"+
								"<td>Total sets missed </td>\r\n"+
								"<td>"+setNotReceivedCount+"</td>\r\n"+
								"</tr>\r\n";
								if(setNotReceivedCount!=0){
									analysisReport += "<tr bgcolor=\"#C2C2C2\">\r\n"+
								    "<td>SIDs not received </td>\r\n"+
								    "<td>"+setNotReceived+"</td>\r\n"+
								    "</tr>\r\n";
								}
								
								analysisReport += "</table>\r\n";
		
		
		logLines.add(logLines.size()-3, analysisReport);

		try {
			FileWriter fw = new FileWriter(new File(scanLogFile));
			fw.write(String.join("\r\n", logLines));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogFileHandler.jb.setValue(LogFileHandler.jb.getValue()+25); 

		return null;
		
	}
	
	
	public Boolean validatePerAdvReports(String templateFile, String scanLogFile){
		
		ArrayList<String> logLines = LogFileHandler.readFile(scanLogFile);
		HashMap<String,PeriodicAdvertisingSyncEstablishedEvent> perSyncEstEvent = LogFileHandler.extractPerSyncEstablishedEvents(logLines);
		ArrayList<ArrayList<PeriodicAdvertisingReport>> perAdvReports = LogFileHandler.extractPerAdvReportEvents(logLines);
		extractParams(templateFile);
		if(perAdvReports.size() == 0) return null;
		Integer corruptCount = 0, 
				passCount = 0,
				incompleteCount = 0,
				interruptCount = 0;
		
		for(int i=0;i< perAdvReports.size();i++){
			ArrayList<PeriodicAdvertisingReport> periodicAdvertisingReportSet = perAdvReports.get(i);
			String data = "";
			Integer dataLength = 0;
			Boolean match = true;
			String message = "";
			String color = "#71FF66";

			
			for(PeriodicAdvertisingReport periodicAdvertisingReport : periodicAdvertisingReportSet){
				data += " " + periodicAdvertisingReport.getData();
				dataLength += Integer.parseInt(periodicAdvertisingReport.getData_Length().replace("0x", ""),16);
				if(checkSet.containsKey(perSyncEstEvent.get(periodicAdvertisingReport.getSync_Handle()).getAdvertising_SID())){
					checkSet.replace(perSyncEstEvent.get(periodicAdvertisingReport.getSync_Handle()).getAdvertising_SID(), true);

				}else{
					match = false;
					color = "#C2C2C2";
					message = "Set Not Present in Advertising Input";
					break;
				}
			}
			
			Integer perAdvDataLength;
			String perAdvData;

			if(perAdvDataSets.containsKey(perSyncEstEvent.get(periodicAdvertisingReportSet.get(0)
					.getSync_Handle()).getAdvertising_SID())){
				perAdvDataLength = Integer.parseInt(
						perAdvDataSets.get(
								perSyncEstEvent.get(periodicAdvertisingReportSet.get(0)
										.getSync_Handle()).getAdvertising_SID()
								).getAdvertising_Data_Length().replace("0x", ""),16
						);
				perAdvData = perAdvDataSets.get(
						perSyncEstEvent.get(periodicAdvertisingReportSet.get(0)
								.getSync_Handle()).getAdvertising_SID()
						).getAdvertising_Data();
			
			}else{
				perAdvDataLength = 0;
				perAdvData = "";
			}			
			if((dataLength.equals(perAdvDataLength))&&
					data.trim().equals(perAdvData)){
				

			}else if((dataLength==perAdvDataLength)&&
					!(data.trim().equals(perAdvData))){
				match = false;
				color = "#E6B0AA";
				message = "Corrupt Periodic Data";
			}else{

				if(match!=false && (dataLength>perAdvDataLength 
						&& periodicAdvertisingReportSet.get(periodicAdvertisingReportSet.size()-1).getData_Status().charAt(3)!='2')){
					match = false;
					color = "#E6B0AA";
					message = "Corrupt Periodic Data";
				}else if(match!=false && (dataLength<perAdvDataLength 
						&& periodicAdvertisingReportSet.get(periodicAdvertisingReportSet.size()-1).getData_Status().charAt(3)!='2')){
					match = false;
					color = "#E6B0AA";
					message = "Incomplete Periodic Data";
				}else if(match != false){
					if(!(dataLength.equals(perAdvDataLength))){
						match = false;
						color = "#A9CCE3";
						message = "Interrupt received! Periodic Data Truncated";

					}else{
						if(periodicAdvertisingReportSet.size()!=1){
							PeriodicAdvertisingReport ear = periodicAdvertisingReportSet.remove(periodicAdvertisingReportSet.size()-1);
							perAdvReports.add(new ArrayList<PeriodicAdvertisingReport>());
							perAdvReports.get(perAdvReports.size()-1).add(ear);
						}
					}
				}
				
			}
			
			if(!match){
				
				LogFileHandler.markPerAdvReports(periodicAdvertisingReportSet,scanLogFile,message,color);
			}else{
				LogFileHandler.markPerAdvReports(periodicAdvertisingReportSet,scanLogFile,"Periodic Report Set Matched","#ABEBC6");

			}

			
      		
		}
		
		int setNotReceivedCount = 0;
		String setNotReceived = "";

		for(String sid:checkSet.keySet()){
			
			if(checkSet.get(sid).equals(false)){
				setNotReceivedCount++;
				setNotReceived += sid+" ";
			}
			
		}
		
		logLines = LogFileHandler.readFile(scanLogFile);
		
		passCount = Collections.frequency(logLines, "<td>Periodic Report Set Matched</td>");
		interruptCount = Collections.frequency(logLines, "<td>Interrupt received! Periodic Data Truncated</td>");
		corruptCount = Collections.frequency(logLines, "<td>Corrupt Periodic Data</td>");
		incompleteCount = Collections.frequency(logLines, "<td>Incomplete Periodic Data</td>");
		String analysisReport = "<table width=\"100%\" style=\"font-size:20px; border: 1px solid black\">"+
				                "<tr><th>Periodic Advertising Analysis Report</th></tr>\r\n"+
								"<tr bgcolor=\"#EEEEE0\">\r\n"+
								"<td>Total </td>\r\n"+
								"<td>"+(passCount+corruptCount+incompleteCount+interruptCount)+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#ABEBC6\">\r\n"+
								"<td>Correct </td>\r\n"+
								"<td>"+passCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#E6B0AA\">\r\n"+
								"<td>Incomplete </td>\r\n"+
								"<td>"+incompleteCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#A9CCE3\">\r\n"+
								"<td>Interrupted </td>\r\n"+
								"<td>"+interruptCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#E6B0AA\">\r\n"+
								"<td>Corrupt </td>\r\n"+
								"<td>"+corruptCount+"</td>\r\n"+
								"</tr>\r\n"+
								"<tr bgcolor=\"#C2C2C2\">\r\n"+
								"<td>Total sets missed </td>\r\n"+
								"<td>"+setNotReceivedCount+"</td>\r\n"+
								"</tr>\r\n";
								if(setNotReceivedCount!=0){
									analysisReport += "<tr bgcolor=\"#C2C2C2\">\r\n"+
								    "<td>SIDs not received </td>\r\n"+
								    "<td>"+setNotReceived+"</td>\r\n"+
								    "</tr>\r\n";
								}
								
								analysisReport += "</table>\r\n";
		
		
		logLines.add(logLines.size()-3, analysisReport);

		try {
			FileWriter fw = new FileWriter(new File(scanLogFile));
			fw.write(String.join("\r\n", logLines));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogFileHandler.jb.setValue(LogFileHandler.jb.getValue()+25); 

		return null;
		
	}	
	
}
