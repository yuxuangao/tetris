package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;

public class GamePad extends JPanel{
	
	public boolean isStart = false;
	public boolean godownPermission = true;
	private Elements element;
	private int squaresize;
	private int xsize;
	private int ysize;
	private int[][] backarray;
	private Square[][] back;
	private int score;
	private int scoretimes = 1;
	private int[] proabID = {-1,-1,-1};
	private boolean multi = true;
	
	
	public GamePad(int squaresize,int xsize,int ysize){
		this.squaresize = squaresize;
		this.xsize = xsize;
		this.ysize = ysize;
		back = new Square[ysize][xsize];
		this.setSize(new Dimension(squaresize*xsize,squaresize*ysize));
		this.setLayout(null);
		this.setBackground(new Color(255, 255, 204));
		element = new Elements(squaresize);
		element.setVisible(false);
		for (int i=0;i<ysize;i++)
			for (int j=0;j<xsize;j++){
				back[i][j] = new Square(squaresize);
				back[i][j].setAllVisible(false);
				back[i][j].setLocation(new Point(j*squaresize,i*squaresize));
				this.add(back[i][j]);
			}
		this.add(element);
	}
	
	private int getRandom(){
		int random = (int)(0+Math.random()*element.getTypeNumber());
		for (int i=0;i<proabID.length;i++){
			if (proabID[i]!=-1){
				int r = (int)(0+Math.random()*9);
				if (r<3)
					random = proabID[i];
			}
		}
		return random;
	}
	
	public void addnewElement() throws InterruptedException{
		element.elChange(getRandom());
		for (int i=0;i<(int)(1+Math.random()*4);i++)
			element.transpo();
		element.setLocation(new Point(squaresize*(xsize/2-1),0-squaresize*element.getTopRest()));
		element.setVisible(true);
	}
	
	public void transpo(){
		element.transpo();
		Point temppoint = element.getLocation();
		for (int k=0;k<3;k++)
			for (int i=0;i<4;i++)
				for (int j=0;j<4;j++)
					if (element.getArray()[i][j]==1&&(element.getLocation().x/squaresize+j)>=xsize)
						element.setLocation(new Point(
								Math.max(element.getLocation().x-squaresize,0),element.getLocation().y));
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				if (element.getLocation().y/squaresize+i<ysize&&element.getLocation().x/squaresize+j<xsize&&element.getLocation().y/squaresize+i>0)
					if (element.getArray()[i][j]==1&&
						backarray[element.getLocation().y/squaresize+i][element.getLocation().x/squaresize+j]==1){
						element.transpo();
						element.transpo();
						element.transpo();
						element.setLocation(temppoint);
						temppoint = null;
						return;
					}
					else{}
				else if(element.getLocation().y/squaresize+i<0||element.getLocation().x/squaresize+j>=xsize){
					if(element.getArray()[i][j]==1){
						element.transpo();
						element.transpo();
						element.transpo();
						element.setLocation(temppoint);
						temppoint = null;
						return;
					}
				}
	}
	
	public void goDown(){
		element.setLocation(new Point(element.getLocation().x,element.getLocation().y+squaresize));
		collDetection();
	}
	
	public void goLeft(){
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				if (element.getArray()[i][j]==1)
					if (element.getLocation().x!=0)
						if (backarray[element.getLocation().y/squaresize+i][element.getLocation().x/squaresize+j-1]==1)
							return;
		element.setLocation(new Point(
				Math.max(element.getLocation().x-squaresize,0),element.getLocation().y));
		collDetection();
	}
	
	public void goRight(){
		for (int i=0;i<4;i++)
			for (int j=0;j<4;j++)
				if (element.getArray()[i][j]==1)
					if (element.getLocation().x!=squaresize*(xsize-4+element.getRightRest()))
						if (backarray[element.getLocation().y/squaresize+i][element.getLocation().x/squaresize+j+1]==1)
							return;
		element.setLocation(new Point(
				Math.min(element.getLocation().x+squaresize,squaresize*(xsize-4+element.getRightRest())),element.getLocation().y));
		collDetection();
	}
	
	public void start() throws InterruptedException{
		isStart = true;
		godownPermission = true;
		score = 0;
		backarray = new int[ysize][xsize];
		for (int i=0;i<ysize;i++)
			for (int j=0;j<xsize;j++){
				back[i][j].setAllVisible(false);
				backarray[i][j] = 0;
			}
		addnewElement();
	}
	
