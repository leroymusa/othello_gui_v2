package ca.yorku.eecs3311.othello.viewcontroller;

import java.io.InputStream;

import ca.yorku.eecs3311.othello.model.Hints;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Constructs and updates the HintMenuItem Hint representations in the Menu 
 * 
 * @autor leroy musa
 *
 */
public class HintMenuItem extends MenuItem implements Observer{
	private Hints hints;
	private String hintType;
	
	/**
	 * Constructs a new HintMenuItem 
	 * 
	 * @param hints The hints for this game
	 * @param hintType the String representation of the MoveStrategy this HintMenuItem corresponds to
	 */
	public HintMenuItem(Hints hints, String hintType) {
		this.hints = hints;
		this.hintType = hintType;
		
		this.setText(this.hintType + " hint");
		this.setGraphic(this.getImage(this.hintType + "outline.png"));
	}
	
	/**
	 * Loads a graphic by the name name
	 * 
	 * @param name of the graphic
	 * @return the graphic
	 */
	private ImageView getImage(String name) {
		InputStream input1 = OthelloApplication.class.getResourceAsStream(name);
	    Image image = new Image(input1); 
	    ImageView vimage = new ImageView(image); 
		return vimage;
	}
	
	/**
	 * Returns the type of this HintMenuItem
	 * 
	 * @return the String representation of the MoveStrategy this HintMenuItem corresponds to
	 */
	public String getHintType() {
		return this.hintType;
	}

	/**
	 * updates the graphic based on whether the hint is on or off. An empty box for off
	 * and a checked box for on. The color of the box corresponds to the type of Hint.
	 */
	@Override
	public void update(Observable o) {
		if(this.hintType=="greedy") {
			if(this.hints.greedyHint.isHintOn())
				this.setGraphic(this.getImage(this.hintType + "checkbox.png"));
			else
				this.setGraphic(this.getImage(this.hintType + "outline.png"));
		}
		else if(this.hintType=="random") {
			if(this.hints.randomHint.isHintOn())
				this.setGraphic(this.getImage(this.hintType + "checkbox.png"));
			else
				this.setGraphic(this.getImage(this.hintType + "outline.png"));
		}
	}

}
