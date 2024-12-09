/**
 * Interface for defining a visitor that retrieves the disc at a specific position on an Othello board.
 * Classes implementing this interface can specify how to access a disc at the given row and column.
 */

package ca.yorku.eecs3311.othello.model;

public interface DiscGetterVisitorInterface {
	
    /**
     * Visits the Othello board and retrieves the disc at the specified row and column.
     *
     * @param board the Othello board to process
     * @param row   the row index of the disc to retrieve
     * @param col   the column index of the disc to retrieve
     * @return the disc character at the specified position
     */
	public char visit(OthelloBoard board,int row,int col);
}