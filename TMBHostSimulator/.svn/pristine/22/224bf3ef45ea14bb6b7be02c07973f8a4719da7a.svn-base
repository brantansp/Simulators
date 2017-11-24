/******************************************************************
 * COMPANY    - FSS Pvt. Ltd.
 *****************************************************************

		Name of the Program			: M24Utility
		Description 				: Utility Class used in Msg24
		Classes Referred to			:
		Incoming parameters 		:
		Outgoing parameters 		:
		Tables Used               	:
		Values from Session			: N/A
		Values to Session			: N/A
		Created by					: Aravindan. G, Sankar P	Created Date	: 31/01/2006
		Modified by					: 							Modified Date	:
		Reason for Modification		:
		Modified tag/ CR No.		:

*/

package api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import xjava.security.Cipher;
import cryptix.provider.key.RawSecretKey;
import cryptix.util.core.Hex;

public class M24Utility{

	/* Decimalization Table for Pin generation */
	 String decimalTable[][] = new String[16][16];

	 /* String array to send as response */
	 String response[];

	/** The Cipher Object(Single DES or Triple DES). */
	 Cipher des= null;

	 /** DES Key. */
	 RawSecretKey desKey = null;

	 /** DES Key. */
	 RawSecretKey localMasterKey = null;

	/*<i>Pads the star at the end of the given Input String and returns the same</i>
	 *	<p>This is an utility method used for building ISO 8583 Message<p>
	 */
	public String rightPadStar(String ilosgStr, int ilonuLength) {
			if(null == ilosgStr){//if01
				ilosgStr = "";
			}//if01
			String olosgPadStr = new String (ilosgStr);
			if (ilonuLength < ilosgStr.length ()) { //if02
				return ilosgStr.substring (0, ilonuLength);
			}//if02
			for (int i = ilosgStr.length (); i < ilonuLength; i++) { //for01
				olosgPadStr = olosgPadStr + '*';
			}
			return olosgPadStr;
	}

	/*<i>Pads the star at the left end of the given Input String and returns the same</i>
	 *	<p>This is an utility method used for building ISO 8583 Message<p>
	 */
	public String leftPadStar(String ilosgStr, int ilonuLength) {
			if(null == ilosgStr){//if03
				ilosgStr = "";
			}//if03
			String olosgPadStr = "";
			if (ilonuLength < ilosgStr.length ()) {//if04
				return ilosgStr.substring (0, ilonuLength);
			}//if04
			for (int i = ilosgStr.length (); i < ilonuLength; i++) {//for02
				olosgPadStr = "*" + olosgPadStr;
			}//for02
			olosgPadStr = olosgPadStr + ilosgStr;
			return olosgPadStr;
	}

	/*<i>Pads the blank space at right side of the given Input String and returns the same</i>
	 *<p>This is an utility method used for building ISO 8583 Message<p>
	 */
	public String rightPadSpace (String ilosgStr, int ilonuLength) {
		
		if(null == ilosgStr){//if05
				ilosgStr = "";
		}//if05
		
		String olosgPadStr = new String (ilosgStr);
		
		if (ilonuLength < ilosgStr.length ()) {//if06
			return ilosgStr.substring (0, ilonuLength);
		}//if06
		
		for (int i = ilosgStr.length (); i < ilonuLength; i++) {//for03
			olosgPadStr = olosgPadStr + ' ';
		}//for03
		
		return olosgPadStr;
	}

	/**<i>Pads the blank space at left side</i>
	<p>This is an utility method used for building ISO 8583 Message<p>
	*/
	public String leftPadSpace (String ilosgStr, int ilonuLength) {
		
		if(null == ilosgStr){//if07
				ilosgStr = "";
		}//if07
		
		String olosgPadStr = "";
		
		if (ilonuLength < ilosgStr.length ()) {//if08
			return ilosgStr.substring (0, ilonuLength);
		}		
		
		for (int i = ilosgStr.length (); i < ilonuLength; i++) {//for04
				olosgPadStr = ' '+ olosgPadStr;
		}//for04
		olosgPadStr = olosgPadStr + ilosgStr;
		return olosgPadStr;
	}

	/**<i>Pads the zero at the begin</i>
	 *<p>This is an utility method used for building ISO 8583 Message<p>
	 *Equivalent to Pad1
	 */
	public String leftPadZeros(String ilosgStr, int ilonuLength) {
		if(null == ilosgStr){//if09
				return null;
		}//if09
		String olosgPadStr = new String (ilosgStr);
		if (ilonuLength < ilosgStr.length ()) {//if10
			return ilosgStr.substring (0, ilonuLength);
		}//if10
		for (int i = ilosgStr.length (); i < ilonuLength; i++) {//for05
			olosgPadStr = '0' + olosgPadStr;
		}//for05
		return olosgPadStr;
	}


