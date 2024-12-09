package ca.yorku.eecs3311.othello.model;

import java.util.ArrayList;

/**
 * 
 * Counts the number of discs added to the center 4x4 space of the 
 * OthelloBoard for this Player.
 *
 */
public class StartConfig_DiscCountVisitor implements DiscCountVisitorInterface{
	
	/**
	 * Returns the number of discs added to the center 4x4 space of the OthelloBoard for this player
	 * 
	 * Returns the number of discs added to the center 4x4 space of the 
	 * OthelloBoard for this Player
	 * 
	 * @param player P1 or P2
	 * @return the number of discs on the board for player
	 */
	@Override
	public int visit(OthelloBoard board, char player) {
		int count = 0;
		ArrayList<Integer> centre = new ArrayList<Integer>();
		centre.add(2);
		centre.add(3);
		centre.add(4);
		centre.add(5);
		for (int row=0;row<board.getDimension();row++) {
			for (int col=0;col<board.getDimension();col++) {
				if (board.get(row,col)==player&&centre.contains(row)&&centre.contains(col))
					count++;
			}
		}
		return count;
	}
}