/**
* This template can be used as reference or a starting point
* for your final summative project
* @author Mangat
**/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.*; 

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


class GameFrame extends JFrame { 
  
  //Characteristics
  static int[][] coordinates = new int[8][8]; //(this might be redundant)
  static boolean[][] check_pos = new boolean[8][8]; //checks if a given piece is on a given coordinate (x,y) ((x&y {0..7}))
  static String[][] piece_check = new String[8][8]; //contains name of piece on given coordinate (x,y)
  
  public void assign_char(int x_pos, int y_pos, boolean exist, String name){ //call when creating board
    if(exist==true){
      check_pos[x_pos][y_pos] = true; //assigns values to given node
      piece_check[x_pos][y_pos] = name;
    }
    if(exist==false){
      check_pos[x_pos][y_pos] = false; 
      piece_check[x_pos][y_pos] = null;
    }  
  }
  
  
  public static String text_notation(int x_pos, int y_pos){
   String num = null;
   String[] alph = new String[8];
   alph[0] = "a";
   alph[1] = "b";
   alph[2] = "c";
   alph[3] = "d";
   alph[4] = "e";
   alph[5] = "f";
   alph[6] = "g";
   alph[7] = "g";
   num = alph[x_pos] + y_pos;
   return num;
  }
  public static String check_piece (int x, int y, String pieceXPosition[], String pieceYPosition[]){
       // String x_pos[] = a[];
       // String y_pos[] = b[];
     if(pieceXPosition[y].equals(pieceYPosition[x])){
      return pieceYPosition[y]; 
     }
    return null;
  }
  
