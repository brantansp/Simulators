
/**
 * @(#)MPaySimulator.java	1.0a 25/04/2007
 *
 * Copyright 2007-2008 Financial Software & Systems (P)  Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of Financial Software & Systems (P) Ltd.
 * Use is subject to license terms.
 */
package api;

import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.FastDateFormat;

//import com.fss.m24.api.messagehandler.ConvertToExternalFormat;
//import com.fss.m24.station.ConvertToISOFormat;








import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import db.ConnectionManager;
import simulator.HostSimulator;

/**
 * @author <a href="aravindang@fss.co.in">Aravindan. G</a>
 * @since
 * @version
 * @created date: Apr 25, 2007 6:16:20 PM
 */

public class MPaySimulator implements Processor {

	private static Log log = LogFactory.getLog(MPaySimulator.class);
	
	private String cardNumber;

	private String fromCardNo;

	private Connection connection;

	private String details = null;

	private Hashtable<String, String> isoBuffer;

	private M24ISOMethods m24ISOMethods;

	//private ConvertToISOFormat convertToIso;

	//private ConvertToExternalFormat convToExt;

    private M24Utility m24Utility;

	private ResultSet resultSet;

	private StringBuffer sqlBuffer;

	private Statement statement;

	private String type = null;
	
	public static final String CONF_PROPERTY = "simulator\\stopcheque.properties";
	
	public static PropertiesConfiguration conf;
	
	final URL input   = this.getClass().getClassLoader().
	getResource(MPaySimulator.CONF_PROPERTY);


final int[] UNBIBITMAP87 = {/*

	8, -2, 6, 16, 12, 12, 10, 8, // 8
			8, 8, 12, 14, 4, 4, 4, 4, // 16
			8, 4, 3, 3, 3, 3, 3, 3, // 24
			2, 2, 1, 9, 9, 9, 9, -2, // 32
			-2, -2, -2, -3, 12, 6, 3, 3, // 40
			16, 15, -2, -2, -2, -3, -3, -3, // 48
			3, 3, 3, 16, 16, 15, -3, -2, // 56
			-3, 0, -3, -3, -3, -3, 19, 16, // 64
			0, 1, 2, 3, 3, 3, 4, 4, // 72
			6, 10, 10, 10, 10, 10, 10, 10, // 80
			10, 12, 12, 12, 12, 16, 16, 16, // 88
			16, 42, 0, 2, 5, 7, 42, 16, // 96
			17, 0, -2, -2, 0, -2, -2, -2, // 102
			-3, -3, -3, -3, -3, -3, -3, 0, // 112
			0, 0, 0, 0, 0, 0, -3, -3, // 120
			-3, -3, -3, -3, -3, -3, -3, 16 */
		


		/*8, -2, 6, 16, 12, 12, 10, 8, // 8
		//8, 8, 12, 14, 4, 4, 4, 4, // 16
		8, 8, 6, 6, 4, 4, 4, 4, // 16
		4, 4, 3, 3, 3, 3, 3, 3, // 24
		2, 2, 1, 9, 9, 9, 9, -2, // 32
		-2, -2, -2, -3, 12, 6, 2, 3, // 40
		//16, 15, -2, -2, -2, -3, -3, -3, // 48
		16, 15, 40, -2, -2, -3, -3, -3, // 48
		3, 3, 3, 16, 16, 15, -3, -2, // 56
		-3, 0, -3, -3, -3, -3, 19, 16, // 64
		0, 1, 2, 3, 3, 3, 4, 4, // 72
		6, 10, 10, 10, 10, 10, 10, 10, // 80
		10, 12, 12, 12, 12, 16, 16, 16, // 88
		16, 42, 0, 2, 5, 7, 42, 16, // 96
		17, 0, -2, -2, 0, -2, -2, -2, // 102
		-3, -3, -3, -3, -3, -3, -3, 0, // 112
		0, 0, 0, 0, 0, 0, -3, -3, // 120
		-3, -3, -3, -3, -3, -3, -3, 16 */
		
		
		8, -2, 6, 16, 12, 12, 10, 8, // 8
		8, 8, 12, 14, 4, 4, 4, 4, // 16
		8, 4, 3, 3, 3, 3, 3, 3, // 24
		2, 2, 1, 9, 9, 9, 9, -2, // 32
		-2, -2, -2, -3, 12, 6, 3, 3, // 40
		16, 15, -2, -2, -2, -3, -3, -3, // 48
		3, 3, 3, 16, 16, 15, -3, -2, // 56
		-3, 0, -3, -3, -3, -3, 19, 16, // 64
		0, 1, 2, 3, 3, 3, 4, 4, // 72
		6, 10, 10, 10, 10, 10, 10, 10, // 80
		10, 12, 12, 12, 12, 16, 16, 16, // 88
		16, 42, 0, 2, 5, 7, 42, 16, // 96
		17, 0, -2, -2, 0, -2, -2, -2, // 102
		-3, -3, -3, -3, -3, -3, -3, 0, // 112
		0, 0, 0, 0, 0, 0, -3, -3, // 120
		-3, -3, -3, -3, -3, -3, -3, 16 
};

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
	      String Account = isoBuffer.get("S-102");
	       System.out.println("Account length is : " +Account.length());
	       String Acc= Account.substring(19, Account.length());		
			System.out.println("S-102 is " +Acc);
			
