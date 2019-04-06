package Main;

import java.awt.Rectangle;

public class Var {
	
	public static Environment environment;
	public static EditingType editType = EditingType.MAZE;
	
	public static double
		mutationRate = 0.15
	;
	
	public static boolean
		mutation = true
	;
	
	public static int subjectSize = 8;
	
	public static int
		buttonSpaceHeight = 50,
		width = 800,
		height = buttonSpaceHeight + 800,
		
		populationSize = 20,
		DnaLength = 200,
		vectorLength = 5,
		vectorMaxValue = 5,
		vectorMinValue = -5,
		vectorAngle = 30,
		
		generationNumber = 0,
		dnaIndex = 0,
		
		mazeHeight = 30,
		mazeWidth = 30,
		squareSize = (int)(Math.sqrt(vectorMaxValue*vectorMaxValue + vectorMinValue*vectorMinValue))  + 2, //height / mazeHeight
		minRectSize = 10
	;
	
	public static Rectangle end;
	public static Rectangle start;
	
	public static int	// UNUSED ? -> using square size
		obstacleThickness = (int)(Math.sqrt(vectorMaxValue*vectorMaxValue + vectorMinValue*vectorMinValue)) * 4
	;
	
	public static double
		averageFitness,
		
		iterationSleep = 1000 / 60
	;
	
	// variables of boxes for start and end
	public static int 
		startWidth = 30,
		startHeight = 30,
		
		endWidth = 30,
		endHeight = 30
	;

}
