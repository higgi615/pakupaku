package game.controllers.examples;

import java.awt.Color;
import java.util.ArrayList;
import game.controllers.HeroController;
import game.core.G;
import game.core.Game;
import game.core.GameView;
import game.core.Node;

/*
 * Same as NearestPillHero but does some visuals to illustrate what can be done.
 * Please note: the visuals are just to highlight different functionalities and may
 * not make sense from a controller's point of view (i.e., they might not be useful)
 * Comment/un-comment code below as desired (drawing all visuals would probably be too much).
 */
public final class NearestPillHeroVS implements HeroController
{	
	public int getAction(Game game,long timeDue)
	{		
		Node[] pills = game.getPillNodes();
		Node[] powerPills = game.getPowerPillNodes();
		Node current = game.getHero().getLocation();

		ArrayList<Node> targets = new ArrayList<Node>();
		
		for(int i=0;i<pills.length;i++)			
			if(game.checkPill(i))
				targets.add(pills[i]);
		
		for(int i=0;i<powerPills.length;i++)			
			if(game.checkPowerPill(i))
				targets.add(powerPills[i]);

		Node[] targetsArray=new Node[targets.size()];
		
		for(int i=0;i<targetsArray.length;i++)
			targetsArray[i] = targets.get(i);
		
		Node nearest = game.getTarget(current, targetsArray,true,G.DM.PATH);
		
		//add the path that Ms Pac-Man is following
//		GameView.addPoints(game,Color.GREEN,game.getPath(current,nearest));
		
		//add the path from Ms Pac-Man to the first power pill
		GameView.addPoints(game,Color.CYAN,game.getPath(current,powerPills[0]));
		
		//add the path AND ghost path from Ghost 0 to the first power pill (to illustrate the differences)
//		if(game.getLairTime(0)==0)
//		{
//			GameView.addPoints(game,Color.ORANGE,game.getPath(game.getCurEnemyLoc(0),powerPills[0]));
//			GameView.addPoints(game,Color.YELLOW,game.getEnemyPath(0,powerPills[0]));
//		}
		
		//add the path from Ghost 0 to the closest power pill
//		if(game.getLairTime(0)==0)
//			GameView.addPoints(game,Color.WHITE,game.getEnemyPath(0,game.getEnemyTarget(0,powerPills,true)));
		
		//add lines connecting Ms Pac-Man and the power pills
//		for(int i=0;i<powerPills.length;i++)
//			GameView.addLines(game,Color.CYAN,current,powerPills[i]);
		
		//add lines to the ghosts (if not in lair) - green if edible, red otherwise
		for(int i = 0; i<G.NUM_ENEMY; i++)
			if(game.getLairTime(i)==0)
				if(game.isEdible(i))
					GameView.addLines(game, Color.GREEN, current, game.getCurEnemyLoc(i));
				else
					GameView.addLines(game, Color.RED, current, game.getCurEnemyLoc(i));

		return game.getNextDir(game.getHero().getLocation().getNeighbors(), nearest,true,Game.DM.PATH);
	}
}