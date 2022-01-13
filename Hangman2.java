import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList; 
public class Hangman2 {
	
	public static void main(String[] args) throws IOException {
		System.out.println("\\        /  __      __  __        __   _____  __    |    |            __                   ");
		System.out.println(" \\  /\\  /  |_  |   |   |  | |\\/| |_      |   |  |   |----|  /\\  |\\ | | _  |\\/|  /\\  |\\ |");
		System.out.println("  \\/  \\/   |__ |__ |__ |__| |  | |__     |   |__|   |    | /--\\ | \\| |__| |  | /--\\ | \\|");
		System.out.println("\n~~~~~~~~~~~~~~");
		System.out.println("-------------");
        System.out.println("|        |   ");
        System.out.println("|        O   ");
        System.out.println("|       /|\\ ");
        System.out.println("|        |   ");
        System.out.println("|       / \\ ");
        System.out.println("|            ");
        System.out.println("|");
        System.out.println("-------------");
		
		System.out.println("\nIMPORTANT INSTRUCTIONS BEFORE YOU PLAY THE GAME:-\r\n"
				+"\n-> A secret random word will be selected from your choice of file.\r\n"
				+ "-> You have to guess a letter that can be in the secret word, you'll be informed if you guessed correctly or not.\r\n"
				+ "-> You will also be informed how many tries you have and how many letters are there in the secret word to be guessed.\r\n"
				+ "-> For every wrong guess your number of tries will decrease.\r\n"
				+ "-> Don't enter any digit, special character or a letter you already used before. Otherwise, \n   you'll recieve a warning, for every 3 warnings 1 try will be reduced.\r\n"
				+ "-> If you win, your score will be displayed, and highest score after the game ends.\r\n"
				//+ "-> If you want to quit the game at any point, enter a underscore(_)."
				+ "\n   ALL THE BEST & ENJOY !!! :)");
		
		//Arraylists for different categories
		ArrayList<String> words1 = new ArrayList<>();
		ArrayList<String> words2 = new ArrayList<>();
		ArrayList<String> words3 = new ArrayList<>();
		
		ReadAndWriteFromTextFiles(words1, words2, words3);
		
		//Opening a file in writing mode for leaderboard
		FileWriter fw = new FileWriter("C:\\Users\\smahi\\Documents\\Leaderboard.txt");		
			
		
		String randomWordToGuess= "";     //Initialising the secret word
		Scanner input = new Scanner(System.in);
		boolean wordIsGuessed = false;
		int scoreValue = 0;
		boolean weArePlaying = true;
		while (weArePlaying==true) {
			
			wordIsGuessed = false;
			int warnings = 1;    //Initializing number of warnings
			//Informing the user that there are 3 categories for words and how they can choose
			System.out.println("\nThere are 3 categories for words, which are:\n\n"+"1. Brands\n"+"2. Places\n"+"3. Fictional Characters\n");
			System.out.println("1) If you choose Brands, the secret words will be name of Brands or well-known companies.\r\n"
					+ "2) If you choose Places, the secret words will be name of well-known countries, cities etc.\r\n"
					+ "3) If you chooses Fictional Characters, the words will be the names of most famous \nfictional characters e.g., tom, jerry, ironman, harrypotter etc.\r\n"
					+ "\nIn Fictional Characters, the full names are merged so that there is no space or special characters \nin between e.g., Harry Potter is like harrypotter, Wonder Woman is like wonderwoman, Mr Bean is like mrbean etc.\r\n"
					);
			System.out.println("Enter B for Brands OR P for Places OR  F for Fictional Characters");
			System.out.println("Enter your choice:");
			Scanner choice = new Scanner(System.in);
			char userChoice = choice.nextLine().charAt(0);  //Taking input from user for categories
			String secretLetter	= ChooseRandomWord(choice, userChoice, randomWordToGuess, words1, words2, words3);
			
			//Arraylist for used letters
			ArrayList<Character> UsedLetter = new ArrayList<>();				
										
			//Creating a variable for amount of guesses the user have at the start
			int amountOfGuesses = (secretLetter.length()+2);
			System.out.println("The secret word have "+secretLetter.length()+" letters.");
			char[] playerGuess = new char[amountOfGuesses];
								
			for (int i = 0; i<playerGuess.length-2; i++) { 
				playerGuess[i] = '_';   //Adding underscores to the arraylist
			}
								
			int tries = 0;
			
			while (!wordIsGuessed && tries!=amountOfGuesses) { 
				System.out.print("Current guesses: ");
				printArray(playerGuess);
					
				System.out.printf("\n"+"You have %d tries left.\n",amountOfGuesses - tries);
				HangmanFigure.createimage(tries);
				ArrayList<Integer> UpdateValue = new ArrayList<>();						
				UpdateValue = ComparisonAndWarning(choice, weArePlaying, wordIsGuessed, tries, UsedLetter, secretLetter, playerGuess, fw, warnings);
				tries = UpdateValue.get(0);
				warnings = UpdateValue.get(1);
				
				if (isTheWordGuessed(playerGuess)) {
					ArrayList updateValue = new ArrayList<>();
					updateValue = ScoreCalculation(secretLetter, amountOfGuesses, tries, fw, weArePlaying);
					wordIsGuessed = (boolean) updateValue.get(0);
					scoreValue = (int) updateValue.get(1);
				}					
		}			
		if (!wordIsGuessed) System.out.println("\n"+"You ran out of guesses:/\n");
		System.out.println("The secret word is: "+secretLetter);
		System.out.println("  \n*************************************\n");
				
		RemoveUsedWords(userChoice, secretLetter, words1, words2, words3);
		
		boolean AskUser = true;
		while(AskUser){
			//Asking the user if they want to play again or not
			System.out.println("Do you want to play another game? (Y/N)");
			String anotherGame = input.nextLine();
			if (anotherGame.equals("N") || anotherGame.equals("n")) {
				weArePlaying = false;
				AskUser = false;
				break;
			}
			else if(anotherGame.equals("Y") || anotherGame.equals("y")) {
				weArePlaying = true;
				AskUser = false;
				break;
			}
			else{
				System.out.println("Invalid Input..!! Enter only Y or N");
				AskUser = true;
				continue;
			}
		}
	}	
		
	System.out.println("Game Over..!!");
	fw.close();    //Closing the written file before reading it
	if(scoreValue!=0) {		
		Leaderboard();
	} else {
		System.out.println("You haven't achieved highscore");
	}	
}
	