		if(isoBuffer.get("S-102").toString().trim().equals("*")){

			isoBuffer.put("S-102","317802070054600");
		}else{
			isoBuffer.put("S-102", Acc);
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("SELECT BALANCEAMT, ACCOUNTNO FROM BALANCE WHERE ACCOUNTNO='"+ isoBuffer.get("S-102").toString().trim() + "'");

		doSelect();

		String _dbAmount = "0";

		if (resultSet != null) {
			if (resultSet.next()) {
				_dbAmount = resultSet.getString("BALANCEAMT");
				System.out.println("Amount :: " + m24Utility.getFormattedAmount(_dbAmount, 2));
				String Avlbal =StringUtils.leftPad(_dbAmount, 16, '0');
			    int ldgbal = Integer.parseInt(Avlbal)+1575;
			    String ledgr = String.valueOf(ldgbal);
			    ledgr = StringUtils.leftPad(ledgr, 16, '0');
			    System.out.println("Avlbal : " +Avlbal );
			    System.out.println("ledgr : " +ledgr );
				isoBuffer.put("P-48", "+"+ledgr+"+"+Avlbal+"+0000000008000000+0000000000000000+0000000000000000INR              ");
			// isoBuffer.put("P-48", "+0000000000000008+0000000000000008+0000000000000062+0000000000000000+0000000000000000INR              ");
/*
				isoBuffer.put("P-48", "+"
						+ StringUtils.leftPad(m24Utility.getFormattedAmount(
								_dbAmount, 2), 16, '0')+"+"+
								StringUtils.leftPad(m24Utility.getFormattedAmount(
										_dbAmount, 2), 16, '0')+"+"+
										StringUtils.leftPad(m24Utility.getFormattedAmount(
												_dbAmount, 2), 16, '0')+"+"
												+StringUtils.leftPad(m24Utility.getFormattedAmount(
														_dbAmount, 2), 16, '0')+"+"
														+StringUtils.leftPad(m24Utility.getFormattedAmount(
																_dbAmount, 2), 16, '0')+"+"
																+StringUtils.leftPad(m24Utility.getFormattedAmount(
																		_dbAmount, 2), 16, '0')+
												
											"INR");
	*/

				isoBuffer.put("S-102", StringUtils.rightPad(resultSet
						.getString("ACCOUNTNO"), 28));
			}
			else {
				throw new ProcessException("068");
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

		if (resultSet != null) {
			while (resultSet.next()) {
				final String type = resultSet.getString("TYPE");
				final String amount = resultSet.getString("AMOUNT");
				final String details = resultSet.getString("DETAILS");
				final Date _date = resultSet.getDate("TXNDATE");
                
				
				statementBuffer.append(StringUtils.rightPad(getTime("ddMMyyyy", _date), 9));
				statementBuffer.append(StringUtils.rightPad(details, 6));	
				statementBuffer.append(" "+StringUtils.leftPad(amount, 11));
				
				//20130214 DTRTR        1.00 20130214 DNEFT        1.00 20130214 DTRTR       10.00 20130214 DTRTR        1.00 20130214 DTRTR        1.00 20130214 DTRTR        50.00 20130211 CTRRR       50.00 20130211 DTRTR       50.00                   
				//statementBuffer.append(StringUtils.leftPad(StringUtils.rightPad(type + "", 1), 3));
				
			}
			System.out.println("statement ::"+ statementBuffer.toString());
			if (statementBuffer.length() <= 0) {
				throw new ProcessException("069");
			}
			else {
	
	if ("422132".equals(isoBuffer.get("P-32")))
	          {// ministatement for IOB
		           isoBuffer.put("S-125",statementBuffer.toString());
	          }
	else
	         {//mini statement for TMB
		           isoBuffer.put("S-125", "20171108                     NEFT/SUBRAMANIAN VELRAJAN/TMBLH173120502D           103.0020171108                     NEFT/VGN Facility Mgmt Service/TMBLH1731D           107.0020171108                     eBank/Tr To S.ANISH KUMAR               D           201.0020171108                     RTGS/TATA CARD/TMBLH17312050228         D        216000.0020171108                     eBank/Tr To SAROJINI.C                  D           204.0020171108                     NEFT/testaudit/TMBLH17312050227         D           108.0020171108                     NEFT/Shanmugavel/TMBLH17312050222       D           102.0020171108                     NEFT/ing vysys test3/TMBLH17312050223   D           109.0020171108                     NEFT/ANNA NAGAR OWNER/TMBLH17312050220  D           100.00       ");
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
			throw new ProcessException("000");
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
				throw new ProcessException("070");
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
				throw new ProcessException("069");
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

		/*try {
			Thread.sleep(450000);
		}
		catch(Exception e) {
		}
*/
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

		//convToExt = new ConvertToExternalFormat();

		sqlBuffer = new StringBuffer();

		String response = null;

		System.out.println("Message :: "+message);

		try {
			//Thread.sleep(9000000);
			System.out.println("inside simulator to build request");
			System.out.println("ReqMessage :: "+message);
			isoBuffer = unbiFormatter87(message);
			System.out.println("ISO BUFFER ::::::"+isoBuffer);
		} catch (final Exception e) {
			e.printStackTrace();
		}



		try {

			//System.out.println("P-35 :::"+isoBuffer.get("P-35").toString());
			//isoBuffer.put("P-35","4213680007890000=");
			
			MPaySimulator.conf 	= new PropertiesConfiguration(input);
			
			String rescode =conf.getString("RES_CODE");
			String cheqstatus =conf.getString("CHQ_STATUS");
			System.out.println("RESCODE::"+rescode+":::::chequeStatus::::"+cheqstatus);
			if(isoBuffer.get("P-35")==null){
				isoBuffer.put("P-35","4747470007890000");
			}
			
			if(isoBuffer.get("P-35").toString().length() == 0){

				isoBuffer.put("P-35","4747470007890000");

			}
			System.out.println("msg TYPE =="+isoBuffer.get("MSG-TYP").toString());
			if ("0200".equals(isoBuffer.get("MSG-TYP").toString().trim()) || "1200".equals(isoBuffer.get("MSG-TYP").toString().trim()) || "1420".equals(isoBuffer.get("MSG-TYP").toString().trim())) {
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
										2).equals("96") ) {
					System.out.println("Inside fetch card details block");
					fetchCardDetails();
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					/*if(isoBuffer.get("P-3").toString().trim().substring(0,
							2).equals("94")){
					isoBuffer.put("P-3","38"+isoBuffer.get("P-3").toString().trim().substring(2));
					}
					isoBuffer.put("P-48","+0000000000231852+0000000000228952+0000000000000000+0000000000000000+0000000000000000INR              ");
					isoBuffer.put("S-125","20130214 DTRTR        1.00 20130214 DNEFT        1.00 20130214 DTRTR       10.00 20130214 DTRTR        1.00 20130214 DTRTR        1.00 20130214 DTRTR        50.00 20130211 CTRRR       50.00 20130211 DTRTR       50.00                   ");
					//isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR              +0000017221245177+0000017221245177+0000000000000000+0000000000000000+0000000000000000INR              ");
					isoBuffer.put("P-41", "*");
					isoBuffer.put("P-24","*");
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					isoBuffer.put("P-43", "*");
					isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR");
					isoBuffer.put("P-56", "*");
					//isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR              +0000017221245177+0000017221245177+0000000000000000+0000000000000000+0000000000000000INR              ");         
					//isoBuffer.put("S-103","*");
					isoBuffer.put("P-24","*");
					isoBuffer.put("P-32", "504465");
					isoBuffer.put("P-38", "UNI000");
					
					isoBuffer.put("P-39", rescode);
					isoBuffer.put("P-43", "FKL0033U        ");
					isoBuffer.put("P-43", "*");
					isoBuffer.put("P-48", "+0000000013037533+0000000013037533+0000000000000000+0000000000000000+0000000000000000INR              ");
					isoBuffer.put("P-49", "INR");
					isoBuffer.put("P-56", "*");
					isoBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");
					isoBuffer.put("S-123","MOB");
					isoBuffer.put("S-125","UBNET");
					isoBuffer.put("P-39", "000");*/
					
					
					response = buildNPCIResponse87("1210", isoBuffer);
					System.out.println("RSP::");
					return response;
				}else if(isoBuffer.get("P-3").toString().trim().substring(0,
						2).equals("42")){
					isoBuffer.put("P-39", "000");
					response = buildNPCIResponse87("1210", isoBuffer);
					System.out.println("RSP::");
					return response;
				}
				
				else if(isoBuffer.get("P-3").toString().trim().substring(0,
						2).equals("38")){
					
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					/*isoBuffer.put("P-48","+0000000000231852+0000000000228952+0000000000000000+0000000000000000+0000000000000000INR              ");
					isoBuffer.put("S-125","20130214 DTRTR        1.00 20130214 DNEFT        1.00 20130214 DTRTR       10.00 20130214 DTRTR        1.00 20130214 DTRTR        1.00 20130214 DTRTR        50.00 20130211 CTRRR       50.00 20130211 DTRTR       50.00                   ");*/
					fetchCardDetails();
					fetchMiniStatement();
					
					response = buildNPCIResponse87("1210", isoBuffer);
					System.out.println("RSP::");
					return response;
				}
				else if (
					isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("53") || isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MB")|| isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MV") || isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MF")) {
					System.out.println("Inside update balance block");
					details = "FUND";
					type = "DR";
					//fundTransfer();
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					//VR|9840451478|727217217217|900800100801633|000000001000|9840451478
					isoBuffer.put("S-125", "VR|9791135973|727217217217|900800100801633|000000001000|9791135973");
					response = buildNPCIResponse87("1210", isoBuffer);
					return response;
				}else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("82")||isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("97")) {
					System.out.println("Inside update balance block");
					details = "FUND";
					type = "DR";
					//fundTransfer();
					
					if  (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("97"))
					{
						String s= "" + (long)(Math.random() * 1000000 * 100000);
						isoBuffer.put("S-125", "IOBAN" + s);
					}
					else {
						isoBuffer.put("S-125", "SBAINRSBSTF19940526I        +0000000015480601A.S. KHARBANDA                                +0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000015480601+0000000000000000+0000000015480601");
					}
														
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					//isoBuffer.put("P-48","+0000000015480601+0000000015480601+0000000000000000+0000000000000000+0000000015480601INR");
					response = buildNPCIResponse87("1210", isoBuffer);
					return response;
				}
				
				
				
				/* else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("95")){

					response = "ISO0660000150210F33880012AF5801800000000160000481642145811739999339500000000000000000224150232        0024581502320224022411111111     374214581173999933=9912000000000000000000245809022400REMOTBANKINGSRVR000000000000000UBI                MUMBAI       MH 91   27252            000057752080012000000000000044A                       40035635711111111111   015MPAYCER11234   01111000325013131234ABN111  P281825101012034               19                   0101234567890007P";
					return response;

				}*/
				
				//Verification block
				else if ("1420".equals(isoBuffer.get("MSG-TYP").toString().trim()) && isoBuffer.get("P-3").toString().trim().substring(0,2).equals("40")){
					try
					{
					//Thread.sleep(15000);
					}catch (Exception e) {
						// TODO: handle exception
					}
					
					isoBuffer.put("P-24","*");
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39", "000");
					isoBuffer.put("P-43", "*");
					isoBuffer.put("P-56", "*");
					isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR              +0000017221245177+0000017221245177+0000000000000000+0000000000000000+0000000000000000INR              ");
                    //isoBuffer.put("S-102","*");
					//isoBuffer.put("S-103","*");
					System.out.println("Before building 1430 RSP:::");
					response = buildNPCIResponse87("1430", isoBuffer);
					System.out.println("RSP::");
					return response;
				}

				//Original transaction block
				
				else if ("1200".equals(isoBuffer.get("MSG-TYP").toString().trim()) && isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("40")){
					
					
					if (isoBuffer.get("P-32").toString().equals("422132")) 
					{

						Date date = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						String strDate= formatter.format(date);
					       Random rand = new Random();
						    BigInteger randNum = new BigInteger(30, rand);
						    
						isoBuffer.put("P-38","UNI000");
						isoBuffer.put("P-39","000");
						//isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR              +0000017221245177+0000017221245177+0000000000000000+0000000000000000+0000000000000000INR              ");
						isoBuffer.put("S-123","MOB");
						isoBuffer.put("S-125","Success|"+randNum+"|"+strDate+" 00:00:00");
						//Success|S45862396|11-07-2017 00:00:00
						isoBuffer.put("S-126", "AMIT");
						response = buildNPCIResponse87("1210", isoBuffer);
						//response = "1699999900000000004000000000000000000500"+isoBuffer.get("P-37").toString().trim()+"201111021857482011110209504465700"+isoBuffer.get("P-37").toString().trim()+"UNI000000FKL0033U        40UNI  CHANDANNAGAR    WEST BENGAL     IND204+0000000112659220+0000000112659220+0000000000000000+0000000000000000+0000000000000000INR              +0000017221955542+0000017221955542+0000000000000000+0000000000000000+0000000000000000INR              INR041300000^0153001530103422610^356^0.00^0200^32027                0389010002828003MOB035  027                00000190023002";
						return response;
						
					}
					else{
					
					//Thread.sleep(90000);
					//isoBuffer.put("P-2","5045110100000012");
					//isoBuffer.put("P-24","*");
					//isoBuffer.put("P-35","*");
					//isoBuffer.put("P-38","UNI000");
					isoBuffer.put("P-39","000");
					//isoBuffer.put("P-48", "+0000000009593543+0000000009593543+0000000000000000+0000000000000000+0000000000000000INR              +0000017221245177+0000017221245177+0000000000000000+0000000000000000+0000000000000000INR              ");
					//isoBuffer.put("S-103","*");
					//isoBuffer.put("S-125","  027                00000190023002");
					isoBuffer.put("S-126", "IMPSIFSCAMIT B%R&OAH");
					response = buildNPCIResponse87("1210", isoBuffer);
					//response = "1699999900000000004000000000000000000500"+isoBuffer.get("P-37").toString().trim()+"201111021857482011110209504465700"+isoBuffer.get("P-37").toString().trim()+"UNI000000FKL0033U        40UNI  CHANDANNAGAR    WEST BENGAL     IND204+0000000112659220+0000000112659220+0000000000000000+0000000000000000+0000000000000000INR              +0000017221955542+0000017221955542+0000000000000000+0000000000000000+0000000000000000INR              INR041300000^0153001530103422610^356^0.00^0200^32027                0389010002828003MOB035  027                00000190023002";
					return response;
					}
			}	else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MA")||isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("95")||isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("94")){

					String rrn = isoBuffer.get("P-37").toString().trim();
					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					System.out.println("P_32::"+P_32);
					isoBuffer.put("P-32",P_32);
					String tempS125="";
					if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("94")){
					 tempS125=cheqstatus;
					}else{
					 tempS125=cheqstatus;
					}
					isoBuffer.put("P-38", "UNI000");
					isoBuffer.put("P-39",rescode);
					isoBuffer.put("P-62",isoBuffer.get("P-62").substring(isoBuffer.get("P-62").length()-13,isoBuffer.get("P-62").length()));
					//isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));
					isoBuffer.put("S-125","S");
					//response = m24ISOMethods.buildResponse("0210", "000",isoBuffer);
					response = buildNPCIResponse87("1210", isoBuffer);

					return response;


				}//account sync
				else if(isoBuffer.get("P-3").toString().trim().substring(0,
					2).equals("37")){

				isoBuffer.put("P-39", "000");
				//isoBuffer.put("P-48","+0000000-49218004+0000000000781996+0000000000000000INR");
				isoBuffer.put("S-102","*");
				isoBuffer.put("S-123","*");
				String s125 = isoBuffer.get("S-125");
				
				if(s125.substring(0, 2).equals("01"))
				{
					isoBuffer.put("S-125",s125.substring(0, 13)+"ALL201150050800005 CAA16500.00INR5012432476849822SBA -46300.00INR5012432476849800SBA -46300.00INR");

				}
				else if(s125.substring(0, 2).equals("02"))
				{
					s125 = isoBuffer.get("S-125").replace(s125.substring(0, 2), "02");
					isoBuffer.put("S-125",s125.substring(0, 13)+"ALL201150050800065 SBA16500.00INR5012432476849862SBA -46300.00INR5012432476849860SBA -46300.00INR");
				}
				else
				{
					s125 = isoBuffer.get("S-125").replace(s125.substring(0, 2), "LP");
					isoBuffer.put("S-125",s125.substring(0, 13)+"ALL5012433476349826SBA n -46300.00INR5012432472349283SBA -46300.00INR5012432470000000SBA -46300.00INR");
				}
				
				
				
				//s125 = isoBuffer.get("S-125").replace(s125.substring(0, 2), "LP");
				//isoBuffer.put("S-125",s125.substring(0, 13)+"5012432476349827CAA16500.00INR5012432476849822SBA -46300.00INR161100390300021 CAA 244.76INR5012432426349822SBA -46300.00INR5012434476349822SBA -46300.00INR5012435476349820SBA -46300.00INR161100352300061 CAA 244.76INR5012433476349826SBA -46300.00INR5012432472349283SBA -46300.00INR5012324763698284SBA -46300.00INR161100354300065 CAA 244.76INR5012432263498266SBA -46300.00INR5012438463498287SBA -46300.00INR5012432463398288SBA -46300.00INR161100359300069 CAA 244.76INR5012432426498210SBA -46300.00INR5012432483498211SBA -46300.00INR5012434063498212SBA -46300.00INR161100350380013 CAA 244.76INR5012432764498146SBA -46300.00INR5012432762498215SBA -46300.00INR5012432473428216SBA -46300.00INR161100340300017 CAA 244.76INR5012432753498218SBA -46300.00INR5017432763498219SBA -46300.00INR");
				//isoBuffer.put("S-125",s125.substring(0, 13)+"5012432476349827SBA16500.00INR161100390300021 SBA 244.76INR5012432426349822SBA -46300.00INR5012434476349822SBA -46300.00INR5012435476349820SBA -46300.00INR161100359300069 CAA 244.76161100359300069 CAA 244.76");
				response = buildNPCIResponse87("1210", isoBuffer);
				System.out.println("RSP::");
				return response;
			}else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("88")){


					response = "ISO0160000170210F23A80012EB080180000000014000048164213684285000481MA1000000000000000061417111001050822411206140614061411504432     314213684285000481=12051264670000010508090614SIN00000REMOTBANKINGSRVRUBI                MUMBAI        MH 91  253000000000000000000000000356012DVLPMPAY1234013UBSBUBSB    P065044321542850201027033501600000000001000212000100P160000000000100021                                                                                                                                                                                  ";
					return response;
				}

				else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("000")){

					if(true){
					throw new ProcessException("000");

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
					if(P_32.trim().equals("800004")){
						tempS125+="IND001"+"IndusInd Bank";
					}else{
					tempS125+="TEST CARD MPURSE FSEH                                                           (OLD NO.4) NEW NO.5, WESTMADA STREET, SRINAGAR COLONY,SAIDAPET,CHENNAI    TAMIL NADU600015INDIA                      ";
					}
					isoBuffer.put("S-125",StringUtils.rightPad(tempS125, 200));

					//String tempRsp = "ISO0160000150210F23880012EB080180000000014000028165044323005005251MC100000000000000005010515561005011046350501050111"+P_32+"274213413005005251=120712693310050162323035535100REMOTBANKINGSRVRVIJAYA BANK        BANGALORE            253000002119495000002119495356012VIJBMPAY1234013VIJBPRO11100P0650446115300501010015685015012MPAY        2000100TEST CARD MPURSE FSEH                                                           (OLD NO.4) NEW NO.5, WESTMADA STREET, SRINAGAR COLONY,SAIDAPET,CHENNAI    TAMIL NADU600015INDIA                      ";
					//response = tempRsp.replace(tempRsp.substring(156,168).toString(),rrn);
					response = m24ISOMethods.buildResponse("0210", "000",isoBuffer);
					return response;
				}else if(isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("MM")){
				isoBuffer.put("P-24","*");
					isoBuffer.put("P-35","*");
					isoBuffer.put("P-38","UNI000");
					isoBuffer.put("P-39","000");
					isoBuffer.put("P-48", "+0000000000000000+0000000000000000+0000000000000000+0000000000000000+0000000000000000INR              ");
				isoBuffer.put("S-125","UNBIMPAY|AS|UNREGISTERED MOBILE NO.|9938133809|001|0875010114226|");
                response = buildNPCIResponse87("1210", isoBuffer);
                return response;
				}else if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("CP")){

					String P_32=isoBuffer.get("P-32").toString().trim();
					P_32=StringUtils.rightPad(P_32, 11);
					isoBuffer.put("P-32",P_32);
					isoBuffer.put("P-39","000");
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
						.toString().trim(), "000", isoBuffer);

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
					response = m24ISOMethods.buildResponse("0210", "000",isoBuffer);

