package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GamePad extends JPanel implements KeyListener {

	private Cell[][] cells;
	private boolean[][] staticCells;
	private Item item;
	private MainLoop mainLoop;
	private int score;

	/**
	 * Create the panel.
	 */
	public GamePad(int width, int height, int cellLength) {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 51, 255)));
		cells = new Cell[height][width];
		staticCells = new boolean[height][width];
		setPreferredSize(new Dimension(width * cellLength, height * cellLength));
		setLayout(null);

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell(cellLength);
				cells[i][j].setLocation(j * cellLength, i * cellLength);
				add(cells[i][j]);
			}

		clear();

		addKeyListener(this);
	}

	public void clear() {
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++) {
				cells[i][j].setVisible(false);
				staticCells[i][j] = false;
			}
		score = 0;
	}

	public void start() {
		clear();
		newItem();
		requestFocus();

		if (mainLoop != null)
			mainLoop.stop();
		mainLoop = new MainLoop();
		new Thread(mainLoop).start();
	}

	private void newItem() {
		item = Item.generateRandom();
		item.setLocation((int)Math.floor(cells[0].length / 2 - 1), (int)Math.floor(Math.log(item.getStatus()) / Math.log(16)) - 3);
		showCell();

		if (isOverlay()) {
			mainLoop.stop();
			JOptionPane.showMessageDialog(this, "fail");
		}
	}

	private void showCell() {
		boolean[][] itemArray = item.getArray();
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++) {
				if (i - item.getY() >= 0 && i - item.getY() < 4 && j - item.getX() >= 0 && j - item.getX() < 4) {
					cells[i][j].setVisible(itemArray[i - item.getY()][j - item.getX()] || staticCells[i][j]);
				} else {
					cells[i][j].setVisible(staticCells[i][j]);
				}
			}
	}

	private void itemLeft() {
		if (item.getX() <= 0)
			return;
		boolean[][] itemArray = item.getArray();
		for (int i = 0; i < 4; i++) {
			if (item.getY() + i < cells.length && itemArray[i][0] && staticCells[item.getY() + i][item.getX() - 1]) {
				return;
			}
		}
		item.setLocation(item.getX() - 1, item.getY());
	}

	private void itemRight() {
		boolean[][] itemArray = item.getArray();
		for (int i = 0; i < 4; i++) {
			if (cells[0].length - 1 - item.getX() < 4 && itemArray[i][cells[0].length - 1 - item.getX()]) {
				return;
			}
			for (int j = 0; j < 4; j++) {
				if (item.getY() + i < cells.length && item.getX() + j + 1 < cells[0].length && itemArray[i][j] && staticCells[item.getY() + i][item.getX() + j + 1]) {
					return;
				}
			}
		}
		item.setLocation(item.getX() + 1, item.getY());
	}

	private void itemDown() {
		if (item.getY() >= cells.length - 4) {
			fixItem();
			newItem();
			return;
		}
		boolean[][] itemArray = item.getArray();
		for (int i = 0; i < 4; i++) {
			if (item.getX() + i < cells[0].length && itemArray[3][i] && staticCells[item.getY() + 4][item.getX() + i]) {
				fixItem();
				newItem();
				return;
			}
		}
		item.setLocation(item.getX(), item.getY() + 1);
	}

	private void itemTurn() {
		Item tempItem = new Item(item);

		item.turnLeft(1);
		item.setLocation(item.getX(), Math.max(item.getY(), (int)Math.floor(Math.log(item.getStatus()) / Math.log(16)) - 3));
		while (cells[0].length - item.getX() < 4 && (item.getStatus() & 0x1111 * (int)Math.pow(2, 3 - cells[0].length + item.getX())) != 0) {
			item.setLocation(item.getX() - 1, item.getY());
		}

		if (isOverlay()) {
			item = tempItem;
		}
	}

	private void checkLine() {
		for (int i = cells.length - 1; i >= 0; i--) {
			boolean sum = true;
			for (int j = 0; j < cells[0].length; j++)
				sum &= staticCells[i][j];
			if (sum)
				deleteLine(i);
		}
	}

	private void deleteLine(int line) {
		for (int i = line; i > 0; i--)
			for (int j = 0; j < cells[0].length; j++)
				staticCells[i][j] = staticCells[i - 1][j];
		for (int j = 0; j < cells[0].length; j++)
			staticCells[0][j] = false;
	}

	private void fixItem() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (item.getX() + j < 0 || item.getX() + j >= cells[0].length || item.getY() + i < 0 || item.getY() + i >= cells.length)
					continue;
				staticCells[item.getY() + i][item.getX() + j] = staticCells[item.getY() + i][item.getX() + j] || item.getArray()[i][j];
			}
		checkLine();
	}

	private boolean isOverlay() {
		boolean[][] itemArray = item.getArray();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (item.getY() + i < cells.length && item.getX() + j < cells[0].length && itemArray[i][j] && staticCells[item.getY() + i][item.getX() + j])
					return true;
			}

		return false;
	}

	public void pause() {
		if (mainLoop != null)
			mainLoop.pause();
	}

	public void resume() {
		requestFocus();
		if (mainLoop != null)
			mainLoop.resume();
	}

	public void stop() {
		if (mainLoop != null)
			mainLoop.stop();
		clear();
	}

	public int getScore() {
		return score;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			itemLeft();
			showCell();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			itemRight();
			showCell();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			itemTurn();
			showCell();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			mainLoop.pause();
			itemDown();
			showCell();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		mainLoop.resume();
	}

	private class MainLoop implements Runnable {
		private boolean pause = false;
		private boolean stop = false;

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (pause)
					continue;
				if (stop)
					break;

				itemDown();
				showCell();
			}
		}

		public void pause() {
			pause = true;
		}

		public void resume() {
			pause = false;
		}

		public void stop() {
			stop = true;
		}
	}
}
