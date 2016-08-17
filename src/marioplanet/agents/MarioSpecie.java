package marioplanet.agents;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jgap.Chromosome;
import org.jgap.Genotype;
import org.jgap.Specie;

public class MarioSpecie {
	private Vector<MarioAgent> agents;
	private MarioAgent eliteAgent;
	public MarioSpecie(List chromosome)
	{
		agents = new Vector<MarioAgent>(100);
		if(agents!=null)
			agents.clear();
		Iterator a = chromosome.iterator();
		
		while(a.hasNext())
		{
			agents.add(new MarioAgent((Chromosome)a.next()));
		}
	}
	public void setAgents(MarioAgent[] agents) {
		this.agents.clear();
		for(MarioAgent a :agents)
		{
			this.agents.add(a);
		}
		refreshEliteAgent();
	}
	public List<MarioAgent> getAgents() {
		return agents;
	}
	public void setAgents(Specie specie)
	{
		 List<Chromosome> chList = specie.getChromosomes();
		 this.agents.clear();
		 while(chList.iterator().hasNext())
		 {
			 agents.add(new MarioAgent(chList.iterator().next()));
		 }
		
	}
	public void refreshEliteAgent()
	{
		MarioAgent elite = null;
		double fitness=0;
		Iterator a = agents.iterator();
		while(a.hasNext())
		{
			MarioAgent b = (MarioAgent)a.next();
			if(b.getDNA().getFitnessValue()>fitness)
				{
					elite = b;
					fitness = b.getDNA().getFitnessValue();
				}
		}
		this.eliteAgent=elite;
	}
	public MarioAgent getEliteAgent() {
		refreshEliteAgent();
		return eliteAgent;
	}
	
	
}
