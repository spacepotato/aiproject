import java.util.ArrayList;
import java.util.List;

public class Hexagon {

	protected final int emptyCell = 999;

	protected int row;
	protected int column;
	protected int offset;
	protected int checked;
	
	protected int rowTotal;

	protected boolean isLeftEdge;
	protected boolean isRightEdge;

	protected int value;

	protected int priorityValue;
	protected Hexagon prevHex;
	
	protected ArrayList<Coordinate> adjacencies = new ArrayList<Coordinate>();
	
	public Hexagon(Hexagon h){
		
		this.row = h.row;
		this.column = h.column;
		this.offset  =h.offset;
		this.checked = h.checked;
		this.adjacencies = h.adjacencies;
		this.isLeftEdge = h.isLeftEdge;
		this.isRightEdge = h.isRightEdge;
		this.value = h.value;
		this.priorityValue = h.priorityValue;
		this.prevHex = h.prevHex;
		
	}

	public Hexagon(int row, int column, int totalRows, boolean isLeftEdge, boolean isRightEdge) {

		 this.row = row;
		 this.column = column;
		 this.value = 0;
		 this.isLeftEdge = isLeftEdge;
		 this.isRightEdge = isRightEdge;
		 this.rowTotal = totalRows;


		this.checked = 0;

		// Setting up the adjacency list for the hexagon
		this.adjacencies.ensureCapacity(6);
		for (int i = 0; i < 6; i++) {
			this.adjacencies.add(new Coordinate(0, 0));
		}

		//
		// Below adjacencies
		//

		// Hexagon in top half or non-edge pieces in bottom half
		if ((row < (totalRows + 1) / 2 - 1)
				|| (!isLeftEdge && !isRightEdge && row != totalRows - 1)) {

			Coordinate temp1 = this.adjacencies.get(3);
			Coordinate temp2 = this.adjacencies.get(4);

			temp1.setColumn(column + 1);
			temp1.setRow(row + 1);
			temp2.setColumn(column);
			temp2.setRow(row + 1);

		}

		// Bottom line pieces
		else if (row == totalRows - 1) {
			Coordinate temp1 = this.adjacencies.get(3);
			Coordinate temp2 = this.adjacencies.get(4);

			temp1.setColumn(emptyCell);
			temp1.setRow(emptyCell);
			temp2.setColumn(emptyCell);
			temp2.setRow(emptyCell);

		}

		// Right-edge in bottom
		else if (isRightEdge) {

			Coordinate temp1 = this.adjacencies.get(3);
			Coordinate temp2 = this.adjacencies.get(4);

			temp1.setColumn(emptyCell);
			temp1.setRow(emptyCell);
			temp2.setColumn(column);
			temp2.setRow(row + 1);
		}

		// Left-edge in bottom
		else {

			Coordinate temp1 = this.adjacencies.get(3);
			Coordinate temp2 = this.adjacencies.get(4);

			temp1.setColumn(column + 1);
			temp1.setRow(row + 1);
			temp2.setColumn(emptyCell);
			temp2.setRow(emptyCell);
		}

		//
		// Side adjacencies
		//
		if (!this.isLeftEdge && !this.isRightEdge) {

			Coordinate temp1 = this.adjacencies.get(2);
			Coordinate temp2 = this.adjacencies.get(5);

			temp1.setColumn(column + 1);
			temp1.setRow(row);
			temp2.setColumn(column - 1);
			temp2.setRow(row);
		}

		// Left side edge
		else if (!this.isRightEdge) {

			Coordinate temp1 = this.adjacencies.get(2);
			Coordinate temp2 = this.adjacencies.get(5);

			temp1.setColumn(column + 1);
			temp1.setRow(row);
			temp2.setColumn(emptyCell);
			temp2.setRow(emptyCell);
		}

		else {

			Coordinate temp1 = this.adjacencies.get(2);
			Coordinate temp2 = this.adjacencies.get(5);

			temp1.setColumn(emptyCell);
			temp1.setRow(emptyCell);
			temp2.setColumn(column - 1);
			temp2.setRow(row);
		}

		//
		// Top adjacencies
		//

		// Top line pieces
		if (row == 0) {

			Coordinate temp1 = this.adjacencies.get(0);
			Coordinate temp2 = this.adjacencies.get(1);

			temp1.setColumn(emptyCell);
			temp1.setRow(emptyCell);
			temp2.setColumn(emptyCell);
			temp2.setRow(emptyCell);
		}

		// Bottom half pieces
		else if ((row > (totalRows + 1) / 2 - 1)
				|| (!isLeftEdge && !isRightEdge && row != totalRows - 1)) {

			Coordinate temp1 = this.adjacencies.get(0);
			Coordinate temp2 = this.adjacencies.get(1);

			temp1.setColumn(column - 1);
			temp1.setRow(row - 1);
			temp2.setColumn(column);
			temp2.setRow(row - 1);
		}

		// Right-edge in top

		else if (isRightEdge) {

			Coordinate temp1 = this.adjacencies.get(0);
			Coordinate temp2 = this.adjacencies.get(1);

			temp1.setColumn(column - 1);
			temp1.setRow(row - 1);
			temp2.setColumn(emptyCell);
			temp2.setRow(emptyCell);
		}

		// Left-edge in top
		else {
			Coordinate temp1 = this.adjacencies.get(0);
			Coordinate temp2 = this.adjacencies.get(1);

			temp1.setColumn(emptyCell);
			temp1.setRow(emptyCell);
			temp2.setColumn(column);
			temp2.setRow(row - 1);
		}

	}
	
