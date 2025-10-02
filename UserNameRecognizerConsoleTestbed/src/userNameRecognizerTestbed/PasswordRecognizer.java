package userNameRecognizerTestbed;

/**
 * <p> Title: FSM-translated PasswordRecognizer. </p>
 * 
 * <p> Description: A demonstration of the mechanical translation of a Finite State Machine 
 * diagram into an executable Java program using the Password Recognizer. This version
 * requires at least one uppercase, one lowercase, one digit, and one allowed special character.
 * Password length must be between 8 and 16 characters. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2024 </p>
 * 
 * @author Karson Shin
 * @version 2.00  2025-09-07  Aligned with FSM boolean flags and semantic actions
 */

public class PasswordRecognizer {

	/**********************************************************************************************
	 * 
	 * Result attributes to be used for GUI applications where a detailed error message and a 
	 * pointer to the character of the error will enhance the user experience.
	 * 
	 *********************************************************************************************/

	public static String passwordRecognizerErrorMessage = "";     // The error message text
	public static String passwordRecognizerInput = "";            // The input being processed
	public static int passwordRecognizerIndexOfError = -1;        // The index of error location

	private static int state = 0;                  // The current FSM state value
	private static int nextState = 0;              // The next FSM state value
	private static boolean finalState = false;     // Is this state a final state?
	private static String inputLine = "";          // The input line
	private static char currentChar;               // The current character in the line
	private static int currentCharNdx;            // The index of the current character
	private static boolean running;                // Flag that specifies if the FSM is running

	// Semantic Action Flags 
	private static int charCounter = 0;           // Number of characters processed
	private static boolean upperCase = false;     // Flag if uppercase char present
	private static boolean lowerCase = false;     // Flag if lowercase char present
	private static boolean numericChar = false;   // Flag if numeric char present
	private static boolean specialChar = false;   // Flag if special char present
	private static boolean longEnough = false;    // Flag if minimum length reached
	private static boolean otherChar = false;     // Flag if invalid character found

	// Private method to display debugging data
	private static void displayDebuggingInfo() {
		if (currentCharNdx >= inputLine.length())
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
					((finalState) ? "       F   " : "           ") + "None");
		else
			System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state + 
					((finalState) ? "       F   " : "           ") + "  " + currentChar + "  " + nextState + "     " + charCounter);
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

	// Private helper method to test for special characters
	private static boolean isSpecial(char c) {
		return "~!@#$%^&*()_-{}[]|:,.?/".indexOf(c) >= 0;
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
	 * method for password validation.
	 * 
	 * @param input		The input string for the Finite State Machine
	 * @return			An output string that is empty if everything is okay or it is a String
	 * 						with a helpful description of the error
	 **********/
	public static String checkForValidPassword(String input) {

	    // Check for empty input
	    if (input.length() <= 0) {
	        passwordRecognizerIndexOfError = 0;
	        return "\n*** ERROR *** The password cannot be empty";
	    }

	    // Reset FSM variables
	    state = 0;
	    inputLine = input;
	    currentCharNdx = 0;
	    currentChar = input.charAt(0);
	    passwordRecognizerInput = input;
	    running = true;
	    nextState = -1;
	    finalState = false;
	    charCounter = 0;
	    upperCase = lowerCase = numericChar = specialChar = longEnough = otherChar = false;

	    System.out.println("\nCurrent Final Input  Next  Size");

	    // FSM loop
	    while (running) {
	        switch (state) {
	            case 0: // Single state FSM processing all characters
	                // Semantic Actions: classify char
	                if (Character.isUpperCase(currentChar)) {
	                    charCounter++;
	                    upperCase = true;
	                    nextState = 0;
	                } else if (Character.isLowerCase(currentChar)) {
	                    charCounter++;
	                    lowerCase = true;
	                    nextState = 0;
	                } else if (Character.isDigit(currentChar)) {
	                    charCounter++;
	                    numericChar = true;
	                    nextState = 0;
	                } else if (isSpecial(currentChar)) {
	                    charCounter++;
	                    specialChar = true;
	                    nextState = 0;
	                } else {
	                    otherChar = true;
	                    running = false; // invalid char
	                }

	                // Check length
	                if (charCounter >= 8) longEnough = true;
	                // REMOVE max length check
	                break;
	        }

	        if (running) {
	            displayDebuggingInfo();
	            moveToNextCharacter();
	            state = nextState;
	            finalState = (state == 0 && upperCase && lowerCase && numericChar && specialChar && longEnough && !otherChar);
	            nextState = -1;
	        }
	    }

	    displayDebuggingInfo();
	    System.out.println("The loop has ended.");

	    passwordRecognizerIndexOfError = currentCharNdx;
	    passwordRecognizerErrorMessage = "\n*** ERROR *** ";

	    // Check FSM final conditions and provide detailed error messages
	    if (finalState && currentCharNdx == input.length()) {
	        passwordRecognizerIndexOfError = -1;
	        passwordRecognizerErrorMessage = "";
	        return passwordRecognizerErrorMessage; // valid password
	    } else {
	        if (charCounter < 8) 
	            return "*** ERROR *** Password must be at least 8 characters long.\n";
	        if (!lowerCase) 
	            return "*** ERROR *** Password must contain at least one lowercase letter.\n";
	        if (!upperCase) 
	            return "*** ERROR *** Password must contain at least one uppercase letter.\n";
	        if (!numericChar) 
	            return "*** ERROR *** Password must contain at least one digit.\n";
	        if (!specialChar) 
	            return "*** ERROR *** Password must contain at least one special character (~!@#$%^&*()_-{}[]|:,.?/).\n";
	        if (otherChar) 
	            return "*** ERROR *** Password contains invalid characters.\n";
	        return "*** ERROR *** Invalid password.\n";
	    }
	}
}
