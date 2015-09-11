package package1;

import java.awt.Color;
import java.util.ArrayList;

public class SurroundGame {
	//Clean up unnecessary methods and instance vars.
	/******************************************************************
	 * 2d array of Cell objects that hold the value of the player that
	 * currently holds the Cell.	 
	 *****************************************************************/
	private Cell[][] board;
	
	/******************************************************************
	 * GameStatus instance variable that keeps track of the current
	 * end conditions of the game.	 
	 *****************************************************************/
	private GameStatus status;
	
	/******************************************************************
	 * This is the current player.	 
	 *****************************************************************/
	private int playerIndex;
	
	/******************************************************************
	 * This is the total number of players in the game. This value is
	 * set using a dialog box in the gui. 
	 *****************************************************************/
	private int playersNumber;
	
	
	/******************************************************************
	 * Array that holds values for each player in the game.
	 * It allows the players to be incremented in the correct order. 
	 *****************************************************************/
	private ArrayList<Integer> players;
	
	/******************************************************************
	 * Array that holds the players who have been defeated
	 * Allows the GUI to set the Icons appropriately.
	 *****************************************************************/
	private ArrayList<Integer> deceased;
	
	/******************************************************************
	 * This is the size of the game board. This variable is set using
	 * a dialog box in the gui.
	 *****************************************************************/
	private int size;
	
	/******************************************************************
	 * Getter for the board 2d array.	 
	 *****************************************************************/	
	public Cell[][] getBoard(){
		return board;
	}
	
	/******************************************************************
	 * Getter for the current status of the game. 
	 *****************************************************************/
	public GameStatus getGameStatus(){
		return status;
	}
	
	/******************************************************************
	 * Default constructor for the Surround Game class. 
	 *****************************************************************/
	public SurroundGame(){
		playersNumber = 2;
		size = 10;
		playerIndex = 0;
		board = new Cell[10][10];
		status = GameStatus.IN_PROGRESS;
//		playerIndex = 0;
		reset();		
	}
	

	/******************************************************************
	 * Constructor for Surround Game class that will create the board
	 * and number of players based on user import from gui.	 
	 *****************************************************************/
	public SurroundGame(int pSize, int pPlayersNumber, int firstTurn){
		playersNumber = pPlayersNumber;
		size = pSize;
		playerIndex = 0;
		board = new Cell[size][size];
		status = GameStatus.IN_PROGRESS;	
		reset();
		playerIndex = firstTurn-1;
		
		if(playerIndex<0||playerIndex>=players.size()){
			playerIndex = 0;
		}
		
		playerIndex = players.get(playerIndex);
		
	}
	
	public int getPlayer(){
		return playerIndex;
	}
	
	public int numberOfPlayers(){
		return playersNumber;
	}
	
	/******************************************************************
	 this methods pulls the next player int from the array of players
	 and sets the instance for currentPlayer to this new value. 
	 It adjusts for outofbounds by resetting currentPlayer to 0 when
	 the end of the array of players is reached.
	******************************************************************/	
	public void nextPlayer(){
		playerIndex++;
		
		if(playerIndex >= players.size()){
			playerIndex = 0;
			playerIndex = players.get(playerIndex);
		}
		else{
			playerIndex = players.get(playerIndex);
		}
		
	}
	
	
	
	/******************************************************************
	 * Checks to make sure that a selected Cell is not currently
	 * assigned to a player. If Cell is empty sets cell value 
	 * to currentPlayer	 
	 *****************************************************************/
	public Boolean select(int row, int col){
	
		Cell currentTurn;
		currentTurn = board[row][col];
		
		if (currentTurn.getPlayer() == -1){
			board[row][col].setPlayer(playerIndex);
			return true;
		}		
		
		else{
			return false;			
		}		
	}
	
	/******************************************************************
	 * Checks if a cell is surrounded on all sides by other players.
	 * Adds functionality of wrapping the 2d array board around 
	 * a sphere	 
	 *****************************************************************/
	private Boolean determineType(int row, int col,
			int rowTemp, int colTemp){
		int newRow = rowTemp;
		int newCol = colTemp;
		
		if(newRow < 0){					
			newRow = (((newRow % size) + size) % size);
		}
		else if(newRow >= size){
			newRow = newRow % size;
		}
		
		if(newCol < 0){
			newCol = (((newCol % size) + size) % size);
		}
		else if(newCol >= size){
			newCol = newCol % size;
		}			
		
		Cell temp1 = board[row][col];
		
		Cell temp2 = board[newRow][newCol];
		
		if(temp2.getPlayer() != -1 &&
				temp2.getPlayer() != temp1.getPlayer() && temp1.getPlayer() != -1){
			return true;
		}
		else
			return false;
				
	}
	
