/**
 * Interface for defining a visitor that counts discs on an Othello board.
 * Classes implementing this interface can specify how to count discs for a given player.
 */


package ca.yorku.eecs3311.othello.model;


/**
 * Visits the Othello board and counts the discs for the specified player.
 *
 * @param board  the Othello board to process
 * @param player the player whose discs are to be counted
 * @return the number of discs belonging to the specified player
 */
public interface DiscCountVisitorInterface {
	public int visit(OthelloBoard board, char player);
}