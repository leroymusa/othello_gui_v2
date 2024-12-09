package ca.yorku.eecs3311.othello.model;

//import ca.yorku.eecs3311.othello.model.DiscGetterVisitorInterface;
/**
 * 
 * Visits OthelloBoard and extracts the disc at a certain position. Implementation of a visitor
 * 
 * @autor leroy musa
 */

public class DiscGetterVisitor implements DiscGetterVisitorInterface{
	
	/**
	 * 
	 * @param board
	 * @param row
	 * @param col
	 * 
	 * 
	 * Return the disc at position (row, col)
	 */
	@Override
	public char visit(OthelloBoard board,int row,int col) {
		if (board.validCoordinate(row,col))
			return (board.getBoardList())[row][col];
		else
			return OthelloBoard.EMPTY;
	}
}