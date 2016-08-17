package marioplanet.environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

import org.apache.log4j.Logger;
import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.MarioConfiguration;
import org.jgap.Specie;

import com.anji.Copyright;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.neat.Evolver;
import com.anji.neat.MarioFitness;
import com.anji.neat.NeatActivator;
import com.anji.neat.NeatConfiguration;
import com.anji.nn.ActivationFunctionFactory;
import com.anji.util.Properties;

import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.scenarios.Main;
import marioplanet.agents.MarioAgent;
import marioplanet.agents.MarioPopulation;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

public class Planet extends Galaxy{

	public Planet(GalaxyRecipe gR, Vector<Galaxy> galaxies, Properties prop) throws Exception {
		super(gR, galaxies, prop);
		// TODO Auto-generated constructor stub
	}
	// Global Parameters
//	
//	public static boolean DEBUG = true;
//	
//	private static Logger logger = Logger.getLogger( Evolver.class );
//	
//	private Evolver evolver;
//	private MarioFitness bulkFunction;
//	private NeatConfiguration config;
//	private Properties props;
//	private MarioConfiguration mConfig;
//	private String planetName;
//	
//	Planet(Properties props,String planetName,MarioPlanetSystemOfValues sov) throws Throwable
//	{
//		super(planetName,sov,new Vector<Galaxy>(0),props);
//	}
////	public Planet(String configurationFileName,String planetName) throws Throwable
////	{
////		super(planetName,new MarioPlanetSystemOfValues());
////		this.planetName = planetName;
////		UniverseStateVariables.planetName = planetName;
////		try {
////			System.out.println( Copyright.STRING );
////	
////			if ( configurationFileName== null ) {
////				usage();
////				System.exit( -1 );
////			}
////			props = new Properties( configurationFileName);
////			mConfig = new MarioConfiguration(props);
////			props.setProperty("persistence.base.dir",props.getProperty("persistence.base.dir")+UniverseStateVariables.runNumber+"/"+planetName+"/Difficulty"+mConfig.getLevelDifficulty() );
////			String baseDirectoryPathString = props.getProperty("persistence.base.dir");
////			File file = new File(props.getProperty("persistence.base.dir"));
////			file.mkdirs();
////			props.setProperty("id.file",baseDirectoryPathString+"/id.xml");
////			props.setProperty("neat.id.file",baseDirectoryPathString+"/neatid.xml");
////			props.setProperty("presentation.dir",props.getProperty("persistence.base.dir")+"/"+props.getProperty("presentation.dir"));
////			config = new NeatConfiguration(props);
////			BulkFitnessFunction a = (BulkFitnessFunction)props.singletonObjectProperty("fitness_function");
////			config.setBulkFitnessFunction(a);
////			bulkFunction = (MarioFitness)a;
////			bulkFunction.init(props,mConfig);
////			evolver = new Evolver();
////			evolver.init( props,mConfig );
////			
////		}
////		catch ( Throwable th ) {
////			Evolver.getLogger().error( "", th );
////			throw th;
////		}
////	}
////	public Planet(Properties props,String planetName) throws Throwable
////	{
////		this.planetName = planetName;
////		UniverseStateVariables.planetName = planetName;
////		this.props = props;
////		mConfig = new MarioConfiguration(props);
////		String baseDirectoryPathString = props.getProperty("persistence.base.dir");
////		props.setProperty("id.file",baseDirectoryPathString+UniverseStateVariables.runNumber+"/id.xml");
////		props.setProperty("neat.id.file",baseDirectoryPathString+UniverseStateVariables.runNumber+"/neatid.xml");
////		props.setProperty("persistence.base.dir",props.getProperty("persistence.base.dir")+UniverseStateVariables.runNumber+"/"+planetName+"/Difficulty"+mConfig.getLevelDifficulty());
////		baseDirectoryPathString = props.getProperty("persistence.base.dir");
////		File file = new File(props.getProperty("persistence.base.dir")); 
////		if(!file.mkdirs())
////		{
////			System.out.println(file.getAbsolutePath());
////			throw new IOException("Folder cannot be created");
////		}
////		props.setProperty("presentation.dir",props.getProperty("persistence.base.dir")+"/"+props.getProperty("presentation.dir"));
////		config = new NeatConfiguration(props);
////		BulkFitnessFunction a = (BulkFitnessFunction)props.singletonObjectProperty("fitness_function");
////		config.setBulkFitnessFunction(a);
////		bulkFunction = (MarioFitness)a;
////		bulkFunction.init(props,mConfig);
////		evolver = new Evolver();
////		evolver.init( props,mConfig );
////			
////	}
////	public void marioSimulate(Vector<MarioAgent> agents)
////	{
////		Iterator a = agents.iterator();
////		MarioAgent b = null;
////		while(a.hasNext())
////		{
////			b = (MarioAgent)a.next();
////			if(b==null)
////			{
////				System.out.println("Planet : There is a bug . agent is null");
////				break;
////			}
////			else
////			{
////				bulkFunction.config.setVisOption(true);
////				try {
////					bulkFunction.runMario(b);
////				} catch (TranscriberException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////			}
////		}
////	}
////	public void marioSimulate(Vector<MarioAgent> agents,int generation)
////	{
////		Iterator a = agents.iterator();
////		int specieSize = agents.size();
////		int count=-1;
////		MarioAgent b = null;
////		while(a.hasNext())
////		{
////			count++;
////			b = (MarioAgent)a.next();
////			if(b==null)
////			{
////				System.out.println("Planet : There is a bug . agent is null");
////				
////			}
////			else
////			{
////				b.updateName(generation);
////			//EHSAN change specie visualisation
////				System.out.println("Planet: MarioSimulate: Specie elite Sim: "+count+" out of "+specieSize);
////				bulkFunction.config.setVisOption(true);
////				try {
////					bulkFunction.runMario(b);
////				} catch (TranscriberException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////			}
////		}
////	}
////	public void runSimulationInit()
////	{
////		evolver.runInit();
////	}
////	public void runSimulation(int numberOfGenerations)
////	{
////			
////			if(numberOfGenerations>0)
////			{
////				for(int i =0;i<numberOfGenerations;i++)
////				{
////					evolver.run(1);
////					System.out.println("generation" + i +" ENDS im in Planet"); 
////					UniverseStateVariables.generationNumber++;
////					mConfig.nextLevel();
////					if(UniverseStateVariables.endRun)
////						break;
////				}
////			}
////			else
////			{
////				try {
////					evolver.run();
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////			}
////		evolver.runConclusion();
////		UniverseStateVariables.generationNumber=0;
////			
////	}
////	public void simulationInit()
////	{
////		evolver.runInit();
////		
////	}
////	public void simulationConclusion()
////	{
////		evolver.runConclusion();
////	}
//	private static void usage() {
//		System.err.println( "usage: <cmd> <properties-file>" );
//	}
//	public static int getRunNumber(Properties props)
//	{
//		for(int i=0;;i++)
//		 {
//			 
//			 if(Files.notExists(Paths.get(props.getProperty("persistence.base.dir")+i), LinkOption.NOFOLLOW_LINKS))
//			 {
//				 return i;
//			 }
//			 
//		 }
//		
//	}
////	public static void EvolveSingleLevel() throws IOException
////	{
////		Properties props= new Properties("mario.properties");
////		if(UniverseStateVariables.runNumber<0)
////		 {
////			 for(int i=0;;i++)
////			 {
////				 
////				 if(Files.notExists(Paths.get(props.getProperty("persistence.base.dir")+i), LinkOption.NOFOLLOW_LINKS))
////				 {
////					 UniverseStateVariables.runNumber=i;
////					 break;
////				 }
////				 
////			 }
////		 }
////		try 
////		{
////			int[] weights = new int[MarioPlanetSystemOfValues.getWeightsLength()];
////			weights[0]=10;
////			Planet marion = null;
////			marion = new Planet(props,"General-Optimized on one level");
////			marion.runSimulationInit();
////			marion.runSimulation(marion.props.getIntProperty("num.generations"));
////			
////		}catch(Throwable e)
////		{
////			e.printStackTrace();
////			return;
////		}
////			
////		
////	}
//	public static void treeLearning() throws IOException
//	{
//		Properties props= new Properties("mario.properties");
//		if(UniverseStateVariables.runNumber<0)
//		 {
//			 for(int i=0;;i++)
//			 {
//				 
//				 if(Files.notExists(Paths.get(props.getProperty("persistence.base.dir")+i), LinkOption.NOFOLLOW_LINKS))
//				 {
//					 UniverseStateVariables.runNumber=i;
//					 break;
//				 }
//				 
//			 }
//		 }
//		try 
//		{
//			String[] planetNames = 
//			{
//				"run",
//				"Eater",
//				"Killer",
//			};
//			int[] planetWeightsIndex={MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS,
//			                          MarioPlanetSystemOfValues.MUSHROOM_DEVOURED,
//			                          MarioPlanetSystemOfValues.KILLS_BY_FIRE,
//			                          MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES};
//			int[] weights = new int[MarioPlanetSystemOfValues.getWeightsLength()];
//			weights[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=1;
//			String parentBaseDir=null;
//			Planet marion = null;
//			marion = new Planet(props,"run");
//			marion.runSimulationInit();
//			for(int levelDifficulty=0;levelDifficulty<2;levelDifficulty++)
//			{
//				props = new Properties("mario.properties");
//				props.setProperty(MarioPropertiesKeys.MARIO_DIFFICULTY, Integer.toString(levelDifficulty));
//				marion.mConfig.setLevelDifficulty(levelDifficulty);
//				MarioPlanetSystemOfValues.setWeights(weights);
//				marion.runSimulationInit();
//				marion.evolver.loadGenotype(parentBaseDir);
//				marion.runSimulation(marion.props.getIntProperty("num.generations"));
//				parentBaseDir = marion.props.getProperty(MarioPropertiesKeys.BASE_DIR);
//			
//			}
//			String runBaseDir = parentBaseDir;
//			weights[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=0;
//			//EATER LEarning
//				parentBaseDir = runBaseDir;
//				weights[planetWeightsIndex[1]]=10;
//				weights[planetWeightsIndex[1]+1]=10;
//				for(int levelDifficulty=0;levelDifficulty<2;levelDifficulty++)
//				{
//					props = new Properties("mario.properties");
//					props.setProperty(MarioPropertiesKeys.MARIO_DIFFICULTY, Integer.toString(levelDifficulty));
//					marion.mConfig.setLevelDifficulty(levelDifficulty);
//					MarioPlanetSystemOfValues.setWeights(weights);						
//					marion = new Planet(props, planetNames[1]);
//					marion.mConfig.
//					marion.runSimulationInit();
//					marion.evolver.loadGenotype(parentBaseDir);
//					marion.runSimulation(marion.props.getIntProperty("num.generations"));
//					parentBaseDir = marion.props.getProperty(MarioPropertiesKeys.BASE_DIR);
//				}
//				weights[planetWeightsIndex[1]]=0;
//				weights[planetWeightsIndex[1]+1]=0;
//				runBaseDir = parentBaseDir;
//				
//				weights[planetWeightsIndex[2]]=10;
//				weights[planetWeightsIndex[2]+1]=10;
//				weights[planetWeightsIndex[2]+2]=10;
//				for(int levelDifficulty=0;levelDifficulty<2;levelDifficulty++)
//				{
//					props = new Properties("mario.properties");
//					props.setProperty(MarioPropertiesKeys.MARIO_DIFFICULTY, Integer.toString(levelDifficulty));
//					marion.mConfig.setLevelDifficulty(levelDifficulty);
//					MarioPlanetSystemOfValues.setWeights(weights);						
//					marion = new Planet(props, planetNames[2]);
//					marion.runSimulationInit();
//					marion.evolver.loadGenotype(parentBaseDir);
//					marion.runSimulation(marion.props.getIntProperty("num.generations"));
//					parentBaseDir = marion.props.getProperty(MarioPropertiesKeys.BASE_DIR);
//				}
//				weights[planetWeightsIndex[1]]=0;
//				weights[planetWeightsIndex[2]]=0;
//				runBaseDir = parentBaseDir;
//			
//			
//		
//		} catch (Throwable e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	public static void planetLearn(int[] weights , String loadDirectory, String planetName ,int difficulty) throws Throwable
//	{
//		Properties 	props = new Properties("mario.properties");
//		Planet marion;
//		if(UniverseStateVariables.runNumber<0)
//		 {
//			 for(int i=0;;i++)
//			 {
//				 
//				 if(Files.notExists(Paths.get(props.getProperty("persistence.base.dir")+i), LinkOption.NOFOLLOW_LINKS))
//				 {
//					 UniverseStateVariables.runNumber=i;
//					 break;
//				 }
//				 
//			 }
//		 }
//		for(int levelDifficulty=0;levelDifficulty<2;levelDifficulty++)
//		{
//			props = new Properties("mario.properties");
//			props.setProperty(MarioPropertiesKeys.MARIO_DIFFICULTY, Integer.toString(levelDifficulty));
//			MarioPlanetSystemOfValues.setWeights(weights);						
//			marion = new Planet(props, planetName);
//			marion.runSimulationInit();
//			marion.evolver.loadGenotype(loadDirectory);
//			marion.runSimulation(marion.props.getIntProperty("num.generations"));
//			loadDirectory = marion.props.getProperty(MarioPropertiesKeys.BASE_DIR);
//		}
//	}
//	public static void main(String[] args) throws Throwable 
//	{
//		treeLearning();
////		int[] weights = new int[MarioPlanetSystemOfValues.getWeightsLength()];
////		int[] planetWeightsIndex={MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS,
////                MarioPlanetSystemOfValues.MUSHROOM_DEVOURED,
////                MarioPlanetSystemOfValues.KILLS_BY_FIRE,
////                MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES};
////		weights[planetWeightsIndex[0]]=1;
////		weights[planetWeightsIndex[1]]=10;
////		weights[planetWeightsIndex[1]+1]=10;
////		planetLearn(weights, "./db/CrazyMarioRun0/run/Difficulty1", "Eater", 1);
//		
//	}
}
