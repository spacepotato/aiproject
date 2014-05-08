import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import aiproj.fencemaster.*;

public class Ahowindt implements Player, Piece {

	protected Gameboard board;

	@Override
	public int init(int n, int p) {

		// As defined in the spec, we return a positive value in the event of
		// success and a negative value in the event
		// of failure
		int success = 1;
		int failure = -1;

		this.board = new Gameboard();
		if (this.board != null) {
			return success;
		} else {
			return failure;
		}
	}

	@Override
	public Move makeMove() {
		Move ourMove = new Move();

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

			if (winCheck.tripodWin(this.board, tempPlayer.getPlayerChar())) {
				tempPlayer.setPlayerWin(true);
				tempPlayer.setPlayerWinState("Tripod");
			}

			if (winCheck.loopWin(this.board, tempPlayer.getPlayerChar())) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printBoard(PrintStream output) {
		// TODO Auto-generated method stub

	}

}