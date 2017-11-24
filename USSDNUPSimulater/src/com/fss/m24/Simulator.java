package com.fss.m24;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class Simulator {
	
	public static void main(String args[]){
		//Simulator simulator = new Simulator();
		//simulator.simulate("test","http://10.44.71.106:7001/mpayservices/hdfcussd");
	}

	public void simulate(String request,String posturl){
		
		System.out.println("inside build request method::"+request);
	
	try{
		
		String req= request;
		String record = "";

		URL url = new URL(posturl);

		Object obj;
		obj = (HttpURLConnection)url.openConnection();	//create a SSL connection object server-to-server
		((URLConnection)obj).setDoInput(true);
		((URLConnection)obj).setDoOutput(true);
		((URLConnection)obj).setUseCaches(false);
		((URLConnection)obj).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		if(req.length()>0)
		{
			
			OutputStreamWriter out = new OutputStreamWriter(((URLConnection)obj).getOutputStream());
			out.write(req);
			out.close();
				// Here the HTTPS request URL is created
				/*DataOutputStream dataoutputstream = new DataOutputStream(((URLConnection)obj).getOutputStream());
				dataoutputstream.writeBytes(req);	// here the request is sent to payment gateway
	           //	dataoutputstream.flush();
	            dataoutputstream.close();*/	//connection closed
	            System.out.println("Request posted:");

	            //Response is read
				BufferedReader br = new BufferedReader(new InputStreamReader(((URLConnection)obj).getInputStream()));

				while((record = br.readLine())!=null){
					System.out.println(record);
				}
				br.close();
		
		 }  
	}
		 catch (Exception e)  {
			 e.printStackTrace();
		 }
	}
}
