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

	protected boolean generateHexagons() {
		Scanner in = new Scanner(System.in);
		List<Hexagon> tempList;
		int index = 0;
		int lastChar = 0;

		char[] tempChars;
		int row = 0;
		int offset = 0;
		boolean isLeftEdge, isRightEdge;
		
		//Dealing with the first line being the number of rows
		//and also with fringe cases where there is an error in the input 
		//where two seperate numbers are entered. E.g 5 6
		if (in.hasNextInt()) {
			this.totalRows = in.nextInt() * 2 - 1;
			in.nextLine();
			if (in.hasNextInt()) {
				in.close();
				return false;
			}
		}

		else {
			in.close();
			return false;
		}

		while (in.hasNextLine()) {

			String tempInput = in.nextLine();
			
			index = 0;
			lastChar = 0;
			tempList = new ArrayList<Hexagon>();
			this.gameboard.add(row, tempList);
			
			//Too many rows
			if(row > this.totalRows){
				in.close();
				return false;
			}

			// All hexagons below the middle row do not start at 0 due to the
			// layout of the columns
			if (row > Math.ceil((this.totalRows) / 2)) {
				offset++;
			}

			for (int i = 0; i < offset; i++) {
				tempList.add(i, null);
			}

			tempChars = tempInput.toCharArray();
			
			//Because we don't know how many whitespaces, we do this check to see if the character
			//we are about to create a hexagon for is the last non-whitespace character in the line
			//and thus can be considered as a right edge
			for(int j = 0; j < tempChars.length; j++){
				if(tempChars[j] != ' '){
					lastChar = j;
				}
			}

			for (int k = 0; k < tempChars.length; k++) {
				if (tempChars[k] == ' ') {
					continue;
				}

				if (tempChars[k] != 'B' && tempChars[k] != 'W'
						&& tempChars[k] != '-') {
					in.close();
					return false;
				}

				// Simple ternary check for edge pieces
				isLeftEdge = (k == 0 || k == tempChars.length - 1) ? true : false;
				isRightEdge = (k == lastChar ? true : false);
				
				tempList.add(index + offset, new Hexagon(row, index + offset,
						this.totalRows, tempChars[k], isLeftEdge, isRightEdge));

				index++;
			}

			row++;
		}
		// Closing the inputstream
		in.close();
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
