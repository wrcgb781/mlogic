package mahjonglogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class HourReportController extends  AbstractReportController {
	HourReportController(){}


	protected Object[][] getTableData(){

		Connection connection = null;
		ResultSet results = null;
//		Object[][] dayReport = null;
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		String SQLString = new String (
				"SELECT HOUR,TOTAL_SCORE FROM HOUR_REPORT;"
				);
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(SQLString);
			int i = 0;
			tableData = new String[24][2];
			while (results.next()) {
				tableData[i][0] = results.getString(1);
				tableData[i][1] = results.getString(2);
				++i;
			}


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

		return(tableData);
	}

}
