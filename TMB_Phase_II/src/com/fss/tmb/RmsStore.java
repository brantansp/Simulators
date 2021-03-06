package com.fss.tmb;



import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;

public class RmsStore{


	public static final String parsedRecords = "MiscDetails"+StaticStore.bankCode;
	Context context;
	Cursor cursor1;

	//UAT	//9223173929 9223173930 9223173932// Production	//9223173933
	//Ram gave VMN 9248082871
	
	public static SQLiteDatabase db;
	public static String defaultString    =   "9223173929~0~0~0~0~0~0~0~0~0~0~1~203577465141885203944391850079714410739~0~0~0~0~"+StaticStore.GprsServiceUrl+"~http://www.fss.co.in~" +
    "0~0~0~0~203577465141885203944391850079714410739~0~" +
    "2~0~0~0~0~0~0~0~0~0~0~";

//	public static String accountString        =   "6~0*0~0*0~0*0~0*0~0*0~0*0~0*0"+"#"+StaticStore.registeredUserStatus+"#"+
	
	public static String accountString = "150~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0~0*0"+"#"+StaticStore.registeredUserStatus+"#"+			
 
    StaticStore.isMpinAtLast+"#"+
    StaticStore.mPinHexaValue+"#"+
    StaticStore.SMS_AUTHENTICATION_SMSMODE+"#"+
    StaticStore.SMS_AUTHENTICATION_GPRSMODE+"#"+
    StaticStore.chequeLength+"#"+
    StaticStore.accOwner+"#";//31000000000001
	
	public static String categoryID_Labels = "EMPTY";
	public static String Recharge_Nickname = "EMPTY";
	//public static String account_Type = "SA*Savings A/c *N*Y*Y*Y*Y#CC*Current Current A/c*N*N*N*Y*Y#";
	public static String account_Type = "EMPTY";
	
	
	private final String DB_NAME = "MPAY_NI"+StaticStore.bankCode;  
	private final int DB_VERSION = 1;
	public static String s;
	private static String TABLE_NAME;
	private static String TABLE_NAME_IMG;
	private static String TABLE_ROW_ID = "id";
	public static String TABLE_ROW_VALUE_DEF = "1";
	public static String TABLE_ROW_VALUE_INB = "2";
	public static String TABLE_ROW_VALUE_ACC = "3";
	public static String TABLE_ROW_VALUE_MBT = "4";
	public static String TABLE_ROW_VALUE_MBT_NICK = "5";
	public static String TABLE_ROW_VALUE_ACC_TYPE = "6";
	private static String TABLE_ROW_ONE = "table_row_one";
	public static SQLiteStatement insertStmt;
	public static int count = 0;
	
	 public RmsStore(Context context) {
		 this.context = context;
		 this.TABLE_NAME = parsedRecords;
		 this.TABLE_NAME_IMG = parsedRecords+"img";
		 CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
		 this.db = helper.getWritableDatabase();		
		 s=db.getPath();
		 StaticStore.LogPrinter('i',">>>>>>>>>>>>DBNAMW"+s);
	//	 helper.onCreate(db);
		 if(StaticStore.installAddRow)
			{
				StaticStore.LogPrinter('i',"*********************insatlladdrow********************");
				StaticStore.installAddRow = false;
				
				addRow(defaultString, TABLE_ROW_VALUE_DEF);
				addRow("", TABLE_ROW_VALUE_INB);
				addRow(accountString, TABLE_ROW_VALUE_ACC);
				addRow(categoryID_Labels, TABLE_ROW_VALUE_MBT);
				addRow(Recharge_Nickname, TABLE_ROW_VALUE_MBT_NICK);
				addRow(account_Type, TABLE_ROW_VALUE_ACC_TYPE);
			}
	}
	
	public synchronized  static void writeRecordStore(String record, String recordStoreName, String rowIdValue) {
		TABLE_NAME = recordStoreName;		
		deleteRow(rowIdValue);
		addRow(record, rowIdValue);
	}

	public synchronized  static String[] writeInboxRecordStore(
			String[] recordArray, String newRecord, String recordStoreName) {
		
		if (StaticStore.withMemory) {
			TABLE_NAME = recordStoreName;
			deleteRow(TABLE_ROW_VALUE_INB);
		}
		String recordString = "";
		String[] newRecArray = new String[((recordArray != null && recordArray.length == 5) ? recordArray.length
				: (recordArray == null) ? 1 : recordArray.length + 1)];

		if (recordArray != null && recordArray.length == 5) {
			recordString += newRecord + "`";
			newRecArray[0] = newRecord;

			for (int i = 0; i < recordArray.length - 1; i++) {
				recordString += recordArray[i] + "`";
				newRecArray[i + 1] = recordArray[i];
			}
		} else {
			recordString += newRecord + "`";
			newRecArray[0] = newRecord;

			if (recordArray != null) {
				for (int i = 0; i < recordArray.length; i++) {
					recordString += recordArray[i] + "`";
					newRecArray[i + 1] = recordArray[i];
				}
			}
		}

		if (StaticStore.withMemory){
			addRow(recordString, TABLE_ROW_VALUE_INB);
		}

		return newRecArray;
		
	}

