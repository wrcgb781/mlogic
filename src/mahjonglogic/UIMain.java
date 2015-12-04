package mahjonglogic;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

public class UIMain {

	public JFrame frame;
	public JButton getDataButton;
	public JButton exitButton;
	public JButton displayReportButton;
	public JList<Object> list;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIMain window = new UIMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("MajongLogic 戦績集計　メイン画面");
		frame.setBounds(100, 100, 289, 344);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//UIテーマの変更
		String winTheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(winTheme);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		JLabel titleLabel = new JLabel("MajongLogic 戦績集計 Ver1.00");
		titleLabel.setBounds(12, 13, 192, 27);
		frame.getContentPane().add(titleLabel);

		getDataButton = new JButton("データ取得");
		getDataButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		getDataButton.setBounds(72, 62, 106, 27);
		frame.getContentPane().add(getDataButton);

		displayReportButton = new JButton("集計表示");
		displayReportButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		displayReportButton.setBounds(147, 190, 106, 27);
		frame.getContentPane().add(displayReportButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 117, 120, 100);
		frame.getContentPane().add(scrollPane);
		
				list = new JList<Object>();
				scrollPane.setViewportView(list);
				list.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
				list.setModel(new AbstractListModel<Object>() {
					String[] values = new String[] {"日付集計", "曜日集計", "時間帯集計", "対戦者集計"};
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
				list.setSelectedIndex(0);
				list.setBorder(new LineBorder(new Color(0, 0, 0)));

		exitButton = new JButton("終了");
		exitButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		exitButton.setBounds(72, 250, 106, 27);
		frame.getContentPane().add(exitButton);

		URL url = this.getClass().getResource("img/icon.png");
		System.out.println(url);
	    ImageIcon icon = new ImageIcon(url);
	    frame.setIconImage(icon.getImage());


	}
}