					return response;

				}




				else {

					if (isoBuffer.get("P-3").toString().trim().substring(0, 2).equals("000")) {
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

					response = m24ISOMethods.buildResponse("0215", "000",
																isoBuffer);
					return response;
				}

				//response = convertToIso.unbiFormatter87(isoBuffer.get("MSG-TYP").toString().trim(), "000", isoBuffer);
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

				//response = convertToIso.unbiFormatter87(isoBuffer.get("MSG-TYP").toString().trim(), "000", isoBuffer);
				// Thread.sleep(35000);
			}
			else if ("1804".equals(isoBuffer.get("MSG-TYP").toString().trim()))
			{
				isoBuffer.put("P-39","000");
				response = m24ISOMethods.buildResponse("1814", "000",
						isoBuffer);
                 return response;
			}
			else {
				response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
						.toString().trim(), "000", isoBuffer);
			}
		}
		catch (final ProcessException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), e.getMessage(), isoBuffer);
			return response;
		}
		catch (final RequestException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), "30", isoBuffer);
			return response;
		}
		catch (final SQLException e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), "30", isoBuffer);
			return response;
		}
		catch (final Exception e) {
			e.printStackTrace();
			response = m24ISOMethods.buildResponse(isoBuffer.get("MSG-TYP")
					.toString().trim(), "30", isoBuffer);
			return response;
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
				throw new ProcessException("69");
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
				+ isoBuffer.get("S-102") + "'");

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
					throw new ProcessException("69");
				}

				System.out.println("Amount :: "
						+ m24Utility.getFormattedAmount(amount + "", 2));

				isoBuffer.put("P-44", StringUtils.rightPad("252", 15)
						+ StringUtils.leftPad(m24Utility.getFormattedAmount(
								amount + "", 2), 12, '0'));

			} else {
				throw new ProcessException("69");
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
				+ isoBuffer.get("S-102") + "'");

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
					throw new ProcessException("69");
				}

				System.out.println("Amount :: "
						+ m24Utility.getFormattedAmount(amount + "", 2));

				isoBuffer.put("P-44", StringUtils.rightPad("252", 15)
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
				throw new ProcessException("69");
			}
		}
		else {
			throw new RequestException("Connection is empty");
		}

		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("UPDATE BALANCE SET BALANCEAMT = '" + amount
				+ "' WHERE ");
		sqlBuffer.append(" ACCOUNTNO= '" + isoBuffer.get("S-102") + "'");

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

