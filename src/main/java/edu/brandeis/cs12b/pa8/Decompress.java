package edu.brandeis.cs12b.pa8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.zip.InflaterInputStream;

public class Decompress extends Decompresser {

	public String[] decompress(File f) throws IOException {
		//read from file and inflate
		FileInputStream fis = new FileInputStream(f);
		InflaterInputStream inflate=new InflaterInputStream(fis);
		BufferedReader in = new BufferedReader(new InputStreamReader(inflate));
        String order = null;
        String[]orders=null;
        //decompress using my methods
        while ((order = in.readLine()) != null) {
        	order=addPrices(order);
        	order=addStates(order);
            order=addSellers(order);
            order=addIds(order);
            order=addBuyers(order);
            //split into array of orders
            orders=split(order);
        }
        in.close();
        //first value in orders is empty, so ordersFin removes this value
        String[]ordersFin=new String[orders.length-1];
        for (int i=1; i<orders.length; i++){
        	ordersFin[i-1]=orders[i];
        	ordersFin[i-1]=ordersFin[i-1].substring(0, ordersFin[i-1].length()-1);
        }
        return ordersFin;
	}

	//adds price to order based on ID (using map)
	private String addPrices(String order) {
		String[]s=order.split("(\n)|(\\|)");
		for (int i=3; i<s.length; i+=5){
			s[i]=s[i]+"|"+Compress.getPrice().get(s[i]);
		}
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<s.length; i++){
			builder.append(s[i]+"|");
		}
		return builder.toString();
	}
	
	//adds state to order based on zip (using ZipCodes.java)
	private String addStates(String order) throws IOException {
		String[]orderDetails=order.split("(\n)|(\\|)");
		for (int i=5; i<orderDetails.length; i+=6){
			String zip=orderDetails[i];
			ZipCodes codes=new ZipCodes();
			BufferedReader br = new BufferedReader(new StringReader(codes.getZips()));
			String line=null;
			//reads ZipCodes.java string
			while((line=br.readLine()) != null){
				if (line.contains(zip)){
					orderDetails[i]=orderDetails[i]+"|"+line.substring(line.length() - 2);
					break;
	    	    }
			}
		}
		StringBuilder builder = new StringBuilder();
		for (String value : orderDetails) {
		    builder.append(value+"|");
		}
		return builder.toString();
	}

	//decompresses seller names by getting full names from map
	private String addSellers(String order) {
		String[]s=order.split("(\n)|(\\|)");
		for (int i=1; i<s.length; i+=7){
			s[i]=Compress.getSells().get(s[i]);
		}
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<s.length; i++){
			builder.append(s[i]+"|");
		}
		return builder.toString();
	}
	
	//decompresses Ids by getting full IDs from map
	private String addIds(String order) {
		String[]s=order.split("(\n)|(\\|)");
		for (int i=3; i<s.length; i+=7){
			s[i]=Compress.getId().get(s[i]);
		}
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<s.length; i++){
			builder.append(s[i]+"|");
		}
		return builder.toString();
	}

	//decompresses buyer names by getting full names from map
	private String addBuyers(String order) {
		String[]s=order.split("(\n)|(\\|)");
		for (int i=0; i<s.length; i+=7){
			s[i]="\n"+Compress.getBuys().get(s[i]);
		}
		StringBuilder builder = new StringBuilder();
		for (String value : s) {
		    builder.append(value+"|");
		}
		return builder.toString();
	}
	
	//splits orders into array
	private String[] split(String order) {
		String[]spliti=order.split("\n");
		return spliti;
	}

}
