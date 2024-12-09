package ca.yorku.eecs3311.othello.viewcontroller;

import ca.yorku.eecs3311.othello.model.Hints;
import ca.yorku.eecs3311.othello.model.Othello;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

/**
 * An extension of InnerShadow that sets the color according to what the MoveStrategy of the Hint is.
 * 
 * @autor leroy musa
 *
 */
public class HintHighlight extends InnerShadow{
	private Othello othello;
	private int row, col;
	private Hints hints;

	/**
	 * Constructs a new HintHighlight for this BoardSquare to represent a Hint.
	 * 
	 * @param othello  the current Othello game
	 * @param row  the row of this BoardSquare
	 * @param col  the column of this BoardSquare
	 * @param hints  the Hints for this game
	 */
	public HintHighlight(Othello othello, int row, int col, Hints hints) {
		this.othello = othello;
		this.hints = hints;
		this.row = row;
		this.col = col;
		
		this.setHighlightColor();
		
		this.setOffsetX(0f);
		this.setOffsetY(0f);
		this.setWidth(25);
		this.setHeight(25);
		this.setBlurType(BlurType.ONE_PASS_BOX);
	}
	
	/**
	 * Sets the color according to the MoveStrategy of this Hint
	 */
	private void setHighlightColor() {
		if(this.othello.getImage(row, col)!=null)
			this.setColor(null);

		if(this.hints.greedyHint.isButtonHint(row, col))
			this.setColor(Color.rgb(222, 2, 2));
		else if(this.hints.randomHint.isButtonHint(row, col)) 
			this.setColor(Color.rgb(11, 222, 211));
		else if(this.othello.inAllMoves(row, col))
			this.setColor(Color.rgb(67, 209, 6));
		else
			this.setColor(null);
	}
}
