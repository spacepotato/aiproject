import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class GameBoardDisplay extends Component {
	
		int n;
		ArrayList<ArrayList<HexagonCell>> gb;
				
		public GameBoardDisplay(){
			gb = new ArrayList<ArrayList<HexagonCell>>();
		}
		
		
		protected void fillGameBoardDisplay(int boardSize){
			this.n = boardSize;
			int curRowSize = n;
			int x0, y0, xStart;
			boolean middleRow = false;
			int offSet = 0;
			
			
			x0 = 34*n;
			xStart = x0;
			y0 = 0;
			
			for(int row=0;row<(2*n-1);row++){
				this.gb.add(row, new ArrayList<HexagonCell>());
				xStart = x0;
				
				if(row > (n-1)){
					offSet++;
				}
				for(int k=0;k<offSet;k++){
					this.gb.get(row).add(k,null);
				}
				
				
				for(int column=0;column<curRowSize;column++){
					this.gb.get(row).add(column+offSet, new HexagonCell(x0, y0, column+offSet, row, '-'));
					x0 = x0 + 68;
				}
				
				y0 = y0+60;
				if(row == (n-1)){
					middleRow = true;
				}
				
				if(middleRow == true){
					curRowSize = curRowSize -1;
					x0 = xStart + 34;
				}
				else{
					curRowSize +=1;
					x0 = xStart - 34;
				}
			}	
			
		}
		
		public void paint(Graphics g){
			
			Graphics2D g2 = (Graphics2D)g;
			
			for(ArrayList<HexagonCell> tempList : gb){
				for(HexagonCell tempHex : tempList){
					if(tempHex == null){
						continue;
					}
					g2.setColor(Color.black);
					g2.drawPolygon(tempHex.getOuterHex());
					g2.setColor(Color.lightGray);
					g2.fillPolygon(tempHex.getInnerHex());
					
					
					
					if(tempHex.getColour() == 'B'){
						g2.setColor(Color.black);
						g2.fillOval(tempHex.getCCX(), tempHex.getCCY(), 60, 60);
					}
					else if(tempHex.getColour() == 'W'){
						g2.setColor(Color.white);
						g2.fillOval(tempHex.getCCX(), tempHex.getCCY(), 60, 60);
						g2.setColor(Color.black);
						g2.drawOval(tempHex.getCCX(), tempHex.getCCY(), 60, 60);
					}

				}
			}

		}
		
		protected void updateGameBoard(char player, int row, int column){
			this.gb.get(row).get(column).setColour(player);			
		}
		
		protected ArrayList<ArrayList<HexagonCell>> getBoard(){
			return this.gb;
		}
		
		protected int getBoardSize(){
			return this.n;
		}
		
		protected void reset(){
			
			for(ArrayList<HexagonCell> tempHexCellList : gb){
				for(HexagonCell tempHexCell : tempHexCellList){
					if(tempHexCell == null){
						continue;
					}
					tempHexCell.setColour('-');
				}
			}
		}
		
		protected Coordinate findHex(int x, int y){
			Coordinate Loc = new Coordinate(999, 999);
			for(ArrayList<HexagonCell> tempList : this.gb){
				for(HexagonCell tempHex : tempList){
					if(tempHex == null){
						continue;
					}
					if(tempHex.getOuterHex().contains(x,y)){
						Loc.setRow(tempHex.getRow());
						Loc.setColumn(tempHex.getColumn());
					}
				}
			}
			
			return Loc;
		}
	}