public class Player {

	protected String playerName;
	protected boolean playerWin;
	protected String playerWinState;

	public Player(String name) {
		this.playerName = name;
	}
	
	public void setPlayerWin(boolean win){
		this.playerWin = win;
	}
	
	public boolean getPlayerWin(){
		return this.playerWin;
	}
	
	public void setPlayerWinState(String winState){
		this.playerWinState = winState;
	}
	
	public String getPlayerWinState(){
		return this.playerWinState;
	}
	
	public char getPlayerChar(){
		return this.playerName.charAt(0);
	}
	
	public String getPlayerName(){
		return this.playerName;
	}
}
