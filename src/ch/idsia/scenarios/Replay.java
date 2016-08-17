/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.scenarios;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jgap.MarioConfiguration;

import com.anji.util.Properties;

import ch.idsia.benchmark.tasks.ReplayTask;
import ch.idsia.tools.MarioAIOptions;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * Date: Oct 9, 2010
 * Time: 8:00:13 PM
 * Package: ch.idsia.scenarios
 */
public class Replay
{
public static void main(String[] args) throws IOException
{

    //TODO : FIX IT!
    /* TODO : FIX IT!
[~ Mario AI Benchmark ~ 0.1.9]
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 0
	at ch.idsia.scenarios.Replay.main(Replay.java:18)
    
     */
//    final MarioAIOptions cmdLineOptions = new MarioAIOptions("mario.properties");
	  final ReplayTask replayTask = new ReplayTask();
//	  MarioConfiguration a = new MarioConfiguration(new Properties("mario.properties"));
	  
	  String[] planet ={"runner","eater","fire","flower","kill","fire","win","bricker","suicide","unBiased"};
	  int runNumber =7;
	  int Generation =3855;
	  int champrunNumber=MarioPlanetSystemOfValues.KILLS
			  
			  
			  ;
	  String Name="Test";
	  int testNumber=1;
	  String planetName = planet[4];
	  while(Generation>=0){
//		try{
		  for(int i=0;i<testNumber;i++)
		  {
			  
		  System.out.println(Generation);
			replayTask.reset("db/"+Name+runNumber+"/"+planetName+"/R"+runNumber+"Gen"+Generation+" "+planetName+ " Champ"+MarioPlanetSystemOfValues.variableNames[champrunNumber]+" Test"+i);
//			replayTask.reset("db/"+Name+runNumber+"/"+planetName+"/R"+runNumber+"Gen"+Generation+" "+planetName+ " ChampNormalFitness"+" Test"+testNumber);
//	    replayTask.reset("db/"+Name+runNumber+"/"+planetName+"/R"+runNumber+"Gen"+Generation+" "+planetName+ " Champ"+champrunNumber+"Test"+testNumber);
	    replayTask.options.setMarioMode(0);
	    replayTask.options.setFPS(24);
	    replayTask.options.setTimeLimit(500);
	    replayTask.options.setMarioInvulnerable(true);
	    replayTask.options.setReceptiveFieldWidth(7);
	    replayTask.options.setReceptiveFieldHeight(7);
	    replayTask.options.setReceptiveFieldVisualized(true);
//	    System.out.println(replayTask.options.getReceptiveFieldHeight());
	    replayTask.options.setZLevelEnemies(-1);
	    replayTask.options.setZLevelScene(-1);
	    replayTask.setOptionsAndReset(replayTask.options);
//	    replayTask.options.setLevelRandSeed(4);
	    replayTask.startReplay();
	    replayTask.printStatistics();
	    System.out.println("Hello");
//	    break;
//		}
//		catch(Exception e)
//		{
////			System.out.println("file Not Found");
////			continue;
//		}
		System.out.println("daa");
		
		  }
		  Generation--;
	  }
	  System.out.println("Baasd");
    // TODO: output evaluationInfo as in BasicTask
//    System.out.println(replayTask.getEnvironment().getEvaluationInfoAsString());
    System.exit(0);
}
}
