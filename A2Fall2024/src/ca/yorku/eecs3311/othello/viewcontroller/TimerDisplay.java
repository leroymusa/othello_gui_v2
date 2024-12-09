package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.TimeTrackerSingleton;
import ca.yorku.eecs3311.util.*;
import javafx.scene.control.TextField;

class TimerDisplay extends TextField implements Observer{
	private int minute;
	private int second;
	private TimeTrackerSingleton timer;
	
	public TimerDisplay(TimeTrackerSingleton tracker) {
		this.setText(minute + ":"+ second);
		this.timer = tracker;
	}

	@Override
	/**
	 * updates the timer display with the current time
	 */
	public void update(Observable o) {
		this.setText(timer.getMinutes() + ":" + timer.getSeconds() );
	}
}

