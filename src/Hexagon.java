
public class Hexagon {
		
	
		protected final int emptyCell = 999;
	
		protected int row;
		protected int column;
		protected char value;
		protected int offset;
		protected Integer[] adjacentBelow;
		protected Integer[] adjacentAbove;
		protected Integer[] adjacentSide;
		protected boolean isEdge;
		
		public Hexagon(int row, int column, int totalRows, char value, boolean isEdge, int offset){
			adjacentBelow = new Integer[2];
			adjacentSide = new Integer[2];
			adjacentAbove = new Integer[2];
			
			this.row = row;
			this.column = column;
			this.value = value;
			this.offset = offset;
			this.isEdge = isEdge;
			
			//
			//Below adjacencies
			//
			
			//Hexagon in top half
			if(row < (totalRows + 1)/ 2 - 1 ){
				adjacentBelow[0] = column;
				adjacentBelow[1] = column + 1;
			}
			
			//Non-edge pieces in bottom half
			else if(!isEdge && row != totalRows - 1){
				adjacentBelow[0] = column;
				adjacentBelow[1] = column + 1;
			} //fine
			
			//Bottom line pieces
			else if(row == totalRows - 1){
				adjacentBelow[0] = emptyCell;
				adjacentBelow[1] = emptyCell;
			}
			
			//Right-edge in bottom
			else if (isEdge && column == totalRows - 1){
				adjacentBelow[0] = column;
				adjacentBelow[1] = emptyCell;
			}
			
			//Left-edge in bottom
			else{
				adjacentBelow[0] = emptyCell;
				adjacentBelow[1] = column + 1;
			}
//			System.out.println("We have set below[0] to be " + adjacentBelow[0] + " on the hexagon at " + this.row + " , " + this.column);
//			System.out.println("We have set below[1] to be " + adjacentBelow[1] + " on the hexagon at " + this.row + " , " + this.column);
			
			//
			//Side adjacencies
			//
			if(!this.isEdge){
				adjacentSide[0] = column - 1;
				adjacentSide[1] = column + 1;
			}
			
			//Left side edge
			else if(this.isEdge && column <= totalRows/2){
				adjacentSide[0] = emptyCell;
				adjacentSide[1] = column + 1;
			}
			
			else{
				adjacentSide[0] = column - 1;
				adjacentSide[1] = emptyCell;
			}
			
			//
			//Top adjacencies
			//
			
			//Top line pieces
			if(row == 0){
				adjacentAbove[0] = emptyCell;
				adjacentAbove[1] = emptyCell;
			}
			
			//Bottom half pieces
			else if(row > (totalRows + 1)/ 2 - 1 ){
				adjacentAbove[0] = column - 1;
				adjacentAbove[1] = column;
			}
			
			//Non-edge pieces in Top half
			else if(!isEdge && row != totalRows - 1){
				adjacentAbove[0] = column - 1;
				adjacentAbove[1] = column;
			} //fine
			

			
			//Right-edge in top
			
			
			else if (isEdge && column > Math.floor(totalRows/2)){
				adjacentAbove[0] = column - 1;
				adjacentAbove[1] = emptyCell;
			}
			
			//Left-edge in top
			else{
				adjacentAbove[0] = emptyCell;
				adjacentAbove[1] = column;
			}
			
		}
		
		//Getting the adjacency objects 
		protected Integer[] getAdjacentBelow(){
			return this.adjacentBelow;
		}
		
		protected Integer[] getAdjacentSide(){
			return this.adjacentSide;
		}
		
		protected Integer[] getAdjacentAbove(){
			return this.adjacentAbove;
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
		
		protected boolean isEdge(){
			return this.isEdge;
		}
		//Just so we can make sure that the array has been read in properly
		@Override
		public String toString(){
			return "This " + value + " hexagon lies at: (" +  Integer.toString(row) + "," + Integer.toString(column) + ") ";
		}
}
