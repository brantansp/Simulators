package com.fss.tmb;


import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
//comment for use
public class mPAY{
	String classname="",prevfrag="";
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	public   int[] enableTXN = null;
    public String[] responseMessages        =   null;
    public String billNotifyMessage = "";
    private String msg = "";
    public String[] responseInboxMessages  =   null;
    public boolean  pauseApplication; //isAutomaticallyStarted
    public boolean stopSplash = false;
    public boolean firstTimeLoading;
    public boolean inResponseInbox = false;
    public boolean isUsedForBack   = false;
    public boolean isImageTextList   = false;
    public int[] imagesListArray = null;
    public String rechargeMinAmount;
	public String rechargeMaxAmount;
    public RechargeDynamicInputs rechargeDynamicInputs;
    public boolean isPasswordEntered;
    public boolean isRetry;
    public boolean startedAutomatically, isProcessing;
    private boolean inAirlinePayment = false ,inReprint = false;
    public boolean moreUnRegBillers = false,moreFlag = false;
    public boolean neftPaymentFlag,neftDeregFlag,partnerPaymentFlag,partnerDergFlag,onlineFTPaymentFlag,onlineFTDeregFlag;
    private byte selectedIndex  =   0, settingsSelectedIndex    =   0;
    private byte[] selectedListIndex = new byte[]{0,0,0,0,0,0};
    private boolean isListRetrived = true;
    public String[] secretQues = {"Enter your school name?","Enter your mother maiden name?"};
    public String[][] mobileRegList,billerCategoryList,un_Reg_Billers,un_Reg_Sub_Billers,un_Reg_Pres_Billers;
    public String[] tempDW,tempDX,tempDT,tempmainDT,Sub_CatInput,tempmainN1,tempN1 = null; 
    public String[][] sub_CatId  = null; 
    public String[][] billpayPres;
    public String[][] accRegList;
    public String[][] unRegBillers;
    public String[][] regBillers;
    public String[][] billpayBills;
    public String[][] presenmentBills;
    public String[][] temples;
    public String[][] feedbackList;
    public String[][] loanList;
    public String[][] tnebList;
    public String[][] cocTaxList;
    public String[][] ifpList;
    public String[][] templeSchemes;
    public String[][] templeSubSchemes;
    public String[][] showTimings;
    public String[][] movieVenue;
    public String[][] movies;
    public String[][] movieDates;
    public String[][] moviePrice;
    public String[][] regInstitutes;
    public String[][] regInstitutes4Dereg;
    public String[][] movieOrderDetails;
    public String[][] enableTransaction;
    public String[][] neftRegList;
    public String[] preLoginShortDescList;
	public String[] preLoginLongDescList;
	public String[] postLoginShortDescList;
	public String[] postLoginLongDescList;
    public String[][] partnerRegList;
    public String[][] onlineFTRegList;
    public String[][] regIMPSAccs;
    public String[][] withinBenDeregList,withinBenMobList,withinBenAccList;
    public String[][] billpayNotifyDet;
    public String[][] billpayCustMneName;
    public String[][] withInBankBenDetails;
    public String[][] neftBenDetails;
    public String[][] m2mAccounts;
    public String venue,cinema,date,region,language,noOfTickets,showTime;
    public String instId,rollNoLabel,rollNo,name,hostelId,instCategoryId,instSubCategoryId;
    public String airSource,airDestination,airJourneyDate,airTotalTickets,airTotalAdults,airTotalChildren,airTotalInfants,
            airFlightClass,airJourneyTime;
    public boolean hostelFlag,categoryFlag,subCategoryFlag,presenmentFlag;
    public String instSearchStr;
    public String templeSearchStr,templeID,templeSchemeID,templeName,schemeName,subSchemeName,templeDate;
    public String txnID;
    public String[][] tarifPlans;
    public String[][] institutions;
    public String[][] instBills123;
    public String[][] instHostelTutionList;
    public String[][] instCatagories;
    public String[][] instSubCatagories;
    public String[] instCatType;
    public String[][] bankList;
    public String[][] offer2OList;
    public String[][] offer3OList;
    public String[][] offer4OList;
    public String[][] offer5OList;
    public String[] dynamicDisplayableArr;
    public String[] dynamicDisplayableOffersArr;
    public String[] dynamicBillpayRefArray;
    public String[][] dynamiBillArr = null;
    /*This array is used to store the all biller category.because the biller array is assigned to Dynamic Canvas class */
    public String[] billerCategory;
    public String[][] instBillDetails;
    public String[] AirlineId, AirlineNames;
    public String[][] airlineArray;
    public String[][] airlineBooking;
    public String[][] transactionHistoryList;
    public String[][] inwardChequeStatusList;
    public String[][] outwardChequeStatusList;
    public String feesPaySearchInst;
    public String feesPaySearchInstInputFields;
    public String feesPaySearchInstN100Res;
    public String[][] feesPayN100Details;
    public String[][] collegeDetailsList;
    public String[] instConfirmDetailsList;
    public String[][] feesPaySearchInstInputFieldsList;
    public String[] N400DetailsList;
    public String[][] collegeUniqueDetailsList;
    
    /*B2B Values Starts here*/
    public String[][]  unRegisteredCorpLists;   // First Column  ==> Corporate ID, Second Column  ==> Sub-Corporate Flag
    public String[][]  unRegisteredSubCorpLists;// First Column  ==> Corporate ID, Second Column  ==> Sub-Corporate ID
    public String[][]  allRegList;
    public String[][]  cftDistributorList;
    public String modeFlag;
    public String displaySearchString;
    public String[] airlineOrderDetail;
    public String[][] atmSearch;
    public String[][] branchSearch;
    public String[][] eLobbySearch;
    public String[][] iCashStatus;
    public String[][] iCashForgetPin;
    public String[][] iCashCancel;
    public String[][] IRCTC_CardList;
    public String[][] IRCTCCardIssue;
    public String IRCTC_CARD_DATA;
    /*B2B Values Ends here*/
    public int maxCnt = 0;
    public int templeMinAmount,templeDenomination;
    public boolean templeDenFlag;
    public boolean isAdhocPayment;
    public int selectedBillIndex;
    private Loading loading;
   // private SMSSend sendSMS;
   // private HttpConnect httpConnect;
    private Thread thread;
    public  String recMsgType = "";
    public String dynamicBillMsg = "";
    public String billPayTxnId = "";
    public String movieLoadingMsg = "Fetching theater list, please wait for the response";
    public String emailId = "";
    public DynamicCanvas pubDynCan;
    public String campaignId = " ";
    public String nextStartRecNumber = "";
    //public String cftHeader = "CFT";
    public String cftHeader = "Corporate Fund Transfer";
    public String cftCorpId = "";
    public String pinBranch;
    public String locatorArea;
    public String cftSubCorpId = "";
    public String billerCat,billerState,billerCity;
    public String airlineDispMsg;
    public String ifpMessage;
    public String ifpPaymentMsg = "";
    public String ifscBankSearchString,ifscBranchSearchString,ifscBankName,ifscLocationName;
	public String p2unumber, p2uaccntype, p2ubankname;
    public String balEnqAccNo = "";        
    public String[][] depositAccountBalanceList; 
    public String[] selectedAccNumberForDepositAccBal; 
    public static String selectedAccNumberForDepositAccBalSave = ""; 
    public String[][] loanBalanceList; 
    public String[] selectedAccNumberForLoanBal; 
    public static String selectedAccNumberForLoanBalSave = ""; 
    
    public short[][] strColors   =   new short[][]{
        {0, 0, 0},          //  Black Grid Square & Responses Font Everywhere
        {255, 255, 255},    //  White Font for Getting Input
        {255, 255, 255},    //  White Whole BackGround Color
        {252, 152, 50},     //  Orange Get Input, Response Line By Line Heading Surrounding Color
        {0, 0, 0},          //  Heading Font Everywhere
        {206, 103, 0},      //  Darker Color for TXN ID Heading
        {0, 0, 0}           //  Black Font for Getting Input Screen Caption
    };
    
    public int minAmt=0;
    public int maxAmt=0;
    public String[][] IFSCList;
    public String[][] locationList;
    public String[][] CardhotlistDet;
    public String[][] ifscArray;
    Intent mPayIntent;   
 //   RespectiveScreen resp = new RespectiveScreen(this);
	public boolean smsSend;
	public String[][] vmtRegList;
	public String[][] P2UFTRegList;
	public String[][] newProduct;
	public String[][] impsDetails;
	public String[][] operatorList;
	public String[][]  neftDetails;
	public String[][] vmtDetails;
	public String[] rechargeCategory;
	public String[] rechargeCategoryId;
	public String BillerB300Msg="";
	public String m2mSelectedAccNumber = null;
	public boolean initiatedCustomer=false;
	public String enableFlag;

	   public String[][] onlineAccPaymentList;
	    public String[][] onlineAccDeRegList;
	    public String[][] onlineAccDetailsList;
	    public String[][] onlineAccStatList;
		public ListObject listObject;
		public String[][] operatorDetails;
		public String topUpIndicator = "";
		public String[][] accTypeArr;
		public String[] BillerB300;
		public String CATID7T00;
		public String TrnID_7T00;
		public String rechargelabels;
		private static String[] staticLookup = new String[]
		                                                  {"0000","0001","0010","0011","0100","0101","0110","0111",
		                                                   "1000","1001","1010","1011","1100","1101","1110","1111"};

