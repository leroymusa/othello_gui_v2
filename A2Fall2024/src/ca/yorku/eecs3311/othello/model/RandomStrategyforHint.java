package ca.yorku.eecs3311.othello.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * A concrete implementation of MoveStrategy. 
 * 
 * This MoveStrategy algorithm returns a random Move that the Player can make this turn.
 * 
 * @autor leroy musa
 *
 */
public class RandomStrategyforHint implements MoveStrategyforHints{
	private Othello othello;
	private Random rand = new Random();

	/**
	 * Constructs a new RandomMoveStrategy
	 * @param othello  The Othello object we are using to run the game
	 */
	public RandomStrategyforHint(Othello othello) {
		this.othello = othello;
	}
	
	/**
	 * @return a random Move that the Player can make this turn
	 */
	public Move getMove() {
		if(this.othello.isGameOver())
			return new Move(-1,-1);
		
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				Othello othelloCopy = othello.copy();
				if (othelloCopy.move(row, col))
					moves.add(new Move(row, col));
			}
		}
		return moves.get(this.rand.nextInt(moves.size()));
	}

}
