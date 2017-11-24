package com.fss.m24;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.RandomStringUtils;

import com.fss.m24.encrypt.RSAEncryption;
import com.sun.xml.internal.ws.util.StringUtils;


public class USSDNUUP  extends Thread{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedWriter bufferedWriter;
		boolean reversalFlag = false;
		RSAEncryption rsaObj = null;
		
		ResourceBundleUtil rb=new ResourceBundleUtil();
		//String time = getTime("HHmmss");
		
		int cnt = 0;
		int i = 0;
		//Dynamic1
		String[] reqArr=new String[100];
		String[] txnArr=new String[100];
		//RSAEncryption encryption = new RSAEncryption();
		StringTokenizer txnToken=new StringTokenizer(rb.getValue("Txns"),",");
		//String URL ="http://10.44.120.60:7060/hdfcmpayservices/hdfcussd"; 
		String URL = "http://mobileprd.fssnet.co.in/ubimpayservices/ubiussd";
		//String URL ="https://10.44.120.60:7020/txnmpayservices/tmbussd"; 
		//String URL = "http://mobileprd.fssnet.co.in:80/iobmpayservices/iobussd";
		//String URL ="http://mobileprd.fssnet.co.in/mpayservices/vijussd"; 
		//String URL ="http://mobileprd.fssnet.co.in:80/iobmpayservices/iobussd"; 
		//String URL ="http://mobileprd.fssnet.co.in/mpayservices/unbiussd"; 
		System.out.println("URL:::"+URL);
		int recCnt=0;
		while(txnToken.hasMoreTokens()){
			String tempToken=txnToken.nextToken().trim();
			StringTokenizer tempTokens=new StringTokenizer(tempToken,"|");
			while(tempTokens.hasMoreTokens()){
				txnArr[recCnt]=tempTokens.nextToken().trim();
				reqArr[recCnt]=tempTokens.nextToken().trim();
				//System.out.println(">>"+txnArr[recCnt]+":"+reqArr[recCnt]);
			}
			recCnt++;
		}
		int opt=0;		
		
		System.out.println("Select(1 -"+recCnt+") from Following Transactions");
		System.out.println("**************************************************");
		//Print the Transaction headings
		for(int h=0;h<recCnt;h++){
			System.out.println(h+1+" . "+txnArr[h]);
		}

		//System.out.println("1.Balance Enquiry\n2.Mini Statement\n3.Mobile Topup\n4.FT-Beneficiary Confirm\n5.FT-Payment\n6.Purchase\n7.mPin Change\n8.IFP-Presentment\n9.IFP-Payment\n10.Reversal\n11.Registration\n12.DeRegistration\n13.Forgot mPIN\n14.Terminal Initialization Download");
		System.out.println("**************************************************");


