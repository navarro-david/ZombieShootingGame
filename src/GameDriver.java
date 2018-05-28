
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
//import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameDriver extends Canvas implements KeyListener, Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

    protected boolean[] keys;
    protected BufferedImage back;
    protected int timer = 6;

    public GameDriver() {
        //set up all variables related to the game

        // number of key possibilities
        keys = new boolean[12];


        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);		//starts the key thread to log key strokes
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void setTimer(int value) {
        timer = value;
    }

    public void paint(Graphics window) {
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }
        Graphics2D graphToBack = (Graphics2D) back.createGraphics();

        draw(graphToBack);

        Graphics2D win2D = (Graphics2D) window;
        win2D.drawImage(back, null, 0, 0);

    }

    public abstract void draw(Graphics2D win);

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys[0] = true;
                break;
            case KeyEvent.VK_S:
                keys[1] = true;
                break;
            case KeyEvent.VK_A:
                keys[2] = true;
                break;
            case KeyEvent.VK_D:
                keys[3] = true;
                break;
            case KeyEvent.VK_F:
                keys[4] = true;
                break;

            case KeyEvent.VK_8:
                keys[5] = true;
                break;
            case KeyEvent.VK_5:
                keys[6] = true;
                break;
            case KeyEvent.VK_4:
                keys[7] = true;
                break;
            case KeyEvent.VK_6:
                keys[8] = true;
                break;
            case KeyEvent.VK_PLUS:
                keys[9] = true;
                break;
            case KeyEvent.VK_ENTER:
                keys[10] = true;
                break;
            case KeyEvent.VK_SPACE:
                keys[11] = true;
                break;
            case KeyEvent.VK_UP:
                keys[12] = true;
                break;
            case KeyEvent.VK_DOWN:
                keys[13] = true;
                break;
            case KeyEvent.VK_LEFT:
                keys[14] = true;
                break;
            case KeyEvent.VK_RIGHT:
                keys[15] = true;
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys[0] = false;
                break;
            case KeyEvent.VK_S:
                keys[1] = false;
                break;
            case KeyEvent.VK_A:
                keys[2] = false;
                break;
            case KeyEvent.VK_D:
                keys[3] = false;
                break;
            case KeyEvent.VK_F:
                keys[4] = false;
                break;

            case KeyEvent.VK_8:
                keys[5] = false;
                break;
            case KeyEvent.VK_5:
                keys[6] = false;
                break;
            case KeyEvent.VK_4:
                keys[7] = false;
                break;
            case KeyEvent.VK_6:
                keys[8] = false;
                break;
            case KeyEvent.VK_PLUS:
                keys[9] = true;
                break;
            case KeyEvent.VK_ENTER:
                keys[10] = true;
                break;
            case KeyEvent.VK_SPACE:
                keys[11] = false;
                break;
            case KeyEvent.VK_UP:
                keys[12] = true;
                break;
            case KeyEvent.VK_DOWN:
                keys[13] = true;
                break;
            case KeyEvent.VK_LEFT:
                keys[14] = true;
                break;
            case KeyEvent.VK_RIGHT:
                keys[15] = true;
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(timer);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    public BufferedImage addImage(String name) {

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(name));

        } catch (IOException e) {
            System.out.println(e);
        }

        return img;



    }
    //MouseListener Methods

    public void mouseClicked(MouseEvent e) {
        //System.out.println("Clicked at " + e.getX() + " " + e.getY());
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println("Pressed at " + e.getX() + " " + e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        //System.out.println("Released at " + e.getX() + " " + e.getY());
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse has entered the area");
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse has left the area");
    }
    //MouseMotionListener Methods

    public void mouseDragged(MouseEvent e) {
        //System.out.println("Dragged at " + e.getX() + " " + e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        //System.out.println("Moved at " + e.getX() + " " + e.getY());
    }
    //MouseWheelListener Moethod

    public void mouseWheelMoved(MouseWheelEvent e) {
        //System.out.println("Wheel moved " + e.getWheelRotation() + " clicks and " + e.getScrollAmount() + " units");
    }
}