package ca.yorku.eecs3311.othello.model;

/**
 * 
 * Visits OthelloBoard and extracts the disc count for a certain player. Implementation of a visitor
 * 
 * @autor leroy musa
 */

public class DiscCountVisitor implements DiscCountVisitorInterface{
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of disc on the board for player
	 */
	@Override
	public int visit(OthelloBoard board,char player) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int row=0;row<board.getDimension();row++) {
			for (int col=0;col<board.getDimension();col++) {
				if (board.get(row,col)==player)
					count++;
			}
		}
		return count;
	}
}