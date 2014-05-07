import java.awt.Polygon;
import java.util.ArrayList;

public class HexagonCell {
	
	//Variables used to draw the hexagons
	Polygon outerHex = new Polygon();
	Polygon innerHex = new Polygon();
	ArrayList<Integer> xCoordsOuter = new ArrayList<Integer>();
	ArrayList<Integer> xCoordsInner = new ArrayList<Integer>();
	ArrayList<Integer> yCoordsOuter = new ArrayList<Integer>();
	ArrayList<Integer> yCoordsInner = new ArrayList<Integer>();
	int x0, y0;
	int xIn, xOut, yIn, yOut;
	int circleCentreX, circleCentreY;
	
	//Colour of the piece in the hexagon
	char colour;
	
	int column, row;//Index for the hexagon on gameboard
	
	//Creates an empty hexagon
	public HexagonCell(){
		
		this.x0 = 0;
		this.y0 = 0;
		this.column = 0;
		this.row = 0;
		this.circleCentreX = 0;
		this.circleCentreY = 0;
		this.colour = '-';
	}
	
	//Creates a new hexagon with specified values
	public HexagonCell(int x0, int y0, int column, int row, char colour){
		
		
		this.colour = colour;
		this.column = column;
		this.row = row;
		this.x0 = x0;
		this.y0 = y0;
		
		this.circleCentreX = x0 - (60/2);
		this.circleCentreY = (y0+(y0+80))/2 - (60/2);
		
		
		for(int i=0;i<6;i++){
			if (i == 0 || i == 3){
				this.xOut = x0;
				this.xIn = x0;
			}
			else if (i==1 || i== 2){
				this.xOut = x0 + 34;
				this.xIn = this.xOut;
			}
			else {
				this.xOut = x0 - 34;
				this.xIn = this.xOut + 1;
			}
			
			this.xCoordsOuter.add(i,new Integer(this.xOut));
			this.xCoordsInner.add(i, new Integer(this.xIn));
			
			if(i==0){
				this.yOut = y0;
				this.yIn = this.yOut + 1;
			}
			else if(i%2 == 0){
				this.yOut = y0 + 60;
				this.yIn = this.yOut;
			}
			else if( i == 3){
				this.yOut = y0 + 80;
				this.yIn = this.yOut - 1;
			}
			else if( i == 1 || i == 5){
				this.yOut = y0 + 20;
				this.yIn = this.yOut;
			}
			
			this.yCoordsOuter.add(i, new Integer(this.yOut));
			this.yCoordsInner.add(i, new Integer(this.yIn));
		}

		for(int i=0;i<6;i++){
			this.outerHex.addPoint(this.xCoordsOuter.get(i).intValue(), this.yCoordsOuter.get(i).intValue());
			this.innerHex.addPoint(this.xCoordsInner.get(i).intValue(), this.yCoordsInner.get(i).intValue());
		}
		
		
	}
	
	
	
	//Getters and setters
	protected Polygon getOuterHex(){
		return this.outerHex;
	}
	protected Polygon getInnerHex(){
		return this.innerHex;
	}
	protected void setColour(char col){
		this.colour = col;
	}
	protected char getColour(){
		return this.colour;
	}
	protected void setColumn(int in){
		this.column = in;
	}
	protected int getColumn(){
		return this.column;
	}
	protected void getRow(int in){
		this.row = in;
	}
	protected int getRow(){
		return this.row;
	}
	protected void setX0(int in){
		this.x0 = in;
	}
	protected int getX0(){
		return this.x0;
	}
	protected void setY0(int in){
		this.y0 = in;
	}
	protected int getY0(){
		return this.y0;
	}
	protected int getCCX(){
		return this.circleCentreX;
	}
	protected int getCCY(){
		return this.circleCentreY;
	}
}