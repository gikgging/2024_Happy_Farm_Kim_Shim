package main;

import items.Compost;
import items.Egg;
import items.Namool;
import items.RedBull;

public class ItemFactory {
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
