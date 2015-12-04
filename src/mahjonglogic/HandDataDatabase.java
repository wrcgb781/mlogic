package mahjonglogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

import mahjonglogic.HandDataParse.HandDataSet;

public class HandDataDatabase {
	private HandDataSet hds;
	private String handIdAndWalletdId;


	HandDataDatabase(){
	}

	public void insert(HandDataSet ahds,String ahandIdAndWalletdId){

		this.hds = ahds;
		this.handIdAndWalletdId = ahandIdAndWalletdId;
		Connection connection = null;
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		System.out.println(handIdAndWalletdId);


		//ユーザ点数の書き込み処理
		insertUserdata(connection);

		//row_numberの書き込み処理(横展開用ID)
//		updateUserdata(connection);


		//局データの書き込み処理
		insertHandData(connection);



		//コネクション切断
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e);
		}

	}
	/**
	 * @param userData
	 * @param id2_3
	 * @param connection
	 */
	private void insertHandData(Connection connection) {
		String SQLString = new String (
				"INSERT INTO HANDINFO VALUES ('" +
						handIdAndWalletdId + "','" +
						hds.getRuleset() + "','" +
						hds.getPrevailingWind() +
						"');"
				);

		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			statement.execute(SQLString);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * @param userData
	 * @param id2_3
	 * @param connection
	 */
	private void insertUserdata(Connection connection) {

//		System.out.println(handIdAndWalletdId);


		for ( int i = 0; i < hds.getUserDataList().size(); ++i  ){
			if ( hds.getUserDataList().get(i) != null && hds.getUserDataList().get(i)[0].length() > 0){
				String SQLString = new String (
						"INSERT INTO USER_POINT VALUES ('" +
								handIdAndWalletdId + "','" +
								hds.getUserDataList().get(i)[0] + "','" +
								hds.getUserDataList().get(i)[1] + "'," +
								i +
								");"
						);
				try {
					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30); // set timeout to 30 sec.
					statement.execute(SQLString);

				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}

			}

		}
	}

}
