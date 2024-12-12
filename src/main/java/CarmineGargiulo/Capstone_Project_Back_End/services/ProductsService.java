package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.ProductsRepository;
import CarmineGargiulo.Capstone_Project_Back_End.tools.SpoonacularSender;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SpoonacularSender spoonacularSender;

    public Product getProductByReference(long reference) {
        return productsRepository.findByReference(reference).orElseThrow(() -> new NotFoundException("Product not " +
                "founded"));
    }

    public Product saveProductByJson(long reference, JSONObject productObj) {
        try {
            return getProductByReference(reference);
        } catch (NotFoundException e) {
            String productImg = "https://img.spoonacular.com/ingredients_250x250/" + productObj.getString("image");
            String productName = productObj.getString("nameClean");
            return productsRepository.save(new Product(reference, productImg, productName));
        }
    }

    public Product saveProduct(Product product) {
        if (productsRepository.existsByReference(product.getReference()))
            throw new BadRequestException("Product already exists");
        return productsRepository.save(product);
    }

    public List<Product> getCommonProducts() {
        List<Long> references = List.of(9037L, 1001L, 11124L, 1123L, 1077L, 11282L, 11352L, 5062L, 18064L, 11215L,
                20444L, 11529L, 1026L, 1041009L, 11260L, 20420L);
        List<Product> commonProducts = new ArrayList<>();
        references.forEach(r -> {
            try {
                Product product = getProductByReference(r);
                commonProducts.add(product);
            } catch (NotFoundException ignored) {

            }
        });
        return commonProducts;
    }

    public HashMap getProductsByQuery(String query) {
        return spoonacularSender.getProductsByQuery(query);
    }
}
