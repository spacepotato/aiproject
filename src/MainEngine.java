import java.io.IOException;
import java.util.List;

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
}
