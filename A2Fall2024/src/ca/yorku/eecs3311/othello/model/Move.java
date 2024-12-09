package ca.yorku.eecs3311.othello.model;

/**
 * 
 * A Move is the coordinates of an Othello move.
 * A Move has a row and a column.
 * 
 * @autor leroy musa
 *
 */
public class Move {
	private int row, col;

	/**
	 * The Move constructor
	 * 
	 * @param row  The row of the Move.
	 * @param col  The column of the Move.
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Returns the row of the Move
	 * @return the row of the coordinates of the Move
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the Move
	 * @return the column of the coordinates of the Move
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Sets the row of the Move
	 * @param the row of the coordinates of the Move
	 */
	public void setRow(int r) {
		this.row = r;
	}

	/**
	 * Sets the column of the Move
	 * @param the column of the coordinates of the Move
	 */
	public void setCol(int c) {
		this.col = c;
	}
	
	public String toString() {
		return "("+this.row+","+this.col+")";
	}
}
