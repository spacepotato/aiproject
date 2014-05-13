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

		Move ourMove = new Move();

		if (previousMove == null) {
			ourMove.P = ourPlayerValue;

			ourMove.Row = 1;
			ourMove.Col = 1;
		}
		else{
			Hexagon previousHexagon = this.hexBoard.get(previousMove.Row).get(previousMove.Col);
			List<Coordinate> adjacencies = previousHexagon.getAdjacencies();
			List<Coordinate> potentials = new ArrayList<Coordinate>();
			
			ourMove.P = ourPlayerValue;
			
			System.out.println("We are getting to here");
			System.out.println("There are " + adjacencies.size() + " spots in total");
			
						
			for(Coordinate tempCoord: adjacencies){
				System.out.println("The coord we are checking is at: " + tempCoord.getRow() + " , " + tempCoord.getColumn());
				
				if(tempCoord.getRow() != 999 || tempCoord.getColumn() != 999){
					Hexagon toAdd = this.hexBoard.get(tempCoord.getRow()).get(tempCoord.getColumn());
					System.out.println("The value of the hexagon is " + toAdd.getValue());
					if(toAdd.getValue() == 0){
						potentials.add(tempCoord);
					}
				}
				
			}
			
			System.out.println("We are finding " + potentials.size() + " free spots");
			
			//generating the random move
			nextMove = potentials.get(rand.nextInt(potentials.size()));
			ourMove.Col = nextMove.getColumn();
			ourMove.Row = nextMove.getRow();
			ourMove.P = 1;
			
			
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