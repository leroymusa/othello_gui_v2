package ca.yorku.eecs3311.othello.model;

public interface MoveVisitorInterface {
	public boolean visit(OthelloBoard board,int row,int col,char player);
}