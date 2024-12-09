package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Handles the user clicking on a new opponent Button by changing player strategies.
 * 
 * @autor Leroy Musa
 */
public class OpponentEventHandler implements EventHandler<ActionEvent> {
    private Othello othello;

    public OpponentEventHandler(Othello othello) {
        this.othello = othello;
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        String mode = button.getText();

        if (mode.equals("Human vs. Human")) {
            othello.setPlayer1(new PlayerHuman(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerHuman(othello, OthelloBoard.P2));
        } else if (mode.equals("Human vs. Random")) {
            othello.setPlayer1(new PlayerHuman(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerRandom(othello, OthelloBoard.P2));
        } else if (mode.equals("Human vs. Greedy")) {
            othello.setPlayer1(new PlayerHuman(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerGreedy(othello, OthelloBoard.P2));
        } else if (mode.equals("Random vs. Random")) {
            othello.setPlayer1(new PlayerRandom(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerRandom(othello, OthelloBoard.P2));
        } else if (mode.equals("Random vs. Greedy")) {
            othello.setPlayer1(new PlayerRandom(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerGreedy(othello, OthelloBoard.P2));
        } else if (mode.equals("Greedy vs. Greedy")) {
            othello.setPlayer1(new PlayerGreedy(othello, OthelloBoard.P1));
            othello.setPlayer2(new PlayerGreedy(othello, OthelloBoard.P2));
        }
        
        othello.checkForAIMove();
    }
}
