package ca.yorku.eecs3311.othello.model;

/**
 * A concrete implementation of MoveStrategy. 
 * 
 * This MoveStrategy algorithm returns the Move that would lead to the highest token count for this Player.
 * 
 * @autor leroy musa
 *
 */
public class GreedyStrategyforHint  implements   MoveStrategyforHints{
	private Othello othello;

	/**
	 * Constructs a new GreedyMoveStrategy
	 * @param othello  The Othello object we are using to run the game
	 */
	public GreedyStrategyforHint(Othello othello) {
		this.othello = othello;
	}
	
	/**
	 * @return the Move that would result in the highest increased token count
	 */
	public Move getMove() {
		if(this.othello.isGameOver())
			return new Move(-1,-1);
		
		Othello othelloCopy = this.othello.copy();
		Move bestMove = new Move(0, 0); 
		int bestMoveCount = othelloCopy.getCount(this.othello.getWhosTurn());
		
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				othelloCopy = this.othello.copy();
				if (othelloCopy.move(row, col) && othelloCopy.getCount(this.othello.getWhosTurn())>bestMoveCount) {
					bestMoveCount = othelloCopy.getCount(this.othello.getWhosTurn());
					bestMove = new Move(row, col);
				}
			}
		}
		return bestMove;
	}
}
