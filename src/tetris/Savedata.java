package tetris;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class Savedata {

	private File save = new File("tetris.save");
	private boolean isnew = false;
	private final static String[] key = {"nick","icon","level","exp","skill","highscore","backcolor"};
	private String[] value = {"nickname","0","1","0","-1 -1 -1 -1","0","255 255 204"};
	
	
	public Savedata() throws IOException{
		if(!save.exists()){
			isnew = true;
		}
		else{
			BufferedReader reader = new BufferedReader(new FileReader(save));
			String tempstring;
			for (int i=0;i<key.length;i++){
				tempstring = reader.readLine();
				value[i] = "";
				String[] tempsp = tempstring.split(" ");
				for (int j=1;j<tempsp.length;j++)
					value[i] += tempsp[j]+" ";
				value[i] = value[i].substring(0, value[i].length()-1);
			}
			reader.close();
		}
	}
	
	public boolean IsNew(){
		return isnew;
	}
	
	public void createNewFile() throws IOException{
		save.createNewFile();
		writeSave();
	}
	
	public void writeSave() throws IOException{
		FileOutputStream out = new FileOutputStream(save);
		for (int i=0;i<key.length;i++){
			out.write((key[i]+" "+value[i]+"\n").getBytes());
		}
		out.close();
	}
	
	public String getNickname(){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("nick")){
				return value[i];
			}
		return "nickname";
	}
	
	public void setNickname(String nickname){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("nick")){
				value[i] = nickname;
				break;
			}
	}
	
	public int getIconserial(){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("icon")){
				return Integer.parseInt(value[i]);
			}
		return 0;
	}
	
	public void setIconserial(int iconserial){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("icon")){
				value[i] = String.valueOf(iconserial);
				break;
			}
	}
	
	public int getLevel(){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("level")){
				return Integer.parseInt(value[i]);
			}
		return 0;
	}
	
	public void setLevel(int level){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("level")){
				value[i] = String.valueOf(level);
				break;
			}
	}
	
	public int getExp(){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("exp")){
				return Integer.parseInt(value[i]);
			}
		return 0;
	}
	
	public void setExp(int exp){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("exp")){
				value[i] = String.valueOf(exp);
				break;
			}
	}
	
	public int[] getSkillID(){
		int[] id = new int[4];
		for (int i=0;i<key.length;i++)
			if (key[i].equals("skill")){
				String[] temp = value[i].split(" ");
				for (int j=0;j<4;j++)
					id[j] = Integer.parseInt(temp[j]);
				break;
			}
		return id;
	}
	
	public void setSkillID(int[] id){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("skill")){
				value[i] = "";
				for (int j=0;j<4;j++)
					value[i] += String.valueOf(id[j])+" ";
				value[i] = value[i].substring(0, value[i].length()-1);
				break;
			}
	}
	
	public int getHighscore(){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("highscore")){
				return Integer.parseInt(value[i]);
			}
		return 0;
	}
	
	public void setHighscore(int highscore){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("highscore")){
				value[i] = String.valueOf(highscore);
				break;
			}
	}
	
	public Color getBackcolor(){
		Color color = new Color(255,255,204);
		for (int i=0;i<key.length;i++)
			if (key[i].equals("backcolor")){
				String[] rgb = value[i].split(" ");
				color = new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
				return color;
			}
		return color;
	}
	
	public void setBackcolor(Color color){
		for (int i=0;i<key.length;i++)
			if (key[i].equals("backcolor")){
				String rgb = String.valueOf(color.getRed())+" "+String.valueOf(color.getGreen())+" "+String.valueOf(color.getBlue());
				value[i] = rgb;
				break;
			}
	}
	
}
