
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
	
	protected int getRow(){
		return this.row;
	}
	
}
