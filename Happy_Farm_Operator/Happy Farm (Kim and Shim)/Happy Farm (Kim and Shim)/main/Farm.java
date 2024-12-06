package main;

import java.util.ArrayList;
import java.util.Random;

import weather.Sunny;
import weather.Rainy;
import weather.Cloudy;
import main.Weather;

//Farm class where the users farm gets managed
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
	
	
	//Constructor of Farm only with farmName
	public Farm(String farmName)
	{
        this.farmName = farmName;
        this.money = 100.0; 
        this.cropSpace = 3;
        this.crops = new ArrayList<>(); 
        this.items = new ArrayList<>(); 
        this.prods = new ArrayList<>();
        WeatherGenerator(); //Generate the first day weather

	}
	
	//Constructor of Farm with each parameter
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
	
	//Return today's farm's weather
	public Weather getTodayWeather() {
		return todayWeather;
	}

	
	//Return Free Space (without crop)
	public int calculateFreeSpace() 
	{
		return cropSpace - crops.size();
	}
	
	//Farm gets money +alpha
	public void increaseMoney(double delta)
	{
		money += delta;
	}

	//Farm loses money -alpha
	public void decreaseMoney(double delta)
	{
		money -= delta;
	}
	
	//Increase cropSpace for weekly bonus!
	public void increaseSpace(int delta)
	{
		cropSpace += delta;
	}
	
	//Return whether we can harvest minimum 1 crop
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

	
	//Make all the growing crops grow
	public void growCrops(Weather todayWeather)
	{
		for(Crop crop: crops) 
		{
			crop.grow(todayWeather);
		}
	}
	
	//Tend specific crop
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
	
	//Buy and Start growing crops
	public void increaseCrops(Crop crop)
	{
		crops.add(new Crop(crop));
		//There's no relation between farmer's inventory and farm's crop
		money -= crop.getBuyPrice();
	}
	
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

	//Use item (with Item class parameter)
	public void decreaseItems(Item item) // It decreased one by one & It's the original form
	{
		//traverseItems();
		items.remove(items.indexOf(item));
		return;
	}
	
	//Use item (with integer parameter)
	public void decreaseItems(int idx)
	{
		items.remove(idx);
		return;
	}
	
	//Decrease products
	public void decreaseProds(Product prod)
	{
		prods.remove(prods.indexOf(prod));
		return;
	}
	
	//Return the array about all of growing crops as string
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
	
	//Return the array about all of items as string
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
	
	//Return Farm's Money
	public double getMoney()
	{
		return money;
	}
	
	//Return Farm's Profit
	public double getProfit()
	{
		return money - initMoney;
	}
	
	//Return Farm's Name
	public String getFarmName() 
	{
		return farmName;
	}
	
	//Return Array about growing crops
	public ArrayList<Crop> getCrops() 
	{
		return crops;
	}
	
	//Return Array about items the farm has
	public ArrayList<Item> getItems() 
	{
		return items;
	}
	
	//Return Array about prods the farm has
	public ArrayList<Product> getProds()
	{
		return prods;
	}
	
	//Return used crop space
	public int getCropSpace() 
	{
		return cropSpace;
	}
	
	
}