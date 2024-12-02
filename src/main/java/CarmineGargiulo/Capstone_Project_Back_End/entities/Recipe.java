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
@Table(name = "recipes")
@JsonIgnoreProperties({"meals"})
public class Recipe {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "recipe_id")
    private long recipeId;
    @Column(nullable = false)
    private long reference;
    @Column(nullable = false, name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double calories;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns =
    @JoinColumn(name = "ingredient_id"), name = "recipes_ingredients")
    private List<Ingredient> ingredientList;
    @OneToMany(mappedBy = "recipe")
    private List<Meal> meals;

    public Recipe(long reference, String imageUrl, String name, double calories) {
        this.reference = reference;
        this.imageUrl = imageUrl;
        this.name = name;
        this.calories = calories;
    }
}
