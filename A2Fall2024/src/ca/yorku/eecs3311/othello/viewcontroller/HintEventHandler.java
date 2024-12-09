package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Hints;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * An implementation of EventHandler that knows what HintMenuItem was clicked
 * and toggles that Hint to turn on or off.
 * 
 * @autor leroy musa
 *
 */
public class HintEventHandler implements EventHandler<ActionEvent> {
	private Hints hints;
	
	/**
	 * Constructs a HintEventHandler to handle the user turning on and off the HintMenuItems
	 * 
	 * @param hints  The Hints for this game
	 */
	public HintEventHandler(Hints hints) {
		this.hints = hints;
	}

	/**
	 * Turns the HintMenuItem corresponding to the click on or off.
	 */
	public void handle(ActionEvent event) {		
		HintMenuItem menuitem=(HintMenuItem) event.getSource();
		if(menuitem.getHintType()=="greedy")
			this.hints.greedyHint.toggleHint();
		else if(menuitem.getHintType()=="random")
			this.hints.randomHint.toggleHint();
	}
}
