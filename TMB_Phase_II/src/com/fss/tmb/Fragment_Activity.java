package com.fss.tmb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * @author Siva .G.
 * Date : 04-09-2013 
 *  
 */

public class Fragment_Activity extends FragmentActivity {

	/** Called when the activity is first created. */
	Context context;
	LinearLayout list_frag;
	RelativeLayout frag_header;
	LinearLayout line_header;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        StaticStore.context = context;
        setContentView(R.layout.fragment_main);
        
  		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
  		StaticStore.width = display.getWidth();
  		StaticStore.height = display.getHeight();
          
          if (StaticStore.width > StaticStore.height && isTablet(StaticStore.context)) {
  			 StaticStore.istablet = true;
  			 StaticStore.IsMobileTypeSel = true;
  			 StaticStore.IsCDMA = true;
  			 StaticStore.isOTPBuild = true; 
  		} else {
  			StaticStore.istablet = false;
  			StaticStore.ismobile = true;
  			StaticStore.is_SmallMobile = isSmallMobile(StaticStore.context);
  		}        

        
		if (StaticStore.istablet) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		 
        if(savedInstanceState == null){
        	
        	 StaticStore.LogPrinter('i',"---- Came inside this Fragment_Activity ==> ");
        	//Add fragment 1 without XML
            StaticStore.fragmentManager = getSupportFragmentManager(); //getFragmentManager();	//"support" because we're using the support libraries
           StaticStore.fragmentTransaction = StaticStore.fragmentManager.beginTransaction();
           
           frag_header = (RelativeLayout) findViewById(R.id.frag_header);   
           final ViewTreeObserver observer1 = frag_header.getViewTreeObserver();
           observer1.addOnGlobalLayoutListener(
              new ViewTreeObserver.OnGlobalLayoutListener() {
                  public void onGlobalLayout() {
                	  /*  By ABINAYA.J.A  */
                	  if(StaticStore.istablet){
                		 StaticStore.Header_Height  = 62;
                	  }else{
                		 StaticStore.Header_Height  = 50; //+18;// For Height Space Reducing Add This By ABINAYA.J.A
                		 //StaticStore.Header_Height  = 50 + (StaticStore.height/27); // For Future Use GRID HEIGHT DIFFERENCE By ABINAYA.J.A
                	  }
                	  /*  By ABINAYA.J.A  */
//                	 StaticStore.Header_Height  = frag_header.getHeight(); // Commented By ABINAYA.J.A
                	 frag_header.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                  }
                  });
           line_header = (LinearLayout) findViewById(R.id.line_header); 
                     
           StaticStore.gridNofragHeader = frag_header;
           StaticStore.gridNolineHeader = line_header;
           
       	if(!StaticStore.IsPersonalInfoGot && !StaticStore.IsPermitted &&(!StaticStore.istablet && StaticStore.restartAlert)){
			if(StaticStore.restartAlert){
				String msg = "Please restart your phone after installing TMB mConnect.";
				
				Intent mPayIntent  = new Intent(Fragment_Activity.this, DisplayableView.class);
				StaticStore.restartAlert = false;
				StaticStore.midlet.writeinMemory(getApplicationContext());
				mPayIntent.putExtra("response",msg);
				mPayIntent.putExtra("formName","restart");
				StaticStore.midlet.startFragment(Fragment_Activity.this,mPayIntent);
			}
        }else{
        	StaticStore.midlet.startFragment(Fragment_Activity.this, StaticStore.midlet.initiateUserOption(Fragment_Activity.this));
        }
        }else{
        }
       
    }
 
    public boolean isTablet(Context context) {
		boolean TabVal = false;
		TabVal= ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);	
		return TabVal;
    }

    public boolean isSmallMobile(Context context) {
	   return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            <= Configuration.SCREENLAYOUT_SIZE_SMALL;
	}
    
    @Override
    public void onBackPressed(){
    	if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".ListSelection")){
    		if(!ListSelection.onBackPressed(context)){
    			StaticStore.LogPrinter('i',"onBackPressed Fragment_Activity ListSelection");
    			int index = StaticStore.backlistIndex;
    			if(StaticStore.istablet && (index == 1 || index == 12 || index == 75 || index == 106 || index == 121 || index == 122)){
    				exit();
    			}else{
    				StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
    			}
    		}
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenView")){
    		if(StaticStore.isDashBoard){
    			Intent mPayIntent  = new Intent(Fragment_Activity.this, FirstPageActivity.class);
    			StaticStore.midlet.startFragment(Fragment_Activity.this,mPayIntent);
    		}else{
    			exit();
    		}
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".FirstPageActivity")){
    			exit();
     	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".SecondPageActivity")){
			Intent mPayIntent  = new Intent(Fragment_Activity.this, FirstPageActivity.class);
			StaticStore.midlet.startFragment(Fragment_Activity.this,mPayIntent);
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation")){
    		 StaticStore.LogPrinter('i',"onBackPressed GridScreenViewActivation");
    		exit();
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation_Mobile")){
   		 StaticStore.LogPrinter('i',"onBackPressed GridScreenViewActivation_Mobile");
   		exit();
   	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenView")){
    		 StaticStore.LogPrinter('i',"onBackPressed GridScreenView");
    		StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
   	}	else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".FirstPageActivity")){
       		 StaticStore.LogPrinter('i',"onBackPressed FirstPageActivity");
       		StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
   	}	else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".SecondPageActivity")){
  		 StaticStore.LogPrinter('i',"onBackPressed SecondPageActivity");
  		StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation_Left")){
    		 StaticStore.LogPrinter('i',"onBackPressed GridScreenViewActivation_Left");
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".DynamicCanvas")){
    		 StaticStore.LogPrinter('i',"onBackPressed DynamicCanvas");
    		 String backstring = DynamicCanvas.onBackPressed(context);
    		 StaticStore.LogPrinter('i',"onBackPressed Fragment_Activity DynamicCanvas"+backstring);
    		 if(backstring.equalsIgnoreCase("false") || backstring.equalsIgnoreCase("exit") ){
    			 exit();
        	 }else if(StaticStore.enableHome && backstring.equalsIgnoreCase("super")){
        		 super.onBackPressed();
        	 }
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".DisplayableView")){
    		StaticStore.LogPrinter('i',"onBackPressed DisplayableView");
    		String backstring = DisplayableView.onBackPressed(context);
    		
    		StaticStore.LogPrinter('i',"onBackPressed Fragment_Activity DisplayableView ==> "+ backstring);
    		if(backstring.equalsIgnoreCase("false") || backstring.equalsIgnoreCase("exit")){
    			exit();
    		}else if(StaticStore.enableHome && backstring.equalsIgnoreCase("super")){
    			super.onBackPressed();
    		}
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".HelpScreen")){
    		 StaticStore.LogPrinter('i',"onBackPressed HelpScreen");
    		if(!HelpScreen.onBackPressed(context)){
    			 StaticStore.LogPrinter('i',"onBackPressed Fragment_Activity HelpScreen");
    			StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
    		}
    	}else if(StaticStore.fragclassname.equalsIgnoreCase(StaticStore.PackageName+".Inbox")){
    		 StaticStore.LogPrinter('i',"onBackPressed Inbox");
    		if(!Inbox.onBackPressed(context)){
    			 StaticStore.LogPrinter('i',"onBackPressed Fragment_Activity Inbox");
    			StaticStore.midlet.responseMessages = RmsStore.readInboxRecordStore(RmsStore.parsedRecords, StaticStore.midlet.responseMessages);
    			if(StaticStore.isInbox){
    				if(StaticStore.midlet.responseMessages == null){
    				}else{
    					Intent myIntent = StaticStore.midlet.get_ResponseInbox(context);
    					StaticStore.midlet.startFragment(context,myIntent);
    				}
    			}else{
    				StaticStore.midlet.startFragment(context, StaticStore.midlet.getHome(context));
    			}
    		}
    	}else{
    		StaticStore.LogPrinter('i',"super.onBackPressed() Calledddddddddddd-kdkddk");
    		if(StaticStore.indexCtr > 0){
    			StaticStore.indexCtr -= 1;
    			StaticStore.isBack = true;	    	
    			StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>.."+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
    			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
    			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
    			Intent myIntent = StaticStore.midlet.getList(context,indexForBack,indexForSelectedBack);
    			StaticStore.midlet.startFragment(context,myIntent);
    			StaticStore.indexCtr -= 1;
    		}else{
    			exit();
    		}
    	}
    }
    
	private void exit() {
		exitMIDlet(new AlertDialog.Builder(Fragment_Activity.this),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,Fragment_Activity.this).show();			
	}
	public AlertDialog.Builder exitMIDlet(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {

		AlertDialog.Builder alertbox = tempAlertBox;

		alertbox.setMessage(message);

		alertbox.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				StaticStore.LogPrinter('i',">>>>>>>>>>ready to close>>>>>>>");
				Intent myIntent = StaticStore.midlet.getExitScreen(context);
				StaticStore.midlet.startFragment(Fragment_Activity.this,myIntent);
			}
		});
		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			// do something when the button is clicked
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
			}
		});
		return alertbox;
	}
}