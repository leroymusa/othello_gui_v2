package ca.yorku.eecs3311.othello.model;

public interface DiscCountVisitorInterface {
	public int visit(OthelloBoard board, char player);
}