	/******************************************************************
	 * Checks if conditions for a player to lose are met. If a player
	 * has lost (is completely surrounded), sets GameStatus to WINNER
	 * Also checks if game is a draw.	 
	 *****************************************************************/
	public int isWinner(){
		status = GameStatus.IN_PROGRESS;		
		nextPlayer();
		for (int row = 0; row < size; row++)     
			for (int col = 0; col < size; col++) {
				
				if(isCats()){
					status =  GameStatus.CATS;
					return -2;
					// currently will try to remove players more than once if surrounded again
				}
				if(board[row][col].getPlayer() != -3){			
				 if((determineType(row, col, row - 1, col - 1)
						 && determineType(row, col, row - 1, col)
						 && determineType(row, col, row - 1, col + 1)
						 && determineType(row, col, row, col + 1)
						 && determineType(row, col, row + 1, col + 1)
						 && determineType(row, col, row + 1, col)
						 && determineType(row, col, row + 1, col - 1)
						 && determineType(row, col, row, col - 1))){
					 	
					 	if(players.size() > 2){
					 		
					 		RemovePlayer(board[row][col].getPlayer());
					 		//
					 		System.out.println(players.toString());
					 		System.out.println(deceased.toString());
					 		status = GameStatus.REMOVED;
					 		board[row][col].setPlayer(-3);
					 		return -3;
					 	}
					 	status = GameStatus.WINNER;	
					 	RemovePlayer(board[row][col].getPlayer());
					 	playerIndex = players.get(0);
					 	return players.get(0);
				 }
			}
			}
		return -1;
	}
	
	public ArrayList<Integer> getPlayers(){
		return players;
	}
	
	public ArrayList<Integer> getDecease(){
		return deceased;
	}
	
	public int getDead(int Player){
		for(int i = 0; i<deceased.size(); i++){
			if(deceased.get(i)==Player){
				return deceased.get(i);
			}
		}
		return -5;
	}
	
	/******************************************************************
	 * Changes color of a cell based on how close the cell is to be 
	 * surrounded.
	 * @return Color or null
	 *****************************************************************/
	public Color inTrouble(int row, int col){		
		int Trouble = 0;
		
	if(board[row][col].getPlayer() != -1){	
		
		if(determineType(row, col, row - 1, col - 1)){
			Trouble++;
		}
		if(determineType(row, col, row - 1, col)){
			Trouble ++;
		}
		if(determineType(row, col, row - 1, col + 1)){
			Trouble++;
		}
		if(determineType(row, col, row, col + 1)){
			Trouble++;
		}
		if(determineType(row, col, row + 1, col + 1)){
			Trouble++;
		}
		if(determineType(row, col, row - 1, col)){
			Trouble++;
		}
		if(determineType(row, col, row + 1, col - 1)){
			Trouble++;
		}
		if(determineType(row, col, row, col - 1)){
			 Trouble++;
		}	
		
		if(Trouble >= 3 && Trouble <= 6){
			return Color.YELLOW;
		}
		
		if(Trouble > 6 && Trouble <=8){
			return Color.RED;
		}
		if(Trouble > 0){
			return Color.GREEN;
		}
		else
			return null;
	}
	else{	
		return null;
	}
	}
	
	public void RemovePlayer(int PlayerNumber){
		
		for(int i =0; i<players.size();i++){
			
			if(players.get(i)==PlayerNumber){
				deceased.add(0,players.get(i));
				players.remove(i);
			}
			
		}
	}
	
	
	/******************************************************************
	 * Method that calculates the conditions for a draw game. If all
	 * Cells are filled with player values.	 
	 *****************************************************************/
	private boolean isCats(){
		int numberOfUsedCells = 0;
		
		for (int row = 0; row < size; row++)     
			for (int col = 0; col < size; col++) {		 
				if(board[row][col].getPlayer() != - 1){
					numberOfUsedCells++;
				}
			}
		if(numberOfUsedCells == (size * size)){
			return true;
		}
		else
			return false;
			
	}
	
	/******************************************************************
	 * Resets board to default empty state and creates the array of
	 * players based on input from gui. 
	 *****************************************************************/		
	public void reset() {
		
	players = new ArrayList<Integer>();
	deceased = new ArrayList<Integer>();
	
		for (int row = 0; row < size; row++) 
			for (int col = 0; col < size; col++) 
				board[row][col] = new Cell(-1);
		for (int i = 0; i < playersNumber ; i++)
			players.add(i, i);
		//currentPlayer = players.get(PlayerIndex);
	}
	
	public String toString(){
		int currentTurn = getPlayer()+1;
		return "It is player " + currentTurn+"'s turn.";
	}
	
	public static void main(String args[]){
		SurroundGame g = new SurroundGame(10,5,1);
		
		System.out.println("The current Players are...");
		System.out.println(""+g.getPlayers());
		
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());
		g.nextPlayer();
		System.out.println("It is player: "+ g.getPlayer());

	}
	
}