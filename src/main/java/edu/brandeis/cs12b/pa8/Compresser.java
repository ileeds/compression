package edu.brandeis.cs12b.pa8;

import java.io.IOException;

public abstract class Compresser {
	public abstract void compress(String[] orders, String filename) throws IOException;
}
