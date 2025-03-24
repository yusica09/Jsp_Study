package kr.util;

import java.io.File;

public class FileUtil {
	public static String getFilename(String headerName) {
		for(String name : headerName.split(";")) {
			if(name.trim().startsWith("filename")) {
				String filename = name.substring(name.indexOf("=")+1)
						              .trim()
						              .replace("\"", "");
				int index = filename.lastIndexOf(File.separator);
				return filename.substring(index+1);
			}
		}
		return null;
	}
}
