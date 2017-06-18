import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** 
 * Controls a fully functional game of classic checkers, WITH A TWIST. 
 * Multiple modes (versus AI, two player)
 * This version follows most of the checkers rules but with some exceptions
 * @author Zain Thaver, Sulaiman Ahmadi and Ryan Derry
 * @version June 2017
 */
public class MainCheckers extends JFrame implements ActionListener
{
 private JMenuItem newOption, exitOption, rulesMenuItem, aboutMenuItem,
 creditsMenuItem, controlsMenuItem, aiOption, twoPlayer;
 private BufferedImage playerOnePiece, playerTwoPiece, playerOneKing,
 playerTwoKing, checkersBackground, checkersBoard;
 private int playerOnePieces, playerTwoPieces, selCol, selRow,
 multiRow, multiCol, greatestPriority, greatestIndex,noOfJumps,
 singleMoves, animX, animY, animPiece, transX, transY, transPiece;
 private boolean multiJump, vsAI, animationOn, pieceSelected
 , transMultiJump;
 private ArrayList <Integer> movePriority, firstRow, firstCol, moveRow,
 moveCol;
 private DrawingPanel boardArea;
 private JFrame frame;
 private String musicFile;
 private Clip clip;
 protected int turn, board[][];
 private final int EMPTY = 0;
 private final int BLACK = -1;
 private final int WHITE = 1;
 private final int BLACK_KING = -2;
 private final int WHITE_KING = 2;

 /**
  * Creates the checkers Frame Application and all the options in the two
  * menus: Game and Help. Also loads up the images and the drawing panel 
  */
 public MainCheckers()
 {
  super ("'Classy' Chckers");
  this.setResizable(false);
  
  //Sets up the Game Menu items with action listeners
  newOption = new JMenuItem("New");
  newOption.addActionListener(this);
  exitOption = new JMenuItem("Exit");
  exitOption.addActionListener(this);
  aiOption = new JMenuItem("Play VS AI");
  aiOption.addActionListener(this);
  twoPlayer = new JMenuItem("2 Players");
  twoPlayer.addActionListener(this);

  // Set up the Help Menu items with action listeners
  JMenu helpMenu = new JMenu("FIDGET SPNNER");
  helpMenu.setMnemonic('H');
  rulesMenuItem = new JMenuItem("FIDGET SPNNER", 'R');
  rulesMenuItem.addActionListener(this);
  helpMenu.add(rulesMenuItem);
  controlsMenuItem = new JMenuItem("FIDGET SPNNER", 'I');
  controlsMenuItem.addActionListener(this);
  helpMenu.add(controlsMenuItem);
  aboutMenuItem = new JMenuItem("FIDGET SPNNER", 'A');
  aboutMenuItem.addActionListener(this);
  helpMenu.add(aboutMenuItem);
  creditsMenuItem = new JMenuItem("FIDGET SPNNER", 'C');
  creditsMenuItem.addActionListener(this);
  helpMenu.add(creditsMenuItem);
  JMenu gameMenus = new JMenu("Game");
  gameMenus.setMnemonic('G');

  // Add each MenuItem to the Game Menu
  JMenu gameMenu = new JMenu("Game");
  gameMenu.add(newOption);
  gameMenu.add (aiOption);
  gameMenu.add (twoPlayer);
  gameMenu.addSeparator();
  gameMenu.add(exitOption);
  
  // Adds the game Menu and the help Menu into the main menu
  JMenuBar mainMenu = new JMenuBar();
  mainMenu.add(gameMenu);
  mainMenu.add(helpMenu);

  // Set the menu bar for this frame to mainMenu
  setJMenuBar(mainMenu);

  // Loads the piece and board images with a try catch
  // Try catch prevents error in case the image is not found
  try
  {
   playerOnePiece = ImageIO.read(new File("fidgetspinner.jpg"));
   playerTwoPiece = ImageIO.read(new File("fidgetspinnertwoo.jpg"));
   playerOneKing = ImageIO.read(new File("fidgetspinnerKING.png"));
   playerTwoKing = ImageIO.read(new File("fidgetspinnertwooKING.png"));
   checkersBackground =ImageIO.read(new File("woodenbackground.jpg"));
   checkersBoard = ImageIO.read(new File("woodenboard.jpg"));
  } 
  catch (IOException e)
  {
   System.out.println ("Invalid File");
  }

  // Sets up the drawing panel starting area
  setLayout(new BorderLayout());
  boardArea = new DrawingPanel();
  add(boardArea, BorderLayout.CENTER);
  Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  setLocation((screen.width) / 4, (screen.height) / 8);
  
  // Starts the game mode in Player VS Player mode
  vsAI = false;
  musicFile = "LOUDEST VIDEO ON YOUTUBE! SHREK EAR RAPE.wav";
  music();
  newGame();
 }

