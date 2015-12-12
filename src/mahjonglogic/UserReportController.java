package mahjonglogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

public class UserReportController extends  AbstractReportController {

	UserReportView reportView;

	UserReportController(){

	}

	protected void createView(int viewType){


		//ビューのインスタンス作成
		reportView = new UserReportView();

		//tableData取得
		tableData = getTableData();

		//ビューの初期化
		reportView.initialize(tableData);

		// buttonにアクションリスナーを登録
		reportView.closeButton.addActionListener(closeAction);
		reportView.userFilterButton.addActionListener(filterAction);

		//ビューの表示
		reportView.frame.setVisible(true);

	}


	private ActionListener closeAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			reportView.frame.dispose();
		};
	};

	//ユーザ名フィルタ処理
	private ActionListener filterAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			List<String[]> filterTabledata = new ArrayList<String[]>();
			String filterStr = reportView.userFilterText.getText().toLowerCase();

			for (int i = 0;i < tableData.length;++i){
				if ( ((String) tableData[i][0]).toLowerCase().startsWith(filterStr)) {
					filterTabledata.add((String[]) tableData[i].clone());
				}
			}
//			reportView.reportTable.setModel(new DefaultTableModel(
//					(String[][])filterTabledata.toArray(new String[0][0]),
//					new String[] {
//							"ユーザ名", "対戦数", "合計点数" ,"平均点数"
//					}
//					));
//
//
//			TableModel tm = reportView.reportTable.getModel();
//			tm.setValueAt("!jjj!", 0, 0);
			reportView.tableData = (String[][])filterTabledata.toArray(new String[0][0]);
			reportView.createTable();


			//			reportView.initialize((String[][])filterTabledata.toArray(new String[0][0]));
//			reportView.reportTable.revalidate();
//			reportView.reportTable.repaint();

		};
	};


	protected Object[][] getTableData(){

		Connection connection = null;
		ResultSet results = null;
//		Object[][] dayReport = null;
		List<String[]> tableDataList = new ArrayList<String[]>();
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		String SQLString = new String (
				"SELECT NAME,CNT,TOTAL,AVG FROM UserReport;"
				);
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(SQLString);
			while (results.next()) {
				String[] line = {"","","","" };
				line[0] = results.getString(1);
				line[1] = results.getString(2);
				line[2] = results.getString(3);
				line[3] = results.getString(4);
				tableDataList.add(line.clone());
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

		tableData = (String[][])tableDataList.toArray(new String[0][0]);

		return(tableData);
	}

}
