package main;

import java.util.Map;

import products.Baguette;
import products.Bibimbap;
import products.Bread;
import products.Kimchi;

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
    
    
    public static String[] getAllProduct() {
        // 제품 배열의 크기 지정
        String[] productNames = new String[4]; // 4개의 제품이 있으므로

        // 각 제품의 이름과 재료를 가져와서 포맷
        productNames[0] = ProductWithIngredients(new Baguette());
        productNames[1] = ProductWithIngredients(new Bibimbap());
        productNames[2] = ProductWithIngredients(new Bread());
        productNames[3] = ProductWithIngredients(new Kimchi());

        return productNames;
    }

    // 재료와 함께 제품 이름을 포맷하는 헬퍼 메소드
    private static String ProductWithIngredients(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append(product.getName()).append(" (");

        // 재료 가져오기
        Map<String, Integer> ingredients = product.getIngrd();
        boolean first = true;

        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if (!first) {
                sb.append(", "); // 첫 번째 항목이 아닐 때는 쉼표 추가
            }
            sb.append(entry.getKey()).append(", ").append(entry.getValue());
            first = false;
        }

        sb.append(")");
        return sb.toString();
    }

}
