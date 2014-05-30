import aiproj.fencemaster.Move;
import aiproj.ahowindt.Gameboard;
import aiproj.ahowindt.MinMaxTree;




public class TreeTesting {
	
	public static void main(String args[]){
		Gameboard gb = new Gameboard();
		int player = 1;
		
		gb.generateHexagons(6);

		gb.updateBoard(new Move(player,false,0,4));
		gb.updateBoard(new Move(player,false,1,5));
//		gb.updateBoard(new Move(player,false,5,8));
//		gb.updateBoard(new Move(player,false,6,8));
//		gb.updateBoard(new Move(player,false,3,4));
//		gb.updateBoard(new Move(player,false,2,1));
		
		MinMaxTree MMT = new MinMaxTree(gb, player);
		
		double value = MMT.tripodEval(gb, player);
		
		System.out.println("Value = " + value);
		
	}
	
	
}