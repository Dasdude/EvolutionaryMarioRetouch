package marioplanet.environment;

import com.anji.util.Properties;

import ch.idsia.benchmark.tasks.SystemOfValues;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

public class GalaxyRecipe { 
	public static final int  RUNNER = 0;
	public static final int  BRICKER = 1;
	public static final int  FIRE = 2;
	public static final int SHELL = 3;
	public static final int STOMP = 4;
	public static final int SUICIDE = 5;
	public static final int FLOWER = 6;
	public static final int MUSHROOM = 7;
	public static final int KILL = 8;
	public static final int EATER = 9;
	public static final int WIN = 10;
	public static final int UNBIASED = 11;
	
	
	public static final String[] planetNames = {
			
			"runner",
			"bricker",
			"fire",
			"shell",
			"stomp",
			"suicide",
			"flower",
			"mushroom",
			"kill",
			"eater",
			"win",
			"unBiased"
	};
	public static final int[][] weights ;
			
//			"runner",
//			"bricker",
//			
//			"fire",
//			
//			"shell",
//			
//			"stomp",
//		
//			"suicide",
//			
//			"flower",
//		
//			"mushroom",
//			
//			"kill",
//			
//			"eater",
//			
//			"win"
	private static final MarioPlanetSystemOfValues[]  SOV ;
			
	static{
		weights = new int[planetNames.length][MarioPlanetSystemOfValues.weightsLength];
		for(int i=0;i<weights.length;i++)
		{
//			for(int j = 0;j<weights[i].length;j++)
//			{
				weights[i][MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=0;
//			}
		}
		for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
		{
			weights[UNBIASED][i]=0;
		}
		weights[UNBIASED][MarioPlanetSystemOfValues.MODE]=0;
		weights[UNBIASED][MarioPlanetSystemOfValues.TIME_SPENT]=0;
		weights[UNBIASED][MarioPlanetSystemOfValues.TIME_LEFT]=0;
		weights[UNBIASED][MarioPlanetSystemOfValues.WIN]=1000;
		weights[UNBIASED][MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=0;
		weights[UNBIASED][MarioPlanetSystemOfValues.COINS]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.CELLWINS]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.KILLS]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.BRICKS]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=1;
		weights[UNBIASED][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=1;
		SOV = new MarioPlanetSystemOfValues[planetNames.length];
		weights[RUNNER][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
		
//		weights[BRICKER][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
		weights[BRICKER][MarioPlanetSystemOfValues.BRICKS]=100000;
		weights[BRICKER][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=10000;
		weights[BRICKER][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=10000;
		
//		weights[EATER][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//		weights[EATER][MarioPlanetSystemOfValues.BRICKS]=10000;
//		weights[EATER][MarioPlanetSystemOfValues.COINS]=1000;
		weights[EATER][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=10000;
		weights[EATER][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=10000;
		
//		weights[FIRE][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//		weights[FIRE][MarioPlanetSystemOfValues.BRICKS]=10;
//		weights[FIRE][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=10;
//		weights[FIRE][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=10;
		weights[FIRE][MarioPlanetSystemOfValues.KILLS_BY_FIRE]=100;
		
//		weights[STOMP][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//		weights[STOMP][MarioPlanetSystemOfValues.BRICKS]=10;
//		weights[STOMP][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=10;
//		weights[STOMP][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=10;
		weights[STOMP][MarioPlanetSystemOfValues.KILLS_BY_STOMP]=100;
		
		weights[SHELL][MarioPlanetSystemOfValues.KILLS_BY_SHELL]=100;
		
		weights[SUICIDE][MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=100;
//		weights[SUICIDE][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//		weights[KILL][MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//		weights[KILL][MarioPlanetSystemOfValues.BRICKS]=10;
//		weights[KILL][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=10;
//		weights[KILL][MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=10;
		weights[KILL][MarioPlanetSystemOfValues.KILLS]=1000;
		
		weights[FLOWER][MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=100000;
		
		
		for(int i = 0 ;i<planetNames.length;i++)
		{
			SOV[i] = new MarioPlanetSystemOfValues(weights[i]);
		}
		SOV[WIN] = new MarioPlanetSystemOfValues(weights[RUNNER]);
//		SOV[WIN].setWeights(newWeights);
		
	}
	public GalaxyRecipe(int GalaxyNumber) {
		// TODO Auto-generated constructor stub
		this.sov = SOV[GalaxyNumber];
		this.galaxyName= planetNames[GalaxyNumber];
		
	}
	public MarioPlanetSystemOfValues sov;
	public String galaxyName;
	
	
	
}
