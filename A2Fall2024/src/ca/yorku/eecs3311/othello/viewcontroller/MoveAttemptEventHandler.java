package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.PlayerHuman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * 
 * A MoveAttemptEventHandler handles the user trying to make a move by clicking on a BoardSquare
 * 
 * @autor leroy musa
 *
 */
public class MoveAttemptEventHandler implements EventHandler<ActionEvent> {
    private final Othello othello;
    private final ListView<String> historyList;

    /**
     * Constructs a MoveAttemptEventHandler to handle the user clicking on a BoardSquare
     * 
     * @param othello the Othello game
     */
    public MoveAttemptEventHandler(Othello othello,ListView<String> historyList) {
        this.othello=othello;
        this.historyList=historyList;
    }

    /**
     * Tries to make a move with the BoardSquare the user clicked on
     */
    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row=GridPane.getRowIndex(button);
        int col=GridPane.getColumnIndex(button);

        //Check if the current player is human based on its class type
        if ((this.othello.getWhosTurn() == this.othello.getPlayer1().getPlayer() && this.othello.getPlayer1() instanceof PlayerHuman)
                || (this.othello.getWhosTurn() == this.othello.getPlayer2().getPlayer() && this.othello.getPlayer2() instanceof PlayerHuman)) {
            if (othello.move(row, col)) { //Make the move and check success
                updateHistory();
            }
        }
    }

    /**
     * Updates the move history in the ListView.
     */
    private void updateHistory() {
        historyList.getItems().clear();
        historyList.getItems().addAll(othello.getHistory());
    }
}