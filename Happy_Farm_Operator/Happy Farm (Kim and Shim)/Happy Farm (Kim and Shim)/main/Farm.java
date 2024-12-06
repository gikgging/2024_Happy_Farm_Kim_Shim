package main;

import java.util.ArrayList;
import java.util.Random;

import weather.Sunny;
import weather.Rainy;
import weather.Cloudy;
import main.Weather;

//Farm class where the users farm gets managed.
public class Farm
{
	
	//Farm Name
	private String farmName;
	
	//Crops ArrayList
	private ArrayList<Crop> crops = new ArrayList<Crop>();
		
	//Items ArrayList
	private ArrayList<Item> items = new ArrayList<Item>();
	
	//Products ArrayList
	private ArrayList<Product> prods = new ArrayList<Product>();
	
	//Money the farm has
	private double money;
	
	//Initial money owned when the game started.
	private double initMoney;
	
	//Total crop space available.
	private int cropSpace;
	
	//Generate the weather
	private Weather todayWeather;
	
	
	/**
	 * Constructor function for Farm Class, this constructor initializes variables
	 * @param name The name of the farmer.
	 */

	public Farm(String farmName)  //(SY)For Initialize
	{
        this.farmName = farmName;
        this.money = 100.0; 
        this.cropSpace = 3;
        this.crops = new ArrayList<>(); 
        this.items = new ArrayList<>(); 
        this.prods = new ArrayList<>();
        WeatherGenerator(); //Generate the first day weather

	}
	
	//Load Farm Constructor
	public Farm(String farmName, double money, int cropSpace, ArrayList<Crop> crops,
            ArrayList<Item> items, Farmer farmer) {
		this.farmName = farmName;
		this.money = money;
		this.cropSpace = cropSpace;
		this.crops = crops; 
		this.items = items; 
		this.prods = new ArrayList<>();
		WeatherGenerator(); 
}

	//Generate daily weather of the Farm
	public void WeatherGenerator() {
		Random random = new Random();
		int num = random.nextInt(10);
		
		if (num < 3) { //The probability of Sunny weather = 30%
			todayWeather = new Sunny();
		}
		else if (num < 7) { //The probability of Cloudy weather = 40%
			todayWeather = new Cloudy();
		}
		else { //The probability of Rainy weather = 30%
			todayWeather = new Rainy();
		}
	}
	
	public Weather getTodayWeather() {
		return todayWeather;
	}

	
	/**
	 * Calculates the available free space.
	 * @return Returns the available free space.
	 */
	public int calculateFreeSpace() 
	{
		return cropSpace - crops.size();
	}
	
	/**
	 * Increase the money the farm.
	 * @param alpha The increase of money.
	 */
	public void increaseMoney(double alpha)
	{
		money += alpha;
	}

	/**
	 * Decrease the money of the farm.
	 * @param alpha The decrease of money.
	 */
	public void decreaseMoney(double alpha)
	{
		money -= alpha;
	}
	
	//Increase cropSpace for weekly bonus!
	public void increaseSpace(int delta)
	{
		cropSpace += delta;
	}
	
	/**
	 * To determine whether to harvest crops or not.
	 * @return true if crops can be harvested, false otherwise.
	 */
	public boolean canHarvestCrops() 
	{
		for(Crop crop: crops)
		{
			if (crop.canHarvest())
			{
				return true;
			}
		}
		return false;
	}

	
	/**
	 * A function to grow all of the crops owned
	 * @param todayWeather 
	 */
	public void growCrops(Weather todayWeather)
	{
		for(Crop crop: crops) 
		{
			crop.grow(todayWeather);
		}
	}
	

	public void tendSpecificCrops(String cropName, int daysPlus, Weather weatherPlus)
	{
		for(Crop crop: crops) 
		{
			if (crop.getName() == cropName)
			{
				Weather tendWeather = weatherPlus;
				crop.tend(daysPlus, tendWeather);
			}
		} 
	}
	
	
	/**
	 * Increase crops owned to ArrayList.
	 * @param crop The crop being purchased.
	 */
	//Buy and Start growing crops
	public void increaseCrops(Crop crop)
	{
		crops.add(new Crop(crop));
		//There's no relation between farmer's inventory and farm's crop
		money -= crop.getBuyPrice();
	}
	
