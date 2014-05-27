public class FenceMaster {

	boolean replaceRule = true;
	Ahowindt boardController = new Ahowindt();
	String player;
	int clickNum;

	public FenceMaster() {

		player = "Black";

		// Initializing a white AI player
		boardController.init(6, 1);
		
		boardController.makeMove();

	}

	public static void main(String[] arg) {
		new FenceMaster();
	}

}
