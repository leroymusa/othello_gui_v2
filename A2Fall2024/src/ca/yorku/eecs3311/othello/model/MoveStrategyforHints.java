package ca.yorku.eecs3311.othello.model;

/**
 * The interface for the Move strategies for hints.
 * Each concrete   MoveStrategy uses an algorithm to generate a Move.
 * 
 * @autor leroy musa
 *
 */
public interface MoveStrategyforHints {

	/**
	 * Will return a Move according to the strategy implemented
	 * @return the Move according to the strategy implemented
	 */
	public abstract Move getMove();
}