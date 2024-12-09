package ca.yorku.eecs3311.othello.model;
/**
 * 
 * /**
 * 
 * Constructs a copy of OthelloBoard. Implmentation of a visitor
 * 
 * @autor leroy musa
 */

 

public class BoardVisitor implements BoardVisitorInterface{
	
	/**
	 * 
	 * @return a board copy of OthelloBoard
	 */
	@Override
	public OthelloBoard visit(OthelloBoard board) {
		OthelloBoard ob = new OthelloBoard(board.getDimension());
		for (int row = 0; row < board.getDimension(); row++) {
			for (int col = 0; col < board.getDimension(); col++) {
				ob.getBoardList()[row][col] = board.getBoardList()[row][col];
			}
		}
		return ob;
	}
	
	

}