 /**
  *  Handles all menu events such as new game, VS AI and two playermode
  *  @param event The option in the menu that triggers the methods below
  */
 @Override
 public void actionPerformed(ActionEvent event)
 {
  if (event.getSource() == newOption) // Selected "New"
  {
   newGame();
  }

  else if (event.getSource() == exitOption) // Selected "Exit"
  {
   System.exit(0);
  }
  
  else if (event.getSource() == aiOption) // Selected "Play VS AI"
  {
   vsAI = true;
   newGame();
  }
  
  else if (event.getSource() == twoPlayer) // Selected "2 Players"
  {
   vsAI = false;
   newGame();
  }

  else if (event.getSource() == rulesMenuItem) // Selected "Rules"
  {
   JOptionPane.showMessageDialog(this,
     "1. Black side goes first"
       + "\n\n2. Normal pieces can only jump diagonally "
       + "to the opposite side."
       + "\n\n3. Pieces that have reached the opposite "
       + "side will become kings."
       + "\n\n4. Kings can move diagonally in any"
       + " direction."
       + "\n\n5. Hostile pieces can only be taken if it"
       + " is diagonally in front of your piece."
       + "\n\n6. You can decide whether or not to take"
       + " a piece."
       + "\n\n7. If there is a multi jump, you must jump."
       + "\n\n8. You win if you eliminate all opposite "
       + "colour pieces."
       + "\n\n9. You can also win if you trap the enemy's"
       + " pieces."
       + "\n\n10. Have fidget spinners", "Rules",
      
       
     JOptionPane.INFORMATION_MESSAGE);
  }

  else if (event.getSource() == controlsMenuItem) // Selected "Controls"
  {
   JOptionPane.showMessageDialog(this,
     "Drag and drop the pieces when it is your turn."
       + "\n\nThe piece will only move if you dragged"
       + " and dropped the piece in a valid area."
       + "\n\nTo take a piece, drag and drop your piece "
       + "to the square located behind the enemy piece."
       + "\n\nUse the menu panels to access extra "
       + "features and start a new game."
       , "'Figet SPinner' Checkers",
     JOptionPane.INFORMATION_MESSAGE);
  }

  else if (event.getSource() == aboutMenuItem) // Selected "About"
  {
   JOptionPane.showMessageDialog(this, "Checkers with fidget spinner because why not?"
     + "game ... or is it??"
     , "Checkers",
     JOptionPane.INFORMATION_MESSAGE);
  }

  else if (event.getSource() == creditsMenuItem) // Selected "Credits"
  {
   JOptionPane.showMessageDialog(this, "By Zain Sulaiman and Ryan"
     + "\nFor Mr. Mangat"
     + "\n\n\u00a9 2017", "Fidget SPinners",
     JOptionPane.INFORMATION_MESSAGE);
  }
 }

 /**
  * Method to change all variables and arrays to original values
  * in order to start a fresh game
  */
 public void newGame()
 {
  // Total amount of pieces for each player
  playerOnePieces = 12;
  playerTwoPieces = 12;
  // Initializes a 2D array to keep track of everything on the board
  board = new int[8][8];
  // Assigns pieces their starting position and opposite sides
  for (int row = 0; row < 8; row++)
  {
   for (int col = 0; col < 8; col++)
   {
    board[row][col] = EMPTY;
    if ((row + col) % 2 == 0 && row < 3)
    {
     board[row][col] = WHITE;
    }
    else if ((row + col) % 2 == 0 && row > 4)
    {
     board[row][col] = BLACK;
    }
   }
  }
  
  // First turn is black's
  turn = BLACK;
  multiJump = false;
  repaint();
  animationOn = false;
 }

 /**
  * Contains all methods used in drawing the entire board including pieces,
  * and side panel. Also includes the mouse motion handler and mouse 
  * pressed/ released
  * @author  Zain Thaver, Sulaiman Ahmadi and Ryan Derry
  * @version June 2017
  *
  */
 private class DrawingPanel extends JPanel
 {
  /**
   * Makes the drawing panel 1100 X 800 pixels and adds the mouse
   * handlers
   */
  public DrawingPanel()
  {
   setPreferredSize(new Dimension(1100, 800));
   this.addMouseMotionListener(new MouseMotionHandler());
   this.addMouseListener(new CheckerMouseHandler());
  }