  //class variable (non-static)
   static double x, y;
   static GameAreaPanel gamePanel;
  
  
  //Constructor - this runs first
  GameFrame() { 
    
    super("My Game");  
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
   // this.setUndecorated(true);  //Set to true to remove title bar
   //frame.setResizable(false);


    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());

    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
  
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
     ThaverListener listener = new ThaverListener();
    this.addMouseListener(listener);
    this.requestFocusInWindow(true);
  } //End of Constructor

  //the main gameloop - this is where the game state is updated
  public void animate() { 
    
    
      this.x = 0;  //update coords
      this.y = 0;
      
      
      try{ Thread.sleep(500);} catch (Exception exc){}  //delay
    
    }    
  
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
       super.paintComponent(g); //required
       setDoubleBuffered(true);
       int x2=0;
       int boardXLength =8;
       int boardYLength = 8;
       //Co-ordinate system
       int []   boardXPosition = new int [8];
       int[] boardYPosition = new int [8];
       
          //Piece identification system
            String[] pieceXPosition = new String[8]; 
            String[] pieceYPosition = new String[8];
       
       String [] [] textPosition = new String  [8][8];
        for ( int row = 0;  row < 8;  row++ ) {
          
             for (int col = 0;  col < 8;  col++) {
                boardXPosition[row] = row * 50;
                    //System.out.println("Yes is" + boardXPosition[row]);
                boardYPosition[col] = col * 50;
                x = col*50;
                y= row *50;
                if ( (row % 2) == (col % 2) )
            
                   g.setColor(Color.white);
                   else 
                   g.setColor(Color.blue);
                g.fillRect((int)x,(int)y, 50, 50);
                   
             }
            
        }   
                          
             
             
       
           
       /*
         for (int rows =0;rows<8;rows++  ){
         for(int cols =0;cols<8;cols++){
           if(boardPosition [8] % 2==0)   
           {
             
             
           }
           else 
           {
             
           }
           */
         
        
           
      
      BufferedImage []  blackPawns = new BufferedImage [8];
       
       BufferedImage [] whitePawns  = new BufferedImage [8];
       int [] PieceYPos = {5,57,107,157,207,257,307,357};
       BufferedImage []  bishopsBlack= new BufferedImage [2] ;
       BufferedImage []  whitebishops = new  BufferedImage [2];
       BufferedImage []  blackrooks = new  BufferedImage [2];
       BufferedImage []  whiterooks = new BufferedImage [2];
       BufferedImage []  blackKnights = new BufferedImage[2];
       BufferedImage []  whiteKnights = new BufferedImage [2];
       BufferedImage whiteQueen = null;
       BufferedImage blackQueen = null;
       BufferedImage whiteKing = null;
       BufferedImage blackKing = null;
       
      try {   
        blackPawns [0] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [1] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [2] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [3] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [4] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [5] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [6] = ImageIO.read(new File("blackpawn.png"));
        blackPawns [7] = ImageIO.read(new File("blackpawn.png"));

        
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     
     for (int counter =0; counter <8;counter++)
     {
       g.drawImage (blackPawns[counter],boardXPosition[counter],boardYPosition[1],this);
       pieceXPosition [counter] = "P";
       pieceYPosition [0] = "P";     
       
       
     }
     
     try {   
       whitePawns [0] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [1] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [2] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [3] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [4] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [5] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [6] = ImageIO.read(new File("whitepawn.png"));
       whitePawns [7] = ImageIO.read(new File("whitepawn.png"));
       
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     for (int counter =0; counter <8;counter++)
     {
       g.drawImage (whitePawns[counter],boardXPosition[counter],boardYPosition[6],this);
       pieceXPosition [counter] = "p";
       pieceYPosition [7] = "p"; 
     }
     
     /*
      g.drawImage(nyet, 7,310,this);
      g.drawImage(nyet, 57,310,this);
      g.drawImage(nyet, 107,310,this);
      g.drawImage(nyet, 157,310,this);
      g.drawImage(nyet, 207,310,this);
      g.drawImage(nyet, 257,310,this);
      g.drawImage(nyet, 307,310,this);
      g.drawImage(nyet, 357,310,this);
      
      */
     try {
       bishopsBlack[0] = ImageIO.read(new File ("blackbishops.png")); 
       bishopsBlack[1] = ImageIO.read(new File ("blackbishops.png"));
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     int bishopsCounter = 0;
     for (int bishopsCount = 2;bishopsCount < 7;bishopsCount+=3)
       
     {
       g.drawImage (bishopsBlack[bishopsCounter],boardXPosition[bishopsCount],boardYPosition[0],this);
       pieceXPosition [bishopsCounter] = "B";
       pieceYPosition [0] = "B"; 
       
     }
     g.setColor(Color.red);
//   g.fillRect(boardXPosition[0],boardYPosition[0], 50, 50);
//   g.fillRect(boardXPosition[1],boardYPosition[1], 50, 50);
//   g.fillRect(boardXPosition[2],boardYPosition[2], 50, 50);
//   g.fillRect(boardXPosition[3],boardYPosition[3], 50, 50);
//   g.fillRect(boardXPosition[4],boardYPosition[4], 50, 50);
//   g.fillRect(boardXPosition[5],boardYPosition[5], 50, 50);
//   g.fillRect(boardXPosition[6],boardYPosition[6], 50, 50);
//   g.fillRect(boardXPosition[7],boardYPosition[7], 50, 50);
     
     
     try
     {
       whitebishops [0] = ImageIO.read(new File ("whitebishops.png"));
       whitebishops [1] = ImageIO.read(new File ("whitebishops.png"));
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     int bishopsCountere = 0;
     for (int bishopsCounte = 2;bishopsCounte < 7;bishopsCounte+=3)
       
     {
       g.drawImage (whitebishops[bishopsCountere],boardXPosition[bishopsCounte],boardYPosition[7],this);
       pieceXPosition [bishopsCounte] = "b";
       pieceYPosition [7] = "b"; 
       
     }
     
     
     
     
     try
     {
       whiterooks [0] = ImageIO.read(new File ("whiterooks.png")); 
       whiterooks [1] = ImageIO.read(new File ("whiterooks.png"));
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(whiterooks [0], boardXPosition[0],boardYPosition[7],this);
     pieceXPosition [0] = "r";
     pieceYPosition[7] = "r";
     g.drawImage(whiterooks [1],boardXPosition[7] ,boardYPosition[7],this);
     pieceXPosition [7] = "r";
     pieceYPosition[7] = "r";
     
     
     try
     {
       whiteKnights[0] = ImageIO.read(new File ("whiteknights.png"));
       whiteKnights[1] = ImageIO.read(new File ("whiteknights.png"));
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(whiteKnights[0], boardXPosition[1],boardYPosition[7],this);
     pieceXPosition[1] = "n";
     pieceYPosition[7]="n";
     
     g.drawImage(whiteKnights[1], boardXPosition[6],boardYPosition[7],this);
     pieceXPosition[6] = "n";
     pieceYPosition[7]="n";
     
     
     
     
     try
     {
       whiteQueen = ImageIO.read(new File ("whitequeen.png")); 
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(whiteQueen, boardXPosition[3],boardYPosition[7],this);
     pieceXPosition[3] = "q";
     pieceYPosition[7] = "q";
     try
     {
       whiteKing = ImageIO.read(new File ("whiteking.png")); 
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(whiteKing, boardXPosition[4],boardYPosition[7],this);
     pieceXPosition[4] = "k";
     pieceYPosition[7] = "k";
     
     
     
     try
     {
       blackKing = ImageIO.read(new File ("blackking.png")); 
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(blackKing, boardXPosition[4],boardYPosition[0],this);
     pieceXPosition[4] = "K";
     pieceYPosition[0] = "K";
     
     
     
     try
     {
       blackQueen = ImageIO.read(new File ("blackqueen.png")); 
       
     } catch(Exception e) { System.out.println("error loading sprite");}; 
     
     g.drawImage(blackQueen, boardXPosition[3],boardYPosition[0],this);
     pieceXPosition[4] = "Q";
     pieceYPosition[0] = "Q";
     
      try
             {
                  blackrooks [0] = ImageIO.read(new File ("blackrooks.png")); 
                  blackrooks [1] = ImageIO.read(new File ("blackrooks.png"));
    } catch(Exception e) { System.out.println("error loading sprite");}; 
     g.drawImage(blackrooks [0], boardXPosition[0],boardYPosition[0],this);
      pieceXPosition[0] = "R";
     pieceYPosition[0] = "R";
     g.drawImage(blackrooks [1], boardYPosition[7],boardYPosition[0],this);
      pieceXPosition[7] = "R'";
     pieceYPosition[0] = "R";
     
     
        try
             {
                  blackKnights[0] = ImageIO.read(new File ("blackknights.png")); 
                   pieceXPosition[6] = "R'";
     pieceYPosition[0] = "R";
                    blackKnights[1] = ImageIO.read(new File ("blackknights.png")); 
       pieceXPosition[1] = "R'";
     pieceYPosition[0] = "R";
    } catch(Exception e) { System.out.println("error loading sprite");}; 
     g.drawImage(blackKnights[0], boardXPosition[1],boardYPosition[0],this);
     g.drawImage(blackKnights[1], boardXPosition[6],boardYPosition[0],this);
            setDoubleBuffered(true); 
      }

       
    }
  }

     //note - would be better to make player class and pass in map, test movement in there
  
  
  class ThaverListener implements MouseListener {
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        System.out.println("X:"+e.getX() + " y:"+e.getY());
     
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
  }
    //end of mouselistener

   