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
	
	
	
	/**
	 * Constructor function for Crop Class with each element
	 * kind: The name of the crop.
	 * i_buyPrice: The initial purchase price of the crop.
	 * i_sellPrice: The initial sell price of the crop.
	 * i_goalGrowth: The initial number of days the crop has to grow.
	 */
	public Crop(String kind, double i_buyPrice, double i_sellPrice, int i_day, double i_sun, double i_rain)
	{
		name = kind;
		buyPrice = i_buyPrice;
		sellPrice = i_sellPrice;
		dayMax = i_day;
		sunMax = i_sun;
		rainMax = i_rain;
	}
	
	/**
	 * for copying a crop class (this is used when you buy an crop).
	 * @param crop Crop class.
	 */
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
	
	/**
	 * Returns true of false depending on whether the crop can be harvested.
	 * This is done by checking if the <code>daysGrown</code> is greater than or equal to <code>daysToGrow</code>.
	 * @return true if the crop can be harvested, false otherwise.
	 */
	public boolean canHarvest()
	{
		if ((dayGrown >= dayMax) && (sunGrown >= sunMax) && (rainGrown >= rainMax) )
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Grows the crop by increasing its <code>daysGrown</code> only if the days left to grow is greater than 0.
	 */
	public void grow(Weather todayWeather)
	{
		if (getLeftDay() > 0)
		{
			dayGrown++;
		}
		
		if (getLeftSun() > todayWeather.GetSun())
		{
			sunGrown += todayWeather.GetSun();
		}
		else
		{
			sunGrown = sunMax;
		}
		
		if (getLeftRain() > todayWeather.GetRain())
		{
			rainGrown += todayWeather.GetRain();
		}
		else
		{
			rainGrown = rainMax;
		}
		
		
	}
	
	/**
	 * Tends to the crop by the specified double <code>daysToIncrease</code>, only if the days left to grow is greater than 0.
	 * @param daysToIncrease Number of days to increase growth by.
	 */
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
	
	/**
	 * Returns the purchase price of the crop.
	 * @return the purchase price.
	 */
	public double getBuyPrice()
	{
		return buyPrice;
	}	
	
	/**
	 * Returns the sell price of the crop.
	 * @return the sell price.
	 */
	public double getSellPrice()
	{
		return sellPrice;
	}
	
	/**
	 * Returns the name of the crop.
	 * @return the name of the crop.
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Returns the number of days the crop has grown.
	 * @return The days the crop has grown.
	 */
	public int getDayGrown() 
	{
		return dayGrown;
	}
	
	public int getDayMax()
	{
		return dayMax;
	}
	
	public double getSunGrown()
	{
		return sunGrown;
	}
	
	public double getSunMax()
	{
		return sunMax;
	}
	
	public double getRainGrown()
	{
		return rainGrown;
	}
	
	public double getRainMax()
	{
		return rainMax;
	}

	
	/**
	 * Calculates and returns the days the crop has left to grow by subtracting the <code>daysGrown</code> from <code>daysToGrow</code>.
	 * @return The days the crop has left to grow.
	 */
	public int getLeftDay() 
	{
		return dayMax - dayGrown;
	}
	
	public double getLeftSun()
	{
		return sunMax - sunGrown;
	}
	
	public double getLeftRain()
	{
		return rainMax - rainGrown;
	}
	
}
