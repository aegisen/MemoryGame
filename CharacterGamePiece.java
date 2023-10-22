//package edu.wm.cs.cs301.memorygame;

public class CharacterGamePiece implements GamePiece {
	private final Character symbol;
	private boolean visible = false;
	
	public CharacterGamePiece(char s) {
		this.symbol = s;
	}

	public Character getSymbol() {
        return (Character) this.symbol;
	}
	
	public void setVisible(boolean v) {
	    this.visible = v;
	}
	
	public boolean isVisible() {
	    return this.visible;
	}
	
	public boolean equals(GamePiece other) {
	    if((char)this.symbol == (char)other.getSymbol())   
	        return true;
	       
	    else   
	        return false;
	    

	}
	
}
