package ca.yorku.eecs3311.othello.model;

public class GameMemento {
    private final char[][] boardState;
    private final char whosTurn;
    private final int numMoves;

    public GameMemento(char[][] boardState, char whosTurn, int numMoves) {
        // Deep copy of the board state
        this.boardState = new char[boardState.length][boardState.length];
        for (int i = 0; i < boardState.length; i++) {
            System.arraycopy(boardState[i], 0, this.boardState[i], 0, boardState[i].length);
        }
        this.whosTurn = whosTurn;
        this.numMoves = numMoves;
    }

    public char[][] getBoardState() {
        //return a deep copy to avoid mutability issues
        char[][] copy = new char[boardState.length][boardState.length];
        for (int i = 0; i < boardState.length; i++) {
            System.arraycopy(this.boardState[i], 0, copy[i], 0, boardState[i].length);
        }
        return copy;
    }

    public char getWhosTurn() {
        return whosTurn;
    }

    public int getNumMoves() {
        return numMoves;
    }
}
