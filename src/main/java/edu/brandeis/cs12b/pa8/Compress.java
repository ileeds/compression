package edu.brandeis.cs12b.pa8;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;

public class Compress extends Compresser {
	
	//static so only 1 instance at a time- can be retrieved from decompress.java
	static Map<String, String>buys=new HashMap<String,String>();
	static Map<String, String>sells=new HashMap<String,String>();
	static Map<String, String>id=new HashMap<String,String>();
	static Map<String, String>price=new HashMap<String,String>();
	
	public void compress(String[] orders, String filename) throws IOException {
		StringBuilder s = new StringBuilder();
		//break orders into lines
		for (String order:orders){
			s.append(order+"\n");
		}
		//compress using my methods
		StringBuilder t = removeStates(s);
		StringBuilder u = buyers(t);
		StringBuilder v = sellers(u);
		StringBuilder w = id(v);
		StringBuilder x = removePrices(w);
		//deflate
		FileOutputStream fos = new FileOutputStream(filename);
		DeflaterOutputStream deflate=new DeflaterOutputStream(fos);
		//write to file
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(deflate));
		out.append(x);
		out.close();
	}
	
	//removes prices from orders
	private StringBuilder removePrices(StringBuilder w) {
		String[]s=w.toString().split("(\n)|(\\|)");
		StringBuilder t = new StringBuilder();
		int j=4;
		for (int i=0; i<s.length; i++){
			if (i==j){
				//in price map, puts key=ID and value=price
				price.put(s[i-1], s[i]);
				j+=6;
			}else{
				t.append(s[i]+"|");
			}
		}
		return t;
	}

	//compresses ID to substring of ID
	private StringBuilder id(StringBuilder v) {
		String[]w=v.toString().split("(\n)|(\\|)");
		id=new HashMap<String,String>();
		StringBuilder x = new StringBuilder();
		int j=3;
		//in ID map, puts key=ID substring and value=full ID
		for (int i=0; i<w.length; i++){
			if (i==3){
				j+=6;
				id.put(w[i].substring(0, 1), w[i]);
				x.append(w[i].substring(0, 1)+"|");
			}else{
				if (i==j){
					j+=6;
					for (int l=1; l<w[i].length(); l++){
						if(id.containsKey(w[i].substring(0, l))){
							if (id.get(w[i].substring(0, l)).equals(w[i])){
								x.append(w[i].substring(0, l)+"|");
								break;
							}
						}else{
							id.put(w[i].substring(0, l), w[i]);
							x.append(w[i].substring(0, l)+"|");
							break;
						}
					}
				}else{
					x.append(w[i]+"|");
				}
			}
		}
		return x;
	}
	
	//compresses name of seller
	private StringBuilder sellers(StringBuilder u) {
		String[]v=u.toString().split("(\n)|(\\|)");
		sells=new HashMap<String,String>();
		StringBuilder w = new StringBuilder();
		int j=1;
		//in seller map, puts key=seller substring and value=full seller name
		for (int i=0; i<v.length; i++){
			if (i==1){
				j+=6;
				sells.put(v[i].substring(0, 1), v[i]);
				w.append(v[i].substring(0, 1)+"|");
			}else{
				if (i==j){
					j+=6;
					for (int l=1; l<v[i].length(); l++){
						if(sells.containsKey(v[i].substring(0, l))){
							if (sells.get(v[i].substring(0, l)).equals(v[i])){
								w.append(v[i].substring(0, l)+"|");
								break;
							}
						}else{
							sells.put(v[i].substring(0, l), v[i]);
							w.append(v[i].substring(0, l)+"|");
							break;
						}
					}
				}else{
					w.append(v[i]+"|");
				}
			}
		}
		return w;
	}

	//compresses name of buyer
	private StringBuilder buyers(StringBuilder t) {
		String[]u=t.toString().split("(\n)|(\\|)");
		buys=new HashMap<String,String>();
		StringBuilder v = new StringBuilder();
		int j=0;
		//in buyer map, puts key=buyer substring and value=full buyer name
		for (int i=0; i<u.length; i++){
			if (i==0){
				j+=6;
				buys.put(u[i].substring(0, 1), u[i]);
				v.append(u[i].substring(0, 1));
			}else{
				if (i==j){
					j+=6;
					for (int l=1; l<u[i].length(); l++){
						if(buys.containsKey(u[i].substring(0, l))){
							if (buys.get(u[i].substring(0, l)).equals(u[i])){
								v.append("|"+u[i].substring(0, l));
								break;
							}
						}else{
							buys.put(u[i].substring(0, l), u[i]);
							v.append("|"+u[i].substring(0, l));
							break;
						}
					}
				}else{
					v.append("|"+u[i]);
				}
			}
		}
		return v;
	}

	//removes state from order
	private StringBuilder removeStates(StringBuilder s) {
		String[]t=s.toString().split("(\n)|(\\|)");
		StringBuilder u = new StringBuilder();
		int j=6;
		for (int i=0; i<t.length; i++){
			if (i==j){
				j+=7;
			}else{
				u.append(t[i]+"|");
			}
		}
		return u;
	}

	//methods used to retrieve values from maps
	public static Map<String,String> getBuys(){
		return buys;
    }
	
	public static Map<String,String> getSells(){
		return sells;
    }
	
	public static Map<String, String> getId() {
		return id;
	}
	
	public static Map<String, String> getPrice() {
		return price;
	}

}