import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gameboard {

	protected String filePath;
	protected List<List<Hexagon>> gameboard;
	protected int totalRows;

	public Gameboard() {
		gameboard = new ArrayList<List<Hexagon>>();
	}
	
	public Gameboard(GameBoardDisplay gbd){
		int i=0,j=0, k=0;
		gameboard = new ArrayList<List<Hexagon>>();
		int totalRows = 2*(gbd.getBoardSize()) - 1;
		boolean isLeftEdge, isRightEdge;
		ArrayList<ArrayList<HexagonCell>> hexagonCells = gbd.getBoard();
		
		for(ArrayList<HexagonCell> tempCellList : hexagonCells){
			this.gameboard.add(i,new ArrayList<Hexagon>());
			for(HexagonCell tempHexCell : tempCellList){
				if(tempHexCell == null){
					gameboard.get(i).add(j,null);
				}
				else{
					isLeftEdge = (k == 0) ? true : false;
					isRightEdge = (j == tempCellList.size()-1) ? true : false;
					gameboard.get(i).add(j,new Hexagon(tempHexCell, totalRows, isLeftEdge, isRightEdge));
					k++;
				}
				j++;
			}
			j=0;
			k=0;
			i++;
		}
		
		
	}

	protected boolean generateHexagons(int dimensions) {
		List<Hexagon> tempList;
		int index = 0;
		int lastChar = 0;

		char[] tempChars;
		int row = 0;
		int offset = 0;
		boolean isLeftEdge, isRightEdge;
		this.totalRows = dimensions * 2 - 1;


		for(int i = 0; i < this.totalRows; i++){
			
			index = 0;
			lastChar = 0;
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
			}

			for (int x = 0; i < offset; i++) {
				tempList.add(i, null);
			}
			
			//Because we don't know how many whitespaces, we do this check to see if the character
			//we are about to create a hexagon for is the last non-whitespace character in the line
			//and thus can be considered as a right edge
//			for(int j = 0; j < tempChars.length; j++){
//				if(tempChars[j] != ' '){
//					lastChar = j;
//				}
//			}
//
//			for (int k = 0; k < tempChars.length; k++) {
//				if (tempChars[k] == ' ') {
//					continue;
//				}
//
//				if (tempChars[k] != 'B' && tempChars[k] != 'W'
//						&& tempChars[k] != '-') {
//					return false;
//				}
//
//				// Simple ternary check for edge pieces
//				isLeftEdge = (k == 0 || k == tempChars.length - 1) ? true : false;
//				isRightEdge = (k == lastChar ? true : false);
//				
//				tempList.add(index + offset, new Hexagon(row, index + offset,
//						this.totalRows, tempChars[k], isLeftEdge, isRightEdge));

//				index++;
//			}

			row++;
		}
		// Closing the inputstream
		return true;

	}

	// Getters and setters
	protected List<List<Hexagon>> getBoard() {
		return this.gameboard;
	}

	protected int getTotalRows() {
		return this.totalRows;
	}
}
