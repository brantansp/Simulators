package com.fss.tmb;

 
import java.lang.reflect.Field;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import android.R.integer;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Spanned;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Path.Direction;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.Layout.Directions;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class DynamicCanvas extends android.support.v4.app.Fragment {
    //** Called when the activity is first created. *//*//error comment,comment for instrument//navigate comment
	private BroadcastReceiver sendBroadcastReceiver,deliveryBroadcastReceiver,batteryInfoReceiver;
	ProgressDialog pd;
	LinearLayout lltab;	
	String SENT = "SMS_SENT";        
	String DELIVERED = "SMS_DELIVERED";  
	AlertDialog.Builder alertbox;
	private boolean BatterysentReq = false;
	EditText[] editText;
	TextView[] textView1;
	TableRow.LayoutParams rowSpanLayout;
	public static TableLayout table;
	public static TableRow[] keypad;
	public static Button[] keypadbtn;
	boolean mpindelflag;
	int minStringLen,maxStringLength;
	int[] minStringLength;
	int[] maxStringLen;
	boolean isStarred; 
	String inputType;
	String[] emptyString;
	int[] emptyIndex;
	int[] validIndex;
	private static String mPINString  = StaticStore.mPINString;
	public static int count = 1;
	private String seperator = ";";
	public int leg;
	public  String encrpt;
	private Calendar cal;
	private boolean firstLegAutoStart = false;
	private int instrument;
	public int templeMinAmount, templeDenomination;
	public boolean templeDenFlag;
	public static boolean isResetPWD;	
	private mPAY midlet;	 
	private  String smsNumber = "";
	public Button okButton;
	public String currentDate;
	Context context;
	String[] label;	
	String[] validation;
	boolean DynamicCanvastest;
	public static int i;
	public static ScrollView s;
	public static RelativeLayout r1;
	public static LinearLayout ll,llhead,llmain,llnote;
	private Map<Integer,Integer> tabBarOver;
	int tempcount=0;
	int testi=0;
	private  int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private String selectedlist = "";
	private String selectedlist1 = "";
	public void DynamicCanvastest(Context context){
		DynamicCanvastest = true ;
		this.context = context;
		midlet = StaticStore.midlet;
		cal = Calendar.getInstance();
		setDynamicList(StaticStore.index);
		leg = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
		StaticStore.isNoInput = true;
		navigateTo(StaticStore.index);
	}
    	 @Override
    		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    		 this.context = StaticStore.context;
    	    	 DynamicCanvastest = false;
    	    	 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
				 StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);   	   
    	StaticStore.LogPrinter('i',"Dynamic Canvas..uuuuuuuuuuuu...." + StaticStore.index);  
		StaticStore.LogPrinter('i',"#########Dynamic Canvas..FromListScreen. --->" + StaticStore.FromListScreen);  
		
		StaticStore.isNoInput = false;
		try{

		r1= new RelativeLayout(StaticStore.context);
		LinearLayout.LayoutParams lp;
		s = new ScrollView(StaticStore.context);  
		midlet = StaticStore.midlet;
		lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
				LayoutParams.FILL_PARENT);
		lp.setMargins(10, 10, 10, 10);
		
		 ll = new LinearLayout(StaticStore.context);
		 llhead = new LinearLayout(StaticStore.context);
		 llmain = new LinearLayout(StaticStore.context);
		 llnote = new LinearLayout(StaticStore.context);
		ll.setOrientation(LinearLayout.VERTICAL);
		llhead.setOrientation(LinearLayout.VERTICAL);
		llmain.setOrientation(LinearLayout.VERTICAL);
		llnote.setOrientation(LinearLayout.VERTICAL);
		if(StaticStore.is_SmallMobile){
			llnote.setPadding(10,10, 10, 0);
			ll.setPadding(5, 5, 5, 10);
		}else{
			llnote.setPadding(10,20, 10, 0);
			ll.setPadding(20, 10, 20, 10);
		}
		s.setBackgroundResource(R.drawable.bg_innerpage);
		cal = Calendar.getInstance();
		
		//Note: Message Display part
		if(StaticStore.index == 9 || StaticStore.index == 27)
		{
			String Message = "Note: Please ensure that you have configured your E-mail ID in MySetup";
			TextView textView = new TextView(StaticStore.context);
			textView.setText(Message);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
			llnote.addView(textView);
		}
		//Note: Message Display part
		if(StaticStore.index == 114)
		{
			String Message = "<b>Note:</b> Please create your own Login Password; you must remember this password for further logins.";
			TextView textView = new TextView(StaticStore.context);
			textView.setText(Html.fromHtml("<medium>"+ Message+"</medium>"));
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
			textView.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			llnote.addView(textView);
		}
		//Note: Message Display part
		if(StaticStore.index == 220 && StaticStore.tagType.equals("APOV"))
		{
			String Message = "<b>Note:</b> An OTP SMS, generated against the above Ref. ID, has been sent to your mobile Inbox. Please check.";
			TextView textView = new TextView(StaticStore.context);
			textView.setText(Html.fromHtml("<medium>"+ Message+"</medium>"));
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
			textView.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			llnote.addView(textView);
		}
		
		if(StaticStore.NoteForgotPWD)
		{
			String Message = "<b>Note:</b> If you do not remember your mPIN, please visit your nearest bank branch.";
			TextView notetext = new TextView(StaticStore.context);
			notetext.setText(Html.fromHtml("<medium>"+ Message+"</medium>"));
			notetext.setGravity(Gravity.CENTER);
			notetext.setTextColor(Color.rgb(18, 56, 127));
			notetext.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			llnote.addView(notetext);
		}
		
		if(StaticStore.index == 88)
		{
			String Message = "<b>Note:</b> NEFT can be initated only on Branch Working days and Branch timings from 8.30 AM to 6.30 PM.";
			TextView textView = new TextView(StaticStore.context);
			textView.setText(Html.fromHtml("<medium>"+ Message+"</medium>"));
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
			textView.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			llnote.addView(textView);
		}
		
		if(StaticStore.forTNEBNote && ((StaticStore.index == 220 || StaticStore.index == 221 || StaticStore.index == 222 || StaticStore.index == 58) && (StaticStore.tagType.equals("AP2B") || StaticStore.tagType.equals("APDY"))))
		{
			String Message = "<b>Note:</b> Consumer No should be entered without Region Code.";
			TextView textView = new TextView(StaticStore.context);
			textView.setText(Html.fromHtml("<medium>"+ Message+"</medium>"));
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
			textView.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			llnote.addView(textView);
		}
		
		final Typeface mFontBold = Typeface.createFromAsset(StaticStore.context.getAssets(),StaticStore.mFont_Bold_Typeface);
		
		TextView textView = new TextView(StaticStore.context);
		if(StaticStore.isAirline){
			textView.setText(StaticStore.airlineTitle);	
			 minStringLength = new int[3];
		}else{
			StaticStore.LogPrinter('i',">>>>>"+StaticStore.menuDesc[StaticStore.index].length);
			for(int i = 0; i < StaticStore.menuDesc[StaticStore.index].length;i++){
				StaticStore.LogPrinter('i',">>>>>"+StaticStore.menuDesc[StaticStore.index][i]);
			}
				leg = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
		textView.setText(StaticStore.menuDesc[StaticStore.index][0]);
		}
		textView.setGravity(Gravity.LEFT);
		if(StaticStore.istablet){
		textView.setPadding(10, 10, 10, 10);
		}else{
			textView.setPadding(5, 5, 5, 5);
		}
		textView.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
		textView.setTextColor(Color.rgb(18, 56, 127));
		textView.setTypeface(mFontBold);
		
		if(StaticStore.isAirline){
			if(StaticStore.indexAir == 1000){
		        this.label = StaticStore.airlabelForBuk;
		         this.validation = StaticStore.airvalidationForBuk;
			}else{
				this.label = StaticStore.airlabelForPas;
				this.validation = StaticStore.airvalidationForPas;
			}
		}else{
			if(midlet.getFilledAccArray().length == 0 && StaticStore.index != 188 && StaticStore.index != 123 && StaticStore.IsPersonalInfoGot && StaticStore.IsPermitted && !StaticStore.isFromLoginScreen)
			{
				StaticStore.isFinancialAccSync = false;
				StaticStore.specialDynamicIndexForAccountSyn = StaticStore.index;
				StaticStore.specialDynamicTAGTYPEForAccountSyn = StaticStore.tagType;
				StaticStore.tagType = "SY";
				StaticStore.isSYFlag = true;
				StaticStore.FromListScreen = true;
				StaticStore.index = 188;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
			}else{
				StaticStore.isSYFlag = false;
				StaticStore.LogPrinter('i',"After initialize StaticStore.FromListScreen -->"+StaticStore.FromListScreen);
				setDynamicList(StaticStore.index);
			}
		}
		if(!StaticStore.isSYFlag){
			editText = new EditText[label.length];
			textView1 = new TextView[label.length];
			maxStringLen = new int[label.length];
				for (i = 0; i < label.length; i++) {
					splitValidation(validation[i]);
					minStringLength[i] = minStringLen;

					maxStringLen[i]		=	maxStringLength;

					
					textView1[i] = new TextView(StaticStore.context);
					if(label[i].contains("-")){
						label[i] = label[i].replace('-', ' ');
				    }
					textView1[i].setText(label[i]);
					if(StaticStore.is_SmallMobile){
						textView1[i].setPadding(0,0, 0,4);
					}else{
						textView1[i].setPadding(0,0, 0, ((StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize) / 2));	
					}
					textView1[i].setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
					textView1[i].setTypeface(Typeface.createFromAsset(StaticStore.context.getAssets(),StaticStore.mFont_Typeface));
					textView1[i].setTextColor(Color.rgb(18, 56, 127));//BLACK
					editText[i] = new EditText(StaticStore.context);
					editText[i].setMaxLines(1);
					editText[i].setBackgroundResource(R.drawable.edittext_focus);
					editText[i].setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
					editText[i].setTypeface(Typeface.createFromAsset(StaticStore.context.getAssets(),StaticStore.mFont_Typeface));
					if(i == label.length-1)
					{
						editText[i].setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_ACTION_GO);
						editText[i].setOnEditorActionListener(new TextView.OnEditorActionListener() {

							public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
								if (actionId == EditorInfo.IME_ACTION_DONE | actionId == EditorInfo.IME_ACTION_GO) {
									StaticStore.LogPrinter('i',"setLegValues StaticStore.mpincount 318");
									try {
										setLegValues();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return true;
								}
								return false;
							}
						});
					}
					if(StaticStore.istablet){
						editText[i].setHint(label[i]);//Html.fromHtml("<medium>"+ label[i]+"</medium>")
					}else{
						editText[i].setHint(Html.fromHtml("<small>"+ label[i]+"</small>"));//
					}
					editText[i].setHorizontallyScrolling(true);
					if(StaticStore.isAirline){
						if(StaticStore.indexAir == 1000){
							if(i == 0){
								editText[i].setText(StaticStore.airValues[2]);
							}else if(i == 1){
								editText[i].setText(StaticStore.airValues[3]);	
							}
						}else{
							if(i == 0){
								editText[i].setText(StaticStore.passValues[StaticStore.indexAir + 3]);
							}else if(i == 1){
								editText[i].setText(StaticStore.passValues[StaticStore.indexAir + 4]);
							}else if(i == 2){
								editText[i].setText(StaticStore.passValues[StaticStore.indexAir + 1]);
							}
						}
					}else if(StaticStore.index == 118 || StaticStore.index == 151){
						StaticStore.LogPrinter('i',"<<<<<<<<<<In Dynamic>>>>>>>>>>"+StaticStore.selectedIndex);
						if(StaticStore.selectedIndex == 0){
							editText[i].setText(StaticStore.shortCodeNumber);
						}else{
							editText[i].setText(StaticStore.VMN_Number);
						}
					}else if(StaticStore.index == 128){
						editText[i].setText(StaticStore.midlet.emailId);
					}else if(StaticStore.index == 180){
						editText[i].setText(StaticStore.numberOfTimeout+""); 

					}else if(StaticStore.index == 119){
						editText[i].setText(StaticStore.GprsUrl);

					}else if((StaticStore.index == 82) && (i == 1) && (StaticStore.isSearchAndRegister)){
						editText[i].setText(midlet.ifscArray[StaticStore.neftbenereg][0]);

					}else if(StaticStore.index == 209){
						editText[i].setText(StaticStore.fdAmount);		           
					}else if(StaticStore.index == 218){
						editText[i].setText(StaticStore.fdMPin);	
					}else if(StaticStore.index == 219){
						editText[i].setText(StaticStore.fdNoOfDays);
					}else if((StaticStore.index == 217) && (i == 0)){
						editText[i].setText(StaticStore.cpMPin);
					}else if((StaticStore.index == 217) && (i == 1)){
						editText[i].setText(StaticStore.cpInstruments);
					}
					else{
						
					}
					if (inputType.equals("AN")) {
						editText[i].setInputType(524288); 

						final String allowedSymbols=". @*#_-";
						InputFilter[] filterArray = new InputFilter[2];
						filterArray[0] = new InputFilter() {
							public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
								for (int i = start; i < end; i++) { 
									boolean inputAllowedSymbols=false;
									boolean inputAlphanumeric=false;

									if((dstart == 0) && (source.equals(" ")))
									{
										return "";
									}
									for(int j=0;j<allowedSymbols.length();j++)
									{

										if(allowedSymbols.charAt(j)==(source.charAt(i)))
										{
											inputAllowedSymbols=true;
										}
									}
									if ((Character.isLetterOrDigit(source.charAt(i)))) { 

										inputAlphanumeric=true;

									}     
									if(inputAllowedSymbols == true || inputAlphanumeric== true)
									{

									}
									else
									{
										inputAllowedSymbols=false;
										inputAlphanumeric=false;
										return "";  
									}
								}
								return null;   
							}  
						};
						filterArray[1] = new InputFilter.LengthFilter(maxStringLength);
						editText[i].setFilters(filterArray); 


					} else if (inputType.equals("ANW")) { 
						editText[i].setInputType(524288); 


						final String allowedSymbols=" ";

						InputFilter[] filterArray = new InputFilter[2];
						filterArray[0] = new InputFilter() {
							public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
								/*StaticStore.LogPrinter('i',"****1***inputfilter source*********"+source);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+start);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+start);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+end);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+dstart);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+dend);
							StaticStore.LogPrinter('i',"*****1**inputfilter source*********"+((dstart == 0) && (source.equals(" "))));
								 */							for (int i = start; i < end; i++) { 
									 boolean inputAllowedSymbols=false;
									 boolean inputAlphanumeric=false;

									 for(int j=0;j<allowedSymbols.length();j++)
									 {
										 if((dstart == 0) && (source.equals(" ")))
										 {
											 return "";
										 }
										 if(allowedSymbols.charAt(j)==(source.charAt(i)))
										 {
											 inputAllowedSymbols=true;
										 }
									 }
									 if ((Character.isLetterOrDigit(source.charAt(i)))) { 

										 inputAlphanumeric=true;

									 }     
									 if(inputAllowedSymbols == true || inputAlphanumeric== true)
									 {

									 }
									 else
									 {
										 inputAllowedSymbols=false;
										 inputAlphanumeric=false;
										 return "";  
									 }
								 }
								 return null;   
							}  
						};
						filterArray[1] = new InputFilter.LengthFilter(maxStringLength);
						editText[i].setFilters(filterArray); 


					} else if (inputType.equals("N") || inputType.equals("D")) {
						editText[i].setInputType(524288|InputType.TYPE_CLASS_PHONE); 
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

						filterArray[1] = new InputFilter.LengthFilter(maxStringLength);
						editText[i].setFilters(filterArray); 

					} else if (inputType.equals("ND")) {
						editText[i].setInputType(524288|InputType.TYPE_CLASS_PHONE);

						InputFilter[] filterArray = new InputFilter[2];
						filterArray[0] = new InputFilter() {
							public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
								for (int i = start; i < end; i++) { 

									boolean inputnumeric=false;


									if ((Character.isDigit(source.charAt(i))) || (source.charAt(i)=='.'))
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
						filterArray[1] = new InputFilter.LengthFilter(maxStringLength);
						editText[i].setFilters(filterArray); 
					} else if (inputType.equals("DMY")) {
						final String allowedSymbols="-";
						editText[i].setInputType(524288); 
						InputFilter[] filterArray = new InputFilter[2];
						filterArray[0] = new InputFilter() {
							public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
								for (int i = start; i < end; i++) { 

									boolean inputnumeric=false;
									boolean inputAllowedSymbols=false;
									if ((Character.isDigit(source.charAt(i))))
									{ 
										inputnumeric=true;
									}     
									
									for(int j=0;j<allowedSymbols.length();j++)
									{

										if(allowedSymbols.charAt(j)==(source.charAt(i)))
										{
											inputAllowedSymbols=true;
										}
									}
								}
								return null;   
							}  
						};

						filterArray[1] = new InputFilter.LengthFilter(maxStringLength);
						editText[i].setFilters(filterArray); 

					}
					editText[i].setId(i);
					
//					InputMethodManager imm = (InputMethodManager) StaticStore.context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//				    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
					
					
//					editText[i].setOnKeyListener(edKeyListener);
//					editText[i].addTextChangedListener(textwatcher);
					/*editText[i].addTextChangedListener(new TextWatcher() {

					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						StaticStore.LogPrinter('i',"Siva >>>"+editText[0].getFilters());
						if(maxStringLen[0] == s.length()){
							editText[i].requestFocus();
						}

					}

					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});*/

					if (isStarred) {
						editText[i]
						         .setTransformationMethod(PasswordTransformationMethod
						        		 .getInstance());
//						editText[i].setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI); 
					}
					//}
					
//					
					
//					getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
					
					ll.addView(textView1[i]);
					
					int date1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
					String date  = Integer.toString(date1);
					if(date.length() == 1){
						date = "0"+ date;
					}
					
					int month1 = Calendar.getInstance().get(Calendar.MONTH) + 1;
					String month  = Integer.toString(month1);
					if(month.length() == 1){
						month = "0"+ month;
					}
					
					currentDate = date + "-" + month + "-" + Calendar.getInstance().get(Calendar.YEAR);
						
					/* Created by ABINAYA.J.A for showing Custom ContactPicker LinearLayout */
					if(textView1[i].getText().toString().equalsIgnoreCase("Enter Mobile No.")){
                         if(StaticStore.rechargeSelcetedCategoryID.equals("MTP")){
							ll.setOrientation(LinearLayout.VERTICAL);
							
							LinearLayout d_ll=new LinearLayout(StaticStore.context);
							d_ll.setOrientation(LinearLayout.HORIZONTAL);
	  					    d_ll.setPadding(0, 13, 13, 0);	
							editText[i].setVisibility(View.VISIBLE);
			//				editText[i].setWidth(185);
							editText[i].setWidth(215);
							
						    Button	btn = new Button(StaticStore.context);
							btn.setWidth(40);
							btn.setHeight(40);
							btn.setBackgroundResource(R.drawable.li_contact);
							btn.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
			                    	Intent in = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			    					startActivityForResult(in, StaticStore.PICK_CONTACT);
			    				}
			                });
						    d_ll.addView(editText[i]);
							d_ll.addView(btn);
							ll.addView(d_ll);
						}else{
							ll.addView(editText[i]);
						}
					}else if(textView1[i].getText().toString().equalsIgnoreCase("Account Opening Date")){
					
						
					editText[0].setText(currentDate.toString());
					editText[0].setEnabled(false);
					ll.addView(editText[i]);
					}else{
						ll.addView(editText[i]);	
					}
					 
					
					/*if (inputType.equals("D")) {
			ll.addView(l1);	
			}else{*/
					// ll.addView(editText[i]);
					//	}
				}
//		
				//<------------ RESEND OTP LINK START--------------->
				if(StaticStore.tagType.equals("APOV")  && StaticStore.index == 220)
				{
					TextView tvResendOTP = new TextView(StaticStore.context);
					String btnName = "Click here to regenerate OTP";
					SpannableString content = new SpannableString(btnName);
					content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
					
					tvResendOTP.setText(content);
					tvResendOTP.setPadding(10, 10, 10, 10);
					tvResendOTP.setTextColor(Color.rgb(18, 56, 127));
					tvResendOTP.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
							StaticStore.menuDesc[220] = new String []{"OTP Generation","APOG",StaticStore.myMobileNo,"","1","false","false","Y"};
		   					StaticStore.tagType = "APOG";
		   					StaticStore.index = 220;
		   					StaticStore.regenerateClicked = true;
	    					StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));	
							
						}
					});
					
					ll.addView(tvResendOTP);
				}		
				
				
