package CarmineGargiulo.Capstone_Project_Back_End.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "ingredients")
@JsonIgnoreProperties({"recipeList"})
public class Ingredient {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "ingredient_id")
    private long ingredientId;
    @Column(nullable = false)
    private double qty;
    @Column(nullable = false)
    private String unit;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToMany(mappedBy = "ingredientList")
    private List<Recipe> recipeList;

    public Ingredient(double qty, String unit, Product product) {
        this.qty = qty;
        this.unit = unit;
        this.product = product;
    }
}
