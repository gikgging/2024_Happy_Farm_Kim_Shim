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
	
	//The Item constructure for initializing items
	public Item(String name, String initType, double price, int initBonus)
	{
		itemName = name;
		type = initType;
		buyPrice = price;
		bonus = initBonus;
		
	}
	
	//Copy item class
	public Item(Item item)
	{
		itemName = item.getName();
		type = item.getType();
		buyPrice = item.getBuyPrice();
		bonus = item.getBonus();
		
	}
	
	//Get item name
	public String getName()
	{
		return itemName;
	}
	
	//Get item price
	public double getBuyPrice() 
	{
		return buyPrice;
	}
	
	//Get item bonus
	public int getBonus()
	{
		return bonus;
	}
	
	//Get item type
	public String getType()
	{
		return type;
	}

	
	
	
}