//<------------ RESEND OTP LINK END  --------------->
			//		<---------- NOTIFICATION START --------------->

			if(StaticStore.isPreLoginNotification  && StaticStore.index == 123)
			{
				LinearLayout lb = new LinearLayout(StaticStore.context);
				LinearLayout.LayoutParams lnotelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				lnotelp.setMargins(10, 10, 10, 10); // llp.setMargins(left, top, right, bottom);

				lb.setLayoutParams(lnotelp);
				lb.setOrientation(LinearLayout.HORIZONTAL);
				LinearLayout imglb1=new LinearLayout(StaticStore.context);
				imglb1.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
				ImageView imageView1 = new ImageView(StaticStore.context);
				imageView1.setImageResource(R.drawable.pre_notification);

				TextView textViews2 = new TextView(StaticStore.context);
				String text = "";
				for(String s:StaticStore.midlet.preLoginShortDescList)
				{
					if(text!="")
						text = text+"\n"+s;
					else
						text = s;	
				}
				textViews2.setText(text);
				textViews2.setPadding(10, 10, 10, 10);
				textViews2.setTextColor(Color.rgb(18, 56, 127));
				imglb1.addView(imageView1);
				lb.addView(imglb1);
				lb.addView(textViews2);

				llnote.addView(lb);
				textViews2.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						StaticStore.FromListScreen = false;
						Intent intent = new Intent(StaticStore.context , DisplayableView.class);
						intent.putExtra("formName", "PreNotification");
						intent.putExtra("title","Notification");
						StaticStore.midlet.startFragment(StaticStore.context,intent);
					}
				});
				imageView1.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						StaticStore.FromListScreen = false;
						Intent intent = new Intent(StaticStore.context , DisplayableView.class);
						intent.putExtra("formName", "PreNotification");
						intent.putExtra("title","Notification");
						StaticStore.midlet.startFragment(StaticStore.context,intent);
					}
				});
			}
			
			
			//		<---------- NOTIFICATION END --------------->

			llmain.addView(llhead);
			llmain.addView(textView);
			//		llmain.addView(llnote);
			llmain.addView(ll,lp);
			if(StaticStore.isPreLoginNotification || StaticStore.index == 114 || StaticStore.NoteForgotPWD || (StaticStore.index == 220 && StaticStore.tagType.equals("APOV"))
				|| StaticStore.index == 88 || (StaticStore.forTNEBNote && ((StaticStore.index == 220 || StaticStore.index == 221 || StaticStore.index == 222 || StaticStore.index == 58) && (StaticStore.tagType.equals("AP2B") || StaticStore.tagType.equals("APDY"))))){
				StaticStore.NoteForgotPWD = false;
				StaticStore.forTNEBNote = false;
				llmain.addView(llnote);
			}
			s.addView(llmain, lp);



			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,  
					LayoutParams.WRAP_CONTENT);  
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);  
			params.addRule(RelativeLayout.ABOVE,333);  

			//   s.setId(101);
			s.setLayoutParams(params);  
			s.setId(222);



			/*Tabbar calling part*/		 

			try {

				lltab = StaticStore.Tabbar(tabBarListener,setDynamicTabBar(),StaticStore.context,false);
		         r1.addView(lltab);
		         
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r1.addView(s);
			r1.setLayoutParams( new
					ViewGroup.LayoutParams( LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT) ); 



			if (isInput()) {
				try{
					 StaticStore.LogPrinter('i',"Dynamic canvas setContentView");
//					LinearLayout Frag_Det = (LinearLayout) findViewById(R.id.frag_Det);
//					Frag_Det.addView(r1);
//					editText[0].requestFocus();
					
//					if(!StaticStore.Leftsidepane){
//						StaticStore.Leftsidepane = false;
//						r1.setFocusableInTouchMode(true);
//						r1.requestFocus();
//						r1.setOnKeyListener(new View.OnKeyListener() {
//							public boolean onKey(View v, int keyCode, KeyEvent event) {
//								Log.i("keyCode", "keyCode: " + keyCode);
//								//					            if( keyCode == KeyEvent.KEYCODE_BACK ) {
//								//					                    Log.i("keyCode", "onKey Back listener is working!!!");
//								////					                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//								////					                return true;
//								//					                    
//								//					                    
//								//					                    
//								//					                    
//								//					            } else {
//								//					                return false;
//								//					            }
//								  if (event.getAction() !=KeyEvent.ACTION_DOWN)
//					                    return true;
//								  
//								  if( keyCode == KeyEvent.KEYCODE_BACK ) { 
//									  return onKeyDown(keyCode,event);
//								  } else {
//									  return false;
//								  }
//							}
//						});
//					}
					return r1;
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				StaticStore.isNoInput = true;
				navigateTo(StaticStore.index);
			}

			//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
			//<------ Font Style Part Start ---->

			final Typeface mFont = Typeface.createFromAsset(StaticStore.context.getAssets(),StaticStore.mFont_Typeface); 
			final ViewGroup mContainer = (ViewGroup)ll ; //contentgetRootView()
			DynamicCanvas.setAppFont(mContainer, mFont,(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));

			//<------ Font Style Part End ---->
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// // finish();
			Intent intent = new RespectiveScreen().ResponceError(StaticStore.context); //Siva G
			StaticStore.midlet.startFragment(StaticStore.context,intent);
			//new RespectiveScreen().ResponceError(StaticStore.context);
			
		}
		
		 return null;
    }
    	 
    	 @Override
    	    public void onPause() {
    	    	// TODO Auto-generated method stub
    	    	super.onPause();
    	    	 StaticStore.LogPrinter('e',"onPause() DynamicCanvas");
    	    	 StaticStore.midlet.onPauseCalled();
    	    }
    	    
    
    	 
    	    @Override
    	    public void onResume() {
    	    	// TODO Auto-generated method stub
    	    	super.onResume();
    	    	StaticStore.LogPrinter('e',"onResume() DynamicCanvas");
    	    	StaticStore.midlet.onResumeCalled();
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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig); 
            StaticStore.width = StaticStore.height;
    }
    
		private void exit() {
			exitMIDlet(new AlertDialog.Builder(StaticStore.context),
					"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,StaticStore.context).show();
		
		}
		private Map<Integer, Image> setDynamicTabBar() {

			 StaticStore.LogPrinter('i',"Enter into setDynamicTabBar==");
			Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
			if (StaticStore.index == 123) {
//				tabBarSetter.put(114,  new Image(R.drawable.icon_forget, "Forgot Password"));
//				tabBarSetter.put(2, new Image(R.drawable.icon_reset, "Reset Password"));
//				tabBarSetter.put(3, new Image(R.drawable.icon_reactive, "Reactivate"));

			}else if(StaticStore.index != 216){ //StaticStore.index != 113 && 
//				if( StaticStore.IsPersonalInfoGot && StaticStore.IsPermitted){//StaticStore.isCommModeSelected  &&
					if(!StaticStore.istablet  && StaticStore.IsPermitted) //StaticStore.enableHome == true
					{
						tabBarSetter.put(110,  new Image(R.drawable.icon_home, "Home"));
					}else{
						if(!StaticStore.enableHome && StaticStore.IsPermitted){
							tabBarSetter.put(110,  new Image(R.drawable.icon_home, "Home"));
							}
						}
				}
				
//			}
//			if(StaticStore.index != 58) //mpin screen submit no need 
//			{
			tabBarSetter.put(111, new Image(R.drawable.icon_submit, "Ok"));
//			}
			//tabBarSetter.put(5,  new Image(R.drawable.icon_about, "About"));
			try
			{
				int id = getResources().getIdentifier("help_"+StaticStore.index,"string",StaticStore.context.getPackageName());
				if(id!= 0)
				{
					tabBarSetter.put(116, new Image(R.drawable.icon_help, "Help"));
					StaticStore.LogPrinter('i',"Help menu Add --> "+id);
				}
			}catch (Exception e) {
				// TODO: handle exception
				StaticStore.LogPrinter('i',"Sorry help error --> ");
			}
			tabBarSetter.put(117,  new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")));

			// TODO Auto-generated method stub
			return tabBarSetter;
		}
		
		
//		private OnKeyListener edKeyListener = new OnKeyListener() {
//			
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				for(int i=0;i<=label.length;i++)
//				{
//					if(v.getId() == i)
//					{
//						tempcount = i;
//											}
//				}
//				return false;
//			}
//		};
//		private TextWatcher textwatcher = new TextWatcher() {
//			
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
////		//GS 		if(editText[tempcount].getText().toString().length()==0)
////				{
////				textView1[tempcount].setVisibility(View.INVISIBLE);
////				}else
////				{
////				textView1[tempcount].setVisibility(View.VISIBLE);
////			//GS 	}
//				
//				int temp1,temp2;
//				temp1 = s.length();
//				if(label.length >1 && tempcount < label.length-1)
//				{
//				if(maxStringLen[tempcount] == s.length()){
////					StaticStore.LogPrinter('i',"Came inside");
////					maxStringLen[tempcount] < editText[tempcount].length()
//					//tempcount = tempcount +1;
//					editText[tempcount+1].requestFocus();
//				}
//				}
//			}
//		};
//		
//		private TextWatcher mpintextwatcher = new TextWatcher() {
//
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				//   StaticStore.LogPrinter('i',"onTextChanged called"+tempcount);
//
//			}
//
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				//   StaticStore.LogPrinter('i',"beforeTextChanged called"+tempcount);
//
//
//			}
//
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				if(StaticStore.mpincount == 3 && s.length() == 1)
//				{
//					StaticStore.LogPrinter('i',"setLegValues StaticStore.mpincount 1073");
//					setLegValues();
//				}
//				/*//   StaticStore.LogPrinter('i',"afterTextChanged called"+tempcount);
//				 mpindelflag = true;
//					if(s.length() == 1 && tempcount < 3 ){
////						//   StaticStore.LogPrinter('i',"Came inside");
////						maxStringLen[tempcount] < editText[tempcount].length()
//						tempcount = tempcount+1;
//						editText[tempcount].requestFocus();
//					}else if(s.length() == 1 && tempcount == 3 )
//					{
//						editText[tempcount].requestFocus();
//						setLegValues();
//					}else
//					{
//
//						if(tempcount >0)
//						{
//						tempcount--;
//						}
//					}*/
//
//			}
//		};


//		private OnKeyListener mpinedKeyListener = new OnKeyListener() {
//
//
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				//   StaticStore.LogPrinter('i',"v.getId()-->"+v.getId());
//				//   StaticStore.LogPrinter('i',"tempcount-->"+tempcount);
//				// TODO Auto-generated method stub
//				/*if(keyCode == KeyEvent.KEYCODE_DEL && mpindelflag){  
//		                 //this is for backspace
//						 for(int i=0;i<=label.length;i++)
//						{
//							 if(v.getId() == i)
//							 {
//						 mpindelflag = false;
//		            	 if(tempcount>=0)
//		            	 {
//		            		 tempcount = v.getId();
//			            	 if(tempcount != 0)
//			            	 {
//			            	 tempcount--;
//			            	 }
//			             //   StaticStore.LogPrinter('i',"after tempcount -->"+tempcount);
//			             editText[tempcount].setText("");
//		            	 editText[tempcount].requestFocus();
//
//		            	 }
//		                 }
//						}
//
//					 }*/
//				/*for(int i=0;i<4;i++)
//					{
//						 if(v.getId() == i)
//						 {
//
//						 }
//					}*/
//
//				return false;
//			}
//		};
	    //tabBarListener
	    private OnClickListener tabBarListener = new OnClickListener() { 
	    	public void onClick(View v) {
	    		// do something
	    		if(getActivity().getCurrentFocus()!=null) {
	        		StaticStore.LogPrinter('e',"Keyboard hidden called ===> "+getActivity().getCurrentFocus());
	        		InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);             
	        		im.hideSoftInputFromWindow(ll.getApplicationWindowToken(), 0);
	        	}
	    		
	    		if(v.getId() != 111){
	    			menuDescRestore();
	    		}
	    		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
	    		if(sessionflag){
	    			if (v.getId() == 110 ) {
	    				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getHome(StaticStore.context));
	    			} else if (v.getId() == 111) {
	    				StaticStore.LogPrinter('e',"setLegValues onClick 1150  "+v);
	    				try {
	    					setLegValues();
	    				} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    					StaticStore.LogPrinter('i',"Error in setLeg Values");
	    				}

	    			} /*else if ( v.getId() == 2) {

	   				StaticStore.IsGPRS = false;
	   				isResetPWD = true;
	   				StaticStore.index = 216;
	   				StaticStore.midlet.startFragment(StaticStore.context,new Intent(DynamicCanvas.this, DynamicCanvas.class));
	   			}else if (v.getId() == 3) {

//	   				StaticStore.IsGPRS = false;          
//	   	            isResetPWD         = true;
//	   	            StaticStore.menuDesc[113][0]   = "Reactivate TMB mConnect";
//	   	            StaticStore.index = 113;
//	   				StaticStore.midlet.startFragment(StaticStore.context,new Intent(DynamicCanvas.this, DynamicCanvas.class));
	   			}*/else if (v.getId() == 114) {
	   				//Forgot Password
	   				StaticStore.isForgotPassword = true;
	   				StaticStore.NoteForgotPWD = true;
	   				if(StaticStore.isOTPBuild && StaticStore.SMS_AUTHENTICATION_GPRSMODE)
	   				{
	   					StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
	   					StaticStore.FromListScreen = false;
	   					StaticStore.menuDesc[220] = new String []{"OTP Generation","APOG",StaticStore.myMobileNo,"","1","false","false","Y"};
	   					StaticStore.tagType = "APOG";
	   					StaticStore.index = 220;
    					StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));	
	   				}else{
	   					StaticStore.FromListScreen = true;
	   					StaticStore.index = 115;
	   				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
	   				}
	   			}else if (v.getId() == 115) {
	   				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
	   			}else if (v.getId() == 116) {
	   				StaticStore.FromListScreen = false;
	   				Intent intent = new Intent(StaticStore.context,HelpScreen.class);
	   				intent.putExtra("title",StaticStore.menuDesc[StaticStore.index][0] );
	   				intent.putExtra("index",""+StaticStore.index);
	   				StaticStore.midlet.startFragment(StaticStore.context,intent);

	   			}else if (v.getId() == 117) {

	   				exit();
	   			}
	    		}
	    	}
	};
	
	

	  /* Created by ABINAYA.J.A for showing Custom ContactPicker */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (StaticStore.PICK_CONTACT):

	        if (resultCode == Activity.RESULT_OK) {
	        	    String cNumber;
	        	    Uri contactData = data.getData();
	                Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
	                if (c.moveToFirst()) {
	                	String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
	                    String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (hasPhone.equalsIgnoreCase("1")) {
	                       Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
	                       phones.moveToFirst();
	                  	   StaticStore.contactPickerNo=phones.getString(phones.getColumnIndex("data1")); //NEW
	                     }
	                     StaticStore.list_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

	                    /*Created by ABINAYA.J.A for Mobile Number Validation like Countrycode,space and - */
	  					String val1 = StaticStore.contactPickerNo;
	  					if (val1 != null) {
	  						val1 = val1.replace("-", "");
	  						val1 = val1.replace(" ", "");
	  						val1 = val1.replace("(", "");
	  						val1 = val1.replace(")", "");
	  						if (val1.length() > 0) {
	  							val1 = getPhoneNoAlone(val1);
	  						}
                        }
	  					editText[0].setText(val1.toString());
	                    }
            }
            break;
            default:
			break;
		}
  }
	

	 /* Created by ABINAYA.J.A For Mobile Number Validation */
	public static String getPhoneNoAlone(String str) {

		String phone_valid = "";		
		try
		{
			phone_valid = str.substring(str.length() - 10, str.length());
		}catch(Exception e)
		{
			phone_valid = str;
		}		
		return phone_valid;
	}
    
    public void onClick(View v) {
		// if(v == o)
		// showDialog(DATE_DIALOG_ID);
		/*
		 * for (int i = 0; i < label.length; i++) { if(v == dateButton[i]){
		 * dateIndex = i; showDialog(DATE_DIALOG_ID); } }
		 */
		
    	StaticStore.LogPrinter('i',"Calling from OK Button");
		if (v == okButton) {

			try {
				setLegValues();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				StaticStore.LogPrinter('i',"Error in setLegValues 1216 ok bu");
				e.printStackTrace();
			}
		}
		
		
	}
    public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getGroupId() == 0) {
//			setLegValues();
		} else if (item.getGroupId() == 1) {
//			StaticStore.LogPrinter('i',">>>>>>>>>>>");
//			Intent intent = new Intent(StaticStore.context,
//					DisplayableView.class);
//			String message  = "Please visit your Branch / ATM.";
//			intent.putExtra("response",message);
//        	intent.putExtra("formName", "ForgotPWD");
//			StaticStore.midlet.startFragment(StaticStore.context,intent);
		} else if (item.getGroupId() == 2) {
//			StaticStore.IsGPRS = false;
//			isResetPWD = true;
//			StaticStore.index = 216;
//			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
		}else if (item.getGroupId() == 3) {
			exitMIDlet(new AlertDialog.Builder(StaticStore.context),
					"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,StaticStore.context).show();
		}else if (item.getGroupId() == 4) {
//			StaticStore.IsGPRS = false;          
//            isResetPWD         = true;
//            StaticStore.menuDesc[113][0]   = "Reactivate TMB mConnect";
//            StaticStore.index = 113;
//			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
		}
		/*
		 * else if (item.getItemId() == 1) { myIntent = new
		 * Intent(StaticStore.context, ListSelection.class);
		 * myIntent.putExtra("listIndex", 100); StaticStore.midlet.startFragment(StaticStore.context,myIntent); }
		 */
		super.onOptionsItemSelected(item);
		return true;
	}   
    
    public void splitValidation(String validString) {
    	//1-2-N-N-N
    	String temp = validString;
    	String minLimit = temp.substring(0,temp.indexOf('-')).trim();
    	temp = temp.substring(temp.indexOf('-') + 1);
    	String maxLimit = temp.substring(0,temp.indexOf('-')).trim();
    	temp = temp.substring(temp.indexOf('-') + 1);
    	inputType = temp.substring(0,temp.indexOf('-'));
    	temp = temp.substring(temp.indexOf('-') + 1);
    	encrpt = temp.substring(0,temp.indexOf('-'));
    	temp = temp.substring(temp.indexOf('-') + 1);
    	String stared = temp;
    	minStringLen = Integer.parseInt(minLimit);
    	maxStringLength = Integer.parseInt(maxLimit);
    	if(stared.equals("Y")){
    		isStarred = true;
    	}else if(stared.equals("N")){
    		isStarred = false;
    	}
    }  

    

    public  void setDynamicList(int index){
    	//{"Mobile Registration","APRM;Y",mPINString,"","Beneficiary mobile No.",
     //   "Beneficiary Nickname","0001","4-4-N-Y-Y","","10-10-N-N-N","1-20-AN-N-N","","5","true","true"}
    	int numLabels;
//    	 if(StaticStore.index == 17 || StaticStore.index == 24){// || StaticStore.index == 32 || StaticStore.index == 33 || (StaticStore.index == 220 && StaticStore.tagType.equals("R6"))
//    		numLabels = Integer.parseInt(StaticStore.menuDesc[index][StaticStore.menuDesc[index].length - 2]);
//    	}else{
    	    numLabels = Integer.parseInt(StaticStore.menuDesc[index][StaticStore.menuDesc[index].length - 4]);
//    	}
    	 //numLabels -= 1;
        int count = 0;                
        int emptyCount = 0;
        int starter = 0 ;
        if(index == 199){
        	starter = 3;
        }else{
        	starter = 2;
        }
        for(int i = starter;i < 2 + numLabels;i++){
            if(StaticStore.menuDesc[index][i + numLabels].trim().equals("")){
                emptyCount += 1;
            } else{                    	
                count += 1;
            }
        }
        label = new String[count];
        validation = new String[count];
        minStringLength = new int[count];
        emptyString = new String[emptyCount];
        emptyIndex = new int[emptyCount];
        validIndex = new int[count];
        int j = 0;
        int k = 0;
       	StaticStore.encyptionFlag = new boolean[Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4])];
        int encCounter	=	0;
      
        for(int i = starter;i < 2 + numLabels;i++){
        	
        	
        	String temp = StaticStore.menuDesc[index][i + numLabels];
        	
        	StaticStore.LogPrinter('i',"temp >>>>>" + StaticStore.encyptionFlag.length + " " + temp);
        	
        	if(!temp.equals("")) {
    	    	temp = temp.substring(temp.indexOf('-') + 1);
    	    	temp = temp.substring(temp.indexOf('-') + 1);
    	    	temp = temp.substring(temp.indexOf('-') + 1);
    	    	temp = temp.substring(0,temp.indexOf('-'));
    	    	
    	    	if (temp.equals("Y")) {
    				StaticStore.encyptionFlag[encCounter] = true;
    			} else if (temp.equals("N")) {
    				StaticStore.encyptionFlag[encCounter] = false;
    			}
        	} else {
        		StaticStore.encyptionFlag[encCounter] = false;
        	}

        	encCounter++;
        	
        	if(StaticStore.menuDesc[index][i + numLabels].trim().equals("")){
            	emptyIndex[k] = i - starter;
                emptyString[k] = StaticStore.menuDesc[index][i];
                k += 1;
            } else{
                label[j] = StaticStore.menuDesc[index][i];
                validation[j] = StaticStore.menuDesc[index][i + numLabels];
                validIndex[j] = i - starter;
                j += 1;
            }
        }
       }

	
    public void navigateTo(int tempIndex) {
    	
    	try {    		       		    		    		    	
    		if(StaticStore.isShortCode){
    	        smsNumber = StaticStore.shortCodeNumber;
    	    }else{
    	        smsNumber = StaticStore.VMN_Number;
    	    }

    		if(StaticStore.index == 221){//  || (StaticStore.index == 58 && StaticStore.forSeperateMPinAtFirst == true)
        		StaticStore.index = StaticStore.transIndex;
        		tempIndex = StaticStore.index;
                StaticStore.legValue[0] = StaticStore.globalMPIN;
                StaticStore.encyptionFlag[0] = true; 
        		StaticStore.menuDesc[StaticStore.index ][2 + Integer.parseInt(StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 4])] = "4-4-N-Y-Y";
        	
        	}
    		 StaticStore.LogPrinter('i',"StaticStore.isMoreClicked --> "+StaticStore.isMoreClicked);
//    		   if(StaticStore.isMoreClicked){
//    			   StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 1] = "Y";//(StaticStore.isMpinAtLast?"Y":"N")
//    		   }
    		
    	if(tempIndex != -6 ){ //&& tempIndex != -7
			try{
				StaticStore.LogPrinter('i',"assignValues(tempIndex) ==> "+tempIndex);
	    	assignValues(tempIndex);
	    	
			}catch(Exception e){
				e.printStackTrace();
			}
    	}
    	
    	StaticStore.LogPrinter('i',"menu array (tempIndex) ==> "+Arrays.deepToString(StaticStore.menuDesc[tempIndex]));
    	
      //  assignValues(tempIndex);    
        /*The following condition is used to avoid next mpin for same transaction.- 11-09-2009*/
        if(firstLegAutoStart && StaticStore.isGetMPIN || StaticStore.index == 155){
        	StaticStore.menuDesc[tempIndex][2+leg] = "";            
            StaticStore.isGetMPIN = false;
        }
//       / /StaticStore.LogPrinter('i'," StaticStore.globalMPIN"," StaticStore.globalMPIN --> "+ StaticStore.globalMPIN);
//        /StaticStore.LogPrinter('i'," StaticStore.legValue[0]"," StaticStore.legValue[0] --> "+ StaticStore.legValue[0]);
        if(StaticStore.menuDesc[StaticStore.index][2].equals(mPINString) && StaticStore.menuDesc[StaticStore.index][2 + Integer.parseInt(StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 4])].equals("4-4-N-Y-Y")){
           StaticStore.globalMPIN = StaticStore.legValue[0];
//           /StaticStore.LogPrinter('i',"inside globalMPIN"," StaticStore.globalMPIN --> "+ StaticStore.globalMPIN);
//           /StaticStore.LogPrinter('i',"inside legValue[0]"," StaticStore.legValue[0] --> "+ StaticStore.legValue[0]);
        }
      //  String sendSMSString = StaticStore.menuDesc[StaticStore.index][1]+seperator;
        String sendSMSString = "";
        if(!StaticStore.isMpinNeeded){
          sendSMSString = StaticStore.menuDesc[StaticStore.index][1]+seperator;
          if(!midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){
              sendSMSString = sendSMSString.substring(0,5)  + "N" + seperator;
              }
        }else if(StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.index != 58){// && StaticStore.index != 123
            if(StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 1].equals("Y")){
                 sendSMSString = StaticStore.menuDesc[StaticStore.index][1]+seperator;
                 sendSMSString = sendSMSString.substring(0,5)  + "N" + seperator;
            }else{
                sendSMSString = StaticStore.menuDesc[StaticStore.index][1]+seperator; 
            }
        }
        else{
        sendSMSString = StaticStore.menuDesc[StaticStore.index][1]+seperator;
        StaticStore.LogPrinter('i',"Came to else part ==> "+sendSMSString);
        }
        
        if(StaticStore.index == 122  && StaticStore.menuDesc[122][12].equals("P")){
            if(midlet.billpayNotifyDet[14][0].indexOf(".") == -1){
                midlet.billpayNotifyDet[14][0] = midlet.billpayNotifyDet[14][0];
            }else{
                midlet.billpayNotifyDet[14][0] = midlet.billpayNotifyDet[14][0].substring(0,midlet.billpayNotifyDet[14][0].indexOf("."));
            }
            if(midlet.billpayNotifyDet[15][0].indexOf(".") == -1){
                midlet.billpayNotifyDet[15][0] = midlet.billpayNotifyDet[15][0];
            }else{
                midlet.billpayNotifyDet[15][0] = midlet.billpayNotifyDet[15][0].substring(0,midlet.billpayNotifyDet[15][0].indexOf("."));
            }
        }

         if(StaticStore.index == 122 && StaticStore.menuDesc[122][12].equals("E")){
            if(midlet.billpayNotifyDet[14][0].indexOf(".") == -1){
                midlet.billpayNotifyDet[14][0] = midlet.billpayNotifyDet[14][0];
            }else{
                midlet.billpayNotifyDet[14][0] = midlet.billpayNotifyDet[14][0].substring(0,midlet.billpayNotifyDet[14][0].indexOf("."));
            }
        }
        int amount	=	0;
        
        if(StaticStore.index == 97){
            amount = Integer.parseInt(StaticStore.legValue[6]);
        }
        
        StaticStore.LogPrinter('i',">>>>>StaticStore.index>>>>>>>"+StaticStore.index);
        StaticStore.LogPrinter('i',">>>>>StaticStore.tagType>>>>"+StaticStore.tagType);
        StaticStore.LogPrinter('i',">>>StaticStore.initialFlag>>>>"+StaticStore.initialFlag);
        if(StaticStore.index == 180){
            int timeOutValue = Integer.parseInt(StaticStore.legValue[0]);
            if((timeOutValue >= 2 && timeOutValue <= StaticStore.maxTimeoutCount)  || timeOutValue== 0){
                StaticStore.numberOfTimeout = timeOutValue;
                midlet.writeinMemory(StaticStore.context);//comment for use
                StaticStore.ToastDisplay(StaticStore.context,"Your settings has been saved successfully");	
                
            }else{
	            	 StaticStore.ToastDisplay(StaticStore.context,"Only 0,2,3,4,5 are allowed in Timeout Count");
            }
        }else if(StaticStore.index == 220 && StaticStore.tagType.equals("APSU") && StaticStore.initialFlag == true){
        	StaticStore.LogPrinter('i',">>>>>>cccccccccccccccccccccccmmmmmmmmmmmmmmmmm");
        	StaticStore.initialFlag=false;
        	for(i=0;i<StaticStore.legValue.length;i++)
        	{
        		 StaticStore.LogPrinter('i',"***********"+i+"  "+StaticStore.legValue[i]);
        	}
//        	StaticStore.myMobileNo=StaticStore.legValue[3];
        	StaticStore.dynamicTempSignup = new String[StaticStore.legValue.length];
	//		StaticStore.tempIndex = StaticStore.index;
			for (int i = 0; i < StaticStore.dynamicTempSignup.length; i++) {
				StaticStore.dynamicTempSignup[i] = StaticStore.legValue[i];
			}
        	Intent myIntent = StaticStore.midlet.getmPAYRegistration(StaticStore.context);
			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
        }else if(StaticStore.index == 123){
        	if(StaticStore.retryCount < 3){
        		String userPWD = StaticStore.midlet.hashingByMD5(StaticStore.legValue[0].trim());
        		if(userPWD.equals(StaticStore.applicationPWD.trim())&& StaticStore.retryCount < 3){
        			StaticStore.retryCount = 0;
        			midlet.writeinMemory(StaticStore.context);
        			midlet.isPasswordEntered = true;

        			if(midlet.firstTimeLoading) {
        				midlet.firstTimeLoading = false;
        			} else {
        				if(StaticStore.IsGPRS){
        					StaticStore.menuDesc[220] = new String []{"Internet Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
        					StaticStore.tagType = "APCG";
        					StaticStore.index = 220;
        				StaticStore.midlet.startFragment(context,new Intent(context,DynamicCanvas.class));
        				}else{
        					StaticStore.midlet.startFragment(context,StaticStore.midlet.Loginvalidation());
        				}
        			}
        		}else{
        			String msg = "";
        			StaticStore.retryCount++;
        			midlet.writeinMemory(StaticStore.context);
        			if(StaticStore.retryCount < 3){
        				StaticStore.LogPrinter('i',">>>>"+StaticStore.retryCount);
        				msg = "Invalid login, please enter correct Login Password";
        			}else{
        				msg ="User has been locked, Contact bank for further assistance";
        			}
        			// CustomAlert custAlert   =   new CustomAlert("",msg, null, AlertType.INFO, new DynamicCanvas(midlet,display,123), midlet, 111);
        			StaticStore.ToastDisplay(StaticStore.context,msg);
        		}

        	}else{
        		StaticStore.ToastDisplay(StaticStore.context,"User has been locked, Contact bank for further assistance");
        	}

        }else if(StaticStore.index == 217){
    	   instrument = StaticStore.instrument;
           int yValue = instrument +3;
           
           if( instrument > 6 || instrument == 0){
        	   StaticStore.ToastDisplay(StaticStore.context,"Enter the limit Between 1 to 6");
           }else{        	   
                StaticStore.tempMPin = StaticStore.cpMPin;
                StaticStore.index = 199;
                StaticStore.LogPrinter('i',">>>>>>StaticStore.tempMPin"+StaticStore.tempMPin);
               // To Revert the old index values in navigate to method.
               StaticStore.dynamicMenuTemp = 	new String[StaticStore.menuDesc[StaticStore.index].length];
               StaticStore.dynamicMenuTemp	=	StaticStore.menuDesc[199];               
               StaticStore.tempIndex = StaticStore.index;
               StaticStore.isMoreClicked	=	true;
               
               StaticStore.menuDesc[199]	=	new String[15+(2*instrument)];
               StaticStore.legValue = new String[5+instrument];
               
               for(int i = 0;i < StaticStore.legValue.length;i++){
                   StaticStore.legValue[i] = "";
               }
               
               StaticStore.encyptionFlag =  new boolean[5+instrument];
               
               StaticStore.encyptionFlag[0]   =   true;
               
             StaticStore.legValue[0]="";//tempMPin
             /*  StaticStore.legValue[legCtr] = staredString;*/
               StaticStore.legValue[1]="";//instrument+
               StaticStore.menuDesc[199][0]=StaticStore.dynamicMenuTemp[0];
               
               StaticStore.menuDesc[199][1]=StaticStore.dynamicMenuTemp[1];
               StaticStore.menuDesc[199][2]=StaticStore.dynamicMenuTemp[2];
               /* menuDesc[199][3]=StaticStore.dynamicMenuTemp[3];*/
               
               StaticStore.menuDesc[199][3] = instrument + "";
               for(int i = 4,j = 1 ; i<=yValue;i++ , j++){
                   StaticStore.menuDesc[199][i]="Cheque Number "+j;
                   
               }
               StaticStore.menuDesc[199][yValue+1]="Date Of Pickup(DDMMYYYY)";
               StaticStore.menuDesc[199][yValue+2]="Preferred Hour(00 to 23)";
               StaticStore.menuDesc[199][yValue+3]="Preferred Minute(00,15,30,45)";
              /* StaticStore.menuDesc[199][yValue+4]="4-4-N-Y-Y";
               StaticStore.menuDesc[199][yValue+5]="1-1-N-N-N";*/
               StaticStore.menuDesc[199][yValue+4]="";
               StaticStore.menuDesc[199][yValue+5]="";
               
               for(int i = yValue+6; i<=yValue+5+instrument;i++){
            	   StaticStore.menuDesc[199][i]="6-6-N-N-N";
               }
               StaticStore.menuDesc[199][yValue+instrument+6]="8-8-N-N-N";
               StaticStore.menuDesc[199][yValue+instrument+7]="2-2-N-N-N";
               StaticStore.menuDesc[199][yValue+instrument+8]="2-2-N-N-N";
               StaticStore.menuDesc[199][yValue+instrument+9]=(5+instrument)+"";               
               /*if(midlet.widthCheck()) {
                   leg = 5+instrument;
                   menuDesc[199][yValue+instrument+10]="false";
                   menuDesc[199][yValue+instrument+11]="false";
               } else {
                   menuDesc[199][yValue+instrument+10]="true";
                   menuDesc[199][yValue+instrument+11]="true";
               } 
               
               
               if (!midlet.widthCheck()) {
                   legCtr++;
                   configLegVales(true);
                   midlet.prevDispDyn= midlet.getDisplay().getCurrent();
                   setDynamicList(199);
               } else {
                   legCtr++;
                   configLegVales(true);
                   
                   if(legCtr < leg){
                       
                       KeyInputs.currentText = StaticStore.legValue[legCtr];
                       staredString          = StaticStore.legValue[legCtr];
                       repaint();
                   } else{*/
                      /* navigateTo(StaticStore.index);
                   
               }         */
               StaticStore.fromChequePickup = true;    	    
    	   Intent intent = new Intent(StaticStore.context, DynamicCanvas.class);
        	StaticStore.midlet.startFragment(StaticStore.context,intent);
           }
       }        
       
       else if(StaticStore.index == 218){
    	   midlet.pubDynCan = this;
       	if(StaticStore.selectedAccType.equals("CE")
                   || StaticStore.selectedAccType.equals("CP")
                   || StaticStore.selectedAccType.equals("CX") 
                   || StaticStore.selectedAccType.equals("EC")
                   || StaticStore.selectedAccType.equals("CF")
                   || StaticStore.selectedAccType.equals("EB")){
       		Intent myIntent = StaticStore.midlet.get_DepositType(StaticStore.context);
			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
                } else {
                	StaticStore.menuDesc[209][3] = "A";
                       Intent myIntent = StaticStore.midlet.get_DepositProduct(StaticStore.context);
           			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
                }

       }
       else if(StaticStore.index == 219){
           if(Integer.parseInt(StaticStore.fdNoOfDays) > 366 || Integer.parseInt(StaticStore.fdNoOfDays) < 30){
        	   StaticStore.ToastDisplay(StaticStore.context,"Enter a day between 30 to 366");	
          }else{
        	  StaticStore.fromFdDaysToAmount = true;
       	    StaticStore.menuDesc[209][5] = StaticStore.fdNoOfDays + "";
       	    StaticStore.index = 209;
       	 StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
          }
       }
       else if(StaticStore.index == 209 && !StaticStore.fromFdDisplayable){
           if(Integer.parseInt(StaticStore.legValue[4]) > 50000 || Integer.parseInt(StaticStore.legValue[4]) < 10000){
        	   StaticStore.ToastDisplay(StaticStore.context,"Please Enter Amount between 10000 to 50000");	
           }else{
        	   midlet.pubDynCan = this;
        	   Intent myIntent = StaticStore.midlet.get_MaturityInstructions(StaticStore.context);
      			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
           }
       }
        
        else if((StaticStore.index == 82  || StaticStore.index == 84) && !StaticStore.fromCurrentSavingList ){
        	midlet.pubDynCan = this;
        	Intent myIntent = StaticStore.midlet.get_accTypeMenu(StaticStore.context,44,"NEFT");
        	if(myIntent == null){
        		StaticStore.fromCurrentSavingList = true;
    			StaticStore.legValue[2] = "-";
    			navigateTo(tempIndex);
    			
        	}else{
        		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
        	}
        }else if(StaticStore.index == 1 && !StaticStore.fromCurrentSavingList){
                	midlet.pubDynCan = this;
        	Intent myIntent = StaticStore.midlet
			.get_accTypeMenu(StaticStore.context,66,"M2A");
        	if(myIntent == null){
        		StaticStore.fromCurrentSavingList = true;
    			StaticStore.legValue[3] = "-";
    			navigateTo(tempIndex);
    			
        	}else{
        		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
        	}
        
        }else if(StaticStore.index == 159 && !StaticStore.fromCurrentSavingList){
        	midlet.pubDynCan = this;
        	Intent myIntent = StaticStore.midlet
			.getAccountTypeListM2A(StaticStore.context,82);
	StaticStore.midlet.startFragment(StaticStore.context,myIntent);
        }else if(StaticStore.index == 9 && !StaticStore.airline1st){
	        int totalPassengers = Integer.parseInt(StaticStore.legValue[6]);
	        if(totalPassengers < Integer.parseInt(StaticStore.legValue[7])){
	        	StaticStore.ToastDisplay(StaticStore.context,"Total number of adults must be less than or equal to "+totalPassengers);
	        }else if(totalPassengers < (Integer.parseInt(StaticStore.legValue[7]) + Integer.parseInt(StaticStore.legValue[8]))){
	        	StaticStore.ToastDisplay(StaticStore.context,"Total number of children must be less than or equal to "+(totalPassengers - Integer.parseInt(StaticStore.legValue[7])));
	        }else if(totalPassengers < (Integer.parseInt(StaticStore.legValue[7]) + Integer.parseInt(StaticStore.legValue[8]) + Integer.parseInt(StaticStore.legValue[9]))){
	        	StaticStore.ToastDisplay(StaticStore.context,"Total number of infants must be less than or equal to "+(totalPassengers - (Integer.parseInt(StaticStore.legValue[7]) + Integer.parseInt(StaticStore.legValue[8]))));
	        }else if(totalPassengers != (Integer.parseInt(StaticStore.legValue[7]) + Integer.parseInt(StaticStore.legValue[8]) + Integer.parseInt(StaticStore.legValue[9]))){
	        	StaticStore.ToastDisplay(StaticStore.context,"Total number of adults,children and infants are not matched to total number of passengers");
	        }else{//comment for use
	        	midlet.pubDynCan = this;
	        	Intent myIntent = midlet.getAirlineTimings(StaticStore.context);
		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
	        }
	    }else if((StaticStore.index == 51 || StaticStore.index == 52 ||((StaticStore.index == 220 || StaticStore.index == 58) && StaticStore.eLobbyLocationFlag)) && (StaticStore.legValue[1].trim().equals("") && StaticStore.legValue[2].trim().equals(""))){
    		 
	    	StaticStore.ToastDisplay(StaticStore.context,"Please enter either City or Location"); //mid:12914 
    	}
        //<<<<<<<<<<<<<<<<<!!!!!!!!!!!!!!!!!!!! frm whr it cme start !!!!!!!!!!!>>>>>>>>>>>>>>>>>>>>>>
    		
    	else if((StaticStore.index == 199 || StaticStore.index == 198 || StaticStore.index == 197) && Integer.parseInt(StaticStore.legValue[leg - 2]) > 23){
        	StaticStore.ToastDisplay(StaticStore.context,"Please enter valid hours (00-23)");	
    	}else if((StaticStore.index == 199 || StaticStore.index == 198 || StaticStore.index == 197) && Integer.parseInt(StaticStore.legValue[leg - 2]) < 0){
        	StaticStore.ToastDisplay(StaticStore.context,"Preferred Hour must be above 0");	 
    	}else if((StaticStore.index == 199 || StaticStore.index == 198 || StaticStore.index == 197) && Integer.parseInt(StaticStore.legValue[leg - 1]) != 0 && Integer.parseInt(StaticStore.legValue[leg - 1]) != 15 
    			&& Integer.parseInt(StaticStore.legValue[leg - 1]) != 30 && Integer.parseInt(StaticStore.legValue[leg - 1]) != 45 ){
        	StaticStore.ToastDisplay(StaticStore.context,"Preferred Minutes must be valid");
    	} else if(StaticStore.index == 204 && Integer.parseInt(StaticStore.legValue[leg - 1]) > 24){ 
    		StaticStore.ToastDisplay(StaticStore.context,"Please enter valid hours (01-24)");
    	}else if(StaticStore.index == 204 && Integer.parseInt(StaticStore.legValue[leg - 1]) < 1){
    		StaticStore.ToastDisplay(StaticStore.context,"Please enter valid hours (01-24)");
    	}else if(StaticStore.index == 216 && !StaticStore.legValue[1].trim().equals(StaticStore.legValue[2])){
        	StaticStore.ToastDisplay(StaticStore.context,"New Password and Confirm New Password must be the same");
        }else if(tempIndex == 216 && StaticStore.legValue[0].equals(StaticStore.legValue[1])){
        	StaticStore.ToastDisplay(StaticStore.context,"Login Password and new password must be different");
        }else if(tempIndex == 114 &&  !(StaticStore.legValue[1].equals(StaticStore.legValue[2]))){
        	StaticStore.ToastDisplay(StaticStore.context,"Login Password and Confirm Login Password must be same");
        }
        	
        	
        else if(StaticStore.index == 117 && !StaticStore.legValue[0].trim().equals(StaticStore.legValue[1])){
        	StaticStore.ToastDisplay(StaticStore.context,"New Password and Confirm New Password must be the same");
        } else if(StaticStore.index == 97 && amount < StaticStore.midlet.templeMinAmount){
        	StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater or equal to Minimum Amount.");
        }else if(StaticStore.index == 97 && StaticStore.midlet.templeDenFlag && (amount % StaticStore.midlet.templeDenomination != 0)){
        	StaticStore.ToastDisplay(StaticStore.context,"Amount must be in multiples of ‘"+StaticStore.midlet.templeDenomination+"’; please check.");
        }else if(StaticStore.index == 179 && StaticStore.accpaymentFlag){
	
	    	assignValues(StaticStore.index);
	    	midlet.pubDynCan = this;
	    			            String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
	    			            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
	    			          	intent.putExtra("response",msg);
	    			           	intent.putExtra("formName", "QI00");
	    			           	StaticStore.accpaymentFlag = false;
	    			            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }else if(StaticStore.index == 174 && StaticStore.accpaymentFlag){
		    	assignValues(StaticStore.index);
		    	midlet.pubDynCan = this;
		    	String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2];
		    	Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		    	intent.putExtra("response",msg);
		    	intent.putExtra("formName", "W400");
		    	StaticStore.accpaymentFlag = false;
		    	StaticStore.midlet.startFragment(StaticStore.context,intent);
		        }/*else if(StaticStore.index == 171 && StaticStore.IMPS2M_REG_Flag){
			    	assignValues(StaticStore.index);
			    	midlet.pubDynCan = this;
			    	String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
			    	Intent intent = new Intent(StaticStore.context , DisplayableView.class);
			    	intent.putExtra("response",msg);
			    	intent.putExtra("formName", "W100");
			    	StaticStore.IMPS2M_REG_Flag = false;
			    	StaticStore.midlet.startFragment(StaticStore.context,intent);
			        }*/else if(((StaticStore.index == 101 || StaticStore.index == 103 || StaticStore.index == 88) && StaticStore.accpaymentFlag)){
		        	
		            assignValues(StaticStore.index);
		          //  StaticStore.LogPrinter('i',">>>>>>Dynamic Index"+);
		            midlet.pubDynCan = this;
		            String msg = StaticStore.legValue[3]+";"+StaticStore.legValue[1]+";"+StaticStore.legValue[2];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "FT00");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.menuDesc[220][3].equals("17")) && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		            StaticStore.depositIDConfirm = "17";
		            String msg = StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.menuDesc[220][3].equals("53")) && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		            StaticStore.depositIDConfirm = "53";
		            String msg = StaticStore.legValue[2]+";"+StaticStore.legValue[3];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempMDConfirmFlag  && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		        	StaticStore.depositIDConfirm = "16";
		            if(StaticStore.dynamicTempMD[5].equals("R")){
		            	selectedlist = "Renew My Maturity Amount";
		            }else if(StaticStore.dynamicTempMD[5].equals("P")){
		            	selectedlist = "Renew only My Deposit";
		            }else if(StaticStore.dynamicTempMD[5].equals("C")){
		            	selectedlist = "Close the Account on Maturity";
		            }
		            String msg = StaticStore.dynamicTempMD[2]+";"+StaticStore.dynamicTempMD[3]+";"+StaticStore.dynamicTempMD[4]+";"+selectedlist;
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		           	StaticStore.dynamicTempMDConfirmFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempS555ConfirmFlag  && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		            StaticStore.depositIDConfirm = "52";
		            if(StaticStore.dynamicTempS555[4].equals("M")){
		            	selectedlist = "Monthly Interest Payment";
		            }else if(StaticStore.dynamicTempS555[4].equals("Q")){
		            	selectedlist = "Quarterly Interest Payment";
		            }else if(StaticStore.dynamicTempS555[4].equals("C")){
		            	selectedlist = "Interest on Maturity";
		            }
		            String msg = StaticStore.dynamicTempS555[2]+";"+StaticStore.dynamicTempS555[3]+";"+selectedlist;
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		           	StaticStore.dynamicTempS555ConfirmFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempNDConfirmFlag && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		            if(StaticStore.dynamicTempND[2].equals("5")){
		            	selectedlist = "5 Years";
		            	StaticStore.depositIDConfirm = "51";
		            }else if(StaticStore.dynamicTempND[2].equals("10")){
		            	selectedlist = "10 Years";
		            	StaticStore.depositIDConfirm = "50";
		            }
		            String msg = selectedlist+";"+StaticStore.dynamicTempND[3]+";"+StaticStore.dynamicTempND[4]+";"+StaticStore.dynamicTempND[5];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		           	StaticStore.dynamicTempNDConfirmFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
			  }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempFDConfirmFlag  && StaticStore.accpaymentFlag){
		            assignValues(StaticStore.index);
		            midlet.pubDynCan = this;
		            StaticStore.depositIDConfirm = "15";
		            if(StaticStore.dynamicTempFD[6].equals("R")){
		            	selectedlist = "Renew My Deposit";
		            }else if(StaticStore.dynamicTempFD[6].equals("C")){
		            	selectedlist = "Close the Account on Maturity";
		            }
		            if(StaticStore.dynamicTempFD[7].equals("N")){
		            	selectedlist1 = "Interest Payment on Maturity";
		            }else if(StaticStore.dynamicTempFD[7].equals("M")){
		            	selectedlist1 = "Monthly Interest Payment";
		            }else if(StaticStore.dynamicTempFD[7].equals("Q")){
		            	selectedlist1 = "Quarterly Interest Payment";
		            }
		            String msg = StaticStore.dynamicTempFD[2]+";"+StaticStore.dynamicTempFD[3]+";"+StaticStore.dynamicTempFD[4]+";"+StaticStore.dynamicTempFD[5]+";"+selectedlist+";"+selectedlist1;
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APDOConfirm");
		           	StaticStore.accpaymentFlag = false;
		           	StaticStore.dynamicTempFDConfirmFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);

		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.menuDesc[220][3].equals("52"))){
		    		  StaticStore.dynamicTempS555Flag = true;
			    	  StaticStore.dynamicTempS555 = new String[StaticStore.legValue.length];
					  for (int i = 0; i < StaticStore.dynamicTempS555.length; i++) {
						 StaticStore.dynamicTempS555[i] = StaticStore.legValue[i];
					  }
				      Intent myIntent = StaticStore.midlet.getInterestOption(StaticStore.context);
					  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.menuDesc[220][3].equals("16"))){
			    	  StaticStore.dynamicTempMDFlag = true;
			    	  StaticStore.dynamicTempMD = new String[StaticStore.legValue.length];
					  for (int i = 0; i < StaticStore.dynamicTempMD.length; i++) {
						 StaticStore.dynamicTempMD[i] = StaticStore.legValue[i];
					  }
				      Intent myIntent = StaticStore.midlet.getMaturityInstructionMD(StaticStore.context);
					  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.menuDesc[220][3].equals("15"))){
		    	  	  String field2=editText[2].getEditableText().toString();
			    	  String field3=editText[3].getEditableText().toString(); 
			    	  StaticStore.depositPeriodInMonthsForQuarterly = false;
			    	  StaticStore.depositPeriodInMonthsForNonQuarterly = false;
			    	  StaticStore.depositPeriodInDays = false;
			    	  if((field2.equals("") && field3.equals(""))){
			      		  StaticStore.ToastDisplay(StaticStore.context,"Please enter atleast Deposit Period in Months or Deposit Period in Days");
			    	  }else if((!field2.equals("") && field3.equals(""))){
			    		  if((Integer.parseInt(field2) < 1) || (Integer.parseInt(field2) > 120)){
				    			 StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months should be in the range of 1 and 120 months");
				    	  }else{
				    		  if((Integer.parseInt(field2) > 2)){
					    		  StaticStore.depositPeriodInMonthsForQuarterly = true;
				    		  }else{
				    			  StaticStore.depositPeriodInMonthsForNonQuarterly = true;
				    		  }
						      StaticStore.dynamicTempFD = new String[StaticStore.legValue.length];
							  for (int i = 0; i < StaticStore.dynamicTempFD.length; i++) {
								  StaticStore.dynamicTempFD[i] = StaticStore.legValue[i];
							  }
							  StaticStore.dynamicTempFD[5] = "0";
							  Intent myIntent = StaticStore.midlet.getMaturityInstructionFD(StaticStore.context);
							  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
				    	  }
			    	  }else if((field2.equals("") && !field3.equals(""))){
			    		  boolean currentYearFlag = false;
			    		  if(currentYear % 4 == 0){
			    			  currentYearFlag = true;
			    		  }else{
			    			  currentYearFlag = false;
			    		  }
			    		  if((currentYearFlag == true && ((Integer.parseInt(field3) < 15) || (Integer.parseInt(field3) > 366)))){
			    				  StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Days should be in the range of 15 and 366 days");
			    		  }else if((Integer.parseInt(field3) < 15) || (Integer.parseInt(field3) > 365)){
				    			 StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Days should be in the range of 15 and 365 days");
			    		  }else{
				    			StaticStore.depositPeriodInDays = true;
						    	StaticStore.dynamicTempFD = new String[StaticStore.legValue.length];
								for (int i = 0; i < StaticStore.dynamicTempFD.length; i++) {
								    StaticStore.dynamicTempFD[i] = StaticStore.legValue[i];
								}
								StaticStore.dynamicTempFD[4] = "0";
							    Intent myIntent = StaticStore.midlet.getMaturityInstructionFD(StaticStore.context);
								StaticStore.midlet.startFragment(StaticStore.context,myIntent);
				    	  }
			    	  }else if((!field2.equals("") && !field3.equals(""))){
			    		  if((Integer.parseInt(field2) < 1) || (Integer.parseInt(field2) > 120)){
				    			 StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months should be in the range of 1 and 120 months");
						  }else{
							  if((Integer.parseInt(field2) == 120) && (Integer.parseInt(field3) > 0)){
								  StaticStore.ToastDisplay(StaticStore.context,"Maximum Period has already been reached");
							  }else if((Integer.parseInt(field3) < 1) || (Integer.parseInt(field3) > 30)){
					    			 StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Days should be in the range of 1 and 30 days");
				    		  }else{
				    			  if((Integer.parseInt(field2) > 2)){
						    		  StaticStore.depositPeriodInMonthsForQuarterly = true;
					    		  }else{
					    			  StaticStore.depositPeriodInMonthsForNonQuarterly = true;
					    		  }
							      StaticStore.dynamicTempFD = new String[StaticStore.legValue.length];
								  for (int i = 0; i < StaticStore.dynamicTempFD.length; i++) {
									  StaticStore.dynamicTempFD[i] = StaticStore.legValue[i];
								  }
								  Intent myIntent = StaticStore.midlet.getMaturityInstructionFD(StaticStore.context);
								  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
				    		  }
				    	  }
			    	  }
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && (StaticStore.dynamicTempNDFlag == true)){
			    	  StaticStore.dynamicTempNDFlag = false;
			    	  Intent myIntent = StaticStore.midlet.getSchemeTypeNMD(StaticStore.context);
					  StaticStore.midlet.startFragment(StaticStore.context,myIntent);	  
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APDO") && ((StaticStore.dynamicTempND1Flag == true) || (StaticStore.dynamicTempND2Flag == true))){
			    	  StaticStore.dynamicTempND = new String[StaticStore.legValue.length];
					  for (int i = 0; i < StaticStore.dynamicTempND.length; i++) {
						 StaticStore.dynamicTempND[i] = StaticStore.legValue[i];
					  }
				      Intent myIntent = StaticStore.midlet.getDepositPeriodNMD(StaticStore.context);
					  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APST") &&  !StaticStore.accpaymentFlag && StaticStore.initialMyOwnAccFlag){		
		    	  StaticStore.initialMyOwnAccFlag = false;
		    	  Intent myIntent = StaticStore.midlet.getAccScreenTo(StaticStore.context);
				  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APST") &&  !StaticStore.accpaymentFlag && StaticStore.secondMyOwnAccFlag){
		    	  StaticStore.secondMyOwnAccFlag = false;
		    	  assignValues(StaticStore.index);
               	  midlet.pubDynCan = this;
	              Intent myIntent;
		          myIntent = StaticStore.midlet.get_accTypeMenu(StaticStore.context,275,"M2A");
	              if(myIntent == null){
	        			String Acctype = null;
	        			StaticStore.menuDesc[220][4] = "-";
	        			StaticStore.selectedAccTypeTo = StaticStore.menuDesc[220][4];
	        			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
	              }else{
	            		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
	              }
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APST") &&  StaticStore.accpaymentFlag){		
		    	  assignValues(StaticStore.index);
		          midlet.pubDynCan = this;
		          String msg = StaticStore.midlet.maskedAccNumber(StaticStore.selectedAccNumberFrom) + ";" + StaticStore.midlet.maskedAccNumber(StaticStore.legValue[1]) + ";" + StaticStore.selectedAccTypeTo + ";" +StaticStore.legValue[3]+";"+StaticStore.legValue[4];
		          Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          intent.putExtra("response",msg);
		          intent.putExtra("formName", "APST");
		          StaticStore.accpaymentFlag = false;
		          StaticStore.midlet.startFragment(StaticStore.context,intent);
		      }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APCH") && StaticStore.accpaymentFlag){
		        	
		        	if(Integer.parseInt(StaticStore.legValue[0]) >= Float.parseFloat(midlet.IRCTCCardIssue[0][6])
		        			&& Integer.parseInt(StaticStore.legValue[0]) <= Float.parseFloat(midlet.IRCTCCardIssue[0][7])){
		        		assignValues(StaticStore.index);
		        		midlet.pubDynCan = this;
			            String msg = StaticStore.midlet.IRCTCCardIssue[StaticStore.selectedIndex][0]+" "+StaticStore.midlet.IRCTCCardIssue[StaticStore.selectedIndex][1]+";"+StaticStore.legValue[0];
			            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
			          	intent.putExtra("response",msg);
			           	intent.putExtra("formName", "APCH");
			           	StaticStore.accpaymentFlag = false;
			            StaticStore.midlet.startFragment(StaticStore.context,intent);
		        	}else{
		        		StaticStore.ToastDisplay(StaticStore.context,"The amount should be in the range of Rs."+(Integer.parseInt(midlet.IRCTCCardIssue[0][6]) )+" and Rs."+(Integer.parseInt(midlet.IRCTCCardIssue[0][7]))+".");
		        	}
		        	
		        			
		        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APTP")){
		        	
		        	if(Integer.parseInt(StaticStore.legValue[0]) >= Integer.parseInt(midlet.IRCTCCardIssue[0][6])
		        			&& Integer.parseInt(StaticStore.legValue[0]) <= Integer.parseInt(midlet.IRCTCCardIssue[0][7])){
		        		assignValues(StaticStore.index);
		        		midlet.pubDynCan = this;
		        		StaticStore.tagType ="APTC";
			            String msg = StaticStore.midlet.IRCTCCardIssue[0][0]+" "+StaticStore.midlet.IRCTCCardIssue[0][1]+";"+StaticStore.legValue[0];
			            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
			          	intent.putExtra("response",msg);
			           	intent.putExtra("formName", "APTP");
			           	StaticStore.accpaymentFlag = false;
			            StaticStore.midlet.startFragment(StaticStore.context,intent);
		        	}else{
		        		StaticStore.ToastDisplay(StaticStore.context,"The amount should be in the range of Rs."+(Integer.parseInt(midlet.IRCTCCardIssue[0][6]) )+" and Rs."+(Integer.parseInt(midlet.IRCTCCardIssue[0][7]))+".");
		        	}
	    			
	        
        
    	}else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("AP1U") && StaticStore.accpaymentFlag){
    		StaticStore.LogPrinter('i',">>>>>>>>>>>..hai");
	        	assignValues(StaticStore.index);
	        	
                	midlet.pubDynCan = this;
                	Intent myIntent = StaticStore.midlet
        			.get_accTypeMenu(StaticStore.context,178,"P2U");
                	if(myIntent == null){
            			String Acctype = null;
            			StaticStore.menuDesc[220][4] = "-";
            			StaticStore.legValue[2]= "-";
            			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";Rs."+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
            			Intent intent = new Intent(StaticStore.context , DisplayableView.class);
            			intent.putExtra("response",msg);
            			intent.putExtra("formName", "AP1U");
            			StaticStore.accpaymentFlag = false;
            			StaticStore.midlet.startFragment(StaticStore.context,intent);	
                	}else{
                		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
                	}
    	 }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("AP2U") && StaticStore.accpaymentFlag){
    			assignValues(StaticStore.index);
    			
            	midlet.pubDynCan = this;
            	Intent myIntent;
            	 myIntent = StaticStore.midlet.get_accTypeMenu(StaticStore.context,179,"P2U");
            	if(myIntent == null){
            		StaticStore.legValue[2]= "-";
//            	String msg = StaticStore.legValue[1] + ";" +StaticStore.legValue[2] 
//            	+ ";" + StaticStore.legValue[3] + ";Rs."+ StaticStore.legValue[4];
            	StaticStore.accpaymentFlag = false;
            	StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);

            	}else{
            		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
            	}
    	 }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APQN") && StaticStore.accpaymentFlag){
    		 assignValues(StaticStore.index);
    		
         	midlet.pubDynCan = this;
         	Intent myIntent;
         	 myIntent = StaticStore.midlet
 			.get_accTypeMenu(StaticStore.context,216,"NEFT");
         	
         	if(myIntent == null){
     			String Acctype = null;
     			StaticStore.menuDesc[220][4] = "-";
     			StaticStore.legValue[2]= "-";
     			String msg = StaticStore.legValue[1] + ";" + StaticStore.legValue[2]
     			+ ";" + StaticStore.legValue[3] + ";Rs."+ StaticStore.legValue[4]+ ";"+ StaticStore.legValue[5];
     			Intent intent = new Intent(StaticStore.context , DisplayableView.class);
     			intent.putExtra("response",msg);
     			intent.putExtra("formName", "QN00");
     			StaticStore.accpaymentFlag = false;
     			StaticStore.midlet.startFragment(StaticStore.context,intent);
     			//String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
//     			navigateTo(tempIndex);
         	}else{
         		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
         	}
    	}else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APQT") && StaticStore.accpaymentFlag){
        	assignValues(StaticStore.index);
        	
            	midlet.pubDynCan = this;
            	Intent myIntent;
            		 myIntent = StaticStore.midlet.get_accTypeMenu(StaticStore.context,180,"M2A");
            	if(myIntent == null){
        			String Acctype = null;
        			StaticStore.menuDesc[220][4] = "-";
        			StaticStore.legValue[2]= "-";
        		String msg = StaticStore.legValue[1] + ";" + StaticStore.legValue[2]
        		+ ";Rs." + StaticStore.legValue[3] + ";"+ StaticStore.legValue[4];
        		Intent intent = new Intent(StaticStore.context , DisplayableView.class);
        		intent.putExtra("response",msg);
        		intent.putExtra("formName", "QT00");
        		StaticStore.accpaymentFlag = false;
        		StaticStore.midlet.startFragment(StaticStore.context,intent);
//        			//String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
//        			navigateTo(tempIndex);
            	}else{
            		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
            	}
       }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APK1") && StaticStore.accpaymentFlag){
         	        	assignValues(StaticStore.index);
         	        	
                         	midlet.pubDynCan = this;
                         	Intent myIntent = StaticStore.midlet
                 			.get_accTypeMenu(StaticStore.context,119,"P2A");
                         	if(myIntent == null){
                     			String Acctype = null;
                     			StaticStore.menuDesc[220][4] = "-";
                     			StaticStore.legValue[2]= "-";
                     			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
                     			Intent intent = new Intent(StaticStore.context , DisplayableView.class);
                     			intent.putExtra("response",msg);
                     			intent.putExtra("formName", "APK1");
                     			StaticStore.accpaymentFlag = false;
                     			StaticStore.midlet.startFragment(StaticStore.context,intent);	
                         	}else{
                         		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
                         	}
          }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APK2") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	        	
            	midlet.pubDynCan = this;
            	Intent myIntent = StaticStore.midlet
    			.get_accTypeMenu(StaticStore.context,120,"P2A");
            	if(myIntent == null){
        			StaticStore.menuDesc[220][4] = "-";
        			StaticStore.legValue[2]= "-";
//        			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
//        			Intent intent = new Intent(StaticStore.context , DisplayableView.class);
//        			intent.putExtra("response",msg);
//        			intent.putExtra("formName", "APK2");
//        			StaticStore.accpaymentFlag = false;
//        			StaticStore.midlet.startFragment(StaticStore.context,intent);
        			StaticStore.accpaymentFlag = false;
                	StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
            	}else{
            		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
            	}
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equals("APSU") && StaticStore.initialFlag == true){
	        //	StaticStore.LogPrinter('i',">>>>>>cccccccccccccccccccccccmmmmmmmmmmmmmmmmm");
	        	StaticStore.initialFlag=false;
	        	for(i=0;i<StaticStore.legValue.length;i++)
	        	{
	        		 StaticStore.LogPrinter('i',"***********"+i+"  "+StaticStore.legValue[i]);
	        	}
	        	StaticStore.myMobileNo=StaticStore.legValue[3];
	        	StaticStore.dynamicTempSignup = new String[StaticStore.legValue.length];
		//		StaticStore.tempIndex = StaticStore.index;
				for (int i = 0; i < StaticStore.dynamicTempSignup.length; i++) {
					StaticStore.dynamicTempSignup[i] = StaticStore.legValue[i];
				}
	        	Intent myIntent = StaticStore.midlet.getmPAYRegistration(StaticStore.context);
				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APK5") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	            midlet.pubDynCan = this;
	            
	            String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::APK5 ::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
//	           
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,StaticStore.midlet.onlineAccPaymentList[StaticStore.selectedIndex][3].trim());
	           
		            String msg = StaticStore.midlet.onlineAccPaymentList[StaticStore.selectedIndex][0]+";"+StaticStore.midlet.onlineAccPaymentList[StaticStore.selectedIndex][1]+";"+
		            StaticStore.midlet.onlineAccPaymentList[StaticStore.selectedIndex][2]+";"+acctypename+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3];
		            //SSSS		            
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APK5");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APPI") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	            midlet.pubDynCan = this;
		            String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APPI");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APQM") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	            midlet.pubDynCan = this;
		            String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APQM");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("APPM") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	            midlet.pubDynCan = this;
		            String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "APPM");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }else if(StaticStore.index == 220 && StaticStore.tagType.equalsIgnoreCase("AP5U") && StaticStore.accpaymentFlag){
	        	assignValues(StaticStore.index);
	            midlet.pubDynCan = this;
		            String msg = StaticStore.legValue[2]+";"+StaticStore.legValue[3];
		            Intent intent = new Intent(StaticStore.context , DisplayableView.class);
		          	intent.putExtra("response",msg);
		           	intent.putExtra("formName", "AP5U");
		           	StaticStore.accpaymentFlag = false;
		            StaticStore.midlet.startFragment(StaticStore.context,intent);
	        }
        else if(tempIndex == 10 || tempIndex == 67){
        	//navigate comment
           /* dynamicDisp = midlet.getDisplay().getCurrent();
           if(tempIndex == 10){
                midlet.getDisplay().setCurrent(getGenderType());
            }*/
        }
    	
        else if((tempIndex == 5 || (tempIndex == 220 && StaticStore.tagType.equals("APP2"))) && !StaticStore.legValue[1].equals(StaticStore.legValue[2])){
        	StaticStore.ToastDisplay(StaticStore.context,"New mPIN and Confirm New mPIN must be same");
            
        }else if((tempIndex == 5 || (tempIndex == 220 && StaticStore.tagType.equals("APP2"))) && StaticStore.legValue[0].equals(StaticStore.legValue[1])){
        	StaticStore.ToastDisplay(StaticStore.context,StaticStore.IsPermitted?"mPIN and new mPIN must be different":"Activation code and new mPIN must be different");
        }else if(tempIndex == 205 && !StaticStore.legValue[1].equals(StaticStore.legValue[2])){
        	StaticStore.ToastDisplay(StaticStore.context,"New mPIN and Confirm New mPIN must be same");
        }else if(tempIndex == 205 && StaticStore.legValue[0].equals(StaticStore.legValue[1])){
        	StaticStore.ToastDisplay(StaticStore.context,StaticStore.IsPermitted?"mPIN and new mPIN must be different":"Activation code and new mPIN must be different");

 
}else if(StaticStore.index == 138  && !midlet.hashingByMD5(StaticStore.legValue[0]).equals(StaticStore.applicationPWD)){
	  StaticStore.ToastDisplay(StaticStore.context,"Please enter correct Login Password");
    
        }else if(tempIndex == 138 && !StaticStore.legValue[1].equals(StaticStore.legValue[2])){
            StaticStore.legValue[2] = "";
            StaticStore.ToastDisplay(StaticStore.context,"New password and Confirm New password must be same");
        } else if(tempIndex == 138 && StaticStore.legValue[0].equals(StaticStore.legValue[1])){
        	StaticStore.ToastDisplay(StaticStore.context,"Login Password and New password should not be same");
        }else if(StaticStore.index == 161 && !StaticStore.legValue[2].equals(StaticStore.legValue[3])){
          	StaticStore.ToastDisplay(StaticStore.context,"Remitter PIN and Confirm Remitter PIN must be the same");
        }else if(StaticStore.index == 161 && StaticStore.legValue[4].equals("0")){
           	StaticStore.ToastDisplay(StaticStore.context,"Amount should not be zero.");
        }else if(StaticStore.index == 161 && ((Integer.parseInt(StaticStore.legValue[4]) % 100)) != 0){
        	StaticStore.ToastDisplay(StaticStore.context,"Amount must be multiples of hundred.");
        }else if((StaticStore.index == 161 && (StaticStore.legValue[2].equals("0000") || StaticStore.legValue[3].equals("0000"))) ||
                ((StaticStore.index == 163 || StaticStore.index == 165 || StaticStore.index == 168) && StaticStore.legValue[2].equals("0000"))){
        	StaticStore.ToastDisplay(StaticStore.context,"Please try any other pin except 0000.");
        }else if((StaticStore.index == 51 || StaticStore.index == 52) && (StaticStore.legValue[2].trim().equals("") && StaticStore.legValue[3].trim().equals(""))){
            //CustomAlert custAlert   =   new CustomAlert("", "Either city or area must be entered.", null, AlertType.INFO, new DynamicCanvas(midlet,display,StaticStore.index,2), midlet, 33);
        	StaticStore.ToastDisplay(StaticStore.context,"Either city or area must be entered.");
        }//Amount validation
        else if(StaticStore.index == 122 &&  StaticStore.menuDesc[122][12].equals("P") && (Integer.parseInt(StaticStore.legValue[11]) > Integer.parseInt(midlet.billpayNotifyDet[14][0]) ||
                Integer.parseInt(StaticStore.legValue[11]) < Integer.parseInt(midlet.billpayNotifyDet[15][0]))){
        	StaticStore.ToastDisplay(StaticStore.context,"The amount must be in the range of Rs."+(Integer.parseInt(midlet.billpayNotifyDet[15][0]))+" and Rs."+(Integer.parseInt(midlet.billpayNotifyDet[14][0]))+".");
    }else if(StaticStore.index == 122 && StaticStore.menuDesc[122][12].equals("E") && (Integer.parseInt(StaticStore.legValue[11]) <= Integer.parseInt(midlet.billpayNotifyDet[14][0]))){
    	StaticStore.ToastDisplay(StaticStore.context,"The amount must be more than Rs."+midlet.billpayNotifyDet[14][0]+".");
    }else if(StaticStore.index == 151 ||StaticStore.index == 118 ){
        	if(StaticStore.index == 151){
        		StaticStore.shortCodeNumber = StaticStore.legValue[0];
        	}else if(StaticStore.index == 118){
        		StaticStore.VMN_Number = StaticStore.legValue[0];
        	}
        	StaticStore.midlet.writeinMemory(StaticStore.context);
        	StaticStore.ToastDisplay(StaticStore.context, "Your settings have been saved successfully");
        }
          else if(StaticStore.index == 201 && !StaticStore.fromChequeList){
        	  midlet.pubDynCan = this;
        	  Intent myIntent = midlet
        	  .getNoOfChequeLeaf(StaticStore.context);
        	  StaticStore.midlet.startFragment(StaticStore.context,myIntent);
          }
          
          else if(StaticStore.forSeperateMPinAtFirst == true && StaticStore.index == 58){
        	StaticStore.LogPrinter('i',"Calling from == 58 ");
        	StaticStore.LogPrinter('i',"StaticStore.isMpinAtLast == 58==> "+StaticStore.isMpinAtLast );
       	StaticStore.menuDesc[221] = new String[StaticStore.menuDesc[StaticStore.transIndex].length];			
			for (int i = 0; i < StaticStore.menuDesc[StaticStore.transIndex].length; i++) {
				StaticStore.menuDesc[221][i] = StaticStore.menuDesc[StaticStore.transIndex][i];
			}
//               DynamicCanvas.menuDesc[58] = DynamicCanvas.menuDesc[tempIndex];
       		 int numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.transIndex][StaticStore.menuDesc[StaticStore.transIndex].length - 4]);
       	     
       	     for(int j = 2;j < 2 + numLabels;j++){
       	         if(j != 2){
       	        	 StaticStore.menuDesc[221][j] = StaticStore.menuDesc[StaticStore.transIndex][j];
       	        	 StaticStore.menuDesc[221][j + numLabels] = StaticStore.menuDesc[StaticStore.transIndex][j + numLabels];
       	         } else{
       	        	 StaticStore.menuDesc[221][j] = StaticStore.globalMPIN;
       	        	 StaticStore.menuDesc[221][j + numLabels] = "";
       	         }
//       	         if(j < 1 + numLabels){
//       	        	 DynamicCanvas.menuDesc[58][j + numLabels + 1] = "false";
//       	        	 DynamicCanvas.menuDesc[58][j + numLabels + 2] = "false";
//                                 DynamicCanvas.menuDesc[58][j + numLabels + 3] = "N";
//       	         }
       	     }
                        //{"IMPS Fund Transfer","APQI;Y",mPINString,"Beneficiary Mobile No.","Beneficiary MMID","Amount (Rs.)","Remarks","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"},//174-179
       	     StaticStore.menuDesc[221][StaticStore.menuDesc[221].length - 3] = "false";
       	     StaticStore.menuDesc[221][StaticStore.menuDesc[221].length - 2] = "false";
       	     StaticStore.menuDesc[221][StaticStore.menuDesc[221].length - 1] = StaticStore.menuDesc[StaticStore.transIndex][StaticStore.menuDesc[StaticStore.transIndex].length - 1];
                       
