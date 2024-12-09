package ca.yorku.eecs3311.othello.model;


/**
 * Interface for defining a visitor that checks for available moves on an Othello board.
 * Classes implementing this interface can specify how to determine if a move is available.
 */
public interface HasMoveVisitorInterface {
	
	
    /**
     * Visits the Othello board to check for the availability of moves.
     *
     * @param board the Othello board to inspect
     * @return the character representing the player ('X' or 'O') who has a valid move,
     *         or a special value indicating no moves are available
     */
	public char visit(OthelloBoard board);
}
