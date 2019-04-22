package number;

import java.awt.Dimension;

import javax.swing.JPanel;

import common.Constants;

public class NumberTablet extends JPanel {

	private NumberPad[] numberPads;

	/**
	 * Create the panel.
	 */
	public NumberTablet() {
		setLayout(null);
		setBackground(Constants.NUMBER_PAD_COLOR_BACKGROUND);
		setPreferredSize(new Dimension(Constants.NUMBER_PAD_WIDTH * Constants.NUMBER_TABLET_DIGIT
				+ (Constants.NUMBER_TABLET_DIGIT - 1) * Constants.NUMBER_TABLET_INTERVAL, Constants.NUMBER_PAD_HEIGHT));

		numberPads = new NumberPad[Constants.NUMBER_TABLET_DIGIT];
		for (int i = 0; i < Constants.NUMBER_TABLET_DIGIT; i++) {
			numberPads[i] = new NumberPad(Constants.NUMBER_PAD_WIDTH, Constants.NUMBER_PAD_HEIGHT);
			numberPads[i].setLocation(i * (Constants.NUMBER_PAD_WIDTH + Constants.NUMBER_TABLET_INTERVAL), 0);
			numberPads[i].setThickness(Constants.NUMBER_PAD_THICKNESS);
			numberPads[i].setBlankTopBottom(Constants.NUMBER_PAD_BLANK_TOP_BOTTOM);
			numberPads[i].setBlankLeftRight(Constants.NUMBER_PAD_BLANK_LEFT_RIGHT);
			numberPads[i].setInterval(Constants.NUMBER_PAD_INTERVAL);
			numberPads[i].setBackgroundColor(Constants.NUMBER_PAD_COLOR_BACKGROUND);
			numberPads[i].setDisabledColor(Constants.NUMBER_PAD_COLOR_DISABLED);
			numberPads[i].setEnabledColor(Constants.NUMBER_PAD_COLOR_ENABLED);
			add(numberPads[i]);
			numberPads[i].repaint();
		}
	}

	public void setNumber(int number) {
		number %= Math.pow(10, Constants.NUMBER_TABLET_DIGIT);
		for (int i = Constants.NUMBER_TABLET_DIGIT - 1; i >= 0; i--) {
			if (i != 0 && Math.floor(number / Math.pow(10, i)) == 0) {
				numberPads[Constants.NUMBER_TABLET_DIGIT - 1 - i].setNumber(-1);
			} else {
				numberPads[Constants.NUMBER_TABLET_DIGIT - 1 - i].setNumber((int)Math.floor(number / Math.pow(10, i)));
			}
			number %= Math.pow(10, i);
		}
	}
}
