import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Vector;




public class checker extends Applet
{// define the whole board of the game 
	
	public void init() 
	{




		 setLayout(null);  // get layout by developer
	
		// gather all the component
		 CheckersCanvas board = new CheckersCanvas();
		 add(board); // create 2 button on the board
		
		 //Create New gane button
		 board.newGameButton.setBackground(Color.lightGray);
		 add(board.newGameButton);
	
		 // create resign button 
		 board.resignButton.setBackground(Color.lightGray);
		 add(board.resignButton);
		
		 // Message for the player
		 board.message.setForeground(Color.black);
		 board.message.setFont(new Font("Serif", Font.BOLD, 14));
		 add(board.message);
		
		 // bound method location on the board
		 board.setBounds(20,20,400,400); 
		 board.newGameButton.setBounds(430, 60, 100, 30);
		 board.resignButton.setBounds(430, 120, 100, 30);
		 board.message.setBounds(70,420, 330, 30);
	}




}// end board game define 








class CheckersCanvas extends Canvas implements ActionListener, MouseListener 
{// open class for the game operation 




Button resignButton;   // Current player can resign by clicking this button.
Button newGameButton;  // This button starts a new game.  It is enabled only when the current game has ended.
Label message;   // A label for displaying messages to the user.
CheckersData board;  // The data for the checkers board is kept here This board is also responsible for generating lists of legal moves.
boolean gameInProgress; // Is a game currently in progress?




	// the value is valid only when the game is say
int currentPlayer;      // Whose turn is it now?  The possible values are CheckersData.RED and CheckersData.BLACK.
int selectedRow, selectedCol;  // If the current player has selected a piece to move,
CheckersMove[] legalMoves;  // current player with legal move 




public CheckersCanvas() 
	{
     // Create the board and start the first game.
	 setBackground(Color.black);
	 addMouseListener(this);
	 setFont(new  Font("Serif", Font.BOLD, 14));
	 resignButton = new Button("Resign");
	 resignButton.addActionListener(this);
	 newGameButton = new Button("New Game");
	 newGameButton.addActionListener(this);
	 message = new Label("",Label.CENTER);
	 board = new CheckersData();
	 doNewGame();
	}




public void actionPerformed(ActionEvent evt) 
	{
	    // Respond to user's click on one of the two buttons.
	 Object src = evt.getSource();
	 if (src == newGameButton)
	    doNewGame();
	 else if (src == resignButton)
	    doResign();
	}




void doNewGame() 
	{ // Begin a new game.
		 if (gameInProgress == true) {
		        // This should not be possible, but it doens't 
		    message.setText("Finish the current game first!");
		    return;
		 }
		 board.setUpGame();   // Set up the pieces.
		 currentPlayer = CheckersData.RED;   // RED moves first.
		 legalMoves = board.getLegalMoves(CheckersData.RED);  // Get RED's legal moves.
		 selectedRow = -1;   // RED has not yet selected a piece to move.
		 message.setText("Red:  Make your move.");
		 gameInProgress = true;
		 newGameButton.setEnabled(false);
		 resignButton.setEnabled(true);
		 repaint();
	}




void doResign() 
	{
	     // Current player resigns.  Game ends.  Opponent wins.
	  if (gameInProgress == false) {
	     message.setText("There is no game in progress!");
	     return;
	  }
	  if (currentPlayer == CheckersData.RED)
	     gameOver("RED resigns. BLACK wins.");
	  else
	     gameOver("BLACK resigns. RED winds.");
	}




void gameOver(String str) 
	// start and end new game function
	{
	 message.setText(str);
	 newGameButton.setEnabled(true);
	 resignButton.setEnabled(false);
	 gameInProgress = false;
	}
 
void doClickSquare(int row, int col) 
	{
		// double check click 
		 for (int i = 0; i < legalMoves.length; i++)
		    if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
		       selectedRow = row;
		       selectedCol = col;
		       if (currentPlayer == CheckersData.RED)
		          message.setText("RED:  Make your move.");
		       else
		          message.setText("BLACK:  Make your move.");
		       repaint();
		       return;
		    }
		 
		 // select a piece before move 
		 if (selectedRow < 0) {
		     message.setText("Click the piece you want to move.");
		     return;
		 }
		 
		 // click on legal move
		 for (int i = 0; i < legalMoves.length; i++)
		    if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
		            && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
		       doMakeMove(legalMoves[i]);
		       return;
		    }
		 // message remind
		 message.setText("Click the square you want to move to.");
	}  // end do click square function 




