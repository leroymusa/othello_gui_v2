package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LoadHandler implements EventHandler<ActionEvent> {
    private final Othello othello;
    private final Stage stage;
    private final GridPane boardGrid;
    private final GameStatusTracker turnText; // Game status tracker for turn
    private final TimerDisplay scoreText;    // Timer display for scores
    private final ListView<String> historyList;

    public LoadHandler(Othello othello, Stage stage, GridPane boardGrid, GameStatusTracker turnText, TimerDisplay scoreText, ListView<String> historyList) {
        this.othello = othello;
        this.stage = stage;
        this.boardGrid = boardGrid;
        this.turnText = turnText;
        this.scoreText = scoreText;
        this.historyList = historyList;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to load the game");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                othello.loadFromFile(selectedFile.getAbsolutePath());

                // Explicitly refresh the board
                updateBoard(boardGrid, othello);

                // Refresh UI elements
                updateTurnFeedback(othello, turnText, scoreText);
                updateHistory(othello, historyList);

                // Display success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Load Game");
                alert.setHeaderText(null);
                alert.setContentText("Game loaded successfully!");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to load the game: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    private void updateBoard(GridPane boardGrid, Othello othello) {
        for (javafx.scene.Node node : boardGrid.getChildren()) {
            if (node instanceof BoardSquare) {
                ((BoardSquare) node).update(othello);
            }
        }
    }

    private void updateTurnFeedback(Othello othello, GameStatusTracker turnText, TimerDisplay scoreText) {
        turnText.setText(othello.getWhosTurn() + "'s Turn");

        // Update the score display for both players
        String scoreTextValue = "X: " + othello.getCount('X') + " | O: " + othello.getCount('O');
        scoreText.setText(scoreTextValue);
    }

    private void updateHistory(Othello othello, ListView<String> historyList) {
        historyList.getItems().clear();
        historyList.getItems().addAll(othello.getHistory());
    }
}
