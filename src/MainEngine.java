import java.io.IOException;
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
				}

			}
		}

	}

	//Checking the 6 adjacent pieces to see if they are the same value as the hexagon we are checking
	protected static int checkAdjacency(Hexagon toCheck,
			List<List<Hexagon>> board, int totalRows) {

		Integer[] checkSide = toCheck.getAdjacentSide();
		Integer[] checkBelow = toCheck.getAdjacentBelow();
		Integer[] checkAbove = toCheck.getAdjacentAbove();

		int currentRow = toCheck.getRow();
		
		char currentValue = 'W';

		
		int numberAdjacent = 0;

		if (checkSide[0] != 999
				&& checkSide[0] != board.get(currentRow).size()) {
			if (board.get(currentRow).get(checkSide[0]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}

		if (checkSide[1] != 999
				&& checkSide[1] != board.get(currentRow).size()) {
			if (board.get(currentRow).get(checkSide[1]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}

		if (checkBelow[0] != 999) {
			if (board.get(currentRow + 1).get(checkBelow[0]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}

		if (checkBelow[1] != 999) {
			if (board.get(currentRow + 1).get(checkBelow[1]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}
		
		if (checkAbove[0] != 999) {
			if (board.get(currentRow - 1).get(checkAbove[0]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}

		if (checkAbove[1] != 999) {
			if (board.get(currentRow - 1).get(checkAbove[1]).getValue() == currentValue) {
				numberAdjacent++;
			}
		}

		return numberAdjacent;
	}
	
	
	protected static boolean isItAWin(Gameboard board, char player){
		
		boolean win = false;
		List<List<Hexagon>> hexagons;
		int currentCol, currentRow;
		int [] edgesIndicators = new int[6];
		int edgenum = 0;
		int order = 1;
		boolean foundLoop = false;
		
		PriorityQueue<Hexagon> hexagonQueue = new PriorityQueue<Hexagon>();
		
		hexagons = board.getBoard();
		
		for (List<Hexagon> tempList : hexagons) {
			for (Hexagon tempHexagon : tempList) {
				
				if(checkAdjacency(tempHexagon, hexagons, board.getTotalRows()) == 0 || tempHexagon.value != player){
					continue; // Hexagon has no adjacent pices of same colour, or is wrong colour, move on to next hexagon
				}
				if(tempHexagon.checked != 0) continue; // skip over checked hexagons.
				
				hexagonQueue.add(tempHexagon);
				
				while(!hexagonQueue.isEmpty()){
					Hexagon currentHex = hexagonQueue.poll();
					
					for(Coordinate coords : currentHex.getAdjacencies()){
						Hexagon next = hexagons.get(coords.getRow()).get(coords.getColumn());
						if(next.value == currentHex.value){
							hexagonQueue.add(next);
						}
												
					}
					
					//Finding the number of edge hexagons in the current path
					if (edgesIndicators[0] == 0 && currentHex.adjacentAbove[0] == 999 && currentHex.adjacentAbove[1] == 999){
						edgesIndicators[0]++;
					}
					else if (edgesIndicators[1] == 0 && currentHex.adjacentAbove[1] == 999 && currentHex.adjacentSide[1] == 999){
						edgesIndicators[1]++;
					}
					else if (edgesIndicators[2] == 0 && currentHex.adjacentBelow[1] == 999 && currentHex.adjacentSide[1] == 999){
						edgesIndicators[2]++;
					}
					else if (edgesIndicators[3] == 0 && currentHex.adjacentBelow[0] == 999 && currentHex.adjacentBelow[1] == 999){
						edgesIndicators[3]++;
					}
					else if (edgesIndicators[4] == 0 && currentHex.adjacentBelow[0] == 999 && currentHex.adjacentSide[0] == 999){
						edgesIndicators[4]++;
					}
					else if (edgesIndicators[5] == 0 && currentHex.adjacentAbove[0] == 999 && currentHex.adjacentSide[0] == 999){
						edgesIndicators[5]++;
					}
					
					//Summing the number of edge hexagons possible for a tree...
					for(int i : edgesIndicators){
						edgenum += edgesIndicators[i];
					}
					
					if(edgenum > 3){
						return true; //Wins by having a tree
					}
					
					
					
					
					
				}
				
				tempHexagon.isChecked(order);
				order++;
			}
		}
		
		
		
		
		return win;
		
		
	}
	
	
}