void doMakeMove(CheckersMove move) 
	{
		// move function 
		 board.makeMove(move);
		 // check if piece can jump
		 if (move.isJump()) {
		    legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
		    if (legalMoves != null) {
		       if (currentPlayer == CheckersData.RED)
		          message.setText("RED:  You must continue jumping.");
		       else
		          message.setText("BLACK:  You must continue jumping.");
		       selectedRow = move.toRow;  
		       selectedCol = move.toCol;
		       repaint();
		       return;
		    }
		 }
		 
		 // next player turn 
		 if (currentPlayer == CheckersData.RED) {
		    currentPlayer = CheckersData.BLACK;
		    legalMoves = board.getLegalMoves(currentPlayer);
		    if (legalMoves == null)
		       gameOver("BLACK has no moves.  RED wins.");
		    else if (legalMoves[0].isJump())
		       message.setText("BLACK:  Make your move.  You must jump.");
		    else
		       message.setText("BLACK:  Make your move.");
		 }
		 else {
		    currentPlayer = CheckersData.RED;
		    legalMoves = board.getLegalMoves(currentPlayer);
		    if (legalMoves == null)
		       gameOver("RED has no moves.  BLACK wins.");
		    else if (legalMoves[0].isJump())
		       message.setText("RED:  Make your move.  You must jump.");
		    else
		       message.setText("RED:  Make your move.");
		 }
		 
		 // Set selectedRow = -1 to record that the player has not yet selected a piece to move. 
		 
		 selectedRow = -1;
		 
		 //automatically select to force the legal move 
		 
		 if (legalMoves != null) {
		    boolean sameStartSquare = true;
		    for (int i = 1; i < legalMoves.length; i++)
		       if (legalMoves[i].fromRow != legalMoves[0].fromRow
		                            || legalMoves[i].fromCol != legalMoves[0].fromCol) {
		           sameStartSquare = false;
		           break;
		       }
		    if (sameStartSquare) {
		       selectedRow = legalMoves[0].fromRow;
		       selectedCol = legalMoves[0].fromCol;
		    }
		 }
		 // Make sure the board is redrawn in its new state. 
		 repaint();
 
	}  // end doMakeMove function 




public void update(Graphics g) // update the gui everytime there a legal move is make
	{
	paint(g);
	}




public void paint(Graphics g) 
	{//open paint function 




			 // initialize the checker
			 for (int row = 0; row < 8; row++) {
			    for (int col = 0; col < 8; col++) {
			        if ( row % 2 == col % 2 )
			           g.setColor(new Color(70,70,70));
			        else
			           g.setColor(new Color(255,60,60));
			        g.fillRect(col*50,row*50, 50, 50);
			        switch (board.pieceAt(row,col)) {
			           case CheckersData.RED:
			              g.setColor(Color.red);
			              g.fillOval(5+col*50,5+row*50, 40, 40);
			              break;
			           case CheckersData.BLACK:
			              g.setColor(Color.black);
			              g.fillOval(5+col*50,5+row*50, 40, 40);
			              break;
			           case CheckersData.RED_KING:
			              g.setColor(Color.red);
			              g.fillOval(5+col*50,5+row*50, 40, 40);
			              g.setColor(Color.white);
			              g.drawString("K",21 + col*50, 29 + row*50);
			              break;
			           case CheckersData.BLACK_KING:
			              g.setColor(Color.black);
			              g.fillOval(5+col*50,5+row*50, 40, 40);
			              g.setColor(Color.white);
			              g.drawString("K", 21 + col*50, 29 + row*50);
			              break;
			        }// end switch
			    }// end 2ns loop 
			 }// end first loop
 
 	if (gameInProgress) 
 		{
		       // cyan is list of the legal move 
		    g.setColor(Color.cyan);
		    for (int i = 0; i < legalMoves.length; i++) {
		       g.drawRect(legalMoves[i].fromCol*50,legalMoves[i].fromRow*50, 50, 50);
		    }
		    
		    if (selectedRow >= 0) {
		       g.setColor(Color.white);
		       g.drawRect(selectedCol*50,selectedRow*50, 50, 50);
		       g.drawRect(selectedCol*50,selectedRow*50, 50, 50);
		       g.setColor(Color.green);
		       for (int i = 0; i < legalMoves.length; i++) {
		          if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow)
		             g.drawRect(legalMoves[i].toCol*50,legalMoves[i].toRow*50, 50, 50);
		       }
		    }
 		}
	}  // end paint function 




