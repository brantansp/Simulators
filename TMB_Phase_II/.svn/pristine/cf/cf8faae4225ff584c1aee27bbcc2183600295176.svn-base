package com.fss.tmb;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
*
* @author Siva G
* 
*/


public class TMBControlApplication extends Application
{
	private static final String TAG = TMBControlApplication.class.getName();
	private static final String Process_info = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
		StaticStore.LogPrinter('i',"^^^^^^^^^^^^Starting application");
		SharedPreferences settings = getSharedPreferences(Process_info,0);
		int pid_det = settings.getInt("PID", 0);
		String pname= getAppNameByPID(getApplicationContext(),pid_det);
		Context context = getApplicationContext();
		String packageName = context.getPackageName();
		
		if(!packageName.equals(pname) && pid_det!= 0 && StaticStore.muTerminate){
			StaticStore.LogPrinter('e'," tmb Inside IF Statement StaticStore.restartAppFlag -->>"+pid_det);
			Intent intent = new Intent(this, SplashScreen.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Intent.FLAG_ACTIVITY_CLEAR_TOP | 
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			StaticStore.midlet.startFragment(this,intent);
		}
	}
	
	public static String getAppNameByPID(Context context, int pid){
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for(RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()){
	        if(processInfo.pid == pid){
	        	StaticStore.LogPrinter('e',"processInfo.pid -->"+processInfo.pid);
		    	StaticStore.LogPrinter('e',"processInfo.Name -->"+processInfo.processName);
	            return processInfo.processName;
	        }
	    }
	    return "";
	}
}
