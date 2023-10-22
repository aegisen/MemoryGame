//package edu.wm.cs.cs301.memorygame;

import java.util.Random;
import java.util.stream.*;
import java.util.Arrays;

public class GameBoard {
	private final GamePiece[][] board;
	
	public GameBoard(int rows, int cols, Alphabet a)
	{
		GamePiece[][] temp = new GamePiece[rows][cols];
	    GamePiece[] allPieces = new CharacterGamePiece[(rows*cols)];
	    char[] chars = a.toCharArray();
	    
	    //Fill board; generate (row*col) random indexes to grab symbols for board
	    Random random = new Random();
        int min = 0;
        int max = chars.length;
        int[] indexes = IntStream.generate(() -> random.nextInt(max - min) + min).limit((rows*cols)/2).toArray();
        
        //Create array of gamepieces; two pairs of each value
        for(int pc = 0; pc < allPieces.length; pc++)
        {
            int index = indexes[pc % indexes.length];
            allPieces[pc] = new CharacterGamePiece(chars[index]);
        }
        
        //Shuffle pieces
        for (int i = allPieces.length - 1; i > 0; --i)
        {
            int j = random.nextInt(i + 1);
            GamePiece temp_pc = allPieces[i];
            allPieces[i] = allPieces[j];
            allPieces[j] = temp_pc;
        }
        
        int i = 0; //keep track of location in all-pieces array
        for(int r = 0; r < rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                temp[r][c] = allPieces[i];
                i++;
            }
        }
        //Set board to the temp holder
        this.board = temp;
	}

    public GamePiece[][] getBoard()
    {
        return this.board;
    }
}
