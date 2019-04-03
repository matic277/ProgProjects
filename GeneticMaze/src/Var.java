import java.awt.Rectangle;

public class Var {
	
	//public static Var Vars = new Var();
	
	
	public static Environment environment;
	
	public static double
		mutationRate = 0.15
	;
	
	public static boolean
		mutation = true
	;
	
	public static int
		buttonSpaceHeight = 50,
		width = 800,
		height = buttonSpaceHeight + 800,
		
		populationSize = 100,
		DnaLength = 100,
		vectorMaxValue = 5,
		vectorMinValue = -5,
		
		generationNumber = 0,
		dnaIndex = 0,
		
		mazeHeight = 30,
		mazeWidth = 30,
		squareSize = height / mazeHeight
	;
	
	public static Rectangle end;
	public static Rectangle start;
	
	
	public static int 		
		obstacleThickness = (int)(Math.sqrt(vectorMaxValue*vectorMaxValue + vectorMinValue*vectorMinValue)) * 4
	;
	
	public static double
		averageFitness,
		
		iterationSleep = 1000 / 60
	;

}
