/**
 * @(#)MPaySimulator.java	1.0a 25/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */
package api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import simulator.HostSimulator;

/**
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @since
 * @version
 * @created date: Apr 25, 2007 6:16:20 PM
 */
public class MPaySimulator implements Processor {

	private String cardNumber;

	private String fromCardNo;

	private Connection connection;

	private String details = null;

	private Hashtable<String, String> isoBuffer;

	private M24ISOMethods m24ISOMethods;

	private M24Utility m24Utility;

	private ResultSet resultSet;

	private StringBuffer sqlBuffer;

	private Statement statement;

	private String type = null;

	public void clear() {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			resultSet = null;
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			statement = null;
		}
	}

	private ResultSet doSelect() throws RequestException, SQLException {

		if (connection == null) {
			throw new RequestException("Connection is null");
		}

		clear();

		statement = connection.createStatement();
		System.out.println("select query :: "+sqlBuffer.toString());
		resultSet = statement.executeQuery(sqlBuffer.toString());

		return resultSet;
	}

	private int doUpdate() throws RequestException, SQLException {

		if (connection == null) {
			throw new RequestException("Connection is null");
		}

		clear();

		statement = connection.createStatement();
		final int count = statement.executeUpdate(sqlBuffer.toString());
		System.out.println("Query :: "+sqlBuffer);
		System.out.println("UPDATED COUNT :: "+count);
		return count;
	}

	private void fetchCardDetails() throws RequestException, SQLException,
															ProcessException {
		// For testing

		if(isoBuffer.get("S-102").toString().trim().equals("*")){

			isoBuffer.put("S-102","317802070054600");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE ACCOUNTNO='"+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		String _dbAmount = "0";
		//Added by Karthik.S
		String avlInd= getProp("AVL_IND");
		String ldgInd= getProp("LDG_IND");
		System.out.println(avlInd+":::"+ldgInd);
		if(avlInd.equals("+")){
		avlInd="0";
		}
if(ldgInd.equals("+")){
		ldgInd="0";
		}

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = resultSet.getString("BALANCEAMT");
				if(!_dbAmount.contains(".")){
		_dbAmount=_dbAmount+".00";
				}
				//editied by brantansp - _dbAmount
				//_dbAmount =  m24Utility.getFormattedAmount(_dbAmount, 2);
				_dbAmount="100000000020";
				System.out.println("Amount :: "	+ _dbAmount);
     	//_dbAmount= StringUtils.leftPad(_dbAmount.substring(1),11,'0');
			_dbAmount= _dbAmount.substring(1);
		System.out.println("Amount1 :: "	+ _dbAmount);
		isoBuffer.put("P-44", "253"+ ldgInd+_dbAmount+avlInd+_dbAmount);
/*
				isoBuffer.put("P-44", "253"
						+ "0000000000000068"+StringUtils.leftPad(m24Utility.getFormattedAmount(
								_dbAmount, 2), 11, '0')+"0000000000000098"+ StringUtils.leftPad(m24Utility.getFormattedAmount(
										_dbAmount, 2), 11, '0'));
*/
				isoBuffer.put("S-102", StringUtils.rightPad(resultSet
						.getString("ACCOUNTNO"), 28));
			}
			else {
				throw new ProcessException("69");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}
	}

	public void fetchMiniStatement() throws RequestException, SQLException,
															ProcessException {

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT TYPE, AMOUNT, DETAILS, TXNDATE FROM ");
		sqlBuffer.append("STATEMENT WHERE ROWNUM <= 9 AND ACCOUNTNO='" + isoBuffer.get("S-102").toString().trim() + "' ORDER BY TXNDATE DESC");

		doSelect();

		final StringBuffer statementBuffer = new StringBuffer();
		int length =0;
		if (resultSet != null) {
		System.out.println("tetsetset :: ");
		
			while (resultSet.next()) {
				String type = resultSet.getString("TYPE");
				final String amount = resultSet.getString("AMOUNT");
				final String details = resultSet.getString("DETAILS");
				final Date _date = resultSet.getDate("TXNDATE");
                //INDUSIND
                if(isoBuffer.get("P-32").trim().equals("800004")||isoBuffer.get("P-32").trim().equals("444445")){
                // statementBuffer.append(" ");
                statementBuffer.append(StringUtils.rightPad(getTime("dd/MM/yy", _date), 11));
				statementBuffer.append(StringUtils.rightPad("", 11));
				statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type + "", 1), 3));
				statementBuffer.append(StringUtils.leftPad(amount + "", 10));
				System.out.println("****111****"+statementBuffer.toString());

				}else  if(isoBuffer.get("P-32").trim().equals("404322")){

					statementBuffer.append(StringUtils.rightPad(getTime("dd/MM", _date), 5));
					statementBuffer.append(" ");
					statementBuffer.append(StringUtils.rightPad("KVBMPY", 6));
					statementBuffer.append(" ");
					if(type.trim().equals("CR")){
						type="C";
					}else{
						type ="D";
					}					
					statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type, 1), 1));
					statementBuffer.append(StringUtils.leftPad(amount, 12));				
					
					
					if(length==0){
						length=statementBuffer.length();
					}
					
					
					System.out.println("****"+statementBuffer.toString());
				
				}else  if(isoBuffer.get("P-32").trim().equals("421430")){

					statementBuffer.append(StringUtils.rightPad(getTime("dd/MM/yy", _date), 8));
					statementBuffer.append(" ");
					statementBuffer.append(StringUtils.rightPad("UCOMPY", 6));
					statementBuffer.append(" ");
					if(type.trim().equals("CR")){
						type="C";
					}else{
						type ="D";
					}					
					statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type, 1), 1));
					statementBuffer.append(StringUtils.leftPad(amount, 12));				
					
					
					if(length==0){
						length=statementBuffer.length();
					}
					
					
					System.out.println("****"+statementBuffer.toString());
				
				}else  if(isoBuffer.get("P-32").trim().equals("504432")||isoBuffer.get("P-32").trim().equals("504482")){

					statementBuffer.append(StringUtils.rightPad(getTime("yyyyMMdd", _date), 8));
					statementBuffer.append(" ");
					statementBuffer.append(StringUtils.rightPad("UMOBILE", 7));
					statementBuffer.append(" ");
					if(type.trim().equals("CR")){
						type="C";
					}else{
						type ="D";
					}					
					statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type, 1), 1));
					statementBuffer.append(StringUtils.leftPad(amount, 12));				
					
					
					if(length==0){
						length=statementBuffer.length();
					}
					
					
					System.out.println("****"+statementBuffer.toString());
				
				}
				else  if(isoBuffer.get("P-32").trim().equals("454545")|| isoBuffer.get("P-32").trim().equals("504461")){

					statementBuffer.append(StringUtils.rightPad(getTime("yyyyMMdd", _date), 8));
					statementBuffer.append(" ");
					statementBuffer.append(StringUtils.rightPad("VMOBILE", 7));
					statementBuffer.append(" ");
					if(type.trim().equals("CR")){
						type="C";
					}else{
						type ="D";
					}					
					statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type, 1), 1));
					statementBuffer.append(StringUtils.leftPad(amount, 12));				
					
					if(length==0){
						length=statementBuffer.length();
					}
					
					
					System.out.println("****"+statementBuffer.toString());
				
				}
			else{
				statementBuffer.append(StringUtils.rightPad(getTime("yyyyMMdd", _date), 8));
				statementBuffer.append(" ");
				statementBuffer.append(StringUtils.rightPad("MCONNECT", 8));
				//changed by Karthik
				//Amount - LEFT PAD to 12 Bytes
				//statementBuffer.append(" ");
				//statementBuffer.append(amount);				
				statementBuffer.append(StringUtils.leftPad(amount, 12));
				statementBuffer.append(" ");
				if(type.trim().equals("CR")){
					type="C";
				}else{
					type ="D";
				}
				statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type, 1), 1));
				
				if(length==0){
					length=statementBuffer.length();
				}
				
				
				System.out.println("****"+statementBuffer.toString());
		      	}
			}
			if (statementBuffer.length() <= 0) {
				throw new ProcessException("68");
			}
			else {
				String bal=getBalance(isoBuffer.get("S-102").toString().trim());
				System.out.println("Balance : "+bal);
				if(isoBuffer.get("P-32").trim().equals("404322")){
					isoBuffer.put("S-125", "1P06122701"+length+statementBuffer.toString()+"AVAIL BAL        "+bal);
				}else if(isoBuffer.get("P-32").trim().equals("421430")){
					isoBuffer.put("S-125", "1P06122701"+length+statementBuffer.toString()+"AVAIL BAL +000"+bal);
				}else if(isoBuffer.get("P-32").trim().equals("504432")){
					isoBuffer.put("S-125", "1P06122701"+length+statementBuffer.toString()+"AVAIL AMT Rs.00"+bal);
				}			
				else{
				
				if(!bal.contains(".")){
				bal=bal+".00";
				}
				if(getProp("MIN_IND")!=null && getProp("MIN_IND").equals("+")){
				//isoBuffer.put("S-125", "1P06122701"+length+statementBuffer.toString()+"AVL  BALANCE: RS.       "+bal); //+" THANKYOU FOR BANKING WITH US");
					if(isoBuffer.get("P-32").trim().equals("403362"))
					{
						//ABK bank Mini statement
						isoBuffer.put("S-125", "1P000000003506/10/17 UPI/727901  D         2.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  D        10.0005/10/17 IMPS/72781  D         1.0005/10/17 TO 3125010  D         1.0005/10/17 ATMD OTBANKINGSRVR C         1.0005/10/17 IMPS/72781  D         1.00           LEDGER BAL +000000186.25            AVL  BALANCE: RS. +"+bal);//000000001.25");//+length+statementBuffer.toString()+"AVAIL BAL"+bal); //+" THANKYOU FOR BANKING WITH US");
					}else{
					isoBuffer.put("S-125", "1P000000003506/10/17 UPI/727901  D         2.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  D        10.0005/10/17 IMPS/72781  D         1.0005/10/17 TO 3125010  D         1.0005/10/17 ATMD OTBANKINGSRVR C         1.0005/10/17 IMPS/72781  D         1.00           LEDGER BAL +000000186.25            AVAIL BAL +000000000.25");//+length+statementBuffer.toString()+"AVAIL BAL"+bal); //+" THANKYOU FOR BANKING WITH US");
					}
				//AVAIL BAL
				}else{
				
				//isoBuffer.put("S-125", "1P06122701"+length+statementBuffer.toString()+"AVL  BALANCE: RS.       "+getProp("MIN_IND")+bal);//+" THANKYOU FOR BANKING WITH US");
				isoBuffer.put("S-125", "1P000000003506/10/17 UPI/727901  D         2.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  C        10.0006/10/17 UPI/727901  D        10.0005/10/17 IMPS/72781  D         1.0005/10/17 TO 3125010  D         1.0005/10/17 ATMD OTBANKINGSRVR C         1.0005/10/17 IMPS/72781  D         1.00           LEDGER BAL +000000186.25            AVAIL BAL +000000000.25"); //+length+statementBuffer.toString()+"AVAIL BAL"+getProp("MIN_IND")+bal);//+" THANKYOU FOR BANKING WITH US");
				    }
				}
			}
		}
		else {
			throw new ProcessException("30");
		}
	}

	private void fundTransfer() throws RequestException, SQLException,
																ProcessException {

		/*try {
			Thread.sleep(45000);
		}
		catch(Exception e) {
		}
		if (true) {
			throw new ProcessException("00");
		}*/
		final double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT FROM BALANCE ");
		sqlBuffer.append("WHERE ACCOUNTNO='"+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		double _dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
										.getString("BALANCEAMT"));
			} else {
				throw new ProcessException("69");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		System.out.println("_dbAmount :: " + _dbAmount);

		/*try{
			Thread.sleep(45000);
		}
		catch(Exception e){
		}*/

		if( _dbAmount > amount) {

			System.out.println("AMOUNT DEDUCTION :::: "+amount);
			_dbAmount = _dbAmount - amount;
		}
		else {
			throw new ProcessException("51");
		}

		System.out.println("AMOUNT AFTER DEDUCTION :::: "+_dbAmount);
		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("UPDATE BALANCE SET BALANCEAMT = '" + _dbAmount + "' ");
		sqlBuffer.append("WHERE ACCOUNTNO='"+ isoBuffer.get("S-102").toString().trim() + "'");

		if (doUpdate() != 1) {
			throw new RequestException(
			"Unable to update the database for mini statement");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT, CARDNO FROM BALANCE WHERE ");
		sqlBuffer.append("ACCOUNTNO='"
				+ isoBuffer.get("S-103").toString().trim() + "'");

		doSelect();

		_dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet.getString("BALANCEAMT"));
				fromCardNo = resultSet.getString("CARDNO");
			}
			else {
				throw new ProcessException("69");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		System.out.println("_dbAmount :: " + _dbAmount);

		_dbAmount = _dbAmount + amount;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("UPDATE BALANCE SET BALANCEAMT = '" + _dbAmount + "' ");
		sqlBuffer.append("WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-103").toString().trim() + "'");

		if (doUpdate() == -1) {
			throw new RequestException(
			"Unable to update the database for mini statement");
		}
	}

	private Connection getConnection() throws SQLException {
		return java.sql.DriverManager.getConnection("jdbc:apache:commons:dbcp:"
				+ HostSimulator.conf.getString("TOKEN"));
	}

	public String process1(final String message) {

		String response = "";
		String tag = "";
		String authId = "";
		String mobileNumber = "";
		String bankId = "";
		String rrn = "";

		System.out.println("Request :::: "+message);

		StringTokenizer token = new StringTokenizer(message, " ");

		try {
			//Thread.sleep(450000);
		}
		catch(Exception e) {
		}

		while( token.hasMoreTokens() ) {
			//System.out.println("Token :: "+token.nextToken());

			tag				= token.nextToken();
			authId 			= token.nextToken();
			mobileNumber 	= token.nextToken();
			bankId 			= token.nextToken();

			if( tag.equalsIgnoreCase("APPC") || tag.equalsIgnoreCase("APPR")) {
				token.nextToken();
				token.nextToken();
			}

			response = tag+"00 "+authId+" "+mobileNumber+" "+bankId+" N";
			//response = tag+"45 "+authId+" "+mobileNumber+" "+bankId+" N";
		}
		//response = "APPC00 002806070726 9333333333 123456 N";

		System.out.println("Response :: "+response);

		return response;
	}

	public String process(final String message) {

		m24ISOMethods = new M24ISOMethods();

		m24Utility = new M24Utility();

		sqlBuffer = new StringBuffer();

		String response = null;

		System.out.println("Message :: "+message);

		try {
			String t= getProp("TH_SLEEP");			
			isoBuffer = m24ISOMethods.ISOFormatter87(message);
			System.out.println("ISO BUFFER ::::::"+isoBuffer);
			if (!("0420".equals(isoBuffer.get("MSG-TYP").toString().trim()))) {
			Thread.sleep(Long.parseLong(t));
			}
			
		} catch (final Exception e) {
			//e.printStackTrace();
		}



		try {

			//System.out.println("P-35 :::"+isoBuffer.get("P-35").toString());
			//isoBuffer.put("P-35","5050500007890000=");
			if(isoBuffer.get("P-35").toString().length() == 0){

				isoBuffer.put("P-35","50443270007890000");

			}
			//Thread.sleep(10000000);
			
			
			if ("0200".equals(isoBuffer.get("MSG-TYP").toString().trim())) {
				//Thread.sleep(90000);
				System.out.println("CARD NO =="+isoBuffer.get("P-35").toString());

			if(isoBuffer.get("P-35").toString().indexOf("=") != -1){

				cardNumber = isoBuffer.get("P-35").toString().substring(0,
						isoBuffer.get("P-35").toString().indexOf("="));

			}else{
				cardNumber = isoBuffer.get("P-35").toString();

			}


				connection = getConnection();

				System.out.println("P-3 :: "
						+ isoBuffer.get("P-3").toString().trim().substring(0, 2));

				if (isoBuffer.get("P-3").toString().trim().substring(0, 2)
						.equals("31")
						|| isoBuffer.get("P-3").toString().trim().substring(0,
								2).equals("94")
								|| isoBuffer.get("P-3").toString().trim().substring(0,
										2).equals("96") || isoBuffer.get("P-3").toString().trim().substring(0,
										2).equals("83")) {
					System.out.println("Inside fetch card details block");
					fetchCardDetails();
					if (isoBuffer.get("P-3").toString().trim().substring(0, 2)
							.equals("31")){
					
						/*String tempS125=isoBuffer.get("S-125").toString().trim();
						String isoS125="3000006694796000006694796";
						//isoBuffer.put("P-32", "448899");
						isoBuffer.put( "P-44",isoS125);*/
						response = m24ISOMethods.buildResponse("0210", "00",isoBuffer);
						return response;
					}
					if (isoBuffer.get("P-3").toString().trim().substring(0, 2)
							.equals("94")){
						fetchMiniStatement();
						/*String tempS125=isoBuffer.get("S-125").toString().trim();
						String isoS125="1P1210080535           08/10/12 CR      2000.00           01/08/12 CR     50000.00           31/07/12 DR   1151544.91           05/06/12 DR    946347.23           05/06/12 CR    200000.00-----------------------------------AVL.BAL                  1019832.32                                                                                                                   ";
						isoBuffer.put( "S-125",isoS125);*/
						response = m24ISOMethods.buildResponse("0215", "00",isoBuffer);
						return response;
					}
					
					
				}
				else if (
					isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("53") || isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MB") || isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MF")) {


					//Thread.sleep(450000);
					System.out.println("Inside update balance block");
					
					if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MB") && isoBuffer.get("P-32").trim().equals("504432")){
						
						String S125=isoBuffer.get("S-125").toString();
						if(S125.contains("|")){
						
						String S125nw="";
						StringTokenizer str =new StringTokenizer(S125,"|");
						List<String> data=new ArrayList<String>();
						while(str.hasMoreTokens()){
							data.add(str.nextToken());	
						}
						String rrn =RandomStringUtils.randomNumeric(12);
						data.remove(3);
						data.add(3,rrn);
						for(String st:data){
							S125nw+=st+"|";
						}
			
						isoBuffer.put("S-125", S125nw);
						}
					}
					details = "FUND";
					type = "DR";
					fundTransfer();
				} else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("95")){

					response = "ISO0660000150210F33880012AF5801800000000160000481642145811739999339500000000000000000224150232        0024581502320224022411111111     374214581173999933=9912000000000000000000245809022400REMOTBANKINGSRVR000000000000000UBI                MUMBAI       MH 91   27252            000057752080012000000000000044A                       40035635711111111111   015MPAYCER11234   01111000325013131234ABN111  P281825101012034               19                   0101234567890007P";
					return response;

					//ISO0160000150210F23A80012EA080180000000016000008165555550100000019711010000000010000091013201545850218545209100910091011800004     315555550100000019=1508126000676032531845850245850200REMOTBANKINGSRVRINDUSIND              MUMBAI       MH IN356016INDSMPAY1234    013INDSMITR    P0650459412100000248777140099356461004426202107157069840535311  9840535311         N0000000Your fees payment for Rs 100.00 successful for the institution VIT,category BSC BOOK FEES                                                   2325|VIT|BSC BOOK FEES| |000000010000|Arul                               
					
				} else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MA")){

					String rrn = isoBuffer.get("P-37").toString().trim();
					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					/*String p_35=isoBuffer.get("P-35");
					p_35="504594"+p_35.substring(6);*/
					//isoBuffer.put("P-35", p_35);
					
					
					System.out.println("P_32::"+P_32);
					//isoBuffer.put("P-32","800004");
					String tempS125=isoBuffer.get("S-125").toString().trim();
					if(tempS125.startsWith("01")){
					tempS125+="PPaid Cheque";
					}else{
					tempS125+="SStopped cheque";
					}
					isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));
					response = m24ISOMethods.buildResponse("0210", "00",isoBuffer);

					return response;


				} else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("88")){

					response = "ISO0160000170210F23A80012EB080180000000014000048164213684285000481MA1000000000000000061417111001050822411206140614061411504432     314213684285000481=12051264670000010508090614SIN00000REMOTBANKINGSRVRUBI                MUMBAI        MH 91  253000000000000000000000000356012DVLPMPAY1234013UBSBUBSB    P065044321542850201027033501600000000001000212000100P160000000000100021                                                                                                                                                                                  ";
					return response;
				}

				else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("00")){

					if(true){
					throw new ProcessException("00");

					}
    				String rrn = isoBuffer.get("P-37").toString().trim();
					//String tempRsp = "ISO0260000170210B23E84812EA1801A00000000140000BC001000000000015000040911085909902416415904090409040904098888811504432     314213683016018648=14061263840000100201044261UNI00000REMOTBANKINGSRVRUBI                MUMBAI       MH 91   02700000000000000000010001Chen356016MPAYCER11234    019SBUBSBUB00000000000048896523                                          065044321530160202001843802078562             F0015012MPAY        009123456   012V STB2BICI0 038000                                111 ";
					//String tempRsp = "ISO0160000150210F23880012EA180180000000016000028165044619016877619MB101000000002000002011650271002011650270201020111504432     275044619016877619=130912644610020104427510020100REMOTBANKINGSRVRUBI                MUMBAI       MH 91   044A                       40035635711111111111356016MPAYCER11234    01111000325013131234ABN111  P140129201001681015317802070054179015012MPAY        2000110Testregistration              317802070054179    10HS TAKOLA                     01292010016810     10                                                                                              ";
					//response = tempRsp.replace(tempRsp.substring(151,163).toString(),rrn);
					return response;
					//response = "ISO0260000170210B23E84812EA1801A00000000140000BC001000000000015000040911085909902416415904090409040904098888811504432     314213683016018648=14061263840000100201044261UNI00000REMOTBANKINGSRVRUBI                MUMBAI       MH 91   02700000000000000000010001Chen356016MPAYCER11234    019SBUBSBUB00000000000048896523                                          065044321530160202001843802078562             F0015012MPAY        009123456   012V STB2BICI0 038000                                111 ";
					//return response;

				} else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MC")){

					String rrn = isoBuffer.get("P-37").toString().trim();
					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					System.out.println("P_32::"+P_32);
                    String tempS125=isoBuffer.get("S-125").toString().trim();
                    isoBuffer.put("P-39", "91");
					if(P_32.trim().equals("800004")){
						tempS125+="IND00100"+"IndusInd Bank"+"SBSTF";
					}else{
					tempS125+="TEST CARD MPURSE FSEH                                                           (OLD NO.4) NEW NO.5, WESTMADA STREET, SRINAGAR COLONY,SAIDAPET,CHENNAI    TAMIL NADU600015INDIA                      ";
					}
					isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));

					//String tempRsp = "ISO0160000150210F23880012EB080180000000014000028165044323005005251MC100000000000000005010515561005011046350501050111"+P_32+"274213413005005251=120712693310050162323035535100REMOTBANKINGSRVRVIJAYA BANK        BANGALORE            253000002119495000002119495356012VIJBMPAY1234013VIJBPRO11100P0650446115300501010015685015012MPAY        2000100TEST CARD MPURSE FSEH                                                           (OLD NO.4) NEW NO.5, WESTMADA STREET, SRINAGAR COLONY,SAIDAPET,CHENNAI    TAMIL NADU600015INDIA                      ";
					//response = tempRsp.replace(tempRsp.substring(156,168).toString(),rrn);
					response = m24ISOMethods.buildResponse("0210", "00",isoBuffer);
					return response;
				}else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MM")){
				isoBuffer.put("P-39","00");
				isoBuffer.put("S-125","UMOBILE|ACIS|UNREGISTERED MOBILE.NO|9791060580|123456898989|XYZ|");

				}else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("CP")){

					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					isoBuffer.put("P-32",P_32);
					isoBuffer.put("P-39","00");
					isoBuffer.put("S-102","*");
					String tempS125=isoBuffer.get("S-125").trim();
					if(tempS125.startsWith("01")){
					tempS125+="YN                 ";//Account Indicator Flag
					tempS125+="002";//No. Of accounts
                    //13AccNo//AccType2//AccText35
                    tempS125+="IND0000000123EESavings                       ";//Acc1
                    tempS125+="IND0000000124ECView account                  ";//Acc2
					}else{
						tempS125=StringUtils.rightPad(tempS125, 20);
						tempS125+="TG1234567890YYYYH00";

					}


					isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));
					response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
						.toString().trim(), "00", isoBuffer);

					//response = "ISO0660000150210F33880012AF5801800000000160000481642145811739999339500000000000000000224150232        0024581502320224022411111111     374214581173999933=9912000000000000000000245809022400REMOTBANKINGSRVR000000000000000UBI                MUMBAI       MH 91   27252            000057752080012000000000000044A                       40035635711111111111   015MPAYCER11234   01111000325013131234ABN111  P281825101012034               19                   0101234567890007P";
					return response;

				}else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("NE")){



    				String rrn = isoBuffer.get("P-37").toString().trim();
					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					System.out.println("P_32::"+P_32);
					isoBuffer.put("P-32",P_32);
					String tempS125=isoBuffer.get("S-125").toString().trim();
					tempS125+="          ";//Mobile number
					tempS125+=RandomStringUtils.randomNumeric(16);//Reg no
					isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));
					response = m24ISOMethods.buildResponse("0210", "00",isoBuffer);

					return response;

				}else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MT")){

					System.out.println("UBI- MT Request");
					String cardNumber = isoBuffer.get("P-2");
					String P_35= cardNumber+"=1602121419";
					isoBuffer.put("P-35",P_35);		

					response = m24ISOMethods.buildResponse("0210", "00",isoBuffer);

					return response;

				}




				else {

					if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("00")) {
						System.out.println("Inside update balance block1");
						details = "PURCH";
						type = "DR";
					}
					updateBalance();
				}

				if (isoBuffer.get("P-3").toString().trim().substring(0, 2)
						.equals("31")
						|| isoBuffer.get("P-3").toString().trim().substring(0,
								2).equals("94")
								|| isoBuffer.get("P-3").toString().trim().substring(0,
										2).equals("96")) {
					// Ignore this block
				}
				else {
					System.out
					.println("Inside update MINI STATEMENT balance block");
					updateForMiniStatement();
				}

				//isoBuffer.put("S-100", "11000325013");


				Iterator bitDetails = HostSimulator.bitProperty.getKeys();

				while( bitDetails.hasNext() ) {
					String key = bitDetails.next().toString();
					isoBuffer.put(key, HostSimulator.bitProperty.getString(key));
				}

				if (isoBuffer.get("P-3").toString().substring(0, 2).equals("94")||isoBuffer.get("P-3").toString().substring(0, 2).equals("70")) {

					fetchBalance();

					fetchMiniStatement();

					response = m24ISOMethods.buildResponse("0215", "00",
																isoBuffer);
					return response;
				}

				if("404322".equals(isoBuffer.get("P-32").toString().trim()) && "SY".equals(isoBuffer.get("P-3").toString().substring(0, 2)))
				{
					System.out.println("KVB bank Account Sync");
					isoBuffer.put("P-38","UNI000");
					isoBuffer.put("S-125","010013550650  989405989501174113500000223810	");
					//9894059895 01 1741135000002238
				}
