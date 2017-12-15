package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.lang.RandomStringUtils;

import simulator.HostSimulator;

public class BalanceInsert {

	
	
	private ResultSet resultSet;

	private StringBuffer sqlBuffer =new StringBuffer();

	private Statement statement;
	
	private static Connection connection;
	
	private String accNO;
	
	String cardNumber;
	
	
	public static void main(String ar[]) {
		
		new BalanceInsert().insert();
		
	}
	
	
	public void insert(){
		
		
		try {
			try {
				connection = getConnection();
			} catch (ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Please Enter account number for Statement & Balance:");
			try {
				String str = bf.readLine();
				accNO=str;
				
				updateMinistatement(accNO);
				UpdateBalance(accNO);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			closeConnection();
			System.err.println("Balance and Statement Inserted successfully:");
		}
	}
	
	public void UpdateBalance(String AccNo){
		
		String amount =Amount();
		
		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("INSERT INTO BALANCE ( ID, BALANCEAMT, ");
		sqlBuffer.append("ACCOUNTNO, CARDNO, ACCOUNTTYPE , LEDGERAMT ) VALUES (");
		sqlBuffer.append("(select max(ID)+1 from balance)" + ",'" + amount + "','" + AccNo + "', '"
				+ cardNumber + "', '" +"10','"+amount
				+ "')");
		
		
		try {
			resultSet=doSelect(sqlBuffer);
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public ResultSet doSelect(StringBuffer sqlbuffer) throws RequestException, SQLException {

		if (connection == null) {
			throw new RequestException("Connection is null");
		}

		clear();

		statement = connection.createStatement();
		//System.out.println("select query :: "+sqlBuffer.toString());
		resultSet = statement.executeQuery(sqlBuffer.toString());

		return resultSet;
	}
	
	
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
	 
	public Connection getConnection() throws SQLException, ConfigurationException {
	
		
	String url="";
	String DSN="";
	String user="";
	String pwd="";
		
		
		   	try {
		   		
		   		
		   		URL input   = this.getClass().getClassLoader().getResource(HostSimulator.CONF_PROPERTY);
		   		PropertiesConfiguration conf =new PropertiesConfiguration(input);
		   		
		   		url = conf.getString("URL");
		   		DSN =conf.getString("DSN");
		   		user=conf.getString("USERNAME");
		   		pwd =conf.getString("PASSWORD");
				Class.forName(url);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      ConnectionFactory  conn =new DriverManagerConnectionFactory(DSN, user, pwd);
		
		      connection= conn.createConnection(); 
		      
		      return connection;
	}
	
	private void updateMinistatement(String AccNo){
		
		
		
		cardNumber =CardNo();
		String type;
		String amount;
		String details;
		for(int i=0;i<9;i++){
		
			if(i%2==0){
				type="DR";
				details ="DEBIT";
			}else{
				type="CR";
				details ="FUND";
			}
			
			amount =Amount();
			
		sqlBuffer.delete(0, sqlBuffer.length());
		sqlBuffer.append("INSERT INTO STATEMENT ( CARDNO, TYPE, ");
		sqlBuffer.append("AMOUNT, DETAILS, ACCOUNTNO ) VALUES ('");
		sqlBuffer.append(cardNumber + "','" + type + "','" + amount + "', '"
				+ details + "', '" + AccNo.toString().trim()
				+ "')");
		
		try {
			resultSet = doSelect(sqlBuffer);
		} catch (RequestException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		}
		
		
	}
	
	
	
	
	
	
	
	
	public String CardNo() {
		
		String card =RandomStringUtils.randomNumeric(16);
		
		return card;
	}
	
	public String Amount() {
		
		String amount =RandomStringUtils.randomNumeric(7);
		
		return amount;
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			connection =null;
		}
	}
	
	
}
