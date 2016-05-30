package tetris;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;

public class Elements extends JPanel {
	
	private Square[] square = new Square[16];
	private static final char display[][] = {	{1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
												{0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0},
												{0,0,0,0,1,0,0,0,1,1,0,0,0,1,0,0},
												{0,0,0,0,0,1,0,0,1,1,0,0,1,0,0,0},
												{0,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0},
												{0,0,0,0,0,1,0,0,0,1,0,0,1,1,0,0},
												{0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0},
												{0,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0},
												{0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0},
												{0,1,1,0,0,1,0,0,0,1,0,0,1,1,0,0},
												{1,1,0,0,0,1,0,0,0,1,0,0,0,1,1,0}};
	private int[] visible = new int[16];
	private boolean nonconv = false;
	
	public Elements(int squaresize){
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(new Dimension(squaresize*4, squaresize*4));
		for (int i=0;i<16;i++){
			square[i] = new Square(squaresize);
			square[i].setLocation(new Point(i%4*squaresize,i/4*squaresize));
			this.add(square[i]);
		}
	}
	
	public void transpo(){
		int[] visiblecopy = new int[16];
		for (int i=0;i<16;i++)
			visiblecopy[i] = visible[i];
		for (int i=0;i<16;i++){
			visible[i] = visiblecopy[(3-i%4)*4+i/4];
			square[i].setAllVisible(visible[i]==1);
		}
		visiblecopy = null;
		while (visible[12]+visible[13]+visible[14]+visible[15]==0){
			for (int i=15;i>=0;i--){
				if (i>=4)
					visible[i] = visible[i-4];
				else
					visible[i] = 0;
			}
		}
		while (visible[0]+visible[4]+visible[8]+visible[12]==0){
			for (int i=0;i<4;i++)
				for (int j=0;j<4;j++)
					if (i<3)
						visible[j*4+i] = visible[j*4+i+1];
					else
						visible[j*4+i] = 0;
		}
		for (int i=0;i<16;i++){
			square[i].setAllVisible(visible[i]==1);
		}
	}
	
	public void elChange(int typeID){
		for (int i=0;i<16;i++){
			visible[i] = display[typeID][i];
			square[i].setAllVisible(visible[i]==1);
		}
	}
	
	public int getTopRest(){
		int toprest;
		for (toprest=3;toprest>=0;toprest--)
			if(visible[toprest*4]+visible[toprest*4+1]+visible[toprest*4+2]+visible[toprest*4+3]==0)
				break;
		return toprest+1;
	}
	
	public int getRightRest(){
		int rightrest;
		for (rightrest=0;rightrest<4;rightrest++)
			if(visible[rightrest]+visible[rightrest+4]+visible[rightrest+8]+visible[rightrest+12]==0)
				break;
		return 4-rightrest;
	}
	
	public int[][] getArray(){
		int[][] array = new int[4][4];
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				array[i][j] = visible[i*4+j];
		return array;
	}
	
	public int getTypeNumber(){
		if (!nonconv)
			return 7;
		return display.length;
	}
	
	public void setNonConv(boolean nonconv){
		this.nonconv = nonconv;
	}
}
