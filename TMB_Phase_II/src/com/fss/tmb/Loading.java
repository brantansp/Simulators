package com.fss.tmb;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;

/**
 * @author Siva G
 * 
 */

public class Loading extends FragmentActivity {
	int count;
	
	public boolean notifyMsg;
	Thread loadingBar = null;
	AlertDialog.Builder alertbox;
	private TextView txtLoading;
	private TextView txtCompleted;
	private ProgressBar bar;
	public int total=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		StaticStore.date = new Date().getTime();
		if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation") && StaticStore.isProgressBarClosed){
			if (!StaticStore.istablet) {
				StaticStore.gridNofragHeader.setVisibility(View.GONE);
				StaticStore.gridNolineHeader.setVisibility(View.GONE);
			}
		}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation_Mobile") && StaticStore.isProgressBarClosed){
			if (!StaticStore.istablet) {
				StaticStore.gridNofragHeader.setVisibility(View.GONE);
				StaticStore.gridNolineHeader.setVisibility(View.GONE);
			}
		}
		StaticStore.isProgressBarClosed = false;
		alertbox = new AlertDialog.Builder(this);
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading);
        LoadingProgress();
		}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		StaticStore.LogPrinter('i',"onStop Called from Loading");
		try{
			loadingBar = null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StaticStore.LogPrinter('i',"Loading onStop catch");
		}
		super.onStop();
	}
	
	
	public void LoadingProgress() {
		if(StaticStore.onceListClicked){
			StaticStore.onceListClicked = false;
		}else{
			StaticStore.onceListClicked = true;
		}
		StaticStore.midlet.isProcessing = true;
		count = 1;
		StaticStore.dialog = new Dialog(this);
		StaticStore.window = StaticStore.dialog.getWindow();	
		StaticStore.window.requestFeature(Window.FEATURE_RIGHT_ICON);
		StaticStore.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		StaticStore.window.setTitle("Processing Request...");
		StaticStore.window.setFeatureInt(Window.FEATURE_RIGHT_ICON, R.drawable.icon);
		StaticStore.window.setContentView(R.layout.progrees);
		
		
		bar=(ProgressBar)StaticStore.window.findViewById(R.id.progress);
		txtLoading = (TextView) StaticStore.window.findViewById(R.id.txtLoading);
		
		txtCompleted = (TextView) StaticStore.window.findViewById(R.id.txtCompleted);
		StaticStore.dialog.setCancelable(true); 
		bar.setProgress(1); 
		StaticStore.dialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
//				System.out.println("RESPONSE FROM SERVER >>>>>>>>"+StaticStore.responseMsg);
				StaticStore.midlet.isProcessing = false;
				StaticStore.LogPrinter('e',"!StaticStore.messageReceived ==> "+!StaticStore.messageReceived) ;
				StaticStore.LogPrinter('e',"Cancel Clikedddddddddddddddddd ==> ") ;
				
		    	 if(StaticStore.isAirline){
					Intent intent = new Intent(getApplicationContext() , DisplayableView.class);
				 	intent.putExtra("response",StaticStore.midlet.airlineDispMsg);
				   	intent.putExtra("formName", "A100");
				   	StaticStore.midlet.startFragment(getApplicationContext(),intent);
				}else if(!StaticStore.messageReceived && StaticStore.index == 188){
					StaticStore.isUpdateClicked = false;
					StaticStore.midlet.moreFlag = false;

					if(StaticStore.midlet.getFilledAccArray().length!=0){
					try {
							String tempMsg = "";
							String accArray[] = new String[StaticStore.midlet
									.getFilledAccArray().length];
							accArray = StaticStore.midlet.getFilledAccArray();
							for (int i = 0; i < accArray.length; i++) {
								tempMsg += accArray[i] + ";";
							}
							Intent intent = new Intent(getApplicationContext() , DisplayableView.class);
                        	intent.putExtra("response",tempMsg);
                         	intent.putExtra("formName", "AccDisplay");
                         	StaticStore.LogPrinter('i',":::::::::::::tempMSG"+tempMsg);
                         	StaticStore.midlet.startFragment(getApplicationContext(),intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
					}else{
					}
				}else if(!StaticStore.messageReceived && (StaticStore.indexCtr > 0)) {
					StaticStore.LogPrinter('i',"Calling here Loading SaveList Back");
					StaticStore.isBack = true;	    	
			    	StaticStore.indexCtr -= 1;	    
			    	StaticStore.LogPrinter('i',"calling Back from Loading ==>");
			    	StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>"+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
					int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
					int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
					Intent myIntent = StaticStore.midlet.getList(getApplicationContext(),indexForBack,indexForSelectedBack);
					StaticStore.midlet.startFragment(getApplicationContext(),myIntent);					
				} else if(!StaticStore.messageReceived && StaticStore.index == 155 && StaticStore.indexCtr == 0) {
					StaticStore.index	=	123;
					StaticStore.midlet.startFragment(Loading.this,new Intent(Loading.this, DynamicCanvas.class));
				}else if(!StaticStore.messageReceived && StaticStore.isInbox ) {
					StaticStore.midlet.startFragment(Loading.this,new Intent(Loading.this,GridScreenView.class));									
				}
				else {					
					StaticStore.messageReceived	=	false;
				}
				
				StaticStore.LogPrinter('i'," Endding of Loading Screen --> ");
				StaticStore.isProgressBarClosed = true;
				if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation") && (StaticStore.isProgressBarClosed || !StaticStore.isProgressBarClosed)){
					if (!StaticStore.istablet) {
						StaticStore.gridNofragHeader.setVisibility(View.GONE);
						StaticStore.gridNolineHeader.setVisibility(View.GONE);
					}
				}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation_Mobile") && (StaticStore.isProgressBarClosed || !StaticStore.isProgressBarClosed)){
					if (!StaticStore.istablet) {
						StaticStore.gridNofragHeader.setVisibility(View.GONE);
						StaticStore.gridNolineHeader.setVisibility(View.GONE);
					}
				}
				finish();
			}
		});
		// get the maximum value
		int maximum = 100;
		// set the maximum value         
		bar.setMax(maximum);         
		// display the progressbar
	//	alertbox.show();
			StaticStore.dialog.show();	
		// create a thread for updating the progress bar
		loadingBar = new Thread (new Runnable() {  
			public void run() {
					while (!StaticStore.isProgressBarClosed && bar.getProgress()< bar.getMax()) {
						if(StaticStore.isProgressBarClosed) {
							StaticStore.LogPrinter('i',"Exiting");
							break;
						}
						try {
							// wait 1000ms between each update 
							Thread.sleep(2000);
							// active the update handler
							progressHandler.sendMessage(progressHandler.obtainMessage()); 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					StaticStore.LogPrinter('i',"Still thread existing");
				 }

	
			}); 
		// start the background thread  
		loadingBar.start();      
		}
	// handler for the background updating     
	Handler progressHandler = new Handler() {  
		public void handleMessage(Message msg) {
		
			
			StaticStore.LogPrinter('i',">>>>>>>>SentRequest"+StaticStore.SentRequest);
			StaticStore.LogPrinter('i',">>>>>>>>StaticStore.dialog.getProgress()"+bar.getProgress()+"=="+bar.getMax());
			if(StaticStore.midlet.smsSend){
				StaticStore.midlet.smsSend = false;
			}
			
			try{
				if(bar.getProgress()== bar.getMax()){
					txtLoading.setText("You will receive  the status of the last transaction shortly in inbox");
					if (StaticStore.numberOfTimeout > 0 && StaticStore.timeoutFlag) {

						StaticStore.timeoutTran[StaticStore.totalTimeouts] = StaticStore.menuDesc[StaticStore.index][1]
								.substring(2, 4);
						StaticStore.timeoutTime[StaticStore.totalTimeouts] = System
								.currentTimeMillis()
								+ "";
						StaticStore.totalTimeouts++;
						StaticStore.midlet.writeinMemory(getApplicationContext());
					}

					if (StaticStore.totalTimeouts >= StaticStore.numberOfTimeout
							&& StaticStore.numberOfTimeout > 0
							&& StaticStore.timeoutFlag) {
						StaticStore.complaintValues = "";
						for (int i = 0; i < StaticStore.totalTimeouts; i++) {
							StaticStore.complaintValues += StaticStore.timeoutTran[i]
									+ "#" + StaticStore.timeoutTime[i] + "*";
						}
						StaticStore.complaintValues = StaticStore.complaintValues
								.substring(0,
										StaticStore.complaintValues.length() - 1);

						getCountAlert(alertbox,
								"Do you want to complaint timeout?", 100,
								getApplicationContext()).show();
					}
				}
				else
				{
					 total=total+1;
					 String perc=String.valueOf(total).toString();
					 txtCompleted.setText(perc+"% completed");
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			bar.incrementProgressBy(count); 
			}
		
		};
		public AlertDialog.Builder getCountAlert(AlertDialog.Builder tempAlertBox,
				String message, int index, final Context context) {
 
			AlertDialog.Builder alertbox = tempAlertBox;

			alertbox.setMessage(message);

			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							StaticStore.menuDesc[181][2] = StaticStore.complaintValues;
							StaticStore.index = 181;
							StaticStore.midlet.startFragment(Loading.this,new Intent(context, DynamicCanvas.class));
						}
					});
			// set a negative/no button and create a listener
			alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
				// do something when the button is clicked
				public void onClick(DialogInterface arg0, int arg1) {
					StaticStore.totalTimeouts = 0;
					for (int i = 0; i < StaticStore.maxTimeoutCount; i++) {
						StaticStore.timeoutTran[i] = "0";
						StaticStore.timeoutTime[i] = "0";
					}
					StaticStore.midlet.writeinMemory(getApplicationContext());
					return;
				}
			});

			return alertbox;
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			StaticStore.LogPrinter('i',"Came inside disp Back-->"+StaticStore.index);
			if(StaticStore.indexCtr == 1)
			{
			StaticStore.midlet.isImageTextList = true;
			}else
			{
				StaticStore.midlet.isImageTextList = false;
			}
			
			if(keyCode == KeyEvent.KEYCODE_BACK)
			{
				finish();
				return false;
			}else
			{
			return true;
			}
		}
}