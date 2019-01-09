
public class GridChunker {
	
	int n;			// number of chunks
	int g[][];
	Chunk chunks[];
	
	public GridChunker(int gg[][], int nn) {
		g = gg;
		n = nn;
		
		//System.out.println(gg.length + " " + gg[0].length);
		
		chunks = new Chunk[n];
	}
	
	public void ChunkGrid() {
		int chunk = g.length / n ;
		int lastchunk = 0;
		
		for (int i=0; i<n-1; i++, lastchunk = i*chunk) {
			chunks[i] = new Chunk(g, i*chunk, i*chunk+chunk);
		}
		chunks[n-1] = new Chunk(g, lastchunk, g.length);
		
		/*
		for (int i=0; i<chunks.length; i++) {
			chunks[i].print();
		}
		*/
		
		
	}
	
	public int[][] getGridById(int id) {
		return chunks[id].g;
	}
	

}
