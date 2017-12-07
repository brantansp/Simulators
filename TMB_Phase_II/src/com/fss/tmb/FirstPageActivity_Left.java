package com.fss.tmb;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.util.ByteArrayBuffer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FirstPageActivity_Left extends android.support.v4.app.Fragment{    
	LinearLayout gLayout;
	private long startTime;
	Intent intent ;
	Typeface mFontBold;
	private boolean gridLeftsidepane;
	View rootView;
	LinearLayout linearView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.firstpage_left, container, false);
		linearView = (LinearLayout)rootView.findViewById(R.id.linearView); 
		return rootView;
	}

	@Override
	public void onDetach() {
		super.onDetach();
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
	
	private Map<Integer, Image> setListTabBar() {
		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
		tabBarSetter.put(1, new Image(R.drawable.icon_exit, (StaticStore.enableHome?"Logout":"Exit")) );
		return tabBarSetter;
	}
	
	private OnClickListener tabBarListener = new OnClickListener() { 
		public void onClick(View v) {
			StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(getActivity());
			if(sessionflag){
				if ( v.getId() == 1) {
					exit();
				}  
			}
		}
	};
	
	private void exit() {
		exitMIDlet(new AlertDialog.Builder(getActivity()),
				"Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,getActivity()).show();			
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		exit();
		return true;
	}

	public AlertDialog.Builder exitMIDlet(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {
		AlertDialog.Builder alertbox = tempAlertBox;
		alertbox.setMessage(message);
		alertbox.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent myIntent = StaticStore.midlet.getExitScreen(context);
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
		});
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				return;
			}
		});
		return alertbox;
	}
}
