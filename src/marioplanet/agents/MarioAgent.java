package marioplanet.agents;

import java.util.Random;

import org.jfree.util.ArrayUtilities;
import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.util.Randomizer;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.benchmark.mario.environments.Environment;
import jj2000.j2k.util.ArrayUtil;
import marioplanet.environment.Galaxy;
import marioplanet.environment.UniverseStateVariables;

public class MarioAgent extends BasicMarioAIAgent implements Agent{
	private Chromosome dna;
	private Activator activator;
	private Galaxy currentGalaxy;
	public Randomizer randomizer;
	public MarioAgent() {
		// TODO Auto-generated constructor stub
		super("Mario with No Name");
		randomizer = (Randomizer) currentGalaxy.props.singletonObjectProperty( Randomizer.class );
	}
	public void setActivator(Activator activator) {
		this.activator = activator;
	}
	public MarioAgent(Chromosome dna)
	{
		
		
		super("DNA:"+dna.getId());
		try {
			super.setName("DNA:"+dna.getId()+"Specie: "+dna.getSpecie().getRepresentativeId());
			
		} catch (NullPointerException e) {
			// TODO: handle exception
			super.setName(("DNA:"+dna.getId()));
			if(dna==null)
				throw e;
		}
		
		this.dna=dna;
		this.currentGalaxy=dna.galaxyHome;
		if(currentGalaxy!=null)
		randomizer = (Randomizer) currentGalaxy.props.singletonObjectProperty( Randomizer.class );
		dna.agent = this;
		
		
	}
	public void updateName(int GenerationID)
	{
		super.setName("Gen:"+GenerationID+"DNA:"+dna.getId()+"Specie: "+dna.getSpecie().getRepresentativeId()+"FV:"+dna.getFitnessValue());
	}
	public Chromosome getDNA() {
		return dna;
	}

