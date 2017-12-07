package com.fss.tmb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fss.tmb.R;

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

/**
 * 
 * @author manish.s
 * 
 */
public class CustomGridViewAdapterFirstPage extends ArrayAdapter<Object> {
	Context context;
	int layoutResourceId;
	ArrayList<String> data = new ArrayList<String>();
	
	ArrayList<String> selectedItemListNames = new ArrayList<String>();
	
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	static ArrayList<String> storePosition = new ArrayList<String>();
	
	int[] imageId = { R.drawable.generateotp_dashboard, R.drawable.quickpay_dashboard, R.drawable.imps_p2m,
			R.drawable.imps_p2u, R.drawable.cheque_dashboard, R.drawable.balance_enquiry_dashboard,
			R.drawable.hotlistdebit_card_dashboard, R.drawable.imps_p2a,R.drawable.mobilerecharge_dashboard,
			R.drawable.imps_p2p,R.drawable.billpay_dashboard,R.drawable.ministatement_dashboard,R.drawable.addmoreicon};
	
	
	@SuppressWarnings("unchecked")
	public CustomGridViewAdapterFirstPage(Context context, int layoutResourceId,
			ArrayList data, ArrayList selectedListNames) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.selectedItemListNames = selectedListNames ;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_textview);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_imageview);
			holder.txtTitle.setTextColor(Color.rgb(18, 56, 127));
			holder.checkboxItem = (CheckBox) row.findViewById(R.id.item_checkbox);
			if(StaticStore.width == 600 && StaticStore.height >= 1000 && !StaticStore.istablet){
				row.setPadding(0, 0, 0, 35);
			}
			row.setTag(holder);
		}else {
			holder = (RecordHolder) row.getTag();
		}
		
		String item = data.get(position);
		holder.txtTitle.setText(selectedItemListNames.get(position));
		holder.txtTitle.setVisibility(View.GONE) ;
		holder.imageItem.setTag(Integer.parseInt(data.get(position))) ;
		holder.imageItem.setImageResource(imageId[Integer.parseInt(data.get(position))]);
		if( selectedItemListNames.get(position).trim().equalsIgnoreCase("Generate OTP") ) {
			holder.imageItem.setImageResource(R.drawable.generateotp_dashboard);
		}else if( selectedItemListNames.get(position).trim().equalsIgnoreCase("Quick Pay - Within TMB") ) {
			holder.imageItem.setImageResource(R.drawable.quickpay_dashboard);
		}else if( selectedItemListNames.get(position).trim().equalsIgnoreCase("IMPS P2M Instant Pay") ) {
			holder.imageItem.setImageResource(R.drawable.imps_p2m);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("IMPS P2U Instant Pay") ) {
			holder.imageItem.setImageResource(R.drawable.imps_p2u);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Cheque Status") ) {
			holder.imageItem.setImageResource(R.drawable.cheque_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Balance Enquiry") ) {
			holder.imageItem.setImageResource(R.drawable.balance_enquiry_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Hotlist Debit Card") ) {
			holder.imageItem.setImageResource(R.drawable.hotlistdebit_card_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("IMPS P2A Instant Pay") ) {
			holder.imageItem.setImageResource(R.drawable.imps_p2a);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Mobile Recharge") ) {
			holder.imageItem.setImageResource(R.drawable.mobilerecharge_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("IMPS P2P Instant Pay") ) {
			holder.imageItem.setImageResource(R.drawable.imps_p2p);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Bill Pay") ) {
			holder.imageItem.setImageResource(R.drawable.billpay_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("Mini Statement") ) {
			holder.imageItem.setImageResource(R.drawable.ministatement_dashboard);
		}else if( selectedItemListNames.get(position).equalsIgnoreCase("AddMore") ) {	
			holder.imageItem.setImageResource(R.drawable.addmoreicon);
			holder.txtTitle.setText("");
		}
		return row;
	}

	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;
		CheckBox checkboxItem;

	}
}