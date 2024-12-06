package main;

//Item class in Store
public class Item implements StoreProduct
{
	//The name of the item
	private String itemName;

	//The type of the item
	private String type;
	
	//The buy price of the item
	private double buyPrice;

	//The bonus of the item.
	private int bonus;
	
	/**
	 * The Constructor function for Item Class, this constructor initializes variables <code>itemName</code>, <code>type</code>, <code>purchasePrice</code> and <code>bonus</code>.
	 * @param name Name of the Item
	 * @param initType Type of the Item.
	 * @param price Price of the Item
	 * @param initBonus Bonus of the Item.
	 */
	public Item(String name, String initType, double price, int initBonus)
	{
		itemName = name;
		type = initType;
		buyPrice = price;
		bonus = initBonus;
		
	}
	
	/**
	 * For copying an item class (this is used when you buy an item)
	 * @param item Item Class.
	 */
	public Item(Item item)
	{
		itemName = item.getName();
		type = item.getType();
		buyPrice = item.getBuyPrice();
		bonus = item.getBonus();
		
	}
	
	/**
	 * Returns the name of the item.
	 * @return the name of item.
	 */
	public String getName()
	{
		return itemName;
	}
	
	/**
	 * Returns the purchase price of the item.
	 * @return The purchase price of item.
	 */
	public double getBuyPrice() 
	{
		return buyPrice;
	}
	
	/**
	 * Returns the bonus given by the item.
	 * @return The bonus of item.
	 */
	public int getBonus()
	{
		return bonus;
	}
	
	/**
	 * Returns the type of the item.
	 * @return The type of item.
	 */
	public String getType()
	{
		return type;
	}

	
	
	
}
