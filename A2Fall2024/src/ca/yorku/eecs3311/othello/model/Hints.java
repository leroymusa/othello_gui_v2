package ca.yorku.eecs3311.othello.model;

import ca.yorku.eecs3311.util.*;

/**
 * Hints create the hints for the game. 
 * Each Hint can be turned on or off, and if on, each Hint updates each turn. 
 * 
 * @autor leroy musa
 *
 */
public class Hints implements Observer {
	private Othello othello;
	public Hint randomHint;
	public Hint greedyHint;

	/**
	 * Constructs the hints and stores whether they are on or not. 
	 */
	public Hints(Othello othello) {
		this.othello = othello;
		this.randomHint = new Hint(new RandomStrategyforHint(this.othello));
		this.greedyHint = new Hint(new GreedyStrategyforHint(this.othello));
	}

	/**
	 * updates each Hint each turn, if on
	 */
	@Override
	public void update(Observable o) {
		if (!othello.isGameOver()) {		
			if(this.randomHint.isHintOn())
				this.randomHint.setHint();
			if(this.greedyHint.isHintOn())
				this.greedyHint.setHint();
		}
	}
}
