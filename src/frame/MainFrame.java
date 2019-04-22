package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.Constants;
import core.GamePad;
import core.GamePad.ScoreCallback;
import number.NumberTablet;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private GamePad gamePad;
	private NumberTablet numberTablet;
	private JPanel rightPane;
	private JButton btnStart;
	private JButton btnResume;
	private JButton btnPause;
	private JButton btnStop;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 646);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel topPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPane.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		numberTablet = new NumberTablet();
		topPane.add(numberTablet);
		contentPane.add(topPane, BorderLayout.NORTH);

		JPanel mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		gamePad = new GamePad(Constants.WIDTH_NUMBER, Constants.HEIGHT_NUMBER, Constants.CELL_LENGTH);
		gamePad.setScoreCallback(new ScoreCallback() {
			public void scoreUpdate(int score) {
				numberTablet.setNumber(score);
			}
		});
		mainPane.add(gamePad);

		rightPane = new JPanel();
		rightPane.setPreferredSize(new Dimension(90, 10));
		FlowLayout fl_rightPane = (FlowLayout) rightPane.getLayout();
		fl_rightPane.setVgap(15);
		contentPane.add(rightPane, BorderLayout.EAST);

		btnStart = new JButton("Start");
		btnStart.setPreferredSize(new Dimension(80, 32));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePad.start();
				btnStart.setEnabled(false);
				btnResume.setEnabled(false);
				btnPause.setEnabled(true);
				btnStop.setEnabled(true);
			}
		});
		rightPane.add(btnStart);

		btnResume = new JButton("Resume");
		btnResume.setEnabled(false);
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePad.resume();
				btnStart.setEnabled(false);
				btnResume.setEnabled(false);
				btnPause.setEnabled(true);
				btnStop.setEnabled(true);
			}
		});
		btnResume.setPreferredSize(new Dimension(80, 32));
		rightPane.add(btnResume);

		btnPause = new JButton("Pause");
		btnPause.setEnabled(false);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePad.pause();
				btnStart.setEnabled(false);
				btnResume.setEnabled(true);
				btnPause.setEnabled(false);
				btnStop.setEnabled(true);
			}
		});
		btnPause.setPreferredSize(new Dimension(80, 32));
		rightPane.add(btnPause);

		btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePad.stop();
				btnStart.setEnabled(true);
				btnResume.setEnabled(false);
				btnPause.setEnabled(false);
				btnStop.setEnabled(false);
			}
		});
		btnStop.setPreferredSize(new Dimension(80, 32));
		rightPane.add(btnStop);
	}
}
