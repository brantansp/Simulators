package com.fss.tmb;

import java.util.Iterator;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StaticStore {
	public static FragmentTransaction fragmentTransaction;
	public static  android.support.v4.app.Fragment newFragment = null;
	public static  android.support.v4.app.Fragment prevfragment = null;
	public static long listIndex = 0;
	public static int listLegCtr = 0;
	public static String[] listIndexArr = new String[25];
	public static String[] selectedListIndexArr = new String[25];
	public static boolean loadingCanceled = false;
	static int[] colors = { 0,Color.rgb(255, 128, 0), 0 };
	static int[] divider = {Color.rgb(255, 128, 0),Color.rgb(255, 255, 255),Color.rgb(255, 128, 0)};
	public static String[] accountNumbers;
	public static mPAY midlet = new mPAY();
	public static boolean isBenSyncMoreClicked = false;
	public static Dialog dialog;
	public static Context context;
	public static AlertDialog alertDialog1;
	public static String registeredUserStatus;
	public static boolean isAccTypeRefresh;
	public static String chequeLength = "0606";
	public static String mPinHexaValue="1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
    public static boolean SMS_AUTHENTICATION_SMSMODE = false;
    public static boolean SMS_AUTHENTICATION_GPRSMODE = false;
    public static int specialDynamicIndexForAccountSyn = 0;
    public static String specialDynamicTAGTYPEForAccountSyn = "";
    public static String mpinNeededTransactions;
    public static boolean isMpinNeeded = false;
    public static boolean isMpinAtLast = false;
    public static boolean isMpinRemoved = false;
    public static boolean forSeperateMPinAtFirst = false;
    public static boolean isPinChangeFromTabbar = false;
    public static int transIndex = 0;
    public static boolean initialFlag=false;
    public static boolean isFrom1T00Response=false;
    public static String[] dynamicTempSignup;
    public static boolean isProgressBarClosed = true;
	public static int i,width,height,orientation;
	public static int tabwidth,tabheight;
	public static BroadcastReceiver mybroadcast = null;
	public static final int mFontsize = 17 ;
	public static final int tFontsize = 24 ;
	public static final int mFontsize_Title = 19 ;
	public static final int tFontsize_Title = 26 ;
	public static final String mFont_Typeface = "Segoe.ttf" ;
	public static final String mFont_Bold_Typeface = "SegoeBold.ttf" ;
	public static int DisplayWidth = 0;
	public static int DisplayHeight = 0;
	public static boolean readMessage = false;
	public static boolean IsPermitted = false;
    public static boolean IsMobileTypeSel   =   false;
    public static boolean IsCDMA      		=   false;
	public static boolean IsTouchScreen = false;
	public static boolean IsPersonalInfoGot = false;
	public static boolean IsGPRS = false;
	public static boolean loadingFlag = false;
	public static boolean isBillpayEnabled = false;
	public static boolean isGetMPIN = false;
	public static String globalMPIN = "";
	public static String[] ArrayLegs = new String[] { "", "" };
	public static String[] legValue;
	public static boolean[] encyptionFlag;
	public static boolean singleFlag        = false;
	public static boolean isSYFlag        = false;
	public static int index;
	public static String StoreTxnNo = "";
	public static String VMN_Number = "";
	public static String myMobileNo = "0";
	public static String tempMobileNo     =   "0";
	public static boolean regenerateClicked;
	public static String GprsUrl = "";
	public static int originalindex = 0;
	public static final int PICK_CONTACT    = 1;  // By ABINAYA.J.A for showing contact picker
	public static String contactPickerNo = ""; // By ABINAYA.J.A for showing contact picker 
	public static String list_name =""; // By ABINAYA.J.A for showing contact picker 
	public static String selection = ""; // By ABINAYA.J.A
	public static String delselection = ""; // By ABINAYA.J.A
	public static  long  date; // By ABINAYA.J.A
	public static int forRechargeSelectedIndex; // By ABINAYA.J.A
	public static boolean forRechargeBeneList;
	public static boolean forRechargeBeneDereg;
	public static boolean forRechargeBeneDetailsDel;
	public static boolean renewMyDeposit;
	public static boolean depositPeriodInMonthsForQuarterly;
	public static boolean depositPeriodInMonthsForNonQuarterly;
	public static boolean depositPeriodInDays;
	public static boolean closeTheAccountOnMaturity;
	public static String[] dynamicTempS555;
	public static boolean dynamicTempS555Flag = false;
	public static boolean dynamicTempS555ConfirmFlag = false;
	public static String[] dynamicTempMD;
	public static boolean dynamicTempMDFlag = false;
	public static boolean dynamicTempMDConfirmFlag = false;
	public static String[] dynamicTempFD;
	public static boolean dynamicTempFDFlag = false;
	public static boolean dynamicTempFDConfirmFlag = false;
	public static String[] dynamicTempND;
	public static boolean dynamicTempNDFlag = false;
	public static boolean dynamicTempND1Flag = false;
	public static boolean dynamicTempND2Flag = false;
	public static boolean dynamicTempNDConfirmFlag = false;
	public static boolean dynamicTempNDFinalFlag = false;
	public static boolean dynamicTempRDFlag = false;
	public static String depositID;
	public static String depositIDConfirm;
	public static String depositIDNegative;
	public static String depositAccNo; 
	public static String depositCustomerName; 
	public static String depositSchemeCode; 
	public static String depositPeriodMonths; 
	public static String depositPeriodDays; 
	public static String depositAccOpenDate; 
	public static String depositMaturityAmt; 
	public static String depositMaturitydate; 
	public static String depositRateOfInterest;
	public static String depositeReceipt;
	public static String depositAccBalanceListSelected; 
	public static String depositAccBalDepositorName;
	public static String depositAccBalAccNo; 
	public static String depositAccBalScheme; 
	public static String depositAccBalDepositdate; 
	public static String depositAccBalDepositAmt; 
	public static String depositAccBalMaturitydate; 
	public static String depositAccBalMaturityAmt; 
	public static String depositAccBalInterestAmt; 
	public static String depositAccBalBalanceAmt; 
	public static String loanBalanceAmt; 
	public static String openDate; 
	public static String loanTotalAmount; 
	public static String loanScheme;
	public static String selectedAccNumberSave = "";
	public static String selectedAccNumberFrom = "";
	public static String selectedAccNumberTo = "";
	public static String selectedAccTypeTo = "";
	public static boolean initialMyOwnAccFlag = false;
	public static boolean secondMyOwnAccFlag = false;
	public static boolean isMyOwnAccSync = false;
	public static boolean forBillerRegistration = false;
	public static boolean forAddBiller = false;
	public static boolean forTNEBNote = false;
	public static boolean forCOCTAX = false;
	public static int neftbenereg = 0;
	public static boolean forAPDOBack = false;
	public static boolean forAPLIBack = false;
	public static boolean forAPLOBack = false;
	public static String instID; 
	public static String N400TxnID; 
	public static String instConfirmDetails; 
	public static String noOfCategories; 
	public static String collegeDetails; 
	public static String collegeDetailsID;
	public static String N400Details; 
	public static String collegeUniqueDetails;
	public static boolean eLobbyLocationFlag = false;
	public static boolean onceListClicked = false;
	public static boolean depositAccBalance = false;
	public static boolean loanBalance = false;
	public static String lastLoginTime = null;
	public static boolean ismobile;
	
//	public static String GprsServiceUrl = "http://mobilesit.fssnet.co.in/MPAYPORTAL";  //SIT
//	public static String GprsServiceUrl = "https://mobiletest.fssnet.co.in/MPAYPORTAL"; //UAT
//	public static String GprsServiceUrl = "http://mobiledvlp.fssnet.co.in/MPAYPORTAL"; //DVLP
//	public static String GprsServiceUrl = "http://mobileprd.fssnet.co.in/MPAYPORTAL"; //PRE PROD
//  public static String GprsServiceUrl = "https://mobile.fssnet.co.in/txnmpayportal";   //PRODUCTION
//	public static String GprsServiceUrl = "http://10.44.112.13:8010/MPAYPORTAL"; // DVLP Local
	public static String GprsServiceUrl = "http://mobileprd.fssnet.co.in/txnmpayportal"; //PRE PROD
	
	public static String buildVersion =  "4.0.0";
	public static final boolean testbuild = false; 
	public static boolean isOTPBuild = true; //for OTP Build make this as true
	public static final boolean Logwrite = 	false;
	public static boolean isOTPVerified = false;
	public static String wapUrl = "";
	public static String SentRequest = "";
	public static String corpSearchString = "";
	public static String subCorpSearchString = "";
	public static String airlineID = "";
	public static String distributorId = "";
	public static String defaultPWD = "";
	public static String publicKey = "";// for
	public static String defaultPublicKey = "";// for
	public static String KSN = "";
	public static String BDK = "";
	public static String applicationPWD = "";
	public static int InputKeyeDelay = 1000;
	public static long RequestSentTime;
	public static long timeDelay = 30000;
	public static String BE = "Balance Enquiry";
	public static String MS = "Mini Statement";
	public static String FT = "Fund Transfer";
    public static String PC = "Change mPIN";
	public static String MPAY = "Service No.";
	public static String secretQues = "";
	public static String offerFirstSubCategory = "";
	public static String offerSecondSubCategory = "";
	public static String offerThirdSubCategory = "";
	public static String encryptStr = "-EnCrYpT";
	public static int selectedPosition = 1;
	public static String[] stringLeg = new String[] { "", "", "" };
	public static boolean withMemory = false;
	public static final int lineGap = 10;
	public static String originalMessage = "";
	public static boolean LogoutImagecalled = false;
    public static String Device_Model = null;
    public static boolean DDdeliveryFlag = false;
    public static HttpConnect httpObj = null;
	public static String httpError = "";
	public static String[] actLegValues = { "", "", "", "", "", "" };
	public static String[] airValues = { "", "", "", "", "" };
	public static String[] passValues;
	public static boolean beFlag = true;
	public static boolean msFlag = true;
	public static boolean m2mFlag = true;
	public static boolean m2aFlag = true;
	public static boolean neftFlag = true;
	public static boolean stopChequeFlag = true;
	public static boolean chequeStatusFlag = true;
	public static boolean cardHotListFlag = true;
	public static boolean billpayFlag = true;
	public static boolean airlineFlag = true;
	public static boolean movieFlag = true;
	public static boolean shopingFlag = true;
	public static boolean mobileRechargeFlag = true;
	public static boolean b2bFlag = true;
	public static boolean templeFlag = true;
	public static boolean feesFlag = true;
	public static boolean IRCTC_Card_Flag = true;
	public static boolean blockCardFlag = true;
	public static boolean unblockCardFlag = true;
	public static boolean locatorFlag = true;
	public static boolean statementRequestFlag = true;
	public static boolean fixedDepositFlag = true;
	public static boolean logOnlyFixedDepositFlag = true;
	public static boolean LoanRequestFlag = true;
	public static boolean creditCardRequestFlag = true;
	public static boolean debitCardRequest = true;
	public static boolean accountRequestFlag = true;
	public static boolean multipleAccountFlag = true;
	public static boolean iCashFlag = true;
	public static boolean onlineFTFlag = true;
	public static boolean timeoutFlag = true;
	public static boolean newLeadFlag = true;
	public static boolean adHocPaymentFlag = true;
	public static boolean vmtFlag = true;
	public static boolean impsFlag = true;
	public static boolean cashPickupFlag = true;
	public static boolean debitCardFlag = true;
	public static boolean moneySendFlag = true;
	public static boolean stmtRqPrintFlag = true;
	public static boolean cahDeliveryFlag = true;
	public static boolean chequePickUpFlag = true;
	public static boolean stmtRqemailFlag = true;
	public static boolean cardLimitFlag = true;
	public static boolean donationFlag = true;
	public static boolean linkingCustFlag = true;
	public static boolean corpratePaymentFlag = true;
	public static boolean eCashFlag = true;
	public static boolean loyalityFlag = true;
	public static boolean feedbackFlag = true;
	public static boolean chequeBookFlag = true;
	public static boolean ATMlocatorFlag = true;
	public static boolean branchLocatorFlag = true;
	public static boolean eLobbyLocatorFlag = true;
	public static String shortCodeNumber = "";
	public static boolean isShortCode = true;
	public static boolean isForgotPWD;
	public static boolean isCommModeSelected;
	public static String bankCode = "463795"; // TMB - 463795  // IOB - 422132
	public static String duckPT = "123457";
	public static String version = "4.0";
	public static int numberOfAccounts = 5;
	public static String servlet = "/servlet/MQSender";
	public static String selectedAccNumber = "";
	public static String[] accType;
	public static String benSearchString;
	public static String ifscBankID = "";
	public static boolean isPincode;
	public static boolean isSettingsSelected = false;
	public static boolean mobileNumberFlag;
	public static String splittedServiceControl = "";
	public static String billPayCustMneName;
	public static String tempApplicationPWD;
	public static String m2mAccHolderName;
	public static String m2mRegMobileNumber;
	public static String m2mregMnemonicName;
	public static int totalAccounts = 0;
	public static String iCashMobNo, iCashAmt, iCashSurCharge,iCashRemitterPin;
	public static String MMID, benMobNo, benNicName;
	public static String[] dynamicListValues;
	public static String helpHeading;
	public static int numberOfTimeout;
	public static int totalTimeouts = 0;
	public static String[] timeoutTran;
	public static String[] timeoutTime;
	public static String complaintValues = "";
	public static int maxTimeoutCount = 5;
	public static String[][] withinDereg;
	public static String regSearchString = " ";
	public static String accNumberHeadingName = "Select A/C";
	public static boolean signInFlag = false;
	public static String[] accessibleAccountNumbers;
	public static String[] accessibleAccTypes;
	public static String []IRCTC_CardIssuance_confirm = new String[2];
	public static boolean isMoreClicked;
	public static String[] dynamicMenuTemp;
	public static String[] Display_header;
	public static int tempIndex;
	public static short indexCtr = 0;
	public static boolean isAppNewlyopened = true;
	public static int maxAccLengthFromResponse = 0;
	public static int selectedListIndex = 0;
	public static int recCount;
	public static boolean DDFlag = true;
	public static String selectedAccType = "";
	public static boolean fromList = false;
	public static boolean isSearchAndRegister = false;// neft mod
	public static boolean isUpdateClicked;
	public static boolean wapFlag;
	public static boolean pinChangeFlag;
	public static boolean partnerFlag;
	public static String mPINString = "mPIN";
	public static int indexAir = 1000;
	public static boolean isAirline = false;
	public static String airlineTitle = "";
	public static String[] airlabelForPas = new String[] { "First Name","Last Name", "Age" };
	public static String[] airvalidationForPas = new String[] { "1-20-ANW-N-N","1-20-ANW-N-N", "1-3-N-N-N" };
	public static String[] airlabelForBuk = new String[] { "First Name","Last Name" };
	public static String[] airvalidationForBuk = new String[] { "1-20-ANW-N-N","1-20-ANW-N-N" };
	public static boolean airline1st;
	public static int[] listIndexArray;
	public static boolean[] listImageArray;
	public static int[] selectedIndexArray;
	public static String[][] listContent;
	public static String[] listHeading;
	public static boolean[] listMore;
	public static boolean isBack = false;
	public static boolean isNoInput = false;
	public static boolean fromGprs = false;
	public static boolean selectClassBack = false;
	public static String responseMsg = "";
	public static String flightTiming;
	public static boolean fromAccountList = false;
	public static int selectedGridIndex = 0;
	public static int selectedLoginGridIndex = 0;
	public static int instrument;
	public static boolean fromChequePickup = false;
	public static String tempMPin;
	public static boolean appStarted = false;
	public static boolean messageReceived = false;
	public static boolean fromCurrentSavingList = false;
	public static boolean fromChequeList = false;
	public static boolean fromFdDisplayable = false;
	public static String fdMPin = "";
	public static String fdNoOfDays = "";
	public static String fdAmount = "";
	public static boolean fromFdDaysToAmount = false;
	public static int check = 0;
	public static Window window;
	public static boolean enableHome = false;
	public static boolean IsPresentment = false; //TNEB DX00
	public static String tagTypePrev = " ";
	public static String cpMPin = "";
	public static String cpInstruments = "";
	public static boolean isFromLoginScreen = false;
    public static boolean isSuccessGC = false;
    public static String mobileType =  "ANDROID"+android.os.Build.VERSION.RELEASE;
    public static String mobileScreenSize =  "H"+StaticStore.height+"W"+StaticStore.width;
    public static int mpincount = 0;
    public static String mobileDetails =  " ";
    public static boolean isPreLoginNotification =  false;
    public static boolean isPostLoginNotification =  false;
    public static String ImageStreamingURL =  "";
    public static boolean isFromVersionUpdate =  false;
    public static String chequeMin = "1";
    public static String chequeMax = "6";
    public static String backupmenuDesc[][];
    
	public static String menuDesc[][] = new String[][] {
		    {"Add Beneficiary","APRM;Y",mPINString,StaticStore.bankCode,"Mobile No.",
             "Nickname","0001","4-4-N-Y-Y","","10-10-N-N-N","1-20-ANW-N-N","","5","true","true","Y"},//0
            {"Add Beneficiary","APRA;Y",mPINString,StaticStore.bankCode,"A/C No.","001",
             "Nickname","4-4-N-Y-Y","","15-15-N-N-N","","1-20-ANW-N-N","5","true","true","Y"},//1
            {"Beneficiary List","APRL;Y",mPINString,"","1","false","false"},//2
            {"Mobile Fund Transfer","APFT;Y",mPINString,"Amount (Rs.)","Remarks","","1-7-ND-N-N","0-25-AN-N-N","3","false","true","N"},//3
            {"A/C Fund Transfer","APFA;Y",mPINString,"Amount (Rs.)","","1-7-ND-N-N","2","true","true","N"},//4
            {"Change mPIN", "APPC;Y", mPINString, "New mPIN", "Confirm New mPIN",
            "4-4-N-Y-Y", "4-4-N-Y-Y", "4-4-N-Y-Y", "3" ,"true","true","N"},//5
            {"Payment Only","APAI;Y",mPINString,"001","4-4-N-Y-Y","","2","false","true","Y"},//6
            {"Payment Only","APAB;Y",mPINString,"001","BRN No.","","","1-6-N-N-N","3","true","true","Y"},//7
            {"Payment Only","APPA;Y",mPINString,"Y","001","","","","3","false","true","N"},//8  // Airline Confirmation
            {"Booking & Payment","APAS;Y",mPINString,"From","To","Departure Date (DDMMYYYY)","Departure Time","Economy","No. of Passengers",
             "No. of Adults","No. of Children","No. of Infants","001","4-4-N-Y-Y","3-20-ANW-N-N","3-20-ANW-N-N","8-8-D-N-N","","","1-2-N-N-N",
            "1-2-N-N-N","1-2-N-N-N","1-2-N-N-N","","11","true","true","Y"},//9
            {"Customer Details","APCD;Y",mPINString,"First Name","Last Name","","1-20-ANW-N-N","1-20-ANW-N-N","3","true","true"},//10
            {"Add Biller","APB1;Y",mPINString,"E-mail ID","4-4-N-Y-Y","4-25-AN-N-N","2","true","true"},//11
            {"Add Biller","APB2;Y",mPINString,"Flag","001","4-4-N-Y-Y","","","3","false","false","Y"},//12
            {"Add Biller","APB3;Y",mPINString,"Biller ID","Flag","","","","3","false","false","Y"},//13
            {"Add Biller","APB3;Y",mPINString,"001","001","001","001","001","","","","","","","6","true","true","Y"},//14
            {"Balance Enquiry","APBE;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//15
            {"Mini Statement","APMS;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//16
            {},//17 Used for dynamic biller linking. Dont modify this array//when u Change mPIN length change it into unregbillers array in.
            {"Registered Bill Payment","AP1B;Y",mPINString,"A","CustMneNme","001","","","","","4","false","false","Y"},//18 APB5;Y Changed to AP1B
            {"Pay Biller","APB6;Y",mPINString,"CustMneName","Payment Flag","Bill No.","Amount (Rs.)","TXNID","","","","","","","6","false","false","N"},//19
            {"Pay Biller","APB6;Y",mPINString,"CustMneName","Payment Flag","Bill No.","Amount (Rs.)","TXNID","","","","","1-10-ND-N-N","","6","false","false","N"},//20
            {"Registered Bill Payment","APBN;Y",mPINString,"001","","","2","true","true"},//21
            {"Pay Biller","APB6;Y",mPINString,"CustMneName","Payment Flag","Bill No.","Amount (Rs.)","001","","","","0-35-AN-N-N","1-10-ND-N-N","","6","true","true","N"},//22
            {"Delete Biller","APBY;Y",mPINString,"Nickname","","","2","false","false","N"},//23
            {},//24 Used for dynamic adhoc payment. Dont modify this array
            {"Donation","APTL;Y",mPINString,"001","","","2","true","true"},//25
            {"Donation","APTD;Y",mPINString,"001","001","001","Amount (Rs.)","","","","","1-7-N-N-N","5","true","true"},//26
            {"Movie Ticketing","APM1;Y",mPINString,"City/Town","Language"," "," ","No. of Tickets","Date(DDMMYYYY)-Optional","001",
            "4-4-N-Y-Y","1-16-ANW-N-N","1-16-ANW-N-N","","","1-2-N-N-N","0-8-D-N-N","","8","true","true","Y"},//27
            {"Movie Ticketing","APM1;Y",mPINString,"001","001","001","001","001","001","001",
            "","","","","","","","","8","false","true","Y"},//28
            {"Movie Ticketing","APM2;Y",mPINString,"001","001","001","001","001","001","001","001","001",
            "","","","","","","","","","","10","false","false","Y"},//29
            {"Movie Ticketing","APM3;Y",mPINString,"Region Name","Language","Venue Name","Cinema Name","No Of Tickets","Show-Date","Show-Time","Price ID","Price",
            "","","","","","","","","","","10","false","false","Y"},//30
            {"Movie Ticketing","APM4;Y",mPINString,"TXN-ID","Order-ID","Ticket-Price","No. of Tickets","","","","","","5","false","false","N"},//31
            {"Mobile Recharge","APR5;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"},//32   
        	{"Mobile Recharge","APR6;Y",mPINString,"","Mobile No.","Amount (Rs.)","","","","10-10-N-N-N","1-7-N-N-N","","5","false","false","Y"},//33
            {"Institution Registration","APIS;Y",mPINString,"Search Institution","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//34
            {"Institution Registration","APIS;Y",mPINString,"001","001","","","","3","true","true","Y"},//35
            {"Institution Registration","APIR;Y",mPINString,"001","","","2","false","false","N"},//36
            {"Fees Payment","APIL;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"},//37
            {"Fees Payment","API1;Y",mPINString,"001","Roll No.","","","2-12-N-N-N","3","true","true","Y"},//38
            {"Fees Payment","API2;Y",mPINString,"001","001","001","001","001","","","","","","","6","false","false","Y"},//39
            {"Cheque Book","APL1;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//40
            {"Demand Draft","APLY;Y",mPINString,"Favour Of","Payable At","Amount (Rs.)","4-4-N-Y-Y","3-30-AN-N-N","3-30-AN-N-N","1-10-ND-N-N","4","true","true","N"},//41
            {"Request for A/C Statement","APL3;Y",mPINString,"From Date (DDMMYYYY)","To Date (DDMMYYYY)","4-4-N-Y-Y","8-8-D-N-N","8-8-D-N-N","3","true","true","N"},//42
            {"Stop Cheque","APL4;Y",mPINString,"Cheque No.","4-4-N-Y-Y",StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N","2","true","true","N"},//43
            {"Fixed Deposit","APL5;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//44
            {"Mobile Number Registration","APL6;Y",mPINString,"4-4-N-Y-Y","1","false","false"},//45
            {"Loan","APL7;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//46
            {"Credit Card","APL8;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//47
            {"Debit Card","APL9;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//48
            {"Request for New A/C","APL6;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//49
            {"Internet Banking PIN","APLZ;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//50
            {"ATM","APAL;N","L","Search City","Search Location","001","","0-20-ANW-N-N","0-20-ANW-N-N","","4","true","true","N"},//51
            {"Location","AP1L;N","L","Search City","Search Location","001","","0-20-ANW-N-N","0-20-ANW-N-N","","4","true","true","N"},//52
            {"Enable/Disable Transaction","APE5;Y",mPINString,"4-4-N-Y-Y","1","false","false","Y"},//53
            {"Disable Transation","APDE;Y",mPINString,"N","001","","","","3","true","true","N"},//54
            {"Shopping","APCP;Y",mPINString,"Amount (Rs.)",StaticStore.bankCode,"Merchant Nickname","4-4-N-Y-Y","1-10-ND-N-N","","1-25-ANW-N-N","4","true","true","Y"},//55
            {"Corporate Search", "APCS;Y", mPINString, "Y",
             "Search Corporate",  "001","4-4-N-Y-Y", "", "0-20-ANW-N-N", "", "4","true","true"},//56-4
            {"Sub-Corporate Search", "APSS;Y", mPINString, "001","Y","Search Sub-corporate", "001",
             "", "", "", "0-20-ANW-N-N", "", "5","true","true"},//57 - 5
            {},//58 - 14
            {"Sub-Corporate List","APSS;Y",mPINString,"001","N","001","001","","","","","","5","true","true"},//59 -15
            {"Corporate Registration","APSR;Y",mPINString,"001","N","Distributor ID:","","","","0-12-AN-N-N","4","true","true"},//60 - 7
            {"Sub-Corporate Registration","APSR;Y",mPINString,"001","Y","001","Distributor ID","","","","","0-12-AN-N-N","5","true","true"},//61
            {"Payment", "APPS;Y", mPINString,"Transaction ID","Corporate ID","Sub Corporate ID","Amount (Rs.)","Distributor ID","Invoice No.",
              "","","","","1-10-ND-N-N","","0-20-N-N-N", "7","true","true"},//62 - 9  // "With Invoice No.",
            {"Payment", "APPS;Y", mPINString,"Transaction ID","Corporate ID","Sub Corporate ID","Amount (Rs.)","distributor ID"," ",
              "","","","","1-10-ND-N-N","","", "7","true","true"},//63 - 9  // "Without Invoice No.",
            {"SI-Registration", "APSI;Y", mPINString,"Corporate ID","Sub corporate ID","Per Day Transaction Limit", "Per Transaction Limit", "Distributor ID",
                     "", "", "","1-10-ND-N-N", "1-10-ND-N-N", "", "6","true","true"},//64 - 11
            {"Deregistration", "APDR;Y", mPINString,"Corporate ID","sub corporate ID","Distributor ID","", "", "","",  "4","true","true"},//65 - 12
            {"Forgot Password", "APCF;Y", mPINString,"Corporate ID","sub corporate ID","Distributro ID","", "", "","", "4","true","true"},//66 - 13
            {"Payment Only","APAR;Y",mPINString,"001","Title","First Name","Last Name","Passenger Count","",
             "","","1-20-ANW-N-N","1-20-ANW-N-N","1-1-N-Y-N","6","true","true","Y"},//67
            {"Booking & Payment","APAS;Y",mPINString,"001","001","001","001","001","001",
             "001","001","001","001","","","","","","","",
            "","","","","11","true","true","Y"},//68
            {"Booking & Payment","APAC;Y",mPINString,"001","001","001","","","","","4","false","false","N"},//69
            {"Delete Biller","APBZ;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"},//70
            {"Cheque Status","APLW;Y",mPINString,"Cheque No.","4-4-N-Y-Y",StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N","2","true","true","N"},//71
            {"Hotlist Debit Card","APL0;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//72
            {"Un-Block Card","APL2;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//73
            {"ATM","APAL;N","P","Pincode"," ","001","","6-6-N-N-N","","","4","true","true","N"},//74 mid:12897
            {"Branch","AP1L;N","P","Pincode"," ","001","","6-6-N-N-N","","","4","true","true","N"},//75
            {"CFT Registered Lists", "APSP;Y", mPINString,"Mode Flag", "Search Beneficiary","001","4-4-N-Y-Y","","0-20-ANW-N-N","", "4","true","true"},//76 - 8
            {"Shopping","APCN;Y",mPINString,"001","001","4-4-N-Y-Y","","","3","false","false","N"},//77
            {"Payment Only","APR1;Y",mPINString,"001","BRN No.","","","1-6-N-N-N","3","true","true","N"},//78
            {"Booking & Payment","APR2;Y",mPINString,"Order ID","4-4-N-Y-Y","5-20-AN-N-N","2","true","true","N"},//79
            {"Enable/Disable Transaction","APE6;Y",mPINString,"001","","","2","false","false","N"},//80
            {"Bank Download","APQ1;Y",mPINString,"001","","","2","true","true"},//81
            {"Add Beneficiary","APQ2;Y",mPINString,"A/C No.","001","IFS Code","Beneficiary Name",
             "","6-19-ANW-N-N","","11-11-ANW-N-N","1-20-AN-N-N","5","true","true","Y"},//82//4-4-N-Y-Y
            {"Add Beneficiary","APQ3;Y",mPINString,"A/C No.","001","IFS Code","Nickname","","","","","","5","false","false","N"},//83
            {"Partner Bank","APQ4;Y",mPINString,"A/C No.","001","001","Nickname",
            "4-4-N-Y-Y","6-19-ANW-N-N","","","1-20-ANW-N-N","5","true","true"},//84
            {"VMT Registration","APQ5;Y;",mPINString,"Card No.","Expiry Date","Nickname",
            "4-4-N-Y-Y","16-19-N-N-N","4-4-N-N-N","1-20-ANW-N-N","4","true","true"},//85
            {"MMT Registration","APQ6;Y",mPINString,"Card No.","Expiry Date","Nickname",
            "4-4-N-Y-Y","16-19-N-N-N","4-4-N-N-N","1-20-ANW-N-N","4","true","true"},//86
            {"Pay Beneficiary","APQ7;Y",mPINString,"001","001","4-4-N-Y-Y","","","3","false","false","Y"},//87
            {"Pay Beneficiary","APQ8;Y",mPINString,"Amount (Rs.)","Remarks","001","","1-7-N-N-N","0-25-AN-N-N","","4","false","false","N"},//88
            {"Partner Bank Registered List","APQ9;Y",mPINString,"001","001","4-4-N-Y-Y","","","3","true","true"},//89
            {"Partner Bank Fund Transfer","APQA;Y",mPINString,"Amount (Rs.)","001","4-4-N-Y-Y","1-10-ND-N-N","","3","true","true"},//90
            {"Delete Beneficiary","AP4D;Y",mPINString,"001","","","2","false","false","N"},//91
            {"Donation","APT1;Y",mPINString,"Search Entity","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//92
            {"Donation","APT1;Y",mPINString,"001","001","","","","3","false","false","Y"},//93
            {"Donation","APT2;Y",mPINString,"001","001","","","","3","false","false","Y"},//94 //  Select Scheme
            {"Donation","APT3;Y",mPINString,"001","001","001","","","","","4","false","false","Y"},//95   //  Select Sub-schemes
            {"Donation","APT4;Y",mPINString,"001","001","001","001","001","", //  Confirm Temple Payment Details
            "","","","","","6","false","false","Y"},//96
            {"Donation","APT5;Y",mPINString,"001","001","001","001","001","Amount (Rs.)","001",
            "","","","","","","1-9-N-N-N","","8","false","false","N"},//97 mid: 12985 -ND- to -N-
            {"Within Bank Deregistration","APF4;Y",mPINString,"D","001","4-4-N-Y-Y","","","3","true","true"},//98
            {"Within Bank Deregistration","APF3;Y",mPINString,"001","","","2","true","true"},//99
            {"Pay Beneficiary","APF1;Y",mPINString,"P","001","4-4-N-Y-Y","","","3","false","false","Y"},//100
            {"Pay Beneficiary","APFT;Y",mPINString,"Amount (Rs.)","Remarks","001","","1-10-ND-N-N","0-25-AN-N-N","","4","false","false","N"},//101
            {"Pay Beneficiary","APF2;Y",mPINString,"P","001","4-4-N-Y-Y","","","3","false","false","Y"},//102
            {"Pay Beneficiary","APFA;Y",mPINString,"Amount (Rs.)","Remarks","001","","1-10-ND-N-N","0-25-AN-N-N","","4","false","false","N"},//103
            {"Fees Payment","API4;Y",mPINString,"001","001","001","001","001","001","001","001",
            "","","","","","","","","","9","false","false","Y"},//104
            {"Fees Payment","API2;Y",mPINString,"001","001","001","001","","","","","","5","true","true","Y"},//105
            {"Fees Payment","API3;Y",mPINString,"001","001","001","001","","","","","","5","false","false","Y"},//106
            {"Fees Payment","APIP;Y",mPINString,"001","001","001","001","001","001","001","001","001","001",
            "001","Amount (Rs.)","001","","","","","","","","","","","","","1-10-ND-N-N","","14","true","true","N"},//107
            {"Fees Payment","APIP;Y",mPINString,"001","001","001","001","001","001","001","001","001","Room No.",
            "001","Amount (Rs.)","001","","","","","","","","","","","0-12-AN-N-N","","1-10-ND-N-N","","14","false","false","N"},//108
            {"Fees Payment","APIP;Y",mPINString,"001","001","001","001","001","001","001","001","001","Room No.",
            "001","001","001","","","","","","","","","","","","","","","14","true","true","N"},//109
            {"Fees Payment","APIP;Y",mPINString,"001","001","001","001","001","001","001","001","001","Room No.",
            "001","001","001","","","","","","","","","","","0-12-AN-N-N","","","","14","true","true","N"},//110
            {"Institution Deregistration","API5;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"},//111
            {"Institution Deregistration","APID;Y",mPINString,"001","","","2","false","false","N"},//112
            {"TMB mConnect Login","APMU","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" +  StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails,"","1","false","false","N"},//113
            {(StaticStore.isForgotPassword?"Create Password":"TMB mConnect Login"),"APNA","BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" +  StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails,"Login Password","Confirm Login Password","","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"},//114
            {"Forgot Password","APP3;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//115
            {"Forgot Password","APFP","001","Your Answer","","0-12-AN-N-N","2","true","true"},//116
            {"Change Login Password","APCW","Select New Password","Confirm New Password","4-4-N-Y-Y","4-4-N-Y-Y","2","true","true","N"},//117
            {"TMB mConnect Service No.","1111;Y","Service No.","4-10-N-N-N","1","false","false","N"},//118
            {"TMB mConnect Service Url","2222;Y","Service URL","0-150-AN-N-N","1","false","false","N"},//119
            {"Pay Biller","AP1N;Y",mPINString,"001","001","001","001","001","001","001","001","001","001","001","001",
            "4-4-N-Y-Y","","","","","","","","","","","","","13","false","false"},//120
            {"Defer Bill","APDN;Y",mPINString,"001","001","001","001","Defer Date(DDMMYYYY)","4-4-N-Y-Y","","","","","8-8-D-N-N","6","true","true"},//121
            {"Pay Biller","AP1N;Y",mPINString,"001","001","001","001","001","001","001","001","001","001","Amount (Rs.)","001",
            "4-4-N-Y-Y","","","","","","","","","","","1-10-ND-N-N","","13","true","true"},//122
            {"TMB mConnect Login","APUM","Login Password","4-4-N-Y-Y","1","false","false","N"},//123
            {"Add Beneficiary","APC1;Y",mPINString,StaticStore.bankCode,"001",
             "001","Beneficiary Acc No.","","","","","","5","false","false","N"},//124
            {"Add Beneficiary","APC2;Y",mPINString,StaticStore.bankCode,"001","001",
             "001","","","","","","5","false","false","N"},//125
            {"WAP","APZP;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//126
            {"Configure/Update E-mail ID","APZ6;Y",mPINString,"4-4-N-Y-Y","1","false","false","Y"},//127
            {"Configure/Update E-mail ID","APZ3;Y",mPINString,"E-mail ID","","1-35-AN-N-N","2","false","false","N"},//128
            {"Refer a Friend","APZ5;N","Friend Name","Friend's E-mail ID","Friend's Mobile No.","1-35-ANW-N-N","1-35-AN-N-N","10-10-N-N-N","3","true","true","N"},//129
            {"Promotional Campaign","AP1Y;Y",mPINString,"001","001","4-4-N-Y-Y","","","3","true","true"},//130
            {"Promotional Campaign","AP3Y;Y",mPINString,"001","Defer Date(DDMMYYYY)","001","4-4-N-Y-Y","","8-8-D-N-N","","4","true","true"},//131
            {"CFT Registered Lists", "APSP;Y", mPINString,"Mode Flag", "Search Beneficiary","001","4-4-N-Y-Y","","","", "4","true","true"},//132- More 76 - 8
            {"Payment","APPL;Y",mPINString,"Mode Flag","Corp","Sub Corp","001","4-4-N-Y-Y","","","","","5","true","true"},//133
            {"Add Biller","APB9;Y",mPINString,"Cat ID","Search State (optional)","Search City (optional)","Flag","001",
              "","","0-20-ANW-N-N","0-20-ANW-N-N","","","6","true","true","Y"},//134
            {"Add Biller","APB9;Y",mPINString,"Cat ID","Search State (optional)","Search City (optional)","Flag","001",
              "","","","","","","6","true","true","Y"},//135
            {"Booking & Payment","APA1;Y",mPINString,"Flight ID","From","To","Journey Date","Ticktes","Adults","Child","Infants","Flight Class","001",
              "","","","","","","","","","","","","11","false","false","Y"},//136
            {"Reactivate TMB mConnect","APCA","Login Password","4-4-N-N-Y","1","false","false","N"},//137
            {"Change Login Password","APP5","Login Password","New Password","Confirm New Password","4-4-N-Y-Y","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"},//138
            {"IFSC Search","APS1;Y",mPINString,"Search Bank","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//139
            {"IFSC Search","APS2;Y",mPINString,"Bankname","Search Location","001","","","0-20-ANW-N-N","","4","true","true","Y"},//140
            {"IFSC Search","AP3S;Y",mPINString,"Bankname","Location","001","","","","","4","false","false","N"},//141
            {"IFSC Search","APS1;Y",mPINString,"Search Bank","001","","","","3","true","true","Y"},//142
            {"IFSC Search","APS2;Y",mPINString,"Bankname","Search Location","001","","","","","4","true","true","Y"},//143(IFSC)
            {"ATM","APSA;Y",mPINString,"P","Pincode"," ","001","","","","","","5","false","false","N"},//144
            {"Branch","AP1L;Y",mPINString,"P","Pincode"," ","001","","","","","","5","false","false","N"},//145
            {"Registered Bill Payment","APBX;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","Y"},//146
            {"Registered Bill Payment","APBW;Y",mPINString,"Customer Mne Name","","","2","false","false","Y"},//147
            {"Register Feedback","APFC;Y",mPINString,"Feedback","4-4-N-Y-Y","1-80-AN-N-N","2","true","true","N"},//148
            {"Status Enquiry","APFS;Y",mPINString,"Ticket ID","4-4-N-Y-Y","12-12-N-N-N","2","true","true","N"},//149
            {"Bank Search","AP4S;Y",mPINString,"IFS Code","4-4-N-Y-Y","11-11-ANW-N-N","2","true","true","N"},//150
            {"TMB mConnect Service No.","3333;Y","Service No.","6-6-N-N-N","1","false","false","N"},//151
            {},//152
            {},//153
            {"Checking Internet Connectivity","APGT","","","1","false","false","N"},//154
            {"Authenticating Mobile No.","APCM",StaticStore.myMobileNo,"","1","false","false","N"},//155 
            {"Deregistration","APDG;Y",mPINString,"4-4-N-Y-Y","1","false","false","N"},//156
            {"Beneficiary Details","APE3;Y",mPINString,"0001","4-4-N-Y-Y","","2","true","true"},//157
            {"Beneficiary Details","APE4;Y",mPINString,"0001","4-4-N-Y-Y","","2","true","true"},//158
            {"Link an A/C","APAA;Y",mPINString,"A/C No.","A/C Type","4-4-N-Y-Y","6-19-ANW-N-N","","3","true","true","N"},//159
            {"De-Link an A/C","APDA;Y",mPINString,"A/C No.","4-4-N-Y-Y","","2","false","false","N"},//160
            {"Remittance","APSC;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","Confirm Remitter PIN","Amount (Rs.)",
            "4-4-N-Y-Y","10-10-N-N-N","4-4-N-Y-Y","4-4-N-Y-Y","1-9-N-N-N","5","true","true","Y"},//161
             {"Remittance","APEC;Y",mPINString,"Mobile No.","Surcharge","Amount (Rs.)","TXNID","","","","","","5","false","false","N"},//156-162
             {"Status","APSE;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "4-4-N-Y-Y","10-10-N-N-N","4-4-N-Y-Y","","4","true","true","N"},//157-163
             {"Status","APSE;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "","","","","4","true","true","N"},//158-164
             {"Forgot PIN","APPF;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "4-4-N-Y-Y","10-10-N-N-N","4-4-N-Y-Y","","4","true","true","Y"},//159-165
             {"Forgot PIN","APPF;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "","","","","4","false","false","Y"},//160-166
             {"Forgot PIN","APFF;Y",mPINString,"Beneficiary Mobile No.","Reciept No.",
             "","","","3","false","false","N"},//161-167
             {"Cancel eCash ","APCE;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "4-4-N-Y-Y","10-10-N-N-N","4-4-N-Y-Y","","4","true","true","Y"},//162-168
             {"Cancel eCash ","APCE;Y",mPINString,"Beneficiary Mobile No.","Remitter PIN","0001",
             "","","","","4","true","true","Y"},//163-169
             {"Cancel eCash ","APCC;Y",mPINString,"Beneficiary Mobile No.","Reciept No.",
             "","","","3","false","false","N"},//164-170
             {"Add Beneficiary","APW1;Y",mPINString,"Mobile No.","MMID No.","Nickname",
             "4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-20-ANW-N-N","4","true","true","Y"},//165-171
             {"Add Beneficiary","APW2;Y",mPINString,"MMID","Mobile No.","Nickname",
             "","","","","4","false","false","N"},//166-172
             {"Pay Beneficiary","APW3;Y",mPINString,"P","Search Beneficiary","001","4-4-N-Y-Y","","0-20-ANW-N-N","","4","true","true","Y"},//167-173
             {"Pay Beneficiary","APW4;Y",mPINString,"Amount (Rs.)","Remarks","001","","1-10-ND-N-N","0-25-AN-N-N","","4","false","false","N"},//168-174
             {"MBIN Search","APS4;Y",mPINString,"Search Bank","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true"},//169-175
             {"Bank Search By MBIN","APS5;Y",mPINString,"MBIN","4-4-N-Y-Y","0-4-N-N-N","2","true","true"},//170-176
             {"Retrieve MMID","APS6;Y",mPINString,"Acc No.","4-4-N-Y-Y","","2","false","false","N"},//171-177
             {"Generate MMID","APIM;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","N"},//173-178
             {"Instant Pay","APQI;Y",mPINString,"Enter Beneficiary Mobile No.","Enter Beneficiary MMID No.","Enter Amount","Remarks","4-4-N-Y-Y","10-10-N-N-N","7-7-N-N-N","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"},//174-179
             {"Transaction Timeout Count","APTC","Timeout Count","1-1-N-N-N","1","false","false","N"},//180
             {"Transaction Timeout Count","APTO","Last Tran and Time","","1","false","false","N"},//181
             {"Delete Beneficiary","AP2L;Y",mPINString," Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//182
             {"Delete Beneficiary","AP2D;Y",mPINString,"Nickname","","","2","false","false","N"},//183
             {"Delete Beneficiary","AP3L;Y",mPINString," Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//182
             {"Delete Beneficiary","AP3D;Y",mPINString,"Nickname","","","2","false","false","N"},//185
             {"Beneficiary Details","APD5;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","N"},//186
             {"Beneficiary Details","APD6;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","N"},//187
             {"Account Sync/update","APSY","001","","1","false","false","N"},//188
             {"Visa Card Beneficiary Registration","APV1;Y",mPINString,"Nickname","Card No.","4-4-N-Y-Y","1-20-ANW-N-N","16-19-N-Y-N","3","true","true","N"},//189
             {"Visa Card Bill Payment ","APV2;Y",mPINString,"Search String","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","Y"},//190
             {"Visa Card Bill Payment ","APV3;Y",mPINString,"Amount (Rs.)","001","","1-10-ND-N-N","","3","false","false","N"},//191
             {"Delete Beneficiary","APV5;Y",mPINString,"001","","","2","false","false"},//192 
             {"Delete Beneficiary","APV4;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true"},//193
             {"Beneficiary Payment Status","APNS;Y",mPINString,"Reference No.","4-4-N-Y-Y","4-16-AN-N-N","2","false","false","N"},//194
             {"Recharge Status Enquiry","AP7T;Y",mPINString," ","Enter Transaction ID (Optional)","1","","","0-12-N-N-N","","4","false","false","N"},//195 //4-4-N-Y-Y
             {"Delete Beneficiary","AP4L;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","Y"},//196                                                                     
             {"Request for Cash Pickup","APG1;Y",mPINString,"Amount (Rs.)","Date Of Pickup(DDMMYYYY)","Preferred Hour(00-23)","Preferred Minute(00,15,30,45)","4-4-N-Y-Y","1-10-ND-N-N","8-8-N-N-N","2-2-N-N-N","2-2-N-N-N","5","true","true","N"},//197
             {"Request for Cash Delivery","APG2;Y",mPINString,"Cheque No.","Amount (Rs.)","Cheque Date(DDMMYYYY)","Date Of Delivery(DDMMYYYY)","Preferred Hour(00-23)","Preferred Minute(00,15,30,45)","4-4-N-Y-Y",StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N","1-10-ND-N-N","8-8-N-N-N","8-8-N-N-N","2-2-N-N-N","2-2-N-N-N","7","true","true","N"},//198
             {"Cheque Pickup", "APG3;Y", mPINString, "", "Cheque No.",
					"Date Of Pickup(DDMMYYYY)", "Preferred Hour(00-23)",
					"Preferred Minute(00,15,30,45)", "", "",StaticStore.chequeMin+"-"+StaticStore.chequeMax+"-N-N-N",
					"8-8-N-N-N", "2-2-N-N-N", "2-2-N-N-N", "6", "false",
					"false" ,"N"},// 199
             {"Request for Draft Delivery","APG4;Y",mPINString,"Amount (Rs.)","Date Of  Delivery(DDMMYYYY)","Preferred Hour(00-23)","Preferred Minute(00,15,30,45)","Beneficiary Name","Payee Location","4-4-N-Y-Y","1-10-ND-N-N","8-8-N-N-N","2-2-N-N-N","2-2-N-N-N","1-30-AN-N-N","1-30-N-N","0-10-N-N-N","0-10-N-N-N","7","true","true","N"},//200
             {"Cheque Book Request","APG7;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","N"},//201
             {"Request for A/C Statement","APL3;Y",mPINString,"From Date(DDMMYYYY)","To Date(DDMMYYYY)","4-4-N-Y-Y","8-8-N-N-N","8-8-N-N-N","3","true","true","N"},//202
             {"Request for Statement - Email","APG5;Y",mPINString,"From Date(DDMMYYYY)","To Date(DDMMYYYY)","4-4-N-Y-Y","8-8-N-N-N","8-8-N-N-N","3","true","true","N"},//203
             {"Request for a New Product","APG6;Y",mPINString,"","Preferred Branch","Contact Date(DDMMYYYY)","Contact Time-HH(01-24)","","","6-20-AN-N-N","8-8-N-N-N","2-2-N-N-N","5","true","true","N"},//204
             {"Change mPIN", "APNC;Y", mPINString, "New mPIN", "Confirm New mPIN",
             "4-4-N-Y-Y", "4-4-N-Y-Y", "4-4-N-Y-Y", "3" ,"true","true","N"},//205
             {"Old User Activation","APOC","","","1","false","false","N"},//206
             {"View Paid Bills","APPB;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","N"},//207
             {"Request for Demand Draft","APG8;Y",mPINString,"First Name","Middle Name","Last Name","","","","","","","","Amount (Rs.)","Payable At","Remarks","4-4-N-Y-Y","1-15-ANW-N-N","1-15-ANW-N-N","0-50-AN-N-N","","","","","","","","1-10-ND-N-N","1-20-AN-N-N","0-25-AN-N-N","14","true","true","N"},//208
             {"Fixed Deposit", "APG9;Y", "", "", "", "","Amount (Rs.)", "", "", "", "", "", "",	"5-5-N-N-N", "", "", "7", "false", "false","N" },// 209
 			 {"Cancel MMID","APRC;Y",mPINString,"","4-4-N-Y-Y","","2","false","false","N"},//210			
             {"Request for a New Product","APGP;Y",mPINString,"001","4-4-N-Y-Y","","2","false","false","N"},//211
             {"Beneficiary Details","APD9;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","N"},//212
             {"Beneficiary Details","APNL;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true","N"},//213
             {"Beneficiary Details","APD8;Y",mPINString,"","","","2","false","false","N"},//214
             {"VISA Card Beneficiary Details","APV6;Y",mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","true","true"},//215
             {"Reset Password","APPE","Login Password","New Password","Confirm New Password","4-4-N-N-Y","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"},//216
             { "Cheque Pickup", "APG3;Y", mPINString, "Number of Instruments","4-4-N-Y-Y", "1-1-N-N-N", "2", "false", "false","N" },// 217//put	specially for android cheque	pickup
			{ "Fixed Deposit", "APG9;Y", mPINString, "4-4-N-Y-Y", "1","false", "false","N" },// 218//put specially for fixed deposit
			{ "Fixed Deposit", "APG9;Y", "Number Of Days", "1-3-N-N-N", "1","false", "false","N" },// 219//put specially for fixed deposit
					{},//220 dead index
					{},// 221for seperate mPIN screen..
					{}, //222
					};

	public static int retryCount;
	public static int selectedIndex;
	public static int gridselectedpos = 0;
	public static TextView loadingMsg;
	public static String loadMsg = "Your request is under process...";
	public static String sentMsg = "Your request has been sent, please wait for the response";
	public static boolean isInbox;
	public static boolean canShowAlert;
    public static String forDeReg         = "";
	protected static boolean muTerminate;
	public static boolean restartAlert;
	public static Button[] btn;
	public static boolean loanFlag;
	public static boolean eBankServiceFlag;
	public static boolean eBankPinFlag;
	public static boolean smsBankingFlag;
	public static boolean insurancePremiumFlag;
	public static boolean ASBAFlag;
	public static boolean installAddRow=false;
	public static String INSERT = "insert into " + RmsStore.parsedRecords + "(id,table_row_one) values (?,?);";
	public static String INSERT_IMG = "insert into " + RmsStore.parsedRecords+"img" + "(id,table_row_one) values (?,?);";
	public static String tagType = "";
	public static boolean accpaymentFlag;
	public static boolean IMPS2M_REG_Flag;
	public static String acctypeAPK3;
	public static boolean selecedAdapterFlag;
	public static boolean merchentIMPSFlag;
	public static boolean onMerchantFlag;
	public static boolean isFinancialAccSync;
	public static boolean isAccSyncFromMySetup;
	public static String accOwner = "Guest";
	public static String rechargeSelcetedCategoryID  = null;
	public static String rechargeOpID_R_NickName  = null;
	public static boolean isCategoryRefresh;
	
	public static LinearLayout Tabbar(OnClickListener tabBarListener,Map< Integer, Image> tabSetter,Context context,Boolean width_Fullscreen) throws Exception	{
		if(width_Fullscreen){
			tabwidth = width;
		}else{
			tabwidth = (StaticStore.istablet ? ((width/3)*2) : width);
		}
		Map< Integer, Image> tempMap = tabSetter;
		LinearLayout last =new LinearLayout(context);
		last.setOrientation(LinearLayout.VERTICAL);
		last.setBackgroundColor(Color.TRANSPARENT);
		LinearLayout scrollLayout =new LinearLayout(context);
		scrollLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		scrollLayout.setBackgroundResource(R.drawable.bg);
		scrollLayout.setGravity(Gravity.CENTER);
		HorizontalScrollView hv = new HorizontalScrollView(context);
		hv.setVisibility(View.VISIBLE);
		LinearLayout l2 =new LinearLayout(context);
		l2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		int tempCount = 0;
		btn = new Button[tempMap.size()];
		ImageView[] divider =new ImageView[tempMap.size()];
		Image image;
		Iterator it = tempMap.entrySet().iterator();
		int maxWidth = 0;
		int tempWidth = 0;
		Paint paint = new Paint();
		Map.Entry pairs;
		while (it.hasNext()) {
			pairs = (Map.Entry) it.next();
			image = (Image) pairs.getValue();
			btn[tempCount] = new Button(context);
			btn[tempCount].setId((Integer) pairs.getKey());
			btn[tempCount].setText(image.getImage_Name());
			btn[tempCount].setTextColor(Color.WHITE);
			btn[tempCount].setCompoundDrawablesWithIntrinsicBounds(0,(Integer) image.getImage_id(), 0, 0);
			btn[tempCount].setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM);
			if (StaticStore.istablet) {
				btn[tempCount].setPadding(1, 15, 0, 15);
				btn[tempCount].setTextSize(18);
				paint.setTextSize(20);
			} else {
				btn[tempCount].setTextSize(12);
				paint.setTextSize(13);
				btn[tempCount].setPadding(0, 2, 0, 5);
			}
			btn[tempCount].setOnClickListener(tabBarListener);
			btn[tempCount].setBackgroundResource(R.drawable.btnselect);
			tempWidth = (int) paint.measureText(image.getImage_Name()) + 1;
			if (tempWidth > maxWidth) {
				maxWidth = tempWidth;
			}
			tempCount++;
		}
		for(int i = 0; i < btn.length ; i++)
		{
			if((tempMap.size()) == 1) {
				maxWidth	=	tabwidth;
			}
			if(StaticStore.is_SmallMobile){
				maxWidth	= (tabwidth-6) / tempMap.size();
			}else{
				if((maxWidth * tempMap.size()) < tabwidth) {
					maxWidth	=		tabwidth / tempMap.size();
				}
			}
			btn[i].setWidth(maxWidth);
			divider[i] = new ImageView(context);
			divider[i].setBackgroundResource(R.drawable.tab_divider);
			l2.addView(btn[i]);
			if(i!= btn.length-1){
				l2.addView(divider[i]);
			}
		}
		hv.addView(l2);
		scrollLayout.addView(hv);
		last.addView(scrollLayout);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);  
		params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		last.setLayoutParams(params1); 
		last.setId(333);
		StaticStore.LogPrinter('i',"Enter into Buuton count "+btn.length);
	    return last;
	 }
	
	
	public static AlertDialog alertDialog;
	public static boolean FromListScreen;
	public static boolean rechargeStatus;
	public static int Batterylevel = 0;
	public static boolean BatteryAlertFlag = true;
	public static String accTypeCompleteData;
	public static int indexBeforeAccTypeInitiation;
	public static boolean isForgotPassword = false;
	public static boolean isLogout = false;
	public static byte[] LogoutImageStream = null;
	public static byte[] LoginImageStream = null;
	public static boolean istablet;
	public static String versionUpdateURL = "";
	protected static int Header_Height = 0;
	protected static int Footer_Height = 0;
	protected static int Titlebar_Height = 0;
	public static int HF_Height = 0;
	public static boolean TF00NEFTAlert;
	public static boolean NoteForAccSel;
	protected static boolean NoteForgotPWD;
	public static String[] mPinArray;
	public static int mPinBackupindex;
	public static String OTPRefID="";
	public static FragmentManager fragmentManager;
    public static boolean Leftsidepane = false;
	public static long totalTimeOutTime = 300000;
	public static long currentTimeFromIdle = System.currentTimeMillis();
	public static String fragclassname;
	protected static boolean gridupdateneeded;
	public static boolean FromGridActivation = false;
	public static int backlistIndex = 0;
	public static String PackageName;
	public static boolean IS_Login_download_flag;
	public static String IS_Login_img_URL="";
	public static boolean is_SmallMobile;
	public static boolean gridViewConfigcalled;
	public static RelativeLayout gridNofragHeader; // ABINAYA.J.A
	public static LinearLayout gridNolineHeader; // ABINAYA.J.A
	public static boolean isDashBoard =  false;
	public static boolean isDashBoardProfileImageRemoved =  false;
	public static boolean isMenuFromDashBoard =  false;
	public static boolean isDashBoardLeftSideView =  false;
	
	public static void LogPrinter(char c,String msg){
		String Title = "TMB Phase II" ;
	//	System.out.println(msg);
		if(StaticStore.Logwrite){
			if(c == 'v')
				Log.v(Title,msg);
			else if(c == 'd')
				Log.d(Title,msg);
			else if(c == 'i')
				Log.i(Title,msg);
			else if(c == 'w')
				Log.w(Title,msg);
			else if(c == 'e')
				Log.e(Title,msg);
		}
	}

	public static void ToastDisplay(Context context, String message) {
		// TODO Auto-generated method stub
		Boolean sessionflag = StaticStore.midlet.getsessionTimeOut(context);
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.setTitle("Alert Message !!!");
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            		dialog.cancel();
            }
        });
        alertDialog.show();
        StaticStore.LogPrinter('i',"Alert Displayed --->");
	}
	

	
	public static Boolean comprobarSDCard(Context mContext) {
	    String auxSDCardStatus = Environment.getExternalStorageState();

	    if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
	        return true;
	    else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
	        Toast.makeText(
	                mContext,
	                "Warning, the SDCard it's only in read mode.\nthis does not result in malfunction"
	                        + " of the read aplication", Toast.LENGTH_LONG)
	                .show();
	        return true;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS)) {
	        Toast.makeText(
	                mContext,
	                "Error, the SDCard can be used, it has not a corret format or "
	                        + "is not formated.", Toast.LENGTH_LONG)
	                .show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED)) {
	        Toast.makeText(
	                mContext,
	                "Error, the SDCard is not found, to use the reader you need "
	                        + "insert a SDCard on the device.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED)) {
	        Toast.makeText(
	                mContext,
	                "Error, the SDCard is not mounted beacuse is using "
	                        + "connected by USB. Plug out and try again.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE)) {
	        Toast.makeText(
	                mContext,
	                "Error, the SDCard cant be mounted.\nThe may be happend when the SDCard is corrupted "
	                        + "or crashed.", Toast.LENGTH_LONG).show();
	        return false;
	    } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED)) {
	        Toast.makeText(
	                mContext,
	                "Error, the SDCArd is on the device but is not mounted."
	                        + "Mount it before use the app.",
	                Toast.LENGTH_LONG).show();
	        return false;
	    }

	    return true;
	}
}
