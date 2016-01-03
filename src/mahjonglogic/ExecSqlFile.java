package mahjonglogic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


public class ExecSqlFile {
	String sqlString;
	public final String sqlDirName = "SQL";
	public final String tableDir = sqlDirName + "/CreateTable";
	public final String viewDir = sqlDirName + "/CreateView";
	public final String initialDataDir = sqlDirName + "/InitialData";
	private HashMap<String,String> mahjonglogicConfig; //設定値配列

	//改行コード
	private static final String BR = System.getProperty("line.separator");

	public void initialization () {

		//データベースファイル確認
		//設定ファイル取得
//		mahjonglogicConfig = getMahjonglogicConfig();
//		File dbFile = new File(mahjonglogicConfig.get("databasePath") + "/mahjonglogic.db");
//		if (! dbFile.exists()){
//			//DBファイルが無い場合、SQLiteのEXEをチェック
//			File dbExe = new File(mahjonglogicConfig.get("databasePath") + "/sqlite3.exe");
//			if (dbExe.exists()){
//				//SQLiteのEXEがあれば、DBファイル作成コマンドを実行する
				//なくても問題なかった
//			}  else {
			//パス設定エラー

//		}
//	}

		Connection connection = null;
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}
		//既存テーブル一覧取得
		List<String> tableList = new ArrayList<String>();
		tableList = getTableList(connection);
		//既存のテーブルを削除
		deleteAllTable(connection,tableList);

		//既存ビュー一覧を取得
		List<String> viewList = new ArrayList<String>();
		viewList = getViewList(connection);
		//既存のビューを削除
		deleteAllView(connection,viewList);


		//テーブル作成
		//SQLフォルダ内のファイル情報を取得
		createTable(connection,tableDir);

		//ビュー作成
		createView(connection,viewDir);

		//初期データ設定
		File dir = new File(ExecSqlFile.class.getResource(initialDataDir).getFile());

		//取得したファイルを順に処理
		for (File f : dir.listFiles()) {

			BufferedReader br = null;
			String line = null;
				//BufferedReaderでファイル内容を取得
				try {
					br = new BufferedReader(new FileReader(f));
					while((line = br.readLine()) != null){
						execSql(line,connection);
						System.out.println(line);;
					}
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
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


	private void createTable(Connection connection,String dirName) {
		File dir = new File(ExecSqlFile.class.getResource(dirName).getFile());

		//取得したファイルを順に処理
		for (File f : dir.listFiles()) {

			//ファイル内容の取得
			String filePath = f.getPath();
			sqlString = getFileData(filePath);

//			System.out.println(SQLstring);
			//SQL実行
			execSql(sqlString,connection);
		}
	}

	private void createView(Connection connection,String dirName) {
		File dir = new File(ExecSqlFile.class.getResource(dirName).getFile());

		//ファイル名でソートする(VIEWが参照するVIEWを先に作る)

		List<String> sqlfile = new ArrayList<String>();
		 for (File f: dir.listFiles()){
			 sqlfile.add(f.getPath());
		 }
		 Collections.sort(sqlfile);



		//取得したファイルを順に処理
		for (String filePath : sqlfile) {

			//ファイル内容の取得
			sqlString = getFileData(filePath);

//			System.out.println(SQLstring);
			//SQL実行
			execSql(sqlString,connection);
		}
	}

	private List<String> getTableList(Connection connection){
		String sqlGetTables = "select distinct tbl_name from sqlite_master where type = 'table'";
		ResultSet rs;
		List<String> tableList = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			rs = statement.executeQuery(sqlGetTables);
			while (rs.next()){
				tableList.add(rs.getString(1));
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return tableList;
	}

	private void deleteAllTable(Connection connection,List<String> tableList){

		// 各要素に触るための Iterator を取得
		Iterator iter = tableList.iterator();
	    while(iter.hasNext()) {
			try {
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				String sqlDropTable = "Drop TABLE " + iter.next() + ";";
				statement.execute(sqlDropTable);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
	    }
	}
	private List<String> getViewList(Connection connection){
		String sqlGetView = "select distinct tbl_name from sqlite_master where type = 'view'";
		ResultSet rs;
		List<String> viewList = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			rs = statement.executeQuery(sqlGetView);
			while (rs.next()){
				viewList.add(rs.getString(1));
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return viewList;
	}

	private void deleteAllView(Connection connection,List<String> viewList){
		// 各要素に触るための Iterator を取得
		Iterator iter = viewList.iterator();
	    while(iter.hasNext()) {
			try {
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				String sqlDropView = "DROP VIEW " + iter.next() + ";";
				statement.execute(sqlDropView);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
	    }
	}

	private String getFileData(String filename) {

		String fileData = new String();
		StringBuilder sb = new StringBuilder();
//		String filename = f.getPath();
		File file = new File(filename);
		BufferedReader br = null;

		String line = null;
		try {
			//BufferedReaderでファイル内容を取得
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			while((line = br.readLine()) != null){
				//1行読み込み、StringBuilderで追加
				sb.append(line);
				sb.append(BR);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		fileData = sb.toString();
		try {
			br.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return(fileData);
	}


	private static void execSql(String SQLstring,Connection connection){

		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			statement.executeUpdate(SQLstring);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}	}

	//設定ファイルを読んで配列に取得
	private HashMap<String,String> getMahjonglogicConfig(){
		HashMap<String,String> mahjonglogicConfig = new HashMap<String,String>();

		Properties properties = new Properties();
		InputStream is = ClassLoader.getSystemResourceAsStream("mahjonglogic/mahjonglogic.properties");
		try {
			properties.load(is);
			mahjonglogicConfig.put("databasePath",properties.getProperty("databasePath"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return mahjonglogicConfig;
	}

}


