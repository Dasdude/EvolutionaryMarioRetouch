/*
 * Copyright (C) 2004 Derek James and Philip Tucker
 * 
 * This file is part of ANJI (Another NEAT Java Implementation).
 * 
 * ANJI is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 * 
 * Created on Apr 4, 2004 by Philip Tucker
 */
package com.anji.integration;

import java.util.Iterator;

import org.jgap.Chromosome;
import org.jgap.Genotype;
import org.jgap.Specie;

import com.anji.util.XmlPersistable;

import marioplanet.evaluation.MarioPlanetSystemOfValues;

/**
 * Converts generation data between <code>Genotype</code> and XML.
 * 
 * @author Philip Tucker
 */
public class Generation implements XmlPersistable {

/**
 * XML base tag
 */
public final static String GENERATION_TAG = "generation";

private Genotype genotype;

private Long id;

private String cachedXml;

/**
 * @see java.lang.Object#hashCode()
 */
public int hashCode() {
	return id.hashCode();
}

/**
 * @see java.lang.Object#equals(java.lang.Object)
 */
public boolean equals( Object o ) {
	Generation other = (Generation) o;
	return id.equals( other.id );
}

/**
 * for hibernate
 */
private Generation() {
	super();
}

/**
 * @param aGenotype chromosomes from this object make up generation.
 * @param anId of generation
 */
public Generation( Genotype aGenotype, long anId ) {
	genotype = aGenotype;
	id = new Long( anId );
	cacheXml();
}

/**
 * @see com.anji.util.XmlPersistable#toXml()
 */
public String toXml() {
	return cachedXml;
}
private void cacheXml() {
	int maxFitness = Integer.MIN_VALUE;
	int minFitness = Integer.MAX_VALUE;
	int maxComplexity = Integer.MIN_VALUE;
	int minComplexity = Integer.MAX_VALUE;

	int[] max = new int[MarioPlanetSystemOfValues.weightsLength+1];
	int[] min = new int[MarioPlanetSystemOfValues.weightsLength+1];
	int[] avg = new int[MarioPlanetSystemOfValues.weightsLength+1];
	for(int i=0;i<MarioPlanetSystemOfValues.weightsLength+1;i++)
	{
		max[i]=Integer.MIN_VALUE;
		min[i]=Integer.MAX_VALUE;
		avg[i]=0;
	}
//	int maxDistance=Integer.MIN_VALUE;
//	int minDistance=Integer.MAX_VALUE;
//	int avgDistance=0;
//	
//	int maxMushroom = Integer.MIN_VALUE;
//	int minMushroom=Integer.MAX_VALUE;
//	int avgMushroom=0;
//	
//	int maxFlower=Integer.MIN_VALUE;
//	int minFlower=Integer.MAX_VALUE;
//	int avgFlower=0;
//	
//	int maxKill=Integer.MIN_VALUE;
//	int minKill=Integer.MAX_VALUE;
//	int avgKill=0;
//	
//	int maxCollision=Integer.MIN_VALUE;
//	int minCollision=Integer.MAX_VALUE;
//	int avgCollision=0;
//	
//	int maxBrick=Integer.MIN_VALUE;
//	int minBrick=Integer.MAX_VALUE;
//	int avgBrick=0;

	StringBuffer result = new StringBuffer();
	result.append( "<" ).append( GENERATION_TAG ).append( " id=\"" ).append( id ).append(
			"\" >\n" );

	Iterator iter = genotype.getChromosomes().iterator();
	long runningFitnessTotal = 0;
	int popSize = 0;
	long runningComplexityTotal = 0;

	while ( iter.hasNext() ) {
		Chromosome chrom = (Chromosome) iter.next();
		int thisChromFitness = chrom.getFitnessValue();
		runningFitnessTotal += thisChromFitness;
		int thisChromComplexity = chrom.size();
		runningComplexityTotal += thisChromComplexity;
		popSize++;
		if ( thisChromFitness > maxFitness )
			maxFitness = thisChromFitness;
		if ( thisChromFitness < minFitness )
			minFitness = thisChromFitness;
		if ( thisChromComplexity > maxComplexity )
			maxComplexity = thisChromComplexity;
		if ( thisChromComplexity < minComplexity )
			minComplexity = thisChromComplexity;
		int[] value = chrom.averageEval.getResultsIntwithoutSOVConsideration();
		for(int i=0;i<max.length;i++)
		{
			if(i!=max.length-1)
			{
				if(value[i]<0)
					value[i]=Integer.MAX_VALUE;
				if(value[i]>max[i])
					max[i]=value[i];
				if(value[i]<min[i])
					min[i]=value[i];
				if(value[i]==Integer.MAX_VALUE||avg[i]==Integer.MAX_VALUE)
					avg[i]=Integer.MAX_VALUE;
				else
				{
					avg[i]+=value[i];
					if(avg[i]<0)
						avg[i]=Integer.MAX_VALUE;
					
				}
			}
			else
			{
				int dispdeath = value[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]/value[MarioPlanetSystemOfValues.CELLWINS];
				if(dispdeath>max[i])
					max[i]=dispdeath;
				if(dispdeath<min[i])
					min[i]=dispdeath;
				avg[i]+=dispdeath;
			}
		}
		
	}
	int[] specsIndex={MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS,
					MarioPlanetSystemOfValues.CELLWINS,
					MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES,
					MarioPlanetSystemOfValues.KILLS,
					MarioPlanetSystemOfValues.KILLS_BY_FIRE,
					MarioPlanetSystemOfValues.KILLS_BY_SHELL,
					MarioPlanetSystemOfValues.KILLS_BY_STOMP,
					MarioPlanetSystemOfValues.MUSHROOM_DEVOURED,
					MarioPlanetSystemOfValues.FLOWERS_DEVOURED,
					MarioPlanetSystemOfValues.BRICKS};
	for(int i=0;i<specsIndex.length;i++)
	{
		if(specsIndex[i]==MarioPlanetSystemOfValues.CELLWINS)
		{
			result.append( "<distancePerDeath>\n" );
			result.append( "<max>" ).append( max[max.length-1] );
			result.append( "</max>\n" );
			result.append( "<min>" ).append( min[min.length-1] );
			result.append( "</min>\n" );
			result.append( "<avg>" );
			result.append( avg[avg.length-1] / popSize );
			result.append( "</avg>\n" );
			result.append( "</distancePerDeath>\n" );
		}
		else{
			
		}
		result.append( "<"+MarioPlanetSystemOfValues.variableNames[specsIndex[i]]+">\n" );
		result.append( "<max>" ).append( max[specsIndex[i]] );
		result.append( "</max>\n" );
		result.append( "<min>" ).append( min[specsIndex[i]] );
		result.append( "</min>\n" );
		result.append( "<avg>" );
		result.append( avg[specsIndex[i]] / popSize );
		result.append( "</avg>\n" );
		result.append( "</"+MarioPlanetSystemOfValues.variableNames[specsIndex[i]]+">\n" );
	}
	result.append( "<fitness>\n" );
	result.append( "<max>" ).append( maxFitness );
	result.append( "</max>\n" );
	result.append( "<min>" ).append( minFitness );
	result.append( "</min>\n" );
	result.append( "<avg>" );
	result.append( runningFitnessTotal / popSize );
	result.append( "</avg>\n" );
	result.append( "</fitness>\n" );

	result.append( "<complexity>\n" );
	result.append( "<champ>" ).append( genotype.getFittestChromosome().size() );
	result.append( "</champ>\n" );
	result.append( "<max>" ).append( maxComplexity );
	result.append( "</max>\n" );
	result.append( "<min>" ).append( minComplexity );
	result.append( "</min>\n" );
	result.append( "<avg>" );
	result.append( (double) runningComplexityTotal / popSize );
	result.append( "</avg>\n" );
	result.append( "</complexity>\n" );
//EHSAN commented this part out to avoid writing each individual results
//	Iterator speciesIter = genotype.getSpecies().iterator();
//	while ( speciesIter.hasNext() ) {
//		Specie specie = (Specie) speciesIter.next();
//		result.append( specie.toXml() );
//	}
	result.append( "</" ).append( GENERATION_TAG ).append( ">\n" );

	cachedXml = result.toString();
}

/**
 * @see com.anji.util.XmlPersistable#getXmlRootTag()
 */
public String getXmlRootTag() {
	return GENERATION_TAG;
}

/**
 * @see com.anji.util.XmlPersistable#getXmld()
 */
public String getXmld() {
	return null;
}

/**
 * for hibernate
 * @return unique id
 */
private Long getId() {
	return id;
}

/**
 * for hibernate
 * @param aId
 */
private void setId( Long aId ) {
	id = aId;
}
}
