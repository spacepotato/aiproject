package aiproj.ahowindt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import aiproj.fencemaster.Move;
import aiproj.fencemaster.Piece;

public class MinMaxTree implements Piece {

	private Node parent;
	private Move idealMove;
	private int player, opponent;
	
	private Move forcedMove;

	protected Gameboard gb;

	public MinMaxTree(Gameboard gb, int player) {

		this.idealMove = new Move(player, false, 0, 0);
		this.parent = new Node(1, new Move());
		this.player = player;
		this.gb = gb;
		this.forcedMove = null;

		if (player == BLACK) {
			opponent = WHITE;
		} else if (player == WHITE) {
			opponent = BLACK;
		}

	}

	public Move runMiniMax() {
		
		createTree();
//		createTree2(this.parent, this.player, 0);
		return alphaBetaSearch();
		
	}

	protected void createTree2(Node node, int playerValue, int ply) {

		for (List<Hexagon> tempList : this.gb.getBoard()) {
			for (Hexagon tempHex : tempList) {

				if (tempHex == null)
					continue;
				
				if(tempHex.getValue() != EMPTY)
					continue;
				Move tempMove = new Move();
				tempMove.Row = tempHex.getRow();
				tempMove.Col = tempHex.getColumn();
				tempMove.P = playerValue;

				Node tempNode = new Node(ply + 1, tempMove);

				node.children.add(tempNode);
				tempNode.setParent(node);

				if (ply + 1 <= 3) {
					createTree2(tempNode, swapValue(playerValue), ply + 1);
				} else {
					return;
				}
			}
		}

	}

	protected int swapValue(int playerValue) {
		if (playerValue == WHITE)
			return BLACK;
		else
			return WHITE;

	}
	


