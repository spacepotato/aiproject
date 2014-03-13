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
				System.out.println(tempHexagon.toString()
						+ "and has "
						+ checkAdjacency(tempHexagon, generatedBoard,
								board.getTotalRows()) + " adjacent pieces");

			}
		}

	}

	protected static int checkAdjacency(Hexagon toCheck,
			List<List<Hexagon>> board, int totalRows) {

		Integer[] checkSide = toCheck.getAdjacentSide();
		Integer[] checkBelow = toCheck.getAdjacentBelow();

		List<Hexagon> sideCheck = board.get(toCheck.getRow());

		int numberAdjacent = 0;

		// If our hexagon is in the bottom row then there is no point checking
		// the rows below it...
		if (toCheck.getRow() != totalRows - 1) {
			List<Hexagon> belowCheck = board.get(toCheck.getRow() + 1);

			for (Hexagon temp : belowCheck) {
				if ((temp.getColumn() == checkBelow[0] || temp.getColumn() == checkBelow[1])
						&& temp.getValue() == 'W') {
					numberAdjacent += 1;
					continue;
				}
			}
		}

		for (Hexagon temp : sideCheck) {
			if ((temp.getColumn() == checkSide[0] || temp.getColumn() == checkSide[1])
					&& temp.getValue() == 'W') {
				numberAdjacent += 1;
				continue;
			}
		}

		return numberAdjacent;
	}
}
