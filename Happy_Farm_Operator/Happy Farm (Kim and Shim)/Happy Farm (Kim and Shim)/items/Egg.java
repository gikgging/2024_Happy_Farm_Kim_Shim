package items;

import main.Item;

/**
 * Egg class extending the Item class.
 */
public class Egg extends Item
{
	
	/**
	 * Egg(for Bread) constructor.
	 */
	public Egg()
	{
		super("Egg", "Product", 10.0, 0); //Name, type (crop or Product or Health), price, bonus
	}

}
