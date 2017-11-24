/**
 * ********************************************************************
 *			COMPANY		-	FSS Pvt Ltd.
 * ********************************************************************
 *		Name of the Program			:	M24ISOMethods
 *		Description					:  Used as the common utility methods for Message 24 API
 *		Classes Referrred To		:
 *		Incoming Parmaters			:
 *		Outgoing Parameters			:
 *		Tables Used					:
 *		Values	From Session		:
 *		Values To Session			:
 *		Created By					:	Sankar P				Created Date	: 	18/03/2006
 *		Modified By					:	Aravindan. G			Modified Date	:
 *		Reason For Modification		:
 *		Modified Tag/CR No.			:
 *		author  					:	Sankar P
 */
package api;

import java.util.Hashtable;
import java.util.Vector;

public class M24ISOMethods {

	M24Utility m24Utility	= null;

	String[] MESSAGESTATUS = {
		"REQ", "RSP", "REQ_LOG_ON", "RSP_LOG_OFF", "CDOWN_RSP", "CDOWN_RSP_LOG",
		"CDOWN_RSP_LOG_OFF",  "CDOWN_RSP_LOG_OFF_LOG", "CDOWN_RVSL_REQ",
		"HDOWN_REQ", "HDOWN_RSP", "HCDOWN_REQ_LOG", "HCDOWN_RSP",
		"HCDOWN_RSP_LOG", "CHDOWN_RVSL_REQ", "SUSPECT_RVSL_LOG",
		"TIME_OUT_RSP", "TIME_OUT_RSP_LOG", "COMPLETE_RVSL_LOG",
		"INVALID_MSG_LOG", "INVALID_STATUS_LOG"
	};

	public static char[] HPDHbitmaptype = {

		'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
		'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
		'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
		'N', 'N', 'N', 'A', 'A', 'A', 'A', 'N',
		'N', 'N', 'N', 'N', 'A', 'A', 'A', 'A',
		'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A',
		'N', 'N', 'N', 'N', 'N', 'A', 'A', 'A',
		'A', 'A', 'A', 'A', 'A', 'N', 'A', 'A',
	};

	/*final int[] BITMAP87 = {
		-2, -2, -3, 12,  6,  2,  3, 16, 15, 40, 27, -2, -3, -3,  47, 3,  3,  3, 16, 16, 15, -3, -3, -3,  0,  0, 15, 16, 13, 19, 16,
		 0,  1,  2,  3,  3,  3,  4,  4, 6, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 16, 16, 16, 16, 42,  0,  2,  5,  7, 42, 16,
		17,  0, -2, -2,  0, -2, -2,  0, -3, -3, -3, -3, -3, -3, -3,  0, 0,  0,  0,  0,  0,  0,  0, 36, 0,  0, 15,  4, -3, -3,  -3, 16

	};*/

	public static int[] HPDHbitmap = {

		16, -1,  6, 12, 12, 12, 10,  8,
		 8,  8,  6,  6,  4,  4,  4,  4,
		 4,  4,  3,  3,  3,  3,  3,  3,
		 2,  2,  1,  9,  9,  9,  9, -1,
		-1, -1, -1, -1, 12,  6,  2, -3,
		 8, 15, 40, 20, -1, -1, -2, -2,
		 3,  3,  3, 16,  8, -2, -2, -1,
		-1,	-1, -1, -2, -2, -2, -2,  8,

	};


	public static char[] HPDHbitmapPadAttribute = {

		'F', 'B', 'F', 'F', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'B', 'F', 'F', 'F', 'F',
		'F', 'F', 'B', 'F', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'B', 'F', 'F', 'F', 'F',
		'F', 'F', 'F', 'F', 'F', 'B', 'F', 'F',
	};



	/*final int[] BITMAP87 = {
		-2, -2, -3, 12,  6,  2,  3, 16, 15, 40, 27, -2, -3, -3,  47, 3,  3,  3, 16, 16, 15, -3, -3, -3,  0,  0, 15, 16, 13, 19, 16,
		 0,  1,  2,  3,  3,  3,  4,  4, 6, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 16, 16, 16, 16, 42,  0,  2,  5,  7, 42, 16,
		17,  0, -2, -2,  0, -2, -2,  0, -3, -3, -3, -3, -3, -3, -3,  0, 0,  0,  0,  0,  0,  0,  0, 36, 0,  0, 15,  4, -3, -3,  -3, 16

	};*/

	/*final int[] BITMAP87 = {
		16, -2,  6, 12, 12, 12, 10,  8,
		8,  8,	6,  6,  4,  4,  4,  4,
		4,  4, 3,  3,	3,  3,  3,  3,
		2,  2,  1,  9,  9,  9,	9, -2,
		-2, -2, -2, -3,	12,  6,  2,  3,
		16, 15, 40, -2, -2, -3, -3,  -3,
		3,  3,	3, 16, 16, 15, -3, -3,
		-3,  0,  0, -3, -3, 13, 19, 16,
		0,  1,  2,  3,  3,  3,	4,  4,
		6, 10, 10, 10, 10, 10, 10, 10,
		10, 12, 12, 12, 12, 16, 16, 16,
		16,	42,  0,  2,  5,  7, 42, 16,
		17,  0, -2,	-2,  0, -2, -2,  0,
		-3,	-3, -3, -3, -3,	-3, -3,  0,
		0,  0,  0,  0,  0,  0,  0,	36,
		-3,  -3, -3,  -3, -3, -3,  -3, 16
	};*/

