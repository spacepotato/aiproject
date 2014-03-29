import java.util.ArrayList;


public class Hexagon {
		
	
		protected final int emptyCell = 999;
	
		protected int row;
		protected int column;
		protected char value;
		protected int offset;
		ArrayList<Coordinate> adjacencies = new ArrayList<Coordinate>();
		protected boolean isEdge;
		protected int checked;//ASTON (See below method for explaination)
		
		public Hexagon(int row, int column, int totalRows, char value, boolean isEdge, int offset, int lineLength){

			
			this.row = row;
			this.column = column;
			this.value = value;
			this.offset = offset;
			this.isEdge = isEdge;
			
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
			if((row < (totalRows + 1)/ 2 - 1)||(!isEdge && row != totalRows - 1)){

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
			else if (isEdge && column == totalRows - 1){
				
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
			if(!this.isEdge){
				
				Coordinate temp1 = this.adjacencies.get(2);
				Coordinate temp2 = this.adjacencies.get(5);

				
				temp1.setColumn(column + 1);
				temp1.setRow(row);
				temp2.setColumn(column - 1);
				temp2.setRow(row);
			}
			
			//Left side edge
			else if(this.isEdge && column != lineLength - 1){

				
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
			else if((row > (totalRows + 1)/ 2 - 1 )||(!isEdge && row != totalRows - 1)){

				
				Coordinate temp1 = this.adjacencies.get(0);
				Coordinate temp2 = this.adjacencies.get(1);
				
				temp1.setColumn(column - 1);
				temp1.setRow(row - 1);
				temp2.setColumn(column);
				temp2.setRow(row - 1);
			}

		
			//Right-edge in top
			
			
			else if (isEdge && column > Math.floor(totalRows/2)){
				
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
		
		//ASTON: Added to keep a track of checked hexagons so that multiple checking is not done.
		//Note: not sure how to will be used specifically.
		protected void setChecked(int num){
				this.checked = num;
		}
		protected int getChecked(){
			return this.checked;
		}

		
		protected ArrayList<Coordinate> getAdjacencies(){
			return this.adjacencies;
		}
		
		protected void printAdjacencies(){
			for(int i = 0; i < 6; i++){
				System.out.println("The hexagon at: " + this.row + " , " + this.column + " is adjacent to " + this.adjacencies.get(i).getRow() + " , " + this.adjacencies.get(i).getColumn());
			}
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
