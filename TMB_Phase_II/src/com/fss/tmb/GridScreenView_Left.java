
package com.fss.tmb;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GridScreenView_Left extends android.support.v4.app.Fragment{    
	//for tabbar
	LinearLayout gLayout;
	private String disableMsg = "This service is temporarily unavailable. Inconvenience is regretted.";
	private long startTime;
	ImageAdapter imageAdapter;
	//---the images to display---
	Integer[] imageIDs = {            
			R.drawable.home_transfers,
			R.drawable.home_accounts,
			R.drawable.home_deposits, 
			R.drawable.home_payments,  
			R.drawable.home_imps,
			R.drawable.home_requests,
			R.drawable.home_recharge,  
			R.drawable.home_settings,
			R.drawable.home_changempin			

	};
	String[] GirdMenus = {"Transfer", "Accounts", "Deposit", "Payments", "IMPS", "Requests", "Recharge", "Settings", "Change mPIN"}; //
	Intent intent ;
	Typeface mFontBold;


	private Button notification;
	private boolean gridLeftsidepane;
	View rootView;
	GridView gridView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//rootView = inflater.inflate(R.layout.main, container, false); //OLD
		rootView = inflater.inflate(R.layout.main_left, container, false);
		StaticStore.forBillerRegistration = false;
		StaticStore.forAPLOBack = false;
		StaticStore.forAPLIBack = false;
		StaticStore.loanBalance = false;
		StaticStore.depositAccBalance = false;
		StaticStore.forAPDOBack = false;

		StaticStore.initialMyOwnAccFlag = false;
		StaticStore.secondMyOwnAccFlag = false;
		StaticStore.isMyOwnAccSync = false;
		StaticStore.forAddBiller = false;
		StaticStore.forTNEBNote = false;
		StaticStore.forCOCTAX = false;
		StaticStore.dynamicTempS555Flag = false;
		StaticStore.dynamicTempS555ConfirmFlag = false;
		StaticStore.dynamicTempMDFlag = false;
		StaticStore.dynamicTempMDConfirmFlag = false;
		StaticStore.dynamicTempFDFlag = false;
		StaticStore.dynamicTempFDConfirmFlag = false;
		StaticStore.dynamicTempNDFlag = false;
		StaticStore.dynamicTempND1Flag = false;
		StaticStore.dynamicTempND2Flag = false;
		StaticStore.dynamicTempNDConfirmFlag = false;
		StaticStore.dynamicTempNDFinalFlag = false;
		StaticStore.dynamicTempRDFlag = false;
		StaticStore.isMenuFromDashBoard = false;
		
		StaticStore.LogPrinter('i',"came inside the GridScreenView ==> ");



		DynamicCanvas.menuDescRestore();

		mFontBold = Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.header);

		/*tabbar comp*/ 	  

		if(StaticStore.LogoutImageStream == null && !StaticStore.LogoutImagecalled)
		{
			StaticStore.LogPrinter('i',"Image Download Started == >"+StaticStore.ImageStreamingURL.replaceAll("~", "="));

			String URLimage = StaticStore.ImageStreamingURL.replaceAll("~", "=").trim();
			//			        	String URLimage = "http://10.44.71.26:8080/MPAYPORTAL/streamingURL.htm?bankid=444444&file=Android.JPG";

			if(URLimage != null && URLimage.toLowerCase().startsWith("http"))
			{
				try{
					new DownloadLogoutImageTask().execute(URLimage);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			//        	new DownloadLogoutImageTask().execute("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeTAdjsJQYz8AsyfvUWHULu3vY_OvGkRbKhWBlJwle4Jps9LGZDg"); 
			//"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeTAdjsJQYz8AsyfvUWHULu3vY_OvGkRbKhWBlJwle4Jps9LGZDg"
		}
		gLayout = (LinearLayout)rootView.findViewById(R.id.gridList);
		TextView Gridtitle = (TextView)rootView.findViewById(R.id.Gridtext);
		TextView username = (TextView)rootView.findViewById(R.id.username);
		//     Gridtitle.setText("Welcome "+StaticStore.accOwner);
		Gridtitle.setTypeface(mFontBold);
		Gridtitle.setTextColor(Color.rgb(18, 56, 127));
		username.setTextColor(Color.rgb(18, 56, 127));
		
		if((StaticStore.accOwner.length() == 32) || (StaticStore.accOwner.length() < 32)){
			StaticStore.accOwner = StaticStore.accOwner.toString().toLowerCase();
		}else{
			StaticStore.accOwner = StaticStore.accOwner.substring(0, 32);
			StaticStore.accOwner = StaticStore.accOwner.toString().toLowerCase();
		}

		if(StaticStore.accOwner == null || StaticStore.accOwner.equals("") || StaticStore.accOwner.equalsIgnoreCase("null")){
			Gridtitle.setText("Welcome ");
			username.setText("User");
		}else{
			StringBuilder titleCase = new StringBuilder();
			boolean nextTitleCase = true;
			for(char c : StaticStore.accOwner.toCharArray()){
				if(Character.isSpaceChar(c)){
					nextTitleCase = true;
				}else if(nextTitleCase){
					c= Character.toTitleCase(c);
					nextTitleCase = false;
				}
				titleCase.append(c);
			}
			StaticStore.accOwner = titleCase.toString();
			Gridtitle.setText("Welcome ");
			username.setText(StaticStore.accOwner);
		}

		Gridtitle.setTextSize((StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title));

		notification = (Button)rootView.findViewById(R.id.notifi);
		if(StaticStore.isPostLoginNotification){
			notification.setText(""+(StaticStore.midlet.postLoginShortDescList.length));
			notification.setTextColor(Color.rgb(18, 56, 127));
			notification.setTextSize(14);
			notification.setTypeface(mFontBold);
		}else{
			notification.setVisibility(View.INVISIBLE);
		}

		notification.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("formName", "PostNotification");
				intent.putExtra("title","Notification");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}
		});

		//  gLayout.addView(DynamicCanvas.Tabbar(1));

		final LinearLayout lltitle = (LinearLayout)rootView.findViewById(R.id.llTitle);
		final ViewTreeObserver observer1= lltitle.getViewTreeObserver();
		observer1.addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					public void onGlobalLayout() {
						StaticStore.Titlebar_Height = getView().getHeight();
						getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}
				});
		
		
		int htotal = (StaticStore.Header_Height *2); //+(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
		if(htotal!= 0){
			StaticStore.HF_Height = StaticStore.height - htotal;
		}
		gridView = (GridView)rootView.findViewById(R.id.gridview); 
		StaticStore.LogPrinter('i',"Grid view StaticStore.Leftsidepane  ==> "+StaticStore.Leftsidepane);
		gridLeftsidepane = true;
		gridView.setNumColumns(1);


		imageAdapter = new ImageAdapter(getActivity(),-1);
		imageAdapter.setSelectedPosition(StaticStore.gridselectedpos);

		gridView.setAdapter(imageAdapter);
		int normalColor[] = {Color.rgb(255, 255, 255),Color.rgb(255, 128, 0),Color.rgb(255, 255, 255)};
		gridView.setSelector((new GradientDrawable(Orientation.BOTTOM_TOP, normalColor)));
		gridView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView selectedPosition, 
					View v, int position, long ID) 
			{                
				Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
				if(sessionflag){
					StaticStore.gridselectedpos = position;
					StaticStore.gridupdateneeded = true;
					imageAdapter.setSelectedPosition(position);
					if(position == 0){
						StaticStore.isInbox = false;
						Intent myIntent = StaticStore.midlet.getPayRegList(getActivity());
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 1){
						StaticStore.isInbox = false;
						Intent myIntent = StaticStore.midlet.get_AccountsMenu(getActivity()); 
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 2){
						StaticStore.isInbox = false;
						Intent myIntent = StaticStore.midlet.getDeposits(getActivity()); 
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 3){
						StaticStore.isInbox = false;
						Intent myIntent = StaticStore.midlet.getMyServices(getActivity()); 
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 4){
                        StaticStore.isInbox = false;
						if (StaticStore.impsFlag) {
						Intent myIntent =	 StaticStore.midlet.getIMPSOptionMenu(getActivity());
							StaticStore.midlet.startFragment(getActivity(),myIntent);
						}else{
						    StaticStore.ToastDisplay(getActivity(), disableMsg);
						}
                    }else if(position == 5){
                    	StaticStore.isInbox = false;
    					Intent myIntent = StaticStore.midlet.getLogOnlyMenu(getActivity());
    					StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 6 && StaticStore.mobileRechargeFlag){
						StaticStore.isInbox = false;
                        StaticStore.rechargeStatus = false;
						StaticStore.midlet.moreFlag = false;
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(161);
						StaticStore.midlet.listObject.setTag("R5");
						StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
                    }else if(position == 7){
                    	StaticStore.isInbox = false;
    					Intent myIntent = StaticStore.midlet.get_Settings(getActivity()); 
    					StaticStore.midlet.startFragment(getActivity(),myIntent);
					}else if(position == 8){
						StaticStore.isInbox = false;
						StaticStore.isPinChangeFromTabbar = true;
						StaticStore.tagType = "APP2";
						StaticStore.menuDesc[220] = new String []{"Change mPIN","APP2;Y",StaticStore.mPINString+" ","New mPIN","Confirm New mPIN","4-4-N-Y-Y","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"};
						StaticStore.index =  220;
					    intent = new Intent(getActivity(),DynamicCanvas.class);
						StaticStore.midlet.startFragment(getActivity(),intent);
					}
				}
			}
		});  

		//<------ Font Style Part Start ---->

		final Typeface mFont = Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface); 
		final ViewGroup mContainer = (ViewGroup)rootView ; //contentgetRootView()
		GridScreenView_Left.setAppFont(mContainer, mFont,(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
		//<------ Font Style Part End ---->
		return rootView;
	}

	@Override
	public void onDetach() {
		super.onDetach();

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

	private Map<Integer, Image> setListTabBar() {
		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
		tabBarSetter.put(1,  new Image(R.drawable.icon_inbox, "Messages") );
		tabBarSetter.put(2,  new Image(R.drawable.icon_help, "Help") );
		tabBarSetter.put(3, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")) );
		return tabBarSetter;
	}
	private OnClickListener tabBarListener = new OnClickListener() { 
		public void onClick(View v) {
			StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
			if(sessionflag){
				if (v.getId() == 1) {
	                  if(StaticStore.testbuild){
						  if(StaticStore.isMpinAtLast){
							  StaticStore.isMpinAtLast = false;
						  }else{
							 StaticStore.isMpinAtLast = true;
						  }
					  }
					  StaticStore.midlet.savelistinit();
					  StaticStore.isInbox = true;
					  StaticStore.midlet.responseMessages = RmsStore.readInboxRecordStore(RmsStore.parsedRecords, StaticStore.midlet.responseMessages);
					  if(StaticStore.midlet.responseMessages == null){
					      StaticStore.ToastDisplay(getActivity(),"Message Inbox is empty");
				      }else{
					      Intent myIntent = StaticStore.midlet.get_ResponseInbox(getActivity());
					      StaticStore.midlet.startFragment(getActivity(),myIntent);
				      }
				}else if ( v.getId() == 2) {
					Intent intent = new Intent(getActivity(),HelpScreen.class);
					intent.putExtra("title" , "Help");
					intent.putExtra("index" , "9841" );
					StaticStore.midlet.startFragment(getActivity(),intent);
				}else if ( v.getId() == 3) {
					exit();
				}  
			}
		}
	};
	private void exit() {
		exitMIDlet(new AlertDialog.Builder(getActivity()),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,getActivity()).show();			
	}
	public class ImageAdapter extends ArrayAdapter 
	{
		private Context context;
		private int selectedPos;

		public ImageAdapter(Context c,int selectedPos) 
		{
			super(c, selectedPos);
			this.context = c;
		}
		public void setSelectedPosition(int pos) {
			selectedPos = pos;
		}

		public int getSelectedPosition() {
			return selectedPos;
		}
		//---returns the number of images---
		public int getCount() {
			return imageIDs.length;
		}
		//---returns the ID of an item--- 
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		//---returns an ImageView view---
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View v;
			ImageView imageView; 
			TextView tv;
			if(convertView == null){
				v = LayoutInflater.from(context).inflate(R.layout.gridiconleft,null);
				v.setPadding(10, 0, 10,0);


				if(StaticStore.height != 0 && StaticStore.width !=0 && StaticStore.istablet){
					if(gridLeftsidepane){
						if(selectedPos == position){
							v.setBackgroundResource(R.drawable.grid_bgsel);
						}
						v.setLayoutParams(new GridView.LayoutParams((StaticStore.width/3),(StaticStore.HF_Height/9))); //((StaticStore.height - 164)/6))
					}else{
						v.setLayoutParams(new GridView.LayoutParams((StaticStore.width/3),(StaticStore.HF_Height/3)));
					}
				}else{
					v.setLayoutParams(new GridView.LayoutParams((StaticStore.width)/2,StaticStore.HF_Height/3));
				}

				imageView = (ImageView)v.findViewById(R.id.icon_imageleft);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageResource(imageIDs[position]);
				tv = (TextView) v.findViewById(R.id.icon_textleft);
				tv.setTextColor(Color.rgb(18, 56, 127));
				tv.setTextSize((StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
				tv.setTypeface(mFontBold);
				tv.setText(GirdMenus[position]);
			}else{
				v = convertView;
			}
			return v;
		}
	}  



	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		exit();
		return true;
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
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
		});
		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			// do something when the button is clicked
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});

		return alertbox;
	}

	private class DownloadLogoutImageTask extends AsyncTask<String, Void, byte[]> {
		
		InputStream is;
		byte[] ba;
		protected byte[] doInBackground(String... urls) {
			StaticStore.LogoutImagecalled = true;
			for(String urldisplay:urls){
				try {
					is = null;
					ba = null;
					try
					{
						URL url = new URL(urldisplay);
						startTime = System.currentTimeMillis();
						URLConnection ucon = url.openConnection();
						InputStream is = ucon.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ByteArrayBuffer baf = new ByteArrayBuffer(bis.available());
                        int current = 0;
                        while ((current = bis.read()) != -1) {
                                baf.append((byte) current);
                        }
                        ba  = baf.toByteArray();
                        is.close();
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return ba;
		}

		protected void onPostExecute(byte[] result) {
			if(result != null){
				int size = result.length;
				StaticStore.LogoutImageStream = result;
			}
		}
	
}
}
