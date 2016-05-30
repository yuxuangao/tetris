package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Number extends JPanel{
	
	private int number;
	private Color background = Color.black;
	private Color enableColor = Color.red;
	private Color disableColor = Color.darkGray;
	
	public Number(int number){
		this.setSize(new Dimension(30,50));
		this.setLayout(null);
		this.number = number;
	}
	
	public void setBackground(Color background){
		this.background = background;
		repaint();
	}
	
	public void setEnableColor(Color enableColor){
		this.enableColor = enableColor;
		repaint();
	}
	
	public void setdisableColor(Color disableColor){
		this.disableColor = disableColor;
		repaint();
	}
	
	public void setNumber(int number){
		this.number = number;
		repaint();
	}
	
	public int getNumber(){
		return number;
	}
	
	public void paint(Graphics g){
		g.setColor(background);
		g.fillRect(0, 0, 30, 50);
		{
			//绘制a段
			if (number==0||number==2||number==3||number==5||number==6||number==7||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {3,27,23,7,3};
			int[] ypoint = {3,3,7,7,3};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制b段
			if (number==0||number==1||number==2||number==3||number==4||number==7||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {27,27,23,23,27};
			int[] ypoint = {5,24,20,9,5};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制c段
			if (number==0||number==1||number==3||number==4||number==5||number==6||number==7||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {27,27,23,23,27};
			int[] ypoint = {26,45,41,30,26};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制d段
			if (number==0||number==2||number==3||number==5||number==6||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {27,3,7,23,27};
			int[] ypoint = {47,47,43,43,47};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制e段
			if (number==0||number==2||number==6||number==8)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {3,3,7,7,3};
			int[] ypoint = {45,26,30,41,45};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制f段
			if (number==0||number==4||number==5||number==6||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {3,3,7,7,3};
			int[] ypoint = {24,5,9,20,24};
			g.fillPolygon(xpoint, ypoint, 5);
			xpoint = null;
			ypoint = null;
		}
		{
			//绘制g段
			if (number==2||number==3||number==4||number==5||number==6||number==8||number==9)
				g.setColor(enableColor);
			else
				g.setColor(disableColor);
			int[] xpoint = {4,6,24,26,24,6,4};
			int[] ypoint = {25,23,23,25,27,27,25};
			g.fillPolygon(xpoint, ypoint, 7);
			xpoint = null;
			ypoint = null;
		}
	}

}
