/**
 * 
 * @author James
 * A simple class to aid in keeping track of Adjacent hexagons
 * @param column refers to column on the game board
 * @param likewise row refers to a row on the game board
 */
public class Coordinate {
	
	protected int column;
	protected int row;
	
	public Coordinate(int column, int row){
		this.column = column;
		this.row = row;
	}
	
	protected int getColumn(){
		return this.column;
	}
	
	protected void setColumn(int column){
		this.column = column;
	}
	
	protected int getRow(){
		return this.row;
	}
	
	protected void setRow(int row){
		this.row = row;
	}
	
}
