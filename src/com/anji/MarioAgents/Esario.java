package com.anji.MarioAgents;

import org.jgap.Chromosome;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TranscriberException;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.scenarios.Main;
import ch.idsia.scenarios.Play;


public class Esario extends BasicMarioAIAgent implements Agent {
	
//	Evolution Parameters
	Chromosome gene;
	ActivatorTranscriber activatorFactory;
	Activator activator;
	boolean jump;
//	Mario Environment Fields
	int marioStatus;
	public Esario()
	{
		super("esario");
	}
	public Esario(Chromosome c,Activator activator)
	{
		super("esario");
		this.gene = c;
		this.activator=activator;
		jump = false;
	}
	
	@Override
	public boolean[] getAction() {
		// TODO Auto-generated method stub
		int[] a =super.environment.getSerializedFullObservationZZ(2, 2);
		double[] stim=new double[a.length] ;
		for(int i =0 ;i<a.length;i++)
		{
			stim[i] = (double)a[i];
		}
			double[] response = activator.next(stim);
			boolean[] action = new boolean[response.length];
			for(int i = 0 ; i<response.length;i++)
			{
				if(response[i]<.5)
					action[i]=false;
				else
					action[i]=true;
			}
//			if(action[Mario.KEY_JUMP]==true)
//			{
//				if(!isMarioOnGround )
//					action[Mario.KEY_JUMP]=false;
//				else
//				{
//					if(jump==true)
//					{
//						action[Mario.KEY_JUMP]=false;
//						jump=false;
//					}
//					else
//					{
//						jump=true;
//					}
//					
//				}
//			}
//			else
//			{
//				jump=false;
//			}
		return action ;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		action = new boolean[Environment.numberOfKeys];
		
	}


}
