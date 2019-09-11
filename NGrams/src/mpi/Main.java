package mpi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import common.FileReader;
import common.Gram;
import single.NGram;

public class Main {
	
	static int id = -1, size;
	static int n = 1;

	public static void main(String[] args) {	
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		
		FileReader reader;
		
		String[] words;
		
			// vsi berejo file.... krneki
			reader = new FileReader(FileReader.test);
			words = reader.getWords().toArray(new String[2]);

		int chunk = words.length / size;
		int start = id * chunk;
		int end = (id == size)? words.length : start + chunk;
		// System.out.println("start, end: " + start + ", " + end);
		
		// create a list of words, based on start and end indexes
		String[] localWords = new String[chunk];
		for (int i=start; i<end; i++) localWords[i] = words[i];	
		
		// process them
		NGram ngram = new NGram(n, localWords, false);
		
		// encode
		HashMap<String, Gram> table = ngram.table;
		String[] processedLocalWords = new String[table.size()];
		int i = 0;
		for (Gram g : table.values()) {
			processedLocalWords[i] = g.encode();
			i++; 
		}
		
		int recBufsize = 0;
		String[] recBuf = new String[words.length];
		
		// send buffer is localWords,
		// receive buffer is recBuf, size processedLocalWords_of_all
		// offset is chunk ?
		
		// gather by master
		MPI.COMM_WORLD.Gather(
			localWords,
			0, //sendoffset,
			2, //sendcount,
			MPI.OBJECT, //sendtype,
			recBuf, //recvbuf,
			0, //recvoffset,
			2, //recvcount,
			MPI.OBJECT, //recvtype,
			0 //root
		);		
		
		
//		int[] recBuf = new int[size * 2];
//		int[] sendBuf = new int[2];
//		sendBuf[0] = new Random().nextInt(9);
//		sendBuf[1] = new Random().nextInt(9);
//		
//		System.out.println("id " + id + " -> " + sendBuf[0] + ", " + sendBuf[1]);
//		
//		sleep(100);
//		
//		MPI.COMM_WORLD.Gather(
//			sendBuf,
//			0, //sendoffset,
//			2, //sendcount,
//			MPI.INT, //sendtype,
//			recBuf, //recvbuf,
//			0, //recvoffset,
//			2, //recvcount,
//			MPI.INT, //recvtype,
//			0 //root
//		);
//		
//		if (id == 0) {
//			System.out.println("master received");
//			for (int i : recBuf) System.out.println(i);
//		}
		
		MPI.Finalize();
	}
	
	public static void sleep(long t) {
		try { Thread.sleep(t); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

}
