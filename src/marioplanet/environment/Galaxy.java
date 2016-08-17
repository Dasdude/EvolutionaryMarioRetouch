package marioplanet.environment;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.MarioConfiguration;

import com.anji.neat.Evolver;
import com.anji.neat.MarioFitness;
import com.anji.neat.NeatConfiguration;
import com.anji.util.Properties;

import marioplanet.agents.MarioAgent;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

public class Galaxy {
	//Persistance
	final String baseDirPath;
	//Each Galaxy is set of evolved NN that get connected by a controller Unit NN
	public Properties props;
	private String galaxyName;
	public Vector<Galaxy> galaxies;
	private final MarioPlanetSystemOfValues sov;
	private Evolver evolver;
	private MarioFitness bulkFunction;
	MarioConfiguration mConfig;
	NeatConfiguration config;
	public String getName()
	{
		return this.galaxyName;
	}
	public MarioPlanetSystemOfValues getSOV()
	{
		return sov;
	}
	//
	public MarioAgent getFittestAgent()
	{
		return evolver.getChamp().agent;
	}
//	public Galaxy(String galaxyName,MarioPlanetSystemOfValues sov,Vector<Galaxy> galaxies,Properties prop) throws Exception
//	{
//		this.sov=sov;
//		this.galaxyName = galaxyName;
//		if(galaxies!=null)
//			this.galaxies.addAll(galaxies);
//		else
//			new Vector<Galaxy>(0);
//		this.props=new Properties(prop);
//		baseDirPath = props.getProperty("persistence.base.dir")+UniverseStateVariables.runNumber+"/"+this.galaxyName;
//		props.setProperty("presentation.dir",baseDirPath+"/"+props.getProperty("presentation.dir"));
//		props.setProperty("persistence.base.dir", baseDirPath);
//		File file = new File(props.getProperty("persistence.base.dir")); 
//		if(!file.mkdirs())
//		{
//			System.out.println(file.getAbsolutePath());
//			throw new IOException("Folder cannot be created");
//		}
//		mConfig = new MarioConfiguration(props,sov,this);
//		config = new NeatConfiguration(this.props);
//		BulkFitnessFunction a = (BulkFitnessFunction)props.singletonObjectProperty("fitness_function");
//		config.setBulkFitnessFunction(a);
//		bulkFunction = (MarioFitness)a;
//		bulkFunction.init(props,mConfig);
//		evolver = new Evolver();
//		evolver.init(this.props, this.mConfig);
//	}
	public Galaxy(GalaxyRecipe gR,Vector<Galaxy> galaxies,Properties prop) throws Exception
	{
		this.sov=gR.sov;
		this.galaxyName = gR.galaxyName;
		
		if(galaxies!=null)
			this.galaxies=new Vector<Galaxy>(galaxies);
		else
			new Vector<Galaxy>(0);
//		this.props=new Properties(prop);
		setProp(prop);
		baseDirPath = props.getProperty("persistence.base.dir")+UniverseStateVariables.runNumber+"/"+this.galaxyName;
		props.setProperty("presentation.dir",baseDirPath+"/"+props.getProperty("presentation.dir"));
		props.setProperty("persistence.base.dir", baseDirPath);
		File file = new File(props.getProperty("persistence.base.dir")); 
		if(!file.mkdirs())
		{
			System.out.println(file.getAbsolutePath());
			throw new IOException("Folder cannot be created");
		}
		mConfig = new MarioConfiguration(props,sov,this);
		config = new NeatConfiguration(this.props);
		BulkFitnessFunction a = (BulkFitnessFunction)props.singletonObjectProperty("fitness_function");
		config.setBulkFitnessFunction(a);
		bulkFunction = (MarioFitness)a;
		bulkFunction.init(props,mConfig);
		evolver = new Evolver();
		evolver.init(this.props, this.mConfig);
		UniverseStateVariables.setVariables(this);
	}
	public void loadGenotype(String loadDirectory) throws InvalidConfigurationException
	{
		this.evolver.loadGenotype(loadDirectory);
		System.out.println(loadDirectory);
	}
	public void loadGenotype(String loadDirectory,String GalaxyName) throws InvalidConfigurationException
	{
		this.evolver.loadGenotype(loadDirectory+"/"+GalaxyName);
		System.out.println(loadDirectory+"/"+this.galaxyName);
	}
	private void setProp(Properties prop)
	{
		this.props = new Properties(prop);
		adjustStimSize();
	}
	private void adjustStimSize()
	{
		if(galaxies==null)
			return;
//		if(galaxies.size()>=2)
//		{
//			
//		this.props.setProperty(MarioPropertiesKeys.STIM_SIZE, 
//									Integer.toString
//									(
//														
//														galaxies.size()
//									)
//							  );
//		}
		if(galaxies.size()!=0)
		{
			
		this.props.setProperty(MarioPropertiesKeys.STIM_SIZE, 
									Integer.toString
									(
														this.props.getIntProperty(MarioPropertiesKeys.STIM_SIZE)+
														6*galaxies.size()
									)
							  );
		}
	}
	public void evolve(int totalGeneration)
	{
		evolver.runInit();
		evolver.run(totalGeneration);
		evolver.runConclusion();
	}
	
	
	

}
