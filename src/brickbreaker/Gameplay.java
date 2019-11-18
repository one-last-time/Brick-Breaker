/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 *
 * @author ASUS
 */
public class Gameplay extends JPanel implements KeyListener,ActionListener{
    private boolean play=false;
    private int score=0;
    private int totalbricks=21;
    private Timer timer;
    private int delay=8;
    private int playerX=320;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private MapGenerator map;
    public Gameplay()
            {
                map=new MapGenerator(3, 7);
                addKeyListener(this);
                setFocusable(true);
                setFocusTraversalKeysEnabled(false);
                timer=new Timer(delay, this);
                timer.start();
            }
    public void paint(Graphics g)
    {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        //drawing bricks
        map.draw((Graphics2D)g);
        //score
        g.setColor(Color.blue);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
        
        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        //gameover
        if(ballposY>570)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
             g.setFont(new Font("serif",Font.BOLD,25));
             g.drawString("Game Over, "+"score:"+score, 250, 330);
            
        }
        g.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_RIGHT)
    {
        if(playerX>=600)
        {
            playerX=600;
        }
        else
        {
            moveRight();
        }
                
    }
     if(e.getKeyCode()==KeyEvent.VK_LEFT)
    {
        if(playerX<=10)
        {
            playerX=10;
        }
        else
        {
            moveLeft();
        }
    }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        timer.start();
        if(play)
        {
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
            {
                ballYdir=-ballYdir;
            }
          A:  for(int i=0;i<map.map.length;i++)
            {
                for(int j=0;j<map.map[0].length;j++)
                {
                    if(map.map[i][j]>0)
                    {
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickheight+50;
                        int brickwidth=map.brickwidth;
                        int brickheight=map.brickheight;
                        Rectangle rec=new Rectangle(brickX, brickY, brickwidth, brickheight);
                        Rectangle ballrec=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRec=rec;
                        if(ballrec.intersects(brickRec))
                        {
                            map.setbrickvalue(0, i, j);
                            totalbricks--;
                            score+=10;
                            if(ballposX+19<=brickRec.x||ballposX>=brickRec.x+brickRec.width)
                            {
                                ballXdir=-ballXdir;
                            }
                            else
                            {
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                                
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX<0)
            {
                ballXdir=-ballXdir;
            }
             if(ballposY<0)
            {
                 ballYdir=-ballYdir;
            }
              if(ballposX>670)
            {
                 ballXdir=-ballXdir;
            }
              
            
        }
        repaint();
    }
    public void moveRight()
    {
        play=true;
        playerX+=60;
    }
    public void moveLeft()
    {
        play=true;
        playerX-=20;
    }
    
    
}