	//Reading the text files and writing the words from files into arraylists
	public static void ReadAndWriteFromTextFiles(ArrayList<String> words1, ArrayList<String> words2, ArrayList<String> words3) throws FileNotFoundException {
		//creating new file in reading mode
		File dictionary1 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\Brands.txt");
		File dictionary2 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\Places.txt");
		File dictionary3 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\FictionalCharacters.txt");
				
		//declaring Scanners to read the files	
		Scanner textScanner1 = new Scanner(dictionary1);		
		Scanner textScanner2 = new Scanner(dictionary2);		
		Scanner textScanner3 = new Scanner(dictionary3);
				
		//Writing the values of file in an arraylist		
		while(textScanner1.hasNextLine()) {
			words1.add(textScanner1.nextLine());
		}
				
		while(textScanner2.hasNextLine()) {
			words2.add(textScanner2.nextLine());
		}
				
		while(textScanner3.hasNextLine()) {
			words3.add(textScanner3.nextLine());
		}
	}
	
	//Choosing random word from the file chosen by the user
	public static String ChooseRandomWord(Scanner choice,char userChoice, String randomWordToGuess, ArrayList<String> words1, ArrayList<String> words2, ArrayList<String> words3) {
		while(String.valueOf(userChoice) != null) {
			//Choosing the secret word according to the user's choice
			if(userChoice=='B' || userChoice =='b') {
				System.out.println("You have chosen Brands category !!");
				randomWordToGuess = words1.get((int)(Math.random()*words1.size()));  //choosing word at random from the file
			} else if(userChoice=='P' || userChoice =='p') {
				System.out.println("You have chosen Places category !!");
				randomWordToGuess = words2.get((int)(Math.random()*words2.size()));
			} else if(userChoice== 'F' || userChoice =='f'){
				System.out.println("You have chosen Fictional Characters category !!");
				randomWordToGuess = words3.get((int)(Math.random()*words3.size()));
			} else {
				System.out.println("INVALID INPUT..!! Enter only B or P or F, enter again..!!");
				System.out.println("Enter your choice: ");
				userChoice = choice.nextLine().charAt(0);
				continue;				
				}break;
			}
		return randomWordToGuess;
	}
	
