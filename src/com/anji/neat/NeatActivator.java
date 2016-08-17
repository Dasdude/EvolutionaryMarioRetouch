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
 * created by Philip Tucker on May 17, 2003
 */
package com.anji.neat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Genotype;

import com.anji.Copyright;
import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.TargetFitnessFunction;
import com.anji.persistence.FilePersistence;
import com.anji.persistence.Persistence;
import com.anji.util.Configurable;
import com.anji.util.DummyConfiguration;
import com.anji.util.Properties;
import com.anji.util.Randomizer;

import MarioStart.MarioSimulator;
import ch.idsia.benchmark.tasks.MarioSystemOfValues;
import ch.idsia.benchmark.tasks.SystemOfValues;
import ch.idsia.tools.EvaluationInfo;
import marioplanet.environment.GalaxyRecipe;
import marioplanet.environment.Planet;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

/**
 * Transcribe <code>Chromosome</code> object (loaded from persistence if necessary) into
 * <code>Activator</code> object and activate it with specified stimuli.
 * 
 * @author Philip Tucker
 */

public class NeatActivator implements Configurable {
	int runNumber = 8;
	int Generation = 9;
	int Difficulty = 1;

private static Logger logger = Logger.getLogger( NeatActivator.class );

private Persistence db;

private List idxs = new ArrayList();

//	dimension # activation sets by dim stimuli
private double[][] stimuli;

//	dimension # activation sets by dim response
private double[][] targets;

private ActivatorTranscriber activatorFactory;

private Randomizer randomizer;
private String planetName;

/**
 * See <a href=" {@docRoot}/params.htm" target="anji_params">Parameter Details </a> for
 * specific property settings.
 * @param props configuration parameters.
 */
public void init( Properties props ) {
	try {
		randomizer = (Randomizer) props.singletonObjectProperty( Randomizer.class );
		db = (Persistence) props.singletonObjectProperty( Persistence.PERSISTENCE_CLASS_KEY );
		activatorFactory = (ActivatorTranscriber) props
				.singletonObjectProperty( ActivatorTranscriber.class );

		stimuli = Properties.loadArrayFromFile( props
				.getResourceProperty( TargetFitnessFunction.STIMULI_FILE_NAME_KEY ) );
		targets = Properties.loadArrayFromFile( props
				.getResourceProperty( TargetFitnessFunction.TARGETS_FILE_NAME_KEY ) );

		if ( stimuli.length == 0 || targets.length == 0 )
			throw new IllegalArgumentException( "require at least 1 training set for stimuli ["
					+ stimuli.length + "] and targets [" + targets.length + "]" );
		if ( stimuli.length != targets.length )
			throw new IllegalArgumentException( "# training sets does not match for stimuli ["
					+ stimuli.length + "] and targets [" + targets.length + "]" );

		for ( int i = 0; i < stimuli.length; ++i )
			idxs.add( new Integer( i ) );
		Collections.sort( idxs );
		reset();
	}
	catch ( Exception e ) {
		throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
				+ e.getMessage() );
	}
}
//EHSAN
public void marioInit(Properties props)
{
	try {
		randomizer = (Randomizer) props.singletonObjectProperty( Randomizer.class );
		db = (Persistence) props.singletonObjectProperty( Persistence.PERSISTENCE_CLASS_KEY );
		
		activatorFactory = (ActivatorTranscriber) props
				.singletonObjectProperty( ActivatorTranscriber.class );

		stimuli = Properties.loadArrayFromFile( props
				.getResourceProperty( TargetFitnessFunction.STIMULI_FILE_NAME_KEY ) );
		targets = Properties.loadArrayFromFile( props
				.getResourceProperty( TargetFitnessFunction.TARGETS_FILE_NAME_KEY ) );

		if ( stimuli.length == 0 || targets.length == 0 )
			throw new IllegalArgumentException( "require at least 1 training set for stimuli ["
					+ stimuli.length + "] and targets [" + targets.length + "]" );
		if ( stimuli.length != targets.length )
			throw new IllegalArgumentException( "# training sets does not match for stimuli ["
					+ stimuli.length + "] and targets [" + targets.length + "]" );

		for ( int i = 0; i < stimuli.length; ++i )
			idxs.add( new Integer( i ) );
		Collections.sort( idxs );
		reset();
	}
	catch ( Exception e ) {
		throw new IllegalArgumentException( "invalid properties: " + e.getClass().toString() + ": "
				+ e.getMessage() );
	}
}

/**
 * reshuffle stimuli
 */
public void reset() {
	Collections.shuffle( idxs, randomizer.getRand() );
}

/**
 * Load chromosome from persistence and activate it.
 * 
 * @param chromId persistence ID of chromosome
 * @return SortedMap contains key Integer index, value double[] response
 * @throws Exception
 * @see NeatActivator#activate(Activator)
 */
