package api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.persistence.EntityManager;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;

import com.fss.m24.Message24;
import com.fss.m24.api.Constants;
import com.fss.m24.api.M24Utility;
import com.fss.m24.api.ParameterConstants;
import com.fss.m24.api.TransactionDetails;
import com.fss.m24.api.iso.M24ISOMethods;
import com.fss.m24.api.iso.impl.B24ISOImpl;
import com.fss.m24.api.message.Message;
import com.fss.m24.api.query.impl.ProcessQuery;
 /**
 * @author <a href="karthiksankaran@fss.co.in">Karthik. S</a>
 * @since
 * @version 1.0
 * @created date: Jul 26, 2011 10:43:35 PM
 * @Modified by:
 * @Modified date:
 * @Remarks
 */
public class ConvertToExternalFormat implements Command {

	private static final Logger log = Logger.getLogger("com.src.output");

	private M24ISOMethods m24ISOMethods = null;
	private Hashtable tempISOBuffer = null;

	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub

		m24ISOMethods = new M24ISOMethods();

		Message message = (Message) context.get(ParameterConstants.MESSAGE);

		TransactionDetails tranDetails=(TransactionDetails)context.get(ParameterConstants.TRANSACTIONDETAILS);

		String stationName = message.getStationName();

		if (stationName.startsWith(Constants.UNBI_STATION)) {

			String requestMessage = message.getMessage();

			log.info("Convert to external format :: request Message :: "+requestMessage);

			Hashtable isoBuffer = m24ISOMethods.ISOFormatter87(requestMessage);

			if (isoBuffer.get("P-3").toString().substring(0, 2).equals("31") ||
					isoBuffer.get("P-3").toString().substring(0, 2).equals("38") ) {

				isoBuffer = buildReqToUNBIForBEMS(isoBuffer,tranDetails);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("94") ||
					isoBuffer.get("P-3").toString().substring(0, 2).equals("95")) { //Stop cheque and cheque status

				isoBuffer = getVASRequestforUNBI(isoBuffer,tranDetails);
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("MB") ||
					isoBuffer.get("P-3").toString().substring(0, 2).equals("40")) {

				log.debug("S-125 :: "+isoBuffer.get("S-125"));

				if (isoBuffer.get("S-125").toString().substring(0, 2).equals("51")) { // NEFT

					//TransactionDetails tranDetails = (TransactionDetails) context.get(ParameterConstants.TRANSACTIONDETAILS);

					EntityManager em = (EntityManager) context.get(ParameterConstants.ENTITYMANAGER);

					isoBuffer = buildReqToUNBIForNEFT(isoBuffer, tranDetails,em);
				}
				else { // Other fund transfer
					isoBuffer = buildReqToUNBIForFT(isoBuffer,tranDetails);

					log.info("Park Buffer :: "+Message24.parkBuffer);
				}
			}
			else if (isoBuffer.get("P-3").toString().substring(0, 2).equals("MC") ) {
				isoBuffer = buildReqToUNBIForACEnquiry(isoBuffer,tranDetails);

			}

			requestMessage = buildNPCIResponse87("1200", isoBuffer);

			message.setMessage(requestMessage);

			context.put(ParameterConstants.MESSAGE, message);
		}

