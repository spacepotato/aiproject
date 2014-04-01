import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Gameboard {
	
	protected String filePath;
	protected List<List<Hexagon>> gameboard; 
	protected int totalRows;

	public Gameboard(){
		gameboard = new ArrayList<List<Hexagon>>();
	}

	  protected boolean generateHexagons(){
		  	Scanner in = new Scanner(System.in);
		  	List<Hexagon> tempList;
		  	int index = 0;
		  
		  	char[] tempChars;
		  	int row = 0;
		  	int offset = 0;
		  	boolean consumed = false;
		  	boolean isEdge; 
		  	
		  	if(in.hasNextInt()){
		  		this.totalRows = in.nextInt();
		  		if(in.hasNextInt()){
		  			in.close();
		  			return false;
		  		}
		  	}
		  	
		  	else{
		  		in.close();
		  		return false;
		  	}

			while(in.hasNextLine()){
				
				String tempInput = in.nextLine();
				
				tempList = new ArrayList<Hexagon>();
				this.gameboard.add(row, tempList);
				
				//All hexagons below the middle row do not start at 0 due to the layout of the columns
				if(row > Math.ceil((this.totalRows)/2)){
					offset++;
				}
				
				for(int j = 0; j < offset; j++){
					tempList.add(j, null);
				}
				
				tempChars = tempInput.toCharArray();
				for (int i = 0; i < tempChars.length; i++) {
					if(tempChars[i] == ' '){
						continue;		
					}
					if(tempChars[i] != 'B' && tempChars[i] != 'W' && tempChars[i] != '-'){
						in.close();
						return false;
					}
					
					//Simple ternary check for edge pieces
					isEdge = (i == 0 || i == tempChars.length - 1)? true: false;
					
					tempList.add(index + offset, new Hexagon(row, index + offset, this.totalRows, tempChars[i], isEdge, tempChars.length + offset));
					index++;
				}

				row++;
			}
			//Closing the inputstream
			in.close();
			return true;
			
			
	  }
	  
	  
	
	
	//Getters and setters		
	protected List<List<Hexagon>> getBoard(){
		return this.gameboard;
	}
	
	protected int getTotalRows(){
		return this.totalRows;
	}
}
