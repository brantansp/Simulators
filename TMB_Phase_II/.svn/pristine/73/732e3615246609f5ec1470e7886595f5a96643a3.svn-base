package com.fss.tmb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fss.tmb.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SecondPageActivity extends Fragment{

	private GridView gridView;
	LinearLayout lltab;
	LinearLayout gLayout;
	private ArrayList<String> gridArray = new ArrayList<String>();

	private CustomGridViewAdapterSecondPage customGridAdapter;

	public static final String MY_PREFS_NAME = "MyPrefsFile";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View rootView = inflater.inflate(R.layout.secondpage, container, false);

		 StaticStore.midlet.savelistinit();
		loadMenu_ItemsNames();

		gLayout = (LinearLayout)rootView.findViewById(R.id.gridList);
		gridView = (GridView)rootView.findViewById(R.id.gridView1);

		customGridAdapter = new CustomGridViewAdapterSecondPage(getActivity(), R.layout.row_grid_second,
				gridArray);

		gridView.setAdapter(customGridAdapter);

		
		try {
			lltab = StaticStore.Tabbar(tabBarListener,setListTabBar(),getActivity(),false);
			gLayout.addView(lltab);


			final ViewTreeObserver observer2 = lltab.getViewTreeObserver();
	         observer2.addOnGlobalLayoutListener(
	             new ViewTreeObserver.OnGlobalLayoutListener() {
	                 public void onGlobalLayout() {
	                	 StaticStore.Footer_Height = lltab.getHeight();
	                	 lltab.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	                     }
	                 });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
return rootView;
	}

	private Map<Integer, Image> setListTabBar() {

		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
		tabBarSetter.put(1,  new Image(R.drawable.icon_ok, "OK") );
		tabBarSetter.put(2,  new Image(R.drawable.icon_cancel, "Cancel") );
		tabBarSetter.put(3, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")) );

		return tabBarSetter;
	}
	
	private OnClickListener tabBarListener = new OnClickListener() { 
		public void onClick(View v) {
			StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
			if(sessionflag){
				if (v.getId() == 1) {
					int TotalSelected_MenusLength = CustomGridViewAdapterSecondPage.storePosition.size();
					if (TotalSelected_MenusLength > 0) {
						
						SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, 0).edit();
						
						editor.remove("selecteditemscount") ;
						
						editor.remove("selecteditemsnames") ;
						
						editor.remove("selectedItemsCheckListNames") ;
						
						editor.commit();
						
						editor.putInt("selecteditemscount",TotalSelected_MenusLength);
						
						// store selected Items Positions
						
						Set<String> set = new HashSet<String>();
						set.addAll(CustomGridViewAdapterSecondPage.storePosition);
						editor.putStringSet("selecteditemsnames", set);
						
						// store selected Items Names
						
						Set<String> selectedListNames_Set = new HashSet<String>();
						selectedListNames_Set.addAll(CustomGridViewAdapterSecondPage.storePositionItems_Names);
  					    editor.putStringSet("selectedItemsCheckListNames", selectedListNames_Set);
						editor.commit();
						StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),FirstPageActivity.class));
					} else {
						StaticStore.ToastDisplay(StaticStore.context,"Please select atleast one menu item.");
					}
				}else if (v.getId() == 2) {
					Intent myIntent = new Intent(StaticStore.context,FirstPageActivity.class);
					StaticStore.midlet.startFragment(getActivity(),myIntent);
				}else if (v.getId() == 3) {
					exit();
				}
			}		
			}
	};

	private void exit() {
		exitMIDlet(new AlertDialog.Builder(getActivity()),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,getActivity()).show();			
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

	
	private void loadMenu_ItemsNames() {
		gridArray.add("Generate OTP");
		gridArray.add("Quick Pay - Within TMB");
		gridArray.add("IMPS P2M Instant Pay");
		gridArray.add("IMPS P2U Instant Pay");
		gridArray.add("Cheque Status");
		gridArray.add("Balance Enquiry");
		gridArray.add("Hotlist Debit Card");
		gridArray.add("IMPS P2A Instant Pay");
		gridArray.add("Mobile Recharge");
		gridArray.add("IMPS P2P Instant Pay");
		gridArray.add("Bill Pay");
		gridArray.add("Mini Statement");
	}

}
