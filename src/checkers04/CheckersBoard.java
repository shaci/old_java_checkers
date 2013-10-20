package checkers04;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author wan
 */
public class CheckersBoard extends JFrame implements ActionListener, MouseListener {
    private Handler handler;
    private JLabel title;
    private Field [][] fieldArray;
    private JPanel board;
    public static void main(String[] args) {
        CheckersBoard chBrd = new CheckersBoard();
        chBrd.setVisible(true);
        chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CheckersBoard() {
        Container cp = getContentPane();
        JPanel boardParent = new JPanel();
        boardParent.setLayout(new GridBagLayout());              
        board = new JPanel();
        board.addMouseListener(this);
        board.setPreferredSize(new Dimension(400, 400));
        board.setMinimumSize(new Dimension(400, 400));
        board.setMaximumSize(new Dimension(400, 400));

        boardParent.setPreferredSize(new Dimension(400, 400));
        boardParent.setMinimumSize(new Dimension(400, 400));
        boardParent.setMaximumSize(new Dimension(400, 400));

        board.setLayout(new GridLayout(8,8));

        /*
        for (int i = 0; i < 64; i++)
        {
            JPanel square =  new Field();
            board.add( square );
            
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
            else
                square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
        }*/
        fieldArray = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++ ){                
                fieldArray[i][j] = new Field();                
                board.add(fieldArray[i][j]);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        if (i < 3) {
                            fieldArray[i][j].Black = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Red = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    }
                    else {
                        fieldArray[i][j].setBackground(Color.blue);
                    }
                    //fieldArray[i][j].setBackground( j % 2 == 0 ? Color.white : Color.blue );
                }
                else {
                    if (j % 2 == 0) {
                        fieldArray[i][j].setBackground(Color.blue);
                    }
                    else {
                        if (i < 3) {
                           fieldArray[i][j].Black = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Red = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    }
                    //fieldArray[i][j].setBackground( j % 2 == 0 ? Color.blue : Color.white );
                }
            }
        }

        boardParent.add(board);

        JPanel top = new JPanel();
        title = new JLabel("Checkers Board");
        top.add(title);
        JPanel down = new JPanel();
        JButton start = new JButton("START");
        down.add(start);
        
        cp.add(top, BorderLayout.NORTH);
        cp.add(boardParent, BorderLayout.CENTER);
        cp.add(down, BorderLayout.SOUTH);
        setBounds(50, 50, 500, 600);

        handler = new Handler(fieldArray);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseClicked(MouseEvent e) {       
    }

    public void mousePressed(MouseEvent e) {
        int y = e.getX() / 50;
        int x = e.getY() / 50;
        //fieldArray[x][y] = new RedChecker();
        title.setText(x + " " + y);        

        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j].Black = false;
                fieldArray[i][j].Current = false;
            }
        }
        fieldArray[x][y].Black = true;
        fieldArray[x][y].Current = true;*/
        handler.clickHandler(x, y);
        repaint();

    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }
}
