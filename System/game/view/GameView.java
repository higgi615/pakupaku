/*
 * Implementation of "Ms Pac-Man" for the "Ms Pac-Man versus Ghost Team Competition", brought
 * to you by Philipp Rohlfshagen, David Robles and Simon Lucas of the University of Essex.
 * 
 * www.pacman-vs-ghosts.net
 * 
 * Code written by Philipp Rohlfshagen, based on earlier implementations of the game by
 * Simon Lucas and David Robles. 
 * 
 * You may use and distribute this code freely for non-commercial purposes. This notice 
 * needs to be included in all distributions. Deviations from the original should be 
 * clearly documented. We welcome any comments and suggestions regarding the code.
 */
package game.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.models.*;
import game.system._Game_;
import game.models.Pair;


@SuppressWarnings("serial")
public final class GameView extends JComponent 
{
	public static final String pathImages="images";

    //for debugging/illustration purposes only: draw colors in the maze to check whether controller is working
    //correctly or not; can draw squares and lines (see NearestPillHeroVS for demostration).
    public static ArrayList<DebugPointer> debugPointers=new ArrayList<DebugPointer>();
    public static ArrayList<DebugLine> debugLines=new ArrayList<DebugLine>();

    public GameView(_Game_ game)
    {
        this.game=game;
        images=loadImages();
        
        pacmanImgs[Game.Direction.UP][0]=getImage("mspacman-up-normal.png");
        pacmanImgs[Game.Direction.UP][1]=getImage("mspacman-up-open.png");
        pacmanImgs[Game.Direction.UP][2]=getImage("mspacman-up-closed.png");
        pacmanImgs[Game.Direction.RIGHT][0]=getImage("mspacman-right-normal.png");
        pacmanImgs[Game.Direction.RIGHT][1]=getImage("mspacman-right-open.png");
        pacmanImgs[Game.Direction.RIGHT][2]=getImage("mspacman-right-closed.png");
        pacmanImgs[Game.Direction.DOWN][0]=getImage("mspacman-down-normal.png");
        pacmanImgs[Game.Direction.DOWN][1]=getImage("mspacman-down-open.png");
        pacmanImgs[Game.Direction.DOWN][2]=getImage("mspacman-down-closed.png");
        pacmanImgs[Game.Direction.LEFT][0]=getImage("mspacman-left-normal.png");
        pacmanImgs[Game.Direction.LEFT][1]=getImage("mspacman-left-open.png");
        pacmanImgs[Game.Direction.LEFT][2]=getImage("mspacman-left-closed.png");
        
        ghostsImgs[0][Game.Direction.UP][0]=getImage("blinky-up-1.png");
        ghostsImgs[0][Game.Direction.UP][1]=getImage("blinky-up-2.png");
        ghostsImgs[0][Game.Direction.RIGHT][0]=getImage("blinky-right-1.png");
        ghostsImgs[0][Game.Direction.RIGHT][1]=getImage("blinky-right-2.png");
        ghostsImgs[0][Game.Direction.DOWN][0]=getImage("blinky-down-1.png");
        ghostsImgs[0][Game.Direction.DOWN][1]=getImage("blinky-down-2.png");
        ghostsImgs[0][Game.Direction.LEFT][0]=getImage("blinky-left-1.png");
        ghostsImgs[0][Game.Direction.LEFT][1]=getImage("blinky-left-2.png");
        
        ghostsImgs[1][Game.Direction.UP][0]=getImage("pinky-up-1.png");
        ghostsImgs[1][Game.Direction.UP][1]=getImage("pinky-up-2.png");
        ghostsImgs[1][Game.Direction.RIGHT][0]=getImage("pinky-right-1.png");
        ghostsImgs[1][Game.Direction.RIGHT][1]=getImage("pinky-right-2.png");
        ghostsImgs[1][Game.Direction.DOWN][0]=getImage("pinky-down-1.png");
        ghostsImgs[1][Game.Direction.DOWN][1]=getImage("pinky-down-2.png");
        ghostsImgs[1][Game.Direction.LEFT][0]=getImage("pinky-left-1.png");
        ghostsImgs[1][Game.Direction.LEFT][1]=getImage("pinky-left-2.png");
        
        ghostsImgs[2][Game.Direction.UP][0]=getImage("inky-up-1.png");
        ghostsImgs[2][Game.Direction.UP][1]=getImage("inky-up-2.png");
        ghostsImgs[2][Game.Direction.RIGHT][0]=getImage("inky-right-1.png");
        ghostsImgs[2][Game.Direction.RIGHT][1]=getImage("inky-right-2.png");
        ghostsImgs[2][Game.Direction.DOWN][0]=getImage("inky-down-1.png");
        ghostsImgs[2][Game.Direction.DOWN][1]=getImage("inky-down-2.png");
        ghostsImgs[2][Game.Direction.LEFT][0]=getImage("inky-left-1.png");
        ghostsImgs[2][Game.Direction.LEFT][1]=getImage("inky-left-2.png");
        
        ghostsImgs[3][Game.Direction.UP][0]=getImage("sue-up-1.png");
        ghostsImgs[3][Game.Direction.UP][1]=getImage("sue-up-2.png");
        ghostsImgs[3][Game.Direction.RIGHT][0]=getImage("sue-right-1.png");
        ghostsImgs[3][Game.Direction.RIGHT][1]=getImage("sue-right-2.png");
        ghostsImgs[3][Game.Direction.DOWN][0]=getImage("sue-down-1.png");
        ghostsImgs[3][Game.Direction.DOWN][1]=getImage("sue-down-2.png");
        ghostsImgs[3][Game.Direction.LEFT][0]=getImage("sue-left-1.png");
        ghostsImgs[3][Game.Direction.LEFT][1]=getImage("sue-left-2.png");
        
        ghostsImgs[4][0][0]=getImage("edible-ghost-1.png");
        ghostsImgs[4][0][1]=getImage("edible-ghost-2.png");
        ghostsImgs[5][0][0]=getImage("edible-ghost-blink-1.png");
        ghostsImgs[5][0][1]=getImage("edible-ghost-blink-2.png");                      
    }
    