//                       for(int i = 0; i < StaticStore.menuDesc[221].length;i++){
//                            StaticStore.LogPrinter('i'":::::DynamicCanvas.menuDesc[index]::::::::"+StaticStore.menuDesc[221][i]);
//                       }
                                StaticStore.forSeperateMPinAtFirst = false; 
                                
                       StaticStore.index = 221; 
//Siva G                       StaticStore.FromListScreen = true;
                       StaticStore.LogPrinter('i',"numLabels --> "+numLabels);
                       StaticStore.LogPrinter('i',"validation --> "+validation.length);
                       StaticStore.LogPrinter('i',"StaticStore.index = 221 --> "+Arrays.deepToString(StaticStore.menuDesc[221]));
                       
                       if(numLabels > 1 && validation.length != 1){
                    	   // finish();
                    	   StaticStore.LogPrinter('i',"Finish Called from --> 2361");
                       }
                   	StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
                
            }else if(!StaticStore.isMoreClicked && StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 1].equals("N") && StaticStore.index != 58){
        	StaticStore.LogPrinter('i',"StaticStore.isMpinAtLast  PART Called ==> "+StaticStore.isMpinAtLast ); // !StaticStore.isMoreClicked && Siva G
            	StaticStore.menuDesc[58] = new String[StaticStore.menuDesc[tempIndex].length];			
			for (int i = 0; i < StaticStore.menuDesc[tempIndex].length; i++) {
				StaticStore.menuDesc[58][i] = StaticStore.menuDesc[tempIndex][i];
			}
//                DynamicCanvas.menuDesc[58] = DynamicCanvas.menuDesc[tempIndex];
        		 int numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
        	     
        	     for(int j = 2;j < 2 + numLabels;j++){
        	         if(j != 2){
        	        	 StaticStore.menuDesc[58][j] = StaticStore.legValue[j -2];
        	        	 StaticStore.menuDesc[58][j + numLabels] = "";
        	         } else{
        	        	 StaticStore.menuDesc[58][j] = mPINString;
        	        	 StaticStore.menuDesc[58][j + numLabels] = "4-4-N-Y-Y";
        	         }
//        	         if(j < 1 + numLabels){
//        	        	 DynamicCanvas.menuDesc[58][j + numLabels + 1] = "false";
//        	        	 DynamicCanvas.menuDesc[58][j + numLabels + 2] = "false";
//                                  DynamicCanvas.menuDesc[58][j + numLabels + 3] = "N";
//        	         }
        	     }
                         //{"IMPS Fund Transfer","APQI;Y",mPINString,"Beneficiary Mobile No.","Beneficiary MMID","Amount (Rs.)","Remarks","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"},//174-179
        	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 3] = "false";
        	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 2] = "false";
        	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 1] = "N";
                        
                        for(int i = 0; i < StaticStore.menuDesc[58].length;i++){
                             StaticStore.LogPrinter('i',":::::DynamicCanvas.menuDesc[index]::::::::"+StaticStore.menuDesc[58][i]);
                        }
                        StaticStore.originalindex = StaticStore.index;
                        StaticStore.LogPrinter('i', "2425 mpin Last Creation Called");
                        StaticStore.index = 58;   
                        StaticStore.FromListScreen = true;
                    	StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
                 
             }
        else if(tempIndex == 3 || tempIndex == 4 || tempIndex == 19 || tempIndex == 17 || tempIndex == 24  || tempIndex == 20 || tempIndex == 22 || StaticStore.originalindex == 17 || StaticStore.originalindex == 24 ||
        		(tempIndex == 220 && StaticStore.tagType.equals("APDY"))|| ((tempIndex == 222 ||tempIndex == 58) && StaticStore.tagType.equals("APDM"))){ //TNEB Siva  ||tempIndex == 23 ||

            if(tempIndex == 3) {
            } else if(tempIndex == 4){
            } else if(tempIndex == 19 || tempIndex == 20){
                navigateTo(-6);
            } else if(tempIndex == 22){
                navigateTo(-7);
//            } else if(tempIndex == 23){
//                 navigateTo(-7); 
             }else if((tempIndex == 17 || tempIndex == 24 ) || StaticStore.originalindex == 17 || StaticStore.originalindex == 24 || (tempIndex == 220 && StaticStore.tagType.equals("APDY"))|| ((tempIndex == 222 ||tempIndex == 58) && StaticStore.tagType.equals("APDM"))){ //TNEB Siva tempIndex == 17 || tempIndex == 24||
            	 StaticStore.LogPrinter('i',"StaticStore.tagType >>>"+StaticStore.tagType+tempIndex);
            	 StaticStore.LogPrinter('i',"Semi Replace::>>>"+tempIndex+" :>>>>>"+StaticStore.tagType+">>>> leg"+leg);
	        		StaticStore.LogPrinter('i',"Semi Replace:::::::>>>>>>"+Arrays.deepToString(StaticStore.menuDesc[221]));
	        		StaticStore.LogPrinter('i',"Semi Replace: legvalue::::::>>>>>>"+Arrays.deepToString(StaticStore.legValue));
	        		StaticStore.LogPrinter('i',"StaticStore.isMpinAtLast >>>"+StaticStore.isMpinAtLast);
 	        	if(tempIndex == 17 || tempIndex == 24 || StaticStore.originalindex == 17 || StaticStore.originalindex == 24){
 	        		int i = 0;
 	        		if(StaticStore.index == 58){
 	           		StaticStore.index = StaticStore.originalindex;
 	           		tempIndex = StaticStore.index;
 	           	    }
 	        		for(int k = 0;k < leg; k++){
                	
                	if(!StaticStore.isMpinNeeded && k == 0 && !midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){
                        continue;
                    }else if(StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 1].equals("Y") && k == 0){
                       continue;  
                    }
                	
                    if(tempIndex == 17 && i == StaticStore.legValue.length - 1) {
                        break;
                    } else if(tempIndex == 24 && i == StaticStore.legValue.length - 1) {
                        break;
                    } else if(tempIndex == 24 && i >= StaticStore.legValue.length - 3) {
                        i++;
                    } else {
                        i = k;
                    }
                    if(i == 3){// no of labels
                        sendSMSString += StaticStore.legValue[3] + ";";
                        int labelCount = Integer.parseInt(StaticStore.legValue[3]);
                        for(int j = 0; j < labelCount;j++){
                            if(j == labelCount - 1)
                            sendSMSString += StaticStore.legValue[4 + j] + ";";
                            else
                            sendSMSString += StaticStore.legValue[4 + j] + ":";
                        }
                        if(tempIndex == 17){
                            i = StaticStore.legValue.length - 1;
                        }else{
                            i = StaticStore.legValue.length - 3;
                        }
                    }
                    	
                    	if(StaticStore.encyptionFlag[i]){
        					RsaEncryption rsaObj = new RsaEncryption(StaticStore.legValue[i]);
        					sendSMSString = sendSMSString + rsaObj.cipherText + seperator;
        				}else{
        					sendSMSString = sendSMSString + StaticStore.legValue[i] + seperator; // .replace(' ',StaticStore.spaceSeperator.charAt(0))
        				}    
                }
                }else if((tempIndex == 220 && StaticStore.tagType.equals("APDY"))||((tempIndex == 222 ||tempIndex == 58) && StaticStore.tagType.equals("APDM"))) //TNEB Siva
	        	{
	        		StaticStore.LogPrinter('i',"Semi Replace::>>>"+tempIndex+" :>>>>>"+StaticStore.tagType+">>>> leg"+leg);
	        		StaticStore.LogPrinter('i',"Semi Replace:::::::>>>>>>"+Arrays.deepToString(StaticStore.menuDesc[221]));
	        		StaticStore.LogPrinter('i',"Semi Replace: legvalue::::::>>>>>>"+Arrays.deepToString(StaticStore.legValue));
	        		int i = 0;
	        		for(int k = 0;k < leg; k++){
	        			
	        			if(!StaticStore.isMpinNeeded && k == 0 && !midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){
	                        continue;
	                    }else if(StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 1].equals("Y") && k == 0){
	                       continue;  
	                    }
	        			
	        				i = k;
	        			if(i == 3){// no of labels
	        				int labelCount = 0;
	        				if(StaticStore.tagType.equals("APDY")|| StaticStore.tagType.equals("APDM")){
	        					if(StaticStore.tagTypePrev == "APDT")
	        					{
	        						labelCount = Integer.parseInt(StaticStore.midlet.tempDT[3]);
	        						StaticStore.tagTypePrev = " ";
	        					}
	        					else
	        					labelCount = StaticStore.midlet.Sub_CatInput.length/2; ///Integer.parseInt(StaticStore.legValue[3])
	        						StaticStore.LogPrinter('i',"Label Count>>>"+labelCount);
	        				}
//	        				else if(StaticStore.tagType.equals("APDN")){
//	        					labelCount = Integer.parseInt(StaticStore.midlet.tempDT[3]);
//	        				}
	        				
	        				for(int j = 0; j < labelCount;j++){
	        					
	        					if(j == labelCount - 1){
	        						sendSMSString += StaticStore.legValue[3 + j] + ";";
	        					}else{
	        						sendSMSString += StaticStore.legValue[3 + j] + ":";
	        						k++;
	        					}
	        					StaticStore.LogPrinter('i',"SMS String>>>>"+sendSMSString);
	        				}
	        				
//	        				if(!StaticStore.tagType.equals("APDM"))
//	        				break;

	        			}else{
	        				StaticStore.LogPrinter('i',"Semi Replace:::::::>>>>>>"+tempIndex+" :::::::>>>>>>"+StaticStore.tagType+" :::::::>>>>>>"+i+" :::::::>>>>>>"+k);
	        				if(StaticStore.encyptionFlag[i]){
	        					RsaEncryption rsaObj = new RsaEncryption(StaticStore.legValue[i]);
	        					sendSMSString = sendSMSString + rsaObj.cipherText + seperator;
	        				}else{
	        					sendSMSString = sendSMSString + StaticStore.legValue[i] + seperator; // .replace(' ',StaticStore.spaceSeperator.charAt(0))
	        				}
	        				StaticStore.LogPrinter('i',"sendSMSString >>>>>"+sendSMSString);
	        			}
	        		}
//	        		if(StaticStore.tagType.equalsIgnoreCase("APDM"))
//	        		{
//	        			sendSMSString = sendSMSString +  StaticStore.midlet.txnID + seperator;	
//	        		}
	        	}
 	        	
 	        
 	        	if((StaticStore.menuDesc[tempIndex][1].substring(StaticStore.menuDesc[tempIndex][1].length()-1).equals("N") 
 	             		&& !midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4)))
 	             		|| StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("GT")
 	             		|| StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("AG")
 	             		){//EMPTY REQUEST        
 	                 StaticStore.isMpinNeeded = true;
 	                 StaticStore.globalMPIN = "";
 	                 String tempTagName = sendSMSString.substring(0,sendSMSString.indexOf(';'));
 	                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
 	                 tempTagName = tempTagName+";"+sendSMSString.substring(0,sendSMSString.indexOf(';'))+";";
 	                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
 	                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
 	                 sendSMSString = tempTagName+sendSMSString;
 	                 if(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("GT")){
 	                 	sendSMSString = StaticStore.menuDesc[StaticStore.index ][1] + ";";
 	                 }
 	             }
