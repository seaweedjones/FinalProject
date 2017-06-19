import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ChessGame extends JFrame implements MouseListener,
      MouseMotionListener {

   JLayeredPane layeredPane;
   JFrame startingFrame;
   JPanel chessBoard;
   JLabel chessPiece;
   int xCoordinate;
   int yCoordinate;

   public ChessGame() {
      Dimension boardSize = new Dimension(400, 400);

      layeredPane = new JLayeredPane();
      getContentPane().add(layeredPane);
      layeredPane.setPreferredSize(boardSize);
      layeredPane.addMouseListener(this);
      layeredPane.addMouseMotionListener(this);

      // adding chess board

      chessBoard = new JPanel();
      layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
      chessBoard.setLayout(new GridLayout(8, 8));
      chessBoard.setPreferredSize(boardSize);
      chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

      for (int i = 0; i < 64; i++) {
         JPanel square = new JPanel(new BorderLayout());
         chessBoard.add(square);

         int row = (i / 8) % 2;
         if (row == 0)
            square.setBackground(i % 2 == 0 ? new Color(238, 221, 187)
                  : new Color(204, 136, 68));
         else
            square.setBackground(i % 2 == 0 ? new Color(204, 136, 68)
                  : new Color(238, 221, 187));
      }

      // Black pieces on the board

      JLabel piece = new JLabel(new ImageIcon(getClass().getResource(
            "blackrooks.png")));
      JPanel panel = (JPanel) chessBoard.getComponent(0);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackknights.png")));
      panel = (JPanel) chessBoard.getComponent(1);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackbishops.png")));
      panel = (JPanel) chessBoard.getComponent(2);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackqueen.png")));
      panel = (JPanel) chessBoard.getComponent(3);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackking.png")));
      panel = (JPanel) chessBoard.getComponent(4);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackbishops.png")));
      panel = (JPanel) chessBoard.getComponent(5);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackknights.png")));
      panel = (JPanel) chessBoard.getComponent(6);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackrooks.png")));
      panel = (JPanel) chessBoard.getComponent(7);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(8);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(9);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(10);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(11);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(12);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(13);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(14);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("blackpawn.png")));
      panel = (JPanel) chessBoard.getComponent(15);
      panel.add(piece);

      // White pieces on the board

      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(48);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(49);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(50);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(51);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(52);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(53);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(54);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitepawn.png")));
      panel = (JPanel) chessBoard.getComponent(55);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whiterooks.png")));
      panel = (JPanel) chessBoard.getComponent(56);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whiteknights.png")));
      panel = (JPanel) chessBoard.getComponent(57);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitebishops.png")));
      panel = (JPanel) chessBoard.getComponent(58);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitequeen.png")));
      panel = (JPanel) chessBoard.getComponent(59);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whiteking.png")));
      panel = (JPanel) chessBoard.getComponent(60);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whitebishops.png")));
      panel = (JPanel) chessBoard.getComponent(61);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whiteknights.png")));
      panel = (JPanel) chessBoard.getComponent(62);
      panel.add(piece);
      piece = new JLabel(new ImageIcon(getClass().getResource("whiterooks.png")));
      panel = (JPanel) chessBoard.getComponent(63);
      panel.add(piece);

   }

   public void mousePressed(MouseEvent e) {
      chessPiece = null;
      Component c = chessBoard.findComponentAt(e.getX(), e.getY());

      if (c instanceof JPanel)
         return;

      Point parentLocation = c.getParent().getLocation();
      xCoordinate = parentLocation.x - e.getX();
      yCoordinate = parentLocation.y - e.getY();
      chessPiece = (JLabel) c;
      chessPiece.setLocation(e.getX() + xCoordinate, e.getY() + yCoordinate);
      chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
      layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
   }

   // move pieces

   public void mouseDragged(MouseEvent me) {
      if (chessPiece == null)
         return;
      chessPiece.setLocation(me.getX() + xCoordinate, me.getY() + yCoordinate);
   }

   // drop a piece when mouse is released

   public void mouseReleased(MouseEvent e) {
      if (chessPiece == null)
         return;

      chessPiece.setVisible(false);
      Component c = chessBoard.findComponentAt(e.getX(), e.getY());

      if (c instanceof JLabel) {
         Container parent = c.getParent();
         parent.remove(0);
         parent.add(chessPiece);
      }

      else

      {
         Container parent = (Container) c;
         parent.add(chessPiece);
      }

      chessPiece.setVisible(true);
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mouseMoved(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public static void main(String[] args) {
      JFrame frame = new ChessGame();
      frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      frame.pack();
      frame.setResizable(true);
      frame.setSize(600,600);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

}