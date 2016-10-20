package edu.brandeis.cs12b.pa8;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa8.Compress;
import edu.brandeis.cs12b.pa8.Decompress;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
	private static final String FILENAME = "testfile.dat";
	
	@Before
	public void removeFile() {
		File f = new File(FILENAME);
		if (f.exists())
			f.delete();
	}
	
	@Test
	public void simpleTest() throws IOException {
		String[] orders = new String[] {
				"Mary Stamey|Lowe's|125|852363|692400|47037|IN",
				"Vincent Garcia|Home Depot|580|484384|288388|28727|NC",
				"Francis Bernardi|Amazon|467|013882|333034|28092|NC",
				"Michael Gonzalez|Home Depot|563|441464|597782|96074|CA",
				"Brandon Allen|Metzger's Hardware|394|799559|374963|27698|NC",
				"Nathaniel Alexander|Metzger's Hardware|686|233730|729814|54564|WI",
				"Tanya Horton|Home Depot|379|572145|269657|98038|WA",
				"Jeffery Mugleston|Metzger's Hardware|145|557891|148273|50561|IA",
				"Maynard Roman|Amazon|11|635265|276609|37357|TN",
				"William Weber|Lowe's|194|871347|631738|29225|SC"
		};
		
		Compress c =  new Compress();
		Decompress d = new Decompress();
		
		c.compress(orders, FILENAME);
		String[] decompressed = d.decompress(new File(FILENAME));
		
		Arrays.sort(orders);
		Arrays.sort(decompressed);
		
		Assert.assertArrayEquals("Orders should match", orders, decompressed);
		
	}
	
	
	@Test
	public void smallTest() throws IOException {
		String[] orders = FileUtils.readLines(new File("small_dataset.txt")).toArray(new String[] {});
		
		Compress c = new Compress();
		Decompress d = new Decompress();
		
		c.compress(orders, FILENAME);
		String[] decompressed = d.decompress(new File(FILENAME));
		
		Arrays.sort(orders);
		Arrays.sort(decompressed);
		Assert.assertArrayEquals("Orders should match", orders, decompressed);

		
	}
}
