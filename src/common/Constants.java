package common;

import java.awt.Color;
import java.util.Calendar;
import java.util.Random;

public class Constants {
	public static final int WIDTH_NUMBER = 15;
	public static final int HEIGHT_NUMBER = 25;
	public static final int CELL_LENGTH = 20;

	public static final int NUMBER_PAD_WIDTH = 50;
	public static final int NUMBER_PAD_HEIGHT = 70;
	public static final int NUMBER_PAD_BLANK_TOP_BOTTOM = 5;
	public static final int NUMBER_PAD_BLANK_LEFT_RIGHT = 8;
	public static final int NUMBER_PAD_INTERVAL = 3;
	public static final int NUMBER_PAD_THICKNESS = 6;
	public static final Color NUMBER_PAD_COLOR_BACKGROUND = Color.BLACK;
	public static final Color NUMBER_PAD_COLOR_DISABLED = Color.DARK_GRAY;
	public static final Color NUMBER_PAD_COLOR_ENABLED = Color.RED;

	public static final int NUMBER_TABLET_DIGIT = 4;
	public static final int NUMBER_TABLET_INTERVAL = 1;

	public static final Random random = new Random(Calendar.getInstance().getTimeInMillis());
}
