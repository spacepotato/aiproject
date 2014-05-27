import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import aiproj.fencemaster.Piece;
import aiproj.fencemaster.Move;



public class MinMaxTree implements Piece{
	
	private Node parent;
	private Move idealMove;
	private Move nextMove;
	private int player, opponent;
	
	protected double base = 3;
	
	public MinMaxTree(Gameboard gb, int player){
		
		//System.setOut(new PrintStream(new FileOutputStream("output.txt")));
		
		this.idealMove = new Move(player,false,0,0);
		this.nextMove = new Move();
		this.parent = new Node(gb, 0, this.nextMove);
		this.player = player;
		
		if(player == BLACK){
			opponent = WHITE;
		} else if(player == WHITE){
			opponent = BLACK;
		}

	}
	
	public void runMiniMax(){
		createTree();
		alphaBetaSearch();
	}
	
	protected void createTree(){
		List<List<Hexagon>> Hexagons = this.parent.getState().getBoard();

		
		int ply=0;
//		boolean gameEnd = false;
		
		Stack<Node> treeStack = new Stack<Node>();
		WinChecker winCheck = new WinChecker();
		
		treeStack.push(parent);
		
		while(!treeStack.isEmpty()){
			
			System.out.println("We are stuck in the tree creation loop");
			
			Node currentNode = treeStack.pop();
			

			if(winCheck.getWin(this.parent.getState()) == 1){
				continue;
			}
			
			if(currentNode.getPly() >= 1){
				continue;
			}
			

					
			for(List<Hexagon> tempList : Hexagons){
				for(Hexagon tempHex : tempList){
					if(tempHex == null){
						continue;
					}
					if(tempHex.getValue() == EMPTY){
						
						
						
						nextMove.Row = tempHex.getRow();
						nextMove.Col = tempHex.getColumn();
						
						//Determining who will make the next move
						if(currentNode.getPly()%2 == 0){
							nextMove.P = this.opponent;
						} else if(currentNode.getPly()%2 == 1){
							nextMove.P = this.player;
						}

						Gameboard newGB = new Gameboard(currentNode.getState());
						Node newNode = new Node(newGB, ply, nextMove);
						newNode.setParent(currentNode);
						currentNode.children.add(newNode);
						currentNode.getState().updateBoard(nextMove);
						treeStack.push(newNode);
						
						System.out.println("To the current node of: " + currentNode.getMove().Row + " , " + currentNode.getMove().Col + " we are adding the child " + newNode.getMove().Row + " " + newNode.getMove().Col);
						
					}//End of if statement

				}//End of inner ForLoop
			}//End of outer ForLoop

			if(ply == 0){
				ply++;
			} else {
				ply = currentNode.getParent().getPly() + 2;
			}
				}
			}

	
	//The minimax recursive algorithm enclosed by the searchTree function was adapted from "Artificial Intelligence: A 
	//Modern Approach" by Stuart Russell and Peter Norvig.
	protected void alphaBetaSearch(){
		
		@SuppressWarnings("unused")
		double value = maxValue(this.parent, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		double maxVal = Double.NEGATIVE_INFINITY;
		
		for(Node tempNode : this.parent.children){
//			System.out.println("eval value for child : " + evalFunc(tempNode));
			if(tempNode.getEvalValue() > maxVal){
				this.idealMove = tempNode.getMove();
				maxVal = tempNode.getEvalValue();
			}
		}
		
	}
	
	protected Double maxValue(Node currentNode, Double alpha, Double beta){
		
		Double value;
		Double maxVal = Double.NEGATIVE_INFINITY;
		
		if(currentNode.getPly() > 1 || currentNode.children.isEmpty()){
			currentNode.setEvalValue(evalFunc(currentNode.getState()));
			return currentNode.getEvalValue();
		}
		
		value = Double.NEGATIVE_INFINITY; 
		
		for(Node tempNode : currentNode.children){
			
			value = Math.max(value,minValue(tempNode, alpha, beta));
			
			if(value > maxVal){
				maxVal = value;
			}
			currentNode.setEvalValue(maxVal);
			if(value >= beta){
				return value;
			}
			
			alpha = Math.max(alpha, value);

		}
		return value;
	}
	
	protected Double minValue(Node currentNode, Double alpha, Double beta){

		Double value;
		Double minVal = Double.POSITIVE_INFINITY;
		
		if(currentNode.getPly() > 1 || currentNode.children.isEmpty()){
			currentNode.setEvalValue(evalFunc(currentNode.getState()));
			return evalFunc(currentNode.getState());
		}
		
		value = Double.POSITIVE_INFINITY;
		
		for(Node tempNode : currentNode.children){
			
			value = Math.min(value, maxValue(tempNode,alpha,beta));
			
			if(value < minVal){
				minVal = value;
			}
			
			currentNode.setEvalValue(minVal);
			
			if(value <= alpha){
				return value;
			}
			
			beta = Math.min(beta, value);

		}

		return value;
	}

	private Double evalFunc(Gameboard gb){
		return (tripodEval(gb.getBoard(), this.player) - tripodEval(gb.getBoard(), this.opponent))
				+ (loopEval(gb, this.player) - loopEval(gb, this.opponent));
	}
	
	private double tripodEval(List<List<Hexagon>> hexBoard, int player){
		
		int opponent = 0;
		
		//Determining the colour of the opponent
		if(player == WHITE){
			opponent = BLACK;
		} else if(player == BLACK){
			opponent = WHITE;
		}
		
	
		int number = 0;
		int order = 1;
		
		Comparator<Hexagon> comparator = new HexagonComparator();
		PriorityQueue<Hexagon> hexQueue = new PriorityQueue<Hexagon>(11, comparator);
		
		for(List<Hexagon> tempList : hexBoard){
			innerloop : for(Hexagon tempHex : tempList){
				
				if(tempHex == null){
					continue innerloop;
				}
				
				tempHex.setPriorityValue(player);
				
				if(tempHex.getValue() != player){
					continue innerloop;
				} else if(tempHex.getChecked() != 0){
					continue innerloop;
				}
				
				if(tempHex.getValue() == player){
					hexQueue.add(tempHex);
					tempHex.setParent(null);
				}
				
				while(!hexQueue.isEmpty()){
					
					System.out.println("We are stuck in the tripod loop");
					
					Hexagon currentHex = hexQueue.poll();
					
					
					for(Coordinate coords : currentHex.adjacencies){
						
						if(coords.getRow() == 999 || coords.getColumn() == 999){
							continue;
						}
						
						Hexagon nextHex = hexBoard.get(coords.getRow()).get(coords.getColumn());
						nextHex.setParent(currentHex);
						
						if(nextHex.getValue() == opponent){
							continue;
						}
						
						hexQueue.add(nextHex);
						
					}
					
					hexQueue.comparator();
					
					if(currentHex.getIsEdge()){
						number += backtrace(currentHex, tempHex);
					}
					
					currentHex.setChecked(order);
					order++;
				}
				
			}
		}
		
		
		
		
		return Math.pow(100, -number);
	}
	
	int backtrace(Hexagon end, Hexagon start){
		
		Hexagon hex = end;
		int number=0;
		while(hex != start){
			hex = hex.parent;
			number++;
		}
		return number;
	}
	

	private double loopEval(Gameboard board, int playerValue){

//		int countMax = 0, count = 0;
//		int opponentValue = 0;
//		
//		if(playerValue == 1){
//			opponentValue = 2;
//		} else if(playerValue == 2){
//			opponentValue = 1;
//		}
//
//		for(List<Hexagon> tempList : hexBoard){
//			innerLoop : for(Hexagon tempHex : tempList){
//				if(tempHex == null) {
//					continue innerLoop;
//				}
//				if(tempHex.getValue() == playerValue) {
//					continue innerLoop;
//				}
//				if(tempHex.numberOfExposedEdges() > 0){
//					continue;
//				}
//				
//				adjLoop : for(Coordinate adj : tempHex.adjacencies){
//					if(adj.getColumn() == 999 || adj.getRow() == 999){
//						continue adjLoop;
//					}
////					System.out.println(adj.getRow() + " " + adj.getColumn());
//					Hexagon nextHex = hexBoard.get(adj.getRow()).get(adj.getColumn());
////					System.out.println(nextHex.toString() + " has the value of " + nextHex.getValue());
//
//					//Counting the number of player's pieces surrounding the current space
//					if(nextHex.getValue() == playerValue){
//						count++;
//					}
//					else if(nextHex.getValue() == opponentValue){
////						System.out.println("We are breaking the loop");
//						count = 0;
//						break adjLoop;
//					}
//				}
//				if(count > countMax){
//					countMax = count;
//				}
//				System.out.println("For hexagon: " + tempHex.toString() + "we are returning the count of " + count);
//				count = 0;
//			}
//		}
//		
//		double countMaxD = (double) countMax;
//		return Math.pow(base, countMaxD);
		
		WinChecker winCheck = new WinChecker();
		
		if(winCheck.loopWin(board, playerValue)){
//			System.out.println("Playing a move at (" + node.getMove().Row + " , " + node.getMove().Col + ") causes a win for " + node.getMove().P);
				return 100;
		}
		else{
			return 0;
		}
		
		
		
	}
	
	protected Move getMove(){
		return this.idealMove;
	}
	
	public class HexagonComparator implements Comparator<Hexagon>{

		@Override
		public int compare(Hexagon h1, Hexagon h2) {
			
			return h1.getPriorityValue() - h2.getPriorityValue();
		}
		

		
	}

	
}