// 	        	 if(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("CM")){
// 	        		sendSMSString = sendSMSString+StaticStore.myMobileNo + ";";
// 	        		StaticStore.LogPrinter('i',"sendSMSString ===> "+sendSMSString);
// 	        	}
                
                sendSMSString += StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+seperator+StaticStore.date;// By ABINAYA.J.A time;//new Date().getTime()
              //  sendSMSString  = sendSMSString.replace('@',':');
                sendSMSString  = sendSMSString.replace("@","3EM*#L9");
                sendSMSString  = sendSMSString.replace('_','%');
                StaticStore.LogPrinter('i',">>>>>>>"+sendSMSString);   
                StaticStore.LogPrinter('i',">>>>>>>>>>>>going from 1st");
                if(!StaticStore.IsGPRS){
                    String smsNumber = "";
                    if(StaticStore.isShortCode){
                        smsNumber = StaticStore.shortCodeNumber;
                    }else{
                        smsNumber = StaticStore.VMN_Number;
                    }
                 //   sendSMS         =   new SMSSend(smsNumber, sendSMSString);//navigate comment
                    StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
	                midlet.writeinMemory(StaticStore.context);
	                
                    
                }else{
//                    if(StaticStore.httpObj == null)
                        //StaticStore.httpObj         =   new HttpConnect(StaticStore.GprsUrl + servlet, "1", midlet);
//                    sendSMSString = midlet.replaceSpace(sendSMSString,"$",':');
                    StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
	                midlet.writeinMemory(StaticStore.context);
	              //test1
	                StaticStore.LogPrinter('i',"calling from sendGprs 111 ");
	                sendGprs(sendSMSString);
//                  StaticStore.httpObj         =   new HttpConnect("http://10.44.37.51:7101/MPAYPORTAL/servlet/MQSender?request="+StaticStore.myMobileNo + sendSMSString, "1", midlet);
                  //navigate comment
/*                    StaticStore.httpObj             =   new HttpConnect(StaticStore.GprsUrl + StaticStore.servlet, "1", midlet);
                    StaticStore.httpObj.httpSent    =   false;
                    StaticStore.httpObj.httpNotSent =   false;
                    StaticStore.httpObj.setReqMessage(StaticStore.myMobileNo + sendSMSString);               */
                }
                //httpConnect       = new HttpConnect("http://10.44.3.25:7001/MBank/servlet/MQSender?request=BE%201144",false,midlet);
              //navigate comment
                /*thread          =   new Thread(StaticStore.context);
                thread.start();*/
                }

        } else if(StaticStore.tagType.equals("AP2F")){
        	            			
    			for(int i = 0; i < validIndex.length - 1;i++){
    				StaticStore.legValue[i] = midlet.feedbackList[i][0] + "*" + StaticStore.legValue[i];
    			}
        
        	
        	for(int i = 0;i < leg;i++){
        		if(i < leg - 2){
        			sendSMSString = sendSMSString + StaticStore.legValue[i] + '#';	
        		}else{
        		sendSMSString = sendSMSString + StaticStore.legValue[i] + seperator;
        		}
        	}
        	sendSMSString += StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+seperator+StaticStore.date;// By ABINAYA.J.A time;
            sendSMSString  = sendSMSString.replace('@',':');
            sendSMSString  = sendSMSString.replace('_','%');        
            StaticStore.LogPrinter('i',">>>>>>>"+sendSMSString);            
            if(!StaticStore.IsGPRS){
                String smsNumber = "";
                if(StaticStore.isShortCode){
                    smsNumber = StaticStore.shortCodeNumber;
                }else{
                    smsNumber = StaticStore.VMN_Number;
                }
                StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
                midlet.writeinMemory(StaticStore.context);
                
            }else{
//              sendSMSString = midlet.replaceSpace(sendSMSString,"$",':');
                StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
                midlet.writeinMemory(StaticStore.context);
                //test2
                StaticStore.LogPrinter('i',"calling from sendGprs 222 ");
                sendGprs(sendSMSString);           
            }
           
        }
        else {
            /* list check */
             if(tempIndex != -1 && tempIndex != -4 && tempIndex != -5){
                 if(tempIndex == 5 || tempIndex == 205){
                    leg = 2;
                }             

                if(tempIndex == 138){
                     leg = 1;
                     StaticStore.legValue[0]    = StaticStore.legValue[1];
//                     StaticStore.tempApplicationPWD = StaticStore.legValue[1];
                     StaticStore.LogPrinter('i',"application pwd::new:::"+StaticStore.legValue[1]);
                }
    //
                if(tempIndex == 216 ){
                	leg = 2;
                }
                 if(tempIndex == 220 && (StaticStore.tagType.equalsIgnoreCase("APOG") || StaticStore.tagType.equalsIgnoreCase("APBO"))){
                	 if(!StaticStore.regenerateClicked) { 
            		   StaticStore.tempMobileNo = StaticStore.legValue[0];
            		   StaticStore.LogPrinter('i',"StaticStore.tempMobileNo ===> "+StaticStore.tempMobileNo);
                	 }
            	 }
                 if((StaticStore.index == 220 || StaticStore.index == 58)&& StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempS555Flag == true){
                	 for( int i=0;i<StaticStore.dynamicTempS555.length;i++){
                		 StaticStore.dynamicTempS555Flag = false;
     					if(StaticStore.dynamicTempS555[0].equals("")){
     						StaticStore.dynamicTempS555[0] = StaticStore.legValue[0];
     					}
     					StaticStore.legValue[i]= StaticStore.dynamicTempS555[i];
     				 }
                	 currentDate = StaticStore.legValue[2];
	             }
                 
                 if((StaticStore.index == 220 || StaticStore.index == 58)&& StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempMDFlag == true){
                	 for( int i=0;i<StaticStore.dynamicTempMD.length;i++){
                		 StaticStore.dynamicTempMDFlag = false;
     					if(StaticStore.dynamicTempMD[0].equals("")){
     						StaticStore.dynamicTempMD[0] = StaticStore.legValue[0];
     					}
     					StaticStore.legValue[i]= StaticStore.dynamicTempMD[i];
     				 }
                	 currentDate = StaticStore.legValue[2];
	                 String temp = StaticStore.legValue[3];
	                 StaticStore.legValue[3] = StaticStore.legValue[4];
	                 StaticStore.legValue[4] = temp;
                 }
                 
                 if((StaticStore.index == 220 || StaticStore.index == 58)&& StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempFDFlag == true){
                	 for( int i=0;i<StaticStore.dynamicTempFD.length;i++){
                		StaticStore.dynamicTempFDFlag = false;
     					if(StaticStore.dynamicTempFD[0].equals("")){
     						StaticStore.dynamicTempFD[0] = StaticStore.legValue[0];
     					}
     					StaticStore.legValue[i]= StaticStore.dynamicTempFD[i];
     				 }
                	 currentDate = StaticStore.legValue[2];
                	 String temp = StaticStore.legValue[3].trim();
                	 StaticStore.legValue[3] = StaticStore.legValue[4].trim();
                	 StaticStore.legValue[4] = StaticStore.legValue[5].trim();
                	 StaticStore.legValue[5] = temp;
                 }
                 
                 if((StaticStore.index == 220 || StaticStore.index == 58)&& StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempNDFinalFlag == true){
                	 String temp = StaticStore.dynamicTempND[2];
                	 StaticStore.dynamicTempND[2] = StaticStore.dynamicTempND[3];
                	 StaticStore.dynamicTempND[3] = StaticStore.dynamicTempND[5];
                	 StaticStore.dynamicTempND[5] = temp;
                	 for( int i=0;i<StaticStore.dynamicTempND.length;i++){
                		 StaticStore.dynamicTempNDFinalFlag = false;
     					if(StaticStore.dynamicTempND[0].equals("")){
     						StaticStore.dynamicTempND[0] = StaticStore.legValue[0];
     					}
     					StaticStore.legValue[i]= StaticStore.dynamicTempND[i];
      				 }
	                 currentDate = StaticStore.legValue[2];
                 }
                 
                 if((StaticStore.index == 220 || StaticStore.index == 58)&& StaticStore.tagType.equalsIgnoreCase("APDO") && StaticStore.dynamicTempRDFlag == true){
                	 StaticStore.dynamicTempRDFlag = false;
                	 String temp = StaticStore.legValue[3];
	                 StaticStore.legValue[3] = StaticStore.legValue[4];
	                 StaticStore.legValue[4] = temp;
                 }
                if(tempIndex == 171 || (tempIndex == 220 && StaticStore.tagType=="AP1I")) {
                    String temp    =   StaticStore.legValue[1];
                    StaticStore.legValue[1]    =   StaticStore.legValue[2];
                    StaticStore.legValue[2]    =   temp;
                }
                if(tempIndex == 117){
                     leg = 1;
//                     StaticStore.tempApplicationPWD  = StaticStore.legValue[0];
                }
                if(tempIndex == 209){
                	StaticStore.encyptionFlag[0] = true;
                }
    //
//                 if(tempIndex == 113){
//                     StaticStore.applicationPWD = StaticStore.legValue[4];
//                     midlet.writeinMemory();
//                 }
    //
//                 if(tempIndex == 114){
//                     StaticStore.applicationPWD = StaticStore.legValue[0];
//                     midlet.writeinMemory();
//                 }             

                for(int i = 0;i < leg; i++){
                	 if(!StaticStore.isMpinNeeded && i == 0 && !midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){
                         continue;
                     }else if(StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 1].equals("Y") && i == 0){
                        continue;  
                     }
                	 
                	 if(StaticStore.tagType != null &&(StaticStore.index == 220 &&(StaticStore.tagType.equals("APP4")|| StaticStore.tagType.equals("APP2")))  && i == 1){
                 		continue;
                 	}
                	 
//                     if(tempIndex == 113 && (i == 3 || i == 5)){
//                         continue;
//                     }
                     
//                     if(tempIndex == 155 && (i == 2)){
// 	                    continue;
// 	                 }
                     
                     if(tempIndex == 161 && (i == 2)){
                         continue;
                     } 
                     if(tempIndex == 114 && (i == 2)){
                         continue;
                     }
                     if(tempIndex == 220 && (StaticStore.tagType.equals("APOG") || StaticStore.tagType.equals("APBO") ) && (i == 0)){
 	                    continue;
 	                }
                     if(tempIndex == 220 && StaticStore.tagType.equals("AP3T") && i > 4 && i < leg-2){
                    	 seperator = ":";
                     }else if((tempIndex == 220  || StaticStore.index == 58) && StaticStore.tagType.equals("APDO") && i > 1 && i < leg-1){ 
                    	 seperator = ":"; 
                     }else if((tempIndex == 220  || StaticStore.index == 58 || StaticStore.index == 222 || StaticStore.index == 221) && (StaticStore.tagType.equals("AP2B") || StaticStore.tagType.equals("APDY")) && i > 2 && i < leg-1){ 
                    	 seperator = ":";
                     }else if((tempIndex == 220  || StaticStore.index == 58 || StaticStore.index == 222 || StaticStore.index == 221) && (StaticStore.tagType.equals("APN2")) && i > 1 && i < leg-2){ 
                    	 seperator = ":"; 
                     }else if((tempIndex == 220  || StaticStore.index == 58 || StaticStore.index == 222 || StaticStore.index == 221) && (StaticStore.tagType.equals("APN4")) && i > 1 && i < leg-4){ 
                    	 seperator = ":"; 
                     }else if((tempIndex == 220  || StaticStore.index == 58 || StaticStore.index == 222 || StaticStore.index == 221) && (StaticStore.tagType.equals("APN5")) && i > 2 && i < leg-7){ 
                    	 seperator = ":"; 
                     }else{
                    	 seperator = ";";
                     }
                     
                        if(StaticStore.encyptionFlag[i]){
                           if(tempIndex == -2 || tempIndex == -3 || tempIndex == 3 || tempIndex == 4 || tempIndex == 103 || tempIndex == 101 || tempIndex == 88 ||
                              tempIndex == 69 || tempIndex == 77 || tempIndex == 8 || tempIndex == 62 ||tempIndex == 63 || tempIndex == 97 || tempIndex == 90 ||
                              tempIndex == 24 || tempIndex == 120 || tempIndex == 122 || tempIndex == 31 || tempIndex == 191  || tempIndex == 174){
                           String cipher = "";                                 
                           RsaEncryption rsaObj = new RsaEncryption(StaticStore.midlet.txnID.substring(0,6).trim());
                           cipher = rsaObj.cipherText + "?";
                           rsaObj = new RsaEncryption(StaticStore.legValue[i]);
                           cipher += rsaObj.cipherText;
                           sendSMSString = sendSMSString + cipher + seperator;
                       }else{                         	   
                           if(StaticStore.index == 220 && StaticStore.tagType.equals("APNU")){
                               sendSMSString = sendSMSString + midlet.getTwelveByteEncryption(StaticStore.legValue[i])+seperator;
                           }else{
                           	   RsaEncryption rsaObj = new RsaEncryption(StaticStore.legValue[i]);
                               sendSMSString = sendSMSString + rsaObj.cipherText + seperator;    
                           }
                            
                       }
                        }else{
                        	//StaticStore.LogPrinter('i',"inside else ... else encryption flag");
                           if(StaticStore.legValue[i].trim().equals("")){
                                StaticStore.legValue[i]  = " ";
                            }else if(StaticStore.legValue[i].equals("#")){
                                // which is used for b2b
                                continue;
                            }
//                            else if(StaticStore.legValue[i].equals(currentDate)){
//                            	StaticStore.legValue[i] = null;
//                            }
                           
                         if(!(StaticStore.legValue[i].equals(currentDate))){
                        	     sendSMSString = sendSMSString + StaticStore.legValue[i] + seperator;
                         }
                        }
                    }
              //navigate comment
                 /*if(tempIndex == -2){
                    sendSMSString = sendSMSString + midlet.mobileRegList[midlet.objClsA.mobileList.getSelectedIndex()][0];
                }

                if(tempIndex == -3){
                    sendSMSString = sendSMSString + midlet.accRegList[midlet.objClsA.accList.getSelectedIndex()][0]+seperator+midlet.accRegList[midlet.objClsA.accList.getSelectedIndex()][1];
                }*/
            }else{
                 /* list check */
                 RsaEncryption rsa = new RsaEncryption(StaticStore.legValue[0]);
                 /* -1 means account register request. -4 means airline search request. -5 means gender list*/
                 //navigate comment
                 /*if(tempIndex == -1)
                     sendSMSString = sendSMSString + rsa.cipherText +seperator+StaticStore.legValue[1]+seperator+((accTypeList.getSelectedIndex() == 0)?"20":"10")+seperator+StaticStore.legValue[2]+seperator;
                 else if(tempIndex == -4)
                      sendSMSString = sendSMSString + rsa.cipherText +seperator+StaticStore.legValue[1]+seperator+airlineList.getSelectedIndex()+seperator+StaticStore.legValue[2]+seperator;
                 else if(tempIndex == -5)
                     sendSMSString = sendSMSString + rsa.cipherText +seperator+StaticStore.legValue[1]+seperator+StaticStore.legValue[2]+seperator+genderList.getSelectedIndex()+seperator;*/
            }

            
           
             if((StaticStore.menuDesc[tempIndex][1].substring(StaticStore.menuDesc[tempIndex][1].length()-1).equals("N") 
             		&& !midlet.isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4)))
             		|| StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("GT")
             		|| StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("AG")
             		){//EMPTY REQUEST        
                 StaticStore.isMpinNeeded = true;
                 StaticStore.globalMPIN = "";
                 String tempTagName = sendSMSString.substring(0,sendSMSString.indexOf(';'));
                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
                 tempTagName = tempTagName+";"+sendSMSString.substring(0,sendSMSString.indexOf(';'))+";";
                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
                 sendSMSString = sendSMSString.substring(sendSMSString.indexOf(';')+1);
                 sendSMSString = tempTagName+sendSMSString;
                 if(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("GT")){
                 	sendSMSString = StaticStore.menuDesc[StaticStore.index ][1] + ";";
                 } 
             }
