package items;

import main.Item;

/**
 * Compost class extending the Item class.
 */
public class Compost extends Item
{
	
	/**
	 * Compost (crop item) constructor.
	 */
	public Compost()
	{
		super("Compost", "Crop", 100.0, 3); //Name, type (crop or Product or Health), price, bonus
	}

}
