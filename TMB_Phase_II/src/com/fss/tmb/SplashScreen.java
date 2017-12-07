package com.fss.tmb;


import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Siva G
 * 
 */
public class SplashScreen extends Activity {
	double screenInches;
	String MobileType;
	TelephonyManager mTelephonyMgr;
	private static final String Process_info = null;
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (isRooted() == true){
//			showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
//		}else{
			StaticStore.context = getApplicationContext();
			StaticStore.PackageName = getPackageName();
			StaticStore.midlet.onResumeCalled();
			StaticStore.date = new Date().getTime(); 
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			StaticStore.width = display.getWidth();
			StaticStore.height = display.getHeight();
			StaticStore.orientation = display.getOrientation();
	    	StaticStore.mobileScreenSize =  "H"+StaticStore.height+"W"+StaticStore.width;
	    	
	    	DisplayMetrics dm = getResources().getDisplayMetrics();
			double density = dm.density * 160;
			double x = Math.pow(dm.widthPixels / density, 2);
			double y = Math.pow(dm.heightPixels / density, 2);
			screenInches = Math.sqrt(x + y);
	    	
//	    	if(StaticStore.width >= 500 && StaticStore.height >= 500 && isTablet(StaticStore.context) ){ //NEWLY created by ME on 9=Mach-2015
	    	if (StaticStore.width > StaticStore.height && isTablet(StaticStore.context)) {
				 StaticStore.istablet = true;
				 StaticStore.IsMobileTypeSel = true;
				 StaticStore.IsCDMA = true;
				 StaticStore.isOTPBuild = true;
		    }else{
				 StaticStore.ismobile = true;
				 StaticStore.is_SmallMobile = isSmallMobile(StaticStore.context);
		    }
	    	
  		    if(StaticStore.istablet){
		        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		    }else{
		        	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		    }
		 
			StaticStore.width = display.getWidth();
			StaticStore.height = display.getHeight();
			StaticStore.orientation = display.getOrientation();
			SharedPreferences settings = getSharedPreferences(Process_info, 0);
			SharedPreferences.Editor editor1 = settings.edit();
			editor1.putInt("PID",android.os.Process.myPid());
			editor1.commit();
			
			
			StaticStore.enableHome = false;
			StaticStore.LogoutImageStream = null;
			StaticStore.isOTPVerified = false;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			StaticStore.mobileDetails = getDeviceName();
			ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
			MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
			activityManager.getMemoryInfo(memoryInfo);
			
 		    if (memoryInfo.lowMemory || (memoryInfo.availMem <= memoryInfo.threshold)) {
			    StaticStore.ToastDisplay(SplashScreen.this,"Sorry, Unable to launch the application due to the low phone memory");
			    finish();
			} else if (StaticStore.muTerminate == true) {
				SharedPreferences shpref = this.getSharedPreferences(Process_info, Context.MODE_PRIVATE);
				Editor editor3 = shpref.edit();
				editor3.putInt("PID", 0);
				editor3.commit();
				StaticStore.check = 2890;
				if (StaticStore.mobileDetails.startsWith("RIM")) {
					StaticStore.muTerminate = false;
					System.exit(0);
				} else {
					finish();
				}
			} else {
				setContentView(R.layout.splash);
				final int welcomeScreenDisplay = 3000;
				Thread welcomeThread = new Thread() {
				int wait = 0;
				
					@Override
					public void run() {
						try {
							super.run();
							/**
							 * use while to get the splash time. Use sleep() to
							 * increase the wait variable for every 100L.
							 */
							while (wait < welcomeScreenDisplay) {
								sleep(100);
								wait += 100;
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							/**
							 * Called after splash times up. Do some action
							 * after splash times up. Here we moved to another
							 * main activity class
							 */
							startApp();
							startActivity(new Intent(getApplicationContext(),Fragment_Activity.class));
						}
					}
				};
				welcomeThread.start();
			}
//	    }
	}
	
	public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
	
	private static boolean isRooted() {
        return findBinary("su");
    }
	
	public void showAlertDialogAndExitApp(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(SplashScreen.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.show();
    }
	
	public boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	public boolean isSmallMobile(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            <= Configuration.SCREENLAYOUT_SIZE_SMALL;
	}
	
	public String getDeviceName() {
		 String manufacturer = Build.MANUFACTURER;
		 String model = Build.MODEL;
		 if (model.startsWith(manufacturer)) {
			 return capitalize(model);
		 } else {
			 return capitalize(manufacturer) + " " + model;
		 }
	 }
	 
	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
		    return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	 }
	
	 
	public void startApp() {
		try {
			if (RmsStore.db == null) {
				new RmsStore(getApplicationContext());
			}
			StaticStore.midlet.rechargeDynamicInputs = new RechargeDynamicInputs();
			byte index = 0;
			
			String temp = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_DEF);
	        index = (byte) temp.indexOf("~");
	        StaticStore.VMN_Number = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.restartAlert = (temp.substring(0, index).equals("0") ? true: false);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.IsPermitted = (temp.substring(0, index).equals("1") ? true           : false);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.SentRequest = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.StoreTxnNo = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.RequestSentTime = Long.parseLong(temp.substring(0,index));
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.withMemory = (temp.substring(0, index).equals("1") ? true: false);

	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.IsPersonalInfoGot = temp.substring(0, index).equals("1");
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.IsGPRS = temp.substring(0, index).equals("1");
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.myMobileNo = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.isBillpayEnabled = temp.substring(0, index).trim().equals("1");
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.publicKey = temp.substring(0, index);
	       
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.KSN = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.BDK = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.defaultPWD = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.applicationPWD = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.GprsUrl = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1);
	        index = (byte) temp.indexOf("~");
	        StaticStore.wapUrl = temp.substring(0, index);
	        
	        temp = temp.substring(index + 1).trim();
	        index = (byte) temp.indexOf("~");
	        StaticStore.isDashBoardProfileImageRemoved = temp.substring(0, index).equals("1");
	        
	        temp = temp.substring(index + 1).trim();
	        index = (byte) temp.indexOf("~");
	        StaticStore.isDashBoard = temp.substring(0, index).equals("1");
	        
	        temp = temp.substring(index + 1).trim();
	        index = (byte) temp.indexOf("~");
	        StaticStore.isForgotPWD = temp.substring(0, index).equals("1");
	        
	        temp = temp.substring(index + 1).trim();
	        index = (byte) temp.indexOf("~");
	        StaticStore.isCommModeSelected = temp.substring(0, index).equals("1");
	        
			String parsedAccount = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC);
   		    int parsedAccIndex = (byte) parsedAccount.indexOf("~");
			StaticStore.numberOfAccounts = Integer.parseInt(parsedAccount.substring(0, parsedAccIndex));
			parsedAccount = parsedAccount.substring(parsedAccIndex + 1);
			parsedAccIndex = (byte) parsedAccount.indexOf("~");
			StaticStore.selectedAccNumber = parsedAccount.substring(0,parsedAccount.indexOf("*"));
			StaticStore.selectedAccType = parsedAccount.substring(parsedAccount.indexOf("*") + 1, parsedAccIndex);
            StaticStore.accountNumbers = new String[StaticStore.numberOfAccounts];
            StaticStore.accType = new String[StaticStore.numberOfAccounts];
            StaticStore.LogPrinter('i',">>>>>>>parsedAccount>>>>>>>"+parsedAccount);
            String dummy = parsedAccount.substring(parsedAccount.indexOf("#") + 1, parsedAccount.lastIndexOf('#'));
            StaticStore.LogPrinter('i',">>>>>>>dummy>>>>>>>"+dummy);
            StaticStore.registeredUserStatus = dummy.substring(0, dummy.indexOf('#'));
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.isMpinAtLast = dummy.substring(0, dummy.indexOf('#')).equals("true");
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.mPinHexaValue = dummy.substring(0, dummy.indexOf('#'));
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.SMS_AUTHENTICATION_SMSMODE = dummy.substring(0, dummy.indexOf('#')).equals("true");
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.SMS_AUTHENTICATION_GPRSMODE = dummy.substring(0, dummy.indexOf('#')).equals("true");
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.chequeLength = dummy.substring(0, dummy.indexOf('#'));
            dummy = dummy.substring(dummy.indexOf('#')+1);
            StaticStore.LogPrinter('i',"");
            StaticStore.accOwner = dummy;
            StaticStore.chequeMin = StaticStore.chequeLength.substring(0,2);
            StaticStore.chequeMax = StaticStore.chequeLength.substring(2,4);
            StaticStore.mpinNeededTransactions = new mPAY().getMPinEnabledtransaction(StaticStore.mPinHexaValue);
            parsedAccount = parsedAccount.substring(0, parsedAccount.indexOf("#"))+ "~";

