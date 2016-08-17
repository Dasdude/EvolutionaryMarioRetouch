package marioplanet.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import javax.print.attribute.standard.Destination;

import org.omg.CORBA.FREE_MEM;

import com.anji.nn.SigmoidActivationFunction;
import com.anji.util.Properties;
import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;

public class Universe {
	//path to store all the ids of chromosomes for id factories
	String IDPath;
	int IDNumber;
	Properties prop;
	public Universe(String props,String baseIDMapDir) throws IOException {
		// TODO Auto-generated constructor stub
		prop = new Properties(props);
		setRunNumber(prop);
		System.out.println(prop.getProperty("persistence.base.dir")+IDNumber+"/id.xml");
		prop.setProperty("id.file",prop.getProperty("persistence.base.dir")+IDNumber+"/id.xml");
		prop.setProperty("neat.id.file",prop.getProperty("persistence.base.dir")+IDNumber+"/neatid.xml");
		File file = new File(prop.getProperty("persistence.base.dir")+IDNumber); 
		if(!file.mkdirs())
		{
			System.out.println(file.getAbsolutePath());
			throw new IOException("Folder cannot be created");
		}
		File source = new File(baseIDMapDir+"/id.xml");
		File dest = new File(prop.getProperty("id.file"));
		try{
			
		Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES,LinkOption.NOFOLLOW_LINKS);
		source = new File(baseIDMapDir+"/neatid.xml");
		dest = new File(prop.getProperty("neat.id.file"));
		Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES,LinkOption.NOFOLLOW_LINKS);
		}
		catch(NoSuchFileException e)
		{
			e.printStackTrace();
			System.out.println("File Not Found");
			System.out.println("Source Directory: "+source.getAbsolutePath());
		}
	}
	public void createGalaxies() throws Exception
	{
		int totalGen = prop.getIntProperty(MarioPropertiesKeys.GENERATION_NUM);
//		Galaxy win = new Galaxy(new GalaxyRecipe(GalaxyRecipe.WIN), null, prop);
//		win.evolve(60);
		Galaxy run = new Galaxy(new GalaxyRecipe(GalaxyRecipe.RUNNER), null, prop);
		run.evolve(totalGen);
		Vector<Galaxy> galaxies = new Vector<Galaxy>();
		galaxies.addElement(run);
		Galaxy suicider = new Galaxy(new GalaxyRecipe(GalaxyRecipe.SUICIDE), galaxies, prop);
		suicider.evolve(totalGen);
		Galaxy bricker = new Galaxy(new GalaxyRecipe(GalaxyRecipe.BRICKER),galaxies , prop);
		bricker.evolve(totalGen);
		galaxies.removeAllElements();
		galaxies.addElement(bricker);
		Galaxy eater = new Galaxy(new GalaxyRecipe(GalaxyRecipe.EATER), galaxies, prop);
		eater.evolve(totalGen);
		galaxies.removeAllElements();
		galaxies.addElement(eater);
		galaxies.addElement(run);
		Galaxy dragon = new Galaxy(new GalaxyRecipe(GalaxyRecipe.FIRE), galaxies, prop);
		dragon.evolve(totalGen);
		Galaxy bigAss = new Galaxy(new GalaxyRecipe(GalaxyRecipe.STOMP), galaxies, prop);
		bigAss.evolve(totalGen);
		Galaxy bowler = new Galaxy(new GalaxyRecipe(GalaxyRecipe.SHELL), galaxies, prop);
		bowler.evolve(totalGen);
		galaxies.removeAllElements();
		galaxies.addElement(bowler);
		galaxies.addElement(bigAss);
		galaxies.addElement(dragon);
		Galaxy killer = new Galaxy(new GalaxyRecipe(GalaxyRecipe.KILL), galaxies, prop);
		killer.evolve(totalGen);
		galaxies.removeAllElements();
		galaxies.addElement(killer);
		galaxies.addElement(run);
		galaxies.addElement(suicider);
		galaxies.addElement(eater);
		Galaxy winner = new Galaxy(new GalaxyRecipe(GalaxyRecipe.WIN), galaxies, prop);
		winner.evolve(totalGen);
		
		
		
		
	}
	public void loadGalaxies(String baseDir,int GalaxyNumber) throws Exception
	{
		System.out.println(baseDir);
		int totalGen = prop.getIntProperty(MarioPropertiesKeys.GENERATION_NUM);
//		Galaxy win = new Galaxy(new GalaxyRecipe(GalaxyRecipe.WIN), null, prop);
//		win.evolve(60);
//		prop.setProperty("popul.size",Integer.toString(300));
		Galaxy eat = new Galaxy(new GalaxyRecipe(GalaxyNumber), null, prop);
		if(baseDir!=null)
		eat.loadGenotype(baseDir+"/"+GalaxyRecipe.planetNames[GalaxyNumber]);
		eat.evolve(totalGen);
		
//		Galaxy run = new Galaxy(new GalaxyRecipe(GalaxyRecipe.RUNNER), null, prop);
//		run.evolve(totalGen);
//		baseDir+= "/"+GalaxyRecipe.planetNames[GalaxyRecipe.UNBIASED];
//		Vector<Galaxy> galaxies = new Vector<Galaxy>();
////		galaxies.addElement(run);
//		Galaxy bricker = new Galaxy(new GalaxyRecipe(GalaxyRecipe.BRICKER),galaxies , prop);
//		bricker.loadGenotype(baseDir);
//		bricker.evolve(totalGen);
//		galaxies.removeAllElements();
////		galaxies.addElement(bricker);
//		Galaxy eater = new Galaxy(new GalaxyRecipe(GalaxyRecipe.EATER), galaxies, prop);
//		eater.loadGenotype(baseDir);
//		eater.evolve(totalGen);
//		galaxies.removeAllElements();
////		galaxies.addElement(eater);
////		galaxies.addElement(run);
//		Galaxy dragon = new Galaxy(new GalaxyRecipe(GalaxyRecipe.FIRE), galaxies, prop);
//		dragon.loadGenotype(baseDir);
//		dragon.evolve(totalGen);
//		Galaxy bigAss = new Galaxy(new GalaxyRecipe(GalaxyRecipe.STOMP), galaxies, prop);
//		bigAss.loadGenotype(baseDir);
//		bigAss.evolve(totalGen);
//		Galaxy bowler = new Galaxy(new GalaxyRecipe(GalaxyRecipe.SHELL), galaxies, prop);
//		bowler.loadGenotype(baseDir);
//		bowler.evolve(totalGen);
//		galaxies.removeAllElements();
//		galaxies.addElement(bowler);
//		galaxies.addElement(bigAss);
//		galaxies.addElement(dragon);
//		Galaxy killer = new Galaxy(new GalaxyRecipe(GalaxyRecipe.KILL), galaxies, prop);
//		killer.evolve(totalGen);
//		galaxies.removeAllElements();
//		Galaxy suicider = new Galaxy(new GalaxyRecipe(GalaxyRecipe.SUICIDE), galaxies, prop);
//		suicider.loadGenotype(baseDir);
//		suicider.evolve(totalGen);
//		galaxies.addElement(killer);
//		galaxies.addElement(run);
//		galaxies.addElement(suicider);
//		galaxies.addElement(eater);
//		Galaxy winner = new Galaxy(new GalaxyRecipe(GalaxyRecipe.WIN), galaxies, prop);
//		winner.mConfig.setInvalunable(false);
//		winner.evolve(totalGen);
		
	}
	public static void main(String[] args) throws Exception {
int[] galaxyNames = {GalaxyRecipe.UNBIASED,GalaxyRecipe.EATER,GalaxyRecipe.KILL,GalaxyRecipe.FLOWER,GalaxyRecipe.BRICKER,GalaxyRecipe.FIRE};
//		for(int i=0;i<5 ;i++)	{
			
			try
			{
//				basedir = "db/noConnection1";
				Universe maa = new Universe("mario.properties","db/noConnectiongfsd10");
				maa.loadGalaxies(null,GalaxyRecipe.UNBIASED);
//				SigmoidActivationFunction.SLOPE=SigmoidActivationFunction.SLOPE*10;
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
			}
			
//		}
//		maa = new Universe("mario.properties","db/CrfadazyMarioRun15");
		
//		maa.loadGalaxies(null,GalaxyRecipe.EATER);
//		maa = new Universe("mario.properties","db/CrazyMarioRun"+(maa.IDNumber-1));
//		maa.loadGalaxies("db/Unbiased",GalaxyRecipe.KILL);
	}
	public void setRunNumber(Properties props)
	{
		for(int i=0;;i++)
		 {
			 
			 if(Files.notExists(Paths.get(props.getProperty("persistence.base.dir")+i), LinkOption.NOFOLLOW_LINKS))
			 {
				 this.IDNumber=i;
				 UniverseStateVariables.runNumber=i;
				 return;
			 }
			 
		 }
		
	}

}
