package tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class registFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nickText;
	
	private int icon=0;
	
	private boolean iscancelled = true;

	/**
	 * Create the dialog.
	 */
	public registFrame() {
		setModal(true);
		setBounds(300, 300, 300, 150);
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		CardLayout card = new CardLayout(0,0);
		contentPanel.setLayout(card);
		
		JButton okButton = new JButton("继续");
		
		JPanel nickPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) nickPane.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(15);
		contentPanel.add(nickPane, "nickPane");
		
		JLabel nickLabel = new JLabel("请输入用户名");
		nickPane.add(nickLabel);
		

		nickText = new JTextField();
		nickText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (nickText.getText().equals(""))
					okButton.setEnabled(false);
				else
					okButton.setEnabled(true);
			}
		});
		nickPane.add(nickText);
		nickText.setColumns(20);
		
		JPanel iconPane = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) iconPane.getLayout();
		flowLayout_1.setHgap(20);
		flowLayout_1.setVgap(10);
		contentPanel.add(iconPane, "iconPane");
		
		ImageIcon imageicon = new ImageIcon(getClass().getResource("/icon/icon000.png"));
		imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		JLabel iconLabel = new JLabel(imageicon);
		iconPane.add(iconLabel);
		
		JButton iconButton = new JButton("选择头像");
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iconChooser iconchooser = new iconChooser(icon);
				iconchooser.setVisible(true);
				icon = iconchooser.getIconserial();
				ImageIcon imageicon = new ImageIcon(getClass().getResource(String.format("/icon/icon%03d.png", icon)));
				imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				iconLabel.setIcon(imageicon);
				iconchooser = null;
			}
		});
		iconPane.add(iconButton);
		
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (iconPane.isVisible()){
					iscancelled = false;
					setVisible(false);
				}
				else{
					card.next(contentPanel);
					if (iconPane.isVisible())
						okButton.setText("完成");
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
	}
	
	public String getNickname(){
		return nickText.getText();
	}
	
	public int getIconserial(){
		return icon;
	}
	
	public boolean isCancelled(){
		return iscancelled;
	}

}
