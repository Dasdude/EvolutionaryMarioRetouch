package org.jgap;

import java.io.Serializable;
import java.util.Random;

import com.anji.neat.Evolver;
import com.anji.util.Properties;
//EHSAN

import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.tools.MarioAIOptions;
import marioplanet.agents.MarioAgent;
import marioplanet.environment.Galaxy;
import marioplanet.environment.MarioPropertiesKeys;
import marioplanet.environment.UniverseStateVariables;
import marioplanet.evaluation.MarioPlanetSystemOfValues;
public class MarioConfiguration implements Serializable{

public final int randomGeneratorSeed;
private Random randomGenerator;
private boolean champVisualisation;
private int champFPS;
private int allVisualizationFPS;
private boolean allVisual;
private int timeLimit;
private  MarioPlanetSystemOfValues sov;
private boolean visOption;
private int FPS;
private int levelDifficulty;
private int levelSeed;
private boolean invalunable;
private String marioRunName;
private String recordFileName;
private Properties props;
private int[] levelSeeds;
private int levelLenght;

public Galaxy galaxyHome;
private int levelCounter;
public final int totalLevelPerTest;
private int receptiveFieldWidth;
private int receptiveFieldHeight;
//EHSAN 
public MarioConfiguration(Properties props,MarioPlanetSystemOfValues sov,Galaxy galaxyHome) {
	// TODO Auto-generate constructor stub
	this.props = props;
	champVisualisation= props.getBooleanProperty(Evolver.VISUAL_EACH_GENERATION_CHAMP,false);
	champFPS = props.getIntProperty(Evolver.CHAMP_VISUAL_FPS, 24);
	allVisual = props.getBooleanProperty(Evolver.VISUAL_ALL, false);
	allVisualizationFPS = props.getIntProperty(Evolver.VISUAL_FPS, 34);
	timeLimit = props.getIntProperty(Evolver.TIME_LIMIT,170);
	FPS = props.getIntProperty(Evolver.VISUAL_FPS,24);
	levelDifficulty = props.getIntProperty(Evolver.LEVEL_DIFFICULTY,1);
	randomGeneratorSeed = props.getIntProperty(Evolver.RANDOM_GENERATOR_SEED, 1);
	setInvalunable(props.getBooleanProperty(Evolver.INVALUNABLE));
//	setInvalunable(false);
	randomGenerator = new Random(randomGeneratorSeed);
	totalLevelPerTest=props.getIntProperty(MarioPropertiesKeys.LEVEL_TOTAL_PER_TEST,1);
	levelSeeds=new int[totalLevelPerTest];
	levelLenght=props.getIntProperty(MarioPropertiesKeys.LEVEL_Length);
	receptiveFieldWidth= props.getIntProperty(MarioPropertiesKeys.RECEPTIVE_WIDTH);
	receptiveFieldHeight = props.getIntProperty(MarioPropertiesKeys.RECEPTIVE_HEIGHT);
	
	levelSeed=0;
	levelCounter=0;
	this.sov=sov;
	nextLevel();
	this.galaxyHome=galaxyHome;
	
}
public int getLevelLength()
{
	return this.levelLenght;
}
public MarioPlanetSystemOfValues getSOV()
{
	return this.sov;
}
public void configPassedtoNextMario()
{
	levelCounter=0;
}
public boolean isChampVisualisation() {
	return champVisualisation;
}
public int getNextPsudoLevel()
{
	levelSeed=levelSeeds[levelCounter];
	levelCounter++;
	return levelSeed;
	
}
public void nextLevel()
{
	levelCounter=0;
	for(int i=0;i<levelSeeds.length;i++)
		levelSeeds[i] = randomGenerator.nextInt();
}
public void setChampVisualisation(boolean champVisualisation) {
	
	this.champVisualisation = champVisualisation;
}
public int getChampFPS() {
	return champFPS;
}
public void setChampFPS(int champFPS) {
	this.champFPS = champFPS;
}
public int getAllVisualizationFPS() {
	return allVisualizationFPS;
}
public void setAllVisualizationFPS(int allVisualizationFPS) {
	this.allVisualizationFPS = allVisualizationFPS;
}
public boolean isAllVisual() {
	return allVisual;
}
public void setAllVisual(boolean allVisual) {
	this.allVisual = allVisual;
}
public int getTimeLimit() {
	return timeLimit;
}
public void setTimeLimit(int timeLimit) {
	this.timeLimit = timeLimit;
}
public boolean isVisOption() {
	return visOption;
}
public void setVisOption(boolean visOption) {
	this.visOption = visOption;
}
public MarioAIOptions getmarioAiOption()
{
	
	MarioAIOptions a = new MarioAIOptions();
//	a.setMarioMode(0);
	a.setTimeLimit(getTimeLimit());
	a.setAgent((MarioAgent)null);
	a.setVisualization(isVisOption());
	a.setFPS(getFPS());
	a.setLevelDifficulty(getLevelDifficulty());
	a.setLevelRandSeed(getNextPsudoLevel());
	a.setMarioInvulnerable(this.invalunable);
	if(this.getRecordFileName()!=null)
	a.setRecordFile(getRecordFileName());
	a.setLevelLength(this.levelLenght);
	a.setMarioInvulnerable(isInvalunable());
	a.setMarioMode(0);
	a.setGapsCount(false);
	a.setMarioMode(0);
	a.setEnemies("g,gw,gk,gkw,rk,rkw");
	a.setZLevelEnemies(-1);
	a.setZLevelScene(-1);
	a.setReceptiveFieldWidth(this.receptiveFieldWidth);
	a.setReceptiveFieldHeight(this.receptiveFieldHeight);
	
	return a;
}
public int getFPS() {
	return FPS;
}
public void setFPS(int fPS) {
	FPS = fPS;
}
public int getLevelDifficulty() {
	return levelDifficulty;
}
public void setLevelDifficulty(int levelDifficulty) {
	this.levelDifficulty = levelDifficulty;
}
// Deprecated
public int getLevelSeed() {
	return levelSeed;
}
// Deprecated
public void setLevelSeed(int levelRandSeed) {
	this.levelSeed = levelRandSeed;
}
public boolean isInvalunable() {
	return invalunable;
}
public void setInvalunable(boolean invalunable) {
	this.invalunable = invalunable;
}
private String getRecordFileName() {
	return recordFileName;
}
private void setRecordFileName(String recordFileName) {
	this.recordFileName = recordFileName;
}
public void setRecording(String IDname)
{
	String Path = props.getProperty(MarioPropertiesKeys.BASE_DIR, "");
	if(IDname=="on")
	{
		setRecordFileName("on");
	}
	else
	{
		if(IDname == "off")
		{
			setRecordFileName("off");
		}
		else
		{
			setRecordFileName(Path+"/R"+UniverseStateVariables.runNumber+"Gen"+UniverseStateVariables.generationNumber+" "+UniverseStateVariables.planetName+" "+IDname);
		}
	}
}
public String getMarioRunName() {
	return marioRunName;
}
public  void setSov(MarioPlanetSystemOfValues sov)
{
	this.sov = sov;
	
}
public void setMarioRunName(String marioRunName) {
	this.marioRunName = marioRunName;
}

}