		return false;
	}

	private Hashtable buildReqToUNBIForNEFT(Hashtable isoBuffer, TransactionDetails tranDetails, EntityManager em) throws Exception {
		// TODO Auto-generated method stub

		fillISOBuffer();

		tempISOBuffer.put("MSG-TYP", "1200");

		tempISOBuffer.put("P-3", "970000"); // Processing Code

		log.info("NEFT P-4 :: "+M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32","504465700"); // AcquiringInstitutionId
		tempISOBuffer.put("P-34","00141799"); // TO BE DISCUSSED

		//tempISOBuffer.put("P-37",isoBuffer.get("P-37"));

		tempISOBuffer.put("P-41", M24Utility.rightPadSpace("SWT",16)); // Card Acceptor terminal id
		tempISOBuffer.put("P-42", M24Utility.rightPadSpace("SWT",15)); // Card Acceptor IDENTIFICATION

		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode

		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 11)+ M24Utility.rightPadSpace(isoBuffer.get("S-102").toString().substring(0,4),8) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19)); tranDetails.setBenAccountNo
		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19)+ M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19)+ isoBuffer.get("S-102").toString());
		tempISOBuffer.put("S-103", "  "+M24Utility.rightPadSpace(tranDetails.getBenIfscCode().substring(0, 5).toUpperCase(), 11)+ M24Utility.rightPadSpace(tranDetails.getBenIfscCode().substring(5).toUpperCase(),8) +  M24Utility.rightPadSpace(tranDetails.getBenAccountNo(), 19));
		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125", getAdditionalNEFTRequest(isoBuffer, tranDetails, isoBuffer.get("S-102").toString().substring(0,4), em));

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
	}

	private Object getAdditionalNEFTRequest(Hashtable isoBuffer,
			TransactionDetails tranDetails,String Sol_id,EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		Double amt = new Double(tranDetails.getAmount()).doubleValue() / 100;
		StringBuffer additionalBuffer = new StringBuffer();
		ProcessQuery procQuery = new ProcessQuery();
		String branchInfo = procQuery.getIFSCbranchSolLinkage(tranDetails, em, Sol_id);
		additionalBuffer.append("20"); // subcode
		additionalBuffer.append("|");
		additionalBuffer.append("N06");
		additionalBuffer.append("|");
		additionalBuffer.append("NEFT");
		additionalBuffer.append("|");
		additionalBuffer.append("T");
		additionalBuffer.append("|");
		additionalBuffer.append(isoBuffer.get("S-102").toString().substring(0,4));
		additionalBuffer.append("|");
		additionalBuffer.append(isoBuffer.get("S-102").toString());
		additionalBuffer.append("|");
		additionalBuffer.append(isoBuffer.get("S-103").toString());
		additionalBuffer.append("|");

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String _date 		= df.format(new Date());
		additionalBuffer.append(_date);

		additionalBuffer.append("|");
		additionalBuffer.append("99");
		additionalBuffer.append("|");
		additionalBuffer.append("INR");
		additionalBuffer.append("|");
		additionalBuffer.append(amt.toString().substring(0,amt.toString().indexOf(".")));
		//additionalBuffer.append(Integer.parseInt(tranDetails.getAmount()));
		additionalBuffer.append("|");
		log.debug("Receiver ifsc code :: "+tranDetails.getBenIfscCode());
		log.debug("Remitter name :: "+tranDetails.getInitiatorName());

		additionalBuffer.append(tranDetails.getBenIfscCode().substring(0, 5).toUpperCase()); // receiver bank code
		additionalBuffer.append("|");
		additionalBuffer.append(tranDetails.getBenIfscCode().substring(5)); // receiver branch code
		additionalBuffer.append("|");
		additionalBuffer.append(tranDetails.getBenIfscCode().toUpperCase());
		additionalBuffer.append("|");
		if (tranDetails.getInitiatorName().trim().equals("")) {
			additionalBuffer.append("UNBI");
		}
		else {
			additionalBuffer.append(tranDetails.getInitiatorName()); // REMITTER NAME // TO BE REMOVED
		}
		additionalBuffer.append("|");
		additionalBuffer.append("UNBI"); // remitter address
		additionalBuffer.append("|");
		additionalBuffer.append(tranDetails.getBenAccountNo());
		additionalBuffer.append("|");
		additionalBuffer.append(tranDetails.getBenName());
		additionalBuffer.append("|");
		additionalBuffer.append(tranDetails.getBenName()+" ADDRESS");
		additionalBuffer.append("|");
		additionalBuffer.append(""); // senderToRecvInfo1
		additionalBuffer.append("|");
		additionalBuffer.append("NEFTO");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("NEFRMK"); //Tranpartclrs
		additionalBuffer.append("|");
		additionalBuffer.append("027");
		additionalBuffer.append("|");
		additionalBuffer.append(isoBuffer.get("S-102").toString().substring(0,4));
		additionalBuffer.append("|");
		additionalBuffer.append("UTBI0" +branchInfo);
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("027"); //orderingInstName
		additionalBuffer.append("|");
		additionalBuffer.append("UTBI0" +branchInfo);
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("OCHStreet");
		additionalBuffer.append("|");
		additionalBuffer.append("KOLKATA"); //ordering cust address 3
		additionalBuffer.append("|");

		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("MKBNAGAR"); // beneficiaryCustadress2
		additionalBuffer.append("|");
		additionalBuffer.append("PERAMBUR"); //beneficiaryCustAddress3
		additionalBuffer.append("|");

		additionalBuffer.append("SMS7299001085"); //DETAIL of payment1
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");

		additionalBuffer.append("");
		additionalBuffer.append("|");

		additionalBuffer.append(""); // senderToRecvInfo2
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");

		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");
		additionalBuffer.append("");
		additionalBuffer.append("|");

		return additionalBuffer.toString();
	}

	private Hashtable buildReqToUNBIForACEnquiry(Hashtable isoBuffer,TransactionDetails tranDetails) {
		// TODO Auto-generated method stub

		fillISOBuffer();

		tempISOBuffer.put("MSG-TYP", "1200");
		tempISOBuffer.put("P-2", "5045110100000012");
		tempISOBuffer.put("P-3", "82"+isoBuffer.get("P-3").toString().substring(2, 6)); // Processing Code
		tempISOBuffer.put("P-2", "4213190153009133");
		//Commented on 19/10/11
		//tempISOBuffer.put("P-3", "820000"); // Processing Code
		tempISOBuffer.put("P-3", "82"+isoBuffer.get("P-3").toString().substring(2, 6)); // Processing Code
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32",isoBuffer.get("P-32").toString().trim()); // AcquiringInstitutionId
		tempISOBuffer.put("P-37",isoBuffer.get("P-37"));
		tempISOBuffer.put("P-41", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalId(),16)); // Card Acceptor terminal id

		tempISOBuffer.put("P-43", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalName(),40));

		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode
		tempISOBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");

		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 11)+ M24Utility.rightPadSpace(isoBuffer.get("S-102").toString().substring(0,4),8) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19)+ isoBuffer.get("S-102").toString());
		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125","UBNET");

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
	}

	public Hashtable buildReqToUNBIForBEMS(Hashtable isoBuffer,TransactionDetails tranDetails) throws Exception{

		fillISOBuffer();

		log.debug("tranDetails????"+tranDetails.getS123()+":ter"+tranDetails.getCardAcceptorTeminalName()+"::"+tranDetails.getCardAcceptorTeminalId());

		tempISOBuffer.put("MSG-TYP", "1200");
		tempISOBuffer.put("P-2", "5045110100000012");
		tempISOBuffer.put("P-3", isoBuffer.get("P-3")); // Processing Code
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32",isoBuffer.get("P-32").toString().trim()); // AcquiringInstitutionId
		tempISOBuffer.put("P-37",isoBuffer.get("P-37"));
		tempISOBuffer.put("P-41", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalId(),16)); // Card Acceptor terminal id

		tempISOBuffer.put("P-43", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalName(),40));
		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode
		tempISOBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");

		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 11)+ M24Utility.rightPadSpace(isoBuffer.get("S-102").toString().substring(0,4),8) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19)+ isoBuffer.get("S-102").toString());

		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125","UBNET");

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
	}

	public Hashtable buildReqToUNBIForFT(Hashtable isoBuffer,TransactionDetails tranDetails) throws Exception{

		fillISOBuffer();

		tempISOBuffer.put("MSG-TYP", "1200");
		tempISOBuffer.put("P-2", "5045110100000012");
		tempISOBuffer.put("P-3", "400000"); // Processing Code
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32",isoBuffer.get("P-32").toString().trim()); // AcquiringInstitutionId
		tempISOBuffer.put("P-37",isoBuffer.get("P-37"));
		tempISOBuffer.put("P-41", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalId(),16)); // Card Acceptor terminal id
		tempISOBuffer.put("P-43", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalName(),40));
		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode
		tempISOBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");

		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19) + isoBuffer.get("S-102").toString());
		tempISOBuffer.put("S-103", "  "+M24Utility.rightPadSpace("027", 19) + isoBuffer.get("S-103").toString());
		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		//tempISOBuffer.put("S-103", "  "+M24Utility.rightPadSpace("027", 19) + M24Utility.rightPadSpace(isoBuffer.get("S-103").toString(), 19));
		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125","UBNET");

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
	}

	public Hashtable getVASRequestforUNBI(Hashtable isoBuffer,TransactionDetails tranDetails) {

		B24ISOImpl B24ISOImpl = new B24ISOImpl();

		fillISOBuffer();

		tempISOBuffer.put("MSG-TYP", "1200");
		tempISOBuffer.put("P-2", "5045110100000012");
		tempISOBuffer.put("P-3", isoBuffer.get("P-3")); // Processing Code
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32",isoBuffer.get("P-32").toString().trim()); // AcquiringInstitutionId
		tempISOBuffer.put("P-37",isoBuffer.get("P-37"));
		tempISOBuffer.put("P-41", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalId(),16)); // Card Acceptor terminal id

		tempISOBuffer.put("P-43", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalName(),40));


		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode
		tempISOBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");

		log.info("S-122 :: "+isoBuffer.get("S-122"));
		tempISOBuffer.put("P-62", "00"+isoBuffer.get("S-122").toString().trim()+"001");
		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 11)+ M24Utility.rightPadSpace(isoBuffer.get("S-102").toString().substring(0,4),8) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19)+ isoBuffer.get("S-102").toString());
		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125","UBNET");

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
    }

	public Hashtable buildReversalReqToUNBI(Hashtable isoBuffer,TransactionDetails tranDetails) throws Exception{

		fillISOBuffer();

		tempISOBuffer.put("MSG-TYP", "1420");
		tempISOBuffer.put("P-2", "5045110100000012");
		tempISOBuffer.put("P-3", "400000"); // Processing Code
		tempISOBuffer.put("P-4", M24Utility.leftPadZeros(isoBuffer.get("P-4").toString(),16));

		tempISOBuffer.put("P-11", isoBuffer.get("P-37")); // System Trace Audit Number
		tempISOBuffer.put("P-12", M24Utility.getTime("yyyyMMddHHmmss")); // Local Transaction Date
		tempISOBuffer.put("P-17", M24Utility.getDate(Message24.BIZ_DATE,"yyyyMMdd")); // Capture
		tempISOBuffer.put("P-24", "200");

		tempISOBuffer.put("P-32",isoBuffer.get("P-32").toString().trim()); // AcquiringInstitutionId
		tempISOBuffer.put("P-37",isoBuffer.get("P-37"));
		tempISOBuffer.put("P-41", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalId(),16)); // Card Acceptor terminal id
		tempISOBuffer.put("P-43", M24Utility.rightPadSpace(tranDetails.getCardAcceptorTeminalName(),40));
		tempISOBuffer.put("P-49", M24Utility.rightPadSpace("INR", 3)); // TransactionCurrencyCode
		tempISOBuffer.put("P-56", "1420"+isoBuffer.get("P-37")+M24Utility.getTime("yyyyMMddHHmmss")+isoBuffer.get("P-32").toString().trim());
		tempISOBuffer.put("P-59", "300000^0153001530103422610^356^0.00^0200^");

		tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19) + isoBuffer.get("S-102").toString());
		tempISOBuffer.put("S-103", "  "+M24Utility.rightPadSpace("027", 19) + isoBuffer.get("S-103").toString());
		//tempISOBuffer.put("S-102", M24Utility.rightPadSpace("027", 19) + M24Utility.rightPadSpace(isoBuffer.get("S-102").toString(), 19));
		//tempISOBuffer.put("S-103", "  "+M24Utility.rightPadSpace("027", 19) + M24Utility.rightPadSpace(isoBuffer.get("S-103").toString(), 19));
		tempISOBuffer.put("S-123", M24Utility.rightPadSpace(tranDetails.getS123(), 3));
		tempISOBuffer.put("S-125","UBNET");

        log.debug("tempISOBuffer :::: "+tempISOBuffer);

        return tempISOBuffer;
	}

	public void fillISOBuffer() {

		try {

			int size 		= 0;
			int offset 		= 16;
			String pBitmap 	= null;
			String sBitmap 	= null;

			tempISOBuffer = new Hashtable();
			tempISOBuffer.put("ISO", "*");
			tempISOBuffer.put("DC-ID", "*");
			tempISOBuffer.put("REL-ID", "*");
			tempISOBuffer.put("REASON-CODE", "*");
			tempISOBuffer.put("ORIGINATOR", "*");
			tempISOBuffer.put("AUTHORIZOR", "*");
			tempISOBuffer.put("MSG-TYP", "*");

			for (int i = 0; i < 64; i++) {

				if (i == 0)
					tempISOBuffer.put("P-" + (i + 1), "0000000000000000");

				else
					tempISOBuffer.put("P-" + (i + 1), "*");
			}

			for (int i = 64; i < 128; i++) {
				tempISOBuffer.put("S-" + (i + 1), "*");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	final int[] UNBIBITMAP87 = {

			8, -2,  6, 16, 12, 12, 10,  8, //8
			8,  8,  12,  14,  4,  4,  4,  4, //16
			8,  4,  3,  3,  3,  3,  3,  3, //24
			2,  2,  1,  9,  9,  9,  9, -2, //32
		   -2, -2, -2, -3, 12,  6,  3,  3, //40
		    16, 8, -2, -2, -2, -3, -3, -3, //48
		    3,  3,  3, 16, 16, 15, -3, -3, //56
		   -3,  0, -3, -3, -3, -3, 19, 16, //64
		    0,  1,  2,  3,  3,  3,  4,  4, //72
		    6, 10, 10, 10, 10, 10, 10, 10, //80
		   10, 12, 12, 12, 12, 16, 16, 16, //88
		   16, 42,	0,  2,  5,  7, 42, 16, //96
		   17,  0, -2, -2,  0, -2, -2,  -2, //102
		   -3, -3, -3, -3, -3, -3, -3,	0, //112
			0,  0,  0,  0,  0,  0, -3, -3, //120
		   -3, -3, -3, -3, -3, -3, -3, 16
	};

	public String buildNPCIResponse87(String iobsgMsgType,
			 Hashtable<String, String> pthtISOBuffer) {

		String losgMessage = "";
		try {
			iobsgMsgType = iobsgMsgType;

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

		for (i = 0; i < 128; i++) {

			if (i <= 63) {

				if (!ISOBuffer.get("P-" + (i + 1)).toString().equals("*")) {

					if (UNBIBITMAP87[i] < 0) {

						size = ISOBuffer.get("P-" + (i + 1)).toString().length();

						message += m24ISOMethods.pad1(String.valueOf(size), -1 * UNBIBITMAP87[i]);

						message += ISOBuffer.get("P-" + (i + 1)).toString();

					}
					else {

						message += ISOBuffer.get("P-" + (i + 1)).toString();
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
						message += m24ISOMethods.pad1(String.valueOf(size), -1 * UNBIBITMAP87[i]);
						message += ISOBuffer.get("S-" + (i + 1)).toString();
					}
					else {

						message += ISOBuffer.get("S-" + (i + 1)).toString();

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

				//log.debug("Message type :; " + iobstMsgType);

				message = iobstMsgType + m24ISOMethods.binary2asciiChar(primaryBitMap)
						+ m24ISOMethods.binary2asciiChar(secondaryBitMap)
						+ message.substring(16);

			}
		}


		return message;
	}
}
