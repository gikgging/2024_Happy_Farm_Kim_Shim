package main;

//Play with this file!!! (SY & HJ)

import gui.MainScreen;
import gui.SetupScreen;
import gui.StoreScreen;
import gui.TendCropsScreen;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;
import weather.Rainy;
import weather.Cloudy;

// Game Center
// There exist the most important functions to be run.
// In this screen the user can play the farm simulator game.
public class GameCenter
{
	// Farm's weather
	private Weather weather;
	
	// Player's farm
	private Farm farm;
	
	// Player
	private Farmer farmer;
	
	// Store
	private Store store;
	
	//The Goal Profit for every week
	private double goal;
	
	//The setup window.
	private SetupScreen setupWindow;
	
	private MainScreen mainScreen;
	
	//Set up the Game (farmerName, farmName, GUI, goal, weather, store)
	public void setupGame(String farmerName, String farmName) 

	{
		
		if ((farmerName.length() < 3 || farmerName.length() > 15 || !isAlpha(farmerName)) && (farmName.length() < 3 || farmName.length() > 15 || !isAlpha(farmName))) 
		{
			setupWindow.setWarningText("farmer's name and farm's name are");
		}
		else if (farmerName.length() < 3 || farmerName.length() > 15 || !isAlpha(farmerName)) 
		{
			setupWindow.setWarningText("farmer's name is");
		}
		else if (farmName.length() < 3 || farmName.length() > 15 || !isAlpha(farmName)) 
		{
			setupWindow.setWarningText("farm's name is");
		}
		else 
		{
			setupWindow.setWarningText("");
			farmer = new Farmer(farmerName); // First day
			farm = new Farm(farmName);
			goal = 100;
			weather = farm.getTodayWeather();
			store = new Store(); //Make New Store and Initialize
			
			closeSetupScreen(setupWindow);
		}
	}
		
	
	//Set everyday's start
	public void nextDay() 
	{
		farmer.increaseDays();
		farmer.setStrength(farmer.getFarmerMaxStrength());
		farmer.addMaxStrength(5); //+5 MaxStrength
		farm.WeatherGenerator(); //Change Weather
		weather = farm.getTodayWeather(); //Save it in here
		farm.growCrops(weather); //To make crops grow
		
		if (farmer.getDays() % 7 == 0) { //For everyweek
	        String results = dealwithGoal(); //Deal with weekly goals
	        mainScreen.showGoalResults(results); //Show the results at main screen
	    }
	}
	
	//Find Item from the list of Items
	public int findItemIndex(String itemName)
	{
		int count = 0;
		int index = -1;
		for (Item item: farm.getItems())
		{
			if (item.getName() == itemName)
			{
				index = count;
			}
			count++;
		}
		return index;
	}
	
	//Tend Crops
	public String tendToCrops(int cropIndex, String itemName) //1 tend = Strength 10
	{
		if (farmer.getFarmerStrength() < 10) 
		{
			return "You can't crop: You don't have much strength now.";
		}
		else
		{
			String cropName = returnCropTypeArray()[cropIndex];
			farmer.subStrength(10);
			if (itemName == "Water (free)") // Watered crops
			{
				Weather tendWeather = new Rainy(); //Default Bonus according to the Rainy Weather
				farm.tendSpecificCrops(cropName, 0, tendWeather);
				return "Tended to every " + cropName + " by watering them";
			}
			else
			{
				Item itemUsed = farm.getItems().get(findItemIndex(itemName));
				
				if(itemUsed.getType() == "Crop")
				{
					Weather tendWeather = new Cloudy(); //Default Bonus according to the Cloudy Weather
					farm.tendSpecificCrops(cropName, itemUsed.getBonus(), tendWeather);
					farm.decreaseItems(itemUsed);
					farmer.subItemInven(itemName, 1);
					return "Tended to every " + cropName + " by using " + itemUsed.getName() + " on them";
				}
				else
				{
					return "You can't tend " + cropName + " by using " + itemUsed.getName() + ", because its type is " + itemUsed.getType();
				}
				
			}
		}
	}
	
