package mahjonglogic;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class DayReportView extends  AbstractReportView {
//	public JFrame frame;
//	public JTable reportTable;
//	public JScrollPane scrollPane;
//	public JButton closeButton;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public DayReportView() {

	}

	/**
	 * Initialize the contents of the frame.
	 * @param tableData
	 * @wbp.parser.entryPoint
	 */
	protected void initialize(Object[][] tableData) {
		frame = new JFrame();
		frame.setBounds(100, 100, 310, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("MajongLogic 戦績集計 日付集計画面");

		closeButton = new JButton("閉じる");
		closeButton.setBounds(92, 240, 93, 23);
		frame.getContentPane().add(closeButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 23, 275, 207);
		frame.getContentPane().add(scrollPane);
		reportTable = new JTable();
		reportTable.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 14));
		scrollPane.setViewportView(reportTable);
		reportTable.setModel(new DefaultTableModel(
				tableData,
				new String[] {
						"\u65E5\u4ED8", "", "\u30B9\u30B3\u30A2"
				}
				));
		reportTable.setBorder(new LineBorder(new Color(0, 0, 0)));

		//列幅
		DefaultTableColumnModel cmodel=(DefaultTableColumnModel)reportTable.getColumnModel();
		TableColumn column0 = cmodel.getColumn(0);
		TableColumn column1 = cmodel.getColumn(1);
		TableColumn column2 = cmodel.getColumn(2);
		column0.setPreferredWidth(100);
		column1.setPreferredWidth(20);
		column2.setPreferredWidth(40);

		//文字揃え
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		DefaultTableCellRenderer c = new DefaultTableCellRenderer();
		c.setHorizontalAlignment(SwingConstants.CENTER);
		r.setHorizontalAlignment(SwingConstants.RIGHT);
		column0.setCellRenderer(c);
		column1.setCellRenderer(c);
		column2.setCellRenderer(r);

		//ヘッダー
		JTableHeader jh = reportTable.getTableHeader();
		jh.setFont(new Font(jh.getFont().getFamily(),Font.PLAIN,16));

		URL url = this.getClass().getResource("img/icon.png");
	    ImageIcon icon = new ImageIcon(url);
	    frame.setIconImage(icon.getImage());

	}

}
