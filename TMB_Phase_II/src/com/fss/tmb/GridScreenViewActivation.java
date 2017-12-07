
package com.fss.tmb;


import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;


import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class GridScreenViewActivation extends Activity{    
	//for tabbar
	  LinearLayout gLayout,act_grid;
	  LinearLayout actTab,tab;
	  TableLayout Gridbtntable;
	  TableRow[] Gridrow;
	  Button[] Gridbtn;
	  Context context;
	  ImageView loginbtn;
	  EditText act_pass;
	  EditText edpword;
	  Button btn[];
	  LinearLayout act_loginll,signup,login,refer,feedback,products,locator;
	  ScrollView actScrollView;
	  private String disableMsg = "This service is temporarily unavailable. Inconvenience is regretted.";
		//---the images to display---
		Integer[] act_imageIDs;
		String[] act_GirdMenus;
		
		Typeface mFontBold;

    Intent intent ;
    ProgressDialog dialog;
    boolean authAlert;
    int edtxtheight = 0;
    @Override    
    public void onCreate(Bundle savedInstanceState) 
    {
    	StaticStore.eLobbyLocationFlag = false;
		if (!StaticStore.istablet) {
			StaticStore.gridNofragHeader.setVisibility(View.GONE);
			StaticStore.gridNolineHeader.setVisibility(View.GONE);
		}
    	context = this;
    	 StaticStore.indexCtr = 0;
    	 StaticStore.enableHome = false;
         StaticStore.listIndexArray = new int[10];
 		 StaticStore.selectedIndexArray = new int[10];
 		 StaticStore.listContent = new String[10][10];
 		 StaticStore.listHeading = new String[10];
 		 StaticStore.listMore = new boolean[10];		 
 		 StaticStore.listImageArray = new boolean[10];		 
         StaticStore.midlet.isProcessing = false;
         StaticStore.isInbox = false;
         StaticStore.LogoutImagecalled = false;
         StaticStore.FromListScreen = false;
         StaticStore.tagType = "";
         StaticStore.isMenuFromDashBoard = false;
         super.onCreate(savedInstanceState);
         
    	 if(StaticStore.IsPermitted){
  			 act_imageIDs = new Integer[]{R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
  			 act_GirdMenus = new String[]{"Products ","Refer ","Locator ","Feedback "};
  		 }else{
  			 act_imageIDs = new Integer[]{R.drawable.aboutus,R.drawable.login,R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
  			 act_GirdMenus = new String[]{"About Us ","Login ","Products ","Refer ","Locator ","Feedback "};
  		 }
         
        
    	 /** Commented by ME on 16-Jan-2015 for Orientation Problem***/
        if(StaticStore.istablet){
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        }else{
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activation);
        actScrollView = (ScrollView) findViewById(R.id.act_scrollgsva);
	Loadlayout();
    }
    
    private void Loadlayout() {
		// TODO Auto-generated method stub
    	
         act_grid = (LinearLayout) findViewById(R.id.act_grid);
         
          actTab = (LinearLayout) findViewById(R.id.actTab);
         try {
        	  tab = StaticStore.Tabbar(acttabBarListener,setListTabBar(),StaticStore.context,true);
        	 actTab.addView(tab);
         } catch (Exception e) {
        	 e.printStackTrace();
         }
         
          StaticStore.LogPrinter('i',"act_pass.getHeight() ==> "+edtxtheight);
         
          if(StaticStore.Titlebar_Height == 0){
         	 StaticStore.Titlebar_Height = 60;
          }
          int w=0,h=0;
//      	w = StaticStore.width/4 ;
 		h = (((StaticStore.height/2) - 80));// - (StaticStore.Titlebar_Height*4))/2

		 StaticStore.LogPrinter('i',"grid h ===> "+h);
		 StaticStore.LogPrinter('i',"grid w ===> "+w);
       
       // <<<<--------------table layout Start ------>>>>>>>>
       Gridbtntable = new TableLayout(context);
		TableLayout.LayoutParams gridllm2 = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		gridllm2.setMargins(10, 0, 10, 0);
		Gridbtntable.setLayoutParams(gridllm2);
		
		Gridbtntable.setStretchAllColumns(true);
		Gridbtntable.setShrinkAllColumns(true);
		Gridrow = new TableRow[act_GirdMenus.length/2];
		Gridbtn = new Button[act_GirdMenus.length];
		Paint paint = new Paint();
		int col =0,row = 0;
		
	
		 StaticStore.LogPrinter('i',"StaticStore.Titlebar_Height ===> "+StaticStore.Titlebar_Height);
			h = (StaticStore.height - 130) / 3;
			w = (StaticStore.width - 40) / 2;
		
		for(int s = 0;s < (StaticStore.IsPermitted?2:3) ; s ++){
			Gridrow[row] = new TableRow(context);
			Gridrow[row].setGravity(Gravity.CENTER);
			for(int j=0; j<(StaticStore.IsPermitted?2:2) ; j++){
				Gridbtn[col] = new Button(context);
				Gridbtn[col].setId(col);
				Gridbtn[col].setGravity(Gravity.CENTER);
				Gridbtn[col].setTextColor(Color.rgb(18, 56, 127));
				Gridbtn[col].setBackgroundResource(R.drawable.gridselect);
				Gridbtn[col].setCompoundDrawablesWithIntrinsicBounds(0,act_imageIDs[col],0,0);
				Gridbtn[col].setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
				Gridbtn[col].setText(act_GirdMenus[col].toString());
				Gridbtn[col].setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
				if(StaticStore.istablet){
					Gridbtn[col].setPadding(1,15,0,15);
				}else{
					Gridbtn[col].setPadding(1,2,0,7);
				}	
				Gridbtn[col].setOnClickListener(tabBarListener);
				Gridrow[row].addView(Gridbtn[col]);
				col ++;
			}
			Gridbtntable.addView(Gridrow[row]);
			row ++;
		}
       
       // <<<<--------------table layout End -------->>>>>>>>
		
		
		if(StaticStore.IsPermitted){
			LinearLayout act_loginll = new LinearLayout(context);
			TableLayout table = new TableLayout(context);
			TableLayout.LayoutParams llm2 = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llm2.setMargins(10, 10, 0, 10);
			table.setLayoutParams(llm2);
			
			table.setStretchAllColumns(true);
			table.setShrinkAllColumns(true);
			TableRow datas = new TableRow(context);
			datas.setGravity(Gravity.CENTER_VERTICAL);
			datas.setBackgroundResource(R.drawable.edbg);
			
			Button imgbtnleft = new Button(context);
			imgbtnleft.setBackgroundResource(R.drawable.key);
			imgbtnleft.setGravity(Gravity.CENTER_VERTICAL);
			imgbtnleft.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,0.20f));
			datas.addView(imgbtnleft);
			
	
			
	    edpword = new EditText(context); 
		edpword.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,0.6f));
		edpword.setMaxLines(1);
		edpword.setHint("Password");
		edpword.setGravity(Gravity.CENTER_VERTICAL);
		edpword.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
		edpword.setHorizontallyScrolling(true);
		edpword.setInputType(524288|InputType.TYPE_CLASS_PHONE); 
		edpword.setBackgroundColor(color.white);
	
		InputFilter[] filterArray = new InputFilter[2];
		filterArray[0] = new InputFilter() {
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
				for (int i = start; i < end; i++) { 
					boolean inputnumeric=false;
					if ((Character.isDigit(source.charAt(i)))){ 
						inputnumeric=true;
					}else{
						inputnumeric=false;
						return "";  
					}
				}
				return null;   
			}  
		};

		filterArray[1] = new InputFilter.LengthFilter(4);
		edpword.setFilters(filterArray); 
		edpword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		datas.addView(edpword);
			
			
		Button imgbtn = new Button(context);
		imgbtn.setId(1222);
		imgbtn.setBackgroundResource(R.drawable.btnn);
		imgbtn.setOnClickListener(tabBarListener);
		imgbtn.setGravity(Gravity.CENTER_VERTICAL);
		imgbtn.setPadding(0, 10, 0, 10);
		imgbtn.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.FILL_PARENT,0.20f));
		datas.addView(imgbtn);
		
		table.addView(datas);
		
		
		TableRow datas1 = new TableRow(context);
