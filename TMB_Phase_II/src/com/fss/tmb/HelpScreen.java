package com.fss.tmb;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author Siva .G.
 * Date : 04-09-2013 
 *  
 */
public class HelpScreen  extends android.support.v4.app.Fragment {

	/** set time to Logout Image */
	final int welcomeScreenDisplay = 5000;
	private static boolean  autocallGrid;
	private String messageContent = "";
	private ScrollView scroll;
	private TextView header;
	private static String title;
	private String index;
	private String TXNID = "";
	//home - 0
	
	private int menuGroupIdForYes = 1;
	private int menuGroupIdForNo = 2;
	private int menuGroupIdForDefer = 3;
	private int menuGroupIdForUpdate = 4;
	private int menuGroupIdForMore = 5;
	//about - 6
	//exit - 7
	
	private Bundle displayDatas;

	// tabbar
	public static RelativeLayout r1;
	public static LinearLayout ll,llhead,llmain,llbody,tab;
	
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		 View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
		 
//		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
//		//Display display = getWindowManager().getDefaultDisplay(); 
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		
//		StaticStore.width = display.getWidth();
//		 StaticStore.height = display.getHeight();
//		 StaticStore.orientation = display.getOrientation();
//		 StaticStore.FromListScreen = true;
		 
		 // header image 
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
					LayoutParams.FILL_PARENT);
		 llhead = new LinearLayout (getActivity());
		 llmain = new LinearLayout(getActivity());
		 llbody = new LinearLayout(getActivity());
		 
		 llhead.setOrientation(LinearLayout.VERTICAL);
		 llmain.setOrientation(LinearLayout.VERTICAL);
		 llbody.setOrientation(LinearLayout.VERTICAL);
		 
//		 llhead.setLayoutParams(lp);
		 
//		 ImageView imheader = new ImageView(getActivity());
//		imheader.setImageResource(R.drawable.header_new);
//		imheader.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
//				LayoutParams.WRAP_CONTENT));
//		imheader.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if( StaticStore.enableHome  ){ //&& StaticStore.isCommModeSelected && StaticStore.IsPersonalInfoGot && StaticStore.IsPermitted 
//					StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getHome(getActivity()));
//				}
//				
//				
//			}
//		});
//		llhead.setGravity(Gravity.LEFT);
//		llhead.addView(imheader);
//		llmain.addView(llhead);
		try
		{
		displayDatas = this.getArguments();
//		messageContent = displayDatas.getString("response");
		index = displayDatas.getString("index");
		title = displayDatas.getString("title");
		
		StaticStore.LogPrinter('i',"Help Index  --> "+index);
		StaticStore.LogPrinter('i',"Help Title  --> "+title);
		}catch (Exception e) {
			// TODO: handle exception
		}
			
		if(title == null)
		{
			title = "Help";
		}
		if(index == null)
		{
			index = "nodata";
		}
		
		scroll = new ScrollView(getActivity());
		header = new TextView(getActivity());
		if(StaticStore.enableHome)
			header.setText(title);
		else
			header.setText(title+" Help");
		
		header.setTextColor(Color.rgb(18, 56, 127));
		if(StaticStore.istablet){
			header.setPadding(10, 10, 10, 10);
		}else{
			header.setPadding(5, 5, 5, 5);
		}
		header.setTextSize((StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title));
		header.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface));
		header.setGravity(Gravity.LEFT);
//		table.addView(header);
//		<-------- START Body Part --------->
		llbody.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams bodylp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
				LayoutParams.WRAP_CONTENT);
		bodylp.setMargins(10, 20, 10, 20);
		llbody.setLayoutParams(bodylp);
		
		storeLogggedIn_DateTime();
		
		if(!StaticStore.isLogout)
		{
//			 llbody.setBackgroundColor(Color.rgb(250, 249, 242));
			 llbody.setBackgroundResource(R.drawable.dis_body_bg);
		ImageView img_helpicon = new ImageView(getActivity());
		if(!title.equals("Contact Us")){
			img_helpicon.setImageResource(R.drawable.smile_success);	
		}
		img_helpicon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT));
		
		llbody.addView(img_helpicon);
		
		StaticStore.LogPrinter('i',"M essage Context --> "+index);
		TextView helpdata = new TextView(getActivity());
		
