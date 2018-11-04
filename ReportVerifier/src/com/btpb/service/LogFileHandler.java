package com.btpb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JProgressBar;

import com.btpb.entity.ExtentedAdvertisingReport;
import com.btpb.entity.PeriodicAdvertisingReport;
import com.btpb.entity.PeriodicAdvertisingSyncEstablishedEvent;



public class LogFileHandler {
	
	public static JProgressBar jb;

	public static ArrayList<String> readFile(String Path){
		
		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(Path)));
			Object o[] = br.lines().toArray();
			br.close();
			return (new ArrayList<String>(Arrays.asList(Arrays.copyOf(o, o.length, String[].class))));
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}
	
	public static ArrayList<ArrayList<String>> extractCommandsAndEvents(ArrayList<String> log){
		
		ArrayList<ArrayList<String>> commandEventList = new ArrayList<ArrayList<String>>();

		for(int i = 0;i<log.size();i++){
			if(log.get(i).contains("<tr")){
				commandEventList.add(new ArrayList<String>());
				while(!(log.get(++i).contains("</tr"))){
					if(log.get(i).startsWith("<td"))
						commandEventList.get(commandEventList.size()-1).add(log.get(i).substring(log.get(i).indexOf(">")+1, log.get(i).lastIndexOf("<")));
				}

				if(commandEventList.get(commandEventList.size()-1).size()<5)
					commandEventList.remove(commandEventList.size()-1);
			}
		}
		
		return commandEventList;
		
	}
	
	
	public static ArrayList<ArrayList<ExtentedAdvertisingReport>> extractExtAdvReportEvents(ArrayList<String> log){
		
		ArrayList<ArrayList<ExtentedAdvertisingReport>> extAdvReports = new ArrayList<ArrayList<ExtentedAdvertisingReport>>();
		ArrayList<ArrayList<String>> commandEventList = extractCommandsAndEvents(log);
		
		for(int i = 0;i<commandEventList.size();i++){

			if(commandEventList.get(i).get(3).contains("LE Extended Advertising Report")){
				
				extAdvReports.add(new ArrayList<ExtentedAdvertisingReport>());
				extAdvReports.get(extAdvReports.size()-1).add(new ExtentedAdvertisingReport());
				extAdvReports.get(extAdvReports.size()-1)
					.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
						.populateParams(commandEventList.get(i).get(5));
				
				extAdvReports.get(extAdvReports.size()-1)
					.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
						.setTime_Stamp(commandEventList.get(i).get(0));
				
				if(extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
							.getEvent_Type().charAt(2) == '4'||extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
							.getEvent_Type().charAt(2) == '0')
						continue;
				
				
				
				String advAddress = extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1).getAddress();
				String advSID = extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1).getAdvertising_SID();
				
				while(++i<commandEventList.size()&&!commandEventList.get(i).get(3).contains("LE Extended Advertising Report"))
					continue;
				if(i>=commandEventList.size())
					break;
				String params[] = commandEventList.get(i).get(5).split(" ");
				String eventType = params[2];
				String dataLength = params[25];
				String address = params[5] + " " + params[6] + " " + params[7] 
						+ " " + params[8] + " " + params[9] 
								+ " " + params[10];
				String sid = params[13];
				
				while(i < commandEventList.size()
						&& commandEventList.get(i).get(3).contains("LE Extended Advertising Report")
							&& !(eventType.charAt(2) == '2' 
								&& dataLength.equals("0xD9")) 
						    		&& address.equals(advAddress)
						       			&& sid.equals(advSID)){

					extAdvReports.get(extAdvReports.size()-1).add(new ExtentedAdvertisingReport());
					extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
							.populateParams(commandEventList.get(i).get(5));
					extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
							.setTime_Stamp(commandEventList.get(i).get(0));
					
					if(extAdvReports.get(extAdvReports.size()-1)
						.get(extAdvReports.get(extAdvReports.size()-1).size()-1)
							.getEvent_Type().charAt(2) == '4')
						break;
					
					
					while((++i)<commandEventList.size()&&!commandEventList.get(i).get(3).contains("LE Extended Advertising Report"))
						continue;
					if(i>=commandEventList.size())
						break;
					
					params = commandEventList.get(i).get(5).split(" ");
					eventType = params[2];
					dataLength = params[25];
					
					address = params[5] + " " + params[6] + " " + params[7] 
							+ " " + params[8] + " " + params[9] 
									+ " " + params[10];
					sid = params[13];
				}
				i--;
				
			}   
			
		}
		
		LogFileHandler.jb.setValue(LogFileHandler.jb.getValue()+20);

		return extAdvReports;
		
	}
	
	public static ArrayList<ArrayList<PeriodicAdvertisingReport>> extractPerAdvReportEvents(ArrayList<String> log){
		
		ArrayList<ArrayList<PeriodicAdvertisingReport>> perAdvReports = new ArrayList<ArrayList<PeriodicAdvertisingReport>>();
		ArrayList<ArrayList<String>> commandEventList = extractCommandsAndEvents(log);
		
		for(int i = 0;i<commandEventList.size();i++){
			
			if(commandEventList.get(i).get(3).contains("LE_Periodic_Advertising_Report")){
				
				perAdvReports.add(new ArrayList<PeriodicAdvertisingReport>());
				perAdvReports.get(perAdvReports.size()-1).add(new PeriodicAdvertisingReport());
				perAdvReports.get(perAdvReports.size()-1)
					.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
						.populateParams(commandEventList.get(i).get(5));
				
				perAdvReports.get(perAdvReports.size()-1)
					.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
						.setTime_Stamp(commandEventList.get(i).get(0));
				
				if(perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
							.getData_Status().charAt(3) == '2'||perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
							.getData_Status().charAt(3) == '0')
						continue;
				
				
				
;
				String syncHandle = perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1).getSync_Handle();
				
				
				while(++i<commandEventList.size()&&!commandEventList.get(i).get(3).contains("LE_Periodic_Advertising_Report"))
					continue;
				if(i>=commandEventList.size())
					break;
				String params[] = commandEventList.get(i).get(5).split(" ");
				String dataStatus = params[6];
				String dataLength = params[7];
				String syncH = params[1] + " " + params[2];
				
				while(i < commandEventList.size()
						&& !(dataStatus.charAt(3) == '1' 
								&& dataLength.equals("0xF0")) 
						    		&& syncH.equals(syncHandle)){

					perAdvReports.get(perAdvReports.size()-1).add(new PeriodicAdvertisingReport());
					perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
							.populateParams(commandEventList.get(i).get(5));
					perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
							.setTime_Stamp(commandEventList.get(i).get(0));
					
					if(perAdvReports.get(perAdvReports.size()-1)
						.get(perAdvReports.get(perAdvReports.size()-1).size()-1)
							.getData_Status().charAt(3) == '2')
						break;
					
					
					while(++i<commandEventList.size()&&!commandEventList.get(i).get(3).contains("LE_Periodic_Advertising_Report"))
						continue;
					if(i>=commandEventList.size())
						break;
					
					params = commandEventList.get(i).get(5).split(" ");
					dataStatus = params[6];
					dataLength = params[7];
					syncH = params[1] + " " + params[2];
				}
				i--;
				
			}

			
		}
		
		LogFileHandler.jb.setValue(LogFileHandler.jb.getValue()+15);

		return perAdvReports;
		
	}
	
	public static HashMap<String,PeriodicAdvertisingSyncEstablishedEvent> extractPerSyncEstablishedEvents(ArrayList<String> log){
		
		HashMap<String,PeriodicAdvertisingSyncEstablishedEvent> perSyncReports = new HashMap<String,PeriodicAdvertisingSyncEstablishedEvent>();
		ArrayList<ArrayList<String>> commandEventList = extractCommandsAndEvents(log);		
		
		for(int i = 0;i<commandEventList.size();i++){

			if(commandEventList.get(i).get(3).contains("LE_Periodic_Advertising_Sync_Established")){
				String byteDump = commandEventList.get(i).get(5);
				String syncHandle = byteDump.split(" ")[2]+" "+byteDump.split(" ")[3];
				perSyncReports.put(syncHandle,new PeriodicAdvertisingSyncEstablishedEvent());
				perSyncReports.get(syncHandle).populateParams(commandEventList.get(i).get(5));
				
			}
			
		}
		LogFileHandler.jb.setValue(LogFileHandler.jb.getValue()+10);		
		return perSyncReports;
		
	}




	public static void markExtAdvReports(ArrayList<ExtentedAdvertisingReport> set,
			String scanLogFile, String message, String color) {
		
		
		ArrayList<String> lines = readFile(scanLogFile);
		for(ExtentedAdvertisingReport report : set){
			
			for(int i = lines.size()-1;i>=0;i--){
				if(lines.get(i).contains(report.getTime_Stamp()) && lines.get(i+5).contains(report.getByte_Dump())){
					if(!lines.get(i-1).contains(color)&&!lines.get(i+6).contains(message)){//&&!lines.get(i-1).contains("89C0FF")&&!lines.get(i-1).contains("71FF66")
							lines.set(i-1, "<tr bgcolor=\""+color+"\">");
							if(set.indexOf(report)==(set.size()-1)){
								lines.add(i+6, "<td>"+message+"</td>");

							}
					}
				}
				
			}
				
				
		}
		
		try {
			FileWriter fw = new FileWriter(new File(scanLogFile));
			fw.write(String.join("\r\n", lines));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void markPerAdvReports(ArrayList<PeriodicAdvertisingReport> set,
			String scanLogFile, String message, String color) {
		
		
		ArrayList<String> lines = readFile(scanLogFile);
		for(PeriodicAdvertisingReport report : set){
			
			for(int i = lines.size()-1;i>=0;i--){
				if(lines.get(i).contains(report.getTime_Stamp()) && lines.get(i+5).contains(report.getByte_Dump())){
					if(!lines.get(i-1).contains(color)&&!lines.get(i+6).contains(message)){//&&!lines.get(i-1).contains("89C0FF")&&!lines.get(i-1).contains("71FF66")
							lines.set(i-1, "<tr bgcolor=\""+color+"\">");
							if(set.indexOf(report)==(set.size()-1)){
								lines.add(i+6, "<td>"+message+"</td>");

							}
					}
				}
				
			}
				
				
		}
		
		try {
			FileWriter fw = new FileWriter(new File(scanLogFile));
			fw.write(String.join("\r\n", lines));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
}
