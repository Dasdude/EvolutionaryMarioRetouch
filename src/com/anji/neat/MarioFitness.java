package com.anji.neat;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jfree.data.function.PowerFunction2D;
import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.MarioConfiguration;
import org.jgap.MyCustumBulkFunction;

import com.anji.MarioAgents.Esario;
import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.ErrorFunction;
import com.anji.integration.TargetFitnessFunction;
import com.anji.integration.TranscriberException;
import com.anji.util.Properties;
import com.anji.util.Randomizer;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.tasks.BasicTask;
import ch.idsia.benchmark.tasks.MarioSystemOfValues;
import ch.idsia.benchmark.tasks.SystemOfValues;
import ch.idsia.scenarios.Main;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.tools.MarioAIOptions;
import marioplanet.agents.MarioAgent;
import marioplanet.environment.GalaxyRecipe;
import marioplanet.environment.UniverseStateVariables;
import marioplanet.evaluation.MarioPlanetSystemOfValues;
//EHSAN my function
public class MarioFitness extends TargetFitnessFunction{
	
//	public ActivatorTranscriber activatorFactory;
	private final static boolean SUM_OF_SQUARES = false;
	private final static int MAX_FITNESS = 160000000;
	public MarioConfiguration config;
	public Randomizer randomizer;
//	public Properties prop;
	private Properties propc;
	