public static void main(String[] args) {
	    MPaySimulator obj = new MPaySimulator();
		  obj.unbiFormatter87("1699999900000000003120000000000000000000734715241158201712131504522017112720006463795734715241158FKL0033U        40TMB  ANNASALAICHN    COCHENNAITN     INDINR38TMB        161     161100350300067    003MOB017TMBNET|9865928748");
}
	public Hashtable<String, String> unbiFormatter87(String message) {
log.info(""+message);
		System.out.println("Message : "+message);
			int size = 0;
			int offset = 4;
			String pBitmap = null;
			String sBitmap = null;
			Hashtable<String, String> ISOBuffer = new Hashtable<String, String>();

			ISOBuffer.put("MSG-TYP", message.substring(0, 4));

			pBitmap = parseBitmap(binary2hex(asciiChar2binary(message.substring(
					offset, offset + 8))));
			offset += 8;

			for (int i = 0; i < 64; i++) {

				// log.info("primary bitmap :::::::: "+binary2hex(pBitmap));

				if ('1' == pBitmap.charAt(i)) {

					// log.info((i + 1) + "\t" + ISOBuffer.get("P-" + (i + 1)));

					if (UNBIBITMAP87[i] < 0) {

						size = Integer.parseInt(message.substring(offset, offset
								+ -1 * UNBIBITMAP87[i]));
						offset += -1 * UNBIBITMAP87[i];
						ISOBuffer.put("P-" + (i + 1), message.substring(offset,
								offset + size));
						offset += size;
					} else {
						ISOBuffer.put("P-" + (i + 1), message.substring(offset,
								offset + UNBIBITMAP87[i]));
						offset += UNBIBITMAP87[i];
					}

					if ((i + 1) == 1) {
						System.out.println((i + 1)
								+ "\t"
								+ binary2hex(asciiChar2binary(ISOBuffer.get("P-"
										+ (i + 1)))));
					} else {
						System.out.println((i + 1) + "\t" + ISOBuffer.get("P-" + (i + 1)));
					}
				} else {

					ISOBuffer.put("P-" + (i + 1), "*");
				}

			}

			if ('1' == pBitmap.charAt(0)) {
				sBitmap = parseBitmap(binary2hex(asciiChar2binary(ISOBuffer.get(
						"P-1").toString())));
				for (int i = 64; i < 128; i++) {
					if ('1' == sBitmap.charAt(i - 64)) {
						if (UNBIBITMAP87[i] < 0) {
							size = Integer.parseInt(message.substring(offset,
									offset + -1 * UNBIBITMAP87[i]));
							offset += -1 * UNBIBITMAP87[i];
							ISOBuffer.put("S-" + (i + 1), message.substring(offset,
									offset + size));
							offset += size;
						} else {
							ISOBuffer.put("S-" + (i + 1), message.substring(offset,
									offset + UNBIBITMAP87[i]));
							offset += UNBIBITMAP87[i];
						}
						System.out.println((i + 1) + "\t" + ISOBuffer.get("S-" + (i + 1)));
					} else {
						ISOBuffer.put("S-" + (i + 1), "*");

					}
				}

			}
			// log.info("sBitmap bitmap :::::::: "+binary2hex(sBitmap));

			return ISOBuffer;
		}


	String parseBitmap(String iobsgBitmap) {
		String losgUpperBitmap = "00000000000000000000000000000000";
		String losgLowerBitmap = "00000000000000000000000000000000";
		losgUpperBitmap += Long.toBinaryString(Long.parseLong(iobsgBitmap
				.substring(0, 8), 16));
		losgLowerBitmap += Long.toBinaryString(Long.parseLong(iobsgBitmap
				.substring(8), 16));
		losgUpperBitmap = losgUpperBitmap
				.substring(losgUpperBitmap.length() - 32);
		losgLowerBitmap = losgLowerBitmap
				.substring(losgLowerBitmap.length() - 32);
		return losgUpperBitmap + losgLowerBitmap;
	}

	public String binary2hex(String iobsgBinaryString) {

		if (iobsgBinaryString == null)
			return null;
		String losgHexString = "";
		for (int i = 0; i < iobsgBinaryString.length(); i += 8) {
			String losgTemp = iobsgBinaryString.substring(i, i + 8);
			int lonuIntValue = 0;
			for (int k = 0, j = losgTemp.length() - 1; j >= 0; j--, k++) {
				lonuIntValue += Integer.parseInt("" + losgTemp.charAt(j))
						* Math.pow(2, k);
			}
			losgTemp = "0" + Integer.toHexString(lonuIntValue);
			losgHexString += losgTemp.substring(losgTemp.length() - 2);
		}
		// log.info("binary2hex"+losgHexString);
		return losgHexString;
	}

	public static String asciiChar2binary(String iobsgAsciiString) {

		if (iobsgAsciiString == null)
			return null;

		String losgBinaryString = "";
		String losgTemp = "";
		int lonuIntValue = 0;

		for (int i = 0; i < iobsgAsciiString.length(); i++) {

			lonuIntValue = (int) iobsgAsciiString.charAt(i);
			losgTemp = "00000000" + Integer.toBinaryString(lonuIntValue);
			losgBinaryString += losgTemp.substring(losgTemp.length() - 8);
		}
		// log.info("asciiChar2binary "+losgBinaryString);
		return losgBinaryString;
	}


