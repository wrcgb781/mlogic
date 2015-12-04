package mahjonglogic;

import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UILoginInfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField loginIdField;
	public JPasswordField passwordField;
	public JCheckBox saveLoginId;
	public JButton okButton;
	public JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UILoginInfoDialog dialog = new UILoginInfoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UILoginInfoDialog() {
		setTitle("MajongLogic 戦績集計 ログイン情報入力");
		setModal(true);
		setBounds(100, 100, 250, 209);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 139, 239, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			JLabel taitltLabel = new JLabel("ログイン情報入力");
			taitltLabel.setBounds(12, 10, 126, 19);
			taitltLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
			 getContentPane().add(taitltLabel);

				JLabel usernameLabel = new JLabel("ユーザ名");
				usernameLabel.setBounds(12, 52, 97, 13);
				usernameLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
				getContentPane().add(usernameLabel);
				
				JLabel passwordLabel = new JLabel("パスワード");
				passwordLabel.setBounds(12, 85, 97, 13);
				passwordLabel.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
				getContentPane().add(passwordLabel);
				
				loginIdField = new JTextField();
				loginIdField.setBounds(83, 49, 96, 19);
				getContentPane().add(loginIdField);
				loginIdField.setColumns(10);
				
				passwordField = new JPasswordField();
				passwordField.setBounds(83, 82, 96, 19);
				getContentPane().add(passwordField);
				
				saveLoginId = new JCheckBox("ユーザ名を保存する");
				saveLoginId.setBounds(0, 107, 288, 26);
				saveLoginId.setSelected(false);
				saveLoginId.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 12));
				getContentPane().add(saveLoginId);

				URL url = this.getClass().getResource("img/icon.png");
			    ImageIcon icon = new ImageIcon(url);
			    setIconImage(icon.getImage());

		}
	}

}
