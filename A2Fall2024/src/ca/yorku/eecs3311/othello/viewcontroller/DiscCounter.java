package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.util.*;
import javafx.scene.control.TextField;

public class DiscCounter extends TextField implements Observer  {
	private char disc;
	private Othello othello;
			
	public DiscCounter(String text, char disc) {
		this.setText(text);
		this.disc = disc;
	}
	
	/**
	 * 
	 * Updates the view with the new disc values 
	 * @param Observable o
	 * 
	 * @autor leroy musa
	 */

	@Override
	public void update(Observable o) {
		othello = (Othello) o;
		this.setText(disc + " : " + othello.getCount(disc));
	}
}