  /**
   * Paints on the drawing panel the board, side bar info, the pieces,
   * piece if the player drags the piece.
   * @param g is the graphic object of all the images and fonts on the
   * drawing panel
   */
  public void paintComponent(Graphics g) 
  {
   // Makes the board 800 by 800 since a checkers game is on
   // a 8 by 8 board.
   g.drawImage(checkersBoard, 0,0, 800,800, null);
   // Goes through every position in the 8 by 8 2D array to draw the
   // pieces depending on where they are in the array. Also draws
   // king if the piece is a king in the array.
   for (int row = 0; row < 8; row++) 
   {
    for (int col = 0; col < 8; col++)
    {
     if (board[row][col] == BLACK) 
     {
      g.drawImage(playerOnePiece, col * 100, row * 100,
        100, 100, null);
     } 
     else if (board[row][col] == WHITE)
     {
      g.drawImage(playerTwoPiece, col * 100, row * 100,
        100, 100, null);
     }
     else if (board[row][col] == BLACK_KING) 
     {
      g.drawImage(playerOneKing, col * 100, row * 100,
        100, 100, null);
     } 
     else if (board[row][col] == WHITE_KING)
     {
      g.drawImage(playerTwoKing, col * 100, row * 100,
        100, 100, null);
     } 
    }
   }
   // Redraws the piece as though it is moving if it is the AI's turn
   if (pieceSelected)
   {
    if (transPiece == WHITE)
    {
     // Redraws the piece in a new, slightly different location
     g.drawImage (playerTwoPiece, transX, transY,
       100, 100, null);
    }
    if (transPiece == WHITE_KING)
    {
     g.drawImage (playerTwoKing, transX, transY,
       100, 100, null);
    }
   }
   // Redraws the selected piece onto the new pixel coordinates
   // obtained from the mouseDragged method
   if (animationOn)
   {
    // Erases the piece from the board but still keeps the
    // colour of the piece
    board [selRow][selCol] = turn*3;
    if (animPiece == BLACK)
    {
     g.drawImage (playerOnePiece, animX, animY, 100, 100, null);
    }
    if (animPiece == BLACK_KING)
    {
     g.drawImage (playerOneKing, animX, animY, 100, 100, null);
    }
    if (animPiece == WHITE)
    {
     g.drawImage (playerTwoPiece, animX, animY, 100, 100, null);
    }
    if (animPiece == WHITE_KING)
    {
     g.drawImage (playerTwoKing, animX, animY, 100, 100, null);
    }
   }
   // Draws the side board and sets font for the info on it
   g.drawImage(checkersBackground, 800, 0, 300, 800, null);
   g.setColor(Color.CYAN);
   g.setFont(new Font("Constantia", Font.BOLD, 25));
   g.drawString ("'Fidget Spinner Checkers", 800, 50);
   g.setColor(Color.LIGHT_GRAY);
   g.setFont(new Font("Calibri", Font.BOLD, 28));
   
   // If the player selects to play against the AI, a message will
   // Appear on the side panel saying that is the current option
   if (vsAI)
   {
    g.drawString ("Player VS AI Mode", 845, 225);
    g.drawString ("Select Two Player to play ", 805, 600);
    g.drawString ("against another player", 815, 635);
   }
   
   // If Ai player has not been selected, a message will 
   // Appear on the side panel saying that is the current option
   else 
   {
    g.drawString ("Two Player Mode", 850, 225);
    g.drawString ("Select Player VS AI to play ", 800, 600);
    g.drawString ("against a computer", 840, 635);
   }
   
   // If there is a multijump, it will say so on the side bar
   if (multiJump)
   {
    g.drawString ("Another jump is possible!", 803, 500);
   }
   g.setFont(new Font("Broadway", Font.BOLD, 30));
   
   // Draws a message to say whose turn it is currently
   if (turn == BLACK)
   {
    g.setColor(Color.GRAY);
    g.drawString("It is black's turn", 800 , 400);
   }
   else
   {
    g.setColor(Color.WHITE);
    g.drawString("It is white's turn", 800 , 400);
   }
  }

  /** 
   * Mouse handler to check for mouse presses and releases
   * in order to move around pieces on the board
   * @author Zain Thaver, Sulaiman Ahmadi and Ryan Derry
   * @version June 2017
   */
  private class CheckerMouseHandler extends MouseAdapter
  {

