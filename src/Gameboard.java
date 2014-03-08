import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Gameboard {
	
	protected int sidelength;
	protected String filePath;
	protected ArrayList<Hexagon> gameboard = new ArrayList<Hexagon>();
	
	//When we initialise the game board we need to know how many rows there are 
	//so that we can construct the board as necessary
	public Gameboard(int sidelength, String filePath){
		this.sidelength = sidelength;
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
		  	
		  	char[] tempChars;
		  	int row = 0;
		  	int totalRows = 0;
		  	
			for (String tempInput : input) {
				
				if(row == 0){
					totalRows = Integer.parseInt(tempInput) * 2 - 1;
					row++;
					continue;
				}
				tempChars = tempInput.toCharArray();
				for (int i = 0; i < tempChars.length; i++) {
					this.gameboard.add(new Hexagon(row, i, totalRows, tempChars[i], false));
				}
				row++;
			}
	  }
	  
	  
	
	
	//Getters and setters
	protected int getRows(){
		return this.sidelength;
	}
	
	protected void setRows(int sidelength){
		this.sidelength = sidelength;
	}
	
	protected String getFilePath(){
		return this.filePath;
	}
	
	protected void setInput(String filePath){
		this.filePath = filePath;
	}
	
	protected ArrayList<Hexagon> getBoard(){
		return this.gameboard;
	}
}
