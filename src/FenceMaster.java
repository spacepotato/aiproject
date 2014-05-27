import aiproj.fencemaster.Move;

public class FenceMaster {

	boolean replaceRule = true;
	Ahowindt boardController = new Ahowindt();
	String player;
	int clickNum;

	public FenceMaster() {

		player = "Black";
		
		Move first = new Move();
		first.P = 2;
		first.Row = 1;
		first.Col = 2;
		
		Move second = new Move();
		second.P = 2;
		second.Row = 2;
		second.Col = 0;

		// Initializing a white AI player
		boardController.init(6, 1);
		System.out.println("===============================");
		boardController.makeMove();
		System.out.println("===============================");
		boardController.opponentMove(first);
		boardController.makeMove();
		System.out.println("===============================");
		boardController.opponentMove(second);
		boardController.makeMove();
		System.out.println("===============================");

	}

	public static void main(String[] arg) {
		new FenceMaster();
	}

}
