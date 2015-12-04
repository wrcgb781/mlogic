package mahjonglogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginInfoController {

	public LoginInfo li;
	private UILoginInfoDialog uil;

	LoginInfoController(){
	//ログイン情報クラス
	li = new LoginInfo(1);

	//ログイン情報入力view
	uil = new UILoginInfoDialog();

	//ログイン情報入力viewにLisnterを設定
	// buttonにアクションリスナーを登録
	uil.okButton.addActionListener(getLoginInfoAction);
	uil.cancelButton.addActionListener(loginInfoCanceltAction);

	//ログイン情報入力viewに値を設定
	uil.saveLoginId.setSelected(li.getSaveLoginId());
	uil.loginIdField.setText(li.getLoginId());
	
	//表示
	uil.setVisible(true);


	}

	 private ActionListener getLoginInfoAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			char[] pass = uil.passwordField.getPassword();
			li.setLoginPassword(new String(pass));
			li.setLoginId(uil.loginIdField.getText());
			li.setSaveLoginId(uil.saveLoginId.isSelected());
			li.updateLoginInfoDB(1);
			uil.dispose();
		};
	};

	 private ActionListener loginInfoCanceltAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		};
	};

}