   /**
    * Gets the row and the column of the pressed mouse and saves
    * the values in an integer for future reference (to find
    * the original location of the piece). Also saves what kind
    * of piece it was so that we can keep track of what kind of
    * piece the player is dragging
    * @param event is the position where the mouse was pressed
    */
   public void mousePressed(MouseEvent event)
   {
    int row = (event.getY() / 100);
    int col = (event.getX() / 100);
    selCol = col;
    selRow = row;
    animPiece = 0;
    // Saves the type and colour of the piece if the player
    // clicked on a piece.
    if (isOnBoard (selRow, selCol) && board [selRow][selCol] != 0)
    {
     animPiece = board [selRow][selCol];
    }
   }

   /**
    * Gets the position of the point where the mouse was released
    * and then checks if the released position is a valid move
    * @param event is the position where the mouse was released
    */
   public void mouseReleased(MouseEvent event) 
   {
    // Turns off the animation when the mouse stops dragging
    animationOn = false;
    // Makes sure that the original mouse pressed coordinates are
    // in the board.
    if (!isOnBoard (selRow, selCol))
    {
     return;
    }
    // Returns the original value to the board to check if the
    // move was a valid one
    board [selRow][selCol] = animPiece;
    repaint();
    // Prevent mouse action if the game is over
    if (playerOnePieces == 0 || playerTwoPieces == 0)
    {
     return;
    }
    
    Point mousePoint = event.getPoint();
    int row = (mousePoint.y) / 100;
    int col = (mousePoint.x) / 100;
    
    // If in mousePressed a piece is selected that belongs to 
    // the side whose turn it is, check if its a valid move
    if (isOnBoard (row,col) && (board[selRow][selCol] == turn || 
      board[selRow][selCol] == turn*2))
    {
     // Keeps checking till there are no multijumps left
     if (multiJump)
     {
      if (!getMove (row, col, multiRow, multiCol, turn))
      {
       return;
      }
      // Changes the turn to the opposite side
      turn*=-1;
      // No more multiple jumps
      multiJump = false;
      
      // If the player is VSing the AI then the AI will check
      // for any possible moves and execute them
      if (vsAI && turn == 1)
      {
       boardChecker ();
       // If the AI can multijump, it will check which
       // direction it can multijump
       if (multiJump)
       {
        // Keeps jumping if there are more jumps 
        // available
        while (multiJumpingCheck())
        {
        }
       }
       multiJump = false;
       turn *= -1;
      }
      return;
     }
     
     // Checks if the mouse movement was a valid move for the
     // selected piece
     else if (getMove(row,col,selRow, selCol,turn))
     {
      turn*=-1;
      if (vsAI && turn == 1)
      {
       boardChecker ();
       if (multiJump)
       {
        while (multiJumpingCheck())
        {
        }
       }
       multiJump = false;
       turn *= -1;
      }
     }
     
    }
    // Reset the original coordinates to prevent errors
    selRow = -1;
    selCol = -1;
   }
  }

  /**
   * Checks to see if the cursor is on a selectable piece
   * and if it is on a selectable piece then change the cursor shape
   * to indicate that it is selectable
   * @author Zain Thaver, Sulaiman Ahmadi and Ryan Derry
   * @version June 2017
   */
  private class MouseMotionHandler implements MouseMotionListener 
  {
   /**
    * Checks to see if the piece the mouse is on is a piece that can
    * be currently moved. If it is, change the cursor image
    * @param event is the position where the mouse is moving
    */
   public void mouseMoved(MouseEvent event)
   {
    // Disables mouse if the game is over
    if (playerOnePieces == 0 || playerTwoPieces == 0)
    {
     return;
    }
    int row = event.getY() / 100;
    int col = event.getX() / 100;
    if (isOnBoard (row, col))
    {
     // If VSing the AI then the cursor will not change on a
     // white piece
     if (vsAI && board[row][col] >0)
     {
      return;
     }
     if (board[row][col] == turn || board[row][col] == turn*2)
     {
      // Pointy finger cursor
      setCursor(Cursor.getPredefinedCursor
        (Cursor.HAND_CURSOR));
     } 
     else 
     {
      setCursor(Cursor.getDefaultCursor());
     }
    }
   }

