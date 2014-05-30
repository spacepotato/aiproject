package aiproj.ahowindt;
/**
 * This class runs our alpha-beta search for the board game. It returns an
 * ideal move given a particular game board state.
 */


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

	protected Gameboard gb;

	protected double base = 3;

	public MinMaxTree(Gameboard gb, int player) {

		this.idealMove = new Move(player, false, 0, 0);
		this.parent = new Node(1, new Move());
		this.player = player;
		this.gb = gb;

		if (player == BLACK) {
			opponent = WHITE;
		} else if (player == WHITE) {
			opponent = BLACK;
		}

	}

	public Move runMiniMax() {
		createTree();
		return alphaBetaSearch();
	}

	protected void createTree2(Node node, int playerValue, int ply) {

		for (List<Hexagon> tempList : this.gb.getBoard()) {
			for (Hexagon tempHex : tempList) {

				if (tempHex == null)
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
			if (currentNode.getPly() > 3) {
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

	// The minimax recursive algorithm enclosed by the searchTree function was
	// adapted from "Artificial Intelligence: A
	// Modern Approach" by Stuart Russell and Peter Norvig.
	protected Move alphaBetaSearch() {

		@SuppressWarnings("unused")
		double value = maxValue(this.parent, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		double maxVal = Double.NEGATIVE_INFINITY;
		Move idealMove = new Move();
		for (Node tempNode : this.parent.children) {

			if (tempNode.getEvalValue() >= maxVal) {
					idealMove = tempNode.getMove();
					maxVal = tempNode.getEvalValue();
				
			}
		}
		return idealMove;

	}

	protected Double maxValue(Node currentNode, Double alpha, Double beta) {

		Double value;
		Double maxVal = Double.NEGATIVE_INFINITY;

		boolean updatedParent = false;
		boolean updatedChild = false;

		if (currentNode.getPly() > 1 || currentNode.children.isEmpty()) {
			Move parentMove = currentNode.getParent().getMove();
			Move currentMove = currentNode.getMove();

			if (parentMove.Row != -1 && parentMove.Col != -1)
				updatedParent = this.gb.updateBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1)
				updatedChild = this.gb.updateBoard(currentMove);

			currentNode.setEvalValue(evalFunc(this.gb));

			if (parentMove.Row != -1 && parentMove.Col != -1 && updatedParent)
				this.gb.revertBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1 && 
					updatedChild)
				this.gb.revertBoard(currentMove);
			return currentNode.getEvalValue();
		}

		value = Double.NEGATIVE_INFINITY;

		for (Node tempNode : currentNode.children) {

			value = Math.max(value, minValue(tempNode, alpha, beta));

			if (value > maxVal) {
				maxVal = value;
			}
			currentNode.setEvalValue(maxVal);
			if (value >= beta) {
				return value;
			}

			alpha = Math.max(alpha, value);

		}
		return value;
	}

	protected Double minValue(Node currentNode, Double alpha, Double beta) {

		Double value;
		Double minVal = Double.POSITIVE_INFINITY;

		boolean updatedParent = false;
		boolean updatedChild = false;

		if (currentNode.getPly() > 1 || currentNode.children.isEmpty()) {

			Move parentMove = currentNode.getParent().getMove();
			Move currentMove = currentNode.getMove();

			if (parentMove.Row != -1 && parentMove.Col != -1)
				updatedParent = this.gb.updateBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1)
				updatedChild = this.gb.updateBoard(currentMove);

			currentNode.setEvalValue(evalFunc(this.gb));

			if (parentMove.Row != -1 && parentMove.Col != -1 && updatedParent)
				this.gb.revertBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1 
					&& updatedChild)
				this.gb.revertBoard(currentMove);
			return currentNode.getEvalValue();
		}

		value = Double.POSITIVE_INFINITY;

		for (Node tempNode : currentNode.children) {

			value = Math.min(value, maxValue(tempNode, alpha, beta));

			if (value < minVal) {
				minVal = value;
			}

			currentNode.setEvalValue(minVal);

			if (value <= alpha) {
				return value;
			}

			beta = Math.min(beta, value);

		}

		return value;
	}

	private Double evalFunc(Gameboard gb) {
		Double heuristicVal = (tripodEval(gb, this.player) - tripodEval(
				gb, this.opponent))
				+ (loopEval(gb, this.player) - loopEval(gb, this.opponent));
		return heuristicVal;
	}

	protected double tripodEval(Gameboard board, int player) {
		
		List<List<Hexagon>> hexBoard = board.getBoard();
		
		WinChecker winCheck = new WinChecker();
		
		int minIndex = 0;
		int minPriority = Integer.MAX_VALUE;
		int counter = 0;
		
		initialisePriorityValues(hexBoard, player);
		

		if (winCheck.tripodWin(board, player)) {

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
					continue;
				}

				if (tempHex.getValue() == player) {
					hexQueue.add(tempHex);
				}
				
				if(tempHex.getIsEdge() && !tempHex.isCorner()){
					int index1 = tempHex.whichEdge();
					if (index1 != -1) {
						if (edgeCounter[index1] == 1){
							continue;
						}
						edgeCounter[index1] = 1;
					}
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
						currentHex.updatePriorityValue(player, 
								hexBoard, hexQueue);
					}
					
					
					
					//Finding if the current hexagon is on the edge
					if(currentHex.getIsEdge()){
						int k = currentHex.whichEdge();
						if(edgeCounter[k] == 1){
							continue;
						}
						if(k != -1){
							edgeCounter[k] = 1;
						}
					}
					
					adjLoop : for (Coordinate coords : 
						currentHex.adjacencies) {

						if (coords.getRow() == 999 || 
								coords.getColumn() == 999) {
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

					
					currentHex.setChecked(order);
					order++;
					


					//Finding the number of edges that have been 
					//hit for this search
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
				
				for(int x = 0; x < 6; x++){
					edgeCounter[x] = 0;
				}

				if (number < minNum) {
					minNum = number;
				}

				
				initialisePriorityValues(hexBoard, player);
				
				number = 0;

			}// End of inner for loop
		}// End of outer for loop


		
		
		resetTreeEval(hexBoard);
		if (minNum == 0)
			return 0;
		else
			return 100.0 / minNum;
	}// End of tripodEval

	private void initialisePriorityValues(List<List<Hexagon>> hexBoard, 
			int player){
		

		boolean adjToEdge;
		
		for(List<Hexagon> tempList : hexBoard){
			for(Hexagon tempHex : tempList){
				if(tempHex == null){
					continue;
				}
				tempHex.setIfUpdated(false);
				adjToEdge = false;
				adjLoop : for(Coordinate coords : tempHex.getAdjacencies()){
					if(coords.getRow() == 999 || coords.getColumn() == 999){
						continue;
					}
					Hexagon adjHex = 
							hexBoard.get(coords.getRow()).
							get(coords.getColumn());
					if(!tempHex.getIsEdge() && adjHex.getIsEdge()){
						adjToEdge = true;
						break adjLoop;
					}
				}
				
				int row = tempHex.getRow(), col = tempHex.getColumn();
				if(tempHex.getValue() == player){
					tempHex.setPriorityValue(1);
				} else if(tempHex.getValue() != EMPTY){
					tempHex.setPriorityValue(1000);
				} else if(tempHex.isCorner()){
					tempHex.setPriorityValue(30);
				} else if(tempHex.getIsEdge()){
					tempHex.setPriorityValue(3);
				} else if(row == gb.getTotalRows() && 
						col == gb.getTotalRows()){
					tempHex.setPriorityValue(7);
				} else if(adjToEdge){
					tempHex.setPriorityValue(4);
				} else{
					tempHex.setPriorityValue(6);
				}
				
				adjLoop : for(Coordinate coords : tempHex.getAdjacencies()){
					if(coords.getRow() == 999 || coords.getColumn() == 999){
						continue adjLoop;
					}
					Hexagon adjHex = 
							hexBoard.get(coords.getRow()).
							get(coords.getColumn());
					if(tempHex.getValue() != player && 
							adjHex.getValue() == player){
						tempHex.priorityValue--;
						tempHex.setIfUpdated(true);
						break adjLoop;
					}
					
				}
				
			}
		}
		
	}
	
	private void resetPriorityValues(List<List<Hexagon>> hexBoard){
		
		for(List<Hexagon> tempList : hexBoard){
			for(Hexagon tempHex : tempList){
				if(tempHex == null){
					continue;
				}
				tempHex.resetPriorityValue();
			}
		}
		
	}
	
	
	private void resetTreeEval(List<List<Hexagon>> hexBoard) {

		resetPriorityValues(hexBoard);
		for (List<Hexagon> tempList : hexBoard) {
			for (Hexagon tempHex : tempList) {
				if (tempHex == null) {
					continue;
				}

				tempHex.setChecked(0);

			}
		}

	}

	private double loopEval(Gameboard board, int playerValue) {

		WinChecker winCheck = new WinChecker();

		if (winCheck.loopWin(board, playerValue)) {

			return 100000;
		} else {
			return 0;
		}

	}

	protected Move getMove() {
		return this.idealMove;
	}



}