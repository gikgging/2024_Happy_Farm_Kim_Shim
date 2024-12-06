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
	        String results = dealwithGoal(); //Deal with goal
	        //Call
	        mainScreen.showGoalResults(results); // MainScreen에서 결과를 보여줌
	    }
	}
	
	/**
	 * A function for testing if the game can finish or not by returning true if the farmers age is equal to the number of days + 1, the +1 to have the game finish after the numberOfDays day.
	 * @return true or false depending on the farmers age.
	 */
	//The condition of finishing this game
	public boolean gameFinishing() 
	{
		return false; //game이 끝나는 조건
	}
	
	//Find Item from the list of Items
	public int findItemIndex(String itemName)
	{
		int count = 0;
		int index = -1; //-1로 고쳐봄
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
	
	/**
	 * Tends to all of the crops owned with the specified <code>cropIndex</code> and <code>itemName</code>.
	 * @param cropIndex The crop index
	 * @param itemName The item name
	 * @return String identifying the action performed.
	 */
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
	

	/**
	 * A function that harvests all crops that can be harvested.
	 * @return The total money made form harvesting the crops.
	 */
	public void harvestAvailableCrops() //Inventory로 저장되게끔 수정
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
	
	/**
	 * A function that will harvest the crops that can be harvested, 
	 * it does this by calling the harvestAvailableCrops function in the Farm class.
	 * @return String identifying the action performed.
	 */
	//Harvest all the possible crops
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
	
	/**
	 * Every week, we..
	 * check whether he/she accomplished the weekly goal
	 * give bonus
	 * set new weekly goal
	 * notice him/her about new weekly goal
	 * @return returnString
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
	

	/**
	 * The finishGame function, called when the farmers age has reached the numDays set during startup.
	 * Prints out the farmers name, the number of days passed, the money made and the score.
	 * @return finish game dialog String
	 */
	public String finishGame() //Finish the game
	{
		String profitString;
		double scoreProfit = farm.getProfit();
		double scoreCropSpace = farm.getCropSpace();
		
		if (scoreProfit <= 0.0) 
		{
			scoreProfit = 0;
			profitString = " made no profit!\r\n";
		}
		else 
		{
			profitString = " made $" + returnDollarsCents(scoreProfit) + " in profit.\r\n";
		}
		
		 String returnString = "The game has finished!\n\n"
				+ "Stats for " + farmer.getFarmerName() + " on the farm " + farm.getFarmName() + ":\r\n"
				+ (farmer.getDays() - 1) + " days have passed.\r\n"
				+ farmer.getFarmerName() + profitString + "\r\n"
				+ "The size of your farm is " + scoreCropSpace
				+ "Thank you for enjoying our game!!\r\n";
		 return returnString;
	}
	
	/**
	 * A method to launch the main screen where the user controls the game.
	 */
	public void launchMainScreen()
	{
		mainScreen = new MainScreen(this);
	}
	
	/**
	 * A method to close the main screen
	 * @param mainWindow The main screen.
	 */
	public void closeMainScreen(MainScreen mainWindow)
	{
		mainWindow.closeWindow();
	}
	
	/**
	 * A method to launch the setup screen where the user sets up the game.
	 */
	public void launchSetupScreen()
	{
		setupWindow = new SetupScreen(this);
	}
	
	/**
	 * A method to close the setup screen.
	 * @param setupWindow The setup screen.
	 */
	public void closeSetupScreen(SetupScreen setupWindow)
	{
		setupWindow.closeWindow();
		launchMainScreen(); // Only here for closing the setup screen, as this is used once.
	}
	
	/**
	 * A method to launch the store screen where the user buys crops, animals and items.
	 */
	public void launchStoreScreen()
	{
		new StoreScreen(this);
	}
	
	/**
	 * A method to close the store screen
	 * @param storeWindow The store screen.
	 */
	public void closeStoreScreen(StoreScreen storeWindow)
	{
		storeWindow.closeWindow();
	}
	/**
	 * A method to launch the tend to crops screen where the user tends to crops by using items.
	 */
	public void launchTendCropsScreen()
	{
		new TendCropsScreen(this);
	}
	
	/**
	 * A method to close the tend to crops screen
	 * @param tendCropsWindow The tend crops screen.
	 */
	public void closeTendCropsScreen(TendCropsScreen tendCropsWindow)
	{
		tendCropsWindow.closeWindow();
	}
	

	/**
	 * Returns a String array with the name of all of the crops currently owned.
	 * @return String Array containing names of Animals
	 * 
	 */
	public String[] returnCropTypeArray()  //모든 Crop의 이름만 들어있는 Array
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
	
	/**
	 * Returns a String array with the name of all of the items currently owned given the correct String <code>itemType</code>.
	 * @param itemType The item's type as a String.
	 * @return String Array containing names of items
	 */
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
	
	/**
	 * Returns a String formated correctly for displaying each crop and animal and its status.
	 * @return string containing status of crops and animals
	 */
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
	
	/**
	 * A function for returning the items the user currently owns in a string format with one item per line.
	 * @return string of items
	 */
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
	
	
	/**
	 * A function for returning the harvested crops the user currently owns in a string format with one item per line.
	 * @return string of items
	 */
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
	
	/**
	 * Returns the money the farm currently has in string format to have it to 2dp.
	 * @return farm money
	 */
	public String returnMoneyString()
	{
		return returnDollarsCents(farm.getMoney());
	}
	
	/**
	 * Returns the crop space available, used when the user wants to buy more crops.
	 * @return free crop space
	 */
	public int returnFreeCropSpace()
	{
		return farm.calculateFreeSpace();
	}
	
	/**
	 * Returns the age of the farmer. Used to check the number of.
	 * @return Age of farmer
	 */
	public int returnDays()
	{
		return farmer.getDays();
	}
	
	/**
	 * Returns String Array of crops with the crops formated so that each crop has its details and price on one line.
	 * @return String Array of crops
	 */
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
	
	/**
	 * Takes a purchaseOption index and purchases the Crop at that index in the farm crops ArrayList.
	 * @param purchaseOption Crop the user chose to buy.
	 * @return String detailing what the user did.
	 */
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
	
	
	
	/**
	 * Returns String Array of items with the items formated so that each item has its details and price on one line.
	 * @return String Array of items
	 */
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
	
	/**
	 * Takes a purchaseOption index and purchases the Item at that index in the farm items ArrayList.
	 * @param purchaseOption Item the user chose to buy.
	 * @return String detailing what the user did.
	 */
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
	
	/**
	 * Returns the number of items the user has that have the type "Crop".
	 * @return integer of the number of items of type "Crop".
	 */
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
	
	
	/**
	 * Returns the different types of crops owned by their Name.
	 * @return Crop ArrayList of crops.
	 */
	public ArrayList<Crop> returnDifferentCropsOwned() //모든 Crop들이 하나씩만 있는 ArrayList
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
	
	/**
	 * A simple function to check whether a string has only alphabetical letters.
	 * Returns false if it does not.
	 * @param name The name being tested for only Alpha characters.
	 * @return True or false depending on the characters in <code>name</code>
	 */
	public boolean isAlpha(String name)
	{
	    return name.matches("[a-zA-Z ]+");
	}
	
	/**
	 * Returns a double as a string with two decimal places, for use with dollars and cents.
	 * @param amount The amount of money owning.
	 * @return String format of a double with 2dp
	 */
	public String returnDollarsCents(double amount)
	{
		return String.format("%.2f", amount);
	}
	
	/**
	 * Returns the ArrayList crops from the farm class.
	 * @return ArrayList of crops owned.
	 */
	public ArrayList<Crop> getCrops()
	{
		return farm.getCrops();
	}
	
	/**
	 * main function of the program. this is where the game is started by calling the startGame and mainGame methods.
	 * @param args An array of command line arguments for the application.
	 */
	public static void main(String[] args)
	{
		GameCenter game = new GameCenter();
		game.launchSetupScreen();
	}
	
	public Farmer getFarmer()
	{
		return this.farmer;
	}
	
	public Weather getWeather()
	{
		return this.weather;
	}
	
	public Farm getFarm()
	{
		return this.farm;
	}
	
	public Store getStore()
	{
		return this.store;
	}
	
	public double getGoal()
	{
		return this.goal;
	}

}