//CHANGE HERE FOR IMPS DECLINE
				response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
						.toString().trim(), "00", isoBuffer);
				//Thread.sleep(45000);
				System.out.println("after building the request");
			}
			else if ("0420".equals(isoBuffer.get("MSG-TYP").toString().trim())) {

				connection = getConnection();

				//Thread.sleep(45000);


				if (!isoBuffer.get("P-3").toString().trim().substring(0, 2)
						.equals("31")
						|| !isoBuffer.get("P-3").toString().trim().substring(0,	2)
							.equals("94")
								|| !isoBuffer.get("P-3").toString().trim().substring(0,	2)
									.equals("96")) {
					revertUpdateBalance();
				}

				response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
						.toString().trim(), "00", isoBuffer);
				// Thread.sleep(35000);
			}
			else {
				response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
						.toString().trim(), "00", isoBuffer);
			}
		}
		catch (final ProcessException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), e.getMessage(), isoBuffer);
		}
		catch (final RequestException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), "00", isoBuffer);
		}
		catch (final SQLException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), "00=", isoBuffer);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		finally {

			clear();

			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
				connection = null;
			}
		}

		return response;
	}

	public void revertUpdateBalance() throws RequestException, SQLException,
															ProcessException {

		/*double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE CARDNO='"
				+ cardNumber + "'");

		doSelect();

		double _dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
						.getString("BALANCEAMT"));

				if (amount <= _dbAmount) {
					amount = _dbAmount + amount;
				} else {
					System.out.println("No sufficient fund");
					throw new ProcessException("69");
				}

				System.out.println("Amount :: "
						+ m24Utility.getFormattedAmount(amount + "", 2));

				isoBuffer.put("P-44", StringUtils.rightPad("252", 15)
						+ StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 12, '0'));

				isoBuffer.put("S-102", StringUtils.rightPad(resultSet
						.getString("ACCOUNTNO"), 28));
			} else {
				throw new ProcessException("69");
			}
		} else {
			throw new RequestException("Connection is empty");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("UPDATE BALANCE SET BALANCEAMT = '" + amount
				+ "' WHERE ");
		sqlBuffer.append("CARDNO = '" + cardNumber + "'");

		if (doUpdate() != 1) {
			throw new RequestException("Unable to update the database");
		}*/

		final double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT FROM BALANCE ");
		sqlBuffer.append("WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		double _dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
										.getString("BALANCEAMT"));
			} else {
				throw new ProcessException("00");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		System.out.println("_dbAmount :: " + _dbAmount);

		_dbAmount = _dbAmount + amount;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("UPDATE BALANCE SET BALANCEAMT = '" + _dbAmount + "' ");
		sqlBuffer.append("WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-102").toString().trim() + "'");

		if (doUpdate() != 1) {
			throw new RequestException(
			"Unable to update the database for mini statement");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT FROM BALANCE WHERE ");
		sqlBuffer.append("ACCOUNTNO='"
				+ isoBuffer.get("S-103").toString().trim() + "'");

		doSelect();

		_dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
						.getString("BALANCEAMT"));
			} else {
				throw new ProcessException("00");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		System.out.println("_dbAmount :: " + _dbAmount);

		_dbAmount = _dbAmount - amount;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("UPDATE BALANCE SET BALANCEAMT = '" + _dbAmount + "' ");
		sqlBuffer.append("WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-103").toString().trim() + "'");

		doUpdate();
	}

	public void fetchBalance() throws RequestException, SQLException,
														ProcessException {

		double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		double _dbAmount = 0;
		//Added by Karthik.S
		String avlInd= getProp("AVL_IND");
		String ldgInd= getProp("LDG_IND");

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
						.getString("BALANCEAMT"));

				if (amount <= _dbAmount) {
					amount = _dbAmount - amount;
				} else {
					System.out.println("No sufficient fund");
					throw new ProcessException("00");
				}

				System.out.println("Amount :: "
						+ m24Utility.getFormattedAmount(amount + "", 2));

				isoBuffer.put("P-44", StringUtils.rightPad("253", 15)
						+ ldgInd+StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 11, '0')+avlInd+StringUtils.leftPad(m24Utility.getFormattedAmount(
										amount + "", 2), 11, '0'));

			} else {
				throw new ProcessException("00");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}
	}

	public void updateBalance() throws RequestException, SQLException,
														ProcessException {

		double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer
		.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE ACCOUNTNO='"
				+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		double _dbAmount = 0;

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = Double.parseDouble(resultSet
						.getString("BALANCEAMT"));

				if (amount <= _dbAmount) {
					amount = _dbAmount - amount;
				} else {
					System.out.println("No sufficient fund");
					throw new ProcessException("00");
				}

				System.out.println("Amount :: "
						+ m24Utility.getFormattedAmount(amount + "", 2));

				isoBuffer.put("P-44", StringUtils.rightPad("253", 15)
						+ StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 12, '0'));
				isoBuffer.put("S-102", StringUtils.rightPad(resultSet
						.getString("ACCOUNTNO"), 28));
				//Added for Indus Ind
				String tempBal="10"+ StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 15, '0');//Ledger Bal
				tempBal=tempBal+"10"+StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 15, '0');//Avl Bal
				tempBal=tempBal+"1-"+StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 17, '0');//Comb Bal
				isoBuffer.put("S-125", StringUtils.rightPad(tempBal, 200));
				//End


			} else {
				throw new ProcessException("00");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("UPDATE BALANCE SET BALANCEAMT = '" + amount
				+ "' WHERE ");
		sqlBuffer.append("ACCOUNTNO = '" + isoBuffer.get("S-102").toString().trim() + "'");

		if (doUpdate() != 1) {
			throw new RequestException("Unable to update the database");
		}
	}

	private void updateForMiniStatement() throws RequestException, SQLException {

		final double amount = Double.parseDouble(isoBuffer.get("P-4").toString()
				.trim()) / 100;

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("INSERT INTO STATEMENT ( CARDNO, TYPE, ");
		sqlBuffer.append("AMOUNT, DETAILS, ACCOUNTNO ) VALUES ('");
		sqlBuffer.append(cardNumber + "','" + type + "','" + amount + "', '"
				+ details + "', '" + isoBuffer.get("S-102").toString().trim()
				+ "')");

		if (doUpdate() != 1) {
			throw new RequestException(
			"Unable to update the database for mini statement");
		}

		if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("53")) {

			sqlBuffer.delete(0, sqlBuffer.length());
			sqlBuffer.append("INSERT INTO STATEMENT ( CARDNO, TYPE, ");
			sqlBuffer.append("AMOUNT, DETAILS, ACCOUNTNO ) VALUES ('");
			sqlBuffer.append(fromCardNo + "','CR','" + amount + "', '"
					+ details + "', '" + isoBuffer.get("S-103").toString().trim()
					+ "')");


			if (doUpdate() != 1) {
				throw new RequestException(
				"Unable to update the database for mini statement");
			}
		}
	}

	public final String getTime(final String format, Date date) {
		return FastDateFormat.getInstance(format).format(date);
	}
	
	
	
	public String getBalance(String accNO) throws RequestException, SQLException{
		
		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE ACCOUNTNO='"+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		String _dbAmount = "0";
		String amt ="";

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = resultSet.getString("BALANCEAMT");
				
				amt =_dbAmount;
				
				
				/*if(_dbAmount.contains(".")){
					amt =_dbAmount.substring(0, _dbAmount.indexOf("."));
				}*/
				
				
			}
		}
		return amt;
		
	}
	
	
	
	
	//Added by Karthik. S
	
	public static String convertToINR(String amount) {
		//Remove the decimal 
		
		
		StringBuffer sb= new StringBuffer();

		try {
			sb.delete(0,sb.length());
			StringTokenizer amtTok=new StringTokenizer(amount,".");
			while(amtTok.hasMoreTokens()){
				amount=amtTok.nextToken().trim();
				String tempDecPos=amtTok.nextToken().trim();
				if(tempDecPos.length()<2){
					tempDecPos+="0";
				}
				amount+=tempDecPos;
			}
			
			sb= new StringBuffer(amount);
			int len=amount.length();
			if(len>2){
				sb.insert(len-2, ".");
				if(len>5){
					sb.insert(len-5, ",");
				}
				if(len>7){
					sb.insert(len-7, ",");
				}
				if(len>9){
					sb.insert(len-9, ",");
				}
			} else {
				if(len==2) {
					sb= new StringBuffer("0."+amount);
				} else if(len==1) {
					sb= new StringBuffer("0.0"+amount);
				}
			}
		} catch (Exception e) {
			System.out.println("exception::"+e.getMessage());
		}

		return sb.toString();
}

//Added by Karthik.S
	
	 public String getProp(String key) {
		String val = "";

		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("Host.properties");
			properties.load(fileInputStream);
			val = properties.getProperty(key);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return val;

	}
	
	
	
}
