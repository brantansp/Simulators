package com.fss.tmb;
//
//package com.fss.uco;
//
//
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.TreeMap;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.graphics.drawable.GradientDrawable;
//import android.graphics.drawable.GradientDrawable.Orientation;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.ViewTreeObserver.OnGlobalLayoutListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//
//public class GridScreenViewActivation_Left  extends android.support.v4.app.Fragment{    
//	//for tabbar
//	  LinearLayout gLayout;
////	  getActivity() getActivity();
//	  ImageView loginbtn;
//	  EditText act_pass;
//	  LinearLayout act_loginll,signup,login,refer,feedback,products,locator;
//	  
//	  private String disableMsg = "This service is temporarily unavailable. Inconvenience is regretted.";
//	  GridImageAdapter girdimageAdapter;
//		//---the images to display---
//		Integer[] act_imageIDs ;
//		String[] act_GirdMenus ;
//		
//		Typeface mFontBold;
//
//    Intent intent ;
//    ProgressDialog dialog;
//    boolean authAlert;
//    int edtxtheight = 0;
//    @Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		 View rootView = inflater.inflate(R.layout.left_activation, container, false);
//		 
//		 if(StaticStore.IsPermitted){
//	   			act_imageIDs = new Integer[]{R.drawable.signup,R.drawable.login,R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
//	   	 		act_GirdMenus = new String[]{"SignUp ","Forgot Password ","Products ","Refer ","Locator ","Feedback "};
//	   		 }else{
//	   			 act_imageIDs = new Integer[]{R.drawable.signup,R.drawable.login,R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
//	  			 act_GirdMenus = new String[]{"SignUp ","Login ","Products ","Refer ","Locator ","Feedback "};
//	   		 }
//	         
//    	 //        super.onCreate(savedInstanceState);
//        
////        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        
////        setContentView(R.layout.main);
////        setContentView(R.layout.main_activation);
//        
////        act_loginll = (LinearLayout) findViewById(R.id.act_loginll);
////        loginbtn = (ImageView) findViewById(R.id.act_loginbtn);
////        act_pass = (EditText) findViewById(R.id.act_pwd);
//        
////        act_loginll.setVisibility(View.INVISIBLE);
////        final LinearLayout tab = StaticStore.Tabbar(tabBarListener,setListTabBar(),GridScreenViewActivation.this);
////   	 final ViewTreeObserver observer1= act_pass.getViewTreeObserver();
////        observer1.addOnGlobalLayoutListener(
////            new ViewTreeObserver.OnGlobalLayoutListener() {
////                public void onGlobalLayout() {
////                	edtxtheight  = act_pass.getHeight();
////                    }
////                });
//        
////        ViewTreeObserver vto = act_pass.getViewTreeObserver(); 
////        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
////            public void onGlobalLayout() { 
//////                Toast.makeText(getActivity(), act_pass.getWidth() + " x " + act_pass.getHeight(), Toast.LENGTH_LONG).show();
//////                edtxtheight  = act_pass.getHeight();
////                loginbtn.setHeight(act_pass.getHeight());
////                act_pass.getViewTreeObserver().removeGlobalOnLayoutListener(this);
////            } 
////        }); 
//        
//        
//         StaticStore.LogPrinter('i',"act_pass.getHeight() ==> "+edtxtheight);
//        
////        LinearLayout act_grid = (LinearLayout) rootView.findViewById(R.id.act_grid);
//        
//       
//        GridView act_GridView = (GridView) rootView.findViewById(R.id.gridview);
////        act_GridView.setGravity(Gravity.CENTER);
////        act_GridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
////        act_GridView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,GridView.LayoutParams.WRAP_CONTENT));
//        
//        if(StaticStore.istablet){
//        act_GridView.setNumColumns(1);
//        }
//        
//        girdimageAdapter = new GridImageAdapter(getActivity(),-1);
////		girdimageAdapter.setNotifyOnChange(true);
//        girdimageAdapter.setSelectedPosition(StaticStore.gridselectedpos);
//		
//		act_GridView.setAdapter(girdimageAdapter);
////		int normalColor[] = {Color.rgb(255, 255, 255),Color.rgb(255, 128, 0),Color.rgb(255, 255, 255)};
////		act_GridView.setSelector((new GradientDrawable(Orientation.BOTTOM_TOP, normalColor)));
//		
//		//        gridView.setSelection(StaticStore.selectedGridIndex);
//
//		act_GridView.setOnItemClickListener(activationgridlist);
//		
////		act_grid.addView(act_GridView);
//        
//        
//       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.header);
//   	 /*tabbar comp*/ 	  
////        StaticStore.HF_Height = 0;
////        final LinearLayout llhead = (LinearLayout)findViewById(R.id.llhead);
////        
////        final ViewTreeObserver observer= llhead.getViewTreeObserver();
////        observer.addOnGlobalLayoutListener(
////            new ViewTreeObserver.OnGlobalLayoutListener() {
////                public void onGlobalLayout() {
////                	StaticStore.Header_Height = llhead.getHeight();
////                    }
////                });
//        
//        
////	     gLayout = (LinearLayout)findViewById(R.id.gridList);
////	     RelativeLayout rl1 = (RelativeLayout)findViewById(R.id.rl1);
////	     
////	     signup = (LinearLayout)findViewById(R.id.btn_signup);
////	     login = (LinearLayout)findViewById(R.id.btn_login);
////	     refer = (LinearLayout)findViewById(R.id.btn_refer);
////	     feedback = (LinearLayout)findViewById(R.id.btn_feedback);
////	     products = (LinearLayout)findViewById(R.id.btn_prod);
////	     locator = (LinearLayout)findViewById(R.id.btn_locator);
////	     
////	     signup.setOnClickListener(ActGridClick);
////	     login.setOnClickListener(ActGridClick);
////	     refer.setOnClickListener(ActGridClick);
////	     feedback.setOnClickListener(ActGridClick);
////	     products.setOnClickListener(ActGridClick);
////	     locator.setOnClickListener(ActGridClick);
//	     
////	     try {
////	    	 final LinearLayout tab = StaticStore.Tabbar(tabBarListener,setListTabBar(),GridScreenViewActivation.this);
////	    	 final ViewTreeObserver observer1= tab.getViewTreeObserver();
////	         observer1.addOnGlobalLayoutListener(
////	             new ViewTreeObserver.OnGlobalLayoutListener() {
////	                 public void onGlobalLayout() {
////	                	 StaticStore.Footer_Height = tab.getHeight();
////	                     }
////	                 });
////	    	 gLayout.addView(tab);
////	 		} catch (Exception e) {
////	 			// TODO Auto-generated catch block
////	 			e.printStackTrace();
////	 		}
//
//	 		//<------ Font Style Part Start ---->
//
////			final Typeface mFont = Typeface.createFromAsset(getAssets(),StaticStore.mFont_Bold_Typeface); 
////			final ViewGroup mContainer = (ViewGroup)rl1 ; //contentgetRootView()
////			GridScreenViewActivation.setAppFont(mContainer, mFont,(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
//
//			//<------ Font Style Part End ---->
//	 		
//	return rootView;
//    }
//    
//    public static boolean onBackPressed(Context context2) {
//    	if(StaticStore.indexCtr < 0){	
//    		Intent mPayIntent = new Intent(context2 ,GridScreenViewActivation.class);
//    		StaticStore.midlet.startFragment(context2,mPayIntent);
//    		return true;
//    	}else if (  StaticStore.indexCtr > 0 ){//tab  &&  index != 44 &&  index != 66 &&  index != 137 //&& index != 72 &&  index != 121 &&  index != 122 &&  index != 1 &&  index != 12 &&  index != 75
//    		//return super.onKeyDown(keyCode, event);
//    		StaticStore.isBack = true;
//    		if(StaticStore.indexCtr > 0){
//    			StaticStore.indexCtr -= 1;
//    		}
//    		if(StaticStore.indexCtr == 1)
//    		{
//    			StaticStore.midlet.isImageTextList = true;
//    		}else
//    		{
//    			StaticStore.midlet.isImageTextList = false;
//    		}
//    		StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>"+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
//    		int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
//    		int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];
//    		Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
//    		StaticStore.midlet.startFragment(context2,myIntent);
//    		return true;
//    	}else{
//    		return false;
//    	}
//    }
//    private OnItemClickListener activationgridlist = new OnItemClickListener() { 
//    	public void onItemClick(AdapterView selectedPosition,View v, int position, long ID) 
//    	{
//    		if(position == 1){
//    			StaticStore.selectedLoginGridIndex = 6;
//    		}else{
//    				StaticStore.selectedLoginGridIndex = position;
//    		}
//    		StaticStore.midlet.MenuSelected(getActivity());
//    	}
//
//		
//    };
//    public class GridImageAdapter extends ArrayAdapter 
//	{
//		private Context context;
//		private int selectedPos;
//		
//		public GridImageAdapter(Context c,int selectedPos) 
//		{
//			super(c, selectedPos);
//			this.context = c;
//		}
//		public void setSelectedPosition(int pos) {
//			selectedPos = pos;
////			 StaticStore.LogPrinter('i',"selectedPos ===> "+selectedPos);
//			// inform the view of this change
////			notifyDataSetChanged();
//		}
//
//		public int getSelectedPosition() {
//			return selectedPos;
//		}
//		//---returns the number of images---
//		public int getCount() {
//			return act_imageIDs.length;
//		}
//		//---returns the ID of an item--- 
//		public Object getItem(int position) {
//			return position;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		//---returns an ImageView view---
//		public View getView(int position, View convertView, ViewGroup parent) 
//		{
//			View v;
//			ImageView imageView; 
//			TextView tv;
//			//        if(convertView == null){
//			v = LayoutInflater.from(getActivity()).inflate(R.layout.gridiconleft,null);
//			v.setPadding(10, 0, 10,0);
//			 
//			
//			if(StaticStore.height != 0 && StaticStore.width !=0 && StaticStore.istablet){
////				StaticStore.LogPrinter('i',"StaticStore.height  == >"+ StaticStore.height);
////				StaticStore.LogPrinter('i',"StaticStore.width  == >"+ StaticStore.width);
////				StaticStore.LogPrinter('i',"StaticStore.Leftsidepane  == >"+ StaticStore.Leftsidepane);
////				if(gridLeftsidepane){
////					 StaticStore.LogPrinter('i',"selectedPos GETVIEW ==> "+selectedPos);
//					 if(StaticStore.IsPermitted && StaticStore.selectedLoginGridIndex == position && position != 1){
//						  v.setBackgroundResource(R.drawable.grid_bgsel);
//					  }else if(StaticStore.IsPermitted && StaticStore.selectedLoginGridIndex == 6 && position == 1 ){
//						  v.setBackgroundResource(R.drawable.grid_bgsel);
//					  }else if(!StaticStore.IsPermitted && StaticStore.selectedLoginGridIndex == position){
//						  v.setBackgroundResource(R.drawable.grid_bgsel);
//					  }
////					StaticStore.LogPrinter('i',"StaticStore.Leftsidepane  IFFFFFFFFFFF == >"+ (StaticStore.height - 164)/6);
//					v.setLayoutParams(new GridView.LayoutParams((StaticStore.width/3),((StaticStore.IsPermitted?(StaticStore.height - 110)/6:(StaticStore.height - 110)/6)))); //((StaticStore.height - 164)/6))
////				}else{
////					StaticStore.LogPrinter('i',"StaticStore.Leftsidepane  ELSEEEEEEEEEEEE== >"+ (StaticStore.height - 264)/3);
////				v.setLayoutParams(new GridView.LayoutParams((StaticStore.width/3),((StaticStore.height - 264)/3)));
////				}
//			}
//
//			imageView = (ImageView)v.findViewById(R.id.icon_imageleft);
//			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////			imageView.setPadding(10, 0, 40, 0);
//			imageView.setImageResource(act_imageIDs[position]);
//			tv = (TextView) v.findViewById(R.id.icon_textleft);
//			tv.setTextColor(Color.BLACK);
//			tv.setTextSize((StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
//			tv.setTypeface(mFontBold);
//			tv.setText(act_GirdMenus[position]);
////			tv.setPadding(5, 0, 10, 0);
//
//			//        }
//		//        else
//			//        {
//			//        	v = convertView;
//			//        }
//
//
//			return v;
//		}
//
//	}  
//    public static final void setAppFont(ViewGroup mContainer, Typeface mFont,int mFontsize)
//	{
//	    if (mContainer == null || mFont == null) return;
//
//	    final int mCount = mContainer.getChildCount();
//
//	    // Loop through all of the children.
//	    for (int i = 0; i < mCount; ++i)
//	    {
//	        final View mChild = mContainer.getChildAt(i);
//	        if (mChild instanceof TextView)
//	        {
//	            // Set the font if it is a TextView.
//	            ((TextView) mChild).setTypeface(mFont);
//	            //Set the Text Size
//	            ((TextView) mChild).setTextSize(mFontsize);
//	        }
//	        else if (mChild instanceof ViewGroup)
//	        {
//	            // Recursively attempt another ViewGroup.
//	            setAppFont((ViewGroup) mChild, mFont,mFontsize);
//	        }
//	    }
//	}
//    
////    public boolean onCreateOptionsMenu(Menu menu) {
////		super.onCreateOptionsMenu(menu);
////		menu.add(0, 1, 2, (StaticStore.enableHome?"Logout":"Exit"));
////		return true;
////	}
////    public boolean onOptionsItemSelected(MenuItem item) {
////		// TODO Auto-generated method stub
////		if (item.getGroupId() == 0) {
////			exitMIDlet(new AlertDialog.Builder(this),
////					"Do you want to "+(StaticStore.enableHome?"logout ":"exit ")+"?", 100,getApplicationgetActivity()()).show();
////		}
////		super.onOptionsItemSelected(item);
////		return true;
////	}   
//    
//   
//   
//
////	   private Map<Integer, Image> setListTabBar() {
////
////		   Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
////		   tabBarSetter.put(0,  new Image(R.drawable.icon_about, "About Us"));
////		   tabBarSetter.put(1,  new Image(R.drawable.icon_about, "Version Update"));
////		   tabBarSetter.put(2,  new Image(R.drawable.icon_help, "Help"));
//////		   tabBarSetter.put(6, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")) );
////		
////
////		   return tabBarSetter;
////	   }
////	   private OnClickListener tabBarListener = new OnClickListener() { 
////		   public void onClick(View v) {
////			   Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(GridScreenViewActivation_Left.this);
////			   if(sessionflag){
////				   if (v.getId() == 0 ) {
////					   startActivity(StaticStore.midlet.getAbout(getApplicationgetActivity()()));
////				   } else if (v.getId() == 1) {
////					   StaticStore.isFromLoginScreen = true;
////					   StaticStore.isFromVersionUpdate = true; //siva 
////					   StaticStore.selectedLoginGridIndex = 99; //don't delete this - siva 
////
////					   if(!StaticStore.IsPermitted && !StaticStore.IsPersonalInfoGot) {
////						   StaticStore.index = 154;
////						   startActivity(new Intent(GridScreenViewActivation_Left.this,
////								   DynamicCanvas.class));
////					   }
////					   else if(StaticStore.IsPermitted && StaticStore.IsGPRS &&!StaticStore.isSuccessGC)
////					   {
////						   StaticStore.menuDesc[220] = new String []{"GPRS Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
////						   StaticStore.tagType = "APCG";
////						   StaticStore.index = 220;
////						   startActivity(new Intent(GridScreenViewActivation_Left.this,
////								   DynamicCanvas.class));
////					   }else {
////						   StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
////						   StaticStore.tagType = "APBV";
////						   StaticStore.index = 220;
////						   startActivity(new Intent(GridScreenViewActivation_Left.this,
////								   DynamicCanvas.class));
////					   }
////
////					   //		        	if(StaticStore.IsPermitted){
////					   //		      		   if(StaticStore.IsGPRS){
////					   //		      			   if(StaticStore.isSuccessGC){	                   
////					   //		                     StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,StaticStore.mobileType,"","","2","false","false","N"};
////					   //		    					StaticStore.tagType = "APBV";
////					   //		    					StaticStore.index = 220;
////					   //		    				    startActivity(new Intent(GridScreenViewActivation.this,
////					   //		    							DynamicCanvas.class));
////					   //		      			   }else{
////					   //		      				  
////					   ////		      				 StaticStore.menuDesc[220] = new String []{"GPRS Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileDetails + "#" +StaticStore.mobileDetails,"","1","false","false","N"};
////					   ////		    					StaticStore.tagType = "APCG";
////					   ////		    					StaticStore.index = 220;
////					   //		      				 StaticStore.menuDesc[220] = new String []{"GPRS Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
////					   //		      				 StaticStore.tagType = "APCG";
////					   //		         			StaticStore.index = 220;
////					   //		    				    startActivity(new Intent(GridScreenViewActivation.this,
////					   //		    							DynamicCanvas.class));
////					   //		      			   }
////					   //		      		   }else{	                   
////					   //		                     StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,StaticStore.mobileType,"","","2","false","false","N"};
////					   //		    					StaticStore.tagType = "APBV";
////					   //		    					StaticStore.index = 220;
////					   //		    				    startActivity(new Intent(GridScreenViewActivation.this,
////					   //		    							DynamicCanvas.class));
////					   //		      			   }
////					   //		      	   }else if(StaticStore.IsPersonalInfoGot){	                   
////					   //	                     StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,StaticStore.mobileType,"","","2","false","false","N"};
////					   //	    					StaticStore.tagType = "APBV";
////					   //	    					StaticStore.index = 220;
////					   //	    				    startActivity(new Intent(GridScreenViewActivation.this,
////					   //	    							DynamicCanvas.class));
////					   //	      			   }else{	      				 
////					   //	      				 StaticStore.index = 154;
////					   //	      	   			 startActivity(new Intent(GridScreenViewActivation.this,
////					   //	      	   						DynamicCanvas.class));
////					   //		      	  }          	
////
////				   }else if(v.getId() == 2){
////					   Intent intent = new Intent(GridScreenViewActivation_Left.this,HelpScreen.class);
////					   intent.putExtra("title" , "");
////					   intent.putExtra("index" , "9840" );
////					   startActivity(intent);
////
////				   } else if (v.getId() == 6) {
////					   //				   exit();				
////				   } 
////			   }
////		   }
////	   };
//
//	   private boolean loginalert;
////	   private void exit() {
////		   exitMIDlet(new AlertDialog.Builder(getActivity()),
////				   "Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,getActivity()).show();			
////	   }
//	   //    public class ImageAdapter extends BaseAdapter 
//	   //    {
//	   //        private getActivity() getActivity();
//	   // 
//	   //        public ImageAdapter(getActivity() c) 
//	   //        {
//	   //            getActivity() = c;
//	   //        }
//	   // 
//	   //        //---returns the number of images---
//	   //        public int getCount() {
//	   //            return imageIDs.length;
//	   //        }
//	   //        //---returns the ID of an item--- 
//	   //        public Object getItem(int position) {
//	   //            return position;
//	   //        }
//	   // 
//	   //        public long getItemId(int position) {
//	   //            return position;
//	   //        }
//	   // 
//	   //        //---returns an ImageView view---
//	   //        public View getView(int position, View convertView, ViewGroup parent) 
//	   //        {
//	   //            ImageView imageView;
//	   //            if (convertView == null) {
//	   //                imageView = new ImageView(getActivity());
//	   //                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));                
//	   //                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//	   //                imageView.setPadding(20,20,20,20);                
//	   //            } else {
//	   //                imageView = (ImageView) convertView;
//	   //            }
//	   //            imageView.setImageResource(imageIDs[position]);
//	   //            return imageView;            
//	   //        }
//	   //        
//	   //    }  
//
//
//
////	   public boolean onKeyDown(int keyCode, KeyEvent event) {			
////		   if (keyCode == KeyEvent.KEYCODE_BACK) {			
////			   exit();
////			   return true;
////		   } else{
////			   return super.onKeyDown(keyCode, event);
////		   }
////	   }
//
////	   public AlertDialog.Builder exitMIDletAlert(AlertDialog.Builder tempAlertBox,
////			   String message, int index, final getActivity() getActivity()) {
////
////		   AlertDialog.Builder alertbox = tempAlertBox;
////
////		   alertbox.setMessage(message);
////
////		   alertbox.setPositiveButton("OK",
////				   new DialogInterface.OnClickListener() {
////			   public void onClick(DialogInterface arg0, int arg1) {
////				   //   StaticStore.LogPrinter('i',">>>>>>>>>>ready to close>>>>>>>");
////				   // new ListSelection().closeApplication();
////				   if(loginalert)
////				   {	authAlert=true;
////				   loginalert=false;
//////				   new UploadViewTask().execute(); 
////
////				   }else{
////					   Intent intent = new Intent(getActivity(),SplashScreen.class);
////					   //   StaticStore.LogPrinter('i',"???????"+getActivity().getClass());
////					   intent.putExtra("EXIT", true);
////					   StaticStore.muTerminate=true;
////					   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////					   startActivity(intent);
////				   }
////			   }
////		   });
////		   // set a negative/no button and create a listener
////
////
////
////		   return alertbox;
////	   }
//	   
////	   public AlertDialog.Builder exitMIDlet(AlertDialog.Builder tempAlertBox,
////			   String message, int index, final getActivity() getActivity()) {
////
////		   AlertDialog.Builder alertbox = tempAlertBox;
////
////		   alertbox.setMessage(message);
////
////		   alertbox.setPositiveButton("Yes",
////				   new DialogInterface.OnClickListener() {
////			   public void onClick(DialogInterface arg0, int arg1) {
////				   //   StaticStore.LogPrinter('i',">>>>>>>>>>ready to close>>>>>>>");
////				   // new ListSelection().closeApplication();
////				   if(loginalert)
////				   {	authAlert=true;
////				   loginalert=false;
//////				   new UploadViewTask().execute(); 
////
////				   }else{
//////					   Intent intent = new Intent(getActivity(),SplashScreen.class);
//////					   //   StaticStore.LogPrinter('i',"???????"+getActivity().getClass());
//////					   intent.putExtra("EXIT", true);
//////					   StaticStore.muTerminate=true;
//////					   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//////					   startActivity(intent);
////					   Intent myIntent = StaticStore.midlet.getExitScreen(getActivity());
////						startActivity(myIntent);
////				   }
////			   }
////		   });
////		   // set a negative/no button and create a listener
////		   alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
////			   // do something when the button is clicked
////			   public void onClick(DialogInterface arg0, int arg1) {
////				   return;
////			   }
////		   });
////
////		   return alertbox;
////	   }
//	   
////	   @Override
////	   protected void onResume() {
////		   // TODO Auto-generated method stub
////		   if (StaticStore.muTerminate == true) {
////			   //   StaticStore.LogPrinter('i',"enter into onResume" + StaticStore.muTerminate);
////			   finish();
////		   }
////		   super.onResume();
////	   }
////	   
////	   void show() {
////		   dialog.setMessage("  Loading...");
////		   dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
////		   dialog.show();
////	   }
////	   void hide() {
////		   dialog.dismiss();
////	   }
//	   
////	   private class UploadViewTask extends AsyncTask<Void, Void, Void>
////	   {
////		   //Before running code in the separate thread
////		   @Override
////		   protected void onPreExecute() 
////		   {
////			   //Create a new progress dialog
////			   show();
////		   }
////
////		   //The code to be executed in a background thread.
////		   @Override
////		   protected Void doInBackground(Void... params) 
////		   {
////			   /* This is just a code that delays the thread execution 4 times, 
////			    * during 850 milliseconds and updates the current progress. This 
////			    * is where the code that is going to be executed on a background
////			    * thread must be placed. 
////			    */
////			   try 
////			   {
////
////
////
////				   //Get the current thread's token
////				   synchronized (this) 
////				   {
////					   //Initialize an integer (that will act as a counter) to zero
////					   int counter = 0;
////					   //While the counter is smaller than four
////					   //						while(counter <= 4)
////					   //						{
////					   //Wait 850 milliseconds
////					   this.wait(1000);
////					   //Increment the counter 
////					   //							counter++;
////					   //Set the current progress. 
////					   //This value is going to be passed to the onProgressUpdate() method.
////					   //		publishProgress(counter*25);
////					   //						}
////				   }
////
////			   }
////			   catch (Exception e) 
////			   {
////				   e.printStackTrace();
////			   }
////			   return null;
////		   }
////
////		   //Update the progress
////		   //			@Override
////		   //			protected void onProgressUpdate(Integer... values) 
////		   //			{
////		   //				//set the current progress of the progress dialog
////		   //				progressDialog.setProgress(values[0]);
////		   //			}
////
////		   //after executing the code in the thread
////		   @Override
////		   protected void onPostExecute(Void result) 
////		   {
////			   //close the progress dialog
////			   hide();
////			   if(loginalert){
////				   loginalert();
////			   }
////			   if(authAlert)
////			   {
////				   StaticStore.index = 123;
////				   startActivity(new Intent(GridScreenViewActivation_Left.this,DynamicCanvas.class));
////				   authAlert=false;
////			   }
////		   } 	
////	   }
////	   private void loginalert() {
////		   // TODO Auto-generated method stub
////
////		   exitMIDletAlert(new AlertDialog.Builder(this), "For secure banking please allow application to send an SMS to validate your mobile number", 100,
////				   GridScreenViewActivation_Left.this).show();
////	   }
//}
