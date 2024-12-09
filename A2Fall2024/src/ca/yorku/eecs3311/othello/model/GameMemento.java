package ca.yorku.eecs3311.othello.model;


/**
 * The GameMemento class represents a snapshot of the game state in the Memento design pattern.
 * It stores the board state, the current player's turn, and the number of moves made.
 */
public class GameMemento {
    private final char[][] boardState;
    private final char whosTurn;
    private final int numMoves;

    
    /**
     * Constructs a GameMemento with the given game state details.
     * A deep copy of the board state is created to ensure immutability.
     *
     * @param boardState the current state of the game board
     * @param whosTurn   the player whose turn it is ('X' or 'O')
     * @param numMoves   the number of moves made so far in the game
     */
    public GameMemento(char[][] boardState, char whosTurn, int numMoves) {
        //deep copy of the board state
        this.boardState = new char[boardState.length][boardState.length];
        for (int i = 0; i < boardState.length; i++) {
            System.arraycopy(boardState[i], 0, this.boardState[i], 0, boardState[i].length);
        }
        this.whosTurn = whosTurn;
        this.numMoves = numMoves;
    }

    
    /**
     * Returns a deep copy of the board state to prevent external modification.
     *
     * @return a deep copy of the board state
     */
    public char[][] getBoardState() {
        //return a deep copy to avoid mutability issues
        char[][] copy = new char[boardState.length][boardState.length];
        for (int i = 0; i < boardState.length; i++) {
            System.arraycopy(this.boardState[i], 0, copy[i], 0, boardState[i].length);
        }
        return copy;
    }

    
    /**
     * Returns the player whose turn it is.
     *
     * @return the player whose turn it is ('X' or 'O')
     */
    public char getWhosTurn() {
        return whosTurn;
    }

    
    /**
     * Returns the number of moves made so far in the game.
     *
     * @return the number of moves made
     */
    public int getNumMoves() {
        return numMoves;
    }
}
