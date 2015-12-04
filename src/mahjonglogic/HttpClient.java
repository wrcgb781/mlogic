package mahjonglogic;

	/****************************************************
	クラス名：HttpClient 

	概要：セットされたURLへアクセスし、ソース・ヘッダを戻す

		・メソッド
		setUrl(String): 接続先URLを設定
		setCookie(String): クッキーを設定(JSESSIONIDのみ)
		getData():対象サイトのソースを取得


		・フィールド
		url： (URL) 接続先URL 
		cookie： (String) JSESSONID
		
		・ReturnClass_HttpClient
		戻り用クラス
			cookie： ヘッダ内でSet-Cookieで指定されている文字列
			Body： LIST) ソース
			Header： 連想配列) ヘッダの属性、 値リスト

		・オーバーライド
		toString()
		hashCode()
		equals()


	*****************************************************/


	import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

	public class HttpClient {

		private URL url ;
		private String cookie ;

		public void setUrl(String aUrl) {
			try{
				  url = new URL(aUrl);
				}catch(MalformedURLException e){
				  e.printStackTrace();
				}
		}
		public void setCookie(String aCookie) {
			cookie = aCookie;
		}
		
		
		/* (非 Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "HttpClient [url=" + url + ", cookie=" + cookie + "]";
		}


		/* (非 Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			return result;
		}
		/* (非 Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HttpClient other = (HttpClient) obj;
			if (cookie == null) {
				if (other.cookie != null)
					return false;
			} else if (!cookie.equals(other.cookie))
				return false;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			return true;
		}


		//戻り用クラス
		public class  ReturnClass_HttpClient {
			public String cookie;
			public List<String> Body = new ArrayList<String>();
			public HashMap<String,List<String>> Header = new HashMap<String,List<String>>();
		}

		public ReturnClass_HttpClient getData() {
			//戻り用インスタンス
			ReturnClass_HttpClient ret = new ReturnClass_HttpClient();

			String content="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<A><B>2</B></A>"; // 仮のPOST-body(XML)
			try{
				// アドレス設定、ヘッダー情報設定
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setConnectTimeout(1000 * 10);//タイムアウト 10秒 
				con.setRequestMethod("POST");	// POSTまたはGET
				con.setDoOutput(true);	     // POSTのデータを後ろに付ける
				con.setInstanceFollowRedirects(false);// 勝手にリダイレクトさせない
				con.setRequestProperty("Accept-Language", "jp");
				con.setRequestProperty("Content-Type","text/xml;charset=utf-8");
				//クッキー処理(現在はJSESSIONIDのみ)
				if (cookie != null) {
					con.setRequestProperty("Cookie","JSESSIONID=" + cookie);
				}
				con.connect();
				// 送信
				PrintWriter pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(con.getOutputStream(),"utf-8"))
						);
				pw.print(content);// content
				pw.close();       // closeで送信完了

				// body部の文字コード取得
				String contentType = con.getHeaderField("Content-Type");
				String charSet     = "Shift-JIS";//"ISO-8859-1";
				for(String elm : contentType.replace(" ", "").split(";")) {
					if (elm.startsWith("charset=")) {
						charSet = elm.substring(8);
						break;
					}
				}

				//レスポンスヘッダの処理
				getResponseHeader(ret, con);

				//クッキーは別で取り出し(ヘッダにも含まれてる)
				//	String Cookie = con.getHeaderField("Set-Cookie");
				ret.cookie = con.getHeaderField("Set-Cookie");

				// body部受信
				getBodyData(ret, con, charSet);
			}
			catch(Exception e){
				e.printStackTrace(System.err);
				System.exit(1);
			}
			return ret;

		}
		/**
		 * @param ret
		 * @param con
		 * @param charSet
		 * @throws IOException
		 * @throws UnsupportedEncodingException
		 */
		private void getBodyData(ReturnClass_HttpClient ret, HttpURLConnection con, String charSet)
				throws IOException, UnsupportedEncodingException {
			BufferedReader br;
			try{
				br = new BufferedReader(new InputStreamReader(
						con.getInputStream(),charSet));
			}
			catch(Exception e_){
				System.out.println( con.getResponseCode()
						+" "+ con.getResponseMessage() );
				br = new BufferedReader(new InputStreamReader(
						con.getErrorStream(),charSet));
			}
			String line;
			while ((line = br.readLine()) != null) {
				ret.Body.add(line);
//									System.out.println(line+"\n");
			}
			br.close();
			con.disconnect();
		}
		/**
		 * @param ret
		 * @param con
		 */
		private void getResponseHeader(ReturnClass_HttpClient ret, HttpURLConnection con) {
			Map<String,List<String>> headers = con.getHeaderFields();
			Iterator<String> it = headers.keySet().iterator();
			//			System.out.println("レスポンスヘッダ:");
			while (it.hasNext()){
				String key= (String)it.next();
				//				System.out.println("  " + key + ": " + headers.get(key));
				ret.Header.put(key,(List<String>) headers.get(key));

			}
		}

	}

