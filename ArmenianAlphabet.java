//package edu.wm.cs.cs301.memorygame;

public class ArmenianAlphabet implements Alphabet{
	//The char array holding the alphabet
	private final char[] characters;
     
    public ArmenianAlphabet()
    {
        this.characters = toCharArray();
    }
    
    public char[] toCharArray() {
        //The "alphabet" used
        String symbols = "ՉՊՋՌՍՎՏՐՑՒՓՔՕՖաբգդեզէըթժիլխծկձղմյնշչպջռվտրւփքօֆև֍";

        //Array holding alphabet characters
        char result[] = new char[symbols.length()];
        
        //Iterate through string and copy values to array
        for(int i = 0; i < result.length; i++)
        {
            result[i] = symbols.charAt(i);
        }
        return result;
    }
    
    public char[] getAlphabet()
    {
    	return this.characters;
    }

}
