package api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.fss.m24.Message24;
import com.fss.m24.api.Constants;
import com.fss.m24.api.M24Utility;
import com.fss.m24.api.iso.M24ISOMethods;

/**
 * @author <a href="karthiksankaran@fss.co.in">Karthik. S</a>
 * @since
 * @version 1.0
 * @created date: Jul 26, 2011 2:43:40 PM
 * @Modified by:
 * @Modified date:
 * @Remarks
 */
public class ConvertToISOFormat {

	private static final Logger log = Logger.getLogger("com.src.output");

	private M24ISOMethods m24ISOMethods;
	private M24Utility m24Utility;

	/**
	 *
	 */
	public ConvertToISOFormat() {
		// TODO Auto-generated constructor stub
	}

	public String convert(String message) {
		// TODO Auto-generated method stub

		String isoMessage = "";
		try {
			isoMessage = message;

			m24ISOMethods = new M24ISOMethods();
			m24Utility = new M24Utility();

			Hashtable isoBuffer = unbiFormatter87(message);

			isoBuffer.put("P-32", M24Utility.rightPadSpace(isoBuffer.get("P-32").toString(), 11));

			// log.debug(isoBuffer);

			if (isoBuffer.get("P-3").toString().substring(0, 2).equals(Constants.BALANCE_ENQUIRY_CODE)) {
				isoMessage = buildBalanceEnquiry(isoBuffer);

			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("38")) { // Mini
																						// statement
				isoMessage = buildMiniStatement(isoBuffer);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("94")) { // Cheque
																						// status
				isoMessage = buildChequeStatus(isoBuffer);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("95")) { // Stop
																						// Cheque
				isoMessage = buildStopCheque(isoBuffer);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("40")
					|| isoBuffer.get("P-3").toString().substring(0, 2).equals("97")) { // Fund
																						// Cheque
				isoMessage = buildFTResponse(isoBuffer);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("82")) { // Account
																						// Inquiry
				isoMessage = buildAccountEnquiry(isoBuffer);
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isoMessage;
	}

	private String buildStopCheque(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		isoBuffer.put("P-3", "MA0000");
		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));
		isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		isoBuffer.put("P-17", "*");
		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));

		if (isoBuffer.get("P-39").equals("00")) {
			isoBuffer.put("S-125", "0200"+isoBuffer.get("S-127").toString());

			log.debug("chq status rsp::::::" + isoBuffer.get("S-125").toString());

			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(21));
		}
		else {
			isoBuffer.put("S-125", isoBuffer.get("S-127").toString());
			isoBuffer.put("S-102", "*");
		}

		isoBuffer.put("S-127", "*");
		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");

		log.debug(isoBuffer);

		String message = buildRequestToB24AHI("0210", isoBuffer);

		return message;
	}

	private String buildChequeStatus(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		isoBuffer.put("P-3", "MA0000");
		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));
		isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		isoBuffer.put("P-17", "*");
		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));

		if (isoBuffer.get("P-39").equals("00")) {
			log.debug("chq status rsp before::::::"+ isoBuffer.get("S-125").toString());

			isoBuffer.put("S-125", "0200"+isoBuffer.get("S-125").toString());
			log.debug("chq status rsp after::::::"+ isoBuffer.get("S-125").toString());

			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(21));
		}
		else {
			isoBuffer.put("S-125", isoBuffer.get("S-125").toString());
			isoBuffer.put("S-102", "*");
		}

		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");


		log.debug(isoBuffer);

		String message = buildRequestToB24AHI("0210", isoBuffer);

		return message;
	}

	private String buildMiniStatement(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));
		isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		isoBuffer.put("P-17", "*");

		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));

		if (isoBuffer.get("P-39").equals("00")) {

			String accountdetails = isoBuffer.get("P-48").toString().substring(0, 34);
			log.debug("P-48:::" + accountdetails);

			String plussymbol 		= accountdetails.substring(0, 1).equals("+") ? "" : "-" ;

			String availableBalance = accountdetails.substring(6, 17);

			String ledgerplussymbol = accountdetails.substring(17, 18).equals("+") ? "" : "-";

			String ledgerBalance 	= accountdetails.substring(23, 34);

			accountdetails = plussymbol + availableBalance + ledgerplussymbol + ledgerBalance;

			isoBuffer.put("P-44", "3" + accountdetails);

			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(21));

			// Convert into decimal
			double tempAmt = Double.parseDouble(availableBalance) / 100;

			log.debug("temp tempAmt" + tempAmt);

			isoBuffer.put("S-125", "1P0000000027"
							+ formatMiniStatement(isoBuffer.get("S-125").toString()) + "AVAIL AMT Rs."
							+ plussymbol+tempAmt);
		}
		else {
			isoBuffer.put("P-44", "*");
			isoBuffer.put("S-102", "*");

			isoBuffer.put("S-125", "*");
		}

		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");


		log.debug(isoBuffer);

		String message = buildRequestToB24AHI("0215", isoBuffer);

		return message;
	}

	private String formatMiniStatement(String request) {
		// TODO Auto-generated method stub

		System.out.println("Mini statement :: "+request);

		String s125Bitmap = request;

		int columnLines = 27;

		int columnCount = columnLines;

		List miniStatement = new ArrayList();

		String losgMiniStatement = "";

		int lonuOffSet = 0;

		int lonuCnt = 0;

		for (; columnCount <= (s125Bitmap.trim()).length();) {

			String temp = s125Bitmap.substring(lonuOffSet, columnCount);

			char[] tempChar = temp.toCharArray();

			String _temp = "";

			boolean flag = false;

			for (int i = 0; i < tempChar.length; i++) {

				if (tempChar[i] != ' ') {

					_temp = _temp + tempChar[i];
					flag = true;
				}
				else {

					if (flag) {

						_temp = _temp + tempChar[i];
						flag = false;
					}
				}
			}

			losgMiniStatement = _temp;
			//System.out.println("losgMiniStatement :: "+losgMiniStatement);
			miniStatement.add(losgMiniStatement);

			lonuOffSet = lonuOffSet + columnLines;
			columnCount = columnCount + columnLines;

			lonuCnt += 1;
		}

		losgMiniStatement = "";

		lonuCnt = 0;

		for(int i=0;i<miniStatement.size();i++) {

			if (lonuCnt == 10) {
				break;
			}

			++lonuCnt;

			String temp = miniStatement.get(i).toString();

			StringTokenizer token = new StringTokenizer(temp, " ");

			//System.out.println(token.countTokens()+"\t"+temp);

			try {
				if (token.countTokens() == 3) {

					String date = token.nextToken();

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date daate 	= df.parse(date);

					df 		= new SimpleDateFormat("dd/MM/yy");
					//System.out.println(date+"\t"+df.format(daate));
					temp = df.format(daate);
					temp += " ";
					temp += token.nextToken()+" ";
					temp += token.nextToken();

					//
				}
				else if (token.countTokens() == 4) {

					String date = token.nextToken();

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
					Date daate 	= df.parse(date);

					df 		= new SimpleDateFormat("dd/MM/yy");
					//System.out.println(date+"\t"+df.format(daate));
					temp = df.format(daate);
					temp += " ";
					temp += token.nextToken()+"-"+ token.nextToken()+" ";
					temp += token.nextToken();

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			losgMiniStatement = losgMiniStatement + M24Utility.rightPadSpace(temp, 27) ;
		}

		System.out.println("Mini statement :: "+losgMiniStatement);

		return losgMiniStatement;
	}

	private String buildFTResponse(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		if (isoBuffer.get("P-37").toString().equals("*")) {
			isoBuffer.put("P-37", isoBuffer.get("P-11"));
		}

		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));

		//added by mahadevan for neft on 17.8.2011
		if(!isoBuffer.get("P-12").toString().equals("*")){
			isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		}

		//end

		isoBuffer.put("P-17", "*");
		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));
		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");

		if (isoBuffer.get("P-39").equals("00")) {
			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(21));
            //Added by Karthik.S
			if(!isoBuffer.get("S-103").equals("*")){
				isoBuffer.put("S-103", isoBuffer.get("S-103").toString().substring(21));
            }
		}
		else {
			isoBuffer.put("S-102", "*");
			isoBuffer.put("S-103", "*");
		}
		//isoBuffer.put("P-3", "MB0000");

		log.debug(isoBuffer);

		log.debug("Park Buffer :: "+Message24.parkBuffer);

		String requestId = isoBuffer.get("P-37").toString() + isoBuffer.get("P-32");

		Vector oldRecord = Message24.parkBuffer.get(requestId);

		String message = buildRequestToB24AHI("0210", isoBuffer);

		return message;
	}

	private String buildBalanceEnquiry(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));
		isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		isoBuffer.put("P-17", "*");
		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));

		if (isoBuffer.get("P-39").equals("00")) {

			String accountdetails = isoBuffer.get("P-48").toString().substring(0, 34);

			log.debug("P-48:::" + accountdetails);

			String plussymbol = accountdetails.substring(0, 1).equals("+") ? "0" : "1";

			// 1 - NEGATIVE, 0 - POSTIVE
			String availableBalance = accountdetails.substring(2, 17);

			String ledgerplussymbol = accountdetails.substring(17, 18).equals("+") ? "0" : "1";

			String ledgerBalance = accountdetails.substring(19, 34);

			accountdetails = "1" + "0" + availableBalance + "1" + "0" + ledgerBalance + "0";

			isoBuffer.put("S-125", accountdetails);

			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(21));
		}
		else {
			isoBuffer.put("S-125", "*");

			isoBuffer.put("S-102", "*");
		}

		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");

		log.debug(isoBuffer);

		String message = buildRequestToB24AHI("0210", isoBuffer);

		return message;
	}

	private String buildAccountEnquiry(Hashtable isoBuffer) {
		// TODO Auto-generated method stub

		isoBuffer.put("P-3", "MC"+isoBuffer.get("P-3").toString().substring(2, 6));
		//isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(0, 12));
		isoBuffer.put("P-4", isoBuffer.get("P-4").toString().substring(4, 16));
		isoBuffer.put("P-11", isoBuffer.get("P-11").toString().substring(0, 6));
		isoBuffer.put("P-12", isoBuffer.get("P-12").toString().substring(0, 6));
		isoBuffer.put("P-17", "*");
		isoBuffer.put("P-39", fetchResponseCode(isoBuffer.get("P-39").toString()));

		String accountName = " ";

		if (isoBuffer.get("P-39").equals("00")) {

			accountName = isoBuffer.get("S-125").toString().substring(14);
			isoBuffer.put("S-102", isoBuffer.get("S-102").toString().substring(19));
		}
		else {
			isoBuffer.put("S-102", "*");
		}

		isoBuffer.put("S-125", M24Utility.leftPadZeros(accountName.length()+"", 4)+accountName);

		isoBuffer.put("P-48", "*");
		isoBuffer.put("P-49", "*");
		isoBuffer.put("P-59", "*");


		log.debug(isoBuffer);

		String message = buildRequestToB24AHI("0210", isoBuffer);

		return message;
	}


	public Hashtable<String, String> unbiFormatter87(String message) {

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
					log.debug((i + 1)
							+ "\t"
							+ binary2hex(asciiChar2binary(ISOBuffer.get("P-"
									+ (i + 1)))));
				} else {
					log.debug((i + 1) + "\t" + ISOBuffer.get("P-" + (i + 1)));
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
					log.debug((i + 1) + "\t" + ISOBuffer.get("S-" + (i + 1)));
				} else {
					ISOBuffer.put("S-" + (i + 1), "*");

				}
			}

		}
		// log.info("sBitmap bitmap :::::::: "+binary2hex(sBitmap));

		return ISOBuffer;
	}

	/**
	 * <i>Parse the Primary/Secondary bitmap</i>
	 * <p>
	 * This is an utility method used by the ISO Formatter
	 * <p>
	 */
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

	final int[] UNBIBITMAP87 = {

	8, -2, 6, 16, 12, 12, 10, 8, // 8
			8, 8, 12, 14, 4, 4, 4, 4, // 16
			8, 4, 3, 3, 3, 3, 3, 3, // 24
			2, 2, 1, 9, 9, 9, 9, -2, // 32
			-2, -2, -2, -3, 12, 6, 3, 3, // 40
			16, 15, -2, -2, -2, -3, -3, -3, // 48
			3, 3, 3, 16, 16, 15, -3, -3, // 56
			-3, 0, -3, -3, -3, -3, 19, 16, // 64
			0, 1, 2, 3, 3, 3, 4, 4, // 72
			6, 10, 10, 10, 10, 10, 10, 10, // 80
			10, 12, 12, 12, 12, 16, 16, 16, // 88
			16, 42, 0, 2, 5, 7, 42, 16, // 96
			17, 0, -2, -2, 0, -2, -2, -2, // 102
			-3, -3, -3, -3, -3, -3, -3, 0, // 112
			0, 0, 0, 0, 0, 0, -3, -3, // 120
			-3, -3, -3, -3, -3, -3, -3, 16 };

	// ///////////////////

	final int[] BITMAP87 = { 16, -2, 6, 12, 12, 12, 10, 8,// 8
			8, 8, 6, 6, 4, 4, 4, 4,// 16
			4, 4, 3, 3, 3, 3, 3, 3, // 24
			2, 2, 1, 9, 9, 9, 9, -2, // 32
			-2, -2, -2, -3, 12, 6, 2, 3,// 40
			16, 15, 40, -2, -2, -3, -3, -3,// 48
			3, 3, 3, 16, 16, 15, -3, -3,// 56
			-3, 0, 0, -3, -3, -3, -3, 16,// 64
			0, 1, 2, 3, 3, 3, 4, 4,// 72
			6, 10, 10, 10, 10, 10, 10, 10,// 80
			10, 12, 12, 12, 12, 16, 16, 16,// 88
			16, 42, 0, 2, 5, 7, 42, 16,// 96
			17, 0, -2, -2, 0, -2, -2, 0,// 104
			-3, -3, -3, -3, -3, -3, -3, 0, // 112
			0, 0, 0, 0, 0, 0, 0, 36, -3,// 120
			-3, -3, -3, -3, -3, -3, 16 };// 128

	/**
	 * <i>Build the ISO 8583 Response Message</i>
	 * <p>
	 * This method builds and returns the ISO 8583 Financial Response message
	 * <p>
	 */
	public String buildRequestToB24AHI(String iobsgMsgType,
			Hashtable pthtISOBuffer) {
		String losgMessage = null;
		// For Late response this statement is added for Testing Purpose
		// Thread.sleep(30000);
		try {
			// System.out.println("Message Type 1:: "+iobsgMsgType);
			iobsgMsgType = iobsgMsgType;

			losgMessage = "ISO006000040";

			losgMessage += ISOBuilder87(iobsgMsgType, pthtISOBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}

	public String ISOBuilder87(String iobstMsgType,
			Hashtable<String, String> pthtISOBuffer) {

		int lonuSize = 0;
		String losgMessage = "";
		String losgPrimaryBitMap = "";
		String losgSecondaryBitMap = "";
		int i = 0;
		// System.out.println("pthtISOBuffer :::::::: "+pthtISOBuffer);
		for (i = 0; i < 128; i++) {
			// System.out.println("i :: "+i);
			if (i <= 63) {
				if (!pthtISOBuffer.get("P-" + (i + 1)).toString().equals("*")) {
					if (BITMAP87[i] < 0) {
						lonuSize = pthtISOBuffer.get("P-" + (i + 1)).toString()
								.length();
						losgMessage += m24Utility.leftPadZeros(String
								.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage += pthtISOBuffer.get("P-" + (i + 1))
								.toString();
					} else {
						losgMessage += pthtISOBuffer.get("P-" + (i + 1))
								.toString();
					}
					losgPrimaryBitMap += "1";
				} else
					losgPrimaryBitMap += "0";
			} else {

				if (!pthtISOBuffer.get("S-" + (i + 1)).toString().equals("*")) {
					if (BITMAP87[i] < 0) {
						lonuSize = pthtISOBuffer.get("S-" + (i + 1)).toString()
								.length();
						losgMessage += m24Utility.leftPadZeros(String
								.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage += pthtISOBuffer.get("S-" + (i + 1))
								.toString();
					} else {
						losgMessage += pthtISOBuffer.get("S-" + (i + 1))
								.toString();
					}
					losgSecondaryBitMap += "1";
				} else
					losgSecondaryBitMap += "0";
			}
		}

		if (losgSecondaryBitMap
				.equals("0000000000000000000000000000000000000000000000000000000000000000"))
			losgMessage = iobstMsgType + binary2hex(losgPrimaryBitMap)
					+ losgMessage;
		else {

			losgMessage = iobstMsgType + binary2hex(losgPrimaryBitMap)
					+ binary2hex(losgSecondaryBitMap)
					+ losgMessage.substring(8);

		}
		return losgMessage;
	}

	public static String fetchResponseCode(String response) {

		String isoResponseCode = "69";

		System.out.println("responseCodeTable :: "+responseCodeTable.size()+"\t"+response);

		if (responseCodeTable.get(response) != null) {
			isoResponseCode = responseCodeTable.get(response).toString();
		}

		return isoResponseCode;
	}

	private static Hashtable responseCodeTable = null;

	static {
		responseCodeTable = new Hashtable();

		responseCodeTable.put("000", "00");

		responseCodeTable.put("111", "X1");
		responseCodeTable.put("114", "X2");
		responseCodeTable.put("115", "X3");
		responseCodeTable.put("116", "X4");
		responseCodeTable.put("119", "X5");
		responseCodeTable.put("121", "X6");
		responseCodeTable.put("163", "X7");

		responseCodeTable.put("180", "X8");
		responseCodeTable.put("181", "X9");
		responseCodeTable.put("182", "Y1");
		responseCodeTable.put("183", "Y2");
		responseCodeTable.put("184", "Y3");
		responseCodeTable.put("185", "Y4");
		responseCodeTable.put("186", "Y5");

		responseCodeTable.put("187", "Y6");
		responseCodeTable.put("188", "Y7");
		responseCodeTable.put("189", "Y8");
		responseCodeTable.put("19",  "Y9");
		responseCodeTable.put("800", "Z1");
		responseCodeTable.put("902", "Z2");
		responseCodeTable.put("904", "Z3");

		responseCodeTable.put("906", "Z4");
		responseCodeTable.put("907", "Z5");
		responseCodeTable.put("909", "Z6");
		responseCodeTable.put("911", "Z7");
		responseCodeTable.put("913", "Z8");
	}
}
