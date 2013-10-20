/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers04;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JPanel;
/**
 *
 * @author wan
 */
public class Field extends JPanel {
    public boolean Red;
    public boolean Black;
    public boolean Empty;
    public boolean RedQueen;
    public boolean BlackQueen;
    public boolean Current;

    public Field() {
        Red = false;
        Black = false;
        Empty = true;
        Current = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Current) { g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (Red) {
        g.setColor(Color.RED);
        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
        if (Black){
        g.setColor(Color.BLACK);
        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
        if (BlackQueen) {
        g.setColor(Color.BLACK);
        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        g.setColor(Color.white);
        g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        }
        if (RedQueen) {
        g.setColor(Color.RED);
        g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        g.setColor(Color.white);
        g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
        }
    }

}
