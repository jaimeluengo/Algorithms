import java.util.ArrayList;

public class DPKnapsacktoSP {
	private class Graph{
		int[] weightVector;
		int[] valueVector;
		int[] nodeToItemMap;
		ArrayList<Edge> edgeSet = new ArrayList<>();
		ArrayList<Integer> edgeGroup = new ArrayList<>();
		int weightLimit, numVertices;
		
		
		Graph(int[] wV, int[] vV, int wL){
			weightVector = wV;
			valueVector = vV;
			weightLimit = wL;
			numVertices = wV.length * wV.length +2;
			nodeToItemMap = new int[numVertices];
			
			buildEdges();
		}
		
		
		void buildEdges() {
			//Construct edges from source
			int lastEdgesNum =0, sink = numVertices-1;
			for(int i =0; i<weightVector.length;i++) {
				if(weightVector[i] < weightLimit ) {
					edgeSet.add(new Edge(0, i+1,-valueVector[i],
						weightVector[i]));
					edgeSet.add(new Edge(i+1, sink, 0, weightVector[i]));
					lastEdgesNum+=2;
				}
				edgeGroup.add(lastEdgesNum);
			}
			
			//Edges in between items
			for(int k =1; k<weightVector.length; k++) {
				int dN = 1+ k*weightVector.length; //displacementNumber
				int tempLastEdgesNum = lastEdgesNum, sizeEdgeSet = edgeSet.size();
				lastEdgesNum = 0;
				for(int i =0; i<weightVector.length;i++) {

					for(int j =0; j<tempLastEdgesNum;j++) {
						Edge edge =  edgeSet.get(sizeEdgeSet -j);
						if(i!=edge.dest &&
								i!= sink &&
								edge.weightSofar +weightVector[i] < weightLimit) {
							
							edgeSet.add(new Edge(edge.dest, dN + i,-valueVector[i],
									edge.weightSofar +weightVector[i]));
							edgeSet.add(new Edge(dN+i, sink, 0,
									edge.weightSofar +weightVector[i]));
							lastEdgesNum+=2;
						}
					}
				}
				edgeGroup.add(lastEdgesNum);
			}
			
//			//Construct edges from last colum of items to sink
//			int sizeEdgeSet = edgeSet.size();
//			for(int i =0; i<lastEdgesNum;i++) {
//				Edge edge =  edgeSet.get(sizeEdgeSet -i);
//				edgeSet.add(new Edge(edge.dest, weightVector.length+1,-valueVector[i],
//						edge.weightSofar));
//			}
//			edgeGroup.add(lastEdgesNum);
		}
	}
		
		
		private class Edge{
			int source, dest, cost, weightSofar;
			Edge(int s, int d, int c, int w){
				source = s;
				dest =  d;
				cost = c;
				weightSofar = w;
			}
			
		}
		
		
		private int[] solveKnapsack(int[] weights, int[] values, int weightLimit) {
			Graph g = new Graph(weights, values, weightLimit);
			return solveShortestPath(g);
		}
		
		private int[] solveShortestPath(Graph g) {
			int[] dist = new int[g.numVertices], prevNode =new int[g.numVertices];
			
			//Initialize all nodes with distance inf except source
			for(int i =1; i<g.numVertices; i++) {
				dist[i]= Integer.MAX_VALUE;
			}
			
			//go through each time step and examine all the edges for min cost
	        for (int i=1; i<g.numVertices; ++i) { 
	        	int edgeCounter = 0, edgeGroupCounter = 0;
	            for (Edge e: g.edgeSet){
	            	if(edgeCounter >g.edgeGroup.get(edgeGroupCounter))edgeGroupCounter++;
	            	int start = e.source + (edgeGroupCounter * g.weightVector.length);
	            	// for group 3, start = 1 for the source + e.source of the edge plus a dsiplacer
	            	int end = e.dest + (edgeGroupCounter * g.weightVector.length);
	                if (dist[start]!=Integer.MAX_VALUE && 
	                        dist[start]+e.cost<dist[end]){ 
	                        dist[end]=dist[start]+e.cost;
	                        prevNode[end] = e.source; 
	                }
	                edgeCounter++;
	            }
	        }
	        
	        //reconstruct solution
	        return constructPath(g, prevNode, g.numVertices-1);		
			
		}
		
		private int[] constructPath(Graph g, int[] prevNode, int end) {
			int[] path = new int[g.weightVector.length];
			for(int i = g.weightVector.length-1; i>-1 ; i--) {
				path[i] = prevNode[end];
				end = path[i] +(i*g.weightVector.length);
			}
			return path;
		}
		
		public void main(String[] Args) {
			int[] weights = new int[] {12, 1, 4, 2, 1};
			int[] values = new int[] {4, 2, 10, 2, 1};
			int weightLimit =5;
			DPKnapsacktoSP p2 = new DPKnapsacktoSP();
			int[] result = p2.solveKnapsack(weights,values,weightLimit);
		}
		
}


