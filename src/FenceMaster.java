import java.util.Scanner;

import aiproj.fencemaster.Move;

public class FenceMaster {

	boolean replaceRule = true;
	Ahowindt boardController = new Ahowindt();
	String player;
	int clickNum;

	public FenceMaster() {

		player = "Black";
		
		Scanner userInput = new Scanner(System.in);
		
//		Move first = new Move();
//		first.P = 2;
//		first.Row = 1;
//		first.Col = 2;
//		
//		Move second = new Move();
//		second.P = 2;
//		second.Row = 2;
//		second.Col = 0;
//
//		// Initializing a white AI player
//		boardController.init(6, 1);
//		System.out.println("===============================");
//		boardController.makeMove();
//		System.out.println("===============================");
//		boardController.opponentMove(first);
//		boardController.makeMove();
//		System.out.println("===============================");
//		boardController.opponentMove(second);
//		boardController.makeMove();
//		System.out.println("===============================");
		
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
			System.out.println("===============================");
			if(boardController.getWinner() != -1)
				System.out.println("Game has been won" + boardController.getWinner());
			
		}

	}

	public static void main(String[] arg) {
		new FenceMaster();
	}

}