	//Changed the P-54 fixed length bitmap from 15 to 46
	final int[] BITMAP87 = { //Changed  on 23/01/2007 for key exchange message
		/*16, -2,  6, 12, 12, 12, 10,  8,
		8,  8,	6,  6,  4,  4,  4,  4,
		4,  4,  3,  3,	3,  3,  3,  3,
		2,  2,  1,  9,  9,  9,	9, -2,
		-2, -2, -2, -3,	12,  6,  2,  3,
		16, 15, 40, 27, -2, -3, -3,  -3,
		3,  3,	3, 16, 16, 49, -3, -3,
		-3,  0,  0, -3, -3, -3, -3, -3,
		0,  1,  2,  3,  3,  3,	4,  4,
		6, 10, 10, 10, 10, 10, 10, 10,
		10, 12, 12, 12, 12, 16, 16, 16,
		16,	42,  0,  2,  5,  7, 42, 16,
		17,  0, -2,	-2,  0, -2, -2,  0,
		-3,	-3, -3, -3, -3,	-3, -3,  0,
		0,  0,  0,  0,  0,  0,  0,	36,
		-3,  -3, -3,  -3, -3, -3,  -3, 16*/

		16, -2,  6, 12, 12, 12, 10,  8,
		8,  8,	6,  6,  4,  4,  4,  4,
		4,  4, 3,  3,	3,  3,  3,  3,
		2,  2,  1,  9,  9,  9,	9, -2,
		-2, -2, -2, -3,	12,  6,  2,  3,
		16, 15, 40, -2, -2, -3, -3,  -3,
		3,  3,	3, 16, 16, 15, -3, -3,
		-3,  0,  0, -3,	-3, 13, -3, 16,
		0,  1,  2,  3,  3,  3, 	4,  4,
		6, 10, 10, 10, 10, 10, 10, 10,
		10, 12, 12, 12, 12, 16, 16, 16,
		16,	42,	0,  2,  5,  7, 42, 16,
		17,  0, -2,	-2,	-2, -2, -2,  0,
		-3,	-3, -3, -3, -3, -3, -3,  0,
		0,  0,  0,  0,  0,  0,  0, 36,
		-3,  -3, -3,  -3, -3, -3,  -3, 16

	};

	/*final int[] BITMAP87 = { //Changed  on 23/01/2007 for key exchange message
		16, -2,  6, 12, 12, 12, 10,  8,
		8,  8,	6,  6,  4,  4,  4,  4,
		4,  4,  3,  3,	3,  3,  3,  3,
		2,  2,  1,  9,  9,  9,	9, -2,
		-2, -2, -2, -3,	12,  6,  2,  3,
		16, 15, 40, 27, -2, -3, -3,  -3,
		3,  3,	3, 16, 16, 49, -3, -3,
		-3,  0,  0, -3, -3, -3, -3, -3,
		0,  1,  2,  3,  3,  3,	4,  4,
		6, 10, 10, 10, 10, 10, 10, 10,
		10, 12, 12, 12, 12, 16, 16, 16,
		16,	42,  0,  2,  5,  7, 42, 16,
		17,  0, -2,	-2,  0, -2, -2,  0,
		-3,	-3, -3, -3, -3,	-3, -3,  0,
		0,  0,  0,  0,  0,  0,  0,	-3,
		-3,  -3, -3,  -3, -3, -3,  -3, 16
	};*/

	// P-54 modified to a fixed length of 16 for all the information to be sent to Tenet ATM
	final int[] LogonRSPbitmap87 = {

		16, -2,  6, 12, 12, 12, 10,  8,
		8,  8,  6,  6,  4,  4,  4,  4,
		4,  4,  3,  3,  3,  3,  3,  3,
		2,  2,  1,  9,  9,  9,  9, -2,
		-2, -2, -2, -3, 12,  6,  2,  3,
		16, 15, 40, -2, -2, -3, -3,  -3,
		3,  3,  3, 16, 16, -2, -3, -3,
		-3,  0,  0, -3, -3, 13, 19, 16,
		 0,  1,  2,  3,  3,  3,  4,  4,
		6, 10, 10, 10, 10, 10, 10, 10,
		10, 12, 12, 12, 12, 16, 16, 16,
		16, 42,  0,  2,  5,  7, 42, 16,
		17,  0, -2, -2,  0, -2, -2,  0,
		-3, -3, -3, -3, -3, -3, -3,  0,
		0,  0,  0,  0,  0,  0,  0, 36,
		-3,  -3, -3,  -3, -3, -3,  -3, 16

	};

	public M24ISOMethods(){
		m24Utility	= new M24Utility();
	}


