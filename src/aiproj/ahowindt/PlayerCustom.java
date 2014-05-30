/**
 * Used to aid in the winChecking functionality of our application
 * @param playerName: String value for player. Black or White
 * @param playerWin: whether or not this player has won
 * @param playerWinState: how this player has won. Loop, Tripod or Both
 * @param playerValue: the integer value corresponding to their string value. 1 or 2 
 * 
 * @author AHowindt and JMcLaren
 */

package aiproj.ahowindt;

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

	//Getters and Setters
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