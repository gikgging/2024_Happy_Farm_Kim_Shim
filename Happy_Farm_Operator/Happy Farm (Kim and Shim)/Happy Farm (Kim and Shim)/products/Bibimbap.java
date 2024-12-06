package products;

import main.Product;

/**
 * Bibimbap class extending the Product class.
 */

public class Bibimbap extends Product
{
	public Bibimbap()
	{
		super("Bibimbap", 600.0, 17); //Name, SalePrice(double), Strength(int)
		super.addIngrd("Rice", 4);
		super.addIngrd("Cabbage", 1);
		super.addIngrd("Garlic", 1);
		super.addIngrd("Pepper", 2);
		super.addIngrd("Egg", 1);
		super.addIngrd("Namool", 1);
	}
	
    public String getName() {
        return "Bibimbap";
    }
}

