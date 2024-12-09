package ca.yorku.eecs3311.othello.viewcontroller;

import java.util.List;

import ca.yorku.eecs3311.othello.model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * An OthelloApplication creates and runs an Othello game. 
 * It attaches and creates the Models, Views, and Controllers to run the game.
 *
 */
public class OthelloApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		// MODEL
		Othello othello=new Othello();
		Hints hints = new Hints(othello);
		othello.attach(hints); // MODEL->MODEL
		TimeTrackerSingleton timer = TimeTrackerSingleton.getInstance(othello);
		
		
		// VIEW
		DiscCounter p1Count = new DiscCounter(OthelloBoard.P1 + " : "+othello.getCount(OthelloBoard.P1), OthelloBoard.P1);
		DiscCounter p2Count = new DiscCounter(OthelloBoard.P2 + " : "+othello.getCount(OthelloBoard.P2), OthelloBoard.P2);
		p1Count.setEditable(false);
		p2Count.setEditable(false);
		GameStatusTracker status = new GameStatusTracker(OthelloBoard.P1 + "'s Turn");
		status.setEditable(false);
		MoveStrategyTracker currentPlayerTypeP1 = new MoveStrategyTracker(othello.getPlayer1());
		MoveStrategyTracker currentPlayerTypeP2 = new MoveStrategyTracker(othello.getPlayer2());
		currentPlayerTypeP1.setEditable(false);
		currentPlayerTypeP2.setEditable(false);
		TimerDisplay timedisplay = new TimerDisplay(timer);
		timedisplay.setEditable(false);

		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Hint Menu");
		HintMenuItem randomMenuItem = new HintMenuItem(hints, "random");
		menu.getItems().add(randomMenuItem);
		HintMenuItem greedyMenuItem = new HintMenuItem(hints, "greedy");
		menu.getItems().add(greedyMenuItem);
		menuBar.getMenus().add(menu);
		
		ListView<String> historyList = new ListView<>();
		historyList.setPrefSize(200, 400);
		Button hVsHuman = new Button("Human vs. Human");
		hVsHuman.setPrefSize(190, 20);
		Button hVsRandom = new Button("Human vs. Random");
		hVsRandom.setPrefSize(190, 20);
		Button hVsGreedy = new Button("Human vs. Greedy");
		hVsGreedy.setPrefSize(190, 20);

		Button randomVsRandom = new Button("Random vs. Random");
		Button randomVsGreedy = new Button("Random vs. Greedy");
		Button greedyVsGreedy = new Button("Greedy vs. Greedy");
		randomVsRandom.setPrefSize(190, 20);
		randomVsGreedy.setPrefSize(190, 20);
		greedyVsGreedy.setPrefSize(190, 20);
		Button play = new Button("Play");
		play.setBackground(new Background(new BackgroundFill(Color.web("#4BAAAA"), new CornerRadii(2), null)));
		play.setPrefSize(190, 20);

		Button restart = new Button("Restart");
		Button undo = new Button("Undo");
		Button redo = new Button("Redo");
		Button saveButton = new Button("Save Game");
		Button loadButton = new Button("Load Game");
		Button getHistoryButton = new Button("Get History");
		HBox box = new HBox();
		box.setPadding(new Insets(5, 5, 5, 5));
		box.getChildren().addAll(restart, undo, redo, saveButton, loadButton, getHistoryButton);
		
		
		getHistoryButton.setOnAction(e -> {
		    System.out.println("Get History button clicked!");
		    historyList.getItems().clear();
		    List<String> history = othello.getHistory();
		    if (history.isEmpty()) {
		        historyList.getItems().add("No moves yet!");
		    } else {
		        historyList.getItems().addAll(history);
		    }
		    System.out.println("History retrieved successfully:");
		    history.forEach(System.out::println);
		});

		
		GridPane grid = new GridPane();
		grid.setHgap(5); 
		grid.setVgap(5); 
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(p1Count, 9, 1);
		grid.add(p2Count, 10, 1);
		grid.add(status, 9, 2, 2, 1);
		grid.add(timedisplay, 9, 3, 2, 1);
		grid.add(box, 9, 4);
		grid.add(play, 10, 4);
		grid.add(hVsHuman, 9, 5);
		grid.add(hVsRandom, 9, 6);
		grid.add(hVsGreedy, 10, 5);
		grid.add(currentPlayerTypeP1, 9, 0);
		grid.add(currentPlayerTypeP2, 10, 0);
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// VIEW
				BoardSquare boardSquare = new BoardSquare(othello,row,col,hints);
				// CONTROLLER
				MoveAttemptEventHandler moveToClick = new MoveAttemptEventHandler(othello, historyList);
				// VIEW->CONTROLLER hookup
				boardSquare.addEventHandler(ActionEvent.ACTION, moveToClick);
				
				grid.add(boardSquare, col, row);
				boardSquare.setPrefSize(35, 35);
				
				// MODEL->VIEW hookup
				othello.attach(boardSquare);
				hints.greedyHint.attach(boardSquare);
				hints.randomHint.attach(boardSquare);
			}
		}
		
		
		// MODEL->VIEW hookup
		othello.attach(p1Count);
		othello.attach(p2Count);
		othello.attach(status);
		othello.attach(timer);
		othello.player1.attach(currentPlayerTypeP1);
		othello.player2.attach(currentPlayerTypeP2);
		hints.randomHint.attach(randomMenuItem);
		hints.greedyHint.attach(greedyMenuItem);
		timer.attach(timedisplay);
		
		
		// CONTROLLERS:
		// CONTROLLER->MODEL hookup
		OpponentEventHandler humanOpponentHandler = new OpponentEventHandler(othello);
		OpponentEventHandler randomOpponentHandler = new OpponentEventHandler(othello);
		OpponentEventHandler greedyOpponentHandler = new OpponentEventHandler(othello);
		HintEventHandler handleRandomHint = new HintEventHandler(hints);
		HintEventHandler handleGreedyHint = new HintEventHandler(hints);
		RestartEventHandler restartHandler = new RestartEventHandler(othello, timer);
		UndoInvoker undoInvoker = new UndoInvoker(othello,historyList);
		PlayEventHandler playHandler = new PlayEventHandler(timer);
		RedoInvoker redoInvoker = new RedoInvoker(othello);
		SaveHandler saveHandler = new SaveHandler(othello);
		LoadHandler loadHandler = new LoadHandler(othello, stage, grid, status, timedisplay, historyList);
		randomVsRandom.addEventHandler(ActionEvent.ACTION, new OpponentEventHandler(othello));
		randomVsGreedy.addEventHandler(ActionEvent.ACTION, new OpponentEventHandler(othello));
		greedyVsGreedy.addEventHandler(ActionEvent.ACTION, new OpponentEventHandler(othello));

		// Add buttons to the grid
		grid.add(randomVsRandom, 9, 7);
		grid.add(randomVsGreedy, 10, 7);
		grid.add(greedyVsGreedy, 9, 8);
		// VIEW->CONTROLLER hookup
		randomMenuItem.addEventHandler(ActionEvent.ACTION, handleRandomHint);
		greedyMenuItem.addEventHandler(ActionEvent.ACTION, handleGreedyHint);
		hVsHuman.addEventHandler(ActionEvent.ACTION, humanOpponentHandler);
		hVsRandom.addEventHandler(ActionEvent.ACTION, randomOpponentHandler);
		hVsGreedy.addEventHandler(ActionEvent.ACTION, greedyOpponentHandler);
		restart.addEventHandler(ActionEvent.ACTION, restartHandler);
		undo.addEventHandler(ActionEvent.ACTION, undoInvoker);
		play.addEventHandler(ActionEvent.ACTION, playHandler);
		redo.addEventHandler(ActionEvent.ACTION, redoInvoker);
		saveButton.addEventHandler(ActionEvent.ACTION, saveHandler);
		loadButton.addEventHandler(ActionEvent.ACTION, loadHandler);
		
		// SCENE
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(grid);
		
		Scene scene = new Scene(root);
		stage.setTitle("Othello");
		stage.setScene(scene);

		// LAUNCH THE GUI
		stage.show();
		root.requestFocus();
	}
	
    
	public static void main(String[] args) {
		new OthelloApplication();
		launch(args);
		
	}
}
