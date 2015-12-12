package mahjonglogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class LoginInfo {
	private String loginId = null;
	private String loginPassword = null;
	private String drmlUrl = null;
	private Boolean saveLoginId = null;
	LoginInfo(int type){
		getLoginInfoDB(type);
	}
	
	
	protected void getLoginInfoDB(int typeCode) {

		Connection connection = null;
		ResultSet results = null;
		String SQLString;
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		try {
			SQLString = new String (
					"SELECT VALUE FROM LoginInfo WHERE ATTRIBUTE = 'LoginName' AND SiteType = " + typeCode
					);

			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(SQLString);
			results.next(); 
			loginId	= results.getString(1);

			SQLString = new String (
					"SELECT VALUE FROM LoginInfo WHERE ATTRIBUTE = 'baseUrl' AND SiteType = " + typeCode
					);
			results = statement.executeQuery(SQLString);
			results.next(); 
			drmlUrl	= results.getString(1);

			SQLString = new String (
					"SELECT VALUE FROM LoginInfo WHERE ATTRIBUTE = 'SaveLoginName' AND SiteType = " + typeCode
					);
			results = statement.executeQuery(SQLString);
			results.next(); 
			saveLoginId	= Boolean.valueOf(results.getString(1));



		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		//コネクション切断
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	
	
	
	
	
	public Boolean getSaveLoginId() {
		return saveLoginId;
	}

	public void setSaveLoginId(Boolean saveLoginId) {
		this.saveLoginId = saveLoginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getDrmlUrl() {
		return drmlUrl;
	}

	public void setDrmlUrl(String drmlUrl) {
		this.drmlUrl = drmlUrl;
	}


	public void updateLoginInfoDB(int typeCode) {
		Connection connection = null;
		String SQLString;
		String loginIdStr;
		String saveLoginIdStr;
		
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		try {

			loginIdStr = "";
			if (saveLoginId){
				loginIdStr = loginId;
			} 
			saveLoginIdStr = String.valueOf(saveLoginId)
;			
			SQLString = new String (
					"UPDATE LoginInfo SET VALUE = '" + 
							loginIdStr + "' WHERE ATTRIBUTE = 'LOGIN_ID' and SiteType = " +
							typeCode 
					);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			statement.execute(SQLString);

			SQLString = new String (
					"UPDATE LoginInfo SET VALUE = '" + 
							saveLoginIdStr + "' WHERE ATTRIBUTE = 'SAVE_LOGIN_NAME' and SiteType = " +
							typeCode 
					);
			statement.execute(SQLString);


		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		//コネクション切断
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}


}
////DBから読む様に変える
//properties = new Properties();
//try {
//	//MahjonglogicDataAnalysisを読み込む
//	InputStream is = ClassLoader.getSystemResourceAsStream("mahjonglogic/mahjonglogic.properties");
//	properties.load(is);
//	setLoginId(properties.getProperty("loginId"));
////	setLoginPassword(properties.getProperty("loginPassword"));
//	setDrmlUrl(properties.getProperty("drmlUrl"));
//	setSaveLoginId(Boolean.valueOf(properties.getProperty("saveLoginId")));
//
//} catch (IOException e) {
//	throw new RuntimeException(e);
//} catch (Exception e) {
//	throw new RuntimeException(e);
//}
