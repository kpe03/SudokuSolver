//Solves a typical 9x9 sudoku puzzle.
//Contains a convertToInt method because all values in the puzzle are stored as char
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

public class SudokuSolver {

	public static void main(String[] args) {

		String fileName = "";						//filename
		Vector<Character> puzzleVector = new Vector<Character>();	//vector to hold puzzle data
		char[][] puzzleArray;						//array to hold/display puzzle
			
		System.out.println("Sudoku Solver\n");
		
		//command line input
		if(args.length == 1) {
			fileName = args[0];
		}
		//user input
		else {
			System.out.println("Enter a file name: ");
			Scanner input = new Scanner(System.in);
			fileName = input.nextLine();
		}
		//open file
		try {
			File inputFile = new File(fileName);
			Scanner f = new Scanner(inputFile);
			while(f.hasNext()) {
				//store the next number/character in puzzleVector
				puzzleVector.addElement(f.next().charAt(0));		
			}
			 
			//trim the vector and find the dimensions of the puzzle and instantiate 2D array with dimensions
			puzzleVector.trimToSize();
			double dimension = Math.sqrt((puzzleVector.capacity()));
			puzzleArray = new char[(int)dimension][(int)dimension];	
			
			//put vector into 2D array
			toArray(puzzleArray, puzzleVector, (int)dimension);		
			
			//display the array
			displayUnsolved(puzzleArray, (int)dimension);
			
			/********************
			 * Solve the puzzle *
			 *******************/
			long t1 = System.currentTimeMillis();	//timer

			//display when solved
			if(solvePuzzle(puzzleArray, (int)dimension)) {
				System.out.println("\nSolved successfully");
				displayArray(puzzleArray, (int)dimension);
			}

			else {
				System.out.println("Unsolvable board.");
			}
			
			//get final execution time
			long t2 = System.currentTimeMillis(); 	//timer
			System.out.println("The elapsed time  is " + (t2-t1)/1000. + " seconds.");

			
		}
		//exception
		catch (FileNotFoundException fnfe) {
			System.out.println("File not found.");
			System.exit(0);
		}
		
	}
	/**********************
	 * solvePuzzle method *
	 **********************/
	//recursive algorithm to solve a puzzle
	public static boolean solvePuzzle(char[][] array, int dimension) {
		
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
					return false;
				}
			}
		}
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
	//will return true if there are no problems
	public static boolean isValidPuzzle(char[][] array, int row, int column, int number, int dimension) {
		//check row
		for(int i = 0; i < array.length; i++) {
			if(array[row][i] == number) {
				return true;
			}
		}
		//check column
		for(int j = 0; j < array.length; j++) {
			if(array[j][column] == number) {
				return true;
			}
		}
		//check grid
		int squareRow = row - row % 3;
		int squareCol = column - column % 3;
		for(int k = squareRow; k < squareRow + 3; k++) {
			for(int h = squareCol; h < squareCol + 3; h++) {
				if(array[k][h] == number) {
					return true;
				}
			}
		}
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
	public static void displayArray(char[][] array, int dimension) {
		
		for(int i = 0; i < array.length; i++) {
			for(int j=0; j < array.length; j++) {
				System.out.printf("%2s", convertToInt(array[i][j]));
			}
			System.out.println();
		}
	}
	 
	public static void displayUnsolved(char[][] array, int dimension) {
		for(int i = 0; i < array.length; i++) {
			for(int j=0; j < array.length; j++) {
				System.out.printf("%2s", array[i][j]);
			}
			System.out.println();
		}
	}
}
