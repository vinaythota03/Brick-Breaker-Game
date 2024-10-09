import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 4;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public Gameplay() {
        map=new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 580, 30);
        // map
        map.draw((Graphics2D) g);
        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 500, 100, 8);
        // the ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);
        if(totalBricks<=0) {
        	play=false;
        	ballXdir=0;
        	ballYdir=0;
        	ballposY=570;
           	g.setColor(Color.green);
        	g.setFont(new Font("serif",Font.BOLD,25));
        	g.drawString("YOU WON!",285,300);
        	
        	

        }
        if(ballposY>570) {
        	play=false;
        	ballXdir=0;
        	ballYdir=0;
        	g.setColor(Color.red);
        	g.setFont(new Font("serif",Font.BOLD,30));
        	g.drawString("GAME OVER!", 250, 300);
        	g.setColor(Color.gray);
        	g.setFont(new Font("serif",Font.BOLD,25));
        	g.drawString("SCORE: "+score,285,340);
        	g.drawString("PRESS ENTER TO RESTART", 190, 380);
        }
        g.dispose();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(playerX, 500, 100, 8)) {
                ballYdir = -ballYdir;
            }
            A:for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickheight+50;
                        int brickwidth=map.brickwidth;
                        int brickheight=map.brickheight;
                        Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickheight);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;
                        if(ballRect.intersects(brickRect)){
                             map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.width) {
                            	ballXdir=-ballXdir;
                            }else {
                            	ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == e.VK_RIGHT) {
            if (playerX >= 580) {
                playerX = 580;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == e.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==e.VK_ENTER) {
        	if(!play) {
        		play=true;
        		ballposX=120;
        		ballposY=350;
        		ballXdir=-1;
        		ballYdir=-2;
        		playerX=310;
        		score=0;
        		totalBricks=21;
        		map=new MapGenerator(3,7);
        		repaint();
        	}
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;

    }

    public void moveLeft() {
        play = true;
        playerX -= 20;

    }
}