    ////////////////////////////////////////
    ////// Visual aids for debugging ///////
    ////////////////////////////////////////
    
    // Adds a node to be highlighted using the color specified
    // NOTE: This won't do anything in the competition but your code will still work
    public synchronized static void addPoints(Game game, Color color, List<Node> nodes)
    {
        for (Node point : nodes)
    		debugPointers.add(new DebugPointer(point.getX(),point.getY(),color));
    }
    
    // Adds a set of lines to be drawn using the color specified (fromNnodeIndices.length must be equals toNodeIndices.length)
    // NOTE: This won't do anything in the competition but your code will still work
    public synchronized static void addLines(Game game, Color color, List<Pair<Node, Node>> pairs)
    {
        for (Pair<Node,Node> pair : pairs)
            debugLines.add(new DebugLine(pair.first().getX(), pair.first().getY(), pair.second().getX(), pair.second().getY(), color));
    }

    // Adds a set of lines to be drawn using the color specified
    // NOTE: This won't do anything in the competition but your code will still work
    public synchronized static void addLines(Game game, Color color, List<Node> fromNodes, List<Node> toNodes)
    {
        int size = Math.min(fromNodes.size(), toNodes.size());
        for (int index = 0; index < size; index++)
        {
            Node from = fromNodes.get(index);
            Node to = toNodes.get(index);
            debugLines.add(new DebugLine(from.getX(), from.getY(), to.getX(), to.getY(), color));
        }
    }

    //Adds a line to be drawn using the color specified
    //NOTE: This won't do anything in the competition but your code will still work
    public synchronized static void addLines(Game game, Color color, Node fromNode, Node toNode)
    {
    	debugLines.add(new DebugLine(fromNode.getX(), fromNode.getY(), toNode.getX(), toNode.getY(), color));
    }