//		Button signup = new Button(context);
//		signup.setTextColor(Color.rgb(18, 56, 127));
//		signup.setId(1333);
//		signup.setOnClickListener(tabBarListener);
//		signup.setBackgroundColor(Color.TRANSPARENT);
//		signup.setText(Html.fromHtml("<U>"+"Sign Up" +"</U>"));
//		signup.setGravity(Gravity.LEFT);
//		signup.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//		datas1.addView(signup);
		
		Button forgotpwd = new Button(context);
		forgotpwd.setTextColor(Color.rgb(18, 56, 127));
		forgotpwd.setId(1444);
		forgotpwd.setOnClickListener(tabBarListener);
		forgotpwd.setBackgroundColor(Color.TRANSPARENT);
		forgotpwd.setText(Html.fromHtml("<U>"+"Forgot Password?" +"</U>"));
		TableRow.LayoutParams trlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
		trlp.span = 2;
		forgotpwd.setLayoutParams(trlp);
		forgotpwd.setGravity(Gravity.RIGHT);
		datas1.addView(forgotpwd);
		table.addView(datas1);
		act_loginll.addView(table);
		
		act_grid.addView(Gridbtntable);
		act_grid.addView(act_loginll);
		}else{
			act_grid.addView(Gridbtntable);
		}
	}
    
	private OnItemClickListener activationgridlist = new OnItemClickListener() { 
    	public void onItemClick(AdapterView selectedPosition,View v, int position, long ID){ 
    			finish();
    			StaticStore.selectedLoginGridIndex = (StaticStore.IsPermitted?position+2:position);
    			 StaticStore.LogPrinter('i',"Selected index val ---> "+position);
    			StaticStore.midlet.MenuSelected(StaticStore.context);
		}
    };
   
    public static final void setAppFont(ViewGroup mContainer, Typeface mFont,int mFontsize){
	    if (mContainer == null || mFont == null) return;

	    final int mCount = mContainer.getChildCount();

	    // Loop through all of the children.
	    for (int i = 0; i < mCount; ++i)
	    {
	        final View mChild = mContainer.getChildAt(i);
	        if (mChild instanceof TextView){
	            // Set the font if it is a TextView.
	            ((TextView) mChild).setTypeface(mFont);
	            //Set the Text Size
	            ((TextView) mChild).setTextSize(mFontsize);
	        }else if (mChild instanceof ViewGroup){
	            // Recursively attempt another ViewGroup.
	            setAppFont((ViewGroup) mChild, mFont,mFontsize);
	        }
	    }
	}
    
    private Map<Integer, Image> setListTabBar() {

		   Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
//		   tabBarSetter.put(0,  new Image(R.drawable.icon_about, "About Us"));
		   tabBarSetter.put(1,  new Image(R.drawable.icon_version, "Version Update"));
		   tabBarSetter.put(2,  new Image(R.drawable.icon_help, "Help"));
//		   tabBarSetter.put(6, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")) );
		   return tabBarSetter;
	   }
    
    private OnClickListener acttabBarListener = new OnClickListener() { 
    	public void onClick(View v) {
    		StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
    		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
    		if(sessionflag){
    			if (v.getId() == 0 ) {
    				finish();
    				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
    			} else if (v.getId() == 1) {
    				finish();
    				StaticStore.isFromLoginScreen = true;
    				StaticStore.isFromVersionUpdate = true; //siva 
    				StaticStore.FromGridActivation = true;
    				StaticStore.selectedLoginGridIndex = 99; //don't delete this - siva 

    				if(!StaticStore.IsPermitted && !StaticStore.IsPersonalInfoGot) {
    					StaticStore.index = 154;
    					StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
    				}else if(StaticStore.IsPermitted && StaticStore.IsGPRS &&!StaticStore.isSuccessGC){
    					StaticStore.menuDesc[220] = new String []{"Internet Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
    					StaticStore.tagType = "APCG";
    					StaticStore.index = 220;
    					StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
    				}else {
    					StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
    					StaticStore.tagType = "APBV";
    					StaticStore.index = 220;
    					StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
    				}
    			}else if(v.getId() == 2){
    				finish();
    				Intent intent = new Intent(StaticStore.context,HelpScreen.class);
    				intent.putExtra("title" , "");
    				intent.putExtra("index" , "9840" );
    				StaticStore.midlet.startFragment(StaticStore.context,intent);
    			} 
    		}
    	}
    };

    private OnClickListener tabBarListener = new OnClickListener() { 
    	public void onClick(View v) {
    		StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
    		 StaticStore.LogPrinter('i',"v.getId()  ---> "+v.getId());
    		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
    		if(sessionflag){
    			 StaticStore.LogPrinter('i',"v.getId()v.getId()v.getId() === > "+v.getId());
     			if(v.getId() <= 5 && v.getId()>=0){
     				finish();
     				int position = v.getId();
     				StaticStore.selectedLoginGridIndex = (StaticStore.IsPermitted?position+2:position);
     	    		StaticStore.midlet.MenuSelected(StaticStore.context);
     			}else if(v.getId() == 1222){
    				StaticStore.selectedLoginGridIndex = 1;
    				StaticStore.index = 123;
    				if (edpword.getEditableText().toString().length() == 0) {
    					StaticStore.ToastDisplay(context,"Please enter Login Password");
    					edpword.requestFocus();
    				}else if (edpword.getEditableText().toString().length() < 4) {
    					StaticStore.ToastDisplay(context,"Login Password must be 4 digits");
    					edpword.requestFocus();
    				}else{ 
    					if(StaticStore.retryCount < 5){
    						String userPWD = StaticStore.midlet.hashingByMD5(edpword.getText().toString().trim());
    						if(userPWD.equals(StaticStore.applicationPWD.trim())&& StaticStore.retryCount < 5){
    							StaticStore.retryCount = 0;
    							finish();
    							StaticStore.FromGridActivation = true;
    							StaticStore.midlet.writeinMemory(StaticStore.context);
    							StaticStore.midlet.isPasswordEntered = true;

    							if(StaticStore.midlet.firstTimeLoading) {
    								StaticStore.midlet.firstTimeLoading = false;
    							} else {
    								if(StaticStore.IsGPRS){
    									StaticStore.menuDesc[220] = new String []{"Internet Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
    									StaticStore.tagType = "APCG";
    									StaticStore.index = 220;
    									StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
    								}else{
    									StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.Loginvalidation());
    								}
    							}
    						}else{
    							String msg = "";
    							StaticStore.retryCount++;
    							StaticStore.midlet.writeinMemory(StaticStore.context);
    							if(StaticStore.retryCount < 5){
    								StaticStore.LogPrinter('i',">>>>"+StaticStore.retryCount);
    								msg = "Invalid login, please enter correct Login Password";
    							}else{
    								msg ="User has been locked, Contact bank for further assistance";
    							}
    							StaticStore.ToastDisplay(context,msg);
    						}
    					}else{
    						StaticStore.ToastDisplay(context,"User has been locked, Contact bank for further assistance");
    					}
    				}
    			}else if(v.getId() == 1333){
//    				finish();
//    				StaticStore.selectedLoginGridIndex = 0;
//    				StaticStore.midlet.MenuSelected(StaticStore.context);
    				StaticStore.midlet.startFragment(context,StaticStore.midlet.getSignUpTypes(context));
    			}else if(v.getId() == 1444){
    				//Forgot Password
    				finish();
    				StaticStore.selectedLoginGridIndex = 6;
    				StaticStore.midlet.MenuSelected(StaticStore.context);
    			}
    		}
    	}
    };

	   private void exit() {
		   exitMIDlet(new AlertDialog.Builder(this),
				   "Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,StaticStore.context).show();			
	   }
	 

	   @Override
	   public boolean onKeyDown(int keyCode, KeyEvent event) {			
		   if (keyCode == KeyEvent.KEYCODE_BACK) {			
			   exit();
			   return true;
		   } else{
			   return super.onKeyDown(keyCode, event);
		   }
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
					StaticStore.midlet.startFragment(context,myIntent);
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
