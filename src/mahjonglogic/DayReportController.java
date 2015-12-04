package mahjonglogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class DayReportController extends  AbstractReportController {


	DayReportController(){

	}


	protected Object[][] getTableData(){
		Connection connection = null;
		ResultSet results;
		Object[][] dbData = new String[100][3];
		
		//Object[][] dayReport = null;
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		String SQLString = new String (
				"SELECT DATE,WEEKDAY,TOTAL_SCORE FROM DAY_REPORT LIMIT 100;"
				);
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(SQLString);
			int i = 0;
			while (results.next()) {
				dbData[i][0] = results.getString(1);
				dbData[i][1] = results.getString(2);
				dbData[i][2] = results.getString(3);
				++i;
			}


		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {


			//コネクション切断
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}

		}
		return dbData;

	}



}
