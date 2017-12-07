package com.fss.tmb;



import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectedAdapter extends ArrayAdapter {

		// used to keep selected position in ListView
		private int selectedPos = -1;	// init value for not-selected
		private String heading ="";
		public static boolean left = true;
	@SuppressWarnings("unchecked")
	
	public SelectedAdapter(Context context, int textViewResourceId,
			String[] objects, String heading) {
		super(context, textViewResourceId, objects);
		this.heading = heading;
	}

	public void setSelectedPosition(int pos) {
		selectedPos = pos;
		// inform the view of this change
		notifyDataSetChanged();
	}

	public int getSelectedPosition() {
		return selectedPos;
	}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View v = convertView;
		    // only inflate the view if it's null
		    if (v == null) {
		        LayoutInflater vi  = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        v = vi.inflate(R.layout.textview, null);
		    }
		    // get text view
		    
		    TextView label;
		    label = (TextView)v.findViewById(R.id.listText1);
		    label.setTextSize((StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize));
		       
		     //   if(StaticStore.midlet.isImageTextList){
		        	
		      if(StaticStore.midlet.isImageTextList && position < StaticStore.midlet.imagesListArray.length){
		        		label.setCompoundDrawablesWithIntrinsicBounds(StaticStore.midlet.imagesListArray[position],0,0,0);
		        		label.setCompoundDrawablePadding(10);
				}else{
						label.setCompoundDrawablesWithIntrinsicBounds(R.drawable.common,0,0,0);
						label.setCompoundDrawablePadding(10);
				}
				
	        // change the row color based on selected state
		       if(selectedPos == position){
		    	   if(getItem(position).toString() !=null) {
		    		   if(heading.equalsIgnoreCase(StaticStore.accNumberHeadingName))
		    		   {
		    			   label.setCompoundDrawablesWithIntrinsicBounds(R.drawable.acc_icon,0,0,0);
		    			   label.setCompoundDrawablePadding(10);
		    		   }
		    		   label.setBackgroundResource(R.drawable.menu_list_bgsel_left);  
		    	   }	
		    	   label.setTextColor(Color.WHITE); //Changed by ME on 25-May-2016 for showing ListText Color as WHITE //ByDefault BLACK
		       } else {
			if(heading.equalsIgnoreCase(StaticStore.accNumberHeadingName)){
        		label.setCompoundDrawablesWithIntrinsicBounds(R.drawable.acc_icon,0,0,0);
        		label.setCompoundDrawablePadding(10);
        	}
			label.setBackgroundResource(R.drawable.menu_list_bg_left);  
			label.setTextColor(Color.WHITE);  //Changed by ME on 25-May-2016 for showing ListText Color as WHITE //ByDefault BLACK
		}
		
		label.setText(getItem(position).toString());
		return (v);
	}
}