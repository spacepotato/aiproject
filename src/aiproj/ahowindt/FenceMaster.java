package aiproj.ahowindt;
import java.util.Scanner;

import aiproj.fencemaster.Move;

public class FenceMaster {

	boolean replaceRule = true;
	Ahowindt boardController = new Ahowindt();
	String player;
	int clickNum;

	public FenceMaster() {

		player = "Black";
		
		@SuppressWarnings("resource")
		Scanner userInput = new Scanner(System.in);
		
		
		boardController.init(6, 1);
		
		while(true){
			Move tempMove = new Move();
			tempMove.P = 2;
			System.out.println("Row");
			tempMove.Row = userInput.nextInt();
			System.out.println("Column");
			tempMove.Col = userInput.nextInt();
			
			System.out.println("===============================");
			boardController.opponentMove(tempMove);
			boardController.makeMove();
			boardController.printBoard(System.out);
			System.out.println("===============================");
			if(boardController.getWinner() != -1)
				System.out.println("Game has been won" + boardController.getWinner());
			
		}

	}

	public static void main(String[] arg) {
		new FenceMaster();
	}

}
