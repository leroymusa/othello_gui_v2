package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

/**
 * An UndoInvoker handles an undo button click
 *
 */
class UndoInvoker implements EventHandler<ActionEvent> {
	private  final Othello game;
	private  final ListView<String> historyList;
	
	/**
	 * Constructs an UndoInvoker 
	 * @param othello  this Othello game
	 */
	public UndoInvoker(Othello othello, ListView<String> historyList) {
		this.game = othello;
		this.historyList = historyList;
	}
	
	/**
	 * handles an undo button click, creates an undo command object and 
	 * calls execute on it
	 */
	@Override
	public void handle(ActionEvent event) {
//        if (game.getWhosTurn()==game.getPlayer1().getPlayer()&&!(game.getPlayer1() instanceof PlayerHuman)){
//            game.aiUndo();
//        } else if (game.getWhosTurn()==game.getPlayer2().getPlayer()&&!(game.getPlayer2() instanceof PlayerHuman)){
//            game.aiUndo();
//        } else {
		UndoCommand undo = new UndoCommand(game,historyList);
		undo.execute();
	}
	//}
}