		public Boolean getsessionTimeOut(final Context context){
			 StaticStore.LogPrinter('i', "System.currentTimeMillis() --> "+System.currentTimeMillis());
			 StaticStore.LogPrinter('i', "StaticStore.currentTimeFromIdle"+StaticStore.currentTimeFromIdle);
			 StaticStore.LogPrinter('i', "StaticStore.IsPermitted --> "+StaticStore.IsPermitted);
			 StaticStore.LogPrinter('i', "Time Difference ==> "+(System.currentTimeMillis() - StaticStore.currentTimeFromIdle) );
			AlertDialog alertDialog;
			if(((System.currentTimeMillis() - StaticStore.currentTimeFromIdle) > StaticStore.totalTimeOutTime)  && (StaticStore.enableHome && StaticStore.IsPermitted)){//StaticStore.totalTimeOutTime){
				StaticStore.currentTimeFromIdle = System.currentTimeMillis();
				alertDialog = new AlertDialog.Builder(context).create();
				alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				alertDialog.setTitle("Alert Message !!!");
				alertDialog.setMessage("Session Expired; Please login again");
				alertDialog.setCancelable(false);
				alertDialog.setIcon(R.drawable.tick);

    			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					dialog.cancel();
    					storeLogggedIn_DateTime();
    					StaticStore.midlet.startFragment(context,new Intent(context,GridScreenViewActivation.class));	

    				}
    			});
    			
    			alertDialog.show(); 
    		return false;
				
			}else{
				StaticStore.currentTimeFromIdle = System.currentTimeMillis();
				return true;
			}
		}
		
		private void storeLogggedIn_DateTime() {
			SharedPreferences prefs = StaticStore.context.getSharedPreferences(MY_PREFS_NAME, 0);
			String LastLoggedInDateTime = prefs.getString("LastLoggedIn", null);
			if( LastLoggedInDateTime != null ) {
				String currentDateTimeString = StaticStore.lastLoginTime;
				SharedPreferences.Editor editor = StaticStore.context.getSharedPreferences(MY_PREFS_NAME, 0).edit();
				editor.putString("LastLoggedIn", currentDateTimeString ) ;
				editor.commit();
			}else{
				String currentDateTimeString = StaticStore.lastLoginTime;
				SharedPreferences.Editor editor = StaticStore.context.getSharedPreferences(MY_PREFS_NAME, 0).edit();
				editor.remove("LastLoggedIn") ;
				editor.commit() ;
				editor.putString("LastLoggedIn",  currentDateTimeString ) ;
				editor.commit() ;
			}
		}

		
    public Intent get_AccountsMenu(Context context) {
        isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_balance_enquiry,R.drawable.li_ministatement,R.drawable.li_deposit_account_balance,R.drawable.li_loan_balance,R.drawable.li_transaction_history,R.drawable.li_cheque_status,R.drawable.li_cheque_status};
    	String[] menuItem = { "Balance Enquiry", "Mini Statement", "Deposit Account Balance", "Loan Balance", "Transaction History", "Inward Cheque Status", "Outward Cheque Status"};
    	mPayIntent = new Intent(context, ListSelection.class);
       	mPayIntent.putExtra("listIndex", 121);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Accounts");
       	mPayIntent.putExtra("more", false);
       	mPayIntent.putExtra("isImageTextList", true);
       return mPayIntent;
    }
    
    public Intent get_OtherServicesMenu(Context context) {
//    	StaticStore.indexCtr = 0;
    	isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_stopcheque ,R.drawable.li_cheque_status,R.drawable.li_card_hotlist,R.drawable.li_transaction_history};// ,R.drawable.li_irctc_card,R.drawable.li_cash_on_mobile
    	  	
    	String[] menuItem = {"Stop Cheque","Cheque Status","Card Hotlist","Transaction History"};//,"IRCTC Prepaid Card","eCash"
    	mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 122);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Other Services");
       	   mPayIntent.putExtra("more", false);
       	mPayIntent.putExtra("isImageTextList", true);
       return mPayIntent;
    }

    public Intent getPayRegList(Context context) {
    	isImageTextList = true;
	    imagesListArray = new int[] {R.drawable.li_myownaccounts,R.drawable.li_withinbank,R.drawable.li_otherbank};
	  	String[] menuItem = { "My Own Accounts", "Fund Transfer - Within TMB", "Fund Transfer - Other Bank" };
        mPayIntent = new Intent(context, ListSelection.class);
       	mPayIntent.putExtra("listIndex", 8);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Transfer");
       	mPayIntent.putExtra("isImageTextList", true);
       	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }

    public Intent get_Settings(Context context) {
        isImageTextList = true;
    	String[] menuItem;
    	if(StaticStore.istablet){
    		   imagesListArray = new int[] {R.drawable.li_account_fetch,R.drawable.li_save_msg_in_inbox,R.drawable.li_change_app_pwd,R.drawable.li_inbox,R.drawable.li_contact_us,R.drawable.li_configure_emailid,R.drawable.li_dashboard};
           menuItem = new String[]{"Account Fetch","Save Message in Inbox","Change Login Password","Messages","Contact Us","Configure/Update E-mail ID","Personalize Dashboard"};
    	 }else{
    			imagesListArray = new int[] {R.drawable.li_account_fetch,R.drawable.li_save_msg_in_inbox,R.drawable.li_change_app_pwd,R.drawable.li_contact_us,R.drawable.li_configure_emailid,R.drawable.li_dashboard};
        		menuItem = new String[]{ "Account Fetch","Save Message in Inbox","Change Login Password","Contact Us","Configure/Update E-mail ID","Personalize Dashboard"};
    	}
    	mPayIntent = new Intent(context, ListSelection.class);
       	mPayIntent.putExtra("listIndex", 12);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Settings");
       	mPayIntent.putExtra("more", false);
       	mPayIntent.putExtra("isImageTextList", true);
       return mPayIntent;
    }

    public Intent getLocatorList(Context context) {
    	  
    	String[] menuItem = { "ATM", "Branch" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 123);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Locator");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }

    public Intent getFixedDeposit(Context context) {
  	  
    	String[] menuItem = { "Balance Enquiry ", "View FD", "Sweep-In",
				"Sweep-Out", "View Interest Rate" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 136);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Fixed Deposit");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
    
    public Intent get_ReChargeBeneficiaryListDeregistrationList(Context context,int index) {
    	StaticStore.rechargeSelcetedCategoryID = StaticStore.midlet.rechargeDynamicInputs.recharge.getUniqueCategoryID(StaticStore.forRechargeSelectedIndex);
    	String menuArr[] = StaticStore.midlet.rechargeDynamicInputs.getBeneficiaryListByCatID(StaticStore.rechargeSelcetedCategoryID);
		String[] menuItem = new String[menuArr.length];
        for (int i = 0,j=0; i < menuItem.length; i++,j++) {
		  menuItem[i] = menuArr[j];
	    }
        mPayIntent = new Intent(context, ListSelection.class);
        if(index == 251){
            mPayIntent.putExtra("listIndex", 251);
            mPayIntent.putExtra("menuItem", menuItem);
        	mPayIntent.putExtra("listHeader", "Beneficiary List");
        	mPayIntent.putExtra("more", false);
        }else if(index == 252){
        	if(menuItem.length == 1){
            	StaticStore.forRechargeBeneDetailsDel = true;
            }else{
            	StaticStore.forRechargeBeneDetailsDel = false;
            }
    	    mPayIntent.putExtra("listIndex", 252);
    	    mPayIntent.putExtra("menuItem", menuItem);
        	mPayIntent.putExtra("listHeader", "Beneficiary Deregistration");
        	mPayIntent.putExtra("more", false);
        }
    	return mPayIntent;
    }

    public Intent getLoyality(Context context) {
    	  
    	String[] menuItem = { "Redemption" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 125);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Loyality");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }

    public Intent getMobileToCard(Context context) {
  	  
		String[] menuItem = { "VISA Card Bill Payment" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 126);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Visa Card Bill Payment");//changed by S
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }

   
    public Intent getVMTList(Context context) {
    	  
    	String[] menuItem = { "Beneficiary Registration", "Visa Card Payment",
				"Delete Beneficiary","Beneficiary Details" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 127);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Visa Card Bill Payment");//changed by S
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
    public Intent getNewProduct(Context context) {//same list index for loyality also
	String[] menuItem = new String[newProduct.length];
		for (int i = 0; i < newProduct.length; i++) {
			menuItem[i] = newProduct[i][1];
			
		}
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 125);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Product Interested");
       	 if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
       return mPayIntent;
    }
    
    public Intent getImpsDetails(Context context) {
    	
    	String[] menuItem = new String[impsDetails.length];
		for (int i = 0; i < impsDetails.length; i++) {
			menuItem[i] = impsDetails[i][0];
		}
		   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 101);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Beneficiary List");// mid: 12745 Select Beneficiary
           	 if(moreFlag){
          	   mPayIntent.putExtra("more", true);
            }else{
            	mPayIntent.putExtra("more", false);
            }
          return mPayIntent;
        }
    
    public Intent getOperatorDetails(Context context,String labelDetails) {
    	
    	String[] menuItem = new String[operatorDetails.length];
		for (int i = 0; i < operatorDetails.length; i++) {
			menuItem[i] = operatorDetails[i][1];
		}
		   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 229);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Select Operator");
       	mPayIntent.putExtra("labelDetails", labelDetails);
           	 if(moreFlag){
          	   mPayIntent.putExtra("more", true);
            }else{
            	mPayIntent.putExtra("more", false);
            }
           return mPayIntent;
        }
    
    public Intent getNeftDetails(Context context) {
    	String[] menuItem = new String[neftDetails.length];
    	for (int i = 0; i < neftDetails.length; i++) {
    		menuItem[i] = neftDetails[i][0];

    	}
    	mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 210);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader", "Beneficiary List");//changed by S mid:12772
    	if(moreFlag){
    		mPayIntent.putExtra("more", true);
    	}else{
    		mPayIntent.putExtra("more", false);
    	}
    	return mPayIntent;
    }
  
  
  public Intent getVmtDetails(Context context) {
		String[] menuItem = new String[vmtDetails.length];
		for (int i = 0; i < vmtDetails.length; i++) {
			menuItem[i] = vmtDetails[i][0];

		}
	    	   mPayIntent = new Intent(context, ListSelection.class);
	       	   mPayIntent.putExtra("listIndex", 102);
	       	   mPayIntent.putExtra("menuItem", menuItem);
	       	   mPayIntent.putExtra("listHeader", "Beneficiary List");//changed by S
	       	 if(moreFlag){
	      	   mPayIntent.putExtra("more", true);
	        }else{
	        	mPayIntent.putExtra("more", false);
	        }
	       return mPayIntent;
	    }
  
    public Intent getDoorStepBanking(Context context) {
		String[] menuItem = { "Cash PickUp", "Cash Delivery", "Cheque PickUp" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 128);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Door Step Banking");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
   
    public Intent getBillPayMenu(Context context) {
    	  
    	String[] menuItem = { "Bill Presentment and Payment", "Instant Payment" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 4);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Bill Payments");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
	
    public Intent getTicketingMenu(Context context) {
  	  
    	String[] menuItem = { "Airline Ticketing", "Movie Ticketing" };
    	   mPayIntent = new Intent(context, ListSelection.class);
       	   mPayIntent.putExtra("listIndex", 130);
       	   mPayIntent.putExtra("menuItem", menuItem);
       	   mPayIntent.putExtra("listHeader", "Ticketing");
       	   mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
	

	
    public Intent getMobileRecharge(Context context) {
		String[] menuItem = { "Recharge", "Recharge Status Enquiry" };
		mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 131);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Mobile Recharge");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}
    public Intent getLogOnlyMenu(Context context) {
    	  isImageTextList = true;
          imagesListArray = new int[] {R.drawable.li_card_hotlist,R.drawable.li_cheque_status,R.drawable.li_stopcheque,R.drawable.li_cheque_book,R.drawable.li_debit_card,R.drawable.li_credit_card,R.drawable.li_internet_banking,R.drawable.li_internet_banking_pwd,R.drawable.li_debit_card_pin,R.drawable.li_loan};
          String menuItems[] = {"Hotlist Debit Card","Cheque Status","Stop Cheque","Cheque Book","Debit Card","Credit Card",
        		                "eConnect SignOn password","eConnect Transaction Password","Debit Card PIN","Loan"};
          mPayIntent = new Intent(context, ListSelection.class);
    	  mPayIntent.putExtra("listIndex", 3);
    	  mPayIntent.putExtra("menuItem", menuItems);
    	  mPayIntent.putExtra("listHeader", "Requests");
    	  mPayIntent.putExtra("isImageTextList", true);
    	  mPayIntent.putExtra("more", false);
          return mPayIntent;
	}
    public Intent getSmsType(Context context) {
		String[] menuItem = {
				"Short Code (" + StaticStore.shortCodeNumber + ")",
				"VMN (" + StaticStore.VMN_Number + ")" };
		   StaticStore.selectedIndex = 0;
		mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 77);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Change Service No.");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}
    public Intent get_DepositProduct(Context context) {
			String[] menuItem = {"Short Term(Up to 1 yr)","Long Term(B/W 1 Yr to 10 Yr)","Special Tenor"};
			mPayIntent = new Intent(context, ListSelection.class);
	       	   mPayIntent.putExtra("listIndex", 200);
	       	   mPayIntent.putExtra("menuItem", menuItem);
	       	   mPayIntent.putExtra("listHeader", "Deposit Product");
	       	   mPayIntent.putExtra("more", false);
	       return mPayIntent;
		}
	        
    public Intent get_LongTermDeposit(Context context) {
			String[] menuItem = {"1 Year","2 Years","3 Years","4 Years","5 Years","6 Years","7 Years","8 Years","9 Years","10 Years"};
			
			mPayIntent = new Intent(context, ListSelection.class);
	       	   mPayIntent.putExtra("listIndex", 201);
	       	   mPayIntent.putExtra("menuItem", menuItem);
	       	   mPayIntent.putExtra("listHeader", "Long Term Deposit");
	       	   mPayIntent.putExtra("more", false);
	       return mPayIntent;
		}
	         
    public Intent get_SpecialTenor(Context context) {
			String[] menuItem = {"200 Days","400 Days","999 Days"};
	
			mPayIntent = new Intent(context, ListSelection.class);
	       	   mPayIntent.putExtra("listIndex", 202);
	       	   mPayIntent.putExtra("menuItem", menuItem);
	       	   mPayIntent.putExtra("listHeader", "Special Tenor");
	       	   mPayIntent.putExtra("more", false);
	       return mPayIntent;
		}
    public Intent get_MaturityInstructions(Context context) {
			String[] menuItem = {"Auto Renew for same Tenor","Credit to my linked Savings / Current"};
		
			mPayIntent = new Intent(context, ListSelection.class);
	       	   mPayIntent.putExtra("listIndex", 203);
	       	   mPayIntent.putExtra("menuItem", menuItem);
	       	   mPayIntent.putExtra("listHeader", "Maturity Instructions");
	       	   mPayIntent.putExtra("more", false);
	       return mPayIntent;
		}
    public Intent get_InterestInstructions(Context context) {
		String[] menuItem = { "Simple Interest (Credit to Account)",
				"Compound Interest (Reinvestment)" };
		
		mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 204);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Interest Instructions");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}

    public Intent get_DepositType(Context context) {
		String[] menuItem = { "NRE", "NRO" };
	
		   mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 206);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Deposit Type");
    	   mPayIntent.putExtra("more", false);
           return mPayIntent;
	}

    public Intent get_NewLongTermDeposit(Context context) {
		String[] menuItem = { "1 Year", "2 Years","3 Years" };

		mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 207);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Deposit Type");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}   
	             
    public Intent geteCash(Context context) {
		String[] menuItem = { "Remittance", "Status", "Forgot PIN" };
			mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 132);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "eCash");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}
	// neft mod
    public Intent get_NeftRegister(Context context){
		String[] menuItem = { "Search and Register", "Register",};
	
		mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 208);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "NEFT Registration");
    	   mPayIntent.putExtra("more", false);
    return mPayIntent;
	}

    public Intent get_ResponseInbox(Context context) {
    	isImageTextList = false;
    	String[] messages;
        String[] messageList;
        String[] messageStatus;
        responseMessages = RmsStore.readInboxRecordStore(RmsStore.parsedRecords, responseMessages);

        if(responseMessages == null)
            messages = new String[0];
        else {
            messages = new String[responseMessages.length];
            messages = responseMessages;
        }

        messageList    =   new String[messages.length];
        messageStatus  =   new String[messages.length];
        
        for(int i = 0 ; i < messages.length; i++) {
            String message = "";
            //Future Use
            message = inboxTitleName(messages[i].substring(1, 3).toUpperCase().trim());
            
            if(messages[i].substring(0, 1).equals("Y")) {
            	messageList[i]  	=   message;
            	messageStatus[i]  	=   "U";
            } else if(messages[i].substring(0, 1).equals("N")) {
            	messageList[i]  	=   message;
            	messageStatus[i]  	=   "R";
            }
        }
        
        try {
StaticStore.LogPrinter('i',"messafefaefaef+"+messageList.length);
        	mPayIntent = new Intent(context, Inbox.class);
      	   	mPayIntent.putExtra("listIndex", 99);
      	   	mPayIntent.putExtra("menuItem", messageList);
      	   	mPayIntent.putExtra("menuStatus", messageStatus);

        } catch(Exception IOExp) {
            IOExp.printStackTrace();
        }
        
       
        responseInboxMessages   =   responseMessages;
        
        return mPayIntent;
    }
    
    
    public void get_RespectiveScreens(Context context , String messageContent){
    	RespectiveScreen resp = new RespectiveScreen();
    	StaticStore.LogPrinter('i',"get_RespectiveScreens ==>"+messageContent);
    	try
    	{
    		if (messageContent.indexOf(";BID:") != -1) {
    			messageContent = messageContent.substring(0, messageContent
    					.indexOf(";BID:"));
    		}
    		StaticStore.LogPrinter('i',">>>>message with BID<<<<<<"+messageContent);
    		recMsgType = messageContent.substring(0, 2);
    		StaticStore.LogPrinter('i',">>>>"+recMsgType);
    		if (messageContent.startsWith("BE") || messageContent.startsWith("MS")
    				|| messageContent.startsWith("PC")
    				|| messageContent.startsWith("FT")
    				|| messageContent.startsWith("FA")
    				|| messageContent.startsWith("AI")
    				|| messageContent.startsWith("R0")
    				|| messageContent.startsWith("AB")
    				|| messageContent.startsWith("CN")
    				|| messageContent.startsWith("P3")
    				|| messageContent.startsWith("P4")
    				|| messageContent.startsWith("BV")
    				|| messageContent.startsWith("1F")
    				|| messageContent.startsWith("2F")
    				|| messageContent.startsWith("CG")
    				|| messageContent.startsWith("AG")
    				|| messageContent.startsWith("LR")
    				|| messageContent.startsWith("PA")
    				|| messageContent.startsWith("OG")
    				|| messageContent.startsWith("BO")
    				|| messageContent.startsWith("OV")) {			
    			mPayIntent = resp.BE_MS_PC_FT_FA_AI_R0_AB_CN_PA_P3_P4_BV_1F_2F_CG_AG_LR_OG_BO_OV(context , messageContent);
    		} else if (messageContent.startsWith("MP")
    				|| messageContent.startsWith("B1")
    				|| messageContent.startsWith("B2")
    				|| messageContent.startsWith("B9")
    				|| messageContent.startsWith("B3")
    				|| messageContent.startsWith("Y3")
    				|| messageContent.startsWith("B4")
    				|| messageContent.startsWith("B5")
    				|| messageContent.startsWith("1B")
    				|| messageContent.startsWith("B6")
    				|| messageContent.startsWith("B8")
    				|| messageContent.startsWith("BZ")
    				|| messageContent.startsWith("DW")
    				|| messageContent.startsWith("DX")
    				|| messageContent.startsWith("DY")
    				|| messageContent.startsWith("2B")
    				|| messageContent.startsWith("DZ")
    				|| messageContent.startsWith("DT")
    				|| messageContent.startsWith("DM")
    				|| messageContent.startsWith("DN")) {
    			mPayIntent = resp.MP_B1_B2_B9_B3_Y3_B4_B5_1B_B6_B8_BZ_DW_DX_DY_2B_DZ_DT_DM_DN(context , messageContent);
    		} else if (messageContent.startsWith("BY")
    				|| messageContent.startsWith("TL")
    				|| messageContent.startsWith("TD")
    				|| messageContent.startsWith("CPCN")
    				|| messageContent.startsWith("M1")
    				|| messageContent.startsWith("M2")
    				|| messageContent.startsWith("M3")
    				|| messageContent.startsWith("M4")
    				|| messageContent.startsWith("ML")
    				|| messageContent.startsWith("MC")|| messageContent.startsWith("R5") || messageContent.startsWith("R6")|| messageContent.startsWith("R7")){
    			mPayIntent = resp.BY_TL_TD_CPCN_M1_M2_M3_M4_ML_MC_R5_R6_R7(context , messageContent);
    		} else if (messageContent.startsWith("IS")
    				|| messageContent.startsWith("IR")
    				|| messageContent.startsWith("IL")
    				|| messageContent.startsWith("I1")
    				|| messageContent.startsWith("I2")
    				|| messageContent.startsWith("I3")
    				|| messageContent.startsWith("I4")
    				|| messageContent.startsWith("I5")
    				|| messageContent.startsWith("ID")
    				|| messageContent.startsWith("IP")
    				|| messageContent.startsWith("PI")
    				|| messageContent.startsWith("PM")
    				|| messageContent.startsWith("RC")
    				|| messageContent.startsWith("N1")
    				|| messageContent.startsWith("N2")
    				|| messageContent.startsWith("N4")
    				|| messageContent.startsWith("N5")) {			
    			mPayIntent = resp.IS_IR_IL_I1_I2_I3_I4_I5_ID_IP_RC_PI_PM_N1_N2_N4_N5(context , messageContent);
    		} else if (messageContent.startsWith("IC")
    				|| messageContent.startsWith("MU")
    				|| messageContent.startsWith("NA")
    				|| messageContent.startsWith("SY")
    				|| messageContent.startsWith("P2")
    				|| messageContent.startsWith("FQ")
    				|| messageContent.startsWith("FP")
    				|| messageContent.startsWith("SA")
    				|| messageContent.startsWith("1L")
    				|| messageContent.startsWith("LW")
    				|| messageContent.startsWith("AS")
    				|| messageContent.startsWith("AL")
    				|| messageContent.startsWith("A1")
    				|| messageContent.startsWith("EL")) {
    			StaticStore.LogPrinter('i',"<><><><>+++"+messageContent);
    			mPayIntent = resp.IC_MU_NA_SY_P2_FQ_FP_1L_LW_SA_AS_A1_AL_EL(context , messageContent);
    		} else if (messageContent.startsWith("AR")
    				|| messageContent.startsWith("AC")
    				|| messageContent.startsWith("R1")
    				|| messageContent.startsWith("R2")
    				|| messageContent.startsWith("E5")
    				|| messageContent.startsWith("E6")
    				|| messageContent.startsWith("Q1")
    				|| messageContent.startsWith("Q2")
    				|| messageContent.startsWith("SU")
    				|| messageContent.startsWith("NU")
    				|| messageContent.startsWith("2O")
    				|| messageContent.startsWith("3O")
    				|| messageContent.startsWith("4O")
    				|| messageContent.startsWith("5O")
    				|| messageContent.startsWith("Q3")) {
    			mPayIntent = resp.AR_AC_R1_R2_E5_E6_Q1_Q2_Q3_SU_NU_2O_3O_4O_5O(context , messageContent);
    		} else if (messageContent.startsWith("Q4")
    				|| messageContent.startsWith("Q5")
    				|| messageContent.startsWith("Q6")
    				|| messageContent.startsWith("Q7")
    				|| messageContent.startsWith("Q8")
    				|| messageContent.startsWith("Q9")
    				|| messageContent.startsWith("QA")
    				|| messageContent.startsWith("QF")
    				|| messageContent.startsWith("T1")
    				|| messageContent.startsWith("T2")
    				|| messageContent.startsWith("TF")) {
    			mPayIntent = resp.Q4_Q5_Q6_Q7_Q8_Q9_QA_QF_T1_T2_TF(context , messageContent);
    		} else if (messageContent.startsWith("T3")
    				|| messageContent.startsWith("T4")
    				|| messageContent.startsWith("T5")
    				|| messageContent.startsWith("F1")
    				|| messageContent.startsWith("F4")
    				|| messageContent.startsWith("F3")
    				|| messageContent.startsWith("F2")
    				|| messageContent.startsWith("F6")
    				|| messageContent.startsWith("RM")
    				|| messageContent.startsWith("C1")) {
    			mPayIntent = resp.T3_T4_T5_F1_F4_F3_F2_F6_RM_C1(context , messageContent);
    		} else if (messageContent.startsWith("C2")
    				|| messageContent.startsWith("RA")
    				|| messageContent.startsWith("N1")
    				|| messageContent.startsWith("1N")
    				|| messageContent.startsWith("DN")
    				|| messageContent.startsWith("N2")
    				|| messageContent.startsWith("CS")
    				|| messageContent.startsWith("SS")
    				|| messageContent.startsWith("SR")
    				|| messageContent.startsWith("SP")) {
    			mPayIntent = resp.C2_RA_N1_1N_DN_N2_CS_SS_SR_SP(context , messageContent);
    		} else if (messageContent.startsWith("PL")
    				|| messageContent.startsWith("PS")
    				|| messageContent.startsWith("SI")
    				|| messageContent.startsWith("DR")
    				|| messageContent.startsWith("CF")
    				|| messageContent.startsWith("Z4")
    				|| messageContent.startsWith("ZP")
    				|| messageContent.startsWith("Z6")
    				|| messageContent.startsWith("Z7")) {
    			mPayIntent = resp.PL_PS_SI_DR_CF_Z4_ZP_Z6_Z7(context , messageContent);
    		} else if (messageContent.startsWith("Z3")
    				|| messageContent.startsWith("Z5")
    				|| messageContent.startsWith("Y100")
    				|| messageContent.startsWith("1Y")
    				|| messageContent.startsWith("2Y")
    				|| messageContent.startsWith("3Y")
    				|| messageContent.startsWith("CA")
    				|| messageContent.startsWith("P5")
    				|| messageContent.startsWith("S1")
    				|| messageContent.startsWith("S2")) {
    			mPayIntent = resp.Z3_Z5_Y100_1Y_2Y_3Y_CA_P5_S1_S2(context , messageContent);
    		} else if (messageContent.startsWith("3S")
    				|| messageContent.startsWith("BX")
    				|| messageContent.startsWith("BW")
    				|| messageContent.startsWith("FC")
    				|| messageContent.startsWith("FS")
    				|| messageContent.startsWith("4S")
    				|| messageContent.startsWith("Z8")
    				|| messageContent.startsWith("GT")
    				|| messageContent.startsWith("CM")
    				|| messageContent.startsWith("DG")) {
    			mPayIntent = resp.BX_3S_BW_FC_FS_4S_Z8_GT_CM_DG(context , messageContent);
    		} else if (messageContent.startsWith("E3")
    				|| messageContent.startsWith("E4")
    				|| messageContent.startsWith("AA")
    				|| messageContent.startsWith("DA")
    				|| messageContent.startsWith("Z9")
    				|| messageContent.startsWith("SC")
    				|| messageContent.startsWith("EC")
    				|| messageContent.startsWith("SE")
    				|| messageContent.startsWith("PF")
    				|| messageContent.startsWith("FF")
    				|| messageContent.startsWith("L6")
    				|| messageContent.startsWith("TH")
    				|| messageContent.startsWith("FE")
    				|| messageContent.startsWith("LI")
    				|| messageContent.startsWith("LO")) {
    			mPayIntent = resp.E3_E4_AA_DA_Z9_SC_EC_SE_PF_FF_L6_FE_TH_LI_LO(context , messageContent);

    		} else if (messageContent.startsWith("CE")
    				|| messageContent.startsWith("CC")
    				|| messageContent.startsWith("W1")
    				|| messageContent.startsWith("W2")
    				|| messageContent.startsWith("W3")
    				|| messageContent.startsWith("IM")
    				|| messageContent.startsWith("QI")
    				|| messageContent.startsWith("2L")
    				|| messageContent.startsWith("2D")
    				|| messageContent.startsWith("3L")
    				|| messageContent.startsWith("K1") ||
    				messageContent.startsWith("K2") || messageContent.startsWith("K3") || messageContent.startsWith("K4") || messageContent.startsWith("K5") || 
    				messageContent.startsWith("6L") || messageContent.startsWith("6D") || messageContent.startsWith("4W") || messageContent.startsWith("K9") ) {
    			mPayIntent = resp.CE_CC_W1_W2_W3_IM_QI_2L_2D_3L_K1_K2_K3_K4_K5_6L_6D_4W_K9(context , messageContent);
    		} else if (messageContent.startsWith("3D")
    				|| messageContent.startsWith("D5")
    				|| messageContent.startsWith("D6")
    				|| messageContent.startsWith("S6")
    				|| messageContent.startsWith("W4")
    				|| messageContent.startsWith("AF")
    				|| messageContent.startsWith("4L")
    				|| messageContent.startsWith("V1")
    				|| messageContent.startsWith("V2")
    				|| messageContent.startsWith("V3")
    				|| messageContent.startsWith("L1")) {
    			mPayIntent = resp.D5_3D_D6_S6_W4_AF_4L_V1_V2_V3_L1(context , messageContent);
    		} else if (messageContent.startsWith("V4")
    				|| messageContent.startsWith("V5")
    				|| messageContent.startsWith("L9")
    				|| messageContent.startsWith("G1")
    				|| messageContent.startsWith("G2")
    				|| messageContent.startsWith("G3")
    				|| messageContent.startsWith("G4")
    				|| messageContent.startsWith("L3")
    				|| messageContent.startsWith("L7")
    				|| messageContent.startsWith("L8")
    				|| messageContent.startsWith("G5")
    				|| messageContent.startsWith("G6")
    				|| messageContent.startsWith("G7")
    				|| messageContent.startsWith("G8")
    				|| messageContent.startsWith("G9")
    				|| messageContent.startsWith("IB")
    				|| messageContent.startsWith("BP")
    				|| messageContent.startsWith("DP")
    				|| messageContent.startsWith("DO")
    				|| messageContent.startsWith("LF")
    				|| messageContent.startsWith("DB")
    				|| messageContent.startsWith("LA")
    				|| messageContent.startsWith("LE")
    				|| messageContent.startsWith("ST")) {
    			mPayIntent = resp.V4_V5_L9_L3_L7_L8_G1_G2_G3_G4_G5_G6_G7_G8_G9_IB_BP_DP_DO_LF_DB_LA_LE_ST(context , messageContent);

    		} else if (messageContent.startsWith("NS")
    				|| messageContent.startsWith("L0")
    				|| messageContent.startsWith("HL")
    				|| messageContent.startsWith("HC")
    				|| messageContent.startsWith("L4")
    				|| messageContent.startsWith("NC")
    				|| messageContent.startsWith("OC")
    				|| messageContent.startsWith("RS")
    				|| messageContent.startsWith("PB")
    				|| messageContent.startsWith("CP")
    				|| messageContent.startsWith("MP")
    		) {
    			mPayIntent = resp.NS_HL_HC_L0_L4_NC_OC_RS_PB_CP_MP(context , messageContent);
    		} else if (messageContent.startsWith("1I")
    				|| messageContent.startsWith("2I")
    				|| messageContent.startsWith("3I")
    				|| messageContent.startsWith("2I")
    				|| messageContent.startsWith("7L")
    				|| messageContent.startsWith("M9")
    				|| messageContent.startsWith("7D")
    				|| messageContent.startsWith("NL")
    				|| messageContent.startsWith("4D")
    				|| messageContent.startsWith("QM")
    				|| messageContent.startsWith("QN")
    				|| messageContent.startsWith("5D")
    				|| messageContent.startsWith("5L")
    				|| messageContent.startsWith("QT")
    				|| messageContent.startsWith("MO")) {
    			mPayIntent = resp.MO_1I_2I_3I_7L_7D_NL_4D_5L_5D_QM_QN_QT_M9(context , messageContent);

    		}else if (messageContent.startsWith("GP")
    				||messageContent.startsWith("D9")
    				||messageContent.startsWith("D8")
    				||messageContent.startsWith("V6")
    				||messageContent.startsWith("PE")
    				||messageContent.startsWith("GU")
    				||messageContent.startsWith("1T")
    				||messageContent.startsWith("2T")
    				||messageContent.startsWith("3T")
    				||messageContent.startsWith("4T")
    				||messageContent.startsWith("5T")
    				||messageContent.startsWith("7T")
    				||messageContent.startsWith("8T")
    				||messageContent.startsWith("BD")){
    			mPayIntent = resp.GP_D8_D9_V6_PE_GU_1T_2T_3T_4T_5T_7T_8T_BD(context , messageContent);

    		}else if(messageContent.startsWith("CH") 
    				|| messageContent.startsWith("TP") 
    				|| messageContent.startsWith("TC") 
    				|| messageContent.startsWith("CT")
    				|| messageContent.startsWith("1U")
    				|| messageContent.startsWith("2U")
    				|| messageContent.startsWith("3U")
    				|| messageContent.startsWith("4U")
    				|| messageContent.startsWith("5U")
    				|| messageContent.startsWith("6U")
    				|| messageContent.startsWith("7U")
    				|| messageContent.startsWith("8U")){
    			mPayIntent = resp.CH_CT_TP_TC_1U_2U_3U_4U_5U_6U_7U_8U(context , messageContent);
    		}else{        	
    			//    getDisplay().setCurrent(get_UnknownForm(messageContent));//comment for use
    			StaticStore.ToastDisplay(context,"Unknown Message Received : " + messageContent);
    		}

    	}catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
    		StaticStore.LogPrinter('i'," From TMB mConnect test Invalid Message recived ==> "+messageContent);
    		mPayIntent = resp.ResponceError(context);
    	}
    }
  //comment for use
    public Intent notifyIncomingMessage( Context context , String msg) {
        showScreen( context , msg);
        return mPayIntent;
    }
    
    public String inboxTitleName(String messageContent){
    	
//    	Future Use
    	
    	String message ="Message";
    	
    	if(messageContent.startsWith("N1")){
            message = "Billpay notification";
        }else if(messageContent.startsWith("N2")){
            message = "Fees Payment notification";
        }else if(messageContent.startsWith("Y1")){
			message = "Promotional Campaign";
		} else if (messageContent.startsWith("Z4")) {
			message = "Application Upgrade";
		} else if (messageContent.startsWith("B4")) {
			message = "Add Biller";
		} else if (messageContent.startsWith("N5")) {
			message = "Fees Payment";
		}else if (messageContent.startsWith("B8")) {
			message = "Instant Bill Payment";
		} else if (messageContent.startsWith("K1")) {
			message = "Instant Pay";
		} else if (messageContent.startsWith("1U")) {
			message = "Instant Pay";
		} else if (messageContent.startsWith("3U")) {
			message = "Add Beneficiary";
		} else if (messageContent.startsWith("5U")) {
			message = "Pay Beneficiary";
		} else if (messageContent.startsWith("8U")) {
			message = "Delete Beneficiary";
		}  else if (messageContent.startsWith("QT") || messageContent.startsWith("QM") ||messageContent.startsWith("QN")) {
			message = "Instant Pay";
		} else if (messageContent.startsWith("2D") ||messageContent.startsWith("3D") 
				|| messageContent.startsWith("4D") ||messageContent.startsWith("5D")
				 || messageContent.startsWith("6D") ||messageContent.startsWith("7D")||messageContent.startsWith("8U")) {
			message = "Delete Beneficiary";
		} else if (messageContent.startsWith("CT")) {
			message = "Generate Card";
		} else if (messageContent.startsWith("TC")) {
			message = "Topup";
		}else if (messageContent.startsWith("TH")) {
			message = "Transaction History";
		}else if (messageContent.startsWith("LI")) {
			message = "Inward Cheque Status";
		}else if (messageContent.startsWith("LO")) {
			message = "Outward Cheque Status";
		}else if (messageContent.startsWith("5T")) {
			message = "Recharge";
		}else if (messageContent.startsWith("1I")) {
			message = "Add Merchant";
		}else if (messageContent.startsWith("2I")) {
			message = "Add Merchant";
		} else if (messageContent.startsWith("PI")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("R6")) {
			message = "Mobile Recharge";
		}else if (messageContent.startsWith("TH")) {
			message = "Transaction History";
		}else if (messageContent.startsWith("5T")) {
			message = "Recharge";
		}else if (messageContent.startsWith("BD")) {
			message = "Delete Beneficiary";	
		}else if (messageContent.startsWith("R7")) {
			message = "Mobile Recharge";
		}else if (messageContent.startsWith("HC")) {
			message = "Hotlist Debit Card";
		}else if (messageContent.startsWith("5L")) {
			message = "Delete Beneficiary";
		}else if (messageContent.startsWith("PI")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("3I")) {
			message = "Pay Merchant";
		}else if (messageContent.startsWith("QN")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("QM")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("1T")) {
			message = "Recharge";
		}else if (messageContent.startsWith("QT")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("1U")) {
			message = "Instant Pay";
		}else if (messageContent.startsWith("4U")) {
			message = "Pay Beneficiary";
		}else if (messageContent.startsWith("CH")) {
			message = "Generate Card";
		}else if (messageContent.startsWith("K2") || messageContent.startsWith("K3")) {
			message = "Add Beneficiary";
		}else if (messageContent.startsWith("K4")) {
			message = "Pay Beneficiary";
		}else if (messageContent.startsWith("K5")) {
			message = "Pay Beneficiary";
		}else if (messageContent.startsWith("6L")) {
			message = "Beneficiary DeRegistration";
		}else if (messageContent.startsWith("4W")) {
			message = "Beneficiary Details";
		}else if (messageContent.startsWith("6D")) {
			message = "Delete Beneficiary";
		}else if (messageContent.startsWith("4W")) {
			message = "Beneficiary Details";
		}else if (messageContent.startsWith("1I")) {
			message = "Add Merchant";
		}else if (messageContent.startsWith("7L")) {
			message = "Delete Merchant";
		}else if (messageContent.startsWith("M9")) {
			message = "Merchant Details";
		}else if (messageContent.startsWith("2U")) {
			message = "Add Beneficiary";
		}else if (messageContent.startsWith("7U")) {
			message = "Delete Beneficiary";
		}else if (messageContent.startsWith("6U")) {
			message = "Beneficiary Details";
		}else if (messageContent.startsWith("8U")) {
			message = "Beneficiary DeRegistration";
		}else if (messageContent.startsWith("4U")) {
			message = "Pay Beneficiary";
		}else if (messageContent.startsWith("5U")) {
			message = "Pay Beneficiary";
		}else if (messageContent.startsWith("TP")) {
			message = "Top-up";
		}else if (messageContent.startsWith("5L")) {
			message = "Delete Beneficiary";
		}else if (messageContent.startsWith("3I")) {
			message = "Pay Merchant";
		}else if (messageContent.startsWith("PM")) {
			message = "Pay Merchant";
		}else if (messageContent.startsWith("7D")) {
			message = "Delete Merchant";
		}else if (messageContent.startsWith("7D")) {
			message = "Delete Merchant";
		}else if (messageContent.startsWith("MO")) {
			message = "Generate OTP";
		}else if (messageContent.startsWith("DX")
				|| messageContent.startsWith("DZ")) {
			message = "Instant Bill Payment";
        } else if (messageContent.startsWith("HL") || messageContent.startsWith("HC")){
			message = "Hotlist Debit Card";
        }else if (messageContent.startsWith("DB")) {
			message = "Deposit Account Balance";
        }else if (messageContent.startsWith("LE")) {
			message = "Loan Balance";
        } else if (messageContent.startsWith("DO")) {
        	message = "Deposit";
        } else{
            for(int j = 0 ; j < StaticStore.menuDesc.length; j++) {
                if(StaticStore.menuDesc[j].length > 0 && StaticStore.menuDesc[j][1].substring(2,4).equals(messageContent))
                    message = StaticStore.menuDesc[j][0];
            }
            if(message == null || message.equals("")){
            	message = "Message";
            }
        }
        if(messageContent.substring(1).startsWith("CN00")){
            if(getCharCount(messageContent,';') == 3){
                message = "Shopping";
            }else{
                message = "Payment Only";
            }
        }
        
        return message;
    }
  //comment for use
    public void showScreen(Context context , String gprsString) {
    	try {
    		if(StaticStore.Logwrite){
    		//StaticStore.generateLogsOnSDcard(context,gprsString);
    		}
    		StaticStore.LogPrinter('i',"showScreen Called ===> ");
    		String messageContent = gprsString;
    		boolean notifyNewMessage = true;
    		boolean CorrMsgReceived = true;
    		boolean saveInInbox = true;

    		//Account Fetch enable Start Siva G
    		StaticStore.LogPrinter('i',messageContent.substring(2,4));
    		if(messageContent.substring(2,4).toUpperCase().trim().equals("AF")){
    			try{
    				for (int i = 0; i < StaticStore.accountNumbers.length; i++) {
    					StaticStore.accountNumbers [i]= "0";
    					StaticStore.accType[i] = "0";
    				}
    				StaticStore.midlet.writeAccountInMemory();
    			}catch (Exception e){
    				e.printStackTrace();
    			}
    		}
    		//Account Fetch enable END
    		
    		//Commented by Ramprasath Since, by default the next 2 lines is reverted with % again
    		//messageContent = messageContent.replace('%', ' ');
    		
    		if (!gprsString.equals("")) {
    			messageContent = gprsString;
    		}

    		if (!messageContent.startsWith("Z6")) {
    			messageContent = replaceSpace(messageContent, "~", '=');
    			if(!messageContent.startsWith("BW")){
    				StaticStore.LogPrinter('i',"!!!!!right now2!!!!!!!"+messageContent);
    				messageContent = replaceSpace(messageContent, "|", '@');
    			}
    			// messageContent = replaceSpace(messageContent, "|", '@');
    			if (!messageContent.startsWith("5T") && !messageContent.startsWith("2T")&& !messageContent.startsWith("8T")) {
    				messageContent = replaceSpace(messageContent, "/", '^');
    			}
    		}
    		if (messageContent.indexOf(";BID:") != -1) {
    			messageContent = messageContent.substring(0, messageContent
    					.indexOf(";BID:"));
    		}
    		if(!messageContent.startsWith("TF00")){
    			txnID = messageContent.substring(messageContent.trim().indexOf(
    			"TXNID:") + 6);
    		}
    		//txnID = messageContent.substring(messageContent.trim().indexOf("TXNID:") + 6);
    		if(messageContent.startsWith("BE00") || messageContent.startsWith("MS00")){
    			StaticStore.LogPrinter('i',"Checking"+messageContent);
    			messageContent = messageContent+"^"+StaticStore.selectedAccNumber;
    			StaticStore.LogPrinter('i',"Checking"+messageContent);
    		}
    		CorrMsgReceived = true;//MatchCorrespondingScreens(messageContent);
    		/**
    		 * Incoming message naming 
    		 * Inbox title changes 
    		 * Future use
    		 */
    		String message = inboxTitleName(messageContent.substring(0, 2).toUpperCase().trim()) ;
    		
    		/** ****************************************************************************************** */
    		boolean isExactResponse	=	true; // false earlier

    		//			for (int j = 0; j < StaticStore.menuDesc.length; j++) {
    		//				if (StaticStore.menuDesc[j].length > 0
    		//						&& StaticStore.menuDesc[j][1].substring(2, 4).equals(
    		//								messageContent.substring(0, 2))) {
    		//					isExactResponse = true;
    		//				}
    		//			}			
    		StaticStore.LogPrinter('i',"isProcessing  ==> "+isProcessing);

    		if (!StaticStore.isProgressBarClosed && (messageContent.startsWith(StaticStore.SentRequest))) {//isProcessing &&

    			if (messageContent.startsWith("PC00")) {
    				get_RespectiveScreens(context , messageContent);
    				notifyNewMessage = false;
    			} else {// if(!messageContent.startsWith("CA00")) {
    				StaticStore.LogPrinter('i',"******processing change here**********");
    				inResponseInbox = false;
    				//;

    				if (CorrMsgReceived) {
    					// Following condtion is used to avoid to save incoming
    					// sms of mobile number check
    					StaticStore.LogPrinter('i',"******CorrMsgReceived change here**********");
    					isProcessing = false;
    					StaticStore.loadingFlag = false;
    					if (!messageContent.startsWith("CM")
    							&& !messageContent.startsWith("GT"))
    						inResponseInbox = true;
    					get_RespectiveScreens(context , messageContent);
    					notifyNewMessage = false;
//    					startedAutomatically = false;
    				}
    			}
    		}

    		boolean writeInMem = false;
    		if (!messageContent.startsWith("N1")
    				&& !messageContent.startsWith("N2")
    				&& !messageContent.startsWith("Z4")
    				&& !messageContent.startsWith("Y1")
    				&& !messageContent.startsWith("DZ") && !messageContent.startsWith("DM") && !messageContent.startsWith("K1") && !messageContent.startsWith("K3") && !messageContent.startsWith("K5") && !messageContent.startsWith("6D")) {
    			writeInMem = false;
    		} else {

    			inResponseInbox = true;
    			writeInMem = true;
    			StaticStore.LogPrinter('i',"******here writeInMem**********"+writeInMem);
    		}
    		if(messageContent.startsWith("B4") || messageContent.startsWith("B8")){
    			inResponseInbox = true;
    			writeInMem = true;
    		}
// inbox not saveing part
    		// Future Use 
    		if (!isPreLoginTransaction(messageContent.substring(0, 2).toUpperCase().trim()) && !messageContent.startsWith("AI")
    				&& !messageContent.startsWith("AR") && !messageContent.startsWith("CS")
    				&& !messageContent.startsWith("IS") && !messageContent.startsWith("T1")
    				&& !messageContent.startsWith("M1") && !messageContent.startsWith("M2")
    				&& !messageContent.startsWith("AS") && !messageContent.startsWith("ML")
    				&& !messageContent.startsWith("I2") && !messageContent.startsWith("I3")
    				&& !messageContent.startsWith("IL") && !messageContent.startsWith("PL")
    				&& !messageContent.startsWith("F1") && !messageContent.startsWith("F2")
    				&& !messageContent.startsWith("F4") && !messageContent.startsWith("Q7")
    				&& !messageContent.startsWith("Q1") && !messageContent.startsWith("B2")
    				&& !messageContent.startsWith("B3") && !messageContent.startsWith("Y3") 
    				&& !messageContent.startsWith("B5") && !messageContent.startsWith("1B")
    				&& !messageContent.startsWith("B9") && !messageContent.startsWith("N1")
    				&& !messageContent.startsWith("N2") && !messageContent.startsWith("N4")
    				&& !messageContent.startsWith("BZ") && !messageContent.startsWith("Q9")
    				&& !messageContent.startsWith("QB") && !messageContent.startsWith("T1")
    				&& !messageContent.startsWith("T2") && !messageContent.startsWith("T3")
    				&& !messageContent.startsWith("A1") && !messageContent.startsWith("SP")
    				&& !messageContent.startsWith("W3") && !messageContent.startsWith("IM")
    				&& !messageContent.startsWith("2L") && !messageContent.startsWith("3L")
    				&& !messageContent.startsWith("4L") && !messageContent.startsWith("5L")
    				&& !messageContent.startsWith("6L")&& !messageContent.startsWith("P5")
    				&& !messageContent.startsWith("D5") && !messageContent.startsWith("D6")
    				&& !messageContent.startsWith("OU") && !messageContent.startsWith("FU")
    				&& !messageContent.startsWith("AF") && !messageContent.startsWith("NC")
    				&& !messageContent.startsWith("V2") && !messageContent.startsWith("V4")
    				&& !messageContent.startsWith("RM") && !messageContent.startsWith("CE") 
    				&& !messageContent.startsWith("SE") && !messageContent.startsWith("PF")
    				&& !messageContent.startsWith("B9") && !messageContent.startsWith("BX")
    				&& !messageContent.startsWith("I5") && !messageContent.startsWith("R0")
    				&& !messageContent.startsWith("S1") && !messageContent.startsWith("S2")
    				&& !messageContent.startsWith("E5") && !messageContent.startsWith("GP")
    				&& !messageContent.startsWith("D7") && !messageContent.startsWith("D9")
    				&& !messageContent.startsWith("4W") && !messageContent.startsWith("M9")
    				&& !messageContent.startsWith("V6") && !messageContent.startsWith("4T")
    				&& !messageContent.startsWith("7T") && !messageContent.startsWith("8T")
    				&& !messageContent.startsWith("P2") && !messageContent.startsWith("1T")
    				&& !messageContent.startsWith("2T") && !messageContent.startsWith("3T")
    				&& !messageContent.startsWith("SY") && !messageContent.startsWith("TF")
    				&& !StaticStore.isFromLoginScreen
    				&& !messageContent.startsWith("4U") && !messageContent.startsWith("TH")
    				&& !messageContent.startsWith("LI") && !messageContent.startsWith("LO")
    				&& !messageContent.startsWith("6U")&& !messageContent.startsWith("7U")
    				&& !messageContent.startsWith("TP") && !messageContent.startsWith("CH")
    				&& !messageContent.startsWith("LF") && !messageContent.startsWith("LA")
    				//For Confirm Pages
    				&& !messageContent.startsWith("M3") && !messageContent.startsWith("RA")
    				&& !messageContent.startsWith("Q2") && !messageContent.startsWith("W1")
    				&& !messageContent.startsWith("AB") && !messageContent.startsWith("T4")
    				&& !messageContent.startsWith("I4") && !messageContent.startsWith("SC")
    				&& !messageContent.startsWith("R5") && !messageContent.startsWith("R6")
    				&& !messageContent.startsWith("RS") && !messageContent.startsWith("2U")
    				&& !messageContent.startsWith("DX") && !messageContent.startsWith("DY")
    				&& !messageContent.startsWith("DW") && !messageContent.startsWith("DT")
    				&& !messageContent.startsWith("Z6") && !messageContent.startsWith("BW")
    				&& !messageContent.startsWith("2B") && !messageContent.startsWith("1B")
    				&& !messageContent.startsWith("ZP") && !messageContent.startsWith("OG") && !messageContent.startsWith("BO")
    				&& !messageContent.startsWith("OV") && !messageContent.startsWith("K2")
    				&& !messageContent.startsWith("1I") && !messageContent.startsWith("7L")
    				&& !messageContent.startsWith("3I") && !messageContent.startsWith("HL")
    				&& !messageContent.startsWith("IL") && !messageContent.startsWith("I1")
    				&& !messageContent.startsWith("I2") && !messageContent.startsWith("I3")
    				&& !messageContent.startsWith("I4") && !messageContent.startsWith("I5")
    				&& !messageContent.startsWith("IS") && !messageContent.startsWith("3I")
    				&& !messageContent.startsWith("3S") )

    		{


    			if (!messageContent.startsWith("CM")
    					&& !messageContent.startsWith("GT")) {

    				if(isExactResponse) {
    					writeInMem = true;
    					inResponseInbox = 	true;
    					saveInInbox		=	true;
    				}
    			}
    		} else {
    			saveInInbox		=	false;
    			inResponseInbox = 	false;
    		}
    		StaticStore.LogPrinter('i',">>>>>>>>>>>Coming Inside writeInmem"+writeInMem);

    		if (writeInMem) {
    			///StaticStore.LogPrinter('i',"**** responseMessages ==> "+responseMessages);
    			///StaticStore.LogPrinter('i',"**** responseMessages Array==> "+Arrays.deepToString(responseMessages));
    			responseMessages = (responseMessages == null) ? RmsStore
    					.readInboxRecordStore(RmsStore.parsedRecords,
    							responseMessages) : responseMessages;

    					StaticStore.LogPrinter('i',"**** responseMessages Array==> "+Arrays.deepToString(responseMessages));
    					// responseMessages = RmsInbox.WriteInbox(messageContent,
    					// RmsInbox.ResponseInbox, ((isAutomaticallyStarted ||
    					// (CorrMsgReceived && isProcessing &&
    					// messageContent.startsWith(StaticStore.SentRequest))) ? "N" :
    					// "Y"));
    					if (notifyNewMessage) {
    						if (messageContent.startsWith("BE"))
    							message = StaticStore.BE;
    						else if (messageContent.startsWith("MS"))
    							message = StaticStore.MS;
    						else if (messageContent.startsWith("FT"))
    							message = StaticStore.FT;
    						else if (messageContent.startsWith("PC"))
    							message = "Change mPIN";

    						//StaticStore.LogPrinter('i',"******msg received here**********");
    						inResponseInbox = true;
    						// getDisplay().getCurrent().setTicker(new Ticker("New " +
    						// message + " message received in Application Inbox"));
    						StaticStore.LogPrinter('i',"******msg received here saveInInbox**********"+saveInInbox);
    						if(saveInInbox){

    							String alertMsg="";
    							alertMsg =  "New "	+ message + " message received in inbox";
    							Toast.makeText(context, alertMsg, Toast.LENGTH_SHORT).show();
    							//							 StaticStore.ToastDisplay(context,alertMsg);
    							StaticStore.LogPrinter('i',"******msg received here**********");
    						}

    						
    					}

    					////StaticStore.LogPrinter('i',"inResponseInbox   ==>"+inResponseInbox);
    					////StaticStore.LogPrinter('i',"saveInInbox   ==>"+saveInInbox); //!isAutomaticallyStarted || 
    					if (inResponseInbox && saveInInbox) {
    						responseMessages = RmsStore
    						.writeInboxRecordStore(
    								responseMessages,
    								(((CorrMsgReceived
    										&& isProcessing && messageContent
    										.startsWith(StaticStore.SentRequest))) ? "N"
    												: "Y")
    												+ messageContent,
    												RmsStore.parsedRecords);

    						
    					}
    		}

    		if ((CorrMsgReceived && messageContent
    				.startsWith(StaticStore.SentRequest))
    				|| (messageContent.startsWith("PC00") && !StaticStore.IsPermitted)) {

    			if (!StaticStore.SentRequest.equals("AF"))
    				StaticStore.SentRequest = "0";

    			StaticStore.RequestSentTime = 0;

    			if (StaticStore.withMemory && StaticStore.IsPermitted) {
    				writeinMemory(context);
    			} else if (!StaticStore.IsPermitted) {
    				if (messageContent.startsWith("PC00")) {
    					StaticStore.IsPermitted = true;
    					writeinMemory(context);
    				}
    			}

    			isProcessing = false;
    		}

//    		isAutomaticallyStarted = false;

    	} catch (Exception e) {
			e.printStackTrace();
			
		}
	} 
    
    private void getReplacedArray() {
        for(int i = 0 ; i < responseMessages.length; i++) {
            for(int j = 0 ; j < responseInboxMessages.length; j++) {
                if(responseMessages[i].substring(1).equals(responseInboxMessages[j].substring(1))
                && !responseMessages[i].substring(0, 1).equals(responseInboxMessages[j].substring(0, 1))) {
                    responseMessages[i] =   responseInboxMessages[j];
                }
            }
        }
    }
    
	public void writeinMemory(Context context) {
		StaticStore.LogPrinter('i',">>>>writeinMemory>>");
		try {
			RmsStore.writeRecordStore(StaticStore.VMN_Number
					+ "~"
					+(StaticStore.restartAlert ? 0 : 1)
					+"~"
					+ (StaticStore.IsPermitted ? 1 : 0)
					+ "~"
					+ StaticStore.SentRequest
					+ "~"
					+ (StaticStore.StoreTxnNo.equals("") ? "0": StaticStore.StoreTxnNo) + "~"
					+ StaticStore.RequestSentTime + "~"
					+ (StaticStore.withMemory ? 1 : 0) + "~0~"
					+ (StaticStore.IsPersonalInfoGot ? 1 : 0) + "~"
					+ (StaticStore.IsGPRS ? 1 : 0) + "~" + StaticStore.myMobileNo
					+ "~" + (StaticStore.isBillpayEnabled ? 1 : 0) + "~"
					+ StaticStore.publicKey + "~" + StaticStore.KSN + "~"
					+ StaticStore.BDK + "~" + StaticStore.defaultPWD + "~"
					+ StaticStore.applicationPWD + "~" + StaticStore.GprsUrl + "~"
					+ StaticStore.wapUrl + "~" + (StaticStore.isDashBoardProfileImageRemoved ? 1 : 0) + "~"
					+ (StaticStore.isDashBoard ? 1 : 0) + "~"
					+ (StaticStore.isForgotPWD ? 1 : 0) + "~"
					+ (StaticStore.isCommModeSelected ? 1 : 0) + "~"
					+ StaticStore.defaultPublicKey + "~"
					+ StaticStore.retryCount + "~"
					+ StaticStore.numberOfTimeout + "~"
					+ StaticStore.timeoutTran[0] + "~" + StaticStore.timeoutTran[1]
					+ "~" + StaticStore.timeoutTran[2] + "~"
					+ StaticStore.timeoutTran[3] + "~" + StaticStore.timeoutTran[4]
					+ "~" + StaticStore.timeoutTime[0] + "~"
					+ StaticStore.timeoutTime[1] + "~" + StaticStore.timeoutTime[2]
					+ "~" + StaticStore.timeoutTime[3] + "~"
					+ StaticStore.timeoutTime[4] + "~", RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_DEF);
			StaticStore.LogPrinter('i',">>>>writeinMemory>StaticStore.applicationPWD>"+StaticStore.applicationPWD);
		} catch (Exception e) {
			StaticStore.LogPrinter('i',">>>>writeinMemory>StaticStore.applicationPWD<<<<<exception >"+StaticStore.applicationPWD);
			e.printStackTrace();
			StaticStore.ToastDisplay(context,"Transaction Failure : In : Write Memory");
		}
	}

    public Intent getMyServices(Context context){
//    	StaticStore.indexCtr = 0;
    	isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_billpay,
    			R.drawable.li_temple_donation,R.drawable.li_institutionfees_payment};
    	//,R.drawable.li_airline_ticket,R.drawable.li_movie_ticketing,R.drawable.li_recharge, 
    	String[] menuItem = {"Bill Pay",
				"Donation","Fees Payment"};//, "Airline Ticketing","Movie Ticketing", "Recharge",
