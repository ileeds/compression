package edu.brandeis.cs12b.pa8;

import java.io.IOException;

public class Leaderboard {
	private static final String EMAIL_ADDRESS="ileeds@brandeis.edu";
	private static final String NICKNAME="lilil";
	
	// don't change this, or else you won't get a grade!
	private static final String KEY="student";
	
	public static void main(String[] args) throws IOException  {
		if (EMAIL_ADDRESS.equals("your email address") || NICKNAME.equals("your nickname")) {
			System.err.println("Please enter your email address and a public nickname in the Leaderboard.java file");
			return;
		}
		
		Compress c = new Compress();
		Decompress d = new Decompress();
		
		System.out.println("Checking your submission...");
		info.rmarcus.leaderboard.Leaderboard.submit(c, d, EMAIL_ADDRESS, NICKNAME, KEY);
		System.out.println("Done!");
		
	}
}