public Dimension getPreferredSize() 
	{
		// the size MUST be 400 by 400.
		return new Dimension(400, 400);
	}




public Dimension getMinimumSize() 
	{
		return new Dimension(400, 400);
	}




public void mousePressed(MouseEvent evt) 
	{
		 if (gameInProgress == false)
		    message.setText("Click \"New Game\" to start a new game.");
		 else {
		    int col = (evt.getX() - 2) / 50;
		    int row = (evt.getY() - 2) / 50;
		    if (col >= 0 && col < 8 && row >= 0 && row < 8)
		       doClickSquare(row,col);
		 }
	}




public void mouseReleased(MouseEvent evt) { }
public void mouseClicked(MouseEvent evt) { }
public void mouseEntered(MouseEvent evt) { }
public void mouseExited(MouseEvent evt) { }




}// end class game operation. 








class CheckersMove 
	{ // open checker move class




		int fromRow, fromCol;  // declare the from row and col
		int toRow, toCol;      // declare the move to row and col 
		
		CheckersMove(int r1, int c1, int r2, int c2) 
		{
		   // Constructor.  Just set the values of the instance variables.
		 fromRow = r1;
		 fromCol = c1;
		 toRow = r2;
		 toCol = c2;
		}


boolean isJump() 
		{
		  //test jump or move
		 return (fromRow - toRow == 2 || fromRow - toRow == -2);
		}
	}  // end class 




