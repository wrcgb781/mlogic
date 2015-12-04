package mahjonglogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractReportController {

	protected Object[][] tableData;
	AbstractReportView reportView;
	AbstractReportController(){

	}





	protected void createView(int viewType){


		//ビューのインスタンス作成
		ReportViewFactory rvf = new ReportViewFactory();
		reportView = rvf.getInstance(viewType);

		//tableData取得
		tableData = getTableData();

		//ビューの初期化
		reportView.initialize(tableData);

		// buttonにアクションリスナーを登録
		reportView.closeButton.addActionListener(closeAction);

		//ビューの表示
		reportView.frame.setVisible(true);

	}


	protected abstract Object[][] getTableData();
	private ActionListener closeAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			reportView.frame.dispose();
		};
	};

}
