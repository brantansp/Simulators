package com.fss.tmb;

import android.R.raw;
import android.sax.StartElementListener;

public class RechargeDynamicInputs {
	
	public String message;
	public int dataCount = 0;
	String categoryList[] = null;
	String existingNames = "";
	boolean IdNotAvailable = false;
	
	public RechargeModel recharge = null;
	public RechargeDynamicInputs() {
		// TODO Auto-generated constructor stub
		this.recharge = new RechargeModel();
	}
	public void assignRechargeValues(String message) {
		// TODO Auto-generated constructor stub
		//MT*Mobile recharge*Min Amount^0-1-AN-N-N*Max Amount^1-9-AM-N-N#DTP*DTH Topup*Max Amount^1-9-AN-N-N*Max Amount^1-9-AM-N-N
		
		this.message = message;
		dataCount = message.split("#").length-1;
		categoryList = new String[dataCount+1];
		for (int i = 0; i < dataCount; i++) {
			categoryList[i] = message.substring(0,message.indexOf('#'));
			message = message.substring(message.indexOf('#')+1);
			
		}
		categoryList[categoryList.length-1] = message;
		String categoryId[] = new String[categoryList.length];
		for (int i = 0; i < categoryList.length; i++) {
			categoryId[i] =categoryList[i].substring(0,categoryList[i].indexOf('*')); 
		}
		existingNames = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		StaticStore.LogPrinter('i',"{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{"+existingNames);
		recharge.setCategoryList(categoryList);
		recharge.setCategoryId(categoryId);
		recharge.setCategoryName(categoryName(categoryList));
		//recharge.setCategoryInputs(categoryInputs(categoryList));
	}
	public String[] categoryName(String[] categoryList) {
		String dummy;
		String CategoryName[] = new String[categoryList.length];
		for (int i = 0; i < categoryList.length; i++) {
			dummy = categoryList[i];
			dummy = dummy.substring(dummy.indexOf('*')+1);
			CategoryName[i] =  dummy; 
		}
		return CategoryName;
	}
	public String[][] categoryInputs(String[] categoryList) {
		String dummy;
		int labelOccurence;
		String CategoryInputs[][] = new String[categoryList.length][10];
		for (int i = 0; i < categoryList.length; i++) {
			dummy = categoryList[i];
			dummy = dummy.substring(dummy.indexOf('*')+1);
			dummy = dummy.substring(dummy.indexOf('*')+1);
			labelOccurence = getGivenCharCount(dummy,'*');
			for (int j = 0; j < labelOccurence; j++) {
				CategoryInputs[i][j] =  dummy.substring(0,dummy.indexOf('*'));
				dummy = dummy.substring(dummy.indexOf('*')+1);
			}
			CategoryInputs[i][labelOccurence] = dummy;
		}
		return CategoryInputs;
	}
	public int getGivenCharCount(String msg,char split){
		String s = msg;
		int counter = 0;
		for( int i=0; i<s.length(); i++ ) {
		    if( s.charAt(i) == split ) {
		        counter++;
		    } 
		}
		return counter;
	}
	public void updateNickName(String rechargeSelcetedCategoryID,String nickname_plus_labels,boolean isSync) {
		existingNames = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		StaticStore.LogPrinter('i',"ExistingNames"+existingNames);
		if(existingNames.equals("EMPTY")){
			RmsStore.writeRecordStore(rechargeSelcetedCategoryID+"*"+nickname_plus_labels,RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		}else{
		String arr[] = existingNames.split(";");
		int position = returnIndexPosition(rechargeSelcetedCategoryID, arr);

		if(!isSync){
			if(IdNotAvailable){
				String tempArr[] = existingNames.split(";"); 
				arr = new String[arr.length+1];
				position = tempArr.length;
				for (int i = 0; i < tempArr.length; i++) {
					arr[i] = tempArr[i];
				}
				arr[position] =  rechargeSelcetedCategoryID+"*"+nickname_plus_labels;
			}else{
			String tempArr[] = arr[position].split(":");
			if(tempArr.length == 5){
				arr[position] = arr[position].substring(arr[position].indexOf(":")+1);
				arr[position] = rechargeSelcetedCategoryID+"*"+	arr[position]+":"+nickname_plus_labels;
			}else{
				arr[position] = arr[position]+":"+nickname_plus_labels;
			}
			}
		}else if(isSync && StaticStore.isBenSyncMoreClicked){
			arr[position] = arr[position]+":"+nickname_plus_labels;
		}else{
			if(IdNotAvailable){
				String tempArr[] = existingNames.split(";"); 
				arr = new String[arr.length+1];
				position = tempArr.length;
				for (int i = 0; i < tempArr.length; i++) {
					arr[i] = tempArr[i];
				}
				arr[position] =  rechargeSelcetedCategoryID+"*"+nickname_plus_labels;
			}else{
				arr[position] = rechargeSelcetedCategoryID+"*"+nickname_plus_labels;
			}
		}
		RmsStore.writeRecordStore(frameMsg(arr,";"),RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		}
	}
	public String getLabelDetailsByID_Name(String rechargeSelcetedCategoryID,String nickName) {
		// TODO Auto-generated method stub
		String arr[] = recharge.getBeneficiaryList();
		int index = returnIndexPosition(rechargeSelcetedCategoryID, arr);
		String temp = arr[index];
		StaticStore.LogPrinter('i',"LLLLLLLLLLLL"+temp);
		temp = temp.substring(temp.indexOf('*')+1);
		String labels[] = temp.split(":");
		int nickNameIndex = returnIndexPosition(nickName, labels);
		temp = labels[nickNameIndex];
		temp = temp.substring(temp.indexOf('#')+1);
		StaticStore.LogPrinter('i',"OLD"+temp);
		temp = temp.replace('^','*');
		StaticStore.LogPrinter('i',"OLD"+temp);
		return temp;
	}
	
	private int returnIndexPosition(String temp,String arr[]){
		int i = 0;
		for (i = 0;i < arr.length; i++) {
			if(arr[i].startsWith(temp)){
				IdNotAvailable = false;
				break;
			}else{
				IdNotAvailable = true;
			}
		}
		return i;
	}
	private String frameMsg(String arr[],String str){
		String temp = "";
		for (int i = 0; i < arr.length; i++) {
			temp = temp+arr[i]+str;
		}
		
		return temp.substring(0,temp.length()-1);
	}
	public String[] getBeneficiaryListByCatID(String rechargeSelcetedCategoryID) {
		// TODO Auto-generated method stub
		String arr[] = recharge.getBeneficiaryList();
		String temp = "";
		try{
		for (int i = 0; i < arr.length; i++) {
			if(arr[i].startsWith(rechargeSelcetedCategoryID)){
				temp = arr[i].substring(arr[i].indexOf('*')+1);
			}
		}
		if(temp.equals("")){
			return null;
		}else{
			String tempArr[] = temp.split(":");
			String nickNameArr[] = new String[tempArr.length];
			for (int i = 0; i < tempArr.length; i++) {
				nickNameArr[i] = tempArr[i].substring(tempArr[i].indexOf('*')+1, tempArr[i].indexOf('#'));
			}
			return nickNameArr;
		}
		}catch(Exception e){
			return null;
		}
		
	}
}
