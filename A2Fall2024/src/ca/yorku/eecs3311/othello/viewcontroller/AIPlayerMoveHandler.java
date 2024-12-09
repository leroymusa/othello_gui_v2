package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Move;
import ca.yorku.eecs3311.othello.model.Othello;
import ca.yorku.eecs3311.othello.model.OthelloBoard;
import ca.yorku.eecs3311.othello.model.PlayerHuman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AIPlayerMoveHandler implements EventHandler<ActionEvent> {
    private Othello othello;
    private boolean isAITurnInProgress = false;  //flag to check if AI is currently making a move

    public AIPlayerMoveHandler(Othello othello) {
        this.othello = othello;
    }

    @Override
    public void handle(ActionEvent arg0) {
        if (!othello.isGameOver()) {
            char whosTurn=othello.getWhosTurn();
            if (isAITurnInProgress) {return;}
            Move move = null;
            if (whosTurn==OthelloBoard.P1&&!(othello.getPlayer1() instanceof PlayerHuman)) {move=this.othello.player1.getMove();
            } else if (whosTurn==OthelloBoard.P2&&!(othello.getPlayer2() instanceof PlayerHuman)) {move=this.othello.player2.getMove();}

            if (move != null) {
                isAITurnInProgress=true; //lock the AI move to prevent recursive execution
                othello.move(move.getRow(),move.getCol());
                isAITurnInProgress=false;
                            }
        }
    }
}
