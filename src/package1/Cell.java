package package1;

public class Cell {
	private int currentPlayer;
	
	Cell(int pNumber){
		currentPlayer = pNumber;
	}
	
	public void setPlayer(int pNumber){
		currentPlayer = pNumber;
	}
	
	public int getPlayer(){
		return currentPlayer;
	}	

	
}