//    	String[] menuItem = {"Bill Pay", "Ticketing", "Mobile Recharge",
  //  			"Temple Donation", "Institution Fees Payment","Corporate Payment","Shopping" };
        //myServices = new ListCanvas(this,getDisplay(),"My Services",new String[],1,false,false,false,true);//"eCash",
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 1);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Payment");
  	   mPayIntent.putExtra("more", false);
  	 mPayIntent.putExtra("isImageTextList", true);
        return mPayIntent;
        
       }

	public Intent getDeposits(Context context) {
		isImageTextList = true;
		imagesListArray = new int[] { R.drawable.li_deposit_recurring, R.drawable.li_deposit_fixed,
							R.drawable.li_deposit_navarathnamala, R.drawable.li_deposit_muthukuvial,
							R.drawable.li_deposit_2020,	R.drawable.li_deposit_555};
		String[] menuItem = { "Open Recurring Deposit", "Open Fixed Deposit (Simple Interest)",
				"Open Navarathnamala Deposit", "Open Muthukuvial Deposit",
				"Special 20:20 Deposit", "Special 555 Deposit"};
		mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 253);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Deposit");
		mPayIntent.putExtra("more", false);
		mPayIntent.putExtra("isImageTextList", true);
		return mPayIntent;
	}    
	
	
	public Intent getMaturityInstructionFD(Context context){
        String[] menuItem = {"Renew My Deposit","Close the Account on Maturity"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   	mPayIntent.putExtra("listIndex", 256);
  	   	mPayIntent.putExtra("menuItem", menuItem);
  	   	mPayIntent.putExtra("listHeader", "Maturity Instruction");
  	   	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
	
	public Intent getMaturityInstructionMD(Context context){
        String[] menuItem = {"Renew My Maturity Amount","Renew only My Deposit","Close the Account on Maturity"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   	mPayIntent.putExtra("listIndex", 265);
  	   	mPayIntent.putExtra("menuItem", menuItem);
  	   	mPayIntent.putExtra("listHeader", "Maturity Instruction");
  	   	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
    
	public Intent getPayInterest(Context context) {
    	String[] menuItem = null;
    	if(StaticStore.closeTheAccountOnMaturity  && StaticStore.depositPeriodInDays){
            menuItem = new String[]{"Interest Payment on Maturity"};
    	}else if(StaticStore.closeTheAccountOnMaturity && StaticStore.depositPeriodInMonthsForNonQuarterly){
    		menuItem = new String[]{"Interest Payment on Maturity","Monthly Interest Payment"};
    	}else if(StaticStore.closeTheAccountOnMaturity && StaticStore.depositPeriodInMonthsForQuarterly){
    		menuItem = new String[]{"Interest Payment on Maturity","Monthly Interest Payment","Quarterly Interest Payment"};
    	}else if(StaticStore.renewMyDeposit && StaticStore.depositPeriodInDays){
    		menuItem = new String[]{"Interest Payment on Maturity"};
    	}else if(StaticStore.renewMyDeposit  && StaticStore.depositPeriodInMonthsForNonQuarterly){
    		menuItem = new String[]{"Interest Payment on Maturity","Monthly Interest Payment"};
    	}else if(StaticStore.renewMyDeposit  && StaticStore.depositPeriodInMonthsForQuarterly){
    		menuItem = new String[]{"Interest Payment on Maturity","Monthly Interest Payment","Quarterly Interest Payment"};
    	}
    	mPayIntent = new Intent(context, ListSelection.class);
       	mPayIntent.putExtra("listIndex", 257);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Pay Interest");
       	mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
	
	public Intent getInterestOption(Context context){
        String[] menuItem = {"Monthly Interest Payment","Quarterly Interest Payment","Interest on Maturity"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   	mPayIntent.putExtra("listIndex", 262);
  	   	mPayIntent.putExtra("menuItem", menuItem);
  	   	mPayIntent.putExtra("listHeader", "Interest Option");
  	   	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
	
	public Intent getSchemeTypeNMD(Context context){
        String[] menuItem = {"5 Years","10 Years"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   	mPayIntent.putExtra("listIndex", 259);
  	   	mPayIntent.putExtra("menuItem", menuItem);
  	   	mPayIntent.putExtra("listHeader", "Scheme Type");
  	   	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
	
	public Intent getDepositPeriodNMD(Context context){
		String[] menuItem = null;
		if(StaticStore.dynamicTempND1Flag){
			StaticStore.dynamicTempND1Flag = false;
			StaticStore.dynamicTempND2Flag = false;
            menuItem = new String[]{"36"};
		}else if(StaticStore.dynamicTempND2Flag){
			StaticStore.dynamicTempND1Flag = false;
			StaticStore.dynamicTempND2Flag = false;
            menuItem = new String[]{"36","48","60"};
		}
        mPayIntent = new Intent(context, ListSelection.class);
  	   	mPayIntent.putExtra("listIndex", 260);
  	   	mPayIntent.putExtra("menuItem", menuItem);
  	   	mPayIntent.putExtra("listHeader", "Deposit Period in Months");
  	   	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
	
	public Intent getDepositAccountBalanceList(Context context) {
		isImageTextList = true;
		imagesListArray = new int[] { R.drawable.li_deposit_recurring, R.drawable.li_deposit_fixed,
						R.drawable.li_deposit_navarathnamala,R.drawable.li_deposit_navarathnamala, 
						R.drawable.li_deposit_muthukuvial,R.drawable.li_deposit_2020,	R.drawable.li_deposit_555};
		String[] menuItem = {"Recurring Deposit", "Fixed Deposit", "Navarathnamala Deposit - 5 Years",
				             "Navarathnamala Deposit - 10 Years", "Muthukuvial Deposit","Special 20:20 Deposit","Special 555 Deposit"};
		mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 267);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader","Deposit Account Balance");
		mPayIntent.putExtra("more", false);
		mPayIntent.putExtra("isImageTextList", true);
		return mPayIntent;

	}
	
	 public Intent depositAccountBalanceList(Context context){
			String[] menuItem = new String[depositAccountBalanceList.length];
			for (int i = 0; i < depositAccountBalanceList.length; i++) {
				menuItem[i] = depositAccountBalanceList[i][1];
			}
			selectedAccNumberForDepositAccBal = new String[menuItem.length];
			for (int i = 0; i < menuItem.length; i++) {
				selectedAccNumberForDepositAccBal[i] = maskedAccNumber(menuItem[i]);
			}
			mPayIntent = new Intent(context, ListSelection.class);
			mPayIntent.putExtra("listIndex", 268);
			mPayIntent.putExtra("menuItem", selectedAccNumberForDepositAccBal);
			mPayIntent.putExtra("listHeader", "Deposit Account Balance");
			if (moreFlag) {
				mPayIntent.putExtra("more", true);
			} else {
				mPayIntent.putExtra("more", false);
			}
			return mPayIntent;

		}
	 
	 	public Intent loanBalanceList(Context context){
			String[] menuItem = new String[loanBalanceList.length];
			for (int i = 0; i < loanBalanceList.length; i++) {
				menuItem[i] = loanBalanceList[i][1];
			}
			selectedAccNumberForLoanBal = new String[menuItem.length];
			for (int i = 0; i < menuItem.length; i++) {
				selectedAccNumberForLoanBal[i] = maskedAccNumber(menuItem[i]);
			}
			mPayIntent = new Intent(context, ListSelection.class);
			mPayIntent.putExtra("listIndex", 270);
			mPayIntent.putExtra("menuItem", selectedAccNumberForLoanBal);
			mPayIntent.putExtra("listHeader", "Loan Balance");
			if (moreFlag) {
				mPayIntent.putExtra("more", true);
			} else {
				mPayIntent.putExtra("more", false);
			}
			return mPayIntent;

		}
	
    public Intent get_AirlineLists(Context context) {
        String menuItem[] = new String[airlineBooking.length];
        for(int i = 0; i < airlineBooking.length; i++){
            menuItem[i] = airlineBooking[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 38);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Airline List");
        if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        //    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
        
        return mPayIntent;
        
    }
    
    public Intent get_CollegeDetailsLists(Context context) {
        String menuItem[] = new String[collegeDetailsList.length];
        for(int i = 0; i < collegeDetailsList.length; i++){
            menuItem[i] = collegeDetailsList[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	    mPayIntent.putExtra("listIndex", 285);
   	    mPayIntent.putExtra("menuItem", menuItem);
   	    mPayIntent.putExtra("listHeader", "Category");
        if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
        return mPayIntent;
    }
    
    public Intent getTransactionHistoryList(Context context) {
        String menuItem[] = new String[transactionHistoryList.length];
        for(int i = 0; i < transactionHistoryList.length; i++){
            menuItem[i] = transactionHistoryList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 247);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Transaction History List");
        if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        //    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
        
        return mPayIntent;
        
    }
    
    public Intent getInwardChequeStatus(Context context) {
        String menuItem[] = new String[inwardChequeStatusList.length];
        for(int i = 0; i < inwardChequeStatusList.length; i++){
            menuItem[i] = inwardChequeStatusList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 281);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Cheque No. List");
        /*if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        //    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
        }else{
        	mPayIntent.putExtra("more", false);
        }*/
        
        return mPayIntent;
        
    }
    
    public Intent getOutwardChequeStatus(Context context) {
        String menuItem[] = new String[outwardChequeStatusList.length];
        for(int i = 0; i < outwardChequeStatusList.length; i++){
            menuItem[i] = outwardChequeStatusList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 282);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Cheque No. List");
       /* if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        //    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
        }else{
        	mPayIntent.putExtra("more", false);
        }*/
        
        return mPayIntent;
        
    }
    
    public Intent getBillpayMenu(Context context){
        String[] menuItem = {"Registered Bill Payment","Instant Bill Payment"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 4);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Bill Pay");
  	   mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
    public int getCharCount(String message, char splitter) {
        
        int ctr = 0;
        
        for(int i = 0;i < message.length();i++){
            if(message.charAt(i) == splitter){
                ctr++;
            }
        }
        return ctr;
    }
    
	public String[][] getSplittedValues(String message, int columnCount,
			char splitter, boolean isRefresh, String[][] arr) {
		String tempString = message.substring(0,message.indexOf(';')).trim();
		message = message.substring(message.indexOf(';')).trim();
		String splitstr = Character.toString(splitter);
		String[] temp = tempString.split(splitstr,-1);
		 arr = new String[temp.length][columnCount-1];
			 for(int i=0;i<temp.length;i++) {
				 arr[i] = temp[i].split("\\*");
			 }
		try {
			if(message.indexOf("TXNID:") != -1){
				txnID = message.substring(message.indexOf("TXNID:") + 6);
				message = message.substring(message.indexOf(";"),message.lastIndexOf(';')).trim();
			}
			if(message != null && !message.equals("")){
				String[] txndet = message.split("\\;");
				try{
					for(int s=0;s<txndet.length;s++){
						if(txndet[s].trim().equals("Y")||txndet[s].trim().equals("N")){
							moreFlag = txndet[s].trim().equals("Y");
							nextStartRecNumber = (Integer.parseInt(txndet[s-1]) + arr.length)+ "";
						}
					}
				}catch (Exception e) {
					moreFlag = false; 
					e.printStackTrace();
				}
			}
		} catch (StringIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException numExp) {
			numExp.printStackTrace();
		}
		return arr;
	}
	
	 //Siva for Bill pay
	 public String[][] getSplittedValuesArray(String message,int columnCount,String splitter,String[][] arr){
		 //OBC128|Mahanagar Telephone Nigam Delhi|Y~OBC130|Mahanagar Telephone Nigam Delhi|Y
		 String[] temp = message.split(splitter,-1);
		 arr = new String[temp.length][columnCount-1];
	     for(int i=0;i<temp.length;i++)
	     {
	     	arr[i] = temp[i].split("\\*");
	     }
		 
		 return arr;
	 }
    
    public Intent getUnRegBillers(Context context){
        boolean isEntered = false;
        String menuItem[] = new String[unRegBillers.length];
        for(int i = 0; i < unRegBillers.length; i++){
            if(true){//billerCategoryList[billerCategories.getSelectedIndex()][0].trim().equals(billerCategory[i].trim())
                isEntered = true;
                menuItem[i] = unRegBillers[i][1];
            }
        }
        if(!isEntered){
            menuItem = new String[0];
        }

        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 5);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Biller List");
        if(moreFlag){
      	   mPayIntent.putExtra("more", true);
       
        }else{
        	mPayIntent.putExtra("more", false);
        }
        
        return mPayIntent;
        
    }
    
    public Intent getCategoryList(Context context) {
        String menuItem[] = new String[billerCategoryList.length];
        for(int i = 0; i < billerCategoryList.length; i++){
            menuItem[i] = billerCategoryList[i][0];
        }
        
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 33);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Biller Category");
         if(moreFlag){
       	   mPayIntent.putExtra("more", true);
       
         }else{
         	mPayIntent.putExtra("more", false);
         }
         
         return mPayIntent;
        
        
    }
    
    public Intent get_Un_Reg_BillerList(Context context) {
        String menuItem[] = new String[un_Reg_Billers.length];
        for(int i = 0; i < un_Reg_Billers.length; i++){
            menuItem[i] = un_Reg_Billers[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 226); //109
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Biller List");
         if(moreFlag){
       	   mPayIntent.putExtra("more", true);
       
         }else{
         	mPayIntent.putExtra("more", false);
         }
         
         return mPayIntent;
        
        
    }
    
    public Intent get_Un_Reg_Pres_BillerList(Context context) {
    	String menuItem[] = new String[sub_CatId.length];
    	for(int i = 0; i < sub_CatId.length; i++){
    		menuItem[i] = sub_CatId[i][1];
    	}
    	
    	mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 227); //111
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader", "List Of Sub-Category");
    	if(moreFlag){
    		mPayIntent.putExtra("more", true);
    		
    	}else{
    		mPayIntent.putExtra("more", false);
    	}
    	
    	return mPayIntent;
    	
    	
    }

    public Intent getBillpayBills(Context context){
        String menuItem[] = new String[billpayBills.length];
        for(int i = 0; i < billpayBills.length; i++){
            menuItem[i] = billpayBills[i][0];
        }
        
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 40);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Registered Bill Payment");
      if(moreFlag){
    	   mPayIntent.putExtra("more", true);
      
      }else{
      	mPayIntent.putExtra("more", false);
      }
      
      return mPayIntent;
        
    }    
    
    public void refreshBillerArray(String categoryID) {
        int temp = 0;
        int j    = 0;
        String[][] tempArr;
        String[] tempArr1;
        tempArr  = unRegBillers;
        tempArr1 = billerCategory;
        
        for(int i = 0; i < billerCategory.length;i++){
            if(categoryID.equals(billerCategory[i])){
                temp++;
            }
        }
        
        unRegBillers        = new String[unRegBillers.length - temp][unRegBillers[0].length];
        billerCategory = new String[billerCategory.length - temp];
        
        for(int i = 0;i<tempArr.length;i++){
            if(!categoryID.equals(tempArr1[i])){
                unRegBillers[j]        = tempArr[i];
                billerCategory[j] = tempArr1[i];
                j++;
            }
        }
        
        for(int i = 0;i<unRegBillers.length;i++){
            //StaticStore.LogPrinter('i'," :: "+billerCategory[i]+" :: "+unRegBillers[i][3]);
        }
    }
    
    public int getSelectedBiller(String categoryID,int selectedIndex){
        int i;
        for(i = 0;i<billerCategory.length;i++){
            if(categoryID.trim().equals(billerCategory[i].trim())){
                return (i + selectedIndex);
            }
        }
        return -1;
    }
    
    public void assignValues(int index,boolean isLinkBiller) {
        int i = 0;
        if(isLinkBiller){
            String[][] tempArr = new String[1][unRegBillers[index].length + 5];
            tempArr[0][0] = isLinkBiller?"Add Biller":"Instant Payment";
            tempArr[0][1] = isLinkBiller?"APB4;Y":"APB8;Y";
            tempArr[0][2] = unRegBillers[index][2].trim();
            tempArr[0][3] = unRegBillers[index][3].trim();
            tempArr[0][4] = unRegBillers[index][4].trim();
            tempArr[0][5] = unRegBillers[index][5].trim();
            for(i = 6;!unRegBillers[index][i].trim().equals("4-4-N-Y-Y");i++){
                tempArr[0][i] = unRegBillers[index][i].trim();
            }
            // for removing mPIN in all modules - 10-09-2009
            unRegBillers[index][i] = "";
            tempArr[0][i] = isLinkBiller?"Nickname":"Amount (Rs.)";
            i++;
            while(i<unRegBillers[index].length){
                tempArr[0][i] = unRegBillers[index][i-1];
                i++;
            }
            //Siva Nickname length chaged to 10
            tempArr[0][i] = isLinkBiller?"1-10-ANW-N-N":"1-10-ND-N-N";
            i++;
            tempArr[0][i] = (Integer.parseInt(unRegBillers[index][unRegBillers[index].length - 1]) + 1)+"";
            tempArr[0][i+1] = "false";
            tempArr[0][i+2] = "false";
            tempArr[0][i+3] = isLinkBiller?"N":"Y";
            StaticStore.LogPrinter('i',"APB4 === tempArr --->"+Arrays.deepToString(tempArr));
            if(isLinkBiller){
                StaticStore.menuDesc[17] = tempArr[0];
            }else{
            	StaticStore.menuDesc[24] = tempArr[0];
            }
        }else{
            String[][] tempArr = new String[1][unRegBillers[index].length + 4];
            tempArr[0][0] = "Instant Bill Payment";
            tempArr[0][1] = "APB8;Y";
            tempArr[0][2] = unRegBillers[index][2].trim();
            tempArr[0][3] = unRegBillers[index][3].trim();
            tempArr[0][4] = unRegBillers[index][4].trim();
            tempArr[0][5] = unRegBillers[index][5].trim();
            for(i = 6;!unRegBillers[index][i].trim().equals("4-4-N-Y-Y");i++){
                tempArr[0][i] = unRegBillers[index][i].trim();
            }
            // for removing mPIN in all modules - 10-09-2009
            unRegBillers[index][i] = "";
            tempArr[0][i - 2] = "";
            i++;
            tempArr[0][i - 2] = "Amount (Rs.)";
            i++;
            tempArr[0][i - 2] = billPayTxnId;
            i++;
           // tempArr[0][i - 2] = "4-4-N-Y-Y";
            tempArr[0][i - 2] = "";
            i = i+2;
            while(i<unRegBillers[index].length){
                tempArr[0][i] = unRegBillers[index][i-1 - 2];
                i++;
            }
            tempArr[0][i] = "";
            i++;
            tempArr[0][i] = "1-10-ND-N-N";
            i++;
            tempArr[0][i] = "";
            i++;
            tempArr[0][i] = (Integer.parseInt(unRegBillers[index][unRegBillers[index].length - 1]) + 1)+"";
            for(int k = 0;k < tempArr[0].length;k++){
                if(tempArr[0][k] == null){
                    tempArr[0][k] = "";
                }
            }
            StaticStore.menuDesc[24] = tempArr[0];
        }
    }
    
    public Intent getBillPaymentList(Context context){
    	String[] menuItem = { "Add Biller", "Pay Biller",
				"Delete Biller"}; //, "View Paid Bills" 
        
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 6);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Registered Bill Payment");
       	mPayIntent.putExtra("more", false);
       
       return mPayIntent;
    }
    
    public Intent getTempleList(Context context) {
        String menuItem[] = new String[temples.length];
        for(int i = 0; i < temples.length; i++){
            menuItem[i] = temples[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 48);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Entity List");
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
        
    }
    
    
    public Intent getMultipleAccOptions(Context context){
        String[] menuItem = {"Link an A/C","De-Link an A/C"};
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 80);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "My Accounts");
        	mPayIntent.putExtra("more", false);
        
        return mPayIntent;
    }

    public Intent getCOCTAXOptions(Context context){
        String[] menuItem = {"Old Property","New Property"};
        mPayIntent = new Intent(context, ListSelection.class);
   	   	mPayIntent.putExtra("listIndex", 277);
   	   	mPayIntent.putExtra("menuItem", menuItem);
   	   	mPayIntent.putExtra("listHeader", "Instant Bill Payment");
        mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
    
    public Intent getMyAccounts(boolean isDelete,Context context){
        int selectedindex = 0;
        
        String menuItem[] = new String[StaticStore.totalAccounts];
        int j = 0;
        for(int i = 0; i < StaticStore.accountNumbers.length;i++){
            if(StaticStore.accountNumbers[i].equals("0")){
                continue;
            }else{
                menuItem[j++] = StaticStore.accountNumbers[i];
            }
        }
        if(!isDelete){
        	 mPayIntent = new Intent(context, ListSelection.class);
      	   mPayIntent.putExtra("listIndex", 81);
      	   mPayIntent.putExtra("menuItem", menuItem);
      	   mPayIntent.putExtra("listHeader", "Select Transaction A/C");
           	mPayIntent.putExtra("more", false);
           // LCMyAccounts  = new ListCanvas(this,getDisplay(),"My Accounts",menuItem,81,false,false,false);
            for(int i = 0; i < menuItem.length;i++){
                if(StaticStore.selectedAccNumber.equals(menuItem[i])){
                    selectedindex = i;
                    break;
                }
            }
        }else{
        	 mPayIntent = new Intent(context, ListSelection.class);
        	   mPayIntent.putExtra("listIndex", 83);
        	   mPayIntent.putExtra("menuItem", menuItem);
        	   mPayIntent.putExtra("listHeader", "My Accounts");
             	mPayIntent.putExtra("more", false);
           
        }
        return mPayIntent;
    }
    
    public void notifyHttpIncoming(String response) {
        String messageContent = "";
        boolean notifyNewMessage = true;
        boolean CorrMsgReceived = false;
    }
    
    public Intent getInstitutionList(Context context){
        String[] menuItem = new String[institutions.length];
        for(int i = 0;i< institutions.length;i++){
            menuItem[i] = institutions[i][1];
        }
        
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 55);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Institution List");
        if(moreFlag){
      	   mPayIntent.putExtra("more", true);
        
        }else{
        	mPayIntent.putExtra("more", false);
        }
        return mPayIntent;
    }
    
    public Intent getHostelTution(Context context){
        String[] menuItem = new String[instHostelTutionList.length];
        for(int i = 0;i< instHostelTutionList.length;i++){
            menuItem[i] = instHostelTutionList[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 57);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Category");
      	mPayIntent.putExtra("more", false);
      	return mPayIntent;
    }
    
    
    public Intent getInstPaymentOption(Context context){
        boolean minFlag    = instBillDetails[0][12].trim().toUpperCase().equals("Y");
        boolean partFlag   = instBillDetails[0][10].trim().toUpperCase().equals("Y");
        boolean excessFlag = instBillDetails[0][11].trim().toUpperCase().equals("Y");
        String[] array = null;
        String billAmt  =   "";
        String minAmt   =   "";
        String penAmt   =   "";
        
        try {
            billAmt   =   " - Rs." + instBillDetails[0][16] ;
            minAmt    =   " - " + (instBillDetails[0][12].trim().toUpperCase().equals("Y")?"Rs."+instBillDetails[0][17]:"NA");
            penAmt    =   " - " + (instBillDetails[0][13].trim().toUpperCase().equals("Y")?"Rs."+instBillDetails[0][18]:"NA");
        } catch (Exception e) {
        }
        if(minFlag && partFlag && excessFlag){//111
            String[] menuItem = {"Full Payment"+billAmt,"Minimum Payment"+minAmt,"Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && partFlag && !excessFlag){//110
            String[] menuItem = {"Full Payment"+billAmt,"Minimum Payment"+minAmt,"Part Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && !excessFlag){//100
            String[] menuItem = {"Full Payment"+billAmt,"Minimum Payment"+minAmt};
            array = menuItem;
        }else if(!minFlag && !partFlag && !excessFlag){//000
            String[] menuItem = {"Full Payment"+billAmt};
            array = menuItem;
        }else if(!minFlag && !partFlag && excessFlag){//001
            String[] menuItem = {"Full Payment"+billAmt,"Excess Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && !excessFlag){//010
            String[] menuItem = {"Full Payment"+billAmt,"Part Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && excessFlag){//011
            String[] menuItem = {"Full Payment"+billAmt,"Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && excessFlag){//101
            String[] menuItem = {"Full Payment"+billAmt,"Minimum Payment"+minAmt,"Excess Payment"};
            array = menuItem;
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 60);
  	   mPayIntent.putExtra("menuItem", array);
  	   mPayIntent.putExtra("listHeader", "Payment Options");
       	mPayIntent.putExtra("more", false);
       	return mPayIntent;
    }
    public Intent getNotifyPaymentOption(Context context){
        boolean minFlag    = billpayNotifyDet[9][0].trim().toUpperCase().equals("Y");
        boolean partFlag   = billpayNotifyDet[7][0].trim().toUpperCase().equals("Y");
        boolean excessFlag = billpayNotifyDet[8][0].trim().toUpperCase().equals("Y");
        String[] array = null;
        if(minFlag && partFlag && excessFlag){//111
            String[] menuItem = {"Full Payment","Minimum Payment","Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && partFlag && !excessFlag){//110
            String[] menuItem = {"Full Payment","Minimum Payment","Part Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && !excessFlag){//100
            String[] menuItem = {"Full Payment","Minimum Payment"};
            array = menuItem;
        }else if(!minFlag && !partFlag && !excessFlag){//000
            String[] menuItem = {"Full Payment"};
            array = menuItem;
        }else if(!minFlag && !partFlag && excessFlag){//001
            String[] menuItem = {"Full Payment","Excess Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && !excessFlag){//010
            String[] menuItem = {"Full Payment","Part Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && excessFlag){//011
            String[] menuItem = {"Full Payment","Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && excessFlag){//101
            String[] menuItem = {"Full Payment","Minimum Payment","Excess Payment"};
            array = menuItem;
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 67);
   	   mPayIntent.putExtra("menuItem", array);
   	   mPayIntent.putExtra("listHeader", "Billpay Options");
        	mPayIntent.putExtra("more", false);
        	return mPayIntent;
       
    }
    public Intent getRegInstituteList(Context context){
        String[] menuItem = new String[regInstitutes.length];
        for(int i = 0;i< regInstitutes.length;i++){
            menuItem[i] = regInstitutes[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 56);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader","Institution List"); 	 

         	 if(moreFlag){
          	   mPayIntent.putExtra("more", true);
            
            }else{
            	mPayIntent.putExtra("more", false);
            }
         	return mPayIntent;

    }
    
    public Intent getRegInstituteList4Dereg(Context context){
        String[] menuItem = new String[regInstitutes4Dereg.length];
        for(int i = 0;i< regInstitutes4Dereg.length;i++){
            menuItem[i] = regInstitutes4Dereg[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 61);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Institution List");
         if(moreFlag){
       	   mPayIntent.putExtra("more", true);
         
         }else{
         	mPayIntent.putExtra("more", false);
         }
         return mPayIntent;
       
    }
    
    public Intent getFeesPayInstituteList(Context context){
        String[] menuItem = new String[feesPayN100Details.length];
        for(int i = 0;i< feesPayN100Details.length;i++){
            menuItem[i] = feesPayN100Details[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 284);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader", "Institution List");
        if(moreFlag){
       	   mPayIntent.putExtra("more", true);
        }else{
           mPayIntent.putExtra("more", false);
        }
        return mPayIntent;
    }
    
    public Intent getInstCategoryList(Context context){
        String[] menuItem = new String[instCatagories.length];
        for(int i = 0;i< instCatagories.length;i++){
            menuItem[i] = instCatagories[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 58);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Sub-Category");
      if(moreFlag){
    	   mPayIntent.putExtra("more", true);
      
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
       
    }
    
    public Intent getInstSubCatList(Context context){
    String[] menuItem = new String[instSubCatagories.length];
    for(int i = 0;i< instSubCatagories.length;i++){
        menuItem[i] = instSubCatagories[i][1];
    }
    mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 59);
	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Course List");
   if(moreFlag){
 	   mPayIntent.putExtra("more", true);
   
   }else{
   	mPayIntent.putExtra("more", false);
   }
   return mPayIntent;
}

 
 public Intent get_dispatchType(Context context){
	 
	 String menuItems[] = { "Self","Third Party" };
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 124);
	   mPayIntent.putExtra("menuItem", menuItems);
	   mPayIntent.putExtra("listHeader", "Dispatch To");
   	mPayIntent.putExtra("more", false);
   	return mPayIntent;
     
 }
 public Intent getUnregCorporateList(Context context){
     String[] menuItem = new String[unRegisteredCorpLists.length];
     for(int i = 0; i < menuItem.length;i++){
         menuItem[i] = unRegisteredCorpLists[i][0].substring(0,unRegisteredCorpLists[i][0].indexOf("-"));
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 17);
	   mPayIntent.putExtra("menuItem", menuItem);
	   mPayIntent.putExtra("listHeader", "List of Corporates");
     if(moreFlag){
   	   mPayIntent.putExtra("more", true);
     
     }else{
     	mPayIntent.putExtra("more", false);
     }
     return mPayIntent;
     
 }
 public Intent getUnregSubCorporateList(Context context){
     String[] menuItem = new String[unRegisteredSubCorpLists.length];
     for(int i = 0; i < menuItem.length;i++){
         menuItem[i] = unRegisteredSubCorpLists[i][0].substring(0,unRegisteredSubCorpLists[i][0].indexOf("-"));
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 18);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "List of Sub-Corporates");
      if(moreFlag){
    	   mPayIntent.putExtra("more", true);
      
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
      
}
    
 public Intent getAirlineMenu(Context context){
     //and to change &
    	 String[] menuItem = {"Booking & Payment","Payment Only","Reprint Ticket"};
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 26);
	   mPayIntent.putExtra("menuItem", menuItem);
	   mPayIntent.putExtra("listHeader", "Airline Ticketing");
    	mPayIntent.putExtra("more", false);
    	return mPayIntent;
     
 }
 public Intent getAirlineList(Context context){
     String[] menuItem = new String[airlineArray.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = airlineArray[i][1]+"-"+airlineArray[i][2].substring(0,2)+":"+airlineArray[i][2].substring(2);
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 34);
	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Airline List");
	
     if(moreFlag){
     	mPayIntent.putExtra("more", true);
     }else{
     	mPayIntent.putExtra("more", false);
     }
     return mPayIntent;
 }
    
 public Intent getBankList(Context context){
     String[] menuItem = new String[bankList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = bankList[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 45);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Partner Banks");
	
      if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
     
 }
 
 public Intent getOffer2OList(Context context){
     String[] menuItem = new String[offer2OList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = offer2OList[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 233);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Offers");
	
      if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
     
 }
 
 public Intent getOffer3OList(Context context){
     String[] menuItem = new String[offer3OList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = offer3OList[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 234);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Offers");
	
      if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
     
 }
 
 public Intent getOffer4OList(Context context){
     String[] menuItem = new String[offer4OList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = offer4OList[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 235);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Offers");
	
      if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
      return mPayIntent;
     
 }
 public Intent getNeftRegList(Context context){
     
     String[] menuItem = new String[neftRegList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = neftRegList[i][0];
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 46);
	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Beneficiary List");
	
   if(moreFlag){
   	mPayIntent.putExtra("more", true);
   }else{
   	mPayIntent.putExtra("more", false);
   }
   return mPayIntent;
     
 }
    

 public Intent getPartnerRegList(Context context){
String[] menuItem = new String[partnerRegList.length];
for(int i = 0;i<menuItem.length;i++){
   menuItem[i] = partnerRegList[i][0];
}
mPayIntent = new Intent(context, ListSelection.class);
mPayIntent.putExtra("listIndex", 47);
mPayIntent.putExtra("menuItem", menuItem);
mPayIntent.putExtra("listHeader", "Partner Registered List");

if(moreFlag){
	mPayIntent.putExtra("more", true);
}else{
	mPayIntent.putExtra("more", false);
}
return mPayIntent;

}

 public Intent getTempleSchemes(Context context){
     String[] menuItem = new String[templeSchemes.length];
     for(int i = 0; i < templeSchemes.length; i++){
         menuItem[i] = templeSchemes[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 49);
	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Entity Schemes"); //MID: 12976 "Entity scheme list"
	
     if(moreFlag){
     	mPayIntent.putExtra("more", true);
     }else{
     	mPayIntent.putExtra("more", false);
     }
     return mPayIntent;
     
     
 }
    

 public Intent getTempleSubSchemeList(Context context){
     String[] menuItem = new String[templeSubSchemes.length];
     for(int i = 0; i < templeSubSchemes.length; i++){
         menuItem[i] = templeSubSchemes[i][1];
     }
     mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 50);
 	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Entity Sub-scheme List");
 	   if(moreFlag){
        	mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
     return mPayIntent;
 }
    
 public Intent getDeregFTList(Context context){
     String[] menuItem = new String[withinBenDeregList.length];
     for(int i = 0; i < withinBenDeregList.length; i++){
         menuItem[i] = withinBenDeregList[i][0];
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 51);
	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Within Bank Deregistration");
	   if(moreFlag){
     	mPayIntent.putExtra("more", true);
     }else{
     	mPayIntent.putExtra("more", false);
     }
  return mPayIntent;
    
 }
    
 public Intent withInMobReg(Context context){
     String[] menuItem = new String[withinBenMobList.length];
     for(int i = 0; i < withinBenMobList.length; i++){
         menuItem[i] = withinBenMobList[i][0];
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 52);
	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Beneficiary List");
	   if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
   return mPayIntent;
    
 }
 
 
 
    private boolean isSubCorpExist(String messageContent){
        boolean isSubCorpExist = false;
        String tempStr = messageContent.substring(5);
        msg     = tempStr.substring(0,tempStr.indexOf(";")) + ";";
        tempStr        = tempStr.substring(tempStr.indexOf(";") + 1);
        msg            += tempStr.substring(0,tempStr.indexOf(";")) + ";";
        tempStr        = tempStr.substring(tempStr.indexOf(";") + 1);
        msg            += tempStr.substring(0,tempStr.indexOf(";")) + ";";
        tempStr        = tempStr.substring(tempStr.indexOf(";") + 1);
        isSubCorpExist = tempStr.substring(0,tempStr.indexOf(";")).trim().equals("Y");
        tempStr        = tempStr.substring(tempStr.indexOf(";") + 1);
        msg            += tempStr;
        return isSubCorpExist;
    }
    
    public Intent withInAccReg(Context context){
        String[] menuItem = new String[withinBenAccList.length];
        for(int i = 0; i < withinBenAccList.length; i++){
            menuItem[i] = withinBenAccList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 53);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Beneficiary List");
   	   if(moreFlag){
          	mPayIntent.putExtra("more", true);
          }else{
          	mPayIntent.putExtra("more", false);
          }
       return mPayIntent;
        
    }
    
   
    public Intent getM2MAccountList(Context context){
        String[] menuItem = new String[m2mAccounts.length];
        for(int i = 0; i < m2mAccounts.length; i++){
            menuItem[i] = maskedAccNumberWB(m2mAccounts[i][0]);
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 84);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Beneficiary A/C List");//changed by S
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
    }
  
    public Intent getCFTMenu(Context context){
    	  String[] menuItem = {"Registration","Payment","Standing Instruction","Deregistration","Forgot Password"};
        
  	   mPayIntent = new Intent(context, ListSelection.class);
     	   mPayIntent.putExtra("listIndex", 7);
     	   mPayIntent.putExtra("menuItem", menuItem);
     	   mPayIntent.putExtra("listHeader", "Corporate Fund Transfer");
          	mPayIntent.putExtra("more", false);
          	return mPayIntent;
       }
  
    
    public Intent getSearch(String heading,Context context){
        String[] menuItem = {"Pincode","Location"};//changed by S
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 37);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", heading);
         	mPayIntent.putExtra("more", false);
         	return mPayIntent;
        
    }
 
    public Intent getPaymentOptions(Context context){
        boolean minFlag    = dynamiBillArr[1][0].trim().toUpperCase().equals("Y");
        boolean partFlag   = dynamiBillArr[2][0].trim().toUpperCase().equals("Y");
        boolean excessFlag = dynamiBillArr[3][0].trim().toUpperCase().equals("Y");
        String[] array = null;
        if(minFlag && partFlag && excessFlag){//111
            String[] menuItem = {"Full Payment","Minimum Payment","Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && partFlag && !excessFlag){//110
            String[] menuItem = {"Full Payment","Minimum Payment","Part Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && !excessFlag){//100
            String[] menuItem = {"Full Payment","Minimum Payment"};
            array = menuItem;
        }else if(!minFlag && !partFlag && !excessFlag){//000
            String[] menuItem = {"Full Payment"};
            array = menuItem;
        }else if(!minFlag && !partFlag && excessFlag){//001
            String[] menuItem = {"Full Payment","Excess Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && !excessFlag){//010
            String[] menuItem = {"Full Payment","Part Payment"};
            array = menuItem;
        }else if(!minFlag && partFlag && excessFlag){//011
            String[] menuItem = {"Full Payment","Part Payment","Excess Payment"};
            array = menuItem;
        }else if(minFlag && !partFlag && excessFlag){//101
            String[] menuItem = {"Full Payment","Minimum Payment","Excess Payment"};
            array = menuItem;
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 41);
 	   mPayIntent.putExtra("menuItem", array);
 	   mPayIntent.putExtra("listHeader", "Payment Options");
      	mPayIntent.putExtra("more", false);
      	return mPayIntent;
     
    }
    

    public Intent getRegisteredCFTList(String title,String flag,Context context){
        this.modeFlag = flag;
        String[] menuItem = new String[allRegList.length];
        for(int i = 0;i < allRegList.length;i++){
            menuItem[i] = allRegList[i][0].substring(0,allRegList[i][0].indexOf("-"));
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 19);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", title);
 	   if(moreFlag){
        	mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
     return mPayIntent;
        
    }
    

    public Intent getCFTDistributorList(String title,Context context){
        String[] menuItem = new String[cftDistributorList.length];
        for(int i = 0;i < cftDistributorList.length;i++){
            menuItem[i] = cftDistributorList[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 20);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", title);
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;
        
    }
    
    public String getAccountTypes(String tempData,String matchedString){
    	
    	// Changes done by Siva G MID: 10403
        String tempArr []  = tempData.split("#");
        String acctypename="";
        for (int i = 0; i <= tempArr.length-1; i++) {
            if(tempArr[i].startsWith(matchedString)){
            	acctypename = tempArr[i]; 
            }
        } 
        if(acctypename != null && !acctypename.equals("")){
        	acctypename = acctypename.substring(acctypename.indexOf('*')+1);
        	acctypename = acctypename.substring(0,acctypename.indexOf('*'));
        }else{
        	acctypename = 	matchedString;
        }
    	return acctypename;
    }

    public Intent getIfscBankList(Context context){
        String[] menuItem = new String[IFSCList.length];
        for(int i = 0; i < IFSCList.length; i++){
            menuItem[i] = IFSCList[i][0]; //
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 68);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Bank List");
   	   if(moreFlag){
          	mPayIntent.putExtra("more", true);
          }else{
          	mPayIntent.putExtra("more", false);
          }
       return mPayIntent;
       
    }
    

    public Intent getBankLocationList(Context context) {
        String[] menuItem = new String[locationList.length];
        for(int i = 0; i < locationList.length; i++){
            menuItem[i] =locationList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 69);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Location List");
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
    }
    
    public Intent getCardHot_CARD_List(Context context) {
        String[] menuItem = new String[StaticStore.midlet.CardhotlistDet.length];
        for(int i = 0; i < StaticStore.midlet.CardhotlistDet.length; i++){
            menuItem[i] =StaticStore.midlet.CardhotlistDet[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 249);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Select Card");
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
    }
    public Intent getAtmAddresses(Context context){
        String[] menuItem = new String[atmSearch.length];
        //StaticStore.LogPrinter('i',"More lag:::"+moreFlag);
        int s= Integer.parseInt(StaticStore.midlet.nextStartRecNumber.trim())- atmSearch.length;
        for(int i = 0; i < atmSearch.length; i++){
            menuItem[i] = "ATM "+(i +s);
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 70);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "ATM List");
 	   if(moreFlag){
        	mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
     return mPayIntent;
        
    }

    public Intent getBranchSearch(Context context){
        String[] menuItem = new String[branchSearch.length];
        int s= Integer.parseInt(StaticStore.midlet.nextStartRecNumber.trim())- branchSearch.length;
        for(int i = 0; i < branchSearch.length; i++){
        	menuItem[i] = "Branch "+(i + s);
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 71);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Branch List");
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;       
    }
    
    public Intent geteLobbySearch(Context context){
        String[] menuItem = new String[eLobbySearch.length];
        int s= Integer.parseInt(StaticStore.midlet.nextStartRecNumber.trim())- eLobbySearch.length;
        for(int i = 0; i < eLobbySearch.length; i++){
        	menuItem[i] = "E-Lobby "+(i + s);
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 276);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "E-Lobby List");
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;       
    }


    public Intent getIFSCList(Context context){
        String[] menuItem = new String[ifscArray.length];
        for(int i = 0; i < ifscArray.length; i++){
            menuItem[i] = ifscArray[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
       	if(StaticStore.isSearchAndRegister){
          	 mPayIntent.putExtra("listIndex", 278);
       	}else{
           mPayIntent.putExtra("listIndex", 73);
       	}
        mPayIntent.putExtra("menuItem", menuItem);
        mPayIntent.putExtra("listHeader", "IFSC List");//Changed by S
        if(moreFlag){
            mPayIntent.putExtra("more", true);
        }else{
            mPayIntent.putExtra("more", false);
        }
        return mPayIntent;
     }
    

    public Intent getCustomerMneNameList(Context context) {
        String[] menuItem = new String[billpayCustMneName.length];
        for(int i = 0; i < billpayCustMneName.length; i++){
            menuItem[i] = billpayCustMneName[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 74);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Biller List"); //mid:12873  Registered Biller List 
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
    }

    public Intent getWithInBankBenDetails(Context context) {
        String[] menuItem = new String[withInBankBenDetails.length];
        for(int i = 0; i < withInBankBenDetails.length; i++){
            menuItem[i] = withInBankBenDetails[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 78);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Beneficiary List");// mid: 12745 Select Beneficiary
 	   if(moreFlag){
        	mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
     return mPayIntent;
       
       
    }
    
    public Intent getVmtRegList(Context context,String form) {
		String[] menuItem = new String[vmtRegList.length];
		for (int i = 0; i < menuItem.length; i++) {
			menuItem[i] = vmtRegList[i][0];
		}
		   mPayIntent = new Intent(context, ListSelection.class);
	 	   
	 	   mPayIntent.putExtra("menuItem", menuItem);
	 	   mPayIntent.putExtra("listHeader", "Beneficiary List");// mid: 12745 Select Beneficiary
		if (moreFlag) {
			if (form.equals("V2")) {				
				 mPayIntent.putExtra("listIndex", 107);
		        	mPayIntent.putExtra("more", true);
			} else if (form.equals("V4")) {
				 mPayIntent.putExtra("listIndex", 108);
		        	mPayIntent.putExtra("more", false);
			}
		} else {
			if (form.equals("V2")) {
				 mPayIntent.putExtra("listIndex", 107);
		        	mPayIntent.putExtra("more", true);
			} else if (form.equals("V4")) {
				 mPayIntent.putExtra("listIndex", 108);
		        	mPayIntent.putExtra("more", false);
			}
		}
     
 	   if(moreFlag){
 		 
        }else{
        
        }
     return mPayIntent;
       
       
    }
    public Intent getP2UFTRegList(Context context,int index) {
		  String[] menuItem = new String[P2UFTRegList.length];
	        for(int i = 0; i < P2UFTRegList.length; i++){
	            menuItem[i] = P2UFTRegList[i][0];
	        }
	        mPayIntent = new Intent(context, ListSelection.class);
	 	   mPayIntent.putExtra("listIndex", index);
	 	   mPayIntent.putExtra("menuItem", menuItem);
	 	   mPayIntent.putExtra("listHeader", "Beneficiary List");
	 	   if(moreFlag){
	        	mPayIntent.putExtra("more", true);
	        }else{
	        	mPayIntent.putExtra("more", false);
	        }
	     return mPayIntent;
	       
	       
	}

    public Intent getNeftBenDetails(Context context) {
        String[] menuItem = new String[neftBenDetails.length];
        for(int i = 0; i < neftBenDetails.length; i++){
            menuItem[i] = neftBenDetails[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 79);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Beneficiary List");// mid: 12745 Select Beneficiary
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;
       
    }
    

    
    public Intent getNoOfChequeLeaf(Context context){
        String[] menuItem ={"25","50","100"};
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 137);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader","Select Number of Cheque Leaf");//changed by S
      	mPayIntent.putExtra("more", false);
      	return mPayIntent;
        
    }
    
    
   
    public Intent getICashMenu(Context context){
    	String[] menuItem = {"Remittance","Status","Forgot PIN"};
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 85);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader","eCash");
       	mPayIntent.putExtra("more", false);
       	return mPayIntent;
        
    }

    public Intent getICashStatus(Context context){
        String[] menuItem = new String[iCashStatus.length];
        for(int i = 0; i < iCashStatus.length; i++){
            menuItem[i] = "Rs."+iCashStatus[i][1]+" on "+iCashStatus[i][0];
        }
        StaticStore.LogPrinter('i',">>>>>menuItem"+menuItem.length);
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 86);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Status");
   	   if(moreFlag){
          	mPayIntent.putExtra("more", true);
          }else{
          	mPayIntent.putExtra("more", false);
          }
   	   StaticStore.LogPrinter('i',"INSIDE METHOD"+mPayIntent);
       return mPayIntent;
        
       
    }
    
    public Intent getICashForgetPin(Context context){
        String[] menuItem = new String[iCashForgetPin.length];
        for(int i = 0; i < iCashForgetPin.length; i++){
            menuItem[i] = "Rs."+iCashForgetPin[i][2]+" on "+iCashForgetPin[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 87);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", "Forgot PIN");
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
        
    }

    public Intent getICashCancel(Context context){
        String[] menuItem = new String[iCashCancel.length];
        for(int i = 0; i < iCashCancel.length; i++){
            menuItem[i] = "Rs."+iCashCancel[i][2]+" on "+iCashCancel[i][1];
        }
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 88);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader", "Cancel eCash");
 	   if(moreFlag){
        	mPayIntent.putExtra("more", true);
        }else{
        	mPayIntent.putExtra("more", false);
        }
     return mPayIntent;
     
    }
    
    public Intent getOnlineFTRegList(Context context,String title,int index){
        
        String[] menuItem = new String[onlineFTRegList.length];
        for(int i = 0;i<menuItem.length;i++){
            menuItem[i] = onlineFTRegList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", index);//89, w3
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", title);
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;
       
    }
    public Intent getMerchImpsDetails(Context context) {
		String[] menuItem = new String[StaticStore.midlet.impsDetails.length];
		for (int i = 0; i < StaticStore.midlet.impsDetails.length; i++) {
			menuItem[i] = StaticStore.midlet.impsDetails[i][0];
		}
		 mPayIntent = new Intent(context, ListSelection.class);
	  	   mPayIntent.putExtra("listIndex", 242);//89, w3
	  	   mPayIntent.putExtra("menuItem", menuItem);
	  	   mPayIntent.putExtra("listHeader","Merchant List"); //mid: 12745 Select Merchant
	  	   if(moreFlag){
	         	mPayIntent.putExtra("more", true);
	         }else{
	         	mPayIntent.putExtra("more", false);
	         }
	      return mPayIntent;
		
	}
	 public Intent getOnlineAccPaymentList(Context context){
        
        String[] menuItem = new String[onlineAccPaymentList.length];
        for(int i = 0;i<menuItem.length;i++){
            menuItem[i] = onlineAccPaymentList[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 110);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader", "Beneficiary List");
  	   if(moreFlag){
         	mPayIntent.putExtra("more", true);
         }else{
         	mPayIntent.putExtra("more", false);
         }
      return mPayIntent;
       
    }
 public Intent getOnlineAccDeRegList(Context context){
     
     String[] menuItem = new String[onlineAccDeRegList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = onlineAccDeRegList[i][0];
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 111);
	   mPayIntent.putExtra("menuItem", menuItem);
	   mPayIntent.putExtra("listHeader", "Beneficiary List");
	   if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
   return mPayIntent;
    
 }
public Intent getonlineAccDetailsList(Context context){
     
     String[] menuItem = new String[onlineAccDetailsList.length];
     for(int i = 0;i<menuItem.length;i++){
         menuItem[i] = onlineAccDetailsList[i][0];
         StaticStore.LogPrinter('i'," menuItem[i] > > >> >> "+ menuItem[i]);
     }
     mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", 112);
	   mPayIntent.putExtra("menuItem", menuItem);
	   mPayIntent.putExtra("listHeader", "Beneficiary List");
	   if(moreFlag){
      	mPayIntent.putExtra("more", true);
      }else{
      	mPayIntent.putExtra("more", false);
      }
   return mPayIntent;
    
 }
public Intent getonlineAccTypeList(Context context,int typeindex){
	String[] menuItem = { "Savings A/C", "Current A/C","Overdraft", "Cash Credit","Loan A/C"};
    mPayIntent = new Intent(context, ListSelection.class);
	   mPayIntent.putExtra("listIndex", typeindex);
	   mPayIntent.putExtra("menuItem", menuItem);
	   mPayIntent.putExtra("listHeader","A/C Types");
   	mPayIntent.putExtra("more", false);
   	return mPayIntent;
    
}

    public Intent getRegIMPSAccs(Context context){

        String[] menuItem = new String[regIMPSAccs.length];
        for(int i = 0;i<menuItem.length;i++){
            //menuItem[i] = "XXXXXXXX"+regIMPSAccs[i][0];
        	menuItem[i] = regIMPSAccs[i][0];  // Removed the Masking
        }
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 91);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader", "Select A/C");
   	   if(moreFlag){
          	mPayIntent.putExtra("more", true);
          }else{
          	mPayIntent.putExtra("more", false);
          }
       return mPayIntent;
        
    }
    
    public Intent getWithinRegList(int index,String heading,Context context){
        
        String[] menuItem = new String[StaticStore.withinDereg.length];
        for(int i = 0;i<menuItem.length;i++){
            menuItem[i] = StaticStore.withinDereg[i][0];
        }
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", index);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader", heading);
    	   if(moreFlag){
           	mPayIntent.putExtra("more", true);
           }else{
           	mPayIntent.putExtra("more", false);
           }
        return mPayIntent;
      
    }
    
    public Intent getAirTitle(Context context){
        String[] menuItem = {"Mr.","Mrs.","Ms."};       
        mPayIntent = new Intent(context, ListSelection.class);       
        mPayIntent.putExtra("listIndex", 103);
        mPayIntent.putExtra("menuItem", menuItem);
        mPayIntent.putExtra("listHeader", StaticStore.airlineTitle);
        mPayIntent.putExtra("more", false);
        
        //  ftMenu = new ListCanvas(this,getDisplay(),"Fund Transfer",menuItem,8,false,false,false);        
        return mPayIntent;
    }
    
    public void pushCurrentDisplay(int index){
        if(StaticStore.indexCtr < 6 && index != 44 && index != 90 && index != 66 && index != 82 && index != 27 && index != 28 && index != 41 && index != 40 &&
           index != 29 && index != 30 && index != 93 && index != 94 && index != 46 && index != 89 && index != 35 && index != 61 && 
           index != 83 && index != 13 && index != 19 && index != 20 && index != 84){
         //   StaticStore.previousIndex[StaticStore.indexCtr] = getDisplay().getCurrent();
            if(index != 99)
            StaticStore.indexCtr++;
        }
    }
    

    public void sendAirlineSMS() {
        String sendSMSString = "APAR;Y;";
        for(int i = 0;i<StaticStore.airValues.length;i++){
          StaticStore.airValues[i] = StaticStore.airValues[i].equals("")?" ":StaticStore.airValues[i];
            
            if(i == 1){
                sendSMSString += StaticStore.airlineID + ";";
            }
            if(i == 0){
                // for removing mPIN in all modules - 10-09-2009
                StaticStore.airValues[0] = StaticStore.globalMPIN;
        //        RsaEncryption objRSA = new RsaEncryption(StaticStore.airValues[0]);
          //      sendSMSString += objRSA.cipherText + ";";
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
       // sendSMSString = sendSMSString.substring(0,sendSMSString.length() - 1) + ";"+StaticStore.bankCode+";"+StaticStore.duckPT+";"+StaticStore.version+";"+StaticStore.selectedAccNumber+";"+new Date().getTime();; //comment for use
        sendSMSString  = sendSMSString.replace('@',':');
        sendSMSString  = sendSMSString.replace('_','%');
        //StaticStore.LogPrinter('i',"SMS String:::"+sendSMSString);
        if(!StaticStore.IsGPRS){
            String smsNumber = "";
            if(StaticStore.isShortCode){
                smsNumber = StaticStore.shortCodeNumber;
            }else{
                smsNumber = StaticStore.VMN_Number;
            }
           // sendSMS         =   new SMSSend(smsNumber, sendSMSString);//comment for use
        }else{
//            sendSMSString = replaceSpace(sendSMSString,"%20",' ');
            sendSMSString = replaceSpace(sendSMSString,"$",':');
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
        }
        //httpConnect       = new HttpConnect("http://10.44.3.25:7001/MBank/servlet/MQSender?request=BE%201144",false,midlet);
      //  thread          =   new Thread(this);//comment for use
        //thread.start();
    }
    
    public Intent doAction(Context context,int i, int selectedIndex) {
        if(i == 99) {
            //  To go in to Displayable canvas
            
         //   previousDisplayable = getDisplay().getCurrent(); //comment for use
            try{
                /* The following condition -1 is used to check whether a record is there or not. if record is not there means it will return
                 -1 to the user.So here we avoid array index out of bounsd exception*/
            	 //comment for use
                if(selectedIndex != -1){
                    String subStr = responseInboxMessages[selectedIndex];
                    try{
						get_RespectiveScreens(context , subStr.substring(1));
					}catch(Exception e){
						e.printStackTrace();
					}
                   
                    if(subStr.substring(0, 1).equals("Y")) {
                        responseInboxMessages[selectedIndex] = "N" + subStr.substring(1);
                        getReplacedArray();
                        //RmsInbox.updateRecord(subStr, RmsInbox.ResponseInbox, Integer.parseInt(subStr.substring(0, 2)));
                        responseMessages = RmsStore.updateInboxRecordStore(responseMessages, RmsStore.parsedRecords);
                    }
                    inResponseInbox = true;
                }
            }catch(ArrayIndexOutOfBoundsException e){
            	e.printStackTrace();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return mPayIntent;
    }
    
    public void doAlertProcess(int tempIndex) {
        if(tempIndex == 99) {
            //  Delete single messages
            int pritSub;
            if(responseMessages != null) {
                if(responseMessages.length > 0) {
               //     pritSub = ResponseInbox.getSelectedIndex(); //comment for use
                    /*
                    String[] order = RmsInbox.ArrangeAllOrder(RmsInbox.ResponseInbox);
                    RmsInbox.deleteOneByOneRecordInInbox(Integer.parseInt(subStr.substring(0, 2)),
                            RmsInbox.ResponseInbox, Integer.parseInt(subStr.substring(subStr.length() - 2)), order);
                     */
                 //   responseMessages    =   RmsStore.deleteInboxRecordStore(responseMessages, pritSub, ResponseInbox.size(), responseInboxMessages[pritSub], RmsStore.ResponseInbox); //comment for use
                }
            }
            
           // selectedIndex = (byte)ResponseInbox.getSelectedIndex(); //comment for use
            //getDisplay().setCurrent(get_ResponseInbox()); //comment for use
            //if(selectedIndex == ResponseInbox.size() && ResponseInbox.size() != 0) //comment for use
          //      ResponseInbox.setSelectedIndex(ResponseInbox.getSelectedIndex() - 1); //comment for use
            	 //comment for use
            /*else if(ResponseInbox.size() != 0)
                ResponseInbox.setSelectedIndex(ResponseInbox.getSelectedIndex());*/
            
        } else if(tempIndex == 100) {
            //  Delete all messages
            
            if(responseMessages != null) {
                if(responseMessages.length > 0) {
            //--        RmsStore.deleteRecord(RmsStore.ResponseInbox);
                    responseMessages    =   null;
                }
            }
            //getDisplay().setCurrent(get_ResponseInbox()); //comment for use
        }
    }
    
    public String replaceSpace(String str,String replaceStr,char originalChar){
        char[] chars = str.toCharArray();
        String temp = "";
        for(int i = 0; i < str.length();i++){
            if(chars[i] == originalChar){
                temp += replaceStr;
            }else{
                temp += chars[i];
            }
        }
        
        return temp;
    }
    
    public void run() {
        boolean waitThread = true;
        boolean firstTime = true;
        if(!StaticStore.IsGPRS){
            // boolean smsType = ( sendSMS == null ? false : true);
            
           // boolean smsType = ( sendSMS == null ? false : true); //comment for use
            Thread thisThread = Thread.currentThread();
            
            while(thisThread == thread && waitThread) {
                if(firstTime) {
                    firstTime   =   false;
                    StaticStore.SentRequest             =   "AR";
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }                
            }
            
            thread = null;
        }else{
            
            
            Thread thisThread = Thread.currentThread();
            
            while(thisThread == thread && waitThread) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }                
                
                if(firstTime) {
                    firstTime   =   false;
                    StaticStore.SentRequest = "AR";
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }                
            }
            
            thread  =   null;
        }
    }
    

    public Intent getFTOptions(Context context){
        String[] menuItem = {"Mobile-to-Mobile","Mobile-to-Account"};        
        mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 9);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader","Fund Transfer - Within TMB");
        mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
    
 
    public Intent getFTRegistrationMenu(String header , Context context){
    	String[] menuItem = {"Instant Pay","Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary"};
        mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 10);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader",header);
        mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
    
    public Intent getFTRegistrationMenuM2A(String header , Context context){
    	String[] menuItem = {"Instant Pay","Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary"};
        mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 211);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader",header);
       	mPayIntent.putExtra("more", false);
        return mPayIntent;
    }
 

    public Intent getSaveinApplication(Context context){
    	String[] menuItem = {"Yes","No"};        
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 15);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader","Save Message in Inbox");
    	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
         	mPayIntent.putExtra("more", false);
         	return mPayIntent;

    }

    
    public Intent getCommunicationMode(Context context){
        String[] menuItem = {"GPRS","SMS"};
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 16);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader","Communication Mode");
 	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
      	mPayIntent.putExtra("more", false);
      	return mPayIntent;
    }

    
    Intent getLocatorMenu(Context context){
    	isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_atm,R.drawable.li_branches_locator,R.drawable.li_elobby};
    	String[] menuItem = {"ATM","Branch","E-Lobby"}; 
    	mPayIntent = new Intent(context, ListSelection.class);
    	mPayIntent.putExtra("listIndex", 36);
    	mPayIntent.putExtra("menuItem", menuItem);
    	mPayIntent.putExtra("listHeader","Locator");//changed by S ATM / Branch 
    	//----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
    	mPayIntent.putExtra("more", false);
    	mPayIntent.putExtra("isImageTextList", true);
    	return mPayIntent;
    }
    
    Intent get_ProductsMenu(Context context){
    	isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_offer,R.drawable.li_loan_calculator};//R.drawable.li_accountop,
    	
        String[] menuItem = {"Offers", "Loan Calculator"}; //"New Account Opening",
        mPayIntent = new Intent(context, ListSelection.class);
  	   mPayIntent.putExtra("listIndex", 138);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader","Products");//changed by S
  	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
       	mPayIntent.putExtra("more", false);
       	mPayIntent.putExtra("isImageTextList", true);
       	return mPayIntent;
    }
    
    public Intent getReprintMenu(Context context){
        String[] menuItem = {"Booking & Payment","Payment Only"};
        mPayIntent = new Intent(context, ListSelection.class);
   	   mPayIntent.putExtra("listIndex", 39);
   	   mPayIntent.putExtra("menuItem", menuItem);
   	   mPayIntent.putExtra("listHeader","Reprint Ticket");   	 
        	mPayIntent.putExtra("more", false);
        	return mPayIntent;
    }

    public Intent getOtherFTOptions(String heading,Context context){
    	 String[] menuItem = {"Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary","IFSC/Bank Search"};//"Instant Pay",
         mPayIntent = new Intent(context, ListSelection.class);
    	 mPayIntent.putExtra("listIndex", 42);
    	 mPayIntent.putExtra("menuItem", menuItem);
    	 mPayIntent.putExtra("listHeader",heading);
    	 mPayIntent.putExtra("more", false);
         return mPayIntent;
    }
    
   
    public Intent getIMPSOptionMenu(Context context){
        isImageTextList = true;
    	imagesListArray = new int[] {R.drawable.li_mobile_to_mobile,R.drawable.li_mobile_to_account,R.drawable.li_aadhaarno,
    			R.drawable.li_imps_merchant_payment,R.drawable.li_generate_otp,R.drawable.li_mmid,R.drawable.li_retrive_mmid,R.drawable.li_cancel_mmid};
    	
    	String[] menuItem ={"Fund Transfer - To Mobile No.","Fund Transfer - To Account No.","Fund Transfer - To AADHAAR No.",
    			"IMPS Merchant Payment","Generate OTP","Generate MMID","Retrieve MMID","Cancel MMID"};
      
        mPayIntent = new Intent(context, ListSelection.class);
 	    mPayIntent.putExtra("listIndex", 106);
 	    mPayIntent.putExtra("menuItem", menuItem);
 	    mPayIntent.putExtra("listHeader","IMPS"); 	 
      	mPayIntent.putExtra("more", false);
      	mPayIntent.putExtra("isImageTextList", true);
      	
      	return mPayIntent;
    }
  
    
    public Intent getRegisteredIMPSAccOptions(Context context){
          String[] menuItem = {"Instant Pay","Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary"};
          mPayIntent = new Intent(context, ListSelection.class);
  	      mPayIntent.putExtra("listIndex", 118);
  	      mPayIntent.putExtra("menuItem", menuItem);
  	      mPayIntent.putExtra("listHeader","Fund Transfer - To Account No."); 	 
          mPayIntent.putExtra("more", false);
       	  return mPayIntent;
    }
   public Intent getRegisteredMerchantIMPSOptions(Context context){
         String[] menuItem = {"Instant Pay","Add Merchant","Pay Merchant","Merchant Details","Delete Merchant"};
   	     mPayIntent = new Intent(context, ListSelection.class);
         mPayIntent.putExtra("listIndex", 237);
      	 mPayIntent.putExtra("menuItem", menuItem);
      	 mPayIntent.putExtra("listHeader", "IMPS Merchant Payment");
      	 mPayIntent.putExtra("more", false);
         return mPayIntent;
   }
    
    
    public Intent getRegisteredIMPSOptions(Context context) {
  	    String[] menuItem = {"Instant Pay","Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary"};
    	mPayIntent = new Intent(context, ListSelection.class);
        mPayIntent.putExtra("listIndex", 114);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Fund Transfer - To Mobile No.");
       	mPayIntent.putExtra("more", false);
       return mPayIntent;
    }
    public Intent getIMPSCancelOptions(Context context){

    	 String[] menuItem ={"Single A/C","All Accounts"};
        mPayIntent = new Intent(context, ListSelection.class);//changed by S
  	   mPayIntent.putExtra("listIndex", 212);
  	   mPayIntent.putExtra("menuItem", menuItem);
  	   mPayIntent.putExtra("listHeader","Cancel MMID"); 	 
       	mPayIntent.putExtra("more", false);
       	return mPayIntent;
    }
    

    public Intent initiateUserOption(Context context) {
//    	Sysout
    	StaticStore.LogPrinter('i',"initiateUserOption StaticStore.isOTPBuild ==> "+StaticStore.isOTPBuild);
    	StaticStore.LogPrinter('i',"initiateUserOption StaticStore.IsPermitted ==> "+StaticStore.IsPermitted);
    	StaticStore.LogPrinter('i',"initiateUserOption StaticStore.IsPersonalInfoGot ==> "+StaticStore.IsPersonalInfoGot);
    	StaticStore.LogPrinter('i',"initiateUserOption StaticStore.isOTPVerified ==> "+StaticStore.isOTPVerified);
    	 StaticStore.FromGridActivation = true;
    	if(StaticStore.isOTPBuild)
    	{
    		StaticStore.LogPrinter('i',"Came into the OTP Build");
    		if(!StaticStore.IsPermitted && !StaticStore.IsPersonalInfoGot  && !StaticStore.isOTPVerified)
    		{
    			StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
    			StaticStore.FromListScreen = false;
    			StaticStore.menuDesc[220] = new String []{"OTP Generation","APBO;Y","Mobile No.",StaticStore.mPINString,"10-10-N-N-N","4-4-N-Y-Y","2","true","true","Y"};
    		    StaticStore.tagType = "APBO";
			    StaticStore.index = 220;
			 mPayIntent = new Intent(context,DynamicCanvas.class);
    		}else if(StaticStore.IsPermitted && StaticStore.selectedLoginGridIndex == 1 && StaticStore.isOTPVerified && !StaticStore.isForgotPassword){ //&& StaticStore.IsPersonalInfoGot
    			mPayIntent = new Intent(context,GridScreenView.class);
    		}else if(StaticStore.IsPermitted && StaticStore.isOTPVerified && StaticStore.isForgotPassword){
    			StaticStore.isForgotPassword = false;
    			StaticStore.FromListScreen = true;
   				StaticStore.index = 115;
   				mPayIntent = new Intent(context, DynamicCanvas.class);
    		}else
    		{
    			if (!StaticStore.istablet) {
    				StaticStore.gridNofragHeader.setVisibility(View.GONE);
    				StaticStore.gridNolineHeader.setVisibility(View.GONE);
    			}
				mPayIntent = new Intent(context,GridScreenViewActivation.class);
    		}
    		 return mPayIntent;
    	}else{
    		if (!StaticStore.istablet) {
    			StaticStore.gridNofragHeader.setVisibility(View.GONE);
    			StaticStore.gridNolineHeader.setVisibility(View.GONE);
    		}
    	mPayIntent = new Intent(context,GridScreenViewActivation.class);
    	return mPayIntent;
    	}

    }
    
    
  
    public Intent getOtherFTTypes(Context context){
         String[] menuItem = { "NEFT"};
         mPayIntent = new Intent(context, ListSelection.class);
   	     mPayIntent.putExtra("listIndex", 43);
   	     mPayIntent.putExtra("menuItem", menuItem);
   	     mPayIntent.putExtra("listHeader","Fund Transfer - Other Bank"); 	 
         mPayIntent.putExtra("more", false);
         return mPayIntent;
    }
    public Intent getInstMenu(Context context){
        String[] menuItem ={"Fees Payment"};//"Registration", "Fees Payment", "Deregistration"
        mPayIntent = new Intent(context, ListSelection.class);
    	   mPayIntent.putExtra("listIndex", 54);
    	   mPayIntent.putExtra("menuItem", menuItem);
    	   mPayIntent.putExtra("listHeader","Institution Fees Payment"); 	 
         	mPayIntent.putExtra("more", false);
         	return mPayIntent;
    }
    public Intent getP2UMenus(Context context){
        String[] menuItem = {"Instant Pay","Add Beneficiary","Pay Beneficiary","Beneficiary Details","Delete Beneficiary"};
    	mPayIntent = new Intent(context, ListSelection.class);
        mPayIntent.putExtra("listIndex", 213);
       	mPayIntent.putExtra("menuItem", menuItem);
       	mPayIntent.putExtra("listHeader", "Fund Transfer - To AADHAAR No.");
       	mPayIntent.putExtra("more", false);
       return mPayIntent;
    } 
    
    
    public Intent getAirlineTimings(Context context){
        String[] menuItem ={"Morning Flights","Afternoon Flights","Evening Flights","Late Night Flights"};
        mPayIntent = new Intent(context, ListSelection.class);
 	   mPayIntent.putExtra("listIndex", 72);
 	   mPayIntent.putExtra("menuItem", menuItem);
 	   mPayIntent.putExtra("listHeader","Booking & Payment"); 	 
      	mPayIntent.putExtra("more", false);
      	return mPayIntent;
    }

    public Intent getIFSCSearchMenu(Context context){
        String[] menuItem = {"IFSC Search","Bank Search"};
        mPayIntent = new Intent(context, ListSelection.class);
  	    mPayIntent.putExtra("listIndex", 76);
  	    mPayIntent.putExtra("menuItem", menuItem);
  	    mPayIntent.putExtra("listHeader","IFSC/Bank Search"); 	 
       	mPayIntent.putExtra("more", false);
       	return mPayIntent;
    }

	public String maskedAccNumber(String accNumber) {
		//0018 XXX484 001
		String prsgTemp	=	"";
			try {
				prsgTemp	+=	accNumber.substring(0, 4);
				prsgTemp	+=	"XXX";
//				prsgTemp	+=	accNumber.substring(accNumber.length() - 6);
				prsgTemp	+=	accNumber.substring(7,accNumber.length());
			} catch (Exception e) {
				return accNumber;
			}
		return prsgTemp;
	}
	
	public String maskedAccNumberBE(String accNumber) {
		//0018 XXX484 001
		String prsgTemp	=	"";
			try {
				prsgTemp	+=	accNumber.substring(0, 4);
				prsgTemp	+=	"XXX";
//				prsgTemp	+=	accNumber.substring(accNumber.length() - 9);
				prsgTemp	+=	accNumber.substring(7,accNumber.length());
			} catch (Exception e) {
				return accNumber;
			}
		return prsgTemp;
	}
	
	public String maskedAccNumberWB(String accNumber) {
		// 1238 XXXXXX 123
		String prsgTemp = "";
		try {
			prsgTemp += accNumber.substring(0, 3);
			int accCount = accNumber.substring(3, accNumber.length() - 3).length();
			for(int i = 0;i < accCount; i++){
				prsgTemp += "X";
			}
			prsgTemp += accNumber.substring(accNumber.length() - 3, accNumber.length());
		} catch (Exception e) {
			return accNumber;
		}
		return prsgTemp;
	}
	
	public String serverControl(String temp, String splitter) {

		byte index = 0;
		index = (byte) temp.indexOf(splitter);
		StaticStore.beFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.msFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.m2mFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.m2aFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.neftFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.impsFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.stopChequeFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.chequeStatusFlag = temp.substring(0, index).trim()
				.equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.chequeBookFlag = temp.substring(0, index).trim().equals(
				"1");

		/*temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.DDFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.debitCardFlag = temp.substring(0, index).trim()
				.equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.stmtRqPrintFlag = temp.substring(0, index).trim().equals(
				"1");*/

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.newLeadFlag = temp.substring(0, index).trim().equals(
				"1");

		/*temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.loanFlag = temp.substring(0, index).trim()
				.equals("1");*/

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.fixedDepositFlag = temp.substring(0, index).trim()
				.equals("1");
		
		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.cardHotListFlag = temp.substring(0, index).trim().equals(
				"1");
		/*temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.eBankServiceFlag = temp.substring(0, index).trim().equals(
				"1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.eBankPinFlag = temp.substring(0, index).trim().equals(
				"1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.smsBankingFlag = temp.substring(0, index).trim().equals("1");*/

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.billpayFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.adHocPaymentFlag = temp.substring(0, index).trim().equals(
				"1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.airlineFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.movieFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.mobileRechargeFlag = temp.substring(0, index).trim()
				.equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.donationFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.feesFlag = temp.substring(0, index).trim().equals("1");

		/*temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.corpratePaymentFlag = temp.substring(0, index).trim()
				.equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.shopingFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.insurancePremiumFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.ASBAFlag = temp.substring(0, index).trim().equals(
				"1");*/

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.ATMlocatorFlag = temp.substring(0, index).trim().equals("1");

		temp = temp.substring(index + 1);
		index = (byte) temp.indexOf(splitter);
		StaticStore.branchLocatorFlag = temp.substring(0, index).trim().equals("1");
		
//		temp = temp.substring(index + 1);
//		index = (byte) temp.indexOf(splitter);
//		StaticStore.eLobbyLocatorFlag = temp.substring(0, index).trim().equals("1");
		
     	return temp;

	}

	public void writeAccountInMemory() {
		StaticStore.selectedAccNumber = StaticStore.accountNumbers[0] ;
		StaticStore.selectedAccType = StaticStore.accType[0] ;
		
		RmsStore.writeRecordStore(StaticStore.numberOfAccounts + "~"
				+ StaticStore.selectedAccNumber + "*" + StaticStore.selectedAccType + "~"
//				+ StaticStore.accountNumbers[0] + "*" + StaticStore.accType[0] + "~"
//				+ StaticStore.accountNumbers[1] + "*" + StaticStore.accType[1] + "~"
//				+ StaticStore.accountNumbers[2] + "*" + StaticStore.accType[2] + "~"
//				+ StaticStore.accountNumbers[3] + "*" + StaticStore.accType[3] + "~"
//				+ StaticStore.accountNumbers[4] + "*" + StaticStore.accType[4] + "~"
//				+ StaticStore.accountNumbers[5] + "*" + StaticStore.accType[5] + "#"
				
				+ StaticStore.accountNumbers[0] + "*" + StaticStore.accType[0] + "~"
				+ StaticStore.accountNumbers[1] + "*" + StaticStore.accType[1] + "~"
				+ StaticStore.accountNumbers[2] + "*" + StaticStore.accType[2] + "~"
				+ StaticStore.accountNumbers[3] + "*" + StaticStore.accType[3] + "~"
				+ StaticStore.accountNumbers[4] + "*" + StaticStore.accType[4] + "~"
				+ StaticStore.accountNumbers[5] + "*" + StaticStore.accType[5] + "~"
				+ StaticStore.accountNumbers[6] + "*" + StaticStore.accType[6] + "~"
				+ StaticStore.accountNumbers[7] + "*" + StaticStore.accType[7] + "~"
				+ StaticStore.accountNumbers[8] + "*" + StaticStore.accType[8] + "~"
				+ StaticStore.accountNumbers[9] + "*" + StaticStore.accType[9] + "~"
				+ StaticStore.accountNumbers[10] + "*" + StaticStore.accType[10] + "~"
				+ StaticStore.accountNumbers[11] + "*" + StaticStore.accType[11] + "~"
				+ StaticStore.accountNumbers[12] + "*" + StaticStore.accType[12] + "~"
				+ StaticStore.accountNumbers[13] + "*" + StaticStore.accType[13] + "~"
				+ StaticStore.accountNumbers[14] + "*" + StaticStore.accType[14] + "~"
				+ StaticStore.accountNumbers[15] + "*" + StaticStore.accType[15] + "~"
				+ StaticStore.accountNumbers[16] + "*" + StaticStore.accType[16] + "~"
				+ StaticStore.accountNumbers[17] + "*" + StaticStore.accType[17] + "~"
				+ StaticStore.accountNumbers[18] + "*" + StaticStore.accType[18] + "~"
				+ StaticStore.accountNumbers[19] + "*" + StaticStore.accType[19] + "~"
				+ StaticStore.accountNumbers[20] + "*" + StaticStore.accType[20] + "~"
				+ StaticStore.accountNumbers[21] + "*" + StaticStore.accType[21] + "~"
				+ StaticStore.accountNumbers[22] + "*" + StaticStore.accType[22] + "~"
				+ StaticStore.accountNumbers[23] + "*" + StaticStore.accType[23] + "~"
				+ StaticStore.accountNumbers[24] + "*" + StaticStore.accType[24] + "~"
				+ StaticStore.accountNumbers[25] + "*" + StaticStore.accType[25] + "~"
				+ StaticStore.accountNumbers[26] + "*" + StaticStore.accType[26] + "~"
				+ StaticStore.accountNumbers[27] + "*" + StaticStore.accType[27] + "~"
				+ StaticStore.accountNumbers[28] + "*" + StaticStore.accType[28] + "~"
				+ StaticStore.accountNumbers[29] + "*" + StaticStore.accType[29] + "~"
				+ StaticStore.accountNumbers[30] + "*" + StaticStore.accType[30] + "~"
				+ StaticStore.accountNumbers[31] + "*" + StaticStore.accType[31] + "~"
				+ StaticStore.accountNumbers[32] + "*" + StaticStore.accType[32] + "~"
				+ StaticStore.accountNumbers[33] + "*" + StaticStore.accType[33] + "~"
				+ StaticStore.accountNumbers[34] + "*" + StaticStore.accType[34] + "~"
				+ StaticStore.accountNumbers[35] + "*" + StaticStore.accType[35] + "~"
				+ StaticStore.accountNumbers[36] + "*" + StaticStore.accType[36] + "~"
				+ StaticStore.accountNumbers[37] + "*" + StaticStore.accType[37] + "~"
				+ StaticStore.accountNumbers[38] + "*" + StaticStore.accType[38] + "~"
				+ StaticStore.accountNumbers[39] + "*" + StaticStore.accType[39] + "~"
				+ StaticStore.accountNumbers[40] + "*" + StaticStore.accType[40] + "~"
				+ StaticStore.accountNumbers[41] + "*" + StaticStore.accType[41] + "~"
				+ StaticStore.accountNumbers[42] + "*" + StaticStore.accType[42] + "~"
				+ StaticStore.accountNumbers[43] + "*" + StaticStore.accType[43] + "~"
				+ StaticStore.accountNumbers[44] + "*" + StaticStore.accType[44] + "~"
				+ StaticStore.accountNumbers[45] + "*" + StaticStore.accType[45] + "~"
				+ StaticStore.accountNumbers[46] + "*" + StaticStore.accType[46] + "~"
				+ StaticStore.accountNumbers[47] + "*" + StaticStore.accType[47] + "~"
				+ StaticStore.accountNumbers[48] + "*" + StaticStore.accType[48] + "~"
				+ StaticStore.accountNumbers[49] + "*" + StaticStore.accType[49] + "~"
				+ StaticStore.accountNumbers[50] + "*" + StaticStore.accType[50] + "~"
				+ StaticStore.accountNumbers[51] + "*" + StaticStore.accType[51] + "~"
				+ StaticStore.accountNumbers[52] + "*" + StaticStore.accType[52] + "~"
				+ StaticStore.accountNumbers[53] + "*" + StaticStore.accType[53] + "~"
				+ StaticStore.accountNumbers[54] + "*" + StaticStore.accType[54] + "~"
				+ StaticStore.accountNumbers[55] + "*" + StaticStore.accType[55] + "~"
				+ StaticStore.accountNumbers[56] + "*" + StaticStore.accType[56] + "~"
				+ StaticStore.accountNumbers[57] + "*" + StaticStore.accType[57] + "~"
				+ StaticStore.accountNumbers[58] + "*" + StaticStore.accType[58] + "~"
				+ StaticStore.accountNumbers[59] + "*" + StaticStore.accType[59] + "~"
				+ StaticStore.accountNumbers[60] + "*" + StaticStore.accType[60] + "~"
				+ StaticStore.accountNumbers[61] + "*" + StaticStore.accType[61] + "~"
				+ StaticStore.accountNumbers[62] + "*" + StaticStore.accType[62] + "~"
				+ StaticStore.accountNumbers[63] + "*" + StaticStore.accType[63] + "~"
				+ StaticStore.accountNumbers[64] + "*" + StaticStore.accType[64] + "~"
				+ StaticStore.accountNumbers[65] + "*" + StaticStore.accType[65] + "~"
				+ StaticStore.accountNumbers[66] + "*" + StaticStore.accType[66] + "~"
				+ StaticStore.accountNumbers[67] + "*" + StaticStore.accType[67] + "~"
				+ StaticStore.accountNumbers[68] + "*" + StaticStore.accType[68] + "~"
				+ StaticStore.accountNumbers[69] + "*" + StaticStore.accType[69] + "~"
				+ StaticStore.accountNumbers[70] + "*" + StaticStore.accType[70] + "~"
				+ StaticStore.accountNumbers[71] + "*" + StaticStore.accType[71] + "~"
				+ StaticStore.accountNumbers[72] + "*" + StaticStore.accType[72] + "~"
				+ StaticStore.accountNumbers[73] + "*" + StaticStore.accType[73] + "~"
				+ StaticStore.accountNumbers[74] + "*" + StaticStore.accType[74] + "~"
				+ StaticStore.accountNumbers[75] + "*" + StaticStore.accType[75] + "~"
				+ StaticStore.accountNumbers[76] + "*" + StaticStore.accType[76] + "~"
				+ StaticStore.accountNumbers[77] + "*" + StaticStore.accType[77] + "~"
				+ StaticStore.accountNumbers[78] + "*" + StaticStore.accType[78] + "~"
				+ StaticStore.accountNumbers[79] + "*" + StaticStore.accType[79] + "~"
				+ StaticStore.accountNumbers[80] + "*" + StaticStore.accType[80] + "~"
				+ StaticStore.accountNumbers[81] + "*" + StaticStore.accType[81] + "~"
				+ StaticStore.accountNumbers[82] + "*" + StaticStore.accType[82] + "~"
				+ StaticStore.accountNumbers[83] + "*" + StaticStore.accType[83] + "~"
				+ StaticStore.accountNumbers[84] + "*" + StaticStore.accType[84] + "~"
				+ StaticStore.accountNumbers[85] + "*" + StaticStore.accType[85] + "~"
				+ StaticStore.accountNumbers[86] + "*" + StaticStore.accType[86] + "~"
				+ StaticStore.accountNumbers[87] + "*" + StaticStore.accType[87] + "~"
				+ StaticStore.accountNumbers[88] + "*" + StaticStore.accType[88] + "~"
				+ StaticStore.accountNumbers[89] + "*" + StaticStore.accType[89] + "~"
				+ StaticStore.accountNumbers[90] + "*" + StaticStore.accType[90] + "~"
				+ StaticStore.accountNumbers[91] + "*" + StaticStore.accType[91] + "~"
				+ StaticStore.accountNumbers[92] + "*" + StaticStore.accType[92] + "~"
				+ StaticStore.accountNumbers[93] + "*" + StaticStore.accType[93] + "~"
				+ StaticStore.accountNumbers[94] + "*" + StaticStore.accType[94] + "~"
				+ StaticStore.accountNumbers[95] + "*" + StaticStore.accType[95] + "~"
				+ StaticStore.accountNumbers[96] + "*" + StaticStore.accType[96] + "~"
				+ StaticStore.accountNumbers[97] + "*" + StaticStore.accType[97] + "~"
				+ StaticStore.accountNumbers[98] + "*" + StaticStore.accType[98] + "~"
				+ StaticStore.accountNumbers[99] + "*" + StaticStore.accType[99] + "~"
				+ StaticStore.accountNumbers[100] + "*" + StaticStore.accType[100] + "~"
				+ StaticStore.accountNumbers[101] + "*" + StaticStore.accType[101] + "~"
				+ StaticStore.accountNumbers[102] + "*" + StaticStore.accType[102] + "~"
				+ StaticStore.accountNumbers[103] + "*" + StaticStore.accType[103] + "~"
				+ StaticStore.accountNumbers[104] + "*" + StaticStore.accType[104] + "~"
				+ StaticStore.accountNumbers[105] + "*" + StaticStore.accType[105] + "~"
				+ StaticStore.accountNumbers[106] + "*" + StaticStore.accType[106] + "~"
				+ StaticStore.accountNumbers[107] + "*" + StaticStore.accType[107] + "~"
				+ StaticStore.accountNumbers[108] + "*" + StaticStore.accType[108] + "~"
				+ StaticStore.accountNumbers[109] + "*" + StaticStore.accType[109] + "~"
				+ StaticStore.accountNumbers[110] + "*" + StaticStore.accType[110] + "~"
				+ StaticStore.accountNumbers[111] + "*" + StaticStore.accType[111] + "~"
				+ StaticStore.accountNumbers[112] + "*" + StaticStore.accType[112] + "~"
				+ StaticStore.accountNumbers[113] + "*" + StaticStore.accType[113] + "~"
				+ StaticStore.accountNumbers[114] + "*" + StaticStore.accType[114] + "~"
				+ StaticStore.accountNumbers[115] + "*" + StaticStore.accType[115] + "~"
				+ StaticStore.accountNumbers[116] + "*" + StaticStore.accType[116] + "~"
				+ StaticStore.accountNumbers[117] + "*" + StaticStore.accType[117] + "~"
				+ StaticStore.accountNumbers[118] + "*" + StaticStore.accType[118] + "~"
				+ StaticStore.accountNumbers[119] + "*" + StaticStore.accType[119] + "~"
				+ StaticStore.accountNumbers[120] + "*" + StaticStore.accType[120] + "~"
				+ StaticStore.accountNumbers[121] + "*" + StaticStore.accType[121] + "~"
				+ StaticStore.accountNumbers[122] + "*" + StaticStore.accType[122] + "~"
				+ StaticStore.accountNumbers[123] + "*" + StaticStore.accType[123] + "~"
				+ StaticStore.accountNumbers[124] + "*" + StaticStore.accType[124] + "~"
				+ StaticStore.accountNumbers[125] + "*" + StaticStore.accType[125] + "~"
				+ StaticStore.accountNumbers[126] + "*" + StaticStore.accType[126] + "~"
				+ StaticStore.accountNumbers[127] + "*" + StaticStore.accType[127] + "~"
				+ StaticStore.accountNumbers[128] + "*" + StaticStore.accType[128] + "~"
				+ StaticStore.accountNumbers[129] + "*" + StaticStore.accType[129] + "~"
				+ StaticStore.accountNumbers[130] + "*" + StaticStore.accType[130] + "~"
				+ StaticStore.accountNumbers[131] + "*" + StaticStore.accType[131] + "~"
				+ StaticStore.accountNumbers[132] + "*" + StaticStore.accType[132] + "~"
				+ StaticStore.accountNumbers[133] + "*" + StaticStore.accType[133] + "~"
				+ StaticStore.accountNumbers[134] + "*" + StaticStore.accType[134] + "~"
				+ StaticStore.accountNumbers[135] + "*" + StaticStore.accType[135] + "~"
				+ StaticStore.accountNumbers[136] + "*" + StaticStore.accType[136] + "~"
				+ StaticStore.accountNumbers[137] + "*" + StaticStore.accType[137] + "~"
				+ StaticStore.accountNumbers[138] + "*" + StaticStore.accType[138] + "~"
				+ StaticStore.accountNumbers[139] + "*" + StaticStore.accType[139] + "~"
				+ StaticStore.accountNumbers[140] + "*" + StaticStore.accType[140] + "~"
				+ StaticStore.accountNumbers[141] + "*" + StaticStore.accType[141] + "~"
				+ StaticStore.accountNumbers[142] + "*" + StaticStore.accType[142] + "~"
				+ StaticStore.accountNumbers[143] + "*" + StaticStore.accType[143] + "~"
				+ StaticStore.accountNumbers[144] + "*" + StaticStore.accType[144] + "~"
				+ StaticStore.accountNumbers[145] + "*" + StaticStore.accType[145] + "~"
				+ StaticStore.accountNumbers[146] + "*" + StaticStore.accType[146] + "~"
				+ StaticStore.accountNumbers[147] + "*" + StaticStore.accType[147] + "~"
				+ StaticStore.accountNumbers[148] + "*" + StaticStore.accType[148] + "~"
				+ StaticStore.accountNumbers[149] + "*" + StaticStore.accType[149] + "#"
				+StaticStore.registeredUserStatus+"#"+
				StaticStore.isMpinAtLast+"#"+
				StaticStore.mPinHexaValue+"#"+
				StaticStore.SMS_AUTHENTICATION_SMSMODE+"#"+
				StaticStore.SMS_AUTHENTICATION_GPRSMODE+"#"+
				StaticStore.chequeLength+"#"+
				StaticStore.accOwner+"#",
				RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_ACC);
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

	    public Intent getCommunicationMode(Context context,int index){
	        String[] menuItem = {"GPRS","SMS"};
	        Intent intent = new Intent(context, ListSelection.class);
	        //index =16
	        //index = 63 for communication mode after PC00
	        intent.putExtra("listIndex", index);
	        intent.putExtra("menuItem", menuItem);
	        intent.putExtra("listHeader","Communication Mode");
	 	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
	        intent.putExtra("more", false);
	      	return intent;
	    }

	    
	    public Intent getAccountTypeList(Context context){
	        String[] menuItem = {"Savings","Current"};
	        Intent intent = new Intent(context, ListSelection.class);
	        //index =16
	        //index = 63 for communication mode after PC00
	        intent.putExtra("listIndex", 44);
	        intent.putExtra("menuItem", menuItem);
	        intent.putExtra("listHeader","A/C Types");
	 	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
	        intent.putExtra("more", false);
	      	return intent;
	    }
	    
	    public Intent getAccountTypeListM2A(Context context,int index){
	        String[] menuItem = {"Savings","Current"};
	        Intent intent = new Intent(context, ListSelection.class);
	        //index =16
	        //index = 63 for communication mode after PC00
	        intent.putExtra("listIndex", index);
	        intent.putExtra("menuItem", menuItem);
	        intent.putExtra("listHeader","A/C Types");
	 	 //----  listCanvas.setSelectedIndex(StaticStore.withMemory? 0 : 1);
	        intent.putExtra("more", false);
	      	return intent;
	    }
	    
	    
	public Intent get_accTypeMenu(Context context, int index, String type) {
		Intent intent;
		String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords,
				RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
		String[][] tempAccTypeArr = null;

		tempAccTypeArr = getSplittedValuesArray(tempData.substring(0, tempData	.length() - 1), 7,"#",tempAccTypeArr);
		accTypeArr = new String[tempAccTypeArr.length][2];
		// CC*Current Current A/c*Y*N*N*Y*Y
		int j = 0;
		for (int i = 0; i < tempAccTypeArr.length; i++) {
			if (type.equals("M2A")) {
				if (tempAccTypeArr[i][2].equals("Y")) {
					accTypeArr[j][0] = tempAccTypeArr[i][0];
					accTypeArr[j][1] = tempAccTypeArr[i][1];
					j++;
				}
			} else if (type.equals("P2A")) {
				if (tempAccTypeArr[i][3].equals("Y")) {
					accTypeArr[j][0] = tempAccTypeArr[i][0];
					accTypeArr[j][1] = tempAccTypeArr[i][1];
					j++;
				}
			} else if (type.equals("NEFT")) {
				if (tempAccTypeArr[i][4].equals("Y")) {
					accTypeArr[j][0] = tempAccTypeArr[i][0];
					accTypeArr[j][1] = tempAccTypeArr[i][1];
					j++;
				}
			} else if (type.equals("P2U")) {
				if (tempAccTypeArr[i][6].equals("Y")) {
					accTypeArr[j][0] = tempAccTypeArr[i][0];
					accTypeArr[j][1] = tempAccTypeArr[i][1];
					j++;
				}
			}
		}
		if (j == 0) {
			intent = null;
			return intent;
		} else {
			String[] menuItem = new String[j];
			for (int i = 0; i < menuItem.length; i++) {
				menuItem[i] = accTypeArr[i][1];
			}
			intent = new Intent(context, ListSelection.class);
			intent.putExtra("listIndex", index);
			intent.putExtra("menuItem", menuItem);
			intent.putExtra("listHeader", "A/C Types");
			intent.putExtra("more", false);
			return intent;

		}
	}
	    
	    public Intent getList(Context context,int index,int selectedIndex){
			 StaticStore.isInbox = false;
			 String[] menuItem = StaticStore.listContent[StaticStore.indexCtr];        
		        mPayIntent = new Intent(context, ListSelection.class);
		    	   mPayIntent.putExtra("listIndex", StaticStore.listIndexArray[StaticStore.indexCtr]);
		    	   mPayIntent.putExtra("menuItem", menuItem);
		    	   mPayIntent.putExtra("listHeader",StaticStore.listHeading[StaticStore.indexCtr]);
		         	mPayIntent.putExtra("more", StaticStore.listMore[StaticStore.indexCtr]);
		         	mPayIntent.putExtra("selectedIndex", selectedIndex);
		         	mPayIntent.putExtra("isImageTextList",StaticStore.listImageArray[StaticStore.indexCtr]);
		         	StaticStore.LogPrinter('i',"Get List -->");
		         	return mPayIntent;
	    }

	    public String getTimeAsSpllited(String time) {
			// 10301230
			int fromHr = Integer.parseInt(time.substring(0, 2));
			int fromMin = Integer.parseInt(time.substring(2, 4));
			int ToHr = Integer.parseInt(time.substring(4, 6));
			int ToMin = Integer.parseInt(time.substring(6, 8));
			String frmNotation = null;
			String toNotation = null;
			if (fromHr >= 13) {
				fromHr = fromHr - 12;
				frmNotation = "PM";
			} else {
				frmNotation = "AM";
			}
			if (ToHr >= 13) {
				ToHr = ToHr - 12;
				toNotation = "PM";
			} else {
				toNotation = "AM";
			}if(fromHr == 12 ){
				frmNotation = "PM";
			}else if(ToHr == 12){
				toNotation = "PM";
			}
			// fromHr+":"+fromMin+frmNotation+"#"ToHr+":"ToMin+toNotation
			return fromHr + ":" + fromMin + frmNotation + "-" + ToHr + ":" + ToMin
					+ toNotation;

		}
	    
  public Intent getHome(Context context){
	  StaticStore.isAirline = false;
	  StaticStore.isFrom1T00Response = false;
	  Intent intent;
	  
	  if(StaticStore.enableHome){
		  intent = new Intent(context ,GridScreenView.class);
		  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	  }else {
		  if(StaticStore.isOTPBuild && !StaticStore.IsPermitted && !StaticStore.isOTPVerified){
			  StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
			  StaticStore.FromListScreen = false;
			  StaticStore.menuDesc[220] = new String []{"OTP Generation","APBO;Y","Mobile No.",StaticStore.mPINString,"10-10-N-N-N","4-4-N-Y-Y","2","true","true","Y"};
			  StaticStore.tagType = "APBO";
			  StaticStore.index = 220;
			  intent = new Intent(context,DynamicCanvas.class);
			  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		  }else{
			  intent = new Intent(context ,GridScreenViewActivation.class);
			  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		  }
	  }
	  return intent;
  }
  public Intent getAbout(Context context){
	  Intent intent = new Intent(context, DisplayableView.class);
		 String message = "Customer ;"+ StaticStore.buildVersion +"; FSSNeT ";
		 intent.putExtra("response",message);
		 intent.putExtra("formName", "About");
	  return intent;
  }
public String getAccTypeDisplay(String Acctype) {
	String Acctypedisp = null;
	try{
	if(Acctype.equals("SA")){
		Acctypedisp = "Savings Account";
	}else if (Acctype.equals("CA")){
		Acctypedisp = "Current Account";
	}else if (Acctype.equals("OA")){
		Acctypedisp = "Overdraft";
	}else if (Acctype.equals("CC")){
		Acctypedisp = "Cash Credit";
	}else if (Acctype.equals("LA")){
		Acctypedisp = "Loan Account ";
	}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return Acctypedisp;
	
}
public boolean  isPreLoginTransaction(String tagName){
	  if(tagName.equals("SY") || tagName.equals("SU") || tagName.equals("NU")|| tagName.equals("1L") || tagName.equals("EL") || tagName.equals("AL")|| tagName.equals("1F")|| tagName.equals("2F")|| tagName.equals("1O")
			  || tagName.equals("GT")|| tagName.equals("CM")|| tagName.equals("MU") || tagName.equals("NA")|| tagName.equals("CG")|| tagName.equals("P2") || tagName.equals("Z5")
			  || tagName.equals("2O")|| tagName.equals("3O")|| tagName.equals("4O") || tagName.equals("5O")|| tagName.equals("P5")
			  || tagName.equals("P4")|| tagName.equals("RL")|| tagName.equals("AG") || tagName.equals("LR") || tagName.equals("BV")|| tagName.equals("P3") || tagName.equals("TF")
			  || tagName.equals("OG")|| tagName.equals("OV")){
      return true;
	  }else{
		  return false;
	  }
   }
public void initialize() {
	StaticStore.LogPrinter('i',"initialize zzzzzzzzzzzzzzzzzzzzzz");
	if(StaticStore.index != 58 && StaticStore.index != 221 && StaticStore.index != 220  && !StaticStore.isMoreClicked){ // && StaticStore.isMpinAtLast  
		//mpin start
		   StaticStore.LogPrinter('i',"Backup Array index --> "+StaticStore.index);
		   StaticStore.mPinArray = new String[StaticStore.menuDesc[StaticStore.index].length];
		   StaticStore.mPinBackupindex = StaticStore.index;
		   
		   for (int s = 0; s < StaticStore.menuDesc[StaticStore.index].length; s++) {
			   StaticStore.mPinArray[s] = StaticStore.menuDesc[StaticStore.index][s];
			}
	    	StaticStore.LogPrinter('i',"Backup StaticStore.mPinArray == > "+Arrays.deepToString(StaticStore.mPinArray));
		   //mpin end
	}
//	 else{
	//int count = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 3]);
	//int count;
//	 if(StaticStore.index == 17 || StaticStore.index == 24 ){
//		StaticStore.LogPrinter('i',">>>>>>>"+StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 1]);
//		count = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 1]);
//	}else{

		int count = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
//	}
//staticStore = new String[count];
/*for(int i = 0;i < staticStore.length;i++){
	staticStore[i] = "";
}*/

if(true){//!StaticStore.selectClassBack
 /*  if(StaticStore.isSearchAndRegister){
	   for(int i = 0;i < StaticStore.legValue.length;i++){
		   	StaticStore.legValue[i] = "";
		   }
	   
   }else *///if(!StaticStore.fromChequePickup){
   if(StaticStore.index != 58){            
        StaticStore.legValue = new String[count];
   }
  

   if(isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){
	   StaticStore.isMpinNeeded = false;
   }else if(StaticStore.tagType == "APBO"){
	   StaticStore.isMpinNeeded = true;
   }else{
	   StaticStore.isMpinNeeded = isMPINNeed(StaticStore.menuDesc[StaticStore.index][1].substring(2,4));
   }

//if(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4).equals("P3")){
//StaticStore.isMpinNeeded = true;
//}

   StaticStore.LogPrinter('i',"midlet.getFilledAccArray().length -->"+getFilledAccArray().length);
   StaticStore.LogPrinter('i',"StaticStore.index -->"+StaticStore.index);
   StaticStore.LogPrinter('i',"StaticStore.IsPersonalInfoGot -->"+StaticStore.IsPersonalInfoGot);
   StaticStore.LogPrinter('i',"StaticStore.IsPermitted -->"+StaticStore.IsPermitted);
  
   StaticStore.LogPrinter('i',"menu array 111111111111 inti(tempIndex) ==> "+Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
   StaticStore.LogPrinter('i',">>>>Dynamic   StaticStore.isMpinAtLast>>>>>"+StaticStore.isMpinAtLast);
   StaticStore.LogPrinter('i',">>>>Dynamic   StaticStore.isMpinNeeded>>>>>"+StaticStore.isMpinNeeded);
   
   
	
	
   if(!StaticStore.isMpinNeeded  && StaticStore.index != 58 && !isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index ][1].substring(2, 4))){//&& StaticStore.index != 123
       
       for(int j = 2;j < 2 + count;j++){
    	   
   	         if(StaticStore.menuDesc[StaticStore.index][j].equals(StaticStore.mPINString)){
   	        	StaticStore.menuDesc[StaticStore.index][j] = "";
   	        	StaticStore.menuDesc[StaticStore.index][j + count] = "";
   	        	StaticStore.isMpinRemoved = true;
   	         } 
   	     }
   }else if(StaticStore.isMpinNeeded && StaticStore.isMpinAtLast && StaticStore.index != 58 && !StaticStore.isMoreClicked){//&& StaticStore.index != 123
     for(int j = 2;j < 2 + count;j++){
	         if(StaticStore.menuDesc[StaticStore.index][j].equals(StaticStore.mPINString)){
	        	StaticStore.menuDesc[StaticStore.index][j] = "";
	        	StaticStore.menuDesc[StaticStore.index][j + count] = "";
	        	StaticStore.isMpinRemoved = true;
	         } 
	     }
   }
   
  
//   StaticStore.transIndex = StaticStore.index;
   StaticStore.LogPrinter('i',"1499 StaticStore.transIndex>>>>"+StaticStore.transIndex);
   StaticStore.LogPrinter('i',"menu array 22222222222inti(tempIndex) ==> "+Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
   if(StaticStore.menuDesc[StaticStore.index][2].equals(StaticStore.mPINString) &&
		   StaticStore.menuDesc[StaticStore.index ][2 + Integer.parseInt(StaticStore.menuDesc[StaticStore.index ][StaticStore.menuDesc[StaticStore.index ].length - 4])].equals("4-4-N-Y-Y")
		   && StaticStore.index != 58){
	  
    	
   	StaticStore.menuDesc[58] = new String[StaticStore.menuDesc[StaticStore.index].length];			
		for (int i = 0; i < StaticStore.menuDesc[StaticStore.index ].length; i++) {
			StaticStore.menuDesc[58][i] = StaticStore.menuDesc[StaticStore.index][i];
		}
//           DynamicCanvas.menuDesc[58] = DynamicCanvas.menuDesc[tempIndex];
   		 int numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
   	     
   		  StaticStore.LogPrinter('i',"numLabelsnumLabelsnumLabels ==> "+numLabels);
   	     for(int j = 2;j < 2 + numLabels;j++){
   	         if(j != 2){
   	        	 StaticStore.menuDesc[58][j] = StaticStore.legValue[j -2];
   	        	 StaticStore.menuDesc[58][j + numLabels] = "";
   	         } else{
   	        	 StaticStore.menuDesc[58][j] = StaticStore.mPINString;
   	        	 StaticStore.menuDesc[58][j + numLabels] = "4-4-N-Y-Y";
   	         }
//   	         if(j < 1 + numLabels){
//   	        	 DynamicCanvas.menuDesc[58][j + numLabels + 1] = "false";
//   	        	 DynamicCanvas.menuDesc[58][j + numLabels + 2] = "false";
//                             DynamicCanvas.menuDesc[58][j + numLabels + 3] = "N";
//   	         }
   	     }
                    //{"IMPS Fund Transfer","APQI;Y",mPINString,"Beneficiary Mobile No.","Beneficiary MMID","Amount (Rs.)","Remarks","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"},//174-179
   	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 3] = "false";
   	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 2] = "false";
   	     StaticStore.menuDesc[58][StaticStore.menuDesc[58].length - 1] = (StaticStore.isMpinAtLast?"N":"Y"); // Siva 
                   
                   for(int i = 0; i < StaticStore.menuDesc[58].length;i++){
                        StaticStore.LogPrinter('i',":::::DynamicCanvas.menuDesc[index]::::::::"+StaticStore.menuDesc[58][i]);
                   }
                            StaticStore.forSeperateMPinAtFirst = true;
                            StaticStore.transIndex = StaticStore.index;
                   StaticStore.index = 58;
//                   StaticStore.FromListScreen = true;
            
        }
   
   if(StaticStore.index != 58){
	   StaticStore.originalindex = StaticStore.index;
	   for(int i = 0;i < StaticStore.legValue.length;i++){
		   	StaticStore.legValue[i] = "";
		   }
    }

//}
}
//else{	
//	StaticStore.legValue[3] = removeSlashFromDate(StaticStore.legValue[3]);
//	for(int i = 4;i < 8;i++){
//	StaticStore.legValue[i] = StaticStore.legValue[i + 2]; 
//	}
//	StaticStore.menuDesc[9][6] = StaticStore.flightTiming;
//}

//StaticStore.LogPrinter('i',"StaticStore.isMoreClicked","StaticStore.isMoreClicked --> "+StaticStore.isMoreClicked);
//if(StaticStore.isMoreClicked){
//   StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 1] = "Y";//(StaticStore.isMpinAtLast?"Y":"N")
//}

StaticStore.LogPrinter('i',"menu array inti(tempIndex) ==> "+Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
StaticStore.selectClassBack = false;
}


public String getMPinEnabledtransaction(String mPinControlString){

	StaticStore.LogPrinter('i',"mPinControlString  ===> "+mPinControlString);
    String[][] mpinEnabledTransactions = new String[mPinControlString.length()][1]; 
    mpinEnabledTransactions[0][0] = "BE"; //SivaG Balance Enquiry �
    mpinEnabledTransactions[1][0] = "MS"; //Mini Statement
    mpinEnabledTransactions[2][0] = "QM"; //M2M Quick Fund transfer
    mpinEnabledTransactions[3][0] = "RM"; //M2M Beneficiary Registration
    mpinEnabledTransactions[4][0] = "F1"; //M2M Beneficiary Payment
    mpinEnabledTransactions[5][0] = "D5"; //M2M Beneficiary Details
    mpinEnabledTransactions[6][0] = "2L"; //M2M Beneficiary Deregistration
    mpinEnabledTransactions[7][0] = "QT"; //M2A Quick Fund transfer
    mpinEnabledTransactions[8][0] = "RA"; //M2A Beneficiary Registration
    mpinEnabledTransactions[9][0] = "F2"; //M2A Beneficiary Payment
    mpinEnabledTransactions[10][0] = "D6"; //M2A Beneficiary Details
    mpinEnabledTransactions[11][0] = "3L"; //M2A Beneficiary Deregistration
    mpinEnabledTransactions[12][0] = "QN"; //NEFT Quick Fund transfer
    mpinEnabledTransactions[13][0] = "Q2"; //NEFT Beneficiary Registration
    mpinEnabledTransactions[14][0] = "Q7"; //NEFT Beneficiary Payment
    mpinEnabledTransactions[15][0] = "NL"; //NEFT Beneficiary Details
    mpinEnabledTransactions[16][0] = "S1"; //NEFT --> IFSC / Bank Search --> IFSC Search
    mpinEnabledTransactions[17][0] = "4S"; //NEFT --> IFSC / Bank Search --> Bank Search
    mpinEnabledTransactions[18][0] = "4L"; //NEFT Beneficiary Deregistration
    mpinEnabledTransactions[19][0] = "L1"; //Request for Cheque Book
    mpinEnabledTransactions[20][0] = "L3"; //Request for A/C Statement
    mpinEnabledTransactions[21][0] = "L7"; //Request for Loan
    mpinEnabledTransactions[22][0] = "L8"; //Request for Credit Card
    mpinEnabledTransactions[23][0] = "L6"; //Request for New A/C
    mpinEnabledTransactions[24][0] = "QI"; //Fund Transfer - To  Mobile No.
    mpinEnabledTransactions[25][0] = "K1"; //Fund Transfer - To Account No.
    mpinEnabledTransactions[26][0] = "1U"; //Fund Transfer - To AADHAAR No.
    mpinEnabledTransactions[27][0] = "PI"; //IMPS Merchant Payment
    mpinEnabledTransactions[28][0] = "MO"; //Generate OTP
    mpinEnabledTransactions[29][0] = "IM"; //Generate MMID
    mpinEnabledTransactions[30][0] = "S6"; //Retrieve MMID
    mpinEnabledTransactions[31][0] = "RC"; //Cancel MMID
    
    mpinEnabledTransactions[32][0] = "W1"; //Registered IMPS - Mobile No.--> Registration
    mpinEnabledTransactions[33][0] = "W3"; //Registered IMPS - Mobile No.--> Payment
    mpinEnabledTransactions[34][0] = "D9"; //Registered IMPS - Mobile No.--> Beneficiary Details
    mpinEnabledTransactions[35][0] = "5L"; //Registered IMPS - Mobile No.--> Deregistration
    
    mpinEnabledTransactions[36][0] = "K2"; //Registered IMPS - Account No.--> Registration
    mpinEnabledTransactions[37][0] = "K4"; //Registered IMPS - Account No.--> Payment
    mpinEnabledTransactions[38][0] = "4W"; //Registered IMPS - Account No.--> Beneficiary Details
    mpinEnabledTransactions[39][0] = "6L"; //Registered IMPS - Account No.--> Deregistration
    
    mpinEnabledTransactions[40][0] = "2U"; //Registered IMPS - AADHAAR No. --> Registration
    mpinEnabledTransactions[41][0] = "4U"; //Registered IMPS - AADHAAR No. --> Payment
    mpinEnabledTransactions[42][0] = "6U"; //Registered IMPS - AADHAAR No. --> Beneficiary Details
    mpinEnabledTransactions[43][0] = "7U"; //Registered IMPS - AADHAAR No. --> Deregistration
    
    mpinEnabledTransactions[44][0] = "1I"; //Registered IMPS - Merchant --> Registration
    mpinEnabledTransactions[45][0] = "3I"; //Registered IMPS - Merchant --> Payment
    mpinEnabledTransactions[46][0] = "M9"; //Registered IMPS - Merchant --> Beneficiary Details
    mpinEnabledTransactions[47][0] = "7L"; //Registered IMPS - Merchant --> Deregistration
    
    mpinEnabledTransactions[48][0] = "B2"; //Registered Bill Payment --> Biller Registration
    mpinEnabledTransactions[49][0] = "BX"; //Registered Bill Payment --> Bill Payment
    mpinEnabledTransactions[50][0] = "BZ"; //Registered Bill Payment --> Biller Deregistration
    
    mpinEnabledTransactions[51][0] = "B2"; //Instant Bill Payment "B2" same tag for both
    
    mpinEnabledTransactions[52][0] = "AS"; //Airline Ticketing --> Booking and Payment
    mpinEnabledTransactions[53][0] = "AI"; //Airline Ticketing --> Payment Only
    mpinEnabledTransactions[54][0] = "R2"; //Airline Ticketing --> Reprint Ticket --> Booking and Payment
    mpinEnabledTransactions[55][0] = "R0"; //Airline Ticketing --> Reprint Ticket --> Payment Only
    
    mpinEnabledTransactions[56][0] = "M1"; //Movie Ticketing
    mpinEnabledTransactions[57] = new String[8];
    mpinEnabledTransactions[57][0] = "1T"; // Recharge Note: Only single bit is applicable for the entire rechare transaction
    mpinEnabledTransactions[57][1] = "2T";
    mpinEnabledTransactions[57][2] = "3T";
    mpinEnabledTransactions[57][3] = "4T";
    mpinEnabledTransactions[57][4] = "7T";
    mpinEnabledTransactions[57][5] = "8T";
    mpinEnabledTransactions[57][6] = "BD";
    mpinEnabledTransactions[57][7] = "XX";
    
    mpinEnabledTransactions[58][0] = "T1"; //Temple Donation
    mpinEnabledTransactions[59][0] = "L4"; //Stop Cheque
    mpinEnabledTransactions[60][0] = "LW"; //Cheque Status
    mpinEnabledTransactions[61][0] = "HL"; //Block Card
    mpinEnabledTransactions[62][0] = "TH"; //Transaction History
    mpinEnabledTransactions[63][0] = "CH"; //IRCTC --> Generate Card
    mpinEnabledTransactions[64][0] = "TP"; //IRCTC --> Top-up
    mpinEnabledTransactions[65][0] = "ZP"; //WAP
    mpinEnabledTransactions[66][0] = "Z6"; //Configure/Update E-mail
    mpinEnabledTransactions[67][0] = "IS"; //IFP Registration
    mpinEnabledTransactions[68][0] = "IL";//"IL"; //IFP Payment
    mpinEnabledTransactions[69][0] = "I5"; //IFP De-Registration
    mpinEnabledTransactions[70][0] = "SC"; //Remit ECASH
    mpinEnabledTransactions[71][0] = "SE"; //ECASH Status
    mpinEnabledTransactions[72][0] = "PF"; //Forogt ECASH
    
    mpinEnabledTransactions[73][0] = "AB"; //NEFT Payment status
    
    mpinEnabledTransactions[74][0] = "L9"; //Request for Debit Card
    
    mpinEnabledTransactions[75][0] = "AB"; //Debit Card
    mpinEnabledTransactions[76][0] = "AB"; //Statement Request-Email
    mpinEnabledTransactions[77][0] = "AB"; //New Product
    mpinEnabledTransactions[78][0] = "AB"; //Demand Draft
    mpinEnabledTransactions[79][0] = "AB"; //FD Request to Bank
    mpinEnabledTransactions[80][0] = "AB"; //View Paid Bills	
    mpinEnabledTransactions[81][0] = "AB"; //Manage Debit Card Limit	
    mpinEnabledTransactions[82][0] = "AB"; //VISA Card Bill Payment Beneficiary Registration
    mpinEnabledTransactions[83][0] = "AB"; //VISA Card Bill Payment Payment	
    mpinEnabledTransactions[84][0] = "AB"; //VISA Card Bill Payment Beneficiary De-Registration
    mpinEnabledTransactions[85][0] = "AB"; //VISA Card Bill Payment Beneficiary Details	
    mpinEnabledTransactions[86][0] = "AB"; //FD Balance Inquiry
    mpinEnabledTransactions[87][0] = "AB"; //FDs linked to A/C
    mpinEnabledTransactions[88][0] = "AB"; //FD Sweep-In	
    mpinEnabledTransactions[89][0] = "AB"; //FD Sweep-Out
    mpinEnabledTransactions[90][0] = "AB"; //FD Interest Rate Calculator
    mpinEnabledTransactions[91][0] = "AB"; //Reward Points Inquiry
    
    mpinEnabledTransactions[92][0] = "IB"; //eConnect SignOn password 
    mpinEnabledTransactions[93][0] = "BP"; //eConnect Transaction Password
    mpinEnabledTransactions[94][0] = "DP"; //Debit card PIN	
    
    mpinEnabledTransactions[95][0] = "DO";  // Deposit Account Opening
    mpinEnabledTransactions[96][0] = "LF";  // Deposit Account Balance
    mpinEnabledTransactions[97][0] = "LA";  // Loan Balance
    mpinEnabledTransactions[98] = new String[6];
    mpinEnabledTransactions[98][0] = "C3";  // Credit Card Bill Payment Registration
    mpinEnabledTransactions[98][1] = "C4";  // Credit Card Bill Payment Beneficiary List For Payment
    mpinEnabledTransactions[98][2] = "C5";  // Credit Card Bill Payment Beneficiary List For Deregistration
    mpinEnabledTransactions[98][3] = "C6";  // Credit Card Bill Payment Payment
    mpinEnabledTransactions[98][4] = "C7";  // Credit Card Bill Payment Deegistration
    mpinEnabledTransactions[98][5] = "C8";  // Credit Card Bill Payment Beneficiary Details
    mpinEnabledTransactions[99][0] = "ST";  // My Own Accounts
    mpinEnabledTransactions[100][0] = "LI";  // Inward Cheque Status
    mpinEnabledTransactions[101][0] = "LO";  // Outward Cheque Status
    mpinEnabledTransactions[102][0] = "N1"; // IFP Payment
   
    
    
    String  temp = "";
    String mPINForTransactions = "";
    for (int i = 0; i < mpinEnabledTransactions.length; i++) {
    	temp = mPinControlString.substring(i);
    	  //Parent Flag Assign to Sub menu Items -- Siva G
    	for(int j = 0; j < mpinEnabledTransactions[i].length; j++){
//    		if(i == 48 || i == 49 ||i == 50 ||i == 51)
//    		{//Test Hard code Siva 
//    		if(StaticStore.testbuild){
//    			mPINForTransactions = mPINForTransactions + mpinEnabledTransactions[i][j] + ":"+ "1" + ";";
//    		}else{
    		mPINForTransactions = mPINForTransactions + mpinEnabledTransactions[i][j] + ":"+ temp.substring(0,1) + ";"; // need to uncommen
//    		}
    	}

    }
    
    StaticStore.LogPrinter('i',">>>>>mPINForTransactions>>>>"+mPINForTransactions);
    return mPINForTransactions;
}


public boolean  isMPINNeed(String tagName){
	
	if(StaticStore.mpinNeededTransactions.indexOf(tagName) != -1){
		return StaticStore.mpinNeededTransactions.substring(StaticStore.mpinNeededTransactions.indexOf(tagName) + 3,StaticStore.mpinNeededTransactions.indexOf(tagName) + 4).equals("1")?true:false;
	}else{
		return StaticStore.isMpinNeeded;
	}
	
	//return false;
}
public String hexToBinary(String hexString){
	char[] a = hexString.toCharArray();
	
	String temp = "";
	for (int i = 0; i < a.length; i++) {
		
		temp += staticLookup[Integer.parseInt(a[i]+"", 16)];
		
	}
	StaticStore.LogPrinter('i',":::::::::::::::::::return value"+temp);
	return temp;
}
public String hashingByMD5(String text){
	  MD5 md5 = new MD5();
	  md5.Update(text);
	  StaticStore.LogPrinter('i',">>>??>??>>"+ md5.asHex());
	  return md5.asHex();
}

	public Intent accountScreenCreation(ListObject listObj,Context context) {
		// TODO Auto-generated method stub
		
		if(StaticStore.midlet.getFilledAccArray().length!=0){
			StaticStore.NoteForAccSel = true;
			Intent mPayIntent = new Intent(context, ListSelection.class);
			mPayIntent.putExtra("listIndex", listObj.getIndex());
			mPayIntent.putExtra("menuItem", new ListSelection().getAccessibleAccArray(listObj.getTag()));
			mPayIntent.putExtra("listHeader", listObj.getHeading());
			mPayIntent.putExtra("more", listObj.isMore());
			return mPayIntent;
		}else{
			StaticStore.isFinancialAccSync = true;
	        StaticStore.tagType = "SY";
	        StaticStore.index = 188;
	        return new Intent(context,DynamicCanvas.class);
	 	}
	}

	public Intent getAccScreenTo(Context context){
		StaticStore.NoteForAccSel = false;
		Intent mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 274);
		mPayIntent.putExtra("menuItem", new ListSelection().getAccessibleAccArrayTo("ST"));
		mPayIntent.putExtra("listHeader", "Select To A/C");
		mPayIntent.putExtra("more", false);
		return mPayIntent;
	}

	public Intent getRechargeCategory(Context context) {
		String[] menuItem = new String[rechargeCategory.length];

		isImageTextList = true;
		imagesListArray = new int[rechargeCategory.length];
		
		for (int i = 0; i < rechargeCategoryId.length; i++) {
			if(rechargeCategoryId[i].equals("MTP")){
				imagesListArray[i] = R.drawable.li_mobile_recharge;
			}else if(rechargeCategoryId[i].equals("DTH")){
				imagesListArray[i] = R.drawable.li_dth_recharge;
			}else if(rechargeCategoryId[i].equals("DTC")){
				imagesListArray[i] = R.drawable.li_datacard_recharge;
			}else{
				imagesListArray[i] = R.drawable.li_recharge;
			}
		}

		for (int i = 0; i < rechargeCategory.length; i++) {
			menuItem[i] = rechargeCategory[i];
		}

		mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 221);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Recharge");// changed by S
		if (moreFlag) {
			mPayIntent.putExtra("more", true);
		} else {
			mPayIntent.putExtra("more", false);
		}
		
		mPayIntent.putExtra("isImageTextList", true);
		
		return mPayIntent;
	}

	
//	imagesListArray = new int[] {R.drawable.li_balance_enquiry,R.drawable.li_ministatement,R.drawable.li_deposit_account_balance,R.drawable.li_loan_balance,R.drawable.li_transaction_history};
//	String[] menuItem = { "Balance Enquiry", "Mini Statement", "Deposit Account Balance", "Loan Balance", "Transaction History"};

	
	public Intent getmPAYRegistration(Context context){
        String[] menuItem = {"DOB","PAN"};       
        mPayIntent = new Intent(context, ListSelection.class);       
        mPayIntent.putExtra("listIndex", 232);
        mPayIntent.putExtra("menuItem", menuItem);
        mPayIntent.putExtra("listHeader", "Please Select DOB or PAN");
        mPayIntent.putExtra("more", false);
        
        //  ftMenu = new ListCanvas(this,getDisplay(),"Fund Transfer",menuItem,8,false,false,false);        
        return mPayIntent;
    }

	
	 public Intent getIrctcCardMenu(Context context){
	   	 String menuItems[]      =   {"Generate Card","Top-up"};
	       mPayIntent = new Intent(context, ListSelection.class);
	  	   mPayIntent.putExtra("listIndex", 230);
	  	   mPayIntent.putExtra("menuItem", menuItems);
	  	   mPayIntent.putExtra("listHeader", "IRCTC Prepaid Card");
	     	mPayIntent.putExtra("more", false);
	     	return mPayIntent;
	       
	    }
	 public Intent getSignUpTypes(Context context){
		 isImageTextList = true;
		 imagesListArray = new int[] {R.drawable.li_debit,R.drawable.li_netbank};
		 String menuItems[]      =   {"Debit Card","Net Banking"};
		 mPayIntent = new Intent(context, ListSelection.class);
		 mPayIntent.putExtra("listIndex", 250);
		 mPayIntent.putExtra("menuItem", menuItems);
		 mPayIntent.putExtra("listHeader", "TMB mConnect Registration");
		 mPayIntent.putExtra("more", false);
		 mPayIntent.putExtra("isImageTextList", true);
		 return mPayIntent;
	 }
	 public String amountIndicatorSymbol(String indicator){
			
			if(indicator.equalsIgnoreCase("P")){
				indicator="%";
			}else if(indicator.equalsIgnoreCase("F")){
				indicator="Rs.";
			}else{
				indicator="";
			}
			return indicator; 
		}
	 public Intent getIRCTC_Card_List(Context context) {
	    	
	        String menuItem[] = new String[StaticStore.midlet.IRCTCCardIssue.length];
	        
	        for(int i = 0; i < StaticStore.midlet.IRCTCCardIssue.length; i++){
	        	 menuItem[i] = StaticStore.midlet.IRCTCCardIssue[i][0]+" "+StaticStore.midlet.IRCTCCardIssue[i][1];
	        }
	        Intent  mPayIntent = new Intent(context, ListSelection.class);
	        mPayIntent.putExtra("listIndex", 231);
	  	   	mPayIntent.putExtra("menuItem", menuItem);
	  	   	mPayIntent.putExtra("listHeader", "Card List");
	  	   	
	  	   	if(StaticStore.midlet.moreFlag){
	  	   		mPayIntent.putExtra("more", true);
	       }else{
	       		mPayIntent.putExtra("more", false);
	       }
	       
	       return mPayIntent;
	    }
	    
	 public Intent getIRCTC_Card_topup(Context context) throws Exception {

		 StaticStore.LogPrinter('i',"StaticStore.selectedIndex ---> "+StaticStore.selectedIndex);
	    	 String feeAmtIndicator=StaticStore.midlet.amountIndicatorSymbol(StaticStore.midlet.IRCTCCardIssue[0][3]);
			 String topAmtIndicator=StaticStore.midlet.amountIndicatorSymbol(StaticStore.midlet.IRCTCCardIssue[0][5]);
			 String tempStr = "";
			 tempStr = StaticStore.midlet.IRCTCCardIssue[0][0]+" "+StaticStore.midlet.IRCTCCardIssue[0][1]+";";
			 
			 if(feeAmtIndicator.equalsIgnoreCase("%")){
				 tempStr+=StaticStore.midlet.IRCTCCardIssue[0][2]+feeAmtIndicator+";";// need to check
			 }else if(feeAmtIndicator.equalsIgnoreCase("Rs.")){
				 tempStr+= feeAmtIndicator+StaticStore.midlet.IRCTCCardIssue[0][2]+";";// need to check
			 }else{
				 tempStr+= StaticStore.midlet.IRCTCCardIssue[0][2]+";";// need to check
			 }
			 if(topAmtIndicator.equalsIgnoreCase("%")){
				 tempStr+=StaticStore.midlet.IRCTCCardIssue[0][4]+topAmtIndicator+";";// need to check
			 }else if(topAmtIndicator.equalsIgnoreCase("Rs.")){
				 tempStr+= topAmtIndicator+StaticStore.midlet.IRCTCCardIssue[0][4]+";";// need to check
			 }else{
				 tempStr+= StaticStore.midlet.IRCTCCardIssue[0][4]+";";// need to check
			 }
			 				  
			 tempStr+= "Rs."+StaticStore.midlet.IRCTCCardIssue[0][6]+";"+
			 		   "Rs."+StaticStore.midlet.IRCTCCardIssue[0][7]+";"+StaticStore.midlet.IRCTCCardIssue[0][8]+";Rs."+StaticStore.midlet.IRCTCCardIssue[0][9];
			 
			 Intent  mPayIntent = new Intent(context , DisplayableView.class);
			 mPayIntent.putExtra("response",tempStr);
			 mPayIntent.putExtra("formName", "TP00");
			 return mPayIntent;
	    }
	 public Intent getExitScreen(Context context) {
		 Intent intent;
		 if(StaticStore.enableHome){
			 StaticStore.isLogout = true;
			 intent = new Intent(context,HelpScreen.class);
				intent.putExtra("title" , "Logout");
//				intent.putExtra("index" , "li_"+index);
		 }else{
			 intent = new Intent(context, SplashScreen.class);
			StaticStore.LogPrinter('i',"???????" + context.getClass());
			intent.putExtra("EXIT", true);
			StaticStore.muTerminate = true;
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 }
		 return intent;
	    }
	 
	 public String getTwelveByteEncryption(String plainText){
			int loopCount = plainText.length()/12;
			String encryptedValue = "";
			String splittedText[] = new String[loopCount+1];
			for (int i = 0; i < splittedText.length; i++) {
				if(plainText.length() >= 12 ){
					splittedText[i] = plainText.substring(0,12);
					if(plainText.length() > 12){
						plainText = plainText.substring(12);
					}else if(plainText.length() == 12){
						plainText = "";
					}
				}else{
					splittedText[i] = plainText;
				}
			}
			for (int i = 0; i < splittedText.length; i++) {
				if(!splittedText[i].trim().equals("")){
					String strData = splittedText[i];
					String sb = "";
					char[] dummy = strData.toCharArray();
					for (int j = 0; j < dummy.length; j++) {
						int padChr = 	(int)dummy[j];	    
					    if(padChr<100){
					    	sb = sb+"0";		
					    }
					    sb = sb+padChr;
					}
				     StaticStore.LogPrinter('i',"sb.append>>>"+sb.toString());
				    RsaEncryptionNew newEnc = new RsaEncryptionNew(sb.toString(),"203577465141885203944391850079714410739") ;
					 StaticStore.LogPrinter('i',newEnc.cipherText);
					encryptedValue = encryptedValue+newEnc.cipherText+"|";
				}
			}
			return encryptedValue.substring(0,encryptedValue.length()-1);
		}

	 public void startFragment(Context context, Intent responseIntent) {
		 // TODO Auto-generated method stub
		 try{
			 classname = responseIntent.getComponent().getClassName();
			 
//			 System.out.println("CLASSNAME"+classname);
			 prevfrag = null;				
			  StaticStore.LogPrinter('i',"Class name ==> "+classname);
			  StaticStore.LogPrinter('i',"Class name StaticStore.indexCtr==> "+StaticStore.indexCtr);
			  StaticStore.LogPrinter('i',"Class name StaticStore.enableHome==> "+StaticStore.enableHome);
			  StaticStore.LogPrinter('i',"Class name StaticStore.Leftsidepane  ==> "+StaticStore.Leftsidepane);
			  StaticStore.LogPrinter('i',"Class name StaticStore.FromGridActivation  ==> "+StaticStore.FromGridActivation);
			 
			
			 
			 if(classname.equalsIgnoreCase(StaticStore.PackageName+".DisplayableView")){
				 StaticStore.fragclassname = classname;
				 
				  StaticStore.LogPrinter('i',"Came into DisplayableView frag part == >");
				 String response="",title="",formname="";
				 
					 response = responseIntent.getStringExtra("response");
					 title = responseIntent.getStringExtra("title");
					 formname = responseIntent.getStringExtra("formName");
				
					 
				
//				 DisplayableView displayableView = new DisplayableView();
					 StaticStore.newFragment = new DisplayableView();

				 Bundle bundle = new Bundle();
				 bundle.putString("response", response);
				 bundle.putString("formName", formname);
				 bundle.putString("title", title);
				 StaticStore.newFragment.setArguments(bundle);


				 changeDetailFragment(StaticStore.newFragment,true,false);

			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenView")){
				 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
				 StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
				 StaticStore.fragclassname = classname;
				  StaticStore.LogPrinter('i',"Came into GridScreenView part == >");
				 if(StaticStore.istablet){
					 StaticStore.gridupdateneeded = true;
					 StaticStore.enableHome = true;
					 StaticStore.isDashBoardLeftSideView = false;
					 StaticStore.isInbox = false;
					 Intent myIntent = StaticStore.midlet.getPayRegList(context); 
					 StaticStore.midlet.startFragment(context,myIntent);
				 }else{	 
					 StaticStore.newFragment = new GridScreenView();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }
			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".FirstPageActivity")){
				 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
				 StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
				 StaticStore.fragclassname = classname;
				 if(StaticStore.istablet){
					 StaticStore.gridupdateneeded = true;
					 StaticStore.enableHome = true;
					 StaticStore.isInbox = false;
					 StaticStore.isDashBoardLeftSideView = true;
					 StaticStore.newFragment = new FirstPageActivity();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }else{	 
					 StaticStore.newFragment = new FirstPageActivity();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }
			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".SecondPageActivity")){
				 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
				 StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
				 StaticStore.fragclassname = classname;
				  StaticStore.LogPrinter('i',"Came into GridScreenView part == >");
				 if(StaticStore.istablet){
					 StaticStore.gridupdateneeded = true;
					 StaticStore.enableHome = true;
					 StaticStore.isInbox = false;
					 StaticStore.newFragment = new SecondPageActivity();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }else{	 
					 StaticStore.newFragment = new SecondPageActivity();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }

			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".GridScreenViewActivation")){
				 StaticStore.fragclassname = classname;
				  StaticStore.LogPrinter('i',"Came into GridScreenViewActivation part == >");
				 if(StaticStore.istablet){
//		 StaticStore.gridNofragHeader.setVisibility(View.GONE);//Not To Enable This Code For Automatic Activation Page Call
//		 StaticStore.gridNolineHeader.setVisibility(View.GONE);//Not To Enable This Code For Automatic Activation Page Call
					 context.startActivity(responseIntent);
				 }else{
//		 StaticStore.gridNofragHeader.setVisibility(View.GONE);//Not To Enable This Code For Automatic Activation Page Call
//		 StaticStore.gridNolineHeader.setVisibility(View.GONE);//Not To Enable This Code For Automatic Activation Page Call
					 StaticStore.newFragment = new GridScreenViewActivation_Mobile();
					 changeDetailFragment(StaticStore.newFragment,true,false);
				 }
				 
			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".HelpScreen")){
				 StaticStore.fragclassname = classname;
				  StaticStore.LogPrinter('i',"Came into HelpScreen part == >");
				 String index = responseIntent.getStringExtra("index"); 
				 String title = responseIntent.getStringExtra("title"); 
				 StaticStore.newFragment = new HelpScreen();
				 if(StaticStore.enableHome && !StaticStore.isLogout){
				 StaticStore.gridupdateneeded = true;
				 }else{
				 StaticStore.FromGridActivation = true;
				 }
				 Bundle bundle = new Bundle();
				 bundle.putString("index", index);
				 bundle.putString("title", title);
				 StaticStore.newFragment.setArguments(bundle);
				 changeDetailFragment(StaticStore.newFragment,true,false);
				 
			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".ListSelection")){
				 StaticStore.fragclassname = classname;
				  StaticStore.LogPrinter('i',"Came into ListSelection part == >");
				 int listIndex = responseIntent.getIntExtra("listIndex",999); 
				 String[] menuItem = responseIntent.getStringArrayExtra("menuItem"); 
				 String listHeader = responseIntent.getStringExtra("listHeader"); 
				 Boolean more = responseIntent.getBooleanExtra("more", false);
				 Boolean isImageTextList = responseIntent.getBooleanExtra("isImageTextList", false);
				 int selectedIndex = responseIntent.getIntExtra("selectedIndex", 0);


				  StaticStore.LogPrinter('i',"listIndex  ==> "+listIndex );
				 StaticStore.newFragment = new ListSelection();

				 Bundle bundle = new Bundle();
				 bundle.putInt("listIndex", listIndex);
				 bundle.putStringArray("menuItem", menuItem);
				 bundle.putString("listHeader", listHeader);  
				 bundle.putInt("selectedIndex", selectedIndex);  
				 bundle.putBoolean("more", more);
				 bundle.putBoolean("isImageTextList", isImageTextList);
				 StaticStore.newFragment.setArguments(bundle);
				 changeDetailFragment(StaticStore.newFragment,true,true);
			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".Inbox")){
				 StaticStore.fragclassname = classname;
				 int index = responseIntent.getIntExtra("listIndex",999); 
				 String[] menuItem = responseIntent.getStringArrayExtra("menuItem"); 
				 String messageStatus = responseIntent.getStringExtra("messageStatus"); 
				 StaticStore.newFragment = new Inbox();
				 Bundle bundle = new Bundle();
				 bundle.putInt("listIndex", index);
				 bundle.putStringArray("menuItem", menuItem);
				 bundle.putString("messageStatus", messageStatus);
				 StaticStore.newFragment.setArguments(bundle);
				 changeDetailFragment(StaticStore.newFragment,true,false);


			 }else if(classname.equalsIgnoreCase(StaticStore.PackageName+".DynamicCanvas")){
				  StaticStore.LogPrinter('i',"Came into DynamicCanvas part StaticStore.index == >" +StaticStore.index);
				  StaticStore.LogPrinter('i',"Came into DynamicCanvas part == >" +Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
				  
					 
				 StaticStore.isMpinNeeded = isMPINNeed(StaticStore.menuDesc[StaticStore.index][1].substring(2,4));
				 int counttest = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
				 //&& StaticStore.isMpinNeeded && counttest > 1 && isPreLoginTransaction(StaticStore.menuDesc[StaticStore.index][1].substring(2,4))
				 
				  StaticStore.LogPrinter('i'," DynamicCanvas StaticStore.isMpinNeeded==> "+StaticStore.isMpinNeeded);
				  StaticStore.LogPrinter('i'," DynamicCanvas counttest ==> "+counttest);
				 
				 initialize();
				  StaticStore.LogPrinter('i',"Came into DynamicCanvas part After StaticStore.index == >" +StaticStore.index);
				 if(isInput()){ // && (StaticStore.isMpinNeeded && counttest > 1)
					  StaticStore.LogPrinter('i',"Came IF FFFFFFFFFF DynamicCanvas part == >"+Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
//					 context.startActivity(new Intent(context,Fragment_Activity.class));
					  StaticStore.fragclassname = classname;
					 StaticStore.newFragment = new DynamicCanvas();changeDetailFragment(StaticStore.newFragment,true,false);
				 
				 
				 }else{
					  StaticStore.LogPrinter('i',"Came ELSE eeeeeeeee DynamicCanvas part == >"+Arrays.deepToString(StaticStore.menuDesc[StaticStore.index]));
					 
					 
					  StaticStore.LogPrinter('i'," StaticStore.legValue[i] ==> "+Arrays.deepToString(StaticStore.legValue));
					 new DynamicCanvas().DynamicCanvastest(StaticStore.context);
				 }

			 }else{
				 //				else if(classname.contains("DynamicCanvas")){
				  StaticStore.LogPrinter('i',"Came into else part == >"+classname);
				 //					responseIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				 if(classname.equalsIgnoreCase(StaticStore.PackageName+".Loading") &&  StaticStore.FromGridActivation && StaticStore.istablet){
					  StaticStore.LogPrinter('i',"Came into Loading part == >"+classname);
					  StaticStore.newFragment = new Rightsidedefault();
					  changeDetailFragment(StaticStore.newFragment,true,false);
				 }
				 context.startActivity(responseIntent);
//				 context.finish();

				 //					StaticStore.midlet.startFragment(context,responseIntent);
				 //				}
			 }
			 
			 if(StaticStore.istablet && StaticStore.FromGridActivation){ 
				 StaticStore.prevfragment = new Leftsidedefault(); 
				 changeLeftListFragment(StaticStore.prevfragment,true,false,true);
			 }else if(StaticStore.istablet && StaticStore.enableHome && StaticStore.gridupdateneeded && !StaticStore.isDashBoardLeftSideView){
				 StaticStore.gridupdateneeded = false;
				  StaticStore.LogPrinter('i',"LeftGridScreenView ===>>>>> "+StaticStore.gridupdateneeded);
				 StaticStore.prevfragment = new GridScreenView_Left();
				 changeLeftListFragment(StaticStore.prevfragment,true,false,true);
			 }else if(StaticStore.isDashBoard && StaticStore.istablet && StaticStore.enableHome && StaticStore.gridupdateneeded){
				 StaticStore.gridupdateneeded = false;
				 StaticStore.prevfragment = new FirstPageActivity_Left();
				 changeLeftListFragment(StaticStore.prevfragment,true,false,true);
			 }
			 
			 StaticStore.FromGridActivation = false;
			 StaticStore.LogPrinter('i',"Came into ENDDDDDDDDDDDDDDDD part == >"+classname);

		 }catch (Exception e) {
			 e.printStackTrace();
		 }


	 }
	 
	  public boolean isInput() {
	    	if(StaticStore.isAirline){
	    		return true;
	    	}else{
	    		int numLabels;
	    			numLabels = Integer.parseInt(StaticStore.menuDesc[StaticStore.index][StaticStore.menuDesc[StaticStore.index].length - 4]);
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
	  
	 private void changeDetailFragment(Fragment fragment,boolean animated,boolean addCurrentFragmentToBackStack)
	 {

		 StaticStore.fragmentTransaction = StaticStore.fragmentManager.beginTransaction();
		
		 if (animated){
//			 StaticStore.fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//			 StaticStore.fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
		 }
		 Fragment currentFrag =  StaticStore.fragmentManager.findFragmentById(R.id.frag_Det);
		 String fragName = "NONE";

		 if (currentFrag!=null)
			 fragName = currentFrag.getClass().getSimpleName();
		 if (currentFrag != null){
			 StaticStore.fragmentTransaction.remove(currentFrag);
		 }
		 StaticStore.fragmentTransaction.add(R.id.frag_Det,fragment);
		 StaticStore.fragmentTransaction.commitAllowingStateLoss(); 
	 }
	 
	 private void changeLeftListFragment(Fragment fragment,boolean animated,boolean addCurrentFragmentToBackStack, boolean Leftsidepaneupdate)
	 {
		 
		 StaticStore.Leftsidepane = Leftsidepaneupdate;
		 StaticStore.fragmentTransaction = StaticStore.fragmentManager.beginTransaction();
		
		 if (animated){
//			 StaticStore.fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//			 StaticStore.fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		 }
		 Fragment currentFrag =  StaticStore.fragmentManager.findFragmentById(R.id.frag_List);
		 String fragName = "NONE";

		 if (currentFrag!=null)
			 fragName = currentFrag.getClass().getSimpleName();
		 if (currentFrag != null){
			 StaticStore.fragmentTransaction.remove(currentFrag);
		 }
		 StaticStore.fragmentTransaction.add(R.id.frag_List,fragment);
		 StaticStore.fragmentTransaction.commitAllowingStateLoss(); 
	 }
	 public Intent Loginvalidation() {
		 Intent myIntent = null;
		 if(StaticStore.isOTPBuild)
		 {
			 if(StaticStore.SMS_AUTHENTICATION_GPRSMODE){
				 StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
				 StaticStore.isForgotPassword = false;
				 StaticStore.FromListScreen = false;
				 StaticStore.menuDesc[220] = new String []{"OTP Generation","APOG",StaticStore.myMobileNo,"","1","false","false","Y"};
				 StaticStore.tagType = "APOG";
				 StaticStore.index = 220;
				 myIntent = new Intent(StaticStore.context,DynamicCanvas.class);	
			 }else if(StaticStore.IsPermitted && StaticStore.IsGPRS &&StaticStore.isSuccessGC){
				 if(StaticStore.isDashBoard){
					 myIntent = new Intent(StaticStore.context,FirstPageActivity.class); 
				 }else{
					 myIntent = new Intent(StaticStore.context,GridScreenView.class); 
				 }
			 }else{
				 myIntent = new Intent(StaticStore.context,GridScreenView.class);
			 }
		 }else{
			 if(StaticStore.testbuild){
				 myIntent = new Intent(StaticStore.context,GridScreenView.class);
			 }else{
				 if(StaticStore.IsGPRS) {  
					 if(StaticStore.SMS_AUTHENTICATION_GPRSMODE){
						 StaticStore.index = 155;
						 StaticStore.menuDesc[155][2] = StaticStore.myMobileNo;
						 myIntent = new Intent(StaticStore.context, DynamicCanvas.class);
					 }else{
						 myIntent = new Intent(StaticStore.context,GridScreenView.class);
					 }
				 } else{                                              
					 if(StaticStore.SMS_AUTHENTICATION_SMSMODE){
						 StaticStore.isFromLoginScreen = true;
						 StaticStore.index = 155;
						 StaticStore.menuDesc[155][2] = StaticStore.myMobileNo;
						 myIntent = new Intent(StaticStore.context, DynamicCanvas.class);
					 }else{
						 myIntent = new Intent(StaticStore.context,GridScreenView.class);
					 }
				 }  
			 }
		 }
		 return myIntent;
	 }
	 
	 public void MenuSelected(Context context) {
		 StaticStore.gridNofragHeader.setVisibility(View.VISIBLE);
		 StaticStore.gridNolineHeader.setVisibility(View.VISIBLE);
			// TODO Auto-generated method stub
			Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context);

			StaticStore.LogPrinter('i'," Called from do Click");
			StaticStore.isFromLoginScreen = true;
			StaticStore.isFromVersionUpdate = false;
			StaticStore.FromListScreen = false;
			StaticStore.FromGridActivation = true;
			if(sessionflag){
				 //position; //
				

				StaticStore.LogPrinter('i',"StaticStore.selectedLoginGridIndex ==> "+StaticStore.selectedLoginGridIndex);
				StaticStore.LogPrinter('i',"StaticStore.selectedLoginGridIndex ==> "+StaticStore.selectedLoginGridIndex);
				StaticStore.LogPrinter('i',"StaticStore.IsGPRS ==> "+StaticStore.IsGPRS);

				if(!StaticStore.IsPermitted && !StaticStore.IsPersonalInfoGot && !StaticStore.isOTPBuild){
					StaticStore.index = 154;
					StaticStore.midlet.startFragment(context,new Intent(context,DynamicCanvas.class));
				}
				else if(StaticStore.IsPermitted && StaticStore.IsGPRS &&((!StaticStore.isSuccessGC && StaticStore.selectedLoginGridIndex != 0) || StaticStore.selectedLoginGridIndex == 1))
				{
					StaticStore.menuDesc[220] = new String []{"GPRS Check","APCG","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" + StaticStore.mobileDetails,"","1","false","false","N"};
					StaticStore.tagType = "APCG";
					StaticStore.index = 220;
				StaticStore.midlet.startFragment(context,new Intent(context,DynamicCanvas.class));
				}else{ 
					if ( StaticStore.selectedLoginGridIndex == 0) {

//					StaticStore.midlet.startFragment(context,StaticStore.midlet.getSignUpTypes(context)); // Sign UP
					StaticStore.midlet.startFragment(StaticStore.context,StaticStore.midlet.getAbout(StaticStore.context));
					
					}else if ( StaticStore.selectedLoginGridIndex == 1) {
						
							if(StaticStore.IsPermitted) {
								StaticStore.index = 123;
				    			StaticStore.menuDesc[123][2] = "Login Password";
				 				StaticStore.menuDesc[123][3] = "4-4-N-Y-Y";
								StaticStore.midlet.startFragment(context,new Intent(context,
										DynamicCanvas.class));
							} else {
								StaticStore.menuDesc[114][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
								StaticStore.index = 114;
								StaticStore.FromListScreen = false;
								StaticStore.midlet.startFragment(context,new Intent(context,DynamicCanvas.class));
							}
					}else if ( StaticStore.selectedLoginGridIndex == 2) {
						Intent myIntent = StaticStore.midlet.get_ProductsMenu(context);
						StaticStore.midlet.startFragment(context,myIntent);
//						StaticStore.ToastDisplay(StaticStore.context,"The services will be provided shortly");

					} else if ( StaticStore.selectedLoginGridIndex == 3) {
						//Refer a Friend
						StaticStore.index = 129;
						StaticStore.midlet.startFragment(context,new Intent(context,
									DynamicCanvas.class));
					}else if ( StaticStore.selectedLoginGridIndex == 4) {
						Intent myIntent = StaticStore.midlet.getLocatorMenu(context);
						StaticStore.midlet.startFragment(context,myIntent);
//						StaticStore.ToastDisplay(StaticStore.context,"The services will be provided shortly");
					}else if ( StaticStore.selectedLoginGridIndex == 5) {
						StaticStore.menuDesc[220] = new String []{"Feedback","AP1F;N","001","","1","false","false","N"};
						StaticStore.tagType = "AP1F";
						StaticStore.index = 220;
					StaticStore.midlet.startFragment(context,new Intent(context,
								DynamicCanvas.class));
						
					}else if(StaticStore.selectedLoginGridIndex == 6){
		   				StaticStore.isForgotPassword = true;
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
		   					StaticStore.NoteForgotPWD = true;
		   					StaticStore.index = 115;
		   				StaticStore.midlet.startFragment(StaticStore.context,new Intent(StaticStore.context, DynamicCanvas.class));
		   				}
		   			
	    			
					
					}
				}

			}
		
		}
	public void savelistinit() {
		// TODO Auto-generated method stub
		
		StaticStore.indexCtr = 0;
		StaticStore.enableHome = true;
		StaticStore.listIndexArray = new int[10];
		StaticStore.selectedIndexArray = new int[10];
		StaticStore.listContent = new String[10][10];
		StaticStore.listHeading = new String[10];
		StaticStore.listMore = new boolean[10];		 
		StaticStore.listImageArray = new boolean[10];		 
		StaticStore.midlet.isProcessing = false;
		StaticStore.FromListScreen = false;
	}
	public void onResumeCalled() {
		try{
			if(!StaticStore.isOTPBuild){
				
				IntentFilter filter = new IntentFilter();
				//		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
				filter.addAction("android.intent.action.DATA_SMS_RECEIVED");
				filter.addDataScheme("sms");
				StaticStore.context.registerReceiver(StaticStore.mybroadcast, filter);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onPauseCalled() {
		try{
			if(!StaticStore.isOTPBuild){
				StaticStore.context.unregisterReceiver(StaticStore.mybroadcast);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}