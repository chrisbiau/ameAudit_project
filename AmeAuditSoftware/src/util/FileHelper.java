package util;

import java.io.File;

import org.apache.log4j.Logger;

public class FileHelper {

	private static Logger logger = Logger.getLogger(FileHelper.class);
	
	public static boolean testFileExist(String pathname){
		File f = new File(pathname);
		return f.exists();
	}

	public static boolean testFileIsWritable(String pathname){
		File f = new File(pathname);
		return f.canWrite();
	}
	
	



}
