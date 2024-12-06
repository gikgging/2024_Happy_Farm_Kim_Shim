package main;

import java.util.Map;
import java.util.HashMap;
import main.Farmer;

public class Product
{
	//The name of the product
	private String productName;
	
	//The ingredients for the product
	private Map<String, Integer> ingredients;
	
	//The price for the product to be sold
	private double sellPrice;
	
	//The decreased farmer's strength while making product
	private int decreaseStrength;
	
	
	//Constructor
	public Product(String name, double i_sellPrice, int dec_strength)
	{
		productName = name;
		ingredients = new HashMap<>();
		sellPrice = i_sellPrice;
		decreaseStrength = dec_strength;
	}
	
	public Product(Product prod)
	{
		productName = prod.productName;
		ingredients = prod.ingredients;
		sellPrice = prod.sellPrice;
		decreaseStrength = prod.decreaseStrength;
	}
	
	//Functions for returning value
	public String getName()
	{
		return productName;
	}
	
	public Map<String, Integer> getIngrd()
	{
		return ingredients;
	}
	
	public double getSellPrice()
	{
		return sellPrice;
	}
	
	public int getDecStr()
	{
		return decreaseStrength;
	}
	
	
	public void addIngrd(String name, int totalAmount)
	{
		ingredients.put(name, totalAmount);
		return;
	}	
	
}
