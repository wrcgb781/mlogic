package mahjonglogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;

public class StatementDataDatabase {
	private Boolean dataWriteOver;

	public Boolean getdataWriteOver(){
		return dataWriteOver;
	}

	StatementDataDatabase(){
		dataWriteOver = false;
	}




	public void insert(ArrayList<String[]> statementData) {

		Connection connection = null;

		try {
			//BasicDataSourceクラスのインスタンス作成...コネクションプールを使う
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		for (int i =0 ;i < statementData.size();++i){
			StringBuilder DoraDataLine = new StringBuilder();
			for ( int ii = 0;ii < 7; ++ii){
				DoraDataLine.append(statementData.get(i)[ii]);
				if (ii != statementData.get(i).length -1 ){
					DoraDataLine.append(":");
				}
			}

			System.out.println(DoraDataLine);

			String SQLString = new String (
					"INSERT INTO STATEMENT VALUES ('" +
							statementData.get(i)[0] + "','" +
							statementData.get(i)[1] + "','" +
							statementData.get(i)[2] + "','" +
							statementData.get(i)[3] + "','" +
							statementData.get(i)[4] + "','" +
							statementData.get(i)[5] + "','" +
							statementData.get(i)[6] + "');"
					);

			try {
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				statement.execute(SQLString);

				//		         ResultSet results = statement.executeQuery("select * from STATEMENT");
				//		         while (results.next()) {
				//		            System.out.println("id1 = " + results.getString("ID1"));
				//		            System.out.println("id2 = " + results.getInt("ID2"));
				//		         }
			} catch (SQLException e) {
				//ID1のユニーク制約違反の場合は、取得完了フラグを付与
				String ErrorCHK1 = "[SQLITE_CONSTRAINT]  Abort due to constraint violation (UNIQUE constraint failed: Statement.StatementCode)";
				if ( e.getMessage().equals(ErrorCHK1)){
					 dataWriteOver = true;
					try {
						if (connection != null) {
							connection.close();
						}
					} catch (SQLException e1) {
						System.err.println(e1);
					}
					break;
				} else {
					System.err.println(e.getMessage());
				}
			}
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return;
	}


}
