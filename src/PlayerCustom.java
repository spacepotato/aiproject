public class PlayerCustom {

	protected String playerName;
	protected boolean playerWin;
	protected String playerWinState;
	protected int playerValue;

	public PlayerCustom(String name) {
		this.playerName = name;
		
		if(name.equals("Black"))
			this.playerValue = 2;
		else
			this.playerValue = 1;
		
		this.playerWin = false;
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
