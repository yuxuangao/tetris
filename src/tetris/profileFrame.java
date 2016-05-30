package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.ComponentOrientation;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class profileFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextPane textPane;
	
	private int icon;
	private String nick;
	private int level;
	private int exp;
	private int[] skillid;
	
	private boolean isCancelled = false;
	private int chooseindex = -1;

	/**
	 * Create the dialog.
	 */
	public profileFrame(int Icon,String Nick,int Level,int Exp,int[] skillID) {
		icon = Icon;
		nick = Nick;
		level = Level;
		exp = Exp;
		skillid = skillID;
		setModal(true);
		setTitle("玩家信息");
		setResizable(false);
		setBounds(300, 200, 600, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPanel.add(tabbedPane);
		
		JPanel profilePane = new JPanel();
		tabbedPane.addTab("基本信息", null, profilePane, null);
		profilePane.setLayout(null);
		
		JLabel iconLabel = new JLabel("");
		ImageIcon imageicon = new ImageIcon(getClass().getResource(String.format("/icon/icon%03d.png",icon)));
		imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		iconLabel.setIcon(imageicon);
		iconLabel.setBounds(30, 30, 64, 64);
		profilePane.add(iconLabel);
		
		JLabel nickLabel = new JLabel("");
		nickLabel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		nickLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String temp = JOptionPane.showInputDialog(null, "输入新的用户名", "更改用户名", JOptionPane.QUESTION_MESSAGE);
				if(temp!=null&&!temp.equals("")){
					nick = temp;
					nickLabel.setText(nick);
					nickLabel.setBounds(123, 30, Math.min(nick.length()*18,300), 29);
				}
				repaint();
			}
		});
		nickLabel.setFont(new Font("News Gothic MT", Font.BOLD, 16));
		nickLabel.setBounds(123, 30, Math.min(nick.length()*18,300), 29);
		nickLabel.setText(nick);
		profilePane.add(nickLabel);
		
		JLabel levelLabel = new JLabel("");
		levelLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		levelLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		levelLabel.setBounds(103, 71, 50, 23);
		levelLabel.setText("Lv."+String.valueOf(level));
		profilePane.add(levelLabel);
		
		JProgressBar expProgress = new JProgressBar();
		expProgress.setBounds(155, 71, 180, 25);
		expProgress.setValue(exp);
		profilePane.add(expProgress);
		
		JButton iconButton = new JButton("选择头像");
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iconChooser iconchooser = new iconChooser(icon);
				iconchooser.setVisible(true);
				icon = iconchooser.getIconserial();
				iconchooser = null;
				ImageIcon imageicon = new ImageIcon(getClass().getResource(String.format("/icon/icon%03d.png",icon)));
				imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
				iconLabel.setIcon(imageicon);
			}
		});
		iconButton.setBounds(15, 106, 94, 27);
		profilePane.add(iconButton);
		
		JPanel skillpane = new JPanel();
		tabbedPane.addTab("技能设定", null, skillpane, null);
		skillpane.setLayout(null);
		
		JPanel displayPane = new JPanel();
		displayPane.setBounds(82, 15, 441, 97);
		skillpane.add(displayPane);
		displayPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
		
		JButton loadButton = new JButton("装载技能");
		Integer[] idnumber = new Integer[Skill.NUMBER+1];
		for (int i=-1;i<Skill.NUMBER;i++)
			idnumber[i+1] = i;
		JList<Integer> list = new JList<Integer>(idnumber);
		
		final skillPane skilldis0 = new skillPane(skillid[0],true);
		final skillPane skilldis1 = new skillPane(skillid[1],skillid[0]!=-1);
		final skillPane skilldis2 = new skillPane(skillid[2],skillid[1]!=-1);
		final skillPane skilldis3 = new skillPane(skillid[3],skillid[2]!=-1);
		skilldis0.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1){
					skilldis0.setClick(true);
					skilldis1.setClick(false);
					skilldis2.setClick(false);
					skilldis3.setClick(false);
					list.setSelectedIndex(skilldis0.skillid+1);
					loadButton.setEnabled(true);
					if (skilldis0.skillid==-1)
						loadButton.setEnabled(false);
					chooseindex = 0;
				}
			}
		});
		skilldis1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (skilldis1.isEnabled&&e.getButton()==MouseEvent.BUTTON1){
					skilldis0.setClick(false);
					skilldis1.setClick(true);
					skilldis2.setClick(false);
					skilldis3.setClick(false);
					list.setSelectedIndex(skilldis1.skillid+1);
					loadButton.setEnabled(true);
					if (skilldis1.skillid==-1)
						loadButton.setEnabled(false);
					chooseindex = 1;
				}
			}
		});
		skilldis2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (skilldis2.isEnabled&&e.getButton()==MouseEvent.BUTTON1){
					skilldis0.setClick(false);
					skilldis1.setClick(false);
					skilldis2.setClick(true);
					skilldis3.setClick(false);
					list.setSelectedIndex(skilldis2.skillid+1);
					loadButton.setEnabled(true);
					if (skilldis2.skillid==-1)
						loadButton.setEnabled(false);
					chooseindex = 2;
				}
			}
		});
		skilldis3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (skilldis3.isEnabled&&e.getButton()==MouseEvent.BUTTON1){
					skilldis0.setClick(false);
					skilldis1.setClick(false);
					skilldis2.setClick(false);
					skilldis3.setClick(true);
					list.setSelectedIndex(skilldis3.skillid+1);
					loadButton.setEnabled(true);
					if (skilldis3.skillid==-1)
						loadButton.setEnabled(false);
					chooseindex = 3;
				}
			}
		});
		
		displayPane.add(skilldis0);
		displayPane.add(skilldis1);
		displayPane.add(skilldis2);
		displayPane.add(skilldis3);
		
		
		list.setCellRenderer(new listRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				int[] sktemp = new int[4];
				sktemp[0] = skilldis0.skillid;
				sktemp[1] = skilldis1.skillid;
				sktemp[2] = skilldis2.skillid;
				sktemp[3] = skilldis3.skillid;
				if (list.getSelectedIndex()==0){
					textPane.setText("取消技能");
				}
				else{
					Skill skill = new Skill(list.getSelectedIndex()-1);
					textPane.setText(skill.getDescription());
					if (skill.getLevel()>level||chooseindex==-1){
						loadButton.setEnabled(false);
					}
					else{
						loadButton.setEnabled(true);
						for (int i=0;i<4;i++){
							if (i!=chooseindex&&sktemp[i]!=-1&&sktemp[i]==list.getSelectedIndex()-1){
								loadButton.setEnabled(false);
								break;
							}
						}
					}
				}
				if (skilldis0.skillid==-1&&list.getSelectedIndex()==0)
					loadButton.setEnabled(false);
			}
		});
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(50, 124, 270, 182);
		skillpane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("技能描述");
		lblNewLabel.setBounds(346, 145, 57, 15);
		skillpane.add(lblNewLabel);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setOpaque(false);
		textPane.setBackground(getBackground());
		textPane.setBounds(346, 172, 177, 111);
		skillpane.add(textPane);
		
		
		loadButton.setEnabled(false);
		loadButton.setBounds(415, 135, 94, 27);
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[] sktemp = new int[4];
				sktemp[0] = skilldis0.skillid;
				sktemp[1] = skilldis1.skillid;
				sktemp[2] = skilldis2.skillid;
				sktemp[3] = skilldis3.skillid;
				sktemp[chooseindex] = list.getSelectedIndex()-1;
				for (int i=0;i<3;i++){
					if (sktemp[i]==-1){
						for (int j=i;j<3;j++){
							sktemp[j] = sktemp[j+1];
						}
						sktemp[3] = -1;
					}
				}
				skilldis0.skillid=sktemp[0];
				skilldis1.skillid=sktemp[1];skilldis1.isEnabled=skilldis0.skillid!=-1;
				skilldis2.skillid=sktemp[2];skilldis2.isEnabled=skilldis1.skillid!=-1;
				skilldis3.skillid=sktemp[3];skilldis3.isEnabled=skilldis2.skillid!=-1;
				if (chooseindex!=0&&sktemp[chooseindex-1]==-1){
					chooseindex = -1;
					loadButton.setEnabled(false);
					skilldis0.setClick(false);
					skilldis1.setClick(false);
					skilldis2.setClick(false);
					skilldis3.setClick(false);
				}
				skilldis0.load();
				skilldis1.load();
				skilldis2.load();
				skilldis3.load();
				if (sktemp[0]==-1)
					loadButton.setEnabled(false);
			}
			
		});
		skillpane.add(loadButton);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("确定");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skillid[0] = skilldis0.skillid;
				skillid[1] = skilldis1.skillid;
				skillid[2] = skilldis2.skillid;
				skillid[3] = skilldis3.skillid;
				setVisible(false);
			}
		});
		okButton.setPreferredSize(new Dimension(80, 27));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCancelled = true;
				setVisible(false);
			}
		});
		cancelButton.setPreferredSize(new Dimension(80, 27));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);		
	}
	
	public String getNickname(){
		return nick;
	}
	
	public int getIconserial(){
		return icon;
	}
	
	public boolean isCancelled(){
		return isCancelled;
	}
	
	public int[] getSkillID(){
		return skillid;
	}
	
	
	private class skillPane extends JPanel{
		
		public int skillid;
		public boolean isEnabled;
		private JLabel iconlabel;
		private JLabel namelabel;
		
		public skillPane(int skillID,boolean isenabled){
			skillid = skillID;
			isEnabled = isenabled;
			this.setLayout(new BorderLayout(5,5));
			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			if (isEnabled){
				this.setBackground(new Color(240,240,220));
			}
			else{
				this.setBackground(Color.gray);
			}
			iconlabel = new JLabel();
			iconlabel.setPreferredSize(new Dimension(64,64));
			this.add(iconlabel, BorderLayout.CENTER);
			namelabel = new JLabel();
			namelabel.setPreferredSize(new Dimension(64,15));
			namelabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(namelabel, BorderLayout.SOUTH);
			
			if (skillid==-1){
				namelabel.setText("未设定技能");
			}else{
				Skill skill = new Skill(skillid);
				ImageIcon icon = new ImageIcon(getClass().getResource(String.format("/skillicon/skill%02d.png", skillid)));
				iconlabel.setIcon(icon);
				namelabel.setText(skill.getName());
			}
		}
		
		public void setClick(boolean click){
			if (isEnabled){
				this.setBorder(null);
				if (click)
					this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				else
					this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			}
		}
		
		public void load(){
			if (skillid==-1){
				namelabel.setText("未设定技能");
				iconlabel.setIcon(null);
			}else{
				Skill skill = new Skill(skillid);
				ImageIcon icon = new ImageIcon(getClass().getResource(String.format("/skillicon/skill%02d.png", skillid)));
				iconlabel.setIcon(icon);
				namelabel.setText(skill.getName());
			}
			if (isEnabled){
				this.setBackground(new Color(240,240,220));
			}
			else{
				this.setBackground(Color.gray);
			}
		}
	}
	
	private class listRenderer extends JPanel implements ListCellRenderer<Object>{
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			int id = Integer.parseInt(value.toString());
			this.setLayout(new FlowLayout(FlowLayout.LEFT,5,10));
			JLabel iconlabel = new JLabel();
			iconlabel.setPreferredSize(new Dimension(64,64));
			JLabel namelabel = new JLabel();
			JLabel levellabel = new JLabel();
			if (isSelected){
				this.setBackground(new Color(20,20,128));
				namelabel.setForeground(Color.white);
				levellabel.setForeground(Color.white);
			}
			else{
				this.setBackground(Color.white);
				namelabel.setForeground(Color.black);
				levellabel.setForeground(Color.black);
			}
			this.removeAll();
			this.add(iconlabel);
			this.add(namelabel);
			this.add(levellabel);
			
			if (id==-1){
				namelabel.setText("取消技能");
			}
			else{
				Skill skill = new Skill(id);
				namelabel.setText(skill.getName());
				levellabel.setText(String.format("%d级开始可用", skill.getLevel()));
				ImageIcon icon = new ImageIcon(getClass().getResource(String.format("/skillicon/skill%02d.png", id)));
				iconlabel.setIcon(icon);
				if (skill.getLevel()>level){
					levellabel.setForeground(Color.red);
				}
			}
			
			return this;
		}
	}
	
}
