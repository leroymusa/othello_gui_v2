package ca.yorku.eecs3311.othello.model;



/**
 * Interface for defining a visitor that checks the validity of a move on an Othello board.
 * Classes implementing this interface can specify the logic for validating a move.
 */
public interface MoveVisitorInterface {
	
	
    /**
     * Determines if a move is valid for the given player at the specified row and column.
     *
     * @param board  the Othello board to check
     * @param row    the row index of the move
     * @param col    the column index of the move
     * @param player the player making the move ('X' or 'O')
     * @return true if the move is valid, false otherwise
     */
	public boolean visit(OthelloBoard board,int row,int col,char player);
}