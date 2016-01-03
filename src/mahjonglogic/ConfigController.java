package mahjonglogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ConfigController {

	private ConfigView cv;
	private Config c;
	private HashMap<String,String> mahjonglogicConfig; //設定値配列

	ConfigController(){

		//設定ファイの内容を取得
		c = new Config();
		c.getFileMahjonglogicConfig();
		mahjonglogicConfig = c.getMahjonglogicConfig();
		//設定ビューのインスタンス作成。DBパスを渡す
		cv = new ConfigView(mahjonglogicConfig.get("databasePath"));
		cv.frame.setVisible(true);
		//設定ビューのアクションリスナーを登録
		cv.databasePathButton.addActionListener(databasePathButtonAction);
		cv.exitButton.addActionListener(exitButtonAction);

	}

	 private ActionListener databasePathButtonAction = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			//テキストフィールドの文字列を取得
			mahjonglogicConfig.put("databasePath",cv.databasePathTextField.getText());

			//ConfigオブジェクトのmahjonglogicConfigフィールドを更新
			c.setMahjonglogicConfig(mahjonglogicConfig);
			
			//設定値をファイルへ書き込み
			c.setFileMahjonglogicConfig();
		}

	};
	 private ActionListener exitButtonAction = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			//テキストフィールドの文字列を取得
			cv.frame.dispose();
		}

	};



}
