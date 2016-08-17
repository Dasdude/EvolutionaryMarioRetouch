package marioplanet.environment;

public class UniverseStateVariables {
	public static int generationNumber = 0;
	public static int runNumber=-1;
	public static boolean endRun = false;
	public static String planetName;
	public static void setVariables(Galaxy a)
	{
		generationNumber=0;
		planetName=a.getName();
		
	}
}
