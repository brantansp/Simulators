package com.fss.tmb;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayableView  extends android.support.v4.app.Fragment {



	public String[] FormattedArray = null; 

	public String[] headerArray = null;
	TableRow[] datas;
	TextView[] txtViews;
	private int ctr = 0;
	private String messageContent = "";
	private ScrollView scroll;
	private TableLayout table;
	private TableRow rowTitle;  
	private TextView header;
	public static String title;
	public static String formName;
	private String TXNID = "";
	//home - 0
	
	private int menuGroupIdForYes = 1;
	private int menuGroupIdForNo = 2;
	private int menuGroupIdForDefer = 3;
	private int menuGroupIdForUpdate = 4;
	private int menuGroupIdForMore = 5;
	//about - 6
	//exit - 7
	private int balArrLength = 0;
	private String rupees = "Rs.";
	private String formattedData;

	// response headers
	private String[][] successRespMsgs = {
			{"Flight ID","Flight Name","BRN No.","Amount"},//0,"Date & Time","Transaction ID"
																				// ,"Date & Time","Transaction ID"
   			{"PNR No.","BRN No.","Amount","Booked Date","Date & Time","Transaction ID"},//1
   			{"Acknowledgement","Date & Time","Transaction ID"},//2
			{ "Customer Name", "Initiated" },// 3
   			{"Order ID","Price","Booking Fee","Discount Amount","Seat Info","No. of Tickets"},//4
   			{"Recharge Pin","Date & Time","Transaction ID"},//5
   			{"Booking Code","City/Town","Venue Name","Movie Name","Show Date/Time","Seat No.","Amount",
   				"Booking Fee","Discount","Payment Mode","Date & Time","Transaction ID"},//6
   				{"Booking Person Name","From","To","Journey date","Flight Class","Flight No.",
   					"Departure Time","Fare","Order ID"},//7
   					{"Booking Person Name","Flight No.","From","To","Departure Date","Departure Time","Flight Class","Fare","No. Of Passengers","Order ID","Date & Time","Transaction ID"},      //8                      //8                
   					{ "Bank Name", "Branch Name", "A/C No.", "A/C Type","IFS Code", "Beneficiary Name","Bank ID" },// 9//,"Date & Time","Transaction ID"
   					{"Acknowledgement","PNR No.","Date & Time","Transaction ID"},//10
   					{"Nickname","Bill No.","Bill Date","Due Date","Total Amount","Minimum Amount","Penalty Amount","Transaction ID"},//11                                         
   					{"Distributor ID","Distributor Name","Corporate Name","User ID","Password","Date & Time","Transaction ID"},//12
   					{"Distributor ID","Distributor Name","Corporate Name","Subcorporate Name","User ID","Password","Date & Time","Transaction ID"},//13
   					{"Corporate Name","Subcorporate Name","Distributor ID","Distributor Name","Amount","Invoice No."},//14
   					{"Corporate Name","Subcorporate Name","Distributor ID","Distributor Name","Amount"},//15
   					{"Corporate Name","Distributor ID","Distributor Name","Amount","Invoice No."},//16
   					{"Corporate Name","Distributor ID","Distributor Name","Amount"},//17                                        
   					{"Corporate Name","Subcorporate Name","Distributor ID","Distributor Name","Per Day Transaction Limit","Per Transaction Limit"},//18
   					{"Corporate Name","Distributor ID","Distributor Name","Per Day Transaction Limit","Per Transaction Limit"},//19
   					{"Distributor ID","Distributor Name","Corporate Name","Subcorporate Name","Date & Time","Transaction ID"},//20
   					{"Distributor ID","Distributor Name","Corporate Name","Date & Time","Transaction ID"},//21
            {"Full Name","Mobile No.","A/C No.","Nickname"},//22
   					{"Full Name","A/C No.","A/C Type","Nickname"},//23 ,"Date & Time","Transaction ID"
   					{},//24 Dont use this array, because this is used for bill pay confirmation
   					{"Entity Name","Scheme Name","Sub-Scheme Name","Minimum Payable Amount","In Multiples of"},//25 mid: 12977 name to Entity name
   					{"Entity Name","Scheme Name","Minimum Payable Amount","In Multiples of"},//26 mid: 12977 name to Entity name
   					{"Name","Minimum Payable Amount","In Multiples of"},//27      
   					{"Your e-mail ID"},//28
   					{"Bank Name","A/C No.","A/C Type","Nickname"},//29
   					{"Acknowledgement","Campaign Expiry Date"},//30
   					{"Acknowledgement","Order ID","Date & Time","Transaction ID"},//31      
   					{"Biller ID","Biller Name"},//32                                         
   					//                                         {"Fixed Charges","Variable Charges"},//32                                         
   					{"From","To","No. of Tickets","No. of Adults","No. of Children","No. of Infants","Flight Class","Flight ID","Flight Name","Departure Date","Departure Time","Fare"},//33
   					{"Acknowledgement","Transaction ID"},//34
   					{"000","Name"},//35
   					{"Bill Type","Roll No.","Bill No.","Due Date","Total Amount","Minimum Amount","Penalty Amount"},//36
   					{"Bill Type","Roll No."},//37
   					{"Minimum Amount","Maximum Amount"},//38   
   					//{"Bank ID","Bank Name","Location","IFS Code","DateTime","TXNID"},//39
   					{"Bank Name","Location","IFS Code","Address","Transaction ID"},//39
   					{"City","Address","Pincode"},//40 
   					{"City","Address","Pincode"},// 41
   					{},//42 Dont use this array, because this is used for bill pay - pay bills confirmation
   					{ "IFS Code", "Bank Name", "Branch Name", "Address","Transaction ID" },// 43 Date & Time
   					{"Acknowledgement"},//44
   					{"Application Type","Version","Powered By"},//45
   					{" "},//46
   					{ "Nickname", "Mobile No." },// 47
   					{ "Nickname", "A/C No.", "A/C Type" },// 48
			{ "Nickname", "A/C No.", "IFS Code" },// 49
			{ "Mobile No.", "Amount" },// 50
			{ "Mobile No.", "Amount", "Surcharge" },// 51, "Date & Time","Transaction ID"
			{ "Mobile No.", "Transaction Date", "Actual Amount",
					"Withdrawn Amount", "Balance Amount", "Status",
					"Transaction ID" },// 52
			{ "Bank Name", "MMID", "Mobile No.", "Nickname" },// 53,"Date & Time","Transaction ID"
			{ "Mobile No.", "MMID", "Amount" },// 54
			{ "MMID", "Mobile No.", "Nickname" },// 55
			{ "A/C No.", "MMID No." },// 56
			{ "Message" },// 57
			{ "Declaration" },// 58
			{ " " },// 59
			// Details of record : Nickname*Bank name*MMID*Benf.Mobile Number
			{ "Nickname", "Bank Name", "MMID", "Mobile No." },// 60
			{ "Beneficiary Name","Bank Name", "Branch Name", "A/C No.", "A/C Type",
					"IFS Code" },// 61 // "Date & Time", "Transaction ID"
			{ "Nickname", "Card No." },// 62
			{},// 63 BalanceEnquiry.
			{},// 64 MiniStatement.
			{},// 65 CustomerProfile
			{},// 66 RechargeStatus
			{},// 67 ViewPaidBill
			{},// 68 Branch
			{ " " },// 69 RestartApp
			{ "Beneficiary Mobile No.", "Transaction Date",
					"Actual Amount", "Balance Amount" },// 70
					{},// 71 dead index
					{"Beneficiary A/C No.","Beneficiary A/C Type","Beneficiary IFS Code","Amount","Remarks"},//72
            {"A/C No.","A/C Type","IFS Code","Nickname"},//73
            {"A/C No.","A/C Type","Bank Name","Branch Name","IFS Code","Nickname"},//74,"Date & Time","Transaction ID"
            {"A/C No.","A/C Type","IFS Code","Nickname"},//75
            {"IMPS","MRT","RRN","A/C No.","Remarks"},//76
            {"Nickname","IFS Code","A/C No.","A/C Type","Amount","Remarks"},//77
            {},//78
            {"Current Application Version","Available New Version"},//79
            {"Current Application Version","Available New Version"},//80
	};
	private String[][] accountDescription = { { "CA", "Current Account" },// 0
			{ "CB", "Current Account - NRSR" },// 1
			{ "CC", "Cash Credit - Clean" },// 2
			{ "CD", "Cash Credit Hypothecation" },// 3
			{ "CE", "Current Account - NRE" },// 4
			{ "CF", "Current Account - NRO" },// 5
			{ "CG", "Current Account - EEFC" },// 6
			{ "CH", "Cash Credit - Pledge" },// 7
			{ "CI", "Cash Credit - Book Debts" },// 8
			{ "CJ", "Cash Credit -Lease Assets" },// 9
			{ "CK", "Overdraft - Clean" },// 10
			{ "CL", "Overdraft - Staff" },// 11
			{ "CM", "Overdraft - Others" },// 12
			{ "CN", "Nostro Account" },// 13
			{ "CO", "Overdraft Domestic" },// 14
			{ "CP", "Overdraft NRE" },// 15
			{ "CQ", "Overdraft - FCNR" },// 16
			{ "CR", "Overdraft NRNR" },// 17
			{ "CS", "Overdraft Shares/Sec." },// 18
			{ "CT", "OverDraft UnFixed General" },// 19
			{ "CU", "Current Account RFC" },// 20
			{ "CV", "Vostro Account" },// 21
			{ "CW", "Escrow Account" },// 22
			{ "CX", "UnFixed Deposit NRE" },// 23
			{ "CY", "OverDraft UnFixed (NRNR)" },// 24
			{ "CZ", "Suit Filed Accounts" },// 25
			{ "C1", "D/W,I/W,R/O AT PAR Cur Account" },// 26
			{ "C2", "Clean Overdraft IPO/Plot Funding" },// 27
			{ "C3", "Sweep/Reverse Sweep Account" },// 28
			{ "DO", "Recurring/Flexi Recurring Deposits" },// 29
			{ "EA", "Savings Account" },// 30
			{ "EB", "Savings Account NRO" },// 31
			{ "EC", "Savings Account - NRE" },// 32
			{ "ED", "Savings Account - RFC" },// 33
			{ "EE", "Savings Bank - Comfort" },// 34
			{ "EF", "Savings Account - NRSR" } // 35

	};
	private String tempMobileNo;
	private boolean balanceTypeFlag[] = new boolean[3];
	private Object dummyFormattedArray;;
	private Bundle displayDatas;

	// tabbar
	public static RelativeLayout r1;
	public static LinearLayout ll,llhead,llmain,llminbal,acttab;
	int index;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try{
			StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
			StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
			 StaticStore.LogPrinter('i',"called Display view ==>");
		 StaticStore.FromListScreen = true;
		 StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
		 // header image 
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 
					LayoutParams.FILL_PARENT);
		 llhead = new LinearLayout (getActivity());
		 llmain = new LinearLayout(getActivity());
		 llminbal = new LinearLayout(getActivity());
		
		 
		 llhead.setOrientation(LinearLayout.VERTICAL);
		 llmain.setOrientation(LinearLayout.VERTICAL);
		 llminbal.setOrientation(LinearLayout.VERTICAL);
		 
		 llhead.setBackgroundResource(R.drawable.header_bg);
		 llminbal.setBackgroundColor(Color.rgb(250, 249, 242));
		
		llhead.setGravity(Gravity.LEFT);
		displayDatas = this.getArguments();
		StaticStore.LogPrinter('i',">>>>ccccccccccccccc>>>");
		messageContent = displayDatas.getString("response");
		StaticStore.LogPrinter('i',">>>>>messageContent>>>>"+messageContent);
		formName = displayDatas.getString("formName");
		title = ""+displayDatas.getString("title");
		
		StaticStore.LogPrinter('i',"DisplayableView ==> formName"+formName);

		if (formName.equals("BW00")) {
			successRespMsgs[42] = StaticStore.midlet.dynamicBillpayRefArray;
			StaticStore.LogPrinter('i',"Disp --> "+Arrays.deepToString(successRespMsgs[42]));
		} else if (formName.equals("BillInfo")) {
			successRespMsgs[24] = StaticStore.midlet.dynamicDisplayableArr;
		}
		
		try {
			if(!formName.equals(StaticStore.MS)&&!formName.equals("PreNotification")&&!formName.equals("PostNotification")&&!formName.equals("Offers"))
			{
			FormattedArray = getFormattedArray(messageContent);
			index = getDynamicIndex(formName);
			headerArray = successRespMsgs[index];
			}
			else if(formName.equals("PreNotification"))
			{
				
				FormattedArray = StaticStore.midlet.preLoginLongDescList;
				headerArray = StaticStore.midlet.preLoginShortDescList;
			}else if(formName.equals("PostNotification"))
			{
				FormattedArray = StaticStore.midlet.postLoginLongDescList;
				headerArray = StaticStore.midlet.postLoginShortDescList;
			}else if(formName.equals("Offers"))
			{
				FormattedArray = getFormattedArray(messageContent);
				headerArray = StaticStore.midlet.dynamicDisplayableOffersArr;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		// StaticStore.LogPrinter('i',"::::::::::::FormattedArray" + FormattedArray.length);
		StaticStore.LogPrinter('i',"Header Array ---->>>>"+Arrays.deepToString(headerArray));
		StaticStore.LogPrinter('i',"formatArray  ---->>>>"+Arrays.deepToString(FormattedArray));
		
		
		scroll = new ScrollView(getActivity());
		table = new TableLayout(getActivity());
		
		if(formName.equals("Activation"))
		{
			LinearLayout l2 =new LinearLayout(getActivity());
			l2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			l2.setGravity(Gravity.CENTER);
			table.setBackgroundResource(R.drawable.dis_body_bg);
			TableLayout.LayoutParams llm = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llm.setMargins(10, 10, 10, 10);
			table.setLayoutParams(llm);
			ImageView smileicon = new ImageView(getActivity());
			smileicon.setBackgroundResource(R.drawable.smile_success);
			l2.addView(smileicon);
			table.addView(l2);

		}else{
			TableLayout.LayoutParams llm2 = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llm2.setMargins(20, 10, 20, 10);
			table.setLayoutParams(llm2);
		}
		table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		rowTitle = new TableRow(getActivity());
		rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
		TableLayout.LayoutParams tableRowParams =new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
		
		header = new TextView(getActivity());
		header.setText(title);
		if(StaticStore.istablet){
			header.setPadding(10, 10, 10, 10);
		}else{
			header.setPadding(5, 5, 5, 5);
		}
		header.setTextColor(Color.rgb(18, 56, 127));
		header.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
		header.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),StaticStore.mFont_Bold_Typeface));
		header.setGravity(Gravity.LEFT);
