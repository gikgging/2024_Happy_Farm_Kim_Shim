package products;

import main.Product;

/**
 * Baguette class extending the Product class.
 */

public class Baguette extends Product
{
	public Baguette()
	{
		super("Baguette", 120.0, 10); //Name, SalePrice(double), Strength(int)
		super.addIngrd("Wheat", 5);
	}
	
    public String getName() {
        return "Baguette";
        
    }
}
