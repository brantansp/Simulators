package com.fss.tmb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPageActivity extends Fragment {

	private GridView gridView;
	LinearLayout lltab;
	LinearLayout gLayout;
	private static int RESULT_LOAD_IMAGE = 1;
	public static final String MyPREFERENCES = "MyPre";// file name
	public static final String key = "nameKey";
	private ArrayList<String> selectedItemsgridArray = new ArrayList<String>();
	private ArrayList<String> selected_Items_Names_gridArray = new ArrayList<String>();
	private CustomGridViewAdapterFirstPage customGridAdapter;
	private ImageView imageView1_save, ProfileImage, MainMenu;
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	private static final int REQ_CODE_PICK_IMAGE = 0;
	private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
	private ArrayList<String> MenuItems_Link_Page = new ArrayList<String>();
	Bitmap btmap;
	private int totalMenusItemsSize;
	Typeface mFontBold;
	private TextView textView1_datetime, welcome, username;
	SharedPreferences prefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.firstpage, container, false);
		StaticStore.isFromLoginScreen = false;
		StaticStore.forBillerRegistration = false;
		StaticStore.forAPLOBack = false;
		StaticStore.forAPLIBack = false;
		StaticStore.loanBalance = false;
		StaticStore.depositAccBalance = false;
		StaticStore.forAPDOBack = false;
		StaticStore.isMenuFromDashBoard = true;
		StaticStore.initialMyOwnAccFlag = false;
		StaticStore.secondMyOwnAccFlag = false;
		StaticStore.isMyOwnAccSync = false;
		StaticStore.forAddBiller = false;
		StaticStore.forTNEBNote = false;
		StaticStore.forCOCTAX = false;
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
		if(StaticStore.istablet){
			StaticStore.isDashBoardLeftSideView = true;
		}
		StaticStore.midlet.savelistinit();
		mFontBold = Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface);

		imageView1_save = (ImageView) rootView.findViewById(R.id.imageView1_save);
		gLayout = (LinearLayout) rootView.findViewById(R.id.gridList);
		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		ProfileImage = (ImageView) rootView.findViewById(R.id.imageView1);
		MainMenu = (ImageView) rootView.findViewById(R.id.imageView2);
		welcome = (TextView) rootView.findViewById(R.id.textView1);
		username = (TextView) rootView.findViewById(R.id.textView2);
		textView1_datetime = (TextView) rootView.findViewById(R.id.textView1_datetime);
		welcome.setTypeface(mFontBold);

		if ((StaticStore.accOwner.length() == 32)
				|| (StaticStore.accOwner.length() < 32)) {
			StaticStore.accOwner = StaticStore.accOwner.toString()
					.toLowerCase();
		} else {
			StaticStore.accOwner = StaticStore.accOwner.substring(0, 32);
			StaticStore.accOwner = StaticStore.accOwner.toString()
					.toLowerCase();
		}

		if (StaticStore.accOwner == null || StaticStore.accOwner.equals("")|| StaticStore.accOwner.equalsIgnoreCase("null")) {
			welcome.setText("Welcome ");
			username.setText("User ");
		} else {
			StringBuilder titleCase = new StringBuilder();
			boolean nextTitleCase = true;
			for (char c : StaticStore.accOwner.toCharArray()) {
				if (Character.isSpaceChar(c)) {
					nextTitleCase = true;
				} else if (nextTitleCase) {
					c = Character.toTitleCase(c);
					nextTitleCase = false;
				}
				titleCase.append(c);
			}
			StaticStore.accOwner = titleCase.toString();
			welcome.setText("Welcome ");
			username.setText(StaticStore.accOwner);
		}
		welcome.setTextSize((StaticStore.istablet ? StaticStore.tFontsize : StaticStore.mFontsize));
		welcome.setTextColor(Color.rgb(18, 56, 127));
		username.setTextColor(Color.rgb(18, 56, 127));
		textView1_datetime.setTextColor(Color.rgb(18, 56, 127));
		
		retriveLogggedIn_DateTime();

		prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);

		if (prefs.contains(key)) {
			try {
				String u = prefs.getString(key, "");
				File imgFile = new  File(u);

				if(imgFile.exists()){
					ExifInterface exif = null;
					try {
						exif = new ExifInterface(imgFile.getAbsolutePath());
					} catch (IOException e1) {
						e1.printStackTrace();
					} 
					int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);

					Bitmap btmap = null;
					btmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
					Bitmap resizedBitMap = Bitmap.createScaledBitmap(btmap, 200, 200,true);
					
					if (orientation == 8) {
						resizedBitMap = rotateImage(resizedBitMap, -90);
					} else if (orientation == 6) {
						resizedBitMap = rotateImage(resizedBitMap, 90);
					} else if (orientation == 3) {
						resizedBitMap = rotateImage(resizedBitMap, 180);
					} else {

					}
					
					ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
					resizedBitMap.compress(Bitmap.CompressFormat.JPEG, 100, bos2);
					byte[] byteDatas = bos2.toByteArray();
					Bitmap conv_bm = getCircleBitmap(resizedBitMap, 100);
					ProfileImage.setImageBitmap(conv_bm);
				}else{
					if(!StaticStore.isDashBoardProfileImageRemoved){
						Toast.makeText(getActivity(), "Uploaded image does not exist. Please upload new image.", Toast.LENGTH_SHORT).show();
					}
				}
			} catch (Exception e) {
			}
		}
		
		int selectedItemSize = prefs.getInt("selecteditemscount", 0);
		if (selectedItemSize > 0) {
			Set<String> selectedItemsNames = prefs.getStringSet("selecteditemsnames", null);
			List<String> totalSelectedItems = new ArrayList<String>(selectedItemsNames);
			totalMenusItemsSize = totalSelectedItems.size();

			for (int i = 0; i < totalSelectedItems.size(); i++) {
				selectedItemsgridArray.add(totalSelectedItems.get(i));
			}

			Set<String> selectedItemsCheckListNames = prefs.getStringSet(
					"selectedItemsCheckListNames", null);

			List<String> totalSelectedItemsCheckListNames = new ArrayList<String>(
					selectedItemsCheckListNames);

			for (int i = 0; i < totalSelectedItemsCheckListNames.size(); i++) {

				selected_Items_Names_gridArray
						.add(totalSelectedItemsCheckListNames.get(i));

			}

			// plus symbol purpose..

			selectedItemsgridArray.add("12");

			selected_Items_Names_gridArray.add("AddMore");

			customGridAdapter = new CustomGridViewAdapterFirstPage(
					getActivity(), R.layout.row_grid_first,
					selectedItemsgridArray, selected_Items_Names_gridArray);

			gridView.setAdapter(customGridAdapter);

		} else if (selectedItemSize == 0) {

			// if no menus selected already,

			// just show the Add Symbol only...

			// if first time Loading this screen....

			ArrayList<String> storePosition = new ArrayList<String>();

			storePosition.add("9");

			storePosition.add("1");

			ArrayList<String> storePositionItems_Names = new ArrayList<String>();

			storePositionItems_Names.add("IMPS P2P Instant Pay");

			storePositionItems_Names.add("Quick Pay - Within TMB");

			SharedPreferences.Editor editor = getActivity()
					.getSharedPreferences(MY_PREFS_NAME, 0).edit();

			editor.putInt("selecteditemscount", 2);

			// store selected Items Positions

			Set<String> set = new HashSet<String>();
			set.addAll(storePosition);
			editor.putStringSet("selecteditemsnames", set);

			// store selected Items Names

			Set<String> selectedListNames_Set = new HashSet<String>();
			selectedListNames_Set.addAll(storePositionItems_Names);
			editor.putStringSet("selectedItemsCheckListNames",
					selectedListNames_Set);
			editor.commit();

			totalMenusItemsSize = 2;

			selectedItemsgridArray.add("9");

			selectedItemsgridArray.add("1");

			selectedItemsgridArray.add("12"); // plus symbol purpose..

			selected_Items_Names_gridArray.add("IMPS P2P Instant Pay");

			selected_Items_Names_gridArray.add("Quick Pay - Within TMB");

			selected_Items_Names_gridArray.add("AddMore");

			customGridAdapter = new CustomGridViewAdapterFirstPage(
					getActivity(), R.layout.row_grid_first,
					selectedItemsgridArray, selected_Items_Names_gridArray);

			gridView.setAdapter(customGridAdapter);

		}
		

		try {
			lltab = StaticStore.Tabbar(tabBarListener, setListTabBar(),
					getActivity(), false);
			gLayout.addView(lltab);

			final ViewTreeObserver observer2 = lltab.getViewTreeObserver();
			observer2
					.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
						public void onGlobalLayout() {
							StaticStore.Footer_Height = lltab.getHeight();
							lltab.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,

			int position, long id) {
				Boolean sessionflag = StaticStore.midlet
						.getsessionTimeOut(StaticStore.context);
				if (sessionflag) {

					// here need to make the selected menus buttons links
					// individually

					if (totalMenusItemsSize == position) {
						StaticStore.midlet.startFragment(getActivity(),
								new Intent(getActivity(),
										SecondPageActivity.class));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Generate OTP")) {
						StaticStore.tagType = "APMO";
						StaticStore.menuDesc[220] = new String[] {
								"Generate OTP", "APMO;Y",
								StaticStore.mPINString, "4-4-N-Y-Y", "1",
								"false", "false", "N" };
						StaticStore.index = 220;
						StaticStore.FromListScreen = false;
						StaticStore.midlet.startFragment(getActivity(),
								new Intent(getActivity(), DynamicCanvas.class));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Quick Pay - Within TMB")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(174);
						StaticStore.midlet.listObject.setTag("FT");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("IMPS P2M Instant Pay")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(239);
						StaticStore.midlet.listObject.setTag("OT");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("IMPS P2U Instant Pay")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(176);
						StaticStore.midlet.listObject.setTag("FT");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Cheque Status")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(133);
						StaticStore.midlet.listObject.setTag("LW");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Balance Enquiry")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(154);
						StaticStore.midlet.listObject.setTag("BE");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Hotlist Debit Card")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(248);
						StaticStore.midlet.listObject.setTag("HL");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("IMPS P2A Instant Pay")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(224);
						StaticStore.midlet.listObject.setTag("K1");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Mobile Recharge")) {
						StaticStore.isInbox = false;
						StaticStore.rechargeStatus = false;
						StaticStore.midlet.moreFlag = false;
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(161);
						StaticStore.midlet.listObject.setTag("R5");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("IMPS P2P Instant Pay")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(153);
						StaticStore.midlet.listObject.setTag("OT");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Bill Pay")) {
						StaticStore.midlet.isAdhocPayment = true;
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(169);
						StaticStore.midlet.listObject.setTag("B2");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					} else if (selected_Items_Names_gridArray.get(position)
							.trim().equalsIgnoreCase("Mini Statement")) {
						StaticStore.midlet.listObject = new ListObject();
						StaticStore.midlet.listObject.setIndex(155);
						StaticStore.midlet.listObject.setTag("MS");
						StaticStore.midlet.listObject
								.setHeading(StaticStore.accNumberHeadingName);
						StaticStore.midlet.listObject.setMore(false);
						StaticStore.midlet.startFragment(getActivity(),
								StaticStore.midlet.accountScreenCreation(
										StaticStore.midlet.listObject,
										getActivity()));
					}

					// Toast.makeText(getApplicationContext(),
					// "GoTo: "+selected_Items_Names_gridArray.get(position),
					// 1).show() ;
					// o == BalanceEnquiry , 1 == MiniStatement, up to end level
					// [ BalanceEnq,MiniStatement,FundTransfer...etc.]

					// like that..etc..
				}
			}
		});

		ProfileImage.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {
					// go to image library and pick the image
					if (prefs.contains(key)) {
						String u = prefs.getString(key, "");
						File imgFile = new  File(u);

						if(imgFile.exists()){
							profileImageDetail(
									new AlertDialog.Builder(getActivity()),
									"Do you want to Upload or Remove Image?",100, getActivity()).show();
						}else{
							StaticStore.isDashBoardProfileImageRemoved = false;
							StaticStore.midlet.writeinMemory(getActivity());
							Intent intent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							
							
							// ******** code for crop image
							 /*intent.putExtra("crop", "true");
							 intent.putExtra("aspectX", 80);
							 intent.putExtra("aspectY", 80);
							 intent.putExtra("outputX", 150);
							 intent.putExtra("outputY", 150);*/
							 
							try {
								startActivityForResult(intent, RESULT_LOAD_IMAGE);
							} catch (ActivityNotFoundException e) {
								// Do nothing for now
							}
						}
					
					}else{
						Intent intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						
						
						// ******** code for crop image
						 /*intent.putExtra("crop", "true");
						 intent.putExtra("aspectX", 80);
						 intent.putExtra("aspectY", 80);
						 intent.putExtra("outputX", 150);
						 intent.putExtra("outputY", 150);*/
						 
						try {
							startActivityForResult(intent, RESULT_LOAD_IMAGE);
						} catch (ActivityNotFoundException e) {
							// Do nothing for now
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		MainMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {
					if(StaticStore.istablet){
						StaticStore.isDashBoardLeftSideView = false;
					}
					StaticStore.midlet.startFragment(getActivity(), new Intent(
							getActivity(), GridScreenView.class));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		return rootView;
	}

	private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		try{
			
			final Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(100, 100, 90, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			bitmap.recycle();
		
	} catch (Exception e) {
	
		e.printStackTrace();
	}
return output;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == getActivity().RESULT_OK && null != data) {
			
			try {
			Log.v("sony1", data.toString());
				Uri mImageCaptureUri = data.getData();
				Log.v("sony2", mImageCaptureUri.toString());
				ExifInterface exif = null;
				exif = new ExifInterface(ImageFilePath.getPath(StaticStore.context, mImageCaptureUri));
				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
				Bitmap bitMap = null;
				bitMap = MediaStore.Images.Media.getBitmap(StaticStore.context.getContentResolver(),mImageCaptureUri);
				Bitmap resizedBitMap = Bitmap.createScaledBitmap(bitMap, 200,200, true);

				if (orientation == 8) {
					resizedBitMap = rotateImage(resizedBitMap, -90);
				} else if (orientation == 6) {
					resizedBitMap = rotateImage(resizedBitMap, 90);
				} else if (orientation == 3) {
					resizedBitMap = rotateImage(resizedBitMap, 180);
				} else {

				}

				ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
				resizedBitMap.compress(Bitmap.CompressFormat.JPEG, 100, bos2);
				byte[] byteDatas = bos2.toByteArray();
				Bitmap conv_bm = getCircleBitmap(resizedBitMap, 100);
				ProfileImage.setImageBitmap(conv_bm);
				ProfileImage.setScaleType(ScaleType.FIT_XY);
				Editor editor = prefs.edit();
				editor.putString(key, getRealPathFromURI(mImageCaptureUri));
				editor.commit();
				
			} catch (Exception e) {
				Toast.makeText(getActivity(), "Your Device Does Not Support Crop Action.", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}else{
			switch (requestCode) {
				case REQ_CODE_PICK_IMAGE:
				if (resultCode == getActivity().RESULT_OK) {  
					if (data!=null){
					   File tempFile = getTempFile();
					   String filePath= tempFile.toString();
   					   Bitmap bitMap = null;
						try {
							bitMap = MediaStore.Images.Media.getBitmap(StaticStore.context.getContentResolver(),getTempUri());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						Bitmap resizedBitMap = Bitmap.createScaledBitmap(bitMap, 200,200, true);

					   ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
					   resizedBitMap.compress(Bitmap.CompressFormat.JPEG, 100, bos2);
					   byte[] byteDatas = bos2.toByteArray();
						
					   Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);
					   Bitmap conv_bm = getCircleBitmap(selectedImage, 100);
					   ProfileImage.setImageBitmap(conv_bm);
					   ProfileImage.setScaleType(ScaleType.CENTER_CROP);
					   Editor editor = prefs.edit();
					   editor.putString(key, getRealPathFromURI(getTempUri()));
					   editor.commit();
					   Intent myIntent = new Intent(StaticStore.context,FirstPageActivity.class);
					   StaticStore.midlet.startFragment(getActivity(),myIntent);
					}
				}
			}
		 }
	}
	
	public String getGalleryImagePath(Intent data) { 
		   Uri imgUri = data.getData(); 
		   String filePath = ""; 
		   if (data.getType() == null) { 
		     // For getting images from default gallery app. 
		     String[] filePathColumn = { MediaStore.Images.Media.DATA }; 
		     Cursor cursor = getActivity().getContentResolver().query(imgUri, filePathColumn, null, null, null); 
		     cursor.moveToFirst(); 
		     int columnIndex = cursor.getColumnIndex(filePathColumn[0]); 
		     filePath = cursor.getString(columnIndex); 
		     cursor.close(); 
		   } else if (data.getType().equals("image/jpeg") || data.getType().equals("image/png")) { 
		     // For getting images from dropbox or any other gallery apps. 
		     filePath = imgUri.getPath(); 
		  } 
		   return filePath; 
		 } 

	public String getRealPathFromURI(Uri contentUri)
	{
	    try
	    {
	        String[] proj = {MediaStore.Images.Media.DATA};
	        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	    catch (Exception e)
	    {
	        return contentUri.getPath();
	    }
	}
	
	
	public static Bitmap rotateImage(Bitmap source, float angle) {
		Bitmap retVal;

		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);

		return retVal;
	}

	public static String encodeTobase64(Bitmap image) {

		Bitmap immage = image;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// immage.compress(Bitmap.CompressFormat.PNG, 100, baos);

		immage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		byte[] b = baos.toByteArray();

		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


		return imageEncoded;

	}

	public static Bitmap decodeBase64(String input) {

		byte[] decodedByte = Base64.decode(input, 0);

		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);

	}

	private void retriveLogggedIn_DateTime() {

		SharedPreferences prefs = getActivity().getSharedPreferences(
				MY_PREFS_NAME, 0);

		String LastLoggedInDateTime = prefs.getString("LastLoggedIn", null);


		if (LastLoggedInDateTime != null) {

			textView1_datetime.setText("Last Login: " + LastLoggedInDateTime);
		}
		// else
		//
		// textView1_datetime.setText("Last Login: ");

	}

	private Map<Integer, Image> setListTabBar() {

		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
		tabBarSetter.put(1, new Image(R.drawable.icon_exit,
				(StaticStore.enableHome ? "Logout" : "Exit")));
		return tabBarSetter;
	}

	private OnClickListener tabBarListener = new OnClickListener() {
		public void onClick(View v) {
			StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
			Boolean sessionflag = StaticStore.midlet
					.getsessionTimeOut(StaticStore.context);
			if (sessionflag) {
				if (v.getId() == 1) {
					exit();
				} else if (v.getId() == 2) {
					StaticStore.midlet.startFragment(getActivity(), new Intent(
							getActivity(), GridScreenView.class));
				}
			}
		}
	};

	private void exit() {
		exitMIDlet(
				new AlertDialog.Builder(getActivity()),
				"Do you want to "
						+ (StaticStore.enableHome ? "logout" : "exit") + "?",
				100, getActivity()).show();
	}

	public AlertDialog.Builder exitMIDlet(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {

		AlertDialog.Builder alertbox = tempAlertBox;

		alertbox.setMessage(message);

		alertbox.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						StaticStore.LogPrinter('i',
								">>>>>>>>>>ready to close>>>>>>>");
						Intent myIntent = StaticStore.midlet
								.getExitScreen(context);
						StaticStore.midlet.startFragment(getActivity(),
								myIntent);
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

	public AlertDialog.Builder profileImageDetail(AlertDialog.Builder tempAlertBox,
			String message, int index, final Context context) {

		AlertDialog.Builder alertbox = tempAlertBox;

		alertbox.setMessage(message);

		alertbox.setPositiveButton("Upload",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						StaticStore.LogPrinter('i',
								">>>>>>>>>>ready to close>>>>>>>");
						StaticStore.isDashBoardProfileImageRemoved = false;
						StaticStore.midlet.writeinMemory(getActivity());
						/*Intent intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						

						// ******** code for crop image
						 intent.putExtra("crop", "true");
						 intent.putExtra("aspectX", 80);
						 intent.putExtra("aspectY", 80);
						 intent.putExtra("outputX", 150);
						 intent.putExtra("outputY", 150);
						try {
							startActivityForResult(intent, RESULT_LOAD_IMAGE);
						} catch (ActivityNotFoundException e) {
							// Do nothing for now
						}*/
						
						 Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 photoPickerIntent.setType("image/*");
						 photoPickerIntent.putExtra("crop", "true");
						 photoPickerIntent.putExtra("aspectX", 80);
						 photoPickerIntent.putExtra("aspectY", 80);
						 photoPickerIntent.putExtra("outputX", 150);
						 photoPickerIntent.putExtra("outputY", 150);
						 photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
						 startActivityForResult(photoPickerIntent, REQ_CODE_PICK_IMAGE);
						
					}
				});
		
		// set a negative/no button and create a listener
		if (prefs.contains(key)) {
				
				
		alertbox.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
			// do something when the button is clicked
			public void onClick(DialogInterface arg0, int arg1) {
				StaticStore.isDashBoardProfileImageRemoved = true;
				StaticStore.midlet.writeinMemory(getActivity());
				SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, 0).edit();
				editor.remove("key") ;
				editor.commit();
				ProfileImage.setBackgroundResource(R.drawable.aboutus);
				editor = prefs.edit();
				editor.putString(key, "");
				editor.commit();
				Intent myIntent = new Intent(StaticStore.context,FirstPageActivity.class);
				StaticStore.midlet.startFragment(getActivity(),myIntent);
			}
		});
		}

		return alertbox;
	}
	

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		if (isSDCARDMounted()) {

			File f = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
		try {
			f.createNewFile();
		} catch (IOException e) {

		}
			return f;
		} else {
			return null;
		}
	}
	
		private boolean isSDCARDMounted(){
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
		return true;
		return false;
		}

	
	// ----------------------------------------------------------------------------------------------------------

	class WsloginCall extends AsyncTask<Bitmap, Void, String> {

		// private final CustEnrol_interface custenrol;

		Activity custenrol;

		String convertedImage;

		public WsloginCall(FirstPageActivity loginActivity) {

			// this.custenrol = StaticStore.context.loginActivity;

		}

		@Override
		public void onPreExecute() {

			super.onPreExecute();

		}

		@Override
		protected String doInBackground(Bitmap... params) {

			return convertedImage = encodeTobase64(params[0]);
		}

		protected void onPostExecute(String result) {

			try {

				Editor editor = prefs.edit();

				editor.putString(key, convertedImage);

				editor.commit();

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
		 
	// ----------------------------------------------------------------------------------------------------------
}
