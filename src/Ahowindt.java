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
	protected boolean secondMove = false;

	protected List<List<Hexagon>> hexBoard;

	protected int ourPlayerValue;
	protected int theirPlayerValue;
	protected int size;

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

	@Override
	public Move makeMove() {

		Random rand = new Random();
		// Coordinate nextMove = null;

		Move ourMove = new Move();

		if (previousMove == null) {

			ourMove.P = ourPlayerValue;
			ourMove.IsSwap = true;

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

			System.out
					.println("First Move: " + ourMove.Row + "," + ourMove.Col);
			secondMove = true;
		}

		else if (secondMove == true) {
			ourMove.Row = 0;
			ourMove.Col = 1;
			ourMove.P = 1;
			secondMove = false;
		}

		else {

			MinMaxTree mmt;
			mmt = new MinMaxTree(board, WHITE);
			mmt.runMiniMax();

			ourMove = mmt.getMove();

		}

		this.board.updateBoard(ourMove);
		previousMove = ourMove;
		
		printBoard(System.out);
		return ourMove;
	}

	@Override
	public int getWinner() {

		WinChecker winCheck = new WinChecker();

		return winCheck.getWin(this.board);

	}

	@Override
	public int opponentMove(Move m) {
		if (this.board.updateBoard(m)) {
			return success;
		}

		else {
			return failure;
		}
	}

	@Override
	public void printBoard(PrintStream output) {

		int tempValue = 0;

		for (List<Hexagon> tempList : this.hexBoard) {
			for (Hexagon tempHex : tempList) {
				if(tempHex == null){
					continue;
				}
				tempValue = tempHex.getValue();
				output.print(tempValue);
				
			}
			output.print("\r\n");
		}

	}

}