	public void testPrint()
	{
		//EHSAN sysout
		System.out.println("Hi i am Mario Fitness");
	}
//	public void init( Properties newProps ) {
//		try {
//			super.init( newProps );
//			activatorFactory = (ActivatorTranscriber) newProps.singletonObjectProperty( ActivatorTranscriber.class );
//			ErrorFunction.getInstance().init( newProps );
//			setMaxFitnessValue( MAX_FITNESS );
//			config = new MarioConfiguration(newProps);
//		}
//		catch ( Exception e ) {
//			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString()
//					+ ": " + e.getMessage() );
//		}
//	}
	public void init( Properties newProps,MarioConfiguration mConf ) {
		try {
			
			super.init( newProps );
			this.propc=newProps;
//			activatorFactory = (ActivatorTranscriber) newProps.singletonObjectProperty( ActivatorTranscriber.class );
//			super.activatorFactory=activatorFactory;
			this.randomizer = (Randomizer) newProps.singletonObjectProperty(Randomizer.class);
			ErrorFunction.getInstance().init( newProps );
			setMaxFitnessValue( MAX_FITNESS );
			config = mConf;
		}
		catch ( Exception e ) {
			throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString()
					+ ": " + e.getMessage() );
		}
	}
	protected int calculateErrorFitness(double marioResultParameters, double minResponse, double maxResponse) {
		// TODO Auto-generated method stub
		return 0;
	}
	//EHSAN TODO write Evaluation Function
	public int  evaluate(EvaluationInfo marioResults)
	{
		return 0;
	}
	@Override
	public void evaluate(List genotypes)
	{
		EvaluationInfo marioResults;
		System.out.println("entering my evaluation");
		Iterator it = genotypes.iterator();
		int count = -1;
		while ( it.hasNext() ) 
		{
			count ++;
			Chromosome genotype = (Chromosome) it.next();
			
			if (genotype==null)
			{
				it.remove();
				continue;
			}
			genotype.galaxyHome = config.galaxyHome;
			System.out.println("Mario Fitness: Evaluate Genotype "+count );
			System.out.println("Mario Fitness: Generation "+UniverseStateVariables.generationNumber );

			try
			{
				this.config.setVisOption(config.isAllVisual());
				//EHSAN setiing recording for midterm
				this.config.configPassedtoNextMario();
				EvaluationInfo tempEval=new EvaluationInfo();
				EvaluationInfo trueEval = new EvaluationInfo();
				tempEval.setZero();
				trueEval.setZero();
//				int fitness=0;
				int f=0;
				for(int i = 0;i<config.totalLevelPerTest;i++)
				{
					marioResults= runMario(genotype);
					if(marioResults!=null)
						{
							f=marioResults.computeWeightedFitness(new SystemOfValues());
							trueEval.add(marioResults);
							tempEval.add(marioResults.computeWeightedEval(config.getSOV()));
							System.out.println("Genotype: "+count+"Test "+ i+" Fitness: "+f);
						}
				}
				 tempEval.devide(config.totalLevelPerTest);
				 trueEval.devide(config.totalLevelPerTest);
				 genotype.lastEval=tempEval;
				 genotype.averageEval=trueEval;
				this.config.configPassedtoNextMario();
//				if(fitness!=-1)
//				fitness=fitness/config.totalLevelPerTest;
//				//EHSAN i changed this for the midterm
				//TODO for final
				genotype.setFitnessValue( f+1);
				
				System.out.println(UniverseStateVariables.planetName);
			}
			catch(Exception a)
			{
				a.printStackTrace();
			}
			
		}
		//TODO EHSAN change this if you dont want normal Fitness
		fintnessReEvaluatePDF(genotypes);
		
	}
	private void fintnessReEvaluatePDF(List population) throws NullPointerException
	{
		int individualIndex=0;
		Iterator it = population.iterator();
		double[] evaluationArray= new double[((Chromosome)population.get(0)).lastEval.getResultsdouble().length];
		double[][] populationEvaluationArray = new double[population.size()][evaluationArray.length];
		double[][] PDF = new double[evaluationArray.length][population.size()];
		double[][] normalizedPDF;
		double[] fitnessVector;
		double minFitness=0;
		
		while( it.hasNext())
		{
			
			Chromosome genotype = (Chromosome) it.next();
			
			evaluationArray = genotype.lastEval.getResultsdouble();
			for (int i=0;i<evaluationArray.length;i++)
			{
				PDF[i][individualIndex]=evaluationArray[i]/evaluationArray[MarioPlanetSystemOfValues.CELLWINS];
			}
			individualIndex++;
		}
		normalizedPDF = normalizePdf(PDF);
		fitnessVector=calcFitnessUsingPDF(normalizedPDF);
		minFitness=Double.MAX_VALUE;
		for(int indIndex = 0; indIndex<population.size();indIndex++)
		{
			if(fitnessVector[indIndex]<=minFitness&&fitnessVector[indIndex]!=0)
			{
				minFitness = fitnessVector[indIndex];
			}
			
		}
		for(int indIndex = 0; indIndex<population.size();indIndex++)
		{
			fitnessVector[indIndex] =  (10*fitnessVector[indIndex]/minFitness)+1;
			System.out.println(fitnessVector[indIndex]);
		}
		it = population.iterator();
		individualIndex=0;
		while( it.hasNext())
		{
			
			Chromosome genotype = (Chromosome) it.next();
			genotype.setFitnessValue((int)fitnessVector[individualIndex]);

		}
		
		
	}
	private double[] calcFitnessUsingPDF(double [][] pdf)
	{
		int popSize = pdf[0].length;
		int rvSize = pdf.length;
		double[] fitness = new double[pdf[0].length];
		for(int inindex=0;inindex<popSize;inindex++)
		{
			int max = 0;
			for(int rvindex=0;rvindex<rvSize;rvindex++)
			{
				if(pdf[rvindex][inindex]>max&&rvindex!=MarioPlanetSystemOfValues.CELLWINS)
				fitness[inindex]=pdf[rvindex][inindex];
			}
		}
		return fitness;
	}
	private double[][] normalizePdf(double[][] pdf)
	{
		double[][] npdf = new double[pdf.length][pdf[0].length];
		double sum =0;
		for(int rvIndex=0;rvIndex<pdf.length;rvIndex++)
		{
			for(int individualIndex=0;individualIndex<pdf[rvIndex].length;individualIndex++)
			{
				sum = pdf[rvIndex][individualIndex]+sum;
			}
			if(sum!=0)
			{
				for(int individualIndex=0;individualIndex<pdf[rvIndex].length;individualIndex++)
				{
					npdf[rvIndex][individualIndex]=pdf[rvIndex][individualIndex]/sum;
				}				
			}
			sum=0;
			
		}
		
		
		return npdf;
	}
	private void fitnessReEvaluate4(List genotypes) throws NullPointerException
	{
		Iterator it = genotypes.iterator();
		
		int[] max = new int[MarioPlanetSystemOfValues.weightsLength];
		int[] resultInt;
		int cellWin;
		while ( it.hasNext() ) 
		{
				
			try{
				Chromosome genotype = (Chromosome) it.next();
				resultInt = genotype.lastEval.getResultsInt();
				cellWin = genotype.lastEval.cellWins;
				System.out.println(cellWin);
				if(cellWin<=0)
					cellWin=Integer.MAX_VALUE;
					for(int i =0;i<MarioPlanetSystemOfValues.weightsLength;i++)
					{
						if(resultInt[i]/cellWin*genotype.getAlleles().size()>max[i])
							max[i]=resultInt[i]/(cellWin*genotype.getAlleles().size());
					}
				}
			catch(NullPointerException|ArithmeticException e)
			{
				e.printStackTrace();
			}

			
		}
		int count=0;
		Iterator b = genotypes.iterator();
		while ( b.hasNext() ) 
		{
			Chromosome genotype = (Chromosome) b.next();
			try
			{
				
				int maxFeature=0;
				 resultInt = genotype.lastEval.getResultsInt();
				 cellWin =genotype.lastEval.cellWins;
				 if(cellWin<=0)
				 {
					 cellWin =Integer.MAX_VALUE;
					 genotype.lastEval.cellWins=Integer.MAX_VALUE;
				 }
	
				for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
				{
					
					if(max[i]==0|| cellWin==Integer.MAX_VALUE|| max[i]*cellWin<=0)
						resultInt[i]=0;
					else
						resultInt[i]=(10000*resultInt[i])/(max[i]*cellWin*genotype.getAlleles().size());
				}
				int maxindex=0;
				for(int j=0;j<MarioPlanetSystemOfValues.weightsLength;j++)
				{
					if(resultInt[j]>resultInt[maxindex])
						maxindex=j;
				}
				genotype.champIndex=maxindex;
				System.out.println(resultInt[MarioPlanetSystemOfValues.CONSTANT]);
						maxFeature = resultInt[maxindex];
	
				
				genotype.setFitnessValue(maxFeature);
				if(maxFeature==0)
					genotype.setFitnessValue(1);
				System.out.println("Genotype "+count+":Fitness "+genotype.getFitnessValue());
				System.out.println("Genotype "+count+": "+genotype.lastEval.toStringSingleLineShortForm());
				count++;
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				genotype.setFitnessValue(0);
				System.out.println("Genotype: "+genotype);
				System.out.println("GenotypeNumber: "+count);
				count++;
			}
		}
	}
	private void fitnessReEvaluate2(List genotypes)
	{

		Iterator it = genotypes.iterator();
		int[] max = new int[MarioPlanetSystemOfValues.weightsLength];
		while ( it.hasNext() ) 
		{
				
				Chromosome genotype = (Chromosome) it.next();
					
					for(int i =0;i<MarioPlanetSystemOfValues.weightsLength;i++)
					{
						try{
						if(genotype.lastEval.getResultsInt()[i]>max[i])
							max[i]=genotype.lastEval.getResultsInt()[i];
						}
						catch(NullPointerException e)
						{
							e.printStackTrace();
						}
					}
					
			
		}
		int count=0;
		Iterator b = genotypes.iterator();
		while ( b.hasNext() ) 
		{
			Chromosome genotype = (Chromosome) b.next();
			try{
				
			int maxFeature=0;
			for(int i =0;i<MarioPlanetSystemOfValues.weightsLength;i++)
			{
				
				if(max[i]!=0)
					{
					genotype.lastEval.getResultsInt()[i]=(10000*genotype.lastEval.getResultsInt()[i])/max[i];
					
					if(maxFeature<(10000*genotype.lastEval.getResultsInt()[i])/max[i])
						maxFeature=(10000*genotype.lastEval.getResultsInt()[i])/max[i];
					}
				
			}
			genotype.setFitnessValue(maxFeature);
			System.out.println("Genotype "+count+":Fitness "+maxFeature);
			

			count++;
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				genotype.setFitnessValue(0);
				System.out.println("Genotype: "+genotype);
				System.out.println("GenotypeNumber: "+count);
				count++;
			}
		}

	}
	private void fitnessReEvaluate3(List genotypes) throws NullPointerException
	{
		Iterator it = genotypes.iterator();
		
		int[] max = new int[MarioPlanetSystemOfValues.weightsLength];
		int[] resultInt;
		int cellWin;
		while ( it.hasNext() ) 
		{
				
			try{
				Chromosome genotype = (Chromosome) it.next();
				resultInt = genotype.lastEval.getResultsInt();
//				System.out.println(resultInt[MarioPlanetSystemOfValues.CONSTANT]);
				cellWin = genotype.lastEval.cellWins;
				System.out.println(cellWin);
				if(cellWin<=0)
					cellWin=2147483647;
					for(int i =0;i<MarioPlanetSystemOfValues.weightsLength;i++)
					{
						if(resultInt[i]/cellWin>max[i])
							max[i]=resultInt[i]/cellWin;
					}
				}
			catch(NullPointerException|ArithmeticException e)
			{
				e.printStackTrace();
			}

			
		}
		int count=0;
		Iterator b = genotypes.iterator();
		while ( b.hasNext() ) 
		{
			Chromosome genotype = (Chromosome) b.next();
			try{
				
			int maxFeature=0;
//			int[] featureWeightArray= new int[MarioPlanetSystemOfValues.weightsLength];
			 resultInt = genotype.lastEval.getResultsInt();
			 cellWin =genotype.lastEval.cellWins;
			 if(cellWin<=0)
			 {
				 cellWin =Integer.MAX_VALUE;
				 genotype.lastEval.cellWins=Integer.MAX_VALUE;
			 }
//			int temp;
//			System.out.println("Average Cell Wins"+resultInt[MarioPlanetSystemOfValues.CELLWINS]);
			for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
			{
				
				if(max[i]==0|| cellWin==Integer.MAX_VALUE|| max[i]*cellWin<=0)
					resultInt[i]=0;
				else
					resultInt[i]=(1000*resultInt[i])/(max[i]*cellWin);
//				if(resultInt[i]<0)
//					resultInt[i]=1000+resultInt[i];
				
			}
				int maxindex=0;
				for(int j=0;j<MarioPlanetSystemOfValues.weightsLength;j++)
				{
					if(resultInt[j]>resultInt[maxindex])
						maxindex=j;
				}
				genotype.champIndex=maxindex;
//					if(resultInt[i]>=0)
				System.out.println(resultInt[MarioPlanetSystemOfValues.CONSTANT]);
						maxFeature = resultInt[maxindex];
//					else
//					{
//						maxFeature += (10000+resultInt[i]);
//					}
		
//			System.out.println();
//			resultInt[MarioPlanetSystemOfValues.WIN]=resultInt[MarioPlanetSystemOfValues.WIN]*2;
//			for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
//			{
//				
//						maxFeature += Math.pow((resultInt[i]),1);
//			}
//			int CellWinFeature = genotype.lastEval.cellWins;
			
			genotype.setFitnessValue(maxFeature);
			if(maxFeature==0)
				genotype.setFitnessValue(1);
			System.out.println("Genotype "+count+":Fitness "+genotype.getFitnessValue());
			System.out.println("Genotype "+count+": "+genotype.lastEval.toStringSingleLineShortForm());

			count++;
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				genotype.setFitnessValue(0);
				System.out.println("Genotype: "+genotype);
				System.out.println("GenotypeNumber: "+count);
				count++;
			}
		}
	}
	private void fitnessReEvaluate(List genotypes) throws NullPointerException
	{
		Iterator it = genotypes.iterator();
		int[] max = new int[MarioPlanetSystemOfValues.weightsLength];
		while ( it.hasNext() ) 
		{
				
				Chromosome genotype = (Chromosome) it.next();
					
					for(int i =0;i<MarioPlanetSystemOfValues.weightsLength;i++)
					{
						try{
						if(genotype.lastEval.getResultsInt()[i]>max[i])
							max[i]=genotype.lastEval.getResultsInt()[i];
						else
						{
							if(genotype.lastEval.getResultsInt()[i]<0)
							{
								if(-genotype.lastEval.getResultsInt()[i]>max[i])
									max[i]=-genotype.lastEval.getResultsInt()[i];
							}
						}
						}
						catch(NullPointerException e)
						{
							e.printStackTrace();
						}
					}

			
		}
		int count=0;
		Iterator b = genotypes.iterator();
		while ( b.hasNext() ) 
		{
			Chromosome genotype = (Chromosome) b.next();
			try{
				
			int maxFeature=0;
			int[] featureWeightArray= new int[MarioPlanetSystemOfValues.weightsLength];
			int[] resultInt = genotype.lastEval.getResultsInt();
//			resultInt[MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=-resultInt[MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES];
			int temp;
			for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
			{
				if(max[i]!=0)
					resultInt[i]=(1000*resultInt[i])/max[i];
				if(resultInt[i]<0)
					resultInt[i]=1000+resultInt[i];
				resultInt[i]=(resultInt[i]*(1000*genotype.lastEval.cellWins)/config.getLevelLength())/1000;
//				System.out.print(GalaxyRecipe.planetNames[i]+":"+resultInt[i]+"; ");
				
			}
			System.out.println();
			resultInt[MarioPlanetSystemOfValues.WIN]=resultInt[MarioPlanetSystemOfValues.WIN]*2;
			for(int i=0;i<MarioPlanetSystemOfValues.weightsLength;i++)
			{
				int maxindex=i;
				for(int j=i;j<MarioPlanetSystemOfValues.weightsLength;j++)
				{
					if(resultInt[j]>resultInt[i])
						maxindex=j;
				}
				temp=resultInt[i];
				resultInt[i]=resultInt[maxindex];
				resultInt[maxindex]=temp;
//					if(resultInt[i]>=0)
				
						maxFeature += Math.pow((resultInt[i]),1);
//					else
//					{
//						maxFeature += (10000+resultInt[i]);
//					}
			}
			
			genotype.setFitnessValue(maxFeature+((genotype.lastEval.cellWins*10000)/this.config.getLevelLength()));
			System.out.println("Genotype "+count+":Fitness "+maxFeature);

			count++;
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				genotype.setFitnessValue(0);
				System.out.println("Genotype: "+genotype);
				System.out.println("GenotypeNumber: "+count);
				count++;
			}
		}
	}
	public void activate(Object subject) throws TranscriberException {
		// TODO Auto-generated method stub
		this.config.setVisOption(true);
		runMario((Chromosome)subject);
	}
	
	public EvaluationInfo runMario(Chromosome c ) throws TranscriberException
	{ 	
			MarioAgent a = new MarioAgent(c);
			if(a.randomizer==null)
			{
				a.randomizer = (Randomizer) this.propc.singletonObjectProperty(Randomizer.class);
			}
			
			return runMario(a);
	}
	public EvaluationInfo runMario(MarioAgent mario) throws TranscriberException
	{
		Activator activator;
			activator = this.activatorFactory.newActivator(mario.getDNA());
			mario.setActivator(activator);
			return marioRunner( mario);
	}
//	protected void runMario(Chromosome c,Activator activator,boolean vis)
//	{
//		System.out.println(c);
//		Agent a = new Esario(c,activator);
//		Main.mainEhsan(null, a,vis);
//		
//	}
	@Override
	protected int calculateErrorFitness(double[][] responses, double minResponse, double maxResponse) {
		// TODO Auto-generated method stub
		return 1;
	}
	private EvaluationInfo marioRunner( Agent inputAgent)
	{
	  final Agent agent = inputAgent;
	  MarioAIOptions marioAIOptions = this.config.getmarioAiOption();
	  marioAIOptions.setAgent(agent);
	  BasicTask basicTask = new BasicTask(marioAIOptions);
	  basicTask.doEpisodes(1, false, 1);
//	  if(basicTask.getEvaluationInfo().marioStatus>0)
//	  {
//			 basicTask = new BasicTask(marioAIOptions);
//	
//			System.out.println("WIIIIIIIIIIIIIIIIN");
//			marioAIOptions.setVisualization(true);
//			basicTask.doEpisodes(1, false, 1);
//			UniverseStateVariables.endRun=true;
//	  }
	  return basicTask.getEvaluationInfo();
	}

}
