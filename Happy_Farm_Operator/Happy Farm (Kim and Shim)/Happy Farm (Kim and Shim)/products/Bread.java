package products;

import main.Product;

/**
 * Bread class extending the Product class.
 */

public class Bread extends Product
{
	public Bread()
	{
		super("Bread", 90.0, 8); //Name, SalePrice(double), Strength(int)
		super.addIngrd("Wheat", 2);
		super.addIngrd("Egg", 2);
	}
	public String getName() {
        return "Bread";
    }
}
