package mahjonglogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MahjongLogicController {


	private UIMain view;
	private ProcessWebData sd;
	private ProcessWebData hd;
	protected String loginId = null;
	protected String loginPassword = null;
	protected String drmlUrl = null;
	protected String saveLoginId = null;

	MahjongLogicController(){
		view = new UIMain();
		sd = new StatementData();
		hd = new HandData();

		//viewにmodelを設定


		//viewにLisnterを設定
		// bbuttonとmenuにアクションリスナーを登録
		view.getDataButton.addActionListener(getDataAction);
		view.exitButton.addActionListener(exitAction);
		view.displayReportButton.addActionListener(openReport);
		//

		//viewを表示
		view.frame.setVisible(true);

	}





	private ActionListener getDataAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			LoginInfoController lic = new LoginInfoController(view.connectSiteComboBox.getSelectedIndex());
			sd = new StatementData();
			sd.getWebData(lic.li.getLoginId(), lic.li.getLoginPassword(), lic.li.getDrmlUrl());

			hd = new HandData();
			hd.getWebData(lic.li.getLoginId(), lic.li.getLoginPassword(), lic.li.getDrmlUrl());

		};



	};
	private ActionListener exitAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				System.exit(0);
		};
	};

	//レポート表示用コントローラのインスタンス作成処理
	private ActionListener openReport = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			//Factoryクラスのインスタンス作成
			ReportControllerFactory rcf = new ReportControllerFactory();
			//Factoryクラスのメソッドでコントローラのインスタンス作成
			//listに対応したコントローラのインスタンスが出来る
			AbstractReportController rc;
			rc = rcf.getInstance(view.list.getSelectedIndex());
			rc.createView(view.list.getSelectedIndex());

		};
	};

}
