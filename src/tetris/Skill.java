package tetris;

public class Skill {
	
	private int id;
	
	public final static int NUMBER = 8;
	
	private final static String[] name ={	"技能1",
											"技能2",
											"技能3",
											"技能4",
											"技能5",
											"技能6",
											"技能7",
											"技能8"};
	
	private final static String[] description = 
		{	"长条出现概率增加，同时难度增加略微加快",
			"方块出现概率增加，同时难度增加略微加快",
			"初始难度增加，同时难度增加速度减慢",
			"初始难度降低，同时难度增加速度加快",
			"游戏中会出现非常规图形",
			"难度大幅增加，同时分数获得双倍",
			"难度大幅增加，同时经验值获得双倍",
			"取消同时消除多行时的分数奖励，同时经验值获得三倍"};
	
	private final static int[] probaID = {0,1,-1,-1,-1,-1,-1,-1};
	private final static int[] difficulty = {7,7,-12,12,0,15,15,0};
	private final static int[] scoretimes = {1,1,1,1,1,2,1,1};
	private final static int[] exptimes = {1,1,1,1,1,1,2,3};
	private final static int[] level = {2,3,4,4,6,8,8,10};
	private final static int[] timegap = {0,0,-100,100,0,-120,-120,0};
	
	Skill(int skillID){
		id = skillID;
	}
	
	public String getName(){
		if (id>=0)
			return name[id];
		return "";
	}
	
	public String getDescription(){
		if (id>=0)
			return description[id];
		return "";
	}
	
	public int getProbaID(){
		if (id>=0)
			return probaID[id];
		return -1;
	}
	
	public int getDifficulty(){
		if (id>=0)
			return difficulty[id];
		return 0;
	}
	
	public int getScoreTimes(){
		if (id>=0)
			return scoretimes[id];
		return 1;
	}
	
	public int getExpTimes(){
		if (id>=0)
			return exptimes[id];
		return 1;
	}
	
	public boolean isNonconv(){
		if (id==4)
			return true;
		return false;
	}
	
	public boolean isMulti(){
		if (id==7)
			return false;
		return true;
	}
	
	public int getLevel(){
		if (id>=0)
			return level[id];
		return 1;
	}
	
	public int getTimeGap(){
		if (id>=0)
			return timegap[id];
		return 0;
	}
	
	public static int getLevel(int skillID){
		return level[skillID];
	}

}
