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

package ch.idsia.tools;

import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.tasks.MarioSystemOfValues;
import ch.idsia.benchmark.tasks.SystemOfValues;
import marioplanet.evaluation.MarioPlanetSystemOfValues;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy
 * Date: Apr 12, 2009
 * Time: 12:44:51 AM
 * Package: .Tools
 */

public final class EvaluationInfo implements Cloneable
{
private static final int MagicNumberUnDef = -42;

public static final int numberOfElements = 15;
// ordered in alphabetical order;
public int distancePassedCells = MagicNumberUnDef;
public int distancePassedPhys = MagicNumberUnDef;
public int flowersDevoured = MagicNumberUnDef;
public int killsByFire = MagicNumberUnDef;
public int killsByShell = MagicNumberUnDef;
public int killsByStomp = MagicNumberUnDef;
public int killsTotal = MagicNumberUnDef;
public int marioMode = MagicNumberUnDef;
public int marioStatus = MagicNumberUnDef;
public int mushroomsDevoured = MagicNumberUnDef;
public int greenMushroomsDevoured = MagicNumberUnDef;
public int coinsGained = MagicNumberUnDef;
public int timeLeft = MagicNumberUnDef;
public int timeSpent = MagicNumberUnDef;
public int bricksBumped=MagicNumberUnDef;
public int hiddenBlocksFound = MagicNumberUnDef;

private String taskName = "NoTaskNameSpecified";

public int totalNumberOfCoins = MagicNumberUnDef;
public int totalNumberOfHiddenBlocks = MagicNumberUnDef;
public int totalNumberOfMushrooms = MagicNumberUnDef;
public int totalNumberOfFlowers = MagicNumberUnDef;
public int totalNumberOfCreatures = MagicNumberUnDef; //including spiky flowers

public long bytecodeInstructions = MagicNumberUnDef;

public int levelLength = MagicNumberUnDef;

public int collisionsWithCreatures = MagicNumberUnDef;

private static final int[] retFloatArray = new int[EvaluationInfo.numberOfElements];
private static final int[] zeros = new int[EvaluationInfo.numberOfElements];
public String Memo = "";

private static final DecimalFormat df = new DecimalFormat("#.##");
private static MarioSystemOfValues marioSystemOfValues = new MarioSystemOfValues();

public int[][] marioTrace;
public String marioTraceFileName;

private long evaluationStarted = 0;
private long evaluationFinished = 0;
private long evaluationLasted = 0;

public int cellWins;

public EvaluationInfo()
{
    System.arraycopy(EvaluationInfo.zeros, 0, retFloatArray, 0, EvaluationInfo.numberOfElements);
}

public int computeBasicFitness()
{
    return distancePassedPhys - timeSpent + coinsGained + marioStatus * marioSystemOfValues.win;
}

public int computeWeightedFitness(SystemOfValues sov)
{
//	sov.print();
    return
            distancePassedPhys * sov.distance +
                    flowersDevoured * sov.flowerFire +
                    marioStatus * sov.win +
                    marioMode * sov.mode +
                    mushroomsDevoured * sov.mushroom +
                    greenMushroomsDevoured * sov.greenMushroom +
                    coinsGained * sov.coins +
                    hiddenBlocksFound * sov.hiddenBlock +
                    killsTotal * sov.kills +
                    killsByStomp * sov.killedByStomp +
                    killsByFire * sov.killedByFire +
                    killsByShell * sov.killedByShell +
                    timeLeft * sov.timeLeft+
    				bricksBumped*sov.bricks+
    				collisionsWithCreatures*sov.collisionWithCreature;
    
}
public void devide(int b)
{
	if(b==0)
		return ;
	else
	{
		distancePassedPhys=distancePassedPhys/b;  
	    flowersDevoured=flowersDevoured/b;
	                    marioStatus=marioStatus/b; 
	                    marioMode=marioMode/b;
	                    mushroomsDevoured=mushroomsDevoured/b;
	                    greenMushroomsDevoured=greenMushroomsDevoured/b; 
	                    coinsGained=coinsGained/b;
	                    hiddenBlocksFound=hiddenBlocksFound/b;
	                    killsTotal=killsTotal/b;
	                    killsByStomp=killsByStomp/b;
	                    killsByFire=killsByFire/b;
	                    killsByShell=killsByShell/b;
	                    timeLeft=timeLeft/b;
	    				bricksBumped=bricksBumped/b;
	    				collisionsWithCreatures=collisionsWithCreatures/b;
	    				cellWins=cellWins/b;
	}
}
public void add(EvaluationInfo added)
{
	cellWins+=added.cellWins;
	distancePassedCells+=added.distancePassedCells;
	distancePassedPhys+=added.distancePassedPhys; 
    flowersDevoured+=added.flowersDevoured;
                    marioStatus+=added.marioStatus; 
                    marioMode+=added.marioMode;
                    mushroomsDevoured+=added.mushroomsDevoured;
                    greenMushroomsDevoured+=added.greenMushroomsDevoured; 
                    coinsGained+=added.coinsGained;
                    hiddenBlocksFound+=added.hiddenBlocksFound;
                    killsTotal+=added.killsTotal;
                    killsByStomp+=added.killsByStomp;
                    killsByFire+=added.killsByFire;
                    killsByShell+=added.killsByShell;
                    timeLeft+=added.timeLeft ;
    				bricksBumped+=added.bricksBumped;
    				collisionsWithCreatures+=added.collisionsWithCreatures;
}
public void setZero()
{
	distancePassedCells=0;
	timeSpent=0;
    distancePassedPhys=0; 
    flowersDevoured=0;
                    marioStatus=0; 
                    marioMode=0;
                    mushroomsDevoured=0;
                    greenMushroomsDevoured=0; 
                    coinsGained=0;
                    hiddenBlocksFound=0;
                    killsTotal=0;
                    killsByStomp=0;
                    killsByFire=0;
                    killsByShell=0;
                    timeLeft=0;
    				bricksBumped=0;
    				collisionsWithCreatures=0;
    				timeLeft=0;
    				
}
public EvaluationInfo computeWeightedEval(SystemOfValues sov)
{
    distancePassedPhys=distancePassedPhys * sov.distance; 
    flowersDevoured=flowersDevoured * sov.flowerFire ;
                    marioStatus=marioStatus * sov.win; 
                    marioMode=marioMode * sov.mode ;
                    mushroomsDevoured=mushroomsDevoured * sov.mushroom ;
                    greenMushroomsDevoured=greenMushroomsDevoured * sov.greenMushroom; 
                    coinsGained=coinsGained * sov.coins ;
                    hiddenBlocksFound=hiddenBlocksFound * sov.hiddenBlock ;
                    killsTotal=killsTotal * sov.kills ;
                    killsByStomp=killsByStomp * sov.killedByStomp ;
                    killsByFire=killsByFire * sov.killedByFire ;
                    killsByShell=killsByShell * sov.killedByShell ;
                    timeLeft=timeLeft * sov.timeLeft;
    				bricksBumped=bricksBumped*sov.bricks;
    				collisionsWithCreatures=collisionsWithCreatures*sov.collisionWithCreature;
    return this;
}
public int[] getResultsInt()
{
	int[] a = new int[MarioPlanetSystemOfValues.weightsLength];
	a[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=distancePassedPhys;
	a[MarioPlanetSystemOfValues.KILLS_BY_FIRE]=killsByFire;
	a[MarioPlanetSystemOfValues.KILLS_BY_SHELL]=killsByShell;
	a[MarioPlanetSystemOfValues.KILLS_BY_STOMP]=killsByStomp;
	a[MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=collisionsWithCreatures;
	a[MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=mushroomsDevoured;
	a[MarioPlanetSystemOfValues.TIME_SPENT]=timeSpent;
	a[MarioPlanetSystemOfValues.WIN]=marioStatus;
	a[MarioPlanetSystemOfValues.MODE]=marioMode;
	a[MarioPlanetSystemOfValues.COINS]=coinsGained;
	a[MarioPlanetSystemOfValues.GREEN_MUSHROOM]=greenMushroomsDevoured;
	a[MarioPlanetSystemOfValues.HIDDEN_BLOCKS]=hiddenBlocksFound;
	a[MarioPlanetSystemOfValues.TIME_LEFT]=timeLeft;
	a[MarioPlanetSystemOfValues.KILLS]=killsTotal;
	a[MarioPlanetSystemOfValues.BRICKS]=bricksBumped;
	a[MarioPlanetSystemOfValues.CELLWINS]=0;
	a[MarioPlanetSystemOfValues.CONSTANT]=100000;
return a;
			
}
public double[] getResultsdouble()
{
	double[] a = new double[MarioPlanetSystemOfValues.weightsLength];
	a[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=distancePassedPhys;
	a[MarioPlanetSystemOfValues.KILLS_BY_FIRE]=killsByFire;
	a[MarioPlanetSystemOfValues.KILLS_BY_SHELL]=killsByShell;
	a[MarioPlanetSystemOfValues.KILLS_BY_STOMP]=killsByStomp;
	a[MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=collisionsWithCreatures;
	a[MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=mushroomsDevoured;
	a[MarioPlanetSystemOfValues.TIME_SPENT]=timeSpent;
	a[MarioPlanetSystemOfValues.WIN]=marioStatus;
	a[MarioPlanetSystemOfValues.MODE]=marioMode;
	a[MarioPlanetSystemOfValues.COINS]=coinsGained;
	a[MarioPlanetSystemOfValues.GREEN_MUSHROOM]=greenMushroomsDevoured;
	a[MarioPlanetSystemOfValues.HIDDEN_BLOCKS]=hiddenBlocksFound;
	a[MarioPlanetSystemOfValues.TIME_LEFT]=timeLeft;
	a[MarioPlanetSystemOfValues.KILLS]=killsTotal;
	a[MarioPlanetSystemOfValues.BRICKS]=bricksBumped;
	a[MarioPlanetSystemOfValues.CELLWINS]=cellWins;
	a[MarioPlanetSystemOfValues.CONSTANT]=100000;
	return a;
}
public int[] getResultsIntwithoutSOVConsideration()
{
	int[] a = new int[MarioPlanetSystemOfValues.weightsLength];
	a[MarioPlanetSystemOfValues.DISTANCE_PASSED_CELLS]=distancePassedPhys;
	a[MarioPlanetSystemOfValues.KILLS_BY_FIRE]=killsByFire;
	a[MarioPlanetSystemOfValues.KILLS_BY_SHELL]=killsByShell;
	a[MarioPlanetSystemOfValues.KILLS_BY_STOMP]=killsByStomp;
	a[MarioPlanetSystemOfValues.COLLISION_WITH_CREATURES]=collisionsWithCreatures;
	a[MarioPlanetSystemOfValues.MUSHROOM_DEVOURED]=mushroomsDevoured;
	a[MarioPlanetSystemOfValues.FLOWERS_DEVOURED]=flowersDevoured;
	a[MarioPlanetSystemOfValues.TIME_SPENT]=timeSpent;
	a[MarioPlanetSystemOfValues.WIN]=marioStatus;
	a[MarioPlanetSystemOfValues.MODE]=marioMode;
	a[MarioPlanetSystemOfValues.COINS]=coinsGained;
	a[MarioPlanetSystemOfValues.GREEN_MUSHROOM]=greenMushroomsDevoured;
	a[MarioPlanetSystemOfValues.HIDDEN_BLOCKS]=hiddenBlocksFound;
	a[MarioPlanetSystemOfValues.TIME_LEFT]=timeLeft;
	a[MarioPlanetSystemOfValues.KILLS]=killsTotal;
	a[MarioPlanetSystemOfValues.BRICKS]=bricksBumped;
	a[MarioPlanetSystemOfValues.CELLWINS]=cellWins;
return a;
			
}
public int computeWeightedFitness()
{
    return this.computeWeightedFitness(marioSystemOfValues);
}

public float computeDistancePassed()
{
    return distancePassedPhys;
}

public int computeKillsTotal()
{
    return this.killsTotal;
}

//TODO: possible fitness adjustments: penalize for collisions with creatures and especially for suicide. It's a sin.

public int[] toIntArray()
{
    retFloatArray[0] = this.distancePassedCells;
    retFloatArray[1] = this.distancePassedPhys;
    retFloatArray[2] = this.flowersDevoured;
    retFloatArray[3] = this.killsByFire;
    retFloatArray[4] = this.killsByShell;
    retFloatArray[5] = this.killsByStomp;
    retFloatArray[6] = this.killsTotal;
    retFloatArray[7] = this.marioMode;
    retFloatArray[8] = this.marioStatus;
    retFloatArray[9] = this.mushroomsDevoured;
    retFloatArray[10] = this.coinsGained;
    retFloatArray[11] = this.timeLeft;
    retFloatArray[12] = this.timeSpent;
    retFloatArray[13] = this.hiddenBlocksFound;

    return retFloatArray;
}

public String toString()
{
    evaluationFinished = System.currentTimeMillis();
    evaluationLasted = evaluationFinished - evaluationStarted;
    // store mario trace:
    try
    {
        if (marioTraceFileName != null && !marioTraceFileName.equals(""))
        {
            final PrintWriter pw = new PrintWriter(new FileWriter(marioTraceFileName));

            for (int j = 0; j < marioTrace[0].length; ++j)

            {
                for (int i = 0; i < marioTrace.length; ++i)
                {
                    System.out.print(spaceFormat(marioTrace[i][j]));
                    pw.print(spaceFormat(marioTrace[i][j]));
                }
                System.out.println();
                pw.println();
            }
//        for (int[] ints : trace)
//        {
//            for (int anInt : ints)
//            {
//                pw.print(df.format(anInt) + " ");
//            }
//            pw.println();
//        }
            pw.flush();
        }
    } catch (IOException e)
    {
        e.printStackTrace();
    } finally
    {
//        Runtime rt = Runtime.getRuntime();
//        try
//        {
//            Process proc = rt.exec("/usr/local/bin/mate " + marioTraceFileName);
//            Process proc = rt.exec("open " + marioTraceFileName);
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }

    return "\n[MarioAI] ~ Evaluation Results for Task: " + taskName +
            "\n        Evaluation lasted : " + Long.toString(evaluationLasted) + " ms" +
            "\n         Weighted Fitness : " + df.format(computeWeightedFitness()) +
            "\n             Mario Status : " + ((marioStatus == Mario.STATUS_WIN) ? "WIN!" : "Loss...") +
            "\n              Average Win : " + cellWins +
            "\n               Mario Mode : " + Mario.MODES[marioMode] +
            "\nCollisions with creatures : " + collisionsWithCreatures +
            "\n     Passed (Cells, Phys) : " + distancePassedCells + " of " + levelLength + ", " + df.format(distancePassedPhys) + " of " + df.format(levelLength * 16) + " (" + distancePassedCells * 100 / levelLength + "% passed)" +
            "\n Time Spent(marioseconds) : " + timeSpent +
            "\n  Time Left(marioseconds) : " + timeLeft +
            "\n             Coins Gained : " + coinsGained + " of " + totalNumberOfCoins + " (" + coinsGained * 100 / (totalNumberOfCoins == 0 ? 1 : totalNumberOfCoins) + "% collected)" +
            "\n      Hidden Blocks Found : " + hiddenBlocksFound + " of " + totalNumberOfHiddenBlocks + " (" + hiddenBlocksFound * 100 / (totalNumberOfHiddenBlocks == 0 ? 1 : totalNumberOfHiddenBlocks) + "% found)" +
            "\n       Mushrooms Devoured : " + mushroomsDevoured + " of " + totalNumberOfMushrooms + " found (" + mushroomsDevoured * 100 / (totalNumberOfMushrooms == 0 ? 1 : totalNumberOfMushrooms) + "% collected)" +
            "\n         Flowers Devoured : " + flowersDevoured + " of " + totalNumberOfFlowers + " found (" + flowersDevoured * 100 / (totalNumberOfFlowers == 0 ? 1 : totalNumberOfFlowers) + "% collected)" +
            "\n              kills Total : " + killsTotal + " of " + totalNumberOfCreatures + " found (" + killsTotal * 100 / (totalNumberOfCreatures == 0 ? 1 : totalNumberOfCreatures) + "%)" +
            "\n            kills By Fire : " + killsByFire +
            "\n           kills By Shell : " + killsByShell +
            "\n           kills By Stomp : " + killsByStomp +
            "\n           Bricks  Bumped : " + bricksBumped +
            "\n    PunJ : " + Long.toString(bytecodeInstructions) +
            ((Memo.equals("")) ? "" : "\nMEMO INFO: " + Memo);
}

public String toStringSingleLine()
{
    evaluationFinished = System.currentTimeMillis();
    evaluationLasted = evaluationFinished - evaluationStarted;

    return "\n[MarioAI] ~ Evaluation Results:" +
            " Evaluation lasted: " + Long.toString(evaluationLasted) + " ms" +
            "; Status: " + ((marioStatus == Mario.STATUS_WIN) ? "WIN!" : "Loss") +
            "; totalDeath : " + cellWins +
            "; Mode: " + Mario.MODES[marioMode] +
            " +  Passed (Cells, Phys): " + df.format((double) distancePassedCells) + ", " +
            df.format(distancePassedPhys) +
            "; Time Spent: " + timeSpent +
            "; Time Left: " + timeLeft +
            "; Coins: " + coinsGained +
            "; Hidden blocks: " + hiddenBlocksFound +
            "; Mushrooms: " + mushroomsDevoured +
            "; Flowers: " + flowersDevoured +
            "; Collisions: " + collisionsWithCreatures +
            "; kills: " + killsTotal +
            "; By Fire: " + killsByFire +
            "; By Shell: " + killsByShell +
            "; By Stomp: " + killsByStomp +
            "; Bricks: " + bricksBumped +
            "; " + ((Memo.equals("")) ? "" : "\nMEMO INFO: " + Memo);
}
public String toStringSingleLineShortForm()
{
    evaluationFinished = System.currentTimeMillis();
    evaluationLasted = evaluationFinished - evaluationStarted;

    return 
            " Death : " + cellWins +
            "; Mode: " + Mario.MODES[marioMode] +
            " dis: "+
            df.format(distancePassedPhys) +
            "; TimeS: " + timeSpent +
            "; TimeL: " + timeLeft +
            "; Co: " + coinsGained +
//            "; Hidden blocks: " + hiddenBlocksFound +
            "; Mush: " + mushroomsDevoured +
            "; Flow: " + flowersDevoured +
            "; Colli: " + collisionsWithCreatures +
            "; kills: " + killsTotal +
            "; Fire: " + killsByFire +
            "; Shell: " + killsByShell +
            "; Stomp: " + killsByStomp +
            "; Bricks: " + bricksBumped 
            ;
}

public void setTaskName(final String name)
{
    taskName = name;
}

private String spaceFormat(int i)
{
    int j = 0;
    String r = "" + ((i == 0) ? "." : i);
    while (r.length() < 4)
        r += " ";
    return r;
}

public EvaluationInfo clone()
{
    try
    {
        // TODO:!H!:double check the validity of this change!
        return (EvaluationInfo) super.clone();
    } catch (CloneNotSupportedException e)
    {
        System.err.println(e);
        return null;
    }

//    EvaluationInfo ret = new EvaluationInfo();
//    ret.marioStatus = this.marioStatus;
//    ret.flowersDevoured = this.flowersDevoured;
//    ret.distancePassedPhys = this.distancePassedPhys;
//
//    ret.distancePassedCells = this.distancePassedCells;
//////     evaluationInfo.totalLengthOfLevelCells = levelScene.level.getWidthCells();
//////     evaluationInfo.totalLengthOfLevelPhys = levelScene.level.getWidthPhys();
//    ret.timeSpent = this.timeSpent;
//    ret.timeLeft = this.timeLeft;
//    ret.coinsGained = this.coinsGained;
//    ret.totalNumberOfCoins = this.totalNumberOfCoins;
//    ret.totalNumberOfHiddenBlocks = this.totalNumberOfHiddenBlocks;
//    ret.totalNumberOfFlowers = this.totalNumberOfFlowers;
//    ret.totalNumberOfMushrooms = this.totalNumberOfMushrooms;
//    ret.totalNumberOfCreatures = this.totalNumberOfCreatures;
//    ret.marioMode = this.marioMode;
//    ret.mushroomsDevoured = this.mushroomsDevoured;
//    ret.killsTotal = this.killsTotal;
//    ret.killsByStomp = this.killsByStomp;
//    ret.killsByFire = this.killsByFire;
//    ret.killsByShell = this.killsByShell;
//    ret.hiddenBlocksFound = this.hiddenBlocksFound;
//    ret.collisionsWithCreatures = this.collisionsWithCreatures;
//    ret.Memo = this.Memo.substring(0);
//    ret.levelLength = this.levelLength;
//    ret.marioTraceFileName = this.marioTraceFileName;
//    ret.marioTrace = this.marioTrace;
//    return ret;
}

public String getTaskName()
{
    return taskName;
}

public void reset()
{
    evaluationStarted = System.currentTimeMillis();
}
}
