import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Implement MouseListener and Action Listener 
public class Chess implements MouseListener, ActionListener 
{

//Initialize window for graphics (i.e. chess pieces, and square tile colors
JFrame frame;   
//Initialize 2D array to hold chess pieces 
JPanel position[][] = new JPanel[8][8];


public Chess() 
{
    frame = new JFrame("Chess");
    frame.setSize(400, 400);
    frame.setLayout(new GridLayout(8, 8));

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);


    //for loop utilized to delineate chess squares and respective black/white tiles 
    for (int i = 0; i<8; i++) 
    {
        for (int j = 0; j<8; j++) 
        {
            position[i][j] = new JPanel();
            if ((i + j) % 2 == 0) 
            {
                position[i][j].setBackground(Color.DARK_GRAY);
            } 
            else
            {
                position[i][j].setBackground(Color.white);
            }   
            frame.add(position[i][j]);
        }
    }



    /** utilized 2D array 'position[i][j] to respectively locate my chess pieces 
     * I would like to create a class possibly called 'piece' in order to 
     * better implement MouseListener and ActionListener so that I can move the pieces 
     */ 
    position[0][0].add(new JLabel(new ImageIcon("blackrooks.png")));
    position[0][1].add(new JLabel(new ImageIcon("blackknight.png")));
    position[0][2].add(new JLabel(new ImageIcon("blackbishop.png")));
    position[0][3].add(new JLabel(new ImageIcon("blackqueen.png")));
    position[0][4].add(new JLabel(new ImageIcon("blackking.png")));
    position[0][5].add(new JLabel(new ImageIcon("blackbishop.png")));
    position[0][6].add(new JLabel(new ImageIcon("blackknights.png")));
    position[0][7].add(new JLabel(new ImageIcon("blackrooks.png")));

    position[7][0].add(new JLabel(new ImageIcon("whiterooks.png")));
    position[7][1].add(new JLabel(new ImageIcon("whiteknights.png")));
    position[7][2].add(new JLabel(new ImageIcon("whitebishop.png")));
    position[7][3].add(new JLabel(new ImageIcon("whitequeen.png")));
    position[7][4].add(new JLabel(new ImageIcon("whiteking.png")));
    position[7][5].add(new JLabel(new ImageIcon("whitebishop.png")));
    position[7][6].add(new JLabel(new ImageIcon("whiteknight.png")));
    position[7][7].add(new JLabel(new ImageIcon("whiterook.png")));


    //Simple for loop to respectively locate my black/white pawn pieces 
    for (int i = 0; i < 8; i++) {
        position[1][i].add(new JLabel(new ImageIcon("blackpawn.png")));
        position[6][i].add(new JLabel(new ImageIcon("whitepawn.png")));
    }
}   
    public static void main(String[] args) 
    {
        new Chess(); 
    }


    /**Lost after this point, I do not know how to correctly implement MouseListener and ActionListener 
     * 
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        

    }
    @Override
    public void mouseClicked(MouseEvent arg0) {


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