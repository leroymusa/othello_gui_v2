package ca.yorku.eecs3311.othello.model;

/**
 * 
 * Checks for players that have a move on the board. Implmentation of a visitor.
 * 
 * @autor leroy musa
 */


public class HasMoveVisitor implements HasMoveVisitorInterface{
	/**
	 * Returns which player(s) have a move somewhere on the OthelloBoard.
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
		@Override
		public char visit(OthelloBoard board) {
			char retVal = OthelloBoard.EMPTY;
			for (int row = 0; row < board.getDimension(); row++) {
				for (int col = 0; col < board.getDimension(); col++) {
					for (int drow = -1; drow <= 1; drow++) {
						for (int dcol = -1; dcol <= 1; dcol++) {
							if (drow == 0 && dcol == 0)
								continue;
							char p = board.hasMove(row, col, drow, dcol);
							if (p == OthelloBoard.P1 && retVal == OthelloBoard.P2)
								return OthelloBoard.BOTH;
							if (p == OthelloBoard.P2 && retVal == OthelloBoard.P1)
								return OthelloBoard.BOTH;
							if (retVal == OthelloBoard.EMPTY)
								retVal = p;
						}
					}
				}
			}
			return retVal;
		}
	
}
