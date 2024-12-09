package ca.yorku.eecs3311.othello.model;

import ca.yorku.eecs3311.util.Observable;

/**
 * A Player has an Othello game and a player token. It also knows what MoveStrategy it 
 * is using and the String representation of that MoveStrategy. 
 * 
 * @autor leroy musa
 *
 */
public abstract class Player extends Observable {
    protected Othello othello;
    protected char player;

    public Player(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    public char getPlayer() {
        return this.player;
    }

    public abstract Move getMove();

    public String getStrategyName() {
        if (this instanceof PlayerGreedy) return "Greedy";
        if (this instanceof PlayerRandom) return "Random";
        if (this instanceof PlayerHuman) return "Human";
        return "Unknown";
    }

    /**
     * Sets the strategy for the player and notifies observers.
     */
    public void setStrategy(MoveStrategyforHints strategy) {
        // Updating the strategy for the player (whether Human, Random, or Greedy)
        if (strategy == null) {
            this.notifyObservers(); // Notify if the strategy is null
        } else {
            // Attach observer here if required
            this.notifyObservers();
        }
    }
}
