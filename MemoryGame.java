//package edu.wm.cs.cs301.memorygame;

import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;

public class MemoryGame {
	private GameBoard board;
	
	public MemoryGame()
	{
	    System.out.println("Welcome to the game!\n");
        Scanner scan = new Scanner(System.in);
    
	    String alphabet_choice;
        String alphabet_label;
	    Alphabet alpha;

	    do
	    {
    	    System.out.println("Please choose your character set using the options:\n1: Latin Alphabet    2: Armenian Alphabet");
    	    System.out.print("Choice: ");
    	    
    	    alphabet_choice = scan.nextLine();

    	    if(!alphabet_choice.equals("1") && !alphabet_choice.equals("2"))
    	    {
    	        System.out.println("Sorry! " + alphabet_choice + " is not a valid response. Please try again.\n");
    	    }
	    }
	    while(!alphabet_choice.equals("1") && !alphabet_choice.equals("2"));
	    
	    if(alphabet_choice.equals("1"))
	    {
	        System.out.println("You have chosen Latin Alphabet.");
            alphabet_label = "Latin Alphabet";
	        alpha = new LatinAlphabet();
	    }
	    
	    else
	    {
	        System.out.println("You have chosen Armenian Alphabet.");
            alphabet_label = "Armenian Alphabet";
	        alpha = new ArmenianAlphabet();
	    }
    
        
        String difficulty_choice;
        int rows;
        int cols;
        
        do
        {
            System.out.println("\nPlease choose your difficulty.");
            System.out.println("1: Easy (3x4)    2: Medium (4x7)    3: Hard (7x8)");
         	System.out.print("Choice: ");
         	
         	difficulty_choice = scan.nextLine();
    
    	    if(!difficulty_choice.equals("1") && !difficulty_choice.equals("2") && !difficulty_choice.equals("3"))
    	    {
    	        System.out.println("Sorry! " + difficulty_choice + " is not a valid response. Please try again.\n");
    	    }
        }
        while(!difficulty_choice.equals("1") && !difficulty_choice.equals("2") && !difficulty_choice.equals("3"));
        
        if(difficulty_choice.equals("1"))
        {
            System.out.println("You have chosen Easy Difficulty.");
            rows = 3;
            cols = 4;
        }
        
        else if(difficulty_choice.equals("2"))
        {
            System.out.println("You have chosen Medium Difficulty.");
            rows = 4;
            cols = 7;
        }
        
        else
        {
            System.out.println("You have chosen Hard Difficulty.");
            rows = 7;
            cols = 8;
        }
        
        GameBoard temp = new GameBoard(rows, cols, alpha);
        this.board = temp;
        GamePiece[][] boardMap = board.getBoard();
        
        String user_entry; //holds what the user enters
        int num_matches = 0; //number of pieces that have been matched
        int max_matches = (rows*cols)/2; //max num of matches in a game; used to check if player won
        int num_rounds = 0;
        int num_flipped = 0; //track how many pieces (max 2) have flipped
        boolean is_valid; //track if entry is a valid entry
        int[] flipped_pieces = new int[4]; //keeps track of what pieces have been flipped [r1, c1, r2, c2]
        boolean victory = false; //tracks if player has won their game
        
        do
        {
            if(num_matches == max_matches) //if all pieces matched...
            {
                System.out.println("CONGRATS! You've matched everything!");
                System.out.println("Total number of rounds: " + num_rounds);
                victory = true;
                break;
            }
            
            //Print out board layout
            System.out.println("\nRound " + (num_rounds+1));

            System.out.print("    "); //visual padding
            int border_length = 0; //track length of border
            for(int c = 1; c < cols + 1; c++)
            {
                System.out.print(c);
                border_length++;
                if(c != cols)
                {
                    System.out.print("   ");
                    border_length += 3;
                }
            }
            border_length += 4;

            //Print border
            System.out.print("\n  ");
            for(int c = 0; c < border_length; c++)
            {
                System.out.print("=");
            }
            
            //Start printing board
            System.out.print('\n');
            char unsolved = ' ';
            
            for(int i = 0; i < rows; i++)
            {
                System.out.print(i+1 + " | ");
                
                for(int j = 0; j < cols; j++)
                {
                    if(boardMap[i][j].isVisible())
                    {
                        System.out.print((char)boardMap[i][j].getSymbol() + " | ");
                    }
                    else
                    {
                        System.out.print(unsolved + " | ");
                    }
                }
                System.out.print('\n');
            }
            
            //Bottom border
            System.out.print("  ");
            for(int c = 0; c < border_length; c++)
            {
                System.out.print("=");
            }
            
            if(num_flipped != 2) //if not yet two flipped, can keep flipping
            {
                do
                {
                    System.out.print("\nChoose a tile [R C] or type 'quit' to exit: ");
                    user_entry = scan.nextLine();
                    user_entry = user_entry.toLowerCase();
                    
                    if(user_entry.equals("quit"))
                    {
                        is_valid = true;
                    }
                    else if(user_entry.length() != 3 || !Character.isDigit(user_entry.charAt(0)) || !Character.isDigit(user_entry.charAt(2)) ||Integer.parseInt(Character.toString(user_entry.charAt(0))) <= 0 ||
                       Integer.parseInt(Character.toString(user_entry.charAt(0))) > rows || Integer.parseInt(Character.toString(user_entry.charAt(2))) <= 0 ||
                       Integer.parseInt(Character.toString(user_entry.charAt(2))) > cols)
                    {
                          System.out.println("\nInvalid input! Please try again.");
                          is_valid = false;
                    }
                    else
                    {
                          is_valid = true;
                    }
                } while(is_valid != true);
                
                if(!user_entry.equals("quit")) //if player didn't quit...
                {
                    //get selected row and column
                    int user_row = Integer.parseInt(Character.toString(user_entry.charAt(0))); 
                    int user_col = Integer.parseInt(Character.toString(user_entry.charAt(2)));
                    
                    //check if piece has already been flipped
                    if(boardMap[user_row-1][user_col-1].isVisible() == true)
                    {
                        System.out.println("Invalid input! Already flipped piece.");
                        continue;
                    }
                    
                    //if one already flipped
                    if(num_flipped == 0)
                    {
                        //save in first two slots
                        flipped_pieces[0] = user_row-1;
                        flipped_pieces[1] = user_col-1;
                        
                    }
                    
                    //otherwise
                    else
                    {
                        //save in last two slots
                        flipped_pieces[2] = user_row-1;
                        flipped_pieces[3] = user_col-1;
                    }
                    
                    boardMap[user_row-1][user_col-1].setVisible(true); //flip over selected piece
                    num_flipped++; //increment number of pieces flipped
                }
                else //if player quit...
                    break;
            }
            
            
            else //if two pieces flipped...
            {
                //check if the pieces matched
                if(boardMap[flipped_pieces[0]][flipped_pieces[1]].equals(boardMap[flipped_pieces[2]][flipped_pieces[3]]))
                {
                    do
                    {
                        num_flipped = 0; //reset number flipped
                        System.out.println("\nMatch!\nEnter any key to continue!");
                        num_matches++; //+1 match
                        
                        user_entry = scan.nextLine();
                        break; //move on when player enters anything
                    } while(user_entry.equals(""));
                    
                }
                
                else
                {
                    do
                    {
                        num_flipped = 0; //reset, but no match :(
                        System.out.println("\nNot a match.\nEnter any key to continue.");
                        
                        user_entry = scan.nextLine();
                        break;
                    } while(user_entry.equals(""));
                    
                    //flip back the selected pieces
                    boardMap[flipped_pieces[0]][flipped_pieces[1]].setVisible(false);
                    boardMap[flipped_pieces[2]][flipped_pieces[3]].setVisible(false);
                    
                }
                num_rounds++;
                
            }  
            
        }while(!user_entry.equals("quit") || !victory); //exit conditions; when met, go below
        
        if(!victory) //if player quit...
        {
            System.out.println("\n\n*****FINAL BOARD*****");
            System.out.print("\n    "); //visual padding
            int border_length = 0; //track length of border
            for(int c = 1; c < cols + 1; c++)
            {
                System.out.print(c);
                border_length++;
                if(c != cols)
                {
                    System.out.print("   ");
                    border_length += 3;
                }
            }
            border_length += 4;

            //Print border
            System.out.print("\n  ");
            for(int c = 0; c < border_length; c++)
            {
                System.out.print("=");
            }

            System.out.println("");

            for(int i = 0; i < rows; i++)
            {
                System.out.print(i+1 + " | ");
                
                for(int j = 0; j < cols; j++)
                {
                    //Reveal full board
                    System.out.print((char)boardMap[i][j].getSymbol() + " | ");
                }
                System.out.print('\n');
            }

            //bottom border
            System.out.print("  ");
            for(int c = 0; c < border_length; c++)
            {
                System.out.print("=");
            }

            System.out.print("\n\nThank you for playing!");
        }
        
        else //otherwise, player won
        {
            String name;
            String play_again; //store entry if player wants to play again
            int record_score; //the record-holding score (lowest num. rounds to have won)
            String record_line; //string line in leaderboard file that has record info
            int record_line_num; //the number of above line
            String new_record; //holds new record

            System.out.print("Please enter your name for the records: ");
            
            name = scan.nextLine();

            try
            {
                Path path = Paths.get("leaderboard.txt"); 

                if(difficulty_choice.equals("1")) //if easy difficulty...
                {
                    record_line = Files.readAllLines(path).get(3); //get the corresponding record line

                    if(record_line.equals(" ")) //if there hasnt been an entry yet...
                        {
                            //enter the new record
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            List<String> lines = Files.readAllLines(path);
                            lines.set(3, new_record);
                            Files.write(path, lines);
                        }

                    else //if record already exists...
                    {
                        //get record num. rounds
                        record_line = record_line.substring(record_line.indexOf(" "), record_line.length()-6).trim();
                        record_score = Integer.parseInt(record_line);

                        if(num_rounds < record_score) //check if current record is better; if it is, replace the record
                        {
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            List<String> lines = Files.readAllLines(path);
                            lines.set(3, new_record);
                            Files.write(path, lines);
                        }

                    }
                }

                else if(difficulty_choice.equals("2")) //if medium difficulty...
                {
                    record_line = Files.readAllLines(path).get(6);

                    if(record_line.equals(" "))
                        {
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            //new_record = (Integer.toString(num_rounds)) + " ROUNDS - " + name;
                            List<String> lines = Files.readAllLines(path);
                            lines.set(6, new_record);
                            Files.write(path, lines);
                        }

                    else
                    {
                        record_line = record_line.substring(record_line.indexOf(" "), record_line.length()-6).trim();
                        record_score = Integer.parseInt(record_line);

                        if(num_rounds < record_score)
                        {
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            List<String> lines = Files.readAllLines(path);
                            lines.set(6, new_record);
                            Files.write(path, lines);

                        }
                    }
                }


                else //if hard difficulty
                {
                    record_line = Files.readAllLines(path).get(9);

                    if(record_line.equals(" "))
                        {
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            List<String> lines = Files.readAllLines(path);
                            lines.set(9, new_record);
                            Files.write(path, lines);
                        }

                    else
                    {
                        record_line = record_line.substring(record_line.indexOf(" "), record_line.length()-6).trim();
                        record_score = Integer.parseInt(record_line);

                        if(num_rounds < record_score)
                        {
                            new_record = (name + ", " + Integer.toString(num_rounds) + " ROUNDS");
                            List<String> lines = Files.readAllLines(path);
                            lines.set(9, new_record);
                            Files.write(path, lines);

                        }
                    }
                } 
                
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            
            //ask if player wants to play again
            System.out.print("Would you like to play again? [Y or N]: ");
            
            do //repeat until valid response
            {
                play_again = scan.nextLine();
                play_again = play_again.toUpperCase();
                
                if(!play_again.equals("Y") && !play_again.equals("N"))
                {
                    System.out.print("\nInvalid input! Please try again.\n");
                }
                
            }while(!play_again.equals("Y") && !play_again.equals("N"));
            
            if(play_again.equals("Y"))
            {
                new MemoryGame(); //new game!
            }
            
            else
            {
                scan.close();
                System.out.println("\nThank you for playing!"); //thank the player for playing! and close tools
            }
        }
    }
}
