package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * A RedoInvoker handles a redo button click.
 */
public class RedoInvoker implements EventHandler<ActionEvent> {
    private Othello game;

    /**
     * Constructs a RedoInvoker
     * @param othello this Othello game
     */
    public RedoInvoker(Othello othello) {
        this.game = othello;
    }

    /**
     * Handles a redo button click, creates a redo command object and calls execute on it.
     */
    @Override
    public void handle(ActionEvent event) {
//    	  if (game.getWhosTurn() == game.getPlayer1().getPlayer() && !(game.getPlayer1() instanceof PlayerHuman)) {
//              game.aiRedo();
//          } else if (game.getWhosTurn() == game.getPlayer2().getPlayer() && !(game.getPlayer2() instanceof PlayerHuman)) {
//              game.aiRedo();
//          } else {
        RedoCommand redo = new RedoCommand(game);
        redo.execute();
    }
//}
}
