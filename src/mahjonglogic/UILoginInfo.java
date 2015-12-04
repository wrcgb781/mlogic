package mahjonglogic;

import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UILoginInfo {

	public JFrame frame;
	public JTextField loginIdField;
	public JPasswordField passwordField;
	public JCheckBox saveLoginId;
	public JButton loginButton;
	public JButton cancelButton;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UILoginInfo window = new UILoginInfo();
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
	public UILoginInfo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 296, 252);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

		
		JLabel taitltLabel = new JLabel("ログイン情報入力");
		taitltLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		taitltLabel.setBounds(12, 10, 126, 19);
		frame.getContentPane().add(taitltLabel);
		
		JLabel usernameLabel = new JLabel("ユーザ名");
		usernameLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		usernameLabel.setBounds(12, 52, 97, 13);
		frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("パスワード");
		passwordLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		passwordLabel.setBounds(12, 85, 97, 13);
		frame.getContentPane().add(passwordLabel);
		
		loginIdField = new JTextField();
		loginIdField.setBounds(83, 49, 96, 19);
		frame.getContentPane().add(loginIdField);
		loginIdField.setColumns(10);
		
		JButton loginButton = new JButton("ログイン");
		loginButton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		loginButton.setBounds(12, 158, 91, 21);
		frame.getContentPane().add(loginButton);
		
		JButton cancelBuuton = new JButton("キャンセル");
		cancelBuuton.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		cancelBuuton.setBounds(134, 158, 91, 21);
		frame.getContentPane().add(cancelBuuton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(83, 82, 96, 19);
		frame.getContentPane().add(passwordField);
		
		saveLoginId = new JCheckBox("ユーザ名を保存する");
		saveLoginId.setSelected(false);
		saveLoginId.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
		saveLoginId.setBounds(12, 118, 167, 21);
		frame.getContentPane().add(saveLoginId);
		
		URL url = this.getClass().getResource("img/icon.png");
		System.out.println(url);
	    ImageIcon icon = new ImageIcon(url);
	    frame.setIconImage(icon.getImage());

	}
}