	//Increase Strength 50 By Drinking RedBull
	public String plusStrength(String itemName)
	{
		Item itemUsed = farm.getItems().get(findItemIndex(itemName));
		
		if(itemUsed.getType() == "Health")
		{
			int delta = 50;
			if(farmer.getFarmerMaxStrength() - farmer.getFarmerStrength() < 50)
			{
				delta = farmer.getFarmerMaxStrength() - farmer.getFarmerStrength();
				farmer.setStrength(farmer.getFarmerMaxStrength());
			}
			else
			{
				farmer.setStrength(farmer.getFarmerStrength() + 50);
			}
			farm.decreaseItems(itemUsed);
			farmer.subItemInven(itemName, 1);
			return "Your strength is increased by "+delta;
		}
		else
		{
			return "You can't increase your strength by using " + itemUsed.getName();
		}
	}

	//Harvest Crop
	public void harvestAvailableCrops()
	{
		ArrayList<Crop> cropsToRemove = new ArrayList<Crop>();
		
		for(Crop crop: farm.getCrops())
		{
			if (crop.canHarvest())
			{
				cropsToRemove.add(crop);
			}
		}
		
		for (Crop crop: cropsToRemove) 
		{
			farm.decreaseCrops(crop); //Harvest Crops from farm
			farmer.addCropInven(crop.getName());
		}
		
		return;
	}

	//Harvest all the possible crops (Managing whole harvest process)
	public String harvestCrops() //1 Harvest = Strength 20
	{
		if (farmer.getFarmerStrength() < 10) 
		{
			return "You can't harvest: You don't have much strength now.";
		}
		else if (farm.canHarvestCrops()) 
		{
			farmer.subStrength(20);
			harvestAvailableCrops();
			return "You harvest all your crops!\r\n";
		}
		else 
		{
			return "There are no available crops to harvest";
		}
	}
	
	//Sell Crops from CropInventory
	public String sellCrops(String cropName)
	{
		String returnStrings = "";
		double moneyMade = 0;
		
		if (!farmer.getCropInven().containsKey(cropName)) {
	        return "Invalid crop name: " + cropName + ". Please check your inventory."; // 유효하지 않은 작물 이름
	    }
		
		if(farmer.getCropInven().get(cropName) > 0)
		{
			Crop cropInfo = CropFactory.createCrop(cropName);
			moneyMade = cropInfo.getSellPrice();
			farmer.subCropInven(cropName, 1);
			farm.increaseMoney(moneyMade);
			returnStrings += "You sell " + cropName + " by $" + moneyMade + "\r\n";
		}
		else
		{
			returnStrings += "You don't have " + cropName + " enough!\r\n";
		}
		
		
		return returnStrings;
	}
	
	/*
	 - Every week, we..
	 - check whether he/she accomplished the weekly goal
	 - give bonus
	 - set new weekly goal
	 - notice him/her about new weekly goal
	 */
	public String dealwithGoal()
	{
		String returnString = "";
		if(isGoal()) //Check
		{
			returnString += "Congratulations! You did it! \r\n";
			
			//Give Bonus
			double initMoney = farm.getMoney();
			int initSpace = farm.getCropSpace();
			
			farm.increaseMoney(50); //Bonus Money
			farm.increaseSpace(2); //Bonus Space
			
			returnString += "You get the Bonus: \r\n";
			returnString += "1) Money: " + initMoney + "(+50)";
			returnString += "2) Space: " + initSpace + "(+2)";
		}
		else
		{
			returnString += "You failed to achieve the goal. \r\n";
		}
		
		goal += 100; //Set New Goal
		returnString += "New Weekly Goal: $" + goal + "\r\n";
		
		return returnString;
	}
	
