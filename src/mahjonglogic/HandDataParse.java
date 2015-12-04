package mahjonglogic;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandDataParse {

	private int DataLoopFlg = 0;
	private int userNumber = 0;

	HandDataParse(){
	}

	public class HandDataSet {
		private String ruleset;
		private String prevailingWind;
		private List<String[]> userDataList = new ArrayList<String[]>();
		public String getRuleset() {
			return ruleset;
		}
		public void setRuleset(String ruleset) {
			this.ruleset = ruleset;
		}
		public String getPrevailingWind() {
			return prevailingWind;
		}
		public void setPrevailingWind(String prevailingWind) {
			this.prevailingWind = prevailingWind;
		}
		public List<String[]> getUserDataList() {
			return userDataList;
		}
		public void setUserDataList(List<String[]> userDataList) {
			this.userDataList = userDataList;
		}




	}

	public HandDataSet parse (List<String> httpSource){

		HandDataSet hd = new HandDataSet();
		String[][] userData = new String[4][3];

		for ( int i = 0; i < httpSource.size(); ++i ) {

			//	System.out.println( httpSource.get(i) );

			if ( DataLoopFlg == 0) {

				//【<h3>Settlement</h3>】までは無視
				Matcher matcher_start = Pattern.compile("<h3>Settlement</h3>").matcher(httpSource.get(i));
				if ( matcher_start.find() ) {
					DataLoopFlg = 1;
				} else {
					continue;
				}
			}

//			System.out.println( httpSource.get(i) );
			//終了判定
			if ( DataLoopFlg == 1) {
				//【<div id="player">】で終了
				Matcher matcher_end = Pattern.compile("<div id=\"player").matcher(httpSource.get(i));
				if ( matcher_end.find() ) {
					//					System.out.println( httpSource.get(i) );
					break;
				}
			}

			//データ取得

			switch ( DataLoopFlg){
			case 1:
				Matcher matcher_userName = Pattern.compile("<th>(.+)</th>").matcher(httpSource.get(i));
				if ( matcher_userName.find() ) {
					userData[userNumber][0] = matcher_userName.group(1);
					userData[userNumber][2] = String.valueOf(userNumber);
					++userNumber;
				} else {
					Matcher matcher_userNameEnd = Pattern.compile("</tr>").matcher(httpSource.get(i));
					if (matcher_userNameEnd.find() ){
						DataLoopFlg = 2;
						userNumber = 0;
					}
				}
				break;
			case 2:
				Matcher matcher_scoreStart = Pattern.compile("Score\\sin\\sgame").matcher(httpSource.get(i));
				if ( matcher_scoreStart.find() ) {
					DataLoopFlg = 3;
				}
				break;

			case 3:
				Matcher matcher_score = Pattern.compile("<td>([^<]+)</td>").matcher(httpSource.get(i));
				if ( matcher_score.find() ) {
					userData[userNumber][1] = matcher_score.group(1);
					hd.userDataList.add(userData[userNumber].clone());
					++userNumber;
				} else {
					DataLoopFlg = 4;
					userNumber = 0;
				}
				break;
			case 4:
				Matcher matcher_Ruleset = Pattern.compile("Ruleset:</td><td>([^<]+)</td>").matcher(httpSource.get(i));
				if ( matcher_Ruleset.find() ) {
					DataLoopFlg = 5;
					hd.ruleset = matcher_Ruleset.group(1);
				}
				break;
			case 5:
				Matcher matcher_prevailingWind = Pattern.compile("<tr><td>Prevailing\\swind:</td><td>([^<]+)</td>").matcher(httpSource.get(i));
				if ( matcher_prevailingWind.find() ) {
					DataLoopFlg = 6;
					hd.prevailingWind = matcher_prevailingWind.group(1);
				}
				break;
			case 6:
				return (hd);
			}
		}
		return (hd);

	}

}