	public void setDNA(Chromosome dna) {
		this.dna = dna;
	}
	public boolean[] getAction2(int[] observation)
	{
		//TODO This funciton does not get called right now
		int totalGalaxy=0;
		if(currentGalaxy.galaxies!=null)
			totalGalaxy = currentGalaxy.galaxies.size();
		double[] stim = new double[observation.length+1+(totalGalaxy*6)];
		for(int i=0;i<totalGalaxy;i++)
		{
			boolean[] tempstim = this.currentGalaxy.galaxies.elementAt(i).getFittestAgent().getAction(observation);
			for(int j =0;j<6;j++)
			{	
				if(tempstim[j])
					stim[6*i+j]=1;
				else
					stim[6*i+j]=0;
			}
			
		}
		for(int i=totalGalaxy*6;i<stim.length-1;i++)
		{
			stim[i] = (double)observation[i-totalGalaxy*6];
		}
		stim[stim.length-1]=1;
		double[] response = activator.next(stim);
		boolean[] action = new boolean[6];
		action[0]=false;
		action[1]=false;
		action[2]=false;
		if(response[0]<.5)
			response[0]=0;
		else
			response[0]=1;
		if(response[1]<.5)
			response[1]=0;
		else
			response[1]=1;
		switch (Integer.toString((int)response[0])+Integer.toString((int)response[1])) {
		case "00":
			action[0]=true;
			break;
		case "01":
			action[2]=true;
			break;
		case "10":
			
			break;
		case "11":
			action[1]=true;

		break;	

			
		default:
			System.out.println(Integer.toString((int)response[0]*2)+Integer.toString((int)response[1]*2));
			break;
		}
		for(int i = 2 ; i<response.length;i++)
		{
			if(response[i]<.5)
				action[i+1]=false;
			else
				action[i+1]=true;
		
			
		}
		return action;
		
		
	}
	public boolean[] getAction(int[] observation)
	{
		//TODO This funciton does not get called right now
		int totalGalaxy=0;
		if(currentGalaxy!=null&&currentGalaxy.galaxies!=null)
			totalGalaxy = currentGalaxy.galaxies.size();
		if(currentGalaxy==null)
			totalGalaxy=0;
		double[] stim = new double[observation.length+1+(totalGalaxy*6)];
		for(int i=0;i<totalGalaxy;i++)
		{
			boolean[] tempstim = this.currentGalaxy.galaxies.elementAt(i).getFittestAgent().getAction(observation);
			for(int j =0;j<6;j++)
			{	
				if(tempstim[j])
					stim[6*i+j]=1;
				else
					stim[6*i+j]=0;
			}
			
		}
		for(int i=totalGalaxy*6;i<stim.length-1;i++)
		{
			stim[i] = (double)observation[i-totalGalaxy*6];
		}
		stim[stim.length-1]=127;
		double[] response = activator.next(stim);
		boolean[] action = new boolean[6];
		action[0]=false;
		action[1]=false;
		action[2]=false;
		double randomValue1 =randomizer.getRand().nextDouble();
		double randomValue2 =randomizer.getRand().nextDouble();
		if(response[0]<randomValue1)
			response[0]=0;
		else
		{
			if(response[0]>randomValue1)
				response[0]=1;
			
		}
		if(response[1]<randomValue2)
			response[1]=0;
		else
		{
			if(response[1]>randomValue2)
				response[1]=1;
			
		}
		switch (Integer.toString((int)response[0])+Integer.toString((int)response[1])) {
		case "00":
			action[0]=true;
			break;
		case "01":
			action[2]=true;
			break;
		case "10":
			
			break;
		case "11":
			action[1]=true;

		break;	

			
		default:
			System.out.println(Integer.toString((int)response[0]*2)+Integer.toString((int)response[1]*2));
			break;
		}
		for(int i = 2 ; i<response.length;i++)
		{
//			if(response[i]<0)
//				action[i+1]=false;
//			else
//			{
//				if(response[i]>0)
//					action[i+1]=true;
//				
//			}
			double randomvalue =randomizer.getRand().nextDouble();
			if(response[i]<randomvalue)
				action[i+1]=false;
			else
			{
				if(response[i]>randomvalue)
					action[i+1]=true;
				
			}
			
		
			
		}
		action[5]=false;
		return action;
		
		
	}
	@Override
	public boolean[] getAction() {
		// TODO Auto-generated method stub
		int[] b = super.environment.getSerializedEnemiesObservationZ(-2);
		int[] a =super.environment.getSerializedLevelSceneObservationZ(-2);
		int[] d = super.environment.getSerializedFullObservationZZ(-3, -3);
		int[] c = new int[4*b.length+7];
			
			for(int i=0;i<a.length;i++)
			{
				if(a[i]!=0)
				c[i]=a[i];
				else
					c[i]=-127;
			}
			for(int i=0;i<b.length;i++)
			{
				if(b[i]!=0)
				c[a.length+i]=b[i];
				else
					c[a.length+i]=-127;
			}
			for(int i=0;i<d.length;i++)
			{
				if(d[i]!=0)
				c[2*a.length+i]=d[i];
				else
				c[2*a.length+i]=-127;
			}
			

			
//		double[] stim=new double[a.length+1] ;
//		for(int i =0 ;i<a.length;i++)
//		{
//			stim[i] = (double)a[i];
//		}
//		//Bias
//		stim[a.length]=1;
//			double[] response = activator.next(stim);
//			boolean[] action = new boolean[6];
//			action[0]=false;
//			action[1]=false;
//			action[2]=false;
//			if(response[0]<.5)
//				response[0]=0;
//			else
//				response[0]=1;
//			if(response[1]<.5)
//				response[1]=0;
//			else
//				response[1]=1;
//			switch (Integer.toString((int)response[0])+Integer.toString((int)response[1])) {
//			case "00":
//				action[0]=true;
//				break;
//			case "01":
//				action[2]=true;
//				break;
//			case "10":
//				
//				break;
//			case "11":
//				action[1]=true;
//	
//			break;	
//
//				
//			default:
//				System.out.println(Integer.toString((int)response[0]*2)+Integer.toString((int)response[1]*2));
//				break;
//			}
////			if(response[0]<.1)
////			{
////				action[0]=true;
////			}
////			else
////			{
////				if(response[0]<.38)
////				{
////				}
////				else
////				{
////					if(response[0]<.63)
////					{
////						action[2]=true;
////					}
////					else{
////						if(response[0]<.9);
////						else
////						{
////							action[1]=true;
////						}
////						
////					}
////				}
////				
////			}
//			for(int i = 2 ; i<response.length;i++)
//			{
//				if(response[i]<.5)
//					action[i+1]=false;
//				else
//					action[i+1]=true;
//			
//				
//			}
		return getAction(c) ;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		action = new boolean[Environment.numberOfKeys];
	}

	
}