	private void collDetection(){
		if (isStart)
			for (int i=0;i<4;i++){
				boolean flag = false;
				for (int j=0;j<4;j++)
					if (element.getArray()[i][j]==1)
						if(element.getLocation().x/squaresize+j<xsize)
							if(element.getLocation().y==squaresize*(ysize-4)){
								flag = true;
								godownPermission = false;
								break;
							}
							else if (backarray[element.getLocation().y/squaresize+i+1][element.getLocation().x/squaresize+j]==1){
								flag = true;
								godownPermission = false;
								break;
							}
				if (flag)
					break;
			}
	}
	
	public void addBack() throws InterruptedException{
		for (int m=0;m<4;m++)
			for (int n=0;n<4;n++)
				if(element.getLocation().y/squaresize+m<ysize&&element.getLocation().y/squaresize+m>=0&&element.getLocation().x/squaresize+n<xsize&&element.getLocation().x/squaresize+n>=0)
					backarray[element.getLocation().y/squaresize+m][element.getLocation().x/squaresize+n] =
						Math.min(element.getArray()[m][n]+backarray[element.getLocation().y/squaresize+m][element.getLocation().x/squaresize+n],1);
		for (int m=0;m<ysize;m++)
			for (int n=0;n<xsize;n++)
				back[m][n].setAllVisible(backarray[m][n]==1?true:false);
		beline();
		addnewElement();
		over();
		collDetection();
	}
	
	private void beline() throws InterruptedException{
		int addscore = 1;
		for (int i=ysize-1;i>=0;i--){
			int sum = 0;
			for (int j=0;j<xsize;j++){
				sum += backarray[i][j];
			}
			if (sum==xsize){
				score += (addscore)*scoretimes;
				if (multi)
					addscore++;
				eliminAnime(i);
				for (int m=i;m>=1;m--)
					for (int n=0;n<xsize;n++){
						backarray[m][n] = backarray[m-1][n];
						back[m][n].setAllVisible(backarray[m][n]==1);
					}
				for (int n=0;n<xsize;n++){
					backarray[0][n] = 0;
					back[0][n].setAllVisible(false);
				}
				i++;
			}
		}
	}
	
	private void over() throws InterruptedException{
		for (int i=element.getTopRest();i<4;i++){
			boolean flag = false;
			for (int j=0;j<4;j++){
				if(element.getLocation().y/squaresize+i>=0)
					if (backarray[element.getLocation().y/squaresize+i][element.getLocation().x/squaresize+j]==1){
						flag = true;
						isStart = false;
						godownPermission = false;
						break;
					}
			}
			if (flag)
				break;
		}
	}
	
	public void gamestop() throws InterruptedException{
		isStart = false;
		overAnime();
	}
	
	//行消除时的动画
	private void eliminAnime(int line) throws InterruptedException{
		element.setVisible(false);
		Thread.sleep(100);
		for (int i=0;i<8;i++){
			if (i%2==0)
				for (int j=0;j<xsize;j++)
					back[line][j].setAllVisible(false);
			else
				for (int j=0;j<xsize;j++)
					back[line][j].setAllVisible(true);
			Thread.sleep(100);
		}
	}
	
	//game over时的动画
	private void overAnime() throws InterruptedException{
		
		for (int i=0;i<ysize;i++){
			for (int j=0;j<xsize;j++){
				back[i][j].setAllVisible(true);
			}
			Thread.sleep(100);
		}
		element.setVisible(false);
		Thread.sleep(100);
		for (int i=0;i<ysize;i++){
			for (int j=0;j<xsize;j++){
				back[i][j].setAllVisible(false);
			}
			Thread.sleep(100);
		}
	}
	
	public int getScore(){
		return score;
	}
	
	public void setNonConv(boolean nonconv){
		element.setNonConv(nonconv);
	}
	
	public void clearProabID(){
		for (int i=0;i<proabID.length;i++){
			proabID[i] = -1;
		}
	}
	
	public void setProabID(int proabid){
		for (int i=0;i<proabID.length;i++){
			if (proabID[i]==proabid)
				return;
			else if (proabID[i]==-1){
				proabID[i] = proabid;
				break;
			}
		}
	}
	
	public void setScoreTimes(int scoretimes){
		this.scoretimes = scoretimes;
	}
	
	public void setMulti(boolean multi){
		this.multi = multi;
	}

}
