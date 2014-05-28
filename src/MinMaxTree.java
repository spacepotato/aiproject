import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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

		// System.setOut(new PrintStream(new FileOutputStream("output.txt")));

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

	public void runMiniMax() {
		// createTree();
		createTree2(this.parent, 1, 0);
		alphaBetaSearch();
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
		if (playerValue == 1)
			return 2;
		else
			return 1;

	}

	protected void createTree() {

		int nextPlayer = opponent;

		Queue<Node> treeQueue = new LinkedList<Node>();
		WinChecker winCheck = new WinChecker();

		treeQueue.add(parent);

		while (!treeQueue.isEmpty()) {
			// System.out.println("Entered the whileLoop");
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
						//
						// Gameboard newState = new
						// Gameboard(currentNode.getState());
						// newState.updateBoard(nextMove);

						// System.out.println("========= " + nextMove.Row +
						// " === " + nextMove.Col + " ================");
						// newState.printBoard(System.out);
						// System.out.println("========================================================================");

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

		// System.out.println("Exited whileLoop");

	}// End of Create Tree Function

	// The minimax recursive algorithm enclosed by the searchTree function was
	// adapted from "Artificial Intelligence: A
	// Modern Approach" by Stuart Russell and Peter Norvig.
	protected void alphaBetaSearch() {

		@SuppressWarnings("unused")
		double value = maxValue(this.parent, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY);
		double maxVal = Double.NEGATIVE_INFINITY;

		for (Node tempNode : this.parent.children) {
			// System.out.println("eval value for child : " +
			// evalFunc(tempNode));

			if (tempNode.getEvalValue() > maxVal) {
				Move tempMove = tempNode.getMove();
				if (this.gb.getBoard().get(tempMove.Row).get(tempMove.Col)
						.getValue() == 0) {
					this.idealMove = tempNode.getMove();
					maxVal = tempNode.getEvalValue();
				}
			}
		}

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
			if (currentMove.Row != -1 && currentMove.Col != -1 && updatedChild)
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
			// this.gb.updateBoard(currentNode.getParent().getParent().getMove());

			Move parentMove = currentNode.getParent().getMove();
			Move currentMove = currentNode.getMove();

			if (parentMove.Row != -1 && parentMove.Col != -1)
				updatedParent = this.gb.updateBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1)
				updatedChild = this.gb.updateBoard(currentMove);

			currentNode.setEvalValue(evalFunc(this.gb));

			if (parentMove.Row != -1 && parentMove.Col != -1 && updatedParent)
				this.gb.revertBoard(parentMove);
			if (currentMove.Row != -1 && currentMove.Col != -1 && updatedChild)
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
		Double heuristicVal = (tripodEval(gb.getBoard(), this.player) - tripodEval(
				gb.getBoard(), this.opponent))
				+ (loopEval(gb, this.player) - loopEval(gb, this.opponent));
		// System.out.println("Heuristic Value = " + heuristicVal);
		return heuristicVal;
	}

	private double tripodEval(List<List<Hexagon>> hexBoard, int player) {

		int opponent = 0;

		// Determining the colour of the opponent
		if (player == WHITE) {
			opponent = BLACK;
		} else if (player == BLACK) {
			opponent = WHITE;
		}

		int number = 0, minNum = Integer.MAX_VALUE, order = 1;

		int[] edgeCounter = new int[6];

		for (int i = 0; i < 6; i++) {
			edgeCounter[i] = 0;
		}

		Comparator<Hexagon> comparator = new HexagonComparator();
		PriorityQueue<Hexagon> hexQueue = new PriorityQueue<Hexagon>(11,
				comparator);

		for (List<Hexagon> tempList : hexBoard) {
			innerloop: for (Hexagon tempHex : tempList) {

				if (tempHex == null) {
					continue innerloop;
				}

				tempHex.setPriorityValue(player, hexBoard);

				if (tempHex.getValue() != player) {
					continue innerloop;
				} else if (tempHex.getChecked() != 0) {
					continue innerloop;
				}

				if (tempHex.getValue() == player) {
					hexQueue.add(tempHex);
					tempHex.setParent(null);
				}

				while (!hexQueue.isEmpty()) {

					Hexagon currentHex = hexQueue.poll();

					for (Coordinate coords : currentHex.adjacencies) {

						if (coords.getRow() == 999 || coords.getColumn() == 999) {
							continue;
						}

						Hexagon nextHex = hexBoard.get(coords.getRow()).get(
								coords.getColumn());

						if (nextHex.getValue() == opponent) {
							continue;
						} else if (hexQueue.contains(nextHex)) {
							continue;
						} else if (nextHex.getChecked() != 0) {
							continue;
						} else if (nextHex.getIsEdge()) {
							int j = nextHex.whichEdge();
							if (j != -1) {
								if (edgeCounter[j] == 1)
								//TODO What should go here?
								continue;
							}
						}

						nextHex.setParent(new Hexagon(currentHex));
						nextHex.setPriorityValue(player, hexBoard);

						hexQueue.add(nextHex);

					}// End of adjacency loop

					hexQueue.comparator();

					if (currentHex.value == EMPTY) {
						number++;
					}

					 for(Hexagon printHex : hexQueue){
					 System.out.println("Priority of " + printHex.getRow() + " , " + printHex.getColumn() + ": " +
					 printHex.getPriorityValue());
					 }

					currentHex.setChecked(order);
					order++;

					int sum = 0;
					for (int i = 0; i < 6; i++) {
						sum += edgeCounter[i];
					}

					if (sum >= 3) {
						// Possible tripod

						while (!hexQueue.isEmpty()) {
							hexQueue.poll();
						}

						break;
					}

				}// End of While Loop

				if (number < minNum) {
					minNum = number;
				}

			}// End of inner for loop
		}// End of outer for loop

		resetTreeEval(hexBoard);
		if (minNum == 0)
			return 0;
		else
			return 100 / minNum;
	}// End of tripodEval

	private void resetTreeEval(List<List<Hexagon>> hexBoard) {

		for (List<Hexagon> tempList : hexBoard) {
			for (Hexagon tempHex : tempList) {
				if (tempHex == null) {
					continue;
				}

				tempHex.resetPriorityValue();
				tempHex.setChecked(0);
				tempHex.setParent(null);
			}
		}

	}

	int backtrace(Hexagon h) {

		Hexagon hex = h;
		int number = 0;

		while (hex.prevHex != null) {
			if (hex.getValue() == EMPTY) {
				number++;
			}
			hex = hex.prevHex;
		}
		return number;
	}

	private double loopEval(Gameboard board, int playerValue) {

		WinChecker winCheck = new WinChecker();

		// board.printBoard(System.out);

		if (winCheck.loopWin(board, playerValue)) {
			System.out.println("A loop win has been detected for " + playerValue);
			board.printBoard(System.out);

			return 10000;
		} else {
			return 0;
		}

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

}