			//new String[100];

		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        try {
			opt=Integer.parseInt(br.readLine().trim());
			System.out.println("TxnType::"+opt);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
		String RequestId = rb.getValue("RequestId");
		String MobileNo =  rb.getValue("MobileNo");
		String MasterShortCode=  rb.getValue("MasterShortCode");
		String SessionGUID =  rb.getValue("SessionGUID");
		//String SessionGUID_CMO =  rb.getValue("SessionGUID_CMO");
		//String SessionGUID_CMO="195747972044";
		String SessionGUID_CMO =  RandomStringUtils.randomNumeric(12);
		String RequestType =  rb.getValue("RequestType");
		String LanguageId =  rb.getValue("LanguageId");
		String OperatorName =  rb.getValue("OperatorName");
		String TimeStamp =  rb.getValue("TimeStamp");
		String IFSCCode = rb.getValue("IFSCCode");
		String ACCNO = rb.getValue("ACCNO");
		String BenMobileNo = rb.getValue("BENMOBILENO");
		String BenMMID = rb.getValue("BENMMID");
		String Amount = rb.getValue("AMOUNT");
		String mPin = rb.getValue("MPIN");
		String Remarks = rb.getValue("REMARKS");
		String BenIFSC = rb.getValue("Ben_IFSC");
		String Ben_Acc_no = rb.getValue("Ben_Acc_NO");
		String Ben_AaDhaar_No = rb.getValue("Ben_AaDhaar_no");
		String old_mPin = rb.getValue("old_mPin");
		String new_mPin = rb.getValue("new_mPin");
		String reEnter_mPin = rb.getValue("reEnter_mPin");
		String msgCode= "1";
		String msgText = "Your available account balance is Rs 5000.";
		
		rsaObj = new RSAEncryption(); 
		
		String encryptedmPin = null;
		String encryptedoldmPin = null;
		String encryptednewmPin = null;
		String encryptedreentermPin = null;
		
		encryptedmPin = rsaObj.encryptData(mPin).trim();
		
		encryptedmPin = encryptedmPin.replaceAll("(\\r\\n)", "");
		
		//while (true)
		//{
			if(opt > 0){
				String req ="<?xml version='1.0' encoding='utf-8'?>"+
				"<RequestObject>"+
					"<RequestId>"+RequestId+"</RequestId>"+
					"<MobileNo>"+MobileNo+"</MobileNo>"+
					"<MasterShortCode>"+MasterShortCode+"</MasterShortCode>"+
					"<SessionGUID>"+SessionGUID+"</SessionGUID>"+
					"<SessionGUID_CMO>"+SessionGUID_CMO+"</SessionGUID_CMO>"+
					"<RequestType>"+RequestType+"</RequestType>"+
					"<LanguageId>"+LanguageId+"</LanguageId>"+
					"<OperatorName>"+OperatorName+"</OperatorName>"+
					"<TimeStamp>"+TimeStamp+"</TimeStamp>";
				String rsp ="<?xml version='1.0' encoding='utf-8'?>"+
				"<RequestObject xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>"+
					"<RequestId>"+RequestId+"</RequestId>"+
					"<MobileNo>"+MobileNo+"</MobileNo>"+
					//"<MasterShortCode>"+MasterShortCode+"</MasterShortCode>"+
					"<SessionGUID>"+SessionGUID+"</SessionGUID>"+
					"<SessionGUID_CMO>"+SessionGUID_CMO+"</SessionGUID_CMO>"+
					//"<RequestType>"+RequestType+"</RequestType>"+
					"<LanguageId>"+LanguageId+"</LanguageId>"+
					"<OperatorName>"+OperatorName+"</OperatorName>"+
					"<TimeStamp>"+TimeStamp+"</TimeStamp>";
				
				if(reqArr[opt-1].startsWith("MPBAL")){//Balance Enquiry...
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>BalanceEnquiry</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
					
				}else if(reqArr[opt-1].startsWith("MPMS")){///Mini Statement
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>MiniStatement</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>2</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
					
				}else if(reqArr[opt-1].startsWith("MMIDPAY")){//Fund Transfer - MMID....
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>FTMMID</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>3</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>Beneficiary_Mobile</ParamName>"+
						   	"<ParamValue>"+BenMobileNo+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>6</ParamId>"+
						   	"<ParamName>Beneficiary_MMID</ParamName>"+
						   	"<ParamValue>"+BenMMID+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>7</ParamId>"+
						   	"<ParamName>Amount</ParamName>"+
						   	"<ParamValue>"+Amount+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>8</ParamId>"+
						   	"<ParamName>Remark</ParamName>"+
						   	"<ParamValue>"+Remarks+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>9</ParamId>"+
						   	"<ParamName>Mpin</ParamName>"+
						   	"<ParamValue>"+encryptedmPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>10</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
					
				}else if(reqArr[opt-1].startsWith("ACCPAY")){//Fund Transfer - Account No....
					
					 req = req +"<RequestParams>"+
						"<Param>"+
						"<ParamId>1</ParamId>"+
						"<ParamName>EventName</ParamName>"+
						"<ParamValue>FTIFSC</ParamValue>"+
						"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>2</ParamId>"+
					   	"<ParamName>NPCI - Main Menu</ParamName>"+
					   	"<ParamValue>1</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>3</ParamId>"+
					   	"<ParamName>Enter IFSC Code</ParamName>"+
					   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>4</ParamId>"+
					   	"<ParamName>MainMenu</ParamName>"+
					   	"<ParamValue>4</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>5</ParamId>"+
					   	"<ParamName>Beneficiary_IFSC</ParamName>"+
					   	"<ParamValue>"+BenIFSC+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>6</ParamId>"+
					   	"<ParamName>Beneficiary_A/c-No</ParamName>"+
					   	"<ParamValue>"+Ben_Acc_no+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>7</ParamId>"+
					   	"<ParamName>Amount</ParamName>"+
					   	"<ParamValue>"+Amount+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>8</ParamId>"+
					   	"<ParamName>Remark</ParamName>"+
					   	"<ParamValue>"+Remarks+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>9</ParamId>"+
					   	"<ParamName>Mpin</ParamName>"+
					   	"<ParamValue>"+encryptedmPin+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
					   "<Param>"+
					   	"<ParamId>10</ParamId>"+
					   	"<ParamName>A/c-No</ParamName>"+
					   	"<ParamValue>"+ACCNO+"</ParamValue>"+
					   	"<ParamType>String</ParamType>"+
					   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
				}else if(reqArr[opt-1].startsWith("AADPAY")){//Fund Transfer - Aadhaar....
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>FTAadhaar</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>5</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>Beneficiary_AaDhaar-No</ParamName>"+
						   	"<ParamValue>"+Ben_AaDhaar_No+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>6</ParamId>"+
						   	"<ParamName>Amount</ParamName>"+
						   	"<ParamValue>"+Amount+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>7</ParamId>"+
						   	"<ParamName>Remark</ParamName>"+
						   	"<ParamValue>"+Remarks+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>8</ParamId>"+
						   	"<ParamName>Mpin</ParamName>"+
						   	"<ParamValue>"+encryptedmPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>9</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
				}else if(reqArr[opt-1].startsWith("KNOMMID")){//Know MMID....
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>KnowMMID</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>6</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
				}else if(reqArr[opt-1].startsWith("CMPIN")){//Change M-Pin....
					
					encryptedoldmPin = rsaObj.encryptData(old_mPin);
					encryptednewmPin = rsaObj.encryptData(new_mPin);
					encryptedreentermPin = rsaObj.encryptData(reEnter_mPin);
					
					encryptedoldmPin = encryptedoldmPin.replaceAll("(\\r\\n)", "");
					encryptednewmPin = encryptednewmPin.replaceAll("(\\r\\n)", "");
					encryptedreentermPin = encryptedreentermPin.replaceAll("(\\r\\n)", "");
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>ChangeMPin</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>7</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>Old-Mpin</ParamName>"+
						   	"<ParamValue>"+encryptedoldmPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>6</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>7</ParamId>"+
						   	"<ParamName>New-Mpin</ParamName>"+
						   	"<ParamValue>"+encryptednewmPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>8</ParamId>"+
						   	"<ParamName>R-Enter-Mpin</ParamName>"+
						   	"<ParamValue>"+encryptedreentermPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
				}if(reqArr[opt-1].startsWith("GENOTP")){//Generate OTP....
					
					 req = req +"<RequestParams>"+
						   "<Param>"+
							"<ParamId>1</ParamId>"+
							"<ParamName>EventName</ParamName>"+
							"<ParamValue>GenerateOTP</ParamValue>"+
							"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>2</ParamId>"+
						   	"<ParamName>NPCI - Main Menu</ParamName>"+
						   	"<ParamValue>1</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>3</ParamId>"+
						   	"<ParamName>Enter IFSC Code</ParamName>"+
						   	"<ParamValue>"+IFSCCode+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>4</ParamId>"+
						   	"<ParamName>MainMenu</ParamName>"+
						   	"<ParamValue>8</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>5</ParamId>"+
						   	"<ParamName>Mpin</ParamName>"+
						   	"<ParamValue>"+encryptedmPin+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						   "<Param>"+
						   	"<ParamId>6</ParamId>"+
						   	"<ParamName>A/c-No</ParamName>"+
						   	"<ParamValue>"+ACCNO+"</ParamValue>"+
						   	"<ParamType>String</ParamType>"+
						   "</Param>"+
						"</RequestParams>"+
					"</RequestObject>";
				}
				String line = null;
				HttpClient client = null;
				String txnID = "";
				String statusMessage = "";
				//String message = URL+req;
				String ackStatus = "";
				//String req1="<?xml version=\"1.0\" encoding=\"utf-8\"?><RequestObject>  <RequestId>20726777</RequestId>  <MobileNo>918286868677</MobileNo>  <MasterShortCode>99*1</MasterShortCode>  <SessionGUID>20726777</SessionGUID>  <SessionGUID_CMO>170823488677</SessionGUID_CMO>  <RequestType>20726777</RequestType>  <LanguageId>0</LanguageId>  <OperatorName>Aircel</OperatorName>  <TimeStamp>2014-08-05T17 8:23.0000488Z</TimeStamp>  <RequestParams>    <Param>      <ParamId>1</ParamId>      <ParamName>EventName</ParamName>      <ParamValue>BalanceEnquiry</ParamValue>      <ParamType>String</ParamType>    </Param>    <Param>      <ParamId>4</ParamId>      <ParamName>NPCI - Main Menu</ParamName>      <ParamValue>1</ParamValue>      <ParamType>String</ParamType>    </Param>    <Param>      <ParamId>2</ParamId>      <ParamName>Enter IFSC Code</ParamName>      <ParamValue>ubin</ParamValue>      <ParamType>String</ParamType>    </Param>    <Param>      <ParamId>3</ParamId>      <ParamName>MainMenu</ParamName>      <ParamValue>1</ParamValue>      <ParamType>string</ParamType>    </Param>  </RequestParams></RequestObject>";
				Simulator simulator = new Simulator();
				simulator.simulate(req,URL);
				/*MultiThreadedHttpConnectionManager mgr = new MultiThreadedHttpConnectionManager();
				mgr.getParams().setDefaultMaxConnectionsPerHost(1000);
				mgr.getParams().setMaxTotalConnections(1000);
				mgr.getParams().setConnectionTimeout(20000);

				client = new HttpClient(mgr);
				client.getHttpConnectionManager().getParams().setSoTimeout(30000);
				PostMethod authpost = null;
				StringBuffer url = new StringBuffer();
				
				
					
					url.delete(0, url.length());
					url.append(message);
					System.out.println("url.toString()========"+url.toString());
					authpost = new PostMethod(url.toString());
					int status = client.executeMethod(authpost);
					if (status == HttpStatus.SC_OK) {
						
						BufferedReader in = new BufferedReader(new InputStreamReader(
								authpost.getResponseBodyAsStream()));
						while ((line = in.readLine()) != null) {
							System.out.println("line:::::::::"+line);
							StringTokenizer strTokens = new StringTokenizer(line, "|");
							//arr = line.split("|");
							if (strTokens.hasMoreTokens()) {
								txnID = strTokens.nextToken().toString();
								statusMessage = strTokens.nextToken().toString();
							}
						}
                        line=in.readLine();
						System.out.println("response========"+line.substring(line.lastIndexOf("<MsgText>")+9,line.indexOf("</MsgText>")));
//						if(statusMessage.equalsIgnoreCase("Success")){
//							ackStatus = "C";
//						}else{
//							ackStatus = "F";
//						}
					}*/
				//rsp +=rsp+"<MsgCode>"+msgCode+"</MsgCode>"+"<MsgText>"+msgText+"</MsgText>";
				
				//System.out.println("Request::::\n"+statusMessage);
				//System.out.println("Responce::::\n"+ackStatus);
			}
			//Thread.sleep (80000);
		//}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