	/**
	 * Increase items owned from the item passed through the method  by adding it to the <code>items</code> ArrayList.
	 * @param item The item being purchased.
	 */
	//Buy and Store items
	public void increaseItems(Item item)
	{
		items.add(new Item(item));
		money -= item.getBuyPrice();
	}
	
	//Harvest and Store crops
	public void decreaseCrops(Crop crop) // It decreased one by one & SY added it
	{
		crops.remove(crops.indexOf(crop));
		return;
	}
	
	/**
	 * Removes an item from the <code>items</code> ArrayList by finding the item and then removing it.
	 * @param item The item to remove.
	 */
	//Use item
	public void decreaseItems(Item item) // It decreased one by one & It's the original form
	{
		//traverseItems();
		items.remove(items.indexOf(item));
		return;
	}
	
	public void decreaseItems(int idx)
	{
		items.remove(idx);
		return;
	}
	
	public void decreaseProds(Product prod) // It decreased one by one & SY added it
	{
		
		prods.remove(prods.indexOf(prod));
		return;
	}
	
	/**
	 * Returns the crops in String format by having one crop per line. adds it to the <code>cropString</code> passed in through the method.
	 * @param cropsString Initial String to be added to and returned.
	 * @param crops Crops to be added to the crop string.
	 * @return A String with the format of one crop per line.
	 */
	public String returnCropsString(String cropsString, ArrayList<Crop> crops) 
	{
		int index = 0;
		for(Crop crop: crops) 
		{
			index++;
			cropsString += index + ". " + crop.getName() + "\n";
		}
		return cropsString;
	}
	
	/**
	 * Returns the items in String format by having one item per line. adds it to the <code>itemString</code> passed in through the method.
	 * Only adds items with the specified <code>itemType</code>
	 * @param itemString Initial String to be added to and returned.
	 * @param itemType The type of the item.
	 * @param index Index to start counting from.
	 * @return A String with the format of one item per line.
	 */
	public String returnItemsString(String itemString, String itemType, int index) 
	{
		for(Item item: items) 
		{
			if (item.getType() == itemType)
			{
				index++;
				itemString += index + ". " + item.getName() + "\n";
			}
		}
		return itemString;
	}
	
	/**
	 * Returns the current available money for the farm.
	 * @return Money available.
	 */
	public double getMoney()
	{
		return money;
	}
	
	/**
	 * Returns the profit the farm has made by subtracting <code>initMoney</code> from <code>money</code>.
	 * @return The profit of the farm.
	 */
	public double getProfit()
	{
		return money - initMoney;
	}
	
	/**
	 * Returns the farm name from the <code>farmName</code> variable.
	 * @return The farm name.
	 */
	public String getFarmName() 
	{
		return farmName;
	}
	
	/**
	 * Returns the crops the farm currently has planted from the crops ArrayList.
	 * @return An ArrayList of crops owned.
	 */
	public ArrayList<Crop> getCrops() 
	{
		return crops;
	}
	
	/**
	 * Returns the items the farm currently owns from the items ArrayList.
	 * @return An ArrayList of items owned.
	 */
	public ArrayList<Item> getItems() 
	{
		return items;
	}
	
	public ArrayList<Product> getProds()
	{
		return prods;
	}
	
	/*public void traverseItems()
	{ //Only for debugging
		for(Item item: items)
		{
			System.out.println(item.getName()+" "+item.getType()+" "+item.getBuyPrice()+" "+item.getBonus()+"\n");
		}
		return;
	}
	
	public void traverseCrops()
	{ //Only for debugging
		for(Crop crop: crops)
		{
			System.out.println(crop.getName()+" "+crop.getBuyPrice()+" "+crop.getSellPrice()+" "+crop.getDayMax()+" "+crop.getRainMax()+" "+crop.getSunMax()+"\n");
		}
	}*/
	
	/**
	 * Returns the total crop space the farm has.
	 * @return The crop space.
	 */
	public int getCropSpace() 
	{
		return cropSpace;
	}
	
	
}