package mahjonglogic;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConfigView {

	public JFrame frame;
	public JTextField databasePathTextField;
	public JButton databasePathButton;
	public JButton exitButton;
	private JLabel lblcfoldermlogicdb;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ConfigView(String databasePath) {
		initialize(databasePath);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String databasePath) {
		frame = new JFrame();
		frame.setBounds(100, 100, 269, 291);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel databasePathLabel = new JLabel("DBファイルのパス");
		databasePathLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		databasePathLabel.setBounds(8, 10, 92, 13);
		frame.getContentPane().add(databasePathLabel);

		databasePathTextField = new JTextField();
		databasePathTextField.setText(databasePath);
		databasePathTextField.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		databasePathTextField.setBounds(8, 47, 224, 19);
		frame.getContentPane().add(databasePathTextField);
		databasePathTextField.setColumns(10);

		databasePathButton = new JButton("設定");
		databasePathButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		databasePathButton.setBounds(156, 76, 93, 23);
		frame.getContentPane().add(databasePathButton);
		
		JLabel msgLbl1 = new JLabel("上記パスにDBファイルが無い場合は");
		msgLbl1.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		msgLbl1.setBounds(12, 151, 237, 29);
		frame.getContentPane().add(msgLbl1);
		
		JLabel label = new JLabel("初期化をすると作成されます");
		label.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		label.setBounds(12, 172, 237, 29);
		frame.getContentPane().add(label);
		
		exitButton = new JButton("閉じる");
		exitButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		exitButton.setBounds(156, 211, 93, 23);
		frame.getContentPane().add(exitButton);
		
		lblcfoldermlogicdb = new JLabel("例：「c:/FOLDER/mlogic.db」");
		lblcfoldermlogicdb.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		lblcfoldermlogicdb.setBounds(12, 113, 192, 13);
		frame.getContentPane().add(lblcfoldermlogicdb);
		
		//アイコン
		URL url = this.getClass().getResource("img/icon.png");
	    ImageIcon icon = new ImageIcon(url);
	    frame.setIconImage(icon.getImage());

	
	}
}