    ////////////////////////////////////////
    ////// Visual aids for debugging ///////
    ////////////////////////////////////////

    public void paintComponent(Graphics g)
    {
        if(offscreen==null)
        {
            offscreen=createImage(this.getPreferredSize().width,this.getPreferredSize().height);
            bufferGraphics=offscreen.getGraphics();
        }

        drawMaze();
        drawDebugInfo();	//this will be used during testing only and will be disabled in the competition itself
        drawPills();
        drawPowerPills();
        drawPacMan();
        drawGhosts();
        drawLives();
        drawGameInfo();

        if(game.gameOver())
            drawGameOver();

        g.drawImage(offscreen,0,0,this);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(game.getWidth()*MAG,game.getHeight()*MAG+20);
    }

    public GameView showGame()
    {
        this.frame=new GameFrame(this);

        //just wait for a bit for player to be ready
        try{Thread.sleep(2000);}catch(Exception e){}

        return this;
    }

    public GameFrame getFrame()
    {
        return frame;
    }

    public class GameFrame extends JFrame
    {
        public GameFrame(JComponent comp)
        {
            getContentPane().add(BorderLayout.CENTER,comp);
            pack();
            Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation((int)(screen.getWidth()*3/8),(int)(screen.getHeight()*3/8));
            this.setVisible(true);
            this.setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            repaint();
        }
    }

    private void drawDebugInfo()
    {
    	for(int i=0;i<debugPointers.size();i++)
    	{
    		DebugPointer dp=debugPointers.get(i);
    		bufferGraphics.setColor(dp.color);
    		bufferGraphics.fillRect(dp.x*MAG+1,dp.y*MAG+5,10,10);
    	}
    	
    	for(int i=0;i<debugLines.size();i++)
    	{
    		DebugLine dl=debugLines.get(i);
    		bufferGraphics.setColor(dl.color);
    		bufferGraphics.drawLine(dl.x1*MAG+5,dl.y1*MAG+10,dl.x2*MAG+5,dl.y2*MAG+10);
    	}
    	
    	debugPointers.clear();
    	debugLines.clear();
    }

    private void drawMaze()
    {
    	bufferGraphics.setColor(Color.BLACK);
    	bufferGraphics.fillRect(0,0,game.getWidth()*MAG,game.getHeight()*MAG+20);
        
        if(images[game.getCurMazeNum()]!=null)
        	bufferGraphics.drawImage(images[game.getCurMazeNum()],2,6,null);
    }

    private void drawPills()
    {
        List<Node> pillNodes = game.getCurMaze().getPillNodes();
        
        bufferGraphics.setColor(Color.white);

        for (Node pill : pillNodes)
        	if (game.checkPill(pill))
        		bufferGraphics.fillOval(pill.getX()*MAG+4,pill.getY()*MAG+8,3,3);
    }
    
    private void drawPowerPills()
    {
          List<Node> powerPillNodes = game.getCurMaze().getPowerPillNodes();
          
          bufferGraphics.setColor(Color.white);

          for (Node pill : powerPillNodes)
          	if(game.checkPowerPill(pill))
          		bufferGraphics.fillOval(pill.getX()*MAG+1,pill.getY()*MAG+5,8,8);
    }
    
    private void drawPacMan()
    {
        Hero hero = game.getHero();
    	Node heroLoc = hero.getLocation();
    	int pacDir = hero.getDirection();
        
    	if(pacDir>=0 && pacDir<4)
    		pacManDir=pacDir;
    	
    	bufferGraphics.drawImage(pacmanImgs[pacManDir][(game.getTotalTime()%6)/2],heroLoc.getX()*MAG-1,heroLoc.getY()*MAG+3,null);
    }