	//Return whether the user achieve the goal!
	public boolean isGoal()
	{
		if(farm.getMoney() >= goal)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Make Product
	public String makeProduct(String prodName)
	{
		String returnString = "";
		
		Product prod = ProductFactory.createProduct(prodName);
		if(!farmer.canProd(prod))
		{
			returnString += "You need more ingredients to produce " + prodName + "\r\n";
		}
		else
		{
			Map<String, Integer> cropInven = farmer.getCropInven();
			Map<String, Integer> itemInven = farmer.getItemInven();
			Map<String, Integer> ingredients = prod.getIngrd();
			
			//Before using this function, "canProd()" must be called
			for(String kindIngrd : ingredients.keySet())
			{
				int neededIngrd = ingredients.get(kindIngrd);
				
				//What I wanna produce is in the cropInven and there are ingredients more than I needed
				if(cropInven.containsKey(kindIngrd) && (farmer.getCropValue(kindIngrd) >= neededIngrd))
				{
					farmer.subCropInven(kindIngrd, neededIngrd);
					continue;
				} //For CropInventory
				
				//What I wanna produce is in the itemInven and there are ingredients more than I needed
				else if(itemInven.containsKey(kindIngrd) && (farmer.getItemValue(kindIngrd) >= neededIngrd))
				{
					farmer.subItemInven(kindIngrd, neededIngrd);
					int decItemIdx = -1; findItemIndex(kindIngrd);
					for(int i=0 ; i<neededIngrd; i++)
					{
						decItemIdx = findItemIndex(kindIngrd);
						farm.decreaseItems(decItemIdx);
					}
					continue;
				}
				
				else
				{
					returnString += "There's an error while producing the product! : Code 1\r\n";
					return returnString;
				}
			}
			
			//Decrease the farmer's strength for producing
			if(farmer.getFarmerStrength() >= prod.getDecStr())
			{
				farmer.subStrength(prod.getDecStr());
			}
			else
			{
				returnString += "There's an error while producing the product! : Code 2\r\n";
				return returnString;
			}
			
			double moneyMade = prod.getSellPrice();
			farm.increaseMoney(moneyMade);
			
			returnString += "Producing " + prodName + " is successfully complete! \r\n";
			returnString += "You earn $" + prod.getSellPrice() + "\r\n";
		}
		return returnString;
	}
	
	
	// A method to launch the main screen
	public void launchMainScreen()
	{
		mainScreen = new MainScreen(this);
	}
	
	//
	public void closeMainScreen(MainScreen mainWindow)
	{
		mainWindow.closeWindow();
	}
	
	// A method to launch the setup screen
	public void launchSetupScreen()
	{
		setupWindow = new SetupScreen(this);
	}
	
	//A method to close the setup screen
	public void closeSetupScreen(SetupScreen setupWindow)
	{
		setupWindow.closeWindow();
		launchMainScreen(); // Only here for closing the setup screen, as this is used once.
	}
	
	//A method to launch the store screen
	public void launchStoreScreen()
	{
		new StoreScreen(this);
	}
	
	//A method to close the store screen
	public void closeStoreScreen(StoreScreen storeWindow)
	{
		storeWindow.closeWindow();
	}
	//A method to launch the tend to crops screen
	public void launchTendCropsScreen()
	{
		new TendCropsScreen(this);
	}
	
	//A method to close the tend to crops screen
	public void closeTendCropsScreen(TendCropsScreen tendCropsWindow)
	{
		tendCropsWindow.closeWindow();
	}
	

	//Return a String array only with the name of all of the crops
	public String[] returnCropTypeArray()
	{
		ArrayList<Crop> differentCrops = returnDifferentCropsOwned();
		ArrayList<String> differentCropNames = new ArrayList<String>();
		for(Crop crop: differentCrops) 
		{
			differentCropNames.add(crop.getName());
		}
		String[] cropArray = differentCropNames.toArray(new String[0]);
		return cropArray;
	}
	
	//Return a String array only with the name of all of the items
	public String[] returnCurrentItemsArray(String itemType) 
	{
		ArrayList<String> currentItems = new ArrayList<String>();
		
		if (itemType == "Crop") 
		{
				currentItems.add("Water (free)");
		}
		
		for(Item item: farm.getItems()) 
		{
			if (item.getType() == itemType)
			{
				currentItems.add(item.getName());
			}
		}
		
		String[] currentItemsArray = currentItems.toArray(new String[0]);
		return currentItemsArray;
	}
	
	//Return a String for displaying each crop and its status.
	public String returnStatusCrops()
	{
		String returnString = "";
		returnString += farm.getFarmName() + " has " + farm.getCrops().size() + " crops\r\n";
		for(Crop crop: farm.getCrops()) 
		{
			if (crop.getDayMax() > 0) 
			{
				returnString += crop.getName() + " Has been growing for " + crop.getDayGrown() 
				+ " days, it needs " + (crop.getDayMax()-crop.getDayGrown())+ " more days to be harvested\n"
				+ "The remain Sun score is " + crop.getLeftSun() +". The remain Rain score is " + crop.getLeftRain() +"\n"
				;
			}
			else 
			{
				returnString += crop.getName() + " Has been growing for " + crop.getDayGrown() 
				+ " days, it is ready to harvest!\r\n";
			}
		}
		
		return returnString;
	}
	
	//A function for returning the items
	public String returnItemsString()
	{
		String returnString;
		if (farm.getItems().size() == 0)
		{
			returnString = farm.getFarmName() + " owns " + farm.getItems().size() + " Items.";
		}
		else
		{
			returnString = farm.getFarmName() + " owns " + farm.getItems().size() + " Items, They are:\r\n";
		}
		
		for(Item item: farm.getItems()) 
		{
			returnString += "- " + item.getName() + "\r\n";
		}
		return returnString;
	}
	
	
	//A function for returning the harvested crops (Related to Farm.crops)
	public String returnCropsString()
	{
		String returnString;
		if (farm.getCrops().size() == 0)
		{
			returnString = farm.getFarmName() + " owns " + farm.getCrops().size() + ".";
		}
		else
		{
			returnString = farm.getFarmName() + " owns " + farm.getItems().size() + ", They are:\r\n";
		}
		
		for(Crop crop: farm.getCrops()) 
		{
			returnString += "- " + crop.getName() + "\r\n";
		}
		return returnString;
	}
	
	//Return the money the farm earns
	public String returnMoneyString()
	{
		return returnDollarsCents(farm.getMoney());
	}
	
	//Return the crop space available
	public int returnFreeCropSpace()
	{
		return farm.calculateFreeSpace();
	}
	
	//Return the days of the farm has been passed
	public int returnDays()
	{
		return farmer.getDays();
	}
	
	//Return String Array of crops with the crops
	public String[] returnCropArray()
	{
		ArrayList<String> cropArrayList = new ArrayList<String>();
		for(Crop crop: store.getCropsInStore()) 
		{
			cropArrayList.add(crop.getName()
			+ ", Purchase price: $" + returnDollarsCents(crop.getBuyPrice())
			+ ", Harvest sell price: $" + returnDollarsCents(crop.getSellPrice())
			+ ", Days to grow: " + crop.getDayMax()); 
		}
		String[] cropArray = cropArrayList.toArray(new String[0]);
		return cropArray;
	}
	
	//Take a purchaseOption(=index) and purchases the Crop
	public String purchaseCrop(int purchaseOption)
	{
		String purchaseCropString = "";
			if (farm.calculateFreeSpace() > 0) 
			{
				if (farm.getMoney() < store.getCropsInStore().get(purchaseOption).getBuyPrice()) 
				{
					purchaseCropString = "You don't have enough money to buy " + store.getCropsInStore().get(purchaseOption).getName() + "!" ;
				}
				else
				{
					farm.increaseCrops(store.getCropsInStore().get(purchaseOption));
					purchaseCropString = store.getCropsInStore().get(purchaseOption).getName() + " bought!";
				}
			}
			else 
			{
				purchaseCropString = "You have no space available for new crops!\n"
						+ "To purchase crops again, you need to either tend to the farm land to increase crop space or harvest crops to remove current crops";
			}
		return purchaseCropString;
	}
	
	
	
	//Return String Array of items with the items
	public String[] returnItemArray()
	{
		ArrayList<String> itemArrayList = new ArrayList<String>();
		for(Item item: store.getItemsInStore()) 
		{
			String tempString = item.getName() + ", Purchase price: $" + returnDollarsCents(item.getBuyPrice());
			if (item.getType() == "Crop")
			{
				tempString += ", Benefit when used: +" + Math.round(item.getBonus()) + " day growth increase for chosen crop type";
			}

			itemArrayList.add(tempString);
			
		}
		String[] itemArray = itemArrayList.toArray(new String[0]);
		return itemArray;
	}
	
	//Take a purchaseOption(=index) and purchases the Item
	public String purchaseItem(int purchaseOption)
	{
		String purchaseItemString = "";
		if (farm.getMoney() < store.getItemsInStore().get(purchaseOption).getBuyPrice()) 
		{
			purchaseItemString = "You don't have enough money to buy " + store.getItemsInStore().get(purchaseOption).getName() + "!" ;
		}
		else
		{
			farm.increaseItems(store.getItemsInStore().get(purchaseOption));
			farmer.addItemInven(store.getItemsInStore().get(purchaseOption).getName());
			purchaseItemString = store.getItemsInStore().get(purchaseOption).getName() + " bought!";
		}
	return purchaseItemString;
	}
	
	//Return the number of Items whose type is "crop"
	public int returnCropItemSize()
	{
		int size = 0;
		for (Item item: farm.getItems()) 
		{
			if (item.getType() == "Crop") 
			{
				size++;
			}
		}
		return size;
	}
	
	
	//Return the array about the different types of each crop
	public ArrayList<Crop> returnDifferentCropsOwned()
	{
		ArrayList<Crop> differentCrops = new ArrayList<Crop>();
		for(Crop crop: store.getCropsInStore())
		{
			for(Crop cropCheck: farm.getCrops()) 
			{
				if (crop.getName() == cropCheck.getName() && !differentCrops.contains(crop))
				{
					differentCrops.add(crop);
				}
			}
		}
		return differentCrops;
	}
	
	//Show Crop Inventory (Related to farmer.crops / already harvested)
	public String showCropInven()
	{
		Map<String, Integer> cropIv = farmer.getCropInven();
		String result = "";
		
		for(String cropName : cropIv.keySet())
		{
			result += cropName + "(" + cropIv.get(cropName) + ")\n";
		}		
		return result;
	}
	
	//A function to check whether a string has only alphabetical letters.
	public boolean isAlpha(String name)
	{
	    return name.matches("[a-zA-Z ]+");
	}
	
	//Return a double as a string with two decimal places, for use with dollars and cents
	public String returnDollarsCents(double amount)
	{
		return String.format("%.2f", amount);
	}
	
	//Return the ArrayList crops from the farm
	public ArrayList<Crop> getCrops()
	{
		return farm.getCrops();
	}
	
	//Main function of the program
	public static void main(String[] args)
	{
		GameCenter game = new GameCenter();
		game.launchSetupScreen();
	}
	
	//Return the farmer class of the game
	public Farmer getFarmer()
	{
		return this.farmer;
	}
	
	//Return the weather class of the game
	public Weather getWeather()
	{
		return this.weather;
	}
	
	//Return the farm class of the game
	public Farm getFarm()
	{
		return this.farm;
	}
	
	//Return the store class of the game
	public Store getStore()
	{
		return this.store;
	}
	
	//Return weekly goal
	public double getGoal()
	{
		return this.goal;
	}

}