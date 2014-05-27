import java.util.ArrayList;
import java.util.List;

import aiproj.fencemaster.Move;

public class Gameboard {

	protected String filePath;
	protected List<List<Hexagon>> gameboard;
	protected int totalRows;

	public Gameboard() {
		gameboard = new ArrayList<List<Hexagon>>();
	}
	
	public Gameboard(Gameboard gb){
		gameboard = gb.getBoard();
	}
	

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
			
			//Because we don't know how many whitespaces, we do this check to see if the character
			//we are about to create a hexagon for is the last non-whitespace character in the line
			//and thus can be considered as a right edge


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
	
	protected boolean updateBoard(Move move){
		Hexagon toChange = this.gameboard.get(move.Row).get(move.Col);
		
		//If the square is already occupied and the move is not a swap move then it must be an illegal move
		if(toChange.getValue() != 0 && !move.IsSwap){
			return false;
		}
		
		else{
			toChange.setValue(move.P);
			return true;
		}
	}

	// Getters and setters
	protected List<List<Hexagon>> getBoard() {
		return this.gameboard;
	}

	protected int getTotalRows() {
		return this.totalRows;
	}
}
