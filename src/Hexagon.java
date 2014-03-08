
public class Hexagon {
		
	
		protected int row;
		protected int column;
		protected char value;
		protected Integer[] adjacentBelow;
		protected Integer[] adjacentAbove;
		
		public Hexagon(int row, int column, int totalRows, char value, boolean isEdge){
			adjacentBelow = new Integer[2];
			
			this.row = row;
			this.column = column;
			this.value = value;
			
			//If the hexagon lies in the top half of the board or it is not an edge piece
			//in the bottom half of the board we know that it will be
			//adjacent to the hexagon in the position below it and one to the right of that
			if(row < (totalRows + 1)/ 2 || !isEdge && row != totalRows){
				adjacentBelow[0] = column;
				adjacentBelow[1] = column + 1;
			}
			
			else if(row == totalRows){
				adjacentBelow[0] = 999;
				adjacentBelow[1] = 999;
			}
			
			//If it is not we know that it will only be adjacent to the piece below it
			else{
				adjacentBelow[0] = column;
			}
			
		}
		
		//Getting the adjacency matrix
		protected Integer[] getAdjacent(){
			return this.adjacentBelow;
		}
		
		protected int getRow(){
			return this.row;
		}
		
		protected int getColumn(){
			return this.column;
		}
		
		protected char getValue(){
			return this.value;
		}
		
		//Just so we can make sure that the array has been read in properly
		@Override
		public String toString(){
			return "This " + value + " hexagon lies at: (" +  Integer.toString(row) + "," + Integer.toString(column) + ") "
					+ "and is adjacent to " + adjacentBelow[0].toString() + " , " + adjacentBelow[1].toString();
		}
}
