package com.fss.tmb;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

/**
 * @author Siva G
 *
 */
public class ListSelection extends android.support.v4.app.Fragment {
	Context context;
	
	private String[] menuItem;
	private DynamicCanvas dc;
	private int selectedIndex;
	AlertDialog.Builder alertbox ;//= new AlertDialog.Builder(this);
	private boolean isEdit;
	public static int index;
	private boolean moreFlag = false;
	private boolean isMultiple;
	private static String heading = "";
	private String endingStr = "";
	private String disableMsg = "This service is temporarily unavailable. Inconvenience is regretted.";
	private String searchString;	
	private int selectedIndexBeforeSelect = 0;
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	/********end*********/
	private boolean isMore;
	private boolean isImageTextList;
	LinearLayout lLayout,llhead,tab;
	/** Called when the activity is first created. */
	private SelectedAdapter selectedAdapter;
	private String labelDetails;

	private String[] getValidAccount() {
		String menuItem[] = new String[StaticStore.totalAccounts];
		int j = 0;
		for(int i = 0; i < StaticStore.accountNumbers.length;i++){
			if(StaticStore.accountNumbers[i].equals("0")){
				continue;
			}else{
				menuItem[j++] = StaticStore.accountNumbers[i];
			}
		}

		return menuItem;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		 View rootView = inflater.inflate(R.layout.listview, container, false);
		 StaticStore.date = new Date().getTime();
		 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
		StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
		StaticStore.FromListScreen = true;
		 Bundle indexes = this.getArguments();
			index = indexes.getInt("listIndex");
			if(index == 229){
				labelDetails = indexes.getString("labelDetails");	
			}
			menuItem =  indexes.getStringArray("menuItem");
			heading = indexes.getString("listHeader");
			isMore = indexes.getBoolean("more");
			//Image List Start
			isImageTextList = indexes.getBoolean("isImageTextList");
			StaticStore.midlet.isImageTextList = isImageTextList;
			//		Image List End
			selectedIndexBeforeSelect = indexes.getInt("selectedIndex");
			
		StaticStore.LogPrinter('i',"Staticstore.indexctr -->>"+StaticStore.indexCtr);
		StaticStore.LogPrinter('i',"List sele index -->>"+index);
		TextView listHeading = (TextView)rootView.findViewById(R.id.listText);
		listHeading.setText(heading);
		if(StaticStore.istablet){
			listHeading.setPadding(10, 10, 10, 10);
		}else{
			listHeading.setPadding(5, 5, 5, 5);
		}
		listHeading.setTextSize((StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title));
		listHeading.setTextColor(Color.rgb(18, 56, 127));
		listHeading.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface));
		
		/*tabbar comp*/ 	    
		lLayout = (LinearLayout)rootView.findViewById(R.id.listTab);
		try {
			tab = StaticStore.Tabbar(tabBarListener,setListTabBar(),getActivity(),false);
			lLayout.addView(tab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		
		
		selectedAdapter = new SelectedAdapter(getActivity(),android.R.layout.simple_list_item_1,menuItem,heading);
		if(index == 16){
			if(StaticStore.IsGPRS){
				selectedAdapter.setSelectedPosition(0);
			}else{
				selectedAdapter.setSelectedPosition(1);
			}
		}else if(index == 15){
			if(StaticStore.withMemory){
				selectedAdapter.setSelectedPosition(0);
			}else{
				selectedAdapter.setSelectedPosition(1);
			}
		}
		else if(index == 81){
			int index	=	0;
			String[] strArray	= getValidAccount();

			for(int i = 0 ; i < strArray.length; i++) {
				if(StaticStore.selectedAccNumber.equals(strArray[i])) {
					index = i;
				}
			}

			selectedAdapter.setSelectedPosition(index);
		}
		else if(index == 103){
			int selectedPosition = 0;
			if(StaticStore.indexAir == 1000){
				//StaticStore.LogPrinter('i',">>>>>inside 1000"+StaticStore.airValues[1]);
				if(StaticStore.airValues[1].equals("Mr")){
					selectedPosition = 0;
				}else if(StaticStore.airValues[1].equals("Mrs")){
					selectedPosition = 1;
				}else if(StaticStore.airValues[1].equals("Miss")){
					selectedPosition = 2;
				}
			}else{
				//StaticStore.LogPrinter('i',">>>>>>>>inside others"+StaticStore.indexAir+">>>StaticStore.passValues"+StaticStore.passValues[StaticStore.indexAir + 2]);
				if(StaticStore.passValues[StaticStore.indexAir + 2].equals("Mr")){
					selectedPosition = 0;
				}else if(StaticStore.passValues[StaticStore.indexAir + 2].equals("Mrs")){
					selectedPosition = 1;
				}else if(StaticStore.passValues[StaticStore.indexAir + 2].equals("Miss")){
					selectedPosition = 2;
				}
			}
			selectedAdapter.setSelectedPosition(selectedPosition);
		}else if(index == 77){
			if(StaticStore.isShortCode){
				selectedAdapter.setSelectedPosition(0);
				selectedIndex=0;
			}else{
				selectedAdapter.setSelectedPosition(1);
				selectedIndex=1;
			}
		}
		else{
			selectedAdapter.setSelectedPosition(selectedIndexBeforeSelect);
		}
		selectedAdapter.setNotifyOnChange(true);   

		//doAction(StaticStore.listIndex, StaticStore.selectedIndex);

		ListView customList = (ListView)rootView.findViewById(R.id.listExample);
		if(StaticStore.NoteForAccSel){ 
			//Note Added Siva G 21-11-2013
			View footerView = inflater.inflate(R.layout.list_footer, null, false);
			customList.addFooterView(footerView,null,false);
			StaticStore.NoteForAccSel = false;
			TextView textView = (TextView)rootView.findViewById(R.id.footer_text);
			String Message = "Note: Please use 'Settings --> Account Fetch' option to sync new account numbers with TMB mConnect application";
			textView.setText(Message);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.rgb(18, 56, 127));
		}
//		customList.setDivider((new GradientDrawable(Orientation.RIGHT_LEFT, StaticStore.divider)));
//		//customList.setDivider(R.color.indusmenu);
		customList.setDividerHeight(5);

		customList.setCacheColorHint(Color.TRANSPARENT);
//		customList.setSelector((new GradientDrawable(Orientation.TOP_BOTTOM, StaticStore.colors)));
		customList.cancelLongPress();
		customList.setAdapter(selectedAdapter);
		

		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.header);
		

		customList.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView selectedPosition, View view,
					int position, long ID) {
				//				selectedAdapter = new SelectedAdapter(this,android.R.layout.simple_list_item_1,menuItem);

				//				StaticStore.selecedAdapterFlag = false;
				Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
				if(sessionflag){
					selectedAdapter.setSelectedPosition(position);
					StaticStore.selectedIndex = position;
					selectedIndex = position;
					if(StaticStore.onceListClicked){
						view.setClickable(false);
					}
					doAction(index,selectedIndex,false);
				}
			}
		});
		if(index == 42&&StaticStore.tagType.equals("APTF") && StaticStore.TF00NEFTAlert)
		{
			StaticStore.TF00NEFTAlert = false; 
			selectedAdapter.setSelectedPosition(1);
			StaticStore.selectedIndex = 1;
			selectedIndex = 1;
			doAction(42,1,false);
		}

  //<------ Font Style Part Start ---->
		
		final Typeface mFont = Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Typeface); 
		final ViewGroup mContainer = (ViewGroup)customList ; //contentgetRootView()
		ListSelection.setAppFont(mContainer, mFont,(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
		return rootView;
	}
	
	 @Override
	    public void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	 StaticStore.LogPrinter('e',"onPause() ListSelection");
	    	 StaticStore.midlet.onPauseCalled();
	    }
	    
	    @Override
	    public void onResume() {
	    	// TODO Auto-generated method stub
	    	super.onResume();
	    	StaticStore.LogPrinter('e',"onResume() ListSelection");
	    	StaticStore.midlet.onResumeCalled();
	    }
	@Override
	public void onDetach() {
	    super.onDetach();
	    StaticStore.LogPrinter('e',"onDetach() ListSelection");
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
		StaticStore.LogPrinter('i',"StaticStore.IsPersonalInfoGot --->"+StaticStore.IsPersonalInfoGot);
		StaticStore.LogPrinter('i',"StaticStore.IsPermitted --->"+StaticStore.IsPermitted);
		StaticStore.LogPrinter('i',"StaticStore.enableHome --->"+StaticStore.enableHome);
		if(!StaticStore.istablet){
			tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
		}else{
			if(!StaticStore.enableHome && StaticStore.IsPermitted){
				tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
			}
			if(StaticStore.enableHome && StaticStore.isDashBoard && StaticStore.istablet){
				tabBarSetter.put(6,  new Image(R.drawable.icon_home, "Dashboard"));
			}
		}
		if(index == 222 || index == 223){
			tabBarSetter.put(3, new Image(R.drawable.icon_more, "Beneficiary Sync"));
		}
		if (isMore) {
			tabBarSetter.put(1, new Image(R.drawable.icon_more, "More"));
		}else if(index == 77){
			tabBarSetter.put(2, new Image(R.drawable.icon_edit, "Edit"));
		}
		//about
		//tabBarSetter.put(3, new Image(R.drawable.icon_about, "About"));
		try
		{
			int id = getResources().getIdentifier("help_li_"+index,"string",getActivity().getPackageName());
			if(id!= 0){
				tabBarSetter.put(4, new Image(R.drawable.icon_help, "Help"));
				StaticStore.LogPrinter('i',"Help menu Add --> "+id);
			}
		}catch (Exception e) {
			// TODO: handle exception
			StaticStore.LogPrinter('i',"Sorry help error --> ");
		}
		tabBarSetter.put(5, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")));
		return tabBarSetter;
	}

	

	private OnClickListener tabBarListener = new OnClickListener() { 
		public void onClick(View v) {
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
			// do something
			if(sessionflag){
				if (v.getId() == 0 ) {
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getHome(getActivity()));
				} else if (v.getId() == 1) {
					StaticStore.dynamicMenuTemp = new String[StaticStore.menuDesc[StaticStore.index].length];
					StaticStore.isMoreClicked = true;
					StaticStore.tempIndex = StaticStore.index;
					for (int i = 0; i < StaticStore.dynamicMenuTemp.length; i++) {
						StaticStore.dynamicMenuTemp[i] = StaticStore.menuDesc[StaticStore.index][i];
					}
					moreFlag = true;
					doAction(index, selectedIndex,isMore);
				} else if ( v.getId() == 2) {
					if (selectedIndex == 0) {
						saveList();
						StaticStore.index = 151;
						StaticStore.selectedIndex=0;
						StaticStore.FromListScreen = false;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
					} else if (selectedIndex == 1) {
						saveList();
						StaticStore.selectedIndex=1;
						StaticStore.index = 118;
						StaticStore.FromListScreen = false;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
					}
				}else if (v.getId() == 3) {
					StaticStore.menuDesc[220] = new String [] {"Beneficiary Sync","AP8T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"1","","","","3","true","true","N"}; //4-4-N-Y-Y
					StaticStore.index = 220;
					StaticStore.tagType = "AP8T";
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}else if (v.getId() == 4) {
					Intent intent = new Intent(getActivity(),HelpScreen.class);
					intent.putExtra("title" , heading);
					intent.putExtra("index" , "li_"+index);
					StaticStore.midlet.startFragment(getActivity(),intent);
				}else if (v.getId() == 5) {
					exit();
				}else if (v.getId() == 6) {
					if(StaticStore.istablet){
						StaticStore.isDashBoardLeftSideView = true;
					}
					Intent myIntent = new Intent(StaticStore.context,FirstPageActivity.class);
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			}
		}
	};
	public void exit() {
		exitMIDlet(new AlertDialog.Builder(getActivity()),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,getActivity()).show();

	}
	/**
	 * direct the command to respective index and to perform its own
	 * functionality.
	 *
	 * @param menuIndex
	 *            - Index of particular menu
	 * @param selectedIndex
	 *            - Index of particular selected item.
	 */

	public void doAction(int index, int selectedIndex,boolean moreFlag) {
		try {
			if (index >= 1 && index <= 20) {
				doAction1To20(index, selectedIndex, moreFlag);
			} else if (index >= 21 && index <= 40) {
				doAction21To40(index, selectedIndex, moreFlag);
			} else if (index >= 41 && index <= 60) {
				doAction41To60(index,selectedIndex,moreFlag);
			} else if (index >= 61 && index <= 80) {
				doAction61To80(index,selectedIndex,moreFlag);
			} else if (index >= 81 && index <= 100) {
				doAction81To100(index, selectedIndex, moreFlag);
			} else if (index >= 101 && index <= 120) {
				doAction101To120(index, selectedIndex, moreFlag);
			} else if (index >= 121 && index <= 140) {
				doAction121To140(index, selectedIndex, moreFlag);
			} else if (index >= 141 && index <= 160) {
				doAction141To160(index, selectedIndex, moreFlag);
			}else if (index >= 161 && index <= 180) {
				doAction161To180(index, selectedIndex, moreFlag);
			}else if (index >= 181 && index <= 199 ) {
				doAction181To199(index, selectedIndex, moreFlag);
			}else if (index >= 200 && index <= 220) {
				doAction200To220(index, selectedIndex, moreFlag);
			}else if (index >= 221 && index <= 240) {
				doAction221To240(index, selectedIndex, moreFlag);
			}else if (index >= 241 && index <= 260) {
				doAction241To260(index, selectedIndex, moreFlag);
			}else if (index >= 261 && index <= 280) {
				doAction261To280(index, selectedIndex, moreFlag);
			}else if (index >= 281 && index <= 300) {
				doAction281To300(index, selectedIndex, moreFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void doAction1To20(int index, int selectedIndex, boolean moreFlag) {
		if (index == 1) {
				StaticStore.midlet.savelistinit(); //Siva G
				
			if (selectedIndex == 0) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getBillpayMenu(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
//			} else if (selectedIndex == 1) {
//				saveList();
//				if (StaticStore.airlineFlag) {
//					saveList();
//					Intent myIntent = StaticStore.midlet
//					.getAirlineMenu(getActivity());
//					StaticStore.midlet.startFragment(getActivity(),myIntent);
//				} else {
//					StaticStore.ToastDisplay(getActivity(), disableMsg);
//				}
//			} else if (selectedIndex == 2) {
//				if (StaticStore.movieFlag) {
//					StaticStore.midlet.movieLoadingMsg = "Fetching theater list, please wait for the response";
//					saveList();
//					StaticStore.midlet.listObject = new ListObject();
//					StaticStore.midlet.listObject.setIndex(160);
//					StaticStore.midlet.listObject.setTag("OT");
//					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
//					StaticStore.midlet.listObject.setMore(false);
//					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
////					String[] menuItem = this.getAccessibleAccArray("OT");
////					Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
////					mPayIntent.putExtra("listIndex", 160);
////					mPayIntent.putExtra("menuItem", menuItem);
////					mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
////					mPayIntent.putExtra("more", false);
////					StaticStore.midlet.startFragment(getActivity(),mPayIntent);
//
//				} else {
//					StaticStore.ToastDisplay(getActivity(), disableMsg);
//				}
//			} else if (selectedIndex == 1 && StaticStore.mobileRechargeFlag) {
////				Intent myIntent = StaticStore.midlet
////				.getMobileRecharge(getActivity());
////				StaticStore.midlet.startFragment(getActivity(),myIntent);
//				StaticStore.rechargeStatus = false;
//				StaticStore.midlet.moreFlag = false;
//				saveList();
//				StaticStore.midlet.listObject = new ListObject();
//				StaticStore.midlet.listObject.setIndex(161);
//				StaticStore.midlet.listObject.setTag("R5");
//				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
//				StaticStore.midlet.listObject.setMore(false);
//				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
////				String[] menuItem = this.getAccessibleAccArray("R5");
////				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
////				mPayIntent.putExtra("listIndex", 161);
////				mPayIntent.putExtra("menuItem", menuItem);
////				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
////				mPayIntent.putExtra("more", false);
////				StaticStore.midlet.startFragment(getActivity(),mPayIntent);
			} else if (selectedIndex == 1 && StaticStore.donationFlag) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(141);
				StaticStore.midlet.listObject.setTag("T1");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			} else if (selectedIndex == 2 && StaticStore.feesFlag) {
//				saveList();
//				Intent myIntent = StaticStore.midlet.getInstMenu(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
				
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(283);
				StaticStore.midlet.listObject.setTag("N1");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));

			} 
			/*else if (selectedIndex == 6) {
				//StaticStore.ToastDisplay(getActivity(), disableMsg);
//				saveList();
//				Intent myIntent = StaticStore.midlet
//				.getP2UMenus(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
				saveList();
				Intent myIntent = StaticStore.midlet.getICashMenu(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
				
			
			}*/
			/*else if (selectedIndex == 6 && StaticStore.shopingFlag) {
				saveList();
				String[] menuItem = getAccessibleAccArray("CP");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 142);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);
			}*/else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}

		}
		else if (index == 2) {

			if (selectedIndex == 0) {
				if (StaticStore.stopChequeFlag) {
					saveList();
					StaticStore.index = 43;
					StaticStore.menuDesc[43][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 1) {
				if (StaticStore.chequeStatusFlag) {
					saveList();
					StaticStore.index = 71;
					StaticStore.menuDesc[71][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 2) {
				if (StaticStore.blockCardFlag) {
					saveList();
					StaticStore.index = 72;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 3) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getLocatorMenu(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 4) {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}
		} else if (index == 3) {
			 StaticStore.midlet.savelistinit(); 
		     StaticStore.midlet.isUsedForBack = false;
			 if(selectedIndex == 0){
                    if (StaticStore.cardHotListFlag) {
						  saveList();
						  StaticStore.midlet.listObject = new ListObject();
						  StaticStore.midlet.listObject.setIndex(248);
						  StaticStore.midlet.listObject.setTag("HL");
						  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
						  StaticStore.midlet.listObject.setMore(false);
						  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
                    } else {
						  StaticStore.ToastDisplay(getActivity(), disableMsg);
					}
             }else if(selectedIndex == 1){
                    if (StaticStore.chequeStatusFlag) {
						  saveList();
						  StaticStore.midlet.listObject = new ListObject();
						  StaticStore.midlet.listObject.setIndex(133);
						  StaticStore.midlet.listObject.setTag("LW");
						  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
						  StaticStore.midlet.listObject.setMore(false);
						  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
                    } else {
						  StaticStore.ToastDisplay(getActivity(), disableMsg);
					}
             }else if(selectedIndex == 2){
                    if (StaticStore.stopChequeFlag) {
 					      saveList();
 					      StaticStore.midlet.listObject = new ListObject();
 					      StaticStore.midlet.listObject.setIndex(132);
 					      StaticStore.midlet.listObject.setTag("L4");
 					      StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
 					      StaticStore.midlet.listObject.setMore(false);
 					      StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
                    } else {
 					      StaticStore.ToastDisplay(getActivity(), disableMsg);
 				    }
             }else if(selectedIndex == 3){
            	    saveList();
				 	StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(181);
					StaticStore.midlet.listObject.setTag("L1");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			 }else if(selectedIndex == 4){
				    /*saveList();
					StaticStore.index = 48;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));*/
				 	saveList();
				 	StaticStore.midlet.listObject = new ListObject();
				 	StaticStore.midlet.listObject.setIndex(271);
				 	StaticStore.midlet.listObject.setTag("L9");
				 	StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				 	StaticStore.midlet.listObject.setMore(false);
				 	StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			 }else if(selectedIndex == 5){
				    saveList();
					StaticStore.index = 47;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			 }else if(selectedIndex == 6){
				    saveList();
				    StaticStore.menuDesc[220] = new String [] {"eConnect SignOn password","APIB;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
					StaticStore.index = 220;
					StaticStore.tagType = "APIB";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			 }else if(selectedIndex == 7){
				    saveList();
				    StaticStore.menuDesc[220] = new String [] {"eConnect Transaction Password","APBP;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
					StaticStore.index = 220;
					StaticStore.tagType = "APBP";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			 }else if(selectedIndex == 8){
				    /*saveList();
				    StaticStore.menuDesc[220] = new String [] {"Debit Card PIN","APDP;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
					StaticStore.index = 220;
					StaticStore.tagType = "APDP";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));*/
				 
				 	saveList();
				 	StaticStore.midlet.listObject = new ListObject();
				 	StaticStore.midlet.listObject.setIndex(272);
				 	StaticStore.midlet.listObject.setTag("DP");
				 	StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				 	StaticStore.midlet.listObject.setMore(false);
				 	StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));

			 }else if(selectedIndex == 9){
				    saveList();
					StaticStore.index = 46;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			 }	 
     } else if (index == 4) {


			if (selectedIndex == 0 && StaticStore.billpayFlag) {
				saveList();
				StaticStore.midlet.isAdhocPayment = false;
				Intent myIntent = StaticStore.midlet
				.getBillPaymentList(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 1 && StaticStore.adHocPaymentFlag) {
				saveList();
				StaticStore.midlet.isAdhocPayment = true;
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(169);
				StaticStore.midlet.listObject.setTag("B2");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			}else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}



		} 		else if (index == 5) {

			if (moreFlag) {
				if (!StaticStore.midlet.isAdhocPayment) {
					StaticStore.menuDesc[135][0] = "Add Biller";
				} else {
					StaticStore.menuDesc[135][0] = "Instant Bill Payment";
				}
				StaticStore.menuDesc[135][3] = StaticStore.midlet.billerCat;
				StaticStore.menuDesc[135][4] = StaticStore.midlet.billerState;
				StaticStore.menuDesc[135][5] = StaticStore.midlet.billerCity;
				StaticStore.menuDesc[135][7] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[135][6] = (!StaticStore.midlet.isAdhocPayment) ? "P"
						: "A";
				StaticStore.index = 135;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (!StaticStore.midlet.isAdhocPayment) {
					StaticStore.menuDesc[13][0] = "Add Biller";
					StaticStore.menuDesc[13][1] = "APB3;Y";
					StaticStore.menuDesc[13][4] = "P";
				} else {
					StaticStore.menuDesc[13][0] = "Instant Bill Payment";
					StaticStore.menuDesc[13][1] = "APY3;Y";
					StaticStore.menuDesc[13][4] = "A";
				}
				StaticStore.menuDesc[13][3] = StaticStore.midlet.unRegBillers[selectedIndex][0];
				StaticStore.index = 13;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
		} else if (index == 6) {
			if (selectedIndex == 0) {
				StaticStore.forAddBiller = true;
				StaticStore.menuDesc[12][0] = "Add Biller";
				StaticStore.menuDesc[12][3] = "P";
				StaticStore.index = 12;
				saveList();
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 1) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(148);
				StaticStore.midlet.listObject.setTag("BX");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			} else if (selectedIndex == 2) {
				saveList();
				StaticStore.index = 70;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
//			else if (selectedIndex == 3) {
//				StaticStore.recCount = 0;
//				saveList();
//				StaticStore.index = 207;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			}

		} else if (index == 7) {

			if (selectedIndex == 0) {
				if (StaticStore.midlet.unRegisteredCorpLists == null) {
					saveList();
					StaticStore.index = 56;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					saveList();
					Intent myIntent = StaticStore.midlet
					.getUnregCorporateList(getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			} else if (selectedIndex == 1) {
				if (StaticStore.midlet.allRegList == null) {
					StaticStore.menuDesc[76][0] = "Payment";
					StaticStore.menuDesc[76][3] = "P";
					saveList();
					StaticStore.index = 76;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					saveList();
					Intent myIntent = StaticStore.midlet
					.getRegisteredCFTList("Payment", "P",getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			} else if (selectedIndex == 2) {
				if (StaticStore.midlet.allRegList == null) {
					StaticStore.menuDesc[76][0] = "SI-Registration";
					StaticStore.menuDesc[76][3] = "S";
					saveList();
					StaticStore.index = 76;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					saveList();
					Intent myIntent = StaticStore.midlet
					.getRegisteredCFTList("SI-Registration", "S",getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			} else if (selectedIndex == 3) {
				if (StaticStore.midlet.allRegList == null) {
					StaticStore.menuDesc[76][0] = "Delete Beneficiary";
					StaticStore.menuDesc[76][3] = "D";
					saveList();
					StaticStore.index = 76;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					saveList();
					Intent myIntent = StaticStore.midlet
					.getRegisteredCFTList("Delete Beneficiary", "D",getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			} else if (selectedIndex == 4) {
				if (StaticStore.midlet.allRegList == null) {
					StaticStore.menuDesc[76][0] = "Forgot Password";
					StaticStore.menuDesc[76][3] = "F";
					saveList();
					StaticStore.index = 76;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					Intent myIntent = StaticStore.midlet
					.getRegisteredCFTList("Forgot Password", "F",getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			}

		} else if (index == 8) {
			 if (selectedIndex == 0) {
				  if(StaticStore.midlet.getFilledAccArray().length !=0){
					  if(StaticStore.midlet.getFilledAccArray().length == 1){
						  StaticStore.ToastDisplay(getActivity(),"You have only one account mapped to this mobile number. You cannot do the funds transfer.");
					  }else{
						  saveList();
						  StaticStore.midlet.listObject = new ListObject();
						  StaticStore.midlet.listObject.setIndex(273);
						  StaticStore.midlet.listObject.setTag("ST");
						  StaticStore.midlet.listObject.setHeading("Select From A/C");
						  StaticStore.midlet.listObject.setMore(false);
						  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
					  }
				  }else{
					  StaticStore.isFinancialAccSync = false;
					  StaticStore.isMyOwnAccSync = true;
				      StaticStore.tagType = "SY";
				      StaticStore.index = 188;
					  StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
				  }
			 } else if (selectedIndex == 1) {
				 saveList();
				 Intent myIntent = StaticStore.midlet.getFTOptions(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
			 } else if (selectedIndex == 2) {
				 saveList();
				 Intent myIntent = StaticStore.midlet.getOtherFTTypes(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
			 }
        } else if (index == 9) {
             if (selectedIndex == 0) {
				 if (StaticStore.m2mFlag) {
					  saveList();
					  Intent myIntent = StaticStore.midlet.getFTRegistrationMenu("Mobile-to-Mobile",getActivity());
					  StaticStore.midlet.startFragment(getActivity(),myIntent);
				 } else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				 }
             } else if (selectedIndex == 1) {
				 if (StaticStore.m2aFlag) {
					 saveList();
					 Intent myIntent = StaticStore.midlet.getFTRegistrationMenuM2A("Mobile-to-Account",getActivity());
					 StaticStore.midlet.startFragment(getActivity(),myIntent);
				 } else {
					 StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			}
       } else if (index == 10) {
             if (selectedIndex == 0) {
				  saveList();
				  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(173);
				  StaticStore.midlet.listObject.setTag("FT");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             }else if (selectedIndex == 1) {
				  saveList();
				  StaticStore.index = 0;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
             } else if (selectedIndex == 2) {
				  saveList();
				  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(164);
				  StaticStore.midlet.listObject.setTag("FT");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             } else if (selectedIndex == 3) {
				  saveList();
				  StaticStore.index = 186;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
             } else if (selectedIndex == 4) {
				  saveList();
				  StaticStore.index = 182;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
             }
		} else if (index == 11) {

			if (selectedIndex == 0) {
				if (StaticStore.m2mFlag) {
					saveList();
					StaticStore.index = 100;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 1) {
				if (StaticStore.m2aFlag) {
					saveList();
					StaticStore.index = 102;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 2) {
				saveList();
				StaticStore.index = 97;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 12) {
			StaticStore.midlet.savelistinit(); 
			if(!StaticStore.istablet && selectedIndex > 2){
				selectedIndex++;
			}
			if (selectedIndex == 0) {
				 saveList();
				 StaticStore.isUpdateClicked = false;
				 StaticStore.midlet.moreFlag = false;
				 if(StaticStore.midlet.getFilledAccArray().length!=0){
				      try {
						  String tempMsg = "";
						  String accArray[] = new String[StaticStore.midlet.getFilledAccArray().length];
						  accArray = StaticStore.midlet.getFilledAccArray();
						  for (int i = 0; i < accArray.length; i++) {
							  tempMsg += accArray[i] + ";";
						  }
						  Intent intent = new Intent(getActivity() , DisplayableView.class);
                    	  intent.putExtra("response",tempMsg);
                     	  intent.putExtra("formName", "AccDisplay");
                     	  StaticStore.midlet.startFragment(getActivity(),intent);
				      } catch (Exception e) {
					      e.printStackTrace();
				      }       
				  }else{
				      StaticStore.isAccSyncFromMySetup = true;
				      StaticStore.isUpdateClicked = true;
				      StaticStore.menuDesc[188][2] = "0001";
				      StaticStore.maxAccLengthFromResponse = 0;
				      StaticStore.index = 188;
				      StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }
			} else if (selectedIndex == 1) {
				  saveList();
				  Intent myIntent = StaticStore.midlet.getSaveinApplication(getActivity());
				  StaticStore.midlet.startFragment(getActivity(),myIntent);
			}  else if (selectedIndex == 2 ) {
				  saveList();
				  StaticStore.index = 138;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			} else if (selectedIndex == 3 ) {
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
            } else if (selectedIndex == 4 ) {
            	Intent intent = new Intent(getActivity(),HelpScreen.class);
				intent.putExtra("title" , "Contact Us");
				intent.putExtra("index" , "256" );
				StaticStore.midlet.startFragment(getActivity(),intent);
			} else if (selectedIndex == 5 ) {
				  saveList();
				  StaticStore.index = 127;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			} else if (selectedIndex == 6 ) {
				if(StaticStore.istablet){
					StaticStore.isDashBoardLeftSideView = true;
				}
				StaticStore.isDashBoard = true;
				StaticStore.midlet.writeinMemory(StaticStore.context);
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),FirstPageActivity.class));
			}
   } else if (index == 13) {

			//				if (isToggle) {
			//					setSelectedIndexes(selectedIndex);
			//				} else {
			//saveList();
//			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), EnableDisable.class));
			//				}

		} else if (index == 14) {
			saveList();
			StaticStore.index = 54;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			//				}

		} else if (index == 15) {

			StaticStore.withMemory = (selectedIndex == 0) ? true:false;
			StaticStore.midlet.writeinMemory(getActivity());
			StaticStore.ToastDisplay(getActivity(), "Your settings have been saved successfully");

		} else if (index == 16) {
			StaticStore.isSettingsSelected = true;
			if (selectedIndex == 0) {
				StaticStore.index = 154;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if(selectedIndex == 1){
				StaticStore.IsGPRS = false;
				StaticStore.midlet.writeinMemory(getActivity());
				StaticStore.ToastDisplay(getActivity(), "Your settings have been saved successfully");
			}

		} else if (index == 17) {

			if (moreFlag) {
				StaticStore.menuDesc[58][3] = StaticStore.menuDesc[56][3];
				StaticStore.menuDesc[58][4] = StaticStore.corpSearchString;
				StaticStore.menuDesc[58][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 58;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				/*
				 * if subcorporate flag exist then requesting for
				 * subcorporate search otherwise it go to corporate
				 * registration
				 */
				if (StaticStore.midlet.unRegisteredCorpLists[selectedIndex][1]
				                                                            .trim().toUpperCase().equals("Y")) {
					StaticStore.menuDesc[57][3] = StaticStore.midlet.unRegisteredCorpLists[selectedIndex][0];
					saveList();
					StaticStore.index = 57;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[60][3] = StaticStore.midlet.unRegisteredCorpLists[selectedIndex][0];
					saveList();
					StaticStore.index = 60;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 18) {

			if (moreFlag) {
				StaticStore.menuDesc[59][3] = StaticStore.midlet.unRegisteredSubCorpLists[selectedIndex][0];
				StaticStore.menuDesc[59][4] = StaticStore.menuDesc[57][4];
				StaticStore.menuDesc[59][5] = StaticStore.subCorpSearchString;
				StaticStore.menuDesc[59][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 59;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[61][3] = StaticStore.midlet.cftCorpId;
				StaticStore.menuDesc[61][5] = StaticStore.midlet.unRegisteredSubCorpLists[selectedIndex][0];
				saveList();
				StaticStore.index = 61;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 19) {
			//empty
			if (moreFlag) {
				//				if (StaticStore.midlet.objClsA.cftMenu != null) {
				//						// This assignment for same session response.
				//						if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 1) {
				//							StaticStore.menuDesc[132][0] = "Payment";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 2) {
				//							StaticStore.menuDesc[132][0] = "SI-Registration";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 3) {
				//							StaticStore.menuDesc[132][0] = "Dereigstration";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 4) {
				//							StaticStore.menuDesc[132][0] = "Forgot Password";
				//						}
				//		}
				//					else {
				//						// This assignment for late response.
				//						StaticStore.menuDesc[132][0] = StaticStore.midlet.cftHeader;
				//					}o
				//					StaticStore.menuDesc[132][3] = StaticStore.midlet.modeFlag;
				//					StaticStore.menuDesc[132][4] = StaticStore.midlet.displaySearchString;
				//					StaticStore.menuDesc[132][5] = StaticStore.midlet.nextStartRecNumber;
			}else {
				//					if (StaticStore.midlet.objClsA.cftMenu != null) {
				//						// This assignment for same session response.
				//						if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 1) {
				//							StaticStore.menuDesc[133][0] = "Payment";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 2) {
				//							StaticStore.menuDesc[133][0] = "SI-Registration";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 3) {
				//							StaticStore.menuDesc[133][0] = "Dereigstration";
				//						} else if (StaticStore.midlet.objClsA.cftMenu.selectedIndex == 4) {
				//							StaticStore.menuDesc[133][0] = "Forgot Password";
				//						}
				//					} else {
				//						// This assignment for late response.
				//						StaticStore.menuDesc[133][0] = StaticStore.midlet.cftHeader;
				//					}
				//					StaticStore.menuDesc[133][3] = StaticStore.midlet.modeFlag;
				//					StaticStore.menuDesc[133][4] = StaticStore.midlet.allRegList[selectedIndex][1];
				//					StaticStore.menuDesc[133][5] = StaticStore.midlet.allRegList[selectedIndex][0];
			}


		}
		else if (index == 20) {

			if (moreFlag) {
				StaticStore.menuDesc[133][0] = StaticStore.midlet.cftHeader;
				StaticStore.menuDesc[133][3] = StaticStore.midlet.modeFlag;
				StaticStore.menuDesc[133][4] = StaticStore.midlet.cftCorpId;
				StaticStore.menuDesc[133][5] = StaticStore.midlet.cftSubCorpId;
				StaticStore.menuDesc[133][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 133;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.modeFlag.equals("P")) {
					if (StaticStore.midlet.cftDistributorList[selectedIndex][2]
					                                                         .toUpperCase().trim().equals("Y")) {
						StaticStore.menuDesc[62][3] = StaticStore.midlet.txnID;
						StaticStore.menuDesc[62][4] = StaticStore.midlet.cftCorpId;
						StaticStore.menuDesc[62][5] = StaticStore.midlet.cftSubCorpId;
						StaticStore.menuDesc[62][7] = StaticStore.midlet.cftDistributorList[selectedIndex][0];
						StaticStore.index = 62;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
								DynamicCanvas.class));
					} else {
						StaticStore.menuDesc[63][3] = StaticStore.midlet.txnID;
						StaticStore.menuDesc[63][4] = StaticStore.midlet.cftCorpId;
						StaticStore.menuDesc[63][5] = StaticStore.midlet.cftSubCorpId;
						StaticStore.menuDesc[63][7] = StaticStore.midlet.cftDistributorList[selectedIndex][0];
						StaticStore.index = 63;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
								DynamicCanvas.class));
					}
				} else if (StaticStore.midlet.modeFlag.equals("S")) {
					StaticStore.menuDesc[64][3] = StaticStore.midlet.cftCorpId;
					StaticStore.menuDesc[64][4] = StaticStore.midlet.cftSubCorpId;
					StaticStore.menuDesc[64][7] = StaticStore.midlet.cftDistributorList[selectedIndex][0];
					StaticStore.index = 64;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (StaticStore.midlet.modeFlag.equals("D")) {
					StaticStore.menuDesc[65][3] = StaticStore.midlet.cftCorpId;
					StaticStore.menuDesc[65][4] = StaticStore.midlet.cftSubCorpId;
					StaticStore.menuDesc[65][5] = StaticStore.midlet.cftDistributorList[selectedIndex][0];
					StaticStore.index = 65;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (StaticStore.midlet.modeFlag.equals("F")) {
					StaticStore.menuDesc[66][3] = StaticStore.midlet.cftCorpId;
					StaticStore.menuDesc[66][4] = StaticStore.midlet.cftSubCorpId;
					StaticStore.menuDesc[66][5] = StaticStore.midlet.cftDistributorList[selectedIndex][0];
					StaticStore.index = 66;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}
		}
	}



	private void doAction21To40(int index, int selectedIndex, boolean moreFlag) {
		if (index == 21) {
			if (moreFlag) {
				StaticStore.menuDesc[28][9] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 28;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[29][3] = StaticStore.midlet.region;
				StaticStore.menuDesc[29][4] = StaticStore.midlet.language;
				StaticStore.menuDesc[29][5] = StaticStore.midlet.venue;
				StaticStore.menuDesc[29][6] = StaticStore.midlet.cinema;
				StaticStore.menuDesc[29][7] = StaticStore.midlet.noOfTickets;
				StaticStore.menuDesc[29][8] = StaticStore.midlet.date;
				StaticStore.menuDesc[29][9] = StaticStore.midlet.showTimings[selectedIndex][0];
				StaticStore.menuDesc[29][10] = StaticStore.midlet.showTimings[selectedIndex][1];
				StaticStore.menuDesc[29][11] = "0001";
				saveList();
				StaticStore.index = 29;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}

		} else if (index == 22 || index == 23 || index == 24) {

			if (moreFlag) {
				StaticStore.menuDesc[28][3] = StaticStore.midlet.region;
				StaticStore.menuDesc[28][4] = StaticStore.midlet.language;
				StaticStore.menuDesc[28][5] = StaticStore.midlet.venue;
				StaticStore.menuDesc[28][6] = StaticStore.midlet.cinema;
				StaticStore.menuDesc[28][7] = StaticStore.midlet.noOfTickets;
				StaticStore.menuDesc[28][8] = StaticStore.midlet.date;
				StaticStore.menuDesc[28][9] = StaticStore.midlet.nextStartRecNumber;
				if(index == 22){
					StaticStore.midlet.movieLoadingMsg = "Fetching theater list, Please wait for the response";
				}else if(index == 23){
					StaticStore.midlet.movieLoadingMsg = "Fetching movie list, please wait for the response";
				}else if(index == 24){
					StaticStore.midlet.movieLoadingMsg = "Fetching the available dates, please wait for response";
				}
				StaticStore.index = 28;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[28][3] = StaticStore.midlet.region;
				StaticStore.menuDesc[28][4] = StaticStore.midlet.language;
				StaticStore.menuDesc[28][7] = StaticStore.midlet.noOfTickets;
				StaticStore.menuDesc[28][9] = "0001";
				if(index == 22){ 
					/*  String location = "";
					  location = menuItem[selectedIndex];*/
					StaticStore.menuDesc[28][5] = menuItem[selectedIndex]; 
					StaticStore.menuDesc[28][6] ="";// StaticStore.midlet.cinema; 
					StaticStore.menuDesc[28][8] = StaticStore.midlet.date;
				}else if(index == 23){ 
					StaticStore.menuDesc[28][5] = StaticStore.midlet.venue; 
					StaticStore.menuDesc[28][6] = menuItem[selectedIndex]; 
					StaticStore.menuDesc[28][8] =  StaticStore.midlet.date; 
				}else if(index == 24){
					StaticStore.menuDesc[28][5] = StaticStore.midlet.venue;
					StaticStore.menuDesc[28][6] = StaticStore.midlet.cinema;
					StaticStore.menuDesc[28][8] = menuItem[selectedIndex]; 
				}
				saveList();
				StaticStore.index = 28;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		}  else if (index == 25) {

			if (moreFlag) {
				StaticStore.menuDesc[29][3] = StaticStore.midlet.region;
				StaticStore.menuDesc[29][4] = StaticStore.midlet.language;
				StaticStore.menuDesc[29][5] = StaticStore.midlet.venue;
				StaticStore.menuDesc[29][6] = StaticStore.midlet.cinema;
				StaticStore.menuDesc[29][7] = StaticStore.midlet.noOfTickets;
				StaticStore.menuDesc[29][8] = StaticStore.midlet.date;
				// comment for use StaticStore.menuDesc[29][9] =
				// StaticStore.midlet.showTimings[StaticStore.midlet.showTimes.selectedIndex][0];
				// StaticStore.menuDesc[29][10] =
				// StaticStore.midlet.showTimings[StaticStore.midlet.showTimes.selectedIndex][1];
				StaticStore.menuDesc[29][11] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 29;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[30][3] = StaticStore.midlet.region;
				StaticStore.menuDesc[30][4] = StaticStore.midlet.language;
				StaticStore.menuDesc[30][5] = StaticStore.midlet.venue;
				StaticStore.menuDesc[30][6] = StaticStore.midlet.cinema;
				StaticStore.menuDesc[30][7] = StaticStore.midlet.noOfTickets;
				StaticStore.menuDesc[30][8] = StaticStore.midlet.date;
				StaticStore.menuDesc[30][9] = StaticStore.midlet.showTime;
				StaticStore.menuDesc[30][10] = StaticStore.midlet.moviePrice[selectedIndex][0];
				StaticStore.menuDesc[30][11] = StaticStore.midlet.moviePrice[selectedIndex][2];
				saveList();
				StaticStore.index = 30;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 26) {






			if (selectedIndex == 0) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(149);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			} else if (selectedIndex == 1) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(150);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			} else if (selectedIndex == 2) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getReprintMenu(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
		} else if (index == 27) {
			String selectedString = "";
			if(selectedIndex == 0){
				selectedString = "Business";
			}else if(selectedIndex == 1){
				selectedString = "Economy";
			}else if(selectedIndex == 2){
				selectedString = "Executive";
			}else if(selectedIndex == 3){
				selectedString = "Gold";
			}else if(selectedIndex == 4){
				selectedString = "Platinum";
			}
			StaticStore.legValue[5] = selectedString;
			StaticStore.airline1st = true;
			StaticStore.isNoInput = true;
			//StaticStore.midlet.pubDynCan = new DynamicCanvas();
			try{
				StaticStore.indexCtr -= 2;
				StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else if (index == 28) {
			if (heading.toUpperCase().trim().equals(
			"BOOKING PERSON TITLE")) {
			} else {
			}
		} else if (index == 29) {
			//empty
		} else if (index == 30) {
			//empty
			//			if (AirlineInputCanvas.passCountLeg != AirlineInputCanvas.count) {
			//			} else {
			//				// AirlineInputCanvas(midlet,display,getSelectedString(),-2,true,false));
			//				StaticStore.passValues[((AirlineInputCanvas.passCountLeg - 1)
			//						* 6 + AirlineInputCanvas.labelLeg - 1)] = getSelectedString();
			//				StaticStore.midlet.sendAirlineSMS();
			//			}

			// /comment for use
		} else if (index == 31) {
			//empty

			if (moreFlag) {
			}
			else {
			}


		} else if (index == 32) {//mobile recharge

			if (moreFlag) {
				StaticStore.menuDesc[32][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[32][4] = "";
				StaticStore.index = 32;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				/*	saveList();
				StaticStore.menuDesc[33][3] = "N";
				StaticStore.menuDesc[33][4] = StaticStore.midlet.txnID;
				// comment for use //
				StaticStore.menuDesc[33][5] =
				 getSelectedString();
				StaticStore.menuDesc[33][9] = "";
				StaticStore.index = 33;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));*/

				saveList();
				StaticStore.tagType="";
				StaticStore.selectedIndex = selectedIndex;
				String temp=StaticStore.midlet.operatorList[selectedIndex][2]+";"+StaticStore.midlet.operatorList[selectedIndex][3];
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "ML00N");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}

		} else if (index == 33) {

			if (moreFlag) {
				if (!StaticStore.midlet.isAdhocPayment) {
					StaticStore.menuDesc[12][3] = "P";
					StaticStore.menuDesc[12][0] = "Add Biller";
					StaticStore.menuDesc[12][5] = "";
				} else {
					StaticStore.menuDesc[12][3] = "A";
					StaticStore.menuDesc[12][0] = "Instant Bill Payment";
					StaticStore.menuDesc[12][5] = "";
				}
				StaticStore.menuDesc[12][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 12;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
//				saveList(); // pls don't enable this MID:9710 fix 
				//StaticStore.menuDesc[134][0] = "Bill Pay-Pay Bill";		
				if (StaticStore.midlet.isAdhocPayment) {
					StaticStore.midlet.billerCategoryList = null;
					StaticStore.menuDesc[220] = new String []{"Instant Bill Payment","APDW;Y",StaticStore.mPINString,"","Search State (optional)","Search City (optional)","001","","","0-20-ANW-N-N","0-20-ANW-N-N","","5","true","true","Y"}; //mid:13071 max len 12 to 20
					StaticStore.tagType = "APDW";
					StaticStore.menuDesc[220][3] = menuItem[selectedIndex];
					//			StaticStore.menuDesc[220][6] = (!StaticStore.midlet.isAdhocPayment)?"P":"A";
					StaticStore.FromListScreen = false;
					StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
				}else
				{
//					StaticStore.midlet.billerCategoryList = null;
					//StaticStore.menuDesc[134][3] = "0001";
					StaticStore.menuDesc[134][3] = menuItem[selectedIndex];
					StaticStore.menuDesc[134][6] = (!StaticStore.midlet.isAdhocPayment)?"P":"A";
					StaticStore.index = 134;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
				}
			} 
		} else if (index == 34) {

			if (moreFlag) {
				StaticStore.menuDesc[68][3] = StaticStore.midlet.airSource;
				StaticStore.menuDesc[68][4] = StaticStore.midlet.airDestination;
				StaticStore.menuDesc[68][6] = StaticStore.midlet.airJourneyTime;
				StaticStore.menuDesc[68][7] = StaticStore.midlet.airFlightClass;
				StaticStore.menuDesc[68][8] = StaticStore.midlet.airTotalTickets;
				StaticStore.menuDesc[68][9] = StaticStore.midlet.airTotalAdults;
				StaticStore.menuDesc[68][10] = StaticStore.midlet.airTotalChildren;
				StaticStore.menuDesc[68][11] = StaticStore.midlet.airTotalInfants;
				StaticStore.menuDesc[68][12] = StaticStore.midlet.nextStartRecNumber;
				String[] months = { "XXX", "JAN", "FEB", "MAR", "APR", "MAY",
						"JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
				int i = 0;
				for (i = 0; i < months.length; i++) {
					if (StaticStore.midlet.airJourneyDate.trim().substring(2, 5)
							.toUpperCase().equals(months[i])) {
						break;
					}
				}
				if (i > 9) {
					StaticStore.menuDesc[68][5] = StaticStore.midlet.airJourneyDate
					.substring(0, 2)
					+ "/"
					+ i
					+ "/"
					+ StaticStore.midlet.airJourneyDate.substring(5);
				} else {
					StaticStore.menuDesc[68][5] = StaticStore.midlet.airJourneyDate
					.substring(0, 2)
					+ "/0"
					+ i
					+ "/"
					+ StaticStore.midlet.airJourneyDate.substring(5);
				}

				StaticStore.index = 68;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
//				{"Booking & Payment","APA1;Y",mPINString,"Flight ID","From","To","Journey Date","Ticktes","Adults","Child","Infants","Flight Class","001",
//		              "","","","","","","","","","","","","11","true","true","Y"},//136
				StaticStore.menuDesc[136][3] = StaticStore.midlet.airlineArray[selectedIndex][0];
				StaticStore.menuDesc[136][4] = StaticStore.midlet.airSource;
				StaticStore.menuDesc[136][5] = StaticStore.midlet.airDestination;
				StaticStore.menuDesc[136][6] = StaticStore.midlet.airJourneyDate;
				StaticStore.menuDesc[136][7] = StaticStore.midlet.airTotalTickets;
				StaticStore.menuDesc[136][8] = StaticStore.midlet.airTotalAdults;
				StaticStore.menuDesc[136][9] = StaticStore.midlet.airTotalChildren;
				StaticStore.menuDesc[136][10] = StaticStore.midlet.airTotalInfants;
				StaticStore.menuDesc[136][11] = StaticStore.midlet.airFlightClass;
				
//				StaticStore.menuDesc[136][StaticStore.menuDesc[136].length - 2] = "false";
//				StaticStore.menuDesc[136][StaticStore.menuDesc[136].length - 1] = "false";
				saveList();
				StaticStore.index = 136;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 35) {
			if (moreFlag) {
				StaticStore.menuDesc[70][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[70][4] = "";
				StaticStore.index = 70;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.LogPrinter('i',"StaticStore.midlet.regBillers[selectedIndex][0] --> "+StaticStore.midlet.regBillers[selectedIndex][0]);
				StaticStore.menuDesc[23][3] = StaticStore.midlet.regBillers[selectedIndex][0];
				//saveList();
				if(StaticStore.isMpinAtLast){
					StaticStore.FromListScreen = true;
				}
					
				StaticStore.index = 23;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}



		} else if (index == 36) {
			//StaticStore.LogPrinter('i',"@@@@@@@@@@@@@@@@@@36@");
			if (selectedIndex == 0 && StaticStore.ATMlocatorFlag) {
				saveList();
				Intent myIntent = StaticStore.midlet.getSearch("ATM",
						getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 1 && StaticStore.branchLocatorFlag){
				saveList();
				Intent myIntent = StaticStore.midlet.getSearch("Branch",
						getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
			
			else if (selectedIndex == 2 && StaticStore.eLobbyLocatorFlag){
				StaticStore.eLobbyLocationFlag = true;
				saveList();
				Intent myIntent = StaticStore.midlet.getSearch("E-Lobby",
						getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
			
			
			else {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}



		} else if (index == 37) {
			if (selectedIndex == 0) {
				if (heading.equals("ATM")) {
					saveList();
					StaticStore.index = 74;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				} else if (heading.equals("Branch")) {
					saveList();
					StaticStore.index = 75;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}else if (heading.equals("E-Lobby")) {
					saveList();
					StaticStore.menuDesc[220] = new String [] {"E-Lobby","APEL;N","P","Pincode"," ","001","","6-6-N-N-N","","","4","true","true","N"};
					StaticStore.index = 220;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}
			} else if (selectedIndex == 1) {
				if (heading.equals("ATM")) {
					saveList();
					StaticStore.index = 51;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (heading.equals("Branch")) {
					saveList();
					StaticStore.index = 52;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}else if (heading.equals("E-Lobby")) {
					saveList();
					StaticStore.menuDesc[220] = new String [] {"E-Lobby","APEL;N","L","Search City","Search Location","001","","0-20-ANW-N-N","0-20-ANW-N-N","","4","true","true","N"};
					StaticStore.index = 220;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}
			}
			//
		} else if (index == 38) {

			if (moreFlag) {
				StaticStore.menuDesc[6][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 6;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.recMsgType.equals("AI")
						|| StaticStore.midlet.recMsgType.equals("AB")) {
					StaticStore.menuDesc[7][3] = StaticStore.midlet.airlineBooking[selectedIndex][0];
					saveList();
					StaticStore.index = 7;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (StaticStore.midlet.recMsgType.equals("R0")
						|| StaticStore.midlet.recMsgType.equals("R1")) {
					StaticStore.menuDesc[78][3] = StaticStore.midlet.airlineBooking[selectedIndex][0];
					//saveList();
					StaticStore.index = 78;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 39) {
			//
			if (selectedIndex == 0) {
				saveList();
				StaticStore.index = 79;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 1) {
				saveList();
				StaticStore.menuDesc[6][0] = "Payment Only";//changed by S
				StaticStore.menuDesc[6][1] = "APR0;Y";
				StaticStore.index = 6;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			//
		} else if (index == 40) {
			//
			if (moreFlag) {
				StaticStore.menuDesc[18][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 18;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			else {//for displayable view as in java
				StaticStore.midlet.selectedBillIndex = selectedIndex;
				StaticStore.midlet.dynamiBillArr = StaticStore.midlet
				.getSplittedValues(StaticStore.midlet.billpayBills[0][3]+"*"+StaticStore.midlet.billpayBills[0][4]+"*"+
						StaticStore.midlet.billpayBills[0][5]+ ";TXNID:" + StaticStore.midlet.txnID, 3,'*', true, StaticStore.midlet.dynamiBillArr);
				
				StaticStore.LogPrinter('i',"StaticStore.midlet.dynamiBillArr --> "+StaticStore.midlet.dynamiBillArr);
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				if (StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][1]
				                                                                          .trim().toUpperCase().equals("Y")) {
					
//					for (int i = 0; i < StaticStore.midlet.dynamiBillArr.length; i++) {
//						// StaticStore.LogPrinter('i',"====> "+i+"
//						// "+midlet.dynamiBillArr[i][0]);
//					}
//					// B500;<CUSTOMER-MNEMONIC
//					// NAME>|<PRESENTMENT-FLAG>|<AMOUNT>|<MIN PAYMENT
//					// FLAG>|<PART PAYMENT FLAG>|<EXCESS PAYMENT
//					// FLAG>|<MIN AMOUNT>|<PENALTY AMOUNT>|<BILL
//					// NO>|<BILL DATE>|<DUEDATE>|<FIXED
//					// CHARGES>|<VARIABLE CHARGES>;<START RECORD>;<MORE
//					// FLAG>;TXNID:124343535634
//					StaticStore.midlet.dynamicDisplayableArr = new String[10];   
//					StaticStore.midlet.dynamicDisplayableArr[0] = "Biller Nickname";
//					StaticStore.midlet.dynamicDisplayableArr[1] = "Bill Amount";
//					StaticStore.midlet.dynamicDisplayableArr[2] = "Minimum Amount";
//					StaticStore.midlet.dynamicDisplayableArr[3] = "Penalty Amount";
//					StaticStore.midlet.dynamicDisplayableArr[4] = "Bill No.";
//					StaticStore.midlet.dynamicDisplayableArr[5] = "Bill Date";
//					StaticStore.midlet.dynamicDisplayableArr[6] = "Due Date";
//					StaticStore.midlet.dynamicDisplayableArr[7] = "Fixed Charges";
//					StaticStore.midlet.dynamicDisplayableArr[8] = "Variable Charges";
//					StaticStore.midlet.dynamicDisplayableArr[9] = "Transaction ID";
//
//					StaticStore.midlet.dynamicBillMsg = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0]
//					                                                                                                          + ";"
//					                                                                                                          + StaticStore.midlet.dynamiBillArr[0][0]
//					                                                                                                                                                + ";"
//					                                                                                                                                                + StaticStore.midlet.dynamiBillArr[4][0]
//					                                                                                                                                                                                      + ";"
//					                                                                                                                                                                                      + StaticStore.midlet.dynamiBillArr[5][0]
//					                                                                                                                                                                                                                            + ";"
//					                                                                                                                                                                                                                            + StaticStore.midlet.dynamiBillArr[6][0]
//					                                                                                                                                                                                                                                                                  + ";"
//					                                                                                                                                                                                                                                                                  + StaticStore.midlet.dynamiBillArr[7][0]
//					                                                                                                                                                                                                                                                                                                        + ";"
//					                                                                                                                                                                                                                                                                                                        + StaticStore.midlet.dynamiBillArr[8][0]
//					                                                                                                                                                                                                                                                                                                                                              + ";"
//					                                                                                                                                                                                                                                                                                                                                              + StaticStore.midlet.dynamiBillArr[9][0]
//					                                                                                                                                                                                                                                                                                                                                                                                    + ";"
//					                                                                                                                                                                                                                                                                                                                                                                                    + StaticStore.midlet.dynamiBillArr[10][0] + "%"
//					                                                                                                                                                                                                                                                                                                                                                                                    + ";"
//					                                                                                                                                                                                                                                                                                                                                                                                    + StaticStore.midlet.txnID;
//					
				} else {
					
//					for (int i = 0; i < StaticStore.midlet.dynamiBillArr.length; i++) {
//						// StaticStore.LogPrinter('i',"====> "+i+"
//						// "+midlet.dynamiBillArr[i][0]);
//					}
//
//					StaticStore.midlet.dynamicDisplayableArr = new String[4];
//					StaticStore.midlet.dynamicDisplayableArr[0] = "Biller Nickname";
//					StaticStore.midlet.dynamicDisplayableArr[1] = "Fixed Charges";
//					StaticStore.midlet.dynamicDisplayableArr[2] = "Variable Charges";
//					StaticStore.midlet.dynamicDisplayableArr[3] = "Transaction ID";
//
//					StaticStore.midlet.dynamiBillArr[9][0] = StaticStore.midlet.dynamiBillArr[9][0]
//					                                                                             .trim().equals("") ? "N/A"
//					                                                                            		 : (StaticStore.midlet.dynamiBillArr[9][0]);
//					StaticStore.midlet.dynamiBillArr[10][0] = StaticStore.midlet.dynamiBillArr[10][0]
//					                                                                               .trim().equals("") ? "N/A"
//					                                                                            		   : (StaticStore.midlet.dynamiBillArr[10][0] + "%");
//					StaticStore.midlet.dynamicBillMsg = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0]
//					                                                                                                          + ";"
//					                                                                                                          + StaticStore.midlet.dynamiBillArr[9][0]
//					                                                                                                                                                + ";"
//					                                                                                                                                                + StaticStore.midlet.dynamiBillArr[10][0]
//					                                                                                                                                                                                       + ";"
//					                                                                                                                                                                                       + StaticStore.midlet.txnID;
				}
				/*
				StaticStore.midlet.selectedBillIndex = selectedIndex;
				//saveList();
				StaticStore.midlet.dynamiBillArr = StaticStore.midlet
				.getSplittedValues(
						StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][2]
								+ ";TXNID:" + StaticStore.midlet.txnID, 1,
						'*', true, StaticStore.midlet.dynamiBillArr);
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				if (StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][1].trim()
						.toUpperCase().equals("Y")) {
					StaticStore.midlet.dynamicDisplayableArr = new String[9];
					StaticStore.midlet.dynamicDisplayableArr[0] = "Nickname";
					StaticStore.midlet.dynamicDisplayableArr[1] = "Bill Amount";
					StaticStore.midlet.dynamicDisplayableArr[2] = "Minimum Payable Amount";
					StaticStore.midlet.dynamicDisplayableArr[3] = "Penalty Amount";
					StaticStore.midlet.dynamicDisplayableArr[4] = "Bill No.";
					StaticStore.midlet.dynamicDisplayableArr[5] = "Bill Date";
					StaticStore.midlet.dynamicDisplayableArr[6] = "Due Date";
					StaticStore.midlet.dynamicDisplayableArr[7] = "Fixed Charges";
					StaticStore.midlet.dynamicDisplayableArr[8] = "Variable Charges";

					StaticStore.midlet.dynamicBillMsg = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0]
							+ ";Rs."
							+ StaticStore.midlet.dynamiBillArr[0][0]
							+ ";Rs."
							+ StaticStore.midlet.dynamiBillArr[4][0]
							+ ";Rs."
							+ StaticStore.midlet.dynamiBillArr[5][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[6][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[7][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[8][0]
							+ ";Rs."
							+ StaticStore.midlet.dynamiBillArr[9][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[10][0] + "%";
				} else {
					StaticStore.midlet.dynamicDisplayableArr = new String[3];
					StaticStore.midlet.dynamicDisplayableArr[0] = "Nickname";
					StaticStore.midlet.dynamicDisplayableArr[1] = "Fixed Charges";
					StaticStore.midlet.dynamicDisplayableArr[2] = "Variable Charges";
					StaticStore.midlet.dynamiBillArr[9][0] = StaticStore.midlet.dynamiBillArr[9][0]
							.trim().equals("") ? "N/A"
							: ("Rs." + StaticStore.midlet.dynamiBillArr[9][0]);
					StaticStore.midlet.dynamiBillArr[10][0] = StaticStore.midlet.dynamiBillArr[10][0]
							.trim().equals("") ? "N/A"
							: (StaticStore.midlet.dynamiBillArr[10][0] + "%");
					StaticStore.midlet.dynamicBillMsg = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[9][0]
							+ ";"
							+ StaticStore.midlet.dynamiBillArr[10][0];
				}*/
				intent.putExtra("response",StaticStore.midlet.dynamicBillMsg);
				intent.putExtra("formName", "BillInfo");
				StaticStore.midlet.startFragment(getActivity(),intent);

			}
		}

		}


	private void doAction41To60(int index, int selectedIndex, boolean moreFlag) {
		if (index == 41) {
			if (menuItem[selectedIndex].trim().startsWith("Full Payment")) {
				StaticStore.menuDesc[19][3] = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0];
				StaticStore.menuDesc[19][4] = "F";
				StaticStore.menuDesc[19][5] = StaticStore.midlet.dynamiBillArr[6][0];
				StaticStore.menuDesc[19][6] = StaticStore.midlet.dynamiBillArr[0][0];
				StaticStore.menuDesc[19][7] = StaticStore.midlet.txnID;
				StaticStore.index = 19;
				StaticStore.LogPrinter('i',"StaticStore.index = 19; --> "+Arrays.deepToString(StaticStore.menuDesc[19]));
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().startsWith("Minimum Payment")) {
				StaticStore.menuDesc[19][3] = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0];
				StaticStore.menuDesc[19][4] = "M";
				StaticStore.menuDesc[19][5] = StaticStore.midlet.dynamiBillArr[6][0];
				StaticStore.menuDesc[19][6] = StaticStore.midlet.dynamiBillArr[4][0];
				StaticStore.menuDesc[19][7] = StaticStore.midlet.txnID;
				StaticStore.index = 19;
				StaticStore.LogPrinter('i',"StaticStore.index = 19 m; --> "+Arrays.deepToString(StaticStore.menuDesc[19]));
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().startsWith("Part Payment")) {
				StaticStore.menuDesc[20][3] = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0];
				StaticStore.menuDesc[20][4] = "P";
				StaticStore.menuDesc[20][5] = StaticStore.midlet.dynamiBillArr[6][0];
				StaticStore.menuDesc[20][7] = StaticStore.midlet.txnID;
				StaticStore.index = 20;
				StaticStore.LogPrinter('i',"StaticStore.index = 20 P; --> "+Arrays.deepToString(StaticStore.menuDesc[20]));
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().startsWith("Excess Payment")) {
				StaticStore.menuDesc[20][3] = StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][0];
				StaticStore.menuDesc[20][4] = "E";
				StaticStore.menuDesc[20][5] = StaticStore.midlet.dynamiBillArr[6][0];
				StaticStore.menuDesc[20][7] = StaticStore.midlet.txnID;
				StaticStore.index = 20;
				StaticStore.LogPrinter('i',"StaticStore.index = 20 E; --> "+Arrays.deepToString(StaticStore.menuDesc[20]));
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
		} else if (index == 42) {
//             if (selectedIndex == 0 ){
//				  saveList();
//				  StaticStore.midlet.listObject = new ListObject();
//				  StaticStore.midlet.listObject.setIndex(175);
//				  StaticStore.midlet.listObject.setTag("FT");
//				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
//				  StaticStore.midlet.listObject.setMore(false);
//				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
//             }else 
			   if (selectedIndex == 0){
				  saveList();
				  String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				  StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				  StaticStore.LogPrinter('i',"StaticStore.isAccTypeRefresh -->"+StaticStore.isAccTypeRefresh);
				  if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					  StaticStore.isAccTypeRefresh = false;
					  StaticStore.tagType = "APTF";
					  StaticStore.accTypeCompleteData = "";
					  StaticStore.indexBeforeAccTypeInitiation = -1;
					  StaticStore.index = 220;
     			      StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			      StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }else{
					  neftRegister(new AlertDialog.Builder(getActivity()),"Do you know the IFS Code of the Bank?", 42,getActivity()).show();
				 }
			}else if (selectedIndex == 1) {
				 saveList();
				 StaticStore.midlet.neftPaymentFlag = true;
				 StaticStore.midlet.listObject = new ListObject();
				 StaticStore.midlet.listObject.setIndex(162);
				 StaticStore.midlet.listObject.setTag("OT");
				 StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				 StaticStore.midlet.listObject.setMore(false);
				 StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
            } else if (selectedIndex == 2) {
				 String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				 StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				 if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					 StaticStore.isAccTypeRefresh = false;
					 StaticStore.tagType = "APTF";
					 StaticStore.accTypeCompleteData = "";
					 StaticStore.indexBeforeAccTypeInitiation = -11;
					 StaticStore.index = 220;
     			     StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			     StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }else{
				     StaticStore.recCount = 0;
				     saveList();
				     StaticStore.index = 213;
				     StaticStore.FromListScreen = false;
				     StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }
			}else if (selectedIndex == 3) {
				 saveList();
				 StaticStore.midlet.neftPaymentFlag = false;
				 StaticStore.menuDesc[196][0] = "Delete Beneficiary";
				 StaticStore.forDeReg="Delete Beneficiary";
				 StaticStore.FromListScreen = false;
				 StaticStore.index = 196;
				 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));	
			} else if (selectedIndex == 4) {
				 saveList();
				 Intent myIntent = StaticStore.midlet.getIFSCSearchMenu(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
			} 
      } else if (index == 43) {
              if (selectedIndex == 0 && StaticStore.neftFlag) {
				  saveList();
				  Intent myIntent = StaticStore.midlet.getOtherFTOptions("NEFT",getActivity());
				  StaticStore.midlet.startFragment(getActivity(),myIntent);
			  }else {
				  StaticStore.ToastDisplay(getActivity(), disableMsg);
			  }
        } else if (index == 44) {

			StaticStore.legValue[2] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			//saveList();
			//if(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 1].equals("true"))
			StaticStore.fromCurrentSavingList = true;
			try{
				StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else if (index == 45) {

			if (moreFlag) {
				StaticStore.menuDesc[81][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 81;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[84][5] = StaticStore.midlet.bankList[selectedIndex][0];
				saveList();
				StaticStore.index = 84;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 46) {


			if (moreFlag) {
				if (!StaticStore.midlet.neftPaymentFlag) {

					//{"NEFT Beneficiary Deregistration","APN3;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false"},//196  
//					{"Delete Beneficiary","AP4L;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"},//196
					StaticStore.menuDesc[196][0] = "Delete Beneficiary";
					StaticStore.menuDesc[196][3] = StaticStore.regSearchString;
					StaticStore.menuDesc[196][4] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[196][5] = "";
					StaticStore.menuDesc[196][6] = "";
					StaticStore.menuDesc[196][7] = "";
					StaticStore.index = 196;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));

				} else {
					StaticStore.menuDesc[87][0] = "Pay Beneficiary";
					StaticStore.menuDesc[87][3] = "P";
					StaticStore.menuDesc[87][4] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[87][5] = "";
					StaticStore.index = 87;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}else {
				if (StaticStore.midlet.neftPaymentFlag && !StaticStore.midlet.neftDeregFlag) {
					StaticStore.menuDesc[88][5] = menuItem[selectedIndex];
					StaticStore.index = 88;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					//					saveList();
					StaticStore.accpaymentFlag = true;
					StaticStore.menuDesc[91][0] = "Delete Beneficiary";
					StaticStore.forDeReg="Delete Beneficiary";
					StaticStore.menuDesc[91][1] = "AP4D;Y";
					StaticStore.menuDesc[91][3] = menuItem[selectedIndex];
					StaticStore.index = 91;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
					
				}
				}
			




		} else if (index == 47) {

			if (moreFlag) {
				StaticStore.menuDesc[89][3] = "P";
				StaticStore.menuDesc[89][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 89;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.partnerPaymentFlag && !StaticStore.midlet.partnerDergFlag) {
					// StaticStore.menuDesc[90][4] = getSelectedString();
					StaticStore.menuDesc[90][4] = menuItem[selectedIndex];
					saveList();
					StaticStore.index = 90;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[91][0] = "Partner Bank Deregistration";
					// StaticStore.menuDesc[91][3] = getSelectedString();
					StaticStore.menuDesc[91][3] = menuItem[selectedIndex];
					saveList();
					StaticStore.index = 91;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 48) {

			if (moreFlag) {
				StaticStore.menuDesc[93][3] = StaticStore.midlet.templeSearchStr;
				StaticStore.menuDesc[93][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 93;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.temples[selectedIndex][2].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[94][3] = StaticStore.midlet.temples[selectedIndex][0];
//					saveList();  MID: 11369 
					StaticStore.index = 94;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[96][3] = StaticStore.midlet.templeID;
					StaticStore.menuDesc[96][4] = "N";
					StaticStore.menuDesc[96][5] = "";
					StaticStore.menuDesc[96][6] = "N";
					StaticStore.menuDesc[96][7] = "";
//					saveList(); MID: 11369
					StaticStore.index = 96;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 49) {

			if (moreFlag) {
				StaticStore.menuDesc[94][3] = StaticStore.midlet.templeID;
				StaticStore.menuDesc[94][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 94;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.templeSchemes[selectedIndex][2].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[95][3] = StaticStore.midlet.templeID;
					StaticStore.menuDesc[95][4] = StaticStore.midlet.templeSchemes[selectedIndex][0];
//					saveList(); MID: 11369
					StaticStore.index = 95;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[96][3] = StaticStore.midlet.templeID;
					StaticStore.menuDesc[96][4] = "Y";
					StaticStore.menuDesc[96][5] = StaticStore.midlet.templeSchemes[selectedIndex][0];
					StaticStore.menuDesc[96][6] = "N";
					StaticStore.menuDesc[96][7] = "";
//					saveList(); MID: 11369
					StaticStore.index = 96;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 50) {

			if (moreFlag) {
				StaticStore.menuDesc[95][3] = StaticStore.midlet.templeID;
				StaticStore.menuDesc[95][4] = StaticStore.midlet.templeSchemeID;
				StaticStore.menuDesc[95][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 95;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[96][3] = StaticStore.midlet.templeID;
				StaticStore.menuDesc[96][4] = "Y";
				StaticStore.menuDesc[96][5] = StaticStore.midlet.templeSchemeID;
				StaticStore.menuDesc[96][6] = "Y";
				StaticStore.menuDesc[96][7] = StaticStore.midlet.templeSubSchemes[selectedIndex][0];
				saveList();
				StaticStore.index = 96;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 51) {

			if (moreFlag) {
				StaticStore.menuDesc[98][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 98;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				// StaticStore.menuDesc[99][3] = getSelectedString();
				StaticStore.menuDesc[99][3] = menuItem[selectedIndex];
				saveList();
				StaticStore.index = 99;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}// commented for use

		} else if (index == 52) {

			if (moreFlag) {
				StaticStore.menuDesc[100][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[100][5] = "";
				StaticStore.index = 100;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				// StaticStore.menuDesc[101][4] = getSelectedString();				
				StaticStore.menuDesc[101][5] = menuItem[selectedIndex];
				//				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.index = 101;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 53) {

			if (moreFlag) {
				StaticStore.menuDesc[102][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[102][5] = "";
				StaticStore.index = 102;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				// StaticStore.menuDesc[103][4] = getSelectedString();				
				StaticStore.menuDesc[103][5] = menuItem[selectedIndex];
				//				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.index = 103;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 54) {

//			if (selectedIndex == 0) {
//				saveList();
//				StaticStore.index = 34;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			} else 
			if (selectedIndex == 0) {
				
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(283);
//				StaticStore.midlet.listObject.setTag("I1");
				StaticStore.midlet.listObject.setTag("N1");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
				
			} 
//			else if (selectedIndex == 2) {
//				saveList();
//				StaticStore.index = 111;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			}

		} else if (index == 55) {

			if (moreFlag) {
				StaticStore.menuDesc[35][3] = StaticStore.midlet.instSearchStr;
				StaticStore.menuDesc[35][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 35;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
//				saveList();
				StaticStore.menuDesc[36][3] = StaticStore.midlet.institutions[selectedIndex][0];
				StaticStore.index = 36;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 56) {

			if (moreFlag) {
				StaticStore.menuDesc[37][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 37;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			else {
//				saveList();
				StaticStore.menuDesc[38][3] = StaticStore.midlet.regInstitutes[selectedIndex][0];
				StaticStore.index = 38;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 57) {

			if (StaticStore.midlet.instHostelTutionList[selectedIndex][2].trim()
					.toUpperCase().equals("Y")) {
				StaticStore.menuDesc[39][3] = StaticStore.midlet.instId;
				StaticStore.menuDesc[39][4] = StaticStore.midlet.rollNo;
				StaticStore.menuDesc[39][5] = StaticStore.midlet.hostelFlag ? "Y" : "N";
				StaticStore.menuDesc[39][6] = StaticStore.midlet.instHostelTutionList[selectedIndex][0]
				                                                                                     .trim();
				StaticStore.menuDesc[39][7] = "001";
//				saveList();
				StaticStore.index = 39;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.menuDesc[104][3] = StaticStore.midlet.instId;
				StaticStore.menuDesc[104][4] = StaticStore.midlet.rollNo;
				StaticStore.menuDesc[104][5] = StaticStore.midlet.hostelFlag ? "Y" : "N";
				StaticStore.menuDesc[104][6] = StaticStore.midlet.instHostelTutionList[selectedIndex][0]
				                                                                                      .trim();
				StaticStore.menuDesc[104][7] = "N";
				StaticStore.menuDesc[104][8] = "";
				StaticStore.menuDesc[104][9] = "N";
				StaticStore.menuDesc[104][10] = "";
//				saveList();
				StaticStore.index = 104;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 58) {

			if (moreFlag) {
				StaticStore.menuDesc[105][3] = StaticStore.midlet.instId;
				StaticStore.menuDesc[105][4] = StaticStore.midlet.rollNo;
				StaticStore.menuDesc[105][5] = StaticStore.midlet.hostelFlag ? "Y" : "N";
				StaticStore.menuDesc[105][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 105;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			else {
				if (StaticStore.midlet.instCatagories[selectedIndex][2].trim()
						.toUpperCase().equals("Y")) {
					StaticStore.menuDesc[106][3] = StaticStore.midlet.instId;
					StaticStore.menuDesc[106][4] = StaticStore.midlet.rollNo;
					StaticStore.menuDesc[106][5] = StaticStore.midlet.instCatagories[selectedIndex][0];
					StaticStore.menuDesc[106][6] = "0001";
//					saveList();
					StaticStore.index = 106;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[104][3] = StaticStore.midlet.instId;
					StaticStore.menuDesc[104][4] = StaticStore.midlet.rollNo;
					StaticStore.menuDesc[104][5] = StaticStore.midlet.hostelFlag ? "Y"
							: "N";
					StaticStore.menuDesc[104][6] = StaticStore.midlet.hostelId;
					StaticStore.menuDesc[104][7] = "Y";
					// StaticStore.LogPrinter('i',"************"+StaticStore.midlet.instCatagories[selectedIndex][0]);
					StaticStore.menuDesc[104][8] = StaticStore.midlet.instCatagories[selectedIndex][0];
					StaticStore.menuDesc[104][9] = "N";
					StaticStore.menuDesc[104][10] = "";
//					saveList();
					StaticStore.index = 104;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		} else if (index == 59) {

			if (moreFlag) {
				StaticStore.menuDesc[106][3] = StaticStore.midlet.instId;
				StaticStore.menuDesc[106][4] = StaticStore.midlet.rollNo;
				StaticStore.menuDesc[106][5] = StaticStore.midlet.instCatagories[selectedIndex][0];
				StaticStore.menuDesc[106][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 106;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

			else {
				StaticStore.menuDesc[104][3] = StaticStore.midlet.instId;
				StaticStore.menuDesc[104][4] = StaticStore.midlet.rollNo;
				StaticStore.menuDesc[104][5] = StaticStore.midlet.hostelFlag ? "Y" : "N";
				StaticStore.menuDesc[104][6] = StaticStore.midlet.hostelId;
				StaticStore.menuDesc[104][7] = StaticStore.midlet.categoryFlag ? "Y"
						: "N";
				StaticStore.menuDesc[104][8] = StaticStore.midlet.instCategoryId;
				StaticStore.menuDesc[104][9] = "Y";
				StaticStore.menuDesc[104][10] = StaticStore.midlet.instSubCatagories[selectedIndex][0];
//				saveList();
				StaticStore.index = 104;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		}else if (index == 60) {
//			saveList();
			StaticStore.LogPrinter('i',"SELECTEDINDEX"+selectedIndex);
			if (menuItem[selectedIndex].trim().startsWith("Full Payment")) {
				if (!StaticStore.midlet.instBillDetails[0][9].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[109][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[109][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[109][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[109][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[109][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[109][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[109][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[109][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[109][11] = StaticStore.midlet.instBillDetails[0][9];
					StaticStore.menuDesc[109][12] = "";
					StaticStore.menuDesc[109][13] = "F";
					StaticStore.menuDesc[109][14] = StaticStore.midlet.instBillDetails[0][16];
					StaticStore.menuDesc[109][15] = StaticStore.midlet.txnID;
					StaticStore.index = 109;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[110][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[110][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[110][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[110][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[110][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[110][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[110][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[110][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[110][11] = "Y";
					StaticStore.menuDesc[110][13] = "F";
					StaticStore.menuDesc[110][14] = StaticStore.midlet.instBillDetails[0][16];
					StaticStore.menuDesc[110][15] = StaticStore.midlet.txnID;
					StaticStore.index = 110;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			} else if (menuItem[selectedIndex].trim().startsWith("Minimum Payment")) {
				if (!StaticStore.midlet.instBillDetails[0][9].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[109][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[109][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[109][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[109][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[109][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[109][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[109][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[109][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[109][11] = StaticStore.midlet.instBillDetails[0][9];
					StaticStore.menuDesc[109][12] = "";
					StaticStore.menuDesc[109][13] = "M";
					StaticStore.menuDesc[109][14] = StaticStore.midlet.instBillDetails[0][17];
					StaticStore.menuDesc[109][15] = StaticStore.midlet.txnID;
					StaticStore.index = 109;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));

				} else {
					StaticStore.menuDesc[110][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[110][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[110][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[110][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[110][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[110][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[110][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[110][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[110][11] = "Y";
					StaticStore.menuDesc[110][13] = "M";
					StaticStore.menuDesc[110][14] = StaticStore.midlet.instBillDetails[0][17];
					StaticStore.menuDesc[110][15] = StaticStore.midlet.txnID;
					StaticStore.index = 110;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			} else if (menuItem[selectedIndex].trim().equals("Part Payment")) {
				if (!StaticStore.midlet.instBillDetails[0][9].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[107][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[107][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[107][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[107][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[107][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[107][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[107][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[107][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[107][11] = StaticStore.midlet.instBillDetails[0][9];
					StaticStore.menuDesc[107][12] = "";
					StaticStore.menuDesc[107][13] = "P";
					StaticStore.menuDesc[107][15] = StaticStore.midlet.txnID;
					StaticStore.index = 107;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[108][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[108][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[108][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[108][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[108][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[108][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[108][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[108][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[108][11] = "Y";
					StaticStore.menuDesc[108][13] = "P";
					StaticStore.menuDesc[108][15] = StaticStore.midlet.txnID;
					StaticStore.index = 108;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			} else if (menuItem[selectedIndex].trim().equals("Excess Payment")) {
				if (!StaticStore.midlet.instBillDetails[0][9].trim().toUpperCase()
						.equals("Y")) {
					StaticStore.menuDesc[107][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[107][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[107][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[107][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[107][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[107][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[107][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[107][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[107][11] = StaticStore.midlet.instBillDetails[0][9];
					StaticStore.menuDesc[107][12] = "";
					StaticStore.menuDesc[107][13] = "P";
					StaticStore.menuDesc[107][15] = StaticStore.midlet.txnID;
					StaticStore.index = 107;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[108][3] = StaticStore.midlet.instBillDetails[0][0];
					StaticStore.menuDesc[108][4] = StaticStore.midlet.instBillDetails[0][2];
					StaticStore.menuDesc[108][5] = StaticStore.midlet.instBillDetails[0][3];
					StaticStore.menuDesc[108][6] = StaticStore.midlet.instBillDetails[0][4];
					StaticStore.menuDesc[108][7] = StaticStore.midlet.instBillDetails[0][5];
					StaticStore.menuDesc[108][8] = StaticStore.midlet.instBillDetails[0][6];
					StaticStore.menuDesc[108][9] = StaticStore.midlet.instBillDetails[0][7];
					StaticStore.menuDesc[108][10] = StaticStore.midlet.instBillDetails[0][8];
					StaticStore.menuDesc[108][11] = "Y";
					StaticStore.menuDesc[108][13] = "P";
					StaticStore.menuDesc[108][15] = StaticStore.midlet.txnID;
					StaticStore.index = 108;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}
		}
	}

	private void doAction61To80(int index, int selectedIndex, boolean moreFlag) {
		if (index == 61) {

			if (moreFlag) {
				StaticStore.menuDesc[111][3] = StaticStore.midlet.nextStartRecNumber;
				;
				StaticStore.index = 111;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[112][3] = StaticStore.midlet.regInstitutes4Dereg[selectedIndex][0];
				StaticStore.index = 112;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 62) {

			StaticStore.legValue[1] = selectedIndex + "";
			saveList();
			StaticStore.index = 113;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));

		} else if (index == 63) {

			if (selectedIndex == 0) {
				StaticStore.IsGPRS = true;
				saveList();
				StaticStore.index = 154;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				saveList();
				StaticStore.IsGPRS = false;
				StaticStore.isCommModeSelected = true;
				StaticStore.midlet.isPasswordEntered = true;
				StaticStore.midlet.writeinMemory(getActivity());

				if (StaticStore.isAppNewlyopened) {
					//disp
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response","To fetch the linked account details, Select Settings, then Select Customer Profile, and click Update");
					intent.putExtra("formName", "Activation");
					StaticStore.midlet.startFragment(getActivity(),intent);


					StaticStore.isAppNewlyopened = false;
				} else {

				}
			}

		} else if (index == 64) {

			StaticStore.IsGPRS = (selectedIndex == 0);
			StaticStore.midlet.writeinMemory(getActivity());
			saveList();
			StaticStore.index = 5;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));

		} else if (index == 65) {

			if (selectedIndex == 0) {
				saveList();
				StaticStore.index = 113;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			} else if (selectedIndex == 1) {
				saveList();
				StaticStore.FromListScreen = false;
//				  StaticStore.index = 114;
				   StaticStore.menuDesc[114][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
				StaticStore.index = 114;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 66) {
			StaticStore.legValue[3] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			StaticStore.fromCurrentSavingList = true;
			try{
				StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else if (index == 67) {

			if (menuItem[selectedIndex].trim().startsWith("Full Payment")) {
				StaticStore.menuDesc[120][12] = "F";
				StaticStore.menuDesc[120][13] = StaticStore.midlet.billpayNotifyDet[14][0];
				StaticStore.index = 120;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().startsWith("Minimum Payment")) {
				StaticStore.menuDesc[120][12] = "M";
				StaticStore.menuDesc[120][13] = StaticStore.midlet.billpayNotifyDet[15][0];
				StaticStore.index = 120;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().equals("Part Payment")) { 
				StaticStore.menuDesc[122][3] = StaticStore.midlet.billpayNotifyDet[1][0];
				StaticStore.menuDesc[122][4] = StaticStore.midlet.billpayNotifyDet[5][0];
				StaticStore.menuDesc[122][5] = StaticStore.midlet.billpayNotifyDet[2][0];
				StaticStore.menuDesc[122][6] = StaticStore.midlet.billpayNotifyDet[0][0];
				StaticStore.menuDesc[122][7] = StaticStore.midlet.billpayNotifyDet[3][0];
				StaticStore.menuDesc[120][8] = StaticStore.midlet.billpayNotifyDet[4][0];
				StaticStore.menuDesc[122][9] = StaticStore.midlet.billpayNotifyDet[11][0];
				StaticStore.menuDesc[122][10] = StaticStore.midlet.billpayNotifyDet[12][0];
				StaticStore.menuDesc[122][11] = StaticStore.midlet.billpayNotifyDet[13][0];
				StaticStore.menuDesc[122][12] = "P";
				StaticStore.menuDesc[122][14] = StaticStore.midlet.txnID;
				StaticStore.index = 122;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (menuItem[selectedIndex].trim().equals("Excess Payment")) {
				StaticStore.menuDesc[122][3] = StaticStore.midlet.billpayNotifyDet[1][0];
				StaticStore.menuDesc[122][4] = StaticStore.midlet.billpayNotifyDet[5][0];
				StaticStore.menuDesc[122][5] = StaticStore.midlet.billpayNotifyDet[2][0];
				StaticStore.menuDesc[122][6] = StaticStore.midlet.billpayNotifyDet[0][0];
				StaticStore.menuDesc[122][7] = StaticStore.midlet.billpayNotifyDet[3][0];
				StaticStore.menuDesc[120][8] = StaticStore.midlet.billpayNotifyDet[4][0];
				StaticStore.menuDesc[122][9] = StaticStore.midlet.billpayNotifyDet[11][0];
				StaticStore.menuDesc[122][10] = StaticStore.midlet.billpayNotifyDet[12][0];
				StaticStore.menuDesc[122][11] = StaticStore.midlet.billpayNotifyDet[13][0];
				StaticStore.menuDesc[122][12] = "E";
				StaticStore.menuDesc[122][14] = StaticStore.midlet.txnID;
				StaticStore.index = 122;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 68) {

			if (moreFlag) {
				StaticStore.menuDesc[142][3] = StaticStore.midlet.ifscBankSearchString;
				StaticStore.menuDesc[142][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 142;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				// StaticStore.midlet.IFSCList
				StaticStore.menuDesc[140][3] = StaticStore.midlet.IFSCList[selectedIndex][0];//
				StaticStore.index = 140;
				StaticStore.FromListScreen = false;
							StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 69) {

			if (moreFlag) {
				StaticStore.menuDesc[143][3] = StaticStore.ifscBankID;
				StaticStore.menuDesc[143][4] = StaticStore.midlet.ifscBranchSearchString;
				StaticStore.menuDesc[143][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 143;
				
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				// "IFSC","AP3S;Y",mPINString,"Bankid","location","4-4-N-Y-Y","","","1"
				// StaticStore.LogPrinter('i',"Coming inside the bakkk");
				
				
				StaticStore.menuDesc[141][3] = StaticStore.ifscBankID;
				StaticStore.menuDesc[141][4] = StaticStore.midlet.locationList[selectedIndex][0];
				if(StaticStore.isSearchAndRegister){
					StaticStore.menuDesc[141][13] = "Y";
				}else{
					StaticStore.menuDesc[141][13] = "N";
				}
				StaticStore.index = 141;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 70) {

			if (moreFlag) {
				if (StaticStore.isPincode) {
//					 {"Pincode","APAL;N","P","Pincode"," ","001","","6-6-N-N-N","","","4","true","true","N"},//74
//					StaticStore.menuDesc[74][2] = "P";
					StaticStore.menuDesc[74][3] = StaticStore.midlet.pinBranch;
					StaticStore.menuDesc[74][5] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[74][7] = "";
					StaticStore.LogPrinter('i',"StaticStore.menuDesc[74] --> "+Arrays.deepToString(StaticStore.menuDesc[74]));
					StaticStore.index = 74;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
//					StaticStore.menuDesc[51][2] = "L";
					StaticStore.menuDesc[51][3] = StaticStore.midlet.pinBranch;
					StaticStore.menuDesc[51][4] = StaticStore.midlet.locatorArea;
					StaticStore.menuDesc[51][5] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[51][7] = "";
					StaticStore.menuDesc[51][8] = "";
					StaticStore.index = 51;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}else {
				String temp = "";
				if (StaticStore.isPincode) {
					temp = StaticStore.midlet.atmSearch[selectedIndex][3] + ";" 
							+ StaticStore.midlet.atmSearch[selectedIndex][2]+";"+ StaticStore.midlet.pinBranch;
					StaticStore.LogPrinter('i',"Temp Dispaly>>> "+temp);
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "SAXX");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				} else {
//					temp = (StaticStore.midlet.pinBranch.trim().equals("") ? "": (StaticStore.midlet.pinBranch+";"))
//						
//							+ (StaticStore.midlet.locatorArea.trim().equals("") ? ""
//									: (StaticStore.midlet.locatorArea+";"))
//							+ StaticStore.midlet.atmSearch[selectedIndex][0]
//							+ ";"
//							+ StaticStore.midlet.txnID;
//					temp = StaticStore.midlet.atmSearch[selectedIndex][0]+";"+ StaticStore.midlet.atmSearch[selectedIndex][1]+";"+ StaticStore.midlet.atmSearch[selectedIndex][2]+";"+ StaticStore.midlet.atmSearch[selectedIndex][3]+";"+ StaticStore.midlet.atmSearch[selectedIndex][4];
					temp = StaticStore.midlet.atmSearch[selectedIndex][3] + ";" 
					+ StaticStore.midlet.atmSearch[selectedIndex][2]+";"+ StaticStore.midlet.atmSearch[selectedIndex][4];
					StaticStore.LogPrinter('i',"Temp Dispaly>>> "+temp);
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "SAYY");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				}
			}

		} else if (index == 71) {


			if (moreFlag) {
				if (StaticStore.isPincode) {
//					{"Pincode","AP1L;N","P","Pincode"," ","001","","6-6-N-N-N","","","4","true","true","N"},//75
					StaticStore.menuDesc[75][2] = "P";
					StaticStore.menuDesc[75][3] = StaticStore.midlet.pinBranch;
					StaticStore.menuDesc[75][5] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[75][7] = "";
					StaticStore.index = 75;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
//					{"Location","AP1L;N","L","Search City","Search Location","001","","0-20-ANW-N-N","0-20-ANW-N-N","","4","true","true","N"},//52
					StaticStore.menuDesc[52][2] = "L";
					StaticStore.menuDesc[52][3] = StaticStore.midlet.pinBranch;
					StaticStore.menuDesc[52][4] = StaticStore.midlet.locatorArea;
					StaticStore.menuDesc[52][5] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[52][7] = "";
					StaticStore.menuDesc[52][8] = "";
					StaticStore.index = 52;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}
			else {
				String temp = "";
				if (StaticStore.isPincode) {
					temp = StaticStore.midlet.branchSearch[selectedIndex][0] +";"+  StaticStore.midlet.pinBranch;
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "BSXX");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				} else {
					temp = StaticStore.midlet.branchSearch[selectedIndex][0];
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "BSYY");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				}
			}

		} else if (index == 72) {
			/*StaticStore.LogPrinter('i',"*************comming 2");
			 StaticStore.menuDesc[9][6] = getSelectedString().charAt(0) +
			 "";
			StaticStore.menuDesc[9][6] ="";
			StaticStore.LogPrinter('i',"*************comming 3");*/

			String timing = "";
			if(selectedIndex == 0){
				timing = "M";
			}else if(selectedIndex == 1){
				timing = "A";
			}else if(selectedIndex == 2){
				timing = "E";
			}else if(selectedIndex == 3){
				timing = "N";
			}
			saveList();
			StaticStore.legValue[4] = timing;
			String selectedString = "Economy";
			StaticStore.legValue[5] = selectedString;
			StaticStore.airline1st = true;
			StaticStore.isNoInput = true;
			try{
				StaticStore.indexCtr -= 2;
				StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
			}catch(Exception e){
				e.printStackTrace();
			}

		} else if (index == 73) {

			if (moreFlag) {
				StaticStore.menuDesc[141][3] = StaticStore.midlet.ifscBankName;
				StaticStore.menuDesc[141][4] = StaticStore.midlet.ifscLocationName;
				StaticStore.menuDesc[141][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 141;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				String tempStr = StaticStore.midlet.ifscBankName + ";"
				+ StaticStore.midlet.ifscLocationName + ";" +StaticStore.midlet.ifscArray[selectedIndex][0]
						+";"+ StaticStore.midlet.ifscArray[selectedIndex][1] +";"+StaticStore.midlet.txnID;
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "3S00");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}// comment for use

		} else if (index == 74) {

			if (moreFlag) {
				StaticStore.menuDesc[146][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[146][4] = "";
				StaticStore.index = 146;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}else {
				StaticStore.menuDesc[147][3] = menuItem[selectedIndex];
				StaticStore.index = 147;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 75) {
			StaticStore.midlet.savelistinit(); //Siva G
			if (selectedIndex == 0 && StaticStore.feedbackFlag) {
				saveList();
				StaticStore.index = 148;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			} else if (selectedIndex == 1 && StaticStore.feedbackFlag) {
				saveList();
				StaticStore.index = 149;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 2 && StaticStore.feedbackFlag) {
				saveList();
				StaticStore.index = 180;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}


		} else if (index == 76) {
             if (StaticStore.neftFlag) {
				 if (selectedIndex == 0) {
					 StaticStore.isSearchAndRegister = false;
					 saveList();
					 StaticStore.index = 139;
					 StaticStore.FromListScreen = false;
					 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 } else if (selectedIndex == 1) {
					 saveList();
					 StaticStore.index = 150;
					 StaticStore.FromListScreen = false;
					 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }
			 } else {
				 StaticStore.ToastDisplay(getActivity(), disableMsg);
			 }
        } else if (index == 77) {
			if (isEdit) {
				if (selectedIndex == 0) {
					//saveList();
					StaticStore.index = 151;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (selectedIndex == 1) {
					//saveList();
					StaticStore.index = 118;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			} else {
				StaticStore.isShortCode = selectedIndex == 0;
				StaticStore.midlet.writeinMemory(getActivity());
				if (StaticStore.indexCtr > 0)
					StaticStore.indexCtr--;
				StaticStore.ToastDisplay(getActivity(), "Your settings have been saved successfully");
			}

		} else if (index == 78) {


			if (moreFlag) {
				StaticStore.menuDesc[157][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[157][4] = "";
				StaticStore.index = 157;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				if (StaticStore.midlet.withInBankBenDetails[selectedIndex][0]
				                                                           .trim().equals("M")) {
					String tempStr = StaticStore.midlet.withInBankBenDetails[selectedIndex][1]
					                                                                        + ";"
					                                                                        + StaticStore.midlet.withInBankBenDetails[selectedIndex][2];
				} else {
					String tempStr = StaticStore.midlet.withInBankBenDetails[selectedIndex][1]
					                                                                        + ";"
					                                                                        + StaticStore.midlet.withInBankBenDetails[selectedIndex][2]
					                                                                                                                                 + ";"
					                                                                                                                                 + StaticStore.midlet.withInBankBenDetails[selectedIndex][3];
				}
			}

		} else if (index == 79) {

			if (moreFlag) {
				StaticStore.menuDesc[158][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[158][4] = "";
				StaticStore.index = 158;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {

				String tempStr = StaticStore.midlet.neftBenDetails[selectedIndex][0] + ";"
				+ StaticStore.midlet.neftBenDetails[selectedIndex][1] + ";"
				+ StaticStore.midlet.neftBenDetails[selectedIndex][2];//disp
			}// comment for use

		} else if (index == 80) {
			try
			{
				if (selectedIndex == 0) {
					if (StaticStore.totalAccounts < StaticStore.numberOfAccounts) {
						StaticStore.index = 159;
						saveList();
						StaticStore.FromListScreen = false;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
								DynamicCanvas.class));
					} else {
						StaticStore.ToastDisplay(getActivity(),"You cannot add more than "+StaticStore.numberOfAccounts+" accounts." );
					}
				} else if (selectedIndex == 1) {
					if (StaticStore.totalAccounts > 1) {
						saveList();
						Intent myIntent = StaticStore.midlet.getMyAccounts(true,getActivity());
						StaticStore.midlet.startFragment(getActivity(),myIntent);
					} else {
						StaticStore.ToastDisplay(getActivity(),"You have only one account.It cannot be deleted." );
					}// coment for use
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	private void doAction81To100(int index, int selectedIndex, boolean moreFlag) {
		if (index == 81) {

			StaticStore.selectedAccNumber = menuItem[selectedIndex].equals(
			"Primary Account") ? " " : menuItem[selectedIndex].trim();
			StaticStore.midlet.writeinMemory(getActivity());
			if (StaticStore.indexCtr > 0)
				StaticStore.indexCtr--;
			StaticStore.ToastDisplay(getActivity(),"Your settings have been saved successfully.");
		} else if (index == 82) {
		
			StaticStore.legValue[2] = selectedIndex == 0 ? "10" : "20";
			StaticStore.fromCurrentSavingList = true;
			StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);

		} else if (index == 83) {


			if (StaticStore.selectedAccNumber.equals(menuItem[selectedIndex])) {
				StaticStore.ToastDisplay(getActivity(),"This account is selected as default account. This cannot be de-registered");
			} else {
				StaticStore.menuDesc[160][3] = menuItem[selectedIndex].trim();
				StaticStore.index = 160;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			//comment for use

		} else if (index == 84) {
			if (moreFlag) {
				//{"Mobile Registration","APRM;Y",mPINString,StaticStore.bankCode,"Beneficiary mobile No.",
				//"Beneficiary Nickname","0001","4-4-N-Y-Y","","10-10-N-N-N","1-20-AN-N-N","","5","true","true"},//0
				StaticStore.menuDesc[0][4] = StaticStore.m2mRegMobileNumber;
				StaticStore.menuDesc[0][5] = StaticStore.m2mregMnemonicName;
				StaticStore.menuDesc[0][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[0][7] = "";
				StaticStore.menuDesc[0][9] = "";
				StaticStore.menuDesc[0][10] = "";
				StaticStore.menuDesc[0][StaticStore.menuDesc[0].length - 1] = "false";
				StaticStore.menuDesc[0][StaticStore.menuDesc[0].length - 2] = "false";
				StaticStore.index = 0;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}else {
				//saveList();
				StaticStore.midlet.m2mSelectedAccNumber = StaticStore.midlet.m2mAccounts[selectedIndex][0];
				String temp = StaticStore.m2mAccHolderName + ";"
				+ StaticStore.m2mRegMobileNumber + ";"
				+StaticStore.midlet.maskedAccNumberWB(StaticStore.midlet.m2mAccounts[selectedIndex][0]) + ";"
				+ StaticStore.m2mregMnemonicName;
				StaticStore.LogPrinter('i',">>>>"+temp);//disp
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "RM00");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}
		} else if (index == 85) {
			if(StaticStore.eCashFlag){
				if (selectedIndex == 0) {
					
					saveList();
					StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(170);
					StaticStore.midlet.listObject.setTag("EC");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));

				} else if (selectedIndex == 1 ) {
					saveList();
					StaticStore.index = 163;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (selectedIndex == 2 ) {
					saveList();
					StaticStore.index = 165;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else if (selectedIndex == 3) {
					saveList();
					StaticStore.index = 168;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}
		} else if (index == 86) {
			if (moreFlag) {
				StaticStore.menuDesc[164][3] = StaticStore.iCashMobNo;
				StaticStore.menuDesc[164][4] = StaticStore.iCashRemitterPin;
				StaticStore.menuDesc[164][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 164;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				String temp = "";
				int i = selectedIndex;
				temp = StaticStore.iCashMobNo + ";"
				+ StaticStore.midlet.iCashStatus[i][0] + ";"
				+ StaticStore.midlet.iCashStatus[i][1] + ";"
				+ StaticStore.midlet.iCashStatus[i][3] + ";"
				+ StaticStore.midlet.iCashStatus[i][2] + ";"
				+ StaticStore.midlet.iCashStatus[i][4] + ";"
				+ StaticStore.midlet.txnID;
				saveList();
				StaticStore.LogPrinter('i',"tempMSg"+temp);
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "ECXX");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}


			// comment for use
		} else if (index == 87) {

			if (moreFlag) {
				StaticStore.menuDesc[166][3] = StaticStore.iCashMobNo;
				StaticStore.menuDesc[166][4] = StaticStore.iCashRemitterPin;
				StaticStore.menuDesc[166][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 166;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				/*StaticStore.menuDesc[167][3] = StaticStore.iCashMobNo;
					StaticStore.menuDesc[167][4] = midlet.iCashForgetPin[getSelectedIndex()][0];
                                    String temp = "";
					int i = getSelectedIndex();
                                         temp = StaticStore.iCashMobNo + ";"
							+ midlet.iCashForgetPin[i][1] + ";"
							+ midlet.iCashForgetPin[i][2] + ";"
							+ midlet.iCashForgetPin[i][3] ;
					StaticStore.indexCtr--;
					//midlet.txnID ="";
				 */				StaticStore.menuDesc[167][3] = StaticStore.iCashMobNo;
				 StaticStore.menuDesc[167][4] = StaticStore.midlet.iCashForgetPin[selectedIndex][0];
				 saveList();		
				 String temp = "";
				 int i = selectedIndex;
				 temp = StaticStore.iCashMobNo + ";"
				 + StaticStore.midlet.iCashForgetPin[i][1] + ";"
				 + StaticStore.midlet.iCashForgetPin[i][2] + ";"
				 + StaticStore.midlet.iCashForgetPin[i][3] ;


				 Intent intent = new Intent(getActivity() , DisplayableView.class);
				 intent.putExtra("response",temp);
				 intent.putExtra("formName", "FCOMP");
				 StaticStore.midlet.startFragment(getActivity(),intent);
			}

		} else if (index == 88) {

			if (moreFlag) {
				StaticStore.menuDesc[169][3] = StaticStore.iCashMobNo;
				StaticStore.menuDesc[169][4] = StaticStore.iCashRemitterPin;
				StaticStore.menuDesc[169][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 169;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				StaticStore.menuDesc[170][3] = StaticStore.iCashMobNo;
				StaticStore.menuDesc[170][4] = StaticStore.midlet.iCashCancel[selectedIndex][0];
				saveList();
				StaticStore.index = 170;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 89) {

			if (moreFlag) {
//				StaticStore.menuDesc[173][0] = StaticStore.midlet.onlineFTPaymentFlag == true ? "Pay Beneficiary"
//						: "Beneficiary Deregstration";
//				StaticStore.menuDesc[173][1] = StaticStore.midlet.onlineFTPaymentFlag == true ? "APW3;Y"
//						: "AP5L;Y";
////				StaticStore.menuDesc[173][3] = StaticStore.midlet.onlineFTPaymentFlag == true ? "P"
////						: "D";
//				StaticStore.menuDesc[173][3] = StaticStore.benSearchString;
//				StaticStore.menuDesc[173][4] = StaticStore.midlet.nextStartRecNumber;
//				StaticStore.menuDesc[173][5] = "";
//				StaticStore.menuDesc[173][6] = "";
//				if((StaticStore.menuDesc[173][0]).equals("Beneficiary Deregstration")){
//					StaticStore.forDeReg="Delete Beneficiary";
//				}
//				StaticStore.index = 173;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
				//************
				if(StaticStore.midlet.onlineFTPaymentFlag){
//					{"Pay Beneficiary","APW3;Y",mPINString,"P","Search Beneficiary","001","4-4-N-Y-Y","","0-20-ANW-N-N","","4","true","true","Y"},//173
					StaticStore.menuDesc[173][0] = "Pay Beneficiary";
					StaticStore.menuDesc[173][3] =  "P";
					StaticStore.menuDesc[173][4] = StaticStore.benSearchString;
					StaticStore.menuDesc[173][5] = StaticStore.midlet.nextStartRecNumber;
					StaticStore.menuDesc[173][6] = "";
					StaticStore.menuDesc[173][7] = "";
					StaticStore.menuDesc[173][8] = "";
					StaticStore.index = 173;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}else{
			        StaticStore.tagType = "5L";
			        StaticStore.menuDesc[220] = new String[] {
	                  "Delete Beneficiary", "AP5L;Y", StaticStore.mPINString,StaticStore.benSearchString,StaticStore.midlet.nextStartRecNumber,
	                  "","","","3","false","false","Y"};
			    	StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}else {
				if (StaticStore.midlet.onlineFTPaymentFlag && !StaticStore.midlet.onlineFTDeregFlag) {
					// StaticStore.menuDesc[174][4] = getSelectedString();
					StaticStore.menuDesc[174][5] = menuItem[selectedIndex];
					//saveList();
					StaticStore.index = 174;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
					// DynamicCanvas(midlet,display,174));
				} else {
					StaticStore.menuDesc[91][0] = "Delete Beneficiary";
                    StaticStore.forDeReg="Delete Beneficiary";
                    StaticStore.menuDesc[91][1] = "AP5D;Y";
                    StaticStore.menuDesc[91][3] = menuItem[selectedIndex];
                    StaticStore.index = 91;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
		}
			}
		
	

		} else if (index == 90) {

			if (selectedIndex == 0) {
				//saveList();
				Intent myIntent = new mPAY().initiateUserOption(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}
		} else if (index == 91) {
			if (moreFlag) {
				StaticStore.menuDesc[178][4] = "";
				StaticStore.menuDesc[178][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 178;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
			else {
				saveList();
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",menuItem[selectedIndex]
				                                    + ";"
				                                    + StaticStore.midlet.regIMPSAccs[selectedIndex][1]);
				intent.putExtra("formName", "REGIMPS");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}
		} else if (index == 92) {
			if (selectedIndex == 0) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("OT");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 153);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);


			} else if (selectedIndex == 1) {
				saveList();
				StaticStore.index = 178;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));


			} else if (selectedIndex == 2) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("OT");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 152);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);

			}else if (selectedIndex == 3) {
				StaticStore.recCount = 0;
				//				saveList();
				//				StaticStore.index = 210;
				//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
				//						DynamicCanvas.class));
				saveList();
				Intent myIntent = StaticStore.midlet.getIMPSCancelOptions(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 4) {
				saveList();
				Intent myIntent =StaticStore.midlet
				.getRegisteredIMPSOptions(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}

		} else if (index == 93) {

			if (moreFlag) {
				StaticStore.menuDesc[182][3] = StaticStore.regSearchString;
				StaticStore.menuDesc[182][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[182][5] = "";
				StaticStore.menuDesc[182][6] = "";
				StaticStore.index = 182;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				//				StaticStore.menuDesc[183][3] = getSelectedString();
				//				saveList();
				StaticStore.menuDesc[183][3] = menuItem[selectedIndex];
				StaticStore.index = 183;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		} else if (index == 94) {


			if (moreFlag) {
				StaticStore.menuDesc[184][3] = StaticStore.regSearchString;
				StaticStore.menuDesc[184][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[184][5] = "";
				StaticStore.menuDesc[184][6] = "";
				StaticStore.index = 184;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				//	StaticStore.menuDesc[185][3] = getSelectedString();
				StaticStore.menuDesc[185][3] = menuItem[selectedIndex];
				//saveList();
				StaticStore.index = 185;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}



		} else if (index == 95) {


			if (moreFlag) {
				StaticStore.menuDesc[186][3] = StaticStore.regSearchString;
				StaticStore.menuDesc[186][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[186][5] = "";
				StaticStore.menuDesc[186][6] = "";
				StaticStore.index = 186;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				String tempStr = StaticStore.withinDereg[selectedIndex][0]
				                                                        + ";"
				                                                        + StaticStore.withinDereg[selectedIndex][1];
				saveList();//disp
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "MBEN");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}



		} else if (index == 96) {


			if (moreFlag) {
				StaticStore.menuDesc[187][3] = StaticStore.regSearchString;
				StaticStore.menuDesc[187][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[187][5] = "";
				StaticStore.menuDesc[187][6] = "";
				StaticStore.index = 187;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else {
				saveList();
//				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
//	            String tempArr []  = tempData.split("#");
//	            int i;
//	            for (i = 0; i < tempArr.length-1; i++) {
//	                if(StaticStore.withinDereg[selectedIndex][2].startsWith(tempArr[i])){
//	                  break;  
//	                }
//	            } 
//	            String acctypename;
//	            if(StaticStore.withinDereg[selectedIndex][2].equals(tempArr[i].substring(0, tempArr[i].indexOf("*")))){
//	            	acctypename = tempArr[i];
//	            	acctypename = acctypename.substring(acctypename.indexOf('*')+1);
//	            	acctypename = acctypename.substring(0,acctypename.indexOf('*'));
//	            }else{
//					acctypename = StaticStore.withinDereg[selectedIndex][2];
//				}
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,StaticStore.withinDereg[selectedIndex][2].trim());
//	          Account Type Display Code END
	           
				String tempStr = StaticStore.withinDereg[selectedIndex][0]+ ";"+ StaticStore.withinDereg[selectedIndex][1]+ ";"+ acctypename;
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "ABEN");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}
		} else if (index == 97) {
			
		} else if (index == 98) {
			
		} else if (index == 99) {
			StaticStore.midlet.isUsedForBack = false;
			//			StaticStore.midlet.doAction(index);
		}
		else if (index == 100) {
//			StaticStore.LogPrinter('i',"*********before********"+StaticStore.beFlag);
//			if (selectedIndex == 0) {
//				if (StaticStore.beFlag) {
//					saveList();
//					String[] menuItem = StaticStore.accountNumbers;
//					Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
//					mPayIntent.putExtra("listIndex", 101);
//					mPayIntent.putExtra("menuItem", menuItem);
//					mPayIntent.putExtra("listHeader", "Select A/C");
//					mPayIntent.putExtra("more", false);
//					StaticStore.midlet.startFragment(getActivity(),mPayIntent);
//				} else {
//					StaticStore.ToastDisplay(getActivity(), disableMsg);
//				}
//			} else if (selectedIndex == 1) {
//				if (StaticStore.msFlag) {
//					saveList();
//
//
//				} else {
//					StaticStore.ToastDisplay(getActivity(), disableMsg);
//				}
//			} else if (selectedIndex == 2) {
//				saveList();
//				Intent myIntent =StaticStore.midlet
//				.getPayRegList(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
//
//			} else if (selectedIndex == 3) {
//				saveList();
//				Intent myIntent =StaticStore.midlet
//				.getMyServices(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
//			} else if (selectedIndex == 4) {
//				saveList();
//				Intent myIntent =StaticStore.midlet
//				.get_Settings(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
//			} else if (selectedIndex == 5) {
//				saveList();
//				//				StaticStore.midlet.responseMessages = RmsStore.readInboxRecordStore(
//				//						RmsStore.ResponseInbox, StaticStore.midlet.responseMessages);
//				//comment for use
//				if (StaticStore.midlet.responseMessages == null)
//					StaticStore.midlet.responseMessages = new String[0];
//
//				//comment for use
//
//			} else if (selectedIndex == 6) {
//				saveList();
//				StaticStore.index = 5;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			} else if (selectedIndex == 7) {
//				saveList();
//				Intent myIntent =StaticStore.midlet
//				.getLogOnlyMenu(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
//			} else if (selectedIndex == 8) {
//				saveList();
//				StaticStore.index = 129;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			} else if (selectedIndex == 9) {
////				saveList();
////				Intent myIntent =StaticStore.midlet
////				.getFeedBackMenu(getActivity());
////				StaticStore.midlet.startFragment(getActivity(),myIntent);
//			} else if (selectedIndex == 10) {
//				saveList();
//				String message = "Customer;" + StaticStore.buildVersion + ";FSSNeT";
//			}
		}
	}

	private void doAction101To120(int index, int selectedIndex, boolean moreFlag) {
		// all comment by v

		if (index == 101) {

			if (moreFlag) {
				StaticStore.menuDesc[212][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[212][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[212][5] = "";
				StaticStore.menuDesc[212][6] = "";
				
				StaticStore.index = 212;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}else {
				String temp="";
				saveList();
				temp = StaticStore.midlet.impsDetails[selectedIndex][0]+";"+StaticStore.midlet.impsDetails[selectedIndex][1]+";"+StaticStore.midlet.impsDetails[selectedIndex][2]+";"+StaticStore.midlet.impsDetails[selectedIndex][3];
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "D900");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}

		} else if (index == 102) {


			if (moreFlag) {
				StaticStore.menuDesc[215][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[215][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[215][5] = "";
				StaticStore.menuDesc[215][6] = "";
				StaticStore.index = 215;
				
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));


			}else {
				saveList();
				String temp="";
				temp = StaticStore.midlet.vmtDetails[selectedIndex][0]+";"+StaticStore.midlet.vmtDetails[selectedIndex][1];
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "V600");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}

			// MadhanModified
		} else if (index == 103) {
			//showRespectiveIntraScreen(1,selectedIndex,getActivity());
			String title = "";
			if(selectedIndex == 0){
				title = "Mr";
			}else if(selectedIndex == 1){
				title = "Mrs";
			}else if(selectedIndex == 2){
				title = "Miss";
			}
			new AirlineInput(title);
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else if (index == 104) {
			//empty
		} else if (index == 105) {

			// NEFT
			StaticStore.midlet.neftPaymentFlag = true;
			StaticStore.midlet.neftDeregFlag = false;
			StaticStore.menuDesc[87][0] = "Pay Beneficiary";
			StaticStore.menuDesc[87][3] = "P";
			saveList();
			StaticStore.index = 87;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		
		} else if (index == 109) {
			if (moreFlag) {
				StaticStore.tagType = "APK9";
				StaticStore.menuDesc[189][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[189][4] = "";
				StaticStore.index = 189;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.LogPrinter('i',"index >>>>><<<<<   sel 112");
				String temp = StaticStore.midlet.onlineAccStatList[selectedIndex][0] + ";"
				+ StaticStore.midlet.onlineAccStatList[selectedIndex][1]+ ";" +StaticStore.midlet.onlineAccStatList[selectedIndex][2]+ ";"
				+StaticStore.midlet.onlineAccStatList[selectedIndex][3]+ ";" +StaticStore.midlet.onlineAccStatList[selectedIndex][4];   
				//                                                         + ";TXNID:" + StaticStore.midlet.txnID;
				saveList();
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "APK9Stat");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}
		} else if (index == 106) {
			StaticStore.midlet.savelistinit(); 
			if(selectedIndex == 0){
				 saveList();
				 Intent myIntent = StaticStore.midlet.getRegisteredIMPSOptions(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);	
			} else if (selectedIndex == 1) {
				 saveList();
				 Intent myIntent = StaticStore.midlet.getRegisteredIMPSAccOptions(getActivity());
				 StaticStore.midlet.startFragment(getActivity(), myIntent);
			}else if(selectedIndex == 2){
		         saveList();
				 Intent myIntent = StaticStore.midlet.getP2UMenus(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
		    }else if(selectedIndex == 3){
		         saveList();
				 Intent myIntent = StaticStore.midlet.getRegisteredMerchantIMPSOptions(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
		    }else if(selectedIndex == 4){
		         saveList();
		         StaticStore.tagType = "APMO";
				 StaticStore.menuDesc[220] = new String []{"Generate OTP","APMO;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
				 StaticStore.index = 220;
				 StaticStore.FromListScreen = false;
				 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		    }else if(selectedIndex == 5){
		         saveList();
				 StaticStore.index = 178;
				 StaticStore.FromListScreen = false;
				 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		    }else if(selectedIndex == 6){
		         saveList();
				 String[] menuItem = this.getAccessibleAccArray("OT");
				 Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				 mPayIntent.putExtra("listIndex", 152);
				 mPayIntent.putExtra("menuItem", menuItem);
				 mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				 mPayIntent.putExtra("more", false);
				 StaticStore.midlet.startFragment(getActivity(),mPayIntent);   
		    }else if(selectedIndex == 7){
		         StaticStore.recCount = 0;
				 saveList();
				 Intent myIntent = StaticStore.midlet.getIMPSCancelOptions(getActivity());
				 StaticStore.midlet.startFragment(getActivity(),myIntent);
		    }
       } else if (index == 107) {
			//empty

			if(!moreFlag){
				//	StaticStore.menuDesc[191][4] = getSelectedString();
				StaticStore.menuDesc[191][4] = menuItem[selectedIndex];
				saveList();
				StaticStore.index = 191;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				//			byte dataNumbers = (byte)(getTokenCount(StaticStore.midlet.messageForList,'#')+1);
				//			searchString = getSearchString(StaticStore.midlet.messageForList);

				//		recCount += dataNumbers;
				//commeny for use
				StaticStore.menuDesc[190][5] = "";
				StaticStore.menuDesc[190][6] = "";
				StaticStore.menuDesc[190][3] = searchString;
				StaticStore.menuDesc[190][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 190;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}




			//				StaticStore.menuDesc[191][4] = getSelectedString();
			//				}else{
			//					byte dataNumbers = (byte)(getTokenCount(StaticStore.midlet.messageForList,'#')+1);
			//					searchString = getSearchString(StaticStore.midlet.messageForList);
			//					recCount += dataNumbers;
			//		            StaticStore.midlet.nextStartRecNumber = ""+(recCount+1);
			//					StaticStore.menuDesc[190][5] = "";
			//					StaticStore.menuDesc[190][6] = "";
			//					StaticStore.menuDesc[190][3] = searchString;
			//					StaticStore.menuDesc[190][4] = StaticStore.midlet.nextStartRecNumber;
			//				}

		} else if (index == 108) {

			if(!moreFlag){
				//				StaticStore.menuDesc[192][3] = getSelectedString();
				StaticStore.menuDesc[192][3] = menuItem[selectedIndex];
				//saveList();
				StaticStore.index = 192;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				//					byte dataNumbers = (byte)(getTokenCount(StaticStore.midlet.messageForList,'#')+1);
				//					searchString = getSearchString(StaticStore.midlet.messageForList);
				//					recCount += dataNumbers;
				//		            StaticStore.midlet.nextStartRecNumber = ""+(recCount+1);
				//					StaticStore.menuDesc[193][5] = "";
				//					StaticStore.menuDesc[193][6] = "";
				//					StaticStore.menuDesc[193][3] = searchString;
				//					StaticStore.menuDesc[193][4] = StaticStore.midlet.nextStartRecNumber;
				//comment for use
				StaticStore.index = 193;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}

		} else if (index == 110) {
			if (moreFlag) {
				StaticStore.tagType = "APK4";
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.selectedIndex = selectedIndex;
				//				saveList();
				StaticStore.menuDesc[220] =new String[] {};
				StaticStore.menuDesc[220] = new String []{"Pay Beneficiary","APK5;Y",StaticStore.mPINString,"","Amount (Rs.)","Remarks","","","1-10-ND-N-N","0-25-AN-N-N","4","false","false","N"};
				StaticStore.tagType = "APK5";
				StaticStore.menuDesc[220][3] = getSelectedString();
				StaticStore.index = 220;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));

			}
		} else if (index == 111) {
			if (moreFlag) {
				StaticStore.tagType = "AP6L";
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {

				//				saveList();
				StaticStore.menuDesc[220] =new String[] {};
				StaticStore.menuDesc[220] = new String []{"Delete Beneficiary","AP6D;Y",StaticStore.mPINString,"","","","2","false","false","N"};
				StaticStore.tagType = "AP6D";
				StaticStore.menuDesc[220][3] = getSelectedString();
				StaticStore.index = 220;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));

			}
		} else if (index == 112) {
			if (moreFlag) {
				StaticStore.menuDesc[220] = new String []{"Beneficiary Details","AP4W;Y",StaticStore.mPINString,"Search Beneficiary","1","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
				StaticStore.tagType = "AP4W";
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				try{
					StaticStore.LogPrinter('i',"index >>>>><<<<<   sel 112");
					String OnlineAcctype;
					// OnlineAcctype =
					// StaticStore.midlet.getAccTypeDisplay(StaticStore.midlet.onlineAccDetailsList[selectedIndex][3].trim().toUpperCase());
					String tempData = RmsStore.readRecordStore(
							RmsStore.parsedRecords,
							RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
					StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+ tempData);
					tempData = tempData.substring(0, tempData.length() - 1);
//					String tempArr[] = tempData.split("#");
//					int i;
//					for (i = 0; i < tempArr.length - 1; i++) {
//
//						if (StaticStore.midlet.onlineAccDetailsList[selectedIndex][3].startsWith(tempArr[i])) {
//
//							break;
//						}
//						
//					}
//					String acctypename = tempArr[i];
//					acctypename = acctypename.substring(acctypename.indexOf('*') + 1);
//					acctypename = acctypename.substring(0, acctypename.indexOf('*'));
					
					 String acctypename = StaticStore.midlet.getAccountTypes(tempData,StaticStore.midlet.onlineAccDetailsList[selectedIndex][3].trim());
					
					String temp = StaticStore.midlet.onlineAccDetailsList[selectedIndex][2]
							+ ";"
							+ acctypename
							+ ";"
							+ StaticStore.midlet.onlineAccDetailsList[selectedIndex][4]
							+ ";"
							+ StaticStore.midlet.onlineAccDetailsList[selectedIndex][0];
					// + ";TXNID:" + StaticStore.midlet.txnID;
					saveList();
					Intent intent = new Intent(getActivity(),
							DisplayableView.class);
					intent.putExtra("response", temp);
					intent.putExtra("formName", "AP4W");
					StaticStore.midlet.startFragment(getActivity(),intent);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} else if (index == 113) {
			saveList();
			StaticStore.index = 200;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		} else if (index == 114) {
			 if (selectedIndex == 0) {
				  saveList();
	        	  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(153);
				  StaticStore.midlet.listObject.setTag("OT");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             }else if (selectedIndex == 1) {
			      saveList();
			      StaticStore.index = 171;
			      StaticStore.IMPS2M_REG_Flag = true;
			      StaticStore.FromListScreen = false;
			      StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}else if (selectedIndex == 2) {
                  saveList();
				  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(163);
				  StaticStore.midlet.listObject.setTag("OT");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
            }else if (selectedIndex == 3){
				  StaticStore.recCount = 0;
				  saveList();
				  StaticStore.index = 212;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		    } else if (selectedIndex == 4) {
                  saveList();
			      StaticStore.tagType = "AP5L";
				  StaticStore.menuDesc[220] =new String[] {};
			      StaticStore.menuDesc[220] = new String[]{"Delete Beneficiary","AP5L;Y",StaticStore.mPINString," Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"};
				  StaticStore.index = 220;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		    }
		} else if (index == 115) {
//			String Acctype = null;
//			Acctype = getAccType();
//			StaticStore.menuDesc[220][4] = Acctype;
//			StaticStore.legValue[2]= menuItem[selectedIndex];
//			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
//			Intent intent = new Intent(getActivity() , DisplayableView.class);
//			intent.putExtra("response",msg);
//			intent.putExtra("formName", "APQM_transfers");
//			StaticStore.accpaymentFlag = false;
//			StaticStore.midlet.startFragment(getActivity(),intent);	
		} else if (index == 116) {
//		

		} else if (index == 117) {//IMPS NEFT

			if(selectedIndex == 0){

				//				{"Quick IMPS","APQI;Y",mPINString,"beneficiary mobile No.","beneficiary MMID No.","Amount (Rs.)","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-9-ND-N-N","4"}
				saveList();
				menuItem = getAccessibleAccArray("K1");
				StaticStore.LogPrinter('i',"MENUMENU"+menuItem.length);
//				for (int i = 0; i < menuItem.length; i++) {
//					StaticStore.LogPrinter('i',">>>heheheheheheh>>"+menuItem[i]);	
//				}
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 224);
				mPayIntent.putExtra("menuItem", getAccessibleAccArray("K1"));
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);
			}else if(selectedIndex == 1){
				saveList();
				Intent myIntent =	 StaticStore.midlet.getRegisteredIMPSAccOptions(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
				//			}else if(selectedIndex == 2){
				//				saveList();
				//				StaticStore.menuDesc[220] =new String[] {};
				//				StaticStore.menuDesc[220] = new String []{"Account Statement","APK9;Y",StaticStore.mPINString,"1","4-4-N-Y-Y","","2"};
				//				StaticStore.tagType = "APK9";
				//				StaticStore.index = 220;
//				StaticStore.FromListScreen = false;
				//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}
		} else if (index == 118) { 
			 if(selectedIndex == 0){
				  saveList();
	        	  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(224);
				  StaticStore.midlet.listObject.setTag("K1");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             }else if(selectedIndex == 1){
				  saveList();
				  String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				  if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					   StaticStore.isAccTypeRefresh = false;
					   StaticStore.tagType = "APTF";
					   StaticStore.accTypeCompleteData = "";
					   StaticStore.indexBeforeAccTypeInitiation = -3;
					   StaticStore.index = 220;
     			       StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			       StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }else{
					   StaticStore.menuDesc[220] =new String[] {};
					   StaticStore.menuDesc[220] = new String []{"Add Beneficiary","APK2;Y",StaticStore.mPINString,"A/C No.","","IFS Code","Nickname","4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-20-ANW-N-N","5","false","false","Y"};
					   StaticStore.tagType = "APK2";
					   StaticStore.index = 220;
					   StaticStore.accpaymentFlag = true;
					   StaticStore.FromListScreen = false;
					   StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }
			}else if(selectedIndex == 2){
				  saveList();
				  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(225);
				  StaticStore.midlet.listObject.setTag("K4");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
            }else if(selectedIndex == 3){
				  saveList();
				  String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				  StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				  if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					   StaticStore.isAccTypeRefresh = false;
					   StaticStore.tagType = "APTF";
					   StaticStore.accTypeCompleteData = "";
					   StaticStore.indexBeforeAccTypeInitiation = -9;
					   StaticStore.index = 220;
     			       StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			       StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }else{
			           StaticStore.menuDesc[220] =new String[] {};
				       StaticStore.menuDesc[220] = new String []{"Beneficiary Details","AP4W;Y",StaticStore.mPINString,"Search Beneficiary","1","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
				       StaticStore.tagType = "AP4W";
				       StaticStore.index = 220;
				       StaticStore.FromListScreen = false;
				       StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }
			}else if(selectedIndex == 4){
				  saveList();
				  StaticStore.menuDesc[220] =new String[] {};
				  StaticStore.menuDesc[220] = new String []{"Delete Beneficiary","AP6L;Y",StaticStore.mPINString,"Search Beneficiary","1","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
				  StaticStore.tagType = "AP6L";
				  StaticStore.index = 220;
				  StaticStore.FromListScreen = false;
				  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
            }
	} else if (index == 119) {
			StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			StaticStore.legValue[2]= menuItem[selectedIndex];
			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
			Intent intent = new Intent(getActivity() , DisplayableView.class);
			intent.putExtra("response",msg);
			intent.putExtra("formName", "APK1");
			StaticStore.accpaymentFlag = false;
			StaticStore.midlet.startFragment(getActivity(),intent);	
		} else if (index == 120) {
			StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			StaticStore.legValue[2]= StaticStore.midlet.accTypeArr[selectedIndex][0];
//			String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
//			Intent intent = new Intent(getActivity() , DisplayableView.class);
//			intent.putExtra("response",msg);
//			intent.putExtra("formName", "APK2");
			StaticStore.accpaymentFlag = false;
//			StaticStore.midlet.startFragment(getActivity(),intent);
			StaticStore.index = 220;
			StaticStore.tagType = "APK2";
			StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
		}

	}
	private void doAction121To140(int index, int selectedIndex, boolean moreFlag) {
		if (index == 121) {
			StaticStore.midlet.savelistinit(); //Siva G
			StaticStore.midlet.isUsedForBack = false;
			if (selectedIndex == 0) {
				if (StaticStore.beFlag) {
					saveList();
					StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(154);
					StaticStore.midlet.listObject.setTag("BE");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
            } else if (selectedIndex == 1) {
                if (StaticStore.msFlag) {
					saveList();
					StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(155);
					StaticStore.midlet.listObject.setTag("MS");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
                } else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
            } else if (selectedIndex == 2) {
            	saveList();
    			StaticStore.accpaymentFlag = false;
    			StaticStore.menuDesc[220] = new String[] {"Deposit Account Balance","APLF;Y",StaticStore.mPINString,"TDA","001","4-4-N-Y-Y","","","3","true","true","Y"};
    			StaticStore.index = 220;
    			StaticStore.tagType = "APLF";
    			StaticStore.FromListScreen = false;
    			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
            } else if (selectedIndex == 3) {	
            	saveList();
    			StaticStore.accpaymentFlag = false;
    			StaticStore.menuDesc[220] = new String [] {"Loan Balance","APLA;Y",StaticStore.mPINString,"LAA","001","4-4-N-Y-Y","","","3","true","true","Y"};
    			StaticStore.index = 220;
    			StaticStore.tagType = "APLA";
    			StaticStore.FromListScreen = false;
    			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
            } else if (selectedIndex == 4) {
                 saveList();
				 StaticStore.FromListScreen = false;
				 StaticStore.menuDesc[220] = new String []{"Transaction History","APTH;Y",StaticStore.mPINString,"001","4-4-N-Y-Y","","2","false","false","N"};				
				 StaticStore.index = 220;
				 StaticStore.tagType = "APTH";
			     StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
            } else if (selectedIndex == 5) {
            	saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(279);
				StaticStore.midlet.listObject.setTag("LI");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             } else if (selectedIndex == 6) {
             	saveList();
 				StaticStore.midlet.listObject = new ListObject();
 				StaticStore.midlet.listObject.setIndex(280);
 				StaticStore.midlet.listObject.setTag("LO");
 				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
 				StaticStore.midlet.listObject.setMore(false);
 				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
              }
		}else if (index == 122) {
			StaticStore.midlet.savelistinit(); //Siva G
			//completely commented by me
			StaticStore.midlet.isUsedForBack = false;


			if (selectedIndex == 0) {
				if (StaticStore.stopChequeFlag) {
					saveList();
					StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(132);
					StaticStore.midlet.listObject.setTag("L4");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
//					String[] menuItem = this.getAccessibleAccArray("L4");
//					Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
//					mPayIntent.putExtra("listIndex",132 );
//					mPayIntent.putExtra("menuItem", menuItem);
//					mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
//					mPayIntent.putExtra("more", false);
//					StaticStore.midlet.startFragment(getActivity(),mPayIntent);
					
					
					
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
//			StaticStore.ToastDisplay(StaticStore.context,"The services will be provided shortly");
			} else if (selectedIndex == 1) {
				if (StaticStore.chequeStatusFlag) {
					saveList();
					StaticStore.midlet.listObject = new ListObject();
					StaticStore.midlet.listObject.setIndex(133);
					StaticStore.midlet.listObject.setTag("LW");
					StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
					StaticStore.midlet.listObject.setMore(false);
					StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 2) {
				StaticStore.LogPrinter('i',"StaticStore.cardHotListFlag >>"+StaticStore.cardHotListFlag);
				if (StaticStore.cardHotListFlag) {
					saveList();
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(248);
						StaticStore.midlet.listObject.setTag("HL");
						StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 3) {
				saveList();
				StaticStore.FromListScreen = false;
				StaticStore.menuDesc[220] = new String []{"Transaction History","APTH;Y",StaticStore.mPINString,"001","4-4-N-Y-Y","","2","false","false","N"};				
				StaticStore.index = 220;
				StaticStore.tagType = "APTH";
			    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
//			}else if (selectedIndex == 4) {
//				if(StaticStore.IRCTC_Card_Flag)
//				{
//					saveList();
//					Intent myIntent = StaticStore.midlet.getIrctcCardMenu(getActivity());
//					StaticStore.midlet.startFragment(getActivity(),myIntent);
//				}else
//				{
//					StaticStore.ToastDisplay(getActivity(), disableMsg);
//				}
//
//			}else if (selectedIndex == 5) {
//				//StaticStore.ToastDisplay(getActivity(), disableMsg);
////				saveList();
////				Intent myIntent = StaticStore.midlet
////				.getP2UMenus(getActivity());
////				StaticStore.midlet.startFragment(getActivity(),myIntent);
//				saveList();
//				Intent myIntent = StaticStore.midlet.getICashMenu(getActivity());
//				StaticStore.midlet.startFragment(getActivity(),myIntent);
				
			
			} /*else if (selectedIndex == 6) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getLocatorMenu(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 7) {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}*/


			//				if (selectedIndex == 0 && StaticStore.cardLimitFlag) {
			//					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				} else if (selectedIndex == 1 && StaticStore.linkingCustFlag) {
			//					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				} else if (selectedIndex == 2 && StaticStore.blockCardFlag) {
			//					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				} else 
			//					if (selectedIndex == 0&& StaticStore.locatorFlag) {
			//					saveList();
			//					Intent myIntent = StaticStore.midlet
			//					.getLocatorList(getActivity());
			//			StaticStore.midlet.startFragment(getActivity(),myIntent);
			//
			////				} else if (selectedIndex == 4 && StaticStore.fixedDepositFlag) {
			////					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				} else if (selectedIndex == 1 && StaticStore.eCashFlag) {
			//					saveList();
			//					Intent myIntent = StaticStore.midlet
			//					.getICashMenu(getActivity());
			//			StaticStore.midlet.startFragment(getActivity(),myIntent);
			////				} else if (selectedIndex == 6 && StaticStore.loyalityFlag) {
			////					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				}else{
			//					StaticStore.ToastDisplay(getActivity(), disableMsg);
			//				}

		} else if (index == 123) {


			if (selectedIndex == 0) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getSearch("ATM",getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 1) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getSearch("Branch",getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);

			}

		} else if (index == 124) {

			if(selectedIndex == 0){
				StaticStore.menuDesc[208][6] = "S";
				StaticStore.menuDesc[208][7] = "";
				StaticStore.menuDesc[208][8] = "";
				StaticStore.menuDesc[208][9] = "";
				StaticStore.menuDesc[208][10] = "";
				StaticStore.menuDesc[208][11] = "";
				StaticStore.menuDesc[208][12] = "";
				StaticStore.menuDesc[208][21] = "";
				StaticStore.menuDesc[208][22] = "";
				StaticStore.menuDesc[208][23] = "";
				StaticStore.menuDesc[208][24] = "";
				StaticStore.menuDesc[208][25] = "";
				StaticStore.menuDesc[208][26] = "";
				StaticStore.fromAccountList = false;
				saveList();
				StaticStore.index = 208;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else if(selectedIndex == 1){
				StaticStore.menuDesc[208][6] = "T";
				StaticStore.menuDesc[208][7] = "Address1";
				StaticStore.menuDesc[208][8] = "Address2";
				StaticStore.menuDesc[208][9] = "Address3";
				StaticStore.menuDesc[208][10] = "City";
				StaticStore.menuDesc[208][11] = "Pincode";
				StaticStore.menuDesc[208][12] = "Contact No.";
				StaticStore.menuDesc[208][21] = "1-20-AN-N-N";
				StaticStore.menuDesc[208][22] = "1-20-AN-N-N";
				StaticStore.menuDesc[208][23] = "1-20-AN-N-N";
				StaticStore.menuDesc[208][24] = "1-20-AN-N-N";
				StaticStore.menuDesc[208][25] = "6-7-N-N-N";
				StaticStore.menuDesc[208][26] = "10-15-N-N-N";
				StaticStore.fromAccountList = false;
				saveList();
				StaticStore.index = 208;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}



		} else if (index == 125) {
			//v			//for new product
			if(moreFlag){
				StaticStore.menuDesc[211][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[211][4] = "";
				StaticStore.index = 211;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				saveList();
				StaticStore.menuDesc[204][3] = StaticStore.midlet.newProduct[selectedIndex][0];
				StaticStore.index = 204;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
				//				Intent myIntent = midlet
				//				.getLogOnlyMenu(getActivity());
				//		StaticStore.midlet.startFragment(getActivity(),myIntent);
			}

		} else if (index == 126) {

			if (selectedIndex == 0) {
				saveList();
				Intent myIntent = StaticStore.midlet
				.getVMTList(getActivity());
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}

		} else if (index == 127) {


			StaticStore.midlet.isUsedForBack = false;
			if (selectedIndex == 0) {
				saveList();
				StaticStore.index = 189;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 1) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("OT");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 156);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);

			} else if (selectedIndex == 2) {
				saveList();
				StaticStore.index = 193;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else if (selectedIndex == 3) {
				StaticStore.recCount = 0;
				saveList();
				StaticStore.index = 215;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}


		} else if (index == 128) {


			if (selectedIndex == 0 && StaticStore.cashPickupFlag) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("G1");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 157);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);

			} else if (selectedIndex == 1 && StaticStore.cahDeliveryFlag) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("G2");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 158);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);

			} else if (selectedIndex == 2 && StaticStore.chequePickUpFlag) {
				saveList();
				String[] menuItem = this.getAccessibleAccArray("G3");
				Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
				mPayIntent.putExtra("listIndex", 159);
				mPayIntent.putExtra("menuItem", menuItem);
				mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
				mPayIntent.putExtra("more", false);
				StaticStore.midlet.startFragment(getActivity(),mPayIntent);

			}else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}


		} else if (index == 130) {



			StaticStore.midlet.isUsedForBack = false;
			if (selectedIndex == 0) {
				if (StaticStore.airlineFlag) {
					saveList();
					Intent myIntent = StaticStore.midlet
					.getAirlineMenu(getActivity());
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			} else if (selectedIndex == 1) {
				if (StaticStore.movieFlag) {
					StaticStore.midlet.movieLoadingMsg = "Fetching theater list, please wait for the response";
					saveList();
					String[] menuItem = this.getAccessibleAccArray("OT");
					Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
					mPayIntent.putExtra("listIndex", 160);
					mPayIntent.putExtra("menuItem", menuItem);
					mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
					mPayIntent.putExtra("more", false);
					StaticStore.midlet.startFragment(getActivity(),mPayIntent);

				} else {
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}
			}


		} else if (index == 131) {
			if(StaticStore.mobileRechargeFlag){
				if (selectedIndex == 0 ) {
					StaticStore.rechargeStatus = false;
					saveList();
					String[] menuItem = this.getAccessibleAccArray("R5");
					Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
					mPayIntent.putExtra("listIndex", 161);
					mPayIntent.putExtra("menuItem", menuItem);
					mPayIntent.putExtra("listHeader", StaticStore.accNumberHeadingName);
					mPayIntent.putExtra("more", false);
					StaticStore.midlet.startFragment(getActivity(),mPayIntent);
				} else if (selectedIndex == 1) {
					StaticStore.recCount = 0;
					StaticStore.rechargeStatus = true;
					String category = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT);
					if(category.equals("EMPTY") || StaticStore.isCategoryRefresh){
						StaticStore.tagType = "AP1T";
						StaticStore.menuDesc[220] = new String []{"Recharge Status Enquiry","AP1T;Y",StaticStore.mPINString,"0001","4-4-N-Y-Y","","2","false","false","Y"};
						StaticStore.index = 220;
						StaticStore.FromListScreen = false;
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
								DynamicCanvas.class));
					}else{
						StaticStore.midlet.rechargeDynamicInputs.assignRechargeValues(category);
						StaticStore.midlet.rechargeCategory = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryName();
//						StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getRechargeCategory(getActivity()));
						
						StaticStore.tagType = "APXX";//recharge change
						StaticStore.menuDesc[220] = new String []{"Recharge","APXX;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","Y"};
		                StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
								DynamicCanvas.class));
					}
				}
			}else{
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}

		} else if (index == 132) {
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.index = 43;
			StaticStore.menuDesc[43][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 133){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.index = 71;
			StaticStore.menuDesc[71][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 134){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.index = 72;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 135){
			//empty
		}else if(index == 136){
			//empty
			if(StaticStore.fixedDepositFlag){
				if (selectedIndex == 0 ) {

				} else if (selectedIndex == 1) {

				} else if (selectedIndex == 2 ) {

				} else if (selectedIndex == 3 ) {
				} else if (selectedIndex == 4 ) {

				}}else{
					StaticStore.ToastDisplay(getActivity(), disableMsg);
				}



		}else if(index == 137){
			StaticStore.fromChequeList = true;
			//StaticStore.menuDesc[201][3] = getSelectedString();
			//StaticStore.menuDesc[201][3] = menuItem[selectedIndex];
			StaticStore.legValue[1] = menuItem[selectedIndex];
			//comment for use
			//dc.navigateTo(StaticStore.index);
			try{
				StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
			}catch(Exception e){
				e.printStackTrace();
			}


		}else if(index == 138){
			saveList();
			if (selectedIndex == 0) {
				StaticStore.tagType = "AP2O";
				StaticStore.menuDesc[220] = new String []{"Offers","AP2O;N","001","","1","false","false","N"};
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
//			} else if (selectedIndex == 1) {
//				StaticStore.tagType = "APAG";
//				StaticStore.menuDesc[220] = new String []{"New Account Opening","APAG;N","","","1","false","false","N"};
//				StaticStore.index = 220;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
			}else if (selectedIndex == 1) {
				StaticStore.FromListScreen = false;
				StaticStore.tagType = "APLR";
				StaticStore.menuDesc[220] = new String []{"Loan Calculator","APLR;N","Loan Amount (Rs.)","Interest Rate (in %)","Tenure (in months)","Processing Fee (in %)","1-9-N-N-N","1-5-ND-N-N","1-4-N-N-N","1-5-ND-N-N","4","true","true","N"};
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
		}

	}
	private void doAction141To160(int index, int selectedIndex, boolean moreFlag) {
		// All comments by v
		if(index == 141){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 92;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 142){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			if (StaticStore.shopingFlag) {
				StaticStore.fromAccountList = true;
				StaticStore.FromListScreen = false;
				StaticStore.index = 55;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}



		}else if(index == 143){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 48;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));


		}else if(index == 144){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 201;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 145){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 202;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 146){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 203;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 147){

			StaticStore.recCount = 0;

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			//saveList();
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 211;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 148){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 146;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));


		}else if(index == 149){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.index = 9;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 150){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.menuDesc[6][1] = "APAI;Y";
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 6;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 151){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 37;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 152){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.menuDesc[177][3]= StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 177;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 153){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.accpaymentFlag = true;
			StaticStore.fromAccountList = true;
			StaticStore.index = 179;
			
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 154){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 15;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 155){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 16;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 156){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 190;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 157){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 197;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 158){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 198;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 159){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			saveList();
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			//StaticStore.index = 199;
			StaticStore.index = 217;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 160){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.menuDesc[27][9] = "0001";
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 27;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}

	}
	private void doAction161To180(int index, int selectedIndex, boolean moreFlag) {
		// all comments by v
		if(index == 161){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			if (StaticStore.mobileRechargeFlag) {
				//dfgdfgdfg
				String category = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT);
				String benNickName = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT_NICK);
				if(category.equals("EMPTY") || StaticStore.isCategoryRefresh){
					StaticStore.tagType = "AP1T";
					StaticStore.menuDesc[220] = new String []{"Recharge","AP1T;Y",StaticStore.mPINString,"0001","4-4-N-Y-Y","","2","false","false","Y"};
					StaticStore.index = 220;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}else{
					StaticStore.midlet.rechargeDynamicInputs.assignRechargeValues(category);
					StaticStore.midlet.rechargeDynamicInputs.recharge.setBeneficiaryList(benNickName);
					StaticStore.midlet.rechargeCategoryId = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryId();
                	StaticStore.midlet.rechargeCategory = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryName();
//              	    StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getRechargeCategory(getActivity()));
                	StaticStore.tagType = "APXX";//recharge change
                	StaticStore.index = 220;
					StaticStore.menuDesc[220] = new String []{"Recharge","APXX;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","Y"};
	                StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}

			} else {
				StaticStore.ToastDisplay(getActivity(), disableMsg);
			}

		}else if(index == 162){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			// NEFT
			StaticStore.accpaymentFlag = true;
			StaticStore.midlet.neftPaymentFlag = true;
			StaticStore.midlet.neftDeregFlag = false;
			StaticStore.menuDesc[87][0] = "Pay Beneficiary";
			StaticStore.menuDesc[87][3] = "P";
			StaticStore.index = 87;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 163){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.menuDesc[173][0] = "Pay Beneficiary";
			StaticStore.menuDesc[173][3] = "P";
			StaticStore.accpaymentFlag = true;
			StaticStore.index = 173;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 164){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.accpaymentFlag = true;
			StaticStore.index = 100;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));


		}else if(index == 165){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.accpaymentFlag = true;
			StaticStore.index = 102;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 166){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			saveList();
			StaticStore.index = 161;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 167){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 43;
			StaticStore.menuDesc[43][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 168){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.index = 71;
			StaticStore.menuDesc[71][5] = StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N";
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 169){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.menuDesc[12][3] = "A";
			StaticStore.menuDesc[12][0] = "Instant Bill Payment";
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 12;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 170){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 161;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));


		}else if(index == 171){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			saveList();
			StaticStore.fromAccountList = true;
			Intent myIntent = StaticStore.midlet.get_dispatchType(getActivity());
			StaticStore.midlet.startFragment(getActivity(),myIntent);

		}
		else if(index == 172){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];

			StaticStore.menuDesc[210][3] = StaticStore.selectedAccNumber;
			StaticStore.singleFlag=false;
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 210;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if(index == 173){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.menuDesc[220] = new String[] {
            "Instant Pay", "APQM;Y",StaticStore.mPINString,"Beneficiary Mobile No.","Amount (Rs.)","Remarks",
            "4-4-N-Y-Y","10-10-N-N-N","1-10-ND-N-N","0-25-AN-N-N","4","true","true","N"};
            StaticStore.index = 220;
            StaticStore.tagType = "APQM";
            StaticStore.FromListScreen = false;
            StaticStore.accpaymentFlag = true;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 174){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
			if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
				StaticStore.isAccTypeRefresh = false;
				StaticStore.FromListScreen = false;
				StaticStore.tagType = "APTF";
				StaticStore.accTypeCompleteData = "";
				StaticStore.indexBeforeAccTypeInitiation = -6;
				StaticStore.index = 220;
 			   StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
 			  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				//mid: 12866
			StaticStore.menuDesc[220] = new String[] {
                    "Instant Pay", "APQT;Y",StaticStore.mPINString,"Beneficiary A/C No.","","Amount (Rs.)","Remarks",
                    "4-4-N-Y-Y","15-15-N-N-N","","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"};
            StaticStore.index = 220;
            StaticStore.tagType = "APQT";
            StaticStore.accpaymentFlag = true;
            StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			}
		}else if(index == 175){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
			
			if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
				StaticStore.isAccTypeRefresh = false;
				StaticStore.tagType = "APTF";
				StaticStore.accTypeCompleteData = "";
				StaticStore.indexBeforeAccTypeInitiation = -7;
				StaticStore.index = 220;
 			   StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
 			  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
			StaticStore.menuDesc[220] = new String[]{
		            "Instant Pay", "APQN;Y",StaticStore.mPINString,"Beneficiary A/C No.","","Beneficiary IFS Code","Amount (Rs.)","Remarks", //mid 13898

		            "4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-7-N-N-N","0-25-AN-N-N","6","true","true","N" //NEFT Amount 1-7_N only
		            };

        	StaticStore.accpaymentFlag = true;
        	StaticStore.fromAccountList = true;
        	StaticStore.FromListScreen = false;

        	StaticStore.tagType = "APQN";
            StaticStore.index = 220;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			}
		}else if (index == 176){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
			
			if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
				StaticStore.isAccTypeRefresh = false;
				StaticStore.tagType = "APTF";
				StaticStore.accTypeCompleteData = "";
				StaticStore.indexBeforeAccTypeInitiation = -4;
				StaticStore.index = 220;
 			   StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
 			  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				StaticStore.fromAccountList = true;
				StaticStore.accpaymentFlag = true;
				StaticStore.tagType = "AP1U";
				StaticStore.menuDesc[220]  = new String[] {
		        "Instant Pay", "AP1U;Y",StaticStore.mPINString,"Beneficiary AADHAAR No.","","Amount (Rs.)","Remarks",
		        "4-4-N-Y-Y","12-12-N-N-N","","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"};
				 StaticStore.index = 220;
				 StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}
			
		
	}else if (index == 177){
	StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
	StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
	StaticStore.tagType = "AP4U";
	StaticStore.menuDesc[220]  = new String[] {
			  "Pay Beneficiary", "AP4U;Y",StaticStore.mPINString,"Search Beneficiary","001",
			    "4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
	 StaticStore.index = 220;
	
	StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
			DynamicCanvas.class));
	} else if (index == 178) {//P2U - 1U
		
		StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
		StaticStore.legValue[2]= menuItem[selectedIndex];
		String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4];
		StaticStore.LogPrinter('i',"<><><><msg>"+msg);
		Intent intent = new Intent(getActivity() , DisplayableView.class);
		intent.putExtra("response",msg);
		intent.putExtra("formName", "AP1U");
		StaticStore.accpaymentFlag = false;
		StaticStore.midlet.startFragment(getActivity(),intent);	
	} else if (index == 179) {//P2U - 2U
		StaticStore.accpaymentFlag = false;
		StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
		StaticStore.legValue[2]= StaticStore.midlet.accTypeArr[selectedIndex][0];
		//String msg = StaticStore.legValue[1]+";"+StaticStore.legValue[2]+";"+StaticStore.legValue[3]+";"+StaticStore.legValue[4]+";"+StaticStore.legValue[5];
		StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
	} else if (index == 180) {// M2A
		
		StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
		StaticStore.legValue[2]= StaticStore.midlet.accTypeArr[selectedIndex][0];
		
	String msg = StaticStore.legValue[1] + ";" + getSelectedString()
	+ ";Rs." + StaticStore.legValue[3] + ";"+ StaticStore.legValue[4];
	StaticStore.LogPrinter('i',"STTTTTTTTTTTTTTT -->"+msg);
	Intent intent = new Intent(getActivity() , DisplayableView.class);
	intent.putExtra("response",msg);
	intent.putExtra("formName", "QT00");
	StaticStore.accpaymentFlag = false;
	StaticStore.midlet.startFragment(getActivity(),intent);

	}
	}
	private void doAction181To199(int index, int selectedIndex, boolean moreFlag) {
		if (index == 181) {
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;

			StaticStore.index = 40;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if (index == 182) {
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;

			StaticStore.index = 42;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if (index == 183) {
			
			
		}

	}
	private void doAction200To220(int index, int selectedIndex, boolean moreFlag) {
		if (index == 200) {// for indus

			StaticStore.menuDesc[209][3] = "A";
			if (selectedIndex == 0) {
				StaticStore.menuDesc[209][4] = "S";
				StaticStore.fromList = false;
				StaticStore.menuDesc[209][5] = "";
				saveList();
				StaticStore.index = 219;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 1) {
				saveList();
				//StaticStore.legValue[2] = "L";
				StaticStore.menuDesc[209][4] = "L";
				Intent myIntent = StaticStore.midlet.get_LongTermDeposit(getActivity());

				StaticStore.midlet.startFragment(getActivity(),myIntent);
			} else if (selectedIndex == 2) {
				//StaticStore.legValue[2] = "T";
				StaticStore.menuDesc[209][4] = "T";
				saveList();
				Intent myIntent = StaticStore.midlet.get_SpecialTenor(getActivity());

				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}

		} else if (index == 201) {

			if (selectedIndex < 9) {
				//					StaticStore.legValue[3] = getSelectedString().substring(0,
				//							1);
				StaticStore.menuDesc[209][5] = menuItem[selectedIndex].substring(0,
						1);
			} else if (selectedIndex == 9) {
				//					StaticStore.legValue[3] = getSelectedString().substring(0,
				//							2);
				StaticStore.menuDesc[209][5]  = menuItem[selectedIndex].substring(0,
						2);
			}
			StaticStore.fromList = true;
			saveList();
			StaticStore.index = 209;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		} else if (index == 202) {
			StaticStore.menuDesc[209][5]  = menuItem[selectedIndex].substring(0, 3);
			StaticStore.fromList = true;
			saveList();
			StaticStore.index = 209;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		} else if (index == 203) {

			if (selectedIndex == 0) {
				StaticStore.legValue[5] = "A";
			} else if (selectedIndex == 1) {
				StaticStore.legValue[5] = "C";
			}
			saveList();
			Intent myIntent = StaticStore.midlet.get_InterestInstructions(getActivity());

			StaticStore.midlet.startFragment(getActivity(),myIntent);

		} else if (index == 204) {

			StaticStore.fromList = false;
			if (selectedIndex == 0) {
				StaticStore.legValue[6] = "S";
			} else if (selectedIndex == 1) {
				StaticStore.legValue[6] = "C";
			}
			String message = "I wish to link the fixed deposit to my source saving account for any debit.In case of insufficient balance,I authorise the bank to break my FD units and honour the transaction";
			saveList();//disp
			Intent intent = new Intent(getActivity() , DisplayableView.class);
			intent.putExtra("response",message);
			intent.putExtra("formName", "fd");
			StaticStore.midlet.startFragment(getActivity(),intent);
		} else if (index == 205) {

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.LogPrinter('i',">>>>>>>>>>>StaticStore.selectedAccType"+StaticStore.selectedAccType);
			saveList();
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.index = 218;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		}else if (index == 206) {

			if(selectedIndex == 0){
				StaticStore.menuDesc[209][3] = "E";
			}else if(selectedIndex == 1){
				StaticStore.menuDesc[209][3] = "O";
			}
			saveList();
			Intent myIntent = StaticStore.midlet.get_NewLongTermDeposit(getActivity());

			StaticStore.midlet.startFragment(getActivity(),myIntent);

		} else if (index == 207) {
			StaticStore.menuDesc[209][4] = "L";
			StaticStore.fromList = true;
			StaticStore.menuDesc[209][5] = menuItem[selectedIndex].substring(0, 1);
			saveList();
			StaticStore.index = 209;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));

		} else if (index == 208) {//neft mod

			if(selectedIndex == 0){
				StaticStore.isSearchAndRegister = true;
				saveList();
				StaticStore.index = 139;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else if (selectedIndex == 1) {
				if (StaticStore.isSearchAndRegister) {
					StaticStore.isSearchAndRegister = false;
					StaticStore.menuDesc[82][2] = StaticStore.mPINString;
					StaticStore.menuDesc[82][7] = "4-4-N-Y-Y";
					StaticStore.globalMPIN = "";
					saveList();
					StaticStore.index = 82;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[82][7] = "4-4-N-Y-Y";
					saveList();
					StaticStore.index = 82;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
			}

		}else if (index == 209) {//neft mod//empty

			if(selectedIndex == 0){

			}

		}
		else if (index == 210) {//neft mod

			if (moreFlag) {
				StaticStore.menuDesc[213][3] = StaticStore.regSearchString;
				StaticStore.menuDesc[213][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[213][5] = "";
				StaticStore.menuDesc[213][6] = "";

				StaticStore.LogPrinter('i',Arrays.deepToString(StaticStore.menuDesc[213]));
				StaticStore.index = 213;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

			}
			
			else {
				//	 StaticStore.menuDesc[214][3] = getSelectedString();
//				StaticStore.menuDesc[214][3] = menuItem[selectedIndex];
//				saveList();
//				StaticStore.index = 214;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
				saveList();
				//				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
//	            String tempArr []  = tempData.split("#");
//	            int i;
//	            for (i = 0; i < tempArr.length-1; i++) {
//	                if(StaticStore.midlet.neftDetails[selectedIndex][4].startsWith(tempArr[i])){
//	                  break;  
//	                }
//	            } 
//	            String acctypename;
//	            if(StaticStore.midlet.neftDetails[selectedIndex][4].equals(tempArr[i].substring(0, tempArr[i].indexOf("*")))){
//	            	acctypename = tempArr[i];
//	            	acctypename = acctypename.substring(acctypename.indexOf('*')+1);
//	            	acctypename = acctypename.substring(0,acctypename.indexOf('*'));
//	            }else{
//					acctypename = StaticStore.midlet.neftDetails[selectedIndex][4];
//				}
//	           
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,StaticStore.midlet.neftDetails[selectedIndex][4].trim());
//	          Account Type Display Code END

				String tempStr = StaticStore.midlet.neftDetails[selectedIndex][0] //Nickname
						+ ";"
						+ StaticStore.midlet.neftDetails[selectedIndex][1]
						+ ";"
						+ StaticStore.midlet.neftDetails[selectedIndex][2]
				        + ";"
				        + StaticStore.midlet.neftDetails[selectedIndex][3]
				        + ";"
				        + acctypename
				        + ";"
				        + StaticStore.midlet.neftDetails[selectedIndex][5];//ifsc code
				StaticStore.LogPrinter('i',":::::::NL00 tempStr "+tempStr);
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "NL00");
				StaticStore.midlet.startFragment(getActivity(),intent);
			}

		}else if (index == 211){ 
			if (selectedIndex == 0) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(174);
				StaticStore.midlet.listObject.setTag("FT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
            }else if (selectedIndex == 1) {
                String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					StaticStore.isAccTypeRefresh = false;
					StaticStore.tagType = "APTF";
					StaticStore.accTypeCompleteData = "";
					StaticStore.indexBeforeAccTypeInitiation = 1;
					StaticStore.index = 220;
     			    StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}else{
					saveList();
					StaticStore.index = 1;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}
            } else if (selectedIndex == 2) {
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(165);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
            } else if (selectedIndex == 3) {
				String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					StaticStore.isAccTypeRefresh = false;
					StaticStore.tagType = "APTF";
					StaticStore.accTypeCompleteData = "";
					StaticStore.indexBeforeAccTypeInitiation = -10;
					StaticStore.index = 220;
     			    StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
     			    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}else{
					saveList();
				    StaticStore.index = 187;
				    StaticStore.FromListScreen = false;
				    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}
            } else if (selectedIndex == 4) {
				saveList();
				StaticStore.index = 184;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
            }
		}else if (index == 212){ 
			if(selectedIndex == 0){
				saveList();
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(172);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
			} else if (selectedIndex == 1) {
				StaticStore.menuDesc[210][3] = "ALL";
				saveList();
				StaticStore.index = 210;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}
		}else if (index== 213){
			 if (selectedIndex == 0) {
				  saveList();
	        	 StaticStore.midlet.listObject = new ListObject();
				 StaticStore.midlet.listObject.setIndex(176);
				 StaticStore.midlet.listObject.setTag("FT");
				 StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				 StaticStore.midlet.listObject.setMore(false);
				 StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             }else if (selectedIndex == 1) {
				 saveList();
				 String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				 if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					 StaticStore.isAccTypeRefresh = false;
					 StaticStore.tagType = "APTF";
					 StaticStore.accTypeCompleteData = "";
					 StaticStore.indexBeforeAccTypeInitiation = -5;
					 StaticStore.index = 220;
					 StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
					 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }else{
					 StaticStore.fromAccountList = true;
					 StaticStore.accpaymentFlag = true;
					 StaticStore.tagType = "AP2U";
					 StaticStore.menuDesc[220] = new String []  {
							"Add Beneficiary", "AP2U;Y",StaticStore.mPINString,"Beneficiary AADHAAR No.","","Nickname",
							"4-4-N-Y-Y","12-12-N-N-N","","1-20-ANW-N-N","4","true","true","Y"};
					 StaticStore.index = 220;
					 StaticStore.FromListScreen = false;
					 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				  }
             } else if (selectedIndex == 2) {
				  saveList();
				  StaticStore.midlet.listObject = new ListObject();
				  StaticStore.midlet.listObject.setIndex(177);
				  StaticStore.midlet.listObject.setTag("OT");
				  StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				  StaticStore.midlet.listObject.setMore(false);
				  StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
             } else if (selectedIndex == 3) {
				 String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				 StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
				 if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
					 StaticStore.isAccTypeRefresh = false;
					 StaticStore.tagType = "APTF";
					 StaticStore.accTypeCompleteData = "";
					 StaticStore.indexBeforeAccTypeInitiation = -8;
					 StaticStore.index = 220;
	 			     StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
	 			     StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }else{
				     saveList();
				     StaticStore.tagType = "AP6U";
				     StaticStore.menuDesc[220] = new String [] {"Beneficiary Details","AP6U;Y",StaticStore.mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
				     StaticStore.index = 220;
				     StaticStore.FromListScreen = false;
				     StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				 }
		     } else if (selectedIndex == 4) {
				saveList();
				StaticStore.tagType = "AP7U";
				StaticStore.menuDesc[220] = new String []  {"Delete Beneficiary","AP7U;Y",StaticStore.mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
				StaticStore.index = 220;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
             }
    }else if(index == 214){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.tagType = "CH";
			StaticStore.menuDesc[220] = new String []{"Generate Card","APCH;Y",StaticStore.mPINString,"001","4-4-N-Y-Y","","2","true","true","N"};
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 215){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.tagType = "TP";
			StaticStore.menuDesc[220] = new String []{"Top-up","APTP;Y",StaticStore.mPINString,"Card Number","4-4-N-Y-Y","16-16-N-N-N","2","true","true","N"};
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 216){
			StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			StaticStore.legValue[2]= menuItem[selectedIndex];
		String msg = StaticStore.legValue[1] + ";" + getSelectedString()
		+ ";" + StaticStore.legValue[3] + ";Rs."+ StaticStore.legValue[4]+ ";"+ StaticStore.legValue[5];
		Intent intent = new Intent(getActivity() , DisplayableView.class);
		intent.putExtra("response",msg);
		intent.putExtra("formName", "QN00");
		StaticStore.accpaymentFlag = false;
		StaticStore.midlet.startFragment(getActivity(),intent);
		
		}
	}
	private void doAction221To240(int index, int selectedIndex, boolean moreFlag) {
		if (index == 221) {
			if (moreFlag) {
				StaticStore.tagType = "AP1T";
				StaticStore.menuDesc[220] = new String []{"Recharge","AP1T;Y",StaticStore.mPINString,"0001","","","2","false","false","Y"};
				StaticStore.index = 220;
				StaticStore.menuDesc[220][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][4] = "";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				StaticStore.date = new Date().getTime(); 
				
				StaticStore.rechargeSelcetedCategoryID = StaticStore.midlet.rechargeDynamicInputs.recharge.getUniqueCategoryID(selectedIndex);
				StaticStore.forRechargeSelectedIndex = selectedIndex;
				StaticStore.midlet.rechargeDynamicInputs.recharge.setBeneficiaryList(RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT_NICK));
					String menuArr[] = StaticStore.midlet.rechargeDynamicInputs.getBeneficiaryListByCatID(StaticStore.rechargeSelcetedCategoryID);
					saveList();
					
					if(menuArr != null){
						String[] menuItem = new String[]{"New Recharge","Recharge Status Enquiry","Beneficiary List","Beneficiary Deregistration"}; 
						Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
						mPayIntent.putExtra("listIndex", 222);
						mPayIntent.putExtra("menuItem", menuItem);
						mPayIntent.putExtra("listHeader", "Recharge");
						mPayIntent.putExtra("more", false);
						StaticStore.midlet.startFragment(getActivity(),mPayIntent);
					}else{
						String[] menuItem = new String[]{"New Recharge","Recharge Status Enquiry"};
						Intent mPayIntent = new Intent(getActivity(), ListSelection.class);
						mPayIntent.putExtra("listIndex", 222);
						mPayIntent.putExtra("menuItem", menuItem);
						mPayIntent.putExtra("listHeader", "Recharge");
						mPayIntent.putExtra("more", false);
						StaticStore.midlet.startFragment(getActivity(),mPayIntent);
					}
				
			}
		}else if(index == 222){
			if(moreFlag){
				//	AP8T
				StaticStore.menuDesc[220] = new String [] {"Beneficiary Sync","AP8T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,StaticStore.midlet.nextStartRecNumber,"","","","3","true","true","N"};
				StaticStore.index = 220;
				StaticStore.tagType = "AP8T";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}else{
				saveList();
				StaticStore.date = new Date().getTime(); 
				
				if(selectedIndex == 0){
					InitiateRequestAP2T();
				}else if(selectedIndex == 1){
					StaticStore.recCount = 0;
					StaticStore.rechargeStatus = true;
					StaticStore.tagType = "AP7T";
					StaticStore.index = 195;
					StaticStore.menuDesc[195][3] = StaticStore.rechargeSelcetedCategoryID; 
//					StaticStore.menuDesc[195][4] = "Enter Transaction ID (Optional)";
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
				}else if(selectedIndex == 2){
					StaticStore.forRechargeBeneList = true;
					StaticStore.menuDesc[220] = new String [] {"Beneficiary Sync","AP8T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"1","","","","3","true","true","N"}; //4-4-N-Y-Y
					StaticStore.index = 220;
					StaticStore.tagType = "AP8T";
				    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
//					saveList();
//					Intent myIntent =StaticStore.midlet
//					.get_ReChargeBeneficiaryListDeregistrationList(StaticStore.context,251);
//					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}else if(selectedIndex == 3){
					StaticStore.forRechargeBeneDereg = true;
					StaticStore.menuDesc[220] = new String [] {"Beneficiary Sync","AP8T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"1","","","","3","true","true","N"}; //4-4-N-Y-Y
					StaticStore.index = 220;
					StaticStore.tagType = "AP8T";
				    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
//					saveList();
//					Intent myIntent =StaticStore.midlet
//					.get_ReChargeBeneficiaryListDeregistrationList(StaticStore.context,252);
//					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}
			}
		}else if(index == 223){
				//				free
		}else if(index == 224){ // NEFT IMPS
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			StaticStore.LogPrinter('i',"::::::::::::::type::::"+type);
			
			if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
				StaticStore.isAccTypeRefresh = false;
				StaticStore.tagType = "APTF";
				StaticStore.accTypeCompleteData = "";
				StaticStore.indexBeforeAccTypeInitiation = -2;
				StaticStore.index = 220;
 			   StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
 			  StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				StaticStore.fromAccountList = true;
				StaticStore.menuDesc[220] =new String[] {};
				StaticStore.menuDesc[220] = new String []{"Instant Pay","APK1;Y",StaticStore.mPINString,"Enter Beneficiary A/C No.","","Enter Beneficiary IFS Code","Enter Amount (Rs.)","Enter Remarks","4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-10-ND-N-N","0-25-AN-N-N","6","false","false","N"};
				StaticStore.tagType = "APK1";
				StaticStore.index = 220;
				StaticStore.accpaymentFlag = true;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}
			
		}else if(index == 225){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.menuDesc[220] =new String[] {};
			StaticStore.menuDesc[220] = new String []{"Pay Beneficiary","APK4;Y",StaticStore.mPINString,"Search Beneficiary","1","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
			StaticStore.tagType = "APK4";
			StaticStore.index = 220;
			StaticStore.accpaymentFlag = true;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		}else if(index == 226){
			if(moreFlag){
				StaticStore.menuDesc[220] = new String []{"Registered Bill Payment","APDW;Y",StaticStore.mPINString,"","Search State (optional)","Search City (optional)","001","","","0-20-ANW-N-N","0-20-ANW-N-N","","5","true","true","Y"}; //mid:13071 max len 12 to 20
				StaticStore.menuDesc[220][3] = StaticStore.midlet.tempDW[1];
				StaticStore.menuDesc[220][4] = StaticStore.midlet.tempDW[2];
				StaticStore.menuDesc[220][5] = StaticStore.midlet.tempDW[3];
				StaticStore.menuDesc[220][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][9] = "";
				StaticStore.menuDesc[220][10] = "";
				StaticStore.tagType = "APDW";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));

			}else{
				StaticStore.LogPrinter('i',"index == 226 un_Reg_Billers List Of Sub-Category"+Arrays.deepToString(StaticStore.midlet.un_Reg_Billers));
				StaticStore.LogPrinter('i',"Sub Cat flag >>>>>>"+StaticStore.midlet.un_Reg_Billers[0][2].trim().toUpperCase());
				if(StaticStore.midlet.un_Reg_Billers[selectedIndex][0].equals("TNEB")){
					StaticStore.forTNEBNote = true;
				}
				if(StaticStore.midlet.un_Reg_Billers[selectedIndex][2].trim().toUpperCase().equals("Y")){
					StaticStore.menuDesc[220] = new String [] {"Biller List","APDX;Y",StaticStore.mPINString,StaticStore.midlet.un_Reg_Billers[selectedIndex][0],"001","","","","3","true","true","Y"};
					StaticStore.index = 220;
					StaticStore.tagType = "APDX";
				}else{
					StaticStore.menuDesc[220] = new String [] {"Biller List","APDT;Y",StaticStore.mPINString,StaticStore.midlet.un_Reg_Billers[selectedIndex][0],"","","2","true","true","Y"};
					StaticStore.index = 220;
					StaticStore.tagType = "APDT";
					if(StaticStore.midlet.un_Reg_Billers[selectedIndex][0].equals("COCTAX")){
						StaticStore.forCOCTAX = true;
					}
				}

				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}

		}else if(index == 227)
		{
			if(moreFlag){
				StaticStore.menuDesc[220][4]= StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.tagType = "APDX";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
			}else{
				try
				{
					saveList();
					StaticStore.LogPrinter('i',"== 227 StaticStore.midlet.un_Reg_Pres_Billers>>>>> "+StaticStore.midlet.un_Reg_Pres_Billers[0][2]);
					StaticStore.LogPrinter('i'," selectedIndex  ==> "+selectedIndex);
					String[] tempReq = null;
					StaticStore.IsPresentment = false;
					if(StaticStore.midlet.un_Reg_Pres_Billers[0][2].trim().toUpperCase().equals("Y")){
						StaticStore.IsPresentment = true;
						tempReq = new String[9+StaticStore.midlet.Sub_CatInput.length];
						tempReq[0]= "Instant Bill Payment";
						tempReq[1]= "AP2B;Y";
						tempReq[2]= StaticStore.mPINString;
						tempReq[3]= StaticStore.midlet.un_Reg_Pres_Billers[0][0];
						tempReq[4]= StaticStore.midlet.sub_CatId[selectedIndex][0];
						int pos = 0;
						for(int i = 0,j=0;i<StaticStore.midlet.Sub_CatInput.length/2;i++,j+=2)
						{
							tempReq[5+i]=StaticStore.midlet.Sub_CatInput[j];
							pos=5+i;
							StaticStore.LogPrinter('i',"DX RESP DY Frame -->"+Arrays.deepToString(tempReq));
						}
						StaticStore.LogPrinter('i',"tempReq.length >>>>"+tempReq.length);
						tempReq[pos+=1]="";
						tempReq[pos+=1]="";
						tempReq[pos+=1]="";
						for(int i = pos+1,j=1;i< tempReq.length-1;i++,j+=2)
						{
							tempReq[i] = StaticStore.midlet.Sub_CatInput[j]; //err
							StaticStore.LogPrinter('i',"DX RESP DY Frame -->"+Arrays.deepToString(tempReq));
						}
						tempReq[1+pos+StaticStore.midlet.Sub_CatInput.length/2] = String.valueOf(3+(StaticStore.midlet.Sub_CatInput.length/2));
						StaticStore.LogPrinter('i',"temp REQ"+Arrays.deepToString(tempReq));
					}else
					{			
						String[] temp = new String[StaticStore.midlet.Sub_CatInput.length+2];	
						StaticStore.LogPrinter('i',"Temp.length > >> > >> siva>"+temp.length );
						temp[StaticStore.midlet.Sub_CatInput.length]="Amount (Rs.)";
						temp[StaticStore.midlet.Sub_CatInput.length+1]="1-10-ND-N-N";
						StaticStore.LogPrinter('i',"New Sub_CatInput>>>> "+Arrays.deepToString(temp));
						System.arraycopy(StaticStore.midlet.Sub_CatInput, 0, temp, 0, StaticStore.midlet.Sub_CatInput.length);
						StaticStore.LogPrinter('i',"New Sub_CatInput>>>> "+Arrays.deepToString(StaticStore.midlet.Sub_CatInput));

						//						StaticStore.midlet.Sub_CatInput = new String[temp.length];  
						//						System.arraycopy(temp, 0, StaticStore.midlet.Sub_CatInput, 0, temp.length);
						//						
						StaticStore.LogPrinter('i',"New Sub_CatInput>>>> "+Arrays.deepToString(temp));
						tempReq = new String[9+temp.length];
						tempReq[0]= "Instant Bill Payment";
						tempReq[1]= "APDM;Y";
						tempReq[2]= StaticStore.mPINString;
						tempReq[3]= StaticStore.midlet.un_Reg_Pres_Billers[0][0];
						tempReq[4]= StaticStore.midlet.sub_CatId[selectedIndex][0]; //"N";
						int pos = 0;
						for(int i = 0,j=0;i<temp.length/2;i++,j+=2)
						{
							tempReq[5+i]=temp[j];
							pos=5+i;
						}
						StaticStore.LogPrinter('i',"tempReq.length >>>>"+tempReq.length);
						tempReq[pos+=1]="";
						tempReq[pos+=1]="";
						tempReq[pos+=1]="";
						for(int i = pos+1,j=1;i< tempReq.length-1;i++,j+=2)
						{
							tempReq[i] = temp[j];
						}

						tempReq[1+pos+temp.length/2] = String.valueOf(3+(temp.length/2));
						StaticStore.LogPrinter('i',"temp REQ"+Arrays.deepToString(tempReq));
					}	
					StaticStore.menuDesc[222] = new String [tempReq.length+3];

					for(int i=0;i<tempReq.length;i++)
					{
						StaticStore.menuDesc[222][i] = tempReq[i];
					}
					StaticStore.LogPrinter('i',"Before Menu Desc[222]>>>>"+Arrays.deepToString(StaticStore.menuDesc[222]));
					//					,"true","true","Y"
					StaticStore.menuDesc[222][tempReq.length] = "true" ;
					StaticStore.menuDesc[222][tempReq.length+1] = "true" ;
					StaticStore.menuDesc[222][tempReq.length+2] = "Y";//StaticStore.IsPresentment?"Y":"N" ;

					StaticStore.LogPrinter('i',"Menu Desc[222]>>>>"+Arrays.deepToString(StaticStore.menuDesc[222]));
					StaticStore.index = 222;
					String Message =null;
					if(StaticStore.IsPresentment)
					{
						Message = StaticStore.midlet.un_Reg_Pres_Billers[0][1]+";"+ StaticStore.midlet.sub_CatId[selectedIndex][1];
						StaticStore.tagType = "APDY";
					}else {
						String Fx_Var[] = StaticStore.midlet.tempDX[3].split("\\|",-1);
						StaticStore.LogPrinter('i',"Varscgjhg>?>>>> "+Arrays.deepToString(Fx_Var));
						Message = StaticStore.midlet.un_Reg_Pres_Billers[0][1]+";"+ StaticStore.midlet.sub_CatId[selectedIndex][1]+";"+"Rs. "+Fx_Var[0]+";"+Fx_Var[1]+"%";
						StaticStore.tagType = "APDM";
					}
					StaticStore.LogPrinter('i',"Message>>>>"+Message);
					//String Message = StaticStore.midlet.un_Reg_Pres_Billers[0][1]+";"+ StaticStore.midlet.sub_CatId[selectedIndex][1];
					//					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response",Message);
					intent.putExtra("formName", "APDX");
					StaticStore.midlet.startFragment(getActivity(),intent);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}

		}else if(index == 228)
		{
				//				saveList();
				StaticStore.index = 220;
				StaticStore.tagType = "APDZ";
				String PaymentFlag = getSelectedString().substring(0,1).toUpperCase();
				if(PaymentFlag.equals("F"))
				{
					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APDZ;Y",StaticStore.mPINString,StaticStore.midlet.billpayPres[0][0],StaticStore.midlet.billpayPres[0][1],
							StaticStore.midlet.billpayPres[0][14],PaymentFlag,StaticStore.midlet.billpayPres[0][9],StaticStore.midlet.billpayPres[0][3],StaticStore.midlet.txnID,"","","","","","","","","8","true","true","N"};
				}else if(PaymentFlag.equals("M"))
				{
					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APDZ;Y",StaticStore.mPINString,StaticStore.midlet.billpayPres[0][0],StaticStore.midlet.billpayPres[0][1],
							StaticStore.midlet.billpayPres[0][15],PaymentFlag,StaticStore.midlet.billpayPres[0][9],StaticStore.midlet.billpayPres[0][7],StaticStore.midlet.txnID,"","","","","","","","","8","true","true","N"};
				}else if(PaymentFlag.equals("E"))
				{
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APDZ;Y",StaticStore.mPINString,StaticStore.midlet.billpayPres[0][0],StaticStore.midlet.billpayPres[0][1],
							StaticStore.midlet.billpayPres[0][15],PaymentFlag,StaticStore.midlet.billpayPres[0][9],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","","","1-10-ND-N-N","","8","true","true","N"};
				}else if(PaymentFlag.equals("P"))
				{
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APDZ;Y",StaticStore.mPINString,StaticStore.midlet.billpayPres[0][0],StaticStore.midlet.billpayPres[0][1],
							StaticStore.midlet.billpayPres[0][15],PaymentFlag,StaticStore.midlet.billpayPres[0][9],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","","","1-10-ND-N-N","","8","true","true","N"};
				}
				
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));

		}else if(index == 229)
		{
			if(moreFlag){
				//"Recharge","AP2T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"Operator Search String","","0001","4-4-N-Y-Y","","2-20-ANW-N-N","","","5","false","false","Y"};
				StaticStore.menuDesc[220][4] = StaticStore.benNicName;
				StaticStore.menuDesc[220][6] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][7] = "";
				StaticStore.menuDesc[220][9] = "";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			}else{
				saveList();
				
				String temp = StaticStore.midlet.operatorDetails[selectedIndex][0];
				labelDetails = temp.substring(temp.indexOf('*')+1);
               	   //label2^6-10-N-N-N*label3^6-10-N-N-N
         	   labelDetails = StaticStore.midlet.replaceSpace(labelDetails,"#", '*');
         	  labelDetails = StaticStore.midlet.replaceSpace(labelDetails,"*", '^');
				InitiateAP3T_R_AP4T("AP3T",labelDetails,temp.substring(0,temp.indexOf('*')), StaticStore.midlet.topUpIndicator);
			}


		}else if(index == 230)
		{//116

//			saveList();
//			if(selectedIndex == 0){
//				StaticStore.tagType = "CH";
//				StaticStore.menuDesc[220] = new String []{"Generate Card","APCH;Y",StaticStore.mPINString,"001","4-4-N-Y-Y","","2","true","true","N"};
//				StaticStore.index = 220;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//			}else if(selectedIndex == 1){     
//				StaticStore.tagType = "CT";
//				StaticStore.menuDesc[220] = new String []{"TopUp","APTP;Y",StaticStore.mPINString,"Card Number","4-4-N-Y-Y","16-16-N-N-N","2","true","true","N"};
//				StaticStore.index = 220;
//				StaticStore.FromListScreen = false;
//				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
//						DynamicCanvas.class));
//
//			}
			saveList();
			if(selectedIndex == 0){
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(214);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));

			
			}else if(selectedIndex == 1){     
				StaticStore.midlet.listObject = new ListObject();
				StaticStore.midlet.listObject.setIndex(215);
				StaticStore.midlet.listObject.setTag("OT");
				StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
				StaticStore.midlet.listObject.setMore(false);
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));

			}
		}else if(index == 231){
			//117
			StaticStore.LogPrinter('i',"Sta.selectedindex -->"+StaticStore.selectedIndex+"LL ist --> "+selectedIndex);
			saveList();
			String feeAmtIndicator=StaticStore.midlet.amountIndicatorSymbol(StaticStore.midlet.IRCTCCardIssue[selectedIndex][3]);
			String topAmtIndicator=StaticStore.midlet.amountIndicatorSymbol(StaticStore.midlet.IRCTCCardIssue[selectedIndex][5]);
			String tempStr = "";
			tempStr = StaticStore.midlet.IRCTCCardIssue[selectedIndex][0]+" "+StaticStore.midlet.IRCTCCardIssue[selectedIndex][1]+";";
			if(feeAmtIndicator.equalsIgnoreCase("%")){
				tempStr+=StaticStore.midlet.IRCTCCardIssue[selectedIndex][2]+feeAmtIndicator+";";// need to check
			}else if(feeAmtIndicator.equalsIgnoreCase("Rs.")){
				tempStr+= feeAmtIndicator+StaticStore.midlet.IRCTCCardIssue[selectedIndex][2]+";";// need to check
			}else{
				tempStr+= StaticStore.midlet.IRCTCCardIssue[selectedIndex][2]+";";// need to check
			}
			if(topAmtIndicator.equalsIgnoreCase("%")){
				tempStr+=StaticStore.midlet.IRCTCCardIssue[selectedIndex][4]+topAmtIndicator+";";// need to check
			}else if(topAmtIndicator.equalsIgnoreCase("Rs.")){
				tempStr+= topAmtIndicator+StaticStore.midlet.IRCTCCardIssue[selectedIndex][4]+";";// need to check
			}else{
				tempStr+= StaticStore.midlet.IRCTCCardIssue[selectedIndex][4]+";";// need to check
			}

			tempStr+= "Rs."+StaticStore.midlet.IRCTCCardIssue[selectedIndex][6]+";"+
			"Rs."+StaticStore.midlet.IRCTCCardIssue[selectedIndex][7]+";"+StaticStore.midlet.IRCTCCardIssue[selectedIndex][8];

			Intent intent = new Intent(getActivity() , DisplayableView.class);
			intent.putExtra("response",tempStr);
			intent.putExtra("formName", "CH00");
			StaticStore.midlet.startFragment(getActivity(),intent);


		}else if (index == 232) { // vinoth
			if (StaticStore.tagType.equals("APSU")) {
				StaticStore.tagType = "APSU";
				if (selectedIndex == 0) {
					// StaticStore.dynamicMenuTemp = new
					// String[StaticStore.menuDesc[StaticStore.index].length];
					// StaticStore.tempIndex = StaticStore.index;
					// for (int i = 0; i < StaticStore.dynamicMenuTemp.length;
					// i++) {
					// StaticStore.dynamicMenuTemp[i] =
					StaticStore.menuDesc[220] = new String[] {
							"Debit Card", "APSU;Y", "", "", "",
							"", "", "Enter Date Of Birth(DDMMYYYY)", "", "",
							"", "", "", "", "8-8-N-N-N", "", "7", "true",
							"true","N"};

					// StaticStore.menuDesc[222] = new String
					// []{"TMB mConnect Registration","APSU:Y","Date Of Birth","8-8-AN-N-N","1","true","true"};
					// saveList();
					StaticStore.dynamicTempSignup[5] = "Y";
					StaticStore.dynamicTempSignup[6] = "N";					
					StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
					// }
				} else if (selectedIndex == 1) {
					StaticStore.menuDesc[220] = new String[] {
							"Debit Card", "APSU;Y", "", "", "",
							"", "", "", "Enter PAN Number", "", "", "", "", "",
							"", "10-10-AN-N-N", "7", "true", "true","N"};

					// StaticStore.menuDesc[222] = new String
					// []{"TMB mConnect Registration","APSU:Y","PAN number","6-10-AN-N-N","1","true","true"};
					// saveList();					
					StaticStore.dynamicTempSignup[5] = "N";
					StaticStore.dynamicTempSignup[6] = "Y";
					StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));

				}
			}
		}

		else if (index == 233) {
			if (moreFlag) {
				StaticStore.menuDesc[220] = new String []{"Offers","AP2O;N","001","","1","false","false","N"};
				StaticStore.tagType = "AP2O";
				StaticStore.menuDesc[220][2] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {

//				mid 11948 siva G saveList();
				StaticStore.menuDesc[220] = new String []{"Offers","AP3O;N",StaticStore.midlet.offer2OList[selectedIndex][0],"001","","","2","false","false","N"};
				StaticStore.tagType = "AP3O";
				StaticStore.index = 220;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));

			}
		}

		else if (index == 234) {
			if (moreFlag) {
				StaticStore.menuDesc[220] = new String []{"Offers","AP3O;N",StaticStore.midlet.offer2OList[selectedIndex][0],"001","","","2","false","false","N"};
				StaticStore.tagType = "AP3O";
				StaticStore.menuDesc[220][3] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {

//				mid 11948 siva G saveList();
				StaticStore.menuDesc[220] = new String []{"Offers","AP4O;N",StaticStore.offerFirstSubCategory,StaticStore.midlet.offer3OList[selectedIndex][0],"001","","","","3","false","false","N"};
				StaticStore.tagType = "AP4O";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));

			}
		}
		else if (index == 235) {
			if (moreFlag) {
				StaticStore.menuDesc[220] = new String []{"Offers","AP4O;N",StaticStore.offerFirstSubCategory,StaticStore.midlet.offer3OList[selectedIndex][0],"001","","","","3","false","false","N"};
				StaticStore.tagType = "AP4O";
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.menuDesc[220] = new String []{"Offers","AP5O;N",StaticStore.offerFirstSubCategory,StaticStore.offerSecondSubCategory,StaticStore.midlet.offer4OList[selectedIndex][0],"","","","3","false","false","N"};
				StaticStore.tagType = "AP5O";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));

			}

		}else if (index == 236) {
				StaticStore.index = 220;
				StaticStore.tagType = "APB6";
				String PaymentFlag = getSelectedString().substring(0,1).toUpperCase();
				StaticStore.LogPrinter('i',"called from 236 == PaymentFlag --> "+PaymentFlag);
				if(PaymentFlag.equals("F"))
				{
					StaticStore.menuDesc[220] = new String [] {"Pay Biller","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],"F"
					                                                                   ,StaticStore.midlet.billpayBills[0][8],StaticStore.midlet.billpayBills[0][2],StaticStore.midlet.txnID,"","","","","","","6","true","true","N"};
				}else if(PaymentFlag.equals("M"))
				{
//					{"Pay Biller","APB6;Y",mPINString,"CustMneName","Payment Flag","Bill No.","Amount (Rs.)","TXNID","","","","","1-10-ND-N-N","","6","false","false","N"},//20
//					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],StaticStore.midlet.billpayBills[0][1],
//							StaticStore.midlet.billpayBills[0][15],PaymentFlag,StaticStore.midlet.billpayBills[0][9],StaticStore.midlet.billpayBills[0][7],StaticStore.midlet.txnID,"","","","","","","","","8","true","true","Y"};
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[220] = new String [] {"Pay Biller","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],"M"
                            ,StaticStore.midlet.billpayBills[0][8],StaticStore.midlet.billpayBills[0][6],StaticStore.midlet.txnID,"","","","","","","6","true","true","N"};

				}else if(PaymentFlag.equals("E"))
				{
//					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],StaticStore.midlet.billpayBills[0][1],
//							StaticStore.midlet.billpayBills[0][15],PaymentFlag,StaticStore.midlet.billpayBills[0][9],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","","","1-10-ND-N-N","","8","true","true","Y"};
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[220] = new String [] {"Pay Biller","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],"E"
                            ,StaticStore.midlet.billpayBills[0][8],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","1-10-ND-N-N","","6","true","true","N"};

				}else if(PaymentFlag.equals("P"))
				{
//					StaticStore.menuDesc[220] = new String [] {"Instant Bill Payment","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],StaticStore.midlet.billpayBills[0][1],
//							StaticStore.midlet.billpayBills[0][15],PaymentFlag,StaticStore.midlet.billpayBills[0][9],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","","","1-10-ND-N-N","","8","true","true","Y"};
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[220] = new String [] {"Pay Biller","APB6;Y",StaticStore.mPINString,StaticStore.midlet.billpayBills[0][0],"P"
                            ,StaticStore.midlet.billpayBills[0][8],"Amount (Rs.)",StaticStore.midlet.txnID,"","","","","1-10-ND-N-N","","6","true","true","N"};

				}
				
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));


	}else if(index == 237){
		if (selectedIndex == 0) {
			 saveList();
       	     StaticStore.midlet.listObject = new ListObject();
			 StaticStore.midlet.listObject.setIndex(239);
			 StaticStore.midlet.listObject.setTag("OT");
			 StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
			 StaticStore.midlet.listObject.setMore(false);
			 StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
        }else if (selectedIndex == 1) {
			 saveList();
			 StaticStore.menuDesc[220] = new String []{"Add Merchant","AP1I;Y",StaticStore.mPINString,"Mobile No.","MMID No.","Nickname","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-20-ANW-N-N","4","true","true","Y"};
			 StaticStore.index = 220;
			 StaticStore.tagType = "AP1I";
			 StaticStore.FromListScreen = false;
			 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		} else if (selectedIndex == 2) {
			 saveList();
			 StaticStore.midlet.listObject = new ListObject();
			 StaticStore.midlet.listObject.setIndex(238);
			 StaticStore.midlet.listObject.setTag("OT");
			 StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
			 StaticStore.midlet.listObject.setMore(false);
			 StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
        }else if (selectedIndex == 3) {
			 saveList();
			 StaticStore.menuDesc[220] = new String []{"Merchant Details","APM9;Y",StaticStore.mPINString,"Search Merchant","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
			 StaticStore.index = 220;
			 StaticStore.tagType = "APM9";
			 StaticStore.FromListScreen = false;
			 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
        }else if (selectedIndex == 4) {
			 saveList();
			 StaticStore.menuDesc[220]  = new String []{"Delete Merchant","AP7L;Y",StaticStore.mPINString,"Search Merchant","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
			 StaticStore.index = 220;
			 StaticStore.tagType = "AP7L";
			 StaticStore.FromListScreen = false;
			 StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		}
   }else if(index == 238)
		{
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.menuDesc[220] = new String []{"Pay Merchant","AP3I;Y",StaticStore.mPINString,"Search Merchant","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"};
			StaticStore.index = 220;
			StaticStore.tagType = "AP3I";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 239){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = true;
			StaticStore.menuDesc[220] = new String []{"Instant Pay","APPI;Y",StaticStore.mPINString,"Enter Merchant Mobile No.","Enter Merchant MMID No.","Enter Amount (Rs.)","Enter Payment Reference","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-10-ND-N-N","0-50-AN-N-N","5","true","true","N"};
			StaticStore.index = 220;
			StaticStore.tagType = "APPI";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(index == 240){
			if (moreFlag) {
				StaticStore.menuDesc[220][0] = "Pay Merchant";
				StaticStore.menuDesc[220][1] = "AP3I;Y";
				StaticStore.menuDesc[220][2] = StaticStore.mPINString;
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.menuDesc[220][7] = "";
				StaticStore.menuDesc[220][8] = "3";
				StaticStore.index = 220;
				StaticStore.tagType = "AP3I";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			} else {
				StaticStore.menuDesc[220] = new String [14];
				StaticStore.menuDesc[220][0] = "Pay Merchant";
				StaticStore.menuDesc[220][1] = "APPM;Y";
				StaticStore.menuDesc[220][2] = StaticStore.mPINString;
				StaticStore.menuDesc[220][3] = "Amount";
				StaticStore.menuDesc[220][4] = "Payment Reference";
				StaticStore.menuDesc[220][5] = menuItem[selectedIndex];
				StaticStore.menuDesc[220][6] = "";
				StaticStore.menuDesc[220][7] = "1-10-ND-N-N";
				StaticStore.menuDesc[220][8] = "0-50-ANW-N-N";
				StaticStore.menuDesc[220][9] = "";
				StaticStore.menuDesc[220][10] = "4";
				StaticStore.menuDesc[220][11] = "false";
				StaticStore.menuDesc[220][12] = "false";
				StaticStore.menuDesc[220][13] = "N";
				
				StaticStore.LogPrinter('i',"APPM ---> "+Arrays.deepToString(StaticStore.menuDesc[220]));
				StaticStore.index = 220;
				StaticStore.tagType = "APPM";
				StaticStore.accpaymentFlag = true;
                StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}	
		}
		

	}
	private void doAction241To260(int index, int selectedIndex, boolean moreFlag) {
	if(index == 241){
		if (moreFlag) {
			StaticStore.menuDesc[220][0] = "Delete Merchant";
			StaticStore.menuDesc[220][1] =  "AP7L;Y";
			StaticStore.menuDesc[220][2] = StaticStore.mPINString;
			StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
			StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
			StaticStore.menuDesc[220][5] = "";
			StaticStore.menuDesc[220][6] = "";
			StaticStore.menuDesc[220][7] = "";
			StaticStore.menuDesc[220][8] = "3";
			StaticStore.index = 220;
			StaticStore.tagType = "AP7L";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else {
			StaticStore.menuDesc[220] = new String [10];
            StaticStore.menuDesc[220][0] = "Delete Merchant";
            StaticStore.menuDesc[220][1] = "AP7D;Y";
            StaticStore.menuDesc[220][2] = StaticStore.mPINString;
            StaticStore.menuDesc[220][3] = menuItem[selectedIndex];
            StaticStore.menuDesc[220][4] = "";
            StaticStore.menuDesc[220][5] = "";
            StaticStore.menuDesc[220][6] = "2";
            StaticStore.menuDesc[220][7] = "false";
            StaticStore.menuDesc[220][8] = "false";
            StaticStore.menuDesc[220][9] = "N";
            StaticStore.index = 220;
			StaticStore.tagType = "AP7D";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			}	
	}else if(index == 242){
	
			if (moreFlag) {
				StaticStore.menuDesc[220][0] = "Merchant Details";
				StaticStore.menuDesc[220][1] = "APM9;Y";
				StaticStore.menuDesc[220][2] = StaticStore.mPINString;
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.menuDesc[220][7] = "";
				StaticStore.menuDesc[220][8] = "3";
				StaticStore.menuDesc[220][9] = "false";
				StaticStore.menuDesc[220][10] = "false";
				StaticStore.menuDesc[220][11] = "N";
				StaticStore.index = 220;
				StaticStore.tagType = "APM9";
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
			} else {
				saveList();
			String temp="";
			temp = StaticStore.midlet.impsDetails[selectedIndex][0]+";"+StaticStore.midlet.impsDetails[selectedIndex][1]+";"+StaticStore.midlet.impsDetails[selectedIndex][2];
			Intent intent = new Intent(getActivity() , DisplayableView.class);
			intent.putExtra("response",temp);
			intent.putExtra("formName", "M900");
			StaticStore.midlet.startFragment(getActivity(),intent);
	}
	}else if(index == 243){
		if (moreFlag) {
			StaticStore.menuDesc[220][0] = "Pay Beneficiary";
			StaticStore.menuDesc[220][1] = "AP4U;Y";
			StaticStore.menuDesc[220][2] = StaticStore.mPINString;
			StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
			StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
			StaticStore.menuDesc[220][5] = "";
			StaticStore.menuDesc[220][6] = "";
			StaticStore.menuDesc[220][7] = "";
			StaticStore.menuDesc[220][8] = "3";
			StaticStore.index = 220;
			StaticStore.tagType = "AP4U";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			
		} else {
			
			StaticStore.LogPrinter('i',"comes inside 243");
			StaticStore.tagType = "AP5U";
			StaticStore.accpaymentFlag = true;
			StaticStore.menuDesc[220]  = new String[] {
					  "Pay Beneficiary", "AP5U;Y",StaticStore.mPINString,menuItem[selectedIndex],"Amount (Rs.)","Remarks","","","1-10-ND-N-N","0-25-AN-N-N",
					    "4","false","false","N"};
//			StaticStore.menuDesc[220] = new String [14];
//            StaticStore.menuDesc[220][0] = "Pay Beneficiary";
//            StaticStore.menuDesc[220][1] = "AP5U;Y";
//            StaticStore.menuDesc[220][2] = StaticStore.mPINString;
//            StaticStore.menuDesc[220][3] = "Amount (Rs.)";
//            StaticStore.menuDesc[220][4] = "Remarks";
//            StaticStore.menuDesc[220][5] = menuItem[selectedIndex];
//            StaticStore.menuDesc[220][6] = "";
//            StaticStore.menuDesc[220][7] = "1-10-ND-N-N";
//            StaticStore.menuDesc[220][8] = "0-50-ANW-N-N";
//            StaticStore.menuDesc[220][9] = "";
//            StaticStore.menuDesc[220][10] = "4";
//            StaticStore.menuDesc[220][11] = "false";
//            StaticStore.menuDesc[220][12] = "false";
//            StaticStore.menuDesc[220][13] = "N";
            StaticStore.index = 220;

			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
			}
	}else if(index== 244){
		
		if (moreFlag) {
			StaticStore.menuDesc[220][0] = "Beneficiary Details";
			StaticStore.menuDesc[220][1] = "AP6U;Y";
			StaticStore.menuDesc[220][2] = StaticStore.mPINString;
			StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
			StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
			StaticStore.menuDesc[220][5] = "";
			StaticStore.menuDesc[220][6] = "";
			StaticStore.menuDesc[220][7] = "";
			StaticStore.menuDesc[220][8] = "3";
	        StaticStore.menuDesc[220][9] = "false";
            StaticStore.menuDesc[220][10] = "false";
            StaticStore.menuDesc[220][11] = "N";
            StaticStore.index = 220;
			StaticStore.tagType = "AP6U";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else {
			saveList();
			
//			Account Type Display Code Start
			String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
            tempData = tempData.substring(0,tempData.length()-1);
//            String tempArr []  = tempData.split("#");
//            int i;
//            for (i = 0; i < tempArr.length-1; i++) {
//                if(StaticStore.midlet.P2UFTRegList[selectedIndex][2].startsWith(tempArr[i])){
//                  break;  
//                }
//            } 
//            String acctypename;
//            if(StaticStore.midlet.P2UFTRegList[selectedIndex][2].equals(tempArr[i].substring(0, tempArr[i].indexOf("*")))){
//            	acctypename = tempArr[i];
//            	acctypename = acctypename.substring(acctypename.indexOf('*')+1);
//            	acctypename = acctypename.substring(0,acctypename.indexOf('*'));
//            }else{
//				acctypename = StaticStore.midlet.P2UFTRegList[selectedIndex][2];
//			}
            
            String acctypename = StaticStore.midlet.getAccountTypes(tempData,StaticStore.midlet.P2UFTRegList[selectedIndex][2].trim());
//          Account Type Display Code END
		String tempStr = StaticStore.midlet.P2UFTRegList[selectedIndex][0]
		                                						+ ";"
		                                						+ StaticStore.midlet.P2UFTRegList[selectedIndex][1]
		                                						+ ";"
		                                						+ acctypename;
		Intent intent = new Intent(getActivity() , DisplayableView.class);
		intent.putExtra("response",tempStr);
		intent.putExtra("formName", "6U00");
		StaticStore.midlet.startFragment(getActivity(),intent);
		}}else if(index == 245){
			if (moreFlag) {
				StaticStore.menuDesc[220][0] = "Delete Beneficiary";
				StaticStore.menuDesc[220][1] = "AP7U;Y";
				StaticStore.menuDesc[220][2] = StaticStore.mPINString;
				StaticStore.menuDesc[220][3] = StaticStore.benSearchString;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.menuDesc[220][5] = "";
				StaticStore.menuDesc[220][6] = "";
				StaticStore.menuDesc[220][7] = "";
				StaticStore.menuDesc[220][8] = "3";
				 StaticStore.index = 220;
					StaticStore.tagType = "AP7U";
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
			} else {
				saveList();
				StaticStore.menuDesc[220] = new String [10];
                StaticStore.menuDesc[220][0] = "Delete Beneficiary";
                StaticStore.menuDesc[220][1] = "AP8U;Y";
                StaticStore.menuDesc[220][2] = StaticStore.mPINString;
                StaticStore.menuDesc[220][3] = menuItem[selectedIndex];;
                StaticStore.menuDesc[220][4] = "";
                StaticStore.menuDesc[220][5] = "";
                StaticStore.menuDesc[220][6] = "2";
                StaticStore.menuDesc[220][7] = "false";
                StaticStore.menuDesc[220][8] = "false";
                StaticStore.menuDesc[220][9] = "N";
                StaticStore.index = 220;
				StaticStore.tagType = "AP8U";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));
			
				}
		}else if(index == 247){
			if (moreFlag) {
				StaticStore.menuDesc[220] = new String []{"Transaction History","APTH;Y",StaticStore.mPINString,StaticStore.midlet.nextStartRecNumber,"","","2","false","false","N"};				
				StaticStore.index = 220;
				StaticStore.tagType = "APTH";
			    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			} else {
				saveList();
				String temp="";
				String status = StaticStore.midlet.transactionHistoryList[selectedIndex][2].equals("S")?"Success":"Failure";
				if(StaticStore.midlet.transactionHistoryList[selectedIndex][4].equals("-") && StaticStore.midlet.transactionHistoryList[selectedIndex][5].equals("-")){
					temp = StaticStore.midlet.transactionHistoryList[selectedIndex][1]+";"+ status +";"+StaticStore.midlet.transactionHistoryList[selectedIndex][3]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][6]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][7];
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response",temp);
					intent.putExtra("formName", "TH001");
					StaticStore.midlet.startFragment(getActivity(),intent);
				}else if(StaticStore.midlet.transactionHistoryList[selectedIndex][4].equals("-")){
					temp = StaticStore.midlet.transactionHistoryList[selectedIndex][1]+";"+ status +";"+StaticStore.midlet.transactionHistoryList[selectedIndex][3]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][5]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][6]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][7];
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response",temp);
					intent.putExtra("formName", "TH002");
					StaticStore.midlet.startFragment(getActivity(),intent);
				}else if(StaticStore.midlet.transactionHistoryList[selectedIndex][5].equals("-")){
					temp = StaticStore.midlet.transactionHistoryList[selectedIndex][1]+";"+ status +";"+StaticStore.midlet.transactionHistoryList[selectedIndex][3]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][4]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][6]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][7];
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response",temp);
					intent.putExtra("formName", "TH003");
					StaticStore.midlet.startFragment(getActivity(),intent);
				}else{
					temp = StaticStore.midlet.transactionHistoryList[selectedIndex][1]+";"+ status +";"+StaticStore.midlet.transactionHistoryList[selectedIndex][3]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][4]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][5]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][6]+";"+StaticStore.midlet.transactionHistoryList[selectedIndex][7];
					Intent intent = new Intent(getActivity() , DisplayableView.class);
					intent.putExtra("response",temp);
					intent.putExtra("formName", "TH00");
					StaticStore.midlet.startFragment(getActivity(),intent);
				}
			}
		}else if(index == 248){
	
	StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
	StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
	StaticStore.fromAccountList = true;
	StaticStore.menuDesc[220] = new String []{"Hotlist Debit Card","APHL;Y",StaticStore.mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"};				
	StaticStore.index = 220;
	StaticStore.tagType = "APHL";
	StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
			DynamicCanvas.class));
	
}else if(index == 249){
	
	if (moreFlag) {
		StaticStore.menuDesc[220] = new String []{"Hotlist Debit Card","APHL;Y",StaticStore.mPINString,StaticStore.midlet.nextStartRecNumber,"","","2","false","false","Y"};				
		StaticStore.index = 220;
		StaticStore.tagType = "APHL";
	    StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
			DynamicCanvas.class));
			
	} else {
		saveList();
		StaticStore.menuDesc[220] = new String []{"Hotlist Debit Card","APHC;Y",StaticStore.mPINString,StaticStore.midlet.CardhotlistDet[selectedIndex][0],StaticStore.midlet.txnID,"4-4-N-Y-Y","","", "3","false","false","N"};				
		StaticStore.index = 220;
		StaticStore.tagType = "APHC";
		String tempStr = StaticStore.midlet.CardhotlistDet[selectedIndex][1] + ";"+ StaticStore.midlet.CardhotlistDet[selectedIndex][2] + ";"+ StaticStore.midlet.CardhotlistDet[selectedIndex][3];
	    Intent intent = new Intent(getActivity() , DisplayableView.class);
		intent.putExtra("response",tempStr);
		intent.putExtra("formName", "APHC");
		StaticStore.midlet.startFragment(getActivity(),intent);
	}
}else if(index == 250){
	if(selectedIndex == 0){
		saveList();
		StaticStore.menuDesc[220] = new String []{"Debit Card","APSU;Y","Card Number","ATM PIN","Card Expiry Date(MMYY)","Mobile No.","CVV","","","16-16-N-Y-N","4-4-N-Y-Y","4-4-N-Y-N","10-10-N-N-N","3-3-N-Y-Y","","","7","true","true","N"};
		StaticStore.tagType = "APSU";
		StaticStore.index = 220;
		StaticStore.FromListScreen = false;
		StaticStore.initialFlag=true;
		Intent intent =  new Intent(getActivity(),DynamicCanvas.class);
		StaticStore.midlet.startFragment(getActivity(),intent);
	}else{
		saveList();
		StaticStore.menuDesc[220] = new String []{"Net Banking","APNU;Y","Login ID","Password","Mobile No.","3-20-ANW-N-N","6-20-AN-Y-Y","10-10-N-N-N","3","true","true","N"};
		StaticStore.tagType = "APNU";
		StaticStore.index = 220;
		StaticStore.FromListScreen = false;
		StaticStore.initialFlag = true;
		Intent intent =  new Intent(getActivity(),DynamicCanvas.class);
		StaticStore.midlet.startFragment(getActivity(),intent);
	}
}else if(index == 251){
	if(selectedIndex == 0){
			labelDetails = StaticStore.midlet.rechargeDynamicInputs.getLabelDetailsByID_Name(StaticStore.rechargeSelcetedCategoryID,getSelectedString());
			StaticStore.midlet.topUpIndicator = "N";
			InitiateAP3T_R_AP4T("AP4T",labelDetails,getSelectedString(),StaticStore.midlet.topUpIndicator);
	}else{
			labelDetails = StaticStore.midlet.rechargeDynamicInputs.getLabelDetailsByID_Name(StaticStore.rechargeSelcetedCategoryID,getSelectedString());
			StaticStore.midlet.topUpIndicator = "N";
			InitiateAP3T_R_AP4T("AP4T",labelDetails,getSelectedString(),StaticStore.midlet.topUpIndicator);
	}
}else if(index == 252){
	if(selectedIndex == 0){
		StaticStore.menuDesc[220] = new String [] {"Delete Beneficiary","APBD;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,menuItem[selectedIndex],"4-4-N-Y-Y","","","3","true","true","N"};
		StaticStore.index = 220;
		StaticStore.tagType = "APBD";
		StaticStore.selection = menuItem[selectedIndex];
		StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
	}else{
		StaticStore.menuDesc[220] = new String [] {"Delete Beneficiary","APBD;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,menuItem[selectedIndex],"4-4-N-Y-Y","","","3","true","true","N"};
		StaticStore.index = 220;
		StaticStore.tagType = "APBD";
		StaticStore.selection = menuItem[selectedIndex];
		StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
	}
}else if(index == 253){
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
	StaticStore.accpaymentFlag = false;
	StaticStore.depositIDConfirm = "";
	StaticStore.forAPDOBack = false;
	StaticStore.closeTheAccountOnMaturity = false;
	StaticStore.depositPeriodInDays = false;
	StaticStore.depositPeriodInMonthsForNonQuarterly = false;
	StaticStore.depositPeriodInMonthsForQuarterly = false;
	StaticStore.renewMyDeposit = false;
	StaticStore.midlet.savelistinit();
	
	if(selectedIndex == 0){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(254);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}else if(selectedIndex == 1){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(255);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}else if(selectedIndex == 2){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(258);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}else if(selectedIndex == 3){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(264);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}else if(selectedIndex == 4){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(263);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}else if(selectedIndex == 5){
		saveList();
		StaticStore.midlet.listObject = new ListObject();
		StaticStore.midlet.listObject.setIndex(261);
		StaticStore.midlet.listObject.setTag("DO");
		StaticStore.midlet.listObject.setHeading(StaticStore.accNumberHeadingName);
		StaticStore.midlet.listObject.setMore(false);
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,getActivity()));
	}
}else if(index == 254){
	StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
	StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
	StaticStore.fromAccountList = true;
	StaticStore.accpaymentFlag = true;
	StaticStore.dynamicTempRDFlag = true;
	StaticStore.menuDesc[220] = new String[] {"Open Recurring Deposit","APDO;Y",StaticStore.mPINString,"","Account Opening Date","Installment Amount (Rs.)","Deposit Period in Months", "4-4-N-Y-Y", "", "10-10-DMY-N-N", "3-6-N-N-N", "2-3-N-N-N", "5", "false", "false", "N" };
	StaticStore.menuDesc[220][3] = "17";
	StaticStore.index = 220;
	StaticStore.tagType = "APDO";
	StaticStore.FromListScreen = false;
	StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
}else if(index == 255){
	StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
	StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
	StaticStore.fromAccountList = true;
	StaticStore.accpaymentFlag = false;
	StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"","Account Opening Date","Deposit Amount (Rs.)","Deposit Period in Months", "Deposit Period in Days", "", "", "4-4-N-Y-Y", "", "10-10-DMY-N-N", "4-7-N-N-N", "0-3-N-N-N","0-3-N-N-N","", "", "8", "false", "false", "N" };
	StaticStore.menuDesc[220][3] = "15";
	StaticStore.index = 220;
	StaticStore.tagType = "APDO";
	StaticStore.FromListScreen = false;
	Intent intent = new Intent(getActivity(), DynamicCanvas.class);
	StaticStore.midlet.startFragment(getActivity(), intent);
}else if(index == 256){
	if (selectedIndex == 0) {
		saveList();
		StaticStore.accpaymentFlag = false;
		StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "","","", "", "8", "false", "false", "N" };
		StaticStore.dynamicTempFD[6] = "R";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.renewMyDeposit = true;
		StaticStore.closeTheAccountOnMaturity = false;
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getPayInterest(getActivity()));
	}else if (selectedIndex == 1) {
		saveList();
		StaticStore.accpaymentFlag = false;
		StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "","","", "", "8", "false", "false", "N" };
		StaticStore.dynamicTempFD[6] = "C";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.renewMyDeposit = false;
		StaticStore.closeTheAccountOnMaturity = true;
		StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getPayInterest(getActivity()));
	}
}else if(index == 257){
	if(selectedIndex == 0){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "","","", "", "8", "false", "false", "N" };
		StaticStore.dynamicTempFD[7] = "N";
		StaticStore.dynamicTempFDConfirmFlag = true;
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.dynamicTempFDFlag = true;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}else if(selectedIndex == 1){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "","","", "", "8", "false", "false", "N" };
		StaticStore.dynamicTempFD[7] = "M";
		StaticStore.dynamicTempFDConfirmFlag = true;
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.dynamicTempFDFlag = true;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}else if(selectedIndex == 2){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Fixed Deposit (Simple Interest)","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "","","", "", "8", "false", "false", "N" };
		StaticStore.dynamicTempFD[7] = "Q";
		StaticStore.dynamicTempFDConfirmFlag = true;
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.dynamicTempFDFlag = true;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}
}else if(index == 258){
	StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
	StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
	StaticStore.fromAccountList = true;
	StaticStore.accpaymentFlag = false;
	StaticStore.dynamicTempNDFlag = true;
	StaticStore.dynamicTempNDConfirmFlag = false;
	StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "4-4-N-Y-Y", "", "", "", "", "", "6", "false", "false", "N" };
	StaticStore.index = 220;
	StaticStore.tagType = "APDO";
	StaticStore.FromListScreen = false;
	Intent intent = new Intent(getActivity(), DynamicCanvas.class);
	StaticStore.midlet.startFragment(getActivity(), intent);
}else if(index == 259){
	if(selectedIndex == 0){
		saveList();
		StaticStore.accpaymentFlag = false;
		StaticStore.dynamicTempND2Flag = false;
		StaticStore.dynamicTempND1Flag = true;
		StaticStore.dynamicTempNDConfirmFlag = false;
		StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "Account Opening Date","Deposit Amount (Rs.)","", "", "", "", "10-10-DMY-N-N", "5-6-N-N-N", "", "6", "false", "false", "N" };
		StaticStore.menuDesc[220][3] = "51";
		StaticStore.menuDesc[220][4] = "5";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.FromListScreen = false;
		Intent intent = new Intent(getActivity(), DynamicCanvas.class);
		StaticStore.midlet.startFragment(getActivity(), intent);
	}else if(selectedIndex == 1){
		saveList();
		StaticStore.accpaymentFlag = false;
		StaticStore.dynamicTempND1Flag = false;
		StaticStore.dynamicTempND2Flag = true;
		StaticStore.dynamicTempNDConfirmFlag = false;
		StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "Account Opening Date","Deposit Amount (Rs.)","", "", "", "", "10-10-DMY-N-N", "5-6-N-N-N", "", "6", "false", "false", "N" };
		StaticStore.menuDesc[220][3] = "50";
		StaticStore.menuDesc[220][4] = "10";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.FromListScreen = false;
		Intent intent = new Intent(getActivity(), DynamicCanvas.class);
		StaticStore.midlet.startFragment(getActivity(), intent);
	}
}else if(index == 260){
	if(selectedIndex == 0){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.dynamicTempNDFinalFlag = true;
		StaticStore.dynamicTempNDConfirmFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "6", "false", "false", "N" };
		StaticStore.dynamicTempND[5] = "36";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.index = 220;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}else if(selectedIndex == 1){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.dynamicTempNDFinalFlag = true;
		StaticStore.dynamicTempNDConfirmFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "6", "false", "false", "N" };
		StaticStore.dynamicTempND[5] = "48";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.index = 220;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}else if(selectedIndex == 2){
		saveList();
		StaticStore.accpaymentFlag = true;
		StaticStore.dynamicTempNDFinalFlag = true;
		StaticStore.dynamicTempNDConfirmFlag = true;
		StaticStore.menuDesc[220] = new String[] {"Open Navarathnamala Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "", "6", "false", "false", "N" };
		StaticStore.dynamicTempND[5] = "60";
		StaticStore.index = 220;
		StaticStore.tagType = "APDO";
		StaticStore.index = 220;
		StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
	}
}
	}
	
	private void doAction261To280(int index, int selectedIndex, boolean moreFlag) {
		if(index == 261){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = false;
			StaticStore.menuDesc[220] = new String[] {"Special 555 Deposit","APDO;Y",StaticStore.mPINString,"","Account Opening Date","Deposit Amount (Rs.)","", "4-4-N-Y-Y", "", "10-10-DMY-N-N", "4-7-N-N-N", "", "5", "false", "false", "N" };
			StaticStore.menuDesc[220][3] = "52";
			StaticStore.index = 220;
			StaticStore.tagType = "APDO";
			StaticStore.FromListScreen = false;
			Intent intent = new Intent(getActivity(), DynamicCanvas.class);
			StaticStore.midlet.startFragment(getActivity(), intent);
		}else if(index == 262){
			if (selectedIndex == 0) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Special 555 Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "5", "false", "false", "N" };
				StaticStore.dynamicTempS555[4] = "M";
				StaticStore.dynamicTempS555ConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.index = 220;
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(),DynamicCanvas.class));
			}else if (selectedIndex == 1) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Special 555 Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "5", "false", "false", "N" };
				StaticStore.dynamicTempS555[4] = "Q";
				StaticStore.dynamicTempS555ConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}else if (selectedIndex == 2) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Special 555 Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "5", "false", "false", "N" };
				StaticStore.dynamicTempS555[4] = "C";
				StaticStore.dynamicTempS555ConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}
		}else if(index == 263){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = true;
			StaticStore.menuDesc[220] = new String[] {"Special 20:20 Deposit","APDO;Y",StaticStore.mPINString,"","Account Opening Date","Deposit Amount (Rs.)", "4-4-N-Y-Y", "", "10-10-DMY-N-N", "4-6-N-N-N", "4", "false", "false", "N" };
			StaticStore.menuDesc[220][3] = "53";
			StaticStore.index = 220;
			StaticStore.tagType = "APDO";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
		}else if(index == 264){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = false;
			StaticStore.menuDesc[220] = new String[] {"Open Muthukuvial Deposit","APDO;Y",StaticStore.mPINString,"","Account Opening Date","Deposit Amount (Rs.)","Deposit Period in Months", "", "4-4-N-Y-Y", "", "10-10-DMY-N-N", "4-7-N-N-N", "1-3-N-N-N", "","6", "false", "false", "N" };
			StaticStore.menuDesc[220][3] = "16";
			StaticStore.index = 220;
			StaticStore.tagType = "APDO";
			StaticStore.FromListScreen = false;
			Intent intent = new Intent(getActivity(), DynamicCanvas.class);
			StaticStore.midlet.startFragment(getActivity(), intent);
		}else if(index == 265){
			if (selectedIndex == 0) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Open Muthukuvial Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "","6", "false", "false", "N" };
				StaticStore.dynamicTempMD[5] = "R";
				StaticStore.dynamicTempMDConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}else if (selectedIndex == 1) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Open Muthukuvial Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "","6", "false", "false", "N" };
				StaticStore.dynamicTempMD[5] = "P";
				StaticStore.dynamicTempMDConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}else if (selectedIndex == 2) {
				saveList();
				StaticStore.accpaymentFlag = true;
				StaticStore.menuDesc[220] = new String[] {"Open Muthukuvial Deposit","APDO;Y",StaticStore.mPINString,"", "", "", "", "", "", "", "", "", "", "","6", "false", "false", "N" };
				StaticStore.dynamicTempMD[5] = "C";
				StaticStore.dynamicTempMDConfirmFlag = true;
				StaticStore.index = 220;
				StaticStore.tagType = "APDO";
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}
		}else if(index == 266){
//			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
//			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
//			StaticStore.fromAccountList = true;
//			StaticStore.accpaymentFlag = true;
//			StaticStore.menuDesc[220] = new String[] {"Deposit Account Balance","APLF;Y",StaticStore.mPINString,"","","4-4-N-Y-Y","","","3","true","true","Y"};
//			StaticStore.index = 220;
//			StaticStore.tagType = "APLF";
//			StaticStore.FromListScreen = false;
//			Intent intent =  new Intent(getActivity(),DynamicCanvas.class);
//			StaticStore.midlet.startFragment(getActivity(),intent);
		}else if(index == 267){
			saveList();
			StaticStore.accpaymentFlag = false;
			StaticStore.menuDesc[220] = new String[] {"Deposit Account Balance","APLF;Y", StaticStore.mPINString, "TDA", "001", "", "", "","3", "true", "true", "Y" };
			if(selectedIndex == 0){
				StaticStore.menuDesc[220][3] = "17"; 
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];
			}else if(selectedIndex == 1){
				StaticStore.menuDesc[220][3] = "15";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];
			}else if(selectedIndex == 2){
				StaticStore.menuDesc[220][3] = "51";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];
			}else if(selectedIndex == 3){
				StaticStore.menuDesc[220][3] = "50";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];
			}else if(selectedIndex == 4){
				StaticStore.menuDesc[220][3] = "16";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];	
			}else if(selectedIndex == 5){
				StaticStore.menuDesc[220][3] = "53";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];	
			}else if(selectedIndex == 6){
				StaticStore.menuDesc[220][3] = "52";
				StaticStore.depositAccBalanceListSelected = StaticStore.menuDesc[220][3];
			}
			StaticStore.index = 220;
			StaticStore.tagType = "APLF";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
		}else if(index == 268){
			if (moreFlag) {
				saveList();
				StaticStore.depositAccBalance = true;
				StaticStore.accpaymentFlag = false;
				StaticStore.menuDesc[220] = new String[] {"Deposit Account Balance","APLF;Y", StaticStore.mPINString,"TDA",StaticStore.midlet.nextStartRecNumber, "", "", "","3", "true", "true", "Y" };
				StaticStore.menuDesc[220][3] = StaticStore.depositAccBalanceListSelected;
				StaticStore.index = 220;
				StaticStore.tagType = "APLF";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}else{
				saveList();
				StaticStore.accpaymentFlag = false;
				int i = selectedIndex;
				StaticStore.menuDesc[220] = new String[] {"Deposit Account Balance","APDB;Y",StaticStore.mPINString,"","1", "false", "false", "N" };
				StaticStore.midlet.selectedAccNumberForDepositAccBalSave = StaticStore.midlet.depositAccountBalanceList[i][1];
				StaticStore.index = 220;
				StaticStore.tagType = "APDB";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}
		
		}else if(index == 269){
//			saveList();
//			StaticStore.accpaymentFlag = false;
//			StaticStore.menuDesc[220] = new String [] {"Loan Balance","APLA;Y",StaticStore.mPINString,"03","001","4-4-N-Y-Y","","","3","true","true","Y"};
//			StaticStore.index = 220;
//			StaticStore.tagType = "APLA";
//			StaticStore.FromListScreen = false;
//			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		}else if(index == 270){
			if (moreFlag) {
				saveList();
				StaticStore.accpaymentFlag = false;
				StaticStore.loanBalance = true;
			//	StaticStore.menuDesc[220] = new String[] {"Loan Balance","APLA;Y", StaticStore.mPINString, "03", StaticStore.midlet.nextStartRecNumber, "", "", "","3", "true", "true", "Y" }; // Removed the hard coded value by Premila
				StaticStore.menuDesc[220] = new String[] {"Loan Balance","APLA;Y", StaticStore.mPINString,"LAA", StaticStore.midlet.nextStartRecNumber, "", "", "","3", "true", "true", "Y" };
				StaticStore.index = 220;
				StaticStore.tagType = "APLA";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}else{
				saveList();
				StaticStore.accpaymentFlag = false;
				int i = selectedIndex;
				StaticStore.menuDesc[220] = new String[] {"Loan Balance","APLE;Y", StaticStore.mPINString,"","1", "false", "false", "N" };
				StaticStore.midlet.selectedAccNumberForLoanBalSave = StaticStore.midlet.loanBalanceList[i][1];
				StaticStore.index = 220;
				StaticStore.tagType = "APLE";
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(), new Intent(getActivity(), DynamicCanvas.class));
			}
		} else if (index == 271) {
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;

			StaticStore.index = 48;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		} else if (index == 272) {
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;

		    StaticStore.menuDesc[220] = new String [] {"Debit Card PIN","APDP;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
			StaticStore.index = 220;
			StaticStore.tagType = "APDP";
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		} else if(index == 273){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.selectedAccNumberFrom = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = false;
			StaticStore.initialMyOwnAccFlag = true;
			StaticStore.menuDesc[220] = new String[] {"My Own Accounts","APST;Y",StaticStore.mPINString,"", "", "", "", "4-4-N-Y-Y", "", "", "", "", "5", "false", "false", "N" };
			StaticStore.index = 220;
			StaticStore.tagType = "APST";
			StaticStore.FromListScreen = false;
			Intent intent = new Intent(getActivity(), DynamicCanvas.class);
			StaticStore.midlet.startFragment(getActivity(), intent);
		} else if(index == 274){
			StaticStore.selectedAccNumberTo = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = false;
			StaticStore.secondMyOwnAccFlag = true;
	    	String type = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
			if(type.equals("EMPTY")|| StaticStore.isAccTypeRefresh){
				  StaticStore.isAccTypeRefresh = false;
				  StaticStore.tagType = "APTF";
				  StaticStore.accTypeCompleteData = "";
				  StaticStore.indexBeforeAccTypeInitiation = -12;
				  StaticStore.index = 220;
			      StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF","001","","1","false","false","N"};
			      StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}else{
				  StaticStore.menuDesc[220] = new String[] {"My Own Accounts","APST;Y",StaticStore.mPINString, "", "", "", "", "", "", "", "", "", "5", "false", "false", "N" };
				  StaticStore.index = 220;
				  StaticStore.tagType = "APST";
				  StaticStore.FromListScreen = false;
				  Intent intent = new Intent(getActivity(), DynamicCanvas.class);
			      StaticStore.midlet.startFragment(getActivity(), intent);
			}
		} else if(index == 275){
			StaticStore.selectedAccTypeTo = getSelectedString();
			StaticStore.fromAccountList = true;
			StaticStore.accpaymentFlag = true;
			StaticStore.menuDesc[220] = new String[] {"My Own Accounts","APST;Y",StaticStore.mPINString, StaticStore.selectedAccNumberTo, "", "Amount","Remarks", "", "", "", "1-10-ND-N-N","0-25-AN-N-N", "5", "false", "false", "N" };
			StaticStore.menuDesc[220][4] = StaticStore.midlet.accTypeArr[selectedIndex][0];
			StaticStore.index = 220;
			StaticStore.tagType = "APST";
			StaticStore.FromListScreen = false;
			Intent intent = new Intent(getActivity(), DynamicCanvas.class);
			StaticStore.midlet.startFragment(getActivity(), intent);
		} else if(index == 276){
			if (moreFlag) {
				if (StaticStore.isPincode) {
					StaticStore.menuDesc[220] = new String [] {"E-Lobby","APEL;N","P",StaticStore.midlet.pinBranch," ",StaticStore.midlet.nextStartRecNumber,"","","","","4","true","true","N"};
					StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[220] = new String [] {"E-Lobby","APEL;N","L",StaticStore.midlet.pinBranch,StaticStore.midlet.locatorArea,StaticStore.midlet.nextStartRecNumber,"","","","","4","true","true","N"};
					StaticStore.index = 220;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
				}
			}else {
				String temp = "";
				if (StaticStore.isPincode) {
					temp = StaticStore.midlet.eLobbySearch[selectedIndex][0] +";"+  StaticStore.midlet.pinBranch;
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "ESXX");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				} else {
					temp = StaticStore.midlet.eLobbySearch[selectedIndex][0];
					saveList();
					Intent intent = new Intent(getActivity() , DisplayableView.class);
	              	intent.putExtra("response",temp);
	               	intent.putExtra("formName", "ESYY");
	               	StaticStore.midlet.startFragment(getActivity(),intent);
				}
			}
		} else if(index == 277){
			if(selectedIndex == 0){
				StaticStore.menuDesc[222][7] = "";
				StaticStore.menuDesc[222][8] = "";
				StaticStore.menuDesc[222][9] = "";
				StaticStore.menuDesc[222][10] = "";
				StaticStore.menuDesc[222][13] = "";
				StaticStore.menuDesc[222][16] = "";
				StaticStore.menuDesc[222][17] = "";
				StaticStore.menuDesc[222][18] = "";
				StaticStore.menuDesc[222][19] = "";
				StaticStore.index = 222;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
			}else{
				StaticStore.menuDesc[222][5] = "";
				StaticStore.menuDesc[222][6] = "";
				StaticStore.menuDesc[222][13] = "";
				StaticStore.menuDesc[222][14] = "";
				StaticStore.menuDesc[222][15] = "";
				StaticStore.index = 222;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
			}
		}else if(index == 278){
			if (isMore) {
				StaticStore.menuDesc[141][3] = StaticStore.midlet.ifscBankName;
				StaticStore.menuDesc[141][4] = StaticStore.midlet.ifscLocationName;
				StaticStore.menuDesc[141][5] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 141;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}else {
				
				StaticStore.neftbenereg = selectedIndex;
				StaticStore.menuDesc[82][7] = "";
            	StaticStore.index = 82;
            	StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
			}
		} else if(index == 279){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.menuDesc[220] = new String [] {"Inward Cheque Status","APLI;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
			StaticStore.index = 220;
			StaticStore.tagType = "APLI";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else if(index == 280){

			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
		    StaticStore.menuDesc[220] = new String [] {"Outward Cheque Status","APLO;Y",StaticStore.mPINString,"4-4-N-Y-Y","1","false","false","N"};
			StaticStore.index = 220;
			StaticStore.tagType = "APLO";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}
	}
	
	private void doAction281To300(int index, int selectedIndex, boolean moreFlag) {
		if(index == 281){
				saveList();
				String temp="";
				temp = StaticStore.midlet.inwardChequeStatusList[selectedIndex][0]+";"+StaticStore.midlet.inwardChequeStatusList[selectedIndex][1]+";"+StaticStore.midlet.inwardChequeStatusList[selectedIndex][2];
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "LI00");
				StaticStore.midlet.startFragment(getActivity(),intent);
		}else if(index == 282){
				saveList();
				String temp="";
				temp = StaticStore.midlet.outwardChequeStatusList[selectedIndex][0]+";"+StaticStore.midlet.outwardChequeStatusList[selectedIndex][1]+";"+StaticStore.midlet.outwardChequeStatusList[selectedIndex][2];
				Intent intent = new Intent(getActivity() , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "LO00");
				StaticStore.midlet.startFragment(getActivity(),intent);
		}else if(index == 283){
			StaticStore.selectedAccNumber = StaticStore.accessibleAccountNumbers[selectedIndex];
			StaticStore.selectedAccType = StaticStore.accessibleAccTypes[selectedIndex];
			StaticStore.fromAccountList = true;
			StaticStore.FromListScreen = false;
			StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN1;Y",StaticStore.mPINString,"Search Institution","001","4-4-N-Y-Y","0-20-AN-N-N","","3","false","false","Y"};
			StaticStore.index = 220;
			StaticStore.tagType = "APN1";
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
		}else if(index == 284){
			if (moreFlag) {
				StaticStore.menuDesc[220][3] = StaticStore.midlet.feesPaySearchInst;
				StaticStore.menuDesc[220][4] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.tagType = "APN1";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
			}else {
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.feesPaySearchInstN100Res = StaticStore.midlet.feesPaySearchInstN100Res.substring(5).trim();
				StaticStore.midlet.feesPaySearchInst = StaticStore.midlet.feesPaySearchInstN100Res.substring(0,1);
				StaticStore.midlet.feesPaySearchInstN100Res = StaticStore.midlet.feesPaySearchInstN100Res.substring(StaticStore.midlet.feesPaySearchInstN100Res.indexOf(";") + 1);
				StaticStore.midlet.feesPaySearchInstInputFields = StaticStore.midlet.feesPaySearchInstN100Res.substring(0,StaticStore.midlet.feesPaySearchInstN100Res.indexOf(";")+1);
				StaticStore.midlet.feesPaySearchInstInputFieldsList = StaticStore.midlet.getSplittedValues(StaticStore.midlet.feesPaySearchInstInputFields,20,'#',true,StaticStore.midlet.collegeUniqueDetailsList);
				
				String[][] arr = new String[StaticStore.midlet.feesPaySearchInstInputFieldsList.length][10];
				for(int i = 0;i< StaticStore.midlet.feesPaySearchInstInputFieldsList .length;i++){
					arr[i] = StaticStore.midlet.feesPaySearchInstInputFieldsList[i];
				}
				
				String[] N1_input = new String[Integer.parseInt(arr[selectedIndex][2]) *2];
				
				for(int i = 0;i <Integer.parseInt(arr[selectedIndex][2]);i++){
					N1_input[i] = arr[selectedIndex][2 + (i+1)];
					N1_input[i + N1_input.length/2] = arr[selectedIndex][2 + (i+1)];
				}
				
				for(int i = 0;i<N1_input.length;i++){
					if(i < N1_input.length/2){
						N1_input[i] = N1_input[i].substring(0, N1_input[i].indexOf("/"));
					}else{
						N1_input[i] = N1_input[i].substring(N1_input[i].indexOf("/")+1,N1_input[i].length());
					}
				}
				
				String[] tempReq = null;
				tempReq = new String[9+N1_input.length];
				tempReq[0]= "Instant Bill Payment";
				tempReq[1]= "APN2;Y"; 
				tempReq[2]= StaticStore.mPINString; 
				tempReq[3]= arr[selectedIndex][0];
				int pos = 0;
				
				for(int i = 0,j=0;i<N1_input.length/2;i++,j+=1){
					tempReq[4+i]=N1_input[j];
					pos=4+i;
				}
				tempReq[pos+=1]="001";
				tempReq[pos+=1]="";
				tempReq[pos+=1]="";

				for(int i = pos+1,j= N1_input.length/2;i< pos+1 + N1_input.length/2;i++,j+=1){
					tempReq[i] = N1_input[j];
				}
				
				StaticStore.Display_header = new String[N1_input.length/2];
				for(int i = 0;i< N1_input.length/2;i++)	{
					StaticStore.Display_header[i] =	N1_input[i];
				}
				
				tempReq[2+pos+N1_input.length/2] = String.valueOf(3+(N1_input.length/2));
				tempReq[(2+pos+N1_input.length/2) - 1] = "";
				StaticStore.menuDesc[220] = new String [tempReq.length+3];
				for(int i=0;i<tempReq.length;i++){
					StaticStore.menuDesc[220][i] = tempReq[i];
				}
				StaticStore.menuDesc[220][tempReq.length] = "true";
				StaticStore.menuDesc[220][tempReq.length+1] = "true";
				StaticStore.menuDesc[220][tempReq.length+2] = "Y"; 
				StaticStore.index = 220;
				StaticStore.tagType = "APN2";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
			}
		}else if(index == 285){
			if (moreFlag) {
				StaticStore.menuDesc[220][7] = StaticStore.midlet.nextStartRecNumber;
				StaticStore.index = 220;
				StaticStore.tagType = "APN2";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
			}else {
				StaticStore.collegeDetailsID = StaticStore.midlet.collegeDetailsList[selectedIndex][0];
				StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN4;Y",StaticStore.mPINString,StaticStore.instID,StaticStore.instConfirmDetails,StaticStore.collegeDetailsID,"","001","","","","","","","6","true","true","Y"};
				StaticStore.index = 220;
				StaticStore.tagType = "APN4";
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(), DynamicCanvas.class));
			}
		}else if(index == 286){
			StaticStore.index = 220;
			StaticStore.tagType = "APN5";
			String PaymentFlag = getSelectedString().substring(0,1).toUpperCase();
			StaticStore.midlet.instConfirmDetailsList = StaticStore.instConfirmDetails.split(":");
			String temp2 = null;
			for(int i = 0;i< StaticStore.midlet.instConfirmDetailsList .length;i++){
				if (temp2 == "") {
					temp2 = StaticStore.midlet.instConfirmDetailsList [i].toString();
				}else{
					temp2 = temp2 + ","+ StaticStore.midlet.instConfirmDetailsList [i].toString();
				}
			}
			if(PaymentFlag.equals("F"))	{
				StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN5;Y",StaticStore.mPINString,StaticStore.N400TxnID,StaticStore.instID,StaticStore.instConfirmDetails,PaymentFlag,StaticStore.midlet.N400DetailsList[3],StaticStore.midlet.N400DetailsList[8],StaticStore.midlet.N400DetailsList[9],StaticStore.midlet.N400DetailsList[10],StaticStore.midlet.N400DetailsList[11],"","","","","","","","","","","10","true","true","N"};
			}else if(PaymentFlag.equals("M")){
				StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN5;Y",StaticStore.mPINString,StaticStore.N400TxnID,StaticStore.instID,StaticStore.instConfirmDetails,PaymentFlag,StaticStore.midlet.N400DetailsList[7],StaticStore.midlet.N400DetailsList[8],StaticStore.midlet.N400DetailsList[9],StaticStore.midlet.N400DetailsList[10],StaticStore.midlet.N400DetailsList[11],"","","","","","","","","","","10","true","true","N"};
			}else if(PaymentFlag.equals("E")){
				StaticStore.FromListScreen = false;
				StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN5;Y",StaticStore.mPINString,StaticStore.N400TxnID,StaticStore.instID,StaticStore.instConfirmDetails,PaymentFlag,"Amount (Rs.)",StaticStore.midlet.N400DetailsList[8],StaticStore.midlet.N400DetailsList[9],StaticStore.midlet.N400DetailsList[10],StaticStore.midlet.N400DetailsList[11],"","","","","","1-10-ND-N-N","","","","","10","true","true","N"};
			}else if(PaymentFlag.equals("P")){
				StaticStore.FromListScreen = false;
				StaticStore.menuDesc[220] = new String [] {"Fees Payment","APN5;Y",StaticStore.mPINString,StaticStore.N400TxnID,StaticStore.instID,StaticStore.instConfirmDetails,PaymentFlag,"Amount (Rs.)",StaticStore.midlet.N400DetailsList[8],StaticStore.midlet.N400DetailsList[9],StaticStore.midlet.N400DetailsList[10],StaticStore.midlet.N400DetailsList[11],"","","","","","1-10-ND-N-N","","","","","10","true","true","N"};
			}
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
		}
	}
	private String getSelectedString() {
		// TODO Auto-generated method stub
		return menuItem[selectedIndex];
	}
	public String maskedAccNumber(String accNumber) {
		// 0018 XXX484 001
		String prsgTemp = "";
		try {
			prsgTemp += accNumber.substring(0, 4);
			prsgTemp += "XXX";
//			prsgTemp += accNumber.substring(accNumber.length() - 6);
			prsgTemp +=	accNumber.substring(7,accNumber.length());
		} catch (Exception e) {
			return accNumber;
		}
		return prsgTemp;
	}

	
	
	
	public String[] getFilledAccArray() {
		int filledAccArraySize = 0;
		for (int i = 0; i < StaticStore.accountNumbers.length; i++) {
			if (!StaticStore.accountNumbers[i].equals("0")) {
				filledAccArraySize++;
			}
		}
		String[] filledAccArray = new String[filledAccArraySize];
		for (int i = 0; i < filledAccArraySize; i++) {
			filledAccArray[i] = maskedAccNumber(StaticStore.accountNumbers[i]);
		}
		return filledAccArray;
	}


	public String[] getAccessibleAccArray(String reqTag) {
		String[] strArray = null;
		StaticStore.accessibleAccountNumbers = null;
		StaticStore.accessibleAccTypes = null;
		int totAccountArray = 0;
		for (int i = 0; i < StaticStore.accType.length; i++) {
			if (!StaticStore.accountNumbers[i].equals("0")) {
				totAccountArray++;
			}
		}
		StaticStore.accessibleAccountNumbers = new String[totAccountArray];
		StaticStore.accessibleAccTypes = new String[totAccountArray];
		strArray = new String[totAccountArray];
		int valueInc = 0;
		for (int i = 0; i < totAccountArray; i++) {
			StaticStore.accessibleAccountNumbers[valueInc] = StaticStore.accountNumbers[i];
			StaticStore.accessibleAccTypes[valueInc] = StaticStore.accType[i];
			strArray[valueInc] = maskedAccNumber(StaticStore.accountNumbers[i]);
			valueInc++;
		}
		return strArray;
	}

	public String[] getAccessibleAccArrayTo(String reqTag) {
		String[] strArray = null;
		String tempAccNoTo = "";
		String tempAccTypeTo = "";
		String[] filledAccArray = null;
		StaticStore.accessibleAccountNumbers = null;
		StaticStore.accessibleAccTypes = null;
		int totAccountArray = 0;
		for (int i = 0; i < StaticStore.accType.length; i++) {
			if (!StaticStore.accountNumbers[i].equals("0")) {
				totAccountArray++;
			}
		}
		int valueInc = 0;
		for (int i = 0; i < totAccountArray; i++) {
			if(!StaticStore.accountNumbers[i].equals(StaticStore.selectedAccNumberFrom)){
				if (tempAccNoTo == "") {
					tempAccNoTo = StaticStore.accountNumbers[i].toString();
					tempAccTypeTo = StaticStore.accType[i].toString();
				}else{
					tempAccNoTo = tempAccNoTo + ";"+ StaticStore.accountNumbers[i].toString();
					tempAccTypeTo = tempAccNoTo + ";"+ StaticStore.accType[i].toString();
				}
			}
		}
		String filledAccNoArrayTemp[] = tempAccNoTo.split(";");
		String filledAccTypeArrayTemp[] = tempAccNoTo.split(";");
		int count = filledAccNoArrayTemp.length;
		StaticStore.accessibleAccountNumbers = new String[count];
		StaticStore.accessibleAccTypes = new String[count];
		strArray = new String[count];
		for (int i = 0; i < count; i++) {
			StaticStore.accessibleAccountNumbers[valueInc] = filledAccNoArrayTemp[i];
			StaticStore.accessibleAccTypes[valueInc] = filledAccTypeArrayTemp[i];
			strArray[valueInc] = maskedAccNumber(filledAccNoArrayTemp[i]);
			valueInc++;
		}
		return strArray;
	}
	
	
	
	
	private boolean checkAccessible(String reqTag, String accType) {
		if(accType.equals("CC")) {
			if(reqTag.equals("BE") || reqTag.equals("MS")
					|| reqTag.equals("L9")|| reqTag.equals("G8")
					|| reqTag.equals("G7")|| reqTag.equals("G9")
					|| reqTag.equals("L3")|| reqTag.equals("GP")
					|| reqTag.equals("G5")|| reqTag.equals("G1")
					|| reqTag.equals("G3")|| reqTag.equals("G2")
					|| reqTag.equals("IB")|| reqTag.equals("BP")
					|| reqTag.equals("DP")|| reqTag.equals("DO")) {
				return true;
			}
		} else if(accType.equals("CV")) {
			if(reqTag.equals("BE") || reqTag.equals("MS")
					|| reqTag.equals("L9")|| reqTag.equals("G8")
					|| reqTag.equals("G7")|| reqTag.equals("G9")
					|| reqTag.equals("L3")|| reqTag.equals("GP")
					|| reqTag.equals("G5")|| reqTag.equals("G1")
					|| reqTag.equals("G3")|| reqTag.equals("G2")
					|| reqTag.equals("IB")|| reqTag.equals("BP")
					|| reqTag.equals("DP")|| reqTag.equals("DO")) {
				return true;
			}
		} else {
			return true;
		}

		return false;
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
				arg0.cancel();
			}
		});

		return alertbox;
	}
	

	public AlertDialog.Builder neftRegister(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {

		AlertDialog.Builder alertbox = tempAlertBox;

		alertbox.setMessage(message);

		alertbox.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				StaticStore.LogPrinter('i',"StaticStore.isSearchAndRegister ==> "+StaticStore.isSearchAndRegister);
				if (StaticStore.isSearchAndRegister) {   
					StaticStore.isSearchAndRegister = false;
					StaticStore.menuDesc[82] = new String[] {"Add Beneficiary","APQ2;Y",StaticStore.mPINString,"A/C No.","001","IFS Code","Beneficiary Name",
			             "4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-20-ANW-N-N","5","true","true","Y"};
//					StaticStore.menuDesc[82][2] = StaticStore.mPINString; 
//					StaticStore.menuDesc[82][7] = "4-4-N-Y-Y";
					StaticStore.globalMPIN = "";
					//midlet.getDisplay().setCurrent(new DynamicCanvas(midlet, midlet.getDisplay(),
					//		82));
					StaticStore.index = 82; 
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				} else {
					StaticStore.menuDesc[82] = new String[] {"Add Beneficiary","APQ2;Y",StaticStore.mPINString,"A/C No.","001","IFS Code","Beneficiary Name",
				             "4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-20-ANW-N-N","5","true","true","Y"};
//					StaticStore.menuDesc[82][7] = "4-4-N-Y-Y";
					/*midlet.getDisplay().setCurrent(new DynamicCanvas(midlet, midlet.getDisplay(),
									82));*/
					StaticStore.index = 82;
					StaticStore.FromListScreen = false;
					StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
							DynamicCanvas.class));
				}
				//midlet.getDisplay().setCurrent(new DynamicCanvas(midlet, midlet.getDisplay(),139));
				arg0.dismiss();
			}
		});
		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			// do something when the button is clicked 
			
			public void onClick(DialogInterface arg0, int arg1) {
				StaticStore.LogPrinter('i',"StaticStore.isSearchAndRegister On Cancel ==> "+StaticStore.isSearchAndRegister);
				StaticStore.isSearchAndRegister = true;
				StaticStore.menuDesc[82][7] = "";
				StaticStore.index = 139;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
						DynamicCanvas.class));	
				arg0.dismiss();
			}
		});

		return alertbox;
	}
	public void saveList(){
		StaticStore.isBack = false;
		StaticStore.listContent[StaticStore.indexCtr] = menuItem;
		StaticStore.listHeading[StaticStore.indexCtr] = heading;
		StaticStore.listMore[StaticStore.indexCtr] = isMore;
		StaticStore.listIndexArray[StaticStore.indexCtr] = index;
		StaticStore.selectedIndexArray[StaticStore.indexCtr] = selectedIndex;
		StaticStore.listImageArray[StaticStore.indexCtr] =isImageTextList;
		
		StaticStore.LogPrinter('i',"Save List items -->"+StaticStore.listImageArray[StaticStore.indexCtr]);
		StaticStore.indexCtr += 1;
	}
	public String getAccType() {
		// TODO Auto-generated method stub
		String Acctype = null;
		try{
			if(selectedIndex == 0){
				Acctype = "SA";
			}else if (selectedIndex == 1){
				Acctype = "CA";
			}else if (selectedIndex == 2){
				Acctype = "OA";
			}else if (selectedIndex == 3){
				Acctype = "CC";
			}else if (selectedIndex == 4){
				Acctype = "LA";
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return Acctype;
	}
	//	 public Intent getcommonmenu(Context context,String[] menuItem,int listIndex,boolean more,String header){
	//
	//		 mPayIntent = new Intent(getActivity(), ListSelection.class);
	//			mPayIntent.putExtra("listIndex", 143);
	//			mPayIntent.putExtra("menuItem", menuItem);
	
	//			mPayIntent.putExtra("listHeader",header);
	//			mPayIntent.putExtra("more", false);
	//			StaticStore.midlet.startFragment(getActivity(),mPayIntent);
	//	 }
	//
	private void InitiateRequestAP2T(){
		StaticStore.midlet.topUpIndicator = "O";
		StaticStore.tagType = "AP2T";
		if(StaticStore.isFrom1T00Response){
			StaticStore.isFrom1T00Response = false;
			StaticStore.menuDesc[220] = new String []{"Recharge","AP2T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"Operator Search String","","0001","","","2-20-ANW-N-N","","","5","false","false","Y"};
		}else{
			 StaticStore.menuDesc[220] = new String []{"Recharge","AP2T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,"Operator Search String","","0001","","","2-20-ANW-N-N","","","5","false","false","Y"}; //4-4-N-Y-Y
		}
		String temp= "";
		String tempCategory[]= StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryId();
		for (int i = 0; i < tempCategory.length; i++) {
			temp = temp+tempCategory[i]+":";
		}
		temp = temp.substring(0,temp.length()-1);
		StaticStore.menuDesc[220][5] = temp;
		StaticStore.index = 220;
		StaticStore.FromListScreen = false;
		StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
	}
	
	private void InitiateAP3T_R_AP4T(String tag,String labelDetails,String selctedString,String topUpIndicator){

		StaticStore.tagType = tag;
		StaticStore.index = 220;
		StaticStore.LogPrinter('i',"::::::::::::" + labelDetails);
		int labelCount = labelDetails.split("#").length;// labelDetails.split("#").length;
		StaticStore.LogPrinter('i',"::::::::::::" + labelCount);
		int arrayLength = 0;
		int countFor3T = 0;
		if(tag.equals("AP3T"))
			arrayLength = (2 * labelCount) + (2 * 7) + 4;
		else
			arrayLength = (2 * labelCount) + (2 * 7) + 2;
		
		StaticStore.menuDesc[220] = new String[arrayLength];
		StaticStore.menuDesc[220][0] = "Recharge";
		StaticStore.menuDesc[220][1] = tag + ";Y";
		StaticStore.menuDesc[220][2] = StaticStore.mPINString;
		StaticStore.menuDesc[220][3] = StaticStore.rechargeSelcetedCategoryID;
		StaticStore.menuDesc[220][4] = selctedString;
		StaticStore.menuDesc[220][5] = topUpIndicator;
		String temp = "";
		String tempCategory[] = StaticStore.midlet.rechargeDynamicInputs.recharge
				.getCategoryId();
		for (int i = 0; i < tempCategory.length; i++) {
			temp = temp + tempCategory[i] + ":";
		}
		temp = temp.substring(0, temp.length() - 1);
		StaticStore.menuDesc[220][6] = temp;
		// labelscount ==2
		for (int i = 0; i < labelCount - 1; i++) {
			StaticStore.menuDesc[220][7 + i] = labelDetails.substring(0,labelDetails.indexOf('*'));
			labelDetails = labelDetails.substring(labelDetails.indexOf('*') + 1);
			String validationString = labelDetails.substring(0, labelDetails.indexOf('#'));
			if(validationString.indexOf("AM") != -1){
				StaticStore.LogPrinter('i',"?????????????:::::::::::::::::::"+validationString);
				validationString = convertedValidationString(validationString);
			}
			if(tag.equals("AP3T")){
				StaticStore.menuDesc[220][7 + labelCount + 6 + i] = validationString;
			}else{
				StaticStore.menuDesc[220][7 + labelCount + 5 + i] = validationString;
			}
			labelDetails = labelDetails.substring(labelDetails.indexOf('#') + 1);
		}
		StaticStore.menuDesc[220][7 + labelCount - 1] = labelDetails.substring(0, labelDetails.indexOf('*'));
		labelDetails = labelDetails.substring(labelDetails.indexOf('*') + 1);
		StaticStore.LogPrinter('i',"?????????????:::::::::::::::::::"+labelDetails);
		if(labelDetails.indexOf("AM") != -1){
			StaticStore.LogPrinter('i',"?????????????:::::::::::::::::::"+labelDetails);
			labelDetails = convertedValidationString(labelDetails);
		}
		if(tag.equals("AP3T")){
			StaticStore.menuDesc[220][7 + labelCount + 6 + (labelCount - 1)] = labelDetails;
		}else{
			StaticStore.menuDesc[220][7 + labelCount + 5 + (labelCount - 1)] = labelDetails;
		}
		if(tag.equals("AP3T")){
			StaticStore.menuDesc[220][7 + labelCount] = StaticStore.midlet.txnID;
			countFor3T++;
		}
		
		if(tag.equals("AP4T")){
		StaticStore.menuDesc[220][7 + labelCount +countFor3T] = ""; //4-4-N-Y-Y
		}else
		{
			StaticStore.menuDesc[220][7 + labelCount +countFor3T] = "";
		}
		StaticStore.menuDesc[220][7 + labelCount +countFor3T+ 1] = "";
		StaticStore.menuDesc[220][7 + labelCount +countFor3T+ 2] = "";
		StaticStore.menuDesc[220][7 + labelCount +countFor3T+ 3] = "";
		StaticStore.menuDesc[220][7 + labelCount +countFor3T+ 4] = "";
		if(tag.equals("AP3T")){
			StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 5] = "";
			countFor3T++;
			StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 5] = 6 + labelCount
			+ "";
		}else{
			StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 5] = 5 + labelCount
			+ "";
		}
		StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 6] = "true";
		StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 7] = "true";
		StaticStore.menuDesc[220][7 + (2 * labelCount)+countFor3T + 8] = "Y";
		StaticStore.FromListScreen = false;
		StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),DynamicCanvas.class));
	}
	private String convertedValidationString(String validationString){
		StaticStore.midlet.rechargeMinAmount = validationString.substring(0,validationString.indexOf('-'));
		validationString = validationString.substring(validationString.indexOf('-')+1);
		StaticStore.midlet.rechargeMaxAmount = validationString.substring(0,validationString.indexOf('-'));
		validationString = validationString.substring(validationString.indexOf('-')+1);
		StaticStore.LogPrinter('i',"vaol::::::::"+validationString);
		validationString = validationString.substring(validationString.indexOf('-')+1);
		StaticStore.LogPrinter('i',"vaol::::::::"+validationString);
		validationString = StaticStore.midlet.rechargeMinAmount.length()+"-"+StaticStore.midlet.rechargeMaxAmount.length()+"-N-"+validationString;
		
		return validationString;
	}


	public static boolean onBackPressed(Context context2) {
		// TODO Auto-generated method stub
		StaticStore.eLobbyLocationFlag = false;
		StaticStore.initialMyOwnAccFlag = false;
		StaticStore.secondMyOwnAccFlag = false;
		StaticStore.forAddBiller = false;
		StaticStore.LogPrinter('i',"onBackPressed List Onback index counter ==> "+StaticStore.indexCtr);
			if(StaticStore.indexCtr == 1)
			{
				StaticStore.midlet.isImageTextList = true;
			}else
			{
				StaticStore.midlet.isImageTextList = false;
			}

			/*else if (keyCode == KeyEvent.KEYCODE_BACK && index == 121 && index == 106) {
			StaticStore.selectedGridIndex = 0;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),GridScreenView.class));
			return true;
		}*/
	    if (StaticStore.isMenuFromDashBoard) {	
			StaticStore.midlet.startFragment(context2,new Intent(context2,FirstPageActivity.class));
			return true;
	    }else if (index == 63) {
//			exit();
			return false;  
		}else if(StaticStore.depositAccBalance && !StaticStore.isInbox){
			StaticStore.depositAccBalance = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return true;
		}else if(StaticStore.loanBalance && !StaticStore.isInbox){
			StaticStore.loanBalance = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return true;
		}else if ( (index == 1 || index == 12 || index == 75 || index == 106 || index == 121 || index == 122 || index == 8 || index == 3 || index == 253)) {
			if(StaticStore.istablet){
				StaticStore.backlistIndex = index;
								return false;	
			}else{
			StaticStore.midlet.startFragment(context2,new Intent(context2,GridScreenView.class));
			return true;
			}
		}else if (  StaticStore.isInbox) {
			StaticStore.midlet.startFragment(context2,new Intent(context2,GridScreenView.class));
			return true;    			
		}else if (  (index == 206 || index == 200)) {
			StaticStore.index = 218;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(context2,new Intent(context2,DynamicCanvas.class));
			return true;
		}else if (  index == 203) {
			StaticStore.index = 209;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(context2,new Intent(context2,DynamicCanvas.class));
			return true;
		}else if (  (index == 36 || index == 138 || index == 250)) {
			Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
			StaticStore.midlet.startFragment(context2,myIntent);
			return true;
		}
		/*else if (  (!StaticStore.IsPermitted || !StaticStore.IsPersonalInfoGot)) {
			exitMIDlet(new AlertDialog.Builder(this),
					"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,context2).show();

			return true;
		} */else if (  StaticStore.isAirline && StaticStore.indexAir != 1000) {
			/*if(StaticStore.indexAir == 1000){
	            new AirlineInput();
	        }else{
	        	StaticStore.indexAir -= 6;
	        	StaticStore.LogPrinter('i',">>>>>>>>inside on key down"+StaticStore.indexAir);
	            new AirlineInput(true);
	        }*/

			if(StaticStore.indexAir > 0){
				StaticStore.indexAir -= 6;
			}else{
				StaticStore.indexAir = 1000;
			}
			new AirlineInput("");
			StaticStore.LogPrinter('i',">>>>inside keycode back"+StaticStore.indexAir);
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(context2,new Intent(context2,
					DynamicCanvas.class));
			// new AirlineInput(midlet,display,getSelectedString());

			return true;
		}else if (  StaticStore.isAirline && StaticStore.indexAir == 1000) {
			Intent intent = new Intent(context2 , DisplayableView.class);
			intent.putExtra("response",StaticStore.midlet.airlineDispMsg);
			intent.putExtra("formName", "A100");
			StaticStore.midlet.startFragment(context2,intent);
			return true;
		}else if (StaticStore.indexCtr > 0 &&  index != 72 && (StaticStore.forBillerRegistration && heading.toUpperCase().startsWith("Biller Category".toUpperCase()) || !StaticStore.forBillerRegistration)) {
			StaticStore.forBillerRegistration = false;
			StaticStore.isBack = true;
			if(index == 221 || index == 222){
				StaticStore.isFrom1T00Response = false;
				StaticStore.LogPrinter('e', StaticStore.isFrom1T00Response+"");
			}
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
			Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
			StaticStore.midlet.startFragment(context2,myIntent);
			return true;
		}
		else if (   index == 72){
			//Note: If u comment this else if part it will return to the Dyanamic canvas screen //Siva
			//			StaticStore.index = 9;
			//			StaticStore.selectClassBack = true;
			//			StaticStore.menuDesc[9][6] = StaticStore.flightTiming;
			//			StaticStore.midlet.startFragment(context2,new Intent(context2,
			//					DynamicCanvas.class));
			StaticStore.indexCtr --;
			Intent myIntent = StaticStore.midlet.getAirlineMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);

			return true;
		}else if(StaticStore.forBillerRegistration && !heading.equals("Biller Category")){
			Intent myIntent = StaticStore.midlet.getCategoryList(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return true;
		}else{
			StaticStore.LogPrinter('i',"Calinng default");
//			exit();
			return false;//super.onKeyDown(keyCode, event);
		}
	}
	
	private int getTokenCount(String prsgMessage, char token) {
		int count = 0;
		char[] prbtarTemp = prsgMessage.toCharArray();

		for (int i = 0; i < prbtarTemp.length; i++) {
			if (prbtarTemp[i] == token) {
				count++;
			}
		}

		return count;
	}
}