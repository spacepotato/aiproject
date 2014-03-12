import java.io.IOException;
import java.util.List;

public class MainEngine {

	public static void main(String args[]) throws IOException {

		List<List<Hexagon>> generatedBoard;
		Gameboard board = new Gameboard(3, args[0]);
		board.readTextFile();

		generatedBoard = board.getBoard();

		for (List<Hexagon> tempList : generatedBoard) {
			for (Hexagon tempHexagon : tempList) {
				System.out.println(tempHexagon.toString());
			}
		}

	}
}
