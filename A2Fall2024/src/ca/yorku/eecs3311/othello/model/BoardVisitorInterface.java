/**
 * Interface for defining operations on an Othello board.
 * Classes implementing this interface can specify how to process a board.
 */
package ca.yorku.eecs3311.othello.model;


/**
 * Visits and processes the given Othello board.
 *
 * @param board the Othello board to process
 * @return the processed Othello board
 */

public interface BoardVisitorInterface {
	public OthelloBoard visit(OthelloBoard board);
}