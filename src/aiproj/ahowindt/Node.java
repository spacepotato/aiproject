package aiproj.ahowindt;
import java.util.ArrayList;

import aiproj.fencemaster.Move;

public class Node {


	private double evalValue;
	private Node parent;
	private int ply;
	private Move newMove;
	protected ArrayList<Node> children;

	public Node(int ply, Move move) {
		this.evalValue = 0;
		this.ply = ply;
		this.newMove = move;
		this.children = new ArrayList<Node>();

	}

	protected void setParent(Node parentNode) {
		this.parent = parentNode;
	}

	protected Node getParent() {
		return this.parent;
	}

//	protected Gameboard getState() {
//		return this.state;
//	}

	protected double getEvalValue() {
		return this.evalValue;
	}

	protected void setEvalValue(double val) {
		this.evalValue = val;
	}

	protected int getPly() {
		return this.ply;
	}

	protected Move getMove() {
		return this.newMove;
	}

	public void printChildren() {
		System.out.println("========== " + this.getMove().Row + " , "
				+ this.getMove().Col + " ==========");
		for (Node tempNode : this.children) {
			System.out.println("Node value: " + tempNode.getMove().Row + " , "
					+ tempNode.getMove().Col);
		}
		System.out.println("===================================");
	}
}