public String buildNPCIResponse87(String iobsgMsgType,
			 Hashtable<String, String> pthtISOBuffer) {
        System.out.println("inside buildNPCImethod::");
		String losgMessage = "";
		try {
			losgMessage += ISONFSBuilder87(iobsgMsgType, pthtISOBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}

	public String ISONFSBuilder87(String iobstMsgType,
			Hashtable<String, String> ISOBuffer) {
		int size = 0;
		String message = "";
		String primaryBitMap = "";
		String secondaryBitMap = "";
		int i = 0;
		System.out.println("ISO Buff : "+ISOBuffer);
        System.out.println("Rsp bits");
		for (i = 0; i < 128; i++) {
			if (i <= 63) {
				if (!ISOBuffer.get("P-" + (i + 1)).toString().equals("*")) {
					if (UNBIBITMAP87[i] < 0) {
						size = ISOBuffer.get("P-" + (i + 1)).toString().length();
						System.out.println(i+1+"-->"+size);
						message += pad1(String.valueOf(size), -1 * UNBIBITMAP87[i]);
						System.out.println(i+1+"-->"+ISOBuffer.get("P-" + (i + 1)).toString());
						message += ISOBuffer.get("P-" + (i + 1)).toString();
					}
					else {
						message += ISOBuffer.get("P-" + (i + 1)).toString();
						//System.out.println("test>>");
						System.out.println(i+1+"-->"+ISOBuffer.get("P-" + (i + 1)).toString());
					}
					primaryBitMap += "1";
				}
				else
					primaryBitMap += "0";
			}
			else {
				if (!ISOBuffer.get("S-" + (i + 1)).toString().equals("*")) {
					//log.info("Secondary bitmap :: " + (i + 1) + "\t"+ ISOBuffer.get("S-" + (i + 1)));
					if (UNBIBITMAP87[i] < 0) {
						size = ISOBuffer.get("S-" + (i + 1)).toString()
								.length();
						message += pad1(String.valueOf(size), -1 * UNBIBITMAP87[i]);
						message += ISOBuffer.get("S-" + (i + 1)).toString();
							System.out.println(i+1+"-->"+ISOBuffer.get("S-" + (i + 1)).toString());
					}
					else {
						message += ISOBuffer.get("S-" + (i + 1)).toString();
							System.out.println(i+1+"-->"+ISOBuffer.get("S-" + (i + 1)).toString());
					}
					secondaryBitMap += "1";
				} else
					secondaryBitMap += "0";
			}
		}

		if (secondaryBitMap
				.equals("0000000000000000000000000000000000000000000000000000000000000000"))

			message = iobstMsgType + m24ISOMethods.binary2hex(primaryBitMap) + message;

		else {

			//log.debug("Primary Bit map inside ISONFSBuilder87 :: "+ m24ISOMethods.binary2hex(primaryBitMap) + "\t" + primaryBitMap);

			//log.debug("Inside else block S120 :::: "+ ISOBuffer.get("S-120").toString());
			if (iobstMsgType.equals("0810")) {
				message = iobstMsgType + m24ISOMethods.binary2asciiChar(primaryBitMap)
				//+ binary2asciiChar(secondaryBitMap)
						+ message;
			} else {

				//debit working with substring 16

				System.out.println("message>>>> :; " + message);

				message = iobstMsgType + m24ISOMethods.binary2asciiChar(primaryBitMap)
						+ m24ISOMethods.binary2asciiChar(secondaryBitMap)
						//+ message.substring(16);
						+ message.substring(8);

			}
		}


		return message;
	}

public static String pad1(String str, int length) {
		str = (null == str) ? new String() : str;
		String padStr = new String(str);
		if (length < str.length()) {

			return str.substring(0, length);

		}

		for (int i = str.length(); i < length; i++) {

			padStr = '0' + padStr;

		}

		return padStr;

	}

}
