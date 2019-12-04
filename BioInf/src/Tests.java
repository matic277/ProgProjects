import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
		
//		List<String> res = Bio.getORFhelper(input1);
//		System.out.println(res.size());
		
//		String calcOutput1 = Bio.getORFhelper(input1).get(0);
//		System.out.println(calcOutput1);
		
//		Assert.assertEquals(expectedOutput1, calcOutput1);
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
	
	@Test
	void getllReadingFramesTest() {
		ArrayList<String> expectedResult = new ArrayList<String>();
		expectedResult.add("MGTVQSTRNGELHG");
		expectedResult.add("MDSAV");
		expectedResult.add("M");
		expectedResult.add("MLAIMHD");
		expectedResult.add("MHD");
		expectedResult.add("MADCLGE");
		expectedResult.add("MGNSMVECRCRPRLSVALIALRRPVRRNSKWTVQYKYEA");
		expectedResult.add("MVECRCRPRLSVALIALRRPVRRNSKWTVQYKYEA");
		expectedResult.add("MINTETTARKTSLGLIPK");
		expectedResult.add("MGLVHGPVGTTPQRVSSGRNS");
		expectedResult.add("MRHRPDI");
		expectedResult.add("MVRWEQLLNVSRQEETADCIYLVSTKTWKYKVSSTKCQISARWITKFYGRSKRPLFTVGNKTST");
		expectedResult.add("MFPTHLGNCYSRAEIPDACASNQANCAGVCSLLLCTSRRVGREGIIIRLLNIRSRSYFLQ");
		expectedResult.add("MVVVAPMVGVHFGINPRLVFRAVVSVLIMHDSKHLYSNQD");
		expectedResult.add("MVGVHFGINPRLVFRAVVSVLIMHDSKHLYSNQD");
		expectedResult.add("MHDSKHLYSNQD");
		expectedResult.add("MLAPQTKPTVQVYVRFCYALLAAWAGKG");
		expectedResult.add("MIASIFTVIKTEGRTCKSLNADRATVYMAWNQASAVIAFSRTR");
		expectedResult.add("MAWNQASAVIAFSRTR");
		expectedResult.add("MPHIYTALSISNCGVPDVGAR");
		expectedResult.add("MFAFVMHFSPRGQGRDNNSSIKY");
		expectedResult.add("MHFSPRGQGRDNNSSIKY");
		expectedResult.add("MQSAVSS");
		expectedResult.add("MY");
		
		String input = "TGCATGGGGACGGTACAAAGCACGCGAAATGGGGAACTCCATGGTTGAGT"
				+ "GCAGGTGCCGCCCGCGATTAAGTGTCGCGCTCATCGCGCTCCGACGTCCGGTACGC"
				+ "CGCAATTCGAAATGGACAGTGCAGTATAAATATGAGGCATAGACCGGATATCTGAGC"
				+ "TTAAAAGGCCAGACTAACGCGTTCTACTGAAGGCTATCACGGCAGACGCCTGATTCCA"
				+ "GGCCATGTAGACAGTAGCCCGATCTGCGTTCAAACTCTTACACGTTCGCCCTTCAGTC"
				+ "TTGATTACTGTAAAGATGCTTGCTATCATGCATGATTAACACTGAAACGACAGCGCGAA"
				+ "AGACAAGTCTTGGGTTGATTCCGAAGTGAACCCCAACCATTGGAGCGACTACAACCATAC"
				+ "AGAATTGTAGGTGGAGATGGCGGACTGTTTGGGTGAGTAGCTACTCACCCAAACAGTCCG"
				+ "CCATTCAAGCGTATATATCGGAAGCTAGTATGGGACTAGTACATGGTCCGGTGGGAACAA"
				+ "CTCCTCAACGTGTCTCGTCAGGAAGAAACAGCTGACTGCATTTACCTAGTTTCTACAAAAA"
				+ "CCTGGAAATACAAGGTATCGTCGACCAAGTGTCAAATCTCGGCCCGTTGGATAACTAAGTT"
				+ "CTACGGCAGATCGAAGCGTCCACTCTTTACTGTAGGAAATAAGACCTCGACCTAATATTTA"
				+ "ATAGACGAATTATTATCCCTTCCCTGCCCACGCGGCGAGAAGTGCATAACAAAAGCGAACAT"
				+ "ACACCTGCACAGTTGGCTTGGTTTGAGGCGCAAGCATCAGGGATTTCAGCTCGCGAGTAACA"
				+ "ATTGCCGAGATGAGTTGGGAACATTTTGTTAACGTGGGAATGCCCCCTTCC";
		
		List<String> calcResult = Bio.getORFs(input);
		
		
		//Assert.assertTrue(calcResult.stream().allMatch(expectedResult::contains));
		Assert.assertEquals(expectedResult, calcResult);
	}

}