//		table.addView(header);
		
		
		// <--- START ---->
		if(formName.equals(StaticStore.MS))
		{
//			    MS00;12/03 MCONNECT/3072 5000.00 D#06/03 40003/2414/06 4.00 D#06/03 CA118REVTRAN 3.00 C#06/03 40002/2414/06 5.00 D#06/03 40001/2414/06 3.00 D#02/03 CHEQUE/BOOK/C 6674.00 D#06/11 MCONNECT/2317 20.00 D#06/11 MCONNECT/2317 10.00 D;;TXNID:326611340366;BID:C444444
			StaticStore.LogPrinter('i',"MINI State -->"+messageContent);
			String[] ms = messageContent.split(";");
			StaticStore.LogPrinter('i',"MS []  --- > "+Arrays.deepToString(ms));
			String[] Allrows = ms[0].split("#"); //Space is the spliter
			StaticStore.LogPrinter('i',"Allrows []  --- > "+Arrays.deepToString(Allrows));
			
			String[][] rowval = new String[Allrows.length][4];
			
			for(int i=0 ; i<rowval.length ; i++)
			{
			rowval[i] = Allrows[i].trim().split(" ");	
			}
			
			StaticStore.LogPrinter('i',"rowval [][]  --- > "+Arrays.deepToString(rowval));
			
			TextView accno,ava_bal,amount,title;
			accno =  new TextView(getActivity());
			accno.setText("Account No. "+StaticStore.selectedAccNumber);
			accno.setTextColor(Color.rgb(5, 122, 215));
			accno.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			accno.setGravity(Gravity.CENTER_HORIZONTAL);
			
			ava_bal =  new TextView(getActivity());
			ava_bal.setText("Available Balance");
			ava_bal.setTextColor(Color.DKGRAY);
			ava_bal.setTypeface(null,Typeface.BOLD);
			ava_bal.setTextSize(StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
			ava_bal.setGravity(Gravity.CENTER_HORIZONTAL);
			
			amount =  new TextView(getActivity());
			if(ms[1].toUpperCase().trim().startsWith("RS."))
			amount.setText(""+ms[1]);
			else
			amount.setText("Rs. "+ms[1]);
			amount.setTextColor(Color.rgb(5, 122, 215));
			amount.setTypeface(null,Typeface.BOLD);
			amount.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
			amount.setGravity(Gravity.CENTER_HORIZONTAL);
			
			View line = new View(getActivity());
			line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			line.setBackgroundColor(Color.rgb(128, 128, 128));
			
			View line1 = new View(getActivity());
			line1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			line1.setBackgroundColor(Color.rgb(128, 128, 128));
			
			title =  new TextView(getActivity());
			title.setText(" Summary ");
			title.setTextColor(Color.rgb(18, 56, 127));
			title.setTypeface(null,Typeface.BOLD);
			title.setTextSize(StaticStore.istablet?StaticStore.tFontsize_Title:StaticStore.mFontsize_Title);
//			title.setGravity(Gravity.LEFT);
			title.setBackgroundColor(Color.rgb(5, 122, 215));
			title.setPadding(5, 5, 0, 5);
			
			View line2 = new View(getActivity());
			line2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
			line2.setBackgroundColor(Color.rgb(128, 128, 128));
			
			llminbal.addView(accno);
			llminbal.addView(line);
			if(ms[1]!=null && !ms[1].trim().equals(""))
			{
			llminbal.addView(ava_bal);
			llminbal.addView(amount);
			}
			llminbal.addView(line1);
			llminbal.addView(title);
			llminbal.addView(line2);
			
			TextView hl,hr,vl,vr;
			for(int i=0 ; i< rowval.length ; i++)
			{
				View layout_number = (TableRow)LayoutInflater.from(getActivity()).inflate(R.layout.tablerow, null);
				
				View v = new View(getActivity());
				v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
				v.setBackgroundColor(Color.rgb(128, 128, 128));
				
				hl = (TextView)layout_number.findViewById(R.id.rowlineleft1);
				vl = (TextView)layout_number.findViewById(R.id.rowlineleft2);
				hr = (TextView)layout_number.findViewById(R.id.rowlineright1);
				vr = (TextView)layout_number.findViewById(R.id.rowlineright2);
				
				hl.setText(rowval[i][0]);
				hl.setTextSize(16);
				hl.setPadding(5, 0, 10, 0);
//				hl.setTextColor(Color.rgb(212, 202, 169));
				hl.setTextColor(Color.rgb(18, 56, 127));
				
				hr.setText(rowval[i][1]);
				hr.setTextSize(16);
				hr.setPadding(5, 0, 10, 0);
				hr.setTextColor(Color.rgb(18, 56, 127));
				
				vl.setText(rowval[i][2]);
				vl.setTextSize(12);
				vl.setPadding(5, 0, 10, 0);
				vl.setTextColor(Color.DKGRAY);
				
				vr.setText(rowval[i][3]);
				vr.setTextSize(12);
				vr.setPadding(5, 0, 10, 0);
				if(rowval[i][3].trim().toUpperCase().equals("D"))
				vr.setTextColor(Color.RED);
				else if(rowval[i][3].trim().toUpperCase().equals("C"))
					vr.setTextColor(Color.GREEN);
				else
					vr.setTextColor(Color.rgb(18, 56, 127));
				vr.setTypeface(null,Typeface.BOLD);
				table.addView(layout_number);
				table.addView(v);
				
				
			}
		}else
		{
			TableRow[] datas = new TableRow[headerArray.length * 2];
			TextView[] txtViews = new TextView[headerArray.length * 2];
			
			for (int i = 0, j = 0, k = 0; i < (headerArray.length * 2); i++) {
				datas[i] = new TableRow(getActivity());
				datas[i].setGravity(Gravity.CENTER);

				txtViews[i] = new TextView(getActivity());

				txtViews[i].setTypeface(Typeface.DEFAULT);
				txtViews[i].setGravity(Gravity.CENTER);
				
				if (i % 2 == 0) {
					if (formName.equals("restart")||formName.equals("alert")||formName.equals("HTTPERROR")) {
						txtViews[i].setText(headerArray[j]);
						continue;
						
					}else if(formName.equals("Activation")){
						continue;
					}else {
						txtViews[i].setText(headerArray[j]);
						txtViews[i].setPadding(5, 0, 5, 0);
						if(formName.equals(StaticStore.BE) && j == 0){
						txtViews[i].setBackgroundResource(R.drawable.acc_h_bg);
						txtViews[i].setTextColor(Color.rgb(5, 122, 215));
						}else{
						txtViews[i].setBackgroundResource(R.drawable.dis_header_bg);
						}
						txtViews[i].setTextColor(Color.rgb(18, 56, 127));
					}
					j++;
				} else {
					
					txtViews[i].setText(FormattedArray[k]);
					
					if(formName.equals("AccDisplay") && FormattedArray[k].equals("") && FormattedArray[k] != null ){
						StaticStore.LogPrinter('i',"Acc Display Came inside empty");
//						continue;
					}else
					{
						if(formName.equals(StaticStore.BE) && k == 0){
							
						}else{
						if(!formName.equals("Activation"))
							txtViews[i].setBackgroundResource(R.drawable.dis_body_bg);
						}
					}
					txtViews[i].setPadding(5, 0, 5, 5);
					txtViews[i].setTextColor(Color.rgb(18, 56, 127));
//					txtViews[i].setTextColor(Color.rgb(5, 122, 215));
					tableRowParams.setMargins(0, 0, 0, 15);
					datas[i].setLayoutParams(tableRowParams);
					if((headerArray[k].contains("Amount") || headerArray[k].contains("Fare") || headerArray[k].contains("Booking Fee") || headerArray[k].contains("Fixed Charges")||headerArray[k].contains("Price") || headerArray[k].contains("Surcharge"))&& (!FormattedArray[k].contains("Rs") && !FormattedArray[k].contains("NA") ))
					{
						txtViews[i].setText("Rs. "+FormattedArray[k]);
					}else if(headerArray[k].contains("Variable Charges")&& !FormattedArray[k].contains("%") ){
						txtViews[i].setText(FormattedArray[k]+" %");
					}
					else
					{
						txtViews[i].setText(FormattedArray[k]);
					}
					
					k++;
				}

				datas[i].addView(txtViews[i]);
				table.addView(datas[i]);

			}
		}
		
		// <--- END -->
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		scroll.setBackgroundResource(R.drawable.bg_innerpage);
		llmain.addView(header);
		if(formName.equals(StaticStore.MS))
		{
			llmain.addView(llminbal);
		}
		llmain.addView(table);
		scroll.addView(llmain);
		// tabbar
		r1 = new RelativeLayout(getActivity());
		/* Tabbar calling part */
		StaticStore.LogPrinter('i',"From displayable view");
		try {
			acttab = StaticStore.Tabbar(tabBarListener, setDisplayTabBar(),getActivity(),false);
			r1.addView(acttab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ABOVE, 333);

		// s.setId(101);
		scroll.setLayoutParams(params);
		scroll.setId(222);
		r1.addView(scroll);
		r1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		r1.setBackgroundColor(Color.rgb(18, 56, 127));
		
//		setContentView(r1);
		// setContentView(table);
//		rootView.
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
  //<------ Font Style Part Start ---->
		if(!formName.equals(StaticStore.MS))
		{
		final Typeface mFont = Typeface.createFromAsset(getActivity().getAssets(), "Segoe.ttf"); 
//		final int mFontsize = 25 ;
		final ViewGroup mContainer = (ViewGroup)table ; //contentgetRootView()
		DisplayableView.setAppFont(mContainer, mFont,StaticStore.istablet?StaticStore.tFontsize:StaticStore.mFontsize);
		}
		//<------ Font Style Part End ---->
		
		if (formName.equals("AccDisplay") && StaticStore.isUpdateClicked) { //Siva G
			StaticStore.isUpdateClicked = false;
			StaticStore.ToastDisplay(getActivity(), "Accounts synced successfully");
		}
		return r1;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//tab			finish();
			Intent intent = new RespectiveScreen().ResponceError(getActivity()); //Siva G
			StaticStore.midlet.startFragment(getActivity(),intent);
			return null;
//			new RespectiveScreen().ResponceError(getActivity());
		}
	}
	
    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	 StaticStore.LogPrinter('e',"onPause() Displayableview");
    	 StaticStore.midlet.onPauseCalled();
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	StaticStore.LogPrinter('e',"onResume() Displayableview");
    	StaticStore.midlet.onResumeCalled();
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


	private Map<Integer, Image> setDisplayTabBar() {

		Map<Integer, Image> tabBarSetter = new TreeMap<Integer, Image>();
		if(!StaticStore.istablet && !formName.equals("restart")){
			tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
		}else{
			if(!StaticStore.enableHome && StaticStore.IsPermitted && !formName.equals("restart") && !formName.equals("Activation")){
				tabBarSetter.put(0,  new Image(R.drawable.icon_home, "Home"));
			}
		}

		if (formName.endsWith("AB00") || formName.equals("M300")
				|| formName.equals("AR00") || formName.equals("CN00")
				|| formName.equals("Q200") || formName.equals("N100")
				|| formName.equals("RM00") || formName.equals("RA00")
				|| formName.equals("BillInfo") || formName.equals("Temple")
				|| formName.equals("Z600") || formName.equals("Partner")
				|| formName.equals("Campaign") || formName.equals("PSZZ")
				|| formName.equals("PSYY") || formName.equals("PSXX")
				|| formName.equals("PSWW") || formName.equals("SIZZ")
				|| formName.equals("SIYY") || formName.equals("Adhocpay")
				|| formName.equals("Billerlink") || formName.equals("A100")
				|| formName.equals("I100")|| formName.equals("2U00") || formName.equals("I4NP")
				|| formName.equals("I4PP") || formName.equals("ML00N")
				|| formName.equals("BW00") || formName.equals("FT00") || formName.equals("APDOConfirm") || formName.equals("APST") || formName.equals("APN2") || formName.equals("APN4")
				|| formName.equals("ICash") || formName.equals("SC00")
				|| formName.equals("W100") || formName.equals("1I00")|| formName.equals("QIMPS")
				|| formName.equals("QIMPS REG") || formName.equals("GTESTCNF")
				|| formName.equals("fd") || formName.equals("Activation")|| formName.equals("SU00_NU00")
				|| formName.equals("APK1") || formName.equals("QT00")|| formName.equals("QN00")|| formName.equals("QM00")|| formName.equals("AP1U")|| formName.equals("APK2") ||formName.equals("APK2FINAL") || formName.equals("APK5") 
				|| formName.equals("W400") || formName.equals("PM00") || formName.equals("QI00")
				|| formName.equals("vpBill") || formName.equals("AccDisplay") || formName.equals("R600") || formName.equals("OTP")//mobile recharge
				|| formName.equals("status") || formName.equals("restart") || formName.equals("alert")||formName.equals("HTTPERROR")|| formName.equals("FCOMP")
				|| formName.equals("APDX")|| formName.equals("APDY")||formName.equals("AP1B")||formName.equals("AP2B") ||formName.equals("APN4")|| formName.equals("APDT")||formName.equals("APHC")
				||formName.equals("APHC")||formName.equals("3T00")||formName.equals("4T00")||formName.equals("7T00") ||formName.equals("Version Update")
				|| formName.equals("CH00") || formName.equals("TP00") || formName.equals("APCH")|| formName.equals("APCT") || formName.equals("APTC")|| formName.equals("APTP")
				|| formName.equals("WAP_ZP")|| formName.equals("APQM")|| formName.equals("APPI")|| formName.equals("APPM")|| formName.equals("AP5U")
				) {

			if (formName.equals("N100")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_paynow,"Pay Now"));
				tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_defer,"Defer"));
				// menu.add(menuGroupIdForYes, 0, 0, "Paynow");
				// menu.add(menuGroupIdForNo, 1, 0, "Defer");
			} else if (formName.equals("BillInfo")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				 tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
			} else if (formName.equals("Temple")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				 tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
				// ConfirmYesCommand = new Command("Confirm", Command.OK, 1);
			} else if (formName.equals("Z600")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_update,"Update"));
				// menu.add(menuGroupIdForYes, 0, 0, "Update");
			} else if (formName.equals("Campaign")) {
				 tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
	        	  tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
	        	  tabBarSetter.put(menuGroupIdForDefer,new Image(R.drawable.icon_defer,"Defer"));
			} else if (formName.equals("ML00N")) {//mobile recharge
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_ok,"OK"));
			}else if (formName.equals("R600")) {//mobile recharge
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
	        	  tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			}
			else if (formName.equals("3S00")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
	        	  tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			} else if (formName.equals("BW00")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				 tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			} else if (formName.equals("ICash") || formName.equals("SC00")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
				tabBarSetter.put(menuGroupIdForYes, new Image(R.drawable.icon_confirm,"Confirm"));
				 tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			} else if (formName.equals("CN00")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
				// menu.add(menuGroupIdForNo, 1, 0, "Cancel");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			} else if (formName.equals("Activation")) {
				// menu.add(menuGroupIdForYes, 0, 0, "OK");
				tabBarSetter.put(menuGroupIdForYes, new Image(R.drawable.icon_ok,"OK"));
			} else if (formName.equals("SU00_NU00")) {
				tabBarSetter.put(menuGroupIdForYes, new Image(R.drawable.icon_proceed,"Proceed"));
			}  else if ((formName.equals("Version Update")&& StaticStore.versionUpdateURL.toLowerCase().startsWith("http"))) {
				// menu.add(menuGroupIdForYes, 0, 0, "OK");
				tabBarSetter.put(menuGroupIdForYes, new Image(R.drawable.icon_ok,"Upgrade"));
			}else if (formName.equals("status") || formName.equals("7T00")) {
				if (StaticStore.midlet.moreFlag) {
					tabBarSetter.put(menuGroupIdForMore, new Image(R.drawable.icon_more,"More"));
				} else {
					StaticStore.recCount = 0;
				}
			} else if (formName.equals("vpBill")) {
				if (StaticStore.midlet.moreFlag) {
					// menu.add(menuGroupIdForMore, 0, 0, "more");
					tabBarSetter.put(menuGroupIdForMore,  new Image(R.drawable.icon_more,"More"));
				} else {
					StaticStore.recCount = 0;
				}
			} else if (formName.equals("AccDisplay")) {
         StaticStore.LogPrinter('i',">>>>><<<<" + StaticStore.isUpdateClicked);
				if (!StaticStore.isUpdateClicked) {
					tabBarSetter.put(menuGroupIdForUpdate,new Image(R.drawable.icon_update,"Update"));
				}
				if (StaticStore.midlet.moreFlag) {
					// menu.add(menuGroupIdForMore, 0, 0, "more");
					tabBarSetter.put(menuGroupIdForMore,  new Image(R.drawable.icon_more,"More"));
				}

			}else if (formName.equals("OTP")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Proceed");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_proceed,"Proceed"));

			} else if (formName.equals("CH00") || formName.equals("TP00")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			
			}else if (formName.equals("APCH")||formName.equals("APCT")|| formName.equals("APTP")) {
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
				tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));
			
			} else if (formName.equals("restart") || formName.equals("alert")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Proceed");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_proceed,"Proceed"));

			}  else if (formName.equals("HTTPERROR")) {
				// menu.add(menuGroupIdForYes, 0, 0, "Proceed");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_proceed,"Confirm"));

			}else {
				// menu.add(menuGroupIdForYes, 0, 0, "Confirm");
				// menu.add(menuGroupIdForNo, 1, 0, "Cancel");
				tabBarSetter.put(menuGroupIdForYes,new Image(R.drawable.icon_confirm,"Confirm"));
	        	  tabBarSetter.put(menuGroupIdForNo,new Image(R.drawable.icon_no,"Cancel"));

			}
			// menu.add(5, 1, 0, "Exit");
			

		}
		
		// exit
		tabBarSetter.put(7,new Image(R.drawable.icon_exit,(StaticStore.enableHome?"Logout":"Exit")));
		return tabBarSetter;
	}

	public String[] getFormattedArray(String messageContent) {
		try{
		this.messageContent = messageContent;

		if (formName.equals(StaticStore.BE)) {
			int balCount = 1;
			// BE00;+ 1000.00; + 1000.00; - 1000.00; 11/01/09
			// 08:15;TXNID:123455;BID:C403362
			try {
				String[] dummyFormattedArray = new String[6];

				dummyFormattedArray[0] = messageContent.substring(0,
						messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent
						.indexOf(";") + 1);
				dummyFormattedArray[1] = messageContent.substring(0,
						messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent
						.indexOf(";") + 1);
				dummyFormattedArray[2] = messageContent.substring(0,
						messageContent.indexOf(";"));
				StaticStore.LogPrinter('i',"dummyFormattedArray"+Arrays.deepToString(dummyFormattedArray));
				StaticStore.LogPrinter('i',messageContent);
				messageContent = messageContent.substring(messageContent
						.indexOf(";") + 1);
				dummyFormattedArray[3] = messageContent.substring(0,
						messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent
						.indexOf(":") + 1);
				dummyFormattedArray[4] = messageContent.substring(
						messageContent.indexOf(":") + 1, messageContent
								.indexOf("^"));
				dummyFormattedArray[5] = messageContent
						.substring(messageContent.lastIndexOf('^') + 1);
				StaticStore.LogPrinter('i',"dummyFormattedArray"+Arrays.deepToString(dummyFormattedArray));
				getBalanceTypes(dummyFormattedArray);
				successRespMsgs[63] = new String[balArrLength + 3];
				FormattedArray = new String[balArrLength + 3];
				String[] balNames = new String[3];
				balNames[0] = "Current Balance";
				balNames[1] = "Available Balance";
				balNames[2] = "Combined Balance";
				for (int i = 0; i < balanceTypeFlag.length; i++) {
					if (balanceTypeFlag[i] == true) {
						successRespMsgs[63][balCount] = balNames[i];
						FormattedArray[balCount] = rupees
								+ dummyFormattedArray[i];
						balCount++;
					} else {
						continue;
					}
				}
				
				FormattedArray[0] = StaticStore.midlet
						.maskedAccNumberBE(dummyFormattedArray[5]);
				FormattedArray[balCount] = dummyFormattedArray[3];
				FormattedArray[balCount + 1] = dummyFormattedArray[4];
				successRespMsgs[63][0] = "A/C No.";
				successRespMsgs[63][balCount] = "Date & Time";
				successRespMsgs[63][balCount + 1] = "Transaction ID";
				StaticStore.LogPrinter('i',"Bal >>>"+Arrays.deepToString(FormattedArray));
				StaticStore.LogPrinter('i',"Bal >>>"+Arrays.deepToString(successRespMsgs[63]));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if (formName.equals(StaticStore.MS)) {

		
		} else if (formName.startsWith("Deb")) {


			// FT00 Fund Transfer to Neville - +919362662693 Successfull Rs.60
			// debited from your account date 2006-12-02 15:12 TXNID : 123456789
			// FT00 FUND TRANSFER OF RS 150.00 TO Malli -+919362662693
			// SUCCESSFULL ON 2008-07-03 15:25 TXNID:123456789112
			// FT00;Fund Transfer to TESTOBC5 - +919790951696 successful.Rs.5.00
			// debited from your account;17/02/2011
			// 23:26;TXNID:110217039757;BID:C504594
			FormattedArray = new String[5];
			FormattedArray[0] = messageContent.substring(
					messageContent.toUpperCase().indexOf("RS"),
					messageContent.toUpperCase().indexOf("TO")).trim();
			FormattedArray[1] = messageContent.substring(
					messageContent.toUpperCase().indexOf("TO") + 2,
					messageContent.toUpperCase().indexOf("-")).trim();
			FormattedArray[2] = messageContent.substring(
					messageContent.toUpperCase().indexOf("-") + 1,
					messageContent.toUpperCase().indexOf("SUC")).trim();
			FormattedArray[3] = messageContent.substring(
					messageContent.toUpperCase().indexOf("ON") + 2,
					messageContent.toUpperCase().indexOf("TXN")).trim();
			FormattedArray[4] = messageContent.substring(
					messageContent.toUpperCase().lastIndexOf(':') + 1).trim();
		} else if (formName.startsWith("Cred")) {

			// FT11 Fund Transfer from Neville - +919362662693 Successfull Rs.60
			// credited to your account date 2006-12-02 15:12 TXNID : 123456789
			// FT11 FUND TRANSFER OF RS 150.00 FROM Malli -+919362662693
			// RECEIVED ON 2008-07-03 15:25 TXNID:123456789112
			FormattedArray = new String[5];
			FormattedArray[0] = messageContent.substring(
					messageContent.toUpperCase().indexOf("RS"),
					messageContent.toUpperCase().indexOf("FROM")).trim();
			FormattedArray[1] = messageContent.substring(
					messageContent.toUpperCase().indexOf("FROM") + 4,
					messageContent.toUpperCase().indexOf("-")).trim();
			FormattedArray[2] = messageContent.substring(
					messageContent.toUpperCase().indexOf("-") + 1,
					messageContent.toUpperCase().indexOf("REC")).trim();
			FormattedArray[3] = messageContent.substring(
					messageContent.toUpperCase().indexOf("ON") + 2,
					messageContent.toUpperCase().indexOf("TXN")).trim();
			FormattedArray[4] = messageContent.substring(
					messageContent.toUpperCase().lastIndexOf(':') + 1).trim();
		} else if (formName.equals("status")) {
			// RS00;13/05/09 * 8778 * 100.0 * S #13/05/09 * 8778 * 100.0 * F
			// ;:110217039502;BID:C212121
			// RS00;123456;13/05/09 * 8778 * 100.0 * S #13/05/09 * 8778 * 100.0
			// * F ; Y; TXNID:110217039502;BID:C504594
			String temp = messageContent.substring(5);
			tempMobileNo = temp.substring(0, temp.indexOf(";"));
			temp = temp.substring(temp.indexOf(";") + 1);
			messageContent = messageContent.substring(5);
			byte dataNumbers = (byte) (getTokenCount(temp, '#') + 1);
			String[] dummyFormattedArray = new String[dataNumbers];
			StaticStore.midlet.moreFlag = temp.substring(
					temp.lastIndexOf(';') - 1).substring(0, 1).trim().equals(
					"Y");
			StaticStore.recCount += dataNumbers;
			StaticStore.midlet.nextStartRecNumber = ""
					+ (StaticStore.recCount + 1);
			String data = temp.substring(0, temp.indexOf(";")) + "#";
			for (int i = 0; i < dataNumbers; i++) {
				dummyFormattedArray[i] = data.substring(0, data.indexOf("#"));
				data = data.substring(data.indexOf("#") + 1);
			}
			FormattedArray = new String[dummyFormattedArray.length + 1];
			successRespMsgs[66] = new String[dummyFormattedArray.length + 1];
			String msg = null;
			for (int i = 0; i < dummyFormattedArray.length; i++) {

				successRespMsgs[66][i] = dummyFormattedArray[i].substring(0,
						dummyFormattedArray[i].indexOf('*'));

				msg = dummyFormattedArray[i].substring(dummyFormattedArray[i]
						.indexOf('*') + 1);
				msg = msg.replace('*', '-');
				if (msg.substring(msg.lastIndexOf('-') + 1).trim().equals("S")) {
					msg = "XX" + msg.substring(0, msg.lastIndexOf('-') + 1)
							+ " SUCCESS";
				} else if (msg.substring(msg.lastIndexOf('-') + 1).trim()
						.equals("F")) {
					msg = "XX" + msg.substring(0, msg.lastIndexOf('-') + 1)
							+ " FAILURE";
				}
				FormattedArray[i] = msg;
			}
			FormattedArray[FormattedArray.length - 1] = messageContent
					.substring(messageContent.lastIndexOf(':') + 1);
			successRespMsgs[66][FormattedArray.length - 1] = "Transaction ID";

		} else if (formName.equals("vpBill")) {
			// PB00;Ram*23/10/2011*100#Ram*24/10/2011*200#Ram*23/10/2011*300;TXNID:110308049952;BID:C504432
			// PB00;Ram*23/10/2011*100#Ram*24/10/2011*200#Ram*23/10/2011*300; Y;
			// TXNID:110308049952;BID:C504594
			try {
				byte dataNumbers = (byte) (getTokenCount(messageContent, '#') + 1);
				StaticStore.midlet.moreFlag = messageContent.substring(
						messageContent.lastIndexOf(';') - 1).substring(0, 1)
						.trim().equals("Y");
				String[] dummyFormattedArray = new String[dataNumbers];
				FormattedArray = new String[dataNumbers + 1];
				successRespMsgs[67] = new String[dataNumbers + 1];
				StaticStore.recCount += dataNumbers;
				StaticStore.midlet.nextStartRecNumber = ""
						+ (StaticStore.recCount + 1);
				String data = messageContent.substring(0, messageContent
						.indexOf(";"))
						+ "#";
				messageContent = messageContent.substring(messageContent
						.indexOf(";") + 1);
				for (int i = 0; i < dataNumbers; i++) {
					dummyFormattedArray[i] = data.substring(0, data
							.indexOf("#"));
					data = data.substring(data.indexOf("#") + 1);
				}

				for (int i = 0; i < dummyFormattedArray.length; i++) {
					FormattedArray[i] = dummyFormattedArray[i].substring(
							dummyFormattedArray[i].indexOf("*") + 1,
							dummyFormattedArray[i].lastIndexOf('*'));

					successRespMsgs[67][i] = dummyFormattedArray[i].substring(
							0, dummyFormattedArray[i].indexOf("*"))
							+ "-"
							+ dummyFormattedArray[i]
									.substring(dummyFormattedArray[i]
											.lastIndexOf('*') + 1);

				}
				successRespMsgs[67][FormattedArray.length - 1] = "Transaction ID";
				FormattedArray[FormattedArray.length - 1] = messageContent
						.substring(messageContent.lastIndexOf(':') + 1);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (formName.equals("branch") || formName.equals("ATM")) {

			byte dataNumbers = (byte) (getTokenCount(messageContent, ';') + 1);
			FormattedArray = new String[dataNumbers];
			messageContent = messageContent + ";";
			for (int i = 0; i < dataNumbers; i++) {
				FormattedArray[i] = messageContent.substring(0,
						messageContent.indexOf(";")).trim();
				messageContent = messageContent.substring(messageContent
						.indexOf(";") + 1);
			}

			if (formName.equals("branch")) {

				int stringPos = 1;
				int addAfterPos = 1;

				for (int i = 0, lineLength = 0; i < FormattedArray.length; i++) {

					// multiLineValues =
					// setLineArray(getLineCount(FormattedArray[1]),FormattedArray[1]);

					// ************Modified ***********//

					// multiLineValues =
					// setLineArrayForLargeScreen(getLineCountForLargeScreen(FormattedArray[1]),
					// FormattedArray[1]);

					StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>1" + FormattedArray[1]);
					if (i == 2) {
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>2"+ successRespMsgs[40][2]);
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ((i * 3) *
						// bitMapFont.getCharHeight()) + scrollYPos+(lineLength
						// * BigFont.heightPx),successRespMsgs[40][2]);
					} else {
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>3"+ successRespMsgs[40][i]);
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ((i * 3) *
						// bitMapFont.getCharHeight()) + scrollYPos+(lineLength
						// * BigFont.heightPx),successRespMsgs[40][i]);
					}

					if (i == FormattedArray.length - 2) {
						String temp = FormattedArray[i];
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>4"+ temp.substring(0, temp.indexOf("*")));
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ( stringPos*
						// bitMapFont.getCharHeight()+1)+
						// scrollYPos+addAfterPos,);
						temp = temp.substring(temp.indexOf("*") + 1);
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>5"+ temp.substring(0, temp.indexOf("*")));
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ( stringPos*
						// (bitMapFont.getCharHeight())+1)+
						// scrollYPos+addAfterPos+bitMapFont.getCharHeight(),);
						temp = temp.substring(temp.indexOf("*") + 1);
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>6" + temp);
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ( stringPos*
						// (bitMapFont.getCharHeight())+1)+
						// scrollYPos+addAfterPos+(2*bitMapFont.getCharHeight()),temp);

					} else if (i == 1) {
						String temp = FormattedArray[i];
						// multiLineValues =
						// setLineArray(getLineCount(FormattedArray[i]),FormattedArray[i]);

						// ************Modified ***********//
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>6"+ FormattedArray[i]);
					} else {
						StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>7"+ FormattedArray[i]);
						// bitMapFont.drawStringCenter(g,StaticStore.DisplayWidth
						// / 2,adjustPosition + ( stringPos*
						// bitMapFont.getCharHeight()+1)+
						// scrollYPos+addAfterPos,FormattedArray[i]);
					}
					stringPos += 3;
				}

			}
  
		} else if (formName.equals("AccDisplay")) {
			// AF00;0001;1;0018279280001;N;TXNID:110325003433;BID:C504594
			try {
				byte AccNos = (byte) (getTokenCount(messageContent, ';'));
				FormattedArray = new String[AccNos];
				successRespMsgs[65] = new String[AccNos];
				String acc_no = "";
				for (int i = 0; i < AccNos; i++) {
					acc_no = messageContent.substring(0, messageContent
							.indexOf(";"));
					successRespMsgs[65][i] = StaticStore.midlet
							.maskedAccNumber(acc_no);
					messageContent = messageContent.substring(messageContent
							.indexOf(";") + 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < successRespMsgs[65].length; i++) {
				FormattedArray[i] = getDescription(StaticStore.accType[i]);
			}
			// for (int i = 0; i < FormattedArray.length; i++) {
			// StaticStore.LogPrinter('i',"::::::::::::::::FORMAT"+FormattedArray[i]);
			// StaticStore.LogPrinter('i',"::::::::::::::::successRespMsgs"+successRespMsgs[65][i]);
			// }
		} else if (formName.equals("AB00") || formName.equals("BR00")||  formName.equals("M900")
				|| formName.equals("LB00") || formName.equals("BD00")||  formName.equals("6U00")
				|| formName.equals("TD00") || formName.equals("M300")
				|| formName.equals("MC00") || formName.equals("MESSAGE")
				|| formName.equals("IR00") || formName.equals("M400")
				|| formName.equals("AR00") || formName.equals("R100")
				|| formName.equals("R200") || formName.equals("Q200")
				|| formName.equals("N100") || formName.equals("SRXX")
				|| formName.equals("SRYY") || formName.equals("PSZZ")
				|| formName.equals("PSYY") || formName.equals("PSXX")
				|| formName.equals("PSWW") || formName.equals("SIZZ")
				|| formName.equals("SIYY") || formName.equals("DRZZ")
				|| formName.equals("DRYY") || formName.equals("CFZZ")
				|| formName.equals("CFYY") || formName.equals("RM00")
				|| formName.equals("RA00") || formName.equals("BillInfo") || formName.equals("Offers")
				|| formName.equals("Temple") || formName.equals("2U00")|| formName.equals("Z600")
				|| formName.equals("Partner") || formName.equals("Campaign")
				|| formName.equals("AC00") || formName.equals("Adhocpay")
				|| formName.equals("Billerlink") || formName.equals("A100")
				|| formName.equals("CW00") || formName.equals("I100")
				|| formName.equals("I4NP") || formName.equals("I4PP")
				|| formName.equals("ML00N") || formName.equals("3S00")
				|| formName.equals("SAXX") || formName.equals("SAYY")
				|| formName.equals("BSXX") || formName.equals("BSYY")
				|| formName.equals("ESXX") || formName.equals("ESYY")
				|| formName.equals("BW00") || formName.equals("4S00")
				|| formName.equals("ForgotPWD") || formName.equals("About")
				|| formName.equals("GTEST") || formName.equals("FT00") || formName.equals("APDOConfirm") || formName.equals("APST") || formName.equals("APN2") || formName.equals("APN4")
				|| formName.equals("MBEN") || formName.equals("ABEN")
				|| formName.equals("NBEN") || formName.equals("ICash")
				|| formName.equals("SC00") || formName.equals("ECXX")|| formName.equals("LR00") || formName.equals("DZ00") || formName.equals("N500")
				|| formName.equals("W100") || formName.equals("1I00")|| formName.equals("QIMPS")||
				formName.equals("APK1")|| formName.equals("AP1U")|| formName.equals("QT00")|| formName.equals("QN00")
				|| formName.equals("QM00") || formName.equals("APK2")|| formName.equals("APK5") //IMPS vinoth
				|| formName.equals("APK2FINAL")|| formName.equals("4W00")|| formName.equals("APK9Stat")
				|| formName.equals("W400") || formName.equals("PM00") || formName.equals("QI00")
				|| formName.equals("QIMPS REG") || formName.equals("REGIMPS") || formName.equals("LI00") || formName.equals("LO00")
				|| formName.equals("GTESTCNF") || formName.equals("HELP") || formName.equals("TH00") || formName.equals("TH001") || formName.equals("TH002") || formName.equals("TH003")
				|| formName.equals("SMSCOMP") || formName.equals("fd")
				|| formName.equals("Activation")|| formName.equals("SU00_NU00")  || formName.equals("D900") || formName.equals("Version Update")
				|| formName.equals("NL00") || formName.equals("V600") || formName.equals("R600")  //mobile recharge//Recharge
				|| formName.equals("restart") || formName.equals("alert")||formName.equals("HTTPERROR")|| formName.equals("FCOMP")
				|| formName.equals("CH00") || formName.equals("TP00") ||formName.equals("APCH")|| formName.equals("Error_in_resp")||formName.equals("APTP")||formName.equals("WAP_ZP")|| formName.equals("R600")|| formName.equals("CT00") || formName.equals("TC00")
				|| formName.equals("APDX")|| formName.equals("APDY")||formName.equals("AP1B")||formName.equals("AP2B") ||formName.equals("APN4")||formName.equals("3T00")||formName.equals("4T00") ||formName.equals("7T00")|| formName.equals("APDT")
				|| formName.equals("APHC")|| formName.equals("OTP")|| formName.equals("APQM")|| formName.equals("APPI")|| formName.equals("APPM") || formName.equals("AP5U")
				|| formName.equals("AP4W")|| formName.equals("APDO") || formName.equals("APDB") || formName.equals("APLE")) {


			if (formName.equals("M300") || formName.equals("Q200")) {
				messageContent = messageContent.replace('*', ';');

			}
			try {
				if (messageContent.toUpperCase().indexOf("TXNID:") != -1) {
					TXNID = messageContent.substring(messageContent
							.toUpperCase().indexOf("TXNID:") + 6);
				} else {
					TXNID = messageContent.substring(messageContent
							.lastIndexOf(':'));
				}
			} catch (Exception e) {
			}

			StaticStore.LogPrinter('i',">>>>>>>" + messageContent);
			ctr = StaticStore.midlet.getCharCount(messageContent, ';');
			/*
			 * The Following Ternary operator condition is used to check whether
			 * the registration or other than that. if other than that means
			 * subcorporate flag will come.This flag we wont show to the user.So
			 * the counter is should be the equal to the seperator.
			 */
			/*
			 * Following variable is used in other than registraion module if it
			 * is true means it entered in subcorporate flag block
			 */

			boolean isEntered = false;
			FormattedArray = new String[ctr + 1];

			for (int i = 0; i < FormattedArray.length - 1; i++) {
				FormattedArray[i] = messageContent.substring(0,
						messageContent.indexOf(';')).trim();
				messageContent = messageContent.substring(
						messageContent.indexOf(';') + 1).trim();
			}

			FormattedArray[FormattedArray.length - 1] = messageContent
					.substring(0).trim();

			try {
				if (FormattedArray[FormattedArray.length - 1].toUpperCase()
						.indexOf("TXNID:") != -1) {
					FormattedArray[FormattedArray.length - 1] = FormattedArray[FormattedArray.length - 1]
							.substring(FormattedArray[FormattedArray.length - 1]
									.toUpperCase().indexOf("TXNID:") + 6);
				}
			} catch (Exception e) {
			}

			if (formName.equals("Q200")) {
				formattedData = FormattedArray[3].trim();

				//				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
	           
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,FormattedArray[3].trim());
//	          Account Type Display Code END
	           FormattedArray[3]  = acctypename;
				
			}
			if (formName.equals("RA00")) {
				formattedData = FormattedArray[2].trim();
			
//				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,FormattedArray[2].trim());
//	          Account Type Display Code END
	           FormattedArray[2]  = acctypename;
//				FormattedArray[2] = FormattedArray[2].trim().equals("10") ? "Savings"
//						: "Current";
			}
			if (formName.equals("2U00")) {
				formattedData = FormattedArray[1].trim();
			
//				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,FormattedArray[1].trim());
//	          Account Type Display Code END
	           FormattedArray[1]  = acctypename;
//				FormattedArray[2] = FormattedArray[2].trim().equals("10") ? "Savings"
//						: "Current";
			}

			if (formName.equals("M300")) {
				FormattedArray[1] = "Rs." + FormattedArray[1];
			}

			if (formName.equals("A100")) {
				FormattedArray[10] = FormattedArray[10].substring(0, 2) + ":"
						+ FormattedArray[10].substring(2);
			}
		} else if (formName.equals("Airline")) {
			FormattedArray = new String[4];
			FormattedArray[0] = messageContent.substring(0, messageContent
					.indexOf(";"));
			messageContent = messageContent.substring(messageContent
					.indexOf(";") + 1);
			FormattedArray[1] = messageContent.substring(0, messageContent
					.indexOf(";"));
			messageContent = messageContent.substring(messageContent
					.indexOf(";") + 1);
			messageContent = messageContent.substring(messageContent
					.indexOf(";") + 1);
			FormattedArray[2] = messageContent.substring(0, messageContent
					.indexOf(";"));
			messageContent = messageContent.substring(messageContent
					.indexOf(";") + 1);
			FormattedArray[3] = messageContent.substring(0).trim();
		}
		else if(formName.equals("DEFAULT") || formName.equals("CN00")||formName.equals("CM11")) {
        	FormattedArray	=	getDefaultFormatArray();
        } 
        
        if(FormattedArray[FormattedArray.length - 1].startsWith("TXN")){
        	FormattedArray[FormattedArray.length - 1] = FormattedArray[FormattedArray.length - 1].substring(6, FormattedArray[FormattedArray.length - 1].length());
    	}
        
        return FormattedArray;
    	}catch(Exception e){
    		formName	=	"DEFAULT";
    		FormattedArray	=	getDefaultFormatArray();
    		if(FormattedArray[FormattedArray.length - 1].startsWith("TXN")){
	        	FormattedArray[FormattedArray.length - 1] = FormattedArray[FormattedArray.length - 1].substring(6, FormattedArray[FormattedArray.length - 1].length());
	    	}
    		return FormattedArray;
    	}
    	

    }

	    private String[] getDefaultFormatArray() {
	    	int pritTxnId       =   0;
            int pritDate        =   0;
            
            if(formName.equals("CN00")){               
                TXNID = messageContent.substring(messageContent.indexOf("TXNID:") + 6);
            }
            //  B600;SUCCESSFUL;11/01/09 08:15;TXNID:124343535634
            
            try {                
                int tempMess     =   messageContent.lastIndexOf(';');                
                String tempMsg   =   messageContent.substring(0, tempMess);
                pritDate         =   tempMsg.lastIndexOf(';');
                pritTxnId        =   messageContent.indexOf("TXN");                
                
                if(pritTxnId != -1 && pritDate != -1) {
                	FormattedArray    	=   new String[3];
                	FormattedArray[0]	=	messageContent.substring(0, pritDate);
                    pritTxnId         	=   messageContent.lastIndexOf(':');
                } else {
                	FormattedArray    	=   new String[2];
                	FormattedArray[0]	=	messageContent.substring(0, pritDate);
                }

                if(pritTxnId != -1 && pritDate != -1) {
                	FormattedArray[FormattedArray.length - 1]   =   messageContent.substring(pritTxnId + 1).trim();
                    pritTxnId                                   =   messageContent.lastIndexOf(';');
                    FormattedArray[FormattedArray.length - 2]   =   messageContent.substring(pritDate + 1, pritTxnId).trim();
                } else {
                	FormattedArray[FormattedArray.length - 2]   =   "";
                	FormattedArray[FormattedArray.length - 1]   =   "";
                }
            } catch(StringIndexOutOfBoundsException e) {                
            	FormattedArray = new String[3];
            	FormattedArray[0] = messageContent;
            	FormattedArray[1] = "";
            	FormattedArray[2] = "";
            } catch(Exception e) {                      
            	FormattedArray = new String[3];
            	FormattedArray[0] = messageContent;
            	FormattedArray[1] = "";
            	FormattedArray[2] = "";
            }
            
            return FormattedArray;
	    }
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

			if (item.getGroupId() == 7) {
			exitMIDlet(new AlertDialog.Builder(getActivity()), "Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?",
					100, getActivity()).show();
		}

		return super.onOptionsItemSelected(item);
	}

	private OnClickListener tabBarListener = new OnClickListener() {
		public void onClick(View v) {
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(StaticStore.context);
			if(sessionflag){
				if (v.getId() == menuGroupIdForUpdate) {
					tabbarmenuGroupIdForUpdate();
				} else if (v.getId() == menuGroupIdForYes) {
					if(!formName.equals("Version Update")){
					v.setEnabled(false);
					}
					tabbarmenuGroupIdForYes();
				} else if (v.getId() == menuGroupIdForNo) {
					tabbarmenuGroupIdForNo();
				} else if (v.getId() == menuGroupIdForDefer) {
					tabbarmenuGroupIdForDefer();
				} else if (v.getId() == menuGroupIdForMore) {
					tabbarmenuGroupIdForMore();
				} else if (v.getId() == 6) {
				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
							
				} else if (v.getId() == 7) {
					exit();
				} else if (v.getId() == 0) {
				StaticStore.midlet.startFragment(StaticStore.context, StaticStore.midlet.getHome(StaticStore.context));
				}

			}
		}
	};

	private void exit() {
		// TODO Auto-generated method stub
		exitMIDlet(new AlertDialog.Builder(getActivity()), "Do you want to "+(StaticStore.enableHome?"logout":"exit")+"?", 100,
				getActivity()).show();
	}

	protected void tabbarmenuGroupIdForDefer() {
		// TODO Auto-generated method stub
		StaticStore.menuDesc[131][3] = StaticStore.midlet.campaignId;
		StaticStore.menuDesc[131][5] = TXNID;
		StaticStore.index = 131;
		StaticStore.FromListScreen = false;
		StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
				DynamicCanvas.class));
	}

	protected void tabbarmenuGroupIdForNo() {
		// TODO Auto-generated method stub
		if (formName.equals("AB00")) {
			StaticStore.menuDesc[8][3] = "N";
			StaticStore.menuDesc[8][4] = TXNID;
			StaticStore.index = 8;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else if( formName.equals("FCOMP")){
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
						.getICashForgetPin(getActivity()));
			}else if (formName.equals("restart")||formName.equals("alert")||formName.equals("HTTPERROR")) {
			exit();
		}
			else if (formName.equals("N100")) {
			StaticStore.menuDesc[121][3] = StaticStore.midlet.billpayNotifyDet[1][0];
			StaticStore.menuDesc[121][4] = StaticStore.midlet.billpayNotifyDet[5][0];
			StaticStore.menuDesc[121][5] = StaticStore.midlet.billpayNotifyDet[11][0];
			StaticStore.menuDesc[121][6] = StaticStore.midlet.billpayNotifyDet[2][0];
			StaticStore.index = 121;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else if (formName.equals("CN00")) {
			StaticStore.menuDesc[77][3] = "N";
			StaticStore.menuDesc[77][4] = TXNID;
			StaticStore.index = 77;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		}else if(formName.equals("RA00")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getFTRegistrationMenuM2A("Mobile-to-Account",getActivity()));
		}else if (formName.equals("RM00")) {
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getFTRegistrationMenu("Mobile-to-Mobile",getActivity()));
		} else if (formName.equals("Partner")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
					.getPayRegList(getActivity()));
		} else if (formName.equals("Campaign")) {
			StaticStore.menuDesc[130][1] = "AP2Y;Y";
			StaticStore.menuDesc[130][3] = StaticStore.midlet.campaignId;
			StaticStore.menuDesc[130][4] = TXNID;
			StaticStore.index = 130;
			StaticStore.midlet.startFragment(getActivity(),new Intent(getActivity(),
					DynamicCanvas.class));
		} else if (formName.equals("PSZZ") || formName.equals("PSYY")
				|| formName.equals("PSXX") || formName.equals("PSWW")
				|| formName.equals("SIZZ") || formName.equals("SIYY")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
					.getCFTMenu(getActivity()));
		}else if (formName.equals("A100")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
					.getAirlineMenu(getActivity()));
		} else if (formName.equals("I100") || formName.equals("I4PP")
				|| formName.equals("I4NP")) {
			String[] menuItem = { "Registration", "Payment",
					"Deregistration" };

			Intent mPayIntent = new Intent(getActivity(),
					ListSelection.class);
			mPayIntent.putExtra("listIndex", 54);
			mPayIntent.putExtra("menuItem", menuItem);
			mPayIntent.putExtra("listHeader", "Fees Payment");
			mPayIntent.putExtra("more", false);
			StaticStore.midlet.startFragment(getActivity(),mPayIntent);

		} else if (formName.equals("FT00")) {
			if (StaticStore.index == 101) {
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
						.withInMobReg(getActivity()));
			} else if (StaticStore.index == 103) {
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
						.withInAccReg(getActivity()));
			} else {
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
						.getNeftRegList(getActivity()));
			}
		} else if (formName.equals("APDOConfirm")) {
			StaticStore.forAPDOBack = false;
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getDeposits(getActivity()));
		}else if (formName.equals("APST")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getPayRegList(getActivity()));
		}else if (formName.equals("APN2") || formName.equals("APN4")){
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.getInstMenu(getActivity()));
		} else if (formName.equals("QIMPS")
				|| formName.equals("QIMPS REG")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
					.getPayRegList(getActivity()));
		}else if(formName.equals("QI00")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getIMPSOptionMenu(getActivity()));

		} else if(formName.equals("APK1")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getIMPSOptionMenu(getActivity()));
		} else if(formName.equals("AP1U")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getP2UMenus(getActivity()));
		} else if(formName.equals("2U00")){
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getP2UMenus(getActivity()));
		}else if(formName.equals("APK2")|| formName.equals("APK2FINAL") || formName.equals("APK5")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getRegisteredIMPSAccOptions(getActivity()));

		}else if(formName.equals("W400")){
			StaticStore.indexCtr--;
			StaticStore.midlet.startFragment(getActivity(), StaticStore.midlet.getRegisteredIMPSOptions(getActivity()));
		}else if (formName.equals("GTESTCNF")) {
			if (StaticStore.isCommModeSelected) {
				StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
						.get_Settings(getActivity()));
			} else {
				exit();
			}
		} else if (formName.equals("fd")) {
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet
					.get_InterestInstructions(getActivity()));
		}else if(formName.equals("R600")){//mobile recharge
        	StaticStore.isBack = true;
			StaticStore.indexCtr -= 1;
			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
			Intent myIntent = StaticStore.midlet.getList(getActivity(),indexForBack,indexForSelectedBack);
//			StaticStore.midlet.startFragment(getActivity(),myIntent);
			StaticStore.midlet.startFragment(getActivity(),myIntent);
			
		}else{               
			StaticStore.isBack = true;
			StaticStore.indexCtr -= 1;
			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
			Intent myIntent = StaticStore.midlet.getList(getActivity(),indexForBack,indexForSelectedBack);
			StaticStore.midlet.startFragment(getActivity(),myIntent);
		}
	
	}

	protected void tabbarmenuGroupIdForUpdate() {
		// TODO Auto-generated method stub
if (formName.equals("AccDisplay")) {
	StaticStore.isAccSyncFromMySetup = true;
	StaticStore.isUpdateClicked = true;
	StaticStore.menuDesc[188][2] = "0001";
	StaticStore.maxAccLengthFromResponse = 0;
	StaticStore.index = 188;
	StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
			DynamicCanvas.class));
}

	}

	protected void tabbarmenuGroupIdForMore() {
		// TODO Auto-generated method stub

		StaticStore.dynamicMenuTemp = new String[StaticStore.menuDesc[StaticStore.index].length];
		StaticStore.isMoreClicked = true;
		StaticStore.tempIndex = StaticStore.index;
		for (int i = 0; i < StaticStore.dynamicMenuTemp.length; i++) {
			StaticStore.dynamicMenuTemp[i] = StaticStore.menuDesc[StaticStore.index][i];
		}
		
		if (formName.startsWith("status")) {
			StaticStore.menuDesc[195][3] = tempMobileNo;
			StaticStore.menuDesc[195][4] = StaticStore.midlet.nextStartRecNumber;
			StaticStore.menuDesc[195][5] = "";
			StaticStore.menuDesc[195][6] = "";
			StaticStore.index = 195;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}else if(formName.equals("7T00")){
			
			StaticStore.menuDesc[195][3] = StaticStore.midlet.CATID7T00;
			StaticStore.menuDesc[195][4] = StaticStore.midlet.TrnID_7T00;
			StaticStore.menuDesc[195][5] = (StaticStore.recCount+FormattedArray.length)+"";
			StaticStore.menuDesc[195][6] = "";
			StaticStore.menuDesc[195][8] = "";
			StaticStore.index = 195;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}else if (formName.startsWith("vpBill")) {
			StaticStore.menuDesc[207][3] = StaticStore.midlet.nextStartRecNumber;
			StaticStore.menuDesc[207][4] = "";
			StaticStore.index = 207;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}
	
		
		if (formName.equals("AccDisplay")) {
			StaticStore.isAccSyncFromMySetup = true;
			StaticStore.menuDesc[188][2] = ""
					+ (StaticStore.maxAccLengthFromResponse + 1);
			StaticStore.index = 188;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}
	
	}

	protected void tabbarmenuGroupIdForYes() {
		// TODO Auto-generated method stub
		if (formName.equals("M300")) {
			StaticStore.menuDesc[31][3] = StaticStore.midlet.txnID;
			StaticStore.menuDesc[31][4] = StaticStore.midlet.movieOrderDetails[0][0];
			StaticStore.menuDesc[31][5] = StaticStore.midlet.movieOrderDetails[0][1];
			StaticStore.menuDesc[31][6] = StaticStore.midlet.movieOrderDetails[0][5];
			StaticStore.index = 31;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("AR00")) {
			StaticStore.menuDesc[69][3] = StaticStore.midlet.txnID;
			StaticStore.menuDesc[69][4] = StaticStore.midlet.airlineOrderDetail[9];
			StaticStore.menuDesc[69][5] = StaticStore.midlet.airlineOrderDetail[8];
			StaticStore.index = 69;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}else if(formName.equals("OTP")){
			StaticStore.FromListScreen = false;
			StaticStore.tagType = "APOV";
			StaticStore.menuDesc[220] = new String []{"","APOV","OTP"+StaticStore.OTPRefID,"6-6-N-Y-N","1","true","true","N"};
			if(StaticStore.IsPermitted)
				StaticStore.menuDesc[220][0] = "OTP for Login";
			else
				StaticStore.menuDesc[220][0] = "OTP for Activation";
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
		}else if (formName.equals("Version Update")) {
			StaticStore.versionUpdateURL = StaticStore.versionUpdateURL.replaceAll("~", "=").trim();
			if(StaticStore.versionUpdateURL.toLowerCase().contains("id=com.fss."))
			{
				String appName = getActivity().getPackageName(); //https://play.google.com/store/apps/details?id=com.fss.
				StaticStore.LogPrinter('i',"AppName Path ==> "+appName);
			try {
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName));
				marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				startActivity(marketIntent);
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appName)));
			}
			}else{
				StaticStore.LogPrinter('i',"Came inside the else of Version Update ==> "+StaticStore.versionUpdateURL);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(StaticStore.versionUpdateURL)); //"market://details?id=com.example.android"
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent);
//			StaticStore.midlet.startFragment(StaticStore.context,intent);
			}
