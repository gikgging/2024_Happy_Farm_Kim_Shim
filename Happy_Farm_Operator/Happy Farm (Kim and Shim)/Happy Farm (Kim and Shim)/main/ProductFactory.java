package main;

import java.util.Map;

import products.Baguette;
import products.Bibimbap;
import products.Bread;
import products.Kimchi;

//To get the Product Name, Product ingredient
public class ProductFactory {
    public static Product createProduct(String productName) {
        switch(productName) {
            case "Baguette":
                return new Baguette();
            case "Bibimbap":
                return new Bibimbap();
            case "Bread":
                return new Bread();
            case "Kimchi":
                return new Kimchi();
            default:
                return null; 
        }
    }
    
    
    //To make a product list with ingredient
    public static String[] getAllProduct() {
        String[] productNames = new String[4]; 

       
        productNames[0] = ProductWithIngredients(new Baguette());
        productNames[1] = ProductWithIngredients(new Bibimbap());
        productNames[2] = ProductWithIngredients(new Bread());
        productNames[3] = ProductWithIngredients(new Kimchi());

        return productNames;
    }

    
    private static String ProductWithIngredients(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(product.getName()).append(" (");

    
        Map<String, Integer> ingredients = product.getIngrd();
        boolean first = true;

        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if (!first) {
                sb.append(", "); 
            }
            sb.append(entry.getKey()).append(", ").append(entry.getValue());
            first = false;
        }

        sb.append(")");
        return sb.toString();
    }

}
