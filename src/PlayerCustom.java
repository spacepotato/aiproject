public class PlayerCustom {

	protected String playerName;
	protected boolean playerWin;
	protected String playerWinState;
	protected int playerValue;

	public PlayerCustom(String name) {
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
	
	public int getPlayerValue(){
		return this.playerValue;
	}
	
	public void setPlayerValue(int playerValue){
		this.playerValue = playerValue;
	}
}
