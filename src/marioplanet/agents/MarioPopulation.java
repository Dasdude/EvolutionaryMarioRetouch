package marioplanet.agents;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jgap.Chromosome;
import org.jgap.Specie;

public class MarioPopulation {
	private Vector<MarioSpecie> species;
	public MarioPopulation() {
		// TODO Auto-generated constructor stub
		species = new Vector<MarioSpecie>(100);
	}

	public List<MarioSpecie> getSpecie() {
		return species;
	}
	public Vector<MarioAgent> getEliteOfEachSpecie()
	{
		Vector<MarioAgent> result = new Vector<MarioAgent>(species.size());
		Iterator a = species.iterator();
		MarioAgent b;
		while(a.hasNext())
		{
			b = ((MarioSpecie)a.next()).getEliteAgent();
			result.addElement(b);
		}
		return result;
	}
	public void setspecies(List speciesList) {
		
		Iterator a = ( speciesList).iterator();
		if(this.species!=null)
			this.species.clear();
		Specie b ;
		while(a.hasNext())
		{
			
			b= (Specie)a.next();
			
			this.species.add(new MarioSpecie(b.getChromosomes()));
		}
	}
	public MarioSpecie getspecie(int index)
	{
		return species.get(index);
		
	}
}
