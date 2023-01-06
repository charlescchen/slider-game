import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.*;

/**
 * GUI for Slider Game
 * 
 * @author Charles Chen
 * @version 1.0
 * @since January 19, 2021
 */
public class SliderGame extends JFrame implements ActionListener {

	// Variables
	private JPanel mainMenu, leaderboardMenu, selectionMenu, playBoard, gameBoard;
	private JButton[][] btn;
	private JButton play, quickPlay, leaderboard, instructions, startGame, resetBoard;
	private JButton returnHomeSelection, returnHomeGame, returnHomeLeaderboard, searchLeaderboard;
	private JComboBox<String> leaderboardSelection;
	private JLabel title, selectionTitle, nameTitle, sizeTitle, leaderboardTitle, moveDisplay;
	private JLabel nameDisplay, searchTitle, authorTitle, dateTitle;
	private JTextField nameInput, sizeInput;
	private Font f = new Font("Trebuchet MS", Font.BOLD, 36);
	private Font f2 = new Font("Trebuchet MS", Font.PLAIN, 30);
	private int boardLength;
	private int emptyI;
	private int emptyJ;
	private int moves;
	private String playerName;

	// Constructor
	public SliderGame() {
		super("Slider Game"); // Set title of window

		try { // Use the cross platform look and feel
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}

		// Adjust Window
		setSize(750, 750);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Main Menu Panel
		mainMenu = new JPanel();
		mainMenu.setBackground(Color.ORANGE);
		mainMenu.setVisible(true);
		mainMenu.setLayout(null);

		// Leaderboard Panel
		leaderboardMenu = new JPanel();
		leaderboardMenu.setBackground(Color.ORANGE);
		leaderboardMenu.setVisible(true);
		leaderboardMenu.setLayout(null);

		// Selection Panel
		selectionMenu = new JPanel();
		selectionMenu.setBackground(Color.ORANGE);
		selectionMenu.setVisible(true);
		selectionMenu.setLayout(null);

		// Playing Board
		playBoard = new JPanel();
		playBoard.setBackground(Color.ORANGE);
		playBoard.setVisible(true);
		playBoard.setLayout(null);

		// Displays game name on main menu
		title = new JLabel("Slider Game");
		title.setBounds(225, 25, 300, 50);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(f);
		mainMenu.add(title);
		
		// Displays author on main menu
		authorTitle = new JLabel("Charles Chen");
		authorTitle.setBounds(440, 600, 300, 50);
		authorTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		authorTitle.setFont(f2);
		mainMenu.add(authorTitle);
		
		// Displays date on main menu
		dateTitle = new JLabel("January 2021");
		dateTitle.setBounds(440, 640, 300, 50);
		dateTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		dateTitle.setFont(f2);
		mainMenu.add(dateTitle);
		
		// Displays title on leaderboard menu
		leaderboardTitle = new JLabel("Leaderboard");
		leaderboardTitle.setBounds(225, 25, 300, 50);
		leaderboardTitle.setHorizontalAlignment(SwingConstants.CENTER);
		leaderboardTitle.setFont(f);
		leaderboardMenu.add(leaderboardTitle);
		
		// Displays search title on leaderboard menu
		searchTitle = new JLabel("Board Size");
		searchTitle.setBounds(225, 125, 300, 50);
		searchTitle.setHorizontalAlignment(SwingConstants.CENTER);
		searchTitle.setFont(f);
		leaderboardMenu.add(searchTitle);
		
		// Displays drop down menu on leaderboard menu
		String[] sizeOptions = {"2", "3", "4", "5", "6", "7"};
		leaderboardSelection = new JComboBox<String>(sizeOptions);
		leaderboardSelection.setBounds(225, 200, 300, 50);
		leaderboardSelection.setFont(f);
		leaderboardSelection.setBackground(Color.WHITE);
		leaderboardMenu.add(leaderboardSelection);
		
		// Return Home Button on leaderboard menu
		returnHomeLeaderboard = new JButton("Home");
		returnHomeLeaderboard.addActionListener(this);
		returnHomeLeaderboard.setFont(f2);
		returnHomeLeaderboard.setBackground(Color.WHITE);
		returnHomeLeaderboard.setForeground(Color.BLUE);
		returnHomeLeaderboard.setBounds(10, 10, 125, 50);
		leaderboardMenu.add(returnHomeLeaderboard);
		
		// Search Button on leaderboard menu
		searchLeaderboard = new JButton("Search");
		searchLeaderboard.addActionListener(this);
		searchLeaderboard.setFont(f);
		searchLeaderboard.setBackground(Color.WHITE);
		searchLeaderboard.setForeground(Color.BLUE);
		searchLeaderboard.setBounds(250, 350, 250, 75);
		leaderboardMenu.add(searchLeaderboard);

		// Displays title on selection menu
		selectionTitle = new JLabel("Adjust Your Game");
		selectionTitle.setBounds(200, 25, 350, 50);
		selectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
		selectionTitle.setFont(f);
		selectionMenu.add(selectionTitle);

		// Displays name selection on selection menu
		nameTitle = new JLabel("Enter your name:");
		nameTitle.setBounds(175, 100, 400, 50);
		nameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		nameTitle.setFont(f2);
		selectionMenu.add(nameTitle);

		// Displays name input field on selection menu
		nameInput = new JTextField("MAX 12 CHARS");
		nameInput.setBounds(175, 175, 400, 50);
		nameInput.setHorizontalAlignment(SwingConstants.CENTER);
		nameInput.setFont(f2);
		nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		selectionMenu.add(nameInput);

		// Displays size selection on selection menu
		sizeTitle = new JLabel("Board Length:");
		sizeTitle.setBounds(175, 250, 400, 50);
		sizeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sizeTitle.setFont(f2);
		selectionMenu.add(sizeTitle);

		// Displays size input field on selection menu
		sizeInput = new JTextField("2-7");
		sizeInput.setBounds(225, 325, 300, 50);
		sizeInput.setHorizontalAlignment(SwingConstants.CENTER);
		sizeInput.setFont(f2);
		sizeInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		selectionMenu.add(sizeInput);

		// Start Game Button (from selection menu)
		startGame = new JButton("Start Game");
		startGame.addActionListener(this);
		startGame.setFont(f);
		startGame.setBackground(Color.WHITE);
		startGame.setForeground(Color.BLUE);
		startGame.setBounds(200, 475, 350, 75);
		selectionMenu.add(startGame);

		// Play Button
		play = new JButton("Play");
		play.addActionListener(this);
		play.setFont(f2);
		play.setBackground(Color.WHITE);
		play.setForeground(Color.BLUE);
		play.setBounds(250, 125, 250, 60);
		mainMenu.add(play);

		// Quick Play Button
		quickPlay = new JButton("Quick Play");
		quickPlay.addActionListener(this);
		quickPlay.setFont(f2);
		quickPlay.setBackground(Color.WHITE);
		quickPlay.setForeground(Color.BLUE);
		quickPlay.setBounds(250, 210, 250, 60);
		mainMenu.add(quickPlay);

		// Leaderboard Button
		leaderboard = new JButton("Leaderboard");
		leaderboard.addActionListener(this);
		leaderboard.setFont(f2);
		leaderboard.setBackground(Color.WHITE);
		leaderboard.setForeground(Color.BLUE);
		leaderboard.setBounds(250, 295, 250, 60);
		mainMenu.add(leaderboard);

		// Instructions Button
		instructions = new JButton("Instructions");
		instructions.addActionListener(this);
		instructions.setFont(f2);
		instructions.setBackground(Color.WHITE);
		instructions.setForeground(Color.BLUE);
		instructions.setBounds(250, 380, 250, 60);
		mainMenu.add(instructions);

		// Return Home Button on Selection Menu
		returnHomeSelection = new JButton("Home");
		returnHomeSelection.addActionListener(this);
		returnHomeSelection.setFont(f2);
		returnHomeSelection.setBackground(Color.WHITE);
		returnHomeSelection.setForeground(Color.BLUE);
		returnHomeSelection.setBounds(10, 10, 125, 50);
		selectionMenu.add(returnHomeSelection);

		this.add(mainMenu);
		setVisible(true);
	}

