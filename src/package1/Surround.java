package package1;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Surround {
		
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Surround");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			
		JMenuItem quitItem = new JMenuItem("Quit");
		JMenuItem newGameItem = new JMenuItem("New Game");
		
		SurroundPanel panel = new SurroundPanel(quitItem, newGameItem);
		frame.getContentPane().add(panel);
			
	
		frame.pack();
		frame.setVisible(true);
				
	}
	
	
	
}
