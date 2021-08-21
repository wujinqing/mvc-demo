package com.jin.mvc.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JavaDocHelper {

	public static void main(String[] args)throws Exception {
		genRes();
//		genReq();
		
//		System.out.println(stopDuration("70"));
	}
	
	public static String stopDuration(String stopDuration)
    {
            int i = Integer.valueOf(stopDuration);

            if(i < 60)
            {
                return i + "m";
            }else {
                int h = i/60;
                int m = i%60;
                return h + "h" + m + "m";
            }
        
    }
	public static void genReq() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(JavaDocHelper.class.getClassLoader().getResourceAsStream("fields.txt")));
	
	
		String buf = "";
		String docText = "";
		String fieldType = "";
		String fieldName = "";
		
		while(null != (buf = br.readLine()))
		{
			buf = buf.trim();
			
			if(buf.length() > 0)
			{
				if(buf.startsWith("*/") || buf.startsWith("@")  || buf.startsWith("/*"))
				{
					continue;
				}
				
				if(buf.startsWith("*"))
				{
					buf = buf.replace("*", "");
					buf = buf.trim();
					
					if(buf.length() > 0)
					{
						docText = buf;
					}
				}else
				{
					buf = buf.substring(0, buf.indexOf(";"));
					
					String[] arr = buf.split(" ");
					fieldType = arr[1];
					fieldName = arr[2];
					
					fieldType = fieldType.replace("<", "\\<").replace(">", "\\>");
					System.out.println("|"+fieldName+"|"+fieldType+"|是否|"+docText+"||");
//					System.out.println(docText  + fieldName); 
//					System.out.println("{field : '"+fieldName+"',title : '"+docText+"' ,width:80},"); 
				}
			}
		}
	}


	public static void genRes() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(JavaDocHelper.class.getClassLoader().getResourceAsStream("fields.txt")));
	
	
		String buf = "";
		String docText = "";
		String fieldType = "";
		String fieldName = "";
		
		while(null != (buf = br.readLine()))
		{
			buf = buf.trim();
			
			if(buf.length() > 0)
			{
				if(buf.startsWith("*/") || buf.startsWith("@")  || buf.startsWith("/*"))
				{
					continue;
				}
				
				if(buf.startsWith("*"))
				{
					buf = buf.replace("*", "");
					buf = buf.trim();
					
					if(buf.length() > 0)
					{
						docText = buf;
					}
				}else
				{
					buf = buf.substring(0, buf.indexOf(";"));
					
					String[] arr = buf.split(" ");
					fieldType = arr[1];
					fieldName = arr[2];
					
					fieldType = fieldType.replace("<", "\\<").replace(">", "\\>");
					System.out.println("|"+fieldName+"|"+fieldType+"|"+docText+"||");
//					System.out.println(docText + fieldType + fieldName); 
				}
			}
		}
	}


}