	public synchronized  static String[] deleteInboxRecordStore(
			String[] recordArray, int index, int size, String strToDelete,
			String recordStoreName) {

		if (StaticStore.withMemory){
			TABLE_NAME = recordStoreName;
			deleteRow(TABLE_ROW_VALUE_INB);
		}

		String recordString = "";
		String[] newRecArray = new String[(recordArray.length == size
				&& (index == recordArray.length - 1) && !recordArray[index]
				.equals(strToDelete)) ? recordArray.length
				: recordArray.length - 1];
		byte increment = 0;

		if (size != recordArray.length)
			index = index + (recordArray.length - size);

		for (int i = 0; i < recordArray.length; i++) {
			if (recordArray.length == size && (index == recordArray.length - 1)
					&& !recordArray[index].equals(strToDelete)) {
				recordString += recordArray[i] + "`";
				newRecArray[increment] = recordArray[i];
				increment++;
			} else if (i != index) {
				recordString += recordArray[i] + "`";
				newRecArray[increment] = recordArray[i];
				increment++;
			}
		}

		if (StaticStore.withMemory){
			addRow(recordString, TABLE_ROW_VALUE_INB);
		}

		return newRecArray;
	}

	public synchronized static String[] updateInboxRecordStore(
			String[] recordArray, String recordStoreName) {

		if (StaticStore.withMemory){
			TABLE_NAME = recordStoreName;
			deleteRow(TABLE_ROW_VALUE_INB);
		}
		String recordString = "";
		String[] newRecArray = new String[recordArray.length];
		byte increment = 0;

		for (int i = 0; i < recordArray.length; i++) {
			recordString += recordArray[i] + "`";
			newRecArray[increment] = recordArray[i];
			increment++;
		}

		if (StaticStore.withMemory){
			addRow(recordString, TABLE_ROW_VALUE_INB);	
		}

		return newRecArray;
	}

	public synchronized static String[] readInboxRecordStore(
			String recordStoreName, String[] recordArray) {
		//db.openDatabase(s, null, SQLiteDatabase.OPEN_READWRITE);
		try {
			if (!StaticStore.withMemory)
				return recordArray;

		//	String recordString = readRecordStore(recordStoreName);
			
			TABLE_NAME = recordStoreName;
			ArrayList<Object> rowArray = getRowAsArray(TABLE_ROW_VALUE_INB);
			String recordString = rowArray.get(1).toString();
			byte totalMessages = get_TotalNoChars(recordString, '`');

			if (totalMessages != 0) {
				String[] newRecArray = new String[totalMessages];

				for (int i = 0; i < totalMessages; i++) {
					int index = recordString.indexOf("`");
					newRecArray[i] = recordString.substring(0, index);

					if (i != totalMessages - 1)
						recordString = recordString.substring(index + 1);

				}
					//db.close();//Comment for use
				return newRecArray;
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;

	}

	public synchronized static String readRecordStore(String recStore, String rowIdValue) {
		String readString = "";
		try {
			TABLE_NAME = recStore;
			ArrayList<Object> rowArray = getRowAsArray(rowIdValue);
			readString = rowArray.get(1).toString();
		} catch (Exception e) {
			e.printStackTrace();

			if (rowIdValue.equals(TABLE_ROW_VALUE_DEF))
				readString = defaultString;
			else if(rowIdValue.equals(TABLE_ROW_VALUE_MBT_NICK)){
					readString = Recharge_Nickname;
			}
			else if(rowIdValue.equals(TABLE_ROW_VALUE_MBT))
					readString = categoryID_Labels;
			else if(rowIdValue.equals(TABLE_ROW_VALUE_ACC_TYPE))
				readString = account_Type;
			else
				readString = accountString;
			return readString;
		}

		return readString;
	}

	private static byte get_TotalNoChars(String string, char character) {
		byte count = 0;
		char[] temp = string.toCharArray();

		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == character)
				count++;
		}
		return count;
	}
	
	public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
		
