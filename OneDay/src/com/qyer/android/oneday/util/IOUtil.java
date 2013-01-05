package com.qyer.android.oneday.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {

	
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
	
	public static void writeObj(Object obj, File f)
	{
		ObjectOutputStream oos = null;
		try {
			
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(obj);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtil.closeOutStream(oos);
		}			
	}
	
	public static Object readObj(File f)
	{
		Object obj = null;
		ObjectInputStream oic = null;
		try {
			
			oic = new ObjectInputStream(new FileInputStream(f));
			obj = oic.readObject();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeInStream(oic);
		}
		return obj;			
	}
}