	   /**<i>Pads the zero at the End</i>
		*<p>This is an utility method used for building ISO 8583 Message<p>
		*/
	public String rightPadZeros(String ilosgStr, int ilonuLength) {

		if(null == ilosgStr){	//if11
				return null;
		}	//if11

		String olosgPadStr = new String (ilosgStr);

		if (ilonuLength < ilosgStr.length ()) {	//if12
			return ilosgStr.substring (0, ilonuLength);
		}	//if12

		for (int i = ilosgStr.length (); i < ilonuLength; i++) {	//for06
			olosgPadStr = olosgPadStr + '0';
		}	//for06

		return olosgPadStr;
	}

	public String rightPadValue(String ilosgStr, int ilonuLength) {
			if(null == ilosgStr){//if01
				ilosgStr = "";
			}//if01
			String olosgPadStr = new String (ilosgStr);
			if (ilonuLength < ilosgStr.length ()) { //if02
				return ilosgStr.substring (0, ilonuLength);
			}//if02
			for (int i = ilosgStr.length (); i < ilonuLength; i++) { //for01
				olosgPadStr = olosgPadStr + 'F';
			}
			return olosgPadStr;
	}

	public String pinvalue(String str, String pin) {
		str = (null == str)?new String ():str;
		int inStringValue = 0;
		inStringValue = str.indexOf("-");

		if(inStringValue > 0) {
			str = str.substring(inStringValue+1,str.length()) + pin.trim();
			return str;
		}
		else {
			return str;
		}
	}


   /**<i>Pads the zero at the End</i>
	*<p>This is an utility method used for building ISO 8583 Message<p>
	*/
	String padChar(String ilosgStr, char ilochPadChar, int ilonuLen, boolean iloboAppend) throws Exception {
		String ilosgPadStr = new String (ilosgStr);
		if (ilonuLen < ilosgStr.length()) {
			return ilosgStr.substring (ilosgStr.length()-ilonuLen);
		}
		for (int i = ilosgStr.length(); i < ilonuLen; i++) {
			if (iloboAppend) {
				ilosgPadStr = ilosgPadStr + ilochPadChar;
			}
			else {
				ilosgPadStr = ilochPadChar + ilosgPadStr;
			}
		}
		return ilosgPadStr;
	}

	/**<i>Checks the Numeric value</i>
	<p>This is an utility method used for validation<p>
	*/
	public boolean isNumeric (String ilosgStr) {
		if(null == ilosgStr){//if13
			return false;
		}//if13
		int lonuLen;
		for (lonuLen = 0; lonuLen < ilosgStr.length (); lonuLen++) {//for07
			if (!Character.isDigit (ilosgStr.charAt (lonuLen)) && ilosgStr.charAt (lonuLen) != ' ') {//if14
				return false;
			}//if14
		}//for07
		return true;
	}

	/**<i>Checks the Alphabets in the given String</i>
	 *<p>This is an utility method used for validation<p>
	 */
	public boolean isAlpha (String ilosgStr) {
		if(null == ilosgStr){//if15
			return false;
		}//if15
		int lonuLen;
		for (lonuLen = 0; lonuLen < ilosgStr.length (); lonuLen++) {//for08
			if (!Character.isLetter (ilosgStr.charAt (lonuLen)) && ilosgStr.charAt (lonuLen) != ' ') {//if16
				return false;
			}//if16
		}//for08
		return true;
	}

		/**<i>Checks the Alphabet and Numeric  in the given String</i>
		<p>This is an utility method used for validation<p>
		*/
		public boolean isAlphaNumeric (String ilosgStr) {
		if(null == ilosgStr){//if17
			return false;
		}//if17
		int lonuLen;
		for (lonuLen = 0; lonuLen < ilosgStr.length (); lonuLen++) {//for09
				if(!Character.isLetter(ilosgStr.charAt (lonuLen))  && !Character.isDigit(ilosgStr.charAt (lonuLen)) && ilosgStr.charAt (lonuLen) != ' ') {//if17
					return false;
				}//if17
			}//for09
			return true;
		}

	/**<i>Checks the Alphabet, Numeric, Hyphen Underscore and Dot</i>
	<p>This is an utility method used for validation<p>
	*/
	public boolean isSpecialChars (String ilosgStr) {
		if(null == ilosgStr){//if18
			return false;
		}//if18
		int lonuLen;
		for (lonuLen = 0; lonuLen < ilosgStr.length (); lonuLen++) {//for10
				if(!Character.isLetter(ilosgStr.charAt (lonuLen))  && !Character.isDigit(ilosgStr.charAt (lonuLen)) &&
					ilosgStr.charAt (lonuLen) != ' ' && ilosgStr.charAt (lonuLen) != '-' &&
					ilosgStr.charAt (lonuLen) != '_' && ilosgStr.charAt (lonuLen) != '.') {//if19
				return false;
			}//if19
		}//for10
		return true;
	}