//		int id = getResources().getIdentifier(String.format("help_%s", index),"string", getPackageName());
		int id;
		try
		{
		 id = getResources().getIdentifier("help_"+index,"string",getActivity().getPackageName());
		 helpdata.setText(Html.fromHtml(getString(id)));
		 StaticStore.LogPrinter('i',"Came into the try -->"+id);
		}catch (Exception e) {
			// TODO: handle exception
			id = R.string.help_nodata;
			 helpdata.setText(Html.fromHtml(getString(id)));
			StaticStore.LogPrinter('i',"Came into the Catch -->"+id);
		}
//		StaticStore.LogPrinter('i',"sTRINGGGG ===> "+getResources().getString(id));
//		String result = getString(Html.fromHtml(id)); 
		helpdata.setTextColor(Color.rgb(18, 56, 127));
		helpdata.setGravity(Gravity.LEFT);
		helpdata.setPadding(10, 10, 10, 10);
		
		llbody.addView(helpdata);
		
		}else
		{
			StaticStore.enableHome = false;
			ImageView Logout = new ImageView(getActivity());
			LinearLayout.LayoutParams Logoutlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
					LayoutParams.WRAP_CONTENT);
			try
			{
				if(StaticStore.LogoutImageStream != null){
//								Bitmap bitmapFromByteArray = BitmapFactory.decodeStream(StaticStore.LogoutImageStream);
					
					Bitmap bitmapFromByteArray = BitmapFactory.decodeByteArray(StaticStore.LogoutImageStream,0,StaticStore.LogoutImageStream.length);

					StaticStore.LogPrinter('i',"ba Length ==> "+StaticStore.LogoutImageStream.length);
					StaticStore.LogPrinter('i',"bitmapFromByteArray Length ==> "+bitmapFromByteArray);
					// createa matrix for the manipulation
					//	        Matrix matrix = new Matrix();
					//	        // resize the bit map
					//	        matrix.postScale(StaticStore.width - 40, StaticStore.height - 200);
					//	        // rotate the Bitmap
					////	        matrix.postRotate(45);
					//
					//	        // recreate the new Bitmap
					//	        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapFromByteArray, 0, 0,
					//	        		width, height, matrix, true);
					System.gc();
					if(bitmapFromByteArray != null){
						Logout.setImageBitmap(bitmapFromByteArray);		
					}else{
						Logout.setImageResource(R.drawable.banner_default);
					}
				}else
				{
					Logout.setImageResource(R.drawable.banner_default);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Logout.setImageResource(R.drawable.banner_default);
			}

			Logout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 
					LayoutParams.WRAP_CONTENT));

			String Message = "Successfully logged out, Thank you ";
			TextView notetext = new TextView(getActivity());
			notetext.setText(Message);
			notetext.setTextSize(StaticStore.mFontsize_Title);
			notetext.setGravity(Gravity.CENTER);
			notetext.setTextColor(Color.rgb(18, 56, 127));
			llbody.addView(notetext);
			
			llbody.addView(Logout); //,Logoutlp
			
		}
		
