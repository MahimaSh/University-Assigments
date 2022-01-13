import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class Hangman2Test {

	@Test
	void test1()  {
		Hangman2 test1 = new Hangman2();
		char[] input = {'l','e','v','i','s'};
		boolean output = test1.isTheWordGuessed(input);
		assertEquals(true, output);
		
	}
	
	@Test
	void test2() throws FileNotFoundException {
		Hangman2 test2 = new Hangman2();
		File dictionary1 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\Brands.txt");
		File dictionary2 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\Places.txt");
		File dictionary3 = new File("C:\\Users\\smahi\\eclipse-workspace\\Hangman2\\src\\FictionalCharacters.txt");
		
		//declaring Scanners to read the files

		Scanner textScanner1 = new Scanner(dictionary1);

		Scanner textScanner2 = new Scanner(dictionary2);

		Scanner textScanner3 = new Scanner(dictionary3);
		ArrayList<String> words1 = new ArrayList<>();
		ArrayList<String> words2 = new ArrayList<>();
		ArrayList<String> words3 = new ArrayList<>();
		
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
		Scanner choice = new Scanner(System.in);
		String output = test2.ChooseRandomWord(choice, 'B', "", words1, words2, words3);
		assertEquals(true, output.equals("levis") || output.equals("google") || output.equals("zara"));
	}
	@Test
	void test3() throws IOException {
		Hangman2 test2 = new Hangman2();
		FileWriter fw = new FileWriter("C:\\Users\\smahi\\Documents\\Leaderboard.txt");
		ArrayList output = test2.ScoreCalculation("levis", 7, 7, fw, true);
		ArrayList expected = new ArrayList<>();
		expected.add(true);
		expected.add(25);
		assertEquals(expected.get(0), output.get(0));
		assertEquals(expected.get(1), output.get(1));
	}

}