	// The minimax recursive algorithm enclosed by the searchTree function was
	// adapted from "Artificial Intelligence: A
	// Modern Approach" by Stuart Russell and Peter Norvig.
	protected Move alphaBetaSearch() {

		double value = maxValue(this.parent, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		System.out.println("value-alpha-beta = " + value);
		
		double maxVal = Double.NEGATIVE_INFINITY;
		Move idealMove = new Move();
		for (Node tempNode : this.parent.children) {
			System.out.println("tempNode.getEvalValue() = " 
		+ tempNode.getEvalValue());

			if (tempNode.getEvalValue() >= maxVal) {
					idealMove = tempNode.getMove();
					maxVal = tempNode.getEvalValue();
				
			}
		}
		if(this.forcedMove != null)
			return this.forcedMove;
		return idealMove;

	}

	protected Double maxValue(Node currentNode, Double alpha, Double beta) {

		double value;
		double maxVal = Double.NEGATIVE_INFINITY;

		if (currentNode.children.isEmpty()) {
			double val1 = evalFunc(this.gb, currentNode.getMove());
			System.out.println("Eval value = " + val1 + " PLY: " + currentNode.getPly());
			currentNode.setEvalValue(val1);
			this.gb.revertBoard(currentNode.getMove());
			return currentNode.getEvalValue();
		}

		value = Double.NEGATIVE_INFINITY;

		for (Node tempNode : currentNode.children) {
			this.gb.updateBoard(tempNode.getMove());
			value = Math.max(value, minValue(tempNode, alpha, beta));
			this.gb.revertBoard(tempNode.getMove());
			if (value > maxVal) {
				maxVal = value;
			}
			
			if (value >= beta) {
				return value;
			}

			alpha = Math.max(alpha, value);

		}
		
		currentNode.setEvalValue(maxVal);

		return value;
	}

	protected Double minValue(Node currentNode, Double alpha, Double beta) {

		double value;
		double minVal = Double.POSITIVE_INFINITY;

		if (currentNode.children.isEmpty()) {
			double val1 = evalFunc(this.gb, currentNode.getMove());
			System.out.println("Eval value = " + val1 + " PLY: " + currentNode.getPly());
			currentNode.setEvalValue(val1);
			this.gb.revertBoard(currentNode.getMove());
			return currentNode.getEvalValue();

		}
		
		value = Double.POSITIVE_INFINITY;

		for (Node tempNode : currentNode.children) {
			this.gb.updateBoard(tempNode.getMove());
			value = Math.min(value, maxValue(tempNode, alpha, beta));
			this.gb.revertBoard(tempNode.getMove());
			if (value < minVal) {
				minVal = value;
			}


			if (value <= alpha) {
				return value;
			}

			beta = Math.min(beta, value);

		}

		currentNode.setEvalValue(minVal);
		
		return value;
	}

	private Double evalFunc(Gameboard gb, Move updateMove) {
		Double heuristicVal = (tripodEval2(gb, this.player, updateMove) 
				- tripodEval2(gb, this.opponent, updateMove))
				+ (loopEval(gb, this.player) - loopEval(gb, this.opponent));
		return heuristicVal;
	}


protected double tripodEval2(Gameboard board, int player, Move updateMove){
	
	WinChecker winCheck = new WinChecker();
	
	if (winCheck.tripodWin(board, player)) {
		System.out.println("A tripod win has been detected for " + 
				player);
//		board.printBoard(System.out);
//		Move toForce = board.getUpdatedMove();
//		toForce.P = swapPlayer(toForce.P);
//		this.forcedMove = toForce;

		return 100000;
	}
	
	board.revertBoard(updateMove);
	initialisePriorityValues(board.getBoard(), player);
	
	double toReturn = board.getBoard()
			.get(updateMove.Row).get(updateMove.Col).getPriorityValue();
	
	board.updateBoard(updateMove);
	
	
	return 100.0/toReturn;

}

boolean hexIsLink(List<List<Hexagon>> hexBoard, Hexagon hex, int player){
	List<Coordinate> adjs = hex.getAdjacencies();
	

	for(int i=0; i<2 ;i++){
		Coordinate adj1Coords = adjs.get(i);
		Coordinate adj2Coords = adjs.get(i+3);
		if(adj1Coords.getRow() == 999 || adj1Coords.getColumn() == 999){
			continue;
		}
		if(adj2Coords.getRow() == 999 || adj2Coords.getColumn() == 999){
			continue;
		}
		
		Hexagon adjHex1 = 
				hexBoard.get(adj1Coords.getRow()).get(adj1Coords.getColumn());
		Hexagon adjHex2 = 
				hexBoard.get(adj2Coords.getRow()).get(adj2Coords.getColumn());
		if(adjHex1.getValue() == player && adjHex2.getValue() == player){
			return true;
		}
	}
	return false;
}


private void initialisePriorityValues(List<List<Hexagon>> hexBoard, 
		int player){

	boolean adjToEdge;
	int n = (gb.getTotalRows()+1)/2;
	int [] edgeCounter = new int[6];
	
	for(List<Hexagon> tempList1 : hexBoard){
		innerLoop : for(Hexagon tempHex1 : tempList1){
			if(tempHex1 == null){
				continue innerLoop;
			}
			
			if(tempHex1.getIsEdge() && tempHex1.getValue() == player){
				int edgeLoc = tempHex1.whichEdge();
				if(edgeLoc != 1){
					edgeCounter[edgeLoc] = 1;
				}
			}
			
		}//End of inner for loop
	}//End of outer for loop
	
	for(List<Hexagon> tempList : hexBoard){
		innerLoop : for(Hexagon tempHex : tempList){
			if(tempHex == null){
				continue innerLoop;
			}
			
			adjToEdge = false;
			
			adjLoop : for(Coordinate coords : tempHex.getAdjacencies()){
				if(coords.getRow() == 999 || coords.getColumn() == 999){
					continue;
				}
				Hexagon adjHex = 
						hexBoard.get(coords.getRow()).get(coords.getColumn());
				if(!tempHex.getIsEdge() && adjHex.getIsEdge()){
					adjToEdge = true;
					break adjLoop;
				}
			}//End of adjLoop
			
			int row = tempHex.getRow(), col = tempHex.getColumn();
			
			if(tempHex.getValue() != EMPTY){
				tempHex.setPriorityValue(1000);
			} else if(tempHex.isCorner()){
				tempHex.setPriorityValue(30);
			} else if(tempHex.getIsEdge()){
				tempHex.setPriorityValue(3);
			} else if(row == n-1 && col == n-1){
				tempHex.setPriorityValue(7);
			} else if(adjToEdge){
				tempHex.setPriorityValue(4);
			} else if(tempHex.getValue() == EMPTY && tempHex.getIsEdge()){
				int edgeLoc = tempHex.whichEdge();
				if(edgeLoc != -1){
					if(edgeCounter[edgeLoc] == 1){
						tempHex.setPriorityValue(999);
					}
				}
			} else{
				tempHex.setPriorityValue(6);
			}
			
			if(hexIsLink(hexBoard, tempHex, player)){
				tempHex.priorityValue--;
				continue innerLoop;
			} 
			adjLoop : for(Coordinate coords : tempHex.getAdjacencies()){
				if(coords.getRow() == 999 || coords.getColumn() == 999){
					continue adjLoop;
				}
				Hexagon adjHex = 
						hexBoard.get(coords.getRow()).get(coords.getColumn());
				if(tempHex.getValue() != player && 
						adjHex.getValue() == player){
					tempHex.priorityValue--;
					break adjLoop;
				}//end of if statement
				
			}//End of adjLoop
			
		}//End of innerLoop
			
	}//End of outer for loop
	
//	for(List<Hexagon> tempList1 : hexBoard){
//		for(Hexagon tempHex1 : tempList1){
//			if(tempHex1 == null){
//				continue;
//			}
//			System.out.print(" " + tempHex1.getPriorityValue());
//		}
//		System.out.println();
//	}//End of printing forloop
//	
//	System.out.println();
	
}//End of tripodEval2
	
	private double loopEval(Gameboard board, int playerValue) {

		WinChecker winCheck = new WinChecker();


		if (winCheck.loopWin(board, playerValue)) {
			Move toForce = board.getUpdatedMove();
			if(toForce != null){
			toForce.P = swapPlayer(toForce.P);
			this.forcedMove = toForce;
			return -10000;
			}
			else{
				return 0;
			}
		} else {
			return 0;
		}

	}
	
	private int swapPlayer(int toSwap){
		if (toSwap == 1)
				return 2;
		else
			return 1;
	}

	protected Move getMove() {
		return this.idealMove;
	}

	public class HexagonComparator implements Comparator<Hexagon> {

		@Override
		public int compare(Hexagon h1, Hexagon h2) {

			return h1.getPriorityValue() - h2.getPriorityValue();
		}

	}

	

//============================================================================	
//Our first attempts at creating the tree and tripod evaluation functions
//============================================================================
	

	protected void createTree() {

		int nextPlayer = opponent;

		Queue<Node> treeQueue = new LinkedList<Node>();
		WinChecker winCheck = new WinChecker();

		treeQueue.add(parent);

		while (!treeQueue.isEmpty()) {
			Node currentNode = treeQueue.poll();

			if (currentNode.getMove().Row != -1
					&& currentNode.getMove().Col != -1) {
				gb.updateBoard(currentNode.getMove());

				if (winCheck.getWin(gb) >= 0) {
					gb.revertBoard(currentNode.getMove());
					continue;
				}

				gb.revertBoard(currentNode.getMove());
			}

			// Restricting the depth to which we will create the tree
			if (currentNode.getPly() > 2) {
				break;
			}

			for (List<Hexagon> tempList : this.gb.getBoard()) {
				for (Hexagon tempHex : tempList) {

					if (tempHex == null) {
						continue;
					}

					if (tempHex.getValue() == EMPTY) {

						if (currentNode.getPly() % 2 == 0) {
							nextPlayer = opponent;
						} else if (currentNode.getPly() % 2 == 1) {
							nextPlayer = player;
						}

						Move nextMove = new Move(nextPlayer, false,
								tempHex.getRow(), tempHex.getColumn());

						Node newNode = new Node(currentNode.getPly() + 1,
								nextMove);
						newNode.setParent(currentNode);

						currentNode.children.add(newNode);

						treeQueue.add(newNode);
						tempHex.setValue(EMPTY);
					}// End of if statement

				}// End of inner ForLoop
			}// End of outer ForLoop

			// currentNode.printChildren();

		}// End of While Loop

	}// End of Create Tree Function
	

protected double tripodEval(Gameboard board, int player) {
		
		List<List<Hexagon>> hexBoard = board.getBoard();
		
		WinChecker winCheck = new WinChecker();
		
		int minIndex = 0;
		int minPriority = Integer.MAX_VALUE;
		int counter = 0;

		//board.printBoard(System.out);
		
		initialisePriorityValues(hexBoard, player);
		
//		for(List<Hexagon> tempList : hexBoard){
//			for(Hexagon tempHex : tempList){
//				if(tempHex == null){
//					continue;
//				}
//				
//				System.out.print(" " + tempHex.getPriorityValue());
//				
//			}
//			System.out.println();
//		}

		if (winCheck.tripodWin(board, player)) {
			System.out.println("A tripod win has been detected for " + 
					player);
//			board.printBoard(System.out);
			Move toForce = board.getUpdatedMove();
			toForce.P = swapPlayer(toForce.P);
			this.forcedMove = toForce;

			return 100000;
		}

		int opponent = 0;

		// Determining the colour of the opponent
		if (player == WHITE) {
			opponent = BLACK;
		} else if (player == BLACK) {
			opponent = WHITE;
		}

		int number = 0, minNum = Integer.MAX_VALUE, order = 1;

		//Variable for counting the number of edges that have been hit so far
		int[] edgeCounter = new int[6];
		for (int i = 0; i < 6; i++) {
			edgeCounter[i] = 0;
		}

		//A queue of possible hexagons
		ArrayList<Hexagon> hexQueue = new ArrayList<Hexagon>();

		for (List<Hexagon> tempList : hexBoard) {
			innerloop: for (Hexagon tempHex : tempList) {

				if (tempHex == null) {
					continue innerloop;
				}

				if (tempHex.getValue() != player) {
					continue innerloop;
				} else if (tempHex.getChecked() != 0) {
					continue innerloop;
				} else if(tempHex.isCorner()){
					continue innerloop;
				}

				if (tempHex.getValue() == player) {
					hexQueue.add(tempHex);
//					System.out.println("The hex we are adding is at " + 
					//tempHex.getRow() + " , " + tempHex.getColumn());
				}
				
				if(tempHex.getIsEdge() && !tempHex.isCorner()){
					int index1 = tempHex.whichEdge();
					if (index1 != -1) {
						edgeCounter[index1] = 1;
					}
					System.out.println(index1 + "," + edgeCounter[index1]);
				}

				

				while (!hexQueue.isEmpty()) {
					
					//Finding the next hexagon with the highest priority
					counter = 0;
					minIndex = 0;
					minPriority = Integer.MAX_VALUE;
					for(Hexagon minHex: hexQueue){
						if(minHex.getPriorityValue() < minPriority){
							minPriority = minHex.getPriorityValue();
							minIndex = counter;
						}
						counter++;
					}
					Hexagon currentHex = hexQueue.get(minIndex);
					hexQueue.remove(minIndex);
					
					//"Increasing" the priority of the surrounding hexagons
					if(currentHex.getValue()  == EMPTY){
						currentHex.updatePriorityValue(player, hexBoard, 
								hexQueue);
					}
					
					
					
					//Finding if the current hexagon is on the edge
					int loc1 = 0;
					if(currentHex.getIsEdge()){
						int k = currentHex.whichEdge();
						loc1 = k;
						if(k != -1){
							if(currentHex.getValue() == EMPTY && 
									edgeCounter[k] == 1){
								continue;
							}
							edgeCounter[k] = 1;
						}
					}
					
//					System.out.println("loc = "+ loc1 + " " + "edgeCounter = "
//							+ edgeCounter[loc1]);
					adjLoop : for (Coordinate coords : currentHex.adjacencies)
					{

						if (coords.getRow() == 999 || coords.getColumn() 
								== 999) {
							continue;
						}

						Hexagon nextHex = hexBoard.get(coords.getRow()).get(
								coords.getColumn());

						if(nextHex.getValue() == opponent){
							continue adjLoop;
						} else if(nextHex.isCorner()){
							continue adjLoop;
						} else if(nextHex.getChecked() != 0){
							continue adjLoop;
						} else if(hexQueue.contains(nextHex)){
							continue adjLoop;
						} else if(nextHex.getIsEdge()){
							int loc = nextHex.whichEdge();
							if(loc != -1){
								if(edgeCounter[loc] == 1){
									continue adjLoop;
								}
							}
						}

						hexQueue.add(nextHex);

					}// End of adjacency loop

					//Finding the number of empty hexagons we have traversed
					if (currentHex.getValue() == EMPTY) {
						number++;
					}

//					System.out.println("CurrentHex: " + currentHex.getRow() + 
//							"," + currentHex.getColumn() + "," + 
//							currentHex.getValue());
					
					currentHex.setChecked(order);
					order++;
					


					//Finding the number of edges that have been hit 
					//for this search
					int sum = 0;
					for (int i = 0; i < 6; i++) {
						sum += edgeCounter[i];
					}

					if (sum >= 3) {
						// Possible tripod
						while (!hexQueue.isEmpty()) {
							hexQueue.clear();
						}
						break;
					}

				}// End of While Loop
				
				//Reseting the edge counter for the next search
				for(int x = 0; x < 6; x++){
					edgeCounter[x] = 0;
				}

				if (number < minNum) {
//					System.out.println(number);
					minNum = number;
				}
		
				
//				for(List<Hexagon> tempList1 : hexBoard){
//					for(Hexagon tempHex1 : tempList1){
//						if(tempHex1 == null){
//							continue;
//						}
//						
//						System.out.print(" " + tempHex1.getPriorityValue());
//						
//					}
//					System.out.println();
//				}
				
				//Re-initisalising Priority Values
				initialisePriorityValues(hexBoard, player);
				
				number = 0;

			}// End of inner for loop
		}// End of outer for loop

		System.out.println("minnum = " + minNum);

		
		
//		resetTreeEval(hexBoard);
		if (minNum == 0)
			return 0;
		else
			return 100.0 / minNum;
	}// End of tripodEval

	
	
	
	
	
	

}