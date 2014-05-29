import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;

public class Heuristic implements Piece{

	private static int[][] posUtility;
	private static final int  CORNER = 1, EDGE = 5, EDGE_CORNER = 6, EDGE_MIDDLE = 2, CORNER_MIDDLE = 4, TWO_FROM_EDGE = 3, 
			NEXT_TO_PLAYER = 7, NEW_EDGE = 9, NO_MOVE = -1, SURROUNDED=5;
	
	
	public static void setPosUtility(Hexagon h, int player, int opponent, Gameboard board){
		
		List<List<Hexagon>> hexBoard = board.getBoard();
		List<Hexagon> edges = new ArrayList<Hexagon>();
		
		
		
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
				
					
				// edges are the best move, particularly the ones closest to the corners
				if(tempHex.getIsEdge()){
					
					tempHex.setPriority(EDGE);
					
					// check if edge is next to the corner
					for(Coordinate tempAdj: tempHex.getAdjacencies())
					if(hexBoard.get(tempAdj.row).get(tempAdj.column).isCorner())
							tempHex.setPriority(EDGE_CORNER);
					
					
					continue;
				}
				// corners are not good moves
				if(tempHex.isCorner()){
					tempHex.setPriority(CORNER);
					continue;
				}
				// tile lies mid-board.
				int counter = 0;
					
				for(int k=0; k<tempHex.getAdjacencies().size(); k++){
					
					Coordinate temp = tempHex.getAdjacencies().get(k);
					if(hexBoard.get(temp.row).get(temp.column).getIsEdge() || hexBoard.get(temp.row).get(temp.column).getIsEdge()){
						counter += 1;
					}
				}
				if(counter == 2){
						tempHex.setPriority(EDGE_MIDDLE);
						continue;
				}
				if(counter == 3){
					tempHex.setPriority(CORNER_MIDDLE);
					continue;
				}
				
			}
		}
		
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
		
				
				if(tempHex == null || tempHex.getPriorityValue() != 0){
					continue;
				}
				
				int counterSecond = 0;
				for(int k=0; k<tempHex.getAdjacencies().size(); k++){
					if(hexBoard.get(tempHex.getAdjacencies().get(k).row).get(tempHex.getAdjacencies().get(k).column).getPriorityValue() == EDGE_MIDDLE){
						counterSecond += 1;
					}
				}
				
				if(counterSecond > 0){
					tempHex.setPriority(TWO_FROM_EDGE);
					continue;
				}
				
				tempHex.setPriority(1);
			}
		}	
		
		
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
		
		
		
		
				if(tempHex.getValue() == player && tempHex.getIsEdge()){
						
					if(!edges.contains(tempHex)){
						edges.add(g[i][j].getEdgeSide());
					}
				}
				
			}
		}	
		
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
				
				if(tempHex.getValue() != EMPTY){
					continue;
				}
				
				
				int player_counter =0;
				
				// Check if tiles are next to a player tile
				for(int k=0; k<tempHex.getAdjacencies().size(); k++){
					if(hexBoard.get(tempHex.getAdjacencies().get(k).row).get(tempHex.getAdjacencies().get(k).column).getValue() == player){
						player_counter += 1;
					}
				}
				if(player_counter == 1 &&  !tempHex.isCorner()){
					if(tempHex.getIsEdge()){
						if(edges.contains(tempHex)){
							tempHex.updatePriority(NEW_EDGE);
						}
						else{
							continue;
						}
					}
				tempHex.updatePriority(NEXT_TO_PLAYER);
				}
			}
		}
		
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
				tempHex.resetPriorityValue();
			}
		}
		boolean tripodWinAvailable = false;
		
		WinChecker checkWin = new WinChecker();
		for(List<Hexagon> tempList: hexBoard){
			for(Hexagon tempHex: tempList){
				
				if(tempHex.getValue() == opponent || g[i][j].isVisited() || g[i][j].getType() == INVALID){ 
					continue;
				}
				
				if(TripodWin.checkSection(g, i, j, player)){
					tripodWinAvailable = true;
				}
				
			}
		}
		

		VisitedNodes.resetBoard(g);
		
		if(tripodWinAvailable){
			for(int i = 0; i < g.length; i++){
				for(int j = 0; j < g.length; j++){
				
					if(g[i][j].getType() == opponent || g[i][j].isVisited() || g[i][j].getType() == INVALID){ 
						continue;
					}
				
					if(!TripodWin.checkSection(g, i, j, player)){
						noTripodWin(g,i,j, opponent);
					}
				
				}
			}
		}
		
		
		for(int i = 0; i < g.length; i++){
			for(int j = 0; j < g.length; j++){
				
				int counter = 0;
				for(int k=0; k<g[i][j].getNeighbours().size(); k++){
					if(g[i][j].getNeighbours().get(k).getType() > EMPTY){
						counter += 1;
					}
				}
				
				if(counter == SURROUNDED){
					posUtility[i][j] = NO_MOVE;
				}
			}
		}
		
	}
	
	
	public static void noTripodWin(Tile[][] g, int row, int col, int opponent){
		
		VisitedNodes.resetBoard(g);
		
		// create new queue
		Queue<Tile> queue = new LinkedList<Tile>();
	
		queue.add(g[row][col]);
		
				
		while(!queue.isEmpty()){
			// loop through neighbours
			queue.element().setVisited(true);
			
			for(int i=0; i<queue.element().getNeighbours().size(); i++){
				if(queue.element().getNeighbours().get(i).getType() != opponent && !queue.element().getNeighbours().get(i).isVisited()){
					queue.add(g[queue.element().getNeighbours().get(i).getRow()][queue.element().getNeighbours().get(i).getCol()]);
				}
			}
					
			posUtility[queue.element().getRow()][queue.element().getCol()] = NO_MOVE;
			queue.remove();
		}
	}

	
	public static int positionValue(Tile[][] g, int player, int opponent){
		
		int value = 0;

		
		// reset board values
		VisitedNodes.resetBoard(g);
		
		for(int i = 0; i<posUtility.length; i++){
			for(int j = 0; j<posUtility.length; j++){
				if(g[i][j].getType()>0){
					if(g[i][j].getType() == player){
						value += posUtility[i][j];
					}
					if(g[i][j].getType() == opponent){
						value -= posUtility[i][j];
					}
				}
			}	
		}
	
		
		return value;
	}
	
	
	public static Move nextMove(Tile[][] g, int player, int opponent){
		
		int max = -1, temp, bestCol = -1, bestRow = -1;
		
		for(int i=0; i<posUtility.length; i++){
			for(int j=0; j<posUtility.length; j++){
				if(g[i][j].getType() == EMPTY){
					temp = posUtility[i][j];
					if(temp > max){
						max = temp;
						bestRow = i;
						bestCol = j;
					}
				}
				
			}
		}
		
		Move m = new Move(player,false,bestRow,bestCol);
		return m;
	}
	
	
	
	
	
	// error check
	public static void printPosUti(){
		
		
		// print game board
		for(int i=0; i<posUtility.length; i++){
			for(int j=0; j<posUtility.length; j++){
				if(i<(posUtility.length-1)/2 && j==0){
					int k=0;
					while(k<(posUtility.length-1)/2-i){
						System.out.print(" ");
						k++;
					}
				}
				if(posUtility[i][j] != 0){
					System.out.print(posUtility[i][j] + " ");
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

}
