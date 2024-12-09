package ca.yorku.eecs3311.othello.viewcontroller;
import ca.yorku.eecs3311.othello.model.*;

import java.util.List;
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

public class OthelloApplication extends Application {
	// REMEMBER: To run this in the lab put 
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well.
	
	@Override
	public void start(Stage stage) throws Exception {
		// Create and hook up the Model, View and the controller

		// MODEL
		Othello othello=new Othello();
		Hints hints = new Hints(othello);
		// MODEL->MODEL
		othello.attach(hints);
		TimeTrackerSingleton timer = TimeTrackerSingleton.getInstance(othello);
		
		
		// VIEW
		DiscCounter p1Count =new DiscCounter(OthelloBoard.P1+" : "+othello.getCount(OthelloBoard.P1),OthelloBoard.P1);
		DiscCounter p2Count= new DiscCounter(OthelloBoard.P2+" : "+othello.getCount(OthelloBoard.P2),OthelloBoard.P2);
		p1Count.setEditable(false);
		p2Count.setEditable(false);
		GameStatusTracker status= new GameStatusTracker(OthelloBoard.P1 +"'s Turn");
		status.setEditable(false);
		MoveStrategyTracker currentPlayerTypeP1=new MoveStrategyTracker(othello.getPlayer1());
		MoveStrategyTracker currentPlayerTypeP2=new MoveStrategyTracker(othello.getPlayer2());
		currentPlayerTypeP1.setEditable(false);
		currentPlayerTypeP2.setEditable(false);
		TimerDisplay timedisplay = new TimerDisplay(timer);
		timedisplay.setEditable(false);

		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Hint Menu");
		HintMenuItem randomMenuItem=new HintMenuItem(hints, "random");
		menu.getItems().add(randomMenuItem);
		HintMenuItem greedyMenuItem = new HintMenuItem(hints, "greedy");
		menu.getItems().add(greedyMenuItem);
		menuBar.getMenus().add(menu);
		
		ListView<String> historyList = new ListView<>();
		historyList.setPrefSize(200, 400);
		Button HumanVSHuman = new Button("Human vs. Human");
		HumanVSHuman.setPrefSize(190, 20);
		Button HumanVSRandom = new Button("Human vs. Random");
		HumanVSRandom.setPrefSize(190, 20);
		Button HumanVSGreedy = new Button("Human vs. Greedy");
		HumanVSGreedy.setPrefSize(190, 20);

		Button RandomVSRandom=new Button("Random vs. Random");
		Button RandomVSGreedy=new Button("Random vs. Greedy");
		Button GreedyVSGreedy=new Button("Greedy vs. Greedy");
		RandomVSRandom.setPrefSize(190, 20);
		RandomVSGreedy.setPrefSize(190, 20);
		GreedyVSGreedy.setPrefSize(190, 20);
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
		grid.add(HumanVSHuman, 9, 5);
		grid.add(HumanVSRandom, 9, 6);
		grid.add(HumanVSGreedy, 10, 5);
		grid.add(currentPlayerTypeP1, 9, 0);
		grid.add(currentPlayerTypeP2, 10, 0);
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// VIEW
				Tile boardSquare = new Tile(othello,row,col,hints);
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
		LoadHandler loadHandler = new LoadHandler(othello,stage,grid,status,timedisplay,historyList);
		RandomVSRandom.addEventHandler(ActionEvent.ACTION,new OpponentEventHandler(othello));
		RandomVSGreedy.addEventHandler(ActionEvent.ACTION,new OpponentEventHandler(othello));
		GreedyVSGreedy.addEventHandler(ActionEvent.ACTION,new OpponentEventHandler(othello));

		// Add buttons to the grid
		grid.add(RandomVSRandom,9,7);
		grid.add(RandomVSGreedy,10,7);
		grid.add(GreedyVSGreedy,9,8);
		// VIEW->CONTROLLER hookup
		randomMenuItem.addEventHandler(ActionEvent.ACTION, handleRandomHint);
		greedyMenuItem.addEventHandler(ActionEvent.ACTION, handleGreedyHint);
		HumanVSHuman.addEventHandler(ActionEvent.ACTION, humanOpponentHandler);
		HumanVSRandom.addEventHandler(ActionEvent.ACTION, randomOpponentHandler);
		HumanVSGreedy.addEventHandler(ActionEvent.ACTION, greedyOpponentHandler);
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