public SortedMap activate( String chromId ) throws Exception {
	Configuration config = new DummyConfiguration();
	Chromosome chrom = db.loadChromosome( chromId, config );
	

	Activator activator = activatorFactory.newActivator( chrom );
	db.store( activator );
	return activate( activator );
}
public void activateMario( String chromIds ,String address,Properties props) throws Exception {
	Configuration config = new DummyConfiguration();
	FilePersistence dbf = (FilePersistence)db;
	dbf.startRun("testrun");
	MarioSimulator simulator = new MarioSimulator(props,true);
	Chromosome chrom = dbf.loadCustomChromosome(chromIds, address, config);
	simulator.evalFunc.config.setVisOption(true);
	simulator.setRecord("off"); 	
	for(int i=0;i<simulator.evalFunc.config.totalLevelPerTest;i++)
	{
		
		simulator.marioSim(chrom);
		
	}
		
}
public int calculateFitness(EvaluationInfo evalInf,int[] weight)
{
	MarioPlanetSystemOfValues sov = new MarioPlanetSystemOfValues(weight);
	if(evalInf==null)
		throw new NullPointerException(); 
	return evalInf.computeWeightedFitness(sov);
	
}
public void activateFittestMario( String address ,Properties props,int[] weight,int levelPerDiff,int totalDifficulty) throws Exception {
	Configuration config = new DummyConfiguration();
	FilePersistence dbf = (FilePersistence)db;
	dbf.startRun("testrun");
	Genotype g = dbf.loadGenotypeCustomDir(config, address);
	System.out.println(g.getChromosomes().size());
	List<Chromosome> chromosomes = g.getChromosomes();
	Iterator<Chromosome> iterator= chromosomes.iterator();
	MarioSimulator simulator = new MarioSimulator(props,true,new GalaxyRecipe(GalaxyRecipe.UNBIASED));
	Chromosome chrom =iterator.next();
	EvaluationInfo chromEval = null;
	Chromosome fittestChrom =chrom;
	simulator.setRecord("off"); 
	simulator.evalFunc.evaluate(chromosomes);
	simulator.evalFunc.config.setVisOption(true);
	simulator.evalFunc.config.nextLevel();
	while(iterator.hasNext())
	{
		chrom = iterator.next();
		
		if(chrom.getFitnessValue()>700){
		for(int i=0;i<simulator.evalFunc.config.totalLevelPerTest;i++)
		{
			EvaluationInfo a = simulator.marioSim(chrom);
			System.out.println(a.toStringSingleLineShortForm());
			simulator.evalFunc.config.configPassedtoNextMario();
		}
		}
		
	}
	
}
/**
 * Activate <code>activator</code> with stimuli, and return results
 * 
 * @param activator
 * @return SortedMap contains key Integer index, value double[] response
 * @throws Exception
 */
public SortedMap activate( Activator activator ) throws Exception {
	SortedMap result = new TreeMap();

	double[][] response = activator.next( stimuli );
	Iterator it = idxs.iterator();
	while ( it.hasNext() ) {
		Integer idx = (Integer) it.next();
		result.put( idx, response[ idx.intValue() ] );
	}

	return result;
}
public void activateMario(String chromId , Properties props)
{
	Configuration config = new DummyConfiguration();
	FilePersistence dbf = (FilePersistence)db;

	Chromosome a =dbf.loadChromosome(chromId, config);
	MarioSimulator.marioSimulate(props, a);
	

}

/**
 * Load chromosome from persistencem transcribe it into activator, and activate it.
 * 
 * @param chromId persistence ID of chromosome
 * @return String representation of activation results
 * @throws Exception
 */
public String displayActivation( String chromId ) throws Exception {
	StringBuffer result = new StringBuffer();

	Map responses = activate( chromId );
	Iterator it = responses.keySet().iterator();
	while ( it.hasNext() ) {
		Integer idx = (Integer) it.next();
		int i = idx.intValue();
		double[] response = (double[]) responses.get( idx );

		result.append( i ).append( ": IN (" ).append( stimuli[ i ][ 0 ] );
		for ( int j = 1; j < stimuli[ i ].length; ++j )
			result.append( ", " ).append( stimuli[ i ][ j ] );
		result.append( ")   OUT (" ).append( response[ 0 ] );
		for ( int j = 1; j < response.length; ++j )
			result.append( ", " ).append( response[ j ] );
		result.append( ")   TARGET (" ).append( targets[ i ][ 0 ] );
		for ( int j = 1; j < targets[ i ].length; ++j )
			result.append( ", " ).append( targets[ i ][ j ] );
		result.append( ")\n" );
	}

	return result.toString();
}


/**
 * command line usage
 */
private static void printUsage() {
	System.err.println( "activator <properties-file> <chromosome-id>" );
}

/**
 * Loads chromosome from persistence, transcribes it into activator, and displays activation.
 * 
 * @param args args[0] is properties file name, args[1] is chromosome ID
 * @throws Exception
 */
public static void main( String[] args ) throws Exception {
	System.out.println( Copyright.STRING );
	String path;
	if ( args.length < 2 )
		printUsage();
	else {
		int[] weights = {100,0,0,0,0};
		  String[] planet ={"run","unBiased","Eater","Killer"};
		NeatActivator na = new NeatActivator();
		na.runNumber = 0
				;
		na.Generation = 9;
		na.Difficulty = 1;
		na.planetName = planet[1];
		
		FilePersistence dataBase = (FilePersistence)na.db;
		Properties props = new Properties( args[ 0 ] );
		na.init( props );
		na.activateFittestMario("db/CrazyMarioRun"+na.runNumber+"/"+na.planetName,props, null,6,2);
//		na.activateMario("88381", "db/CrazyMarioRun"+na.runNumber+"/"+na.planetName, props);
//		logger.info( "\n" + na.displayActivation( args[ 1 ] ) );
		System.exit(0);
	}
}

}
