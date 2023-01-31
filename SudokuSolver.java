//Solves a typical 9x9 sudoku puzzle.
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

public class SudokuSolver {

	public static void main(String[] args) {

		String fileName = "";						//filename
		Vector<Character> puzzleVector = new Vector<Character>();	//vector to hold puzzle data
		char[][] puzzleArray;						//2D array to hold/display puzzle
			
		System.out.println("Sudoku Solver\n");
		
		//get file name from command line input if entered
		if(args.length == 1) {
			fileName = args[0];
		}
		//otherwise, get file name from user input
		else {
			System.out.println("Enter a file name: ");
			Scanner input = new Scanner(System.in);
			fileName = input.nextLine();
		}
		//open file with sudoku puzzle input
		try {
			File inputFile = new File(fileName);
			Scanner f = new Scanner(inputFile);
			//get each number in the input file in while loop
			while(f.hasNext()) {
				//put each number/character in puzzleVector as a char type
				puzzleVector.addElement(f.next().charAt(0));		
			}
			 
			//trim the vector and find the dimensions of the puzzle
			puzzleVector.trimToSize();
			double dimension = Math.sqrt((puzzleVector.capacity()));
			//create array to hold the puzzle
			puzzleArray = new char[(int)dimension][(int)dimension];	
			
			//put vector into 2D array
			toArray(puzzleArray, puzzleVector, (int)dimension);		
			
			//display the array
			displayUnsolved(puzzleArray, (int)dimension);
			
			/********************
			 * Solve the puzzle *
			 *******************/
			long t1 = System.currentTimeMillis();	//timer for experiments

			//display when solved
			if(solvePuzzle(puzzleArray, (int)dimension)) {
				System.out.println("\nSolved successfully");
				displayArray(puzzleArray, (int)dimension);
			}
			//otherwise, display board is unsolvable
			else {
				System.out.println("Unsolvable board.");
			}
			
			
			long t2 = System.currentTimeMillis(); 	//timer for experiments
			System.out.println("The elapsed time  is " + (t2-t1)/1000. + " seconds."); //display elapsed time

			
		}
		//exception catching
		catch (FileNotFoundException fnfe) {
			System.out.println("File not found.");
			System.exit(0);
		}
		
	}
	/**********************
	 * solvePuzzle method *
	 **********************/
	//recursive algorithm to solve a sudoku puzzle
	public static boolean solvePuzzle(char[][] array, int dimension) {
		//go through each element of the puzzle with the nested for loops
		for(int row = 0; row < dimension; row++) {
			for(int column = 0; column < dimension; column++) {
				
				//if tile is empty, start entering numbers.
				if(array[row][column] == '-') {
					for(char k = '1'; k <= '9'; k++) {
						if(!isValidPuzzle(array, row, column, k, dimension) && !isCompletePuzzle(array)) {
							array[row][column] = k;
							
							//recursive call-- if puzzle is solved return true
							if(solvePuzzle(array, dimension)) {
								return true;
							}
							//if puzzle isn't solved backtrack and put '-' on it.
							else {
								array[row][column] = '-';
							}
						}
					}
					//return false is puzzle isn't solved
					return false;
				}
			}
		}
		//return true when puzzle is solved.
		return true;
	}
	/***************************
	 * isCompletePuzzle method *
	 ***************************/
	//check that every spot of the puzzle has a value
	public static boolean isCompletePuzzle(char[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				if(!Character.isLetterOrDigit(array[i][j])) {
					return false;
				}
			}
		}
		return true;
	}
	/************************
	 * isValidPuzzle method *
	 ************************/
	//will return true if puzzle is solved correctly 
	public static boolean isValidPuzzle(char[][] array, int row, int column, int number, int dimension) {
		//check row for no repeated numbers
		for(int i = 0; i < array.length; i++) {
			if(array[row][i] == number) {
				return true;
			}
		}
		//check column for no repeated numbers
		for(int j = 0; j < array.length; j++) {
			if(array[j][column] == number) {
				return true;
			}
		}
		//check grid for no repeated numbers
		int squareRow = row - row % 3;
		int squareCol = column - column % 3;
		for(int k = squareRow; k < squareRow + 3; k++) {
			for(int h = squareCol; h < squareCol + 3; h++) {
				if(array[k][h] == number) {
					return true;
				}
			}
		}
		//returns false if puzzle is invalid
		return false;
		
	}	
	/***********************
	 * convertToInt method *
	 ***********************/
	//convert any char letters/numbers to integer values (base 10)
	public static int convertToInt(char value) {
		int temp = 0;
		
		if (Character.isDigit(value)) {
			temp = (int) value - '0';
			return temp;
		}
		else if (Character.isLetter(value)) {
			temp = (int)(value - 'A') + 10;
			return temp;
		}
		
		return value;
	}
	
	/******************
	 * toArray method *
	 ******************/
	//put Vector into 2-D array
	public static char[][] toArray(char[][] array, Vector<Character> vector, int dimension) {
		int k = 0;

		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				
				array[i][j] = vector.elementAt(k);
				k++;
					
			}
		}
		return array;
	}
	/*******************************************
	 * displayArray and displayUnsolved method *
	 *******************************************/
	//displays the solved puzzle (solved puzzles have char values instead of int, so they display differently)
	public static void displayArray(char[][] array, int dimension) {
		
		for(int i = 0; i < array.length; i++) {
			for(int j=0; j < array.length; j++) {
				//convert each char of the array to an int.
				System.out.printf("%2s", convertToInt(array[i][j]));
			}
			System.out.println();
		}
	}
	 //displays unsolved puzzle. (unsolved puzzles have their original int values still, so they display normally)
	public static void displayUnsolved(char[][] array, int dimension) {
		for(int i = 0; i < array.length; i++) {
			for(int j=0; j < array.length; j++) {
				System.out.printf("%2s", array[i][j]);
			}
			System.out.println();
		}
	}
}