		public CustomSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}
 
		@Override
		public void onCreate(SQLiteDatabase db) {
			try
			{
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IMG);

			}
			catch(Exception e)
			{
				StaticStore.LogPrinter('i',"****************first time creation****************");
			}
			try {
				String newTableQueryString = "create table " + TABLE_NAME + " (" +
											TABLE_ROW_ID + " text primary key not null," +
											TABLE_ROW_ONE + " text" +
											//TABLE_ROW_TWO + " text" +
											");";
				
				String newimgTable = "create table " + TABLE_NAME_IMG + " (" +
						TABLE_ROW_ID + " text primary key not null," +
						TABLE_ROW_ONE + " BLOB"+");";

				
				db.execSQL(newTableQueryString);
				db.execSQL(newimgTable);
				
				StaticStore.installAddRow = true;
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
			// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE.
			onCreate(db);
		}
	}
	
	public static void deleteRow(String rowValue) {
		try {
			db.delete(TABLE_NAME, TABLE_ROW_ID + "=" + rowValue, null);
		} catch (Exception e) {
//			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	
	public static void addRow(String rowStringOne, String rowValue) {
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_ONE, rowStringOne);
		values.put(TABLE_ROW_ID, rowValue);

		try {
			//db.insert(TABLE_NAME, null, values);
			insertStmt = db.compileStatement(StaticStore.INSERT);
			insertStmt.bindString(1, rowValue);
			insertStmt.bindString(2, rowStringOne);
			insertStmt.executeInsert();
			StaticStore.LogPrinter('i',"**********in insert*******************"+(++count));
			StaticStore.LogPrinter('i',"### rowStringOne ---> "+rowStringOne);
			StaticStore.LogPrinter('i',"### Row Value ---> "+rowValue);
		} catch(Exception e) {
//			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		finally {
			try {
				insertStmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addRowImage(byte[] image,String rowidval) {
//		ContentValues values = new ContentValues();
//		values.put(TABLE_ROW_ONE, rowStringOne);
//		values.put(TABLE_ROW_ID, image);

		try {
			//db.insert(TABLE_NAME, null, values);
			insertStmt = db.compileStatement(StaticStore.INSERT_IMG);
			insertStmt.bindString(1, rowidval);
			insertStmt.bindBlob(2, image);
			insertStmt.executeInsert();
			StaticStore.LogPrinter('i',"**********Image inserted in insert*******************");
			StaticStore.LogPrinter('i',"### rowid val ---> "+rowidval);
			StaticStore.LogPrinter('i',"### Row image byte ---> "+image);
		} catch(Exception e) {
//			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		finally {
			try {
				insertStmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Object> getRowAsArray(String rowID) {
		ArrayList<Object> rowArray = new ArrayList<Object>();		
		try {
			Cursor cursor;
			
			cursor = db.query (
					TABLE_NAME,
					new String[] { TABLE_ROW_ID, TABLE_ROW_ONE},//TABLE_ROW_TWO
					TABLE_ROW_ID + "=" + rowID,
					null, null, null, null, null
			);
			
			cursor.moveToFirst();
			
			if (!cursor.isAfterLast()) {
				do {
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getString(1));
				}
				while (cursor.moveToNext());
			}
			cursor.close();
		} catch (SQLException e) {
//			Log.e("DB ERROR", e.toString());
//			e.printStackTrace();
		}
		return rowArray;
	}
	public synchronized static String deletesinglerecord(String recStore,
			String rowIdValue) {
		
		String existingNames = "";
		String nickname = "";
		existingNames = RmsStore.readRecordStore(RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		String tempnicknamelistArr[] = existingNames.split(";");
		for (int i = 0; i < tempnicknamelistArr.length; i++) {
			if (tempnicknamelistArr[i]
					.startsWith(StaticStore.rechargeSelcetedCategoryID)) {
				tempnicknamelistArr[i] = "";
			}else{
				if (nickname == "") {
					nickname = tempnicknamelistArr[i].toString();
				}else{
					nickname = nickname + ";"+ tempnicknamelistArr[i].toString();
				}
			}
		}
		RmsStore.writeRecordStore(nickname,RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
		StaticStore.midlet.rechargeDynamicInputs.recharge.setBeneficiaryList(RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT_NICK));
		return nickname;
	}
	
	public synchronized static String deleterecordfornodetails(String rowIdValue) {
		String[] nicknamelist = StaticStore.midlet.rechargeDynamicInputs.recharge
				.getBeneficiaryList();
		String tempnicknamelist = "";
		String tempnicknamelist1 = "";
		String nickname = "";
		String nickNameArr[] = null;
		String nickNameArr1[] = null;
		String readString = "";
		try {
			for (int i = 0; i < nicknamelist.length; i++) {
				if (nicknamelist[i]
						.startsWith(StaticStore.rechargeSelcetedCategoryID)) {
					tempnicknamelist = nicknamelist[i]
							.substring(nicknamelist[i].indexOf('*') + 1);
					} 
			}
			if (tempnicknamelist.equals("")) {
				return null;
			} else {
				String tempnicknamelistArr[] = tempnicknamelist.split(":");
				nickNameArr = new String[tempnicknamelistArr.length];
				for (int i = 0; i < tempnicknamelistArr.length; i++) {
					nickNameArr[i] = tempnicknamelistArr[i].substring(
							tempnicknamelistArr[i].indexOf('*') + 1,
							tempnicknamelistArr[i].indexOf('#'));
					tempnicknamelistArr[i] = "";
					nickname = tempnicknamelistArr[i].toString();
				}
				if (rowIdValue.equals(TABLE_ROW_VALUE_MBT_NICK)) {
					if(nickname.equals("")) {
						nickname = "";
					}else{
						nickname = StaticStore.rechargeSelcetedCategoryID +"*"+ nickname;
					}
					RmsStore.writeRecordStore(nickname,RmsStore.parsedRecords, RmsStore.TABLE_ROW_VALUE_MBT_NICK);
					StaticStore.midlet.rechargeDynamicInputs.recharge.setBeneficiaryList(RmsStore.readRecordStore(RmsStore.parsedRecords,RmsStore.TABLE_ROW_VALUE_MBT_NICK));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nickname;
	}
}