class CheckersData 
	{// open checker class data


public static final int	// declare the value of all the piece
        EMPTY = 0,
        RED = 1,
        RED_KING = 2,
        BLACK = 3,
        BLACK_KING = 4;


private int[][] board;  // board[r][c] is the contents of row r, column c.  


public CheckersData() 
		{
			// Constructor.  Create the board and set it up for a new game.
			board = new int[8][8];
			setUpGame();
		}


public void setUpGame() 
		{
			 // set up the board
			 for (int row = 0; row < 8; row++) {
			    for (int col = 0; col < 8; col++) {
			       if ( row % 2 == col % 2 ) {
			          if (row < 3)
			             board[row][col] = BLACK;
			          else if (row > 4)
			             board[row][col] = RED;
			          else
			             board[row][col] = EMPTY;
			       }
			       else {
			          board[row][col] = EMPTY;
			       }
			    }
			 }
		}  // end setUpGame function 


public int pieceAt(int row, int col) 
		{
	     	// Return the contents of the square in the specified row and column.
			return board[row][col];
		}


public void setPieceAt(int row, int col, int piece) 
		{
			// put piece at position
			board[row][col] = piece;
		}


public void makeMove(CheckersMove move) 
		{
			// move the piece around
			makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
		}


public void makeMove(int fromRow, int fromCol, int toRow, int toCol)
		{
				// check row and col to get the piece king
			 board[toRow][toCol] = board[fromRow][fromCol];
			 board[fromRow][fromCol] = EMPTY;
			 if (fromRow - toRow == 2 || fromRow - toRow == -2) {
			       // The move is a jump.  Remove the jumped piece from the board.
			    int jumpRow = (fromRow + toRow) / 2;  // Row of the jumped piece.
			    int jumpCol = (fromCol + toCol) / 2;  // Column of the jumped piece.
			    board[jumpRow][jumpCol] = EMPTY;
			 }
			 if (toRow == 0 && board[toRow][toCol] == RED)
			    board[toRow][toCol] = RED_KING;
			 if (toRow == 7 && board[toRow][toCol] == BLACK)
			    board[toRow][toCol] = BLACK_KING;
		}// end make move function 


public CheckersMove[] getLegalMoves(int player) 
		{
				     // the player turn is set up 
				
				 if (player != RED && player != BLACK)
				    return null;
				
				 int playerKing;  // The constant representing a King belonging to player.
				 if (player == RED)
				    playerKing = RED_KING;
				 else
				    playerKing = BLACK_KING;	
				
				 Vector moves = new Vector();  // Moves will be stored in this vector.
				
				 // checking for legal jump then if yes we move the vector move in 
				
				 for (int row = 0; row < 8; row++) {
				    for (int col = 0; col < 8; col++) {
				       if (board[row][col] == player || board[row][col] == playerKing) {
				          if (canJump(player, row, col, row+1, col+1, row+2, col+2))
				             moves.addElement(new CheckersMove(row, col, row+2, col+2));
				          if (canJump(player, row, col, row-1, col+1, row-2, col+2))
				             moves.addElement(new CheckersMove(row, col, row-2, col+2));
				          if (canJump(player, row, col, row+1, col-1, row+2, col-2))
				             moves.addElement(new CheckersMove(row, col, row+2, col-2));
				          if (canJump(player, row, col, row-1, col-1, row-2, col-2))
				             moves.addElement(new CheckersMove(row, col, row-2, col-2));
				       }
				    }
				 }
				 
				 // check to see the size of the move so we can put in the king piece
				 
				 if (moves.size() == 0) {
				    for (int row = 0; row < 8; row++) {
				       for (int col = 0; col < 8; col++) {
				          if (board[row][col] == player || board[row][col] == playerKing) {
				             if (canMove(player,row,col,row+1,col+1))
				                moves.addElement(new CheckersMove(row,col,row+1,col+1));
				             if (canMove(player,row,col,row-1,col+1))
				                moves.addElement(new CheckersMove(row,col,row-1,col+1));
				             if (canMove(player,row,col,row+1,col-1))
				                moves.addElement(new CheckersMove(row,col,row+1,col-1));
				             if (canMove(player,row,col,row-1,col-1))
				                moves.addElement(new CheckersMove(row,col,row-1,col-1));
				          }
				       }
				    }
				 }
	
				 // using array to put all the legal jump in 
				 
				 if (moves.size() == 0)
				    return null;
				 else {
				    CheckersMove[] moveArray = new CheckersMove[moves.size()];
				    for (int i = 0; i < moves.size(); i++)
				       moveArray[i] = (CheckersMove)moves.elementAt(i);
				    return moveArray;
				 }
		} // end checkermove function 


public CheckersMove[] getLegalJumpsFrom(int player, int row, int col) 
		{
				// store all the legal jump in array for both player
			 if (player != RED && player != BLACK)
			    return null;
			 int playerKing;  // The constant representing a King belonging to player.
			 if (player == RED)
			    playerKing = RED_KING;
			 else
			    playerKing = BLACK_KING;
			 Vector moves = new Vector();  // The legal jumps will be stored in this vector.
			 if (board[row][col] == player || board[row][col] == playerKing) {
			    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
			       moves.addElement(new CheckersMove(row, col, row+2, col+2));
			    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
			       moves.addElement(new CheckersMove(row, col, row-2, col+2));
			    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
			       moves.addElement(new CheckersMove(row, col, row+2, col-2));
			    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
			       moves.addElement(new CheckersMove(row, col, row-2, col-2));
			 }
			 if (moves.size() == 0)
			    return null;
			 else {
			    CheckersMove[] moveArray = new CheckersMove[moves.size()];
			    for (int i = 0; i < moves.size(); i++)
			       moveArray[i] = (CheckersMove)moves.elementAt(i);
			    return moveArray;
			 }
		}  // end getLegalMovesFrom()


private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) 
		{ 
				// check the jump is posible for each piece  
			 if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
			    return false;  // (r3,c3) is off the board.
			    
			 if (board[r3][c3] != EMPTY)
			    return false;  // (r3,c3) already contains a piece.
			    
			 if (player == RED) {
			    if (board[r1][c1] == RED && r3 > r1)
			       return false;  // Regular red piece can only move  up.
			    if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
			       return false;  // There is no black piece to jump.
			    return true;  // The jump is legal.
			 }
			 else {
			    if (board[r1][c1] == BLACK && r3 < r1)
			       return false;  // Regular black piece can only move downn.
			    if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
			       return false;  // There is no red piece to jump.
			    return true;  // The jump is legal.
			 }
		}  // end canJump()


private boolean canMove(int player, int r1, int c1, int r2, int c2) 
		{
			    // this function is only allow the player to move with possible jump 
			 if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
			    return false;  // (r2,c2) is off the board.
			    
			 if (board[r2][c2] != EMPTY)
			    return false;  // (r2,c2) already contains a piece.
			
			
			 if (player == RED) {
			    if (board[r1][c1] == RED && r2 > r1)
			        return false;  // Regualr red piece can only move down.
			     return true;  // The move is legal.
			 }
			 else {
			    if (board[r1][c1] == BLACK && r2 < r1)
			        return false;  // Regular black piece can only move up.
			     return true;  // The move is legal.
			 }
		}  // end canMove()




	}// end class check data
