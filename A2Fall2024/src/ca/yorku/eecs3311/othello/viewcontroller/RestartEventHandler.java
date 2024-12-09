package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.TimeTrackerSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 
 * Implmentation of event handler that deals with restarting the game upon clicking the restart button
 * 
 * @autor leroy musa
 */

public class RestartEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	private TimeTrackerSingleton timer;
	
	/**
	 * 
	 * Counstructs a hint event handler
	 * @param Othello othello
	 * @param TimeTrackerSingleton
	 * 
	 * @autor leroy musa
	 */
	
	public RestartEventHandler(Othello othello, TimeTrackerSingleton timer) {
		this.othello = othello;
		this.timer = timer;	
	}

	@Override
	public void handle(ActionEvent event) {
		TimeQuery query = new TimeQuery();
		this.othello.resetOthello();
		query.run();
		this.timer.startTimer();
	}

}
