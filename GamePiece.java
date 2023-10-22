//package edu.wm.cs.cs301.memorygame;

public interface GamePiece {
	public boolean equals(GamePiece other);
	public void setVisible(boolean v);
	public boolean isVisible();
	public Character getSymbol();
}
