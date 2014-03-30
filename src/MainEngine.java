import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
  //import java.util.PriorityQueue;
//import java.util.Comparator;
import java.util.Stack;

public class MainEngine {

	public static void main(String args[]) throws IOException {

		List<List<Hexagon>> generatedBoard;

		Gameboard board = new Gameboard(args[0]);
		board.readTextFile();

		generatedBoard = board.getBoard();

		char player = 'B';
		
		for (List<Hexagon> tempList : generatedBoard) {
			for (Hexagon tempHexagon : tempList) {
				if (tempHexagon != null) {
					System.out.println(tempHexagon.toString()
							+ "and has "
							+ checkAdjacency(tempHexagon, generatedBoard,
									board.getTotalRows(), player));

					// tempHexagon.printAdjacencies();

				}

			}
		}

		if (isItAWin(board, player)) {
			;
			System.out.println("We have a winner");
		}
		else{
			System.out.println("No winner was found");
		}

	}// End of main method

	// Checking the 6 adjacent pieces to see if they are the same value as the
	// hexagon we are checking
	protected static int checkAdjacency(Hexagon toCheck,
			List<List<Hexagon>> board, int totalRows, char player) {

		ArrayList<Coordinate> adjacencies = toCheck.getAdjacencies();
		//char currentValue = 'W';
		int numberAdjacent = 0;

		for (Coordinate tempCoords : adjacencies) {
			int tempColumn = tempCoords.getColumn();
			int tempRow = tempCoords.getRow();

			if (tempColumn != 999
					&& board.get(tempRow).get(tempColumn).getValue() == player) {
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
		boolean win = false;
		//boolean foundLoop = false;
		Stack<Hexagon> hexagonStack = new Stack<Hexagon>();
//		PriorityQueue<Hexagon> hexagonQueue = new PriorityQueue<Hexagon>(100, new Comparator<Hexagon>());
//	    PriorityQueue<Hexagon> hexagonQueue = new PriorityQueue<Hexagon>(100, new Comparator<Hexagon>() {
//	        public int compare(Hexagon hexagon1, Hexagon hexagon2) {
//	            return (hexagon1.getRow() > hexagon2.getRow()) ? 1: -1;
//	        }
//	    });
		hexagons = board.getBoard();

		outerloop: for (List<Hexagon> tempList : hexagons) {
			innerloop: for (Hexagon tempHexagon : tempList) {
				
				if(tempHexagon == null) continue innerloop;
			    else if(tempHexagon.value != player) continue innerloop;
				else if(checkAdjacency(tempHexagon,hexagons,board.getTotalRows(), player) == 0) continue innerloop;
				else if(tempHexagon.getChecked() != 0) continue innerloop; 
				
				hexagonStack.push(tempHexagon);
				
				while(!hexagonStack.isEmpty()){
					//System.out.println("In while loop...");
					Hexagon currentHex = hexagonStack.pop();

					adjacencyLoop: for(Coordinate coords : currentHex.getAdjacencies()){
						
						//Check if next hexagon is on the board
						if(coords.getRow() == 999 || coords.getColumn() == 999) continue adjacencyLoop; 
						
						Hexagon next = hexagons.get(coords.getRow()).get(coords.getColumn());
						
						if(next.value != player) continue adjacencyLoop;
						//Check if next hexagon has already been checked, or to be checked
						else if(hexagonStack.contains(next) || next.checked != 0) continue adjacencyLoop; 
												
						if(next.value == currentHex.value){
							hexagonStack.push(next);
						}

					}//End of adjacencyLoop


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

					if(numberOfEdgeHexagons == 3){
						win = true; //Wins by having a tree
						break outerloop;
					}

					//====================================================================================================================

					//Determining if a loop has occurred
					//====================================================================================================================

					currentHex.setChecked(order);
					order++;
					
					numberOfEdgeHexagons = 0;
					for(int i : edgesIndicators){
						edgesIndicators[i] = 0;
					}

				}//End of while loop
				
			}//End of inner forloop
		}//End of outer forloop




		return win;


	}

}
