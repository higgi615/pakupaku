package game.controllers;

import game.models.Game;

/*
 * Interface that Enemies controllers must implement. The only method that is
 * required is getActions(-), which returns the direction to be taken: 
 * Up - Right - Down - Left -> 0 - 1 - 2 - 3
 * Any other number is considered to be a lack of action (Neutral). 
 */
public interface EnemyController
{
	public void init();
	public void update(Game game, long timeDue);
	public void shutdown();

	public int[] getActions();//Game game, long timeDue);
}