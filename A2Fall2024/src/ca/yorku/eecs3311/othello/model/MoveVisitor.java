package ca.yorku.eecs3311.othello.model;

/**
 * 
 * Visits OthelloBoard and makes a move for a given player. Implementation of a visitor.
 * 
 * @autor Leroy Musa
 */


public class MoveVisitor implements MoveVisitorInterface{
	
	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	@Override
	public boolean visit(OthelloBoard board, int row,int col,char player) {
		if (!board.validCoordinate(row,col))
			return false;
		if (board.get(row, col)!=OthelloBoard.EMPTY)
			return false;

		int numChangedTotal=0;

		for (int drow =-1; drow<=1; drow++) {
			for (int dcol=-1; dcol<=1; dcol++) {
				if (drow==0 && dcol==0)
					continue;
				int numChanged=board.flip(row+drow,col+dcol,drow,dcol,player);
				if (numChanged>=0)
					numChangedTotal+=numChanged;
			}
		}
		if (numChangedTotal>0) {
			board.setCoordinate(row,col,player);;
			return true;
		}
		return false;
	}


	

	


}
