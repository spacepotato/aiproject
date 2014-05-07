import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class FenceMaster extends JFrame implements MouseListener{
	
	boolean replaceRule = true;
	GameBoardDisplay gb = new GameBoardDisplay();
	String player;
	int clickNum;
	WinState gameWinState;
	JScrollPane history;
	
	public FenceMaster(){
		
		String input = JOptionPane.showInputDialog("Please enter board size:");
		if(input == null){
			JOptionPane.showMessageDialog(null, "Incorrect input. Game exiting.");
			System.exit(0);
		}
		
		int n = Integer.parseInt(input);
		int width = 68*(2*n-1) + 40;
		int height = 80*(2*n-1) - 20*(2*n-2) + 60;
		
		input = JOptionPane.showInputDialog("Who will go first: Black or White?");
		if(input == null){
			JOptionPane.showMessageDialog(null, "Inccorect input. Game exiting.");
			System.exit(0);
		}
		player = input;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Menu");
		getJMenuBar().add(menu);
		menu.add(new JMenuItem("Help"));
		menu.add(new JMenuItem("New Game"));
		
//		history = new JScrollPane();
//		history.setOpaque(true);
//		history.setSize(200, height);
//		history.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		gb.fillGameBoardDisplay(n);
		gameWinState = new WinState(gb);
		add(gb);
		
		setTitle("FenceMaster 1.0 by Aston Howindt");
		setSize(width, height);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.darkGray);
		setLocationRelativeTo(null);
		setVisible(true);

		
//		add(history, BorderLayout.EAST);
		addMouseListener(this);
	}

	public static void main(String []arg){
		new FenceMaster();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		boolean goAgain = false;
		boolean finished = false;
		clickNum += 1;

		System.out.println(x +", " + y + ", " + clickNum);
		
		Coordinate hexClicked = gb.findHex(x-8, y-54);
		
		System.out.println(hexClicked.getRow() + ", " + hexClicked.getColumn());
		
		if(hexClicked.getRow() == 999 && hexClicked.getColumn() == 999){
			goAgain = true;
		}
		
		if(!replaceRule && !goAgain){
			if(gb.getBoard().get(hexClicked.getRow()).get(hexClicked.getColumn()).getColour() != '-'){
				JOptionPane.showMessageDialog(null, "Incorrect move: Cannot replace another persons piece after the first turn.");
				goAgain = true;
			}
		}
		if(!goAgain){
			gb.updateGameBoard(player.charAt(0),hexClicked.getRow(), hexClicked.getColumn());
			repaint();
			
			gameWinState.updateWinState(hexClicked.getRow(), hexClicked.getColumn(), player);
			
			if(gameWinState.getWin()){
				JOptionPane.showMessageDialog(null, gameWinState.getWinPlayer() + " has won by using " + gameWinState.getWinMethod());
				finished = true;
			}
			else if(gameWinState.getDraw()){
				JOptionPane.showMessageDialog(null, "Game is a draw.");
				finished = true;
			}
			
			System.out.println(player);
			if(player.charAt(0) == 'B'){
				player = "White";
			}
			else if(player.charAt(0) == 'W'){
				player = "Black";
			}
		}
		
		if(finished){
			int answer = JOptionPane.showConfirmDialog(null, "Would you like another game?");
			if(answer == JOptionPane.YES_OPTION){
				gb.reset();
				gameWinState.reset();
				repaint();
				replaceRule = true;
				player = JOptionPane.showInputDialog("Who will start this time: Black or White?");
				clickNum = 0;
				
			}
			else if(answer == JOptionPane.NO_OPTION || answer == JOptionPane.CLOSED_OPTION || answer == JOptionPane.CANCEL_OPTION){
				JOptionPane.showMessageDialog(null, "Thank you for playing!");
				System.exit(0);
			}
		}

		if(clickNum == 2){
			replaceRule = false;
		}
		
		gameWinState.resetWinState();
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
		
}
	




	