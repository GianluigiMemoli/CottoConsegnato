package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.util.codec.binary.Base64;

public class FileMaker {
	public FileMaker(String path, String encodedFile) throws IOException {
		
		byte[] fileInBASE64 = Base64.decodeBase64(encodedFile);		
		FileOutputStream stream = new FileOutputStream(path);
		stream.write(fileInBASE64);			
		stream.close();
	}
}
