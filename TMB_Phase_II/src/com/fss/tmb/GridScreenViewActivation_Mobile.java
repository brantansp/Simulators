
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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class GridScreenViewActivation_Mobile  extends android.support.v4.app.Fragment{    
	//for tabbar
	  LinearLayout gLayout;
	  LinearLayout actTab,tab;
	  TableLayout Gridbtntable;
	  Context context;
	  ImageView loginbtn;
	  EditText act_pass;
	  LinearLayout act_loginll;
	  RelativeLayout frag_main;
	  TableRow[] Gridrow;
	  Button[] Gridbtn;
	  Integer[] act_imageIDs;
	  String[] act_GirdMenus;
	  EditText edpword;
	  private String disableMsg = "This service is temporarily unavailable. Inconvenience is regretted.";

	  Typeface mFontBold;

    Intent intent ;
    ProgressDialog dialog;
    boolean authAlert;
    int edtxtheight = 0;
    @Override
  	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  		 View rootView = inflater.inflate(R.layout.main_activation, container, false);
  		StaticStore.eLobbyLocationFlag = false;
		if (!StaticStore.istablet) {
			StaticStore.gridNofragHeader.setVisibility(View.GONE);
			StaticStore.gridNolineHeader.setVisibility(View.GONE);
		}
		 
  		//---the images to display---
  		 if(StaticStore.IsPermitted){
  	 		act_imageIDs = new Integer[]{R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
  	 		act_GirdMenus = new String[]{"Products ","Refer ","Locator ","Feedback"};
  		 }else{
  			 act_imageIDs = new Integer[]{R.drawable.aboutus,R.drawable.login,R.drawable.products,R.drawable.refer,R.drawable.locator,R.drawable.feedback};
  			 act_GirdMenus = new String[]{"About Us ","Login ","Products ","Refer ","Locator ","Feedback"};
  		 }
    	context = getActivity();
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
         
         LinearLayout frag_main = (LinearLayout) rootView.findViewById(R.id.frag_main);
      
         actTab = (LinearLayout) rootView.findViewById(R.id.actTab);
		try {
			tab = StaticStore.Tabbar(acttabBarListener, setListTabBar(),StaticStore.context, false);
			actTab.addView(tab);
		} catch (Exception e) {
			e.printStackTrace();
		}
         
         if(StaticStore.Titlebar_Height == 0){
        	 StaticStore.Titlebar_Height = 60;
         }
         int w=0,h=0;
//     	w = StaticStore.width/4 ;
		h = (((StaticStore.height/2) - 80));// - (StaticStore.Titlebar_Height*4))/2

		
		 StaticStore.LogPrinter('i',"grid h ===> "+h);
		 StaticStore.LogPrinter('i',"grid w ===> "+w);
        
        // <<<<--------------table layout Start ------>>>>>>>>
        Gridbtntable = new TableLayout(context);
		TableLayout.LayoutParams gridllm2 = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		gridllm2.setMargins(10, 0, 10, 0);
		Gridbtntable.setLayoutParams(gridllm2);
		Gridbtntable.setGravity(Gravity.CENTER);
		Gridbtntable.setStretchAllColumns(true);
		Gridbtntable.setShrinkAllColumns(true);
		Gridrow = new TableRow[act_GirdMenus.length/2];
		Gridbtn = new Button[act_GirdMenus.length];
		Paint paint = new Paint();
		int col =0,row = 0;
	
		StaticStore.LogPrinter('i',"StaticStore.Titlebar_Height ===> "+StaticStore.Titlebar_Height);
		
		
		for(int s = 0;s < (StaticStore.IsPermitted?1:2) ; s ++){
			Gridrow[row] = new TableRow(context);
			Gridrow[row].setGravity(Gravity.CENTER);
			if(StaticStore.is_SmallMobile){
				Gridrow[row].setPadding(0, 0, 0, 5);
			}else{
				Gridrow[row].setPadding(0, 0, 0, 15);
			}
			for(int j=0; j<(StaticStore.IsPermitted?4:3) ; j++){
				Gridbtn[col] = new Button(context);
				Gridbtn[col].setId(col);
				Gridbtn[col].setGravity(Gravity.CENTER);
				Gridbtn[col].setTextColor(Color.rgb(18, 56, 127)); //Newly added me for selecting Menu in the main screen//
				Gridbtn[col].setBackgroundResource(R.drawable.gridselect);
				Gridbtn[col].setCompoundDrawablesWithIntrinsicBounds(0,act_imageIDs[col],0,0);
				Gridbtn[col].setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
				Gridbtn[col].setText(act_GirdMenus[col].toString());
				Gridbtn[col].setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
				if(StaticStore.istablet){
					Gridbtn[col].setPadding(1,15,0,0);
				}else{
					if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
						Gridbtn[col].setPadding(1,50,0,0);
					}else{
						Gridbtn[col].setPadding(1,2,0,0);
					}
				}	

				Gridbtn[col].setOnClickListener(tabBarListener);
				Gridrow[row].addView(Gridbtn[col]);
				col ++;
			}
			if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				Gridbtntable.setPadding(0, 50, 0, 0);
			}else if(StaticStore.height >= 1000){
				Gridbtntable.setPadding(0, 20, 0, 0);
			}else if(StaticStore.height >= 720 && StaticStore.height < 1000){
				Gridbtntable.setPadding(0, 20, 0, 0);
			}
			Gridbtntable.addView(Gridrow[row]);
			row ++;
		}
        
        // <<<<--------------table layout End -------->>>>>>>>
        
		LinearLayout act_loginll2 = new LinearLayout(context);
		TableLayout table2 = new TableLayout(context);
		TableLayout.LayoutParams llm22 = new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		TableRow pass_datas12 = new TableRow(context);
		if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
			act_loginll2.setBackgroundResource(R.drawable.banner3);
		}else if(StaticStore.height >= 1000){
			act_loginll2.setBackgroundResource(R.drawable.banner2);
		}else if(StaticStore.height >= 720 && StaticStore.height < 1000){
			act_loginll2.setBackgroundResource(R.drawable.banner1);
		}else{
			act_loginll2.setBackgroundResource(R.drawable.banner1);
		}
		
		llm22.setMargins(40, 0, 40, 20);
		table2.setLayoutParams(llm22);
		
		table2.addView( pass_datas12);
		act_loginll2.addView(table2);		
		
		
		    LinearLayout act_loginll1 = new LinearLayout(context);
			TableLayout table1 = new TableLayout(context);
			TableLayout.LayoutParams llm21 = new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			TableRow pass_datas11 = new TableRow(context);
			if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				act_loginll1.setBackgroundResource(R.drawable.login_top2);
			}else if(StaticStore.height >= 1000){
				act_loginll1.setBackgroundResource(R.drawable.login_top1);
			}else if(StaticStore.height >= 720 && StaticStore.height < 1000){
				act_loginll1.setBackgroundResource(R.drawable.login_top);
			}else{
				act_loginll1.setBackgroundResource(R.drawable.login_top);
			}
			llm21.setMargins(40, 0, 40, 20);
			table1.setLayoutParams(llm21);
			
			table1.addView( pass_datas11);
			act_loginll1.addView(table1);
			
	
		if(StaticStore.IsPermitted){
			LinearLayout act_loginll = new LinearLayout(context);
			TableLayout table = new TableLayout(context);
			TableLayout.LayoutParams llm2 = new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			if(StaticStore.width >= 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				act_loginll.setBackgroundResource(R.drawable.login_bottom);
			}else if(StaticStore.height >= 1000){
				act_loginll.setBackgroundResource(R.drawable.login_bottom1);
			}else if(StaticStore.height >= 720 && StaticStore.height < 1000){
				act_loginll.setBackgroundResource(R.drawable.login_bottom);
			}else{
				act_loginll.setBackgroundResource(R.drawable.login_bottom);
			}
			if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				llm2.setMargins(75, 0, 75, 20);
			}else if(StaticStore.height >= 1000){
				llm2.setMargins(75, 0, 75, 20);
			}else if(StaticStore.height >= 720 && StaticStore.height < 1000){
				llm2.setMargins(50, 0, 50, 20);
			}else{
				llm2.setMargins(40, 0, 40, 20);
			}
			table.setLayoutParams(llm2);
			
			 /***For showing Password Textview **/
		       TableRow pass_datas1 = new TableRow(context);
		       TextView showpassword = new TextView(context); 
		       showpassword.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,0.6f));
		       showpassword.setMaxLines(1);
		       showpassword.setPadding(20,2, 20, 1);
		       showpassword.setText("Password");
		       showpassword.setTextColor(Color.rgb(18, 56, 127));
		       showpassword.setGravity(Gravity.CENTER);
		       pass_datas1.addView(showpassword);
			   table.addView( pass_datas1);	

			table.setStretchAllColumns(true);
			table.setShrinkAllColumns(true);
			
			TableRow datas = new TableRow(context);
			datas.setGravity(Gravity.CENTER_VERTICAL);
			
		edpword = new EditText(context); 
		edpword.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,0.6f));
		edpword.setMaxLines(1);
		edpword.setPadding(20, 2, 20, 2);
		edpword.setGravity(Gravity.CENTER_VERTICAL);
		edpword.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
		edpword.setHorizontallyScrolling(true);
		edpword.clearFocus();
		edpword.setInputType(524288|InputType.TYPE_CLASS_PHONE); 

		InputFilter[] filterArray = new InputFilter[2];
		filterArray[0] = new InputFilter() {
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
				for (int i = start; i < end; i++) { 

					boolean inputnumeric=false;
					if ((Character.isDigit(source.charAt(i))))
					{ 
						inputnumeric=true;
					}     
					else
					{
						inputnumeric=false;
						return "";  
					}
				}
				return null;   
			}  
		};

		filterArray[1] = new InputFilter.LengthFilter(4);
		edpword.setFilters(filterArray); 
		edpword.setTransformationMethod(new PasswordTransformationMethod());
		datas.addView(edpword);
		table.addView(datas);	
		
		TableRow datas1 = new TableRow(context);
		Button imgbtn = new Button(context);
		imgbtn.setId(1222);
		imgbtn.setBackgroundResource(R.drawable.btn);
		imgbtn.setOnClickListener(tabBarListener);
		imgbtn.setGravity(Gravity.CENTER_VERTICAL);
		imgbtn.setHintTextColor(Color.rgb(18, 56, 127));
		imgbtn.setTextColor(color.white);
		imgbtn.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,0.20f));
		datas1.addView(imgbtn);
		
		table.addView(datas1);
		
		
		TableRow datas2 = new TableRow(context);