    private void drawGhosts() 
    {
    	for(int index = 0; index< Game.NUM_ENEMY; index++)
    	{
    	    Enemy enemy = game.getEnemy(index);
	    	Node loc = enemy.getLocation();
	    	int x = loc.getX();
	    	int y = loc.getY();
	    	
	    	if(enemy.getEdibleTime() > 0)
	    	{
	    		if(enemy.getEdibleTime() < _Game_.EDIBLE_ALERT && ((game.getTotalTime() % 6) / 3) ==0)
	    			bufferGraphics.drawImage(ghostsImgs[5][0][(game.getTotalTime()%6)/3],x*MAG-1,y*MAG+3,null);
	            else
	            	bufferGraphics.drawImage(ghostsImgs[4][0][(game.getTotalTime()%6)/3],x*MAG-1,y*MAG+3,null);
	    	}
	    	else 
	    	{
	    		if(enemy.getLairTime() > 0)
	    			bufferGraphics.drawImage(ghostsImgs[index][Game.Direction.UP][(game.getTotalTime()%6)/3],x*MAG-1+(index*5),y*MAG+3,null);
	    		else    		
	    			bufferGraphics.drawImage(ghostsImgs[index][enemy.getDirection()][(game.getTotalTime()%6)/3],x*MAG-1,y*MAG+3,null);
	        }
    	}
    }

    private void drawLives()
    {
    	for(int i=0;i<game.getLivesRemaining()-1;i++) //-1 as lives remaining includes the current life
    		bufferGraphics.drawImage(pacmanImgs[Game.Direction.RIGHT][0],210-(30*i)/2,260,null);
    }
    
    private void drawGameInfo()
    {
    	bufferGraphics.setColor(Color.WHITE);
    	bufferGraphics.drawString("S: ",4,271);
    	bufferGraphics.drawString(""+game.getScore(),16,271);        
    	bufferGraphics.drawString("L: ",78,271);
    	bufferGraphics.drawString(""+(game.getCurLevel()+1),90,271);        
    	bufferGraphics.drawString("T: ",116,271);
    	bufferGraphics.drawString(""+game.getLevelTime(),129,271);
    }
    
    private void drawGameOver()
    {
    	bufferGraphics.setColor(Color.WHITE);
    	bufferGraphics.drawString("Game Over",80,150);
    }
    
    private BufferedImage[] loadImages()
    {
        BufferedImage[] images=new BufferedImage[4];
        
        for(int i=0;i<images.length;i++)
        	images[i]=getImage(mazes[i]);            
        
        return images;
    }
    
    private BufferedImage getImage(String fileName) 
    {
        BufferedImage image=null;
        
        try
        {
            image=ImageIO.read(new File(pathImages+System.getProperty("file.separator")+fileName));
        }
        catch(IOException e) 
        {
            e.printStackTrace();
        }
        
        return image;
    }

    private static final String[] mazes={"maze-a.png","maze-b.png","maze-c.png","maze-d.png"};
    private int MAG = 2;
    private int pacManDir = Game.INITIAL_HERO_DIR;

    private final _Game_ game;
    private final BufferedImage[][] pacmanImgs=new BufferedImage[4][3];
    private final BufferedImage[][][] ghostsImgs=new BufferedImage[6][4][2];
    private final BufferedImage[] images;

    private GameFrame frame;
    private Graphics bufferGraphics;
    private Image offscreen;

    private static class DebugPointer
    {
    	public int x,y;
    	public Color color;
    	
    	public DebugPointer(int x,int y,Color color)
    	{
    		this.x=x;
    		this.y=y;
    		this.color=color;
    	}
    }
    
    private static class DebugLine
    {
    	public int x1,y1,x2,y2;
    	public Color color;
    	
    	public DebugLine(int x1,int y1,int x2,int y2,Color color)
    	{
    		this.x1=x1;
    		this.y1=y1;
    		this.x2=x2;
    		this.y2=y2;
    		this.color=color;
    	}
    }
}