	protected Hexagon getParent(){
		return this.prevHex;
	}
	
	protected void setParent(Hexagon h){
		this.prevHex = h;
	}
	
	protected int numberOfExposedEdges(){
		int sum = 0;
		for (int i = 0; i < 6; i++) {// Finding the number of
										// exposed edges that the
										// hexagon has
			sum += this.adjacencies.get(i).getRow();
		}

		return sum / 999;
	}

	// Keeps track of checked hexagons so that multiple checking is not done.
	protected void setChecked(int num) {
		this.checked = num;
	}

	protected int getChecked() {
		return this.checked;
	}

	protected ArrayList<Coordinate> getAdjacencies() {
		return this.adjacencies;
	}

	protected int getRow() {
		return this.row;
	}

	protected int getColumn() {
		return this.column;
	}

	protected int getValue() {
		return this.value;
	}

	protected void setValue(int player) {
		this.value = player;
	}

	protected boolean isLeftEdge() {
		return this.isLeftEdge;
	}

	protected boolean isRightEdge() {
		return this.isRightEdge;
	}
	
	protected int getPriorityValue(){
		return this.priorityValue;
	}

	protected boolean getIsEdge(){
	
//		if(this.numberOfExposedEdges() == 2){
//			return true;
//		}
		
		if(this.isLeftEdge() || this.isRightEdge()){
			return true;
		}
		
		return false;
	}
	
	// Just so we can make sure that the array has been read in properly
	@Override
	public String toString() {
		return "This  hexagon lies at: (" + Integer.toString(row) + ","
				+ Integer.toString(column) + ") ";
	}
	
	@Override 
	public boolean equals(Object h){
		
		Hexagon tempHex = (Hexagon) h;
		
		if(tempHex.getRow() == this.getRow() && tempHex.getColumn() == this.getColumn()){
			return true;
		}
		
		return false;
		
	}
	
	protected void setPriorityValue(int player, List<List<Hexagon>> hexBoard){
		int EMPTY = 0;
		boolean adjIsEdge = false;
		
		int numAdj = this.checkAdjacency(hexBoard, player);
		
		for(Coordinate adj : this.getAdjacencies()){
			if(adj.getRow() == 999 || adj.getColumn() == 999){
				continue;
			} else if(hexBoard.get(adj.getRow()).get(adj.getColumn()).getIsEdge()){
				adjIsEdge = true;
			}
		}
		
		boolean edgeBool = (adjIsEdge || this.getIsEdge());
		
		if(player == this.value){
			this.priorityValue = 1;
		}else if(isCorner()){
			this.priorityValue = -1;
		}else if(edgeBool && !isCorner()){
			this.priorityValue = 25;
		} else if(this.value != player && this.value != EMPTY){
			this.priorityValue = 1000;
		} else if(this.getIsEdge() && numAdj >0){
			this.priorityValue = 2;
		} else if(this.getIsEdge() && numAdj == 0){
			this.priorityValue = 3;
		} else if(!this.getIsEdge() && adjIsEdge && numAdj >0){
			this.priorityValue = 3;
		} else if(!this.getIsEdge() && adjIsEdge && numAdj == 0){
			this.priorityValue = 4;
		} else if(!this.getIsEdge() && !adjIsEdge && numAdj >0){
			this.priorityValue = 3 + numAdj;
		} else if(this.getIsEdge() && adjIsEdge && numAdj == 0){
			priorityValue = 6;
		}
	}
	
	protected void resetPriorityValue(){
		this.priorityValue = 0;
	}
	
	// Checking the 6 adjacent pieces to see if they are the same value as the
	// hexagon we are checking
	protected int checkAdjacency(List<List<Hexagon>> board, int player) {

		ArrayList<Coordinate> adjacencies = this.getAdjacencies();

		int numberAdjacent = 0;

		for (Coordinate tempCoords : adjacencies) {
			int tempColumn = tempCoords.getColumn();
			int tempRow = tempCoords.getRow();

			if (tempColumn != 999
					&& board.get(tempRow).get(tempColumn).getValue() == player) {
				numberAdjacent++;
			}
		}

		return numberAdjacent;
	}
	
	protected int whichEdge(){
		for(int i=0, j=1;i<6;i++){
		
			if (j>5){
				j=0;
			}
		
			if (this.adjacencies.get(i).getRow() == 999
					&& this.adjacencies.get(j).getRow() == 999) {
				return i;
			}	
		}
		return -1;
	}
	
	protected boolean isCorner(){
		if(this.getIsEdge()){
			if(this.getRow() == 0 || this.getRow() == rowTotal || this.getRow() == (rowTotal + 1)/2){
				System.out.println("We are returning true for " + this.getRow() + " , " + this.getColumn());
				return true;
			}
			else
				return false;
		}
		return false;
	}
}

