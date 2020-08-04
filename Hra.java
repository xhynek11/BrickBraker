import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class Hra extends JPanel implements KeyListener, ActionListener{
	private boolean play=false;
	private int score;
	
	private int totalBricks;
	private Timer timer;
	private int delay=10;
	
	private int playerX;
	private int ballposX;
	private int ballposY;
	private double ballXdir;
	private double ballYdir;
	
	private MapGenerator map;
	
	public Hra() {
		newGame();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//pozadí
		g.setColor(Color.black);
		g.fillRect(0, 0, 700, 600);
		
		//hráè
		g.setColor(Color.white);
		g.fillRect(playerX, 550, 100, 8);
		
		//Bricks
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score, 560, 30);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		questionEnd(g);
		g.dispose();
	}
	
	public void newGame() {
		if(!play) {
			play=true;
			ballposX=250;
			ballposY=350;
			ballXdir=-randomInt();
			ballYdir=-randomInt()+1;
			playerX=310;
			score=0;
			totalBricks=21;
			map=new MapGenerator(3, 7);
			repaint();
		}
	}
	
	public double randomInt() {
		double rNumber;
		rNumber=Math.random();
		rNumber=(int)(rNumber*20);
		rNumber=rNumber/10+1;
		System.out.println(rNumber);
		return rNumber;
	}
	
	public void questionEnd(Graphics g) {
		if(totalBricks<=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won, Score: "+score, 200, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Pres Enter to restart", 250, 350);
		}
		if(ballposY>570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Score: "+score, 200, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Pres Enter to restart", 250, 350);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX,550,100,1))) {
				ballYdir=-ballYdir;
				System.out.println(ballXdir);
				System.out.println(ballYdir);
			}
			
			A: for(int i=0; i<map.map.length;i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] >0) {
						int brickX=j*map.brickWidth+70;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							
							if(ballXdir>0) {
								ballXdir+=0.1;
							}else {
								ballXdir-=0.1;
							}
							if(ballYdir>0) {
								ballYdir+=0.1;
							}else {
								ballYdir-=0.1;
							}
							
							score+=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir=-ballXdir;
							}
							else {
								ballYdir=-ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballposX+= ballXdir;
			ballposY+= ballYdir;
			if(ballposX<0) {
				ballXdir=-ballXdir;
			}
			if(ballposY<0) {
				ballYdir=-ballYdir;
			}
			if(ballposX>670) {
				ballXdir=-ballXdir;
			}
		}
		
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<10) {
				playerX=10;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			newGame();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public void moveRight() {
		play=true;
		playerX+=20;
	}
	public void moveLeft() {
		play=true;
		playerX-=20;
	}

}
