package core;

import common.Constants;

public class Item {

	private static final int[] TYPE = {0x000f, 0x00cc, 0x088c, 0x044c, 0x08c4, 0x04c8};

	private int status;
	private int x;
	private int y;

	public Item(int type) {
		status = TYPE[Math.max(0, Math.min(TYPE.length - 1, type))];
		x = 0;
		y = 0;
	}

	public Item(Item item) {
		status = item.status;
		x = item.x;
		y = item.y;
	}

	public static Item generateRandom() {
		return new Item(Constants.random.nextInt(TYPE.length)).turnLeft(Constants.random.nextInt(4));
	}

	public int getStatus() {
		return status;
	}

	public boolean[][] getArray() {
		boolean[][] array = new boolean[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				array[i][j] = (status & (int)Math.pow(2, 15 - i * 4 - j)) != 0;
			}
		return array;
	}

	public Item turnLeft(int times) {
		for (int num = 0; num < times; num++) {
			char newStatus = 0;
			for (int i = 0; i < 4; i++)
				for (int j = 3; j >= 0; j--) {
					newStatus *= 2;
					newStatus += (status & (int)Math.pow(2, j * 4 + i)) == 0 ? 0 : 1;
				}
			status = newStatus;
		}
		normalize();
		return this;
	}

	private void normalize() {
		while ((status & 0x8888) == 0) {
			status *= 2;
		}
		while ((status & 0x000f) == 0) {
			status >>>= 4;
		}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
