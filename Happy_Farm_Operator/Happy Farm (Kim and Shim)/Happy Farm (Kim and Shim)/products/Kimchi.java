package products;

import main.Product;

/**
 * Kimchi class extending the Product class.
 */

public class Kimchi extends Product
{
	public Kimchi()
	{
		super("Kimchi", 420.0, 10); //Name, SalePrice(double), Strength(int)
		super.addIngrd("Cabbage", 1);
		super.addIngrd("Garlic", 3);
		super.addIngrd("Pepper", 2);
	}
	
	public String getName() {
        return "Kimchi";
    }
}