	/**
	 * Starts the game with the given parameters.
	 * 
	 * @param bLength Length of the board
	 * @param pName   Name of the player
	 */
	private void playGame(int bLength, String pName) {
		// Initialize Variables
		boardLength = bLength;
		playerName = pName;
		moves = 0;
		btn = new JButton[boardLength][boardLength];

		// Initialize Game Board
		gameBoard = new JPanel(new GridLayout(boardLength, boardLength));
		gameBoard.setBackground(Color.BLACK);
		gameBoard.setBounds(140, 10, 600, 600);

		// Displays player name
		nameDisplay = new JLabel(pName);
		nameDisplay.setBounds(240, 620, 500, 50);
		nameDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		nameDisplay.setFont(f2);
		playBoard.add(nameDisplay);

		// Displays moves
		moveDisplay = new JLabel("Moves: " + moves);
		moveDisplay.setBounds(240, 660, 500, 50);
		moveDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		moveDisplay.setFont(f2);
		playBoard.add(moveDisplay);

		// Return Home Button on Playing Board
		returnHomeGame = new JButton("Home");
		returnHomeGame.addActionListener(this);
		returnHomeGame.setFont(f2);
		returnHomeGame.setBackground(Color.WHITE);
		returnHomeGame.setForeground(Color.BLUE);
		returnHomeGame.setBounds(10, 10, 115, 50);
		playBoard.add(returnHomeGame);

		// Button to Reset Board on Playing Board
		resetBoard = new JButton("Reset");
		resetBoard.addActionListener(this);
		resetBoard.setFont(f2);
		resetBoard.setBackground(Color.WHITE);
		resetBoard.setForeground(Color.BLUE);
		resetBoard.setBounds(10, 70, 115, 50);
		playBoard.add(resetBoard);

		// Add buttons to the game board
		int counter = 1;
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				btn[i][j] = new JButton(Integer.toString(counter));
				btn[i][j].setFont(f);
				btn[i][j].setBackground(Color.PINK);
				btn[i][j].addActionListener(this);
				gameBoard.add(btn[i][j]);
				counter++;
			}
		}

		playBoard.add(gameBoard);

		// Set empty space
		btn[boardLength - 1][boardLength - 1].setBackground(Color.BLACK);
		btn[boardLength - 1][boardLength - 1].setText("");
		btn[boardLength - 1][boardLength - 1].setEnabled(false);
		emptyI = boardLength - 1;
		emptyJ = boardLength - 1;

		// Shuffle the board
		shuffle();

		// Add the playing board to the screen
		this.add(playBoard);
	}

	/**
	 * Updates the JLabel displaying the number of moves.
	 */
	private void updateMoves() {
		moveDisplay.setText("Moves: " + moves);
	}

	/**
	 * Resets the board and game.
	 */
	private void resetBoard() {
		enableBoard();
		shuffle();
		moves = 0;
		updateMoves();
	}

	/**
	 * Disables the game board.
	 */
	private void disableBoard() {
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				btn[i][j].setEnabled(false);
			}
		}
	}

	/**
	 * Enables the game board.
	 */
	private void enableBoard() {
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				btn[i][j].setEnabled(true);
			}
		}
		btn[emptyI][emptyJ].setEnabled(false);
	}

	/**
	 * Shuffles the game board by performing 10000 legal swaps.
	 */
	private void shuffle() {
		int numValidSwaps = 0;
		int nextEmptyIndex = 0;
		int randomMove = 0;
		int[] possibleI = new int[4];
		int[] possibleJ = new int[4];

		for (int i = 0; i < 10000; i++) {
			// Check if tile above empty space is on board
			if (onBoard(emptyI - 1, emptyJ)) {
				possibleI[nextEmptyIndex] = emptyI - 1;
				possibleJ[nextEmptyIndex] = emptyJ;
				numValidSwaps++;
				nextEmptyIndex++;
			}

			// Check if tile below empty space is on board
			if (onBoard(emptyI + 1, emptyJ)) {
				possibleI[nextEmptyIndex] = emptyI + 1;
				possibleJ[nextEmptyIndex] = emptyJ;
				numValidSwaps++;
				nextEmptyIndex++;
			}

			// Check if tile to the right of empty space is on board
			if (onBoard(emptyI, emptyJ + 1)) {
				possibleI[nextEmptyIndex] = emptyI;
				possibleJ[nextEmptyIndex] = emptyJ + 1;
				numValidSwaps++;
				nextEmptyIndex++;
			}

			// Check if tile to the left of empty space is on board
			if (onBoard(emptyI, emptyJ - 1)) {
				possibleI[nextEmptyIndex] = emptyI;
				possibleJ[nextEmptyIndex] = emptyJ - 1;
				numValidSwaps++;
			}

			// Select a random move
			randomMove = (int) (Math.random() * numValidSwaps);
			swap(possibleI[randomMove], possibleJ[randomMove]);

			// Reset Variables
			numValidSwaps = 0;
			nextEmptyIndex = 0;
		}

		// If the shuffle results in a solved board, another shuffle is performed
		// (mainly an issue on 2x2 board)
		if (isSolved()) {
			shuffle();
		}

	}

	/**
	 * Checks if the game board is solved and returns the result.
	 * 
	 * @return True if the board is solved, false otherwise
	 */
	private boolean isSolved() {
		// If the empty space isn't in bottom right corner, we instantly know the board
		// isn't solved
		if (emptyI != boardLength - 1 || emptyJ != boardLength - 1) {
			return false;
		}

		// We only start checking buttons one by one if empty space is in bottom right
		// corner:
		// Check every row except for last row
		int counter = 1;
		for (int i = 0; i < boardLength - 1; i++) {
			for (int j = 0; j < boardLength; j++) {
				if (Integer.parseInt(btn[i][j].getText()) != counter) {
					return false;
				}
				counter++;
			}
		}

		// Check last row, but don't check last column (empty space)
		for (int j = 0; j < boardLength - 1; j++) {
			if (Integer.parseInt(btn[boardLength - 1][j].getText()) != counter) {
				return false;
			}
			counter++;
		}
		return true;
	}

	/**
	 * Checks if the location specified by the parameters is on the board.
	 * 
	 * @param row The row of the position to be checked
	 * @param col The column of the position to be checked
	 * @return True if the position is on the board, false otherwise
	 */
	private boolean onBoard(int row, int col) {
		if (row < 0 || col < 0) {
			return false;
		} else if (row >= boardLength || col >= boardLength) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Moves the specified tile or collection of tiles to the empty space, if
	 * possible.
	 * 
	 * @param row The row of the tile to be moved
	 * @param col The column of the tile to be moved
	 */
	private void movePiece(int row, int col) {
		String emptyLocation = connectsToEmptySpace(row, col);

		if (emptyLocation.equals("adjacent")) {
			swap(row, col);
			moves++;
			return;
		} else if (emptyLocation.equals("none")) {
			return;
		}

		Stack<Integer> tileI = new Stack<Integer>();
		Stack<Integer> tileJ = new Stack<Integer>();

		if (emptyLocation.equals("right")) {
			for (int j = col; j < emptyJ; j++) {
				tileI.add(row);
				tileJ.add(j);
			}
		} else if (emptyLocation.equals("left")) {
			for (int j = col; j > emptyJ; j--) {
				tileI.add(row);
				tileJ.add(j);
			}
		} else if (emptyLocation.equals("above")) {
			for (int i = row; i > emptyI; i--) {
				tileI.add(i);
				tileJ.add(col);
			}
		} else { // When emptyLocation is equal to "below"
			for (int i = row; i < emptyI; i++) {
				tileI.add(i);
				tileJ.add(col);
			}
		}

		while (!(tileI.empty())) {
			swap(tileI.pop(), tileJ.pop());
		}
		moves++;
	}

	/**
	 * Swaps the tile at the specified position with the empty space.
	 * 
	 * @param row The row of the tile to be swapped with empty space
	 * @param col The column of the tile to be swapped with empty space
	 */
	private void swap(int row, int col) {
		btn[emptyI][emptyJ].setEnabled(true);
		btn[emptyI][emptyJ].setBackground(Color.PINK);
		btn[emptyI][emptyJ].setText(btn[row][col].getText());

		btn[row][col].setEnabled(false);
		btn[row][col].setBackground(Color.BLACK);
		btn[row][col].setText("");
		emptyI = row;
		emptyJ = col;
	}

	/**
	 * Checks if the tile at the specified position is directly adjacent to the
	 * empty space.
	 * 
	 * @param row The row of the tile to be checked
	 * @param col The column of the tile to be checked
	 * @return True if the tile is directly adjacent to the empty space, false
	 *         otherwise
	 */
	private boolean isAdjacent(int row, int col) {
		if (row == emptyI) {
			if (col == emptyJ + 1) { // To the right of empty space
				return true;
			} else if (col == emptyJ - 1) { // To the left of empty space
				return true;
			}
		} else if (col == emptyJ) {
			if (row == emptyI + 1) { // Under empty space
				return true;
			} else if (row == emptyI - 1) { // Over empty space
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the location of the empty space, relative to the specified position.
	 * 
	 * @param row Row of position to be compared to empty space
	 * @param col Column of position to be compared to empty space
	 * @return "adjacent", "above", "below", "right", "left", or "none", relative to
	 *         the specified position
	 */
	private String connectsToEmptySpace(int row, int col) {
		if (isAdjacent(row, col)) {
			return "adjacent";
		} else if (row == emptyI) {
			if (col < emptyJ) {
				return "right";
			} else {
				return "left";
			}
		} else if (col == emptyJ) {
			if (row < emptyI) {
				return "below";
			} else {
				return "above";
			}
		} else {
			return "none";
		}
	}

	/**
	 * Checks if the input from the nameInput and sizeInput JTextFields is valid.
	 * 
	 * @return True if the input is valid, false otherwise
	 */
	private boolean validateInput() {
		int n = 0;
		String errorMessage = "";
		boolean validInput = true;

		// Validate the name input
		if (nameInput.getText().equals("")) {
			errorMessage += "Name cannot be empty.\n";
			nameInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		} else if (nameInput.getText().contains(" ")) {
			errorMessage += "Name cannot contain spaces.\n";
			nameInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		} else if (nameInput.getText().contains(";")) {
			errorMessage += "Name cannot contain semicolons.\n";
			nameInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		} else if (nameInput.getText().contains(":")) {
			errorMessage += "Name cannot contain colons.\n";
			nameInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		} else if (nameInput.getText().length() > 12) {
			errorMessage += "Name cannot be greater than 12 characters.\n";
			nameInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		} else {
			nameInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		}

		// Validate the board size input
		try {
			n = Integer.parseInt(sizeInput.getText());
			if (n < 2 || n > 7) {
				errorMessage += "Board size must be 2-7.";
				sizeInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
				validInput = false;
			} else {
				sizeInput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
			}
		} catch (Exception e) {
			errorMessage += "Board size must be a number.";
			sizeInput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			validInput = false;
		}

		// Display error message if the input is invalid
		if (!validInput) {
			JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}

		return validInput;
	}

	/**
	 * Updates the files by storing the player information into the respective file.
	 * 
	 * @param playerInfo The information to be stored
	 */
	private void updateLeaderboard(String playerInfo) {
		String fileName = boardLength + "x" + boardLength + ".txt";
		ArrayList<String> entries = new ArrayList<String>();
		String input = "";

		try {
			// Add playerInfo to the file
			FileWriter fw = new FileWriter(fileName, true);
			PrintWriter filePrinter = new PrintWriter(fw);
			filePrinter.println(playerInfo);
			filePrinter.close();

			// Read all info
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			input = br.readLine();
			while (input != null) {
				entries.add(input);
				input = br.readLine();
			}
			br.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}

		String[] names = new String[entries.size()];
		int[] moves = new int[entries.size()];
		
		for (int i = 0; i < entries.size(); i++) {
			String[] line = entries.get(i).split(";");
			names[i] = line[0];
			moves[i] = Integer.parseInt(line[1]);
		}

		// Sort the arrays
		sortEntries(names, moves);
		
		// Overwrite old data with sorted data
		try {
			FileWriter fw2 = new FileWriter(fileName);
			PrintWriter overwrite = new PrintWriter(fw2);
			for (int i = 0; i < names.length; i++) {
				overwrite.println(names[i] + ";" + moves[i]);
			}
			overwrite.close();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}

	}

	/**
	 * Modified Selection Sort algorithm so that moves are arranged from least to
	 * greatest and names are arranged as well.
	 * 
	 * @param nameArray The array of player names
	 * @param moveArray The array of moves
	 */
	private void sortEntries(String[] nameArray, int[] moveArray) {
		int n = moveArray.length;

		for (int i = 0; i < n - 1; i++) {
			// Find smallest element
			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (moveArray[j] < moveArray[minIndex]) {
					minIndex = j;
				}
			}

			// Swap smallest element with current element
			int temp = moveArray[minIndex];
			moveArray[minIndex] = moveArray[i];
			moveArray[i] = temp;

			// Swap entries in name array as well
			String tempString = nameArray[minIndex];
			nameArray[minIndex] = nameArray[i];
			nameArray[i] = tempString;
		}

	}
	
	/**
	 * Generate a String containing the leaderboard for the top 3 players.
	 * 
	 * @param n The length of the board
	 * @return A String containing the information of the top 3 players
	 */
	private String generateLeaderboard(int n) {
		String input = "";
		String returnString = "";
		String fileName = n + "x" + n + ".txt";
		ArrayList<String> entries = new ArrayList<String>();
		
		try {
			// Add entries to the ArrayList
			FileReader leaderboardFile = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(leaderboardFile);
			
			input = reader.readLine();
			while (input != null) {
				entries.add(input);
				input = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// No entries yet for this board size
			return "1) No Entries Yet\n2) No Entries Yet\n3) No Entries Yet";
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
		
		if (entries.size() == 1) {
			String[] info = entries.get(0).split(";");
			returnString += "1) " + info[0] + ": " + info[1] + "\n";
			returnString += "2) No Entries Yet\n3) No Entries Yet";
		} else if (entries.size() == 2) {
			for (int i = 1; i <= 2; i++) {
				String[] info = entries.get(i - 1).split(";");
				returnString += i + ") " + info[0] + ": " + info[1] + "\n";
			}
			returnString += "3) No Entries Yet";
		} else { // When entries.size() >= 3
			for (int i = 1; i <= 3; i++) { // We only want top 3 entries
				String[] info = entries.get(i - 1).split(";");
				returnString += i + ") " + info[0] + ": " + info[1] + "\n";
			}
		}
		
		return returnString;
	}

	/**
	 * Called when an action is performed
	 */
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				if (e.getSource() == btn[i][j]) {
					movePiece(i, j);
					updateMoves();
					if (isSolved()) {
						disableBoard();
						updateLeaderboard(playerName + ";" + moves);
						String winMessage = "You win!\n\nCheck the leaderboard menu from the main menu to see ";
						winMessage += "if you made the leaderboard.";
						JOptionPane.showMessageDialog(this, winMessage, "Congratulations!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}

		if (e.getSource() == instructions) {
			String message = "Welcome to Slider Game!\n\nTo play the game, simply click on a tile to slide it. You can slide ";
			message += "multiple tiles at the same time if possible.\nYou win when all the tiles are in order from least to ";
			message += "greatest with 1 in the top left corner. Order the tiles so\nthat you increase by one with each tile to ";
			message += "the right of 1, and go onto the next row and repeat.\n\nClick \"Play\" to choose your board size and ";
			message += "name or click \"Quick Play\" to get started right away with a 4x4\ngame board.";
			JOptionPane.showMessageDialog(this, message, "Instructions", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == quickPlay) {
			mainMenu.setVisible(false);
			playGame(4, "Player");
		} else if (e.getSource() == play) {
			mainMenu.setVisible(false);
			add(selectionMenu);
		} else if (e.getSource() == startGame) {
			if (validateInput()) {
				selectionMenu.setVisible(false);
				playGame(Integer.parseInt(sizeInput.getText()), nameInput.getText());
			}
		} else if (e.getSource() == returnHomeSelection) {
			dispose();
			new SliderGame();
		} else if (e.getSource() == returnHomeGame) {
			dispose();
			new SliderGame();
		} else if (e.getSource() == resetBoard) {
			resetBoard();
		} else if (e.getSource() == leaderboard) {
			mainMenu.setVisible(false);
			add(leaderboardMenu);
		} else if (e.getSource() == returnHomeLeaderboard) {
			dispose();
			new SliderGame();
		} else if (e.getSource() == searchLeaderboard) {
			String size = (String) leaderboardSelection.getSelectedItem();
			String ranking = generateLeaderboard(Integer.parseInt(size));
			JOptionPane.showMessageDialog(this, ranking, "Leaderboard for " + size + "x" + size, JOptionPane.PLAIN_MESSAGE);
		}

	}

	public static void main(String[] args) {
		new SliderGame();
	}

}