//             if(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("CM")){
//	        		sendSMSString = sendSMSString+StaticStore.myMobileNo + ";";
//	        		StaticStore.LogPrinter('i',"sendSMSString 34556===> "+sendSMSString);
//	        	}
             
             if(StaticStore.tagType.equals("APDB")){
            	 StaticStore.tagType = "";
                 sendSMSString   =   sendSMSString.substring(0,sendSMSString.length() - 1) +";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.midlet.selectedAccNumberForDepositAccBalSave)+seperator+ StaticStore.date;// By ABINAYA.J.A time;//new Date().getTime()
             }else if(StaticStore.tagType.equals("APLE")){
            	 StaticStore.tagType = "";
                 sendSMSString   =   sendSMSString.substring(0,sendSMSString.length() - 1) +";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.midlet.selectedAccNumberForLoanBalSave)+seperator+ StaticStore.date;// By ABINAYA.J.A time;//new Date().getTime()
             }else{
                 sendSMSString   =   sendSMSString.substring(0,sendSMSString.length() - 1) +";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+seperator+ StaticStore.date;// By ABINAYA.J.A time;//new Date().getTime()
             }
            	 
//            sendSMSString   =   sendSMSString.substring(0,sendSMSString.length() - 1) +";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+seperator+ StaticStore.date;// By ABINAYA.J.A time;//new Date().getTime()
          //  sendSMSString  = sendSMSString.replace('@',':');
            sendSMSString  = sendSMSString.replace("@","3EM*#L9");
            sendSMSString  = sendSMSString.replace('_','%');            
            StaticStore.LogPrinter('i',">>>>>>>"+sendSMSString);
            StaticStore.LogPrinter('i',">>>>>>>>>>>>going from 2nd");
            
            //LOADING Black Screen fix part 
