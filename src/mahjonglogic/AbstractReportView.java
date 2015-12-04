package mahjonglogic;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class AbstractReportView {
	protected Object[][] tableData;
	public JFrame frame;
	public JTable reportTable;
	public JScrollPane scrollPane;
	public JButton closeButton;

	abstract protected void initialize(Object[][] atableData);



}