//		<-------- END Body Part --------->
		
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		scroll.setBackgroundResource(R.drawable.bg_innerpage);
		llmain.addView(header);
		llmain.addView(llbody);
		scroll.addView(llmain);
		// tabbar
		r1 = new RelativeLayout(getActivity());
		/* Tabbar calling part */
		StaticStore.LogPrinter('i',"From displayable view");
		try {
			tab = StaticStore.Tabbar(tabBarListener, setHelpTabBar(),getActivity(),false);
			r1.addView(tab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ABOVE, 333);

		// s.setId(101);
		scroll.setLayoutParams(params);
		scroll.setId(222);
		r1.addView(scroll);
		r1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		r1.setBackgroundColor(Color.rgb(18, 56, 127));
//		setContentView(r1);
		// setContentView(table);

		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
  //<------ Font Style Part Start ---->
		
		final Typeface mFont = Typeface.createFromAsset(getActivity().getAssets(),
		"Segoe.ttf"); 
//		final int mFontsize = 25 ;
		final ViewGroup mContainer = (ViewGroup)llbody ; //contentgetRootView()
		HelpScreen.setAppFont(mContainer, mFont,(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
		//<------ Font Style Part End ---->
		if(StaticStore.isLogout)
		{
		welcomeThread.start();
		autocallGrid = true;
		}
		
		return r1;
	}
	
	private void storeLogggedIn_DateTime() {
		SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
		String LastLoggedInDateTime = prefs.getString("LastLoggedIn", null);
		if( LastLoggedInDateTime != null ) {
			String currentDateTimeString = StaticStore.lastLoginTime;
			SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, 0).edit();
			editor.putString("LastLoggedIn", currentDateTimeString ) ;
			editor.commit();
		}else{
			String currentDateTimeString = StaticStore.lastLoginTime;
			SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, 0).edit();
			editor.remove("LastLoggedIn") ;
			editor.commit() ;
			editor.putString("LastLoggedIn",  currentDateTimeString ) ;
			editor.commit() ;
		}
	}

	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	    	/****
	    	 * @author sivagovindan
	    	 * Date: 08-05-2014
	    	 * Purpose: Orientation changes 
	    	 */
	    	super.onConfigurationChanged(newConfig);
	    	System.err.println("CALLED CONFIG ++++++++++++++>");
	    	try {
	    		Display display = ((WindowManager)getActivity().getSystemService(getActivity().WINDOW_SERVICE)).getDefaultDisplay();
	    		//Display display = getWindowManager().getDefaultDisplay(); 
	    		StaticStore.width = display.getWidth();
	    		StaticStore.height = display.getHeight();
	    		StaticStore.orientation = display.getOrientation();
	    		StaticStore.LogPrinter('i',"StaticStore.width == >"+StaticStore.width);
	    		StaticStore.LogPrinter('i',"StaticStore.height == >"+ StaticStore.height);
	    		StaticStore.LogPrinter('i',"StaticStore.orientation == >"+StaticStore.orientation);
	    		r1.removeView(tab);
	    		tab = StaticStore.Tabbar(tabBarListener, setHelpTabBar(),getActivity(),false);
				r1.addView(tab);
				
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
	 
	 @Override
	    public void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	 StaticStore.LogPrinter('e',"onPause() Help Screen");
	    	 StaticStore.midlet.onPauseCalled();
	    }
	    
	    @Override
	    public void onResume() {
	    	// TODO Auto-generated method stub
	    	super.onResume();
	    	StaticStore.LogPrinter('e',"onResume() Help Screen");
	    	StaticStore.midlet.onResumeCalled();
	    }
	    
	@Override
	public void onDetach() {
	    super.onDetach();
	    StaticStore.LogPrinter('e',"onDetach() Help Screen");
	    
	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont,int mFontsize)
	{
	    if (mContainer == null || mFont == null) return;

	    final int mCount = mContainer.getChildCount();

	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView)
	        {
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	            //Set the Text Size
	            ((TextView) mChild).setTextSize(mFontsize);
	        }
	        else if (mChild instanceof ViewGroup)
	        {
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont,mFontsize);
	        }
	    }
	}

	private Map<Integer, Image> setHelpTabBar() {

		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
			if(!StaticStore.istablet){
				tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
			}else{
				if(!StaticStore.enableHome && StaticStore.IsPermitted){
					tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
				}
			}
		tabBarSetter.put(7,new Image(R.drawable.icon_exit,(StaticStore.enableHome?"Logout":"Exit")));
		return tabBarSetter;
	}


	private OnClickListener tabBarListener = new OnClickListener() {
		public void onClick(View v) {
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
			if(sessionflag){
				if (v.getId() == 0) {
					if(StaticStore.isLogout){
						StaticStore.isLogout = false;
						autocallGrid = false;
					}
				StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getHome(getActivity()));
				} else if (v.getId() == menuGroupIdForUpdate) {
					tabbarmenuGroupIdForUpdate();
				} else if (v.getId() == menuGroupIdForYes) {
					tabbarmenuGroupIdForYes();
				} else if (v.getId() == menuGroupIdForNo) {
					tabbarmenuGroupIdForNo();
				} else if (v.getId() == menuGroupIdForDefer) {
					tabbarmenuGroupIdForDefer();
				} else if (v.getId() == menuGroupIdForMore) {
					tabbarmenuGroupIdForMore();
				} else if (v.getId() == 6) {
				StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getAbout(getActivity()));
				} else if (v.getId() == 7) {
					exit();
				}

			}
		}
	};

	private void exit() {
		// TODO Auto-generated method stub
		exitMIDlet(new AlertDialog.Builder(getActivity()), "Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,
				getActivity()).show();
	}

	protected void tabbarmenuGroupIdForDefer() {
		
	}

	protected void tabbarmenuGroupIdForNo() {
		
	}

	protected void tabbarmenuGroupIdForUpdate() {
		
	}

	protected void tabbarmenuGroupIdForMore() {
		
	}

	protected void tabbarmenuGroupIdForYes() {
		
	}





	public AlertDialog.Builder exitMIDlet(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {

		AlertDialog.Builder alertbox = tempAlertBox;

		alertbox.setMessage(message);

		alertbox.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						StaticStore.LogPrinter('i',">>>>>>>>>>ready to close>>>>>>>");
						// new ListSelection().closeApplication();
						if(StaticStore.isLogout){
							StaticStore.isLogout = false;
						}
						Intent myIntent = StaticStore.midlet.getExitScreen(context);
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					}
				});
		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			// do something when the button is clicked
			public void onClick(DialogInterface arg0, int arg1) {
				if (StaticStore.index == 209) {
					StaticStore.indexCtr -= 1;
				}
				arg0.cancel();
			}
		});

		return alertbox;
	}

	/** create a thread to show splash up to splash time */
	Thread welcomeThread = new Thread() {

		int wait = 0;

		@Override
		public void run() {
			try {
				super.run();
				/**
				 * use while to get the splash time. Use sleep() to increase
				 * the wait variable for every 100L.
				 */
				while (wait < welcomeScreenDisplay) {
					sleep(100);
					wait += 100;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				/**
				 * Called after splash times up. Do some action after splash
				 * times up. Here we moved to another main activity class
				 * */
				if(autocallGrid){
					autocallGrid = false;
				StaticStore.isLogout = false;
				Intent myIntent = new Intent(getActivity(),GridScreenViewActivation.class);
				StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			}
		}
	};


//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		// return super.onKeyDown(keyCode, event);
//		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
//		if(sessionflag){
//			if (keyCode == KeyEvent.KEYCODE_BACK && StaticStore.isLogout ) {
//				autocallGrid = false;
//				StaticStore.isLogout = false;
//				Intent myIntent = new Intent(getActivity(),GridScreenViewActivation.class);
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
//				return true;
//			} else{
//				exit();
//				return true;//tab super.onKeyDown(keyCode, event);
//			}
//		}else
//		{
//			StaticStore.midlet.getsessionTimeOut(getActivity());
//			return true;
//		}
//	}

	public static boolean onBackPressed(Context context) {
		// return super.onKeyDown(keyCode, event);
		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context);
		if(sessionflag){
			 StaticStore.LogPrinter('i',"onBackPressed helpscreen 2222 ");
			
			if (StaticStore.isLogout ) {
				autocallGrid = false;
				StaticStore.isLogout = false;
				Intent myIntent = new Intent(context,GridScreenViewActivation.class);
				StaticStore.midlet.startFragment(context,myIntent);
				return true;
			}else if(title.equals("Contact Us")){
				Intent myIntent = StaticStore.midlet.get_Settings(context);
				StaticStore.midlet.startFragment(context,myIntent);
				return true;
			} else if(StaticStore.indexCtr > 0){
				if(StaticStore.indexCtr > 0){
					StaticStore.indexCtr -= 1;
				}
				if(StaticStore.indexCtr == 1)
				{
					StaticStore.midlet.isImageTextList = true;
				}else
				{
					StaticStore.midlet.isImageTextList = false;
				}
				StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>"+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
				int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
				int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];
				Intent myIntent = StaticStore.midlet.getList(context,indexForBack,indexForSelectedBack);
				StaticStore.midlet.startFragment(context,myIntent);
				return true;
//				exit();
//				return false;//tab super.onKeyDown(keyCode, event);
			}else{
				return false;
			}
				
		}else
		{
			StaticStore.midlet.getsessionTimeOut(context);
			return false;
		}
	
	}
	
}