import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void testIsStopCodon() {
		//fail("Not yet implemented");
	}
	
	@Test
	void testIsStartCodon() {
		//fail("Not yet implemented");
	}
	
	
	@Test
	void testDnaToRna() {
		String input = "GATGGAACTTGACTACGTAAATT";
		String expectedOutput = "GAUGGAACUUGACUACGUAAAUU";
		String calcOutput = Bio.dnaToRna(input);
		Assert.assertEquals(calcOutput, expectedOutput);
	}
	
	@Test
	void getOrfSingleString() {
		String input1 = "AUGGCCAUGGCGCCCAGAACUGAGAUCAAUAGUACCCGUAUUAACGGGUGA";
		String expectedOutput1 = "MAMAPRTEINSTRING";
		String calcOutput1 = Bio.getORFhelper(input1).get(0);		
		Assert.assertEquals(expectedOutput1, calcOutput1);
	}
	
	@Test
	void testReverseComplement() {
		String input1 = "CTATAGATTGTCTAATAAGG";
		String calcOut1 = Bio.getReverseComplement(input1);
		String expectedOut1 = "CCTTATTAGACAATCTATAG";
		Assert.assertEquals(calcOut1, expectedOut1);
		
		String input2 = "CATACTCCTAATGGCGTAAT";
		String calcOut2 = Bio.getReverseComplement(input2);
		String expectedOut2 = "ATTACGCCATTAGGAGTATG";		
		Assert.assertEquals(calcOut2, expectedOut2);
	}

}
