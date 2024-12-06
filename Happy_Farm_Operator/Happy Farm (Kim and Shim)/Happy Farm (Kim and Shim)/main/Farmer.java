package main;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

//Farmer Class is about the activity he/she does
public class Farmer 
{
	//The name of the farmer.
	private String farmerName;
	
	//The Strength of the farmer.
	private int farmerStrength;
	
	//The days passed of the farmer on the farm, starts at 1.
	private int farmerDays;
	
	//The Maximum Strength which the farmer could have
	private int farmerMaxStrength;
	
	//The inventory for crops
	private Map<String, Integer> cropInventory = new HashMap<>();
	
	//The inventory for items
	private Map<String, Integer> itemInventory = new HashMap<>();
	
	//Constructor for initialize Farmer
	 public Farmer(String name)
	 {
	        this.farmerName = name;
	        this.farmerStrength = 20;
	        this.farmerMaxStrength = 20;
	        this.farmerDays = 1; 
	        
	        initInventories(); //Initialize Inventory
	   		}
	 
	//Constructor with customizing with each parameter
	public Farmer(String name, int strength, int maxStrength, int days)
	{
		this.farmerName = name;
		this.farmerStrength = strength;
		this.farmerMaxStrength = maxStrength;
		this.farmerDays = days;
		
		initInventories(); //Initialize Inventory
	}
	
	//Initialize Inventory
	private void initInventories() {
		//Initialize cropInventory
		cropInventory.put("Cabbage", 0);
		cropInventory.put("Garlic", 0);
		cropInventory.put("Pepper", 0);
		cropInventory.put("Rice", 0);
		cropInventory.put("Wheat", 0);
		
		//Initialize itemInventory
		itemInventory.put("Compost", 0);
		itemInventory.put("Egg", 0);
		itemInventory.put("Namool", 0);
		itemInventory.put("RedBull", 0);
	}
	
	//Function to increase the days passed of the farmer on the farm, increases the days passed by 1
	public void increaseDays()
	{
		farmerDays++;
	}
	
	//Return the farmer's strength.
	public int getFarmerStrength()
	{
		return farmerStrength;
	}
	
	//Return the farmer's Max Strength at that day (for initialize everyday)
	public int getFarmerMaxStrength()
	{
		return farmerMaxStrength;
	}
	
	//Return the days passed for the farmer.
	public int getDays()
	{
		return farmerDays;
	}
	
	//Return the farmer's name.
	public String getFarmerName()
	{
		return farmerName;
	}
	
	//Return Inventory for Crops
	public Map<String, Integer> getCropInven()
	{
		return cropInventory;
	}
	
	//Return Inventory for Items
	public Map<String, Integer> getItemInven()
	{
		return itemInventory;
	}
	
	//Add an Item in Crop Inventory (Already Harvested / It's different with farm's crop)
	public void addCropInven(String cropName)
	{
		if(cropInventory.containsKey(cropName))
		{
			cropInventory.put(cropName, cropInventory.get(cropName)+1);
		}
		else
		{
			cropInventory.put(cropName,  1);
		}
	}
	
	//Add an Item in Item Inventory (It's same as farm's item)
	public void addItemInven(String itemName)
	{
		if(itemInventory.containsKey(itemName))
		{
			itemInventory.put(itemName, itemInventory.get(itemName)+1);
		}
		else
		{
			itemInventory.put(itemName, 1);
		}
		return;
	}
	
	//Remove a Crop from Crop Inventory
	public void subCropInven(String cropName, int dec)
	{
		if(cropInventory.containsKey(cropName))
		{
			Integer value = getCropValue(cropName);
			if(value != null && value >= dec)
			{
				cropInventory.put(cropName, value-dec);
			}
			else
			{
				return;
			}
		}
	}
	
	//Remove an Item from Item Inventory
	public void subItemInven(String itemName, int dec)
	{
		if(itemInventory.containsKey(itemName))
		{
			Integer value = getItemValue(itemName);
			if(value != null && value >= dec)
			{
				itemInventory.put(itemName, value-dec);
				
			}
		}
		return;
	}
	
	//Return whether I can produce this product
	public boolean canProd(Product prod)
	{
		Map<String, Integer> ingredients = prod.getIngrd();
		for(String kindIngrd : ingredients.keySet())
		{
			if(cropInventory.containsKey(kindIngrd))
			{
				if(cropInventory.get(kindIngrd) < ingredients.get(kindIngrd)) //hold amount < requested amount
				{
					return false;
				}
			} //Find it from CropInventory
			else if(itemInventory.containsKey(kindIngrd))
			{
				if(itemInventory.get(kindIngrd) < ingredients.get(kindIngrd)) //hold amount < requested amount
				{
					return false;
				}
			} //Find it from ItemInventory
		}
		
		if(farmerStrength < prod.getDecStr())
		{
			return false;
		} //If needed Strength is bigger than his actual strength
		
		return true;
	}
	
	//Set farmer's strength as set
	public void setStrength(int set)
	{
		this.farmerStrength = set;
		return;
	}
	
	//Add farmer's strength +add
	public void addMaxStrength(int add)
	{
		this.farmerMaxStrength += add;
		return;
	}
	
	//Decrease farmer's strength
	public void subStrength(int dec)
	{
		if(farmerStrength >= dec)
		{
			farmerStrength -= dec;
		}
		return;
	}
	
	//Return value of the crop (when it doesn't exist -> null)
	public Integer getCropValue(String cropName) 
	{
		if(cropInventory.containsKey(cropName))
		{
			return cropInventory.get(cropName);
		}
		else
			return null;
	}
	
	//Return value of the item (when it doesn't exist -> null)
	public Integer getItemValue(String itemName) 
	{
		if(itemInventory.containsKey(itemName))
		{
			return itemInventory.get(itemName);
		}
		else
			return null;
	}
}
