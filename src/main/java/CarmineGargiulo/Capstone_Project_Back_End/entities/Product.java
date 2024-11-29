package CarmineGargiulo.Capstone_Project_Back_End.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "product_id")
    private long productId;
    @Column(nullable = false, unique = true)
    private long reference;
    @Column(nullable = false, name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private String name;

    public Product(long reference, String imageUrl, String name) {
        this.reference = reference;
        this.imageUrl = imageUrl;
        this.name = name;
    }
}