   /**
    * Gets the centre position of the piece image to use for animation
    * when redrawing. When dragging a piece, this method will
    * redraw the piece in its new position to simulate animation.
    * @param event is the position where the mouse is being dragged
    */
   @Override
   public void mouseDragged(MouseEvent event)
   {
    // The current position of the dragging mouse
    animX = event.getX()-50;
    animY = event.getY()-50;
    // Repaints the board if the mouse pressed a piece that is in
    // the board area
    if (isOnBoard (animX/100,animY/100) && isOnBoard(selRow,selCol)
      && (board [selRow][selCol]== turn || 
      board [selRow][selCol] == turn*2 ||
      board [selRow][selCol] == turn*3))
    {
     animationOn = true;
     repaint();
    }
   }
  }

 }

 /**
  * Handles the move rules for the game of checkers.
  * Checks to see if each move is legal or not depending on if 
  * the piece is black or white and if it is a king or not a king.
  * @param row is the vertical position of where the piece wants to move to
  * @param col is the horizontalPosition of where the piece wants to move to
  * @param origRow is the current vertical position of the mouse
  * @param origCol is the current horizontal position of the mouse
  * @param turn The numerical value that dictates which player can move
  * @return returns true if the move being made is legal, false if it is 
  *   illegal or if there is another jump available
  */
 public boolean getMove(int row, int col, int origRow, int origCol,
   int turn)
 {
  // Animate the piece if the AI is moving the piece
  if (pieceSelected)
  {
   translatePiece (row, col, origRow, origCol);
  }
  // Checks if the piece is the current player's piece
  if (board[origRow][origCol] == turn)
  {
   // Checks to see if the initial piece position is the correct
   // distance to where the player wants to move it to and to see
   // if it can take a piece in between
   if (origRow+turn*2 == row && (origCol+2 == col || origCol-2 == col)
     && (board[(origRow+row)/2][(origCol+col)/2] == turn*-1 ||
     board[(origRow+row)/2][(origCol+col)/2] == turn*-2 ))
   {
    // Erases the piece and the taken piece from the board
    board[origRow][origCol] = 0;
    board [(origRow+row)/2][(origCol+col)/2] = 0;
    // Sets the new position of the piece
    board [row][col] = turn;
    // Makes a piece a king if it reached the other side of 
    // the board
    if (isKing(row, turn))
    {
     board[row][col] = turn*2;
    }
    // Repaints the board with updated values
    repaint();
    // Puts down the count of pieces for the opposite player
    if (turn == 1)
    {
     playerOnePieces --;
    }
    else
    {
     playerTwoPieces --;
    }
    // Checks to see if the game is over
    gameOver();
    // Checks for double jumps
    if ((row+turn*2 < 8 && row+turn*2 > -1)&&
      ((col+2 < 8 && (board [row+turn][col+1]== turn*-1 || 
      board [row+turn][col+1]== turn*-2) && board 
      [row + turn*2][col+2] == 0)|| 
      (col-2 > -1 && (board [row+turn][col-1]== turn*-1 || 
      board [row+turn][col-1]== turn*-2) && board
      [row+turn*2][col-2]==0)))
    {
     // Saves value of new row and col of the piece
     multiRow = row;
     multiCol = col;
     multiJump = true;
     return false;
    }

    return true;
   }
   
   // If there is an open space in front, move one space
   else if (origRow+turn == row && (origCol+1 == col || 
     origCol-1 == col) && board[row][col] == 0 && !multiJump)
   {
    board[origRow][origCol] = 0;
    board [row][col] = turn;
    if (isKing(row, turn))
    {
     board[row][col] = turn*2;
    }
    repaint();
    return true;
   }
   
  }
  
  // Checks to see if the piece is a king
  if (board[origRow][origCol] == turn*2)
  {
   // Can take a piece in the front and the back now
   if ((origRow+turn*2 == row || origRow-turn*2 == row) &&
     (origCol+2 == col || origCol-2 == col) 
     && (board[(origRow+row)/2][(origCol+col)/2] == turn*-1 ||
     board[(origRow+row)/2][(origCol+col)/2] == turn*-2))
   {
    // Erases the piece and the taken piece from the board
    board[origRow][origCol] = 0;
    board [(origRow+row)/2][(origCol+col)/2] = 0;
    // Sets the new position of the piece
    board [row][col] = turn*2;
    // Repaints the board with updated values
    repaint();
    // Puts down the count of pieces for the opposite player
    if (turn == 1)
    {
     playerOnePieces --;
    }
    else
    {
     playerTwoPieces --;
    }
    gameOver();
    
    // Checks for a multijump in all 4 diagonal directions
    if ((row + 2 < 8 && col + 2 < 8 && (board [row+1][col+1]== 
      turn*-1 ||board [row+1][col+1]== turn*-2)
      && board [row + 2][col+2] == 0) ||
      (row + 2 < 8 && col - 2 >-1 && 
        (board [row+1][col-1]==
      turn*-1 ||board [row+1][col-1]== turn*-2)
      && board [row + 2][col-2] == 0) ||
      (row - 2 >-1 && col + 2 < 8 && (board [row-1][col+1]==
      turn*-1 ||board [row-1][col+1]== turn*-2)
      && board [row - 2][col+2] == 0) ||
      (row - 2 > -1 && col - 2 > -1 && (board [row-1][col-1]
      == turn*-1 ||board [row-1][col-1]== turn*-2)
      && board [row - 2][col-2] == 0))
    {
     // Saves values for the position of the piece that jumped
     multiRow = row;
     multiCol = col;
     multiJump = true;
     return false;
    }
    return true;
   }
   
   // Move the king in any direction if the space is open
   else if ((origRow+turn == row || origRow-turn == row) && 
     (origCol+1 == col || origCol-1 == col) &&
     board[row][col] == 0 && !multiJump)
   {
    board[origRow][origCol] = 0;
    board [row][col] = turn*2;
    repaint();
    return true;
   }
   
  }
  return false;
 }

