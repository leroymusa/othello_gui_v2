package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.util.*;
import javafx.scene.control.TextField;

public class GameStatusTracker extends TextField implements Observer{
	private Othello othello;
	
	public GameStatusTracker(String text) {
		this.setText(text);
	}
	
	/**
	 * 
	 * Updates the status of whosTurn in the game and who won the game
	 * 
	 * @param Observable o
	 * 
	 */
	@Override
	public void update(Observable o) {
		othello = (Othello) o;
		if (othello.checkTime()) {
			this.setText("No time left!" + " " + othello.getLoser() + " loses.");
		}
		else if (!othello.isGameOver()) {
			this.setText(othello.getWhosTurn()+"'s" + " Turn");
		} else {
			this.setText("Winner: " + othello.getWinner());
		}
	}
}

