package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Player;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.TextField;

/**
 * A MoveStrategyTracker keeps track of a player's type (Human, Random, Greedy).
 * 
 * @autor leroy musa
 */
public class MoveStrategyTracker extends TextField implements Observer {
    @SuppressWarnings("unused")
	private Player player;

    /**
     * Constructs a MoveStrategyTracker for a specific player.
     *
     * @param player the player to track
     */
    public MoveStrategyTracker(Player player) {
        this.player = player;
        this.setText(player.getPlayer() + " : " + player.getClass().getSimpleName().replace("Player", ""));
        this.setEditable(false);
        player.attach(this); //subscribe to player updates
    }

    /**
     * Updates the display whenever the player's type changes.
     */
    @Override
    public void update(Observable o) {
        Player updatedPlayer = (Player) o;
        this.setText(updatedPlayer.getPlayer() + " : " + updatedPlayer.getClass().getSimpleName().replace("Player", ""));
    }
}
