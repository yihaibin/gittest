package com.qyer.android.sns.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class SnsIOUtil {

	
	public static void closeInStream(InputStream input) 
	{
		try {
			if (input != null)
				input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeOutStream(OutputStream output)
	{
		try {
			if (output != null)
				output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void closeReader(Reader reader) 
	{
		try {

			if (reader != null)
				reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeWriter(Writer writer)
	{
		try {
			
			if (writer != null)
				writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
