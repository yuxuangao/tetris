package tetris;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.JProgressBar;

public class mainTetris extends JFrame {

	private final JPanel contentPane;
	private ScoreBoard scoreboard = new ScoreBoard();
	private ScoreBoard highscoreboard = new ScoreBoard();
	private final JPanel buttonPanel = new JPanel();
	private final JButton startButton = new JButton("开始");
	private final JButton pauseButton = new JButton("暂停");
	private final JButton stopButton = new JButton("停止");
	private final JButton profileButton = new JButton("玩家信息");
	private final JButton configButton = new JButton("设置");
	private final JButton exitButton = new JButton("退出");

	private final GamePad gamepad = new GamePad(20,15,25);
	private long timegap = 500;
	private int difficulty = 50;
	private int highscore = 0;
	
	private static String nick;
	private static int icon;
	private int level;
	private int exp;
	private boolean ispause = false;
	private boolean stopflag = false;
	private ChangeListener changelistener = new ChangeListener();
	
	private static Savedata save;
	private final JLabel iconLabel = new JLabel("");
	private final JLabel nickLabel = new JLabel("");
	private final JLabel levelLabel = new JLabel("");
	private JProgressBar expProgress = new JProgressBar();
	
	private int[] skillid;
	private int exptimes;
	private int timegapd;
	private int difficultyd;
	
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					save = new Savedata();
					if (save.IsNew()){
						registFrame regist = new registFrame();
						regist.setVisible(true);
						if (regist.isCancelled()==true)
							System.exit(0);
						nick = regist.getNickname();
						icon = regist.getIconserial();
						save.createNewFile();
						save.setNickname(nick);
						save.setIconserial(icon);
						save.writeSave();
					}
					mainTetris frame = new mainTetris();
					frame.setVisible(true);
				}
				catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				catch (InstantiationException e1) {
					e1.printStackTrace();
				}
				catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				catch (UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "无法创建或读取存档文件", "存档错误", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainTetris() {
		addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (gamepad.isStart&!ispause)
					e.getComponent().requestFocus();
			}
		});
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:	if(gamepad.godownPermission)pressup();break;
				case KeyEvent.VK_DOWN:	if(gamepad.godownPermission)pressdown();break;
				case KeyEvent.VK_LEFT:	pressleft();break;
				case KeyEvent.VK_RIGHT:	pressright();break;
				}
				contentPane.repaint();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		setTitle("TETRIS");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		gamepad.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		gamepad.setLocation(new Point(25,50));
		contentPane.add(gamepad);
		
		buttonPanel.setBounds(345, 260, 132, 250);
		
		contentPane.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				profileButton.setEnabled(false);
				configButton.setEnabled(false);
				pauseButton.setEnabled(true);
				stopButton.setEnabled(true);
				gamestart();
			}
		});
		startButton.setPreferredSize(new Dimension(120, 29));
		buttonPanel.add(startButton);
		pauseButton.setEnabled(false);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ispause){
					ispause = false;
					requestFocus();
				}
				else{
					ispause = true;
					transferFocus();
				}
			}
		});
		pauseButton.setPreferredSize(new Dimension(120, 29));
		buttonPanel.add(pauseButton);
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseButton.setEnabled(false);
				stopButton.setEnabled(false);
				gamestop();
			}
		});
		stopButton.setPreferredSize(new Dimension(120, 29));
		buttonPanel.add(stopButton);
		profileButton.setPreferredSize(new Dimension(120,29));
		profileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				profileFrame profileframe = new profileFrame(icon,nick,level,exp,skillid);
				profileframe.setVisible(true);
				if (!profileframe.isCancelled()){
					nick = profileframe.getNickname();
					icon = profileframe.getIconserial();
					skillid = profileframe.getSkillID();
					skill(skillid);
					nickLabel.setText(nick);
					ImageIcon imageicon = new ImageIcon(getClass().getResource(String.format("/icon/icon%03d.png",icon)));
					imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
					iconLabel.setIcon(imageicon);
					save.setNickname(nick);
					save.setIconserial(icon);
					save.setSkillID(skillid);
					try {
						save.writeSave();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				profileframe = null;
			}
		});
		buttonPanel.add(profileButton);
		configButton.setPreferredSize(new Dimension(120, 29));
		buttonPanel.add(configButton);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setPreferredSize(new Dimension(120, 29));
		
		buttonPanel.add(exitButton);
		
		
		scoreboard.setLocation(new Point(345, 100));
		contentPane.add(scoreboard);
		highscoreboard.setLocation(new Point(345, 190));
		highscore = save.getHighscore();
		highscoreboard.setScore(highscore);
		contentPane.add(highscoreboard);
		
		JLabel scoreLabel = new JLabel("SCORE");
		scoreLabel.setBounds(345, 80, 57, 15);
		contentPane.add(scoreLabel);
		
		JLabel highscoreLabel = new JLabel("HIGH SCORE");
		highscoreLabel.setBounds(345, 170, 76, 15);
		contentPane.add(highscoreLabel);
		
		iconLabel.setBounds(430, 6, 64, 64);
		icon = save.getIconserial();
		ImageIcon imageicon = new ImageIcon(getClass().getResource(String.format("/icon/icon%03d.png",icon)));
		imageicon.setImage(imageicon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		iconLabel.setIcon(imageicon);
		contentPane.add(iconLabel);
		nickLabel.setFont(new Font("News Gothic MT", Font.BOLD, 14));
		nickLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		nickLabel.setBounds(330, 37, 85, 15);
		nick = save.getNickname();
		nickLabel.setText(nick);
		contentPane.add(nickLabel);
		
		exp = save.getExp();
		expProgress.setValue(exp);
		expProgress.setBounds(279, 15, 106, 15);
		contentPane.add(expProgress);
		
		levelLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 13));
		levelLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		levelLabel.setBounds(391, 15, 30, 15);
		level = save.getLevel();
		levelLabel.setText(String.valueOf(level));
		contentPane.add(levelLabel);
		
		skillid = save.getSkillID();
		skill(skillid);
		
		
		changelistener.setProperty(gamepad.godownPermission);
		changelistener.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue().equals(false)){
					addBackTask addbacktask = new addBackTask();
					addbacktask.execute();
				}
			}
		});
		
		
	}
	
	private void pressup(){
		gamepad.transpo();
	}
	
	private void pressleft(){
		gamepad.goLeft();
		changelistener.setProperty(gamepad.godownPermission);
	}
	
	private void pressright(){
		gamepad.goRight();
		changelistener.setProperty(gamepad.godownPermission);
	}
	
	private void pressdown(){
		gamepad.goDown();
		changelistener.setProperty(gamepad.godownPermission);
	}
	
	private void gamestart(){
		this.requestFocus();
		scoreboard.setScore(0);
		TimingTask timingtask = new TimingTask();
		timingtask.execute();
		try {
			gamepad.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		changelistener.setProperty(gamepad.godownPermission);
	}
	
	private void gamestop(){
		stopflag = true;
		StopTask stoptask = new StopTask();
		stoptask.execute();
		this.transferFocus();
	}
	
	private void isHighscore(){
		if (gamepad.getScore()>highscore){
			highscore = gamepad.getScore();
			highscoreboard.setScore(highscore);
			save.setHighscore(highscore);
			try {
				save.writeSave();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addExp(){
		if (gamepad.getScore()!=0)
			exp += Math.max(gamepad.getScore()-level,1)*10*exptimes;
		while(exp>=100){
			level++;
			exp = exp-100;
		}
		levelLabel.setText(String.valueOf(level));
		expProgress.setValue(exp);
		save.setLevel(level);
		save.setExp(exp);
		try {
			save.writeSave();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void skill(int[] id){
		Skill[] skill = new Skill[4];
		gamepad.clearProabID();
		difficultyd = 0;
		timegapd = 0;
		exptimes = 1;
		int scoretimes = 1;
		boolean nonconv = false;
		boolean multi = true;
		for (int i=0;i<4;i++){
			skill[i] = new Skill(id[i]);
			gamepad.setProabID(skill[i].getProbaID());
			difficulty += skill[i].getDifficulty();
			timegap += skill[i].getTimeGap();
			exptimes *= skill[i].getExpTimes();
			scoretimes *= skill[i].getScoreTimes();
			nonconv = nonconv||skill[i].isNonconv();
			multi = multi&&skill[i].isMulti();
		}
		gamepad.setScoreTimes(scoretimes);
		gamepad.setNonConv(nonconv);
		gamepad.setMulti(multi);
	}
	
	class addBackTask extends SwingWorker<Void,Void>{
		public Void doInBackground(){
			try {
				Thread.sleep(150);
				gamepad.addBack();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		public void done(){
			gamepad.godownPermission = true;
			changelistener.setProperty(gamepad.godownPermission);
			scoreboard.setScore(gamepad.getScore());
			isHighscore();
		}
	}
	
	class TimingTask extends SwingWorker<Void,Void>{
		public Void doInBackground(){
			while(gamepad.isStart){
				if (gamepad.godownPermission&!ispause)
					gamepad.goDown();
				changelistener.setProperty(gamepad.godownPermission);
				try {
					Thread.sleep((long) Math.max(timegap+timegapd-Math.sqrt(scoreboard.getScore())*(difficulty+difficultyd),100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				pauseButton.setEnabled(false);
				stopButton.setEnabled(false);
				gamepad.gamestop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		public void done(){
			if(!stopflag){
				transferFocus();
				startButton.setEnabled(true);
				profileButton.setEnabled(true);
				configButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stopButton.setEnabled(false);
			}
			addExp();
		}
	}
	
	class StopTask extends SwingWorker<Void,Void>{
		public Void doInBackground(){
			try {
				gamepad.gamestop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		public void done(){
			startButton.setEnabled(true);
			profileButton.setEnabled(true);
			configButton.setEnabled(true);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(false);
			stopflag = false;
			isHighscore();
		}
	}

	public class ChangeListener {
		private boolean property;
		private PropertyChangeSupport changeSupport = new PropertyChangeSupport(
				this);

		public void setProperty(boolean newValue) {
			boolean oldValue = property;
			property = newValue;
			changeSupport.firePropertyChange("property", oldValue, newValue);
		}

		public void addPropertyChangeListener(PropertyChangeListener l) {
			changeSupport.addPropertyChangeListener(l);
		}

		public void removePropertyChangeListener(PropertyChangeListener l) {
			changeSupport.removePropertyChangeListener(l);
		}
	}
}
