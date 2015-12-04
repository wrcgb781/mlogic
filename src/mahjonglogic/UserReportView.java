package mahjonglogic;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class UserReportView extends  AbstractReportView {

	public JButton userFilterButton;
	public JButton clearFilterButton;
	public JLabel userFilterLabel;
	public JLabel borderFilterLabel;
	public JTextField userFilterText;
	public JTable reportTable;
	public UserReportView() {
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	protected void initialize(Object[][] atableData) {
		tableData = atableData;
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("MajongLogic 戦績集計 対戦者集計画面");

		borderFilterLabel = new JLabel("");
		EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.black);
//		LineBorder border = new LineBorder(Color.BLACK, 1, true);
		borderFilterLabel.setBorder(border);
		borderFilterLabel.setBounds(25, 15, 330, 60);
		frame.getContentPane().add(borderFilterLabel);

		userFilterButton = new JButton("フィルタ");
		userFilterButton.setBounds(180, 45, 80, 23);
		frame.getContentPane().add(userFilterButton);

		clearFilterButton = new JButton("クリア");
		clearFilterButton.setBounds(265, 45, 80, 23);
		frame.getContentPane().add(clearFilterButton);

		userFilterText = new JTextField("");
		userFilterText.setBounds(30, 45, 93, 23);
		frame.getContentPane().add(userFilterText);

		userFilterLabel = new JLabel("ユーザ名フィルタ");
		userFilterLabel.setBounds(30, 20,130, 23);
		frame.getContentPane().add(userFilterLabel);



		closeButton = new JButton("閉じる");
		closeButton.setBounds(130, 535, 93, 23);
		frame.getContentPane().add(closeButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 110, 370, 400);
		frame.getContentPane().add(scrollPane);

		//テーブル関連処理
		createTable();

		URL url = this.getClass().getResource("img/icon.png");
		ImageIcon icon = new ImageIcon(url);
		frame.setIconImage(icon.getImage());
	}

	public void createTable() {
		DefaultTableModel tm = new DefaultTableModel(
				tableData,
				new String[] {
						"ユーザ名", "対戦数", "合計点数" ,"平均点数"
				}
				);
		//ソート関連
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tm);
		sorter.setComparator(1, new Comparator<String>() {
			public int compare(String a, String b) {
				return Integer.parseInt(a) - Integer.parseInt(b);
			}
		});
		sorter.setComparator(2, new Comparator<String>() {
			public int compare(String a, String b) {
				return Integer.parseInt(a) - Integer.parseInt(b);
			}
		});
		sorter.setComparator(3, new Comparator<String>() {
			public int compare(String a, String b) {
				return Integer.parseInt(a) - Integer.parseInt(b);
			}
		});
		reportTable = new JTable();
		reportTable.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 14));
		reportTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(reportTable);
		reportTable.setModel(tm);
		reportTable.setRowSorter(sorter);

		//列幅
		DefaultTableColumnModel cmodel=(DefaultTableColumnModel)reportTable.getColumnModel();
		TableColumn column0 = cmodel.getColumn(0);
		TableColumn column1 = cmodel.getColumn(1);
		TableColumn column2 = cmodel.getColumn(2);
		TableColumn column3 = cmodel.getColumn(3);
		column0.setPreferredWidth(100);
		column1.setPreferredWidth(30);
		column2.setPreferredWidth(60);
		column3.setPreferredWidth(60);

		//文字揃え
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		DefaultTableCellRenderer c = new DefaultTableCellRenderer();
		c.setHorizontalAlignment(SwingConstants.CENTER);
		r.setHorizontalAlignment(SwingConstants.RIGHT);
		column0.setCellRenderer(c);
		column1.setCellRenderer(c);
		column2.setCellRenderer(r);
		column3.setCellRenderer(r);

		//ヘッダー
		JTableHeader jh = reportTable.getTableHeader();
		jh.setFont(new Font(jh.getFont().getFamily(),Font.PLAIN,14));
	}

}
