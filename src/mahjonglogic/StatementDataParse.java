package mahjonglogic;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StatementDataParse {

	private String nextUrl = null;

	public String getNextUrl() {
		return nextUrl;
	}



	public ArrayList<String[]> parse (HttpClient.ReturnClass_HttpClient statementSource){

		ArrayList<String[]> statementData = new ArrayList<String[]>();
		int dataLoopFlg = 0;
		String[] Line = new String[7];

		//ソースを1行ずつ読み、取得対象データを配列に格納する
		for ( int i = 0; i < statementSource.Body.size(); ++i ) {
			//カラムチェック
			if ( dataLoopFlg == 0) {
				//次ページチェック
				Matcher matcher_nexturl = Pattern.compile("(statement.html\\?page=[0-9]+)\">&gt;&gt;&gt;").matcher(statementSource.Body.get(i));
				if ( matcher_nexturl.find() ) {
					nextUrl = matcher_nexturl.group(1);
				}

				//データテーブルのカラムの行までは無視
				Matcher matcher_start = Pattern.compile("<td>Balance</td>").matcher(statementSource.Body.get(i));
				if ( matcher_start.find() ) {
					dataLoopFlg = 1;
				} else {
					continue;
				}
			}


			//終了判定
			if ( dataLoopFlg == 1) {
				Matcher matcher_end = Pattern.compile("</tbody>").matcher(statementSource.Body.get(i));
				if ( matcher_end.find() ) {
					System.out.println( statementSource.Body.get(i) );
					break;
				}
			}

			//データ取得

			switch ( dataLoopFlg){
			case 1:
				Matcher matcher_LoopStart = Pattern.compile("<tr class").matcher(statementSource.Body.get(i));
				if ( matcher_LoopStart.find() ) {
					dataLoopFlg = 2;
				}
				break;
			case 2:
				Matcher matcher_Id1 = Pattern.compile("<td>([0-9]{8})</td>").matcher(statementSource.Body.get(i));
				if ( matcher_Id1.find() ) {
					dataLoopFlg = 3;
					Line[0] = matcher_Id1.group(1);
				}
				break;
			case 3:
				Matcher matcher_Date = Pattern.compile("<td>([^\\s]+\\s[^\\s]+)\\sUTC</td>").matcher(statementSource.Body.get(i));
				if ( matcher_Date.find() ) {
					dataLoopFlg = 4;
					//時刻の処理
					Calendar cal1 = Calendar.getInstance();
					Matcher matcher_Date1 = Pattern.compile("([0-9]{4})-([0-9][0-9])-([0-9][0-9])\\s([0-9][0-9]):([0-9][0-9])").matcher((matcher_Date.group(1)));
					if ( matcher_Date1.find() ) {
						cal1.set(Integer.parseInt(matcher_Date1.group(1)),
						// 0-11 なので 1を引く
						Integer.parseInt(matcher_Date1.group(2)) -1,
						Integer.parseInt(matcher_Date1.group(3)),
						Integer.parseInt(matcher_Date1.group(4)),
						Integer.parseInt(matcher_Date1.group(5)),
						0
						);
					}
					cal1.add(Calendar.HOUR_OF_DAY, 9);
					SimpleDateFormat formatA = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Line[1] = formatA.format(cal1.getTime());
				}
				break;
			case 4:
				Matcher matcher_Kubun = Pattern.compile("<td>([^\\s]+)\\sGame").matcher(statementSource.Body.get(i));
				Matcher matcher_deposit = Pattern.compile("<td>(.*Deposit.*)").matcher(statementSource.Body.get(i));
				Matcher matcher_bonus = Pattern.compile("Bonus Payment").matcher(statementSource.Body.get(i));
				Matcher matcher_KubunEnd = Pattern.compile("</tr>").matcher(statementSource.Body.get(i));
				
				if ( matcher_Kubun.find() ) {
					//通常のレコード
					dataLoopFlg = 5;
					if ( matcher_Kubun.group(1).equals("To") ) {
						Line[2] = "1";
					} else {
						Line[2] = "2";
					}
				} else if (matcher_deposit.find()){
					//入金のレコードの場合
					if(matcher_deposit.group(1).endsWith("(Declined)")){
						//拒否
						dataLoopFlg = 6;
						Line[2] = "3";
						Line[3] = "0000000";
						Line[4] = "1111111";
						
						
					}else if(matcher_deposit.group(1).endsWith("Deposit")){
						//許可
						dataLoopFlg = 6;
						Line[2] = "3";
						Line[3] = "0000000";
						Line[4] = "0000000";
						
					} else {
						//その他
						dataLoopFlg = 6;
						Line[2] = "3";
						Line[3] = "9999999";
						Line[4] = "9999999";
						
					}
					
					
					
				} else if  (matcher_bonus.find()){
					//DORAボーナスのレコードの場合
					dataLoopFlg = 6;
					Line[2] = "4";
					Line[3] = "0000000";
					Line[4] = "0000000";
				} else if (matcher_KubunEnd.find()){
					dataLoopFlg = 6;
					Line[2] = "9";
					Line[3] = "9999999";
					Line[4] = "9999999";
					
				}
			
				break;
			case 5:
				Matcher matcher_Id2_3 = Pattern.compile("handid=([0-9]+)&walletid=([0-9]+)").matcher(statementSource.Body.get(i));
				Matcher matcher_tonament = Pattern.compile("tournamentId=([0-9]+)").matcher(statementSource.Body.get(i));

				if ( matcher_Id2_3.find() ) {
					dataLoopFlg = 6;
					Line[3] = matcher_Id2_3.group(1);
					Line[4] = matcher_Id2_3.group(2);
				} else if ( matcher_tonament.find() ) {
					dataLoopFlg = 6;
					Line[3] = String.format("%7s",matcher_tonament.group(1)).replace(" ","0");
					Line[4] = "0000000";
				}

				break;
			case 6:
				Matcher matcher_Money = Pattern.compile("\\$(.+)").matcher(statementSource.Body.get(i));
				if ( matcher_Money.find() ) {
					dataLoopFlg = 7;
					Line[5] = matcher_Money.group(1);
				}
				break;
			case 7:
				Matcher matcher_PossessionMoney = Pattern.compile("\\$([^<]+)<").matcher(statementSource.Body.get(i));
				if ( matcher_PossessionMoney.find() ) {
					dataLoopFlg = 10;
					Line[6] = matcher_PossessionMoney.group(1);
				}
				break;


			case 10:
				Matcher matcher_LoopEnd = Pattern.compile("</tr>").matcher(statementSource.Body.get(i));
				if ( matcher_LoopEnd.find() ) {
					dataLoopFlg = 1;
					statementData.add(Line.clone());
					for ( int j = 0;j < Line.length;++j){
						Line[j] = null;
					}
				}
				break;

			}
		}

		return(statementData);

	}

}

