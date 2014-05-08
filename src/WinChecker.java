import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WinChecker {

	protected boolean draw(Gameboard board) {

		List<List<Hexagon>> hexagons;
		boolean draw = true;

		hexagons = board.getBoard();

		outerloop: for (List<Hexagon> tempList : hexagons) {
			innerloop: for (Hexagon tempHexagon : tempList) {

				if (tempHexagon == null) {
					continue innerloop;
				}

				if (tempHexagon.value == '-') {
					draw = false;
					break outerloop;
				}

			}
		}

		return draw;

	}

	protected boolean tripodWin(Gameboard board, char player) {

		List<List<Hexagon>> hexagons;

		int[] edgesIndicators = new int[6];
		int numberOfEdgeHexagons = 0;
		int order = 1;
		boolean win = false;
		Stack<Hexagon> hexagonStack = new Stack<Hexagon>();

		hexagons = board.getBoard();

		outerloop: for (List<Hexagon> tempList : hexagons) {
			innerloop: for (Hexagon tempHexagon : tempList) {

				if (tempHexagon == null)
					continue innerloop;
				else if (tempHexagon.value != player)
					continue innerloop;
				else if (checkAdjacency(tempHexagon, hexagons,
						board.getTotalRows(), player) == 0)
					continue innerloop;
				else if (tempHexagon.getChecked() != 0)
					continue innerloop;

				hexagonStack.push(tempHexagon);

				while (!hexagonStack.isEmpty()) {
					Hexagon currentHex = hexagonStack.pop();

					adjacencyLoop: for (Coordinate coords : currentHex
							.getAdjacencies()) {

						// Check if next hexagon is on the board
						if (coords.getRow() == 999 || coords.getColumn() == 999)
							continue adjacencyLoop;

						Hexagon next = hexagons.get(coords.getRow()).get(
								coords.getColumn());

						if (next.value != player)
							continue adjacencyLoop;
						// Check if next hexagon has already been checked, or to
						// be checked
						else if (hexagonStack.contains(next)
								|| next.checked != 0)
							continue adjacencyLoop;

						if (next.value == currentHex.value) {
							hexagonStack.push(next);
						}

					}// End of adjacencyLoop

					// Determining whether a tripod has been formed.
					// ====================================================================================================================

					int sum = 0;
					for (int i = 0; i < 6; i++) {// Finding the number of
													// exposed edges that the
													// hexagon has
						sum += currentHex.adjacencies.get(i).getRow();
					}

					int numberOfExposedEdges = sum / 999;

					if (numberOfExposedEdges == 2) { // Checking to see if the
														// current hexagon is
														// only an edge piece
						for (int i = 0, j = 1; i < 6; i++) { // If not a corner,
																// finding which
																// edge of the
																// board it is
																// on

							if (j > 5)
								j = 0;

							if (currentHex.adjacencies.get(i).getRow() == 999
									&& currentHex.adjacencies.get(j).getRow() == 999) {
								edgesIndicators[i] = 1;
							}

							j++;
						}

					}

					currentHex.setChecked(order);
					order++;

				}// End of while loop

				// ====================================================================================================================

				// Determining if a tripod has occurred
				// ====================================================================================================================

				// Summing the number of edge hexagons possible for a tree...
				for (int i = 0; i < 6; i++) {
					numberOfEdgeHexagons += edgesIndicators[i];
				}

				if (numberOfEdgeHexagons >= 3) {

					win = true; // Wins by having a tree
					break outerloop;
				}

				// resetting the tripod indicators
				numberOfEdgeHexagons = 0;
				for (int i = 0; i < 6; i++) {
					edgesIndicators[i] = 0;
				}

			}// End of inner forloop
		}// End of outer forloop
		resetCheckedState(board);
		return win;

	}

	protected boolean loopWin(Gameboard board, char player) {

		List<List<Hexagon>> hexagons;

		int[] edgesIndicators = new int[6];
		int numberOfEdgeHexagons = 0;
		int order = 1;
		boolean win = false;
		// boolean foundLoop = false;
		Stack<Hexagon> hexagonStack = new Stack<Hexagon>();

		hexagons = board.getBoard();

		outerloop: for (List<Hexagon> tempList : hexagons) {
			innerloop: for (Hexagon tempHexagon : tempList) {

				if (tempHexagon == null) {
					continue innerloop;
				} else if (tempHexagon.value == player) {
					continue innerloop;
				} else if (tempHexagon.getChecked() != 0) {
					continue innerloop;
				}

				hexagonStack.push(tempHexagon);

				while (!hexagonStack.isEmpty()) {
					Hexagon currentHex = hexagonStack.pop();

					adjacencyLoop: for (Coordinate coords : currentHex
							.getAdjacencies()) {

						// Check if next hexagon is on the board
						if (coords.getRow() == 999 || coords.getColumn() == 999)
							continue adjacencyLoop;

						Hexagon next = hexagons.get(coords.getRow()).get(
								coords.getColumn());

						if (next.value == player) {
							continue adjacencyLoop;
						}
						// Check if next hexagon has already been checked, or to
						// be checked
						else if (hexagonStack.contains(next)
								|| next.checked != 0) {
							continue adjacencyLoop;
						}

						if (next.value != player) {
							hexagonStack.push(next);
						}

					}// End of adjacencyLoop

					// Determining whether a tripod has been formed.
					// ====================================================================================================================

					int sum = 0;

					for (int i = 0; i < 6; i++) {// Finding the number of
													// exposed edges that the
													// hexagon has
						sum += currentHex.adjacencies.get(i).getRow();
					}

					int numberOfExposedEdges = sum / 999;

					if (numberOfExposedEdges == 2) { // Checking to see if the
														// current hexagon is
														// only an edge piece
						for (int i = 0, j = 1; i < 6; i++) { // If not a corner,
																// finding which
																// edge of the
																// board it is
																// on

							if (j > 5) {
								j = 0;
							}

							if (currentHex.adjacencies.get(i).getRow() == 999
									&& currentHex.adjacencies.get(j).getRow() == 999) {
								edgesIndicators[i] = 1;
							}

							j++;
						}

					}

					currentHex.setChecked(order);
					order++;

				}// End of while loop

				// ====================================================================================================================

				// Determining if a tripod has occurred
				// ====================================================================================================================

				// Summing the number of edge hexagons possible for a tree...
				for (int i = 0; i < 6; i++) {
					numberOfEdgeHexagons += edgesIndicators[i];
				}

				if (numberOfEdgeHexagons == 0) {

					win = true; // Wins by having a loop
					break outerloop;
				}

				// resetting the tripod indicators
				numberOfEdgeHexagons = 0;
				for (int i = 0; i < 6; i++) {
					edgesIndicators[i] = 0;
				}

			}// End of inner forloop
		}// End of outer forloop
		resetCheckedState(board);
		return win;

	}
	

	protected void resetCheckedState(Gameboard board) {

		List<List<Hexagon>> tempBoard = board.getBoard();

		for (List<Hexagon> tempList : tempBoard) {
			for (Hexagon tempHex : tempList) {
				if (tempHex != null) {
					tempHex.checked = 0;
				}
			}
		}

	}

	// Checking the 6 adjacent pieces to see if they are the same value as the
	// hexagon we are checking
	protected int checkAdjacency(Hexagon toCheck,
			List<List<Hexagon>> board, int totalRows, char player) {

		ArrayList<Coordinate> adjacencies = toCheck.getAdjacencies();

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

}