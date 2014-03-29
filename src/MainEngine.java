import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MainEngine {

	public static void main(String args[]) throws IOException {

		List<List<Hexagon>> generatedBoard;

		Gameboard board = new Gameboard(args[0]);
		board.readTextFile();

		generatedBoard = board.getBoard();


		for (List<Hexagon> tempList : generatedBoard) {
			for (Hexagon tempHexagon : tempList) {
				if (tempHexagon != null) {
					System.out.println(tempHexagon.toString()
							+ "and has "
							+ checkAdjacency(tempHexagon, generatedBoard,
									board.getTotalRows()));

//						tempHexagon.printAdjacencies();
					
				}

			}
		}
		
		
		isItAWin(board, 'W');
		

	}//End of main method

	//Checking the 6 adjacent pieces to see if they are the same value as the hexagon we are checking
	protected static int checkAdjacency(Hexagon toCheck,
			List<List<Hexagon>> board, int totalRows) {

		ArrayList<Coordinate> adjacencies = toCheck.getAdjacencies();
		char currentValue = 'W';
		int numberAdjacent = 0;

		
		for(Coordinate tempCoords: adjacencies){
			int tempColumn = tempCoords.getColumn();
			int tempRow = tempCoords.getRow();
			
			
			if(tempColumn != 999 && board.get(tempRow).get(tempColumn).getValue() == currentValue){
				numberAdjacent++;
			}
		}

		return numberAdjacent;
	}
	
	
	protected static boolean isItAWin(Gameboard board, char player){
		
		List<List<Hexagon>> hexagons;
		
		int [] edgesIndicators = new int[6];
		int numberOfEdgeHexagons = 0;
		int order = 1;
		//boolean foundLoop = false;
		
//		PriorityQueue<Hexagon> hexagonQueue = new PriorityQueue<Hexagon>(100, new Comparator<Hexagon>());
	    PriorityQueue<Hexagon> hexagonQueue = new PriorityQueue<Hexagon>(100, new Comparator<Hexagon>() {
	        public int compare(Hexagon hexagon1, Hexagon hexagon2) {
	            return (hexagon1.getRow() > hexagon2.getRow()) ? 1: -1;
	        }
	    });
		hexagons = board.getBoard();
		
		for (List<Hexagon> tempList : hexagons) {
			for (Hexagon tempHexagon : tempList) {
				if(checkAdjacency(tempHexagon, hexagons, board.getTotalRows()) == 0 || tempHexagon.value != player){
					continue; // Hexagon has no adjacent pieces of same color, or is wrong color, move on to next hexagon
				}
				if(tempHexagon.checked != 0) continue; // skip over already checked hexagons.
				
				hexagonQueue.add(tempHexagon);
				
				while(!hexagonQueue.isEmpty()){
					Hexagon currentHex = hexagonQueue.poll();
					
					for(Coordinate coords : currentHex.getAdjacencies()){
						if(coords.getRow() == 999 || coords.getColumn() == 999) continue;
						Hexagon next = hexagons.get(coords.getRow()).get(coords.getColumn());
						if(next.value == currentHex.value){
							hexagonQueue.add(next);
							//Currently failing here due to some comparison. I think it has something to do with
							//the use of a priority queue. Not sure if we can use another form of queue or stack.
						}			
					}//End of forloop
					
					//Determining whether a tripod has been formed.
					//====================================================================================================================
				
					int sum=0;
					for(int i=0;i<6;i++){//Finding the number of exposed edges that the hexagon has
						sum += currentHex.adjacencies.get(i).getRow();
					}
					
					int numberOfExposedEdges = sum/999;
					
					if(numberOfExposedEdges == 2){ //Checking to see if the current hexagon is only an edge piece
						for(int i=0, j=i+1;i<6;i++){ //If not a corner, finding which edge of the board it is on
							
							if(j>=6) j=0;
												
							if(currentHex.adjacencies.get(i).getRow() == 999 && currentHex.adjacencies.get(j).getRow() == 999){
								edgesIndicators[i] = 1;
							}
						
							j++;
						}
						
					}
				
										
					//Summing the number of edge hexagons possible for a tree...
					for(int i : edgesIndicators){
						numberOfEdgeHexagons += edgesIndicators[i];
					}
					
					if(numberOfEdgeHexagons > 3){
						return true; //Wins by having a tree
					}
					
					//====================================================================================================================
					
					//Determining if a loop has occurred
					//====================================================================================================================
					
					
					
				}//End of while loop
				
				
				
				
				
				tempHexagon.setChecked(order);
				order++;
			}//End of inner forloop
		}//End of outer forloop
		
		
		
		
		return false;
		
		
	}
	
	
}