			for (int i = 0; i < StaticStore.numberOfAccounts; i++) {
				parsedAccount = parsedAccount.substring(parsedAccIndex + 1).trim();
				parsedAccIndex = (byte) parsedAccount.indexOf("~");
				StaticStore.accountNumbers[i] = parsedAccount.substring(0,parsedAccount.indexOf("*"));
				StaticStore.accType[i]	=	parsedAccount.substring(parsedAccount.indexOf("*") + 1, parsedAccIndex);
				StaticStore.totalAccounts = !StaticStore.accountNumbers[i].equals("0")? ++StaticStore.totalAccounts : StaticStore.totalAccounts;
			}

			temp = temp.substring(index + 1);
			index = (byte) temp.indexOf("~");
			StaticStore.defaultPublicKey = temp.substring(0, index);
			
			temp = temp.substring(index + 1);
			index = (byte) temp.indexOf("~");
			StaticStore.retryCount = (byte)Integer.parseInt(temp.substring(0, index));

			temp = temp.substring(index + 1).trim();
			index = (byte) temp.indexOf("~");
			StaticStore.numberOfTimeout = Integer.parseInt(temp.substring(0,index));

			StaticStore.timeoutTran = new String[5];
			StaticStore.timeoutTime = new String[5];

			for (int i = 0; i < StaticStore.maxTimeoutCount; i++) {
				temp = temp.substring(index + 1).trim();
				index = (byte) temp.indexOf("~");
				StaticStore.timeoutTran[i] = temp.substring(0, index);
				StaticStore.totalTimeouts = !StaticStore.timeoutTran[i]
						.equals("0") ? ++StaticStore.totalTimeouts
						: StaticStore.totalTimeouts;
			}
			if(StaticStore.isOTPBuild){
				StaticStore.IsGPRS = true;
				StaticStore.IsMobileTypeSel = true;
	 			StaticStore.IsCDMA = true;
	        }
			StaticStore.indexCtr = 0;
			StaticStore.listIndexArray = new int[10];
			StaticStore.selectedIndexArray = new int[10];
			StaticStore.listContent = new String[10][10];
			StaticStore.listHeading = new String[10];
			StaticStore.listMore = new boolean[10];
			StaticStore.listImageArray = new boolean[10];

			String[] messages = new String[] {
				//"CN00;You have received purchase request from Akila - +919841390888 has Rs.652.00 to pay. please Confirm by Reply;10/02/2011 09:50;TXNID:100910081257;BID:M504594",
			};
			for (int i = 0; i < messages.length; i++) {
				StaticStore.midlet.responseMessages = RmsStore
						.writeInboxRecordStore(
								StaticStore.midlet.responseMessages, "Y"
										+ messages[i].toUpperCase(),
								RmsStore.parsedRecords);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		StaticStore.appStarted = true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				onDestroy();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(StaticStore.check==2890 ) {
			SQLiteDatabase.releaseMemory();
			StaticStore.muTerminate =false;
	 		System.runFinalizersOnExit(true);
			System.exit(0);
		}
	}
}