 /**
  * Checks whether or not a selected piece is at the opposite end of the
  * board
  * @param row is the current horizontal position of the piece
  * @param turn is the numerical value that dictates what piece is being selected
  * @return true if the piece being looked at is at the final row of the board
  *      and returns false if it is not
  */
 public boolean isKing (int row, int turn)
 {
  // If the white piece reaches the opposite row (bottom)
  // "King" that piece by returning true
  if (turn == WHITE && row == 7)
  {
   return true;
  }
  // If the Black piece reaches the opposite row (top)
  // "King" that piece by returning true
  else if (turn == BLACK && row == 0)
  {
   return true;
  }
  return false;
 }
 
 /**
  * Checks to see if the mouse is on the checker board
  * @param row is the current vertical position of the mouse
  * @param col is the current horizontal position of the mouse
  * @return true if the mouse is on the board or false if the mouse
  *   is off the board
  */
 public boolean isOnBoard (int row, int col)
 {
  if (row < 8 && col < 8 && row > -1 && col > -1)
  {
   return true;
  }
  return false;
 }
 
 /**
  * Loads in and plays music
  */
 public void music()
 {
  // Try catch in case the file cannot be found or is invalid
  try
  {
   // Loads the music .wav file
         AudioInputStream audioIn = AudioSystem.getAudioInputStream
           (new File(musicFile).getAbsoluteFile());
         clip = AudioSystem.getClip();
         clip.open(audioIn);
         // Plays the music
         clip.loop(Clip.LOOP_CONTINUOUSLY);
     }
  catch(Exception ex)
  {
         System.out.println("Invalid File");
     }
    }
 
 /**
  * Checks to see if either side's pieces are all gone. If one of them are,
  * show a message showing who won.
  */
 public void gameOver()
 {
  if (playerOnePieces == 0)
  {
   JOptionPane.showMessageDialog(frame, "Player one has lost."
     + " Please select new game.");
  }
  else if (playerTwoPieces == 0)
  {
   JOptionPane.showMessageDialog(frame, "Player two has lost."
     + " Please select new game.");
  }
 }
 