//            if((StaticStore.FromListScreen || StaticStore.index == 15|| StaticStore.index == 16 || StaticStore.index == 100 || StaticStore.index == 102 
//            		|| StaticStore.index == 40|| StaticStore.index == 46|| StaticStore.index == 47|| StaticStore.index == 49 ||
//            		(StaticStore.index == 220 && StaticStore.tagType =="APMO")||StaticStore.index == 177||StaticStore.index == 178
//            		||StaticStore.index == 210) && StaticStore.index != 58 && StaticStore.index != 221)
//            StaticStore.LogPrinter('i'," ###################### validation.length -----> "+validation.length +"Val -->"+Arrays.deepToString(validation));
            StaticStore.LogPrinter('i',"StaticStore.fromAccountList ==>"+StaticStore.fromAccountList);
            StaticStore.LogPrinter('i',"StaticStore.index ==>"+StaticStore.index);
            StaticStore.LogPrinter('i',"StaticStore.FromListScreen ==>"+StaticStore.FromListScreen);
            StaticStore.LogPrinter('i',"validation.length ==>"+validation.length);
            
//            if((StaticStore.FromListScreen || validation.length == 0) && StaticStore.index != 58 && StaticStore.index != 221)
//            {
          	  // finish();
          	  StaticStore.LogPrinter('i',"Finish Called From --> 2810");
//          	  StaticStore.FromListScreen = false;
          	  StaticStore.LogPrinter('i',"StaticStore.FromListScreen Came in");
