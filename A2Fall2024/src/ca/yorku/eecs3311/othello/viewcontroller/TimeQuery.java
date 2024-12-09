
package ca.yorku.eecs3311.othello.viewcontroller;

import java.util.Optional;

import ca.yorku.eecs3311.othello.model.TimeTrackerSingleton;
import javafx.scene.control.TextInputDialog;

public class TimeQuery {
	public void run() {
		TextInputDialog dialogp1 = new TextInputDialog();
		dialogp1.setTitle("");
		dialogp1.setHeaderText("Please enter the time (in minutes) for Player 1");
		dialogp1.setGraphic(null);
		Optional<String> result = dialogp1.showAndWait();
		result.ifPresent(time -> {try {int p1time = Integer.parseInt(time);TimeTrackerSingleton.setMinutesP1(p1time);} catch(Exception e) {
			System.out.println("invalid, time must be an integer");};
		});
		TextInputDialog dialogp2 = new TextInputDialog();
		dialogp2.setTitle("");
		dialogp2.setHeaderText("Please enter the time (in minutes) for Player 2");
		dialogp2.setGraphic(null);
		Optional<String> result2 = dialogp2.showAndWait();
		result2.ifPresent(time -> {try {int p2time = Integer.parseInt(time);TimeTrackerSingleton.setMinutesP2(p2time);} catch(Exception e) {
			System.out.println("invalid, time must be an integer");}
		});
		TimeTrackerSingleton.setSecondsP1(0);
		TimeTrackerSingleton.setSecondsP2(0);
	}
}

