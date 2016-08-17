package marioplanet.evaluation;

import ch.idsia.benchmark.tasks.MarioSystemOfValues;
import ch.idsia.benchmark.tasks.SystemOfValues;
import ch.idsia.tools.EvaluationInfo;

public class MarioPlanetSystemOfValues extends SystemOfValues {
	public static String[] variableNames ;
	public static final int weightsLength  = 18;
	public static final int DISTANCE_PASSED_CELLS =0;
	public static final int KILLS_BY_FIRE =1;
	public static final int KILLS_BY_SHELL =2;
	public static final int KILLS_BY_STOMP =3;
	public static final int COLLISION_WITH_CREATURES =4;
	public static final int MUSHROOM_DEVOURED =5;
	public static final int FLOWERS_DEVOURED =6;
	public static final int TIME_SPENT =7;
	public static final int WIN =8;
	final static public int MODE = 9;
	final static public int COINS = 10;
	final static public int GREEN_MUSHROOM = 11;
	final static public int HIDDEN_BLOCKS = 12;
	final static public int TIME_LEFT = 13;
	final static public int KILLS =14;
	final static public int BRICKS=15;
	public final static int CELLWINS=16;
	public final static int CONSTANT = 17;
	static{
		variableNames = new String[weightsLength];
		variableNames[DISTANCE_PASSED_CELLS]="distance";
		variableNames[KILLS_BY_FIRE]="fire";
		variableNames[KILLS_BY_SHELL]="shell";
		variableNames[KILLS_BY_STOMP]="stomp";
		variableNames[KILLS]="kills";
		variableNames[MUSHROOM_DEVOURED]="mushroom";
		variableNames[FLOWERS_DEVOURED]="flower";
		variableNames[COLLISION_WITH_CREATURES]="collision";
		variableNames[CELLWINS]="death";
		variableNames[BRICKS]="brick";
		variableNames[TIME_LEFT]="timeLeft";
		variableNames[COINS]="Coins";
		variableNames[CONSTANT]="minCollision";
	}
	private int[] W= new int[17];
	private static int weightNormalizedScalarValue=1000;
	

//	public int distance;
//	public int killedByStomp;
//	public int killedByFire;
//	public int killedByShell;
//	public int mushroom;
	public int timeSpent;
	public int cellWin;
//	public int win;
//	public int mode;
//	public int coins;
//	public int greenMushroom;
//	public int flowerFire;
//	public int hiddenBlocks;
//    public int timeLeft;
//    public int kills;
//    public int bricks;
//	public int distancePassedCells=0 ;
//	public int distancePassedPhys=0 ;
//	public int flowersDevoured=0 ;
//	public int killsByFire=0 ;
//	public int killsByShell=0;
//	public int killsByStomp=0;
//	public int killsTotal=0;
//	public int marioMode =0;
//	public int mushroomsDevoured=0;
//	public int greenMushroomsDevoured=0;
//	public int coinsGained=0;
//	public int timeLeft=0;
//	public int timeSpent=0;
//	public int hiddenBlocksFound=0;
//	public int collisionsWithCreatures=0;
//	final public int mode = 32;
//	final public int coins = 16;
//	final public int hiddenItems = 24;
//	final public int flowerFire = 64;  // not used for now
//	public int distance = 1;
//	final public int kills = 42;
//	final public int hiddenBlocks = 24;
//	public int win=0;
//	final public int win = 1024;
//	final public int killsByFire = 4;
//	final public int killsByShell = 17;
//	final public int killsByStomp = 12;
//	final public int timeLeft = 8;
	private int marioStatus;

	private int devision(int value , int devisor)
	{
		if(devisor==0)
		return 0;
		else
			return value/devisor;
		
	}
	private int[] normalizeWeights(int[] weights)
	{
		int[] result= new int[weights.length];
		int totalWeights=0;
		for(int i=0;i<weights.length;i++)
		{
			totalWeights += Math.abs(weights[i]);
		}
		for(int i =0 ; i<weights.length;i++)
		{
			result[i]=weightNormalizedScalarValue* (weights[i]/totalWeights); 
		}
		return result;
	}
	public void setWeights(int[] newWeights)
	{
		
//		 W = normalizeWeights(newWeights);
		for(int i=0;i<newWeights.length;i++)
		{
			W[i] = newWeights[i];
			
		}
	}
	public int getWeightsLength()
	{
		return W.length;
	}
	public void printSOV()
	{
		System.out.println("dis"+distance+killedByFire+killedByShell+killedByStomp+collisionWithCreature);
	}
	public MarioPlanetSystemOfValues()
	{
		super();
	}
	public MarioPlanetSystemOfValues(int[] weights)
	{
		this.W = weights;
		distance = W[DISTANCE_PASSED_CELLS];
		killedByFire = W[KILLS_BY_FIRE];
		killedByShell= W[KILLS_BY_SHELL];
		killedByStomp = W[KILLS_BY_STOMP];
		collisionWithCreature = W[COLLISION_WITH_CREATURES];
		timeSpent = W[TIME_SPENT];
		mushroom = W[MUSHROOM_DEVOURED];
		flowerFire = W[FLOWERS_DEVOURED];
		coins = W[COINS];
		win=W[WIN];
        mode= W[MODE];
        hiddenBlock = W[HIDDEN_BLOCKS];
        greenMushroom = W[GREEN_MUSHROOM];
        timeLeft = W[TIME_LEFT];
        kills = W[KILLS];
        bricks=W[BRICKS];
        cellWin=W[CELLWINS];
        
        
        
	}
//	public MarioPlanetSystemOfValues() {
//		// TODO Auto-generated constructor stub
//		this.evalInf= evalInf;
//		distance = W[DISTANCE_PASSED_CELLS];
//		killedByFire = W[KILLS_BY_FIRE];
//		killedByShell= W[KILLS_BY_SHELL];
//		killedByStomp = W[KILLS_BY_STOMP];
//		collisionWithCreature = W[COLLISION_WITH_CREATURES];
//		timeSpent = W[TIME_SPENT];
//		mushroom = W[MUSHROOM_DEVOURED];
//		flowerFire = W[FLOWERS_DEVOURED];
//		win=W[WIN];
//        mode= W[MODE];
//        hiddenBlock = W[HIDDEN_BLOCKS];
//        greenMushroom = W[GREEN_MUSHROOM];
//        timeLeft = W[TIME_LEFT];
//        kills = W[KILLS];
//	}
	
	
}