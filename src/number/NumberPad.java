package number;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class NumberPad extends JPanel {

	private static final int[] NUMBER_TYPE = {0xfc, 0x60, 0xda, 0xf2, 0x66, 0xb6, 0xbe, 0xe0, 0xfe, 0xf6};

	private int width;
	private int height;

	private int thickness = 6;
	private int blankTopBottom = 5;
	private int blankLeftRight = 5;
	private int interval = 3;
	private Color backgroundColor = Color.BLACK;
	private Color disabledColor = Color.DARK_GRAY;
	private Color enabledColor = Color.RED;

	private int number = -1;

	/**
	 * Create the panel.
	 */
	public NumberPad(int width, int height) {
		setPadSize(width, height);
	}

	public void setPadSize(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setSize(new Dimension(width, height));
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public void setBlankTopBottom(int blankTopBottom) {
		this.blankTopBottom = blankTopBottom;
	}

	public void setBlankLeftRight(int blankLeftRight) {
		this.blankLeftRight = blankLeftRight;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setNumber(int number) {
		this.number = number;
		repaint();
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setDisabledColor(Color disabledColor) {
		this.disabledColor = disabledColor;
	}

	public void setEnabledColor(Color enabledColor) {
		this.enabledColor = enabledColor;
	}

	private int[][] calcPointsX() {
		int[][] xPoints = {
			{blankLeftRight + thickness/2 + interval, width - blankLeftRight - thickness/2 - interval, width - blankLeftRight - interval,
				width - blankLeftRight - thickness/2 - interval, blankLeftRight + thickness/2 + interval, blankLeftRight + interval},
			{width - blankLeftRight - thickness/2, width - blankLeftRight, width - blankLeftRight, width - blankLeftRight - thickness/2,
				width - blankLeftRight - thickness, width - blankLeftRight - thickness},
			{width - blankLeftRight - thickness/2, width - blankLeftRight, width - blankLeftRight, width - blankLeftRight - thickness/2,
				width - blankLeftRight - thickness, width - blankLeftRight - thickness},
			{blankLeftRight + thickness/2 + interval, width - blankLeftRight - thickness/2 - interval, width - blankLeftRight - interval,
				width - blankLeftRight - thickness/2 - interval, blankLeftRight + thickness/2 + interval, blankLeftRight + interval},
			{blankLeftRight + thickness/2, blankLeftRight + thickness, blankLeftRight + thickness, blankLeftRight + thickness/2, blankLeftRight, blankLeftRight},
			{blankLeftRight + thickness/2, blankLeftRight + thickness, blankLeftRight + thickness, blankLeftRight + thickness/2, blankLeftRight, blankLeftRight},
			{blankLeftRight + thickness/2 + interval, width - blankLeftRight - thickness/2 - interval, width - blankLeftRight - interval,
				width - blankLeftRight - thickness/2 - interval, blankLeftRight + thickness/2 + interval, blankLeftRight + interval},
		};
		return xPoints;
	}

	private int[][] calcPointsY() {
		int[][] yPoints = {
			{blankTopBottom, blankTopBottom, blankTopBottom + thickness/2, blankTopBottom + thickness, blankTopBottom + thickness, blankTopBottom + thickness/2},
			{blankTopBottom + thickness/2 + interval, blankTopBottom + thickness + interval, height/2 - thickness/2 - interval,
				height/2 - interval, height/2 - thickness/2 - interval, blankTopBottom + thickness + interval},
			{height/2 + interval, height/2 + thickness/2 + interval, height - blankTopBottom - thickness - interval,
				height - blankTopBottom - thickness/2 - interval, height - blankTopBottom - thickness - interval, height/2 + thickness/2 + interval},
			{height - blankTopBottom - thickness, height - blankTopBottom - thickness, height - blankTopBottom - thickness/2,
				height - blankTopBottom, height - blankTopBottom, height - blankTopBottom - thickness/2},
			{height/2 + interval, height/2 + thickness/2 + interval, height - blankTopBottom - thickness - interval,
					height - blankTopBottom - thickness/2 - interval, height - blankTopBottom - thickness - interval, height/2 + thickness/2 + interval},
			{blankTopBottom + thickness/2 + interval, blankTopBottom + thickness + interval, height/2 - thickness/2 - interval,
				height/2 - interval, height/2 - thickness/2 - interval, blankTopBottom + thickness + interval},
			{height/2 - thickness/2, height/2 - thickness/2, height/2, height/2 + thickness/2, height/2 + thickness/2, height/2}
		};
		return yPoints;
	}

	private int[] calcPointsN() {
		int[] nPoints = {6, 6, 6, 6, 6, 6, 6};
		return nPoints;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		int[][] xPoints = calcPointsX();
		int[][] yPoints = calcPointsY();
		int[] nPoints = calcPointsN();

		for (int i = 0; i < nPoints.length; i++) {
			if (number >= 0 && number < NUMBER_TYPE.length && ((NUMBER_TYPE[number] & (int)Math.pow(2, 7 - i)) != 0)) {
				g.setColor(enabledColor);
			} else {
				g.setColor(disabledColor);
			}

			g.fillPolygon(xPoints[i], yPoints[i], nPoints[i]);
		}
	}

}
