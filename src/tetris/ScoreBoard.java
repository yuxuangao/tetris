package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;

public class ScoreBoard extends JPanel{

	private int gap = 0;//两数字间距
	private int fra = 3;//边框宽度
	private int score = 0;
	private Number[] display = new Number[4];
	
	public ScoreBoard(){
		this.setSize(new Dimension(30*4+gap*3+fra*2,50+fra*2));
		this.setLayout(null);
		this.setBackground(Color.black);
		for (int i=0;i<4;i++){
			display[i] = new Number(0);
			display[i].setLocation(new Point((30+gap)*(3-i)+fra,fra));
			this.add(display[i]);
		}
	}
	
	public void setScore(int score){
		this.score = score;
		if (score>999){
			for (int i=0;i<4;i++)
				display[i].setNumber(9);
		}
		else{
			score = score*10;
			for (int i=3;i>=0;i--){
				display[i].setNumber((int)(score/Math.pow(10,i)));
				score = (int) (score-display[i].getNumber()*Math.pow(10, i));
			}
		}
	}
	
	public int getScore(){
		return score;
	}
}
