package com.fss.tmb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CustomGridViewAdapterSecondPage extends ArrayAdapter<Object> {
	
	private Context context;
	
	private int layoutResourceId;
	
	private ArrayList<String> data = new ArrayList<String>();
	
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	
	static ArrayList<String> storePosition = new ArrayList<String>();
	
	static ArrayList<String> storePositionItems_Names = new ArrayList<String>();
	
	int[] imageIdTicked = { R.drawable.generateotp_check, R.drawable.quickpay_check, R.drawable.imps_check,
			R.drawable.imps_check, R.drawable.cheque_check, R.drawable.balance_enquiry_check,
			R.drawable.hotlistdebit_card_check, R.drawable.imps_check,R.drawable.mobilerecharge_check,
			R.drawable.imps_check,R.drawable.billpay_check,R.drawable.ministatement_check };
	
	int[] imageId = { R.drawable.generateotp_uncheck, R.drawable.quickpay_uncheck, R.drawable.imps_uncheck,
			R.drawable.imps_uncheck, R.drawable.cheque_uncheck, R.drawable.balance_enquiry_uncheck,
			R.drawable.hotlistdebit_card_uncheck, R.drawable.imps_uncheck,R.drawable.mobilerecharge_uncheck,
			R.drawable.imps_uncheck,R.drawable.billpay_uncheck,R.drawable.ministatement_uncheck  };

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CustomGridViewAdapterSecondPage(Context context, int layoutResourceId,
			ArrayList menuitemsnames) {

		super(context, layoutResourceId, menuitemsnames);

		this.layoutResourceId = layoutResourceId;

		this.context = context;

		this.data = menuitemsnames;

		SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME,0);

		int selectedItemSize = prefs.getInt("selecteditemscount", 0);

		if (selectedItemSize > 0) {

			Set<String> selectedItemsNames = prefs.getStringSet(
					"selecteditemsnames", null);

			List<String> totalSelectedItems = new ArrayList<String>(
					selectedItemsNames);

			storePosition.clear();
			
			storePositionItems_Names.clear() ;

			for (int i = 0; i < totalSelectedItems.size(); i++) {

				storePosition.add(totalSelectedItems.get(i));
			}
			
			Set<String> selectedItemsCheckListNames = prefs.getStringSet(
					"selectedItemsCheckListNames", null);

			List<String> totalSelectedItemsCheckListNames = new ArrayList<String>(selectedItemsCheckListNames);
			
			for (int i = 0; i < totalSelectedItemsCheckListNames.size(); i++) {

				storePositionItems_Names.add(totalSelectedItemsCheckListNames.get(i));
			}

		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View row = convertView;

		RecordHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();

			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.txtTitle.setTextColor(Color.rgb(18, 56, 127));
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			holder.toggleButton1 = (ToggleButton) row.findViewById(R.id.toggleButton1) ;
			if(StaticStore.istablet){
				row.setPadding(0, 0, 0, 25);
			}
			
			if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				row.setPadding(0, 0, 0, 50);
			}
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		// holder.imageItem.setImageBitmap(item.getImage());
		
		holder.txtTitle.setText(data.get(position));

		holder.toggleButton1.setId(position) ;
		
		holder.toggleButton1.setBackgroundResource(imageId[Integer.parseInt("0"+position)]) ;
		
		
		for (int i = 0; i < storePosition.size(); i++) {

			int checkboxId = holder.toggleButton1.getId();

			if (storePosition.get(i).equalsIgnoreCase(Integer.toString(checkboxId)) ) {

				holder.toggleButton1.setChecked(true) ;
				
				holder.toggleButton1.setBackgroundResource(imageIdTicked[Integer.parseInt("0"+position)]) ;
				
			}

		}
		
		holder.toggleButton1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
				if(sessionflag){
				ToggleButton c =  (ToggleButton) v;
				
				if (c.isChecked()) {

					storePosition.add(Integer.toString(position));
					
					storePositionItems_Names.add(""+data.get(position)) ;
					
					c.setBackgroundResource(imageIdTicked[Integer.parseInt("0"+position)]) ;

				} else {

					for (int i = 0; i < storePosition.size(); i++) {

						if (storePosition.get(i).toString()
								.equalsIgnoreCase(Integer.toString(position))) {

							storePosition.remove(i);
							
							c.setBackgroundResource(imageId[Integer.parseInt("0"+position)]) ;
							
						}
					}
					
					for (int i = 0; i < storePositionItems_Names.size(); i++) {

						if ( storePositionItems_Names.get(i).equalsIgnoreCase(data.get(position)) ) {

							storePositionItems_Names.remove(i) ;
						}
					}
				}
				
			}	
			}
		}) ;
		
		return row;

	}

	static class RecordHolder {
		
		TextView txtTitle;
		
		ImageView imageItem;
		
		ToggleButton toggleButton1 ;

	}
}