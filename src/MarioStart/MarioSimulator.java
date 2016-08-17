package MarioStart;

import java.io.IOException;

import org.jgap.BulkFitnessFunction;
import org.jgap.Chromosome;
import org.jgap.MarioConfiguration;

import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;
import com.anji.neat.Evolver;
import com.anji.neat.MarioFitness;
import com.anji.util.Properties;
import com.anji.util.Randomizer;

import ch.idsia.benchmark.tasks.SystemOfValues;
import ch.idsia.tools.EvaluationInfo;
import marioplanet.environment.GalaxyRecipe;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

public class MarioSimulator {
	int sampleNumber=0;
	public MarioFitness evalFunc;
	public Randomizer randomizer;
	public MarioSimulator(Properties prop,boolean record) {
		// TODO Auto-generated constructor stub
		evalFunc = new MarioFitness();
		evalFunc.init(prop,new MarioConfiguration(prop,new MarioPlanetSystemOfValues(),null));
		
		if(record)
			evalFunc.config.setRecording("on");
		else
			evalFunc.config.setRecording("off");
		evalFunc.config.setVisOption(true);
		this.randomizer = (Randomizer) prop.singletonObjectProperty(Randomizer.class);
	}
	public MarioSimulator(Properties prop,boolean record,GalaxyRecipe gal) {
		// TODO Auto-generated constructor stub
		evalFunc = new MarioFitness();
//		evalFunc.config = new MarioConfiguration(prop,gal.sov,null);
		evalFunc.init(prop,new MarioConfiguration(prop,gal.sov,null));
		if(record)
			evalFunc.config.setRecording("on");
		else
			evalFunc.config.setRecording("off");
		evalFunc.config.setVisOption(true);
		this.randomizer = (Randomizer) prop.singletonObjectProperty(Randomizer.class);
	}
	public void setRecord(String Recordname)
	{
		if(Recordname!="off")
			{
				evalFunc.config.setRecording(Recordname+sampleNumber);
				sampleNumber++;
			}
		
		else
			evalFunc.config.setRecording("off");
	}
	public void nextLevel()
	{
		evalFunc.config.nextLevel();
	}
	public void setDifficulty(int i)
	{
		evalFunc.config.setLevelDifficulty(i);
	}
	public static EvaluationInfo marioSimulate(String propString,Chromosome mario)
	{
		try {
			Properties props = new Properties( propString);
			MarioFitness evalFunc = (MarioFitness)props.singletonObjectProperty("fitness_function");
			evalFunc.config.setVisOption(true);
			evalFunc.runMario(mario);
		} catch (IOException | TranscriberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static EvaluationInfo marioSimulate(Properties prop,Chromosome mario)
	{
			MarioFitness evalFunc = new MarioFitness();
			evalFunc.init(prop);
			evalFunc.config = new MarioConfiguration(prop,new MarioPlanetSystemOfValues(new int[MarioPlanetSystemOfValues.weightsLength]),null);
			evalFunc.config.setVisOption(true);
			try {
				evalFunc.runMario(mario);
			} catch (TranscriberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
	}
	public  EvaluationInfo marioSim(Chromosome mario)
	{
			
			try {
				return evalFunc.runMario(mario);
			} catch (TranscriberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Evolver.main(args);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
