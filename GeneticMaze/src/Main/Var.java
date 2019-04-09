package Main;

import java.awt.Rectangle;

public class Var {
	
	public static Environment environment;
	public static EditingType editType = EditingType.MAZE;
	
	public static double
		mutationRate = 0.15,
		averageFitness
	;
	
	public static long
		iterationSleep = 1000 / 60,
		FPS = 1000 / 60 // drawing speed
	;
	
	public static boolean
		mutation = true
	;
	
	public static int
		buttonSpaceHeight = 50,
		rightPanelWidth = 400,
		
		drawingWidth = 800,
		drawingHeight = 800,
		
		width = drawingWidth + rightPanelWidth,
		height = drawingHeight + buttonSpaceHeight,
		
		populationSize = 20,
		subjectSize = 8,
		DnaLength = 200,
		vectorLength = 5,
		vectorMaxValue = 5,
		vectorMinValue = -5,
		vectorAngle = 30,
		
		generationNumber = 0,
		dnaIndex = 0,
		
		mazeHeight = 30, //__ not used
		mazeWidth = 30,  //
		squareSize = (int)(Math.sqrt(vectorMaxValue*vectorMaxValue + vectorMinValue*vectorMinValue))  + 2, //height / mazeHeight - NOT USED ANYMORE
		minRectSize = 10
	;
	
	public static Rectangle end;
	public static Rectangle start;
	
	public static int	// UNUSED ? -> using square size
		obstacleThickness = (int)(Math.sqrt(vectorMaxValue*vectorMaxValue + vectorMinValue*vectorMinValue)) * 4
	;
	
	
	// variables of boxes for start and end
	public static int 
		startWidth = 30,
		startHeight = 30,
		
		endWidth = 30,
		endHeight = 30
	;

}
