/**
 * A class used in the creation of the minimax tree
 * which allows us to store information about:
 * @param parent: the parent node to this one
 * @param children: a list of all the child nodes of this one
 * @param newMove: the move that this node represents
 * 
 * @author Ahowindt and JMcLaren
 */
package aiproj.ahowindt;

import java.util.ArrayList;

import aiproj.fencemaster.Move;

public class Node {


	private double evalValue;
	private Node parent;
	private int ply;
	private Move newMove;
	protected ArrayList<Node> children;
	protected boolean hasParent;

	public Node(int ply, Move move) {
		this.evalValue = 0;
		this.ply = ply;
		this.newMove = move;
		this.children = new ArrayList<Node>();
		this.hasParent = false;

	}

	//Getters and Setters
	protected void setParent(Node parentNode) {
		this.parent = parentNode;
		this.hasParent = true;
	}

	protected Node getParent() {
		return this.parent;
	}

	protected boolean getIsParent(){
		return this.hasParent;
	}

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

	protected ArrayList<Node> getChildren(){
		return this.children;
	}
}