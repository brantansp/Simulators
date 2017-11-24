package API;

/**
 ***************************************************************************************************
 **********************************Balance creator**************************************************
 ***********************************GUI Coded by***************************************************
 ***********************************BRANTAN SP***************************************************
 ***************************************************************************************************
 */


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.lang.RandomStringUtils;
import API.RequestException;
import java.awt.TextField;
import java.awt.Button;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.awt.Color;
import java.awt.Checkbox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BalanceGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ResultSet resultSet;
	private StringBuffer sqlBuffer =new StringBuffer();
	private Statement statement;
	private static Connection connection;
	private String accNO;
	String cardNumber;
	TextField UrlTextField = new TextField();
	TextField DsnTextField = new TextField();
	TextField UserNameTestField = new TextField();
	TextField PasswordTextField = new TextField();
	
	public static final String CONF_PROPERTY = "API\\conf.properties";
	public static final String BITS_PROPERTY = "API\\bitsdetails.xml";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BalanceGUI frame = new BalanceGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 */
	public BalanceGUI() {
		setTitle("Balance Creator v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterTheAccount = new JLabel("Enter the Account number  :");
		lblEnterTheAccount.setBounds(10, 150, 158, 14);
		contentPane.add(lblEnterTheAccount);
		
		TextField textField = new TextField();
		textField.setBounds(171, 145, 210, 22);
		contentPane.add(textField);
		
		Label label = new Label("");
		label.setAlignment(Label.CENTER);
		
		label.setBounds(82, 212, 299, 22);
		contentPane.add(label);
		
		Label label_1 = new Label("Balance :");
		label_1.setBounds(10, 240, 62, 22);
		contentPane.add(label_1);
		label_1.setVisible(false);
		
		Label label_2 = new Label("New label");
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(110, 240, 210, 22);
		contentPane.add(label_2);
		label_2.setVisible(false);
		
		Label connLabel = new Label("");
		connLabel.setAlignment(Label.RIGHT);
		connLabel.setBounds(341, 38, 83, 22);
		contentPane.add(connLabel);
		
		Button insrBalBtn = new Button("Insert Balance");
		Button resetbtn = new Button("Reset");
		Button getBalBtn = new Button("Get balance");
		
		resetbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setEditable(true);
				textField.setText("");
				insrBalBtn.setEnabled(true);
				label_1.setVisible(false);
				label_2.setVisible(false);
				resetbtn.setEnabled(false);
				getBalBtn.setEnabled(true);
				label.setVisible(false);
				connLabel.setText("");
			}
		});
		resetbtn.setBounds(322, 184, 90, 22);
		contentPane.add(resetbtn);
		resetbtn.setEnabled(false);
		
		
		insrBalBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setEditable(false);
				insrBalBtn.setEnabled(false);
				resetbtn.setEnabled(true);
			
			    String Acc_No = textField.getText();
				int length= Acc_No.length();
	
				if (length <= 9 || length >=20)
				    {
					label.setText("Account number should be in between 10 & 20 digits");
					label.setForeground(Color.RED);
					label.setVisible(true);
					textField.setEditable(false);
					insrBalBtn.setEnabled(false);
					getBalBtn.setEnabled(false);
					label_1.setVisible(false);
					label_2.setVisible(false);
					resetbtn.setEnabled(true);
				    }
				else
				    {
					try {
						connection = getConnection();
						connLabel.setText("Connected");
						connLabel.setForeground(Color.BLUE);
						
							sqlBuffer.delete(0, sqlBuffer.length());
							sqlBuffer.append("SELECT BALANCEAMT FROM BALANCE WHERE ACCOUNTNO ='" +Acc_No + "'"  +" AND ID =(SELECT MAX(ID) FROM BALANCE WHERE ACCOUNTNO ='"+Acc_No+"')");
							try {
								resultSet=doSelect(sqlBuffer);
							} catch (RequestException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							StringBuilder builder = new StringBuilder();
							int columnCount = 0;
							try {
								columnCount = resultSet.getMetaData().getColumnCount();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							try {
								while (resultSet.next()) {
								    for (int i = 0; i < columnCount;) {
								        builder.append(resultSet.getString(i + 1));
								        if (++i < columnCount) builder.append(",");
								    }
								    builder.append("\r\n");
								}
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							String resultSetAsString = builder.toString();
		
							if (resultSetAsString.equals("")) {
								insert(Acc_No);
								label.setVisible(true);
								label.setText("Balance and Statement Inserted successfully !!!");
								getBalBtn.setEnabled(true);
								label.setForeground(Color.BLUE);
							}	
							else
							{
								label.setVisible(true);
								label.setText("Account already exits is DB");
								label.setForeground(Color.RED);
							}			
					} catch (ConfigurationException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						connLabel.setText("Not Connected");
						connLabel.setForeground(Color.RED);
						label.setText("Please check the DB connection paramaeters");
						label.setForeground(Color.RED);
					}				
			}
			}
		}
		);
		insrBalBtn.setBounds(174, 184, 90, 22);
		contentPane.add(insrBalBtn);
		
		getBalBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Acc_No = textField.getText();
				int length= Acc_No.length();
				//System.out.println(length);
				if (length <= 9 || length >=20)
				{
					label.setText("Account number should be in between 10 & 20 digits");
					label.setForeground(Color.RED);
					label.setVisible(true);
					textField.setEditable(false);
					insrBalBtn.setEnabled(false);
					label_1.setVisible(false);
					label_2.setVisible(false);
					getBalBtn.setEnabled(false);
					resetbtn.setEnabled(true);
				}
				else{
					try {
						try {
							connection = getConnection();
							connLabel.setText("Connected");
							connLabel.setForeground(Color.BLUE);
							sqlBuffer.delete(0, sqlBuffer.length());
							sqlBuffer.append("SELECT BALANCEAMT FROM BALANCE WHERE ACCOUNTNO ='" +Acc_No + "'"  +" AND ID =(SELECT MAX(ID) FROM BALANCE WHERE ACCOUNTNO ='"+Acc_No+"')");
							try {
								resultSet=doSelect(sqlBuffer);
							} catch (RequestException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
							StringBuilder builder = new StringBuilder();
							int columnCount = 0;
							try {
								columnCount = resultSet.getMetaData().getColumnCount();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							try {
								while (resultSet.next()) {
								    for (int i = 0; i < columnCount;) {
								        builder.append(resultSet.getString(i + 1));
								        if (++i < columnCount) builder.append(",");
								    }
								    builder.append("\r\n");
								}
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							String resultSetAsString = builder.toString();
							
							
			                if (resultSetAsString.equals("")) {
			                	label.setText("Account number not found");
								label.setForeground(Color.RED);
								label.setVisible(true);
								insrBalBtn.setEnabled(true);
								getBalBtn.setEnabled(false);
								resetbtn.setEnabled(true);
								textField.setEnabled(true);
							}	
			                else
			                {
			                	label_2.setText(resultSetAsString);
								label_2.setVisible(true);
								label_1.setVisible(true);
								textField.setEditable(false);
								insrBalBtn.setEnabled(false);
								getBalBtn.setEnabled(false);
								label_2.setVisible(true);
								resetbtn.setEnabled(true);
			                }
						} catch (ConfigurationException e1) {
							e1.printStackTrace();
						}						
					} catch (SQLException e1) {
						connLabel.setText("Not Connected");
						connLabel.setForeground(Color.RED);
						label.setText("Please check the DB connection paramaeters");
						label.setForeground(Color.RED);
					}	
				}
			}
		});
		getBalBtn.setBounds(23, 184, 90, 22);
		contentPane.add(getBalBtn);
		Button button = new Button("Test");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					getConnection();
					connLabel.setText("Connected");
					connLabel.setForeground(Color.BLUE);
					label_2.setText("");
					label.setText("");
					textField.setText("");
				} 
				catch 
				(ConfigurationException | SQLException e1) {
				connLabel.setText("Not Connected");
				connLabel.setForeground(Color.RED);
				e1.printStackTrace();	
		    }		
			}
		});
		button.setBounds(379, 10, 45, 22);
		contentPane.add(button);
		
		Checkbox checkbox = new Checkbox("Edit Connection", false);
		checkbox.addItemListener(new ItemListener(){
              @Override
              public void itemStateChanged(ItemEvent e) {
                  if(e.getStateChange() == ItemEvent.SELECTED){
              
                		UrlTextField.setEditable(true);
        				DsnTextField.setEditable(true);
        				UserNameTestField.setEditable(true);
        				PasswordTextField.setEditable(true);
                  }
                  else if(e.getStateChange() == ItemEvent.DESELECTED){
                	    
                		UrlTextField.setEditable(false);
        				DsnTextField.setEditable(false);
        				UserNameTestField.setEditable(false);
        				PasswordTextField.setEditable(false);
                  }

                  validate();
                  repaint();
              }
		
          });
		
		checkbox.setBounds(10, 0, 138, 22);
		contentPane.add(checkbox);
		
		
		UrlTextField.setText("oracle.jdbc.driver.OracleDriver");
		UrlTextField.setEditable(false);
		UrlTextField.setBounds(108, 21, 177, 22);
		contentPane.add(UrlTextField);
		
		Label label_3 = new Label("URL");
		label_3.setBounds(10, 21, 27, 22);
		contentPane.add(label_3);
		
		Label label_4 = new Label("DSN");
		label_4.setBounds(10, 51, 27, 22);
		contentPane.add(label_4);
		
		
		DsnTextField.setText("10.44.121.51:1526:fccuat");
		DsnTextField.setEditable(false);
		DsnTextField.setBounds(108, 51, 177, 22);
		contentPane.add(DsnTextField);
		
		Label label_5 = new Label("USER NAME");
		label_5.setBounds(10, 81, 73, 22);
		contentPane.add(label_5);
		
		Label label_6 = new Label("PASSWORD");
		label_6.setBounds(10, 110, 73, 22);
		contentPane.add(label_6);
		
		
		UserNameTestField.setText("mpayadmin_uat");
		UserNameTestField.setEditable(false);
		UserNameTestField.setBounds(108, 81, 177, 22);
		contentPane.add(UserNameTestField);
		
		
		PasswordTextField.setText("mpayadmin_uat");
		PasswordTextField.setEditable(false);
		PasswordTextField.setBounds(108, 110, 177, 22);
		contentPane.add(PasswordTextField);
	}
	
public void insert(String str){
		try {
			try {
				connection = getConnection();
			} catch (ConfigurationException e1) {
				e1.printStackTrace();
			}
			accNO=str;
			
			updateMinistatement(accNO);
			UpdateBalance(accNO);
				
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			closeConnection();
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
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
}

public ResultSet doSelect(StringBuffer sqlbuffer) throws RequestException, SQLException {

	if (connection == null) {
		throw new RequestException("Connection is null");
	}

	clear();

	statement = connection.createStatement();
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
String DSNPrefix="";
String DSN="";
String user="";
String pwd="";
	   	try {

	   		DSNPrefix="jdbc:oracle:thin:@";
	   		url= UrlTextField.getText();
	   		DSN = DSNPrefix+DsnTextField.getText();
	   		user= UserNameTestField.getText();
	   		pwd = PasswordTextField.getText();
	   		
			Class.forName(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
		e.printStackTrace();
	}finally{
		connection =null;
	}
}
}