	public String buildHPDHResponse(String iobsgMsgType, String iobsgResponseCode,
										Hashtable<String, String> pthtISOBuffer) {

		String losgMessage = null;
		try {
			iobsgMsgType = buildMessageType(iobsgMsgType);
			pthtISOBuffer.put("P-39", iobsgResponseCode);

			pthtISOBuffer.put("TPDU-ID", pthtISOBuffer.get ("TPDU-ID").toString().trim().substring(0,2) +
											pthtISOBuffer.get ("TPDU-ID").toString().trim().substring(6,10) +
												pthtISOBuffer.get ("TPDU-ID").toString().trim().substring(2,6));

			losgMessage = HPDHBuilder(iobsgMsgType, pthtISOBuffer);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}


	public String buildTenetResponse(String iobsgMsgType, String iobsgResponseCode,
										Hashtable<String, String> pthtISOBuffer) {
		String losgMessage = null;
		try {
			iobsgMsgType = buildMessageType(iobsgMsgType);
			pthtISOBuffer.put("P-39", iobsgResponseCode);

			if( iobsgMsgType.equals("0810") )
				losgMessage = LogonRSPISOBuilder87(iobsgMsgType, pthtISOBuffer);
			else
				losgMessage = ISOBuilder87(iobsgMsgType, pthtISOBuffer);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}

	/**
	 *<i>Build the ISO 8583 Response Message</i>
	 *<p>This method builds and returns the ISO 8583 Financial Response message<p>
	 */
	public String buildResponse87(String iobsgMsgType,
									Hashtable<String, String> pthtISOBuffer) {

		String losgMessage = null;
		try {
			//iobsgMsgType = buildMessageType(iobsgMsgType);
			losgMessage = pthtISOBuffer.get("ISO").toString() +
								pthtISOBuffer.get("DC-ID").toString() +
									pthtISOBuffer.get("REL-ID").toString() +
										pthtISOBuffer.get("REASON-CODE").toString() +
											pthtISOBuffer.get("ORIGINATOR").toString() +
												"5";

			if( pthtISOBuffer.get("DC-ID").toString().equals("01") )
					pthtISOBuffer.put("P-38", pthtISOBuffer.get("P-11").toString());

			losgMessage += ISOBuilder87(iobsgMsgType, pthtISOBuffer);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}

	/**
	 *<i>Build the ISO 8583 Response Message</i>
	 *<p>This method builds and returns the ISO 8583 Financial Response message<p>
	 */
	
	public String buildResponse(String iobsgMsgType, String iobsgResponseCode,
			Hashtable<String, String> pthtISOBuffer) {

String losgMessage = null;
try {
	System.out.println("iobsgMsgType " +iobsgMsgType);
	System.out.println("iobsgResponseCode " +iobsgResponseCode);
	System.out.println("pthtISOBuffer " +pthtISOBuffer);
iobsgMsgType = buildMessageType(iobsgMsgType);
losgMessage = pthtISOBuffer.get("ISO").toString() +
pthtISOBuffer.get("DC-ID").toString() +
	pthtISOBuffer.get("REL-ID").toString() +
		pthtISOBuffer.get("REASON-CODE").toString() +
			pthtISOBuffer.get("ORIGINATOR").toString() +
				"5";
pthtISOBuffer.put("P-39", iobsgResponseCode);
if(pthtISOBuffer.get("DC-ID").toString().equals("01") )
pthtISOBuffer.put("P-38", pthtISOBuffer.get("P-11").toString());

losgMessage += ISOBuilder87(iobsgMsgType, pthtISOBuffer);
}
catch(Exception e) {
e.printStackTrace();
}
return losgMessage;
}

	public String buildReversalResponse87(String iobsgMsgType,
									String iobsgResponseCode,
									Hashtable<String, String> pthtISOBuffer) {

		String losgMessage = null;

		try {
			//iobsgMsgType = buildMessageType(iobsgMsgType);
			losgMessage = pthtISOBuffer.get("ISO").toString() +
						 		pthtISOBuffer.get("DC-ID").toString() +
						 			pthtISOBuffer.get("REL-ID").toString() +
						 				pthtISOBuffer.get("REASON-CODE").toString() +
						 					pthtISOBuffer.get("ORIGINATOR").toString() +
						 						"5";

			if( pthtISOBuffer.get("DC-ID").toString().equals("01") )
				pthtISOBuffer.put("P-38", pthtISOBuffer.get("P-11").toString());

			pthtISOBuffer.put("P-39", iobsgResponseCode);

			losgMessage += ISOBuilder87(iobsgMsgType, pthtISOBuffer);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return losgMessage;
	}


	public String LogonRSPISOBuilder87 (String iobstMsgType,Hashtable pthtISOBuffer) {
		int lonuSize = 0;
		String losgMessage	=	"";
		String losgPrimaryBitMap	= "";
		String losgSecondaryBitMap	= "";
		int i = 0;
		for(i = 0; i < 128; i++) {
			if(i <= 63 ) {
				if(!pthtISOBuffer.get("P-" +(i + 1)).toString() .equals("*")) {
					if(LogonRSPbitmap87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("P-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * LogonRSPbitmap87[i]);
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					}
					losgPrimaryBitMap += "1";
				} else losgPrimaryBitMap += "0";
			}else{

				if(!pthtISOBuffer.get("S-" +(i + 1)).toString() .equals("*")) {
					if(LogonRSPbitmap87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("S-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * LogonRSPbitmap87[i]);
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					}
					losgSecondaryBitMap += "1";
				} else losgSecondaryBitMap += "0";
			}
		}

		if( losgSecondaryBitMap.equals("0000000000000000000000000000000000000000000000000000000000000000") ){
			losgMessage = iobstMsgType + binary2hex(losgPrimaryBitMap).toUpperCase() + losgMessage;
		}else if(pthtISOBuffer.get("P-1").toString().equals("*")){
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)).toUpperCase()
											+ binary2hex(losgSecondaryBitMap).toUpperCase()  + losgMessage;
		}else{
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)).toUpperCase()
											+ binary2hex(losgSecondaryBitMap).toUpperCase()  + losgMessage.substring(16);
		}

		return losgMessage;
	}

	public String ISOBuilder87(String iobstMsgType,Hashtable pthtISOBuffer) {
		int lonuSize = 0;
		String losgMessage	=	"";
		String losgPrimaryBitMap	= "";
		String losgSecondaryBitMap	= "";
		int i = 0;
		for(i = 0; i < 128; i++) {
			if(i <= 63 ) {
				if(!pthtISOBuffer.get("P-" +(i + 1)).toString() .equals("*")) {
					if(BITMAP87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("P-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					}
					losgPrimaryBitMap += "1";
				} else losgPrimaryBitMap += "0";
			}
			else {
				if(!pthtISOBuffer.get("S-" +(i + 1)).toString() .equals("*")) {
					if(BITMAP87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("S-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					}
					losgSecondaryBitMap += "1";
				} else losgSecondaryBitMap += "0";
			}
		}

		//System.out.println("losgSecondaryBitMap :: "+losgSecondaryBitMap);

		if( losgSecondaryBitMap.equals("0000000000000000000000000000000000000000000000000000000000000000") ) {
			//System.out.println("comes here 1");
			losgMessage = iobstMsgType + binary2hex(losgPrimaryBitMap).toUpperCase() + losgMessage;
		}
		else if( pthtISOBuffer.get("P-1").toString().equals("*") ) {
			//System.out.println("comes here 2");
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)).toUpperCase() + binary2hex(losgSecondaryBitMap).toUpperCase()  + losgMessage;
		}
		else {
			//System.out.println("comes here 3");
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)).toUpperCase() + binary2hex(losgSecondaryBitMap) .toUpperCase() + losgMessage.substring(16);
		}

		return losgMessage;
	}

	/*public String ISOBuilder87(String iobstMsgType,Hashtable pthtISOBuffer) {

		System.out.println("ISO Builder :: "+pthtISOBuffer);
		int lonuSize = 0;
		String losgMessage	=	"";
		String losgPrimaryBitMap	= "";
		String losgSecondaryBitMap	= "";
		int i = 0;
		for(i = 0; i < 128; i++) {
			if(i <= 63 ) {
				if(!pthtISOBuffer.get("P-" +(i + 1)).toString() .equals("*")) {
					if(BITMAP87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("P-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("P-" +(i + 1)).toString();
					}
					losgPrimaryBitMap += "1";
				} else losgPrimaryBitMap += "0";
			}
			else{
				if(!pthtISOBuffer.get("S-" +(i + 1)).toString() .equals("*")) {
					if(BITMAP87[i] < 0) {
						lonuSize	=	pthtISOBuffer.get("S-" +(i + 1)).toString().length();
						losgMessage	+= 	m24Utility.leftPadZeros(String.valueOf(lonuSize), -1 * BITMAP87[i]);
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					} else {
						losgMessage	+= 	pthtISOBuffer.get("S-" +(i + 1)).toString();
					}
					losgSecondaryBitMap += "1";
				} else losgSecondaryBitMap += "0";
			}
		}

		if( losgSecondaryBitMap.equals("0000000000000000000000000000000000000000000000000000000000000000") ){
			losgMessage = iobstMsgType + binary2hex(losgPrimaryBitMap) + losgMessage;
		}else if(pthtISOBuffer.get("P-1").toString().equals("*")){
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)) + binary2hex(losgSecondaryBitMap).toUpperCase()  + losgMessage;
		}else{
			losgMessage = iobstMsgType + binary2hex("1"+losgPrimaryBitMap.substring(1)) + binary2hex(losgSecondaryBitMap).toUpperCase()  + losgMessage.substring(16);
		}

		return losgMessage;
	}*/

	/*public Hashtable ISOFormatter87(String iobsgMessage) {

		int lonuSize = 0;
		int lonuOffset = 16;
		String losgPBitmap = null;
		String losgSBitmap = null;
		Hashtable pthtISOBuffer = new Hashtable();
		try{
			if(iobsgMessage.substring(0, 3).equals("ISO")) {

				pthtISOBuffer.put("ISO", iobsgMessage.substring(0, 3));
				pthtISOBuffer.put("DC-ID", iobsgMessage.substring(3, 5));
				pthtISOBuffer.put("REL-ID", iobsgMessage.substring(5, 7));
				pthtISOBuffer.put("REASON-CODE", iobsgMessage.substring(7, 10));
				pthtISOBuffer.put("ORIGINATOR", iobsgMessage.substring(10, 11));
				pthtISOBuffer.put("AUTHORIZOR", iobsgMessage.substring(11, 12));
				pthtISOBuffer.put("MSG-TYP", iobsgMessage.substring(12, 16));

				if(!"0200".equals(iobsgMessage.substring(12, 16)) &&
					!"0210".equals(iobsgMessage.substring(12, 16)) &&
					!"0215".equals(iobsgMessage.substring(12, 16)) &&
					!"0220".equals(iobsgMessage.substring(12, 16)) &&
					!"0221".equals(iobsgMessage.substring(12, 16)) &&
					!"0230".equals(iobsgMessage.substring(12, 16)) &&
					!"0402".equals(iobsgMessage.substring(12, 16)) &&
					!"0420".equals(iobsgMessage.substring(12, 16)) &&
					!"0421".equals(iobsgMessage.substring(12, 16)) &&
					!"0430".equals(iobsgMessage.substring(12, 16)) &&
					!"0800".equals(iobsgMessage.substring(12, 16)) &&
					!"0810".equals(iobsgMessage.substring(12, 16)) &&
					!"0100".equals(iobsgMessage.substring(12, 16)) &&
					!"0110".equals(iobsgMessage.substring(12, 16))) {
						throw new NullPointerException();
				}

				losgPBitmap = parseBitmap(iobsgMessage.substring(lonuOffset, lonuOffset + 16)); lonuOffset += 16;
				for(int i = 0; i < 64; i++) {

					if('1' == losgPBitmap.charAt(i)) {
						if(BITMAP87[i] < 0) {
							lonuSize = Integer.parseInt(iobsgMessage.substring(lonuOffset, lonuOffset + -1 * BITMAP87[i])); lonuOffset += -1 * BITMAP87[i];
							pthtISOBuffer.put("P-" +(i + 1), iobsgMessage.substring(lonuOffset, lonuOffset + lonuSize)); lonuOffset += lonuSize;
						} else {
							pthtISOBuffer.put("P-" +(i + 1), iobsgMessage.substring(lonuOffset, lonuOffset + BITMAP87[i])); lonuOffset += BITMAP87[i];
						}
					} else {
						pthtISOBuffer.put("P-" +(i + 1), "*");
					}
				}

				if('1' == losgPBitmap.charAt(0)) {

					losgSBitmap = parseBitmap(pthtISOBuffer.get("P-1").toString());
					for(int i = 64; i < 128; i++) {

						if('1' == losgSBitmap.charAt(i - 64)) {
							if(BITMAP87[i] < 0) {
								lonuSize = Integer.parseInt(iobsgMessage.substring(lonuOffset, lonuOffset + -1 * BITMAP87[i])); lonuOffset += -1 * BITMAP87[i];
								pthtISOBuffer.put("S-" +(i + 1), iobsgMessage.substring(lonuOffset, lonuOffset + lonuSize)); lonuOffset += lonuSize;
							} else {
								pthtISOBuffer.put("S-" +(i + 1), iobsgMessage.substring(lonuOffset, lonuOffset + BITMAP87[i])); lonuOffset += BITMAP87[i];
							}
						} else {
							pthtISOBuffer.put("S-" +(i + 1), "*");
						}
					}
				} else {

					for(int i = 64; i < 128; i++) {
						pthtISOBuffer.put("S-" +(i + 1), "*");
					}
				}
			} else {
				throw new NullPointerException();
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		finally{
			return pthtISOBuffer;
		}
	}*/

	public Hashtable<String, String> ISOFormatter87(String message, int[] BITMAP87) {
		int size = 0;
		int offset = 16;
		String pBitmap = null;
		String sBitmap = null;
		Hashtable<String, String> ISOBuffer = new Hashtable<String, String> ();

		System.out.println(message);
		message = "ISO016000050" + message;

		ISOBuffer.put("ISO", message.substring(0, 3));
		ISOBuffer.put("DC-ID", message.substring(3, 5));
		ISOBuffer.put("REL-ID", message.substring(5, 7));
		ISOBuffer.put("REASON-CODE", message.substring(7, 10));
		ISOBuffer.put("ORIGINATOR", message.substring(10, 11));
		ISOBuffer.put("AUTHORIZOR", message.substring(11, 12));
		ISOBuffer.put("MSG-TYP", message.substring(12, 16));

		System.out.println("msg type :: "+message.substring (12, 16));
		if (!"0200".equals (message.substring (12, 16)) &&
				!"0210".equals (message.substring (12, 16)) &&
				!"0215".equals (message.substring (12, 16)) &&
				!"0220".equals (message.substring (12, 16)) &&
				!"0221".equals (message.substring (12, 16)) &&
				!"0230".equals (message.substring (12, 16)) &&
				!"0402".equals (message.substring (12, 16)) &&
				!"0412".equals (message.substring (12, 16)) &&
				!"0420".equals (message.substring (12, 16)) &&
				!"0421".equals (message.substring (12, 16)) &&
				!"0430".equals (message.substring (12, 16)) &&
				!"0800".equals (message.substring (12, 16)) &&
				!"0810".equals (message.substring (12, 16)) &&
				!"0100".equals (message.substring (12, 16)) &&
				!"0110".equals (message.substring (12, 16))) {
					throw new NullPointerException ();
		}

		pBitmap = parseBitmap (message.substring (offset, offset + 16)); offset += 16;
		for (int i = 0; i < 64; i++) {
			if ('1' == pBitmap.charAt (i)) {
				if (BITMAP87[i] < 0) {
					size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
					ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + size)); offset += size;
				} else {
					ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
				}
			} else {
				ISOBuffer.put ("P-" + (i + 1), "*");
			}
		}
		if ('1' == pBitmap.charAt (0)) {
			sBitmap = parseBitmap (ISOBuffer.get ("P-1").toString ());
			for (int i = 64; i < 128; i++) {
				if ('1' == sBitmap.charAt (i - 64)) {
					if (BITMAP87[i] < 0) {
						size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
						ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + size)); offset += size;
					} else {
						ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
					}
				} else {
					ISOBuffer.put ("S-" + (i + 1), "*");
				}
			}
		}
		else {
			for (int i = 64; i < 128; i++) {
				ISOBuffer.put ("S-" + (i + 1), "*");
			}
		}
		return ISOBuffer;
	}

	public Hashtable<String, String> ISOFormatter87(String message) {

		int size = 0;
		int offset = 16;
		String pBitmap = null;
		String sBitmap = null;
		Hashtable<String, String> ISOBuffer = new Hashtable<String, String> ();

		if (message.substring (0, 3).equals ("ISO")) {
			ISOBuffer.put("ISO", message.substring(0, 3));
			ISOBuffer.put("DC-ID", message.substring(3, 5));
			ISOBuffer.put("REL-ID", message.substring(5, 7));
			ISOBuffer.put("REASON-CODE", message.substring(7, 10));
			ISOBuffer.put("ORIGINATOR", message.substring(10, 11));
			ISOBuffer.put("AUTHORIZOR", message.substring(11, 12));
			ISOBuffer.put("MSG-TYP", message.substring(12, 16));


			if (!"0200".equals (message.substring (12, 16)) &&
					!"0210".equals (message.substring (12, 16)) &&
					!"0215".equals (message.substring (12, 16)) &&
					!"0220".equals (message.substring (12, 16)) &&
					!"0221".equals (message.substring (12, 16)) &&
					!"0230".equals (message.substring (12, 16)) &&
					!"0402".equals (message.substring (12, 16)) &&
					!"0412".equals (message.substring (12, 16)) &&
					!"0420".equals (message.substring (12, 16)) &&
					!"0421".equals (message.substring (12, 16)) &&
					!"0430".equals (message.substring (12, 16)) &&
					!"0800".equals (message.substring (12, 16)) &&
					!"0810".equals (message.substring (12, 16)) &&
					!"0100".equals (message.substring (12, 16)) &&
					!"0110".equals (message.substring (12, 16))) {
						throw new NullPointerException ();
			}
			pBitmap = parseBitmap (message.substring (offset, offset + 16)); offset += 16;
			for (int i = 0; i < 64; i++) {
				if ('1' == pBitmap.charAt (i)) {
					if (BITMAP87[i] < 0) {
						size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
						ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + size)); offset += size;
					} else {
						ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
					}
				} else {
					ISOBuffer.put ("P-" + (i + 1), "*");
				}
			}
			if ('1' == pBitmap.charAt (0)) {
				sBitmap = parseBitmap (ISOBuffer.get ("P-1").toString ());
				for (int i = 64; i < 128; i++) {
					if ('1' == sBitmap.charAt (i - 64)) {
						if (BITMAP87[i] < 0) {
							size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
							ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + size)); offset += size;
						} else {
							ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
						}
					} else {
						ISOBuffer.put ("S-" + (i + 1), "*");
					}
				}
			}
			else {
				for (int i = 64; i < 128; i++) {
					ISOBuffer.put ("S-" + (i + 1), "*");
				}
			}
		}
		else {
			message = "ISO016000050" + message;
			//System.out.println(message);
			ISOBuffer.put("ISO", message.substring(0, 3));
			ISOBuffer.put("DC-ID", message.substring(3, 5));
			ISOBuffer.put("REL-ID", message.substring(5, 7));
			ISOBuffer.put("REASON-CODE", message.substring(7, 10));
			ISOBuffer.put("ORIGINATOR", message.substring(10, 11));
			ISOBuffer.put("AUTHORIZOR", message.substring(11, 12));
			ISOBuffer.put("MSG-TYP", message.substring(12, 16));

			if (!"0200".equals (message.substring (12, 16)) &&
					!"0210".equals (message.substring (12, 16)) &&
					!"0215".equals (message.substring (12, 16)) &&
					!"0220".equals (message.substring (12, 16)) &&
					!"0221".equals (message.substring (12, 16)) &&
					!"0230".equals (message.substring (12, 16)) &&
					!"0402".equals (message.substring (12, 16)) &&
					!"0412".equals (message.substring (12, 16)) &&
					!"0420".equals (message.substring (12, 16)) &&
					!"0421".equals (message.substring (12, 16)) &&
					!"0430".equals (message.substring (12, 16)) &&
					!"0800".equals (message.substring (12, 16)) &&
					!"0810".equals (message.substring (12, 16)) &&
					!"0100".equals (message.substring (12, 16)) &&
					!"0110".equals (message.substring (12, 16))) {
						throw new NullPointerException ();
			}

			pBitmap = parseBitmap (message.substring (offset, offset + 16)); offset += 16;
			for (int i = 0; i < 64; i++) {
				if ('1' == pBitmap.charAt (i)) {
					if (BITMAP87[i] < 0) {
						size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
						ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + size)); offset += size;
					} else {
						ISOBuffer.put ("P-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
					}
				} else {
					ISOBuffer.put ("P-" + (i + 1), "*");
				}
			}
			if ('1' == pBitmap.charAt (0)) {
				sBitmap = parseBitmap (ISOBuffer.get ("P-1").toString ());
				for (int i = 64; i < 128; i++) {
					if ('1' == sBitmap.charAt (i - 64)) {
						if (BITMAP87[i] < 0) {
							size = Integer.parseInt (message.substring (offset, offset + -1 * BITMAP87[i])); offset += -1 * BITMAP87[i];
							ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + size)); offset += size;
						} else {
							ISOBuffer.put ("S-" + (i + 1), message.substring (offset, offset + BITMAP87[i])); offset += BITMAP87[i];
						}
					} else {
						ISOBuffer.put ("S-" + (i + 1), "*");
					}
				}
			}
			else {
				for (int i = 64; i < 128; i++) {
					ISOBuffer.put ("S-" + (i + 1), "*");
				}
			}
		}
		return ISOBuffer;
	}

	public Hashtable<String, String> HPDHFormatter(String message) {

		int size 					= 0;
		int offset 					= 7;
		String pBitmap 				= null;
		String data 				= "";
		Hashtable<String, String> pthtHPDHBuffer 	= new Hashtable<String, String> ();

		//System.out.println(message);
		pthtHPDHBuffer.put("ISO", "ISO016000050".substring(0, 3));
		pthtHPDHBuffer.put("DC-ID", "ISO016000050".substring(3, 5));
		pthtHPDHBuffer.put("REL-ID", "ISO016000050".substring(5, 7));
		pthtHPDHBuffer.put("REASON-CODE", "ISO016000050".substring(7, 10));
		pthtHPDHBuffer.put("ORIGINATOR", "ISO016000050".substring(10, 11));
		pthtHPDHBuffer.put("AUTHORIZOR", "ISO016000050".substring(11, 12));
		pthtHPDHBuffer.put("TPDU-ID", binary2hex(asciiChar2binary(message.substring (0, 5))));
		pthtHPDHBuffer.put("MSG-TYP", binary2hex(asciiChar2binary(message.substring (5, 7))));


		if (!"0200".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0210".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0215".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0220".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0221".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0230".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
    			!"0400".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
			    !"0500".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
			    !"0510".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0410".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0402".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0412".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0420".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0421".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0430".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0320".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0330".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0800".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0810".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0100".equals (pthtHPDHBuffer.get("MSG-TYP").toString()) &&
				!"0110".equals (pthtHPDHBuffer.get("MSG-TYP").toString())) {
			throw new NullPointerException ();
		}

		try {


			pBitmap = parseBitmap (binary2hex(asciiChar2binary(message.substring (offset, offset + 8))));
			offset += 8;

			pthtHPDHBuffer.put ("pBitmap", pBitmap);


			for(int i = 0; i < 64; i++) {


				if ('1' == pBitmap.charAt (i)) {

					if (HPDHbitmap[i] < 0) {

						if (HPDHbitmaptype[i] == 'N') {

							size = Integer.parseInt(binary2hex(asciiChar2binary(message.substring (offset, offset + (-1 * HPDHbitmap[i])))));
							offset += -1 * HPDHbitmap[i];

							if (size%2 != 0) {

								data = binary2hex(asciiChar2binary(message.substring (offset, offset + ((size+1)/2))));

								if(HPDHbitmapPadAttribute[i] == 'F') {
									pthtHPDHBuffer.put ("P-" + (i + 1), data.substring(data.length() - size, data.length()));
								}
								else {

									pthtHPDHBuffer.put ("P-" + (i + 1), data.substring(0, size));
								}
								offset += ((size+1)/2);
							}
							else {

								data = binary2hex(asciiChar2binary(message.substring (offset, offset + (size/2))));

								pthtHPDHBuffer.put ("P-" + (i + 1), data);
								offset += size/2;
							}
						}
						else if (HPDHbitmaptype[i] == 'A') {

							size = Integer.parseInt(binary2hex(asciiChar2binary(message.substring (offset, offset + (-1 * HPDHbitmap[i])))));

							offset += -1 * HPDHbitmap[i];

						   	pthtHPDHBuffer.put ("P-" + (i + 1), message.substring (offset, offset + size));
							offset += size;
						}
					}
					else {

						if (HPDHbitmaptype[i] == 'N') {

							size = HPDHbitmap[i];

							if (size%2 != 0) {

								data = binary2hex(asciiChar2binary(message.substring (offset, offset + ((size+1)/2))));

								if (HPDHbitmapPadAttribute[i] == 'F') {
									//log.info("P-" +(i + 1)+" ::::::5:::::::: "+data.substring(data.length() - size, data.length()));
									pthtHPDHBuffer.put ("P-" + (i + 1), data.substring(data.length() - size, data.length()));
								}
								else {
									//log.info("P-" +(i + 1)+" :::::::6::::::: "+data.substring(0, size));
									pthtHPDHBuffer.put ("P-" + (i + 1), data.substring(0,size));
								}

								offset += ((size+1)/2);
							}
							else {
								data = binary2hex(asciiChar2binary(message.substring (offset, offset + (size/2))));

								pthtHPDHBuffer.put ("P-" + (i + 1), data);
								//log.info("P-" +(i + 1)+" ::::::::7:::::: "+data);

								offset += size/2;
							}
						}
						else if (HPDHbitmaptype[i] == 'A') {
							//log.info("P-" +(i + 1)+" ::::::::7:::::: "+message.substring (offset, offset + HPDHbitmap[i]));
							pthtHPDHBuffer.put ("P-" + (i + 1), message.substring (offset, offset + HPDHbitmap[i])); offset += HPDHbitmap[i];
						}
					}
				}
				else
					pthtHPDHBuffer.put ("P-" + (i + 1), "*");

			}
			for (int i = 64; i < 128; i++) {
				pthtHPDHBuffer.put ("S-" + (i + 1), "*");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//log.info("\n\n");

		return pthtHPDHBuffer;
	}

	public String HPDHBuilder (String msgType, Hashtable ISOBuffer) {

		int size = 0;
		String primaryBitMap	= "";
		String message = "";

		message	=	binary2asciiChar(m24Utility.hex2binary(ISOBuffer.get ("TPDU-ID").toString().trim())) +
							binary2asciiChar(m24Utility.hex2binary(msgType));

		for (int i = 0; i <= 63; i++) {

			if ( !ISOBuffer.get ("P-" + (i + 1)).toString() .equals("*") ) {

				if (HPDHbitmap[i] < 0) {

					if (HPDHbitmaptype[i] == 'N') {

						size	=	ISOBuffer.get ("P-" + (i + 1)).toString().length();
						message	+= 	binary2asciiChar(m24Utility.hex2binary(pad111( String.valueOf(size), -1*HPDHbitmap[i]*2, 'F')));

						if (size%2 != 0) {
							//log.info("1 ::::::::::::::::: "+binary2asciiChar(m24Utility.hex2binary( pad111 ( ISOBuffer.get ("P-" + (i + 1)).toString(),ISOBuffer.get ("P-" + (i + 1)).toString().length()+1,HPDHbitmapPadAttribute[i] ))));
							message	+= 	binary2asciiChar(m24Utility.hex2binary( pad111 ( ISOBuffer.get ("P-" + (i + 1)).toString(),ISOBuffer.get ("P-" + (i + 1)).toString().length()+1,HPDHbitmapPadAttribute[i] )));
						}
						else {
							message	+= 	binary2asciiChar(m24Utility.hex2binary(ISOBuffer.get ("P-" + (i + 1)).toString()));
						}

						primaryBitMap += "1";
					}
					else if (HPDHbitmaptype[i] == 'A') {
						size	=	ISOBuffer.get ("P-" + (i + 1)).toString().length();
						message +=  binary2asciiChar(m24Utility.hex2binary(pad111( String.valueOf(size), -1*HPDHbitmap[i]*2, 'F')));
						message	+= 	ISOBuffer.get ("P-" + (i + 1)).toString();

						primaryBitMap += "1";
					}
					else
						primaryBitMap += "0";
				}
				else {
					if (HPDHbitmaptype[i] == 'N') {

						if (ISOBuffer.get ("P-" + (i + 1)).toString().length()%2 != 0)
							message += binary2asciiChar(m24Utility.hex2binary( pad111 ( ISOBuffer.get ("P-" + (i + 1)).toString(),ISOBuffer.get ("P-" + (i + 1)).toString().length()+1,HPDHbitmapPadAttribute[i])));
						else
							message+= 	binary2asciiChar(m24Utility.hex2binary(ISOBuffer.get ("P-" + (i + 1)).toString()));

						primaryBitMap += "1";
					}
					else if( HPDHbitmaptype[i] == 'A' ) {
						message	+= 	ISOBuffer.get ("P-" + (i + 1)).toString();
						primaryBitMap += "1";
					}
					else
						primaryBitMap += "0";
				}
			}
			else
				primaryBitMap += "0";
		}

		message = message.substring(0,7) + binary2asciiChar(primaryBitMap) + message.substring(7);
		return message;
	}


	/**
	 *<i>Parse the Primary/Secondary bitmap</i>
	 *<p>This is an utility method used by the ISO Formatter<p>
	 */
	private String parseBitmap(String iobsgBitmap) {

		String losgUpperBitmap = "00000000000000000000000000000000";
		String losgLowerBitmap = "00000000000000000000000000000000";
		losgUpperBitmap += Long.toBinaryString(Long.parseLong(iobsgBitmap.substring(0, 8), 16));
		losgLowerBitmap += Long.toBinaryString(Long.parseLong(iobsgBitmap.substring(8), 16));
		losgUpperBitmap = losgUpperBitmap.substring(losgUpperBitmap.length() - 32);
		losgLowerBitmap = losgLowerBitmap.substring(losgLowerBitmap.length() - 32);

		return losgUpperBitmap + losgLowerBitmap;
	}

	public String binary2asciiChar(String iobsgBinaryString) {

		if(iobsgBinaryString == null )	return null;
		String losgCharString ="";

		for( int i=0; i < iobsgBinaryString.length(); i+=8 )	{
			String losgTemp = iobsgBinaryString.substring(i,i+8);
			int lonuIntValue=0;

			for( int k=0, j=losgTemp.length()-1;j >=0 ;j--,k++ ){
				lonuIntValue+= Integer.parseInt(""+losgTemp.charAt(j))	* Math.pow(2,k);
			}

			losgCharString +=(char)lonuIntValue;
		}

		return losgCharString;
	}

	public static String asciiChar2binary(String iobsgAsciiString) {

		if(iobsgAsciiString == null )
			 return null;

		String losgBinaryString="";
		String losgTemp="";
		int lonuIntValue=0;
		for(int i=0;i<iobsgAsciiString.length(); i++) {
			lonuIntValue =(int)iobsgAsciiString.charAt(i);
			losgTemp =  "00000000"+Integer.toBinaryString(lonuIntValue);
			losgBinaryString +=losgTemp.substring(losgTemp.length()-8);
		}

		return losgBinaryString;
	}

	public String binary2hex(String iobsgBinaryString) {

		if( iobsgBinaryString == null )	 return null;
		String losgHexString ="";
		for(int i=0; i < iobsgBinaryString.length(); i+=8) {
			String losgTemp = iobsgBinaryString.substring(i,i+8);
			int lonuIntValue=0;
			for(int k=0, j=losgTemp.length()-1;j >=0 ;j--,k++) {
				lonuIntValue+= Integer.parseInt(""+losgTemp.charAt(j))	* Math.pow(2,k) ;
			}
			losgTemp = "0"+Integer.toHexString(lonuIntValue);
			losgHexString += losgTemp.substring(losgTemp.length()-2);
		}
		return losgHexString;
	}

	/**
	 *<i>Build the ISO 8583 Response Message</i>
	 *<p>This method builds and returns the ISO 8583 Financial Response message<p>
	 */
	public String buildMessageType(String iobsgMsgType) {

		if(iobsgMsgType.equals("0200") ) iobsgMsgType = "0210";
			else if(iobsgMsgType.equals("0205") ) iobsgMsgType = "0215";
			else if(iobsgMsgType.equals("0215") ) iobsgMsgType = "0215";
			else if(iobsgMsgType.equals("0100") ) iobsgMsgType = "0110";
			else if(iobsgMsgType.equals("0402") ) iobsgMsgType = "0412";
			else if(iobsgMsgType.equals("0210") ) iobsgMsgType = "0210";
			else if(iobsgMsgType.equals("0220") || iobsgMsgType.equals("0221") ) iobsgMsgType = "0230";
			else if(iobsgMsgType.equals("0420") || iobsgMsgType.equals("0421") ) iobsgMsgType = "0430";
			else if(iobsgMsgType.equals("0800") ) iobsgMsgType = "0810";
			else if(iobsgMsgType.equals("1200") ) iobsgMsgType = "1210";
			else if(iobsgMsgType.equals("1400") ) iobsgMsgType = "1410";
			else if(iobsgMsgType.equals("1800") ) iobsgMsgType = "1810";
			else if(iobsgMsgType.equals("1804") ) iobsgMsgType = "1814";
			else if(iobsgMsgType.equals("9210") ) iobsgMsgType = "9210";
			else if(iobsgMsgType.equals("9230") ) iobsgMsgType = "9230";
			else if(iobsgMsgType.equals("9430") ) iobsgMsgType = "9430";
			else if(iobsgMsgType.equals("9810") ) iobsgMsgType = "9810";

		return iobsgMsgType;
	}

	/**
	 *<i>Check the Message Status</i>
	 *<p>This method verifies list of valid <b>Message24</b> Message Status<p>
	 */
	public boolean isMessageStatusFound(Vector iobvcMessage) {
		boolean loboFound = false;
		for(int i = 0; i < MESSAGESTATUS.length; i++) {
			if(iobvcMessage.elementAt(4).toString().equals(MESSAGESTATUS[i])) {
				loboFound = true;
				break;
			}
		}
		return loboFound;
	}


	/**<i>Builds the new vector</i>
	<p>This is an utility method used for building ISO 8583 Message<p>
	*/
	Vector buildRow (Object obj1, Object obj2,  Object obj3, Object obj4,
						Object obj5, Object obj6, Object obj7, Object obj8, Object obj9){
		Vector<Object> row = new Vector<Object> ();
		row.addElement (obj1);
		row.addElement (obj2);
		row.addElement (obj3);
		row.addElement (obj4);
		row.addElement (obj5);
		row.addElement (obj6);
		row.addElement (obj7);
		row.addElement (obj8);
		row.addElement (obj9);
		return row;
	}


	/**<i>Builds the new vector</i>
	<p>This is an utility method used for building ISO 8583 Message<p>
	*/
	Vector buildRow (Object obj1, Object obj2,  Object obj3) {

		Vector<Object> row = new Vector<Object> ();
		row.addElement (obj1);
		row.addElement (obj2);
		row.addElement (obj3);
		return row;
	}

	protected void finalize() {
		m24Utility		=	null;
		MESSAGESTATUS	=   null;
	}

	public String pad111 (String str, int length, char bitmapPadAttribute) {
		str = (null == str)?new String ():str;

		String padStr = new String (str+"");
		if (length <= padStr.length ()) {
			return padStr.substring (0, length);
		}
		for (int i = padStr.length (); i < length; i++) {

			if (bitmapPadAttribute == 'F') {

				padStr = '0' + padStr;

			}else padStr = padStr+'0';
		}
		return padStr;
	}

}//end of M24ISOMethods