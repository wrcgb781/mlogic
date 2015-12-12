package mahjonglogic;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbcp2.BasicDataSource;

import mahjonglogic.HandDataParse.HandDataSet;
import mahjonglogic.HttpClient.ReturnClass_HttpClient;

public class HandData  extends MahjongLogic implements ProcessWebData{

	private HttpClient httpClient = new HttpClient();


	//HttpClientに処理を委譲
	private ReturnClass_HttpClient getHttpData(){
		return(httpClient.getData());
	}
	public void setUrl(String aUrl) {
		httpClient.setUrl(aUrl);
	}
	public void setCookie(String aCookie) {
		httpClient.setCookie(aCookie);
	}

	@SuppressWarnings("unchecked")
	public void getWebData(String loginId,String loginPassword,String drmlUrl){

		this.loginId = loginId;
		this.loginPassword = loginPassword;
		this.drmlUrl = drmlUrl;

		Boolean firstAccess = true;
		String jsessionid = null;

		//各ユーザの1局の成績(点数)の処理

		//まず、未取得のhandid・walletidの組み合わせをArrayListで取得する
		//(XXXX-XXXXX)の形式
		ArrayList<String[]> workIdList = getTargetHandIdAndWalletdID();

		//取得したArrayListをループで順に処理
		Iterator<String[]> it = workIdList.iterator();
		while(it.hasNext()){
//			String id2_3 = it.next().toString();
//			//【-】でsplit
//			String Line[] = id2_3.split("-",0);
//			String handid = Line[0];
//			String walletid = Line[1];
			String handid = it.next()[0].toString();
			String walletid = it.next()[1].toString();


			//処理対象明細のハンド一覧画面URL作成
			StringBuffer handUrl = makeHandListUrl(firstAccess, jsessionid, handid, walletid);

			//URLをセット
			setUrl( handUrl.toString() );

			//レスポンスを取得
			HttpClient.ReturnClass_HttpClient HResp = getHttpData();

			for ( int i = 0; i < HResp.Body.size(); ++i ) {
				Matcher matcher_handid = Pattern.compile("(handinfo.html\\?handId=[0-9]+)").matcher(HResp.Body.get(i));
				//一番上の行の個別画面リンクをとる(最終局ということ)
				if ( matcher_handid.find() ) {
					handUrl.setLength(0);
					handUrl.append(drmlUrl);
					handUrl.append(matcher_handid.group(1));
					//					System.out.println( matcher_handid.group(1) );
					break;
				}
			}

			//クッキー処理
			if (HResp.cookie != null) {
				//レスポンスヘッダに SET Cookieがあった場合(JSESSIONIDのみ対象)
				Matcher matcher_jsessionid = Pattern.compile("JSESSIONID=([^;]+);").matcher(HResp.cookie);
				if (matcher_jsessionid.find()) {
					//クッキーをセット
					//System.out.println(matcher_jsessionid.group(1));
					setCookie(matcher_jsessionid.group(1));
					jsessionid = matcher_jsessionid.group(1);
				}
			}

			setUrl( handUrl.toString() );
			HResp = getHttpData();

			//解析用クラスのインスタンスを作成
			HandDataParse hdp = new HandDataParse();

			//解析用クラスの解析メソッドの実行
			HandDataSet hds =  hdp.parse(HResp.Body);

			//ユーザデータを名でソート
			//SQLiteにrow_number() pertition by がなく、横展開用のIDを振りたい、また、
			//コンビ打ちチェック用に決まった並びにしておきたい。
			//
			Comparator cp = new Comparator() {
			     public int compare(Object obj0, Object obj1) {
			         String[] strA = ( String[] ) obj0;
			         String[] strB = ( String[] ) obj1;

			    	 int ret = 0;
			         // 文字列の昇順
			         ret = strA[0].compareTo(strB[0]);
			         return ret;
			     }
			 };

			 List<String[]> userData = hds.getUserDataList();
			 Collections.sort(userData,cp);

			 hds.setUserDataList(userData);

			//データベース処理クラスのインスタンス作成

			HandDataDatabase hddb = new HandDataDatabase();
			//インサートメソッドの実行
			hddb.insert(hds,handid,walletid);


		}
	}

	/**
	 * @param firstAccess
	 * @param jsessionid
	 * @param handid
	 * @param walletid
	 * @return
	 */
	private StringBuffer makeHandListUrl(Boolean firstAccess, String jsessionid,
			String handid, String walletid) {
		StringBuffer handUrl = new StringBuffer();
		handUrl.append(drmlUrl);
		handUrl.append("show_multihand.html?handid=");
		handUrl.append(handid);
		handUrl.append("&walletid=");
		handUrl.append(walletid);
		if (firstAccess){
			handUrl.append("&userName=");
			handUrl.append(loginId);
			handUrl.append("&password=");
			handUrl.append(loginPassword);
		} else {
			setCookie(jsessionid);
		}
		return handUrl;
	}




static  ArrayList<String[]> getTargetHandIdAndWalletdID (){
		Connection connection = null;
		ResultSet results = null;
		ArrayList<String[]> HandIdAndWalletdID = new ArrayList<String[]>();
		try {
			//BasicDataSourceクラスのインスタンス作成
			BasicDataSource bds = DataSource.getInstance().getBds();
			//データソースからConnectionnoの取得
			connection = bds.getConnection();
		} catch (SQLException e) {
			System.err.println(e);
		}

		String SQLString = new String (
				"SELECT HandId,WalletdId FROM SCORE WHERE HandId NOT IN (SELECT HandId FROM HandInfo);;"
				);
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(SQLString);
			while (results.next()) {
				String[] Line = {"",""};
				Line[0] = results.getString(1);
				Line[1] = results.getString(2);
				HandIdAndWalletdID.add(Line.clone());
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
		return(HandIdAndWalletdID);

	}


}
