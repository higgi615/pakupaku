package game.controllers.examples;

import java.util.List;
import game.controllers.EnemyController;
import game.models.*;

public final class AttractRepelGhosts implements EnemyController
{	
	private final static float CONSISTENCY=0.9f;	//move towards/away with this probability
	private boolean attract;

	public AttractRepelGhosts(boolean attract)	//Please note: constructors CANNOT take arguments in the competition!
	{
		this.attract=attract;	//approach or retreat from Ms Pac-Man
	}

	private int[] actions;
    public int[] getActions() { return actions; }
	public void init() { }
	public void shutdown() { }
	public void update(Game game,long timeDue)
	{		
		actions = new int[Game.NUM_ENEMY];

		Enemy[] enemies = (Enemy[]) game.getEnemies().toArray();
		for(int i=0;i<actions.length;i++)	//for each ghost
		{
			Enemy enemy = enemies[i];
			if (enemy.requiresAction())        //if it requires an action
			{
				if (Game.rng.nextFloat() < CONSISTENCY)    //approach/retreat from the current node that Ms Pac-Man is at
					actions[i] = enemy.getNextDir(game.getHero().getLocation(), attract);
				else                                    //else take a random action
				{
					List<Integer> possibleDirs = enemy.getPossibleDirs();    //takes a random LEGAL action. Could also just return any random number
					actions[i] = possibleDirs.get(Game.rng.nextInt(possibleDirs.size()));
				}
			}
		}
	}
}