import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Gameboard {
	
	protected String filePath;
	protected List<List<Hexagon>> gameboard = new ArrayList<List<Hexagon>>();
	protected int totalRows;
	
	//When we initialise the game board we need to know how many rows there are 
	//so that we can construct the board as necessary
	public Gameboard(String filePath){
		this.filePath = filePath;
		
	}
	
	//Reading in the data from the file and then generating the arraylist from this
	//data
	
	//At the moment this just prints out each line that it reads in
	  protected void readTextFile() throws IOException {
		    Path path = Paths.get(this.filePath);
		    this.generateHexagons(Files.readAllLines(path, StandardCharsets.UTF_8));
		  }
	  
	  //TODO get the total rows from the input file
	  protected void generateHexagons(List<String> input){
		  	
		  	List<Hexagon> tempList;
		  
		  	char[] tempChars;
		  	int row = 0;
		  	int offset = 0;
		  	boolean consumed = false;
		  	boolean isEdge; 
		  	
			for (String tempInput : input) {
				
				tempList = new ArrayList<Hexagon>();
				this.gameboard.add(tempList);
				
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
				tempChars = tempInput.toCharArray();
				for (int i = 0; i < tempChars.length; i++) {
					
					//Simple ternary check for edge pieces
					isEdge = (i == 0 || i == tempChars.length - 1)? true: false;
					
					tempList.add(new Hexagon(row, i + offset, this.totalRows, tempChars[i], isEdge));
				}
				row++;
//				
//				System.out.println(this.gameboard.toString());
			}
			
			
	  }
	  
	  
	
	
	//Getters and setters	
	protected String getFilePath(){
		return this.filePath;
	}
	
	protected void setInput(String filePath){
		this.filePath = filePath;
	}
	
	protected List<List<Hexagon>> getBoard(){
		return this.gameboard;
	}
	
	protected int getTotalRows(){
		return this.totalRows;
	}
}
