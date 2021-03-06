package com.fss.tmb;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Siva G
 *
 */
	public class Inbox extends android.support.v4.app.Fragment {

		private int index;
		private SelectedAdapter selectedAdapter;
		private String heading = "Inbox";
		private String[] menuItem;
		private int selectedIndex;
		private String[] messageStatus;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.listview, container, false);
			StaticStore.FromListScreen = false;
			StaticStore.isInbox = true;
			Bundle indexes = this.getArguments();
			index = indexes.getInt("listIndex");
			menuItem =  indexes.getStringArray("menuItem");
			messageStatus = indexes.getStringArray("messageStatus");
			TextView listHeading = (TextView)rootView.findViewById(R.id.listText);
			listHeading.setText(heading);
			listHeading.setTextSize((StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title));
			listHeading.setTextColor(Color.rgb(18, 56, 127));
			listHeading.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface));
			LinearLayout lLayout = (LinearLayout)rootView.findViewById(R.id.listTab);
			try {
				lLayout.addView(StaticStore.Tabbar(tabBarListener,setListTabBar(),getActivity(),false));
			} catch (Exception e) {
			 	// TODO Auto-generated catch block
			 	e.printStackTrace();  
			}
			selectedAdapter = new SelectedAdapter(getActivity(),android.R.layout.simple_list_item_1,menuItem,heading);
			selectedAdapter.setNotifyOnChange(true);
			ListView customList = (ListView)rootView.findViewById(R.id.listExample);
			customList.cancelLongPress();
			customList.setDividerHeight(5);
			customList.setAdapter(selectedAdapter);
			selectedAdapter.setSelectedPosition(0);
			customList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView selectedPosition, View view,int position, long ID) {
					Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
					if(sessionflag){
						selectedAdapter.setSelectedPosition(position);
						setSelectedIndex(position);
						doAction(99,selectedIndex);
					}
				}
			});
			return rootView;
		}

		 @Override
		    public void onPause() {
		    	// TODO Auto-generated method stub
		    	super.onPause();
		    	StaticStore.LogPrinter('e',"onPause() Inbox");
		    	StaticStore.midlet.onPauseCalled();
		    }
		    
		    @Override
		    public void onResume() {
		    	// TODO Auto-generated method stub
		    	super.onResume();
		    	StaticStore.LogPrinter('e',"onResume() Inbox");
		    	StaticStore.midlet.onResumeCalled();
		    }
		    @Override
			public void onDetach() {
			    super.onDetach();
			    StaticStore.LogPrinter('e',"onDetach() Inbox");
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
		//tabBarListener
		private OnClickListener tabBarListener = new OnClickListener() { 
			public void onClick(View v) {
				Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
				// do something
				if(sessionflag){
					if (v.getId() == 0 ) {
						StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getHome(getActivity()));
					} else if (v.getId() == 1) {
						getDelete();
					} else if ( v.getId() == 2) {
						getDeleteAll();
					}else if (v.getId() == 3) {
						StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getAbout(getActivity()));
					}else if (v.getId() == 4) {
						exit();
					}
				}
			}
	};
	
	private void getDelete() {
		getAlertDialog(new AlertDialog.Builder(getActivity()),
				"Are you sure you want to delete message ?", 99,getActivity()).show();

	}
	private void getDeleteAll() {
		getAlertDialog(new AlertDialog.Builder(getActivity()),
				"Are you sure you want to delete all the messages ?",101,getActivity()).show();

	}
	private void exit() {
		getAlertDialog(new AlertDialog.Builder(getActivity()),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?",100,getActivity()).show();

	}
	//tabBar image values
	private Map<Integer, Image> setListTabBar() {

		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
			if(!StaticStore.istablet)
			{
			tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
			}
		tabBarSetter.put(1, new Image(R.drawable.icon_delete, "Delete"));
		tabBarSetter.put(2, new Image(R.drawable.icon_delete_all, "Delete All"));
		//tabBarSetter.put(3, new Image(R.drawable.icon_about, "About"));
		tabBarSetter.put(4,  new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")));
		
		return tabBarSetter;
	}
	
		private void doAction(int index, int selectedIndex) {

			if(index == 99){
				try{
					StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.doAction(getActivity(),index,selectedIndex));
				}catch(Exception e){
					StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.get_ResponseInbox(getActivity()));
				}
			}
		}
		
	    public AlertDialog.Builder getAlertDialog(AlertDialog.Builder tempAlertBox,
				String message, int index, Context context) {

			AlertDialog.Builder alertbox = tempAlertBox;

			alertbox.setMessage(message);
			final int tempIndex = index;
			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							if(tempIndex != 100){
								Intent intent = doAlertProcess(getActivity(),tempIndex); 
								if(intent != null){
									StaticStore.midlet.startFragment(getActivity(),intent);
								}else{
									StaticStore.ToastDisplay(getActivity(),"No Messages to delete");
								}
							}else if(tempIndex == 100){
								Intent myIntent = StaticStore.midlet.getExitScreen(getActivity());
								StaticStore.midlet.startFragment(getActivity(),myIntent);
							}
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
	    

		public void setSelectedIndex(int selectedIndex) {
			this.selectedIndex = selectedIndex;
		}
		public int getSelectedIndex() {
			return selectedIndex;   
		}
		public Intent doAlertProcess(Context context, int tempIndex) {
			Intent myIntent = new Intent();
			if (tempIndex == 99) {
				// Delete single messages
				
				int pritSub;
				StaticStore.LogPrinter('i',">>>>StaticStore.midlet.responseMessages>>>."+ StaticStore.midlet.responseMessages);
				if (StaticStore.midlet.responseMessages != null) {
					if (StaticStore.midlet.responseMessages.length > 0) {
						pritSub = getSelectedIndex();
						StaticStore.midlet.responseMessages = RmsStore
								.deleteInboxRecordStore(
										StaticStore.midlet.responseMessages,
										pritSub,
										StaticStore.midlet.responseMessages.length,
										StaticStore.midlet.responseInboxMessages[pritSub],
										RmsStore.parsedRecords);
					}
				}
				selectedIndex = (byte) getSelectedIndex();
				if (selectedIndex == StaticStore.midlet.responseMessages.length
						&& StaticStore.midlet.responseMessages.length != 0)
					setSelectedIndex(getSelectedIndex() - 1);
				else if (StaticStore.midlet.responseMessages.length != 0)
					setSelectedIndex(getSelectedIndex());
				try {
					if (StaticStore.midlet.responseMessages.length > 0) {
						myIntent = StaticStore.midlet
								.get_ResponseInbox(getActivity());
					} else {
						StaticStore.midlet.responseMessages = null;
						myIntent = new Intent(getActivity(),GridScreenView.class);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (tempIndex == 101) {
				// Delete all messages

				if (StaticStore.midlet.responseMessages != null) {
					if (StaticStore.midlet.responseMessages.length > 0) {
						RmsStore.deleteRow(RmsStore.TABLE_ROW_VALUE_INB);
						StaticStore.midlet.responseMessages = null;
					}
				}
				try {
					myIntent = new Intent(getActivity(),GridScreenView.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return myIntent;
		}
		
		public static boolean onBackPressed(Context context) {

			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context);
			if(sessionflag){
				StaticStore.midlet.startFragment(context,new Intent(context, GridScreenView.class));
				return true;//super.onKeyDown(keyCode, event);
			}else{
				StaticStore.midlet.getsessionTimeOut(context);
				return false;
			}
		}
	}