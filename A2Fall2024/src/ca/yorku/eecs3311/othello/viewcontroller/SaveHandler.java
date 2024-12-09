package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class SaveHandler implements EventHandler<ActionEvent> {
    private final Othello othello;

    public SaveHandler(Othello othello) {
        this.othello = othello;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            othello.saveToFile("othello_save.txt");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }
}
