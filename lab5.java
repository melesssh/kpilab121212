package lab5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class lab5 {
	 File firstFile;
	 File secondFIle;

	public static void main(String[] args) {
    	 File firstFile = new File("D:\\Files\\first.txt"); 
    	 File secondFIle=new File("D:\\Files\\second.txt");
    	 FileManager fileManager= new FileManager();
    	 List<String> strs=new ArrayList();
    	 fileManager.readFile(strs, firstFile);
    	 sortStrs(strs);
    	 fileManager.writeFIle(strs, secondFIle);
    	 }
	
	static void sortStrs(List<String> strs) {
		strs.sort((str1,str2)->str1.length()-str2.length());
	}
	
	public static class FileManager{
		
		public FileManager() {
			
		}
		
		public void readFile(List<String> strs, File file) {
		   	  try {
		    	  
		    	  BufferedReader br = new BufferedReader(new FileReader(file)); 
		    	  
		    	  String st;
					while ((st = br.readLine()) != null) {
						strs.add(st);
					  }
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		void writeFIle(List<String> strs, File file) {
		   	  try {
			        OutputStream os = new FileOutputStream(file);
					strs.forEach(str->{
						try {
							os.write(str.getBytes(), 0, str.length());
							os.write("\n".getBytes(), 0, "\n".length());
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
