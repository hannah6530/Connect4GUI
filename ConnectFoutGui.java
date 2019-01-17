package c4_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class ConnectFourGui extends JFrame{
	

    //private JButton reset;
	private JPanel jpMain;
	private JLabel currentPlayer;
	
	ConnectFourBoard jpBoard;
	
	private Player currPlayer;
	private Player player1;
	private Player player2;

	//private JButton reset;
	
	//private JPanel winner;
    //private JPanel draw;
	
	public ConnectFourGui(){
		player1 = new Player("p1", "P1");
		player2 = new Player("p2", "P2");
		currPlayer = player1;
		//reset = new JButton("Play Again");
		jpMain = new JPanel();
		
		
	    //reset = new JButton("Play Again");
		//winner = new JPanel();
		//draw = new JLabel("BOARD IS FULL... DRAW");

		
		
		/*
		currentPlayer = new JLabel("Current player: " + currPlayer);
		jpMain.add(currentPlayer);
		*/
		
		jpMain.setLayout(new BorderLayout());
		
		
		jpBoard = new ConnectFourBoard();//initialize game board
		jpBoard.setBackground(Color.YELLOW);
		//add score board to jpMain BorderLayout.NORTH //jpMain.add(BorderLayout.NORTH, jpScoreBoard);
		jpMain.add(BorderLayout.CENTER, jpBoard);//add game board to jpMain BorderLayout.CENTER
		
		add(jpMain);
		setSize(700,700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/*
	
	private class ScoreBoard extends JPanel{
		
	}
	*/
	
	
	private class ConnectFourBoard extends JPanel implements GameBoardInterface, GamePlayerInterface, ActionListener{
		
		private JButton [][] board;
		private final int ROWS = 6;
		private final int COLS = 7;
		
		
		
		public ConnectFourBoard(){
			setLayout(new GridLayout(ROWS,COLS));
			board = new JButton[ROWS][COLS];
			displayBoard();
			
			//reset.addActionListener(this);
			
			
		}
	    
	    
		
		
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton btnClicked = (JButton) e.getSource();//find out which button was clicked

			/* find out how to get the bottom button from the same column */
			/* Then loop from bottom to top, if empty place symbol, else move upward */
			
			//if(btnClicked.getText() == "") { btnClicked.setText(currPlayer.getSymbol()) };
			
			btnClicked.setText(currPlayer.getSymbol());//place the marker on that button using setText
			
		
			
			btnClicked.setEnabled(false);//disable the button
			
			if(isWinner()){//check if currPlayer isWinner
				//display the currPlayer as winner
				//ask to play again yes/no
				//clearBoard();//clear board
				
				//add(winner);
			
				JOptionPane.showMessageDialog(null,"WINNER IS: "+currPlayer.getName());
				
			}
			else if(isFull()){//check if full
				//game over... show draw
				//ask to play again yes/no
				//clear board
				JOptionPane.showMessageDialog(null, "IS FULL... DRAW");
				//add(draw);
	
			}
			/*
			
			if(e.getSource() == reset) {
				
				clearBoard();
					
			}
			*/
			takeTurn();//swap players and update the display
			//JOptionPane.showMessageDialog(null, "Player= "+currPlayer.getName());
			}
		


	    @Override
		public void displayBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col] = new JButton();
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
					board[row][col].setFont(bigFont);
					board[row][col].addActionListener(this);
					board[row][col].setEnabled(true);
					add(board[row][col]);	
				}
			}
		}

		@Override
		public void clearBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col].setText("");//clear button text
					board[row][col].setEnabled(true);//re-enable
				}
			}
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isFull() {
			for(int row=0; row < board.length; row++){
				for(int col=0; col< board[row].length; col++){
					String cellContent = board[row][col].getText().trim();
					if(cellContent.isEmpty()){
						return false;//board has an empty slot... not full
					}
				}
			}
			
			return true;
		}

		@Override
		public boolean isWinner() {
			//check rows
			//check cols
			//check main diagonal
			//check secondary diagonal
			if(isWinnerInRow() || isWinnerInCol() || isWinnerInMainDiag() || isWinnerInSecDiag()){
				return true;
			}
			return false;
		}

		public boolean isWinnerInRow() {
			final int MIN_TO_WIN = 4;
			
			String symbol = currPlayer.getSymbol();
			for(int row=0; row < board.length; row++){
				int consecutiveMatchesInRow = 0; //reset on the next row
				for(int col=0; col< board[row].length; col++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						consecutiveMatchesInRow++;
					}
					else if(consecutiveMatchesInRow < MIN_TO_WIN){
						consecutiveMatchesInRow = 0; // reset if next is not same symbol
					}
					else {
						System.out.println("break here");
						break;
					}
				}
				if(consecutiveMatchesInRow >= MIN_TO_WIN){
					return true;
				}
			}
			return false;
		}

		public boolean isWinnerInCol() {
			final int MIN_TO_WIN = 4;
			
			String symbol = currPlayer.getSymbol();
			for(int col=0; col < board[0].length; col++){
				int consecutiveMatchesInRow = 0; //reset on the next column
				for(int row=0; row< board.length; row++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						consecutiveMatchesInRow++;
					}
					else if(consecutiveMatchesInRow < MIN_TO_WIN){
						consecutiveMatchesInRow = 0; // reset if next is not same symbol
					}
					else {
						break;
					}
				}
				if(consecutiveMatchesInRow >= MIN_TO_WIN){
					return true;
				}
			}
			return false;
		}

		public boolean isWinnerInMainDiag() {  // upward diagonal
			final int MIN_TO_WIN = 4;
			
			String symbol = currPlayer.getSymbol();
			for(int upDiag=3; upDiag <= 8; upDiag++) {
				int consecutiveMatchesInRow = 0; //reset on the next upward diagonal
				for(int row=0, col=upDiag; row <= upDiag; row++, col--) {
					if(row < board.length && col < board[0].length && col >= 0) {
						if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
							consecutiveMatchesInRow++;
						}
						else if(consecutiveMatchesInRow < MIN_TO_WIN){
							consecutiveMatchesInRow = 0; // reset if next is not same symbol
						}
						else {
							break;
						}
					}
				}
				if(consecutiveMatchesInRow >= MIN_TO_WIN){
					return true;
				}
			}
			return false;
		}
		

		

		public boolean isWinnerInSecDiag() {  // downward diagonal
			final int MIN_TO_WIN = 4;
			
			String symbol = currPlayer.getSymbol();
			for(int upDiag=3; upDiag <= 8; upDiag++) {
				int consecutiveMatchesInRow = 0; //reset on the next upward diagonal
				for(int row=0, col=row+(6-upDiag); row <= upDiag; row++, col++) {
					if(row < board.length && col < board[0].length && col >= 0) {
						if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
							consecutiveMatchesInRow++;
						}
						else if(consecutiveMatchesInRow < MIN_TO_WIN){
							consecutiveMatchesInRow = 0; // reset if next is not same symbol
						}
						else {
							break;
						}
					}
				}
				if(consecutiveMatchesInRow >= MIN_TO_WIN){
					return true;
				}
			}
			return false;
		}
		
		@Override
		public void takeTurn() {
			if(currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else{
				currPlayer = player1;
			}
		}
	
	}

}
