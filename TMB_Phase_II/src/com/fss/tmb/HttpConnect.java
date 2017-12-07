package com.fss.tmb;

/****
 * @author sivagovindan
 * @date : 04-10-2013 07;30 pm
 */

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;


public class HttpConnect extends FragmentActivity{
	ProgressDialog pd;
	public Context context;
	private String request;
	private String APGTERROR = "GT00FAIL";
	private String APCGERROR = "CG00FAIL";
	/** Called when the activity is first created. */
	 public HttpConnect(String request,Context context) {	
		 this.context = context;
		 this.request = request;
		 try {
	        if(StaticStore.index == 220 && (StaticStore.tagType.equals("APOG") || StaticStore.tagType.equals("APBO") || StaticStore.tagType.equals("APOV"))){
	           	StaticStore.regenerateClicked = false;
	           	StaticStore.LogPrinter('i',"Came inside the APOG and APOV "+StaticStore.tempMobileNo);
	           	StaticStore.LogPrinter('i',"Before Sending Request == >"+StaticStore.GprsServiceUrl + StaticStore.servlet+"?request=" + StaticStore.tempMobileNo +request);
	           	StaticStore.LogPrinter('i',"Before Sending Request == >"+request);
	             new getURLData().execute(StaticStore.GprsServiceUrl + StaticStore.servlet+"?request=" + StaticStore.tempMobileNo +URLEncoder.encode(request));
	        }else{
	            StaticStore.LogPrinter('i',"Came inside the else part "+StaticStore.myMobileNo);
	            StaticStore.LogPrinter('i',"Before Sending Request == >"+StaticStore.GprsServiceUrl + StaticStore.servlet+"?request=" + StaticStore.myMobileNo +request);
	            StaticStore.LogPrinter('i',"Before Sending Request == >"+request);
	            new getURLData().execute(StaticStore.GprsServiceUrl + StaticStore.servlet+"?request=" + StaticStore.myMobileNo +URLEncoder.encode(request));
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
	 }
	 
	 public class getURLData extends AsyncTask<String, Integer, String>{
		 @Override
			protected void onPreExecute() {

			}
		 
		 @Override
		 protected String doInBackground(String... params) {
			 String line = "";
			 Message msg = Message.obtain();
			 try {  
				 
			 /*FOR HTTP START*/
				 
				 DefaultHttpClient httpClient = new DefaultHttpClient();
				 
			 /*FOR HTTP END*/
				 
			 /*FOR HTTPS START*/
				 
//				 SchemeRegistry schemeRegistry = new SchemeRegistry();
//				 schemeRegistry.register(new Scheme("https",SSLSocketFactory.getSocketFactory(), 443));
//
//				 HttpParams httpParams = new BasicHttpParams();
//				 SingleClientConnManager mgr = new SingleClientConnManager(httpParams, schemeRegistry);
//				 DefaultHttpClient httpClient = new DefaultHttpClient(mgr, httpParams);

			  /*FOR HTTPS END*/
				 
//				 System.out.println("REQUEST FROM ANDROID APP >>>>>>>>"+request);
				 
				 HttpPost httppost = new HttpPost(params[0]);
				 HttpResponse httpResponse = httpClient.execute(httppost);
				 int resCode = httpResponse.getStatusLine().getStatusCode();
				 if (resCode == HttpURLConnection.HTTP_OK) {
					 HttpEntity httpEntity = httpResponse.getEntity();
					 line = EntityUtils.toString(httpEntity);
					 if(line.equals("") || line == null){
						 line = "200_Error";	 
					 }
				 }else{
					 line = "Otherthan_200_Error";
				 }
			 } catch (Exception e){
				 if(request.startsWith("APGT")) {
					 line = APGTERROR;
				 }else if(request.startsWith("APCG")) {
					 line = APCGERROR;
				 }else {
					 line = "";
				 }
				 e.printStackTrace();
			 }
			 if(!StaticStore.isProgressBarClosed){
				 StaticStore.midlet.isProcessing = true;
			 }
			 Bundle b = new Bundle();
			 b.putString("text", line);
			 msg.setData(b);
			 messageHandler.sendMessage(msg);
			 return line;
		 }
    }

	  public void showProgressDialog() {
	        if (pd == null || !pd.isShowing()) {
	            pd = new ProgressDialog(context);
	            pd.setIndeterminate(true);
	            pd.setTitle("TMB mConnect");
	            pd.setMessage("Processing your request...");
	            pd.setCancelable(false);
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	    }

	  public void dismissProgressDialog() {
	      if (pd != null && pd.isShowing()){
	            pd.dismiss();
	            StaticStore.isProgressBarClosed = true;
	            StaticStore.midlet.isProcessing = true;
	      }
	  }
		
	  private Handler messageHandler = new Handler() {
			public void handleMessage(Message msg) {
				try {					
					StaticStore.responseMsg = ""+msg.getData().getString("text");	
					try{
						StaticStore.messageReceived = true;
						StaticStore.dialog.cancel();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					StaticStore.isAirline = false;
					StaticStore.isNoInput = false;
					if((StaticStore.responseMsg != null && !StaticStore.responseMsg.equals(APCGERROR) && !StaticStore.responseMsg.equals(APGTERROR) &&
							!StaticStore.responseMsg.equalsIgnoreCase("null") && !StaticStore.responseMsg.equalsIgnoreCase("") &&
							StaticStore.responseMsg.startsWith(StaticStore.SentRequest) && !StaticStore.isProgressBarClosed)){
						Intent responseIntent = StaticStore.midlet.notifyIncomingMessage(StaticStore.context,StaticStore.responseMsg);
						StaticStore.midlet.startFragment(StaticStore.context,responseIntent);
					}else{
						Intent intent = GetFailureMessage();
						if(intent == null) {
							StaticStore.midlet.showScreen(StaticStore.context,StaticStore.responseMsg);
						}else{
							StaticStore.midlet.startFragment(StaticStore.context,intent);
						}
					}	
				} catch (Exception e) {
					e.printStackTrace();
					Intent intent = GetFailureMessage();
					if(intent == null) {
						StaticStore.midlet.showScreen(context,StaticStore.responseMsg);
					}else{
						StaticStore.midlet.startFragment(context,intent);
					}
				}
			}
		};
		
		public Intent GetFailureMessage(){
			// TODO Auto-generated method stub
			Intent intent = null;
			if(StaticStore.responseMsg == null || StaticStore.responseMsg.equalsIgnoreCase("") ||
					StaticStore.responseMsg.equalsIgnoreCase("null")) {							
					StaticStore.fromGprs = true;
					String message  = "Connectivity not established, please check Internet configuration and retry, else please contact your mobile operator for further clarifications.";
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",message);
					intent.putExtra("title","Alert Message");
					intent.putExtra("formName","Error_in_resp");
				}else if(StaticStore.responseMsg.equalsIgnoreCase("200_Error")){
					StaticStore.fromGprs = true;
					String message  = "We are facing some technical difficulties. Please try after sometime. Thank you !";
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",message);
					intent.putExtra("title","Alert Message");
					intent.putExtra("formName","Error_in_resp");
				}else if(StaticStore.responseMsg.equalsIgnoreCase("Otherthan_200_Error")){
					StaticStore.fromGprs = true;
					String message  = "Connectivity could not be established. Please try later.";
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",message);
					intent.putExtra("title","Alert Message");
					intent.putExtra("formName","Error_in_resp");
				}else if(StaticStore.responseMsg.equals(APGTERROR) && !StaticStore.enableHome){ //GT00 fail// 
					String gtfailmsg = "Connectivity could not be established. Please check your Internet connection and try again. To proceed with SMS mode, press Confirm.";
					StaticStore.IsGPRS = false;
					StaticStore.fromGprs = true;
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",gtfailmsg);
					intent.putExtra("formName","HTTPERROR");
				}else if(StaticStore.responseMsg.equals(APGTERROR) && StaticStore.enableHome){
					String gcfailmsg = "Connectivity not established, please check Internet configuration and retry, else please contact your mobile operator for further clarifications.";
					StaticStore.fromGprs = true;
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",gcfailmsg);
					intent.putExtra("title","Alert Message");
					intent.putExtra("formName","Error_in_resp");
				}else if(StaticStore.responseMsg.equals(APCGERROR) && !StaticStore.enableHome){
					String gcfailmsg = "Connectivity not established, please check Internet configuration and retry, else please contact your mobile operator for further clarifications.";
					StaticStore.fromGprs = true;
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",gcfailmsg);
					intent.putExtra("title","Alert Message");
					intent.putExtra("formName","Error_in_resp");
				}
			return intent;
		}
}