 /**
  * This method resets variables for the AI. It checks through the
  * entire board and adds the row, col, the row to move to, the col to
  * move to, and the number of jumps each piece can do. It then gets
  * sends the values of the piece that can make the most jumps to 
  * getMove to do the jumping. If there are no jumps that a random
  * move is made.
  */
 public void boardChecker ()
 {
  // Array of possible moves and their priority numbers (which
  // move will help the computer the most?)
  movePriority = new ArrayList<Integer> ();
  // Arrays of the locations and future locations of a piece
  firstRow = new ArrayList<Integer> ();
  firstCol = new ArrayList<Integer> ();
  moveRow = new ArrayList<Integer> ();
  moveCol = new ArrayList<Integer> ();
  greatestIndex = -2;
  greatestPriority = -2;
  // Number of just moves (no takes)
  singleMoves = 0;
  // Checks each piece for possible moves
  for (int rowPriority = 0 ; rowPriority< 8 ; rowPriority++ )
  {
   for (int colPriority = 0 ; colPriority< 8 ; colPriority++)
   {
    noOfJumps = 0;
    possibleMoveMaker (rowPriority, colPriority);
   }
  }
  
  // If there are no possible moves, player one wins
  if (movePriority.size() == 0)
  {
   playerTwoPieces = 0;
   gameOver();
   // Exits early
   return;
  }
  
  // Searches through the array to find the best move based
  // on priority level
  for (int index = 0; index <movePriority.size(); index++)
  {
   // If the move is just a single block move, increment.
   if (movePriority.get(index) == 0)
   {
    singleMoves ++;
   }
   if (movePriority.get(index) >greatestPriority)
   {
    greatestPriority = movePriority.get(index);
    greatestIndex = index;
   }
  }
  
  // If the array only has single moves, choose a random move to do
  if (singleMoves == movePriority.size ())
  {
   greatestIndex = (int)(Math.random()*movePriority.size());
  }
  // Signify that a piece has to be moved
  pieceSelected = true;
  // Saving the colour and type of the piece for future reference
  transPiece = board [firstRow.get(greatestIndex)]
    [firstCol.get(greatestIndex)];
  // Calls getMove to make the best move possible
  getMove (moveRow.get(greatestIndex), moveCol.get(greatestIndex), 
    firstRow.get(greatestIndex), 
    firstCol.get(greatestIndex), turn);
 }
 
 /**
  * Checks for any possible moves on the currently selected piece
  * @param row is the current vertical position of the piece
  * @param col is the current horizontal position of the piece
  */
 public void possibleMoveMaker (int row, int col)
 {
  // Checks to see if that piece is an AI piece
  if (board[row][col] == turn)
  {
   // Calls a method to check if that piece can take an opposite piece
   takingPiece (row, col);
   // Checks to see if the piece can move in a diagonal direction
   // in front of it
   if (isOnBoard (row+1, col-1) && board[row+1][col-1] == 0)
   {
    // Sends the values to the arrays for future reference
    initializeVariables (row, col, row+1, col-1);
   }
   if (isOnBoard (row+1, col+1) && board[row+1][col+1] == 0)
   {
    initializeVariables (row, col, row+1, col+1);
   }
    
  }
  
  // Checks to see if the current piece is a king
  else if (board[row][col] == turn*2)
  {
   // Checks to see if the piece can take anything in front of it
   takingPiece (row, col);
   
   // Checks to see if the piece can take anything behind it
   if (isOnBoard (row-2, col+2) && (board[row-1][col+1] == turn*-1 ||  
     board[row-1][col+1] == turn*-2)
     && board[row-2][col+2] == 0)
   {
    // Increase the priority of this move
    noOfJumps++;
    // Sends the values to the arrays for future reference
    initializeVariables (row, col, row-2, col+2);
   }
   if (isOnBoard (row-2, col-2) && (board[row-1][col-1] == turn*-1 || 
     board[row-1][col-1] == turn*-2) && board[row-2][col-2] ==0)
   {
    noOfJumps++;
    initializeVariables (row, col, row-2, col-2);
   }
   
   // Checks to see if it the piece can move in any diagonal 
   // direction around it then adds the values to the array if it can
   if (isOnBoard (row+1, col-1) && board[row+1][col-1] == 0)
   {
    initializeVariables (row, col, row+1, col-1);
   }
   if (isOnBoard (row+1, col+1) && board[row+1][col+1] == 0)
   {
    initializeVariables (row, col, row+1, col+1);
   }
   
   if (isOnBoard (row-1, col-1) && board[row-1][col-1] == 0)
   {
    initializeVariables (row, col, row-1, col-1);
   }
   if (isOnBoard (row-1, col+1) && board[row-1][col+1] == 0)
   {
    initializeVariables (row, col, row-1, col+1);
   }
  }
 }
 
 /**
  * Checks to see if the selected piece can take another piece
  * diagonally in front of it.
  * @param row is the current vertical position of the piece
  * @param col is the current horizontal position of the piece
  */
 public void takingPiece (int row, int col)
 {
  // Checks to see if the move-to space is empty and if there is a piece
  // in between. If there is, jump the piece.
  if (isOnBoard (row+2, col+2) && (board[row+1][col+1] == turn*-1 ||  
    board[row+1][col+1] == turn*-2) && board[row+2][col+2] == 0)
  {
   // Ups the priority of the move
   noOfJumps++;
   // Sends the values to the arrays for future reference
   initializeVariables (row, col, row+2, col+2);
  }
  if (isOnBoard (row+2, col-2) && (board[row+1][col-1] == turn*-1 ||  
    board[row+1][col-1] == turn*-2) && board[row+2][col-2] == 0)
  {
   noOfJumps++;
   initializeVariables (row, col, row+2, col-2);
   
  }
 }
 
