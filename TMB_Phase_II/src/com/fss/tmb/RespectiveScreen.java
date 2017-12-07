package com.fss.tmb;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 *
 * @author Siva G
 */
public class RespectiveScreen extends Activity{
	private String serviceControl;
	private Intent intent;
	private String[] message;
	String tempSeparateAccNOfromDesc = "";

	private DisplayableView dspView;
	/** Creates a new instance of RespectiveScreen */
	
	public Intent BE_MS_PC_FT_FA_AI_R0_AB_CN_PA_P3_P4_BV_1F_2F_CG_AG_LR_OG_BO_OV(Context context , String messageContent) throws Exception{
		if(messageContent.startsWith("BE")) {
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("BE00")) {
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.LogPrinter('i',"BE00 >>>>"+messageContent );
				intent.putExtra("response",messageContent);
				intent.putExtra("formName",StaticStore.BE);
			} else {
				intent.putExtra("response", messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Balance Enquiry" );
			}
			return intent;
		}else if(messageContent.startsWith("MS")) {
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("MS00")) {
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName",StaticStore.MS);
				intent.putExtra("title","Mini Statement" );
			} else {
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Mini Statement" );
			}
			return intent;
		}else if(messageContent.startsWith("PC")) {
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("PC")){
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Change mPIN" );//Change mPIN
			}
			return intent;
		}else if(messageContent.startsWith("FT")) {
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
			return intent;
		}else if(messageContent.startsWith("FA")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
			return intent;
		}else if(messageContent.startsWith("AI")){
			if(messageContent.startsWith("AI00")) {
				intent = new Intent();
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.airlineBooking = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.airlineBooking);
				intent = StaticStore.midlet.get_AirlineLists(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Payment Only");
			}
			return intent;
		}else if(messageContent.startsWith("R0")){
			if(messageContent.startsWith("R000")) {
				intent = new Intent();
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.airlineBooking = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.airlineBooking);
				intent = StaticStore.midlet.get_AirlineLists(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Payment Only");
			}
			return intent;
		}else if(messageContent.startsWith("AB")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("AB00")){
				StaticStore.midlet.txnID = messageContent.substring( messageContent.lastIndexOf(':') + 1);
				for(int i = 0; i < 2;i++){
					messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				}
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "AB00");
				intent.putExtra("title","Payment Only");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Payment Only");
			}
			return intent;
		}else if(messageContent.startsWith("CN")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("CN00")){  
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "CN00");
				intent.putExtra("title","Shopping");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Shopping");
			}
			return intent;
		}else if(messageContent.startsWith("PA")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("PA00")){
				messageContent = messageContent.substring(0,messageContent.indexOf("TXNID:")) + messageContent.substring(messageContent.indexOf("TXNID:")+6);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "Airline");
				intent.putExtra("title","Payment Only");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Payment Only");
			}
			return intent;
		}else if(messageContent.startsWith("P3")){
			if(messageContent.startsWith("P300")){
				messageContent = messageContent.substring(messageContent.indexOf(';') + 1);
				messageContent = messageContent.substring(messageContent.indexOf(';') + 1);
				String isMPINChangeNeeded = messageContent.substring(0, messageContent.indexOf(';'));
				StaticStore.LogPrinter('i',">>>>>>respskdshhdsjhfj"+ isMPINChangeNeeded);
				StaticStore.FromListScreen = false;
				if(isMPINChangeNeeded.equals("Y")){
					StaticStore.tagType = "APP4";
					StaticStore.menuDesc[220] = new String []{"Change mPIN","APP4;Y",StaticStore.mPINString+" ","New mPIN","Confirm New mPIN","4-4-N-Y-Y","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"};
					StaticStore.index =  220;                		  
					intent = new Intent(context,DynamicCanvas.class);
				}else{
					StaticStore.menuDesc[114][0] = "Create Password";
					StaticStore.menuDesc[114][2] =  "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
					StaticStore.index =  114;
					intent = new Intent(context,DynamicCanvas.class);
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Forgot Password");
			}
			return intent;
		}else if(messageContent.startsWith("P4")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("P400")){ 
				StaticStore.menuDesc[114][0] = "Create Password";
				StaticStore.menuDesc[114][2] =  "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
				StaticStore.index =  114;
				intent = new Intent(context,DynamicCanvas.class);                               
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Forgot Password");
			}
			return intent;
		}else if(messageContent.startsWith("BV")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("BV00")){  
				String temp = messageContent.substring(5);
				String currentVersion = temp.substring(0, temp.indexOf(';'));
				temp = temp.substring(temp.indexOf(';') + 1);
				String newVersion = temp.substring(0, temp.indexOf(';'));
				
				temp = temp.substring(temp.indexOf(';') + 1);
				StaticStore.versionUpdateURL = temp.substring(0, temp.indexOf(';'));
				temp = currentVersion + ";" + newVersion;
				 
				StaticStore.LogPrinter('i',">>>>>temp>>>>>>"+temp);    				
				intent.putExtra("response",temp);
				intent.putExtra("formName", "Version Update");   
				intent.putExtra("title","Version Update");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Version Update");
			}

			return intent;
		}else if(messageContent.startsWith("1F")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("1F00")){ 
				StaticStore.FromListScreen = false;
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.feedbackList = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.feedbackList);
				StaticStore.menuDesc[220] = new String [(2 * StaticStore.midlet.feedbackList.length) + 8];
				StaticStore.menuDesc[220][0] = "Feedback";
				StaticStore.menuDesc[220][1] = "AP2F;N";
				for(int i = 0; i < StaticStore.midlet.feedbackList.length;i++){
					StaticStore.menuDesc[220][i + 2] =  StaticStore.midlet.feedbackList[i][1];
				}
				StaticStore.menuDesc[220][StaticStore.midlet.feedbackList.length + 2] =  "Feedback";
				for(int i = 0; i < StaticStore.midlet.feedbackList.length;i++){
					StaticStore.menuDesc[220][i + 3 + StaticStore.midlet.feedbackList.length] = "1-1-N-N-N" ;
				}
				StaticStore.menuDesc[220][(2 * StaticStore.midlet.feedbackList.length) + 3] = "1-80-AN-N-N";
				StaticStore.menuDesc[220][(2 * StaticStore.midlet.feedbackList.length) + 4] = StaticStore.midlet.feedbackList.length + 1 + "";
				StaticStore.menuDesc[220][(2 * StaticStore.midlet.feedbackList.length) + 5] = "false";
				StaticStore.menuDesc[220][(2 * StaticStore.midlet.feedbackList.length) + 6] = "false";          		
				StaticStore.menuDesc[220][(2 * StaticStore.midlet.feedbackList.length) + 7] = "N";
				StaticStore.tagType = "AP2F";
				StaticStore.index =  220;                		  
				intent = new Intent(context,DynamicCanvas.class);
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Feedback");
			}
			return intent;
		}else if(messageContent.startsWith("2F")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Feedback");
			return intent;
		}else if(messageContent.startsWith("CG")){
			if(messageContent.startsWith("CG00")){ 
				StaticStore.lastLoginTime = DateFormat.getDateTimeInstance().format(new Date());
				StaticStore.isFromLoginScreen = true;
				StaticStore.isSuccessGC = true;

				StaticStore.LogPrinter('i',">>>>messageContent>>>>>>>"+messageContent);
				messageContent = messageContent.substring(5);
				StaticStore.isPreLoginNotification = messageContent.substring(0,messageContent.indexOf(';')).equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.preLoginShortDescList = (messageContent.substring(0,messageContent.indexOf(';'))).split("#"); 
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.preLoginLongDescList = (messageContent.substring(0,messageContent.indexOf(';'))).split("#");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isPostLoginNotification = messageContent.substring(0,messageContent.indexOf(';')).equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.postLoginShortDescList = (messageContent.substring(0,messageContent.indexOf(';'))).split("#");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.postLoginLongDescList = (messageContent.substring(0,messageContent.indexOf(';'))).split("#");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isMpinAtLast = !(messageContent.substring(0,messageContent.indexOf(';')).equals("Y"));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue1 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue2 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue3 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.SMS_AUTHENTICATION_SMSMODE = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.SMS_AUTHENTICATION_GPRSMODE = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isAccTypeRefresh = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.chequeLength = messageContent.substring(0,messageContent.indexOf(';'));
				StaticStore.chequeMin = StaticStore.chequeLength.substring(0,2);
	            StaticStore.chequeMax = StaticStore.chequeLength.substring(2,4);
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.ImageStreamingURL = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.IS_Login_download_flag = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.IS_Login_img_URL = messageContent.substring(0,messageContent.indexOf(';'));
				
						
				StaticStore.mPinHexaValue = StaticStore.midlet.hexToBinary(mPinHexaValue1)+StaticStore.midlet.hexToBinary(mPinHexaValue2)+StaticStore.midlet.hexToBinary(mPinHexaValue3);
					StaticStore.mpinNeededTransactions = new mPAY().getMPinEnabledtransaction(StaticStore.mPinHexaValue);
				StaticStore.midlet.writeinMemory(context);
				StaticStore.midlet.writeAccountInMemory();

				if(StaticStore.isFromVersionUpdate && StaticStore.selectedLoginGridIndex == 99){
					StaticStore.isFromVersionUpdate = false;
					StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
					StaticStore.index =  220;                		  
					intent = new Intent(context,DynamicCanvas.class);
				}else if(StaticStore.selectedLoginGridIndex == 1){
					
					if(!StaticStore.IsPermitted) //Siva fix
					{
						StaticStore.menuDesc[114][2] =  "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
						StaticStore.index = 114;
						StaticStore.FromListScreen = false;
						intent = new Intent(context,
								DynamicCanvas.class);
					}else{  
				
						intent = StaticStore.midlet.Loginvalidation();
					}
				}else if(StaticStore.selectedLoginGridIndex == 0){            			
					if(StaticStore.IsPermitted ){
						intent = StaticStore.midlet.getAbout(context);
					}else{
						intent = StaticStore.midlet.getAbout(context); //NEW
					}
				}else if(StaticStore.selectedLoginGridIndex == 2){
					intent = new Intent();
					intent = StaticStore.midlet.get_ProductsMenu(context);   
				}else if(StaticStore.selectedLoginGridIndex == 3){
					StaticStore.index = 129;
					intent = new Intent(context,DynamicCanvas.class);       			
				}else if(StaticStore.selectedLoginGridIndex == 4){
					intent = new Intent();
					intent = StaticStore.midlet.getLocatorMenu(context); 
				}else if(StaticStore.selectedLoginGridIndex == 5){  
					StaticStore.menuDesc[220] = new String []{"Feedback","AP1F;N","001","","1","false","false","N"};
					StaticStore.tagType = "AP1F";
					StaticStore.index = 220;
					intent = new Intent(context,DynamicCanvas.class);
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
						intent = new Intent(context,DynamicCanvas.class);	
					}else{
						StaticStore.FromListScreen = true;
						StaticStore.NoteForgotPWD = true;
						StaticStore.index = 115;
						intent = new Intent(context,DynamicCanvas.class);
					}

				}
			}else{
				StaticStore.isSuccessGC = false;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				if(StaticStore.selectedLoginGridIndex == 0){
					if(StaticStore.IsPermitted ){
						intent.putExtra("title","About Us");
					}else{
						intent.putExtra("title","About Us");
					}
				}else if(StaticStore.selectedLoginGridIndex == 1){
					intent.putExtra("title","Login");
				}else if(StaticStore.selectedLoginGridIndex == 2){
					intent.putExtra("title","Products");
				}else if(StaticStore.selectedLoginGridIndex == 3){
					intent.putExtra("title","Refer");
				}else if(StaticStore.selectedLoginGridIndex == 4){
					intent.putExtra("title","Locator");
				}else if(StaticStore.selectedLoginGridIndex == 5){
					intent.putExtra("title","Feedback");
				}else if(StaticStore.selectedLoginGridIndex == 6){
					intent.putExtra("title","Forgot Password");
				}	
				
			}
			return intent;
		}else if(messageContent.startsWith("AG")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","New Account Opening");
			return intent;
		}else if(messageContent.startsWith("LR")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("LR00")){ 
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.loanList = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.loanList);
				String forDisplayable = "";
				for(int i = 0; i < StaticStore.midlet.loanList.length;i++){
					forDisplayable = forDisplayable + StaticStore.midlet.loanList[i][1] +";";

				}
				intent.putExtra("response",forDisplayable);
				intent.putExtra("formName", "LR00");
				intent.putExtra("title","Loan Calculator");   		    
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Loan Calculator");
			}
			return intent;
		}else if(messageContent.startsWith("OG")){
			if(messageContent.startsWith("OG00")){
				StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
	    		messageContent = messageContent.substring(5);
	    		if(messageContent.indexOf("TXNID:") != -1){
					StaticStore.OTPRefID = "(Ref. ID-"+messageContent.substring(messageContent.indexOf("TXNID:") + 6)+")";
				}
	    		StaticStore.LogPrinter('i', "OG00 RefID -->"+StaticStore.OTPRefID);
				StaticStore.FromListScreen = false;
				StaticStore.tagType = "APOV";
				StaticStore.menuDesc[220] = new String []{"","APOV","OTP"+StaticStore.OTPRefID,"6-6-N-Y-N","1","true","true","N"};
				if(StaticStore.IsPermitted)
					StaticStore.menuDesc[220][0] = "OTP for Login";
				else
					StaticStore.menuDesc[220][0] = "OTP for Activation";
				StaticStore.index = 220;
				intent =  new Intent(StaticStore.context,DynamicCanvas.class);
			
	    	}else{
	    		intent = new Intent(context , DisplayableView.class);
	        	intent.putExtra("response",messageContent.substring(5));
	         	intent.putExtra("formName", "DEFAULT");
	         	intent.putExtra("title","OTP Verification");
	    	}
	    	return intent;
		}else if(messageContent.startsWith("BO")){
			if(messageContent.startsWith("BO00")){
				StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
                messageContent = messageContent.substring(5);
	    		if(messageContent.indexOf("TXNID:") != -1){
					StaticStore.OTPRefID = "(Ref. ID-"+messageContent.substring(messageContent.indexOf("TXNID:") + 6)+")";
				}
	    		StaticStore.LogPrinter('i', "OG00 RefID -->"+StaticStore.OTPRefID);
                StaticStore.FromListScreen = false;
				StaticStore.tagType = "APOV";
				StaticStore.menuDesc[220] = new String []{"","APOV","OTP"+StaticStore.OTPRefID,"6-6-N-Y-N","1","true","true","N"};
				if(StaticStore.IsPermitted)
					StaticStore.menuDesc[220][0] = "OTP for Login";
				else
					StaticStore.menuDesc[220][0] = "OTP for Activation";
				StaticStore.index = 220;
				intent =  new Intent(StaticStore.context,DynamicCanvas.class);
			
	    	}else{
	    		intent = new Intent(context , DisplayableView.class);
	        	intent.putExtra("response",messageContent.substring(5));
	         	intent.putExtra("formName", "DEFAULT");
	         	intent.putExtra("title","OTP Verification");
	    	}
	    	return intent;
		}else if(messageContent.startsWith("OV")){
	//	if(messageContent.startsWith("OV00")){ // OTP BYPASS
		 if(messageContent.startsWith("OV")){ // OTP BYPASS
				StaticStore.isOTPVerified = true;
				if(StaticStore.myMobileNo.equals("0")){
					StaticStore.myMobileNo = StaticStore.tempMobileNo;
					StaticStore.midlet.writeinMemory(context);
				}
				if(StaticStore.isForgotPassword){
					StaticStore.FromListScreen = true;
					StaticStore.NoteForgotPWD = true;
					StaticStore.index = 115;
					intent = new Intent(StaticStore.context, DynamicCanvas.class);
				}else{
					intent = StaticStore.midlet.initiateUserOption(context);
				}
			}else{ // OTP BYPASS
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","OTP Verification");
			}
			return intent;
		}else{
			return null;
		}
	}


	public Intent MP_B1_B2_B9_B3_Y3_B4_B5_1B_B6_B8_BZ_DW_DX_DY_2B_DZ_DT_DM_DN(Context context ,String messageContent)throws Exception{
		if(messageContent.startsWith("MP")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Shopping");
			return intent;
		}else if(messageContent.startsWith("B1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Billpay Registration");
			return intent;
		}else if(messageContent.startsWith("B2")){
			if(messageContent.startsWith("B200")){
				if(StaticStore.forAddBiller){
					StaticStore.forAddBiller = false;
					StaticStore.forBillerRegistration = true;
				}
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5).trim();
				String temp = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.isAdhocPayment = temp.startsWith("A");                  
				temp   = temp.substring(temp.indexOf(";") + 1);
				messageContent = messageContent.substring(0,messageContent.indexOf(";")) + ";" + temp;   
				StaticStore.LogPrinter('i',"messageContent B200 --> "+messageContent);
				StaticStore.midlet.billerCategoryList = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.billerCategoryList);
				intent = new Intent();
				intent = StaticStore.midlet.getCategoryList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Biller Category Search");
			}return intent;
		}else if(messageContent.startsWith("B9")){
			if(messageContent.startsWith("B900")){
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5).trim();
				String temp = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.billerCat    = temp.substring(0,temp.indexOf(";"));
				temp   = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.billerState = temp.substring(0,temp.indexOf(";"));
				temp   = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.billerCity = temp.substring(0,temp.indexOf(";"));
				temp       = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.isAdhocPayment = temp.startsWith("A");                                                      
				temp   = temp.substring(temp.indexOf(";") + 1);
				messageContent = messageContent.substring(0,messageContent.indexOf(";")) + ";" + temp;                        
				StaticStore.midlet.unRegBillers = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.unRegBillers);
				intent = new Intent();
				intent = StaticStore.midlet.getUnRegBillers(context);

			}else{
				StaticStore.forBillerRegistration = false;
				StaticStore.midlet.isAdhocPayment = messageContent.contains(";A;");   
				intent = new Intent(context , DisplayableView.class);
				if(StaticStore.midlet.isAdhocPayment)
				{   
					intent.putExtra("title","Instant Bill Payment");

				} else {                       
					intent.putExtra("title","Add Biller");
				}
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
			}
			return intent;

		}else if(messageContent.startsWith("B3")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("B300")){
				StaticStore.midlet.inResponseInbox =  false;
				String temp = messageContent.substring(5).trim().replaceAll("-AN-", "-ANW-");
				temp = temp.substring(temp.indexOf(";") + 1).trim();
				StaticStore.midlet.isAdhocPayment = temp.startsWith("A");
				
				StaticStore.midlet.BillerB300Msg = messageContent;
				seperateBillers(messageContent);
				
				messageContent = messageContent.substring(5).trim().replaceAll("-AN-", "-ANW-");
				StaticStore.LogPrinter('i',"Siva B300 >>>"+messageContent);
				StaticStore.midlet.BillerB300 = messageContent.split("\\*");
				StaticStore.LogPrinter('i',"StaticStore.midlet.BillerB300 ==> "+Arrays.deepToString(StaticStore.midlet.BillerB300));
				if(StaticStore.midlet.isAdhocPayment){
					intent.putExtra("response",StaticStore.midlet.BillerB300[0]+";"+StaticStore.midlet.BillerB300[1]);
					intent.putExtra("formName", "Adhocpay");
				}else{
					intent.putExtra("response",StaticStore.midlet.BillerB300[0]+";"+StaticStore.midlet.BillerB300[1]);
					intent.putExtra("formName", "Billerlink");
				}
			}else{
				StaticStore.forBillerRegistration = false;
				StaticStore.midlet.isAdhocPayment = messageContent.contains(";A;");   
				if(StaticStore.midlet.isAdhocPayment){
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Instant Bill Payment");
				} else{
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Add Biller");
				}
			}
			return intent;
		}else if(messageContent.startsWith("Y3")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("Y300")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.txnID = messageContent.substring(messageContent.indexOf("TXNID")+ 6);
				StaticStore.LogPrinter('i',">>>>>>>>>txnId" + StaticStore.midlet.txnID);
				String temp = messageContent.substring(5);
				temp = temp.substring(temp.indexOf(";") + 1).trim();
				temp = temp.substring(temp.indexOf(";") + 1).trim();
				temp = temp.substring(temp.indexOf(";") + 1).trim();
				StaticStore.LogPrinter('i',"isAdhocPayment 343434 <>"+temp+"<><<>" + StaticStore.midlet.isAdhocPayment);
				StaticStore.midlet.isAdhocPayment = temp.startsWith("A");

				seperateBillers(messageContent);                    
				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				messageContent = messageContent.substring(5).trim();
				temp           = messageContent;
				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				messageContent = temp.substring(0, temp.indexOf("*"));
				temp           = temp.substring(temp.indexOf("*") + 1);
				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				messageContent += ";" + temp.substring(0, temp.indexOf("*"));
				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				temp = messageContent;
				StaticStore.LogPrinter('i',"messageContent temp>>>>>>"+temp);
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				temp = temp.substring(0,temp.lastIndexOf(";"));
				temp = temp.substring(0,temp.lastIndexOf(";"));

				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				StaticStore.LogPrinter('i',"messageContent temp>>>>>>"+temp);
				messageContent = messageContent +";"+temp;
				StaticStore.LogPrinter('i',"messageContent>>>>>>"+messageContent);
				StaticStore.LogPrinter('i',"isAdhocPayment 343434 <><><<>" + StaticStore.midlet.isAdhocPayment);
				if(StaticStore.midlet.isAdhocPayment){
					StaticStore.LogPrinter('i',"isAdhocPayment<><><<>" + StaticStore.midlet.isAdhocPayment);
					intent.putExtra("response",messageContent);
					intent.putExtra("formName", "Adhocpay");
				}
			}else{

				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("B4")){
			StaticStore.forBillerRegistration = false;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Biller");
			return intent;
		}else if(messageContent.startsWith("B5")){
			//B500;<CUSTOMER-MNEMONIC NAME>|<PRESENTMENT-FLAG>|<AMOUNT>|<MIN PAYMENT FLAG>|<PART PAYMENT FLAG>|<EXCESS PAYMENT FLAG>|<MIN AMOUNT>|<PENALTY AMOUNT>|<BILL NO>|<BILL DATE>|<DUEDATE>|<FIXED CHARGES>|<VARIABLE CHARGES>;<START RECORD>;<MORE FLAG>;TXNID:124343535634
			if(messageContent.startsWith("B500")){
				//B500;AADGADGJAD*Y*611.00*N*N*N*0.00*0.00*101065355May2010*20090520*20090607*0.00*0.00;0001;N;TXNID:110308049928;BID:C504594
				StaticStore.midlet.billpayBills = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'#',true,StaticStore.midlet.billpayBills);                      
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent =  StaticStore.midlet.getBillpayBills(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("1B")){
			//B500;<CUSTOMER-MNEMONIC NAME>|<PRESENTMENT-FLAG>|<AMOUNT>|<MIN PAYMENT FLAG>|<PART PAYMENT FLAG>|<EXCESS PAYMENT FLAG>|<MIN AMOUNT>|<PENALTY AMOUNT>|<BILL NO>|<BILL DATE>|<DUEDATE>|<FIXED CHARGES>|<VARIABLE CHARGES>;<START RECORD>;<MORE FLAG>;TXNID:124343535634
			if(messageContent.startsWith("1B00")){
				//B500;AADGADGJAD*Y*611.00*N*N*N*0.00*0.00*101065355May2010*20090520*20090607*0.00*0.00;0001;N;TXNID:110308049928;BID:C504594
				//                	1B00;AADGADGJAD*Y*611.00*N*N*N*0.00*0.00*10106535*5May2010*20090520*20090607*0.00*0.00*0.0;Head 1 * Val 1*Val 11*Val 12*val 13#Head 2 * Val 2#Head 3 * Val 3#Head 4 * Val 4#Head 5 * Val 5#Head 6 * Val 6;0001;N;TXNID:110308049928;BID:C504594                    

				//               error resp 	1B00;eerrrrrrr*Y*1700.00*N*N*N*0.00*0.00*34344577*20120209*20121005*0.00*0.00#;0001;N;TXNID:326711404215

				String temp1b[] = messageContent .split(";");
				StaticStore.LogPrinter('i',"temp1b[] -->"+Arrays.deepToString(temp1b));
				temp1b[1] = temp1b[1].replaceAll("\\|", "\\*");
				messageContent = "";
				for(int i = 0;i< temp1b.length;i++)
				{
					if(i!=2)
					{
						messageContent = messageContent+temp1b[i]+";";	
					}

				}
				messageContent = messageContent.substring(0,messageContent.lastIndexOf(";"));
				StaticStore.LogPrinter('i',"Display Value  --> "+temp1b[2]);
				StaticStore.LogPrinter('i',"Message Context --> "+messageContent);

				StaticStore.midlet.billpayBills = StaticStore.midlet.getSplittedValues(messageContent.substring(5),12,'#',true,StaticStore.midlet.billpayBills);
				StaticStore.LogPrinter('i',"billpayPres >>>> "+Arrays.deepToString(StaticStore.midlet.billpayBills));
				StaticStore.midlet.inResponseInbox =  false;
				String format = Display_Dynamic_Spliter(temp1b[2]);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",format);
				intent.putExtra("formName", "AP1B");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Biller");
			}
			return intent;
		}else if(messageContent.startsWith("B6")){
			StaticStore.indexCtr=3;
			StaticStore.midlet.billpayBills = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Biller");
			return intent;
		}else if(messageContent.startsWith("B8")){
			StaticStore.midlet.billpayBills = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Bill Payment");
			return intent;
		}else if(messageContent.startsWith("BZ")){
			if(messageContent.startsWith("BZ00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.regBillers = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'#',true,StaticStore.midlet.regBillers);
				
				StaticStore.LogPrinter('i',"StaticStore.midlet.regBillers --> "+Arrays.deepToString(StaticStore.midlet.regBillers));
				intent = new Intent();
				intent = getRegBillersList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Biller");
			}

			return intent;
		}else if(messageContent.startsWith("DW")){
			//        	DW00;OBC128|MTNL-DL|Y|Y~OBC065|MTNL-Lline-Mum|N|N;TELEPHONE;;;0001;Y;TXNID:110308049912;BANKID:C403362
			//        	   DW00;LICTESTAD*India Life Insurance Adhoc*N*N#LICTESTADP*India Life Insurance Adhoc Pres*Y*N#000000000457*METLIFE Ins*N*N;INSURANCE;           ;         ;0001;Y;TXNID:326712566370
			if(messageContent.startsWith("DW00")){

				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5).trim();
				StaticStore.LogPrinter('i',"Mess >> > >> "+messageContent); 
				intent = new Intent();
				StaticStore.midlet.tempDW = messageContent.split(";",-1);
				StaticStore.LogPrinter('i',"tempDW >>>>>>> "+Arrays.deepToString(StaticStore.midlet.tempDW));

				StaticStore.midlet.un_Reg_Billers = StaticStore.midlet.getSplittedValuesArray(StaticStore.midlet.tempDW[0], 4,"#", StaticStore.midlet.un_Reg_Billers);
				StaticStore.midlet.moreFlag = StaticStore.midlet.tempDW[5].trim().toUpperCase().equals("Y");
				StaticStore.midlet.nextStartRecNumber = String.valueOf((Integer.parseInt(StaticStore.midlet.tempDW[4].trim())+StaticStore.midlet.un_Reg_Billers.length));
				StaticStore.LogPrinter('i',"un_Reg_Billers"+Arrays.deepToString(StaticStore.midlet.un_Reg_Billers));
				StaticStore.LogPrinter('i',"un_Reg_Billers"+StaticStore.midlet.un_Reg_Billers.length);

				intent = StaticStore.midlet.get_Un_Reg_BillerList(context);



			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Biller List");
			}
			return intent;
		}else if(messageContent.startsWith("DX")){
			/* More flag */
			//        		StaticStore.midlet.moreFlag = false;
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("DX00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.txnID = messageContent.substring(messageContent.indexOf("TXNID")+ 6);
				StaticStore.LogPrinter('i',">>>>>>>>>txnId" + StaticStore.midlet.txnID);
				messageContent = messageContent.substring(5);
				//        			DX00;OBC128|Mahanagar Telephone Nigam Delhi|Y; 1~123|Name ; 1|CA No*8-30-AN-N-N; 100 | 1;001;Y; TXNID:110308049915;BANKID:C403362
				//        			DX00;LICTESTADP|India Life Insurance Adhoc Pres|Y;0002~LIC01|CHENNAI NORTH~LIC02|CHENNAI SOUTH;0001|Consumer No*1-19-N-N-N;0.0|0.0;0001;N;TXNID:326814117639;BID:C444444
				//					DX00;LICTESTADP*India Life Insurance Adhoc Pres*Y;0002#LIC01*CHENNAI NORTH#LIC02*CHENNAI SOUTH;0001*Consumer No*1-19-N-N-N;0.0*0.0;0001;N;TXNID:326814045091;BID:C444444

				//                 DX00;
				//                 tempDX[0]   OBC128|Mahanagar Telephone Nigam Delhi|Y  Biller Details :- Biller Id|Biller Name|Presentment Flag
				//                 tempDX[1]   3~123|Name~123|Name~123|Name;No Of Sub Category ~ Sub Categ Id | Sub Categ Name ~ Sub Categ Id | Sub Categ Name 
				//                 tempDX[2]   3|CA No*8-30-AN-N-N~Test1*8-30-AN-N-N~Test2*8-30-AN-N-N; No Of Labels|Label Name*Min Length-Max Length-Character Type-Need Encryption-Need Password Character
				//                 tempDX[3]   100 | 1; Fixed Amount | Variable Amount
				//        			 				   tempDX[4]   001  START REC NO
				//                 tempDX[5]   Y More flag
				//                 tempDX[6]   TXNID:110308049915;
				//                 tempDX[7]   BANKID:C403362


				StaticStore.midlet.tempDX = messageContent.split(";",-1);
				StaticStore.LogPrinter('i'," StaticStore.midlet.tempDX>>>"+Arrays.deepToString(StaticStore.midlet.tempDX));

				//                    tempDX[0]   OBC128|Mahanagar Telephone Nigam Delhi|Y~OBC130|Mahanagar Telephone Nigam Delhi|Y;  Biller Details :- Biller Id|Biller Name|Presentment Flag
				StaticStore.midlet.un_Reg_Pres_Billers = StaticStore.midlet.getSplittedValuesArray(StaticStore.midlet.tempDX[0],3,"\\#",StaticStore.midlet.un_Reg_Pres_Billers);
				StaticStore.LogPrinter('i',"un_Reg_Pres_Billers"+Arrays.deepToString(StaticStore.midlet.un_Reg_Pres_Billers));

				//                    tempDX[1]   3~123|Name~123|Name~123|Name ;No Of Sub Category ~ Sub Categ Id | Sub Categ Name ~ Sub Categ Id | Sub Categ Name 
				String temp = StaticStore.midlet.tempDX[1].substring(0,StaticStore.midlet.tempDX[1].indexOf("#"));
				StaticStore.midlet.tempDX[1] = StaticStore.midlet.tempDX[1].substring(StaticStore.midlet.tempDX[1].indexOf("#")+1,StaticStore.midlet.tempDX[1].length());
				StaticStore.LogPrinter('i',"Temp Count>>>"+temp);
				StaticStore.LogPrinter('i',"Temp dx[1] ==> "+Arrays.deepToString(StaticStore.midlet.tempDX));
				StaticStore.midlet.sub_CatId=StaticStore.midlet.getSplittedValuesArray(StaticStore.midlet.tempDX[1],2,"\\#",StaticStore.midlet.sub_CatId);
				StaticStore.LogPrinter('i',"StaticStore.midlet.sub_CatId[i]>>>"+Arrays.deepToString(StaticStore.midlet.sub_CatId));

				//                  tempDX[2]   3|CA No*8-30-AN-N-N|Test1*8-30-AN-N-N|Test2*8-30-AN-N-N; No Of Labels|Label Name*Min Length-Max Length-Character Type-Need Encryption-Need Password Character 
				temp = StaticStore.midlet.tempDX[2].substring(0,StaticStore.midlet.tempDX[2].indexOf("*"));
				StaticStore.midlet.tempDX[2] = StaticStore.midlet.tempDX[2].substring(StaticStore.midlet.tempDX[2].indexOf("*")+1,StaticStore.midlet.tempDX[2].length());
				StaticStore.LogPrinter('i',"Temp Count222>>>"+temp);
				StaticStore.LogPrinter('i',"StaticStore.midlet.tempDX[2]34>>"+StaticStore.midlet.tempDX[2]);
				StaticStore.midlet.tempDX[2]=StaticStore.midlet.tempDX[2].replaceAll("-AN-", "-ANW-");
				StaticStore.LogPrinter('i',"StaticStore.midlet.tempDX[2]5656>>"+StaticStore.midlet.tempDX[2]);
				StaticStore.midlet.Sub_CatInput = StaticStore.midlet.tempDX[2].split("\\/"); //Siva *
				StaticStore.LogPrinter('i',"StaticStore.midlet.Sub_CatInput[i]>>>"+Arrays.deepToString(StaticStore.midlet.Sub_CatInput));
				//                
				//	 				   tempDX[4]   001  START REC NO
				StaticStore.midlet.nextStartRecNumber = String.valueOf(Integer.parseInt(StaticStore.midlet.tempDX[4].trim())+StaticStore.midlet.sub_CatId.length);
				//        			  tempDX[5]   Y More flag
				StaticStore.midlet.moreFlag = StaticStore.midlet.tempDX[5].trim().toString().equals("Y");

				StaticStore.midlet.txnID = StaticStore.midlet.tempDX[6].trim().substring(6);
				StaticStore.LogPrinter('i',"midlet.txnID DX00 >>>"+StaticStore.midlet.txnID);
				StaticStore.LogPrinter('i',"DX00 nextStartRecNumber"+StaticStore.midlet.un_Reg_Pres_Billers.length+StaticStore.midlet.nextStartRecNumber);
				intent = StaticStore.midlet.get_Un_Reg_Pres_BillerList(context);
			}else{

				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("DY")){
			StaticStore.LogPrinter('i',"messageContent DY00  --> "+messageContent);
			if(messageContent.startsWith("DY00")){
				messageContent= messageContent.replaceAll("\\|", "\\*");
				StaticStore.midlet.billpayPres = StaticStore.midlet.getSplittedValues(messageContent.substring(5),15,'~',true,StaticStore.midlet.billpayPres);
				StaticStore.LogPrinter('i',"billpayPres>>>> "+Arrays.deepToString(StaticStore.midlet.billpayPres));
				StaticStore.midlet.inResponseInbox =  false;
				String message = "Rs. "+StaticStore.midlet.billpayPres[0][3]+";"+"Rs. "+StaticStore.midlet.billpayPres[0][7]+";"+StaticStore.midlet.billpayPres[0][8]+";"+StaticStore.midlet.billpayPres[0][9]
				                                                                                                                                                                                            +";"+StaticStore.midlet.billpayPres[0][10]+";"+StaticStore.midlet.billpayPres[0][11]+";"+"Rs. "+StaticStore.midlet.billpayPres[0][12]+";"+StaticStore.midlet.billpayPres[0][13]+"%";                                                                                                                                                            
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",message);
				intent.putExtra("formName", "APDY");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("2B")){
			StaticStore.LogPrinter('i',"messageContent 2B00  --> "+messageContent);
			if(messageContent.startsWith("2B00")){

				String temp2b[] = messageContent .split(";");
				temp2b[1] = temp2b[1].replaceAll("\\|", "\\*");
				messageContent = "";
				for(int i = 0;i< temp2b.length;i++)
				{
					if(i!=2)
					{
						messageContent = messageContent+temp2b[i]+";";	
					}

				}
				messageContent = messageContent.substring(0,messageContent.lastIndexOf(";"));
				StaticStore.LogPrinter('i',"Display Value  --> "+temp2b[2]);
				StaticStore.LogPrinter('i',"Message Context --> "+messageContent);

				StaticStore.midlet.billpayPres = StaticStore.midlet.getSplittedValues(messageContent.substring(5),15,'#',true,StaticStore.midlet.billpayPres);
				StaticStore.LogPrinter('i',"billpayPres>>>> "+Arrays.deepToString(StaticStore.midlet.billpayPres));
				StaticStore.midlet.inResponseInbox =  false;
				String format = Display_Dynamic_Spliter(temp2b[2]);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",format);
				intent.putExtra("formName", "AP2B");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("DZ")){
			StaticStore.forTNEBNote = false;
			if (messageContent.startsWith("DZ00")) {
				messageContent = messageContent.substring(5).trim();
				if (messageContent.contains("TNEB")) {
					StaticStore.midlet.tnebList = StaticStore.midlet.getSplittedValues(messageContent, 2, '#', true,StaticStore.midlet.tnebList);
					String forDisplayable = "";
					for (int i = 0; i < StaticStore.midlet.tnebList.length; i++) {
						forDisplayable = forDisplayable+ StaticStore.midlet.tnebList[i][1] + ";";
					}
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response", forDisplayable);
					intent.putExtra("formName", "DZ00");
				}else if (messageContent.contains("COCTAX")) {
						StaticStore.midlet.cocTaxList = StaticStore.midlet.getSplittedValues(messageContent, 2, '#', true,StaticStore.midlet.cocTaxList);
						String forDisplayable = "";
						for (int i = 0; i < StaticStore.midlet.cocTaxList.length; i++) {
							forDisplayable = forDisplayable+ StaticStore.midlet.cocTaxList[i][1] + ";";
						}
						intent = new Intent(context, DisplayableView.class);
						intent.putExtra("response", forDisplayable);
						intent.putExtra("formName", "DZ00");	
				} else {
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response", messageContent);
					intent.putExtra("formName", "DEFAULT");
				}
				intent.putExtra("title", "Instant Bill Payment");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Instant Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("DT")){
			if(messageContent.startsWith("DT00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.txnID = messageContent.substring(messageContent.indexOf("TXNID")+ 6);
				StaticStore.LogPrinter('i',">>>>>>>>>txnId" + StaticStore.midlet.txnID);
				messageContent = messageContent.substring(5);
				StaticStore.midlet.tempmainDT = messageContent.split("\\;",-1);
				StaticStore.midlet.tempmainDT[0] = StaticStore.midlet.tempmainDT[0].replace('/', '*');
				StaticStore.LogPrinter('i',"StaticStore.midlet.tempmainDT[0] -->"+Arrays.deepToString(StaticStore.midlet.tempmainDT));
				StaticStore.midlet.tempDT = StaticStore.midlet.tempmainDT[0].split("\\*",-1); //| changed to * 
				StaticStore.LogPrinter('i'," StaticStore.midlet.tempDT>>>"+Arrays.deepToString(StaticStore.midlet.tempDT));
				StaticStore.LogPrinter('i',"StaticStore.midlet.tempmainDT"+Arrays.deepToString(StaticStore.midlet.tempmainDT));
				String[] DT_input = new String[Integer.parseInt(StaticStore.midlet.tempDT[3])*2];
				StaticStore.LogPrinter('i',"DT_input ---> "+ Arrays.deepToString(DT_input));
				System.arraycopy(StaticStore.midlet.tempDT, 4, DT_input, 0, Integer.parseInt(StaticStore.midlet.tempDT[3])*2);
				StaticStore.LogPrinter('i',".DT_input>>>"+Arrays.deepToString(DT_input));
				String[] tempReq = null;
				StaticStore.IsPresentment = false;
				if(StaticStore.midlet.tempDT[2].trim().toUpperCase().equals("Y")){
					StaticStore.IsPresentment = true;
					tempReq = new String[9+DT_input.length];
					tempReq[0]= "Instant Bill Payment";
					tempReq[1]= "AP2B;Y"; //APDY
					tempReq[2]= StaticStore.mPINString; 
					tempReq[3]= StaticStore.midlet.tempDT[0];
					tempReq[4]= "N";
					int pos = 0;
					for(int i = 0,j=0;i<DT_input.length/2;i++,j+=2)
					{
						tempReq[5+i]=DT_input[j];
						pos=5+i;
					}
					StaticStore.LogPrinter('i',"tempReq.length >>>>"+tempReq.length);
					tempReq[pos+=1]="";
					tempReq[pos+=1]="";
					tempReq[pos+=1]="";
					for(int i = pos+1,j=1;i< tempReq.length-1;i++,j+=2)
					{
						tempReq[i] = DT_input[j];
					}
					tempReq[1+pos+DT_input.length/2] = String.valueOf(3+(DT_input.length/2));
					StaticStore.LogPrinter('i',"temp REQ"+Arrays.deepToString(tempReq));
				}else
				{

					String[] tempelse = new String[DT_input.length+4];	
					StaticStore.LogPrinter('i',"tempelse.length > >> > >> siva>"+tempelse.length );
					tempelse[DT_input.length]="Amount (Rs.)";
					tempelse[DT_input.length+1]="1-10-ND-N-N";
					tempelse[DT_input.length+2]= StaticStore.midlet.txnID;
					tempelse[DT_input.length+3]="";
					StaticStore.LogPrinter('i',"New DT_input.length>>>> "+Arrays.deepToString(tempelse));
					System.arraycopy(DT_input, 0, tempelse, 0, DT_input.length);
					StaticStore.LogPrinter('i',"New DT_input.length>>>> "+Arrays.deepToString(DT_input));
					StaticStore.LogPrinter('i',"New DT_input>>>> "+Arrays.deepToString(DT_input));
					tempReq = new String[9+tempelse.length];
					tempReq[0]= "Instant Bill Payment";
					tempReq[1]= "APDM;Y";
					tempReq[2]= StaticStore.mPINString;
					tempReq[3]= StaticStore.midlet.tempDT[0];
					tempReq[4]= "N";
					int pos = 0;
					for(int i = 0,j=0;i<tempelse.length/2;i++,j+=2)
					{
						tempReq[5+i]=tempelse[j];
						pos=5+i;
					}
					StaticStore.LogPrinter('i',"tempReq.length >>>>"+tempReq.length);
					tempReq[pos+=1]="";
					tempReq[pos+=1]="";
					tempReq[pos+=1]="";
					for(int i = pos+1,j=1;i< tempReq.length-1;i++,j+=2)
					{
						tempReq[i] = tempelse[j];
					}
					tempReq[1+pos+tempelse.length/2] = String.valueOf(3+(tempelse.length/2));
					StaticStore.LogPrinter('i',"temp REQ"+Arrays.deepToString(tempReq));

				}
				StaticStore.menuDesc[222] = new String [tempReq.length+3];

				for(int i=0;i<tempReq.length;i++)
				{
					StaticStore.menuDesc[222][i] = tempReq[i];
				}
				StaticStore.menuDesc[222][tempReq.length] = "true";
				StaticStore.menuDesc[222][tempReq.length+1] = "true";
				StaticStore.menuDesc[222][tempReq.length+2] = "Y"; //mpin flag

				StaticStore.LogPrinter('i',"Menu Desc[222]>>>>"+Arrays.deepToString(StaticStore.menuDesc[222]));
				StaticStore.index = 222;
				String Message =null;
				if(StaticStore.IsPresentment)
				{
					Message = StaticStore.midlet.tempDT[0]+";"+ StaticStore.midlet.tempDT[1];
					StaticStore.tagType = "APDY";
					StaticStore.tagTypePrev = "APDT";
				}else {
					Message = StaticStore.midlet.tempDT[0]+";"+ StaticStore.midlet.tempDT[1]+";"+"Rs. "+StaticStore.midlet.tempmainDT[1]+";"+StaticStore.midlet.tempmainDT[2]+"%";
					StaticStore.tagType = "APDM";
					StaticStore.tagTypePrev = "APDT"; //used for ":" sep
				}
				StaticStore.LogPrinter('i',"Message::::"+Message);   
				StaticStore.midlet.txnID = StaticStore.midlet.tempmainDT[3].trim().substring(6);
				StaticStore.LogPrinter('i',"midlet.txnID DT00 >>>"+StaticStore.midlet.txnID);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",Message);
				intent.putExtra("formName", "APDT");
				intent.putExtra("title","Registered Bill Payment");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
			return intent;
		}else if(messageContent.startsWith("DM")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Bill Payment");
			return intent;
		}else if(messageContent.startsWith("DN")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Bill Payment");
			return intent;
		}else{
			return null;
		}
	}

	public Intent BY_TL_TD_CPCN_M1_M2_M3_M4_ML_MC_R5_R6_R7(Context context , String messageContent)throws Exception{
		if(messageContent.startsWith("BY")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Biller");
			return intent;
		}else if(messageContent.startsWith("TL")){
			if(messageContent.startsWith("TL00")){
				StaticStore.midlet.temples = StaticStore.midlet.getSplittedValues(messageContent.substring(5),7,'~',true,StaticStore.midlet.temples);
				StaticStore.midlet.templeSchemes = getTempleSchemes123(StaticStore.midlet.temples);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getTempleList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Entity List");
			}
			return intent;
		}else if(messageContent.startsWith("TD")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "TD00");
			intent.putExtra("title","Entity List");
			return intent;
		}else if(messageContent.startsWith("CPCN")){
			intent = new Intent(context , DisplayableView.class);
			/*Some thing need to be hand*/
			if(messageContent.startsWith("CPCN00")){

				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "CPCN00");
				intent.putExtra("title","Customer Purchase");
			}else{
                	intent.putExtra("response",messageContent.substring(5));
                	intent.putExtra("formName", "DEFAULT");
                	intent.putExtra("title","Customer Purchase");
                }
			return intent;
		}else if(messageContent.startsWith("M1")){
			String temp = messageContent.substring(messageContent.indexOf(";") + 1);
			int ctr = 0;
			ctr = StaticStore.midlet.getCharCount(messageContent,'#') + 1;
			if(messageContent.startsWith("M100")){
				StaticStore.LogPrinter('i',">>>>>came inside m100");
				temp = movieDatas((byte)1,temp.substring(temp.indexOf(";") + 1).trim());                    
				StaticStore.midlet.showTimings = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.showTimings);
				StaticStore.midlet.inResponseInbox =  false;
				temp = temp.substring(temp.indexOf(";")+1).trim();
				StaticStore.midlet.nextStartRecNumber = (Integer.parseInt(temp.substring(0,temp.indexOf(";")).trim()) + ctr) + "";
				intent = new Intent();
				intent = getMovieTimeList(context);
			}else if(messageContent.startsWith("M101")){
				temp = movieDatas((byte)2,temp.substring(temp.indexOf(";") + 1).trim());
				StaticStore.midlet.movieVenue = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'#',true,StaticStore.midlet.movieVenue);                    
				StaticStore.midlet.inResponseInbox =  false;                    
				StaticStore.midlet.movieLoadingMsg = "Fetching movie list, please wait for the response";                   
				temp = temp.substring(temp.indexOf(";")+1).trim();                    
				StaticStore.midlet.nextStartRecNumber = (Integer.parseInt(temp.substring(0,temp.indexOf(";")).trim()) + ctr) + "";  
				intent = new Intent();
				intent =getVenueList(context); 
			}else if(messageContent.startsWith("M102")){
				temp = movieDatas((byte)3,temp.substring(temp.indexOf(";") + 1).trim());
				StaticStore.midlet.movies = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'#',true,StaticStore.midlet.movies);
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.movieLoadingMsg = "Fetching the available dates, please wait for response";
				temp = temp.substring(temp.indexOf(";")+1).trim();
				StaticStore.midlet.nextStartRecNumber = (Integer.parseInt(temp.substring(0,temp.indexOf(";")).trim()) + ctr) + "";
				intent = new Intent();
				intent =getCinemaList(context); 
			}else if(messageContent.startsWith("M103")){
				temp = movieDatas((byte)4,temp.substring(temp.indexOf(";") + 1).trim());
				StaticStore.midlet.movieDates = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'#',true,StaticStore.midlet.movieDates);
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.movieLoadingMsg = "Fetching the Show timings, please wait for response";
				StaticStore.ToastDisplay(context,"Opted date not available, Select from available date.");
				intent = new Intent();
				temp = temp.substring(temp.indexOf(";")+1).trim();
				StaticStore.midlet.nextStartRecNumber = (Integer.parseInt(temp.substring(0,temp.indexOf(";")).trim()) + ctr) + "";
				intent =getDateList(context);
			} else {
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Movie Ticketing");
			}
			return intent;
		}else if(messageContent.startsWith("M2")){
			String temp = messageContent.substring(messageContent.indexOf(";") + 1);
			if(messageContent.startsWith("M200")){
				movieDatas((byte)5,temp.substring(temp.indexOf(";") + 1).trim());
				StaticStore.midlet.moviePrice = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'#',true,StaticStore.midlet.moviePrice);
				StaticStore.midlet.inResponseInbox =  false;
				intent  = new Intent();
				intent = getPriceList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Movie Ticketing");
			}
			return  intent;
		}else if(messageContent.startsWith("M3")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("M300")){

				messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				StaticStore.midlet.movieOrderDetails = StaticStore.midlet.getSplittedValues(messageContent.substring(5),6,'#',true,StaticStore.midlet.movieOrderDetails);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "M300");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Movie Ticketing");
			}
			return intent;
		}else if(messageContent.startsWith("M4")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Movie Ticketing");
			return intent;
		}else if(messageContent.startsWith("ML")){
			if(messageContent.startsWith("ML00")){  
				String temp = messageContent;
				temp        = temp.substring(5).trim();                     
				if(temp.startsWith("Y"))
				{	intent = new Intent(context , DisplayableView.class);
				String flag=messageContent.substring(messageContent.indexOf(";")+1);
				String minmax=flag.substring(flag.indexOf(";")+1,flag.indexOf("TXNID:"));
				StaticStore.midlet.minAmt=Integer.parseInt(minmax.substring(0,minmax.indexOf(";")).trim());
				StaticStore.midlet.maxAmt=Integer.parseInt(minmax.substring(minmax.indexOf(";")+1,minmax.length()-1).trim());
				flag=flag.substring(0,flag.indexOf(";")).trim();
				StaticStore.midlet.txnID = messageContent.substring(messageContent.indexOf("TXNID:")+6).trim();
				intent.putExtra("response",messageContent.substring(7));
				intent.putExtra("formName", "ML00N");
				}else{                    
					StaticStore.midlet.inResponseInbox =  false;
					StaticStore.midlet.tarifPlans = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'#',true,StaticStore.midlet.tarifPlans);
					intent = new Intent();
					intent = getTarifPlans(context);
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Mobile Recharge");
			}
			return intent;
		}else if(messageContent.startsWith("MC")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Mobile Recharge");
			return intent;
		}else if(messageContent.startsWith("R5")){
			StaticStore.LogPrinter('i',"|||||||||insideMobileRecharge");
			if(messageContent.startsWith("R500")){
				StaticStore.midlet.inResponseInbox =  false;
				String temp = messageContent;
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.operatorList = StaticStore.midlet.getSplittedValues(messageContent,4,'#',true,StaticStore.midlet.operatorList);
				StaticStore.LogPrinter('i',"***********R500****"+Arrays.deepToString(StaticStore.midlet.operatorList));
				intent = new Intent();
				intent = getOperatorList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Mobile Recharge");
			}
			return intent;
		}else if(messageContent.startsWith("R6")){
			if(messageContent.startsWith("R600")){
				StaticStore.tagType="R6";
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5).trim();
				StaticStore.menuDesc[220]=new String[]{"Mobile Recharge","APR7;Y",StaticStore.mPINString,"","Beneficiary Mobile No.","Amount","","","","","","","5","false","false"};
				StaticStore.menuDesc[220][3] = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.LogPrinter('i',"***********R500****"+Arrays.deepToString(StaticStore.midlet.operatorList));
				StaticStore.midlet.txnID = messageContent.substring(messageContent.indexOf("TXNID:")+6).trim();
				messageContent = messageContent.substring(0,messageContent.indexOf("TXNID:"));
				messageContent = messageContent.substring(0,messageContent.lastIndexOf(";"));
				messageContent = messageContent.substring(0,messageContent.lastIndexOf(";"));
				StaticStore.LogPrinter('i',"@@@@@@@@@"+messageContent);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "R600");
			}else{
				StaticStore.indexCtr--;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Mobile Recharge");
			}
			return intent;
		} else if(messageContent.startsWith("R7")){
			StaticStore.tagType="";
			StaticStore.indexCtr--;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Mobile Recharge");
			return intent;

		} 
		return intent;
	}
	public Intent IS_IR_IL_I1_I2_I3_I4_I5_ID_IP_RC_PI_PM_N1_N2_N4_N5(Context context , String messageContent)throws Exception{
		if(messageContent.startsWith("IS")){
			if(messageContent.startsWith("IS00")){
				StaticStore.midlet.instSearchStr = messageContent.substring(messageContent.indexOf(";") + 1);
				messageContent = StaticStore.midlet.instSearchStr;
				StaticStore.midlet.instSearchStr = StaticStore.midlet.instSearchStr.substring(0,StaticStore.midlet.instSearchStr.indexOf(";"));
				StaticStore.midlet.institutions = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";") + 1),2,'#',true,StaticStore.midlet.institutions);
				 StaticStore.LogPrinter('i',"StaticStore.midlet.institutions ==> "+Arrays.deepToString(StaticStore.midlet.institutions));
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getInstitutionList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Institution Registration");
			}
		}else if(messageContent.startsWith("IR")){
			if(messageContent.startsWith("IR00")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "IR00");
				intent.putExtra("title","Institution Registration");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Institution Registration");
			}
		}else if(messageContent.startsWith("IL")){
			if(messageContent.startsWith("IL00")){
				StaticStore.midlet.regInstitutes = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.regInstitutes);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent  = StaticStore.midlet.getRegInstituteList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}else if(messageContent.startsWith("I1")){
			if(messageContent.startsWith("I100")){                    
				StaticStore.midlet.ifpMessage = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.instId         = StaticStore.midlet.ifpMessage.substring(0,StaticStore.midlet.ifpMessage.indexOf(";")).trim();
				StaticStore.midlet.ifpMessage        = StaticStore.midlet.ifpMessage.substring(StaticStore.midlet.ifpMessage.indexOf(";") + 1).trim();
				StaticStore.midlet.rollNoLabel    = StaticStore.midlet.ifpMessage.substring(0,StaticStore.midlet.ifpMessage.indexOf(";")).trim();
				StaticStore.LogPrinter('i',">>>>>>>>StaticStore.midlet.rollNoLabel>>>>"+StaticStore.midlet.rollNoLabel);
				StaticStore.midlet.ifpMessage        = StaticStore.midlet.ifpMessage.substring(StaticStore.midlet.ifpMessage.indexOf(";") + 1).trim();
				StaticStore.midlet.rollNo         = StaticStore.midlet.ifpMessage.substring(0,StaticStore.midlet.ifpMessage.indexOf(";")).trim();
				StaticStore.midlet.ifpMessage        = StaticStore.midlet.ifpMessage.substring(StaticStore.midlet.ifpMessage.indexOf(";") + 1).trim();
				StaticStore.midlet.name           = StaticStore.midlet.ifpMessage.substring(0,StaticStore.midlet.ifpMessage.indexOf(";")).trim();
				StaticStore.midlet.ifpMessage        = StaticStore.midlet.ifpMessage.substring(StaticStore.midlet.ifpMessage.indexOf(";") + 1).trim();
				StaticStore.midlet.hostelFlag     = StaticStore.midlet.ifpMessage.substring(0,StaticStore.midlet.ifpMessage.indexOf(";")).trim().toUpperCase().equals("Y");
				StaticStore.midlet.instHostelTutionList = StaticStore.midlet.getSplittedValues(StaticStore.midlet.ifpMessage.substring(StaticStore.midlet.ifpMessage.indexOf(";") + 1),3,'#',true,StaticStore.midlet.instHostelTutionList);
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.ifpMessage = StaticStore.midlet.rollNo + ";" + StaticStore.midlet.name; 
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",StaticStore.midlet.ifpMessage);
				intent.putExtra("formName", "I100");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}else if(messageContent.startsWith("I2")){
			if(messageContent.startsWith("I200")){
				String tempStr = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.instId         = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.rollNo         = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.hostelFlag     = tempStr.substring(0,tempStr.indexOf(";")).trim().toUpperCase().equals("Y");
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.hostelId       = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();                    
				StaticStore.midlet.instCatagories = StaticStore.midlet.getSplittedValues(tempStr.substring(tempStr.indexOf(";") + 1),3,'#',true,StaticStore.midlet.instCatagories);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getInstCategoryList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}else if(messageContent.startsWith("I3")){
			if(messageContent.startsWith("I300")){
				String tempStr = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.instId         = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.rollNo         = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.categoryFlag   = tempStr.substring(0,tempStr.indexOf(";")).trim().toUpperCase().equals("Y");
				tempStr        = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.instCategoryId = tempStr.substring(0,tempStr.indexOf(";")).trim();
				StaticStore.midlet.instSubCatagories = StaticStore.midlet.getSplittedValues(tempStr.substring(tempStr.indexOf(";") + 1),3,'#',true,StaticStore.midlet.instSubCatagories);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getInstSubCatList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}else if(messageContent.startsWith("I4")){  
			if(messageContent.startsWith("I400")){
				StaticStore.midlet.inResponseInbox =  false;                                         
				StaticStore.midlet.instBillDetails = StaticStore.midlet.getSplittedValues(messageContent.substring(5),19,'#',true,StaticStore.midlet.instBillDetails);
				 StaticStore.LogPrinter('i',"StaticStore.midlet.instBillDetails ==> "+Arrays.deepToString(StaticStore.midlet.instBillDetails));
                 if(StaticStore.midlet.instBillDetails[0][1].trim().toUpperCase().equals("Y")){
                     StaticStore.midlet.ifpPaymentMsg = "Presenment"+ ";" + StaticStore.midlet.instBillDetails[0][2] + ";" + StaticStore.midlet.instBillDetails[0][14] + ";" + StaticStore.midlet.instBillDetails[0][15] + ";" +"Rs."+StaticStore.midlet.instBillDetails[0][16] + ";" +(StaticStore.midlet.instBillDetails[0][12].trim().toUpperCase().equals("Y")?"Rs."+StaticStore.midlet.instBillDetails[0][17]:"NA")+ ";" + (StaticStore.midlet.instBillDetails[0][13].trim().toUpperCase().equals("Y")?"Rs."+StaticStore.midlet.instBillDetails[0][18]:"NA");
                     intent = new Intent(context , DisplayableView.class);
                 	intent.putExtra("response",StaticStore.midlet.ifpPaymentMsg);
                   	intent.putExtra("formName", "I4PP");
                 }else{
                     StaticStore.midlet.ifpPaymentMsg = "Non-Presenment"+ ";" + StaticStore.midlet.instBillDetails[0][2];
                     intent = new Intent(context , DisplayableView.class);
                 	intent.putExtra("response",StaticStore.midlet.ifpPaymentMsg);
                   	intent.putExtra("formName", "I4NP");
                 }
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}else if(messageContent.startsWith("I5")){
			if(messageContent.startsWith("I500")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.regInstitutes4Dereg = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.regInstitutes4Dereg);
				intent = new Intent();
				intent = StaticStore.midlet.getRegInstituteList4Dereg(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Institution Deregistration");
			}
		}else if(messageContent.startsWith("ID")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Institution Deregistration");
		}else if(messageContent.startsWith("IP")){
			StaticStore.midlet.regInstitutes = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Fees Payment");
		}else if(messageContent.startsWith("RC")){
			StaticStore.LogPrinter('i',"Came into the RC -- >"+messageContent);
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Cancel MMID");
			StaticStore.LogPrinter('i',">>>>>>>>>>>>>>>>>>V=CAME AFTER RC00");
		} else if(messageContent.startsWith("PI")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Pay");             	
		} else if(messageContent.startsWith("PM")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Merchant");             	
		} else if(messageContent.startsWith("N1")){
			if(messageContent.startsWith("N100")){
				StaticStore.midlet.feesPaySearchInstN100Res = messageContent;
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.feesPaySearchInst = messageContent.substring(0,1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.feesPayN100Details = StaticStore.midlet.getSplittedValues(messageContent,10,'#',true,StaticStore.midlet.feesPayN100Details);
				intent = new Intent();
				intent = StaticStore.midlet.getFeesPayInstituteList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		} else if(messageContent.startsWith("N2")){
			if(messageContent.startsWith("N200")){
				String temp = messageContent.substring(5).trim();
				int ctr = 0;
				ctr = StaticStore.midlet.getCharCount(temp,'#') + 1;
				StaticStore.instID = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.instConfirmDetails = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.IsPresentment = temp.substring(0,temp.indexOf(";")).equals("Y");
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.noOfCategories = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.collegeDetails = temp.substring(0,temp.indexOf(";")+1);
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.nextStartRecNumber = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
					 
				StaticStore.midlet.instConfirmDetailsList = StaticStore.instConfirmDetails.split(":");
				String temp2 = "";
				for(int i = 0;i< StaticStore.midlet.instConfirmDetailsList.length;i++){
					if (temp2 == "") {
						temp2 = StaticStore.midlet.instConfirmDetailsList [i].toString();
					}else{
						temp2 = temp2 + ";"+ StaticStore.midlet.instConfirmDetailsList [i].toString();
					}
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp2);
				intent.putExtra("formName", "APN2");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		} else if(messageContent.startsWith("N4")){
			if(messageContent.startsWith("N400")){
				String temp = messageContent.substring(5).trim();
				int ctr = 0;
				ctr = StaticStore.midlet.getCharCount(temp,'#') + 1;
				StaticStore.instID = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.instConfirmDetails = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.IsPresentment = temp.substring(0,temp.indexOf(";")).equals("Y");
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.N400Details = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.collegeUniqueDetails = temp.substring(0,temp.indexOf(";")+1);
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.nextStartRecNumber = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				temp = temp.substring(temp.indexOf(";") + 1);
				if(temp.contains(";")){
					StaticStore.N400TxnID = temp.substring(6,temp.indexOf(";"));
				}else{
					StaticStore.N400TxnID = temp.substring(6,temp.length());
				}
				StaticStore.midlet.instConfirmDetailsList = StaticStore.instConfirmDetails.split(":");
				String temp2 = "";
				for(int i = 0;i< StaticStore.midlet.instConfirmDetailsList .length;i++){
					if (temp2 == "") {
						temp2 = StaticStore.midlet.instConfirmDetailsList [i].toString();
					}else{
						temp2 = temp2 + ";"+ StaticStore.midlet.instConfirmDetailsList [i].toString();
					}
				}
				
				StaticStore.midlet.N400DetailsList = StaticStore.N400Details.split("\\*");
				String temp3 = "";
				for(int i = 0;i< StaticStore.midlet.N400DetailsList .length;i++){
					if (temp3 == "") {
						temp3 = StaticStore.midlet.N400DetailsList [i].toString();
					}else{
						temp3 = temp3 + ";"+ StaticStore.midlet.N400DetailsList [i].toString();
					}
				}
				
				StaticStore.midlet.collegeUniqueDetailsList = StaticStore.midlet.getSplittedValues("Bill Type*Presenment" + "#"+ StaticStore.collegeUniqueDetails,2,'#',true,StaticStore.midlet.collegeUniqueDetailsList);
				String forDisplayable = "";
				for(int i = 0; i < StaticStore.midlet.collegeUniqueDetailsList.length;i++){
					forDisplayable = forDisplayable + StaticStore.midlet.collegeUniqueDetailsList[i][1] +";";

				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",forDisplayable);
				intent.putExtra("formName", "APN4");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		} else if(messageContent.startsWith("N5")){
			if(messageContent.startsWith("N500")){
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.ifpList = StaticStore.midlet.getSplittedValues(messageContent, 2, '#', true,StaticStore.midlet.ifpList);
				String forDisplayable = "";
				for (int i = 0; i < StaticStore.midlet.ifpList.length; i++) {
					forDisplayable = forDisplayable+ StaticStore.midlet.ifpList[i][1] + ";";
				}
				intent = new Intent(context, DisplayableView.class);
				intent.putExtra("response", forDisplayable);
				intent.putExtra("formName", "N500");
				intent.putExtra("title", "Fees Payment");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Fees Payment");
			}
		}
		return intent;
	}
	public Intent IC_MU_NA_SY_P2_FQ_FP_1L_LW_SA_AS_A1_AL_EL(Context context , String messageContent)throws Exception{    
		if(messageContent.startsWith("IC")){
			if(messageContent.startsWith("IC00")){
				StaticStore.midlet.instBillDetails = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,';',true,StaticStore.midlet.instBillDetails);
				StaticStore.midlet.instBills123       = StaticStore.midlet.getSplittedValues(StaticStore.midlet.instBillDetails[10][0],11,'~',true,StaticStore.midlet.instBills123);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent  = StaticStore.midlet.getInstSubCatList(context);
			}else{
			}
		}else if(messageContent.startsWith("MU")){
			//FU00;Success;9790954696;237654782357845782354;11111110000000111111100000001;123456;TXNID:00001
			if(messageContent.startsWith("MU00")){
				StaticStore.IsGPRS = true;
				messageContent = messageContent.substring(5);
				StaticStore.myMobileNo =messageContent.substring(0,messageContent.indexOf(';'));
				StaticStore.IsPersonalInfoGot = true;
				StaticStore.midlet.writeinMemory(context);

				if(StaticStore.isFromVersionUpdate){
					StaticStore.isFromVersionUpdate = false;
					StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
					StaticStore.index =  220;                		  
					intent = new Intent(context,DynamicCanvas.class);
				}
				else if(StaticStore.selectedLoginGridIndex == 1){
					StaticStore.FromListScreen = false;
					StaticStore.menuDesc[114][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
					StaticStore.index = 114;
					intent = new Intent(context,
							DynamicCanvas.class);
				}else if(StaticStore.selectedLoginGridIndex == 0){            			
					intent = new Intent();
					intent = StaticStore.midlet.getAbout(context);
				}else if(StaticStore.selectedLoginGridIndex == 2){
					intent = new Intent();
					intent = StaticStore.midlet.get_ProductsMenu(context);  
				}else if(StaticStore.selectedLoginGridIndex == 3){
					StaticStore.index = 129;
					intent = new Intent(context,DynamicCanvas.class);       			
				}else if(StaticStore.selectedLoginGridIndex == 4){
					intent = new Intent();
					intent = StaticStore.midlet.getLocatorMenu(context); 
				}else if(StaticStore.selectedLoginGridIndex == 5){  
					StaticStore.menuDesc[220] = new String []{"Feedback","AP1F;N","001","","1","false","false","N"};
					StaticStore.tagType = "AP1F";
					StaticStore.index = 220;
					intent = new Intent(context,DynamicCanvas.class);
				}else if(StaticStore.selectedLoginGridIndex == 6){            			
					StaticStore.isForgotPassword = true;
					StaticStore.NoteForgotPWD = true;
					//if(StaticStore.isOTPBuild && StaticStore.SMS_AUTHENTICATION_GPRSMODE){
					if(StaticStore.isOTPBuild){
						StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
						StaticStore.FromListScreen = false;
						StaticStore.menuDesc[220] = new String []{"OTP Generation","APOG",StaticStore.myMobileNo,"","1","false","false","Y"};
						StaticStore.tagType = "APOG";
						StaticStore.index = 220;
						intent = new Intent(context,DynamicCanvas.class);	
					}else{
						StaticStore.FromListScreen = true;
						StaticStore.index = 115;
						intent = new Intent(context,DynamicCanvas.class);
					}
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title",StaticStore.menuDesc[114][0]);
			} 
		}else if(messageContent.startsWith("NA")){
			//OU00;<Message>;<Mobile Number>;<PUBLIC KEY>;<SERVER CONTROL>;<DEFAULT PASSWORD>;<TXNID>
			if(messageContent.startsWith("NA00")){
				//NA00;Success;Y;9192921234;1234;Y;70F0000000000000;70F0000000000000;0000000000000000;Y;Y;N;0005;BID:C404322
				StaticStore.lastLoginTime = DateFormat.getDateTimeInstance().format(new Date());
				String firstMsg = ""; 
				String secondMsg = "";
				messageContent = messageContent.substring(5);
				if(StaticStore.isForgotPassword)
				{
					firstMsg = "You have successfully created your TMB mConnect Login Password.";
				}else{
				firstMsg = messageContent.substring(0,messageContent.indexOf(';'));
				}
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.registeredUserStatus = messageContent.substring(0,messageContent.indexOf(';')).trim();
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.myMobileNo = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.applicationPWD = messageContent.substring(0,messageContent.indexOf(';'));
				StaticStore.LogPrinter('i',">>>>StaticStore.applicationPWD>>"+StaticStore.applicationPWD);
				StaticStore.applicationPWD = StaticStore.midlet.hashingByMD5(StaticStore.applicationPWD);
				StaticStore.LogPrinter('i',">>>>StaticStore.applicationPWD>after hashing>"+StaticStore.applicationPWD);
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isMpinAtLast = !(messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y"));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue1 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue2 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String mPinHexaValue3 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.SMS_AUTHENTICATION_SMSMODE = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.SMS_AUTHENTICATION_GPRSMODE = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isAccTypeRefresh = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.chequeLength = messageContent.substring(0,messageContent.indexOf(';')).trim();
				StaticStore.chequeMin = StaticStore.chequeLength.substring(0,2);
	            StaticStore.chequeMax = StaticStore.chequeLength.substring(2,4);
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				if(!messageContent.trim().equals(""))
					StaticStore.publicKey = messageContent;
				StaticStore.mPinHexaValue = StaticStore.midlet.hexToBinary(mPinHexaValue1)+StaticStore.midlet.hexToBinary(mPinHexaValue2)+StaticStore.midlet.hexToBinary(mPinHexaValue3);
				StaticStore.mpinNeededTransactions = new mPAY().getMPinEnabledtransaction(StaticStore.mPinHexaValue);
				StaticStore.LogPrinter('i',"::::::::::::HEXVALUE::::::::::"+StaticStore.mPinHexaValue);
				StaticStore.LogPrinter('i',"::::::::::::mpinneeded::::::::::"+StaticStore.mpinNeededTransactions);
				if(StaticStore.registeredUserStatus.equals("R")){
					secondMsg = "Please change your mPIN before initiating any transaction.";
				}else if(StaticStore.registeredUserStatus.equals("N")){
					secondMsg = "You are not a Mobile Banking customer. Kindly register for mobile banking services through your bank branch / TMB Net Banking.";
				}else{
					secondMsg = "Please use your mPIN to continue using mobile banking services.";
				}
				StaticStore.IsPermitted       = true;
				StaticStore.retryCount = 0;
				StaticStore.midlet.writeinMemory(context);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",firstMsg+"\n\n"+secondMsg);
				intent.putExtra("formName", "Activation");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Activation");
			}
		}else if(messageContent.startsWith("SY")){
			if(messageContent.startsWith("SY00")){
				boolean isMpinChangeNeeded = false;
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				isMpinChangeNeeded = messageContent.substring(0,messageContent.indexOf(";")).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.accOwner = messageContent.substring(0,messageContent.indexOf(";"));


				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				int startervalue = Integer.parseInt(messageContent.substring(0,messageContent.indexOf(";")));
				if(startervalue == 1){
					for (int i = 0; i < StaticStore.accountNumbers.length; i++) {
						StaticStore.accountNumbers [i]= "0";
						StaticStore.accType[i] = "0";
					}
				}
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.maxAccLengthFromResponse += Integer.parseInt(messageContent.substring(0,messageContent.indexOf(";")));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				String accNos = messageContent.substring(0,messageContent.indexOf(";"));
				for(int i = startervalue - 1; i < StaticStore.maxAccLengthFromResponse - 1 ; i++){
					tempSeparateAccNOfromDesc = accNos.substring(0,accNos.indexOf("#")) ;
					StaticStore.accountNumbers[i] = tempSeparateAccNOfromDesc.substring(0,tempSeparateAccNOfromDesc.indexOf("*")) ;
					StaticStore.accType[i]= tempSeparateAccNOfromDesc.substring(tempSeparateAccNOfromDesc.indexOf("*")+1);
					accNos = accNos.substring(accNos.indexOf("#") + 1);
				}
				StaticStore.accountNumbers[StaticStore.maxAccLengthFromResponse - 1] = accNos.substring(0,accNos.indexOf("*"));
				StaticStore.accType[StaticStore.maxAccLengthFromResponse - 1] = accNos.substring(accNos.indexOf("*")+1);

				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				
				String moreFlag = messageContent.substring(0,messageContent.indexOf(";"));

				if(moreFlag.equals("Y")) {
					StaticStore.midlet.moreFlag = true;
				}else{
					StaticStore.midlet.moreFlag = false;
					StaticStore.menuDesc[188][2] ="0001";
					StaticStore.maxAccLengthFromResponse = 0;
				}

				try{
					StaticStore.midlet.writeAccountInMemory();
				}catch (Exception e){
					e.printStackTrace();
				}
				if(!StaticStore.isAccSyncFromMySetup){
					
					 StaticStore.LogPrinter('i',"!StaticStore.isAccSyncFromMySetup ==> "+!StaticStore.isAccSyncFromMySetup);
					if(StaticStore.midlet.moreFlag){
						StaticStore.menuDesc[188][2] = ""+(StaticStore.maxAccLengthFromResponse+1);
						StaticStore.index =  188;
						intent = new Intent(context,
								DynamicCanvas.class);
					}else{
						if(isMpinChangeNeeded){
							StaticStore.tagType = "APP2";
							StaticStore.menuDesc[220] = new String []{"Change mPIN","APP2;Y",StaticStore.mPINString+" ","New mPIN","Confirm New mPIN","4-4-N-Y-Y","4-4-N-Y-Y","4-4-N-Y-Y","3","true","true","N"};
							StaticStore.index =  220;
							StaticStore.FromListScreen = true;
							intent = new Intent(context,DynamicCanvas.class);
						}else{
							if(StaticStore.isFinancialAccSync && !StaticStore.isMyOwnAccSync){
								StaticStore.NoteForAccSel = true;
								ListObject listObj = StaticStore.midlet.listObject;
								intent = new Intent(context, ListSelection.class);
								intent.putExtra("listIndex", listObj.getIndex());
								intent.putExtra("menuItem", new ListSelection().getAccessibleAccArray(listObj.getTag()));
								intent.putExtra("listHeader", listObj.getHeading());
							}else if(!StaticStore.isFinancialAccSync && StaticStore.isMyOwnAccSync){
								StaticStore.isMyOwnAccSync = false;
								if(StaticStore.midlet.getFilledAccArray().length == 1){
									  StaticStore.ToastDisplay(context,"You have only one account mapped to this mobile number. You cannot do the funds transfer.");
								}else{
									  StaticStore.midlet.listObject = new ListObject();
									  StaticStore.midlet.listObject.setIndex(273);
									  StaticStore.midlet.listObject.setTag("ST");
									  StaticStore.midlet.listObject.setHeading("Select From A/C");
									  StaticStore.midlet.listObject.setMore(false);
									  StaticStore.midlet.startFragment(context,StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,context));
								}
							}else{
								StaticStore.index =  StaticStore.specialDynamicIndexForAccountSyn;
								StaticStore.tagType = StaticStore.specialDynamicTAGTYPEForAccountSyn;
								intent = new Intent(context,
										DynamicCanvas.class);
							}
						}
					}
				}else{
					 StaticStore.LogPrinter('i',"ELSE PART StaticStore.isAccSyncFromMySetup ==> "+StaticStore.isAccSyncFromMySetup);
					StaticStore.isAccSyncFromMySetup = true;
					
					if(StaticStore.midlet.moreFlag){
						StaticStore.menuDesc[188][2] = ""+(StaticStore.maxAccLengthFromResponse+1);
						StaticStore.index =  188;
						intent = new Intent(context,
								DynamicCanvas.class);
						
						StaticStore.LogPrinter('i',"StaticStore.midlet.moreFlag ACCount Message"+StaticStore.index);
					}else{
						String tempMsg="";
						String accArray[] = new String[StaticStore.midlet.getFilledAccArray().length];
						accArray = StaticStore.midlet.getFilledAccArray();
						for(int i=0;i<accArray.length;i++){
							tempMsg +=accArray[i]+";";
						}
						StaticStore.LogPrinter('i',"ACCount Message"+tempMsg);
						StaticStore.LogPrinter('i',"MOreFlag"+moreFlag);
						intent = new Intent(context , DisplayableView.class);
						intent.putExtra("response",tempMsg);
						intent.putExtra("formName", "AccDisplay");
					}
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Transaction Failure"); //Account Fetch

			}
		}else if(messageContent.startsWith("P2")){
			if(messageContent.startsWith("P200")){
				//P200;Change mPINd successfully;186624516055148580531709108089691842721;24/09/2013 14:42;TXNID:326714978745
				messageContent = messageContent.substring(5);
				String temp = messageContent.substring(0, messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.publicKey = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.writeinMemory(context);
				if(StaticStore.isPinChangeFromTabbar){
					StaticStore.isPinChangeFromTabbar = false;
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",temp + ";" + messageContent);
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Change mPIN");
				}else if(StaticStore.isFinancialAccSync && !StaticStore.isMyOwnAccSync){
					ListObject listObj = StaticStore.midlet.listObject;
					intent = new Intent(context, ListSelection.class);
					intent.putExtra("listIndex", listObj.getIndex());
					intent.putExtra("menuItem", new ListSelection().getAccessibleAccArray(listObj.getTag()));
					intent.putExtra("listHeader", listObj.getHeading());
				}else if(!StaticStore.isFinancialAccSync && StaticStore.isMyOwnAccSync){
					StaticStore.isMyOwnAccSync = false;
					if(StaticStore.midlet.getFilledAccArray().length == 1){
						  StaticStore.ToastDisplay(context,"You have only one account mapped to this mobile number. You cannot do the funds transfer.");
					}else{
						  StaticStore.midlet.listObject = new ListObject();
						  StaticStore.midlet.listObject.setIndex(273);
						  StaticStore.midlet.listObject.setTag("ST");
						  StaticStore.midlet.listObject.setHeading("Select From A/C");
						  StaticStore.midlet.listObject.setMore(false);
						  StaticStore.midlet.startFragment(context,StaticStore.midlet.accountScreenCreation(StaticStore.midlet.listObject,context));
					}
				}else if(StaticStore.specialDynamicIndexForAccountSyn != 218 && !StaticStore.specialDynamicTAGTYPEForAccountSyn.equals("P2")){
					StaticStore.index =  StaticStore.specialDynamicIndexForAccountSyn;
					StaticStore.tagType = StaticStore.specialDynamicTAGTYPEForAccountSyn;
					intent = new Intent(context,
							DynamicCanvas.class);

				}else{
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",temp + ";" + messageContent);
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Change mPIN"); 
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Change mPIN");
			}
		}else if(messageContent.startsWith("FQ")){
			if(messageContent.startsWith("FQ00")){
				String temp  = messageContent.substring(messageContent.indexOf(";") + 1);
				int    index = Integer.parseInt(temp.substring(0,temp.indexOf(";")));
				StaticStore.secretQues = StaticStore.midlet.secretQues[index];
				StaticStore.index = 116;
				intent = new Intent(context , DynamicCanvas.class);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Forgot password");
			}
		}else if(messageContent.startsWith("FP")){
			if(messageContent.startsWith("FP00")){
				String temp = messageContent.substring(messageContent.indexOf(";") + 1);
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.defaultPWD = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.myMobileNo = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.publicKey       = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.KSN       = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.BDK = temp.substring(0,temp.indexOf(";"));
				StaticStore.IsPersonalInfoGot = true;
				StaticStore.midlet.writeinMemory(context);
				StaticStore.index = 117;
				intent = new Intent(context , DynamicCanvas.class);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Forgot password");
			}
		}
		else if(messageContent.startsWith("LW")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Cheque Status");
		}else if(messageContent.startsWith("SA")){
			if(messageContent.startsWith("SA00")){
				messageContent = messageContent.substring(5).trim();
				StaticStore.isPincode      = messageContent.startsWith("P");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.pinBranch      = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.locatorArea    = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				String temp    = messageContent.substring(0,messageContent.indexOf(";") + 1);  
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0).trim();    
				StaticStore.LogPrinter('i',">>>>>>>>>TempOFLocator:::::::::"+temp);
				String tmptxnid=temp.substring(temp.lastIndexOf(';'),temp.length());
				String tmpString=temp.substring(0,temp.lastIndexOf(';'));
				tmpString=temp.substring(0,tmpString.lastIndexOf(';'));
				temp=tmpString+tmptxnid;
				StaticStore.LogPrinter('i',">>>>>>>>>TempOFLocator"+temp);
				StaticStore.midlet.atmSearch      = StaticStore.midlet.getSplittedValues(temp,1,'#',true,StaticStore.midlet.atmSearch);
				StaticStore.LogPrinter('i',"StaticStore.midlet.atmSearch >>>"+Arrays.deepToString(StaticStore.midlet.atmSearch));
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();

				intent = StaticStore.midlet.getAtmAddresses(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","ATM");
			}
		}else if(messageContent.startsWith("1L")){
			if(messageContent.startsWith("1L00")){
				messageContent = messageContent.substring(5).trim();
				StaticStore.isPincode      = messageContent.startsWith("P");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.pinBranch      = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();                    
				StaticStore.midlet.locatorArea    = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				String temp    = messageContent.substring(0,messageContent.indexOf(";") + 1);                    
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.branchSearch   = StaticStore.midlet.getSplittedValues(temp,1,'#',true,StaticStore.midlet.branchSearch);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getBranchSearch(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Branch");
			}
		}else if(messageContent.startsWith("EL")){
			if(messageContent.startsWith("EL00")){
				StaticStore.eLobbyLocationFlag = false;
				messageContent = messageContent.substring(5).trim();
				StaticStore.isPincode      = messageContent.startsWith("P");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.pinBranch      = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();                    
				StaticStore.midlet.locatorArea    = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				String temp    = messageContent.substring(0,messageContent.indexOf(";") + 1);                    
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				temp          += messageContent.substring(0,messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.eLobbySearch   = StaticStore.midlet.getSplittedValues(temp,1,'#',true,StaticStore.midlet.eLobbySearch);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.geteLobbySearch(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","E-Lobby");
			}
		}else if(messageContent.startsWith("AS")){
			if(messageContent.startsWith("AS00")){
				String temp      = messageContent.substring(5);
				messageContent   = temp;
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airSource        = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airDestination   = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airJourneyDate   = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airJourneyTime   = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airTotalTickets  = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airTotalAdults   = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airTotalChildren = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airTotalInfants  = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.airFlightClass   = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				/*Here we removed all previous leg values and getting only new values*/
				messageContent = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent += ";"+temp;
				StaticStore.midlet.airlineArray = StaticStore.midlet.getSplittedValues(messageContent,3,'#',true,StaticStore.midlet.airlineArray);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getAirlineList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Booking & Payment");
			}
		}else if(messageContent.startsWith("A1")){
			if(messageContent.startsWith("A100")){                	
				messageContent   = messageContent.substring(5).trim();
				StaticStore.midlet.airSource        = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airDestination   = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airJourneyDate   = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airTotalTickets  = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airTotalAdults   = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airTotalChildren = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airTotalInfants  = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airFlightClass   = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent   = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.airlineDispMsg   = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.airlineDispMsg   = StaticStore.midlet.airlineDispMsg.replace('*',';');
				StaticStore.midlet.airlineDispMsg   = StaticStore.midlet.airSource + ";" + StaticStore.midlet.airDestination + ";" + StaticStore.midlet.airTotalTickets + ";" + StaticStore.midlet.airTotalAdults +
				";" + StaticStore.midlet.airTotalChildren + ";" + StaticStore.midlet.airTotalInfants + ";" + StaticStore.midlet.airFlightClass + ";" + StaticStore.midlet.airlineDispMsg;                  
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",StaticStore.midlet.airlineDispMsg);
				intent.putExtra("formName", "A100");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Booking & Payment");
			}
		}else if(messageContent.startsWith("AL")){
			if(messageContent.startsWith("AL00")){
				messageContent = messageContent.substring(5).trim();
				StaticStore.isPincode      = messageContent.startsWith("P");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.pinBranch      = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				if(messageContent.substring(messageContent.lastIndexOf(';')+1).equals("Y")){
					StaticStore.midlet.moreFlag = true;
				}else {
					StaticStore.midlet.moreFlag = false;
				}
				StaticStore.LogPrinter('e', "ATM Issue messageContent -->"+messageContent);
				StaticStore.midlet.locatorArea    = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.LogPrinter('e', "ATM Issue messageContent 12-->"+messageContent);
				StaticStore.LogPrinter('e', "ATM Issue -->"+messageContent);
				StaticStore.midlet.atmSearch      = StaticStore.midlet.getSplittedValues(messageContent,5,'#',true,StaticStore.midlet.atmSearch);
				StaticStore.midlet.inResponseInbox =  false;
				intent = StaticStore.midlet.getAtmAddresses(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","ATM");
			}
		}
		return intent;
	}
	   
	public Intent AR_AC_R1_R2_E5_E6_Q1_Q2_Q3_SU_NU_2O_3O_4O_5O(Context context , String messageContent) throws Exception{
		if(messageContent.startsWith("AR")){
			if(messageContent.startsWith("AR00")){                    
				StaticStore.midlet.airlineOrderDetail = messageContent.substring(5).split("\\;");
				StaticStore.midlet.txnID = messageContent.substring(messageContent.toUpperCase().indexOf("TXNID:") + 6);
				for(int i = 0; i < 2;i++){
					messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				}
				messageContent=messageContent.substring(5);
				String temp = messageContent;
				temp = temp.substring(temp.indexOf(';')+1).trim();
				messageContent=messageContent.substring(0,messageContent.indexOf(';')).trim();
				messageContent = messageContent+" "+temp;

				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "AR00");
				intent.putExtra("title","Booking & Payment");  
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Booking & Payment");
			}
		}else if(messageContent.startsWith("AC")){
			if(messageContent.startsWith("AC00")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "AC00");
				intent.putExtra("title","Booking & Payment");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Booking & Payment");
			}
		}else if(messageContent.startsWith("R1")){
			if(messageContent.startsWith("R100")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "R100");
				intent.putExtra("title","Payment Only");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Payment Only");
			}
		}else if(messageContent.startsWith("R2")){
			if(messageContent.startsWith("R200")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "R200");
				intent.putExtra("title","Booking & Payment");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Booking & Payment");
			}
		}else if(messageContent.startsWith("E5")){
			//                if(messageContent.startsWith("E500")){
			//
			//                	 
			//                	 StaticStore.midlet.enableFlag = messageContent.substring(5,26);
			//                	 intent = new Intent(context,EnableDisable.class);
			//                     
			//                     
			//                }else{
			//                	intent = new Intent(context , DisplayableView.class);
			//                	intent.putExtra("response",messageContent.substring(5));
			//                 	intent.putExtra("formName", "DEFAULT");
			//                 	intent.putExtra("title","Enable/Disable Transaction");
			//                }
		}else if(messageContent.startsWith("E6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Enable/Disable Transaction");
		}else if(messageContent.startsWith("Q1")){
			if(messageContent.startsWith("Q100")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.bankList = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'~',true,StaticStore.midlet.bankList);
				intent = new Intent();
				intent = StaticStore.midlet.getBankList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Partner Bank Registration");
			}
		}else if(messageContent.startsWith("Q2")){
			if(messageContent.startsWith("Q200")){
				for(int i = 0; i < 2;i++){
					messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				}
				
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "Q200");
				intent.putExtra("title","Add Beneficiary");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");
			}
		}else if(messageContent.startsWith("Q3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
		}else if(messageContent.startsWith("SU") || messageContent.startsWith("NU")){
			if(messageContent.startsWith("SU00") || messageContent.startsWith("NU00")){
				
				intent = new Intent(context , DisplayableView.class);
				String msgsu;
				if(StaticStore.IsPermitted)
					msgsu = "You have signed up successfully. \n Click Proceed to log in.";
				else
					msgsu = "You have signed up successfully. \n Click Proceed to create your own login password.";

				intent.putExtra("response",msgsu); //messageContent.substring(5)
				intent.putExtra("formName", "SU00_NU00");
				StaticStore.initialFlag= false;
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				if(StaticStore.tagType == "APSU"){
				intent.putExtra("title","Debit Card");
				}else{
					intent.putExtra("title","Net Banking");
				}
				StaticStore.initialFlag= false;
			}
			
		}else if(messageContent.startsWith("2O")){
			if(messageContent.startsWith("2O00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.offer2OList = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.offer2OList);
				intent = new Intent();
				intent = StaticStore.midlet.getOffer2OList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Offers");
			}
		}else if(messageContent.startsWith("3O")){
			if(messageContent.startsWith("3O00")){
				//AP3O00;CC;DN*Dining#SP*Shopping ;001;Y;TXNID:23232333133;BID:C504594 
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5);
				StaticStore.offerFirstSubCategory = messageContent.substring(0,messageContent.indexOf(';'));                    
				messageContent = messageContent.substring(messageContent.indexOf(';') + 1);
				StaticStore.midlet.offer3OList = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.offer3OList);
				intent = new Intent();
				intent = StaticStore.midlet.getOffer3OList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Offers");
			}
		}else if(messageContent.startsWith("4O")){
			if(messageContent.startsWith("4O00")){
				//AP4O00;CC;DN;B01*BBQ Nation#SP*Spice restaurant ;001;Y;TXNID:23232333133;BID:C504594 
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5);
				StaticStore.offerFirstSubCategory = messageContent.substring(0,messageContent.indexOf(';'));                    
				messageContent = messageContent.substring(messageContent.indexOf(';') + 1);
				StaticStore.offerSecondSubCategory = messageContent.substring(0,messageContent.indexOf(';'));                    
				messageContent = messageContent.substring(messageContent.indexOf(';') + 1);
				StaticStore.midlet.offer4OList = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.offer4OList);
				intent = new Intent();
				intent = StaticStore.midlet.getOffer4OList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Offers");
			}
		}else if(messageContent.startsWith("5O")){
			if(messageContent.startsWith("5O00")){
				StaticStore.indexCtr = 1;
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.offer5OList = StaticStore.midlet.getSplittedValuesArray(messageContent.substring(5),2,"#",StaticStore.midlet.offer5OList);
				String dispMsg = "";
				StaticStore.midlet.dynamicDisplayableOffersArr = new String[StaticStore.midlet.offer5OList.length];
				for(int i = 0; i < StaticStore.midlet.offer5OList.length;i++){
					dispMsg = dispMsg + StaticStore.midlet.offer5OList[i][1] + ";";
					StaticStore.midlet.dynamicDisplayableOffersArr[i] = StaticStore.midlet.offer5OList[i][0];
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",dispMsg);
				intent.putExtra("formName", "Offers");
				intent.putExtra("title","Offers");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Offers");
			}

		}
		return intent;
	}
	//    
	public Intent Q4_Q5_Q6_Q7_Q8_Q9_QA_QF_T1_T2_TF(Context context , String messageContent)throws Exception{
		if(messageContent.startsWith("Q4")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Partner Bank Registration");
		}else if(messageContent.startsWith("Q5")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","VMT Registration");
		}else if(messageContent.startsWith("Q6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","VMT Registration");
		}else if(messageContent.startsWith("Q7")){
			if(messageContent.startsWith("Q700")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.neftPaymentFlag = messageContent.substring(5).trim().toUpperCase().startsWith("P");
				StaticStore.midlet.neftDeregFlag   = messageContent.substring(5).trim().toUpperCase().startsWith("D");
				StaticStore.midlet.neftRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'#',true,StaticStore.midlet.neftRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getNeftRegList(context);
			}else{
				//StaticStore.midlet.neftPaymentFlag = messageContent.substring(5).trim().toUpperCase().startsWith("P");
				// StaticStore.midlet.neftDeregFlag   = messageContent.substring(5).trim().toUpperCase().startsWith("D");
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				if(StaticStore.midlet.neftPaymentFlag)
				{                  
					intent.putExtra("title","Pay Beneficiary");
				} else {                       
					intent.putExtra("title","Delete Beneficiary");
				}

			}
		}else if(messageContent.startsWith("Q8")){
			StaticStore.midlet.neftRegList = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
		}else if(messageContent.startsWith("Q9")){
			if(messageContent.startsWith("Q900")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.partnerPaymentFlag = messageContent.substring(5).trim().startsWith("P");
				StaticStore.midlet.partnerDergFlag  = messageContent.substring(5).trim().toUpperCase().startsWith("D");
				StaticStore.midlet.partnerRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'~',true,StaticStore.midlet.partnerRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getPartnerRegList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Partner Bank Registered List");
			}
		}else if(messageContent.startsWith("QA")){
			StaticStore.midlet.partnerRegList = null; 
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Partner Bank Fund Transfer");
		}else if(messageContent.startsWith("QF")){
			StaticStore.midlet.partnerRegList = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("T1")){
			if(messageContent.startsWith("T100")){
				StaticStore.midlet.inResponseInbox =  false;
				String trimedMSG = "";
				StaticStore.midlet.templeSearchStr = messageContent.substring(messageContent.indexOf(";") + 1);
				trimedMSG       = StaticStore.midlet.templeSearchStr;
				StaticStore.midlet.templeSearchStr = StaticStore.midlet.templeSearchStr.substring(0,StaticStore.midlet.templeSearchStr.indexOf(";"));
				StaticStore.midlet.temples         = StaticStore.midlet.getSplittedValues(trimedMSG.substring(trimedMSG.indexOf(";") + 1),3,'#',true,StaticStore.midlet.temples);
				intent = new Intent();
				intent = StaticStore.midlet.getTempleList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Donation");
			}
		}else if(messageContent.startsWith("T2")){
			if(messageContent.startsWith("T200")){
				String trimedMSG = "";
				StaticStore.midlet.templeID = messageContent.substring(messageContent.indexOf(";") + 1);
				trimedMSG       = StaticStore.midlet.templeID;
				StaticStore.midlet.templeID = StaticStore.midlet.templeID.substring(0,StaticStore.midlet.templeID.indexOf(";"));
				StaticStore.LogPrinter('i',"BEFORE"+StaticStore.midlet.nextStartRecNumber);
				StaticStore.midlet.templeSchemes = StaticStore.midlet.getSplittedValues(trimedMSG.substring(trimedMSG.indexOf(";") + 1),3,'#',true,StaticStore.midlet.templeSchemes);                   
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getTempleSchemes(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Donation");
			}
		}else if(messageContent.startsWith("TF")){
				StaticStore.LogPrinter('i',">>>>>>>>>>>msg"+messageContent);
				if(messageContent.startsWith("TF00")){
					StaticStore.LogPrinter('i',":::::::::::::::::::+StaticStore.indexBeforeAccTypeInitiation"+StaticStore.indexBeforeAccTypeInitiation);
					String accTypeData = "";
					messageContent = messageContent.substring(5);
					messageContent = messageContent.substring(messageContent.indexOf(';')+1);
					accTypeData = messageContent.substring(0,messageContent.indexOf(';'));
					messageContent = messageContent.substring(messageContent.indexOf(';')+1);
					StaticStore.midlet.moreFlag = messageContent.substring(0,messageContent.length()).trim().equals("Y");
					 StaticStore.LogPrinter('i',"messageContent moreFlag--> "+StaticStore.midlet.moreFlag);
					StaticStore.accTypeCompleteData += accTypeData+"#";
					StaticStore.midlet.nextStartRecNumber = (StaticStore.midlet.getCharCount(StaticStore.accTypeCompleteData,'#')+1)+"";
					if(StaticStore.midlet.moreFlag){
						StaticStore.index = 220;
						StaticStore.tagType = "APTF";
						StaticStore.menuDesc[220] = new String []{"A/C Type Fetch","APTF",StaticStore.midlet.nextStartRecNumber,"","1","false","false","N"};
						intent =new Intent(context,DynamicCanvas.class);
					}else{
						RmsStore.writeRecordStore(StaticStore.accTypeCompleteData,RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
						StaticStore.accTypeCompleteData = "";
						StaticStore.LogPrinter('i',"StaticStore.indexBeforeAccTypeInitiation  --> "+StaticStore.indexBeforeAccTypeInitiation);
						if(StaticStore.indexBeforeAccTypeInitiation == -1){
							StaticStore.LogPrinter('i',"Came inside alert --> "+StaticStore.indexBeforeAccTypeInitiation);
							StaticStore.indexCtr-- ;
							StaticStore.TF00NEFTAlert = true;
							intent = StaticStore.midlet.getOtherFTOptions("NEFT",context);
							
						}else if(StaticStore.indexBeforeAccTypeInitiation == -2){
							//APK1
							StaticStore.fromAccountList = true;
							StaticStore.menuDesc[220] =new String[] {};
							StaticStore.menuDesc[220] = new String []{"Instant Pay","APK1;Y",StaticStore.mPINString,"Enter Beneficiary A/C No.","","Enter Beneficiary IFS Code","Enter Amount (Rs.)","Enter Remarks","4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-10-ND-N-N","0-25-AN-N-N","6","false","false","N"};
							StaticStore.tagType = "APK1";
							StaticStore.index = 220;
							StaticStore.accpaymentFlag = true;
							intent =new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -3){
							//APK2
							StaticStore.menuDesc[220] =new String[] {};
							StaticStore.menuDesc[220] = new String []{"Add Beneficiary","APK2;Y",StaticStore.mPINString,"A/C No.","","IFS Code","Nickname","4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-20-ANW-N-N","5","false","false","Y"};
							StaticStore.tagType = "APK2";
							StaticStore.index = 220;
							StaticStore.accpaymentFlag = true;
							intent =new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -4){
							//AP1U
							StaticStore.menuDesc[220] =new String[] {};
							StaticStore.menuDesc[220]  = new String[] {
									"Instant Pay", "AP1U;Y",StaticStore.mPINString,"Beneficiary AADHAAR No.","","Amount (Rs.)","Remarks",
									"4-4-N-Y-Y","12-12-N-N-N","","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"
							};
							StaticStore.tagType = "AP1U";
							StaticStore.accpaymentFlag = true;
							StaticStore.index = 220;
							intent =new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -5){
							//AP1U
							StaticStore.menuDesc[220] =new String[] {};
							StaticStore.menuDesc[220]  = new String[] {
									"Add Beneficiary", "AP2U;Y",StaticStore.mPINString,"Beneficiary AADHAAR No.","","Nickname",
									"4-4-N-Y-Y","12-12-N-N-N","","1-20-ANW-N-N","4","true","true","N"};

							StaticStore.tagType = "AP2U";
							StaticStore.accpaymentFlag = true;
							StaticStore.index = 220;
							intent =new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -6){
							//AP1USF //mid: 12866
							StaticStore.menuDesc[220] = new String[] {
									"Instant Pay", "APQT;Y",StaticStore.mPINString,"Beneficiary A/C No.","","Amount (Rs.)","Remarks",
									"4-4-N-Y-Y","15-15-N-N-N","","1-10-ND-N-N","0-25-AN-N-N","5","true","true","N"};
							StaticStore.index = 220;
							StaticStore.tagType = "APQT";

							StaticStore.accpaymentFlag = true;
							intent =new Intent(context,
									DynamicCanvas.class);

						}else if(StaticStore.indexBeforeAccTypeInitiation == -7){
							//AP1U
							StaticStore.menuDesc[220] = new String[]{
									"Instant Pay", "APQN;Y",StaticStore.mPINString,"Beneficiary A/C No.","","Beneficiary IFS Code","Amount (Rs.)","Remarks",
									"4-4-N-Y-Y","6-19-ANW-N-N","","11-11-ANW-N-N","1-7-N-N-N","0-25-AN-N-N","6","true","true","N"};
							StaticStore.index = 220;
							StaticStore.tagType = "APQN";
							StaticStore.LogPrinter('i',"<><><><><><>ok");
							StaticStore.accpaymentFlag = true;
							StaticStore.index = 220;
							intent =new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -8){
							
							StaticStore.tagType = "AP6U";
							StaticStore.menuDesc[220] = new String [] {"Beneficiary Details","AP6U;Y",StaticStore.mPINString,"Search Beneficiary","001","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
							StaticStore.index = 220;
							StaticStore.FromListScreen = false;
							intent = new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -9){
							StaticStore.menuDesc[220] =new String[] {};
							StaticStore.menuDesc[220] = new String []{"Beneficiary Details","AP4W;Y",StaticStore.mPINString,"Search Beneficiary","1","4-4-N-Y-Y","0-20-ANW-N-N","","3","false","false","N"};
							StaticStore.tagType = "AP4W";
							StaticStore.index = 220;
							StaticStore.FromListScreen = false;
							intent = new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -10){
							StaticStore.index = 187;
							StaticStore.FromListScreen = false;
							intent = new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -11){
							StaticStore.recCount = 0;
							StaticStore.index = 213;
							StaticStore.FromListScreen = false;
							intent = new Intent(context,
									DynamicCanvas.class);
						}else if(StaticStore.indexBeforeAccTypeInitiation == -12){
							StaticStore.secondMyOwnAccFlag = false;
							intent = StaticStore.midlet.get_accTypeMenu(StaticStore.context,275,"M2A");
						}else{//M2A
							StaticStore.index = StaticStore.indexBeforeAccTypeInitiation;
							intent = new Intent(context,
									DynamicCanvas.class);
						}
					}
				}else{
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Transaction Failure");
				}
		}
		return intent;
	}
	
	public Intent T3_T4_T5_F1_F4_F3_F2_F6_RM_C1(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("T3")){
			if(messageContent.startsWith("T300")){
				String trimedMSG = "";
				StaticStore.midlet.templeID         = messageContent.substring(messageContent.indexOf(";") + 1);
				trimedMSG        = StaticStore.midlet.templeID;
				StaticStore.midlet.templeID         = StaticStore.midlet.templeID.substring(0,StaticStore.midlet.templeID.indexOf(";"));
				StaticStore.midlet.templeSchemeID   = trimedMSG.substring(trimedMSG.indexOf(";") + 1);
				trimedMSG        = StaticStore.midlet.templeSchemeID;
				StaticStore.midlet.templeSchemeID   = StaticStore.midlet.templeSchemeID.substring(0,StaticStore.midlet.templeSchemeID.indexOf(";"));
				StaticStore.midlet.templeSubSchemes = StaticStore.midlet.getSplittedValues(trimedMSG.substring(trimedMSG.indexOf(";") + 1),2,'#',true,StaticStore.midlet.templeSubSchemes);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getTempleSubSchemeList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Donation");
			}
		}else if(messageContent.startsWith("T4")){
			if(messageContent.startsWith("T400")){
				String tempStr = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.menuDesc[97][9] = messageContent.substring(messageContent.indexOf("TXNID:") + 6);
				StaticStore.menuDesc[97][3] = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr                     = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.templeName                    = tempStr.substring(0,tempStr.indexOf(";"));
				tempStr                     = tempStr.substring(tempStr.indexOf(";") + 1).trim();                    
				StaticStore.menuDesc[97][4] = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();                    
				StaticStore.menuDesc[97][5] = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr                     = tempStr.substring(tempStr.indexOf(";") + 1).trim();   
				StaticStore.midlet.schemeName                    = tempStr.substring(0,tempStr.indexOf(";"));
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();   
				StaticStore.menuDesc[97][6] = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();                    
				StaticStore.menuDesc[97][7] = tempStr.substring(0,tempStr.indexOf(";")).trim();
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();  
				StaticStore.midlet.subSchemeName                 = tempStr.substring(0,tempStr.indexOf(";"));
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.templeMinAmount               = Integer.parseInt(tempStr.substring(0,tempStr.indexOf(";")));
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.templeDenFlag                 = tempStr.substring(0,tempStr.indexOf(";")).trim().toUpperCase().equals("Y");
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1).trim();
				StaticStore.midlet.templeDenomination            = Integer.parseInt(tempStr.substring(0,tempStr.indexOf(";")));
				tempStr                       = tempStr.substring(tempStr.indexOf(";") + 1);
				StaticStore.midlet.templeDate                    = tempStr.substring(0,tempStr.indexOf(";"));
				if(StaticStore.menuDesc[97][4].trim().toUpperCase().equals("Y") && StaticStore.menuDesc[97][6].trim().toUpperCase().equals("Y")){
					tempStr = StaticStore.midlet.templeName + ";" + StaticStore.midlet.schemeName + ";" + StaticStore.midlet.subSchemeName + ";" +(StaticStore.midlet.templeMinAmount == 0? "NA":"Rs."+StaticStore.midlet.templeMinAmount)+ ";" +(StaticStore.midlet.templeDenFlag ? "Rs."+StaticStore.midlet.templeDenomination : "NA")+ ";" + StaticStore.midlet.templeDate;                       
				}else if(StaticStore.menuDesc[97][4].trim().toUpperCase().equals("Y") && StaticStore.menuDesc[97][6].trim().toUpperCase().equals("N")){
					tempStr = StaticStore.midlet.templeName + ";" + StaticStore.midlet.schemeName + ";" + (StaticStore.midlet.templeMinAmount == 0? "NA":"Rs."+StaticStore.midlet.templeMinAmount)+ ";" +(StaticStore.midlet.templeDenFlag ? "Rs."+StaticStore.midlet.templeDenomination : "NA")+ ";" + StaticStore.midlet.templeDate;                        
				}else if(StaticStore.menuDesc[97][4].trim().toUpperCase().equals("N") && StaticStore.menuDesc[97][6].trim().toUpperCase().equals("N")){
					tempStr = StaticStore.midlet.templeName + ";" + (StaticStore.midlet.templeMinAmount == 0? "NA":"Rs."+StaticStore.midlet.templeMinAmount)+ ";" +(StaticStore.midlet.templeDenFlag ? "Rs."+StaticStore.midlet.templeDenomination : "NA")+ ";" + StaticStore.midlet.templeDate;                        
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "Temple");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Donation");
			}
		}else if(messageContent.startsWith("T5")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Donation");
		}else if(messageContent.startsWith("F1")){
			if(messageContent.startsWith("F100")){
				StaticStore.midlet.withinBenMobList = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'#',true,StaticStore.midlet.withinBenMobList);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.withInMobReg(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Beneficiary");
			}
		}else if(messageContent.startsWith("F4")){
			if(messageContent.startsWith("F400")){
				StaticStore.midlet.withinBenDeregList = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'~',true,StaticStore.midlet.withinBenDeregList);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getDeregFTList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Within Bank Deregistration");
			}
		}else if(messageContent.startsWith("F3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Within Bank Deregistration");
		}else if(messageContent.startsWith("F2")){
			if(messageContent.startsWith("F200")){
				StaticStore.midlet.withinBenAccList = StaticStore.midlet.getSplittedValues(messageContent.substring(7),1,'#',true,StaticStore.midlet.withinBenAccList);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.withInAccReg(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Beneficiary");
			}
		}else if(messageContent.startsWith("F6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","A/C Fund Transfer List");
		}else if(messageContent.startsWith("RM")){
			if(messageContent.startsWith("RM00")){
				String temp = messageContent.substring(5);
				StaticStore.m2mAccHolderName = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.m2mRegMobileNumber = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.m2mregMnemonicName = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.m2mAccounts = StaticStore.midlet.getSplittedValues(temp,1,'#',true,StaticStore.midlet.m2mAccounts);
				StaticStore.LogPrinter('i',">>>>>>>account length"+StaticStore.midlet.m2mAccounts.length);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getM2MAccountList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");
			}
		}else if(messageContent.startsWith("C1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
		}
		return intent;
	}

	public Intent C2_RA_N1_1N_DN_N2_CS_SS_SR_SP(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("C2")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
		}else if(messageContent.startsWith("RA")){
			if(messageContent.startsWith("RA00")){
				String temp = messageContent.substring(5);
				String frontMsg = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";")+1);
				StaticStore.midlet.m2mSelectedAccNumber = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";")+1);
				temp = frontMsg+";"+StaticStore.midlet.maskedAccNumber(StaticStore.midlet.m2mSelectedAccNumber)+";"+temp;
				StaticStore.LogPrinter('i',"!!!!!!!!!!!!OriginalMsg"+temp);

				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "RA00");
				intent.putExtra("title","Add Beneficiary");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");
			}
		}else if(messageContent.startsWith("N1")){
			//changes for displayable as in java
			StaticStore.midlet.billpayNotifyDet = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'*',true,StaticStore.midlet.billpayNotifyDet);
			StaticStore.midlet.billNotifyMessage        = StaticStore.midlet.billpayNotifyDet[5][0] + ";" + StaticStore.midlet.billpayNotifyDet[11][0] + ";" + StaticStore.midlet.billpayNotifyDet[12][0] + ";"
			+ StaticStore.midlet.billpayNotifyDet[13][0] + "; Rs." + StaticStore.midlet.billpayNotifyDet[14][0] + "; Rs."+ StaticStore.midlet.billpayNotifyDet[15][0] + "; Rs." + StaticStore.midlet.billpayNotifyDet[16][0]
			                                                                                                                                                                                                              + ";TXNID:"+StaticStore.midlet.txnID;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",StaticStore.midlet.billNotifyMessage);
			intent.putExtra("formName", "N100");
			intent.putExtra("title","Bill Payments Notification");
		}else if(messageContent.startsWith("1N")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Registered Bill Payment");
		}else if(messageContent.startsWith("DN")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Bill Pay-Defer");
		}else if(messageContent.startsWith("N2")){
			messageContent = messageContent.replace('|',';');
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Fees Notification");
		}else if(messageContent.startsWith("CS")){
			if(messageContent.startsWith("CS00")){
				StaticStore.midlet.unRegisteredCorpLists = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'~',true,StaticStore.midlet.unRegisteredCorpLists);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getUnregCorporateList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Corporate search");
			}
		}else if(messageContent.startsWith("SS")){
			if(messageContent.startsWith("SS00")){
				messageContent = messageContent.substring(5);                   
				StaticStore.midlet.cftCorpId = messageContent.substring(0,messageContent.indexOf(";")).trim();                    
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);                           
				StaticStore.midlet.unRegisteredSubCorpLists = StaticStore.midlet.getSplittedValues(messageContent,1,'~',true,StaticStore.midlet.unRegisteredSubCorpLists);                                        
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.getUnregSubCorporateList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Sub-corporate");
			}
		}else if(messageContent.startsWith("SR")){
			if(messageContent.startsWith("SR00")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","CFT Registration");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","CFT Registration");
			}
		}else if(messageContent.startsWith("SP")){            
			if(messageContent.startsWith("SP00")){
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1).trim();                    
				StaticStore.midlet.modeFlag            = messageContent.substring(0,messageContent.indexOf(";")).toUpperCase().trim();
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.displaySearchString = messageContent.substring(0,messageContent.indexOf(";")).trim();                    
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.allRegList          = StaticStore.midlet.getSplittedValues(messageContent,2,'~',true,StaticStore.midlet.allRegList);                    
				if(StaticStore.midlet.modeFlag.equals("P")){
					StaticStore.midlet.cftHeader = "Payment";
				}else if(StaticStore.midlet.modeFlag.equals("S")){
					StaticStore.midlet.cftHeader = "SI-Registration";
				}else if(StaticStore.midlet.modeFlag.equals("D")){
					StaticStore.midlet.cftHeader = "Delete Beneficiary";
				}else if(StaticStore.midlet.modeFlag.equals("F")){
					StaticStore.midlet.cftHeader = "Forgot Password";
				}
				intent = new Intent();
				intent = StaticStore.midlet.getRegisteredCFTList(StaticStore.midlet.cftHeader,StaticStore.midlet.modeFlag,context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title",StaticStore.midlet.cftHeader);
			}
		}
		return intent;
	}
	  
	public Intent PL_PS_SI_DR_CF_Z4_ZP_Z6_Z7(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("PL")){
			if(messageContent.startsWith("PL00")){
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1).trim();                    
				StaticStore.midlet.modeFlag            = messageContent.substring(0,messageContent.indexOf(";")).toUpperCase().trim();
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.cftCorpId           = messageContent.substring(0,messageContent.indexOf(";")).trim();
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.cftSubCorpId        = messageContent.substring(0,messageContent.indexOf(";")).trim();
				messageContent      = messageContent.substring(messageContent.indexOf(";") + 1);                    
				StaticStore.midlet.cftDistributorList  = StaticStore.midlet.getSplittedValues(messageContent,3,'~',true,StaticStore.midlet.cftDistributorList);     
				if(StaticStore.midlet.modeFlag.equals("P")){
					StaticStore.midlet.cftHeader = "Payment";
				}else if(StaticStore.midlet.modeFlag.equals("S")){
					StaticStore.midlet.cftHeader = "SI-Registration";
				}else if(StaticStore.midlet.modeFlag.equals("D")){
					StaticStore.midlet.cftHeader = "Delete Beneficiary";
				}else if(StaticStore.midlet.modeFlag.equals("F")){
					StaticStore.midlet.cftHeader = "Forgot Password";
				}
				intent = new Intent();
				intent = StaticStore.midlet.getCFTDistributorList(StaticStore.midlet.cftHeader,context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title",StaticStore.midlet.cftHeader);
			}
		}else if(messageContent.startsWith("PS")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Payment");
		}else if(messageContent.startsWith("SI")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","SI-Registration");
		}else if(messageContent.startsWith("DR")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Deregistration");
		}else if(messageContent.startsWith("CF")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Forgot Password");
		}else if(messageContent.startsWith("Z400")){  
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Application Upgrade");
		}else if(messageContent.startsWith("ZP")){
			if(messageContent.startsWith("ZP00")){  
				messageContent = messageContent.substring(5).trim();
				if(messageContent.toUpperCase().startsWith("Y")){
					messageContent = messageContent.substring(messageContent.indexOf(";")+1).trim(); 
					StaticStore.wapUrl=messageContent.substring(0,messageContent.indexOf(";"));
					StaticStore.LogPrinter('i',"******StaticStore.wapUrl********"+StaticStore.wapUrl);
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response","Kindly press confirm button to open the WAP page.");
					intent.putExtra("formName", "WAP_ZP");
					intent.putExtra("title","WAP");
//					StaticStore.dialog.cancel();
//					finish();
//					Uri uri = Uri.parse(StaticStore.wapUrl);
//					intent = new Intent(Intent.ACTION_VIEW, uri);
//					System.gc();
					
				}else{

					messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
					//                      messageContent = "Currently WAP service is not available;"+messageContent;
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",messageContent);
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","WAP");
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","WAP");
			}

		}else if(messageContent.startsWith("Z6")){
			if(messageContent.startsWith("Z600")){                    
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.emailId = messageContent.substring(0,messageContent.indexOf(";")).trim();
				if(StaticStore.midlet.emailId.equals("")){
					messageContent = "Your E-mail ID is not configured for Mobile Banking Services, click Update to configure E-mail ID" + messageContent;                       
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "Z600");
				intent.putExtra("title","Configure/Update E-mail ID");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Configure/Update E-mail ID");
			}
		}
		return intent;
	}
	  
	public Intent Z3_Z5_Y100_1Y_2Y_3Y_CA_P5_S1_S2(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("Z3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Configure/Update E-mail ID");
		}else if(messageContent.startsWith("Z5")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Refer a Friend");
		}else if(messageContent.startsWith("Y100")){
			messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
			String str     = messageContent;
			StaticStore.midlet.campaignId     = str.substring(0,str.indexOf(";"));
			str            = str.substring(str.indexOf(";") + 1);
			str            = str.substring(str.indexOf(";") + 1).trim();
			boolean isCampaign = str.startsWith("C");
			if(isCampaign){
				messageContent  = messageContent.substring(messageContent.indexOf(";") + 1);
				int i = 0;                   
				str = "";
				while(messageContent.indexOf(";") != -1){
					if(i == 1 || i == 3){
						messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
						i++;
						continue;                
					}
					str += messageContent.substring(0,messageContent.indexOf(";")) + ";";
					messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
					i++;
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",str+messageContent);
				intent.putExtra("formName", "Campaign");
				intent.putExtra("title","");
			}else{
				str            = messageContent.substring(messageContent.indexOf(";") + 1);
				messageContent = str.substring(0,str.indexOf(";"));
				str            = str.substring(str.indexOf(";") + 1);
				str            = str.substring(str.indexOf(";") + 1);
				str            = str.substring(str.indexOf(";") + 1);
				str            = str.substring(str.indexOf(";"));
				messageContent += str;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Promotional Campaign");
			}               
		}else if(messageContent.startsWith("1Y") || messageContent.startsWith("2Y") || messageContent.startsWith("3Y")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent);
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Promotional Campaign");
		}else if(messageContent.startsWith("CA")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Reactivate TMB mConnect");
		}else if(messageContent.startsWith("P5")){
			if(messageContent.startsWith("P500")){
				messageContent = messageContent.substring(5);
				String temp    = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.applicationPWD =StaticStore.midlet.hashingByMD5(messageContent.substring(0,messageContent.indexOf(";")).trim());
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();                  
				StaticStore.midlet.writeinMemory(context);                    
				temp = temp + ";" +messageContent;      
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Change Login Password");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Change Login Password");
			}
		}else if(messageContent.startsWith("S1")){
			if(messageContent.startsWith("S100")){
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.ifscBankSearchString = messageContent.substring(0,messageContent.indexOf(";")).trim();
				messageContent = messageContent.substring(messageContent.indexOf(";")+1).trim();
				StaticStore.midlet.IFSCList = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true, StaticStore.midlet.IFSCList);
				intent = new Intent();
				intent = StaticStore.midlet.getIfscBankList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","IFSC Search");
			}
		}else if(messageContent.startsWith("S2")){
			if(messageContent.startsWith("S200")){

				messageContent = messageContent.substring(5).trim();     
				StaticStore.midlet.ifscBranchSearchString = messageContent.substring(0,messageContent.indexOf(";")).trim();
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);                    
				StaticStore.ifscBankID=messageContent.substring(0,messageContent.indexOf(";")).trim();  
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);                                                            
				StaticStore.midlet.locationList=StaticStore.midlet.getSplittedValues(messageContent.trim(),1,'#',true, StaticStore.midlet.locationList);
				intent = new Intent();
				intent = StaticStore.midlet.getBankLocationList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","IFSC Search");
			}
		}
		return intent;
	}

	public Intent NS_HL_HC_L0_L4_NC_OC_RS_PB_CP_MP(Context context,String messageContent) throws Exception{
		if(messageContent.startsWith("NS")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Beneficiary Payment Status");
		}else if(messageContent.startsWith("HL")){
			if(messageContent.startsWith("HL00"))
			{
				messageContent = messageContent.substring(5).trim();     
				String count = messageContent.substring(0,messageContent.indexOf(";")).trim();
				//messageContent = messageContent.substring(messageContent.indexOf(";") + 1);                    
				StaticStore.midlet.CardhotlistDet = StaticStore.midlet.getSplittedValues(messageContent.trim(),4,'#',true, StaticStore.midlet.CardhotlistDet);
				intent = StaticStore.midlet.getCardHot_CARD_List(context);
			}else
			{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Hotlist Debit Card");
			}
		}else if(messageContent.startsWith("HC")){

			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Hotlist Debit Card");
		}else if(messageContent.startsWith("L0")){

			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Hotlist Debit Card");
		}else if(messageContent.startsWith("L4")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Stop Cheque");
		}else if(messageContent.startsWith("NC")){
			if(messageContent.startsWith("NC00")){
				//NC00;1111111111111111111111111111111111111;0015045942122121454;CA;TXNID:110219040425;BID:C504594
				String temp = messageContent.substring(5);
				StaticStore.LogPrinter('i',">"+temp);
				serviceControl = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.accountNumbers[0] = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.accType[0]= temp.substring(0,temp.indexOf(";"));
				StaticStore.selectedAccNumber = StaticStore.accountNumbers[0];
				StaticStore.selectedAccType = StaticStore.accType[0];
				StaticStore.IsPersonalInfoGot = true;
				StaticStore.isForgotPWD       = false;
				StaticStore.IsPermitted = true;

				for(int sc=0;sc<serviceControl.length();sc++) {
					StaticStore.splittedServiceControl+=serviceControl.charAt(sc)+"~";
				}

				StaticStore.splittedServiceControl=StaticStore.splittedServiceControl.substring(0,StaticStore.splittedServiceControl.lastIndexOf('~'));
				StaticStore.midlet.serverControl(StaticStore.splittedServiceControl + "~","~");
				StaticStore.midlet.writeinMemory(context);
				StaticStore.midlet.writeAccountInMemory();
				intent = StaticStore.midlet.getCommunicationMode(context,63);
				StaticStore.LogPrinter('i',">>>>intent action on OU"+intent.getAction());
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Change mPIN");//Change mPIN
			}

		}else if(messageContent.startsWith("OC")){
			if(messageContent.startsWith("OC00") && StaticStore.IsPermitted){
				StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
				String temp = messageContent.substring(5);
				serviceControl = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.accountNumbers[0] = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.accType[0]= temp.substring(0,temp.indexOf(";"));
				StaticStore.selectedAccNumber = StaticStore.accountNumbers[0];
				StaticStore.selectedAccType = StaticStore.accType[0];
				StaticStore.IsPersonalInfoGot = true;
				StaticStore.isForgotPWD       = false;
				StaticStore.IsPermitted = true;
				for(int sc=0;sc<serviceControl.length();sc++) {
					StaticStore.splittedServiceControl+=serviceControl.charAt(sc)+"~";
				}
				StaticStore.splittedServiceControl=StaticStore.splittedServiceControl.substring(0,StaticStore.splittedServiceControl.lastIndexOf('~'));
				StaticStore.midlet.serverControl(StaticStore.splittedServiceControl + "~","~");
				StaticStore.midlet.writeinMemory(context);
				StaticStore.midlet.writeAccountInMemory();
				intent = StaticStore.midlet.getCommunicationMode(context,63);
				StaticStore.LogPrinter('i',">>>>intent action on OU"+intent.getAction());
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Activation");
			}

		}else if(messageContent.startsWith("RS")){
			if(messageContent.startsWith("RS00")){
				StaticStore.midlet.moreFlag = messageContent.substring(messageContent.lastIndexOf(';')-1).substring(0,1).trim().equals("Y");
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "status");
				intent.putExtra("title","Recharge Status Enquiry");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge Status Enquiry");
			}
		}else if(messageContent.startsWith("PB")){
			intent = new Intent(context , DisplayableView.class);
			if(messageContent.startsWith("PB00")){
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "vpBill");
			}else{
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","View Paid Bills");
			}
		}else if(messageContent.startsWith("CP") || messageContent.startsWith("MP")){
			if(messageContent.startsWith("CP00") || messageContent.startsWith("MP00") || messageContent.startsWith("CP08")){
				if(messageContent.startsWith("CP00")){
					StaticStore.midlet.initiatedCustomer = true;
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Customer Shopping");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Customer Shopping");
			}
		}
		return intent;
	}


	public Intent MO_1I_2I_3I_7L_7D_NL_4D_5L_5D_QM_QN_QT_M9(Context context,String messageContent) throws Exception{
		if(messageContent.startsWith("1I")){
			if(messageContent.startsWith("1I00")){
				String temp      = messageContent.substring(5);
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.MMID             = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.benMobNo             = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.benNicName             = temp.substring(0,temp.indexOf(";"));
				for(int i = 0; i < 2;i++){
					messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "1I00");
				intent.putExtra("title","");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Merchant");
			}
		}else if (messageContent.startsWith("2I")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Merchant");
		} else if(messageContent.startsWith("MO")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Generate OTP");             	
		}else if(messageContent.startsWith("3I")){
			if(messageContent.startsWith("3I00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.onlineFTPaymentFlag = true;
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.onlineFTRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),1,'#',true,StaticStore.midlet.onlineFTRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineFTRegList(context,"Merchant List",240);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Beneficiary");
			}
		}else if(messageContent.startsWith("7L")){
			if(messageContent.startsWith("7L00")){
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5);
				StaticStore.LogPrinter('i',"::::::::::messageContent 7L"+messageContent);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.onlineFTRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),1,'#',true,StaticStore.midlet.onlineFTRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineFTRegList(context,"Merchant List",241);
			}else{  
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Merchant");
			}
		}else if(messageContent.startsWith("7D")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Merchant");
		}else if(messageContent.startsWith("M9")){
			//D900;a;awe*TMB mConnect*9012000*9992929299;0001;N;TXNID:110217039377;BID:C504594 
			//GP00;SA*Savings Account#HL*Home Loan;001;Y;TXNID:110115023973;BID:C504594
			if(messageContent.startsWith("M900")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.impsDetails = StaticStore.midlet.getSplittedValues(messageContent,3,'#',true,StaticStore.midlet.impsDetails);
				intent = new Intent();
				intent = StaticStore.midlet.getMerchImpsDetails(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Merchant Details");
			}
		}else if(messageContent.startsWith("NL")){
			//        	 NL00;a;Aent*STATE BANK OF INDIA*ABERDEEN *135833333333333333*10*sbin0002001;001;N;;TXNID:110517041187;BID:C504594       
			if(messageContent.startsWith("NL00")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.LogPrinter('i',"Message content --> "+messageContent);
				StaticStore.midlet.neftDetails = StaticStore.midlet.getSplittedValues(messageContent,6,'#',true,StaticStore.midlet.neftDetails);
				StaticStore.LogPrinter('i',"Siva Test neftDetails-->"+Arrays.deepToString(StaticStore.midlet.neftDetails));
				intent = new Intent();
				intent = StaticStore.midlet.getNeftDetails(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("4D")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("5L")){
			if(messageContent.startsWith("5L00")){
				StaticStore.midlet.onlineFTPaymentFlag = false;
				messageContent = messageContent.substring(5);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.onlineFTRegList = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.onlineFTRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineFTRegList(context,"Beneficiary List",89);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		} else if (messageContent.startsWith("5D")) {
			intent = new Intent(context, DisplayableView.class);
			intent.putExtra("response", messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title", "Delete Beneficiary");
		} else if (messageContent.startsWith("QM")) {
			intent = new Intent(context, DisplayableView.class);
			intent.putExtra("response", messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title", "Instant Pay");
		} else if (messageContent.startsWith("QT")) {
			intent = new Intent(context, DisplayableView.class);
			intent.putExtra("response", messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title", "Instant Pay");
		} else if (messageContent.startsWith("QN")) {
			intent = new Intent(context, DisplayableView.class);
			intent.putExtra("response", messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title", "Instant Pay");
		}
		return intent;
	}
	public Intent BX_3S_BW_FC_FS_4S_Z8_GT_CM_DG(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("3S")){           
			if(messageContent.startsWith("3S00")){                    
				messageContent = messageContent.substring(5).trim();
				StaticStore.midlet.ifscBankName = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.midlet.ifscLocationName = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1).trim();
				StaticStore.LogPrinter('i',"messageContent   ==>  "+messageContent);
				StaticStore.midlet.ifscArray      = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.ifscArray);
				StaticStore.LogPrinter('i',"StaticStore.midlet.ifscArray ==> "+Arrays.deepToString(StaticStore.midlet.ifscArray));
				if(StaticStore.isSearchAndRegister){
					intent = new Intent();
					intent = StaticStore.midlet.getIFSCList(context);
				}else{
					intent = new Intent();
					intent = StaticStore.midlet.getIFSCList(context);
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","IFSC Search");
			}
		}else if(messageContent.startsWith("BX")){
			if(messageContent.startsWith("BX00")){                   
				StaticStore.midlet.billpayCustMneName = StaticStore.midlet.getSplittedValues(messageContent.substring(5),1,'#',true,StaticStore.midlet.billpayCustMneName);
				intent = new Intent();
				intent = StaticStore.midlet.getCustomerMneNameList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Biller");
			}
		}else if(messageContent.startsWith("BW")){
			if(messageContent.startsWith("BW00")){                    
				String[][] temp = null;
				String tempStr = "";
				messageContent = messageContent.substring(5).trim();                     
				StaticStore.billPayCustMneName = messageContent.substring(0,messageContent.indexOf(";"));
				tempStr = StaticStore.billPayCustMneName;
				StaticStore.midlet.dynamicBillpayRefArray = new String[StaticStore.midlet.getCharCount(messageContent,'#') + 2];
				temp = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";") + 1),2,'#',true,temp);
				StaticStore.midlet.dynamicBillpayRefArray[0] = "Nickname";
				for(int i = 0; i <temp.length; i++){
					StaticStore.midlet.dynamicBillpayRefArray[i + 1] = temp[i][0];
					tempStr =  tempStr + ";" + temp[i][1];
				}            
				StaticStore.LogPrinter('i',"dynamicBillpayRefArray  temp --> "+Arrays.deepToString(temp));
				StaticStore.LogPrinter('i',"dynamicBillpayRefArray  --> "+Arrays.deepToString(StaticStore.midlet.dynamicBillpayRefArray));

				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",tempStr);
				intent.putExtra("formName", "BW00");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Registered Bill Payment");
			}
		}else if(messageContent.startsWith("FC")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Register Feedback");
		}else if(messageContent.startsWith("FS")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Status Enquiry");
		}else if(messageContent.startsWith("4S")){
			if(messageContent.startsWith("4S00")){
				String temp = messageContent.substring(5);
				String[] sbarray = new String[5];
				for(int i = 0;i < sbarray.length;i++){
					if(i < sbarray.length - 1){
						StaticStore.LogPrinter('i',">>>iiii"+i);
						sbarray[i] = temp.substring(0,temp.indexOf(';'));
						temp = temp.substring(temp.indexOf(';') + 1);
					}else{
						sbarray[i] = temp;
					}                        
				}
				temp = "";
				for(int i = 0;i < sbarray.length;i++){
					if(i < sbarray.length - 1){
						temp += sbarray[i] + ";";
					}else{
						temp += sbarray[i]; 
					}
				}      
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "4S00");
				intent.putExtra("title","Bank Search");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Bank Search");
			}
		}else if(messageContent.startsWith("GT")){
			if(messageContent.startsWith("GT00")){
				StaticStore.IsGPRS = true;
				StaticStore.midlet.writeinMemory(context);
				if(StaticStore.isSettingsSelected){
					StaticStore.isSettingsSelected = false;
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Internet Check");
				}else{
					if(StaticStore.isOTPBuild){

						if(StaticStore.isFromVersionUpdate){
							StaticStore.isFromVersionUpdate = false;
							StaticStore.menuDesc[220] = new String []{"Version Update","APBV;N",StaticStore.buildVersion,"ARD","","","2","false","false","N"};
							StaticStore.tagType = "APBV";
							StaticStore.index = 220;
							intent =new Intent(StaticStore.context, DynamicCanvas.class);
						}else{
							if ( StaticStore.selectedLoginGridIndex == 0) {
								intent = StaticStore.midlet.getAbout(StaticStore.context);
							}else if ( StaticStore.selectedLoginGridIndex == 1) {
								if(StaticStore.IsPermitted) {
									StaticStore.index = 123;
									StaticStore.menuDesc[123][2] = "Login Password";
									StaticStore.menuDesc[123][3] = "4-4-N-Y-Y";
									intent = new Intent(StaticStore.context, DynamicCanvas.class);
								} else {
									StaticStore.menuDesc[114][2] = "BVD:"+StaticStore.buildVersion + "#" + StaticStore.mobileType + "#" + StaticStore.mobileScreenSize + "#" +StaticStore.mobileDetails;
									StaticStore.index = 114;
									StaticStore.FromListScreen = false;
									intent = new Intent(StaticStore.context, DynamicCanvas.class);
								}
							}else if ( StaticStore.selectedLoginGridIndex == 2) {
								intent = StaticStore.midlet.get_ProductsMenu(StaticStore.context);
							} else if ( StaticStore.selectedLoginGridIndex == 3) {
								StaticStore.index = 129;
								intent = new Intent(StaticStore.context, DynamicCanvas.class);
							}else if ( StaticStore.selectedLoginGridIndex == 4) {
								intent = StaticStore.midlet.getLocatorMenu(StaticStore.context);
							}else if ( StaticStore.selectedLoginGridIndex == 5) {
								StaticStore.menuDesc[220] = new String []{"Feedback","AP1F;N","001","","1","false","false","N"};
								StaticStore.tagType = "AP1F";
								StaticStore.index = 220;
								intent = new Intent(StaticStore.context,DynamicCanvas.class);
							}else if(StaticStore.selectedLoginGridIndex == 6){
								StaticStore.isForgotPassword = true;
								StaticStore.NoteForgotPWD = true;
								//if(StaticStore.isOTPBuild && StaticStore.SMS_AUTHENTICATION_GPRSMODE){
								if(StaticStore.isOTPBuild){
									StaticStore.date = new Date().getTime(); // By ABINAYA.J.A
									StaticStore.FromListScreen = false;
									StaticStore.menuDesc[220] = new String []{"OTP Generation","APOG",StaticStore.myMobileNo,"","1","false","false","Y"};
									StaticStore.tagType = "APOG";
									StaticStore.index = 220;
									intent = new Intent(StaticStore.context,DynamicCanvas.class);	
								}else{
									StaticStore.FromListScreen = true;
									StaticStore.index = 115;
									intent = new Intent(StaticStore.context, DynamicCanvas.class);
								}
							}
						}
					}else{
						String msg = "For secure banking, please allow application to send an SMS to validate your mobile number.";
						intent = new Intent(context, DisplayableView.class);
						intent.putExtra("response",msg);
						intent.putExtra("formName","alert");
					}
				}
			}else{
				if(StaticStore.isSettingsSelected){
					StaticStore.IsGPRS = false;
					StaticStore.isSettingsSelected = false;
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Internet Check");
				}else{
					String msg = "Connectivity could not be established. Please check your Internet connection and try again. To proceed with SMS mode, press Confirm.";
					intent = new Intent(context, DisplayableView.class);
					intent.putExtra("response",msg);
					intent.putExtra("formName","alert"); 
				}
			}
		}else if(messageContent.startsWith("CM")){
			if(messageContent.startsWith("CM00")){   
				if(StaticStore.isFromLoginScreen){
					intent = new Intent();
					intent = new Intent(context , GridScreenView.class);
				}else{
					StaticStore.IsGPRS = true;        
					StaticStore.midlet.writeinMemory(context);
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",messageContent.substring(5));
					intent.putExtra("formName", "DEFAULT");
					intent.putExtra("title","Communication Mode");
				}
			}else{
				StaticStore.midlet.isPasswordEntered = false;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Communication Mode");
			}
		}else if(messageContent.startsWith("DG")){
			if(messageContent.startsWith("DG00")){
				for(int i = 0;i < StaticStore.numberOfAccounts;i++){
					StaticStore.accountNumbers[i] = "0";
				}
				StaticStore.midlet.writeinMemory(context);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Deregistration");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Deregistration");
			}
		}
		return intent;
	}

	public Intent E3_E4_AA_DA_Z9_SC_EC_SE_PF_FF_L6_FE_TH_LI_LO(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("E3")){
			if(messageContent.startsWith("E300")){
				StaticStore.midlet.withInBankBenDetails = StaticStore.midlet.getSplittedValues(messageContent.substring(5),4,'~',true,StaticStore.midlet.withInBankBenDetails);
				intent = new Intent();
				intent = StaticStore.midlet.getWithInBankBenDetails(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("E4")){
			if(messageContent.startsWith("E400")){
				StaticStore.midlet.neftBenDetails = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'~',true,StaticStore.midlet.neftBenDetails);
				intent = new Intent();
				intent = StaticStore.midlet.getNeftBenDetails(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("AA")){
			if(messageContent.startsWith("AA00")){
				String temp = messageContent.substring(5);
				for(int i = 0; i < StaticStore.accountNumbers.length;i++){
					if(StaticStore.accountNumbers[i].equals("0")){
						StaticStore.accountNumbers[i] = temp.substring(0,temp.indexOf(";")).trim();
						StaticStore.totalAccounts++;
						temp = temp.substring(temp.indexOf(";") + 1);
						StaticStore.midlet.writeinMemory(context);
						break;
					}
				}            
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Link an A/C");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Link an A/C");
			}
		}else if(messageContent.startsWith("DA")){
			if(messageContent.startsWith("DA00")){
				String temp = messageContent.substring(5);
				for(int i = 0; i < StaticStore.accountNumbers.length;i++){
					if(StaticStore.accountNumbers[i].equals(temp.substring(0,temp.indexOf(";")).trim())){
						StaticStore.accountNumbers[i] = "0";
						StaticStore.totalAccounts--;
						temp = temp.substring(temp.indexOf(";") + 1);
						StaticStore.midlet.writeinMemory(context);
						break;
					}
				}      
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","DeLink an A/C");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","DeLink an A/C");
			}
		}else if(messageContent.startsWith("SC")){
			if(messageContent.startsWith("SC00")){
				String temp = messageContent.substring(5);
				StaticStore.iCashMobNo  = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.iCashAmt    = temp.substring(0,temp.indexOf(";"));
				temp        = temp.substring(temp.indexOf(";") + 1);
				StaticStore.iCashSurCharge = temp.substring(0,temp.indexOf(";"));
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "SC00");
				intent.putExtra("title","Remittance");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Remittance");
			}
		}else if(messageContent.startsWith("EC")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Remittance");
		}else if(messageContent.startsWith("SE")){
			if(messageContent.startsWith("SE00")){
				StaticStore.LogPrinter('i',">>>>>EXACATLY ON THE RIGHT PATH<<<<<+"+messageContent);
				String temp      = messageContent.substring(5);
				StaticStore.iCashMobNo       = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.iCashRemitterPin = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.iCashStatus = StaticStore.midlet.getSplittedValues(temp,5,'#',true,StaticStore.midlet.iCashStatus);
				intent = new Intent();
				intent = StaticStore.midlet.getICashStatus(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Status");
			}
		}else if(messageContent.startsWith("PF")){
			if(messageContent.startsWith("PF00")){
				String temp      = messageContent.substring(5);
				StaticStore.iCashMobNo       = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.iCashRemitterPin = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.iCashForgetPin = StaticStore.midlet.getSplittedValues(temp,4,'#',true,StaticStore.midlet.iCashForgetPin);
				intent = StaticStore.midlet.getICashForgetPin(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Forgot PIN");
			}
		}else if (messageContent.startsWith("FF")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Forgot PIN");
		}else if(messageContent.startsWith("L6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for New A/C");
		}else if(messageContent.startsWith("FE")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","FD Enquiry Request");
		}else if(messageContent.startsWith("TH")){
			if(messageContent.startsWith("TH00")){
				StaticStore.midlet.transactionHistoryList = StaticStore.midlet.getSplittedValues(messageContent.substring(5),8,'#',true,StaticStore.midlet.transactionHistoryList);
				intent = StaticStore.midlet.getTransactionHistoryList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Transaction History");
			}
		}else if(messageContent.startsWith("LI")){
			StaticStore.forAPLIBack = true;
			if(messageContent.startsWith("LI00")){
				StaticStore.midlet.inwardChequeStatusList = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'#',true,StaticStore.midlet.inwardChequeStatusList);
				intent = StaticStore.midlet.getInwardChequeStatus(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Inward Cheque Status");
			}
		}else if(messageContent.startsWith("LO")){
			StaticStore.forAPLOBack = true;
			/*if(messageContent.startsWith("LO")){
				messageContent="LO00;0000000000123458*12000000000000000*Pending#0000000000123459*13000000000000000*Success;12/12/12009 11:20;TXNID:868541896237;BANKID:C463795";
			*/if(messageContent.startsWith("LO00")){
				StaticStore.midlet.outwardChequeStatusList = StaticStore.midlet.getSplittedValues(messageContent.substring(5),3,'#',true,StaticStore.midlet.outwardChequeStatusList);
				intent = StaticStore.midlet.getOutwardChequeStatus(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Outward Cheque Status");
			}
		}
		return intent;
	}

	public Intent CE_CC_W1_W2_W3_IM_QI_2L_2D_3L_K1_K2_K3_K4_K5_6L_6D_4W_K9(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("CE")){
			if(messageContent.startsWith("CE00")){
				String temp      = messageContent.substring(5);
				StaticStore.iCashMobNo       = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.iCashRemitterPin = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.iCashCancel = StaticStore.midlet.getSplittedValues(temp,4,'#',true,StaticStore.midlet.iCashCancel);
				intent = new Intent();
				intent = StaticStore.midlet.getICashCancel(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","eCash Remittance");
			}
		}else if (messageContent.startsWith("CC")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Cancel eCash");
		}else if(messageContent.startsWith("W1")){
			if(messageContent.startsWith("W100")){
				String temp      = messageContent.substring(5);
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.MMID             = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.benMobNo             = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.benNicName             = temp.substring(0,temp.indexOf(";"));
				for(int i = 0; i < 2;i++){
					messageContent = messageContent.substring(0, messageContent.lastIndexOf(';'));
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "W100");
				intent.putExtra("title","");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");
			}
		}else if (messageContent.startsWith("W2")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
		}else if(messageContent.startsWith("W3")){
			if(messageContent.startsWith("W300")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.onlineFTPaymentFlag = messageContent.substring(5).trim().toUpperCase().startsWith("P");
				StaticStore.midlet.onlineFTDeregFlag   = messageContent.substring(5).trim().toUpperCase().startsWith("D");
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.onlineFTRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),1,'#',true,StaticStore.midlet.onlineFTRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineFTRegList(context,"Beneficiary List",89);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				if(StaticStore.index == 173 && StaticStore.menuDesc[173][3].equals("P")){
					intent.putExtra("title","Pay Beneficiary");
				}else {
					intent.putExtra("title","Delete Beneficiary");
				}
			}
		}else if(messageContent.startsWith("IM")){
			if(messageContent.startsWith("IM00")){
				StaticStore.midlet.regIMPSAccs = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.regIMPSAccs);
				intent = new Intent();
				intent = StaticStore.midlet.getRegIMPSAccs(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Generate MMID");
			}
		}else if(messageContent.startsWith("QI")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Pay");
		}else if(messageContent.startsWith("K1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Instant Pay");
		}else if(messageContent.startsWith("K2")){
			if(messageContent.startsWith("K200")){
				intent = new Intent(context , DisplayableView.class);
				messageContent = messageContent.substring(5);

				String[] temp = null;
				temp = messageContent.split(";",-1);
				StaticStore.LogPrinter('i',"K200 >>> Temp "+Arrays.deepToString(temp));
				StaticStore.acctypeAPK3 = temp[1];
//		/				Account Type Display Code Start
				String tempData = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_ACC_TYPE);
				StaticStore.LogPrinter('i',"::::::::::::::type:tempData:::"+tempData);
	            tempData = tempData.substring(0,tempData.length()-1);
	           	  
	            String acctypename = StaticStore.midlet.getAccountTypes(tempData,temp[1].trim());
//	          Account Type Display Code END
				messageContent =temp[0]+";"+acctypename+";"+temp[2]+";"+temp[3]+";"+temp[4]+";"+temp[5];
				intent.putExtra("response",messageContent);
				intent.putExtra("formName","APK2FINAL");
				intent.putExtra("title","Add Beneficiary");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");
			}
		}else if(messageContent.startsWith("K3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
		}else if(messageContent.startsWith("K4")){
			if(messageContent.startsWith("K400")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.LogPrinter('i',"messageContent "+messageContent);
				StaticStore.midlet.onlineAccPaymentList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),3,'#',true,StaticStore.midlet.onlineAccPaymentList);
				StaticStore.LogPrinter('i',"Message >>> "+Arrays.deepToString(StaticStore.midlet.onlineAccPaymentList));
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineAccPaymentList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Beneficiary");
			}
		}else if(messageContent.startsWith("K5")){
			StaticStore.LogPrinter('i',"sdsdf >>>>> APK5");
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
		}else if(messageContent.startsWith("6L")){
			if(messageContent.startsWith("6L00")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.onlineAccDeRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),1,'#',true,StaticStore.midlet.onlineAccDeRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getOnlineAccDeRegList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
		}else if(messageContent.startsWith("6D")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("4W")){
			if(messageContent.startsWith("4W00")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.onlineAccDetailsList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),5,'#',true,StaticStore.midlet.onlineAccDetailsList);
				StaticStore.LogPrinter('i',"onlineAccDetailsList -->"+Arrays.deepToString(StaticStore.midlet.onlineAccDetailsList));
				intent = new Intent();
				intent = StaticStore.midlet.getonlineAccDetailsList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("2L")){
			if(messageContent.startsWith("2L00")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.withinDereg = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.withinDereg);
				intent = new Intent();
				intent = StaticStore.midlet.getWithinRegList(93,"Beneficiary List",context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
		}else if(messageContent.startsWith("2D")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("3L")){
			if(messageContent.startsWith("3L00")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.withinDereg = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.withinDereg);
				intent = new Intent();
				intent = StaticStore.midlet.getWithinRegList(94,"Beneficiary List",context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
		}
		return intent;
	}
	
	public Intent D5_3D_D6_S6_W4_AF_4L_V1_V2_V3_L1(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("3D")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("D5")){
			if(messageContent.startsWith("D500")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.withinDereg = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.withinDereg);
				intent = new Intent();
				intent = StaticStore.midlet.getWithinRegList(95,"Beneficiary List",context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("D6")){
			if(messageContent.startsWith("D600")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.withinDereg = StaticStore.midlet.getSplittedValues(messageContent,3,'#',true,StaticStore.withinDereg);
				intent = new Intent();
				intent = StaticStore.midlet.getWithinRegList(96,"Beneficiary List",context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("S6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Retrieve MMID");
		}else if (messageContent.startsWith("W4")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
		}
		else if (messageContent.startsWith("AF")){
			if (messageContent.startsWith("AF00")){
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				int startervalue = Integer.parseInt(messageContent.substring(0,messageContent.indexOf(";")));
				if(startervalue == 1){
					for (int i = 0; i < StaticStore.accountNumbers.length; i++) {
						StaticStore.accountNumbers [i]= "0";
						StaticStore.accType[i] = "0";
					}
				}
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.maxAccLengthFromResponse += Integer.parseInt(messageContent.substring(0,messageContent.indexOf(";")));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				String accNos = messageContent.substring(0,messageContent.indexOf(";"));
				for(int i = startervalue - 1; i < StaticStore.maxAccLengthFromResponse - 1 ; i++){
					tempSeparateAccNOfromDesc = accNos.substring(0,accNos.indexOf("#")) ;
					StaticStore.accountNumbers[i] = tempSeparateAccNOfromDesc.substring(0,tempSeparateAccNOfromDesc.indexOf("*")) ;
					StaticStore.accType[i]= tempSeparateAccNOfromDesc.substring(tempSeparateAccNOfromDesc.indexOf("*")+1);
					accNos = accNos.substring(accNos.indexOf("#") + 1);
				}
				StaticStore.accountNumbers[StaticStore.maxAccLengthFromResponse - 1] = accNos.substring(0,accNos.indexOf("*"));
				StaticStore.accType[StaticStore.maxAccLengthFromResponse - 1] = accNos.substring(accNos.indexOf("*")+1);
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				String moreFlag = messageContent.substring(0,messageContent.indexOf(";"));

				if(moreFlag.equals("Y")) {
					StaticStore.midlet.moreFlag = true;
				}else{
					StaticStore.midlet.moreFlag = false;
					StaticStore.menuDesc[188][2] ="0001";
					StaticStore.maxAccLengthFromResponse = 0;
				}

				try{
					StaticStore.midlet.writeAccountInMemory();
				}catch (Exception e){
					e.printStackTrace();
				}
				try{
					String tempMsg="";
					String accArray[] = new String[StaticStore.midlet.getFilledAccArray().length];
					accArray = StaticStore.midlet.getFilledAccArray();
					for(int i=0;i<accArray.length;i++){
						tempMsg +=accArray[i]+";";
					}
					StaticStore.LogPrinter('i',"ACCount Message"+tempMsg);
					StaticStore.LogPrinter('i',"MOreFlag"+moreFlag);
					intent = new Intent(context , DisplayableView.class);
					intent.putExtra("response",tempMsg);
					intent.putExtra("formName", "AccDisplay");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Account Fetch");
			}
		}else if(messageContent.startsWith("4L")){
			if(messageContent.startsWith("4L00")){
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.neftPaymentFlag = false;
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.regSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";") + 1);
				StaticStore.midlet.neftRegList =  StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.neftRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getNeftRegList(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
		}else if(messageContent.startsWith("V1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Visa Card Beneficiary Registration");
		}else if(messageContent.startsWith("V2")){
			//V200;BALA#NAM;0001;N;TXNID:110217039770;BID:C504594
			if(messageContent.startsWith("V200")){
				String[][] temp = null;
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = moreMsgContent(messageContent);
				messageContent = messageContent.substring(messageContent.indexOf(";")+1); 
				StaticStore.midlet.vmtRegList=  StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.vmtRegList);
				StaticStore.LogPrinter('i',">>>>"+StaticStore.midlet.vmtRegList.length);
				intent = new Intent();
				intent = StaticStore.midlet.getVmtRegList(context,"V2");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Visa Card Payment");
			}
		}else if(messageContent.startsWith("V3")){
			StaticStore.midlet.vmtRegList = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Visa Card Payment");
		}else if(messageContent.startsWith("L1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Cheque Book");
		}
		return intent;
	}
	//    
	public Intent V4_V5_L9_L3_L7_L8_G1_G2_G3_G4_G5_G6_G7_G8_G9_IB_BP_DP_DO_LF_DB_LA_LE_ST(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("V4")){
			if(messageContent.startsWith("V400")){
				String[][] temp = null;
				StaticStore.midlet.inResponseInbox =  false;
				messageContent = moreMsgContent(messageContent);
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.vmtRegList=  StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.neftRegList);
				intent = new Intent();
				intent = StaticStore.midlet.getVmtRegList(context,"V4");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
		}else if(messageContent.startsWith("V5")){
			StaticStore.midlet.vmtRegList = null;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
		}else if(messageContent.startsWith("L9")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Debit Card");
		}else if(messageContent.startsWith("G1")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Cash PickUp");
		}else if(messageContent.startsWith("G2")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Cash Delivery");
		}else if(messageContent.startsWith("G3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Cheque PickUp");
		}else if(messageContent.startsWith("G4")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Draft Delivery");
		}else if(messageContent.startsWith("L3")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for A/C Statement");
		}else if(messageContent.startsWith("L7")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Loan");
		}else if(messageContent.startsWith("L8")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Credit Card");
		}else if(messageContent.startsWith("G5")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Statement-Email");
		}else if(messageContent.startsWith("G6")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for a New Product");
		}
		else if(messageContent.startsWith("G7")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Cheque Book Request");
		} 
		else if(messageContent.startsWith("G8")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Demand Draft");
		}else if(messageContent.startsWith("G9")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Request for Fixed Deposit");
		}else if(messageContent.startsWith("IB")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","eConnect SignOn password");
		}else if(messageContent.startsWith("BP")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","eConnect Transaction Password");
		}else if(messageContent.startsWith("DP")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Debit Card PIN");
		}else if(messageContent.startsWith("DO")){
			StaticStore.forAPDOBack = true;
			if(messageContent.startsWith("DO00")){
				StaticStore.closeTheAccountOnMaturity = false;
				StaticStore.depositPeriodInDays = false;
				StaticStore.depositPeriodInMonthsForNonQuarterly = false;
				StaticStore.depositPeriodInMonthsForQuarterly = false;
				StaticStore.renewMyDeposit = false;
				String temp = messageContent.substring(5);
				StaticStore.depositID = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccNo = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositCustomerName = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositSchemeCode = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositPeriodMonths = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositPeriodDays = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccOpenDate = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositMaturityAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositMaturitydate = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositRateOfInterest = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositeReceipt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				temp = StaticStore.depositAccNo + ";" + StaticStore.depositCustomerName + ";" + StaticStore.depositSchemeCode+ ";" + StaticStore.depositPeriodMonths+ ";" + StaticStore.depositPeriodDays+ ";" +
				       StaticStore.depositAccOpenDate+ ";" + StaticStore.depositMaturityAmt+ ";" + StaticStore.depositMaturitydate+ ";" + StaticStore.depositRateOfInterest+ ";" + StaticStore.depositeReceipt;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "APDO");
			}else{
				StaticStore.closeTheAccountOnMaturity = false;
				StaticStore.depositPeriodInDays = false;
				StaticStore.depositPeriodInMonthsForNonQuarterly = false;
				StaticStore.depositPeriodInMonthsForQuarterly = false;
				StaticStore.renewMyDeposit = false;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				if(StaticStore.depositIDNegative.equals("17")){
					intent.putExtra("title","Open Recurring Deposit");
				}else if(StaticStore.depositIDNegative.equals("15")){
					intent.putExtra("title","Open Fixed Deposit (Simple Interest)");
				}else if(StaticStore.depositIDNegative.equals("51") || StaticStore.depositIDNegative.equals("50")){
					intent.putExtra("title","Open Navarathnamala Deposit");
				}else if(StaticStore.depositIDNegative.equals("16")){
					intent.putExtra("title","Open Muthukuvial Deposit");
				}else if(StaticStore.depositIDNegative.equals("53")){
					intent.putExtra("title","Special 20:20 Deposit");
				}else if(StaticStore.depositIDNegative.equals("52")){
					intent.putExtra("title","Special 555 Deposit");
				}
			}
		}else if(messageContent.startsWith("LF")){
			StaticStore.depositAccBalance = true;
			if(messageContent.startsWith("LF00")){
				messageContent = messageContent.substring(5);
				messageContent = messageContent.substring(messageContent.indexOf(";"));
				StaticStore.midlet.depositAccountBalanceList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),2,'#',true,StaticStore.midlet.depositAccountBalanceList);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.depositAccountBalanceList(StaticStore.context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Deposit Account Balance");
			}
		}else if(messageContent.startsWith("DB")){
			StaticStore.depositAccBalance = true;
			if(messageContent.startsWith("DB00")){
				String temp = messageContent.substring(5);
				StaticStore.depositAccBalDepositorName = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalAccNo = maskedAccNumber(temp.substring(0,temp.indexOf(";")));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalScheme = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalDepositdate = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalDepositAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalMaturitydate = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalMaturityAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalInterestAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.depositAccBalBalanceAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				temp = StaticStore.depositAccBalDepositorName + ";" + StaticStore.depositAccBalAccNo+ ";" + StaticStore.depositAccBalScheme+ ";" +StaticStore.depositAccBalDepositdate+ ";"+ StaticStore.depositAccBalDepositAmt + ";" + StaticStore.depositAccBalMaturitydate+ ";" + StaticStore.depositAccBalMaturityAmt+ ";" +StaticStore.depositAccBalInterestAmt+ ";" +StaticStore.depositAccBalBalanceAmt;                        
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "APDB");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Deposit Account Balance");
			}
		}else if(messageContent.startsWith("LA")){
			StaticStore.loanBalance = true;
			if(messageContent.startsWith("LA00")){
				messageContent = messageContent.substring(5);
				messageContent = messageContent.substring(messageContent.indexOf(";"));
				StaticStore.midlet.loanBalanceList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),2,'#',true,StaticStore.midlet.loanBalanceList);
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent();
				intent = StaticStore.midlet.loanBalanceList(StaticStore.context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Loan Balance");
			}
		}else if(messageContent.startsWith("LE")){
			StaticStore.loanBalance = true;
			if(messageContent.startsWith("LE00")){
				String temp = messageContent.substring(5);
				StaticStore.loanBalanceAmt = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.openDate = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.loanTotalAmount = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				StaticStore.loanScheme = temp.substring(0,temp.indexOf(";"));
				temp = temp.substring(temp.indexOf(";") + 1);
				temp = StaticStore.loanBalanceAmt+ ";" + StaticStore.openDate+ ";" +StaticStore.loanTotalAmount+ ";" +StaticStore.loanScheme;                        
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",temp);
				intent.putExtra("formName", "APLE");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Loan Balance");
			}
		} else if (messageContent.startsWith("ST")) {
			intent = new Intent(context, DisplayableView.class);
			intent.putExtra("response", messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title", "My Own Accounts");
		}
		return intent;          
	}
	public String moreMsgContent(String message){
		message = message.substring(5);
		int index = message.indexOf(";");
		//message = message.substring(index + 1);
		return message ;
	}

	//    
	public Intent GP_D8_D9_V6_PE_GU_1T_2T_3T_4T_5T_7T_8T_BD(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("GP")){
			if(messageContent.startsWith("GP00")){
					StaticStore.midlet.newProduct = StaticStore.midlet.getSplittedValues(messageContent.substring(5),2,'#',true,StaticStore.midlet.newProduct);
					StaticStore.recCount+= StaticStore.midlet.newProduct.length ;
					StaticStore.midlet.nextStartRecNumber = (StaticStore.recCount+1) +"";
					intent = new Intent();
					intent = StaticStore.midlet.getNewProduct(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Request for a New Product");
			}
		}else if(messageContent.startsWith("D9")){
			//D900;a;awe*TMB mConnect*9012000*9992929299;0001;N;TXNID:110217039377;BID:C504594 
			//GP00;SA*Savings Account#HL*Home Loan;001;Y;TXNID:110115023973;BID:C504594
			if(messageContent.startsWith("D900")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.impsDetails = StaticStore.midlet.getSplittedValues(messageContent,4,'#',true,StaticStore.midlet.impsDetails);
//				StaticStore.recCount  += StaticStore.midlet.impsDetails.length ;
//				StaticStore.midlet.nextStartRecNumber = (StaticStore.recCount+1) +"";
				intent = new Intent();
				intent = StaticStore.midlet.getImpsDetails(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
		}else if(messageContent.startsWith("D8")){
			//D800;STATE BANK OF INDIA;ABERDEEN BAZAR;1355833333333333333;10;sbin0002001;17/05/2011 12:54;TXNID:110517041187;BID:C504594       
			if(messageContent.startsWith("D800")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "D800");
				intent.putExtra("title","Beneficiary Details");
			}else{             
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}            
		}else if(messageContent.startsWith("V6")){
			if(messageContent.startsWith("V600")){
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.vmtDetails = StaticStore.midlet.getSplittedValues(messageContent,2,'#',true,StaticStore.midlet.vmtDetails);
				StaticStore.recCount+= StaticStore.midlet.vmtDetails.length ;
				StaticStore.midlet.nextStartRecNumber = (StaticStore.recCount+1) +"";
				intent = new Intent();
				intent = StaticStore.midlet.getVmtDetails(context);
			}else{                
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}            
		}else if(messageContent.startsWith("GU")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Version Update");
		}
		else if(messageContent.startsWith("PE")){//tag to be filled
			String temp = "";
			if(messageContent.startsWith("PE00")){
				StaticStore.LogPrinter('i',"!!!!!!!!"+messageContent);
				StaticStore.retryCount = 0;
				messageContent = messageContent.substring(5);
				temp = messageContent . substring(messageContent.indexOf(';')+1);
				StaticStore.applicationPWD = temp.substring(0,temp.indexOf(';'));
				//		StaticStore.LogPrinter('i',"temp  "+temp);
				temp=temp.substring(temp.indexOf(';')+1);
				StaticStore.publicKey = temp.substring(0,temp.indexOf(';'));
				StaticStore.LogPrinter('i',"^^^^^^^^^^^^"+StaticStore.publicKey);
				messageContent = messageContent.substring(0,messageContent.indexOf(';')+1)+temp.substring(temp.indexOf(';')+1);
				StaticStore.midlet.writeinMemory(context);
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Reset Password");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Reset Password");
			}
			//	StaticStore.LogPrinter('i',"!!!!!!!!"+messageContent);

		}else if(messageContent.startsWith("1T")){
			if(messageContent.startsWith("1T00")){
				     
                //1T00;MT*Mobile recharge#DTP*DTH Topup;001;Y;TXNID:123234234234455;BID:C404322
                	messageContent = messageContent.substring(5);
                	String originalMessage = messageContent.substring(0,messageContent.indexOf(';'));
                	messageContent = messageContent.substring(messageContent.indexOf(';')+1);
                	int recordNumber = Integer.parseInt(messageContent.substring(0,messageContent.indexOf(';')));
                	messageContent = messageContent.substring(messageContent.indexOf(';')+1);
                	StaticStore.LogPrinter('e', "1T00 messageContent"+messageContent);
                	
                	StaticStore.midlet.moreFlag = messageContent.substring(0,messageContent.indexOf(';')).equals("Y");
                	StaticStore.LogPrinter('e', "1T00 StaticStore.midlet.moreFlag"+StaticStore.midlet.moreFlag+"");
                	
                	if(recordNumber > 1){
                		StaticStore.originalMessage = StaticStore.originalMessage+"#"+originalMessage;
                		StaticStore.LogPrinter('e', "1T00 if part"+StaticStore.originalMessage);
                	}else{
                		StaticStore.originalMessage = originalMessage;
                		StaticStore.LogPrinter('e', "1T00 else part"+ StaticStore.originalMessage);
                	}
                	
                	StaticStore.isCategoryRefresh = false;
                	StaticStore.isFrom1T00Response = true;
                	if(StaticStore.midlet.moreFlag){
                		StaticStore.midlet.nextStartRecNumber =  (StaticStore.originalMessage.split("#").length+1)+"";
        				StaticStore.tagType = "AP1T";
        				StaticStore.menuDesc[220] = new String []{"Recharge","AP1T;Y",StaticStore.mPINString,"0001","4-4-N-Y-Y","","2","false","false","Y"};
        				StaticStore.index = 220;
        				StaticStore.menuDesc[220][3] = StaticStore.midlet.nextStartRecNumber;
        				StaticStore.menuDesc[220][4] = "";
        				intent = new Intent(context,DynamicCanvas.class);
                	}else{
                		RmsStore.writeRecordStore(StaticStore.originalMessage,RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT);
        				StaticStore.midlet.rechargeDynamicInputs.assignRechargeValues(StaticStore.originalMessage);
        				StaticStore.midlet.rechargeCategoryId = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryId();
        				StaticStore.midlet.rechargeCategory = StaticStore.midlet.rechargeDynamicInputs.recharge.getCategoryName();
        				intent = new Intent();
        				intent = StaticStore.midlet.getRechargeCategory(context);
                	}
                

			}else{             
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}            
		}else if(messageContent.startsWith("2T")){
			//<Tag>;<Selected Category ID>;<Operator Search String>;<Operator details separated by #> ;<Label List separated by #>;<Category Download indicator- Y/N/M>;<Start RECORD NO>; <More Flag> ;<TXNID:Unique Id>;<BID:App Type + Bank Code>
			//2T00;MT;test;1*tett#2*test;Enter amount (1-10)*0-1-AN-N#Enter amount (1-10)*0-1-AN-N;N;001;N;TXND:1321231321323;BID:C504594
			
			messageContent = messageContent.replaceAll("-AN-", "-ANW-"); //Siva G for Issue fix
			
			if(messageContent.startsWith("2T00")){
				String operatorDetails;
				String labelDetails = "";

				messageContent = messageContent.substring(5);
				StaticStore.rechargeSelcetedCategoryID = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.benNicName = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				operatorDetails = messageContent.substring(0,messageContent.indexOf(';'));
				int dataCount = StaticStore.midlet.getCharCount(operatorDetails, '#')+1;
				StaticStore.midlet.operatorDetails = new String[dataCount][2];
				for (int i = 0; i < dataCount; i++) {
					if(dataCount - i == 1){
						StaticStore.midlet.operatorDetails[i][0] = operatorDetails.substring(0,operatorDetails.indexOf('*'));
						operatorDetails = operatorDetails.substring(operatorDetails.indexOf('*')+1);
						StaticStore.midlet.operatorDetails[i][1] = operatorDetails.substring(0,operatorDetails.indexOf('*'));
						operatorDetails = operatorDetails.substring(operatorDetails.indexOf('*')+1);
						StaticStore.midlet.operatorDetails[i][0] =   StaticStore.midlet.operatorDetails[i][0]+"*" + operatorDetails;
					}else{
						StaticStore.midlet.operatorDetails[i][0] = operatorDetails.substring(0,operatorDetails.indexOf('*'));
						operatorDetails = operatorDetails.substring(operatorDetails.indexOf('*')+1);
						StaticStore.midlet.operatorDetails[i][1] = operatorDetails.substring(0,operatorDetails.indexOf('*'));
						operatorDetails = operatorDetails.substring(operatorDetails.indexOf('*')+1);
						StaticStore.midlet.operatorDetails[i][0] =   StaticStore.midlet.operatorDetails[i][0]+"*" + operatorDetails.substring(0,operatorDetails.indexOf('#')+1);
						operatorDetails = operatorDetails.substring(operatorDetails.indexOf('#')+1);
					}
				}

				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.isCategoryRefresh = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.nextStartRecNumber = Integer.parseInt(messageContent.substring(0,messageContent.indexOf(';')))+ dataCount+"";
				StaticStore.LogPrinter('i',"StaticStore.midlet.nextStartRecNumber --> "+StaticStore.midlet.nextStartRecNumber);
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.moreFlag = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				//  midlet.mPAY_Support.operatorDetails = midlet.getSplittedValues(messageContent,2,'#',true,midlet.mPAY_Support.operatorDetails);

				intent = new Intent();
				intent = StaticStore.midlet.getOperatorDetails(context,labelDetails);

			}else{     
				if(messageContent.startsWith("2TCF")){
            		StaticStore.isCategoryRefresh = true;
            	}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}            
		}else if(messageContent.startsWith("3T")){

			if(messageContent.startsWith("3T00")){
				rechargeDisplay3T_R_4T(messageContent, context, "3T00");
			}else{   
				if(messageContent.startsWith("3TCF")){
					StaticStore.isCategoryRefresh = true;
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}            
		}else if(messageContent.startsWith("4T")){

			if(messageContent.startsWith("4T00")){
				rechargeDisplay3T_R_4T(messageContent, context, "4T00"); 
			}else{   
				if(messageContent.startsWith("4TCF")){
					StaticStore.isCategoryRefresh = true;
				}
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}            
		}else if(messageContent.startsWith("5T")){
			if(messageContent.startsWith("5T00")){
				////5T00;O;test;Recharge successful;12/12/2012 12:00;12212121212;BID:C454545
			
				String nickname;
				String labels;
				messageContent = messageContent.substring(5);
				StaticStore.midlet.topUpIndicator = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				nickname = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				labels = messageContent.substring(0,messageContent.indexOf(';'));
				if(StaticStore.midlet.topUpIndicator.equals("O")){
					StaticStore.midlet.rechargeDynamicInputs.updateNickName(StaticStore.rechargeSelcetedCategoryID,nickname+"#"+labels,false);
				}
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.LogPrinter('i',"messsss"+messageContent);
				intent = new Intent(context ,DisplayableView.class);
				intent.putExtra("response",messageContent);
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}else{ 
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge");
			}
		}else if(messageContent.startsWith("7T")){
			if(messageContent.startsWith("7T00")){
				//7T00;MT;12121212;Description*100 Success*Recharge ID:121232323;001;N;TXND:1321231321323;BID:C504594
				messageContent = messageContent.substring(5);
				StaticStore.midlet.CATID7T00 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.TrnID_7T00 = messageContent.substring(0,messageContent.indexOf(';'));
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				String temp = messageContent.substring(0,messageContent.indexOf(';'));
				String format = Display_Dynamic_Spliter(temp);
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.LogPrinter('i',">>>>>>>:::::::"+format);
				
				StaticStore.recCount = Integer.parseInt(messageContent.substring(0,messageContent.indexOf(';')).trim());
				messageContent = messageContent.substring(messageContent.indexOf(';')+1);
				StaticStore.midlet.moreFlag = messageContent.substring(0,messageContent.indexOf(';')).trim().equals("Y");
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",format);
				intent.putExtra("formName","7T00");
				intent.putExtra("title","Recharge Status Enquiry");
			}else{ 
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Recharge Status Enquiry");
			}
		}else if(messageContent.startsWith("8T")){
			if(messageContent.startsWith("8T00")){
				
                //8T00;MT;test1* Enter amount ^10-12-AN-NA-Y-Y*Enter Name ^10-12-AN-N-N-N#test2* Enter amount ^10-12-N-N-N-N;0001;Y;TXND:1321231321323;BID:C504594
              	messageContent = messageContent.substring(5);
              	String catID = messageContent.substring(0,messageContent.indexOf(';'));
              	messageContent = messageContent.substring(messageContent.indexOf(';')+1);
              	String msg = messageContent.substring(0,messageContent.indexOf(';'));
              	messageContent = messageContent.substring(messageContent.indexOf(';')+1);
              	int tempMoreCountFromResponse =  (Integer.parseInt(messageContent.substring(0,messageContent.indexOf(';'))));
              	StaticStore.midlet.nextStartRecNumber = (msg.split("#").length)+tempMoreCountFromResponse+"";
              	messageContent = messageContent.substring(messageContent.indexOf(';')+1);
              	StaticStore.midlet.moreFlag = messageContent.substring(0,messageContent.indexOf(';')).equals("Y");
              	if(tempMoreCountFromResponse > 1){
              		StaticStore.originalMessage =StaticStore.originalMessage +"#"+msg;
              	}else{
              		StaticStore.originalMessage = msg;
              	}
              	if(StaticStore.midlet.moreFlag){
    				StaticStore.menuDesc[220] = new String [] {"Beneficiary Sync","AP8T;Y",StaticStore.mPINString,StaticStore.rechargeSelcetedCategoryID,StaticStore.midlet.nextStartRecNumber,"","","","3","true","true","Y"};
    				StaticStore.index = 220;
    				StaticStore.tagType = "AP8T";
    				StaticStore.FromListScreen = false;
    				intent = new Intent(context,DynamicCanvas.class);
              	}else{
              		StaticStore.originalMessage = StaticStore.midlet.replaceSpace(StaticStore.originalMessage, ":", '#');
                  	StaticStore.originalMessage = StaticStore.midlet.replaceSpace(StaticStore.originalMessage, "#", '*');
                    	if(StaticStore.originalMessage.trim()!="")
                    		StaticStore.midlet.rechargeDynamicInputs.updateNickName(catID, StaticStore.originalMessage,true);
                    	String benNickName = RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT_NICK);
        				StaticStore.LogPrinter('i',"benNickname::::::::::::::::"+benNickName);
        				StaticStore.midlet.rechargeDynamicInputs.recharge.setBeneficiaryList(benNickName);
        				String menuArr[] = StaticStore.midlet.rechargeDynamicInputs.getBeneficiaryListByCatID(StaticStore.rechargeSelcetedCategoryID);
        				if(menuArr != null && !StaticStore.forRechargeBeneList && !StaticStore.forRechargeBeneDereg){
        					String[] menuItem = new String[]{"New Recharge","Recharge Status Enquiry","Beneficiary List","Beneficiary Deregistration"}; //
        					intent = new Intent(context, ListSelection.class);
        					intent.putExtra("listIndex", 222);
        					intent.putExtra("menuItem", menuItem);
        					intent.putExtra("listHeader", "Recharge");
        					intent.putExtra("more", StaticStore.midlet.moreFlag);
        				}else if(!StaticStore.forRechargeBeneList && !StaticStore.forRechargeBeneDereg){
        					String[] menuItem = new String[]{"New Recharge","Recharge Status Enquiry"};
        					intent = new Intent(context, ListSelection.class);
        					intent.putExtra("listIndex", 222);
        					intent.putExtra("menuItem", menuItem);
        					intent.putExtra("listHeader", "Recharge");
        					intent.putExtra("more", StaticStore.midlet.moreFlag);
        				}
        				if(StaticStore.forRechargeBeneList){
        					StaticStore.forRechargeBeneList = false;
        					intent = StaticStore.midlet.get_ReChargeBeneficiaryListDeregistrationList(context,251);
        				}else if(StaticStore.forRechargeBeneDereg){
        					StaticStore.forRechargeBeneDereg = false;
        					intent = StaticStore.midlet.get_ReChargeBeneficiaryListDeregistrationList(context,252);
        				}
              	}
		}else if(messageContent.startsWith("8T34")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			if(StaticStore.forRechargeBeneList){
				StaticStore.forRechargeBeneList = false;
				intent.putExtra("title","Beneficiary List");
			}else if(StaticStore.forRechargeBeneDereg){
				StaticStore.forRechargeBeneDereg = false;
				intent.putExtra("title","Beneficiary Deregistration");
			}else{
				StaticStore.forRechargeBeneList = false;
				StaticStore.forRechargeBeneDereg = false;
				intent.putExtra("title","Beneficiary Sync");
			}
			RmsStore.deleterecordfornodetails(RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		}else{
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			if(StaticStore.forRechargeBeneList){
				StaticStore.forRechargeBeneList = false;
				intent.putExtra("title","Beneficiary List");
			}else if(StaticStore.forRechargeBeneDereg){
				StaticStore.forRechargeBeneDereg = false;
				intent.putExtra("title","Beneficiary Deregistration");
			}else{
				StaticStore.forRechargeBeneList = false;
				StaticStore.forRechargeBeneDereg = false;
				intent.putExtra("title","Beneficiary Sync");
			}
		}
	}else if(messageContent.startsWith("BD")){
		if(messageContent.startsWith("BD00")){
			StaticStore.delselection = StaticStore.selection;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Beneficiary Deregistration");
			if(StaticStore.forRechargeBeneDetailsDel){
				StaticStore.forRechargeBeneDetailsDel = false;
				RmsStore.deletesinglerecord(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
			}
		}else{ 
			StaticStore.forRechargeBeneDetailsDel = false;
			StaticStore.delselection = "";
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Beneficiary Deregistration");
		}
	}
		return intent;
	}
	public Intent CH_CT_TP_TC_1U_2U_3U_4U_5U_6U_7U_8U(Context context,String messageContent)throws Exception{
		if(messageContent.startsWith("CH")){
			if(messageContent.startsWith("CH00")){
				//               	 StaticStore.midlet.getIrctcSplit(messageContent);
				intent = new Intent();
				//unParseAirlines(messageContent.substring(4, messageContent.indexOf("TXN")).trim());
				  StaticStore.LogPrinter('i',"messageContent.substring(5) -->"+messageContent.substring(5));
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.IRCTCCardIssue = StaticStore.midlet.getSplittedValues(messageContent.substring(5),10,'#',true,StaticStore.midlet.IRCTCCardIssue);
				  StaticStore.LogPrinter('i',"IRCTCCardIssue -->"+Arrays.deepToString(StaticStore.midlet.IRCTCCardIssue));
				intent = StaticStore.midlet.getIRCTC_Card_List(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Card List");
			}


		}else if(messageContent.startsWith("CT")){
			StaticStore.indexCtr --;
			if(messageContent.startsWith("CT00")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "CT00");
				intent.putExtra("title","Generate Card");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Card List");

			}
			return  intent;
		}else if(messageContent.startsWith("TP")){
			if(messageContent.startsWith("TP00")){
				intent = new Intent();
				  StaticStore.LogPrinter('i',"messageContent.substring(5) -->"+messageContent.substring(5));
				StaticStore.midlet.inResponseInbox =  false;
				StaticStore.midlet.IRCTCCardIssue = StaticStore.midlet.getSplittedValues(messageContent.substring(5),10,'#',true,StaticStore.midlet.IRCTCCardIssue);
				  StaticStore.LogPrinter('i',"IRCTCCardIssue -->"+Arrays.deepToString(StaticStore.midlet.IRCTCCardIssue));
				StaticStore.LogPrinter('i',"StaticStore.midlet.IRCTCCardIssue --> "+Arrays.deepToString(StaticStore.midlet.IRCTCCardIssue));
				intent = StaticStore.midlet.getIRCTC_Card_topup(context);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Top-up");
			}
			return  intent;

		}else if(messageContent.startsWith("1U")){
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Instant Pay");
			return  intent;
		}else if(messageContent.startsWith("2U")){
			if(messageContent.startsWith("2U00")){
				String temp      = messageContent.substring(5);
				//  temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.p2unumber             = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.p2uaccntype            = temp.substring(0,temp.indexOf(";"));
				temp             = temp.substring(temp.indexOf(";") + 1);
				StaticStore.midlet.p2ubankname             = temp.substring(0,temp.indexOf(";"));
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "2U00");
				intent.putExtra("title","Add Beneficiary");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Add Beneficiary");

			}
			return  intent;
		}else if(messageContent.startsWith("3U")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Add Beneficiary");
			return  intent;
		}else if(messageContent.startsWith("4U")){
			if(messageContent.startsWith("4U00")){
				//4U00; ;32#hi#55hhhh#sa;0001;N;TXNID:326812467970
				StaticStore.midlet.inResponseInbox =  false;
				//                         
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.P2UFTRegList = StaticStore.midlet.getSplittedValues(messageContent,1,'#',true,StaticStore.midlet.P2UFTRegList);
				intent = StaticStore.midlet.getP2UFTRegList(context,243);
			}else{  
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Pay Beneficiary");
			}
			return  intent;
		}else if(messageContent.startsWith("5U")){
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Pay Beneficiary");
			return  intent;
		}else if(messageContent.startsWith("6U")){
			if(messageContent.startsWith("6U00")){
				//6U00; ;32*323232323232*SA#hi*333333344444*SA#55hhhh*555555555555*SA#sa*526398741526*SA;0001;N;TXNID:326812112787;BID:C444444
				messageContent = messageContent.substring(5);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				messageContent = messageContent.substring(messageContent.indexOf(";")+1);
				StaticStore.midlet.P2UFTRegList = StaticStore.midlet.getSplittedValues(messageContent,3,'#',true,StaticStore.midlet.P2UFTRegList);
				intent = StaticStore.midlet.getP2UFTRegList(context,244);
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Beneficiary Details");
			}
			return  intent;
		}else if(messageContent.startsWith("7U")){
			if(messageContent.startsWith("7U00")){

				StaticStore.midlet.inResponseInbox =  false;
				messageContent = messageContent.substring(5);
				StaticStore.LogPrinter('i',"::::::::::messageContent 7U"+messageContent);
				StaticStore.benSearchString = messageContent.substring(0,messageContent.indexOf(";"));
				StaticStore.midlet.P2UFTRegList = StaticStore.midlet.getSplittedValues(messageContent.substring(messageContent.indexOf(";")+1),1,'#',true,StaticStore.midlet.P2UFTRegList);
				intent = StaticStore.midlet.getP2UFTRegList(context,245);
			}else{  
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Delete Beneficiary");
			}
			return  intent;
		}else if(messageContent.startsWith("8U")){
			StaticStore.indexCtr --;
			intent = new Intent(context , DisplayableView.class);
			intent.putExtra("response",messageContent.substring(5));
			intent.putExtra("formName", "DEFAULT");
			intent.putExtra("title","Delete Beneficiary");
			return  intent;
		}else if(messageContent.startsWith("TC")){
			if(messageContent.startsWith("TC00")){
				StaticStore.midlet.inResponseInbox =  false;
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "TC00");
				intent.putExtra("title","Card List");
			}else{
				intent = new Intent(context , DisplayableView.class);
				intent.putExtra("response",messageContent.substring(5));
				intent.putExtra("formName", "DEFAULT");
				intent.putExtra("title","Card List");
			}
		}
		return  intent;
	}
	public Intent ResponceError(Context context){
		intent = new Intent(context , DisplayableView.class);
		intent.putExtra("response","We are facing some technical difficulties. Please try after sometime. Thank you !");//messageContent.substring(5)
		intent.putExtra("formName", "Error_in_resp");
		intent.putExtra("title","Alert Message");
		return  intent;
	}
	private Intent getEnableTransaction(Context context){
		String[] menuItem = {"Balance Enquiry","Mini Statement","M2M Fund Transfer","M2A Fund Transfer","NEFT",
				"IMPS","Visa Card Bill Payment","Money Send","Stop Cheque","Cheque Status",
				"Debit Card","Cash Pick up","Cash Delivery","Cheque Pick up","Draft Delivery",
				"Cheque Book","Statement Request Print","Statement Request Email","Apply for new lead","Bill Presentment & Payment",
				"Instant Payment","Airline Ticketing","Movie Ticketing","Mobile Recharge",//,"View Paid Bills"
				"Trust Donation","Fees Payment","Corporate Payment","Shopping",//"Recharge Status Enquiry",
				"Manage Card Limit","Linking Cust ID","Hotlist Debit Card","ATM / Branch Locator",//"Locator Branch","Locator ATM",
				"Fixed Deposit","eCash","Rewards Points Redemption","Feedback/Complaints", "Demand Draft Request", "Fixed Deposit Request"};
		Intent mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 13);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Enable or Disable Transaction");
		mPayIntent.putExtra("more", false);
		//       	return mPayIntent;
		//   StaticStore.midlet.enableTXN = new ListCanvas(midlet,display,"Enable Transaction",menuItem,13,true,false,false);
		//       for(int i = 0;i < StaticStore.midlet.enableFlag.length();i++){
		//       	if(StaticStore.midlet.enableFlag.charAt(i) == '1'){
		//       		StaticStore.midlet.objClsA.enableTXN.setSelectedIndexes(i);
		//       	}
		//       }
		//        for(int i = 0; i < StaticStore.midlet.enableTransaction.length;i++){
		//            if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("BE")){
		//                StaticStore.midlet.enableTXN[i] = 0;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("MS")){
		//            	StaticStore.midlet.enableTXN[i] = 1;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("FA")){
		//            	StaticStore.midlet.enableTXN[i] = 2;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("FE")){
		//            	StaticStore.midlet.enableTXN[i] = 3;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("MC")){
		//            	StaticStore.midlet.enableTXN[i] = 4;
		//                
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("BP")){
		//            	StaticStore.midlet.enableTXN[i] = 5;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("AT")){
		//            	StaticStore.midlet.enableTXN[i] = 6;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("MT")){
		//            	StaticStore.midlet.enableTXN[i] = 7;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("MR")){
		//            	StaticStore.midlet.enableTXN[i] = 8;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("TD")){
		//            	StaticStore.midlet.enableTXN[i] = 9;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("FP")){
		//            	StaticStore.midlet.enableTXN[i] = 10;
		//            }else if(StaticStore.midlet.enableTransaction[i][0].toUpperCase().trim().equals("CF")){
		//            	StaticStore.midlet.enableTXN[i] = 11;
		//            }
		//        }
		return mPayIntent;
	}


	public void seperateBillers(String billerResp)  throws Exception {
		//BI00 AIRTEL|AIRTN|2|Enter Consumer No(4-12-AN-Y-Y)|Enter Mnemonic name(4-12-AN-Y-Y)~AIRTEL|AIRTN|2|
		//Enter Consumer No(4-12-AN-Y-Y)|Enter Mnemonic name (4-12-AN-Y-Y) ;0001; Y ;11/01/09 08:15;TXNID:124343535634
		int ctr = StaticStore.midlet.getCharCount(billerResp,'#');
		int valueCtr = 0;
		int splitterIndex = 0;
		int noOfRefs      = 0;
		int k = 0;
		String tempStr = "";
		int noOfColumns = 0;
		StaticStore.midlet.billPayTxnId = billerResp.substring(billerResp.indexOf("TXNID:") + 6).trim();
		StaticStore.midlet.maxCnt = getMaxCnt(billerResp,StaticStore.midlet.maxCnt);        
		if(!StaticStore.midlet.isAdhocPayment){        
			noOfColumns = 11 + (2 * StaticStore.midlet.maxCnt);
		}else{
			noOfColumns = 13 + (2 * StaticStore.midlet.maxCnt);
		}
		int startVar     = 0;
		//B300;AIRTN|AIRTEL|2|Enter Consumer No^ 4-12-AN-N-Y|Enter Roll No^4-12-AN-N-Y;A;TXNID:124343535634
		//StaticStore.LogPrinter('i',"billerResp:::"+billerResp);
		String temp       = billerResp.substring(billerResp.indexOf(";") + 1);
		temp              = temp.substring(temp.indexOf(';') + 1);
		//        String categoryID = temp.substring(0,temp.indexOf(';'));
		//        temp           = temp.substring(temp.indexOf(";") + 1);
		//         StaticStore.midlet.isAdhocPayment = temp.substring(0,temp.indexOf(";")).trim().toUpperCase().equals("A");

		//        temp          = temp.substring(temp.indexOf(";") + 1);
		//        temp          = temp.substring(temp.indexOf(";") + 1);
		//        moreFlag      = temp.substring(0,temp.indexOf(";")).trim().toUpperCase().equals("Y");

		String[][] tempArr;
		String[]   tempArr1;
		billerResp = billerResp.substring(billerResp.indexOf(';') + 1);
		// /* y300 Siva
		if(StaticStore.midlet.isAdhocPayment)
		{
			billerResp = billerResp.substring(billerResp.indexOf(';') + 1);
			billerResp = billerResp.substring(billerResp.indexOf(';') + 1);
		}// Y300 */
		if(false){//unRegBillers != null
			tempArr                 =   StaticStore.midlet.unRegBillers;
			tempArr1                =   StaticStore.midlet.billerCategory;
			StaticStore.midlet.unRegBillers                 =   new String[StaticStore.midlet.unRegBillers.length + ctr + 1][noOfColumns];
			StaticStore.midlet.billerCategory          =   new String[StaticStore.midlet.billerCategory.length + ctr +1];

			for(int i = 0 ; i < tempArr.length; i++) {
				for(int j = 0;j<noOfColumns;j++){
					StaticStore.midlet.unRegBillers[i][j]     =   tempArr[i][j];
					StaticStore.midlet.billerCategory[i] =   tempArr1[i];
				}
				startVar++;
			}
		}else{
			StaticStore.midlet.unRegBillers = new String[ctr + 1][noOfColumns];
			StaticStore.midlet.billerCategory = new String[ctr + 1];
		}

		for(int i = 0;i<=ctr;i++){
			int endIndex = i != ctr?billerResp.indexOf("~"):billerResp.indexOf(";");
			tempStr =  billerResp.substring(0,endIndex);
			billerResp = billerResp.substring(endIndex + 1);
			//splitterIndex = i != ctr? billerResp.indexOf("~") + 1:0;
			valueCtr = StaticStore.midlet.getCharCount(tempStr,'*');
			//billerCategory[i+startVar] = categoryID;
			for(int j = 0;j<=valueCtr;j++){
				StaticStore.midlet.unRegBillers[i+startVar][j+3] = j != valueCtr?tempStr.substring(0,tempStr.indexOf("*")):tempStr;
				tempStr         = j != valueCtr?tempStr.substring(tempStr.indexOf("*")+1):tempStr;
				/*Here We seperating the validation and caption*/
				if(j > 2){
					//need to write the code
					StaticStore.midlet.unRegBillers[i+startVar][10+StaticStore.midlet.maxCnt+k] = StaticStore.midlet.unRegBillers[i+startVar][j+3].substring(StaticStore.midlet.unRegBillers[i+startVar][j+3].indexOf("/") + 1);
					StaticStore.midlet.unRegBillers[i+startVar][j+3] = StaticStore.midlet.unRegBillers[i+startVar][j+3].substring(0,StaticStore.midlet.unRegBillers[i+startVar][j+3].indexOf("/"));
					k++;
				}
				//StaticStore.LogPrinter('i',"("+i+", "+j+") ====> "+unRegBillers[i+startVar][j+3]);
			}
			k = 0;
		}
		for(int i = 0;i<=ctr;i++){
			StaticStore.midlet.unRegBillers[i+startVar][0]               = "Billers List";
			StaticStore.midlet.unRegBillers[i+startVar][1]               = "APB4;Y";
			StaticStore.midlet.unRegBillers[i+startVar][2]               = StaticStore.mPINString;
			if(!StaticStore.midlet.isAdhocPayment){
				StaticStore.midlet.unRegBillers[i+startVar][noOfColumns - 1] = (4 + StaticStore.midlet.maxCnt)+"";
				StaticStore.midlet.unRegBillers[i+startVar][StaticStore.midlet.maxCnt+6]        = "4-4-N-Y-Y";
			}else{            
				StaticStore.midlet.unRegBillers[i+startVar][noOfColumns - 1] = (6 + StaticStore.midlet.maxCnt)+"";                
				StaticStore.midlet.unRegBillers[i+startVar][StaticStore.midlet.maxCnt+8]        = "4-4-N-Y-Y";
			}            
			for(int j = 0;j < noOfColumns;j++){
				if(StaticStore.midlet.unRegBillers[i+startVar][j] == null){
					StaticStore.midlet.unRegBillers[i+startVar][j] = "";
				}
			}
		}
		for(int i = 0;i<StaticStore.midlet.unRegBillers.length;i++){
			for(int j = 0;j<noOfColumns;j++){
				//StaticStore.LogPrinter('i',"("+i+", "+j+") ====> "+unRegBillers[i][j]);
			}
		}
		
		StaticStore.LogPrinter('i',"seperateBillers --> StaticStore.midlet.unRegBillers ==>"+Arrays.deepToString(StaticStore.midlet.unRegBillers));
		billerResp = billerResp.substring(billerResp.indexOf(';')+1);
		//categoryID = billerResp.substring(0,billerResp.indexOf(";"));
		billerResp = billerResp.substring(billerResp.indexOf(';')+1);
		//moreUnRegBillers = billerResp.substring(0,billerResp.indexOf(';')).toUpperCase().equals("Y");
		//StaticStore.LogPrinter('i',"**********"+billerResp);
	}
	public int getMaxCnt(String billerResp,int tempMaxCount)  throws Exception{

		String tempStr      =   billerResp;
		String tempStr1     =   "";
		int tmpCnt          =   0;
		boolean tempBool    =   false;    
		//B300;12121|BSNL|2|ACCOUNT NUMBER^2-10-AN-N-N|MOBILE NUMBER^2-10-AN-N-N;03;P;0001;N;TXNID:002092090320
		String tempStr2     =   billerResp.substring(billerResp.indexOf("*") + 1);   
		tempStr2            =   tempStr2.substring(tempStr2.indexOf("*") + 1);
		try{
			StaticStore.midlet.maxCnt              =   Integer.parseInt(tempStr2.substring(0,tempStr2.indexOf("*")).trim());
		}catch(Exception e){            
		}
		while(tempStr.indexOf("#") != -1 || tempBool) {
			int index       =   0;
			if(tempBool) {
				tempStr1      =   tempStr;
			} else {
				index       =   tempStr.indexOf("#");
				tempStr1    =   tempStr.substring(0, index);
			}

			//BI00 AIRTEL|AIRTN|2|Enter Consumer No(4-12-AN-Y-Y)|Enter Mnemonic name(4-12-AN-Y-Y)~AIRTEL|AIRTN|2|i

			int index1      =   tempStr1.indexOf("*");
			tempStr1        =   tempStr1.substring(index1 + 1);
			index1          =   tempStr1.indexOf("*");
			tempStr1        =   tempStr1.substring(index1 + 1);

			tmpCnt          =   tempStr1.startsWith("0") ? 0 : Integer.parseInt(tempStr1.substring(0, tempStr1.indexOf("|")));

			if(tmpCnt > StaticStore.midlet.maxCnt && tmpCnt > tempMaxCount)
				StaticStore.midlet.maxCnt  =   tmpCnt;

			tempStr         =   tempStr.substring(index + 1);

			if(tempStr.indexOf("#") == -1 && !tempBool) {
				tempBool    =   true;
			} else {
				tempBool    =   false;
			}
		}

		return StaticStore.midlet.maxCnt;
	}
	private Intent getRegBillersList(Context context)  throws Exception{

		String menuItem[] = new String[StaticStore.midlet.regBillers.length];
		for(int i = 0; i < StaticStore.midlet.regBillers.length; i++){
			menuItem[i] = StaticStore.midlet.regBillers[i][0];
		}

		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 35);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Biller List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;


	}
	private String[][] getTempleSchemes123(String[][] temples)  throws Exception{
		int noOfSchemes = 0;
		String tempStr = "";
		String scheme  = "";
		int l = 0;

		//Here we Calculating number of schemes
		for(int i = 0; i < temples.length;i++){
			noOfSchemes += Integer.parseInt(temples[i][6].trim().charAt(0)+"");
		}
		String[][] temp = new String[noOfSchemes][4]; 
		for(int i = 0;i<temples.length;i++){
			temples[i][6] += "*";
			noOfSchemes = Integer.parseInt(temples[i][6].trim().charAt(0)+"");
			tempStr = temples[i][6].substring(temples[i][6].indexOf("*")+1);
			for(int j = 0;j<noOfSchemes;j++){

				scheme  = tempStr.substring(0,tempStr.indexOf("*"));
				tempStr = tempStr.substring(tempStr.indexOf("*") + 1);
				int noOfSubSchemes = Integer.parseInt(tempStr.trim().charAt(0)+"");
				tempStr  = tempStr.substring(tempStr.indexOf("*")+1);
				//initially it will be null
				temp[l][3] = "";
				for(int k = 0;k<noOfSubSchemes;k++){
					temp[l][0]  = temples[i][0];
					temp[l][1]  = scheme;
					temp[l][2]  = noOfSubSchemes + "";
					temp[l][3] += tempStr.substring(0,tempStr.indexOf("*"))+",";
					tempStr = tempStr.substring(tempStr.indexOf("*") + 1);
				}
				l++;
			}
		}
		return temp;
	}
	private String movieDatas(byte index,String temp)  throws Exception{
		if(index == 1){
			StaticStore.midlet.region        = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.language       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.venue     = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.cinema       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.noOfTickets  = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.date         = temp.substring(0,temp.indexOf(";")).trim();
		}else if(index == 2){

			StaticStore.midlet.region       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.language     = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.venue        = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.cinema       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.noOfTickets  = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.date         = temp.substring(0,temp.indexOf(";")).trim();            
		}else if(index == 3){
			StaticStore.midlet.region       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.language     = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.venue        = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.cinema       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.noOfTickets  = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.date         = temp.substring(0,temp.indexOf(";")).trim();
		}else if(index == 4){
			StaticStore.midlet.region       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.language     = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.venue        = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.cinema       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.noOfTickets   = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.date         = temp.substring(0,temp.indexOf(";")).trim();
		}else if(index == 5){
			StaticStore.midlet.region       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.language     = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.venue        = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.cinema       = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.noOfTickets  = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.date         = temp.substring(0,temp.indexOf(";")).trim();
			temp         = temp.substring(temp.indexOf(";") + 1);
			temp         = temp.substring(temp.indexOf(";") + 1);
			StaticStore.midlet.showTime     = temp.substring(0,temp.indexOf(";")).trim();
		}
		return temp;
	}
	private Intent getMovieTimeList(Context context)  throws Exception{
		String[] menuItem = new String[StaticStore.midlet.showTimings.length];
		for(int i = 0;i< StaticStore.midlet.showTimings.length;i++){
			menuItem[i] = StaticStore.midlet.showTimings[i][1];
			StaticStore.LogPrinter('i',">>>> menuItem[i]"+ menuItem[i]);
		}

		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 21);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Show Timing");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;
	}

	private Intent getVenueList(Context context)  throws Exception{
		String menuItem[] = new String[StaticStore.midlet.movieVenue.length];
		for(int i = 0; i < StaticStore.midlet.movieVenue.length; i++){
			menuItem[i] = StaticStore.midlet.movieVenue[i][0];
		}

		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 22);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Theatre List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;

	}

	private Intent getCinemaList(Context context)  throws Exception{
		String menuItem[] = new String[StaticStore.midlet.movies.length];
		for(int i = 0; i < StaticStore.midlet.movies.length; i++){
			menuItem[i] = StaticStore.midlet.movies[i][0];
		}
		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 23);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Movie List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;

	}

	private Intent getDateList(Context context)  throws Exception {
		String menuItem[] = new String[StaticStore.midlet.movieDates.length];
		for(int i = 0; i < StaticStore.midlet.movieDates.length; i++){
			menuItem[i] = StaticStore.midlet.movieDates[i][0];
		}
		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 24);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Available Dates");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;

	}

	private Intent getPriceList(Context context) throws Exception {
		String menuItem[] = new String[StaticStore.midlet.moviePrice.length];
		for(int i = 0; i < StaticStore.midlet.moviePrice.length; i++){
			menuItem[i] = StaticStore.midlet.moviePrice[i][1] + "-Rs."+StaticStore.midlet.moviePrice[i][2];
		}
		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 25);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Ticket Price List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;
	}

	private Intent getTarifPlans(Context context)  throws Exception{

		String menuItem[] = new String[StaticStore.midlet.tarifPlans.length];
		for(int i = 0; i < StaticStore.midlet.tarifPlans.length; i++){
			menuItem[i] = StaticStore.midlet.tarifPlans[i][0];
		}
		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 32);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Tariff List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;

	}
	private Intent getOperatorList(Context context) throws Exception {//mobile recharge

		String menuItem[] = new String[StaticStore.midlet.operatorList.length];
		for(int i = 0; i < StaticStore.midlet.operatorList.length; i++){
			menuItem[i] = StaticStore.midlet.operatorList[i][1];
		}
		Intent  mPayIntent = new Intent(context, ListSelection.class);
		mPayIntent.putExtra("listIndex", 32);
		mPayIntent.putExtra("menuItem", menuItem);
		mPayIntent.putExtra("listHeader", "Operator List");
		if(StaticStore.midlet.moreFlag){
			mPayIntent.putExtra("more", true);
			//    LCBookingAirlines = new ListCanvas(this,getDisplay(),"Airline List",menuItem,38,false,true,true);
		}else{
			mPayIntent.putExtra("more", false);
		}

		return mPayIntent;

	}
	public String Display_Dynamic_Spliter(String msg) throws Exception
	{
		StaticStore.LogPrinter('i',"Ori Message -->"+msg);
		String Disp[] = msg.split("#"); 
		StaticStore.Display_header = new String[Disp.length];
		String format = null;
		for(int i = 0;i< Disp.length;i++)
		{
			StaticStore.Display_header[i] =	Disp[i].substring(0,Disp[i].indexOf("*"));
			format = format+";"+Disp[i].substring(Disp[i].indexOf("*")+1,Disp[i].length());
			format = format.replaceAll("\\*", "\n");
		}
		format = format.substring(format.indexOf(";")+1);
		StaticStore.LogPrinter('i',"Header Array  --> "+Arrays.deepToString(StaticStore.Display_header));
		StaticStore.LogPrinter('i',"format Array  --> "+format);
		return format;
	}

	public void rechargeDisplay3T_R_4T(String messageContent,Context context,String formName) throws Exception{
//		3T00;
//		MTP;
//		O;
//		AIRCELMOB;
//		4950000003:10:nick;
//		Operator Name*AIRCEL#Mobile No.*4950000003#Recharge Amount*Rs.10.00#Nick Name*nick;N;TXNID:408318031403
		messageContent = messageContent.substring(5);
		StaticStore.rechargeSelcetedCategoryID = messageContent.substring(0,messageContent.indexOf(';'));
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);
		StaticStore.midlet.topUpIndicator = messageContent.substring(0,messageContent.indexOf(';'));
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);
		StaticStore.rechargeOpID_R_NickName = messageContent.substring(0,messageContent.indexOf(';'));
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);

		StaticStore.midlet.rechargelabels = messageContent.substring(0,messageContent.indexOf(';'));
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);

		 StaticStore.LogPrinter('i',"rechargelabels === > "+StaticStore.midlet.rechargelabels);
		String temp = messageContent.substring(0,messageContent.indexOf(';'));
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);
		String format = Display_Dynamic_Spliter(temp);
		StaticStore.isCategoryRefresh = messageContent.substring(0,messageContent.indexOf(';')).equals("Y");
		messageContent = messageContent.substring(messageContent.indexOf(';')+1);
		intent = new Intent(context , DisplayableView.class);
		intent.putExtra("response",format);
//		intent.putExtra("rechargelabels",rechargelabels);
		intent.putExtra("formName", formName);

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
	

	
}
