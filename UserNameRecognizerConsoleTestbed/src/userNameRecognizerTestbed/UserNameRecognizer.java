package userNameRecognizerTestbed;


public class UserNameRecognizer {
	/**
	 * <p> Title: FSM-translated UserNameRecognizer. </p>
	 * 
	 * <p> Description: A demonstration of the mechanical translation of Finite State Machine 
	 * diagram into an executable Java program using the UserName Recognizer. This version 
	 * requires the first character to be alphabetic, allows subsequent alphanumeric characters, 
	 * and supports '.', '_', or '-' separators between alphanumerics. </p>
	 * 
	 * <p> Copyright: Lynn Robert Carter © 2024 </p>
	 * 
	 * @author Karson Shin
	 * 
	 * @version 2.00	2025-09-07	New FSM rules implemented for HW1
	 */

	/**********************************************************************************************
	 * 
	 * Result attributes to be used for GUI applications where a detailed error message and a 
	 * pointer to the character of the error will enhance the user experience.
	 * 
	 */

	public static String userNameRecognizerErrorMessage = "";	// The error message text
	public static String userNameRecognizerInput = "";			// The input being processed
	public static int userNameRecognizerIndexofError = -1;		// The index of error location
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static boolean finalState = false;			// Is this state a final state?
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;						// The flag that specifies if the FSM is 
														// running
	private static int userNameSize = 0;				// A UserName may not exceed 16 characters

	// Private method to display debugging data
	private static void displayDebuggingInfo() {
		// Display the current state of the FSM as part of an execution trace
		if (currentCharNdx >= inputLine.length())
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
					((finalState) ? "       F   " : "           ") + "None");
		else
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
				((finalState) ? "       F   " : "           ") + "  " + currentChar + " " + 
				((nextState > 99) ? "" : (nextState > 9) || (nextState == -1) ? "   " : "    ") + 
				nextState + "     " + userNameSize);
	}

	// Private method to move to the next character within the limits of the input line
	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;
		}
	}

	// Character classification methods
	private static boolean isAlpha(char c) {
		return Character.isLetter(c);
	}

	private static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	private static boolean isSeparator(char c) {
		return c == '.' || c == '_' || c == '-';
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
	 * method.
	 * 
	 * @param input		The input string for the Finite State Machine
	 * @return			An output string that is empty if everything is okay or it is a String
	 * 						with a helpful description of the error
	 */
	public static String checkForValidUserName(String input) {
		// Check to ensure that there is input to process
		if (input.length() <= 0) {
			userNameRecognizerIndexofError = 0;	// Error at first character
			return "\n*** ERROR *** The input is empty";
		}

		// Initialize FSM
		state = 0;
		inputLine = input;
		currentCharNdx = 0;
		currentChar = input.charAt(0);
		userNameRecognizerInput = input;
		running = true;
		nextState = -1;
		userNameSize = 0;

		System.out.println("\nCurrent Final Input  Next  Date\nState   State Char  State  Size");

		// FSM execution loop
		while (running) {
			switch (state) {
			case 0: 
				// State 0: First character must be alphabetic
				if (isAlpha(currentChar)) {
					nextState = 1;
					userNameSize++;
				}
				else 
					running = false;
				break;

			case 1: 
				// State 1: Accept alphanumeric, or transition on separator
				if (isAlpha(currentChar) || isDigit(currentChar)) {
					nextState = 1;
					userNameSize++;
				}
				else if (isSeparator(currentChar)) {
					nextState = 2;
					userNameSize++;
				}
				else
					running = false;

				if (userNameSize > 16)
					running = false;
				break;

			case 2: 
				// State 2: Must be alphanumeric after separator
				if (isAlpha(currentChar) || isDigit(currentChar)) {
					nextState = 1;
					userNameSize++;
				}
				else 
					running = false;

				if (userNameSize > 16)
					running = false;
				break;
			}

			if (running) {
				displayDebuggingInfo();
				moveToNextCharacter();
				state = nextState;
				if (state == 1) finalState = true;
				nextState = -1;
			}
		}
		displayDebuggingInfo();

		System.out.println();

		// FSM halted, now produce error message or accept
		userNameRecognizerIndexofError = currentCharNdx;
		userNameRecognizerErrorMessage = "\n*** ERROR *** ";

		switch (state) {
		case 0:
			userNameRecognizerErrorMessage += "A UserName must start with an alphabetic character.\n";
			return userNameRecognizerErrorMessage;

		case 1:
			if (userNameSize < 4) {
				userNameRecognizerErrorMessage += "A UserName must have at least 4 characters.\n";
				return userNameRecognizerErrorMessage;
			}
			else if (userNameSize > 16) {
				userNameRecognizerErrorMessage += "A UserName must have no more than 16 characters.\n";
				return userNameRecognizerErrorMessage;
			}
			else if (currentCharNdx < input.length()) {
				userNameRecognizerErrorMessage += "A UserName character may only contain A-Z, a-z, 0-9, '.', '_', or '-'.\n";
				return userNameRecognizerErrorMessage;
			}
			else {
				userNameRecognizerIndexofError = -1;
				userNameRecognizerErrorMessage = "";
				return userNameRecognizerErrorMessage;
			}

		case 2:
			userNameRecognizerErrorMessage += "A UserName may not end with '.', '_' or '-'.\n";
			return userNameRecognizerErrorMessage;

		default:
			return "";
		}
	}
}