package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Square extends JPanel{
	
	private JPanel iner = new JPanel();
	
	public Square(int squaresize){
		this.setSize(new Dimension(squaresize,squaresize));
		this.setLayout(null);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		iner.setSize(new Dimension(squaresize-8,squaresize-8));
		iner.setLocation(new Point(4,4));
		iner.setBackground(new Color(187,255,255));
		this.setBackground(new Color(124,205,124));
		this.add(iner);
	}
	
	public void setinerBackground(Color color){
		iner.setBackground(color);
	}
	
	public void setAllVisible(boolean opaque){
		this.setVisible(opaque);
		iner.setVisible(opaque);
	}
}
