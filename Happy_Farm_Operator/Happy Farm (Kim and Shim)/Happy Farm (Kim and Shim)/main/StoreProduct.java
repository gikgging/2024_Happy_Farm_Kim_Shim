package main;

// StoreProduct interface : This interface specifies what methods classes with StoreProduct(crop, item, product) implemented should have. 
 
public interface StoreProduct 
{

	//A method to get the purchase price of a store product of any crop, item, product.
	double getBuyPrice();
	
	//A method to get the name of a store product of any crop, item, product.
	String getName();
}
