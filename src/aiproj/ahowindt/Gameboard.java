/**
 * A class representing the gameboard in Fencemaster
 * It keeps track of the state of the gameboard
 * and provides various methods through which the player
 * can update the board
 * 
 * @param gameboard: represents the current state of the board
 * @param totalRows: the number of rows the board has. Determined
 * 					 based off of the dimensions passed in
 * @param updatedMove: used to determine what move to make
 * 					  in the case of the opponent winning.
 * 				      basically references the move most
 * 					  recently passed into updateBoard()
 * 
 * @author AHowindt and JMcLaren
 */
package aiproj.ahowindt;

import java.util.ArrayList;
import java.util.List;

import aiproj.fencemaster.Move;

public class Gameboard {

	protected List<List<Hexagon>> gameboard;
	protected int totalRows;
	protected Move updatedMove;
	protected Move prevoiusMove;

	public Gameboard() {
		gameboard = new ArrayList<List<Hexagon>>();
	}

	
	/**
	 * Populating the Arraylist of Lists of Hexagons as defined by the board layout
	 * @param dimensions of the board
	 * @return true if the board is set up properly and input is correct
	 */
	protected boolean generateHexagons(int dimensions) {
		List<Hexagon> tempList;
		
		int index = 0;
		int row = 0;
		int column = dimensions - 1;
		int offset = 0;
		
		boolean isLeftEdge = false;
		boolean isRightEdge = false;
		
		this.totalRows = dimensions * 2 - 1;


		for(int i = 0; i < this.totalRows; i++){
			
			index = 0;
			
			tempList = new ArrayList<Hexagon>();
			this.gameboard.add(row, tempList);
			
			//Too many rows
			if(row > this.totalRows){
				return false;
			}

			// All hexagons below the middle row do not start at 0 due to the
			// layout of the columns
			if (row > Math.ceil((this.totalRows) / 2)) {
				offset++;
				column--;
			}
			
			else{
				column++;
			}

			for (int j = 0; j < offset; j++) {
				tempList.add(j, null);
			}
			
			//Because hexagons below the middle of the board do not start at 
			//column = 0 we need to keep track of the offset
			for(int k = offset; k < column + offset; k++ ){
				
				isLeftEdge = false;
				isRightEdge = false;
				
				if(k == offset){
					isLeftEdge = true;
				}
				
				if(k + 1 == column + offset){
					isRightEdge = true;
				}
				
				tempList.add(index + offset, new Hexagon(row, index + offset, this.totalRows, isLeftEdge, isRightEdge));
				index++;
				
			}
			row++;
		}
		return true;

	}
	
	/**
	 * A function that makes a move on the board
	 * @param move: the move to make
	 * @return true if move is valid
	 */
	protected boolean updateBoard(Move move){
		Hexagon toChange = this.gameboard.get(move.Row).get(move.Col);
		
		//If the square is already occupied and the move is not a swap move then it must be an illegal move
		if(toChange.getValue() != 0 && !move.IsSwap){
			return false;
		}
		
		else{
			toChange.setValue(move.P);
			this.updatedMove = move;
			return true;
		}
	}
	
	/**
	 * Deleting a move from the board
	 * Useful for AB searching where you want to make a move 
	 * and then undo it to try a different move
	 * @param move: the move to revert
	 * @return if reverted successfully
	 */
	public boolean revertBoard(Move move){
		Hexagon toChange = this.gameboard.get(move.Row).get(move.Col);
		toChange.setValue(0);
//		toChange.resetPriorityValue();
		this.updatedMove = null;
		return true;
		
	}

	// Getters and setters
	protected List<List<Hexagon>> getBoard() {
		return this.gameboard;
	}

	protected int getTotalRows() {
		return this.totalRows;
	}
	

	
	public Move getUpdatedMove(){
		return this.updatedMove;
	}
}
