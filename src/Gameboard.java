import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Gameboard {
	
	protected String filePath;
	protected List<List<Hexagon>> gameboard = new ArrayList<List<Hexagon>>();
	protected int totalRows;

	public Gameboard(){
		
	}

	  protected void generateHexagons(){
		  	Scanner in = new Scanner(System.in);
		  	List<Hexagon> tempList;
		  
		  	char[] tempChars;
		  	int row = 0;
		  	int offset = 0;
		  	boolean consumed = false;
		  	boolean isEdge; 

			while(in.hasNextLine()){
				
				String tempInput = in.nextLine();
				//Get rid of all spaces
				tempInput.replaceAll("\\s", "");
				
				tempList = new ArrayList<Hexagon>();
				this.gameboard.add(row, tempList);
				
				//We need to read the first line to get the board size
				if(!consumed){
					this.totalRows = Integer.parseInt(tempInput) * 2 - 1;
					consumed = true;
					continue;
				}
				
				//All hexagons below the middle row do not start at 0 due to the layout of the columns
				if(row > Math.ceil((this.totalRows)/2)){
					offset++;
				}
				
				for(int j = 0; j < offset; j++){
					tempList.add(j, null);
				}
				
				tempChars = tempInput.toCharArray();
				for (int i = 0; i < tempChars.length; i++) {
					
					//Simple ternary check for edge pieces
					isEdge = (i == 0 || i == tempChars.length - 1)? true: false;
					
					tempList.add(i + offset, new Hexagon(row, i + offset, this.totalRows, tempChars[i], isEdge, tempChars.length + offset));

				}

				row++;
			}
			//Closing the inputstream
			in.close();
			
			
	  }
	  
	  
	
	
	//Getters and setters		
	protected List<List<Hexagon>> getBoard(){
		return this.gameboard;
	}
	
	protected int getTotalRows(){
		return this.totalRows;
	}
}