//			StaticStore.ToastDisplay(this,"Check mobile inbox to download new application through URL.");
		}
		else if (formName.equals("AB00")) {
			StaticStore.menuDesc[8][3] = "Y";
			StaticStore.menuDesc[8][4] = StaticStore.midlet.txnID;
			StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>StaticStore.menuDesc[8][4]"+StaticStore.menuDesc[8][4]);
			StaticStore.index = 8;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("FCOMP")) {
				StaticStore.index = 167;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
			} else if (formName.equals("Q200")) {
			StaticStore.menuDesc[83][3] = FormattedArray[2];
			StaticStore.menuDesc[83][4] = formattedData;
			StaticStore.menuDesc[83][5] = FormattedArray[4];
			StaticStore.menuDesc[83][6] = FormattedArray[5];
			StaticStore.index = 83;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("N100")) {
			StaticStore.menuDesc[120][3] = StaticStore.midlet.billpayNotifyDet[1][0];
			StaticStore.menuDesc[120][4] = StaticStore.midlet.billpayNotifyDet[5][0];
			StaticStore.menuDesc[120][5] = StaticStore.midlet.billpayNotifyDet[2][0];
			StaticStore.menuDesc[120][6] = StaticStore.midlet.billpayNotifyDet[0][0];
			StaticStore.menuDesc[120][7] = StaticStore.midlet.billpayNotifyDet[3][0];
			StaticStore.menuDesc[120][8] = StaticStore.midlet.billpayNotifyDet[4][0];
			StaticStore.menuDesc[120][9] = StaticStore.midlet.billpayNotifyDet[11][0];
			StaticStore.menuDesc[120][10] = StaticStore.midlet.billpayNotifyDet[12][0];
			StaticStore.menuDesc[120][11] = StaticStore.midlet.billpayNotifyDet[13][0];
			StaticStore.menuDesc[120][12] = "F";
			StaticStore.menuDesc[120][13] = StaticStore.midlet.billpayNotifyDet[14][0];
			StaticStore.menuDesc[120][14] = StaticStore.midlet.txnID;
			StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet
					.getNotifyPaymentOption(StaticStore.context));
		} else if (formName.equals("RM00")) {

			StaticStore.menuDesc[124][4] = FormattedArray[1];
			StaticStore.menuDesc[124][5] = FormattedArray[3];
			StaticStore.menuDesc[124][6] = StaticStore.midlet.m2mSelectedAccNumber;
			StaticStore.index = 124;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("RA00")) {

			StaticStore.menuDesc[125][4] = StaticStore.midlet.m2mSelectedAccNumber;
			StaticStore.menuDesc[125][5] =formattedData;
			StaticStore.menuDesc[125][6] = FormattedArray[3];
			StaticStore.index = 125;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("restart")) {
			 StaticStore.midlet.startFragment(StaticStore.context, StaticStore.midlet.initiateUserOption(StaticStore.context));
		}else if(formName.equals("alert") || formName.equals("HTTPERROR")){
			//Test Activation false 
			if(StaticStore.IsGPRS && !StaticStore.isOTPBuild && !StaticStore.isOTPVerified ){
				if(StaticStore.testbuild)
				{
					StaticStore.IsGPRS = true;	
				}else{
					StaticStore.IsGPRS = false;
				}
				//APMU
				StaticStore.menuDesc[113][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
				StaticStore.index = 113;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
			}else{
				if(!StaticStore.isOTPBuild)
				{
					StaticStore.IsGPRS = false;
					StaticStore.midlet.writeinMemory(StaticStore.context);
				}
				
				if(StaticStore.isFromVersionUpdate){
					StaticStore.isFromVersionUpdate = false;
					 StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
					   StaticStore.tagType = "APBV";
					   StaticStore.index = 220;
					   StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
				}else{
//				 StaticStore.midlet.MenuSelected(getActivity());
					
					if ( StaticStore.selectedLoginGridIndex == 0) {

//						StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getSignUpTypes(StaticStore.context)); // Sign Up
						StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));

						}else if ( StaticStore.selectedLoginGridIndex == 1) {
							
								if(StaticStore.IsPermitted) {
									StaticStore.index = 123;
					    			StaticStore.menuDesc[123][2] = "Login Password";
					 				StaticStore.menuDesc[123][3] = "4-4-N-Y-Y";
									StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
											DynamicCanvas.class));
								} else {
									StaticStore.menuDesc[114][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
									StaticStore.index = 114;
									StaticStore.FromListScreen = false;
									StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
								}
						}else if ( StaticStore.selectedLoginGridIndex == 2) {
							Intent myIntent = StaticStore.midlet.get_ProductsMenu(StaticStore.context);
							StaticStore.midlet.startFragment(StaticStore.context,myIntent);
//							StaticStore.ToastDisplay(StaticStore.context,"The services will be provided shortly");

						} else if ( StaticStore.selectedLoginGridIndex == 3) {
							//Refer a Friend
							StaticStore.index = 129;
							StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
										DynamicCanvas.class));
						}else if ( StaticStore.selectedLoginGridIndex == 4) {
							Intent myIntent = StaticStore.midlet.getLocatorMenu(StaticStore.context);
							StaticStore.midlet.startFragment(StaticStore.context,myIntent);
//							StaticStore.ToastDisplay(StaticStore.context,"The services will be provided shortly");
						}else if ( StaticStore.selectedLoginGridIndex == 5) {
							StaticStore.menuDesc[220] = new String []{"Feedback","AP1F;N","001","","1","false","false","N"};
							StaticStore.tagType = "AP1F";
							StaticStore.index = 220;
							
						StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
									DynamicCanvas.class));
							
						}else if(StaticStore.selectedLoginGridIndex == 6){
			   				StaticStore.isForgotPassword = true;
			   				StaticStore.NoteForgotPWD = true;
			   				//if(StaticStore.isOTPBuild && StaticStore.SMS_AUTHENTICATION_GPRSMODE)
			   				if(StaticStore.isOTPBuild)
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
			   			
		    			
						
						}
				}
			}

		}
		else if (formName.equals("BillInfo")) {
			if (StaticStore.midlet.billpayBills[0][1]
					.trim().toUpperCase().equals("Y")) {
				StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet
						.getPaymentOptions(StaticStore.context));
			} else {

				// *******************NEED TO BE
				// MODIFIED************************//
				// StaticStore.menuDesc[22][3] =
				// StaticStore.midlet.billpayBills[StaticStore.midlet.objClsA.LCBillpayBills.getSelectedIndex()][0];

				StaticStore.menuDesc[22][4] = "N";
				StaticStore.menuDesc[22][7] = StaticStore.midlet.txnID;
				StaticStore.index = 22;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
			}
		} else if (formName.equals("Temple")) {
			StaticStore.index = 97;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}else if (formName.equals("APCH")||formName.equals("APCT"))
		{
//			<Tag>;<Pin Flag>;<Encrypted mPIN>;<Card Details separated by #>;<Top-up Amount> ;<Previous Tran.ID><Bank Id>;<DUKPT>;<Version>;<Account Number - Selected Account Number>;<Time In Milli Seconds>

			StaticStore.tagType="APCT";
			StaticStore.menuDesc[220] = new String []{"Generate Card","APCT;Y",StaticStore.mPINString,StaticStore.midlet.IRCTCCardIssue[StaticStore.selectedIndex][0]+"*"+StaticStore.midlet.IRCTCCardIssue[StaticStore.selectedIndex][1],FormattedArray[1],StaticStore.midlet.txnID,
			  "","","","","4","true","true","N"};
			
			  StaticStore.LogPrinter('i',"Req Str--> "+Arrays.deepToString(StaticStore.menuDesc[220]));
			  StaticStore.LogPrinter('i',"StaticStore.midlet.IRCTCCardIssue --> "+Arrays.deepToString(StaticStore.midlet.IRCTCCardIssue));
			StaticStore.index = 220;
			 StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
 						DynamicCanvas.class));
		}else if (formName.equals("2U00")){
			StaticStore.tagType="AP3U";
			StaticStore.menuDesc[220] =new String []{"Add Beneficiary","AP3U;Y",StaticStore.mPINString,StaticStore.midlet.p2unumber,StaticStore.midlet.p2uaccntype,StaticStore.midlet.p2ubankname,
                    "","","","","4","false","false","N"};
			StaticStore.index = 220;
			 StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
 						DynamicCanvas.class));

		}else if (formName.equals("APTP"))
		{
//			<Tag>;<Pin Flag>;<Encrypted mPIN>;<Card Details separated by #>;<Top-up Amount>;<Previous Tran.ID>;<Bank Id>;<DUKPT>;<Version>;<Account Number - Selected Account Number>;<Time In Milli Seconds>
			StaticStore.tagType="APTC";
			StaticStore.menuDesc[220] = new String []{"Top-up","APTC;Y",StaticStore.mPINString,StaticStore.midlet.IRCTCCardIssue[0][0]+"*"+StaticStore.midlet.IRCTCCardIssue[0][1],FormattedArray[1],StaticStore.midlet.txnID,
					  "","","","","4","true","true","N"};
			StaticStore.LogPrinter('i',"APTC --> "+Arrays.deepToString(StaticStore.menuDesc[220]));
					StaticStore.index = 220;
					 StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
		 						DynamicCanvas.class));

			
		} else if (formName.equals("WAP_ZP"))
		{
//			finish();
//			Intent myIntent = StaticStore.midlet.get_Settings(DisplayableView.this); 
//			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
//			
			Uri uri = Uri.parse(StaticStore.wapUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			StaticStore.midlet.startFragment(StaticStore.context,new Intent(Intent.ACTION_VIEW, Uri.parse(StaticStore.wapUrl)));
			StaticStore.LogPrinter('i',"WAP URL Called ==> "+StaticStore.wapUrl);
			startActivity(intent);
			onBackPressed(StaticStore.context);
//			
		}else if (formName.equals("Z600")) {
			StaticStore.index = 128;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("Partner")
				|| formName.equals("PSZZ") || formName.equals("PSYY")
				|| formName.equals("PSXX") || formName.equals("PSWW")
				|| formName.equals("SIZZ") || formName.equals("SIYY")
				|| formName.equals("FT00") || formName.equals("ICash") || formName.equals("APDOConfirm") || formName.equals("APST")
				|| formName.equals("QI00") || formName.equals("QT00")|| formName.equals("QN00")|| formName.equals("QM00")|| formName.equals("PM00") ||formName.equals("W400")
				|| formName.equals("APK1") || formName.equals("AP1U")|| formName.equals("QN00")|| formName.equals("QM00")|| formName.equals("APK2")||  formName.equals("APK5")
				|| formName.equals("APQM") || formName.equals("APPI")|| formName.equals("APPM")|| formName.equals("AP5U")) {
			try{
	        		if (formName.equals("APK1")||formName.equals("APK2")|| formName.equals("AP1U")|| formName.equals("QT00")|| formName.equals("QN00"))
	        				{
	        			StaticStore.legValue[2]	= StaticStore.menuDesc[220][4];
	        			StaticStore.LogPrinter('i',"!!!!!!!!StaticStore.legValue[2]??"+StaticStore.legValue[2]);
	        				}
	        		StaticStore.LogPrinter('i',"????????sasasasass???StaticStore.legValue[2]??"+StaticStore.legValue[2]);
	        		StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);//comment x
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
			
		} else if(formName.equals("APK2FINAL")){
	        	StaticStore.LogPrinter('i',"APK2FINAL");
	        	StaticStore.menuDesc[220] =new String[] {};
				StaticStore.menuDesc[220] = new String []{"Add Beneficiary","APK3;Y",StaticStore.mPINString,FormattedArray[0],StaticStore.acctypeAPK3,FormattedArray[4],FormattedArray[5],"","","","","","5","false","false","N"};
				StaticStore.tagType = "APK3";
				StaticStore.index = 220;
			 StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
		} else if (formName.equals("Campaign")) {
			StaticStore.menuDesc[130][1] = "AP1Y;Y";
			StaticStore.menuDesc[130][3] = StaticStore.midlet.campaignId;
			StaticStore.menuDesc[130][4] = TXNID;
			StaticStore.index = 130;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("Adhocpay")) {
			try {
				StaticStore.midlet.assignValues(0, false);
				StaticStore.index = 24;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (formName.equals("Billerlink")) {
			StaticStore.midlet.assignValues(0, true);
			StaticStore.index = 17;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("A100")) {
			StaticStore.airlineID = FormattedArray[7];// airline ID
			StaticStore.airValues[0] = "";
			StaticStore.airValues[1] = "";
			StaticStore.airValues[2] = "";
			StaticStore.airValues[3] = "";
			StaticStore.airValues[4] = "";
			StaticStore.isAirline = true;
			StaticStore.passValues = new String[6 * Integer.parseInt(StaticStore.midlet.airTotalTickets)];
			for (int i = 0; i < StaticStore.passValues.length; i++) {
				StaticStore.passValues[i] = "";
			}
			new AirlineInput();
			Intent myIntent = StaticStore.midlet.getAirTitle(StaticStore.context);
			StaticStore.midlet.startFragment(StaticStore.context,myIntent);
			
		} else if (formName.equals("I100")) {
			StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet
					.getHostelTution(StaticStore.context));
		} else if (formName.equals("I4PP")) {
			StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet
					.getInstPaymentOption(StaticStore.context));

		} else if (formName.equals("I4NP")) {
			if (!StaticStore.midlet.instBillDetails[0][9].trim()
					.toUpperCase().equals("Y")) {
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
				StaticStore.menuDesc[107][13] = "N";
				StaticStore.menuDesc[107][15] = StaticStore.midlet.txnID;
				StaticStore.index = 107;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
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
				StaticStore.menuDesc[108][13] = "N";
				StaticStore.menuDesc[108][15] = StaticStore.midlet.txnID;
				StaticStore.index = 108;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
			}
    }else if(formName.equals("ML00N")){//mobile recharge
        StaticStore.menuDesc[33][3] = StaticStore.midlet.operatorList[StaticStore.selectedIndex][0];
        StaticStore.menuDesc[33][6] = StaticStore.midlet.txnID;
        StaticStore.index = 33;
	    StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
    }else if(formName.equals("R600")){//mobile recharge
    	 StaticStore.menuDesc[220][4] = FormattedArray[1];
         StaticStore.menuDesc[220][5] = FormattedArray[2];
          StaticStore.menuDesc[220][6] = StaticStore.midlet.txnID;
            StaticStore.index = 220;
            StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
						DynamicCanvas.class));
		}else if(formName.equals("APDX") || formName.equals("APDT")){//TNEB
			if(StaticStore.forCOCTAX){
				StaticStore.forCOCTAX = false;
				Intent myIntent = StaticStore.midlet.getCOCTAXOptions(StaticStore.context);
				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
			}else{
				StaticStore.index = 222;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
			}
		}else if(formName.equals("APHC")){
			StaticStore.indexCtr--;
			StaticStore.tagType = "APHC";	
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}else if(formName.equals("APDY")||formName.equals("AP2B")){//TNEB Siva
			//   		        	StaticStore.tagType = "APDY";
			StaticStore.LogPrinter('i',"Test <><><>");
			String Temp = "Full Payment";
			if(StaticStore.midlet.billpayPres[0][4].trim().equals("Y")){
				Temp +=";Minimum Payment";
			}
			if(StaticStore.midlet.billpayPres[0][5].trim().equals("Y")){
				Temp +=";Part Payment";
			}
			if(StaticStore.midlet.billpayPres[0][6].trim().equals("Y")){
				Temp +=";Excess Payment";
			}
			StaticStore.LogPrinter('i',"Temp String >>>>>"+Temp);
			String[] menuItem =Temp.split(";",-1);

			Intent mPayIntent = new Intent(StaticStore.context, ListSelection.class);
			mPayIntent.putExtra("listIndex", 228);
			mPayIntent.putExtra("menuItem", menuItem);
			mPayIntent.putExtra("listHeader","Payment Options"); 	 
			mPayIntent.putExtra("more", false);
			StaticStore.midlet.startFragment(StaticStore.context,mPayIntent);

		}else if(formName.equals("APN4")){
			String Temp = "Full Payment";
			if(StaticStore.midlet.N400DetailsList[4].trim().equals("Y")){
				Temp +=";Minimum Payment";
			}
			if(StaticStore.midlet.N400DetailsList[5].trim().equals("Y")){
				Temp +=";Part Payment";
			}
			if(StaticStore.midlet.N400DetailsList[6].trim().equals("Y")){
				Temp +=";Excess Payment";
			}
			String[] menuItem =Temp.split(";",-1);

			Intent mPayIntent = new Intent(StaticStore.context, ListSelection.class);
			mPayIntent.putExtra("listIndex", 286);
			mPayIntent.putExtra("menuItem", menuItem);
			mPayIntent.putExtra("listHeader","Payment Options"); 	 
			mPayIntent.putExtra("more", false);
			StaticStore.midlet.startFragment(StaticStore.context,mPayIntent);
		} else if(formName.equals("AP1B")){//TNEB Siva
			String Temp = "Full Payment";
			if(StaticStore.midlet.billpayBills[0][3].trim().equals("Y")){
				Temp +=";Minimum Payment";
			}
			if(StaticStore.midlet.billpayBills[0][4].trim().equals("Y")){
				Temp +=";Part Payment";
			}
			if(StaticStore.midlet.billpayBills[0][5].trim().equals("Y")){
				Temp +=";Excess Payment";
			}
			StaticStore.LogPrinter('i',"Temp String >>>>>"+Temp);
			String[] menuItem =Temp.split(";");

			Intent mPayIntent = new Intent(StaticStore.context, ListSelection.class);
			mPayIntent.putExtra("listIndex", 236);
			mPayIntent.putExtra("menuItem", menuItem);
			mPayIntent.putExtra("listHeader","Payment Options"); 	 
			mPayIntent.putExtra("more", false);
			StaticStore.midlet.startFragment(StaticStore.context,mPayIntent);

		} else if (formName.equals("BW00")) {
			StaticStore.menuDesc[18][4] = StaticStore.billPayCustMneName;
			StaticStore.index = 18;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("SC00")) {
			StaticStore.menuDesc[162][3] = StaticStore.iCashMobNo;
			StaticStore.menuDesc[162][4] = StaticStore.iCashSurCharge;
			StaticStore.menuDesc[162][5] = StaticStore.iCashAmt;
			StaticStore.menuDesc[162][6] = StaticStore.midlet.txnID;
			StaticStore.index = 162;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("W100")) {
			StaticStore.menuDesc[172][3] = StaticStore.MMID;
			StaticStore.menuDesc[172][4] = StaticStore.benMobNo;
			StaticStore.menuDesc[172][5] = StaticStore.benNicName;
			StaticStore.index = 172;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		  }else if(formName.equals("1I00")){
          	
        	  StaticStore.menuDesc[220] = new String []{"Add Merchant","AP2I;Y",StaticStore.mPINString,StaticStore.MMID,StaticStore.benMobNo,StaticStore.benNicName,
                    "","","","","4","false","false","N"};
        	  StaticStore.tagType="AP2I";
        	  StaticStore.index = 220;
  			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
  					DynamicCanvas.class));
		} else if(formName.equals("CH00")){//172 cahnged to 166
        	StaticStore.accpaymentFlag = true;
        	StaticStore.menuDesc[220] =new String[] {};
			StaticStore.menuDesc[220] = new String []{"Generate Card","APCH;Y","Amount (Rs.)","0-9-ND-N-N","1","true","true","N"};
			StaticStore.tagType = "APCH";
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
        }else if(formName.equals("TP00")){//172 cahnged to 166
        

        	StaticStore.menuDesc[220] =new String[] {};
			StaticStore.menuDesc[220] = new String []{"Top-up","APTP;Y","Amount (Rs.)","0-9-ND-N-N","1","true","true","N"};
			StaticStore.tagType = "APTP";
			StaticStore.index = 220;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
			

//          
        
        }else if (formName.equals("GTESTCNF")) {

			StaticStore.GprsUrl = "http"
					+ StaticStore.GprsServiceUrl
							.substring(StaticStore.GprsServiceUrl
									.indexOf(":"));
			StaticStore.IsGPRS = true;
			StaticStore.midlet.writeinMemory(StaticStore.context);
			StaticStore.index = 154;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		} else if (formName.equals("QIMPS")
				|| formName.equals("QIMPS REG")) {
			StaticStore.legValue[1] = StaticStore.dynamicListValues[0];
			StaticStore.legValue[2] = StaticStore.dynamicListValues[1];
			StaticStore.legValue[3] = StaticStore.dynamicListValues[2];
			StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
		} else if (formName.equals("fd")) {
			StaticStore.fromFdDisplayable = true;
			StaticStore.midlet.pubDynCan.navigateTo(StaticStore.index);
		} else if (formName.equals("Activation")) {
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					GridScreenView.class));
		} else if (formName.equals("SU00_NU00")) {
			if(StaticStore.IsPermitted){
				Intent myIntent = new Intent(StaticStore.context,GridScreenViewActivation.class);
				StaticStore.midlet.startFragment(StaticStore.context,myIntent);
			}else{
				StaticStore.index = 114;
				StaticStore.FromListScreen = false;
				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
			}
			
		} else if(formName.equals("3T00")||formName.equals("4T00")){
			StaticStore.tagType = "AP5T";
			
			StaticStore.menuDesc[220] = new String[]{"Recharge","AP5T;Y",StaticStore.mPINString,StaticStore.midlet.topUpIndicator,StaticStore.rechargeOpID_R_NickName,
					StaticStore.rechargeSelcetedCategoryID,StaticStore.midlet.rechargelabels,StaticStore.midlet.txnID,"","","","","","","6","false","false","N"};
			StaticStore.index = 220;
			StaticStore.FromListScreen = false;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,DynamicCanvas.class));
		}else if (formName.equals("APN2")){
			StaticStore.midlet.collegeDetailsList = StaticStore.midlet.getSplittedValues(StaticStore.collegeDetails,3,'#',true,StaticStore.midlet.collegeDetailsList);
			StaticStore.midlet.startFragment(getActivity(),StaticStore.midlet.get_CollegeDetailsLists(getActivity()));
		}
			else {
			StaticStore.menuDesc[77][3] = "Y";
			StaticStore.menuDesc[77][4] = TXNID;
			StaticStore.index = 77;
			StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context,
					DynamicCanvas.class));
		}
	}

	private int getDynamicIndex(String formName) {
		if (formName.startsWith("AB00")) {
			title = "Payment Details";
			return 0;
		} else if (formName.startsWith("FCOMP")) {
			title = "Forgot eCash Pin";
			return 70;
		}else if (formName.startsWith("CN00")) {
			title = "Shopping";
			return 2;
		}else if(formName.equals("6U00")){
			successRespMsgs[78] = new String[3];
			successRespMsgs[78][0] = "Nickname";
			successRespMsgs[78][1] = "AADHAAR No.";
			successRespMsgs[78][2] = "A/C type";
			title = "Beneficiary Details";     
			return 78;
		}else if(formName.equals("M900")){
			successRespMsgs[78] = new String[3];
			successRespMsgs[78][0] = "Merchant Nickname";
			successRespMsgs[78][1] = "MMID";
			successRespMsgs[78][2] = "Mobile No.";
			title = "Merchant Details";     
			return 78;
		} else if (formName.startsWith("BR00")) {
			// if(StaticStore.isBillpayEnabled)
			// previousDisplay = midlet.getBillpayMenu();
			// else
			// previousDisplay = midlet.get_MainMenu();
			// title = "Bill Payments Registration";
			return 2;
		} else if (formName.startsWith("LB00")) {
			title = "Add Biller";
			return 2;
		} else if (formName.startsWith("DEFAULT")) {

			return 2;
		} else if (formName.startsWith("BD00")) {
			title = "Delete Biller";
			return 2;
		} else if (formName.startsWith("TD00")) {
			title = "Donation";
			return 2;
		} else if (formName.startsWith("CPCN99")) {
			title = "Payment Details";
			return 3;
		} else if (formName.equals("M300")) {
			title = "Movie Ticketing";
			return 4;
		} else if (formName.equals("FCOMP")) {
			title = "Forgot eCash Pin";
			return 70;
		} else if (formName.equals("MC00")) {
			title = "Mobile Topup";
			return 5;
		} else if (formName.equals("MESSAGE")) {
			title = "";
			return 2;
		} else if (formName.equals("IR00")) {

			title = "Institution Registration";
			return 2;
		} else if (formName.equals("M400")) {

			title = "Movie Ticketing";
			return 6;
		} else if (formName.equals("AR00")) {

			title = "Booking & Payment";
			return 7;
		} else if (formName.equals("R100")) {

			title = "Payment Only";
			return 1;
		} else if (formName.equals("R200")) {

			title = "Booking & Payment";
			return 8;
		} else if (formName.equals("Q200")) {

			title = "Add Beneficiary";
			return 9;
		} else if (formName.equals("Airline")) {

			title = "Payment Details";
			return 10;
		} else if (formName.equals("N100")) {
			title = "Bill Notification";
			return 11;
		} else if (formName.equals("restart")|| formName.equals("alert")||formName.equals("HTTPERROR")) {
			title = " ";
			return 69;
		} else if (formName.equals("SRXX")) {

			title = "Corporate Registration";
			return 12;
		} else if (formName.equals("SRYY")) {

			title = "Sub-Corporate Registration";
			return 13;
		} else if (formName.equals("PSZZ")) {

			title = "Sub-Corporate Payment";
			return 14;
		} else if (formName.equals("PSYY")) {

			title = "Sub-Corporate Payment";
			return 15;
		} else if (formName.equals("PSXX")) {

			title = "Corporate Payment";
			return 16;
		} else if (formName.equals("PSWW")) {

			title = "Corporate Payment";
			return 17;
		} else if (formName.equals("SIZZ")) {

			title = "Standing Instructions";
			return 18;
		} else if (formName.equals("SIYY")) {

			title = "Standing Instructions";
			return 19;
		} else if (formName.equals("DRZZ")) {

			title = "Delete Beneficiary";
			return 20;
		} else if (formName.equals("DRYY")) {

			title = "Delete Beneficiary";
			return 21;
		} else if (formName.equals("CFZZ")) {

			title = "Forgot Password";
			return 13;
		} else if (formName.equals("CFYY")) {

			title = "Change Login Password";
			return 12;
		} else if (formName.equals("RM00")) {

			title = "Add Beneficiary";
			return 22;
		} else if (formName.equals("RA00")) {

			title = "Add Beneficiary";
			return 23;
		} else if (formName.equals("BillInfo")) {
			if (StaticStore.midlet.billpayBills[StaticStore.midlet.selectedBillIndex][1]
			                                                                          .trim().toUpperCase().equals("Y")) {
				title           = "Pay Biller"; //mid:12873 Registered Bill Payment
			} else {
				title           = "Pay Biller";//mid:12873 Registered Bill Payment
			}
			return 24;
		} else if (formName.equals("BillInfo")) {

			title = "Offers";

			return 24;
		}
		else if (formName.equals("Temple")) {
			StaticStore.LogPrinter('i',"messageContent" + messageContent);
			title = "Donation";
			int ctr = StaticStore.midlet.getCharCount(messageContent, ';');
			StaticStore.LogPrinter('i',"CTR" + ctr);
			if (ctr == 5) {
				return 25;
			} else if (ctr == 4) {
				return 26;
			} else if (ctr == 3) {
				return 27;
			}
		} else if (formName.equals("Z600")) {
			if(StaticStore.midlet.emailId.equals("")){
				successRespMsgs[28][0] = "Description";
			}else{
				successRespMsgs[28][0] = "E-mail ID";
			}

			title = "Configure/Update E-mail ID ";
			return 28;
		} else if (formName.equals("Partner")) {
			title = "Partner Bank Details";
			return 29;
		} else if (formName.equals("Campaign")) {
			title = "Promotional Campaign";
			return 30;
		} else if (formName.equals("AC00")) {
			title = "Booking & Payment";
			return 31;
		} else if (formName.equals("Adhocpay")) {
			title = "Instant Bill Payment";
			successRespMsgs[32] = new String[] {"Biller ID","Biller Name","Fixed Charges","Variable Charges"};//32   //siva
			return 32;
		} else if (formName.equals("Billerlink")) {
			title = "Add Biller";
			successRespMsgs[32] = new String[] {"Biller ID","Biller Name"};//32 //y300 Siva
			return 32;
		} else if (formName.equals("A100")) {
			title = "Airline Details";
			return 33;
		} else if (formName.equals("CW00")) {
			title = "Change Login Password";
			return 34;
		} else if (formName.equals("I100")) {
			title = "Student Details";
			successRespMsgs[35][0] = StaticStore.midlet.rollNoLabel;
			return 35;
		} else if (formName.equals("I4PP")) {
			title = "Fees Payment";
			return 36;
		} else if (formName.equals("I4NP")) {
			title = "Fees Payment";
			return 37;
		}else if(formName.equals("ML00N")){//mobile recharge
			title = "Mobile Recharge";           
			return 38;
		}else if(formName.equals("R600")){//mobile recharge
			title = "Mobile Recharge";
			successRespMsgs[71] = new String[] {"Operator Name","Mobile No.","Recharge Amount"};//32   //vinoth
			return 71;
		} else if (formName.equals("3S00")) {
			title = "IFSC Search";
			return 39;
		} else if (formName.equals("D900")) {
			title = "Beneficiary Details";
			return 60;
		} else if (formName.equals("Version Update")) {
			title = "Version Update";
			return 79;
		} else if (formName.equals("V600")) {
			title = "Visa Card Beneficiary Details";
			return 62;
		} else if (formName.equals("ATM")) {
			title = "ATM";
			return 41;
		}else if(formName.equals("SAXX")){
			successRespMsgs[40] = new String[] {"City","Address","Pincode"}; 
			title = "ATM"; //MID: 12897
			return 40;
		}else if(formName.equals("SAYY")){
			successRespMsgs[41] = new String[] {"City","Address","Pincode"}; 
			title = "ATM"; //MID: 12897
			return 41;
		}else if(formName.equals("BSXX")){
			successRespMsgs[40] = new String[] {"Address","Pincode"}; 
			title = "Branch";
			return 40;
		}else if(formName.equals("BSYY")){
			successRespMsgs[41] = new String[] {"Address"}; 
			title = "Branch";
			return 41;
		}else if(formName.equals("ESXX")){
			successRespMsgs[40] = new String[] {"Address","Pincode"}; 
			title = "E-Lobby";
			return 40;
		}else if(formName.equals("ESYY")){
			successRespMsgs[41] = new String[] {"Address"}; 
			title = "E-Lobby";
			return 41;
		}else if (formName.equals("BW00")) {
			title = "Pay Biller"; //mid:12873 Registered Bill Payment
			return 42;
		} else if (formName.equals("4S00")) {
			title = "Bank Search";
			return 43;
		} else if (formName.equals("ForgotPWD")) {
			if(StaticStore.fromGprs){
				title = "Internet TEST";	
			}else{
				title = "Forgot Password";
			}
			return 44;
		} else if (formName.equals("About")) {
			title = "About TMB mConnect";
			return 45;
		} else if (formName.equals("GTEST") || formName.equals("GTESTCNF")) {
			title = "Internet Test";
			return 44;
		} else if (formName.equals("FT00")) {
			if(StaticStore.index == 101){
	               successRespMsgs[46] = new String[3];
	               successRespMsgs[46][0] = "Nickname";
	               successRespMsgs[46][1] = "Amount";
	               successRespMsgs[46][2] = "Remarks";
	               title = "Pay Beneficiary";
	               return 46;
	            }else if(StaticStore.index == 103){
	               successRespMsgs[46] = new String[3];
	               successRespMsgs[46][0] = "Nickname";
	               successRespMsgs[46][1] = "Amount";
	               successRespMsgs[46][2] = "Remarks";
	               title = "Pay Beneficiary";
	               return 46;
	            }else{
	               successRespMsgs[46] = new String[3];
	               successRespMsgs[46][0] = "Beneficiary Name";
	               successRespMsgs[46][1] = "Amount";
	               successRespMsgs[46][2] = "Remarks";
	               title = "Pay Beneficiary";
	               return 46;
	            }
		} else if (formName.equals("MBEN")) {
			title = "Beneficiary Details";
			return 47;
		} else if (formName.equals("ABEN")) {
			title = "Beneficiary Details";
			return 48;
		} else if (formName.equals("NBEN")) {
			title = "Beneficiary Details";
			return 49;
		} else if (formName.equals("ICash")) {
			title = "Remittance";
			return 50;
		} else if (formName.equals("SC00")) {
			title = "Remittance";
			return 51;
		} else if (formName.equals("ECXX")) {
			title = "Status";
			return 52;
		} else if (formName.equals("W100")) {
			title = "Add Beneficiary";
			return 53;
		} else if (formName.equals("QIMPS")) {
			title = "Instant Pay";
			return 54;
		} else if (formName.equals("N400")) {
			title = "Fees Payment";
			successRespMsgs[71] = new String[StaticStore.midlet.loanList.length];
			for(int i = 0; i < StaticStore.midlet.collegeUniqueDetailsList.length;i++){
				successRespMsgs[71][i] = StaticStore.midlet.collegeUniqueDetailsList[i][0];
			}
			return 71;
		} else if (formName.equals("LR00")) {
			title = "Loan Calculator";
			successRespMsgs[71] = new String[StaticStore.midlet.loanList.length];
			for(int i = 0; i < StaticStore.midlet.loanList.length;i++){
				successRespMsgs[71][i] = StaticStore.midlet.loanList[i][0];
			}
			return 71;	
		} else if (formName.equals("DZ00")) {
			title = "Instant Bill Payment";
			if(StaticStore.forCOCTAX){
				StaticStore.forCOCTAX = false;
				successRespMsgs[71] = new String[StaticStore.midlet.cocTaxList.length];
				for(int i = 0; i < StaticStore.midlet.cocTaxList.length;i++){
					successRespMsgs[71][i] = StaticStore.midlet.cocTaxList[i][0];
				}
			}else{
				successRespMsgs[71] = new String[StaticStore.midlet.tnebList.length];
				for(int i = 0; i < StaticStore.midlet.tnebList.length;i++){
					successRespMsgs[71][i] = StaticStore.midlet.tnebList[i][0];
				}
			}
			return 71;
		} else if (formName.equals("N500")) {
			title = "Fees Payment";
			successRespMsgs[71] = new String[StaticStore.midlet.ifpList.length];
			for(int i = 0; i < StaticStore.midlet.ifpList.length;i++){
				successRespMsgs[71][i] = StaticStore.midlet.ifpList[i][0];
			}
			return 71;
		} else if (formName.equals("APN4")) {
			title = "Fees Payment";
			successRespMsgs[71] = new String[StaticStore.midlet.collegeUniqueDetailsList.length];
			for(int i = 0; i < StaticStore.midlet.collegeUniqueDetailsList.length;i++){
				successRespMsgs[71][i] = StaticStore.midlet.collegeUniqueDetailsList[i][0];
			}
			return 71;		
		} else if (formName.equals("QIMPS REG")) {
			title = "Registered Beneficiary IMPS";
			return 55;
		} else if (formName.equals("REGIMPS")) {
			title = "Generate MMID";
			return 56;
		} else if (formName.equals("HELP")) {
			title = "Help";
			// successRespMsgs[57][0] = StaticStore.helpHeading;
			return 57;
		} else if (formName.equals("SMSCOMP")) {
			title = "Timeout Complaint";
			return 57;
		} else if (formName.equals("fd")) {// for indus
			title = "";
			return 58;
		} else if (formName.equals("Activation")) {
			if(StaticStore.isForgotPassword){
				title = "Forgot Password";
			}else{
				title = "Activation";
			}
			return 59;
		}else if (formName.equals("SU00_NU00")) {
			successRespMsgs[78] = new String[] {"Acknowledgement"};
			if(StaticStore.tagType == "APSU"){
				title = "Debit Card" ;
			}else{
				title = "Net Banking" ;
			}
			return 78;
		} else if (formName.equals("NL00")) {
			title = "Beneficiary Details";
			return 61;
		} else if (formName.equals(StaticStore.BE)) {
			title = "Balance Enquiry";
			return 63;
		} else if (formName.equals(StaticStore.MS)) {
			title = "Mini Statement";
			return 64;
		} else if (formName.equals("AccDisplay")) {
			title = "Account Fetch";
			return 65;
		} else if (formName.equals("status")) {
			title = "Recharge Status Enquiry";
			return 66;
		} else if (formName.equals("vpBill")) {
			title = "View Paid Bills";
			return 67;
		}else if (formName.equals("APK1")) {
			title = "Instant Pay";
			return 72;

		}else if (formName.equals("TH00")) {
			successRespMsgs[78] = new String[] {"TXN Date & Time","Status","Amount","A/C No.","Nickname","Delivery Channel","Transaction ID"};
			title = "Transaction Details";     
			return 78;
		}else if (formName.equals("TH001")) {
			successRespMsgs[78] = new String[] {"TXN Date & Time","Status","Amount","Delivery Channel","Transaction ID"};
			title = "Transaction Details";     
			return 78;
		}else if (formName.equals("TH002")) {
			successRespMsgs[78] = new String[] {"TXN Date & Time","Status","Amount","Nickname","Delivery Channel","Transaction ID"};
			title = "Transaction Details";     
			return 78;
		}else if (formName.equals("TH003")) {
			successRespMsgs[78] = new String[] {"TXN Date & Time","Status","Amount","A/C No.","Delivery Channel","Transaction ID"};
			title = "Transaction Details";     
			return 78;

		}else if (formName.equals("LI00")) {
			successRespMsgs[78] = new String[] {"Cheque No.","Amount","Cheque Status"};
			title = "Inward Cheque Status";     
			return 78;

		}else if (formName.equals("LO00")) {
			successRespMsgs[78] = new String[] {"Cheque No.","Amount","Cheque Status"};
			title = "Outward Cheque Status";     
			return 78;

		}else if (formName.equals("1I00")) {
			successRespMsgs[78] = new String[] {"Bank Name","MMID","Mobile No.","Nickname"};
			title = "Add Merchant";
			return 78;

		}else if (formName.equals("AP1U")) {
			successRespMsgs[78] = new String[] {"Beneficiary AADHAAR No.","Beneficiary A/C Type","Amount (Rs.)","Remarks"};
			title = "Instant Pay";
			return 78;
		}else if (formName.equals("2U00")) {
			successRespMsgs[78] = new String[] {"Beneficiary AADHAAR No.","Beneficiary A/C Type","Beneficiary Nickname"};
			title = "Add Beneficiary";
			return 78;
		}else if(formName.equals("QN00")){
			successRespMsgs[78] = new String[5];
			successRespMsgs[78][0] = "Beneficiary A/C No.";
			successRespMsgs[78][1] = "Beneficiary A/C Type";
			successRespMsgs[78][2] = "Beneficiary IFS Code";
			successRespMsgs[78][3] = "Amount";
			successRespMsgs[78][4] = "Remarks";
			title = "Instant Pay";
			return 78;
		}else if(formName.equals("QT00")){
			successRespMsgs[78] = new String[4];
			successRespMsgs[78][0] = "Beneficiary A/C No.";
			successRespMsgs[78][1] = "A/C Type";
			successRespMsgs[78][2] = "Amount";
			successRespMsgs[78][3] = "Remarks";
			title = "Instant Pay";
			return 78;
		}else if(formName.equals("QM00")){
			successRespMsgs[78] = new String[3];
			successRespMsgs[78][0] = "Beneficiary Mobile No.";
			successRespMsgs[78][1] = "Amount";
			successRespMsgs[78][2] = "Remarks";
			title = "Instant Pay";
			return 78;
		}else if (formName.equals("APK2")) {
			title = "Add Beneficiary";
			return 73;

		}else if (formName.equals("APK2FINAL")) {
			title = "Add Beneficiary";
			return 74;
		}else if (formName.equals("4W00")) {
			title = "Beneficiary Details";
			return 75;
		}else if (formName.equals("APK9Stat")) {
			title = "A/C Statement";
			return 76;
		}else if (formName.equals("APK5")) {
			title = "Pay Beneficiary";
			return 77;
		}else if (formName.equals("W400")) {
			successRespMsgs[78] = new String[] {"Amount","Remarks"};
			title = "Pay Beneficiary";
			return 78;
		}else if (formName.equals("APPI")) {
			successRespMsgs[78] = new String[] {"Merchant Mobile No.","Merchant MMID No.","Amount","Payment Reference"};
			title = "Instant Pay";
			return 78;
		}else if (formName.equals("APPM")) {
			successRespMsgs[78] = new String[] {"Amount","Payment Reference"};
			title = "Pay Merchant";
			return 78;
		}else if (formName.equals("AP5U")) {
			successRespMsgs[78] = new String[] {"Amount","Remarks"};
			title = "Pay Beneficiary";
			return 78;
		}else if (formName.equals("AP4W")) {
			successRespMsgs[78] = new String[] {"A/C No.","A/C Type","IFS Code","Nickname"};
			title = "Beneficiary Details";
			return 78;
		}else if (formName.equals("QI00")) {
			successRespMsgs[78] = new String[] {"Beneficiary Mobile No.","Beneficiary MMID No.","Amount","Remarks"};
			title = "Instant Pay";
			return 78;
		}else if (formName.equals("APDX")) {
			if(StaticStore.IsPresentment)
			{
				successRespMsgs[78] = new String[]{"Biller Name","Sub-Category Name"};
			}else
			{
				successRespMsgs[78] = new String[]{"Biller Name","Sub-Category Name","Fixed Charges","Variable Charges"};
			}
			title = "Instant Bill Payment";
			return 78;
		}else if (formName.equals("APDT")) {
			if(StaticStore.IsPresentment)
			{
				successRespMsgs[78] = new String[]{"Biller ID","Biller Name"};
			}else
			{
				successRespMsgs[78] = new String[]{"Biller ID","Biller Name","Fixed Charges","Variable Charges"};
			}
			title = "Instant Bill Payment";
			return 78;
		}else if (formName.equals("APDY")) {
			title = "Instant Bill Payment";
			successRespMsgs[78] = new String[]{"Bill Amount","Minimum Amount","Customer Name","Bill No.","Bill Date","Due Date","Fixed Charges","Variable Charges"};// 66 confirm APDY
			return 78;
		}else if (formName.equals("APHC")) {
			successRespMsgs[78] = new String[] {"Card No.","Card Holder Name","Card Position"};
			title = "Card No. Details";
			return 78;
		}else if (formName.equals("APQM")) {
			successRespMsgs[78] = new String[] {"Beneficiary Mobile No.","Amount","Remarks"};
			title = "Instant Pay";
			return 78;
		}else if (formName.equals("AP2B")) {
			title = "Instant Bill Payment";
			successRespMsgs[78] = StaticStore.Display_header;
			StaticStore.LogPrinter('i',"AP2B --> "+Arrays.deepToString(successRespMsgs[78]));
			return 78;
		}else if (formName.equals("AP1B")) {
			title = "Pay Biller"; //mid:12873 Registered Bill Payment
			successRespMsgs[78] = StaticStore.Display_header;
			StaticStore.LogPrinter('i',"AP1B --> "+Arrays.deepToString(successRespMsgs[78]));
			return 78;
		}else if (formName.equals("3T00")||formName.equals("4T00")) {
			title = "Recharge";
			successRespMsgs[78] = StaticStore.Display_header;
			return 78;
		}else if (formName.equals("7T00")) {
			title = "Recharge Status Enquiry";
			successRespMsgs[78] = StaticStore.Display_header;
			return 78;
		}else if (formName.equals("CH00")) {
			successRespMsgs[78] = new String[] {"Card Type","Issuance Fees","Topup Fees","Min. Limit","Max .Limit","Validity"};
			title = "Generate Card";
			return 78;
		}else if (formName.equals("TP00")) {
			successRespMsgs[78] = new String[] {"Card Type","Issuance Fees","Topup Fees","Min. Limit","Max .Limit","Validity","Available Balance"};
			title = "Top-up";
			return 78;
		}else if (formName.equals("TC00")) {
			successRespMsgs[78] = new String[] {"Acknowledgement","Tracking ID","Date & Time","Transaction ID"};
			title = "Top-up";
			return 78;
		}else if (formName.equals("CT00")) {
			successRespMsgs[78] = new String[] {"Acknowledgement","Tracking ID","Date & Time","Transaction ID"};
			title = "Generate Card";
			return 78;
		}else if (formName.equals("APCH")) {
			successRespMsgs[78] = new String[] {"Card Type","Amount"};
			title = "Generate Card";
			return 78;
		}else if (formName.equals("APTP")) {
			successRespMsgs[78] = new String[] {"Card Type","Amount"};
			title = "Top-up";
			return 78;  
		}else if (formName.equals("Error_in_resp") || formName.equals("WAP_ZP")) {
			successRespMsgs[78] = new String[] {"Acknowledgement"};
			return 78;  
		}else if(formName.equals("OTP")){
			successRespMsgs[78] = new String[] {"Acknowledgement"};
			if(StaticStore.IsPermitted)
				title = "OTP for Login";
			else
				title = "OTP for Activation";	
			return 78; 
		}else if (formName.equals("APDOConfirm")) {
			if(StaticStore.depositIDConfirm.equals("17")){
				successRespMsgs[78] = new String[] {"Account Opening Date","Installment Amount (Rs.)","Deposit Period in Months"};
				title = "Recurring Deposit";
			}else if(StaticStore.depositIDConfirm.equals("15")){
				successRespMsgs[78] = new String[] {"Account Opening Date","Deposit Amount (Rs.)","Deposit Period in Months", "Deposit Period in Days","Maturity Instruction","Pay Interest"};
				title = "Fixed Deposit";
			}else if(StaticStore.depositIDConfirm.equals("50") || StaticStore.depositIDConfirm.equals("51")){
				successRespMsgs[78] = new String[] {"Scheme Type","Account Opening Date","Deposit Amount (Rs.)","Deposit Period in Months"};
				title = "Navarathnamala Deposit";
			}else if(StaticStore.depositIDConfirm.equals("16")){
				successRespMsgs[78] = new String[] {"Account Opening Date","Deposit Amount (Rs.)","Deposit Period in Months","Maturity Instruction"};
				title = "Muthukuvial Deposit";
			}else if(StaticStore.depositIDConfirm.equals("53")){
				successRespMsgs[78] = new String[] {"Account Opening Date","Deposit Amount (Rs.)"};
				title = "Special 20:20 Deposit";
			}else if(StaticStore.depositIDConfirm.equals("52")){
				successRespMsgs[78] = new String[] {"Account Opening Date","Deposit Amount (Rs.)","Interest Option"};
				title = "Special 555 Deposit";
			}
	
			return 78;
		}else if (formName.equals("APDO")) {
			successRespMsgs[78] = new String[] {"Account No.", "Customer Name", "Scheme Code", "Deposit Period in Months", "Deposit Period in Days", "Account Opening Date", "Maturity Amount", "Maturity Date", "Rate Of Interest", "E-Receipt"};
			if(StaticStore.depositID.equals("17")){
				title = "Open Recurring Deposit";
			}else if(StaticStore.depositID.equals("15")){
				title = "Open Fixed Deposit (Simple Interest)";
			}else if(StaticStore.depositID.equals("51") || StaticStore.depositID.equals("50")){
				title = "Open Navarathnamala Deposit";
			}else if(StaticStore.depositID.equals("16")){
				title = "Open Muthukuvial Deposit";
			}else if(StaticStore.depositID.equals("53")){
				title = "Special 20:20 Deposit";
			}else if(StaticStore.depositID.equals("52")){
				title = "Special 555 Deposit";
			}
			return 78;
		}else if (formName.equals("APDB")) {
			successRespMsgs[78] = new String[] {"Customer Name","A/C No.","SchemeCode","Deposit Period in Months","Deposit Period in Days","Maturity Date","Maturity Amount","Account Opening Date","Rate Of Interest"};
			title = "Deposit Account Balance";
			return 78;
		}else if (formName.equals("APLE")) {
			successRespMsgs[78] = new String[] {"Balance Amount", "Open Date", "Total Amount", "Scheme"};
			title = "Loan Balance";
			return 78;
		}else if (formName.equals("APST")) {
			successRespMsgs[78] = new String[] {"From A/C No.", "To A/C No.", "A/C Type", "Amount", "Remarks"};
			title = "My Own Accounts";
			return 78;
		}else if (formName.equals("APN2")) {
			successRespMsgs[78] = StaticStore.Display_header;
			title = "Student Details";
			return 78;
		}
		return 0;
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

	public void getBalanceTypes(String[] dummy) {

		if (dummy[0].trim().equals("")) {
			balanceTypeFlag[0] = false;
		} else {
			balanceTypeFlag[0] = true;
			balArrLength++;
		}
		if (dummy[1].trim().equals("")) {

			balanceTypeFlag[1] = false;
		} else {
			balanceTypeFlag[1] = true;
			balArrLength++;
		}
		if (dummy[2].trim().equals("")) {
			balanceTypeFlag[2] = false;
		} else {
			balanceTypeFlag[2] = true;
			balArrLength++;
		}
	}

	private String getDescription(String desc) {
		String description = "";
		for (int i = 0; i < accountDescription.length; i++) {
			if (desc.equals(accountDescription[i][0])) {
				description = accountDescription[i][1];
			}
		}
		return description;
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
						StaticStore.midlet.startFragment(StaticStore.context,myIntent);
					}
				});
		// set a negative/no button and create a listener
		alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
			// do something when the button is clicked
			public void onClick(DialogInterface arg0, int arg1) {
				if (StaticStore.index == 209) {
					StaticStore.indexCtr -= 1;
				}
				arg0.cancel();
			}
		});

		return alertbox;
	}

	
	public static String onBackPressed(Context context2)
	{
		StaticStore.forCOCTAX = false;
		// TODO Auto-generated method stub
		// return super.onKeyDown(keyCode, event);
		StaticStore.LogPrinter('i',"Came inside disp Back-->"+StaticStore.index);
		StaticStore.LogPrinter('i',"formName disp Back-->"+formName);
		StaticStore.LogPrinter('i',"StaticStore.midlet.recMsgType -->"+StaticStore.midlet.recMsgType);
		StaticStore.LogPrinter('i',"StaticStore.isInbox ==>"+StaticStore.isInbox);

		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context2);
		if(sessionflag){
			if(StaticStore.indexCtr == 1)
			{
				StaticStore.midlet.isImageTextList = true;
			}else
			{
				StaticStore.midlet.isImageTextList = false;
			}
		if (StaticStore.isMenuFromDashBoard) {	
    		StaticStore.midlet.startFragment(context2,new Intent(context2,FirstPageActivity.class));
    		return "true";
		}else if(  StaticStore.index == 220 &&StaticStore.tagType=="APP2" && !StaticStore.isInbox ){
    		StaticStore.midlet.startFragment(context2,new Intent(context2,GridScreenView.class));
    		return "true";
        }else if(StaticStore.depositAccBalance && !StaticStore.isInbox){
			StaticStore.depositAccBalance = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
        }else if(StaticStore.loanBalance && !StaticStore.isInbox){
			StaticStore.loanBalance = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
        }else if(  formName.equals("Activation")&& !StaticStore.isInbox ){
    		//    		exit();
    		return "false";
    	}else if( (formName.equals("SU00_NU00") && title.equals("Feedback")|| title.equals("Refer a Friend") || formName.equals("Version Update") ||  formName.equals("About"))&& !StaticStore.isInbox ){
    		Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
    	}else if(  (StaticStore.IsPermitted && (formName.equals("OTP") || (StaticStore.index == 220 && StaticStore.tagType.equals("APOV")))) && !StaticStore.isInbox){
    		Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
    	}else if(  (!StaticStore.IsPermitted && (formName.equals("OTP") || (StaticStore.index == 220 && StaticStore.tagType.equals("APOV")))) && !StaticStore.isInbox ){
    		StaticStore.FromListScreen = false;
    		StaticStore.midlet.startFragment(context2,new Intent(StaticStore.midlet.initiateUserOption(context2)));
    		return "true";
    	}else if(  (StaticStore.midlet.recMsgType.equals("AC") || StaticStore.midlet.recMsgType.equals("PA"))&& !StaticStore.isInbox){
    		StaticStore.indexCtr = 2;
    		StaticStore.midlet.startFragment(context2, StaticStore.midlet.getAirlineMenu(context2));
    		return "true";
    	}else if(  StaticStore.midlet.recMsgType.equals("DZ") && !StaticStore.isInbox ){
    		StaticStore.indexCtr = 1;
    		StaticStore.index = 12;
    		Intent myIntent = StaticStore.midlet.getBillpayMenu(context2);
    		StaticStore.midlet.startFragment(context2, myIntent);
    		return "true";
    	}else if(  StaticStore.midlet.recMsgType.equals("DM") && !StaticStore.isInbox ){	
    		StaticStore.isBack = true;	
    		StaticStore.indexCtr = 1;
    		StaticStore.index = 12;
    		Intent myIntent = StaticStore.midlet.getBillpayMenu(context2);
    		StaticStore.midlet.startFragment(context2, myIntent);
    		return "true";
    	}else if(  StaticStore.midlet.recMsgType.equals("M4")&& !StaticStore.isInbox){//Movie ticket  Back LC Siva
			StaticStore.indexCtr = 2;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getMyServices(context2));
			return "true";
		}else if(  (StaticStore.midlet.recMsgType.equals("2L")) && !StaticStore.isInbox){//StaticStore.midlet.recMsgType.equals("FT") || 
			StaticStore.indexCtr = 3;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getFTRegistrationMenu("Mobile-to-Mobile",context2));
			return "true";
		}else if( (StaticStore.midlet.recMsgType.equals("3L")) && !StaticStore.isInbox){//StaticStore.midlet.recMsgType.equals("FA") || 
			StaticStore.indexCtr = 3;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getFTRegistrationMenuM2A("Mobile-to-Account",context2));
			return "true";
		}else if( (StaticStore.midlet.recMsgType.equals("3S")||formName.equals("3S00")) && !StaticStore.isInbox){//Fund transfer IFSC ser Back LC Siva
			StaticStore.indexCtr = 4;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getIFSCSearchMenu(context2));
			return "true";
		}else if(  StaticStore.midlet.recMsgType.equals("T5")&& !StaticStore.isInbox){//Temple Donation Back LC Siva
			StaticStore.indexCtr = 1;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getMyServices(context2));
			return "true";
		}else if( ( StaticStore.midlet.recMsgType.equals("IR")|| StaticStore.midlet.recMsgType.equals("IP"))&& !StaticStore.isInbox){//Ins Fees Payment Back LC Siva
			StaticStore.indexCtr = 1;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getInstMenu(context2));
			return "true";
		}else if((title.equals("Delete Beneficiary")) && (StaticStore.midlet.recMsgType.equals("BD"))){
            StaticStore.isBack = true;
			StaticStore.indexCtr -= 2;
			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];
			Intent myIntent = StaticStore.midlet
					.getList(context2, indexForBack,
							indexForSelectedBack);

			StaticStore.midlet.startFragment(context2, myIntent);
			return "true";
		}else if (  StaticStore.index != 123 && !StaticStore.forAPDOBack && !StaticStore.forAPLIBack && !StaticStore.forAPLOBack
				&& !StaticStore.isInbox && StaticStore.indexCtr > 0
				&& !formName.equals("SMSCOMP") && !((title.equals("Delete Beneficiary")) && (StaticStore.midlet.recMsgType.equals("BD"))) && !StaticStore.forBillerRegistration){
			if (StaticStore.isAirline) {
				StaticStore.isAirline = false;
			}
			StaticStore.isBack = true;
			StaticStore.indexCtr -= 1;
			int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
			int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];
			Intent myIntent = StaticStore.midlet
					.getList(context2, indexForBack,
							indexForSelectedBack);

			StaticStore.midlet.startFragment(context2, myIntent);
			return "true";
		}else if(  formName.equals("SMSCOMP")&& !StaticStore.isInbox){
			if(StaticStore.IsPermitted && StaticStore.IsPersonalInfoGot && !StaticStore.enableHome) 
			{
				Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
	    		StaticStore.midlet.startFragment(context2,myIntent);
			}
			else{
				if(StaticStore.isAirline){
					StaticStore.isAirline = false;
				}
				StaticStore.isBack = true;	    	
				StaticStore.indexCtr -= 1;	    	
				StaticStore.LogPrinter('i',">>>>>>>came to if");
				StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>"+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
				int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
				int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
				Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
				StaticStore.midlet.startFragment(context2, myIntent);
			}
			return "true";
		} else if(  title.equals("Communication Mode") && !StaticStore.isInbox ){
			StaticStore.midlet.startFragment(context2, StaticStore.midlet.getHome(context2));
			return "true";
		}else if(  StaticStore.midlet.recMsgType.equals("PE")&& !StaticStore.isInbox){
			Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
    		StaticStore.midlet.startFragment(context2,myIntent);
    		return "true";
		}else if(  StaticStore.midlet.recMsgType.equals("AC") && !StaticStore.isInbox){
			StaticStore.indexCtr = 2;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getAirlineMenu(context2));
			return "true";
		}else if(  StaticStore.midlet.recMsgType.equals("MO") && !StaticStore.isInbox){
			StaticStore.indexCtr = 2;
			StaticStore.midlet.startFragment(context2,  StaticStore.midlet.getIMPSOptionMenu(context2));
			return "true";
		}else if(  formName.equals("restart")||(!StaticStore.enableHome && title.equals("Mobile Number Validation")) && !StaticStore.isInbox ) { //||title.equals("Reset Password")|| title.equals("Message")|| title.equals("Change mPIN")
			if(StaticStore.isCommModeSelected == true){
				Intent myIntent = new Intent(context2,GridScreenViewActivation.class);
	    		StaticStore.midlet.startFragment(context2,myIntent);
	    		return "true";
			}else{
				return "exit";
			}
		}else if(((formName.equals("DEFAULT")) || (formName.equals("Error_in_resp")) || (formName.equals("HTTPERROR"))) && !StaticStore.isInbox && !StaticStore.forAPDOBack && !StaticStore.forAPLIBack && !StaticStore.forAPLOBack){
			if(!StaticStore.IsPermitted){
				StaticStore.midlet.startFragment(context2,new Intent(StaticStore.midlet.initiateUserOption(context2)));
			}else{
				if(StaticStore.indexCtr > 0){
					StaticStore.indexCtr -= 1;
					StaticStore.isBack = true;	    	
					StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>.."+StaticStore.indexCtr+"<<<<<<<<<<<<<<"+StaticStore.listIndexArray[StaticStore.indexCtr]+">>>>>>>>"+StaticStore.selectedIndexArray[StaticStore.indexCtr]);
					int indexForBack = StaticStore.listIndexArray[StaticStore.indexCtr];
					int indexForSelectedBack = StaticStore.selectedIndexArray[StaticStore.indexCtr];			
					Intent myIntent = StaticStore.midlet.getList(context2,indexForBack,indexForSelectedBack);
					StaticStore.midlet.startFragment(context2,myIntent);
					StaticStore.indexCtr -= 1;
				}else{
					StaticStore.midlet.startFragment(context2,StaticStore.midlet.getHome(context2));
				}
			}
			return "true";//tab super.onKeyDown(keyCode, event);
		}else if(StaticStore.forBillerRegistration){
			Intent myIntent = StaticStore.midlet.getCategoryList(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
		}else if(StaticStore.forAPDOBack && !StaticStore.isInbox){
			StaticStore.forAPDOBack = false;
			Intent myIntent = StaticStore.midlet.getDeposits(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
		}else if(StaticStore.forAPLIBack && !StaticStore.isInbox){
			StaticStore.forAPLIBack = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
		}else if(StaticStore.forAPLOBack && !StaticStore.isInbox){
			StaticStore.forAPLOBack = false;
			Intent myIntent = StaticStore.midlet.get_AccountsMenu(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			return "true";
		}else if(StaticStore.isInbox){
			StaticStore.isInbox = true;
			StaticStore.midlet.responseMessages = RmsStore.readInboxRecordStore(RmsStore.parsedRecords, StaticStore.midlet.responseMessages);
			if(StaticStore.midlet.responseMessages == null){
			StaticStore.ToastDisplay(context2,"Message Inbox is empty");
			}else{
			Intent myIntent = StaticStore.midlet.get_ResponseInbox(context2);
			StaticStore.midlet.startFragment(context2,myIntent);
			}
			return "true";
		}else {
			StaticStore.LogPrinter('i',"Display Default else back");
			return "super";//tab super.onKeyDown(keyCode, event);
		}
		}else{
			StaticStore.midlet.getsessionTimeOut(context2);
			return "true";
		}

	}
}