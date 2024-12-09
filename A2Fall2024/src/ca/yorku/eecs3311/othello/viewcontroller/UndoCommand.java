package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Othello;
import javafx.scene.control.ListView;

/**
 * An UndoCommand undoes the last move or the last two moves if playing 
 * against an AI
 *
 */
public class UndoCommand implements GameStatusCommand {
	private final Othello game;
	private final ListView<String> historyList;
	/**
	 * Creates an undo command object
	 * @param othello  the Othello game 
	 */
	public UndoCommand(Othello othello,ListView<String> historyList) {
		this.game=othello;
		this.historyList=historyList;
	}

	/**
	 * executes an undo command
	 */
	@Override
	public void execute() {
		this.game.undo();
		if (!historyList.getItems().isEmpty()) {
            historyList.getItems().remove(historyList.getItems().size()-1);
        }
	}

}