//		Button signup = new Button(context);
//		signup.setTextColor(Color.rgb(18, 56, 127));
//		signup.setId(1333);
//		signup.setOnClickListener(tabBarListener);
//		signup.setBackgroundColor(Color.TRANSPARENT);
//		signup.setText(Html.fromHtml("<U>"+"Sign Up" +"</U>"));
//		signup.setGravity(Gravity.LEFT);
//		signup.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//		datas2.addView(signup);
		
		Button forgotpwd = new Button(context);
		forgotpwd.setTextColor(Color.rgb(18, 56, 127));
		forgotpwd.setId(1444);
		forgotpwd.setOnClickListener(tabBarListener);
		forgotpwd.setBackgroundColor(Color.TRANSPARENT);
		forgotpwd.setText(Html.fromHtml("<U>"+"Forgot Password?" +"</U>"));
		TableRow.LayoutParams trlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
		trlp.span = 1;
		forgotpwd.setLayoutParams(trlp);
		forgotpwd.setGravity(Gravity.RIGHT);
		datas2.addView(forgotpwd);
		table.addView(datas2);
		act_loginll.addView(table);
		
		frag_main.addView(act_loginll1);
		frag_main.addView(act_loginll);
		frag_main.addView(Gridbtntable);
		}else{
			frag_main.addView(act_loginll2);
			frag_main.addView(Gridbtntable);
		}
	return rootView;
    }
    
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	 StaticStore.LogPrinter('e',"onPause() GridScreenViewActivation_Mobile");
    	 StaticStore.midlet.onPauseCalled();
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	StaticStore.LogPrinter('e',"onResume() GridScreenViewActivation_Mobile");
    	StaticStore.midlet.onResumeCalled();
    }

    private OnClickListener tabBarListener = new OnClickListener() { 
    	public void onClick(View v) {
    		StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
    		 if(getActivity().getCurrentFocus()!=null) {
		    		StaticStore.LogPrinter('e',"Keyboard hidden called ===> "+getActivity().getCurrentFocus());
		    		InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);             
		    		im.hideSoftInputFromWindow(Gridbtntable.getApplicationWindowToken(), 0);
		    	}
    		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
    		if(sessionflag){
    			StaticStore.isForgotPassword = false;
    			 StaticStore.LogPrinter('i',"v.getId()v.getId()v.getId() === > "+v.getId());
    			if(v.getId() <= 5 && v.getId()>=0){
    				int position = v.getId();
    				StaticStore.selectedLoginGridIndex = (StaticStore.IsPermitted?position+2:position);
    	    		StaticStore.midlet.MenuSelected(getActivity());
    			}else if(v.getId() == 1222){
    				StaticStore.selectedLoginGridIndex = 1;
    				if (edpword.getEditableText().toString().length() == 0) {
    					StaticStore.ToastDisplay(StaticStore.context,"Please enter Login Password");
    					edpword.requestFocus();
    				}else if (edpword.getEditableText().toString().length() < 4) {
    					StaticStore.ToastDisplay(StaticStore.context,"Login Password must be 4 digits");
    					edpword.requestFocus();
    				}else{ 
    					StaticStore.index = 123;
    					StaticStore.menuDesc[123][2] = edpword.getText().toString().trim();
    					StaticStore.menuDesc[123][3] = "";
    					StaticStore.midlet.initialize();
    					new DynamicCanvas().DynamicCanvastest(context);
    				}
    			}else if(v.getId() == 1333){
//    				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
    				StaticStore.midlet.startFragment(context,StaticStore.midlet.getSignUpTypes(context));
    			}else if(v.getId() == 1444){
    				//Forgot password screen
    				StaticStore.selectedLoginGridIndex = 6;
    				StaticStore.midlet.MenuSelected(getActivity());
    			}
    		}
    	}
    		};
    		
    public static final void setAppFont(ViewGroup mContainer, Typeface mFont,int mFontsize)
	{
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
				   if(getActivity().getCurrentFocus()!=null) {
			    		StaticStore.LogPrinter('e',"Keyboard hidden called ===> "+getActivity().getCurrentFocus());
			    		InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);             
			    		im.hideSoftInputFromWindow(Gridbtntable.getApplicationWindowToken(), 0);
			    	}
				   if (v.getId() == 0 ) {
					   StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
				   } else if (v.getId() == 1) { //version update
					   StaticStore.isFromLoginScreen = true;
					   StaticStore.isFromVersionUpdate = true; //siva 
					   StaticStore.selectedLoginGridIndex = 99; //don't delete this - siva 

					   if(!StaticStore.IsPermitted && !StaticStore.IsPersonalInfoGot) {
						   StaticStore.index = 154;
						   StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
					   }
					   else if(StaticStore.IsPermitted && StaticStore.IsGPRS && !StaticStore.isSuccessGC)
					   {
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
					   StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
					   StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
					   Intent intent = new Intent(StaticStore.context,HelpScreen.class);
					   intent.putExtra("title" , "");
					   intent.putExtra("index" , "9840" );
					   StaticStore.midlet.startFragment(StaticStore.context,intent);
				   } 
			   }
		   }
	   };
}
