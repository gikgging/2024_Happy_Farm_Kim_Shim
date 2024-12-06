package main;

import items.Compost;
import items.Egg;
import items.Namool;
import items.RedBull;

//ItemFactory give information about the item which we only know the name
public class ItemFactory {
	
	//Give info about the crop
    public static Item createItem(String itemName) {
        switch(itemName) {
            case "Compost":
                return new Compost();
            case "Egg":
                return new Egg();
            case "Namool":
                return new Namool();
            case "RedBull":
                return new RedBull();
            default:
                return null;
        }
    }
}
