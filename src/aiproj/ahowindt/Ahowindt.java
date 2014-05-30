/**
 * Main class called by the referee
 * @author AHowindt and JMcLaren
 */
package aiproj.ahowindt;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;
import aiproj.fencemaster.Player;

public class Ahowindt implements Player, Piece {

	protected final int success = 1;
	protected final int failure = -1;

	protected Gameboard board;

	protected Move previousMove;

	protected List<List<Hexagon>> hexBoard;

	protected int ourPlayerValue;
	protected int theirPlayerValue;
	protected int size;

	/**
	 * Initializing the board and the player
	 * 
	 * @param n
	 *            : The dimension value of the board
	 * @param p
	 *            : The player value we are assigned
	 */
	@Override
	public int init(int n, int p) {

		this.ourPlayerValue = p;

		this.size = n;

		if (this.ourPlayerValue == WHITE) {
			this.theirPlayerValue = BLACK;
		} else if (this.ourPlayerValue == BLACK) {
			this.theirPlayerValue = WHITE;
		}

		this.board = new Gameboard();

		// Setting up the board based on the provided dimensions
		this.board.generateHexagons(n);

		hexBoard = this.board.getBoard();

		// As defined in the spec, we return a positive value in the event of
		// success and a negative value in the event
		// of failure
		if (this.board != null) {
			return success;
		} else {
			return failure;
		}

	}

	/**
	 * Called by referee to prompt us to make a move on the board
	 */
	@Override
	public Move makeMove() {

		Random rand = new Random();

		Move ourMove = new Move();

		// We randomly choose a piece which is one loop in from the corners
		// We also have it so that if our opponent goes first and makes one
		// of these moves, we replace it
		if (previousMove == null) {

			ourMove.P = ourPlayerValue;
			ourMove.IsSwap = true;
			boolean moveMade = false;

			List<List<Hexagon>> tempBoard = this.board.getBoard();

			if (tempBoard.get(1).get(size - 1).getValue() == theirPlayerValue)
			{
				ourMove.Row = 1;
				ourMove.Col = size - 1;
				moveMade = true;
			} else if (tempBoard.get(size - 1).get(2 * size - 3)
					.getValue() == theirPlayerValue) {
				ourMove.Row = size - 1;
				ourMove.Col = 2 * size - 3;
				moveMade = true;
			} else if (tempBoard.get(2 * size - 3).get(2 * size - 3)
					.getValue() == theirPlayerValue) {
				ourMove.Row = 2 * size - 3;
				ourMove.Col = 2 * size - 3;
				moveMade = true;
			} else if (tempBoard.get(2 * size - 3).get(size - 1)
					.getValue() == theirPlayerValue) {
				ourMove.Row = 2 * size - 3;
				ourMove.Col = size - 1;
				moveMade = true;
			} else if (tempBoard.get(size - 1).get(1)
					.getValue() == theirPlayerValue) {
				ourMove.Row = size - 1;
				ourMove.Col = 1;
				moveMade = true;
			} else if (tempBoard.get(1).get(1)
					.getValue() == theirPlayerValue) {
				ourMove.Row = 1;
				ourMove.Col = 1;
				moveMade = true;
			}
			if (!moveMade) {
				int firstMoveLoc = Math.abs(rand.nextInt(100)) % 6;

				// First move to just inside one of the corners
				if (firstMoveLoc == 0) {
					ourMove.Row = 1;
					ourMove.Col = 1;
				} else if (firstMoveLoc == 1) {
					ourMove.Row = 1;
					ourMove.Col = size - 1;
				} else if (firstMoveLoc == 2) {
					ourMove.Row = size - 1;
					ourMove.Col = 2 * size - 3;
				} else if (firstMoveLoc == 3) {
					ourMove.Row = 2 * size - 3;
					ourMove.Col = 2 * size - 3;
				} else if (firstMoveLoc == 4) {
					ourMove.Row = 2 * size - 3;
					ourMove.Col = size - 1;
				} else if (firstMoveLoc == 5) {
					ourMove.Row = size - 1;
					ourMove.Col = 1;
				} else {
					ourMove.Row = 1;
					ourMove.Col = 1;
				}
			}

		}

		else {

			MinMaxTree mmt;
			mmt = new MinMaxTree(board, ourPlayerValue);
			ourMove = mmt.runMiniMax();

		}
		// We need to make sure our move is valid
		if (this.board.updateBoard(ourMove)) {
			previousMove = ourMove;
			return ourMove;
		} else {
			System.out.println("Invalid move made");
			return null;
		}
	}

	/**
	 * Returns an integer value corresponding to the win state -1 = game not
	 * finished 0 = draw 1 = WHITE win 2 = BLACK win
	 */
	@Override
	public int getWinner() {

		WinChecker winCheck = new WinChecker();

		return winCheck.getWin(this.board);

	}

	/**
	 * Updating the board based on the opponents move
	 * 
	 * @param m
	 *            : the opponents move
	 */
	@Override
	public int opponentMove(Move m) {
		if (this.board.updateBoard(m)) {
			return success;
		}

		else {
			return failure;
		}
	}

	/**
	 * Printing the board to the output
	 * 
	 * @param output
	 *            : the output to which we are printing, usually System.out
	 */
	@Override
	public void printBoard(PrintStream output) {

		int tempValue = 0;

		for (List<Hexagon> tempList : this.hexBoard) {
			for (Hexagon tempHex : tempList) {
				if (tempHex == null) {
					continue;
				}
				tempValue = tempHex.getValue();
				output.print(tempValue);

			}
			output.print("\r\n");
		}

	}

}