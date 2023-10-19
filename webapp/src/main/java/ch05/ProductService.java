package ch05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    Map<String, Product> products = new HashMap<>();

    public ProductService() {
        Product p = new Product("101", "애플사과폰 12", "애플전자", 12000000, "2020.12.12");
        products.put("101", p);
        p = new Product("102", "삼전우주폰 21", "삼성전자", 130, "2021.2.2");
        products.put("102", p);
        p = new Product("103", "엘스듀얼폰", "엘스전자", 15000000, "2021.3.2");
        products.put("103", p);
    }
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product find(String id){
        return products.get(id);
    }
}