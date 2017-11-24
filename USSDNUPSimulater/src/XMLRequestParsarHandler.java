import java.io.StringReader;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



public class XMLRequestParsarHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String req = "<?xml version='1.0' encoding='utf-8'?>"
					+ "<RequestObject>" + "<RequestId>RequestId</RequestId>"
					+ "<MobileNo>91742878837</MobileNo>"
					+ "<MasterShortCode>MasterShortCode</MasterShortCode>"
					+ "<SessionGUID>SessionGUID</SessionGUID>"
					+ "<SessionGUID_CMO>SessionGUID_CMO</SessionGUID_CMO>"
					+ "<RequestType>RequestType</RequestType>"
					+ "<LanguageId>LanguageId</LanguageId>"
					+ "<OperatorName>OperatorName</OperatorName>"
					+ "<TimeStamp>TimeStamp</TimeStamp>";
			// Get the DOM Builder Factory
			String FTMMID = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>FTMMID</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>IFSCCode</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>3</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>"
					+ "<ParamName>Beneficiary_Mobile</ParamName>"
					+ "<ParamValue>BenMobileNo</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>6</ParamId>"
					+ "<ParamName>Beneficiary_MMID</ParamName>"
					+ "<ParamValue>BenMMID</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>7</ParamId>" + "<ParamName>Amount</ParamName>"
					+ "<ParamValue>Amount</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>8</ParamId>" + "<ParamName>Remark</ParamName>"
					+ "<ParamValue>Remarks</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>9</ParamId>" + "<ParamName>mPin</ParamName>"
					+ "<ParamValue>encriptmPin</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>10</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>ACCNO</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String BalEnq = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>BalanceEnquiry</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>HDFC</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>4254</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String MiniStatement = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>MiniStatement</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>INB0</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>2</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>4254</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String FTIFSC = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>FTIFSC</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>5432</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>4</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>"
					+ "<ParamName>Beneficiary_IFSC</ParamName>"
					+ "<ParamValue>HDFC</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>6</ParamId>"
					+ "<ParamName>Beneficiary_A/c-No</ParamName>"
					+ "<ParamValue>6543</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>7</ParamId>" + "<ParamName>Amount</ParamName>"
					+ "<ParamValue>100</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>8</ParamId>" + "<ParamName>Remark</ParamName>"
					+ "<ParamValue>P2A</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>9</ParamId>" + "<ParamName>mPin</ParamName>"
					+ "<ParamValue>756hweiyrwe45</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>10</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>4521</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String FTAadhaar = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>FTAadhaar</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>3467</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>5</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>"
					+ "<ParamName>Beneficiary_AaDhaar-No</ParamName>"
					+ "<ParamValue>9678</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>6</ParamId>" + "<ParamName>Amount</ParamName>"
					+ "<ParamValue>100</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>7</ParamId>" + "<ParamName>mPin</ParamName>"
					+ "<ParamValue>564hfh7u6</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>8</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>9821</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String KnowMMID = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>KnowMMID</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>4258</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>6</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String ChangeMPin = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>ChangeMPin</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>IFSC</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>7</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>"
					+ "<ParamName>Old-Mpin</ParamName>"
					+ "<ParamValue>123</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>6</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>7546</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>7</ParamId>"
					+ "<ParamName>New-Mpin</ParamName>"
					+ "<ParamValue>2222</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>8</ParamId>"
					+ "<ParamName>R-Enter-Mpin</ParamName>"
					+ "<ParamValue>222</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			String GenOTP = req + "<RequestParams>" + "<Param>"
					+ "<ParamId>1</ParamId>"
					+ "<ParamName>EventName</ParamName>"
					+ "<ParamValue>GenerateOTP</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>2</ParamId>"
					+ "<ParamName>NPCI - Main Menu</ParamName>"
					+ "<ParamValue>1</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>3</ParamId>"
					+ "<ParamName>Enter IFSC Code</ParamName>"
					+ "<ParamValue>9876</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>4</ParamId>"
					+ "<ParamName>MainMenu</ParamName>"
					+ "<ParamValue>8</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>5</ParamId>" + "<ParamName>Mpin</ParamName>"
					+ "<ParamValue>hhdf7fhk97</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>" + "<Param>"
					+ "<ParamId>6</ParamId>" + "<ParamName>A/c-No</ParamName>"
					+ "<ParamValue>7842</ParamValue>"
					+ "<ParamType>String</ParamType>" + "</Param>"
					+ "</RequestParams>" + "</RequestObject>";
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder
					.parse(new InputSource(new StringReader(ChangeMPin)));
			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());
			//Root elements.....
			NodeList MobileNoElement = doc.getElementsByTagName("MobileNo");
			String MobileNo = MobileNoElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList RequestIdElement = doc.getElementsByTagName("RequestId");
			String RequestId = RequestIdElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList LanguageIdElement = doc.getElementsByTagName("LanguageId");
			String LanguageId = LanguageIdElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList SessionGUIDElement = doc.getElementsByTagName("SessionGUID");
			String SessionGUID = SessionGUIDElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList SesGUID_CMO_Element = doc.getElementsByTagName("SessionGUID_CMO");
			String SessionGUID_CMO = SesGUID_CMO_Element.item(0).getChildNodes().item(0).getNodeValue();
			NodeList OperatorNameElement = doc.getElementsByTagName("OperatorName");
			String OperatorName = OperatorNameElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList TimeStampElement = doc.getElementsByTagName("TimeStamp");
			String TimeStamp = TimeStampElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList RequestTypeElement = doc.getElementsByTagName("RequestType");
			String RequestType = RequestTypeElement.item(0).getChildNodes().item(0).getNodeValue();
			NodeList MasterShortCodeElement = doc.getElementsByTagName("MasterShortCode");
			String MasterShortCode = MasterShortCodeElement.item(0).getChildNodes().item(0).getNodeValue();
			
			NodeList RequestParams = doc.getElementsByTagName("RequestParams");
			int totalPersons = RequestParams.getLength();
			System.out.println("Total no of people : " + totalPersons);
			Hashtable<String, String> requestParsdata = new Hashtable<String, String>();
			for (int s = 0; s < RequestParams.getLength(); s++) {

				Node firstNode = RequestParams.item(s);
				if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element firstElement = (Element) firstNode;
					NodeList Param = firstElement.getElementsByTagName("Param");
					for (int i = 0; i < Param.getLength(); i++) {
						Node ParamNode = Param.item(i);
						if (ParamNode.getNodeType() == Node.ELEMENT_NODE) {
							Element firstParemElement = (Element) Param.item(i);
							NodeList paramValues = firstParemElement
									.getChildNodes();
							Node ParamId = paramValues.item(0);
							Node ParamName = paramValues.item(2);
							requestParsdata.put(ParamId.getChildNodes().item(0)
									.getNodeValue(), ParamName.getChildNodes()
									.item(0).getNodeValue());
							/*System.out.println(ParamId.getNodeName()
									+ "::::"
									+ ParamId.getChildNodes().item(0)
											.getNodeValue());
							System.out.println(ParamName.getNodeName()
									+ "::::"
									+ ParamName.getChildNodes().item(0)
											.getNodeValue());*/
							/*for (int j = 1; j < textFNList.getLength(); j++) {
								Node node2 = textFNList.item(j);
								System.out.println(node2.getNodeName()+"::::"+node2.getChildNodes().item(0).getNodeValue());
								requersData.pu
							}*/

						}

					}

				} else {
					System.out.println("no element");// end of if clause
				}

			}// end of for loop with s var
			//for (int i = 1; i <= requestParsdata.size(); i++) {
			System.out.println("RequestDat:::" + (1) + "---->"
					+ requestParsdata.get(String.valueOf(1)));
			if ("BalanceEnquiry".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)));
			} else if ("MiniStatement".equalsIgnoreCase(requestParsdata
					.get(String.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)));
			} else if ("FTMMID".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)) + ";"
						+ requestParsdata.get(String.valueOf(6)) + ";"
						+ requestParsdata.get(String.valueOf(7)) + ";"
						+ requestParsdata.get(String.valueOf(8)) + ";"
						+ requestParsdata.get(String.valueOf(9)) + ";"
						+ requestParsdata.get(String.valueOf(10)));
			} else if ("FTIFSC".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)) + ";"
						+ requestParsdata.get(String.valueOf(6)) + ";"
						+ requestParsdata.get(String.valueOf(7)) + ";"
						+ requestParsdata.get(String.valueOf(8)) + ";"
						+ requestParsdata.get(String.valueOf(9)) + ";"
						+ requestParsdata.get(String.valueOf(10)));
			} else if ("FTAadhaar".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)) + ";"
						+ requestParsdata.get(String.valueOf(6)) + ";"
						+ requestParsdata.get(String.valueOf(7)) + ";"
						+ requestParsdata.get(String.valueOf(8)));
			} else if ("KnowMMID".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)));
			} else if ("ChangeMPin".equalsIgnoreCase(requestParsdata.get(String
					.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)) + ";"
						+ requestParsdata.get(String.valueOf(6)) + ";"
						+ requestParsdata.get(String.valueOf(7)) + ";"
						+ requestParsdata.get(String.valueOf(8)));
			} else if ("GenerateOTP".equalsIgnoreCase(requestParsdata
					.get(String.valueOf(1)))) {
				System.out.println(requestParsdata.get(String.valueOf(3)) + ";"
						+ requestParsdata.get(String.valueOf(5)) + ";"
						+ requestParsdata.get(String.valueOf(6)));
			}
			//}
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {

		}

	}

}
