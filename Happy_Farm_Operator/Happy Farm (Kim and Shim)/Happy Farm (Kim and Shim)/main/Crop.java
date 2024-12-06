package main;

import main.Weather;

/**
 * Crop class where crops are extended from.
 * Here the user can grow the crop, get days left to grow and get the sell price.
 * -Must be modified by SY and HJ-
 */
public class Crop implements StoreProduct
{
	//The kind(name) of the crop
	private String name;
	
	//The price as which the user would purchase the crop.
	private double buyPrice;
	
	//The price as which the user would sell the crop.
	private double sellPrice;
	
	//The Maximum Days for Harvest
	private int dayMax;
	
	//The Days to be grown
	private int dayGrown = 0;
	
	//The Maximum amount of Sun for Harvest
	private double sunMax;
	
	//The amount of Sun to be grown
	private double sunGrown = 0;
	
	//The Maximum amount of rain for Harvest
	private double rainMax;
	
	//The amount of Rain to be grown
	private double rainGrown = 0;
	
	
	//Constructor function for Crop with each parameter
	public Crop(String kind, double i_buyPrice, double i_sellPrice, int i_day, double i_sun, double i_rain)
	{
		name = kind;
		buyPrice = i_buyPrice;
		sellPrice = i_sellPrice;
		dayMax = i_day;
		sunMax = i_sun;
		rainMax = i_rain;
	}
	
	//Constructor function for Crop with Crop class parameter
	public Crop(Crop crop)
	{
		name = crop.getName();
		buyPrice = crop.getBuyPrice();
		sellPrice = crop.getSellPrice();
		dayMax = crop.getDayMax();
		sunMax = crop.getSunMax();
		rainMax = crop.getRainMax();
		dayGrown = 0;
		sunGrown = 0;
		rainGrown = 0;
	}
	
	//Return whether we can harvest the crop
	public boolean canHarvest()
	{
		if ((dayGrown >= dayMax) && (sunGrown >= sunMax) && (rainGrown >= rainMax) )
		{
			return true;
		}
		return false;
	}
	
	
	//Grow the crop
	public void grow(Weather todayWeather)
	{
		if (getLeftDay() > 0)
		{
			dayGrown++;
		} //Add Day
		
		if (getLeftSun() > todayWeather.GetSun())
		{
			sunGrown += todayWeather.GetSun();
		}
		else
		{
			sunGrown = sunMax;
		} //Add Sun
		
		if (getLeftRain() > todayWeather.GetRain())
		{
			rainGrown += todayWeather.GetRain();
		}
		else
		{
			rainGrown = rainMax;
		} //Add Rain
		return;
	}
	
	//Tend the crop with Item or Water
	public void tend(int increasedDay, Weather tendWeather)
	{
		//For Day
		if (dayMax - dayGrown < increasedDay)
		{
			dayGrown = dayMax;
		}
		else
		{
			dayGrown += increasedDay;
		}
		
		//For Sun
		if (sunMax - sunGrown > tendWeather.GetSun())
		{
			sunGrown += tendWeather.GetSun();
		}
		else
		{
			sunGrown = sunMax;
		}
		
		//For Rain
		if (rainMax - rainGrown > tendWeather.GetRain())
		{
			rainGrown += tendWeather.GetRain();
		}
		else
		{
			rainGrown = rainMax;
		}
	}
	
	//Return the BuyPrice of the crop
	public double getBuyPrice()
	{
		return buyPrice;
	}	

	//Return the SellPrice of the crop
	public double getSellPrice()
	{
		return sellPrice;
	}
	
	//Return the Name of the crop
	public String getName() 
	{
		return name;
	}
	
	//Return the Days the crop is being grown by now
	public int getDayGrown() 
	{
		return dayGrown;
	}
	
	//Return the Days for completed grown crop
	public int getDayMax()
	{
		return dayMax;
	}
	
	//Return the Sun the crop has gotten by now
	public double getSunGrown()
	{
		return sunGrown;
	}
	
	//Return the Sun the crop needed to be completed
	public double getSunMax()
	{
		return sunMax;
	}
	
	//Return the Water the crop has gotten by now
	public double getRainGrown()
	{
		return rainGrown;
	}
	
	//Return the Water the crop needed to be completed
	public double getRainMax()
	{
		return rainMax;
	}

	
	//How many days are left to be completed
	public int getLeftDay() 
	{
		return dayMax - dayGrown;
	}
	
	//How many suns are left to be completed
	public double getLeftSun()
	{
		return sunMax - sunGrown;
	}
	
	//How many days are left to be completed
	public double getLeftRain()
	{
		return rainMax - rainGrown;
	}
	
}
