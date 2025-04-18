package thrillio.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/*
 * The methods in this class were created when user data was read from a text file
 * 
 * This was done before reading the data from a SQL database and thus, ignoring the text files
 */
public class IOUtil {
	
	/**
	 * Method that reads user bookmark data from a text file
	 * @param data --> String array representing the data in the file
	 *             --> Each element of the data array corresponds to a single line
	 *             
	 * @param filename --> Text file to read
	 */
	public static void read(List<String> data, String filename)
	{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))){
			String line;
			
			while((line = br.readLine()) != null){
				data.add(line);
				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that reads data from the input stream
	 * 
	 * @param in --> InputStream source
	 *           --> More generic implementation (not limited to only files)
	 *           
	 * @return --> Bookmark data that is read from the input stream source
	 *             (i.e. remote host/network)
	 */
	public static String read(InputStream in) {
		
		StringBuilder text = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))){
			String line;
			
			//readLine() method removes new lines
			//append() method is used to re-add new lines
			while((line = br.readLine()) != null){
				text.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text.toString();
	}

	
	/**
	 * Method that writes data to the disk
	 * 
	 * @param webpage --> URL of the bookmark
	 * @param id --> Bookmark identifier
	 */
	public static void write(String webpage, long id) {
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/Users/astee/Documents/Essentials/Misc/Udemy/JavaInDepth/CourseProject/thrillio/pages/" + String.valueOf(id) + ".html"), "UTF-8"))){
			writer.write(webpage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
