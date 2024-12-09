package ca.yorku.eecs3311.othello.model;

import ca.yorku.eecs3311.othello.viewcontroller.OthelloApplication;
import ca.yorku.eecs3311.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over. It also has two players.
 * 
 * @autor leroy musa
 *
 */
public class Othello extends Observable {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private OthelloBoard board = new OthelloBoard(Othello.DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;
	private boolean timeout = false;
	private char loser = OthelloBoard.EMPTY;
	private ArrayList<Othello> boards = new ArrayList<Othello>();
	public Player player1 = new PlayerHuman(this, OthelloBoard.P1);
	public Player player2 = new PlayerHuman(this, OthelloBoard.P2);
    private Stack<Othello> undoStack = new Stack<>();
    private Stack<Othello> redoStack = new Stack<>();
    private Stack<GameMemento> historyStack = new Stack<>();
    private List<String> moveHistory = new ArrayList<>();

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}
	

	
	/**
	 * Generates a list of all valid moves that can be made by the current player.
	 * 
	 * This method iterates over all board positions and checks if a move can be made
	 * at each position. For every valid move, it adds the corresponding 	Move
	 * object to the list of moves.
	 *
	 * @return an rrayList of 	Move objects representing all valid moves
	 *         that the current player can make
	 */
	public ArrayList<Move> allMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				Othello othelloCopy = this.copy();
				if (othelloCopy.move(row, col))
					moves.add(new Move(row, col));
			}
		}
		return moves;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return whether this row and column is a current Move that can be made
	 */
	public boolean inAllMoves(int row, int col) {
		for(Move move: this.allMoves()) {
			if(move.getRow() == row && move.getCol() == col)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		DiscGetterVisitor tokenVisitor = new DiscGetterVisitor();
		return tokenVisitor.visit(board, row, col);
	}
	/**
	 * 
	 * @return change number of moves committed
	 */
	public void incrementNumMoves() {
		numMoves++;
		
	}
	/**
	 * @param num
	 * @return change number of moves committed
	 */
	public void setWhosTurn(int num) {
		numMoves = num;
		
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return the image at position row, col.
	 */
	public ImageView getImage(int row, int col) {
	
	    InputStream input1 = OthelloApplication.class.getResourceAsStream("black.png");
	    InputStream input2 = OthelloApplication.class.getResourceAsStream("white.png"); 
	    
	    Image black = new Image(input1); 
	    Image white = new Image(input2); 

	    ImageView vblack = new ImageView(black); 
	    ImageView vwhite = new ImageView(white);
	    
	    ImageView result = null;
		
		if(this.getToken(row, col) == 'X') {
			result = vblack;
		} else if(this.getToken(row, col) == 'O') {
			result = vwhite;
		}
		
		return result;
	}

	
	/**
	 * Checks if it's the turn of an AI player and if so, triggers the AI to make its move.
	 * This method is called to automatically make a move for the AI player during the game.
	 * 
	 * If it is Player 1's turn and Player 1 is an AI (not a human), the AI move is retrieved
	 * using Player.getMove() and applied to the board.
	 * Similarly, if it is Player 2's turn and Player 2 is an AI, the AI's move is retrieved 
	 * and applied to the board.
	 * 
	 * The method ensures that the AI automatically takes its turn without user input.
	 */
	public void checkForAIMove() {
	    if (whosTurn == player1.getPlayer() && !(player1 instanceof PlayerHuman)) {
	        Move move = player1.getMove();
	        if (move != null) {
	            this.move(move.getRow(), move.getCol());
	        }
	    }
	    else if (whosTurn == player2.getPlayer() && !(player2 instanceof PlayerHuman)) {
	        Move move = player2.getMove();
	        if (move != null) {
	            this.move(move.getRow(), move.getCol());
	        }
	    }
	}



    
	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		MoveVisitor boardVisitor = new MoveVisitor();
		HasMoveVisitor hasMoveVisitor = new HasMoveVisitor();
		//Visitor that makes visits the board and attempts to make a move
		if (boardVisitor.visit(board, row, col, this.whosTurn)) {
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			char allowedMove = hasMoveVisitor.visit(board);
			if (allowedMove != OthelloBoard.BOTH)
				this.whosTurn = allowedMove;
			this.numMoves++;
			
	        String moveDescription = "Player " + (this.whosTurn == OthelloBoard.P1 ? "O" : "X") +
                    " moved to (" + row + ", " + col + ")";
	        saveToHistory(moveDescription);

			this.notifyObservers();
			boards.add(this.copy());
			checkForAIMove();
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Undo the last move of a human player
	 * The method restores the game to the previous state by popping the last state from the undo stack 
	 * and pushing the current state onto the redo stack. It also handles AI-specific undo behavior.
	 */
	public void undo() {
	    if (boards.size() > 1) {
	        redoStack.push(this.copy());
	        boards.remove(boards.size()-1);
	        Othello current = boards.get(boards.size()-1);
	        this.restoreState(current);
	        if (!moveHistory.isEmpty()) {
	            moveHistory.remove(moveHistory.size()-1);
	        }
	        if (whosTurn == player1.player && !(player1 instanceof PlayerHuman)) {
	            this.undo();//Auto-undo for non-human player 1
	        } else if (whosTurn == player2.player && !(player2 instanceof PlayerHuman)) {
	            this.undo();//Auto-undo for non-human player 2
	        }

	        //Notify observers to update the GUI
	        this.notifyObservers();}
	        else if (boards.size() == 1) {
	        	redoStack.push(this.copy());
			boards.remove(boards.size()-1);
			this.resetOthello();
			moveHistory.clear();
			this.notifyObservers();
		}
		else if (boards.size() != 0) {
			boards.remove(boards.size()-1);
			Othello current = boards.get(boards.size()-1);
			board = current.board;
			whosTurn = current.whosTurn;
			numMoves = current.numMoves;
			
			if (!moveHistory.isEmpty()) {
	            moveHistory.remove(moveHistory.size()-1);
	        }
			
			if (whosTurn == player1.getPlayer()) {
			    if (!(player1 instanceof PlayerHuman)) {
			        this.checkForAIMove();
			    }
			} else if (whosTurn == player2.getPlayer()) {
			    if (!(player2 instanceof PlayerHuman)) {
			        this.checkForAIMove();
			    }
			}
			this.notifyObservers();		
		}
	}

	
    /**
     * Redo the last undone move
     */
	public void redo() {
	    if (!redoStack.isEmpty()) {
	        boards.add(this.copy());
	        Othello redoneState = redoStack.pop();
	        this.restoreState(redoneState);
	        this.notifyObservers();
	    }
	}
    
    /**
     * Helper method to restore the state of the game
     * @param state The Othello state to restore
     */
    private void restoreState(Othello state) {
        this.board = state.board;
        this.whosTurn = state.whosTurn;
        this.numMoves = state.numMoves;
        this.timeout = state.timeout;
        this.loser = state.loser;
    }
    
    /**
     * Saves the current state of the game into a ameMemento.
     * This method captures the current board layout, the player whose turn it is, and the number of moves made.
     * 
     * @return a 	object containing the saved state of the game.
     */
    public GameMemento saveStateToMemento() {
        return new GameMemento(board.getBoardList(), whosTurn, numMoves);
    }

    
    /**
     * Restores the game state from a GameMemento.
     * This method updates the board, current turn, and move count using the information stored in the memento.
     * It also notifies observers of the updated game state.
     * 
     * @param memento 	the containing the saved state to restore.
     */
    public void restoreStateFromMemento(GameMemento memento) {
        char[][] savedBoard = memento.getBoardState();
        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                board.setCoordinate(row, col, savedBoard[row][col]);
            }
        }
        this.whosTurn = memento.getWhosTurn();
        this.numMoves = memento.getNumMoves();
        this.notifyObservers();
    }

    
    
    /**
     * Sets player1 as a specific type.
     * 
     * @param playerType the new player type for player1
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    
    /**
     * Sets player2 as a specific type.
     * 
     * @param playerType the new player type for player2
     */

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }




    /**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString()+"\n";
	}
	
	
	/**
	 * Saves the current game state to a file.
	 *
	 * @param filename the name of the file to save the game state to
	 * @throws IOException if an I/O error occurs during saving
	 */
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(filename))) {
            writer.write(whosTurn+"\n");
            writer.write(numMoves+"\n");
            for (int row=0;row<Othello.DIMENSION;row++) {
                writer.write(new String(board.getBoardList()[row])+"\n");
            }
        }
    }

    
    
    /**
     * Retrieves the move history of the game.
     *
     * @return a list of strings representing the move history
     */
    public List<String> getHistory() {
        return new ArrayList<>(moveHistory); //return a copy to avoid direct mod
    }

    
    
    /**
     * Loads a saved game state from a file.
     *
     * @param filename the name of the file to load the game state from
     * @throws IOException if an I/O error occurs during loading
     */
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            whosTurn = reader.readLine().charAt(0);
            numMoves = Integer.parseInt(reader.readLine());
            char[][] newBoard = new char[Othello.DIMENSION][Othello.DIMENSION];
            for (int row = 0; row < Othello.DIMENSION; row++) {
                newBoard[row] = reader.readLine().toCharArray();
            }
            for (int row = 0; row < Othello.DIMENSION; row++) {
                for (int col = 0; col < Othello.DIMENSION; col++) {
                    board.setCoordinate(row, col, newBoard[row][col]);
                }
            }
            notifyObservers();
        }
    }

    
    
    /**
     * Saves the current state of the game as a memento.
     *
     * @return a object representing the current game state
     */
    public GameMemento saveState() {
        char[][] boardCopy = new char[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            System.arraycopy(board.getBoardList()[i], 0, boardCopy[i], 0, DIMENSION);
        }
        return new GameMemento(boardCopy, whosTurn, numMoves);
    }

    
    /**
     * Restores the game state from a given memento.
     *
     * @param memento the memento containing the saved game state
     */
    public void restoreState(GameMemento memento) {
        char[][] boardState = memento.getBoardState();
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                board.setCoordinate(row, col, boardState[row][col]);
            }
        }
        whosTurn = memento.getWhosTurn();
        numMoves = memento.getNumMoves();
        notifyObservers();
    }

    
    /**
     * Saves a description of a move to the history.
     *
     * @param moveDescription a string representing the description of the move
     */
    public void saveToHistory(String moveDescription) {
        moveHistory.add(moveDescription);
    }

    
    /**
     * Undoes the last move in the history by restoring the previous state.
     */
    public void undoFromHistory() {
        if (!historyStack.isEmpty()) {
            restoreState(historyStack.pop());
        }
    }
    
    
    
    /**
     * Retrieves the total number of moves made in the game.
     *
     * @return the number of moves made
     */
    public int getNumMoves() {
        return this.numMoves;
    }

    
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		DiscCountVisitor tokenCounter = new DiscCountVisitor();
		return tokenCounter.visit(board, player);
	}
	
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount4x4(char player) {
		StartConfig_DiscCountVisitor fourBYfourcounter = new StartConfig_DiscCountVisitor();
		return fourBYfourcounter.visit(board, player);
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if (!this.isGameOver()) 
			return OthelloBoard.EMPTY;
		if (this.getCount(OthelloBoard.P1) > this.getCount(OthelloBoard.P2))
			return OthelloBoard.P1;
		if (this.getCount(OthelloBoard.P1) < this.getCount(OthelloBoard.P2))
			return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return this.whosTurn == OthelloBoard.EMPTY;
	}
	/**
	 * Alert the game that time has run out for one of the players
	 */
	public void noTime() {
		timeout = true;
		if (this.whosTurn == OthelloBoard.P1) {
			loser = OthelloBoard.P1;
		}
		else if (this.whosTurn == OthelloBoard.P2) {
			loser = OthelloBoard.P2;
		}
		whosTurn = OthelloBoard.EMPTY;
		this.notifyObservers();
	
	}
	/**
	 * 
	 * @return the player whose time has run out
	 */
	public char getLoser() {
		return loser;
	}
	/**
	 * 
	 * @return if time has run out for one of the players
	 */
	public boolean checkTime() {
		return timeout;
	}	

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o = new Othello();
		BoardVisitor copy = new BoardVisitor();
		o.board = copy.visit(board);
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}
	
	/**
	 * Fully resets the game - Used by our restart button
	 * 
	 *
	 */
	
	public void resetOthello() {
		board = new OthelloBoard(Othello.DIMENSION);
		whosTurn = OthelloBoard.P1;
		numMoves = 0;
		loser = OthelloBoard.EMPTY;
		boards = new ArrayList<Othello>();
		player1 = new PlayerHuman(this, OthelloBoard.P1);
		player2 = new PlayerHuman(this, OthelloBoard.P2);
		
		this.notifyObservers();
	}

	
	
	
	/**
	 * Performs an undo operation specifically tailored for AI moves.
	 * Pushes the current state to the redo stack and restores the previous game state.
	 * If the undo operation results in an AI player's turn, it will trigger the AI to make a move.
	 * <p>
	 * Note: The observer notification is commented out to prevent app freezing
	 * when AI makes rapid undo/redo operations.
	 */
	public void aiUndo() {
	    if (!undoStack.isEmpty()) {
	        redoStack.push(this.copy());
	        Othello previousState = undoStack.pop();
	        this.restoreState(previousState);
	        if (whosTurn == player1.getPlayer() && !(player1 instanceof PlayerHuman)) {
	            checkForAIMove();
	        } else if (whosTurn == player2.getPlayer() && !(player2 instanceof PlayerHuman)) {
	            checkForAIMove();
	        }
	        //Commented out to prevent freeze
	        //So an Undo op will just take it to start pos
	        //notifyObservers();
	    }
	}

	/**
	 * Performs a redo operation specifically tailored for AI moves.
	 * Restores the next game state from the redo stack and pushes the current state to the undo stack.
	 * If the redo operation results in an AI player's turn, it will trigger the AI to make a move.
	 */
	public void aiRedo() {
	    if (!redoStack.isEmpty()) {
	        Othello nextState = redoStack.pop();
	        undoStack.push(this.copy());
	        this.restoreState(nextState);

	        if (whosTurn == player1.getPlayer() && !(player1 instanceof PlayerHuman)) {
	            checkForAIMove();
	        } else if (whosTurn == player2.getPlayer() && !(player2 instanceof PlayerHuman)) {
	            checkForAIMove();
	        }
	        //notifyObservers(); 
	    }
	}
	
	
	
	public Player getPlayer1() {
		// TODO Auto-generated method stub
		return this.player1;
	}
	
	public Player getPlayer2() {
	    return this.player2;
	}
}



