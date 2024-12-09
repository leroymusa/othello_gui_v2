package ca.yorku.eecs3311.othello.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import ca.yorku.eecs3311.util.*;
import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class TimeTrackerSingleton extends Observable implements Observer {
	private static Othello othello;
	private static int minutesP1=5;
	private static int secondsP1=0;
	private static int minutesP2=5;
	private static int secondsP2=0;
	private char whosTurn='X';
	private static TimeTrackerSingleton instance;
	
	Timeline countdownP1=new Timeline();
	Timeline countdownP2=new Timeline();
	
	private TimeTrackerSingleton(Othello o) {
		countdownP1.setCycleCount(Timeline.INDEFINITE);
		countdownP2.setCycleCount(Timeline.INDEFINITE);
		
		@SuppressWarnings("unchecked")
		KeyFrame secondsFrameP1=new KeyFrame(Duration.seconds(1), timeEventHandlerP1);
		@SuppressWarnings("unchecked")
		KeyFrame secondsFrameP2=new KeyFrame(Duration.seconds(1), timeEventHandlerP2);
		
		countdownP1.getKeyFrames().add(secondsFrameP1);
		countdownP2.getKeyFrames().add(secondsFrameP2);
	}
	public static synchronized TimeTrackerSingleton getInstance(Othello o) {
		othello = o;
		if (instance==null) {
			instance=new TimeTrackerSingleton(othello);
		}
		return instance;
		
	}
	
	@SuppressWarnings("rawtypes")
	EventHandler timeEventHandlerP1 = new EventHandler() {
		
		public void handle(Event event) {
			setSecondsP1(getSecondsP1()-1);
			notifyObservers();
			if (getSecondsP1()<=0) {
				setMinutesP1(getMinutesP1()-1);
				setSecondsP1(60);
			}
			if (getMinutesP1()<0) {
				countdownP1.stop();
				othello.noTime();
			}		
		}
	};
	
	@SuppressWarnings("rawtypes")
	EventHandler timeEventHandlerP2 = new EventHandler() {
		
		@Override
		public void handle(Event event) {
			setSecondsP2(getSecondsP2()-1);
			notifyObservers();
			if (getSecondsP2()<=0) {
				setMinutesP2(getMinutesP2()-1);
				setSecondsP2(60);
			}
			if (getMinutesP2()<0) {
				countdownP2.stop();
			}
		}

	};
	
	/**
	 * 
	 * @return the minutes for the player whose timer is running
	 */
	public int getMinutes() {
		if (countdownP1.getStatus()==Animation.Status.RUNNING) {
			return getMinutesP1(); }
		else {
			return getMinutesP2();
		}
	}
	/**
	 * 
	 * @return the seconds for the player whose timer is running
	 */
	public int getSeconds() {
		if (countdownP1.getStatus()==Animation.Status.RUNNING) {
			return getSecondsP1();
		}
		else {
			return getSecondsP2();
		}
	}
	/**
	 * starts the timer
	 */
	public void startTimer() {
		countdownP1.play();
	}
	@Override
	public void update(Observable o) {
		
		othello = (Othello) o;
		if (othello.isGameOver()) {
			countdownP1.stop();
			countdownP2.stop();
		}
		else if (othello.getWhosTurn()!=whosTurn) {
			whosTurn=othello.getWhosTurn();
			if (whosTurn=='X') {
				countdownP2.pause();
				countdownP1.play();
			}
			else {
				countdownP1.pause();
				countdownP2.play();
			}
		}
	}
	public static int getMinutesP1() {
		return minutesP1;
	}
	public static void setMinutesP1(int minutesP1) {
		TimeTrackerSingleton.minutesP1=minutesP1;
	}
	public static int getMinutesP2() {
		return minutesP2;
	}
	public static void setMinutesP2(int minutesP2) {
		TimeTrackerSingleton.minutesP2=minutesP2;
	}
	public static int getSecondsP1() {
		return secondsP1;
	}
	public static void setSecondsP1(int secondsP1) {
		TimeTrackerSingleton.secondsP1=secondsP1;
	}
	public static int getSecondsP2() {
		return secondsP2;
	}
	public static void setSecondsP2(int secondsP2) {
		TimeTrackerSingleton.secondsP2=secondsP2;
	}	
}