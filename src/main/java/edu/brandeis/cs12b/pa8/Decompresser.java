package edu.brandeis.cs12b.pa8;

import java.io.File;
import java.io.IOException;

public abstract class Decompresser {
	public abstract String[] decompress(File f) throws IOException;

}
