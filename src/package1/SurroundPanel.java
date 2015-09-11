package package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SurroundPanel extends JPanel {
	
	/**JButton array to initialize the board**/
	private JButton[][] board;
	
	/**Instance of the Surround game that does most of the background work**/
	private SurroundGame game;
	
	/**JMenuItem to start a new Game if the user desires**/
	private JMenuItem newGameItem;
	
	/**JMenuItem to exit out of the Game itself**/
	private JMenuItem quitItem;
	
	/**JMenuBar for the drop down resources**/
	private JMenuBar menuBar;
	
	/**The Menu Item to add the NewGame, and Quit item into the GUI**/
	private JMenu menu;
	
	/**Text area to keep track of each Players Wins**/
	private JTextArea winCounter;
	
	/**Text Area to say which Players Turn it is**/
	private JTextArea playerTrack;
	
	private Dimension dim;
	
	private ButtonListener listener;
	
	private JPanel center, top, bottom, southTextArea, east;
	
	private ImageIcon[] icons;
	
	private int selectedSize;
	
	private int selectedPlayers;
	
	private int firstTurn;
	
	private JTextArea winnerCount[];
	
	private Cell[] winsTracker = new Cell[10];
	
	
	/******************************************************************
	 * Constructor for the Surround Panel Inherits the attributes
	 * of a JPanel. Enables the user to interact with the SurroundGame.
	 * @param quitItem
	 * @param newGameItem
	 *****************************************************************/
		public SurroundPanel(JMenuItem quitItem,JMenuItem newGameItem){
			try{
				this.addIcons();
			}catch(MalformedURLException exception){
				exception.printStackTrace();
			}
			
			
			this.quitItem = quitItem;
			this.newGameItem = newGameItem;
			
			startUpConditions();
			
			game = new SurroundGame(selectedSize,selectedPlayers, firstTurn);
			winCounter = new JTextArea(1,10);
			playerTrack = new JTextArea(1,10);
			
			playerTrack.setText(game.toString());
			
			menuBar = new JMenuBar();
			menu = new JMenu("Options");
			
			winCounter.setFont(new Font("Serif", Font.BOLD, 24));
			playerTrack.setFont(new Font("Serif", Font.BOLD, 24));
			
			southTextArea = new JPanel();
			center = new JPanel();
			top = new JPanel();
			bottom = new JPanel();
			
			setLayout(new BorderLayout());
			
//			board = new JButton[selectedSize][selectedSize];
			
			listener = new ButtonListener();
			
			center.setLayout(new GridLayout(selectedSize,
					selectedSize,3,2));
			southTextArea.setLayout(new GridLayout(1,2,50,0));
			
			dim = new Dimension(30,30);
			
			Buttons();
			setCells();
			
			quitItem.addActionListener(listener);
			newGameItem.addActionListener(listener);
			
			menu.add(quitItem);
			menu.add(newGameItem);
			menuBar.add(menu);
			
			top.add(menuBar);
			bottom.add(winCounter);
			bottom.add(playerTrack);
			
			southTextArea.add(winCounter);
			southTextArea.add(playerTrack);
			
			JLabel title = new JLabel("Surround");
			top.add(title);			
			
			center.setBackground(Color.LIGHT_GRAY);
			add(setWinPanel(),BorderLayout.EAST);
			add(center, BorderLayout.CENTER);
			add(top, BorderLayout.NORTH);
			add(bottom, BorderLayout.SOUTH);
			add(southTextArea,BorderLayout.SOUTH);
			setPreferredSize(new Dimension(1000,800));
		}

		private void Buttons() {
			board = new JButton[selectedSize][selectedSize];
			for(int row = 0; row < selectedSize; row++){
				for(int col = 0; col < selectedSize; col++){
					board[row][col] = new JButton("");
					board[row][col].setPreferredSize(dim);
				
					board[row][col].addActionListener(listener);
					center.add(board[row][col]);
				}
			}
		}
		
		private void createBoard(){
			center.removeAll();
			startUpConditions();
			game = new SurroundGame(selectedSize,selectedPlayers,
					firstTurn);
			center.setLayout(new GridLayout(selectedSize,
					selectedSize,3,2));
			Buttons();
			center.revalidate();
			center.repaint();
			
		}
		
		
		private void addIcons() throws MalformedURLException{
			
			URL url1 = new URL(
					"http://imageshack.com/a/img661/3996/WvLYyY.png");
			URL url2 = new URL(
					"http://imageshack.com/a/img538/3466/GN136H.png");
			URL url3 = new URL(
					"http://imageshack.com/a/img537/6084/c4YH04.png");
			URL url4 = new URL(
					"http://imageshack.com/a/img538/438/LE45HQ.png");
			URL url5 = new URL(
					"http://imageshack.com/a/img905/5369/EF7ClE.png");
			URL url6 = new URL(
					"http://imageshack.com/a/img537/1483/90oh1M.png");
			URL url7 = new URL(
					"http://imageshack.com/a/img537/7053/MQHWpW.png");
			URL url8 = new URL(
					"http://imageshack.com/a/img540/2675/kwg3F4.png");
			URL url9 = new URL(
					"http://imageshack.com/a/img673/2/bCuGcb.png");
			URL url10 = new URL(
					"http://imageshack.com/a/img633/3541/UzTbhW.png");
			
			icons = new ImageIcon[11];
			icons[0] = new ImageIcon(url1);
			icons[1] = new ImageIcon(url2);
			icons[2] = new ImageIcon(url3);
			icons[3] = new ImageIcon(url4);
			icons[4] = new ImageIcon(url5);
			icons[5] = new ImageIcon(url6);
			icons[6] = new ImageIcon(url7);
			icons[7] = new ImageIcon(url8);
			icons[8] = new ImageIcon(url9);
			icons[9] = new ImageIcon(url10);
		}
		
		private void setCells(){
			for(int i=0; i<10;i++){
				winsTracker[i]= new Cell(0);
			}
		}
		
		private JPanel setWinPanel(){
			
			east = new JPanel();
			east.setLayout(new GridLayout(10,1,5,5));
			
			winnerCount = new JTextArea[10];
			
			for(int i=0;i<10;i++){
				winnerCount[i] = new JTextArea(1,10);
				east.add(winnerCount[i]);
			}
			east.setBackground(Color.LIGHT_GRAY);
			return east;
		}
		
		public String setWins(int Player){
			//the index of the array is the player
			//for instance player 1 is the index 0.
			int winsUpdater = winsTracker[Player].getPlayer();
			winsUpdater++;
			
			for(int i = 0; i<winsTracker.length;i++){
				if(i==Player){

					winsTracker[Player].setPlayer(winsUpdater);
					Player++;
					return "Player "+Player+"'s wins " +winsUpdater;
				}
				
			}
			return "null";
		}
		

		private void displayBoard() {
			Cell [][] tempBoard = game.getBoard();
//			ArrayList<Integer> Dead = game.getDecease();
			
			for (int r = 0; r < selectedSize; r++)
				for (int c = 0; c < selectedSize; c++) 
					for(int i = 0; i < game.numberOfPlayers(); i++){
							if (tempBoard[r][c].getPlayer() == i){
//							board[r][c].setText((""
//									+tempBoard[r][c].getPlayer()));
								board[r][c].setIcon(getIcon
									(tempBoard[r][c].getPlayer()));
							}
							
							if(game.inTrouble(r, c) != null){
								board[r][c].setBackground(
									game.inTrouble(r, c));
							}
						}
		}

		private Icon getIcon(int Player){
			for(int i = 0; i<icons.length; i++){
				if(Player==i){
					return icons[i];
				}
			}
			return null;
		}
			
		//Don't need to call Display board, or reset
		//reset it done when the game is constructed
		//Display board is done only when the user inputs a valid
		//JButton press.
		/**************************************************************
		 this methods handles all of the dialog boxes for the panel
		**************************************************************/		
		private void startUpConditions(){
			String strSize = JOptionPane.showInputDialog(null, 
					"Pick a Board Size between 3-30.");
			try{
				selectedSize = Integer.parseInt(strSize);
				
			if(selectedSize < 2||selectedSize > 30){
				selectedSize = 10;
				JOptionPane.showMessageDialog(null, "The Game has"
				+ " defaulted to a Board Size of 10.");
				}
			
			}catch(NumberFormatException exception){

				JOptionPane.showMessageDialog(null, "The Game has"
				+ " defaulted to a Board Size of 10."+
				" Because you input characters other than numbers.");
				selectedSize = 10;				
			}

	
		//selecting the number of players within the game
		//verifying a valid input for the Players
		//It's awfully boring to play by yourself after all.
			String strPlay = JOptionPane.showInputDialog(null, 
					"Pick number of players between 2-10");
			try{
				if(strPlay==null){
					JOptionPane.showMessageDialog(null, "The game"
					+" has defaulted to 2 players.");
					selectedPlayers = 2;
				}
//				if(input == JOptionPane.CANCEL_OPTION){
//					System.exit(1);
//				}
				if(strPlay!=null){
					selectedPlayers = Integer.parseInt(strPlay);
					
					if(selectedPlayers < 2||selectedPlayers > 10){
						selectedPlayers = 2;
					JOptionPane.showMessageDialog(null, "The game"
							+" has defaulted to 2 players."
							+" Because you input a number that "
							+"was out of bounds.");
					}
				}
			}catch(NumberFormatException exception){
				JOptionPane.showMessageDialog(null,"You input "
					+ "something that was not a number."
					+ " The game has defaulted to 2 players.");
				selectedPlayers = 2;
			}
			
			String goFirst = JOptionPane.showInputDialog(null, "Pick"
			+ " who will go first.");
			
			try{
				if(goFirst==null){
					JOptionPane.showMessageDialog(null, "The game"
					+" has defaulted to Player 1s turn.");
					this.firstTurn = 0;
				}
				if(goFirst!=null){
					 this.firstTurn = Integer.parseInt(goFirst);
				}
			}catch(NumberFormatException exception){
				JOptionPane.showMessageDialog(null, "You input "
				+ "something other than a number."
				+ " The Game has defaulted to player 1s turn.");
				this.firstTurn = 0;
			}finally{
				JOptionPane.showMessageDialog(null, "The game"
					+" will now begin.");
			}
			
		}
		
		/**************************************************************
		 this method handles all of the Listener events and resulting 
		 actions. Currently newGameItem does not work correctly
		 It will not reset the JButtions or the size/player values.
		**************************************************************/	
		private class ButtonListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e){
				
				JComponent fired = (JComponent)e.getSource();
				
				if(fired == quitItem){
					System.exit(1);
				}
				
				if(fired==newGameItem){
					createBoard();
				}
							
				for (int r = 0; r < selectedSize; r++) 
					for (int c = 0; c < selectedSize; c++)
						if (board[r][c] == fired){
							if(game.select(r,c)){
								
//								game.nextPlayer();
								displayBoard();
								
//								board[r][c].setBackground(
//										game.inTrouble(r, c));
								game.isWinner();
								playerTrack.setText(game.toString());
							}
						}
				if (game.getGameStatus() == GameStatus.CATS) {
					JOptionPane.showMessageDialog(null, 
							"The game is a draw!"
							+ "\n The game will now reset");
					createBoard();
					
				}
				
				if (game.getGameStatus() == GameStatus.WINNER) {
					int temp = game.getPlayer() + 1;
					JOptionPane.showMessageDialog(null, 
							"Player" + temp + 
							" has won the game!"
							+ "\n The game will now reset");
					
					int winner = game.isWinner();
					if(winner>=0){
						winnerCount[winner].setText(setWins(winner));
					}
					createBoard();
				}
				
				// need to find a way to make this accurate currently using deceased
				if(game.getGameStatus() == GameStatus.REMOVED){
					int temp = game.getDecease().get(0) + 1;
					JOptionPane.showMessageDialog(null, 
							"Player" + temp + "was"
									+ " surrounded, and will be"
									+ "removed"
							+ "\n The game will continue");
				}
			}
		}
}


