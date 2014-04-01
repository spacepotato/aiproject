import java.util.ArrayList;


public class Hexagon {
		
	
		protected final int emptyCell = 999;
	
		protected int row;
		protected int column;
		protected int offset;
		protected int checked;
		
		protected boolean isLeftEdge;
		protected boolean isRightEdge;
		
		protected char value;
		
		protected ArrayList<Coordinate> adjacencies = new ArrayList<Coordinate>();
		
		
		public Hexagon(int row, int column, int totalRows, char value, boolean isLeftEdge, boolean isRightEdge){

			
			this.row = row;
			this.column = column;
			this.value = value;
			this.isLeftEdge = isLeftEdge;
			this.isRightEdge = isRightEdge;
			
			
			//setting the hexagon to have not been checked yet.
			this.checked = 0;
			
			//Setting up the adjacency list for the hexagon
			this.adjacencies.ensureCapacity(6);
			for(int i=0;i<6;i++){
				this.adjacencies.add(new Coordinate(0,0));
			}
						
			//
			//Below adjacencies
			//
			
			//Hexagon in top half or non-edge pieces in bottom half
			if((row < (totalRows + 1)/ 2 - 1)||(!isLeftEdge && !isRightEdge && row != totalRows - 1)){

				Coordinate temp1 = this.adjacencies.get(3);
				Coordinate temp2 = this.adjacencies.get(4);
				
				temp1.setColumn(column + 1);
				temp1.setRow(row + 1);
				temp2.setColumn(column);
				temp2.setRow(row + 1);
				
			}

			
			//Bottom line pieces
			else if(row == totalRows - 1){
				Coordinate temp1 = this.adjacencies.get(3);
				Coordinate temp2 = this.adjacencies.get(4);
				
				temp1.setColumn(emptyCell);
				temp1.setRow(emptyCell);
				temp2.setColumn(emptyCell);
				temp2.setRow(emptyCell);
				
				
			}
			
			//Right-edge in bottom
			else if (isRightEdge){
				
				Coordinate temp1 = this.adjacencies.get(3);
				Coordinate temp2 = this.adjacencies.get(4);
				
				temp1.setColumn(emptyCell);
				temp1.setRow(emptyCell);
				temp2.setColumn(column);
				temp2.setRow(row + 1);
			}
			
			//Left-edge in bottom
			else{
			
				Coordinate temp1 = this.adjacencies.get(3);
				Coordinate temp2 = this.adjacencies.get(4);
				
				temp1.setColumn(column + 1);
				temp1.setRow(row + 1);
				temp2.setColumn(emptyCell);
				temp2.setRow(emptyCell);
			}

			//
			//Side adjacencies
			//
			if(!this.isLeftEdge && !this.isRightEdge){
				
				Coordinate temp1 = this.adjacencies.get(2);
				Coordinate temp2 = this.adjacencies.get(5);

				
				temp1.setColumn(column + 1);
				temp1.setRow(row);
				temp2.setColumn(column - 1);
				temp2.setRow(row);
			}
			
			//Left side edge
			else if(!this.isRightEdge){

				
				Coordinate temp1 = this.adjacencies.get(2);
				Coordinate temp2 = this.adjacencies.get(5);
				
				temp1.setColumn(column + 1);
				temp1.setRow(row);
				temp2.setColumn(emptyCell);
				temp2.setRow(emptyCell);
			}
			
			else{

				
				Coordinate temp1 = this.adjacencies.get(2);
				Coordinate temp2 = this.adjacencies.get(5);
				
				
				temp1.setColumn(emptyCell);
				temp1.setRow(emptyCell);
				temp2.setColumn(column - 1);
				temp2.setRow(row);
			}
			
			//
			//Top adjacencies
			//
			
			//Top line pieces
			if(row == 0){

				
				Coordinate temp1 = this.adjacencies.get(0);
				Coordinate temp2 = this.adjacencies.get(1);
				
				temp1.setColumn(emptyCell);
				temp1.setRow(emptyCell);
				temp2.setColumn(emptyCell);
				temp2.setRow(emptyCell);
			}
			
			//Bottom half pieces
			else if((row > (totalRows + 1)/ 2 - 1 )||(!isLeftEdge && !isRightEdge && row != totalRows - 1)){

				
				Coordinate temp1 = this.adjacencies.get(0);
				Coordinate temp2 = this.adjacencies.get(1);
				
				temp1.setColumn(column - 1);
				temp1.setRow(row - 1);
				temp2.setColumn(column);
				temp2.setRow(row - 1);
			}

		
			//Right-edge in top
			
			
			else if (isRightEdge){
				
				Coordinate temp1 = this.adjacencies.get(0);
				Coordinate temp2 = this.adjacencies.get(1);
				
				temp1.setColumn(column - 1);
				temp1.setRow(row - 1);
				temp2.setColumn(emptyCell);
				temp2.setRow(emptyCell);
			}
			
			//Left-edge in top
			else{
				Coordinate temp1 = this.adjacencies.get(0);
				Coordinate temp2 = this.adjacencies.get(1);
				
				temp1.setColumn(emptyCell);
				temp1.setRow(emptyCell);
				temp2.setColumn(column);
				temp2.setRow(row - 1);
			}

			
		}
		
		//Keeps track of checked hexagons so that multiple checking is not done.
		protected void setChecked(int num){
				this.checked = num;
		}
		protected int getChecked(){
			return this.checked;
		}

		
		protected ArrayList<Coordinate> getAdjacencies(){
			return this.adjacencies;
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
		
		protected boolean isLeftEdge(){
			return this.isLeftEdge;
		}
		//Just so we can make sure that the array has been read in properly
		@Override
		public String toString(){
			return "This " + value + " hexagon lies at: (" +  Integer.toString(row) + "," + Integer.toString(column) + ") ";
		}
}
