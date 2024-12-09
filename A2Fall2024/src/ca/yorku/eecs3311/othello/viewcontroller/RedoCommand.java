package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;

/**
 * A RedoCommand redoes the last undone move.
 */
public class RedoCommand implements GameStatusCommand {
    private Othello game;

    /**
     * Creates a redo command object
     * @param othello the Othello game
     */
    public RedoCommand(Othello othello) {
        this.game = othello;
    }

    /**
     * Executes a redo command
     */
    @Override
    public void execute() {
        this.game.redo();
    }
}
