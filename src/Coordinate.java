
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