 /**
  * Adds the following values to each array in order to get these
  * values when the highest priority move is chosen
  * @param row is the current vertical position of the piece
  * @param col is the current horizontal position of the piece
  * @param newRow the vertical position of where the piece can move to
  * @param newCol the horizontal position of where the piece can move to
  */
 public void initializeVariables (int row, int col, int newRow, int newCol)
 {
  movePriority.add (noOfJumps);
  firstRow.add (row);
  firstCol.add (col);
  moveRow.add(newRow);
  moveCol.add (newCol);
 }
 
 /**
  * Checks to see if the AI can jump in any other direction. Also
  * calls the translate method to move the piece once again for
  * a multijump.
  * @return true if there can be another jump and false if no other
  *   jump was found
  */
 public boolean multiJumpingCheck ()
 {
  transMultiJump = true;
  // The value of the piece that was moved. This signifies its
  // colour and type.
  transPiece = board [multiRow][multiCol];
  // Check all diagonal directions for an available multijump
  if (isOnBoard (multiRow+2, multiCol-2) && 
    getMove (multiRow + 2, multiCol -2, multiRow, multiCol, turn))
  {
   pieceSelected = true;
   // Animates the jumping piece
   translatePiece (multiRow+2,multiCol-2, multiRow, multiCol);
   transMultiJump = false;
   return true;
  }
  else if (isOnBoard (multiRow+2, multiCol+2) && 
    getMove (multiRow + 2, multiCol +2, multiRow, multiCol, turn))
  {
   pieceSelected = true;
   translatePiece (multiRow+2,multiCol+2, multiRow, multiCol);
   transMultiJump = false;
   return true;
  }
  else if (isOnBoard (multiRow-2, multiCol-2) && 
    getMove (multiRow - 2, multiCol -2, multiRow, multiCol, turn))
  {
   pieceSelected = true;
   translatePiece (multiRow-2,multiCol-2, multiRow, multiCol);
   transMultiJump = false;
   return true;
  }
  else if (isOnBoard (multiRow-2, multiCol+2) && 
    getMove (multiRow - 2, multiCol +2, multiRow, multiCol, turn))
  {
   pieceSelected = true;
   translatePiece (multiRow-2,multiCol+2, multiRow, multiCol);
   transMultiJump = false;
   return true;
  }
  transMultiJump = false;
  return false;
 }
 
 /**
  * This method continuously redraws the drawing panel with updated x and y
  * coordinates to simulate the AI dragging the pieces across the board.
  * @param row is the vertical position of where the piece wants to move to
  * @param col is the horizontalPosition of where the piece wants to move to
  * @param origRow is the current vertical position of the piece
  * @param origCol is the current horizontal position of the piece
  */
 private void translatePiece (int row, int col, int origRow, int origCol)
 {
  transX = origCol*100;
  transY = origRow*100;
  // The amount of pixels to move the piece
  int moveX = (col*100 - transX) / 50;
  int moveY = (row*100 - transY) / 50;
  board [origRow][origCol] = 0;
  // Redraws the piece 50 times with 10ms delays, slowly moving
  // the piece to the desired coordinates on the board.
  for (int times = 1; times <= 50; times++)
  {
   transX += moveX;
   transY += moveY;
   instantDraw();
   delay(5);
  }
  // Turn the original position of the piece back to its regular value
  // so getMove can evaluate the move properly.
  if (!transMultiJump)
  {
   board [origRow][origCol] = transPiece;
  }
  pieceSelected = false;
 }
 
 /**
  * Refreshes and repaints the board area immediatly
  * regardless of sleeping threads.
  */
 private void instantDraw()
 {
  boardArea.paintImmediately(new Rectangle(0, 0, boardArea.getWidth(),
    boardArea.getHeight()));
 }
 
 /**
  * Delays a thread for the given amount of milliseconds
  * @param time is the milliseconds to delay the thread
  */
 private void delay (int time)
 {
  try
  {
   Thread.sleep (time);
  }
  catch (Exception e)
  {
  }
 }
 
 /**
  * The main method for this program.
  * Creates an instance of the MainCheckers object which is the game.
  * It then makes it visible on the screen. 
  * @param args main
  */
 public static void main(String[] args)
 {
  MainCheckers checkers = new MainCheckers();
  // Closes window if exit is clicked
  checkers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  checkers.pack();
  checkers.setVisible(true);
 }
}