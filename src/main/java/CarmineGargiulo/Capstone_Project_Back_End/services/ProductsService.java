package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.ProductsRepository;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Product getProductByReference(long reference, JSONObject productObj) {
        return productsRepository.findByReference(reference).orElseGet(() -> saveProductByJson(reference, productObj));
    }

    public Product saveProductByJson(long reference, JSONObject productObj) {
        String productImg = "https://img.spoonacular.com/ingredients_250x250/" + productObj.getString("image");
        String productName = productObj.getString("nameClean");
        return productsRepository.save(new Product(reference, productImg, productName));
    }
}
