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

public class MainFrame extends JFrame {

	private JPanel contentPane;

	private GamePad gamePad;
	private JPanel panel;
	private JButton btnStart;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);

		gamePad = new GamePad(Constants.WIDTH_NUMBER, Constants.HEIGHT_NUMBER, Constants.CELL_LENGTH);
		mainPane.add(gamePad);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(90, 10));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(15);
		contentPane.add(panel, BorderLayout.EAST);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePad.start();
			}
		});
		panel.add(btnStart);
	}
}
