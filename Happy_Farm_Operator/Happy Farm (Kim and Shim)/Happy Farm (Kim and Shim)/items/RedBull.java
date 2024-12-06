package items;

import main.Item;

/**
 * RedBull class extending the Item class.
 */
public class RedBull extends Item
{
	
	/**
	 * RedBull(fill health) constructor.
	 */
	public RedBull()
	{
		super("RedBull", "Health", 2.0, 0); //Name, type (crop or Product or Health), price, bonus
	}

}