//            }
//            if(StaticStore.fromAccountList && (StaticStore.index == 58 || StaticStore.index == 221))
//            {
//            	finish();
//            	 StaticStore.LogPrinter('i',"fromAccountList StaticStore.FromListScreen Came in");
//            }
          
            if(!StaticStore.tagType.equals("APXX")){//recharge change
            if((!StaticStore.IsGPRS || StaticStore.index == 155 || StaticStore.index == 181) && StaticStore.index != 154){
                String smsNumber = "";
                if(StaticStore.isShortCode){
                    smsNumber = StaticStore.shortCodeNumber;
                }else{
                    smsNumber = StaticStore.VMN_Number;
                }
                //sendSMS         =   new SMSSend(smsNumber, sendSMSString);//navigate comment                
              //  sendSMS(smsNumber, sendSMSString);
                StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
	            midlet.writeinMemory(StaticStore.context);
	            StaticStore.airline1st = false;
	            StaticStore.LogPrinter('i',">>>>>>>>>>>"+sendSMSString);
	            StaticStore.LogPrinter('i',"<<<<<"+StaticStore.SentRequest);
	            
            }else{
                //if(StaticStore.httpObj == null)
                
//                sendSMSString = midlet.replaceSpace(sendSMSString,"$",':');
                StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
	            midlet.writeinMemory(StaticStore.context);
	            StaticStore.airline1st = false;
	            StaticStore.LogPrinter('i',">>>>>>>>>>>"+sendSMSString);
	            StaticStore.LogPrinter('i',"<<<<<<<<<<<<<<<"+StaticStore.SentRequest);
	            //test3
	            StaticStore.LogPrinter('i',"calling from sendGprs 3333 ");
	            sendGprs(sendSMSString);
              //navigate comment
                /*StaticStore.httpObj = null;
                //StaticStore.httpObj         =   new HttpConnect(StaticStore.GprsUrl + servlet, "1", midlet);             
//                 StaticStore.httpObj         =   new HttpConnect("http://10.44.37.51:7101/MPAYPORTAL/servlet/MQSender?request="+StaticStore.myMobileNo + sendSMSString, "1", midlet);            
                StaticStore.httpObj             =   new HttpConnect(StaticStore.GprsUrl + StaticStore.servlet, "1", midlet);
                StaticStore.httpObj.httpSent    =   false;
                StaticStore.httpObj.httpNotSent =   false;
                StaticStore.httpObj.setReqMessage(StaticStore.myMobileNo + sendSMSString);*/
            }
            //httpConnect       = new HttpConnect("http://10.44.3.25:7001/MBank/servlet/MQSender?request=BE%201144",false,midlet);        
            /*thread          =   new Thread(StaticStore.context);
            thread.start();*/
        }else{
        	 StaticStore.LogPrinter('i',"APXX ===> called ");
            StaticStore.tagType = "" ;
             StaticStore.LogPrinter('i',">>>>>came befor lists selection111111111111>>");   
            String category = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT);
            StaticStore.midlet.rechargeDynamicInputs.assignRechargeValues(category);
			StaticStore.midlet.rechargeCategory = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryName();
            StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getRechargeCategory(StaticStore.context));
        }
        }
    	}catch (Exception e) {
    		e.printStackTrace();
    		StaticStore.ToastDisplay(StaticStore.context,"Transaction Failure : In : Navigate To");
    	}
    }
     
    
    public static boolean isAirplaneModeOn(Context context){
    	   return Settings.System.getInt(context.getContentResolver
    	                        (),Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    	 }
    public void setLegValues() throws Exception {
    	
    	if(getActivity().getCurrentFocus()!=null) {
    		StaticStore.LogPrinter('e',"Keyboard hidden called ===> "+getActivity().getCurrentFocus());
    		InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);             
    		im.hideSoftInputFromWindow(ll.getApplicationWindowToken(), 0);
    	}

	    
	cal = Calendar.getInstance();
	
    	Boolean emptyToast = false;
	Boolean lengthToast = false;
	
	StaticStore.LogPrinter('i',"^^^^^^^^^^^^^^^^^"+Arrays.deepToString(label));
	
	alertbox = new AlertDialog.Builder(StaticStore.context);
	if(batteryInfoReceiver == null){
	 batteryInfoReceiver = new BroadcastReceiver(){ 
			@Override
		 public void onReceive(Context arg0, Intent intent) {
				if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				StaticStore.Batterylevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
					StaticStore.LogPrinter('i',"Battery level  ---> "+ StaticStore.Batterylevel);
				}
			}
		 }; 
		 StaticStore.context.registerReceiver(batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	for (int i = 0; i < label.length; i++) {
		if ((editText[i].getEditableText().toString().trim().equals("")) && (minStringLength[i] > 0)) {
		StaticStore.LogPrinter('i',"**********main validation**************");
		
			if((label[i].equals("From") || label[i].equals("To")) && StaticStore.index == 9)
			{
				StaticStore.ToastDisplay(StaticStore.context,"Please enter " + label[i]+" Location");
				editText[i].requestFocus();
				emptyToast = true;
				break;
			}else{
				StaticStore.ToastDisplay(StaticStore.context,"Please enter "+trimlabel(label[i]));
    			editText[i].requestFocus();
    			emptyToast = true;
    			break;
			}
		}
	}
    	if (!emptyToast) {
    		 lengthToast = false;
    		for (int i = 0; i < label.length; i++) { 
    			if (editText[i].getEditableText().toString().length() < minStringLength[i]) {
    					StaticStore.ToastDisplay(StaticStore.context,trimlabel(label[i])+ " must be "+ minStringLength[i]+ " digits");
        				editText[i].requestFocus();
        				lengthToast = true;
        				break;
    			}    			
    			String ndValue = "";
    			//ndValue = validation[i].substring(4,6);
    			String temp = validation[i];		
    			int minlen = Integer.parseInt(temp.substring(0,temp.indexOf('-')).trim());
    			temp = temp.substring(temp.indexOf('-') + 1);	
    			int maxlen = Integer.parseInt(temp.substring(0,temp.indexOf('-')).trim());
    			temp = temp.substring(temp.indexOf('-') + 1);
    			ndValue = temp.substring(0,temp.indexOf('-'));
    			StaticStore.LogPrinter('i',"????????????????????ndValue minlen"+minlen);
    			StaticStore.LogPrinter('i',"????????????????????ndValue maxlen"+maxlen);
    			StaticStore.LogPrinter('i',"????????????????????ndValue ndValue"+ndValue);
    			if(ndValue.equals("ND")){

    				Double amntValidation;
    				try{
    					String ndTemp=editText[i].getText().toString().trim();
    					StaticStore.LogPrinter('i',"????????????????????editText[i].toString()"+editText[i].getText());
    					StaticStore.LogPrinter('i',"ndTemp.contains(.)  ==> "+ndTemp.contains("."));
    					StaticStore.LogPrinter('i',"label[i] ===> " +label[i]);
    					StaticStore.LogPrinter('i',"ndTemp.length() ===> " +ndTemp.length());
    					try{
//    						String[] dotscount = ndTemp.split("\\.");
    						
    						
    						int dotscount = 0;
    						for(int s=0;s<ndTemp.length();s++){
    							if(ndTemp.substring(s, s+1).equals(".")){
    								dotscount++;
    							}
    							
    						}
    						
    						StaticStore.LogPrinter('e',"dotscount ==> "+dotscount);    		           			 
    						StaticStore.LogPrinter('i',"ndTemp.indexOf ."+ndTemp.indexOf("."));    		           			 
    						if(ndTemp.startsWith("."))
    						{
    							StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
    							editText[i].requestFocus();
    							lengthToast = true;
    							break;
    						}else if(ndTemp.contains(".") == true && !(ndTemp.substring(ndTemp.indexOf(".")+1).length() == 2)|| dotscount > 1){ // && (ndTemp.indexOf(".") != (maxlen-2))
    							StaticStore.ToastDisplay(StaticStore.context,"Incorrect "+trimlabel(label[i])+"; only 2 decimal characters are allowed after dot");
    							editText[i].requestFocus();
    							lengthToast = true;
    							break;
    						}else if(ndTemp.contains(".") == false && ndTemp.length() > maxlen - 3 ){
    							StaticStore.ToastDisplay(StaticStore.context,"Incorrect "+trimlabel(label[i])+"; only 2 decimal characters are allowed after dot");
    							editText[i].requestFocus();
    							lengthToast = true;
    							break;
    						}
    						if(!(label[i].contains("Interest Rate") || label[i].contains("Processing Fee"))){
    							amntValidation = Double.parseDouble(ndTemp);
    							StaticStore.LogPrinter('i',"amntValidation  ==> "+amntValidation);
    							if(amntValidation < 1){
    								StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+ trimlabel(label[i]));
    								editText[i].requestFocus();
    								lengthToast = true;
    								break;
    							}
    						}
    					}catch (Exception e){
    						e.printStackTrace();
    						StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
    						editText[i].requestFocus();
    						lengthToast = true;
    						break;
    					}
    				}catch(Exception e){
    					e.printStackTrace();
    					StaticStore.ToastDisplay(StaticStore.context,"Please enter "+ trimlabel(label[i]));
    					editText[i].requestFocus();
    					lengthToast = true;
    					break;
    				}

    			}else if(ndValue.equals("N")){ //Siva for Mpin Zero Validation ND
    				if(StaticStore.index != 123 &&(label[i].contains("Amount") || label[i].equals(mPINString) ||
    						label[i].contains("Password") || label[i].contains("Cheque No")|| label[i].contains("Pincode")
    						||label[i].contains("Mobile No")||label[i].contains("Tickets")||label[i].contains("Ticket ID")
    						||label[i].contains("MMID")||label[i].contains("PIN")|| label[i].contains("Tenure")
    						|| label[i].contains("Cheque No")|| label[i].contains("Subscriber ID")))
    				{
						try{
    					Double amntValidation;
//    					if(label[i].equals("mPIN")) //
//    					{
//    						amntValidation = Double.parseDouble(editText[0].getText().toString()) + Double.parseDouble(editText[1].getText().toString())+
//    						Double.parseDouble(editText[2].getText().toString())+Double.parseDouble(editText[3].getText().toString());
//    						StaticStore.LogPrinter('i',"amntValidation ---> "+amntValidation);
//							StaticStore.LogPrinter('i'," mPin Validation Part --> "+label[i]);
//    						if(amntValidation < 1){
//								editText[0].setText("");
//								editText[1].setText("");
//								editText[2].setText("");
//								editText[3].setText("");
//								StaticStore.mpincount = 0;
//								editText[0].requestFocus();
////								Toast.makeText(StaticStore.context,"Please enter correct "+label[i],Toast.LENGTH_LONG).show();
//								StaticStore.ToastDisplay(StaticStore.context,"Please enter correct " +label[i]);
//								lengthToast = true;
//								break;
//							}
//
//    					}else{
    						amntValidation = Double.parseDouble(editText[i].getText().toString());	
    							if(amntValidation < 1){
    								StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
    								editText[i].requestFocus();
    								lengthToast = true;
    								break;
    							}else if(label[i].contains("Mobile No"))
    							{
    								if(editText[i].getText().toString().startsWith("0"))
    								{
    									lengthToast=true;
    									StaticStore.ToastDisplay(StaticStore.context,"Please enter correct " +trimlabel(label[i]));
    									break;
    								}
    							}

    							if((StaticStore.tagType.equals("AP3T") ||StaticStore.tagType.equals("AP4T") )&& label[i].contains("Amount")){
    								if((Integer.parseInt(editText[i].getText().toString()) < 1) || (Integer.parseInt(editText[i].getText().toString()) > 9999999)){
    									lengthToast=true;
    									StaticStore.ToastDisplay(StaticStore.context,"Please enter correct " +trimlabel(label[i]));
    									break;
    								} else if((StaticStore.tagType.equals("AP3T") 
    										|| StaticStore.tagType.equals("AP4T"))
    										&& label[i].toUpperCase().indexOf("Amount".toUpperCase()) != -1 
    										&& (Integer.parseInt(editText[i].getText().toString())>Integer.parseInt(StaticStore.midlet.rechargeMaxAmount) 
    												|| Integer.parseInt(editText[i].getText().toString())<Integer.parseInt(StaticStore.midlet.rechargeMinAmount))){
    									lengthToast=true;
    									StaticStore.ToastDisplay(StaticStore.context,"Please enter amount between " + StaticStore.midlet.rechargeMinAmount+" to "+StaticStore.midlet.rechargeMaxAmount);
    									break;

    								}
    							}
//    							else if(label[i].contains("Amount")){
//
//    							}
//    					}
    						}catch(Exception e){
    							StaticStore.LogPrinter('i',"mpin Catch part >>>>>");
    							StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
    							editText[i].requestFocus();
    							lengthToast = true;
    							break;
    						}
					
    				}else if(StaticStore.tagType.equals("AP2F") && validation[i].equals("1-1-N-N-N") && (Integer.parseInt(editText[i].getText().toString()) < 1 || Integer.parseInt(editText[i].getText().toString()) > 5)){
    					lengthToast=true;
						StaticStore.ToastDisplay(StaticStore.context,"Please enter " + label[i] + " between 1 to 5");
						break;
    				}else if((label[i].contains("Beneficiary A/C No.") || label[i].contains("A/C No."))){
    					Double maxLength = Double.parseDouble(editText[i].getText().toString());
    					//Double maxLength = Double.parseDouble(editText[i].getText().toString().length());
    					if(maxLength < 1){
    						StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
    					if(editText[i].getText().toString().length() <1){
   							StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
    				}
    			}else if(ndValue.equals("AN")){
    			///SivaE-mail ID
    				if(StaticStore.index != 123 &&(label[i].contains("E-mail ID"))){
    					if(!isValidEmail(editText[i].getText().toString().trim()))
    					{
    					   					StaticStore.ToastDisplay(StaticStore.context,"Please enter correct E-mail ID (Ex:abc@xyz.com)");
    					   					editText[i].requestFocus();
    			    						lengthToast = true;
    			    						break;
    					}
    				}
    				if((label[i].contains("Beneficiary A/C No.") || label[i].contains("A/C No."))){
    					/*Double maxLength = Double.parseDouble(editText[i].getText().toString());
    					if(maxLength < 1){*/
    					if(editText[i].getText().toString().startsWith("00")){
    						StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
    					if(editText[i].getText().toString().length() <1){
   							StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
    				}
    			}else if(ndValue.equals("ANW")){
    			///Siva Beneficiary A/C No TID: 
    				if((label[i].contains("Beneficiary A/C No.") || label[i].contains("A/C No."))){
    					StaticStore.LogPrinter('i',"String ANW Value --> "+editText[i].getText().toString());
    					
    				//	Double maxLength = Double.parseDouble(editText[i].getText().toString());
    					if(editText[i].getText().toString().contains(" ")){
    					   	 StaticStore.ToastDisplay(StaticStore.context,"Please enter "+trimlabel(label[i])+" without space.");
    					   	 editText[i].requestFocus();
    			    		 lengthToast = true;
    			    		 break;
    					}
    					if(editText[i].getText().toString().length() <1){
   							StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
     					if(editText[i].getText().toString().startsWith("00")){
    						StaticStore.ToastDisplay(StaticStore.context,"Please enter correct "+trimlabel(label[i]));
							editText[i].requestFocus();
							lengthToast = true;
							break;
    					}
    				}
    			}
    		}
    		
    		if (!lengthToast) {
    			StaticStore.LogPrinter('i',"Came into the empty -- >");
    			if(StaticStore.Batterylevel != 0 && StaticStore.Batterylevel <= 10 && StaticStore.BatteryAlertFlag)
    			{
//    				StaticStore.ToastDisplay(StaticStore.context,"Battery Level is Low = "+StaticStore.Batterylevel);
    				exitMIDlet(new AlertDialog.Builder(StaticStore.context),
    						"Battery is low ("+StaticStore.Batterylevel+" %); do you want to proceed ?", 100,StaticStore.context).show();
    				BatterysentReq = true;
    				StaticStore.BatteryAlertFlag = false ;
					lengthToast = true;
    			}
    			if(isAirplaneModeOn(StaticStore.context))
    			{
    				StaticStore.ToastDisplay(StaticStore.context,"Sorry! Please turn off the airplane mode to proceed...");
					lengthToast = true;
    			}
    	 if(StaticStore.index == 33)//mobile recharge
			{
    		 String temp=editText[1].getEditableText().toString();
    		 			
    		 if((Integer.parseInt(temp)> Integer.parseInt( StaticStore.midlet.operatorList[StaticStore.selectedIndex][3]))){
					StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount lessthan or equal to Maximum Amount.");
					lengthToast = true;
				}else if((Integer.parseInt(temp)< Integer.parseInt(StaticStore.midlet.operatorList[StaticStore.selectedIndex][2]))){
					StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greaterthan or equal to Minimum Amount.");
					lengthToast = true;
				}
			}	
    	 
    	    if((StaticStore.index == 220 && StaticStore.tagType == "APB6")){
       		 if(StaticStore.menuDesc[220][4].equals("P")){ 
       			 String temp=editText[0].getEditableText().toString();
       		     if(temp.lastIndexOf('.') == -1){
                        if ((Integer.parseInt(temp) > Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
                   	     StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount less than " + StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.')));
                   	     lengthToast = true;
                        } else if ((Integer.parseInt(temp) == Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
                       	 StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount less than " + StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.')));
                       	 lengthToast = true;
                        } else if ((Integer.parseInt(temp) < Integer.parseInt(StaticStore.midlet.billpayBills[0][6].substring(0, StaticStore.midlet.billpayBills[0][6].lastIndexOf('.'))))) {
                       	 StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][6].substring(0, StaticStore.midlet.billpayBills[0][6].lastIndexOf('.')));
                       	 lengthToast = true;
                        } else if ((Integer.parseInt(temp) == Integer.parseInt(StaticStore.midlet.billpayBills[0][6].substring(0, StaticStore.midlet.billpayBills[0][6].lastIndexOf('.'))))) {
                       	 StaticStore.ToastDisplay(StaticStore.context, "Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][6].substring(0, StaticStore.midlet.billpayBills[0][6].lastIndexOf('.')));
                        	lengthToast = true;
                        }
                   }else if(temp.lastIndexOf('.') != -1){
       		    	if ((Integer.parseInt(temp.substring(0, temp.lastIndexOf('.'))) > Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
       		    		StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount less than " + StaticStore.midlet.billpayBills[0][2]);
       		    		lengthToast = true;
       		    	}else if ((Integer.parseInt(temp.substring(0, temp.lastIndexOf('.'))) == Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
       		    		StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount less than " + StaticStore.midlet.billpayBills[0][2]);
       		    		lengthToast = true;
       		    	} else if ((Integer.parseInt(temp.substring(0, temp.lastIndexOf('.'))) < Integer.parseInt(StaticStore.midlet.billpayBills[0][6].substring(0, StaticStore.midlet.billpayBills[0][6].lastIndexOf('.'))))) {
       		    		StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][6]);
       		    		lengthToast = true;
       		    	} else if ((temp.equals(StaticStore.midlet.billpayBills[0][6]))) {
       		    		StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][6]);
       		    		lengthToast = true;
       		    	}
                   }
             } else if(StaticStore.menuDesc[220][4].equals("E")){
           	  String temp=editText[0].getEditableText().toString();
           	  if(temp.lastIndexOf('.') == -1){
           		  if((Integer.parseInt(temp) == Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
           			  StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.')));
           			  lengthToast = true;
           		  }else if((Integer.parseInt(temp)< Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
           			  StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.')));
           			  lengthToast = true;
           		  }
           	  } else if(temp.lastIndexOf('.') != -1){
           		  if((temp.equals(StaticStore.midlet.billpayBills[0][2]))){
           			  StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][2]);
           			  lengthToast = true;
           		  }else if((Integer.parseInt(temp.substring(0, temp.lastIndexOf('.')))< Integer.parseInt(StaticStore.midlet.billpayBills[0][2].substring(0, StaticStore.midlet.billpayBills[0][2].lastIndexOf('.'))))) {
           			  StaticStore.ToastDisplay(StaticStore.context,"Please enter Amount greater than " + StaticStore.midlet.billpayBills[0][2]);
           			  lengthToast = true;
           		  }
           	  }
             }
          }
    	 
    	    if(((StaticStore.index == 220 || StaticStore.index == 221) && StaticStore.tagType == "APDO")){
    	    	if(StaticStore.menuDesc[220][3].equals("17")){
    	    		String field1=editText[1].getEditableText().toString();
        	    	String field2=editText[2].getEditableText().toString();
    	    		if((Integer.parseInt(field1) < 100) || (Integer.parseInt(field1) > 500000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Installment Amount should be in the range of Rs.100 and Rs.5,00,000");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field1)% 100 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Installment Amount must be multiples of Rs.100");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field2) < 12) || (Integer.parseInt(field2) > 120)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months should be in the range of 12 and 120 months");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field2)% 3 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months must be multiples of 3");
    	    			lengthToast = true;
    	    		}
    	    	}else if(StaticStore.menuDesc[220][3].equals("15")){
    	    		String field1=editText[1].getEditableText().toString();
    	    		if((Integer.parseInt(field1) < 5000) || (Integer.parseInt(field1) > 1000000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount should be in the range of Rs.5000 and Rs.10,00,000");
    	    			lengthToast = true;
    	    		}/*else if((Integer.parseInt(field1)% 100 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount must be multiples of Rs.100");
    	    			lengthToast = true;
    	    		}*/
    	    	}else if((StaticStore.menuDesc[220][3].equals("51")) || (StaticStore.menuDesc[220][3].equals("50"))){
    	    		String field1=editText[1].getEditableText().toString();
    	    		if((Integer.parseInt(field1) < 10000) || (Integer.parseInt(field1) > 500000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount should be in the range of Rs.10,000 and Rs.5,00,000");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field1)% 1000 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount must be multiples of Rs.1,000");
    	    			lengthToast = true;
    	    		}
    	    	}else if(StaticStore.menuDesc[220][3].equals("16")){
    	    		String field1=editText[1].getEditableText().toString();
        	    	String field2=editText[2].getEditableText().toString();
        	 		if((Integer.parseInt(field1) < 5000) || (Integer.parseInt(field1) > 1000000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount should be in the range of Rs.5000 and Rs.10,00,000");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field1)% 100 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount must be multiples of Rs.100");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field2) < 12) || (Integer.parseInt(field2) > 120)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months should be in the range of 12 and 120 months");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field2)% 3 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"Deposit Period in Months must be multiples of 3");
    	    			lengthToast = true;
    	    		}
    	    	}else if(StaticStore.menuDesc[220][3].equals("53")){
    	    		String field1=editText[1].getEditableText().toString();
        	    	if((Integer.parseInt(field1) < 5000) || (Integer.parseInt(field1) > 500000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount should be in the range of Rs.5,000 and Rs.5,00,000");
    	    			lengthToast = true;
    	    		}/*else if((Integer.parseInt(field1)% 100 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount must be multiples of Rs.100");
    	    			lengthToast = true;
    	    		}*/
    	    	}else if(StaticStore.menuDesc[220][3].equals("52")){
    	    		String field1=editText[1].getEditableText().toString();
        	    	if((Integer.parseInt(field1) < 5000) || (Integer.parseInt(field1) > 1000000)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount should be in the range of Rs.5,000 and Rs.10,00,000");
    	    			lengthToast = true;
    	    		}else if((Integer.parseInt(field1)% 1000 != 0)){
    	    			StaticStore.ToastDisplay(StaticStore.context,"The Deposit Amount must be multiples of Rs.1,000");
    	    			lengthToast = true;
    	    		}
    	    	}
    	    	
    	    }
    	    
			}
    		if (!lengthToast) {
    			if (StaticStore.isAirline) {
    				if(StaticStore.indexAir == 1000){
    					StaticStore.airValues[2] = editText[0].getEditableText().toString();
    					StaticStore.airValues[3] = editText[1].getEditableText().toString();	
    					StaticStore.indexAir = 0;
                        new AirlineInput(true);
                        Intent myIntent = midlet.getAirTitle(StaticStore.context);
        				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
    				}else{				
    					StaticStore.LogPrinter('i',">>>>>>>>>> Air "+StaticStore.indexAir+"airTotalTickets>>>>>"+Integer.parseInt(midlet.airTotalTickets.trim()));
    					 StaticStore.passValues[StaticStore.indexAir + 3] = editText[0].getEditableText().toString();
    					 StaticStore.passValues[StaticStore.indexAir + 4] = editText[1].getEditableText().toString();
    					 StaticStore.passValues[StaticStore.indexAir + 1] = editText[2].getEditableText().toString();
    					StaticStore.indexAir += 6;
    					StaticStore.LogPrinter('i',">>>>>>>>>>"+StaticStore.indexAir+"airTotalTickets>>>>>"+Integer.parseInt(midlet.airTotalTickets.trim()));
    					if(StaticStore.indexAir  < (6 * Integer.parseInt(midlet.airTotalTickets.trim()))){
                        new AirlineInput(true);
                        Intent myIntent = midlet.getAirTitle(StaticStore.context);
        				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
    					}else{
    					sendAirlineSMS();
    					}
    				}
    			} else {
    				
    				if(StaticStore.index == 209){
    					StaticStore.fdAmount = editText[0].getEditableText().toString();
    				}
    				if(StaticStore.index == 217){
    					StaticStore.legValue[0] = editText[0].getEditableText().toString();
    					StaticStore.cpMPin = editText[0].getEditableText().toString();
    					StaticStore.instrument = Integer.parseInt(editText[1].getEditableText().toString());
    				}else if(StaticStore.index == 218){
    				StaticStore.fdMPin = editText[0].getEditableText().toString();
    				StaticStore.menuDesc[209][2] = editText[0].getEditableText().toString();
    				StaticStore.legValue[0] = editText[0].getEditableText().toString();
    				}else if(StaticStore.index == 219){
    				StaticStore.fdNoOfDays = editText[0].getEditableText().toString();	
    				}
    				else{    	
    					for (int i = 0; i < validIndex.length; i++) {
    						StaticStore.LogPrinter('i',">>>>>>>>>>>>validIndex[i]"+validIndex[i]+"<<<<<<<<<<<<label[i]");
    					}
    				for (int i = 0; i < validIndex.length; i++) {
    					if(StaticStore.index == 199){
                             StaticStore.legValue[0] = StaticStore.tempMPin;
                             StaticStore.legValue[1] = StaticStore.instrument + "";
                             StaticStore.encyptionFlag[0] = true;
                             StaticStore.LogPrinter('i',">>>>>inside if>>>>>StaticStore.legValue[0]"+StaticStore.legValue[0]);
                             StaticStore.LogPrinter('i',">>>>>inside if>>>>>StaticStore.legValue[1]"+StaticStore.legValue[1]);
        					if(label[i].toUpperCase().contains("date".toUpperCase())){
        						StaticStore.legValue[validIndex[i] + 1] = editText[i].getEditableText().toString().substring(0,2) + "/" + editText[i].getEditableText().toString().substring(2,4) + "/" + editText[i].getEditableText().toString().substring(4);
        					}else{
        					StaticStore.legValue[validIndex[i] + 1] = editText[i].getEditableText().toString();
        					}
        					StaticStore.LogPrinter('i',">>>>>>validIndex[i] " + validIndex[i] + ">>" + StaticStore.legValue[validIndex[i]]);
        				
//    					}else if (StaticStore.index == 58){ // && label[0].startsWith(mPIN)
//							String str = "";
//							for(int p = 0;p<4;p++)
//							{
//								str +=editText[p].getEditableText().toString();
////								   StaticStore.LogPrinter('i',"String -->"+str);
//								StaticStore.legValue[0] = str;
//							}
						}else{
    					if((label[i].toUpperCase().contains("date".toUpperCase())&& (editText[i].getEditableText().toString().length() >= 6) ) && !(label[i].equals("Account Opening Date"))){
//    						if(StaticStore.index == 27 && validation[i].startsWith("0")){
//    							StaticStore.legValue[validIndex[i]] = "";
//    						}else{
    							StaticStore.legValue[validIndex[i]] = editText[i].getEditableText().toString().substring(0,2) + "/" + editText[i].getEditableText().toString().substring(2,4) + "/" + editText[i].getEditableText().toString().substring(4);
//    						}
//    					}else if(label[i].equals("Account Opening Date")){
//    						String data =  editText[i].getEditableText().toString();
//    						data =	data.replace('-', '/');
//    						StaticStore.legValue[validIndex[i]] = data;
    					}else{
    					StaticStore.legValue[validIndex[i]] = editText[i].getEditableText().toString().trim();
    					}
    					StaticStore.LogPrinter('i',">>>>>>validIndex[i] " + validIndex[i] + ">>" + StaticStore.legValue[validIndex[i]]);
    				} 
    				}
    				}
    				
    				if(StaticStore.index == 220 && StaticStore.tagType.equals("APSU") && !StaticStore.initialFlag)
    				{
    					StaticStore.FromListScreen = false;
    		StaticStore.encyptionFlag[0]=true;
    		StaticStore.encyptionFlag[1]=true;
    		StaticStore.encyptionFlag[2]=true;
    		StaticStore.encyptionFlag[3]=false;
    		StaticStore.encyptionFlag[4]=true;
    				 //    StaticStore.LogPrinter('i',"********StaticStore.index**********"+StaticStore.index);
    					StaticStore.index=220;
    					//    StaticStore.LogPrinter('i',"********to assign dob/pan**********");
    					if(StaticStore.dynamicTempSignup[6].equals("N"))
    					{
    						StaticStore.dynamicTempSignup[5]=editText[0].getEditableText().toString();
    				
    					}
    					else
    					{
    						StaticStore.dynamicTempSignup[6]=editText[0].getEditableText().toString();
    				
    					}
    					//    StaticStore.LogPrinter('i',"********to assign dynamicTempSignup**********");
    					for(i=0;i<StaticStore.dynamicTempSignup.length;i++)
    					{
    						StaticStore.legValue[i]=StaticStore.dynamicTempSignup[i];
    						
    					}
    					StaticStore.dynamicTempSignup[5]="";
    					StaticStore.dynamicTempSignup[6]="";
    				}
    				
//    				if(!StaticStore.tagType.equals("APXX")){//recharge change
//    		           StaticStore.LogPrinter('i',">>>>config if");
    				navigateTo(StaticStore.index);
//    				}
    			}
    		}
    	}


    }
    


	
    private String trimlabel(String messageContent) {
    	//Siva G Label Fix
    	String labelfinal ="";
    	 StaticStore.LogPrinter('i',"messageContent == > "+messageContent);
    	if(messageContent.contains("(")){
    		if(messageContent.length()-1 == messageContent.lastIndexOf(')')){
    			labelfinal = messageContent.substring(0, messageContent.lastIndexOf('('));
    			//    			messageContent = messageContent.substring(messageContent.lastIndexOf(')')+1);
    			return (labelfinal.trim().toUpperCase().startsWith("ENTER")?labelfinal.trim().substring(6):labelfinal);
    		}else{
    			return messageContent;
    		}
    	}else{
    		labelfinal = messageContent;
    		return labelfinal.trim().toUpperCase().startsWith("ENTER")?labelfinal.trim().substring(6):labelfinal;
    	}
    }


	public boolean isValidEmail(String email)
	{ //Siva E-Mail Validation 
	    boolean isValidEmail = false;

	    String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches())
	    {
	        isValidEmail = true;
	    }
	    return isValidEmail;
	}
    public void sendGprs(String sendSMSString){
    	if(StaticStore.Logwrite){
    	//	StaticStore.generateLogsOnSDcard(StaticStore.context,sendSMSString); //Commented by ME on 4-Feb-2015 for Malware Issue //
    		}
    	if(StaticStore.fromFdDaysToAmount){
    		StaticStore.indexCtr -= 5;
    	}
    	StaticStore.fromCurrentSavingList = false;
    	StaticStore.fromChequePickup = false;
    	StaticStore.fromChequeList = false;
    	StaticStore.fromFdDisplayable = false;
    	StaticStore.fromFdDaysToAmount = false;
    //	StaticStore.fromFd1stList = false;
    	StaticStore.fdMPin = "";
    	StaticStore.fdNoOfDays = "";
    	StaticStore.fdAmount = "";
    	StaticStore.cpInstruments = "";
    	StaticStore.cpMPin = "";
    	if(StaticStore.index == 208){
    		StaticStore.indexCtr -= 2;
    	}else if(StaticStore.index == 199){
    		StaticStore.indexCtr -= 1;
    		
    	}
    	
//    	   if(StaticStore.isMpinRemoved){
    		   StaticStore.LogPrinter('i',"3444 StaticStore.transIndex ==> "+StaticStore.transIndex);
        		StaticStore.LogPrinter('i',"3444 count ==> "+count);
        		StaticStore.LogPrinter('i',"3517 isMoreClicked ==> "+StaticStore.isMoreClicked);
        		StaticStore.LogPrinter('i',"3517 moreFlag ==> "+StaticStore.midlet.moreFlag);
          		StaticStore.LogPrinter('i',"2919 StaticStore.menuDesc ==> "+Arrays.deepToString(StaticStore.menuDesc[StaticStore.transIndex]));
          		menuDescRestore();
          		 if(StaticStore.isMoreClicked && StaticStore.index == StaticStore.tempIndex){
    	              StaticStore.isMoreClicked = false;        
    	              StaticStore.menuDesc[StaticStore.index] = StaticStore.dynamicMenuTemp;
    	              StaticStore.dynamicMenuTemp = null;
    	          }else{
    	              StaticStore.isMoreClicked = false;
    	              StaticStore.dynamicMenuTemp = null;
    	          }
          		 
       StaticStore.LogPrinter('i',"DynamicCanvastest 3602 ==> "+DynamicCanvastest);   
       StaticStore.LogPrinter('i',"3432 count StaticStore.legValue ==> "+Arrays.deepToString(StaticStore.legValue));
       if(!StaticStore.onceListClicked){
    	   StaticStore.onceListClicked = true;
    	   Intent intent = new Intent(context, Loading.class);
    	   StaticStore.midlet.startFragment(context,intent);
       }
    	
    if((StaticStore.index == 220 || StaticStore.index == 58 )&& StaticStore.tagType.equalsIgnoreCase("APDO")){
    	String temp1 = sendSMSString.substring(5);
    	String temp2 = temp1.substring(0,temp1.indexOf(";"));
    	temp1 = temp1.substring(2);
    	if(temp2.equals("Y")){
    		temp1 = temp1.substring(0,(temp1.indexOf(";")+3));
    		temp1 = temp1.substring(temp1.indexOf(";")+1);
    	}else if(temp2.equals("N")){
    		temp1 = temp1.substring(0,temp1.indexOf(";"));
    	}
    	StaticStore.depositIDNegative = temp1;
    }
    
    
    	StaticStore.httpObj         =   new HttpConnect(sendSMSString,context); //StaticStore.context
    	
    }
    public void sendAirlineSMS() {	
    	
    	StaticStore.LogPrinter('i',"sendAirlineSMS Called -->");
        String sendSMSString;
        if(!StaticStore.isMpinAtLast && StaticStore.isMpinNeeded){
        	sendSMSString = "APAR;Y;";
        }else{
        	sendSMSString = "APAR;N;";
        }
        
        for(int i = 0;i<StaticStore.airValues.length;i++){
          StaticStore.airValues[i] = StaticStore.airValues[i].equals("")?" ":StaticStore.airValues[i];
            
            if(i == 1){
                sendSMSString += StaticStore.airlineID + ";";
            }
            if(i == 0){
//                // for removing mPIN in all modules - 10-09-2009
            	if(!StaticStore.isMpinAtLast && StaticStore.isMpinNeeded){
                StaticStore.airValues[0] = StaticStore.globalMPIN;
                RsaEncryption objRSA = new RsaEncryption(StaticStore.airValues[0]);
                sendSMSString += objRSA.cipherText + ";";
            	}
            	
            }else{
                sendSMSString +=  StaticStore.airValues[i] + ";";
            }
        }
        
        for(int i = 0;i < StaticStore.passValues.length;i++){
            StaticStore.passValues[i] = StaticStore.passValues[i].equals("")?" ":StaticStore.passValues[i];
            if(((i + 1) % 6) == 0){
                sendSMSString += StaticStore.passValues[i] + "~";
            }else{
                sendSMSString += StaticStore.passValues[i] + ":";
            }
        }
       // sendSMSString = sendSMSString.substring(0,sendSMSString.length() - 1) + ";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+";"+new Date().getTime();; //comment for use
        
        sendSMSString = sendSMSString.substring(0,sendSMSString.length() - 1) + ";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+(midlet.isPreLoginTransaction(sendSMSString.substring(2,4))?"0":StaticStore.selectedAccNumber)+";"+StaticStore.date;// By ABINAYA.J.A time; //comment for use
      //  sendSMSString  = sendSMSString.replace('@',':');
        sendSMSString  = sendSMSString.replace("@","3EM*#L9");
        sendSMSString  = sendSMSString.replace('_','%');
        StaticStore.LogPrinter('i',"SMS String:::"+sendSMSString);
        StaticStore.globalMPIN = StaticStore.airValues[0];
        if(!StaticStore.IsGPRS){
            String smsNumber = "";
            if(StaticStore.isShortCode){
                smsNumber = StaticStore.shortCodeNumber;
            }else{
                smsNumber = StaticStore.VMN_Number;
            }
           // sendSMS         =   new SMSSend(smsNumber, sendSMSString);//comment for use     
           // StaticStore.SentRequest = StaticStore.menuDesc[StaticStore.index][1].substring(2,4);
            StaticStore.SentRequest = "AR";
            midlet.writeinMemory(StaticStore.context);
             
        }else{
//            sendSMSString = replaceSpace(sendSMSString,"%20",' ');
//            sendSMSString = midlet.replaceSpace(sendSMSString,"$",':');
            // if(StaticStore.httpObj == null)
            // StaticStore.httpObj         =   new HttpConnect(StaticStore.GprsUrl + servlet, "1", this);
//            StaticStore.httpObj         =   new HttpConnect("http://10.44.37.51:7101/MPAYPORTAL/servlet/MQSender?request="+StaticStore.myMobileNo + sendSMSString, "1", this);
//            StaticStore.httpObj         =   new HttpConnect(StaticStore.GprsUrl + "=" + StaticStore.myMobileNo + sendSMSString, "1", this);
//            
//            StaticStore.httpObj.httpSent    =   false;
//            StaticStore.httpObj.httpNotSent =   false;
//            StaticStore.httpObj.setReqMessage(StaticStore.myMobileNo + sendSMSString);
          //comment for use
          /*  StaticStore.httpObj         =   new HttpConnect(StaticStore.GprsUrl + StaticStore.servlet, "1", this);
            StaticStore.httpObj.httpSent    =   false;
            StaticStore.httpObj.httpNotSent =   false;
            StaticStore.httpObj.setReqMessage(StaticStore.myMobileNo + sendSMSString);*/
            StaticStore.SentRequest = "AR";
            midlet.writeinMemory(StaticStore.context);
            //test4
            StaticStore.LogPrinter('i',"calling from sendGprs 4444 ");
            sendGprs(sendSMSString);
            
        }
        //httpConnect       = new HttpConnect("http://10.44.3.25:7001/MBank/servlet/MQSender?request=BE%201144",false,midlet);
      //  thread          =   new Thread(StaticStore.context);//comment for use
        //thread.start();
        StaticStore.indexAir = 1000;
    	//StaticStore.isAirline = false;
    	StaticStore.airlineTitle = "";
    	//StaticStore.airValues = null;
    	StaticStore.airValues[0] = "";
        StaticStore.airValues[1] = "";
        StaticStore.airValues[2] = "";
        StaticStore.airValues[3] = "";
        StaticStore.airValues[4] = "";
    }

    public void assignValues(int index){
    	try{
    		StaticStore.LogPrinter('i',"StaticStore.legValue.length -->"+StaticStore.legValue.length);
    		StaticStore.LogPrinter('i',"StaticStore.legValue -->"+Arrays.deepToString(StaticStore.legValue));
    		StaticStore.LogPrinter('i',"index -->"+index);
    		StaticStore.LogPrinter('i',"StaticStore.menuDesc[index] -->"+Arrays.deepToString(StaticStore.menuDesc[index]));
    		
    		for(int i = 0; i < StaticStore.legValue.length;i++){
    			if(StaticStore.legValue[i] == null || StaticStore.legValue[i].equalsIgnoreCase("null") 
    					|| StaticStore.legValue[i].trim().equals("")){
    				/*
    				 * Following statement is used to assigning menuDesc value if static store legvalue value is empty.
    				 * before that you should assign the all values in menu descripion array. And assign there encryptFlag to
    				 * show whether the field is need to encrypt or not.
    				 */
    				StaticStore.LogPrinter('i',">>>>>>>>>>>>>StaticStore.menuDesc[index][2+i]"+StaticStore.menuDesc[index][2+i]);
    				
    				if(StaticStore.menuDesc[index][2+i] != null && StaticStore.menuDesc[index][2+i].trim().equals(StaticStore.mPINString)){
    					StaticStore.menuDesc[index][2+i] = StaticStore.globalMPIN+StaticStore.encryptStr; //EnCrYpT
    				}
    				
    				int subIndex = 0;
    				
    				if(StaticStore.menuDesc[index][2+i] != null){
    					subIndex = StaticStore.menuDesc[index][2+i].indexOf(StaticStore.encryptStr);
    				}
    				
    				if( subIndex != -1 && subIndex != 0){
    					StaticStore.legValue[i]  = StaticStore.menuDesc[index][2+i].substring(0,subIndex);
    					StaticStore.encyptionFlag[i]   = true;
    				}else if(StaticStore.menuDesc[index][2+i+leg] != null && !StaticStore.menuDesc[index][2+i+leg].trim().equals("")){
    					StaticStore.legValue[i] = "";
    					StaticStore.encyptionFlag[i]  = false;
    				}else{
    					if(StaticStore.menuDesc[index][2+i] != null)
    						StaticStore.legValue[i] = StaticStore.menuDesc[index][2+i];
    					else
    						StaticStore.legValue[i] = " ";

    					StaticStore.encyptionFlag[i]  = false;
    				}
    			}
    		}
    	}catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
    	}
    }


    public  static void menuDescRestore() {
		// TODO Auto-generated method stub
    	try{
    	if(StaticStore.index != 220 && (StaticStore.isMpinRemoved || StaticStore.isMoreClicked)){ // StaticStore.index != 58&& StaticStore.index != 220// && StaticStore.isMpinAtLast  || StaticStore.isMpinAtLast && !StaticStore.isMoreClicked
    		StaticStore.LogPrinter('e',"Restore Index -- > "+StaticStore.index);
    		StaticStore.menuDesc[StaticStore.mPinBackupindex] = new String[StaticStore.mPinArray.length];
    		if(StaticStore.mPinArray.length > 5){
    		  for (int s = 0; s < StaticStore.mPinArray.length; s++) {
				   StaticStore.menuDesc[StaticStore.mPinBackupindex][s] = StaticStore.mPinArray[s];
				}
    		}
    		StaticStore.isMpinRemoved = false;
//    		StaticStore.menuDesc[StaticStore.index] = Arrays.copyOf(StaticStore.mPinArray, StaticStore.mPinArray.length);
    		StaticStore.LogPrinter('e',"Restore Index -- > "+Arrays.deepToString(StaticStore.mPinArray));
    		StaticStore.LogPrinter('e',"Restored data Index -- > "+Arrays.deepToString(StaticStore.menuDesc[StaticStore.mPinBackupindex]));
    		}
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
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
    					// new ListSelection().closeApplication();
    					if(BatterysentReq)
    	    			{
    	    				arg0.cancel();
    	    				StaticStore.LogPrinter('i',"setLegValues 3666 BatterysentReq");
    	    				try {
								setLegValues();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								StaticStore.LogPrinter('i',"Error setLegValues 3666 BatterysentReq");
								e.printStackTrace();
							}
    	    			}else{
    	    				Intent myIntent = StaticStore.midlet.getExitScreen(context);
    	    				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
    	    			}

    				}
    			});
    	// set a negative/no button and create a listener
    	alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
    		// do something when the button is clicked
    		public void onClick(DialogInterface arg0, int arg1) {
    			if(BatterysentReq)
    			{
    				Intent intent = new Intent(context,SplashScreen.class);
					StaticStore.LogPrinter('i',"???????"+context.getClass());
					intent.putExtra("EXIT", true);

					StaticStore.muTerminate=true;
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					StaticStore.midlet.startFragment(context,intent);

    			}
    			else
    			{
    				arg0.cancel();
    			}
    		}
    	});

    	return alertbox;
    }

    public boolean isInput() {
    	if(StaticStore.isAirline){
    		return true;
    	}else{
    		int numLabels;
//    		 if(StaticStore.index == 17 || StaticStore.index == 24 ){
//    			StaticStore.LogPrinter('i',">>>>>>>"+StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 2]);
//    			numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 2]);
//    		}else{
    			numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
//    		}
    	
    	
    	boolean flag = false;
    	for(int i = 2 + numLabels; i < 2 + (2 * numLabels);i++ ){
    		if(!StaticStore.menuDesc[StaticStore.index][i].equals("")){
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    	}
}
    
    public String removeSlashFromDate(String date){
    	String dateWS = "";
    	String temp = date;
    	for(int i = 0;i < 2;i++){
    	dateWS += temp.substring(0, temp.indexOf('/'));
    	temp = temp.substring(temp.indexOf('/') + 1);
    	}
    	dateWS += temp;
    	return dateWS;
    }
	

	 @Override
	public void onStop()
	    {
	    	try
	    	{
	    		if(sendBroadcastReceiver != null || deliveryBroadcastReceiver != null){
	    			getActivity().unregisterReceiver(sendBroadcastReceiver);
	    			getActivity().unregisterReceiver(deliveryBroadcastReceiver);
	    			
	    		}
	    		if(batteryInfoReceiver != null){
	    			getActivity().unregisterReceiver(batteryInfoReceiver);
	    		}
	    		//        StaticStore.LogPrinter('i',"ONSTop Called Dynamic can --> ");
	    	}catch (Exception e) {
				// TODO: handle exception
//	    		e.printStackTrace();
			}
	        super.onStop();
	    }
//	    
	@Override
	public void onDestroy() {
		try
    	{
			if(sendBroadcastReceiver!=  null || deliveryBroadcastReceiver !=  null){
				getActivity().unregisterReceiver(sendBroadcastReceiver);
				getActivity().unregisterReceiver(deliveryBroadcastReceiver);
				
    		}
    		if(batteryInfoReceiver != null){
    			getActivity().unregisterReceiver(batteryInfoReceiver);
	    		}
    		//        StaticStore.LogPrinter('i',"ONSTop Called Dynamic can --> ");
    	}catch (Exception e) {
			// TODO: handle exception
//    		e.printStackTrace();
		}
	super.onDestroy();
	}
    

	public static String onBackPressed(Context context2) {
		// TODO Auto-generated method stub
		StaticStore.forTNEBNote = false;
    	// TODO Auto-generated method stub
    	StaticStore.LogPrinter('i',"onKeyDown StaticStore.isInbox--> "+StaticStore.isInbox);
    	StaticStore.LogPrinter('i',"onKeyDown StaticStore.indexCtr--> "+StaticStore.indexCtr);
    	StaticStore.LogPrinter('i',"onKeyDown StaticStore.index--> "+StaticStore.index);
    	StaticStore.LogPrinter('i',"onKeyDown StaticStore.tagType--> "+StaticStore.tagType);
    	
    	Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context2);
		if(sessionflag){
    		menuDescRestore();
    	if(StaticStore.indexCtr == 1)
    	{
    		StaticStore.midlet.isImageTextList = true;
    	}else
    	{
    		StaticStore.midlet.isImageTextList = false;
    	}
    	/*if (keyCode == KeyEvent.KEYCODE_BACK && StaticStore.isFromLoginScreen) {
    		Intent myIntent = new Intent(StaticStore.context,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(StaticStore.context,myIntent);
        	return true;
    	}
    	else*/ if ( StaticStore.isAirline) {
    		if (StaticStore.indexAir == 1000) {
    			new AirlineInput();
    		} else {
    			new AirlineInput(true);
    		}
    		Intent myIntent = StaticStore.midlet.getAirTitle(context2);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
    	}else if (StaticStore.isMenuFromDashBoard) {	
    		StaticStore.midlet.startFragment(context2,new Intent(context2,FirstPageActivity.class));
    		return "true";
    	}else if (StaticStore.isInbox) {
    		StaticStore.midlet.startFragment(context2,new Intent(context2,GridScreenView.class));
    		return "true";    			
    	}else if ( StaticStore.index == 123) {
    		Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
    	}else if ( (StaticStore.IsPermitted && StaticStore.index == 220 && StaticStore.tagType.equals("APOV"))) {
    		Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
    	}else if ( (!StaticStore.IsPermitted && StaticStore.index == 220 && (StaticStore.tagType.equals("APOG") || StaticStore.tagType.equals("APBO")))) {
    		return "false";
    	}else if ( (!StaticStore.IsPermitted && StaticStore.index == 220 && StaticStore.tagType.equals("APOV"))) {
    		StaticStore.midlet.startFragment(context2,new Intent(StaticStore.midlet.initiateUserOption(context2)));
    		return "true";
    	}else if ( StaticStore.index == 206){
    			Intent intent = new Intent(context2,SplashScreen.class);
    			intent.putExtra("EXIT", true);
    			StaticStore.muTerminate=true;
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			StaticStore.midlet.startFragment(context2,intent);
    			return "true";
    		}else if( StaticStore.tagType.equalsIgnoreCase("APP2") && (StaticStore.index == 220 || StaticStore.index == 221)){
				StaticStore.midlet.startFragment(context2,new Intent(context2,GridScreenView.class));
				return "true"; 
			}else if ( StaticStore.index == 209 && StaticStore.fromFdDaysToAmount){
    			StaticStore.fromFdDaysToAmount = false; 
    			StaticStore.fdAmount = "";
    			StaticStore.index = 219;
    			StaticStore.midlet.startFragment(context2,new Intent(context2, DynamicCanvas.class));
    			return "true";
    		}else if ( StaticStore.index == 221 && !StaticStore.forBillerRegistration){
    			StaticStore.LogPrinter('i',"==221 StaticStore.indexCtr"+StaticStore.indexCtr);
    			StaticStore.LogPrinter('i',"==221 StaticStore.index"+StaticStore.index);
    			if(StaticStore.index == 33){
    				StaticStore.index = 32;
    			}
    			if(StaticStore.indexCtr >= 1)
    			{
    				StaticStore.LogPrinter('i',"from == 221 Getting Save List");
    				StaticStore.isBack = true;	    	
    				StaticStore.indexCtr -= 1;	    		
    				int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
    				int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
    				Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
    				StaticStore.midlet.startFragment(context2,myIntent);
    				return "true";
    			}else{
    				StaticStore.LogPrinter('i',"Getting Save List StaticStore.indexCtr < 1"+StaticStore.indexCtr);
//    					exit();
    					return "super"; //super.onKeyDown(keyCode, event);
    			}
    		}else if ( StaticStore.index == 58 && !StaticStore.forBillerRegistration){
    				// finish();
    			StaticStore.LogPrinter('i',"StaticStore.indexCtr"+StaticStore.indexCtr);
    			StaticStore.LogPrinter('i',"StaticStore.index"+StaticStore.index);
    			if(StaticStore.indexCtr >= 1)
    			{
    				StaticStore.LogPrinter('i',"from == 58 Getting Save List");
    				StaticStore.isBack = true;	    	
    				StaticStore.indexCtr -= 1;	    		
    				int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
    				int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
    				Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
    				StaticStore.midlet.startFragment(context2,myIntent);
    				return "true";
    			}else{
    				StaticStore.index = StaticStore.transIndex;
    				StaticStore.LogPrinter('i',"from == 58 Getting StaticStore.index ==>"+StaticStore.index);
    				if(StaticStore.index == 115)
    				{
//    					StaticStore.index = 123;
//    					StaticStore.midlet.startFragment(context2,new Intent(context2,DynamicCanvas.class));
//    		    		return true;  
    					Intent mPayIntent = new Intent(context2 ,GridScreenViewActivation.class);
    		    		StaticStore.midlet.startFragment(context2,mPayIntent);
    		    		return "true";
    				}else if(StaticStore.tagType.equals("AP1T")){
    					Intent mPayIntent = new Intent(context2 ,GridScreenView.class);
    		    		StaticStore.midlet.startFragment(context2,mPayIntent);
    		    		return "true";
    				}else
    				{
//    					exit();
    				return "super"; //super.onKeyDown(keyCode, event);
    				}
    			}
    		}
    		else if ( StaticStore.indexCtr > 0 && StaticStore.index != 113 && StaticStore.index != 216 
    				 && StaticStore.index != 199  && StaticStore.index != 151 
    				&& StaticStore.index != 118 && StaticStore.index != 129 && StaticStore.index != 205 && !StaticStore.forBillerRegistration){
    			if(StaticStore.index == 218){
    				StaticStore.fdMPin = "";
    			}else if(StaticStore.index == 219){
    				StaticStore.fdNoOfDays = "";
    			}else if(StaticStore.index == 209){
    				StaticStore.fdAmount = "";
    			}else if(StaticStore.index == 217){  
    				StaticStore.cpInstruments = ""; 
    				StaticStore.cpMPin = "";   
    			}  
    			//Siva restore fix 
    			if(StaticStore.tempIndex != 0)
    			{
    				StaticStore.LogPrinter('i',"StaticStore.isMoreClicked>>>>>>>> "+StaticStore.isMoreClicked);
    				StaticStore.index = StaticStore.tempIndex; //Siva 
    				StaticStore.tempIndex = 0;
    			}
    			StaticStore.isBack = true;	    	
    			StaticStore.indexCtr -= 1;	    		
    			StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>................."+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
    			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
    			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
    			Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
    			StaticStore.midlet.startFragment(context2,myIntent);
    			return "true";
    		}else if ( (StaticStore.index == 216 || StaticStore.index == 115)){ 
    			Intent mPayIntent = new Intent(context2 ,GridScreenViewActivation.class);
        		StaticStore.midlet.startFragment(context2,mPayIntent);
        		return "true";
    		}else if ( (StaticStore.IsPermitted && StaticStore.IsPersonalInfoGot && !StaticStore.enableHome && StaticStore.index == 114)){
    			Intent mPayIntent = new Intent(context2 ,GridScreenViewActivation.class);
        		StaticStore.midlet.startFragment(context2,mPayIntent);
        		return "true";
    		}else if ( (StaticStore.index == 114 && StaticStore.isForgotPassword) || StaticStore.index == 129 ){
    			Intent mPayIntent = new Intent(context2 ,GridScreenViewActivation.class);
        		StaticStore.midlet.startFragment(context2,mPayIntent);
        		return "true";
    		}else if ( StaticStore.index == 199){
    			StaticStore.index = 217;
    			return "super"; //super.onKeyDown(keyCode, event);	
    		}else if ( (StaticStore.index == 151  || StaticStore.index == 118)){ 
    			Intent myIntent = StaticStore.midlet.getSmsType(context2);
    			StaticStore.midlet.startFragment(context2,myIntent);
    			return "true";
    			//return super.onKeyDown(keyCode, event);	
    		}else if ( StaticStore.index == 205) {
//    			exit();
    			return "false";
    		}else if(StaticStore.forBillerRegistration){
    			Intent myIntent = StaticStore.midlet.getCategoryList(context2);
    			StaticStore.midlet.startFragment(context2,myIntent);
    			return "true";
    		}else{    			    	
    			StaticStore.LogPrinter('i',"B4 normal indent back..........");
    			StaticStore.fromAccountList = false;
//    			return super.onKeyDown(keyCode, event);
//    			exit();
    			return "false";
    		}
    }else
    {
    	StaticStore.midlet.getsessionTimeOut(context2);
    	return "true";
    }
    
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		 StaticStore.LogPrinter('i',"onDetach called here testtttt-->");
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
    
}

