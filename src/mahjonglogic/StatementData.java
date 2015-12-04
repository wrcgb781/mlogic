package mahjonglogic;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatementData extends MahjongLogic implements ProcessWebData{

	StatementData() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private String nextStatementUrl;
	private String jsessionid;
	private HttpClient httpClient = new HttpClient();

	private HttpClient.ReturnClass_HttpClient getHttpData(){
		return(httpClient.getData());
	}

	public void setUrl(String aUrl) {
		httpClient.setUrl(aUrl);
	}
	public void setCookie(String aCookie) {
		httpClient.setCookie(aCookie);
	}


	public void setnextStatementUrl(String xnextStatementUrl){
		nextStatementUrl = xnextStatementUrl;
	}

	public void getWebData(String loginId,String loginPassword,String drmlUrl) {


		//ログイン用のURL文字列を作成
		StringBuffer nextStatementUrlBf = new StringBuffer();
		nextStatementUrlBf.append("statement.html?page=1");
		nextStatementUrlBf.append("&userName=");
		nextStatementUrlBf.append(loginId);
		nextStatementUrlBf.append("&password=");
		nextStatementUrlBf.append(loginPassword);
		nextStatementUrl = nextStatementUrlBf.toString();

		//データ取得･データベース書き込み。
		//次ページURLがある間は繰り返す。
		//一意性違反(書き込み済みデータがあった)時は抜ける
		while(nextStatementUrl != null){
			StringBuffer requestUrl = new StringBuffer();
			requestUrl.append(drmlUrl);
			requestUrl.append(nextStatementUrl);
			setUrl(requestUrl.toString());
			if(jsessionid != null){
				setCookie(jsessionid);
			}
			HttpClient.ReturnClass_HttpClient HResp = getHttpData();
			ArrayList<String[]> statementData = new ArrayList<String[]>();

			//取得したHTTPソースを整形
			StatementDataParse sdp = new StatementDataParse();
			statementData = sdp.parse(HResp);
			nextStatementUrl = sdp.getNextUrl();

			//データベース処理
			StatementDataDatabase sdd = new StatementDataDatabase ();
			sdd.insert(statementData);
			//書き込み終了フラグがtrueなら、ループを抜ける
			if (sdd.getdataWriteOver()){
				break;
			}

			//クッキー処理
			if (HResp.cookie != null) {
				//レスポンスヘッダに SET Cookieがあった場合
				Matcher matcher_jsessionid = Pattern.compile("JSESSIONID=([^;]+);").matcher(HResp.cookie);
				if (matcher_jsessionid.find()) {
					//クッキーをセット
					//System.out.println(matcher_jsessionid.group(1));
					jsessionid = matcher_jsessionid.group(1);
				}
			}

		}

	}


}
