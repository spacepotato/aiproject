import java.io.PrintStream;
import java.util.ArrayList;
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

	@Override
	public int init(int n, int p) {

		this.ourPlayerValue = p;
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
		Coordinate nextMove = null;
		boolean foundMove = false;

		Move ourMove = new Move();

		if (previousMove == null) {
			ourMove.P = ourPlayerValue;

			ourMove.Row = 5;
			ourMove.Col = 5;
		}
		else{
			Hexagon previousHexagon = this.hexBoard.get(previousMove.Row).get(previousMove.Col);
			List<Coordinate> adjacencies = previousHexagon.getAdjacencies();
			
			ourMove.P = ourPlayerValue;
			
			
			
			while(!foundMove){
				int  adjacency = rand.nextInt(5);
				System.out.println(adjacency);
				nextMove = adjacencies.get(adjacency);
				
				if(nextMove.getRow() > this.board.totalRows){
					continue;
				}
				
				if(nextMove.getRow() == 999 || nextMove.getColumn() == 999){
					continue;
				}
				else if(this.hexBoard.get(nextMove.getRow()).get(nextMove.getColumn()).getValue() == 0){
					ourMove.Row = nextMove.getRow();
					ourMove.Col = nextMove.getColumn();
					foundMove = true;
				}
			}
			
			
		}

		this.board.updateBoard(ourMove);
		previousMove = ourMove;
		return ourMove;
	}

	@Override
	public int getWinner() {

		List<PlayerCustom> players = new ArrayList<PlayerCustom>();
		players.add(new PlayerCustom("Black"));
		players.add(new PlayerCustom("White"));

		WinChecker winCheck = new WinChecker();

		PlayerCustom player1;
		PlayerCustom player2;

		for (PlayerCustom tempPlayer : players) {

			if (winCheck.tripodWin(this.board, tempPlayer.getPlayerValue())) {
				tempPlayer.setPlayerWin(true);
				tempPlayer.setPlayerWinState("Tripod");
			}

			if (winCheck.loopWin(this.board, tempPlayer.getPlayerValue())) {
				if (tempPlayer.getPlayerWin()) {
					tempPlayer.setPlayerWinState("Both");
				} else {
					tempPlayer.setPlayerWin(true);
					tempPlayer.setPlayerWinState("Loop");
				}
			}
		}// End forloop

		player1 = players.get(0);
		player2 = players.get(1);

		if (player1.getPlayerWin()) {
			return 2;
		}

		else if (player2.getPlayerWin()) {
			return 1;
		} else if (winCheck.draw(this.board)) {
			return 0;
		} else {
			return -1;
		}
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
				tempValue = tempHex.getValue();

				if (tempHex != null) {
					output.print(tempValue);
				}
			}
		}

	}

}