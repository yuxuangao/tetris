package core;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Cell extends JPanel {

	/**
	 * Create the panel.
	 */
	public Cell(int length) {
		setBackground(new Color(255, 204, 102));
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setSize(length, length);
	}

}