	//Taking input from user and comparing them. Giving warning if needed or checking if the guess is correct or not.
	public static ArrayList<Integer> ComparisonAndWarning(Scanner input, boolean weArePlaying, boolean wordIsGuessed, int tries, ArrayList<Character> UsedLetter, String secretLetter, char[] playerGuess, FileWriter fw, int warnings) throws IOException {
		
		System.out.println("Enter a single character");
		char input1 = input.nextLine().charAt(0);  //Taking input from user, even if by mistake user inputs 'asjhk' then only 'a' will be considered
		//If user wants quit the game at any point of time
		if (input1 == '_') {
			weArePlaying = false;
			wordIsGuessed = true;
		} 
		//Condition for any invalid input, warning will be given
		else if((input1>= 48 && input1<=57) || (input1>=32 && input1<=47) || (input1>=58 && input1<=64) || (input1>=91 && input1<=96) || (input1>=123 && input1<=126)) {
			System.out.println("WARNING: You have entered invalid format, enter only letters\n"+"Number of warnings you received: "+ warnings);
			if (warnings%3==0){ //For every 3 subsequent invalid input, number of guesses will be reduced
				tries=tries+1;
			}
			warnings++;
		} 
		else if (UsedLetter.contains(input1)) {
			System.out.println("WARNING: You already tried this letter, try another letter !! Enter only letters\n"+"Number of warnings you received: "+ warnings);
			if (warnings%3==0){ //For every 3 subsequent invalid input, number of guesses will be reduced
				tries=tries+1;
			}
			warnings++;
		}
		else {
				boolean found = false; 						
				//Comparing the user input with all the letters of secret word
				for (int i = 0; i < secretLetter.length(); i++) {
					if (secretLetter.charAt(i) == input1 || secretLetter.charAt(i) == Character.toLowerCase(input1)) {
						playerGuess[i] = input1;  //Assigning the input value to the underscore at the matching index
						found = true;				
				}				
			}
			
			if(!found){
				tries++;
				System.out.println("Wrong guess/letter, try another..!!");
			}else {
				System.out.println("Woah..!! That's a good guess, keep going..!!");
			}
			
			//Adding used letter to the arraylist
			UsedLetter.add(input1);														
		}
		ArrayList<Integer> UpdateValue = new ArrayList<>();
		UpdateValue.add(tries);
		UpdateValue.add(warnings);
		return UpdateValue;		
	}
	
	//Counting number of unique letters in the secret word
	public static ArrayList ScoreCalculation(String secretLetter, int amountOfGuesses, int tries, FileWriter fw, boolean wordIsGuessed) throws IOException {
			
			wordIsGuessed = true;
			System.out.println("Congratulations you won!");
						
			boolean[] chars = new boolean[26];
			for (int i = 0; i < secretLetter.length(); ++i) {
			    char ch = secretLetter.charAt(i);
			    if (ch >= 97 && ch <= 122) { 
			        chars[ch - 'a'] = true;   //Assigning true at the index of character/letter in the array
			    }
			}
			int count = 0;
			for (int i = 0; i < chars.length; ++i) {
			    if (chars[i]) count++;   //Increase count by 1 if the value at index i is true
			}
			int score = count*(amountOfGuesses - tries);
			System.out.println("Your score is: " + score);
			
			//Writing the value of score in the file opened at the start
			if(score!=0) {
				fw.write(String.valueOf(score)+'\n');
			}
			ArrayList updateValue = new ArrayList<>();
			updateValue.add(wordIsGuessed);
			updateValue.add(score);
			return updateValue;
	}
	
	//Removing the word used from the corresponding file
	public static void RemoveUsedWords(char userChoice, String secretLetter, ArrayList<String> words1, ArrayList<String> words2, ArrayList<String> words3) {
		
			if(userChoice=='B' || userChoice == 'b') {
				words1.remove(secretLetter);
			}else if(userChoice=='P' || userChoice == 'p') {
				words2.remove(secretLetter);
			}else {
				words3.remove(secretLetter);
			}
	}
	
	//Leaderboard calculation	
	public static void Leaderboard() throws FileNotFoundException {
				
		File fr = new File("C:\\Users\\smahi\\Documents\\Leaderboard.txt");	    //Reading the leaderboard file
		Scanner textinfr = new Scanner(fr);
		ArrayList<String> lbscore = new ArrayList<>();
		while(textinfr.hasNextLine()) {
			lbscore.add(textinfr.nextLine());    //Adding all the scores from the file to arraylist
		}
		int highest = Integer.valueOf(lbscore.get(0));    //Assigning the first value to a variable

		for (int i = 0; i <lbscore.size(); i++){    //A loop to choose and compare each and every value in lbscore(arraylist)
		    if (Integer.valueOf(lbscore.get(i))>highest) {
		        highest=Integer.valueOf(lbscore.get(i));       //Assigning the current largest value to the variable highest
		    }
		}		
		if (lbscore.size()>0)
		{			
			System.out.println("The highest score achieved in this game is: "+ highest);
			System.out.println("Rest of the scores are as follows: ");
			for(int i = 0; i<lbscore.size(); i++) {
				int j = i+1;
				System.out.println("Score "+ j +" you achieved was:- " + lbscore.get(i));
			}
		}	
	}
	

	//Method for just putting space between the underscores
	public static void printArray(char[] array) {
		for (int i = 0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();		
	}
	
	//Method to check if the word is guessed or not
	public static boolean isTheWordGuessed(char[] array) {
		for (int i = 0; i<array.length; i++) {
			if (array[i] == '_') return false;   //If any of the element is an underscore that means the word isn't guessed
		}
		return true;
	}	
}