	/**<i>Validates the Date</i>
	 *<p>This is an utility method used for validation<p>
	 */
	public boolean isValidDate (String ilosgDateFormat, String ilosgDate) {
			Calendar loobCal = Calendar.getInstance ();
			SimpleDateFormat loobSDF = new SimpleDateFormat(ilosgDateFormat);
			loobSDF.setLenient(false);
			try {
				loobCal.setTime(loobSDF.parse(ilosgDate));
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

	/**<i>Converts the Given Hours to Minutes</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsToMins(String ilosgHrs) {
			try {
				if(ilosgHrs == null){//if20
					return 0;
				}//if20
				int lonumMins = 0;
				lonumMins = (Integer.parseInt(ilosgHrs.trim())) * 60;
				return lonumMins;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours to Seconds</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsToSecs(String ilosgHrs) {
			try {
				if(ilosgHrs == null){//if21
					return 0;
				}//if21
				int lonumSecs = 0;
				lonumSecs = (Integer.parseInt(ilosgHrs.trim())) * 60 * 60;
				return lonumSecs;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours and Minitues to Minutes</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsMinsToMins(String ilosgHrsMins) {
			try {
				if(ilosgHrsMins == null ){//if22
					return 0;
				}//if22
				String losgHrs;
				String losgMins;
				if(ilosgHrsMins.indexOf(":") == -1){//if23// for Input String Format = HHMM
					losgHrs = ilosgHrsMins.trim().substring(0,2);
					losgMins = ilosgHrsMins.trim().substring(2);
				}else{//else01 //for Input String Format = HH:MM
					losgHrs = ilosgHrsMins.trim().substring(0,2);
					losgMins = ilosgHrsMins.trim().substring(3);
				}
				int lonumMins = 0;
				lonumMins = (Integer.parseInt(losgHrs.trim())) * 60 + Integer.parseInt(losgMins.trim());
				return lonumMins;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours and Minitues to Seconds</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsMinsToSecs(String ilosgHrsMins) {
			try {
				if(ilosgHrsMins == null ){
					return 0;
				}
				String losgHrs;
				String losgMins;
				if(ilosgHrsMins.indexOf(":") == -1){//if24 // for Input String Format = HHMM
					losgHrs = ilosgHrsMins.trim().substring(0,2);
					losgMins = ilosgHrsMins.trim().substring(2);
				}else{ //else02 //for Input String Format = HH:MM
					losgHrs = ilosgHrsMins.trim().substring(0,2);
					losgMins = ilosgHrsMins.trim().substring(3);
				}
				int lonumMins = 0;
				lonumMins = (Integer.parseInt(losgHrs.trim())) * 60* 60 + (Integer.parseInt(losgMins.trim())) * 60;
				return lonumMins;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours and Minitues to Minutes</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsMinsToMins(String ilosgHrs, String ilosgMins) {
			try {
				if(ilosgHrs == null || ilosgMins == null){//if25
					return 0;
				}//if25
				int lonumMins = 0;
				lonumMins = (Integer.parseInt(ilosgHrs.trim())) * 60 + Integer.parseInt(ilosgMins.trim());
				return lonumMins;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours and Minitues to Seconds</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsMinsToSecs(String ilosgHrs, String ilosgMins) {
			try {
				if(ilosgHrs == null || ilosgMins == null){//if26
					return 0;
				}//if26
				int lonumSecs = 0;
				lonumSecs = (Integer.parseInt(ilosgHrs.trim())) * 60 * 60 + (Integer.parseInt(ilosgMins.trim())) * 60;
				return lonumSecs;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Converts the Given Hours,Minitues, Sec  to Seocnds</i>
	 *<p>This is an utility method used for converstion<p>
	 */
	public int convertHrsMinsSecsToSecs(String ilosgHrs, String ilosgMins,String ilosgSecs) {
			try {
				if(ilosgHrs == null || ilosgMins == null || ilosgSecs == null){//if27
					return 0;
				}//if27
				int lonumSecs = 0;
				lonumSecs = (Integer.parseInt(ilosgHrs.trim())) * 60 * 60 + (Integer.parseInt(ilosgMins.trim())) * 60 +Integer.parseInt(ilosgSecs.trim());
				return lonumSecs;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}

	/**<i>Validates the Card Expiry Date</i>
	 *<p>This is an utility method used for validation<p>
	 */
	public boolean isValidExpDate (String ilosgDate) {
		try {
			Calendar loobSysDate = Calendar.getInstance ();
			loobSysDate.set (
				Integer.parseInt (dateTime("yyyy")),
				Integer.parseInt (dateTime("MM")) - 1,
				Integer.parseInt (dateTime("dd")));
			Calendar loobExpDate = Calendar.getInstance ();
			loobExpDate.set(Integer.parseInt(ilosgDate.substring (6)), Integer.parseInt (ilosgDate.substring (3, 5)) - 1, Integer.parseInt(ilosgDate.substring (0, 2)));
			return loobExpDate.after(loobSysDate);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**<i>Validates the Start Date</i>
	<p>This is an utility method used for validation<p>
	*/
	public boolean isValidStartDate (String ilosgDate) {
		try {
			Calendar loobSysDate = Calendar.getInstance ();
			loobSysDate.set (
				Integer.parseInt (dateTime("yyyy")),
				Integer.parseInt (dateTime("MM")) - 1,
				Integer.parseInt (dateTime("dd")));
			Calendar loobStartDate = Calendar.getInstance ();
			SimpleDateFormat loobSDF = new SimpleDateFormat ("MM/dd/yyyy");
			loobSDF.setLenient (false);
			try {
				loobStartDate.setTime (loobSDF.parse (ilosgDate));
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			if (loobStartDate.before (loobSysDate)) {//if28
				return false;
			}//if28
			loobSysDate.add (Calendar.DATE, 90);
			return loobStartDate.before (loobSysDate);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**<i>Validates the From & To date</i>
	<p>This is an utility method used for validation<p>
	*/
	boolean isValidFromAndToDate (String ilosgFromDate, String ilosgToDate) {
		try {
			SimpleDateFormat loobSDF = new SimpleDateFormat ("MM/dd/yyyy");
			loobSDF.setLenient (false);
			java.util.Date loobFromDate = loobSDF.parse(ilosgFromDate);
			java.util.Date loobToDate = loobSDF.parse (ilosgToDate);
			return !loobToDate.before (loobFromDate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/**<i>Validates the From & To date</i>
	<p>This is an utility method used for validation<p>
	*/
	boolean isValidFromAndToDate (String ilosgFromDate, String ilosgToDate, String ilosgOriginalDate) {
		try {
			SimpleDateFormat loobSDF = new SimpleDateFormat ("MM/dd/yyyy");
			loobSDF.setLenient (false);
			java.util.Date loobFromDate		 = loobSDF.parse(ilosgFromDate);
			java.util.Date loobToDate		 = loobSDF.parse (ilosgToDate);
			java.util.Date iloobOriginalDate = loobSDF.parse(ilosgOriginalDate);
			//System.out.println(iloobOriginalDate.after(loobFromDate) +" "+ iloobOriginalDate.before(loobToDate));
			if( iloobOriginalDate.after(loobFromDate) && iloobOriginalDate.before(loobToDate)){//if29
				return true;
			}else{//else03
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**<i>Returns the Current Date in the Given Format</i>
	<p>This is an utility method used for get the Current date in required Format<p>
	*/
	public String dateTime(String ilosgDateFormat) {
			try{
				SimpleDateFormat loobSDF = new SimpleDateFormat (ilosgDateFormat);
				 String olosgDate= loobSDF.format(new java.util.Date());
				return olosgDate;
			} catch (Exception e) {
				return null;
			}
	}

	@SuppressWarnings("deprecation")
	public String formatB24DateTime (String p12, String p17) {
			String formattedDate = null;
			try {
				java.util.Date	date	=	 new java.util.Date();
				int hr=0;
				hr	=	date.getHours();
				int yr=0;
				yr	=	date.getYear()+1900;
				if(p17.substring(0,2) .equals("01") && p17.substring(2,4) .equals("01") && hr >= 23) //if30
					yr +=1;
					p17	=	p17 + yr;
					formattedDate	=	p17.substring(0,2) +"/"+ p17.substring(2,4) + "/" + yr +" " +p12.substring(0,2) + ":" + p12.substring(2,4) + ":" + p12.substring(4,6);
			} catch (Exception e) {

			}
			return formattedDate;
	}



	/**<i>Formats the Date field from the Database basedstored value to </i>
	<p>This is an utility method used for building the formatted date<p>
	*/
	public String formatDate (String ilosgDate, String ilosgFromDateFormat, String ilosgToDateFormat) {
			try{
				SimpleDateFormat loobSDF = new SimpleDateFormat (ilosgFromDateFormat);
				java.util.Date loobTmpDate = loobSDF.parse(ilosgDate);
				loobSDF = new SimpleDateFormat(ilosgToDateFormat);
				String olosgDate = loobSDF.format(loobTmpDate );
				return olosgDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	/**<i>Returns the Start Date of the week in the format given</i>
	*/
	public String currentWeekStartDate(String ilosgDateFormat) {
		try{
			Calendar c = Calendar.getInstance();
			Date d = new Date();
				long l = d.getTime();
			d.setTime(l - ((c.get(Calendar.DAY_OF_WEEK)-1) * 1000 * 60 * 60 * 24));
			c.setTime(d);
			String losgDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) +"/"+c.get(Calendar.DAY_OF_MONTH);
			String olosgDate = formatDate(losgDate,"yyyy/mm/dd", ilosgDateFormat);
			return olosgDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**<i>Returns the End Date of the week in the format given</i>
	*/
	public String currentWeekEndDate(String ilosgDateFormat) {
		try {
			Calendar c = Calendar.getInstance();
			Date d = new Date();
			long l = d.getTime();
			d.setTime(l + (7-c.get(Calendar.DAY_OF_WEEK)) * 1000 * 60 * 60 * 24);
			c.setTime(d);
			String losgDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+1) +"/"+c.get(Calendar.DAY_OF_MONTH);
			String olosgDate = formatDate(losgDate,"yyyy/mm/dd", ilosgDateFormat);
			return olosgDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**<i>Returns the Start Date of the Month in the format given</i>
	*/
	public String currentMonthStartDate(String ilosgDateFormat) {
		try{
			Calendar loobCal = Calendar.getInstance();
			String losgDate = loobCal.getActualMinimum(Calendar.DATE) + "/" + Calendar.MONTH + "/" + loobCal.get(Calendar.YEAR);
			SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
			java.util.Date loobTmpDate = loobSDF.parse(losgDate);
			loobSDF = new SimpleDateFormat(ilosgDateFormat);
			String olosgDate = loobSDF.format(loobTmpDate );
			return olosgDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**<i>Returns the End Date of the Month in the format given</i>
	*/
	public String currentMonthEndDate(String ilosgDateFormat) {
		try{
			Calendar loobCal = Calendar.getInstance();
			String losgDate = loobCal.getActualMaximum(Calendar.DATE) + "/" + Calendar.MONTH + "/" + loobCal.get(Calendar.YEAR);
			SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
			java.util.Date loobTmpDate = loobSDF.parse(losgDate);
			loobSDF = new SimpleDateFormat(ilosgDateFormat);
			String olosgDate = loobSDF.format(loobTmpDate );
			return olosgDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

/**<i>Returns the Start Date of the current in the format given</i>
	*/
	public String currentYearStartDate(String ilosgDateFormat) {
		try {
			Calendar c = Calendar.getInstance();
			String olosgDate = formatDate(c.get(Calendar.YEAR)+"01/01","yyyy/mm/dd", ilosgDateFormat);
			return olosgDate;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**<i>Returns the End Date of the current year in the format given</i>
	*/
	public String currentYearEndDate(String ilosgDateFormat) {
			try{
						Calendar c = Calendar.getInstance();
						String olosgDate = formatDate(c.get(Calendar.YEAR)+"12/31","yyyy/mm/dd", ilosgDateFormat);
						return olosgDate;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
			}
	}

	/**<i>Returns the Start Date of the required Quarterly Month in the format given</i>
	*/
	public String StartDateByQuarterly(String ilosgDateFormat, int ilonuQno) {
			try{
				if(ilonuQno > 4){//if31
					ilonuQno = 4;
				}//if31
				if(ilonuQno < 0 ){//if32
					ilonuQno = 0;
				}//if32
				Calendar loobCal = Calendar.getInstance();
				if(ilonuQno == 0){//if33
					int lonumMon = loobCal.get(Calendar.MONTH);
					if(lonumMon < 3){//if34
						ilonuQno = 1;
					}else if(ilonuQno  < 6 ){//eif01
						ilonuQno = 2;
					}else if(ilonuQno  < 9 ){//eif02
						ilonuQno = 3;
					}else{//else04
						ilonuQno = 4;
					}
				}//if33
				String losgCalMonth[] = {"01", "04", "07","09"};
				String losgDate = "";
				losgDate = loobCal.getActualMinimum(Calendar.DATE) + "/" + losgCalMonth[ilonuQno-1]+ "/" + loobCal.get(Calendar.YEAR);
				SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
				java.util.Date loobTmpDate = loobSDF.parse(losgDate);
				loobSDF = new SimpleDateFormat(ilosgDateFormat);
				String olosgDate = loobSDF.format(loobTmpDate );
				return olosgDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	/**<i>Returns the Start Date of the required Quarterly Month in the format given</i>
	*/
	public String EndDateByQuarterly(String ilosgDateFormat, int ilonuQno) {
			try{
				if(ilonuQno > 4){//if34
					ilonuQno = 4;
				}//if34
				if(ilonuQno < 0 ){//if35
					ilonuQno = 0;
				}//if35
				Calendar loobCal = Calendar.getInstance();
				if(ilonuQno == 0){//if36
					int lonumMon = loobCal.get(Calendar.MONTH);
					if(lonumMon < 3){//if37
						ilonuQno = 1;
					}else if(ilonuQno  < 6 ){//eif03
						ilonuQno = 2;
					}else if(ilonuQno  < 9 ){//eif04
						ilonuQno = 3;
					}else{//else05
						ilonuQno = 4;
					}
				}//if36
				String losgCalMonth[] = {"03", "06", "09","12"};
				String losgCalDate[] = {"31", "30", "30","31"};
				String losgDate = "";
				losgDate = losgCalDate[ilonuQno-1]+ "/" + losgCalMonth[ilonuQno-1]+ "/" + loobCal.get(Calendar.YEAR);
				SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
				java.util.Date loobTmpDate = loobSDF.parse(losgDate);
				loobSDF = new SimpleDateFormat(ilosgDateFormat);
				String olosgDate = loobSDF.format(loobTmpDate );
				return olosgDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}


	/**<i>Returns the Start Date of the required HalfYearly in the format given</i>
	*/
	public String StartDateByHalfYearly(String ilosgDateFormat, int ilonuQno) {
			try{
				if(ilonuQno > 2){//if37
					ilonuQno = 2;
				}//if37
				if(ilonuQno < 0 ){//if38
					ilonuQno = 0;
				}//if38
				Calendar loobCal = Calendar.getInstance();
				if(ilonuQno == 0){//if39
					int lonumMon = loobCal.get(Calendar.MONTH);
					if(lonumMon < 6){//if40
						ilonuQno = 1;
					}else{//else06
						ilonuQno = 2;
					}
				}//if39
				String losgCalMonth[] = {"01", "06"};
				String losgDate = "";
				losgDate = loobCal.getActualMinimum(Calendar.DATE) + "/" + losgCalMonth[ilonuQno-1]+ "/" + loobCal.get(Calendar.YEAR);
				SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
				java.util.Date loobTmpDate = loobSDF.parse(losgDate);
				loobSDF = new SimpleDateFormat(ilosgDateFormat);
				String olosgDate = loobSDF.format(loobTmpDate );
				return olosgDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	/**<i>Returns the Start Date of the required HalfYearly in the format given</i>
	*/
	public String EndDateByHalfYearly(String ilosgDateFormat, int ilonuQno) {
			try{
				if(ilonuQno > 2){//if41
					ilonuQno = 2;
				}//if41
				if(ilonuQno < 0 ){//if42
					ilonuQno = 0;
				}//if42
				Calendar loobCal = Calendar.getInstance();
				if(ilonuQno == 0){//if43
					int lonumMon = loobCal.get(Calendar.MONTH);
					if(lonumMon < 6){//if44
						ilonuQno = 1;
					}else{//else07
						ilonuQno = 2;
					}
				}//if43
				String losgCalMonth[] = {"06", "12"};
				String losgCalDate[]   = {"30","31"};
				String losgDate = "";
				losgDate = losgCalDate[ilonuQno-1]+ "/" + losgCalMonth[ilonuQno-1]+ "/" + loobCal.get(Calendar.YEAR);
				SimpleDateFormat loobSDF = new SimpleDateFormat ("dd/MM/yyyy");
				java.util.Date loobTmpDate = loobSDF.parse(losgDate);
				loobSDF = new SimpleDateFormat(ilosgDateFormat);
				String olosgDate = loobSDF.format(loobTmpDate );
				return olosgDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}


	/**<i>Remove the zeros in the String</i>
	 *<p>This is an utility method used for building ISO 8583 Message<p>
	 */
	public String removeZeroes(String ilosgStr){
			String olosgStr = new String();
			for(int i=0;i<ilosgStr.length()-2;i++) {//for11
				if( !ilosgStr.substring(i,i+1).equals("0") ){//if45
					olosgStr = ilosgStr.substring(i,ilosgStr.length()-2);
					break;
				}//if45
			}//for11
			if(Integer.parseInt(ilosgStr.substring(10,11)) > 0){//if46
				olosgStr+="."+ ilosgStr.substring(10,11);
			}//if46
			if(Integer.parseInt(ilosgStr.substring(11)) > 0){//if47
					olosgStr+=ilosgStr.substring(11);
			}//if47
			return olosgStr;
		}

	/**<i>Remove the Leading zeros in the String</i>
 	 *<p>This is an utility method used for building ISO 8583 Message<p>
	 */
	public String removeLeadingZeroes(String ilosgStr){
		String olosgStr = new String();
		for(int i=0;i<ilosgStr.length();i++) {//for12
			if( !ilosgStr.substring(i,i+1).equals("0") ){//if48
				olosgStr = ilosgStr.substring(i);
				break;
			}//if48
		}//for12
		return olosgStr;
	}

	/**<i>Formats the amount given in the String with and without decimal place</i>
	  *  if the mode == 1 then the return value is formatted amount with decimal places
	  *  if the mode == 2 then the return value is 12 digit formatted amount without decimal place
 	  */
	public String getFormattedAmount(String ilosgStr,int ilonuMode){

		String olosgFAmt = "";
		int lonuIndex = 0;
		if(ilonuMode == 1) {//if49
			if( ilosgStr.trim().equals("") ) {
				return ilosgStr;
			}
			if( Long.parseLong(ilosgStr) == 0 ) {
				ilosgStr = "0.00";
				return ilosgStr;
			}
			else {
				ilosgStr 	= removeLeadingZeroes(ilosgStr);
				lonuIndex = ilosgStr.length();
				olosgFAmt	= ilosgStr.substring(0,lonuIndex-2)+"."+ilosgStr.substring(lonuIndex-2);
				return olosgFAmt;
			}
		}//if49
		if(ilonuMode == 2){//if50
			lonuIndex =  ilosgStr.indexOf(".");
			if(lonuIndex == -1){//if51
				ilosgStr = ilosgStr + "00";
			}//if51
			if((ilosgStr.length() - lonuIndex) == 1){//if52
				ilosgStr = ilosgStr + "0";
			}//if52
			olosgFAmt 	= removeDecimal(ilosgStr);
			olosgFAmt 	= leftPadZeros(olosgFAmt,12);
			return olosgFAmt;
		}//if50
		return ilosgStr;
	}

	/**<i>Remove the decimal places in the String</i>
 	  *<p>This is an utility method used for building ISO 8583 Message<p>
	  */
	public String removeDecimal(String ilosgStr){
		String olosgStr = new String();
		if (ilosgStr.indexOf(".") == -1){//if53
			return ilosgStr+"00";
		} else {//else08
			int k = ilosgStr.indexOf(".");
			olosgStr = 	ilosgStr.substring(k+1);
			olosgStr += (olosgStr.length()==1)?"0":"";
			return ilosgStr.substring(0,k)+olosgStr;
		}
	}//if53

	/**<i>Rounds the Floating Number in the given String</i>
	  *<p>This is an utility method used for building ISO 8583 Message<p>
	  */
	public static String roundingFloatValue (String ilosgStr) {
		char lochChar = 0;
		String losgDecStr = null;
		String olosgValue = null;
		olosgValue = ilosgStr;
		int lonuVal = 0;
		int lonuIndex=0;
		int lonuIntVal=0;
		if(olosgValue.indexOf(".")!=-1) {//if54
		   lonuIndex = olosgValue.indexOf(".");
		   losgDecStr = olosgValue.substring(lonuIndex+1);
		  if(losgDecStr.length() > 2)  {//if55
			lochChar= losgDecStr.charAt(2);
			if(Integer.parseInt(String.valueOf(lochChar))>5) {//if56
				lonuVal = Integer.parseInt(losgDecStr.substring(0,2));
				lonuVal = lonuVal+1;
				if ( String.valueOf(lonuVal).length() >2) {//if57
					lonuIntVal = Integer.parseInt(olosgValue.substring(0,lonuIndex))  + 1;
					olosgValue = String.valueOf(lonuIntVal) + ".00";
				}else{//else09
					olosgValue = olosgValue.substring(0, lonuIndex) + "." + String.valueOf(lonuVal);
				}
			  }else{//else10
				  olosgValue = olosgValue.substring(0, lonuIndex+3);
			  }
		  }//if55
 		}//if54

		return olosgValue;
   }


	public String genPIN( String ilosgCHN) {
			String ilosgPIN = "";
			try {
				String losgRandomPin =null;
				Random loobRnd  = new Random();
				losgRandomPin = new Double(loobRnd.nextDouble()).toString().substring(2,2+4);
				ilosgPIN  =  generatePin(ilosgCHN.substring(0,16),"6E38ECF12C027038", 4, losgRandomPin);
			} catch (Exception e) {
				}
			return ilosgPIN;
	}

	public String generatePin(String PAN,String keyManagementKey, int pinLength, String PINOffset) throws Exception{

		String pin;
		try{
			java.security.Security.addProvider(new cryptix.provider.Cryptix());
			try{
				pin = getHexValue(PAN,keyManagementKey);
			}catch(Exception e){
				return null;
			}
			fillDecimalizationTable("0123456789012345");
			pin  = decimalizePin(pin);
			pin = pin.substring(0,pinLength);
			/*Random r1  = new Random();
			randomPin = new Double(r1.nextDouble()).toString().substring(2,2+pinLength);*/
			pin = generateFinalPin(pinLength,pin,PINOffset);
			return pin;

		}catch(Exception e){

			response[0] = e.getMessage();
			return null;
		}
	}

	private String generateFinalPin(int pinLength,String pin,String randomPin) {

		String temp = pin;
		pin="";

		for(int i=0;i<pinLength;i++){
			int k = Integer.parseInt(temp.substring(i,i+1));
			int h = Integer.parseInt(randomPin.substring(i,i+1));
			pin+=Integer.toString((k+h)%10);
		}
		return pin;
	}

	public String getHexValue(String pin,String key) throws Exception {
		try{
			des=Cipher.getInstance("DES/ECB/NONE","Cryptix");
			desKey = new RawSecretKey("DES",Hex.fromString(key));

			des.initEncrypt(desKey);

			byte[] pinInByteArray = Hex.fromString(pin);
			byte[] ciphertext = des.crypt(pinInByteArray);
			return(Hex.toString(ciphertext));
		}catch(Exception e){
			throw e;
		}
	}

	private String decimalizePin(String pin) {
		String [] pinInArray = new String[16];
		for(int i=0;i<16;i++){
			pinInArray[i] = pin.substring(i,i+1);
		}
		for(int i=0;i<pinInArray.length;i++){
			if(pinInArray[i].equalsIgnoreCase("A"))pinInArray[i] = decimalTable[10][1];
			if(pinInArray[i].equalsIgnoreCase("B"))pinInArray[i] = decimalTable[11][1];
			if(pinInArray[i].equalsIgnoreCase("C"))pinInArray[i] = decimalTable[12][1];
			if(pinInArray[i].equalsIgnoreCase("D"))pinInArray[i] = decimalTable[13][1];
			if(pinInArray[i].equalsIgnoreCase("E"))pinInArray[i] = decimalTable[14][1];
			if(pinInArray[i].equalsIgnoreCase("F"))pinInArray[i] = decimalTable[15][1];
		}
		pin="";
		for(int i=0;i<16;i++){
			pin+=pinInArray[i];
		}
		return pin;
	}

	private void fillDecimalizationTable(String decimalizationTable) {

		for(int i=0;i<16;i++){
			decimalTable[i][0] = new Integer(i).toString();
		}
		for(int i=0;i<16;i++){
			decimalTable[i][1] = decimalizationTable.substring(i,i+1);
		}
		return;
	}


	public static synchronized String getAuthId () throws Exception {
		BufferedReader bufferedReader = new BufferedReader (new FileReader ("authid.txt"));
		int intAuthId = Integer.parseInt (bufferedReader.readLine ());
		//int intRRN = Integer.parseInt (bufferedReader.readLine ());
		bufferedReader.close ();
		if(999999 == intAuthId) {//if64
		   intAuthId = 0;
		}//if64
		String strAuthId = String.valueOf (++intAuthId);
		strAuthId =  ("000000" + strAuthId).substring (strAuthId.length ());
		BufferedWriter bufferedWriter = new BufferedWriter (new FileWriter ("authid.txt"));
		bufferedWriter.write (strAuthId);
		bufferedWriter.close ();
		return strAuthId;
	}

    public String hex2binary(String hexString) {

		if( hexString == null )
			return null;

		if( hexString.length()%2 != 0 )
			hexString="0"+hexString;

		String binary = "";
		String temp = "";

		for ( int i=0; i < hexString.length(); i++ ) {
			temp = "0000"+ Integer.toBinaryString(Character.digit(hexString.charAt(i),16));
			binary += temp.substring(temp.length()-4);
		}
		return binary;
	}

	public String generateRandom() {

		String randomPin = null;

		while(true) {
			Random r1 = new Random();

			randomPin = new Double(r1.nextDouble()).toString();

			if( randomPin.length() > 18 ) {
				break;
			}
		}

		return randomPin;
	}

	/* Writes to a file in case of failure in inserting transaction log */
	public void WriteTxnlogToFile_DBdown(String query) {

		try {
            String LABELS    = "com.fss.m24.LogFile";
            Locale locale 	 = Locale.getDefault();
            String FILE_PATH = null;
            ResourceBundle labels = ResourceBundle.getBundle(LABELS, locale);

            if( labels != null ) {//if65
                FILE_PATH = labels.getString("FILE_PATH");
			}//if65

			WritableByteChannel out =
					new FileOutputStream(FILE_PATH, true).getChannel();
			Charset charset = Charset.forName("ISO-8859-1");

			out.write(charset.encode(query+"\n"));
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to write stack trace to the log file
	 * @param throwable - Exception
	 * @param log		- Logger
	 */
	/*public void printStackTraceLog(Throwable throwable, Logger log) {

		StringWriter sw = null;
		PrintWriter pw  = null;

		try {
	        sw = new StringWriter();
	        pw = new PrintWriter(sw);
	        throwable.printStackTrace(pw);
	        log.error("\n"+sw.toString());
		}
		finally {
			if( sw != null )
				try {
					sw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if( pw != null )
				pw.close();
		}
	}*/
}//End of M24Utility
