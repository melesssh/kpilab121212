package lab6;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lab6 {

	public static void main(String[] args) {
    	 File in = new File("D:\\Files\\inCrypt.txt"); 
    	 File out=new File("D:\\Files\\outCrypt.txt");
   	  try {
   		  FilterWriter writer= new CryptooWriter(new FileWriter(out));
   		  FilterReader reader=new CryptoReader(new FileReader(in));
    	  
    	  List<Integer> strs=new ArrayList();
    	  int st;
			while ((st = reader.read()) != -1) {
				strs.add(st);
				System.out.print((char)st);
			  }
			System.out.print("\n");
			strs.forEach(str->{
				try {
					System.out.print((char)(str>>1));
					writer.write(str);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			writer.flush();
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	static class CryptooWriter extends FilterWriter{

		protected CryptooWriter(Writer out) {
			super(out);
		}
		
	    public void write(int str) throws IOException {
    		super.write(str>>1);
	    }  
	}
	
	static class CryptoReader extends FilterReader {  
		
		CryptoReader(Reader in) {  
            super(in);  
        }  
        public int read() throws IOException {  
        	int read=super.read();
        	if(read==-1) {
        		return read;
        	}
            return read<<1;
        }
    }
	
}
